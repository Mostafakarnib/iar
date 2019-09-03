package org.pirlo.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.pirlo.enums.LangEnum;
import org.pirlo.facades.ConfigurationFacade;

@ManagedBean
@ViewScoped
public class TicketFormBean implements Serializable
{

    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    LangEnum lang = LangEnum.en;

    @EJB
    ConfigurationFacade configurationFacade;

    @PostConstruct
    public void init()
    {

    }

    public String getUpperCasedLang()
    {
        return lang.name().toUpperCase();
    }

    public void changeLang(LangEnum tmpLang)
    {
        lang = tmpLang;
    }

    public ConfigurationFacade getConfigurationFacade()
    {
        return configurationFacade;
    }

    public void setConfigurationFacade(ConfigurationFacade configurationFacade)
    {
        this.configurationFacade = configurationFacade;
    }

    public UtilityBean getUtilityBean()
    {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean)
    {
        this.utilityBean = utilityBean;
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
