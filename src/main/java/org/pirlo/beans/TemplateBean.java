package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Ajax;
import org.pirlo.entities.Configuration;
import org.pirlo.entities.Notification;
import org.pirlo.enums.LangEnum;
import org.pirlo.enums.NotificationEnum;
import org.pirlo.enums.OutcomeEnum;
import org.pirlo.facades.ConfigurationFacade;

@ManagedBean
@SessionScoped
public class TemplateBean implements Serializable
{

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    private static final long serialVersionUID = 1L;
    String activePage;
    private LangEnum lang;

    @EJB
    ConfigurationFacade configurationFacade;

    private Locale locale;

    private Integer unreadNotificationsCount = 0;

    List<Notification> notificationList = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        try
        {
            refreshLangEnumValue();
            updateViewLocale(new Locale(lang.toString()));

            Notification notification = new Notification();
            notification.setOperator(loginBean.operator);
            notification.setOutcome(OutcomeEnum.TICKET);
            notification.setTitle("New Ticket added");
            notification.setType(NotificationEnum.NEW_TICKET);
            notificationList.add(notification);

            notification = new Notification();
            notification.setOperator(loginBean.operator);
            notification.setOutcome(OutcomeEnum.TASKS);
            notification.setTitle("New Task added");
            notification.setType(NotificationEnum.ASSIGN_TASKS);
            notificationList.add(notification);
        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void refreshLangEnumValue()
    {
        Configuration conf = configurationFacade.findByOperator(loginBean.operator);
        if (conf.getId() == null)
        {
            lang = LangEnum.en;
            conf.setLang(lang);
            conf.setOperator(loginBean.operator);
            conf = configurationFacade.save(conf);
        } else
        {
            lang = conf.getLang();
        }
    }

    public void print()
    {
        System.out.println("hello");
    }

    public String getUpperCasedLang()
    {
        return lang.toString().toUpperCase();
    }

    public void updateViewLocale(Locale locale)
    {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        refreshLangEnumValue();
        this.locale = locale;
        try
        {
            Ajax.oncomplete("onLanguageChange();");
        } catch (Exception e)
        {
            // do nothing
        }

    }

    public String getActivePage()
    {
        return activePage;
    }

    public void setActivePage(String activePage)
    {
        this.activePage = activePage;
    }

    public LangEnum getLang()
    {
        return lang;
    }

    public void setLang(LangEnum lang)
    {
        this.lang = lang;
    }

    public ConfigurationFacade getConfigurationFacade()
    {
        return configurationFacade;
    }

    public void setConfigurationFacade(ConfigurationFacade configurationFacade)
    {
        this.configurationFacade = configurationFacade;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    public LoginBean getLoginBean()
    {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean)
    {
        this.loginBean = loginBean;
    }

    public Integer getUnreadNotificationsCount()
    {
        return unreadNotificationsCount;
    }

    public void setUnreadNotificationsCount(Integer unreadNotificationsCount)
    {
        this.unreadNotificationsCount = unreadNotificationsCount;
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
