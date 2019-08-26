package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.pirlo.entities.Department;
import org.pirlo.facades.DepartmentFacade;

@ManagedBean
@ViewScoped
public class DepartmentBean implements Serializable {

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    DepartmentFacade departmentFacade;

    Department department = new Department();
    List<Department> departmentList = new ArrayList<>();

    private final String DEPARTMENT_DLG = "department-dlg";

    @PostConstruct
    public void init() {
        try {
            departmentList = departmentFacade.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDepartment() {
        department.setHospital(loginBean.operator.getHospital());
        boolean check = false;
        if (department.getId() == null) {
            check = true;
        }
        department = departmentFacade.save(department);
        if (check) {
            departmentList.add(department);
        }
        utilityBean.hideModal(DEPARTMENT_DLG);
    }

    public void selectDepartment(Department tmpDepartment) {
        department = tmpDepartment;
        if (department == null) {
            department = new Department();
        }
    }

    public void deleteDepartment() {
        departmentList.remove(department);
        departmentFacade.remove(department);
    }

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public DepartmentFacade getDepartmentFacade() {
        return departmentFacade;
    }

    public void setDepartmentFacade(DepartmentFacade departmentFacade) {
        this.departmentFacade = departmentFacade;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

}
