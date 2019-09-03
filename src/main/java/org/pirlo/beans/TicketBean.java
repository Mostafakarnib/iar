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
import org.pirlo.entities.Comment;
import org.pirlo.entities.Department;
import org.pirlo.entities.Hospital;
import org.pirlo.entities.MedicationError;
import org.pirlo.entities.Notification;
import org.pirlo.entities.Operator;
import org.pirlo.entities.RootCauseAnalysis;
import org.pirlo.entities.Task;
import org.pirlo.entities.Ticket;
import org.pirlo.enums.MEEnum;
import org.pirlo.enums.METypeEnum;
import org.pirlo.enums.NotificationEnum;
import org.pirlo.enums.SeverityEnum;
import org.pirlo.enums.TaskStatusEnum;
import org.pirlo.enums.TicketStatusEnum;
import org.pirlo.enums.ToasterEnum;
import org.pirlo.facades.CommentFacade;
import org.pirlo.facades.DepartmentFacade;
import org.pirlo.facades.HospitalFacade;
import org.pirlo.facades.NotificationFacade;
import org.pirlo.facades.OperatorFacade;
import org.pirlo.facades.RootCauseAnalysisFacade;
import org.pirlo.facades.TaskFacade;
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
    DepartmentFacade departmentFacade;
    @EJB
    OperatorFacade operatorFacade;
    @EJB
    CommentFacade commentFacade;
    @EJB
    RootCauseAnalysisFacade rootCauseAnalysisFacade;
    @EJB
    TaskFacade taskFacade;
    @EJB
    NotificationFacade notificationFacade;

    Ticket ticket = new Ticket();
    List<Ticket> ticketList = new ArrayList<>();
    List<Department> departmentList = new ArrayList<>();
    List<Operator> operatorList = new ArrayList<>();
    List<MEClass> meClassList = new ArrayList<>();

    RootCauseAnalysis rootCauseAnalysis = new RootCauseAnalysis();
    Comment comment = new Comment();
    Task task = new Task();
    Notification notification = new Notification();

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
            departmentList = departmentFacade.findByHospital(loginBean.operator.getHospital());

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

                                boolean sendEmergencyEmail = false;
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
                                if (ticket.getSeverity() == SeverityEnum.SEVERE) {
                                    sendEmergencyEmail = true;
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

                                    notification.setType(NotificationEnum.NEW_TICKET);
                                    notification.setOperator(loginBean.operator);
                                    notificationFacade.save(notification);

                                    System.out.println("sendEmergencyEmail: " + sendEmergencyEmail);
                                    if (sendEmergencyEmail) {
                                        List<Operator> opList = operatorFacade.findByHospitalAndEmergency(hospital, true);

                                        for (Operator operator : opList) {
                                            utilityBean.sendEmail("Emergency Ticket" + ticket.getId(), ticket.getDetails(), operator.getEmail());
                                        }
                                        utilityBean.sendEmail("Emergency Ticket" + ticket.getId(), ticket.getDetails(), loginBean.getOperator().getEmail());
                                    }

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

    public List<RootCauseAnalysis> getAnalysisByOperator(Operator tmpOperator) {
        List<RootCauseAnalysis> analysisList = rootCauseAnalysisFacade.findByTargetOperatorAndTicket(tmpOperator, ticket);
        return analysisList;
    }

    public List<Task> getTaskByOperator() {
        List<Task> tasksList = taskFacade.findTaskByOperatorAndTicket(loginBean.operator, ticket);
        return tasksList;
    }

    public void saveTicketSummary() {
        ticket.setStatus(TicketStatusEnum.CLOSED);
        ticket = ticketFacade.save(ticket);
        utilityBean.hideModal("ticket-summary-dlg");
    }

    public void updateTicket() {
        ticket = ticketFacade.save(ticket);
    }

    public void saveComment() {
        comment.setOperator(loginBean.getOperator());
        comment.setTicket(ticket);
        comment = commentFacade.save(comment);
        ticket.getCommentList().add(comment);
        ticket = ticketFacade.save(ticket);
        comment = new Comment();
    }

    public void requestUpdate() {
        for (Operator operator : ticket.getOperatorList()) {
            utilityBean.sendEmail("Reminder update for Ticket" + ticket.getId(), ticket.getSummary(), operator.getEmail());
        }
    }

    public void showTicketAnalysisModal() {
        utilityBean.showModal("request-dlg");
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

    public void saveRootCauseAnalysis() {
        rootCauseAnalysis.setTicket(ticket);
        rootCauseAnalysis.setSenderOperator(loginBean.operator);
        ticket.getRootCauseAnalysisList().add(rootCauseAnalysis);
        if (!ticket.getOperatorList().contains(rootCauseAnalysis.getTargetOperator())) {
            ticket.getOperatorList().add(rootCauseAnalysis.getTargetOperator());
        }
        ticket.setStatus(TicketStatusEnum.IN_PROGRESS);
        ticket = ticketFacade.save(ticket);
        rootCauseAnalysis = new RootCauseAnalysis();
        utilityBean.hideModal("edit_task-dlg");
        utilityBean.hideModal("request-dlg");
    }

    public void saveTask() {
        task.setStatus(TaskStatusEnum.NEW);
        task.setTicket(ticket);
        ticket.getTaskList().add(task);
        ticket.setStatus(TicketStatusEnum.IN_PROGRESS);
        ticket = ticketFacade.save(ticket);
        task = new Task();
        utilityBean.hideModal("task-dlg");
    }

    public void selectTask(Task tmpTask) {
        task = tmpTask;
        if (task == null) {
            task = new Task();
        }
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

    public HospitalFacade getHospitalFacade() {
        return hospitalFacade;
    }

    public void setHospitalFacade(HospitalFacade hospitalFacade) {
        this.hospitalFacade = hospitalFacade;
    }

    public OperatorFacade getOperatorFacade() {
        return operatorFacade;
    }

    public void setOperatorFacade(OperatorFacade operatorFacade) {
        this.operatorFacade = operatorFacade;
    }

    public RootCauseAnalysis getRootCauseAnalysis() {
        return rootCauseAnalysis;
    }

    public void setRootCauseAnalysis(RootCauseAnalysis rootCauseAnalysis) {
        this.rootCauseAnalysis = rootCauseAnalysis;
    }

    public RootCauseAnalysisFacade getRootCauseAnalysisFacade() {
        return rootCauseAnalysisFacade;
    }

    public void setRootCauseAnalysisFacade(RootCauseAnalysisFacade rootCauseAnalysisFacade) {
        this.rootCauseAnalysisFacade = rootCauseAnalysisFacade;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public TaskFacade getTaskFacade() {
        return taskFacade;
    }

    public void setTaskFacade(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public DepartmentFacade getDepartmentFacade() {
        return departmentFacade;
    }

    public void setDepartmentFacade(DepartmentFacade departmentFacade) {
        this.departmentFacade = departmentFacade;
    }

    public CommentFacade getCommentFacade() {
        return commentFacade;
    }

    public void setCommentFacade(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

}
