package org.pirlo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.pirlo.enums.GenderEnum;
import org.pirlo.enums.IACategoryEnum;
import org.pirlo.enums.IATypeEnum;
import org.pirlo.enums.OccupationStatusEnum;
import org.pirlo.enums.SeverityEnum;
import org.pirlo.enums.TicketStatusEnum;

@Entity
@Table(name = "tbl_ticket")
@NamedQueries(
        {
            @NamedQuery(name = Ticket.FIND_ALL, query = "SELECT t FROM Ticket t")
            ,@NamedQuery(name = Ticket.FIND_BY_ID, query = "SELECT t FROM Ticket t WHERE t.id = :id")
            ,@NamedQuery(name = Ticket.FIND_BY_HOSPITAL, query = "SELECT t FROM Ticket t WHERE t.hospital = :hospital")
            ,@NamedQuery(name = Ticket.FIND_BY_DEPARTMENT_CATEGORY, query = "SELECT t FROM Ticket t WHERE t.department = :department AND t.IACategory = :IACategory AND t.occuranceDateTime BETWEEN :starDate AND :endDate")
            ,@NamedQuery(name = Ticket.FIND_BY_TYPE_CATEGORY, query = "SELECT t FROM Ticket t WHERE :aTypeEnum MEMBER OF t.IATypeList AND t.IACategory = :IACategory")
            ,@NamedQuery(name = Ticket.FIND_BY_DEPARTMENT_TYPE, query = "SELECT t FROM Ticket t WHERE t.department = :department AND :aTypeEnum MEMBER OF t.IATypeList AND t.occuranceDateTime BETWEEN :starDate AND :endDate")
            ,@NamedQuery(name = Ticket.FIND_BY_MEDICATION_ERRORS_CATEGORY, query = "SELECT t FROM Ticket t LEFT JOIN t.medicationErrorList mList WHERE :mEEnum = mList.errorType AND t.IACategory = :aCategoryEnum AND t.occuranceDateTime BETWEEN :starDate AND :endDate")
            ,@NamedQuery(name = Ticket.FIND_BY_DEPARTMENT_MEDICATION_ERRORS, query = "SELECT t FROM Ticket t LEFT JOIN t.medicationErrorList mList WHERE :mEEnum = mList.errorType AND t.department = :department AND t.occuranceDateTime BETWEEN :starDate AND :endDate")
            ,@NamedQuery(name = Ticket.FIND_BY_MEDICATION_ERRORS_TYPE, query = "SELECT t FROM Ticket t LEFT JOIN t.medicationErrorList mList WHERE :mEEnum = mList.errorType AND :aTypeEnum MEMBER OF t.IATypeList AND t.occuranceDateTime BETWEEN :starDate AND :endDate")
            ,@NamedQuery(name = Ticket.FIND_BY_DEPARTMENT_TICKET_STATUS, query = "SELECT t FROM Ticket t WHERE t.department = :department AND t.status= :ticketStatusEnum AND t.occuranceDateTime BETWEEN :starDate AND :endDate")
            ,@NamedQuery(name = Ticket.FIND_BY_TICKET_STATUS_SEVERITY, query = "SELECT t FROM Ticket t WHERE t.status= :ticketStatusEnum AND t.severity=:severityEnum AND t.occuranceDateTime BETWEEN :starDate AND :endDate")
        })
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Ticket.findAll";
    public static final String FIND_BY_ID = "Ticket.findById";
    public static final String FIND_BY_HOSPITAL = "Ticket.findByHospital";
    public static final String FIND_BY_DEPARTMENT_CATEGORY = "Ticket.findByDepartmentCategory";
    public static final String FIND_BY_TYPE_CATEGORY = "Ticket.findCategoryCountByType";
    public static final String FIND_BY_DEPARTMENT_TYPE = "Ticket.findByDepartmentType";
    public static final String FIND_BY_MEDICATION_ERRORS_CATEGORY = "Ticket.findCategoryCountByMedicationErrors";
    public static final String FIND_BY_DEPARTMENT_MEDICATION_ERRORS = "Ticket.findMedicationErrorsCountByDepartment";
    public static final String FIND_BY_MEDICATION_ERRORS_TYPE = "Ticket.findMedicationErrorsCountByType";
    public static final String FIND_BY_DEPARTMENT_TICKET_STATUS = "Ticket.findTicketStatusCountByDepartment";
    public static final String FIND_BY_TICKET_STATUS_SEVERITY = "Ticket.findTicketStatusCountBySeverity";
    public static final String FIND_BY_MONTH_CATEGORY = "Ticket.findCategoryCountByMonth";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Column(name = "col_reporter_name")
    private String reporterName;

    @Basic
    @Column(name = "col_affected_person_age")
    private String apAge;

    @Basic
    @Column(name = "col_affected_person_medical_num")
    private String apmedicalNumber;

    @Basic
    @Column(name = "col_affected_person_gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum apgender;

    @Basic
    @Column(name = "col_affected_person_name")
    private String apName;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_occupational_status")
    private OccupationStatusEnum apOccupationStatus;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_ia_category")
    private IACategoryEnum IACategory;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @Lob
    @Basic
    @Column(name = "col_summary")
    private String summary;

    @Lob
    @Basic
    @Column(name = "col_details")
    private String details;

    @Basic
    @Column(name = "col_status")
    @Enumerated(EnumType.STRING)
    private TicketStatusEnum status;

    @Basic
    @Column(name = "col_severity")
    @Enumerated(EnumType.STRING)
    private SeverityEnum severity;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_occurance_datetime")
    private Date occuranceDateTime;

    @Basic
    @Column(name = "col_reporter_mobile")
    private String reporterMobile;

    @Basic
    @Column(name = "col_location")
    private String location;

    @Basic
    @Column(name = "col_reporter_id")
    private String reporterID;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<IATypeEnum> IATypeList;

    @ManyToOne(targetEntity = Department.class)
    private Department department;

    @OneToMany(targetEntity = MedicationError.class, mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<MedicationError> medicationErrorList = new ArrayList<>();

    @ManyToOne(targetEntity = Hospital.class)
    private Hospital hospital;

    @OneToMany(targetEntity = Comment.class, mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(targetEntity = Task.class, mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Task> taskList = new ArrayList<>();

    @OneToMany(targetEntity = RootCauseAnalysis.class, mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<RootCauseAnalysis> rootCauseAnalysisList = new ArrayList<>();

    @ManyToMany(targetEntity = Operator.class)
    private List<Operator> operatorList = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReporterName() {
        return this.reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getApAge() {
        return this.apAge;
    }

    public void setApAge(String apAge) {
        this.apAge = apAge;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getApmedicalNumber() {
        return this.apmedicalNumber;
    }

    public void setApmedicalNumber(String apmedicalNumber) {
        this.apmedicalNumber = apmedicalNumber;
    }

    public String getApName() {
        return this.apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public OccupationStatusEnum getApOccupationStatus() {
        return this.apOccupationStatus;
    }

    public void setApOccupationStatus(OccupationStatusEnum apOccupationStatus) {
        this.apOccupationStatus = apOccupationStatus;
    }

    public IACategoryEnum getIACategory() {
        return this.IACategory;
    }

    public void setIACategory(IACategoryEnum IACategory) {
        this.IACategory = IACategory;
    }

    public Date getInsertionDate() {
        return this.insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public GenderEnum getApgender() {
        return apgender;
    }

    public void setApgender(GenderEnum apgender) {
        this.apgender = apgender;
    }

    public TicketStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TicketStatusEnum status) {
        this.status = status;
    }

    public SeverityEnum getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityEnum severity) {
        this.severity = severity;
    }

    public Date getOccuranceDateTime() {
        return this.occuranceDateTime;
    }

    public void setOccuranceDateTime(Date occuranceDateTime) {
        this.occuranceDateTime = occuranceDateTime;
    }

    public String getReporterMobile() {
        return this.reporterMobile;
    }

    public void setReporterMobile(String reporterMobile) {
        this.reporterMobile = reporterMobile;
    }

    public String getReporterID() {
        return this.reporterID;
    }

    public void setReporterID(String reporterID) {
        this.reporterID = reporterID;
    }

    public List<IATypeEnum> getIATypeList() {
        return this.IATypeList;
    }

    public void setIATypeList(List<IATypeEnum> IATypeList) {
        this.IATypeList = IATypeList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<MedicationError> getMedicationErrorList() {
        return this.medicationErrorList;
    }

    public void setMedicationErrorList(List<MedicationError> medicationErrorList) {
        this.medicationErrorList = medicationErrorList;
    }

    public List<Comment> getCommentList() {
        return this.commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Task> getTaskList() {
        return this.taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<RootCauseAnalysis> getRootCauseAnalysisList() {
        return this.rootCauseAnalysisList;
    }

    public void setRootCauseAnalysisList(List<RootCauseAnalysis> rootCauseAnalysisList) {
        this.rootCauseAnalysisList = rootCauseAnalysisList;
    }

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Ticket other = (Ticket) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getReporterName(), other.getReporterName())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getApAge(), other.getApAge())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getApmedicalNumber(), other.getApmedicalNumber())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getApgender(), other.getApgender())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getApName(), other.getApName())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getApOccupationStatus(), other.getApOccupationStatus())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getIACategory(), other.getIACategory())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getInsertionDate(), other.getInsertionDate())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getSummary(), other.getSummary())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getDetails(), other.getDetails())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getHospital(), other.getHospital())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getStatus(), other.getStatus())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getSeverity(), other.getSeverity())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getOccuranceDateTime(), other.getOccuranceDateTime())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getReporterMobile(), other.getReporterMobile())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getReporterID(), other.getReporterID())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getIATypeList(), other.getIATypeList())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getLocation(), other.getLocation())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getMedicationErrorList(), other.getMedicationErrorList())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getCommentList(), other.getCommentList())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getTaskList(), other.getTaskList())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getRootCauseAnalysisList(), other.getRootCauseAnalysisList())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 41 * hash + (this.getReporterName() != null ? this.getReporterName().hashCode() : 0);
        hash = 41 * hash + (this.getApAge() != null ? this.getApAge().hashCode() : 0);
        hash = 41 * hash + (this.getApmedicalNumber() != null ? this.getApmedicalNumber().hashCode() : 0);
        hash = 41 * hash + (this.getApgender() != null ? this.getApgender().hashCode() : 0);
        hash = 41 * hash + (this.getApName() != null ? this.getApName().hashCode() : 0);
        hash = 41 * hash + (this.getApOccupationStatus() != null ? this.getApOccupationStatus().hashCode() : 0);
        hash = 41 * hash + (this.getIACategory() != null ? this.getIACategory().hashCode() : 0);
        hash = 41 * hash + (this.getInsertionDate() != null ? this.getInsertionDate().hashCode() : 0);
        hash = 41 * hash + (this.getSummary() != null ? this.getSummary().hashCode() : 0);
        hash = 41 * hash + (this.getDetails() != null ? this.getDetails().hashCode() : 0);
        hash = 41 * hash + (this.getStatus() != null ? this.getStatus().hashCode() : 0);
        hash = 41 * hash + (this.getSeverity() != null ? this.getSeverity().hashCode() : 0);
        hash = 41 * hash + (this.getOccuranceDateTime() != null ? this.getOccuranceDateTime().hashCode() : 0);
        hash = 41 * hash + (this.getReporterMobile() != null ? this.getReporterMobile().hashCode() : 0);
        hash = 41 * hash + (this.getReporterID() != null ? this.getReporterID().hashCode() : 0);
        hash = 41 * hash + (this.getIATypeList() != null ? this.getIATypeList().hashCode() : 0);
        hash = 41 * hash + (this.getLocation() != null ? this.getLocation().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Ticket{" + " id=" + id + ", reporterName=" + reporterName + ", apAge=" + apAge + ", apmedicalNumber=" + apmedicalNumber + ", apgender=" + apgender + ", apName=" + apName + ", apOccupationStatus=" + apOccupationStatus + ", IACategory=" + IACategory + ", insertionDate=" + insertionDate + ", summary=" + summary + ", status=" + status + ", severity=" + severity + ", occuranceDateTime=" + occuranceDateTime + ", reporterMobile=" + reporterMobile + ", reporterID=" + reporterID + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
