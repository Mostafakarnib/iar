package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;
import org.pirlo.custom.MEClass;
import org.pirlo.custom.METypeClass;
import org.pirlo.entities.Department;
import org.pirlo.entities.Hospital;
import org.pirlo.entities.MedicationError;
import org.pirlo.entities.Operator;
import org.pirlo.entities.Ticket;
import org.pirlo.enums.MEEnum;
import org.pirlo.enums.METypeEnum;
import org.pirlo.enums.SeverityEnum;
import org.pirlo.enums.TicketStatusEnum;
import org.pirlo.enums.ToasterEnum;
import org.pirlo.facades.HospitalFacade;
import org.pirlo.facades.OperatorFacade;
import org.pirlo.facades.TicketFacade;

@ManagedBean
@ViewScoped
public class TicketBean implements Serializable {

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    TicketFacade ticketFacade;
    @EJB
    HospitalFacade hospitalFacade;
    @EJB
    OperatorFacade operatorFacade;

    Ticket ticket = new Ticket();
    List<Ticket> ticketList = new ArrayList<>();
    List<Department> departmentList = new ArrayList<>();
    List<Operator> operatorList = new ArrayList<>();
    List<MEClass> meClassList = new ArrayList<>();

    @PostConstruct
    public void init() {

        try {
            operatorList = operatorFacade.findByHospital(loginBean.operator.getHospital());
            refreshTicketInstance();

            for (SelectItem meSelectItem : utilityBean.meEnumList) {
                List<METypeClass> meTypeClassList = new ArrayList<>();
                MEClass meClass = new MEClass();
                for (SelectItem meTypeSelectItem : utilityBean.meTypeEnumList) {
                    meTypeClassList.add(new METypeClass(meTypeSelectItem, false));
                }

                meClass.setMe(meSelectItem);
                meClass.setMeTypeClassList(meTypeClassList);

                meClassList.add(meClass);
            }

            ticketList = ticketFacade.findByHospital(loginBean.operator.getHospital());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTicketInstance() {
        if (ticket.getId() == null) {
            String strID = Faces.getRequestParameter("id");
            if (strID != null) {
                try {
                    int id = Integer.parseInt(strID);
                    ticket = ticketFacade.find(new Long(id));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            ticket = ticketFacade.find(ticket.getId());
        }
    }

    public void showTaskModal() {
        utilityBean.showModal("task-dlg");
    }

    public void saveTicket() {

        if (ticket.getReporterID().matches("\\d+")) {
            if (ticket.getReporterMobile().matches("\\d+")) {
                if (ticket.getApAge().matches("\\d+")) {
                    if (ticket.getApmedicalNumber().matches("\\d+")) {
                        try {
                            List<Hospital> hospitalList = hospitalFacade.findAll();
                            if (hospitalList.size() > 0) {
                                Hospital hospital = hospitalList.get(0);
                                ticket.setHospital(hospital);

                                if (ticket.getId() == null) {
                                    ticket.setStatus(TicketStatusEnum.NEW);
                                }

                                switch (ticket.getIACategory()) {
                                    case NEAR_MISS:
                                        ticket.setSeverity(SeverityEnum.LOW);
                                        break;
                                    case PATIENT_DEATH:
                                        ticket.setSeverity(SeverityEnum.SEVERE);
                                        break;
                                    case SENTINEL_EVENT:
                                        ticket.setSeverity(SeverityEnum.SEVERE);
                                        break;
                                    default:
                                        ticket.setSeverity(SeverityEnum.LOW);
                                        break;
                                }
                                boolean doesContainCheckedME = false;
                                List<MedicationError> medicationErrorList = new ArrayList<>();
                                for (MEClass meClass : meClassList) {
                                    MEEnum meEnum = (MEEnum) meClass.getMe().getValue();

                                    List<METypeEnum> medicationErrorTypeList = new ArrayList<>();
                                    for (METypeClass meTypeClass : meClass.getMeTypeClassList()) {
                                        METypeEnum meType = (METypeEnum) meTypeClass.getMeType().getValue();
                                        if (meTypeClass.isSelected()) {
                                            System.out.println("selected: " + meType);
                                            medicationErrorTypeList.add(meType);
                                            doesContainCheckedME = true;
                                        }
                                    }
                                    if (!medicationErrorTypeList.isEmpty()) {
                                        MedicationError me = new MedicationError();
                                        me.setErrorType(meEnum);
                                        me.setErrorDetailList(medicationErrorTypeList);
                                        me.setTicket(ticket);
                                        medicationErrorList.add(me);
                                    }
                                }
                                ticket.setMedicationErrorList(medicationErrorList);

                                if (doesContainCheckedME) {
                                    ticket = ticketFacade.save(ticket);
                                    String description = "Save Ticket " + "ID: " + ticket.getId();
                                    String toast = "showToastr('" + description + "','info','500')";
                                    Ajax.oncomplete(toast);

                                    ticket = new Ticket();
                                } else {
                                    utilityBean.showToastr("Should Contain at least one checked error!", ToasterEnum.error.name());
                                }

                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        throw new ValidatorException(new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, "Invalid format", null));
                    }
                }
            }
        }
    }

    public void saveTicketSummary() {
        ticket.setStatus(TicketStatusEnum.CLOSED);
        ticket = ticketFacade.save(ticket);
        utilityBean.hideModal("ticket-summary-dlg");
    }

    public void updateTicket() {
        ticket = ticketFacade.save(ticket);
    }

    public void selectTicket(Ticket tmpTicket) {
        ticket = tmpTicket;
        if (ticket == null) {
            ticket = new Ticket();
        }
    }

    public void deleteTicket() {
        ticketList.remove(ticket);
        ticketFacade.remove(ticket);
    }

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public TicketFacade getTicketFacade() {
        return ticketFacade;
    }

    public void setTicketFacade(TicketFacade ticketFacade) {
        this.ticketFacade = ticketFacade;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<MEClass> getMeClassList() {
        return meClassList;
    }

    public void setMeClassList(List<MEClass> meClassList) {
        this.meClassList = meClassList;
    }

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

}
