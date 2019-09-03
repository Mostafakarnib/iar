/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.pirlo.entities.Operator;
import org.pirlo.enums.RoleEnum;
import org.pirlo.enums.ToasterEnum;
import org.pirlo.facades.OperatorFacade;
import org.pirlo.utils.Utils;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;

    @EJB
    OperatorFacade operatorFacade;

    Operator operator = new Operator();

    String username;
    String password;
    String pwd1;
    String pwd2;
    String originalURL;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance()
                .getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(
                RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/home.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap()
                    .get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }

    public void login() {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext
                .getRequest();

        try {
            operator = operatorFacade.getOperatorByUsername(username);

            if (operator == null) {
                context.addMessage(null, new FacesMessage("Wrong username or password"));
                return;
            }

            String pageUrl = "/views/";
            request.login(username, password);
            RoleEnum role = operator.getRole();
            //OperatorTypeEnum type = operator.getType();
            switch (role) {
                case ROOT:
                    pageUrl = pageUrl + "pirlo/hospitals.xhtml";
                    break;
                case ADMIN:
                    pageUrl = pageUrl + "dashboard.xhtml";
                    break;
                case USER:
                    pageUrl = pageUrl + "tickets.xhtml";
                    break;
            }

            externalContext.redirect(externalContext.getRequestContextPath() + pageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage("Wrong username or password"));
        }
    }

    public void logout() {
        try {
            operator = null;
            pwd1 = "";
            pwd2 = "";
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.invalidateSession();
            ec.redirect(ec.getRequestContextPath() + "/login/login.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
            //utilityBean.showToastr(e.getMessage(), Toaster.error.name());
        }
    }

    public void changePassword() {
        try {
            if (pwd1 == null ? pwd2 == null : pwd1.equals(pwd2)) {
                operator.setPassword(Utils.convertToSHA256(pwd1));
                operator = operatorFacade.save(operator);
                operator = operatorFacade.refresh(operator);
                pwd1 = "";
                pwd2 = "";
                utilityBean.hideModal("password-dlg");
            } else {
                utilityBean.showToastr("Passwords do not match!", ToasterEnum.error.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
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
