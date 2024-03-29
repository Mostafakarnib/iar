package org.pirlo.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_root_cause_analysis")
@NamedQueries(
        {
            @NamedQuery(name = RootCauseAnalysis.FIND_ALL, query = "SELECT r FROM RootCauseAnalysis r")
            ,@NamedQuery(name = RootCauseAnalysis.FIND_BY_ID, query = "SELECT r FROM RootCauseAnalysis r WHERE r.id = :id")
            ,@NamedQuery(name = RootCauseAnalysis.FIND_BY_OPERATOR, query = "SELECT r FROM RootCauseAnalysis r WHERE r.senderOperator = :sender "
                    + "AND r.targetOperator = :target AND r.ticket = :ticket")
            ,@NamedQuery(name = RootCauseAnalysis.FIND_BY_TARGET_OPERATOR_AND_TICKET, query = "SELECT r FROM RootCauseAnalysis r WHERE r.targetOperator = :target AND r.ticket = :ticket")
            ,@NamedQuery(name = RootCauseAnalysis.FIND_BY_TARGET_OPERATOR, query = "SELECT r FROM RootCauseAnalysis r WHERE r.targetOperator = :target")
        })
public class RootCauseAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "RootCauseAnalysis.findAll";
    public static final String FIND_BY_ID = "RootCauseAnalysis.findById";
    public static final String FIND_BY_OPERATOR = "RootCauseAnalysis.findByOperator";
    public static final String FIND_BY_TARGET_OPERATOR = "RootCauseAnalysis.findByTargetOperator";
    public static final String FIND_BY_TARGET_OPERATOR_AND_TICKET = "RootCauseAnalysis.findByTargetOperatorAndTicket";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Lob
    @Column(name = "col_request_details")
    private String requestDetails;

    @Basic
    @Lob
    @Column(name = "col_response_details")
    private String responseDetails;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @ManyToOne(targetEntity = Ticket.class)
    private Ticket ticket;

    @ManyToOne(targetEntity = Operator.class)
    private Operator targetOperator;

    @ManyToOne(targetEntity = Operator.class)
    private Operator senderOperator;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final RootCauseAnalysis other = (RootCauseAnalysis) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getRequestDetails(), other.getRequestDetails())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getResponseDetails(), other.getResponseDetails())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getInsertionDate(), other.getInsertionDate())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 89 * hash + (this.getRequestDetails() != null ? this.getRequestDetails().hashCode() : 0);
        hash = 89 * hash + (this.getResponseDetails() != null ? this.getResponseDetails().hashCode() : 0);
        hash = 89 * hash + (this.getInsertionDate() != null ? this.getInsertionDate().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "RootCauseAnalysis{" + " id=" + id + ", requestDetails=" + requestDetails + ", responseDetails=" + responseDetails + ", insertionDate=" + insertionDate + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
