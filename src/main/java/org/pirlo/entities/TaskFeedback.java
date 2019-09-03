package org.pirlo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_task_feed_back")
@NamedQueries(
        {
            @NamedQuery(name = TaskFeedback.FIND_ALL, query = "SELECT t FROM TaskFeedback t")
            ,@NamedQuery(name = TaskFeedback.FIND_BY_ID, query = "SELECT t FROM TaskFeedback t WHERE t.id = :id")
        })
public class TaskFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "TaskFeedback.findAll";
    public static final String FIND_BY_ID = "TaskFeedback.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @ManyToOne(targetEntity = Task.class)
    private Task task;

    @ManyToOne(targetEntity = Operator.class)
    private Operator targetOperator;

    @ManyToOne(targetEntity = Operator.class)
    private Operator senderOperator;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Operator getTargetOperator() {
        return targetOperator;
    }

    public void setTargetOperator(Operator targetOperator) {
        this.targetOperator = targetOperator;
    }

    public Operator getSenderOperator() {
        return senderOperator;
    }

    public void setSenderOperator(Operator senderOperator) {
        this.senderOperator = senderOperator;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.insertionDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskFeedback other = (TaskFeedback) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.task, other.task)) {
            return false;
        }
        if (!Objects.equals(this.insertionDate, other.insertionDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TaskFeedback{" + "id=" + id + ", insertionDate=" + insertionDate + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
