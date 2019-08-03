package org.pirlo.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.pirlo.enums.LangEnum;

@Entity
@Table(name = "tbl_configuration")
public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "col_lang")
    private LangEnum lang;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @OneToOne(targetEntity = Operator.class)
    private Operator operator;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LangEnum getLang() {
        return this.lang;
    }

    public void setLang(LangEnum lang) {
        this.lang = lang;
    }

    public Date getInsertionDate() {
        return this.insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        final Configuration other = (Configuration) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getLang(), other.getLang())) {
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
        hash = 71 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 71 * hash + (this.getLang() != null ? this.getLang().hashCode() : 0);
        hash = 71 * hash + (this.getInsertionDate() != null ? this.getInsertionDate().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Configuration{" + " id=" + id + ", lang=" + lang + ", insertionDate=" + insertionDate + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
