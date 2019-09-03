package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.pirlo.entities.Hospital;
import org.pirlo.entities.Operator;
import org.pirlo.enums.OperatorTypeEnum;
import org.pirlo.enums.RoleEnum;
import org.pirlo.facades.HospitalFacade;
import org.pirlo.facades.OperatorFacade;
import org.pirlo.utils.Utils;

@ManagedBean
@ViewScoped
public class PirloBean implements Serializable {

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    HospitalFacade hospitalFacade;
    @EJB
    OperatorFacade operatorFacade;

    Hospital hospital = new Hospital();
    Operator operator = new Operator();

    List<Hospital> hospitalList = new ArrayList<>();
    List<Operator> operatorList = new ArrayList<>();

    private final String HOSPITAL_DLG = "hospital-dlg";
    private final String ADMIN_DLG = "admin-dlg";

    @PostConstruct
    public void init() {
        try {
            hospitalList = hospitalFacade.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOperator() {
        try {
            operator.setHospital(hospital);
            operator.setRole(RoleEnum.ADMIN);
            operator.setRecievingEmergencyEmails(true);
            operator.setType(OperatorTypeEnum.QUALITY_MANAGER);
            try {
                operator.setPassword(Utils.convertToSHA256("123456"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            operator = operatorFacade.save(operator);
            operatorList.add(operator);
            operator = new Operator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectOperator(Operator tmpOperator) {
        operator = tmpOperator;
    }

    public void deleteOperator() {
        operatorList.remove(operator);
        operatorFacade.remove(operator);
    }

    public void saveHospital() {

        boolean check = false;
        if (hospital.getId() == null) {
            check = true;
        }
        if (hospital.getRepresentativeEmail().matches("(^(.+)@(.+)$)")) {
            hospital = hospitalFacade.save(hospital);
            if (check) {
                hospitalList.add(hospital);
            }
            utilityBean.hideModal(HOSPITAL_DLG);
        } else {
            System.out.println("email false");
        }
    }

    public void selectHospital(Hospital tmpHospital) {
        hospital = tmpHospital;
        if (hospital == null) {
            hospital = new Hospital();
        } else {
            operatorList = operatorFacade.findByHospitalAndRole(hospital, RoleEnum.ADMIN);
        }
    }

    public void showHospitalAdminsModal(Hospital tmpHospital) {
        hospital = tmpHospital;
        operatorList = operatorFacade.findByHospitalAndRole(hospital, RoleEnum.ADMIN);
        utilityBean.showModal(ADMIN_DLG);
    }

    public void deleteHospital() {
        hospitalList.remove(hospital);
        hospitalFacade.remove(hospital);
    }

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public HospitalFacade getHospitalFacade() {
        return hospitalFacade;
    }

    public void setHospitalFacade(HospitalFacade hospitalFacade) {
        this.hospitalFacade = hospitalFacade;
    }

    public List<Hospital> getHospitalList() {
        return hospitalList;
    }

    public void setHospitalList(List<Hospital> hospitalList) {
        this.hospitalList = hospitalList;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
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

}
