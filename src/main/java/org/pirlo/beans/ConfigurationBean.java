package org.pirlo.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.pirlo.entities.Configuration;
import org.pirlo.enums.LangEnum;
import org.pirlo.facades.ConfigurationFacade;

@ManagedBean
@ViewScoped
public class ConfigurationBean implements Serializable
{

    @ManagedProperty(value = "#{templateBean}")
    private TemplateBean templateBean;

    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;

    private Configuration configuration = new Configuration();

    LangEnum lang;

    @EJB
    ConfigurationFacade configurationFacade;

    @PostConstruct
    public void init()
    {
        configuration = configurationFacade.findByOperator(loginBean.operator);
    }

    public void changeLang(LangEnum tmpLang)
    {
        lang = tmpLang;
        configuration = configurationFacade.findByOperator(loginBean.operator);
        configuration.setOperator(loginBean.getOperator());
        configuration.setLang(tmpLang);
        configuration = configurationFacade.save(configuration);
        templateBean.updateViewLocale(new Locale(configuration.getLang().toString()));
        utilityBean.refreshEnumsList();
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    public ConfigurationFacade getConfigurationFacade()
    {
        return configurationFacade;
    }

    public void setConfigurationFacade(ConfigurationFacade configurationFacade)
    {
        this.configurationFacade = configurationFacade;
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

    public LangEnum getLang()
    {
        return lang;
    }

    public void setLang(LangEnum lang)
    {
        this.lang = lang;
    }

}
