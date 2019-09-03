package org.pirlo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_hospital")
@NamedQueries(
        {
            @NamedQuery(name = Hospital.FIND_ALL, query = "SELECT h FROM Hospital h")
            ,@NamedQuery(name = Hospital.FIND_BY_ID, query = "SELECT h FROM Hospital h WHERE h.id = :id")
        })
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Hospital.findAll";
    public static final String FIND_BY_ID = "Hospital.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Column(name = "col_name")
    private String name;

    @Basic
    @Column(name = "col_representative_name")
    private String representativeName;

    @Basic
    @Column(name = "col_representative_phone")
    private String representativePhone;

    @Basic
    @Column(name = "col_representative_email")
    private String representativeEmail;

    @Basic
    @Lob
    @Column(name = "col_address")
    private String address;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_subscription_end_date")
    private Date subscriptionEndDate;

    @Basic
    @Column(name = "col_fees")
    private float subscriptionFees;

    @OneToMany(targetEntity = Operator.class, mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operator> operatorList = new ArrayList<>();

    @OneToMany(targetEntity = Department.class, mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departmentList = new ArrayList<>();

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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getRepresentativePhone() {
        return representativePhone;
    }

    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }

    public String getRepresentativeEmail() {
        return representativeEmail;
    }

    public void setRepresentativeEmail(String representativeEmail) {
        this.representativeEmail = representativeEmail;
    }

    public void setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public float getSubscriptionFees() {
        return subscriptionFees;
    }

    public void setSubscriptionFees(float subscriptionFees) {
        this.subscriptionFees = subscriptionFees;
    }

    public List<Operator> getOperatorList() {
        return this.operatorList;
    }

    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    public List<Department> getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Hospital other = (Hospital) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getAddress(), other.getAddress())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getInsertionDate(), other.getInsertionDate())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getSubscriptionEndDate(), other.getSubscriptionEndDate())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getSubscriptionFees(), other.getSubscriptionFees())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.address);
        hash = 97 * hash + Objects.hashCode(this.insertionDate);
        hash = 97 * hash + Objects.hashCode(this.getSubscriptionEndDate());
        hash = 97 * hash + Float.floatToIntBits(this.subscriptionFees);
        return hash;
    }

    @Override
    public String toString() {
        return "Hospital{" + " id=" + id + ", name=" + name + ", address=" + address + ", insertionDate=" + insertionDate + ", subscriptionFees=" + subscriptionFees + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
