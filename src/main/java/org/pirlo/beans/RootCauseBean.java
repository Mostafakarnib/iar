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
import org.pirlo.entities.RootCauseAnalysis;
import org.pirlo.facades.OperatorFacade;
import org.pirlo.facades.RootCauseAnalysisFacade;

@ManagedBean
@ViewScoped
public class RootCauseBean implements Serializable {

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    RootCauseAnalysisFacade rootCauseAnalysisFacade;
    @EJB
    OperatorFacade operatorFacade;

    RootCauseAnalysis rootCauseAnalysis = new RootCauseAnalysis();
    List<RootCauseAnalysis> rootCauseAnalysisList = new ArrayList<>();

    List<Operator> operatorList = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            operatorList = operatorFacade.findByHospital(loginBean.operator.getHospital());
            if ((loginBean.operator.getType().getBundleKey()).equalsIgnoreCase("QUALITY_MANAGER")) {
                rootCauseAnalysisList = rootCauseAnalysisFacade.findAll();
            } else {
                rootCauseAnalysisList = rootCauseAnalysisFacade.findByTargetOperator(loginBean.operator);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String isQuality() {
        return (loginBean.operator.getType().getBundleKey());

    }

    public void saveRootCauseAnalysis() {
        boolean check = false;
        if (rootCauseAnalysis.getId() == null) {
            check = true;
        }
        rootCauseAnalysis = rootCauseAnalysisFacade.save(rootCauseAnalysis);
        if (check) {
            rootCauseAnalysisList.add(rootCauseAnalysis);
        }
        utilityBean.hideModal("response-dlg");
    }

    public void selectRootCauseAnalysis(RootCauseAnalysis tmprootCauseAnalysis) {
        rootCauseAnalysis = tmprootCauseAnalysis;
        if (rootCauseAnalysis == null) {
            rootCauseAnalysis = new RootCauseAnalysis();
        }
    }

    public void deleteRootCauseAnalysis() {
        rootCauseAnalysisList.remove(rootCauseAnalysis);
        rootCauseAnalysisFacade.remove(rootCauseAnalysis);
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

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    public RootCauseAnalysisFacade getRootCauseAnalysisFacade() {
        return rootCauseAnalysisFacade;
    }

    public void setRootCauseAnalysisFacade(RootCauseAnalysisFacade rootCauseAnalysisFacade) {
        this.rootCauseAnalysisFacade = rootCauseAnalysisFacade;
    }

    public RootCauseAnalysis getRootCauseAnalysis() {
        return rootCauseAnalysis;
    }

    public void setRootCauseAnalysis(RootCauseAnalysis rootCauseAnalysis) {
        this.rootCauseAnalysis = rootCauseAnalysis;
    }

    public List<RootCauseAnalysis> getRootCauseAnalysisList() {
        return rootCauseAnalysisList;
    }

    public void setRootCauseAnalysisList(List<RootCauseAnalysis> rootCauseAnalysisList) {
        this.rootCauseAnalysisList = rootCauseAnalysisList;
    }

}
