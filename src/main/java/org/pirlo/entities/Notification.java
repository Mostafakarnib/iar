package org.pirlo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.pirlo.enums.NotificationEnum;
import org.pirlo.enums.OutcomeEnum;

@Entity
@Table(name = "tbl_notification")
@NamedQueries(
        {
            @NamedQuery(name = Notification.FIND_ALL, query = "SELECT n FROM Notification n")
            ,@NamedQuery(name = Notification.FIND_BY_ID, query = "SELECT n FROM Notification n WHERE n.id = :id")
        })
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Notification.findAll";
    public static final String FIND_BY_ID = "Notification.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Column(name = "col_target_id")
    private Long targetId = new Long(-1);

    @Basic
    @Column(name = "col_title")
    private String title;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_outcome")
    private OutcomeEnum outcome;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_type")
    private NotificationEnum type;

    @OneToOne(targetEntity = Operator.class)
    private Operator operator;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Date getInsertionDate() {
        return this.insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public NotificationEnum getType() {
        return this.type;
    }

    public void setType(NotificationEnum type) {
        this.type = type;
    }

    public OutcomeEnum getOutcome() {
        return outcome;
    }

    public void setOutcome(OutcomeEnum outcome) {
        this.outcome = outcome;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.title);
        hash = 59 * hash + Objects.hashCode(this.outcome);
        hash = 59 * hash + Objects.hashCode(this.insertionDate);
        hash = 59 * hash + Objects.hashCode(this.type);
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
        final Notification other = (Notification) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.outcome != other.outcome) {
            return false;
        }
        if (!Objects.equals(this.insertionDate, other.insertionDate)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", title=" + title + ", outcome=" + outcome + ", insertionDate=" + insertionDate + ", type=" + type + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
