package org.pirlo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.pirlo.enums.OperatorTypeEnum;
import org.pirlo.enums.RoleEnum;

@Entity
@Table(name = "tbl_operator")
@NamedQueries(
        {
            @NamedQuery(name = Operator.FIND_ALL, query = "SELECT o FROM Operator o")
            ,@NamedQuery(name = Operator.FIND_BY_ID, query = "SELECT o FROM Operator o WHERE o.id = :id")
            ,@NamedQuery(name = Operator.FIND_BY_USERNAME, query = "SELECT o FROM Operator o WHERE o.username = :username")
            ,@NamedQuery(name = Operator.FIND_BY_HOSPITAL, query = "SELECT o FROM Operator o WHERE o.hospital = :hospital")
            ,@NamedQuery(name = Operator.FIND_BY_ROLE_AND_HOSPITAL, query = "SELECT o FROM Operator o WHERE o.hospital = :hospital AND o.role = :role")
            ,@NamedQuery(name = Operator.FIND_BY_HOSPITAL_AND_EMERGENCY, query = "SELECT o FROM Operator o WHERE o.hospital.id = :hospitalID AND o.recievingEmergencyEmails = :recievingEmergencyEmails")
        })
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Operator.findAll";
    public static final String FIND_BY_ID = "Operator.findById";
    public final static String FIND_BY_USERNAME = "Operator.FindByUsername";
    public final static String FIND_BY_HOSPITAL = "Operator.FindByHospital";
    public final static String FIND_BY_ROLE_AND_HOSPITAL = "Operator.FindByRoleAndHospital";
    public final static String FIND_BY_HOSPITAL_AND_EMERGENCY = "Operator.FindByHospitalAndEmergency";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Column(name = "col_username")
    private String username;

    @Basic
    @Column(name = "col_password")
    private String password;

    @Basic
    @Column(name = "col_email")
    private String email;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_operator_type")
    private OperatorTypeEnum type;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_role")
    private RoleEnum role;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @Basic
    @Column(name = "col_title")
    private String title;

    @Basic
    @Column(name = "col_recieving_emergency_emails")
    private boolean recievingEmergencyEmails;

    @OneToOne(targetEntity = Configuration.class, mappedBy = "operator", cascade = CascadeType.ALL)
    private Configuration configuration;

    @ManyToOne(targetEntity = Hospital.class)
    private Hospital hospital;

    @OneToMany(targetEntity = Notification.class, mappedBy = "operator")
    private List<Notification> notificationList = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRecievingEmergencyEmails() {
        return recievingEmergencyEmails;
    }

    public void setRecievingEmergencyEmails(boolean recievingEmergencyEmails) {
        this.recievingEmergencyEmails = recievingEmergencyEmails;
    }

    public OperatorTypeEnum getType() {
        return this.type;
    }

    public void setType(OperatorTypeEnum type) {
        this.type = type;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public Date getInsertionDate() {
        return this.insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Operator other = (Operator) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getUsername(), other.getUsername())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getPassword(), other.getPassword())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getType(), other.getType())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getRole(), other.getRole())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getInsertionDate(), other.getInsertionDate())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getTitle(), other.getTitle())) {
            return false;
        }
        if (this.isRecievingEmergencyEmails() != other.isRecievingEmergencyEmails()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 53 * hash + (this.getUsername() != null ? this.getUsername().hashCode() : 0);
        hash = 53 * hash + (this.getPassword() != null ? this.getPassword().hashCode() : 0);
        hash = 53 * hash + (this.getType() != null ? this.getType().hashCode() : 0);
        hash = 53 * hash + (this.getRole() != null ? this.getRole().hashCode() : 0);
        hash = 53 * hash + (this.getInsertionDate() != null ? this.getInsertionDate().hashCode() : 0);
        hash = 53 * hash + (this.getTitle() != null ? this.getTitle().hashCode() : 0);
        hash = 53 * hash + (this.isRecievingEmergencyEmails() ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Operator{" + " id=" + id + ", username=" + username + ", password=" + password + ", type=" + type + ", role=" + role + ", insertionDate=" + insertionDate + ", title=" + title + "}";
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }
}
