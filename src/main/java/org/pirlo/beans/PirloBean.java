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
import org.pirlo.facades.HospitalFacade;

@ManagedBean
@ViewScoped
public class PirloBean implements Serializable {

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    HospitalFacade hospitalFacade;

    List<Hospital> hospitalList = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            hospitalList = hospitalFacade.findAll();
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

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

}
