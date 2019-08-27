package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.pirlo.entities.Operator;
import org.pirlo.enums.RoleEnum;
import org.pirlo.enums.ToasterEnum;
import org.pirlo.facades.OperatorFacade;
import org.pirlo.utils.Utils;

@ManagedBean
@ViewScoped
public class OperatorBean implements Serializable {

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    OperatorFacade operatorFacade;

    Operator operator = new Operator();
    List<Operator> operatorList = new ArrayList<>();

    String pwd1;
    String pwd2;

    @PostConstruct
    public void init() {
        try {
            operatorList = operatorFacade.findByHospital(loginBean.operator.getHospital());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveOperator() {
        if (operator.getId() == null) {
            try {
                operator.setPassword(Utils.convertToSHA256(operator.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            operator.setRole(RoleEnum.USER);
        }
        operator.setHospital(loginBean.operator.getHospital());

        boolean check = false;
        if (operator.getId() == null) {
            check = true;
        }
        if (operator.getEmail().matches("(^(.+)@(.+)$)")) {
            operator = operatorFacade.save(operator);
            if (check) {
                operatorList.add(operator);
            }
            utilityBean.hideModal("operator-dlg");
        }
    }

    public void selectOperator(Operator tmpOperator) {
        operator = tmpOperator;
        if (operator == null) {
            operator = new Operator();
        }
    }

    public void editOperator(Operator tmpOperator) {
        operator = tmpOperator;
        utilityBean.showModal("operator-dlg");
    }

    public void editOperatorPassword(Operator tmpOperator) {
        operator = tmpOperator;
        utilityBean.showModal("opertorpwd-dlg");
    }

    public void changePassword() {
        try {
            if (pwd1 == null ? pwd2 == null : pwd1.equals(pwd2)) {
                operator.setPassword(Utils.convertToSHA256(pwd1));
                operator = operatorFacade.save(operator);
                pwd1 = "";
                pwd2 = "";
                utilityBean.hideModal("opertorpwd-dlg");
            } else {
                utilityBean.showToastr("Passwords do not match!", ToasterEnum.error.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOperator() {
        operatorList.remove(operator);
        operatorFacade.remove(operator);
    }

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public OperatorFacade getOperatorFacade() {
        return operatorFacade;
    }

    public void setOperatorFacade(OperatorFacade operatorFacade) {
        this.operatorFacade = operatorFacade;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    public String getPwd1() {
        return pwd1;
    }

    public void setPwd1(String pwd1) {
        this.pwd1 = pwd1;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

}
