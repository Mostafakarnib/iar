package org.pirlo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.pirlo.enums.MEEnum;
import org.pirlo.enums.METypeEnum;

@Entity
@Table(name = "tbl_medication_error")
public class MedicationError implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_type")
    private MEEnum errorType;

    @ElementCollection
    private List<METypeEnum> ErrorDetailList;

    @ManyToOne(targetEntity = Ticket.class)
    private Ticket ticket;

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

    public MEEnum getErrorType() {
        return this.errorType;
    }

    public void setErrorType(MEEnum errorType) {
        this.errorType = errorType;
    }

    public List<METypeEnum> getErrorDetailList() {
        return this.ErrorDetailList;
    }

    public void setErrorDetailList(List<METypeEnum> ErrorDetailList) {
        this.ErrorDetailList = ErrorDetailList;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final MedicationError other = (MedicationError) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getErrorType(), other.getErrorType())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getErrorDetailList(), other.getErrorDetailList())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getTicket(), other.getTicket())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 11 * hash + (this.getErrorType() != null ? this.getErrorType().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MedicationError{" + " id=" + id + ", errorType=" + errorType + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
