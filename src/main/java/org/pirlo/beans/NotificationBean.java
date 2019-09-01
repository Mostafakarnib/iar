package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.pirlo.entities.Notification;
import org.pirlo.entities.Operator;
import org.pirlo.facades.NotificationFacade;
import org.pirlo.facades.OperatorFacade;

@ManagedBean
@ViewScoped
public class NotificationBean implements Serializable
{

    @ManagedProperty(value = "#{templateBean}")
    private TemplateBean templateBean;

    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    @EJB
    NotificationFacade notificationFacade;
    @EJB
    OperatorFacade operatorFacade;

    Notification notification = new Notification();
    List<Notification> notificationList = new ArrayList<>();

    List<Operator> operatorList = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        operatorList = operatorFacade.findByHospital(loginBean.operator.getHospital());
        notificationList = notificationFacade.findAll();
    }

    public TemplateBean getTemplateBean()
    {
        return templateBean;
    }

    public void setTemplateBean(TemplateBean templateBean)
    {
        this.templateBean = templateBean;
    }

    public UtilityBean getUtilityBean()
    {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean)
    {
        this.utilityBean = utilityBean;
    }

    public LoginBean getLoginBean()
    {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean)
    {
        this.loginBean = loginBean;
    }

    public NotificationFacade getNotificationFacade()
    {
        return notificationFacade;
    }

    public void setNotificationFacade(NotificationFacade notificationFacade)
    {
        this.notificationFacade = notificationFacade;
    }

    public OperatorFacade getOperatorFacade()
    {
        return operatorFacade;
    }

    public void setOperatorFacade(OperatorFacade operatorFacade)
    {
        this.operatorFacade = operatorFacade;
    }

    public Notification getNotification()
    {
        return notification;
    }

    public void setNotification(Notification notification)
    {
        this.notification = notification;
    }

    public List<Operator> getOperatorList()
    {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList)
    {
        this.operatorList = operatorList;
    }

    public List<Notification> getNotificationList()
    {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList)
    {
        this.notificationList = notificationList;
    }

}
