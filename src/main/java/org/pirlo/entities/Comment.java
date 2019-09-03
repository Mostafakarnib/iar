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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_comment")
@NamedQueries(
        {
            @NamedQuery(name = Comment.FIND_ALL, query = "SELECT c FROM Comment c")
            ,@NamedQuery(name = Comment.FIND_BY_ID, query = "SELECT c FROM Comment c WHERE c.id = :id")
        })
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Comment.findAll";
    public static final String FIND_BY_ID = "Comment.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "col_id")
    private Long id;

    @Basic
    @Lob
    @Column(name = "col_description")
    private String description;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "col_insertion_date")
    private Date insertionDate;

    @OneToOne(targetEntity = Operator.class)
    private Operator operator;

    @ManyToOne(targetEntity = Ticket.class)
    private Ticket ticket;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        final Comment other = (Comment) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getDescription(), other.getDescription())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getInsertionDate(), other.getInsertionDate())) {
            return false;
        }
        if (!java.util.Objects.equals(this.getOperator(), other.getOperator())) {
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
        hash = 23 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 23 * hash + (this.getDescription() != null ? this.getDescription().hashCode() : 0);
        hash = 23 * hash + (this.getInsertionDate() != null ? this.getInsertionDate().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Comment{" + " id=" + id + ", description=" + description + ", insertionDate=" + insertionDate + '}';
    }

    @PrePersist
    public void initializeInsertionDate() {
        this.insertionDate = new Date();
    }

}
