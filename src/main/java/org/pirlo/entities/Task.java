package org.pirlo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.pirlo.enums.TaskStatusEnum;

@Entity
@Table(name = "tbl_task")
@NamedQueries(
        {
            @NamedQuery(name = Task.FIND_ALL, query = "SELECT t FROM Task t")
            ,@NamedQuery(name = Task.FIND_BY_ID, query = "SELECT t FROM Task t WHERE t.id = :id")
            ,@NamedQuery(name = Task.FIND_TASK_BY_OPERATOR, query = "SELECT t FROM Task t WHERE t.operator = :operator")
            ,@NamedQuery(name = Task.FIND_TASK_BY_OPERATOR_AND_TICKET, query = "SELECT t FROM Task t WHERE t.operator = :operator AND t.ticket = :ticket")
        })
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Task.findAll";
    public static final String FIND_BY_ID = "Task.findById";
    public static final String FIND_TASK_BY_OPERATOR = "Task.findTaskByOperator";
    public static final String FIND_TASK_BY_OPERATOR_AND_TICKET = "Task.findTaskByOperatorAndTicket";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Lob
    @Column(name = "col_details")
    private String details;

    @Basic
    @Lob
    @Column(name = "col_summary")
    private String summary;

    @Basic
    @Lob
    @Column(name = "col_result")
    private String result;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_end_date")
    private Date endDate;

    @Basic
    @Column(name = "col_status")
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status;

    @ManyToOne(targetEntity = Ticket.class)
    private Ticket ticket;

    @ManyToOne(targetEntity = Operator.class)
    private Operator operator;

    @OneToMany(targetEntity = TaskFeedback.class, mappedBy = "task")
    private List<TaskFeedback> taskFeedbackList = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<TaskFeedback> getTaskFeedbackList() {
        return this.taskFeedbackList;
    }

    public void setTaskFeedbackList(List<TaskFeedback> taskFeedbackList) {
        this.taskFeedbackList = taskFeedbackList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Task other = (Task) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getDetails(), other.getDetails())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getInsertionDate(), other.getInsertionDate())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getEndDate(), other.getEndDate())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getOperator(), other.getOperator())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getStatus(), other.getStatus())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getTicket(), other.getTicket())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 71 * hash + (this.getDetails() != null ? this.getDetails().hashCode() : 0);
        hash = 71 * hash + (this.getInsertionDate() != null ? this.getInsertionDate().hashCode() : 0);
        hash = 71 * hash + (this.getEndDate() != null ? this.getEndDate().hashCode() : 0);
        hash = 71 * hash + (this.getStatus() != null ? this.getStatus().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Task{" + " id=" + id + ", details=" + details + ", insertionDate=" + insertionDate + ", endDate=" + endDate + ", status=" + status + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
