package org.pirlo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Department.findAll";
    public static final String FIND_BY_ID = "Department.findById";
    public static final String FIND_BY_HOSPITAL = "Department.findByHospital";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Column(name = "col_name")
    private String name;

    @OneToMany(targetEntity = Ticket.class, mappedBy = "department")
    private List<Ticket> ticketList = new ArrayList<>();

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @ManyToOne(targetEntity = Hospital.class)
    private Hospital hospital;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInsertionDate() {
        return this.insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Department other = (Department) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getInsertionDate(), other.getInsertionDate())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 97 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        hash = 97 * hash + (this.getInsertionDate() != null ? this.getInsertionDate().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Department{" + " id=" + id + ", name=" + name + ", insertionDate=" + insertionDate + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
