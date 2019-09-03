/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.facades;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.joda.time.DateTime;
import org.pirlo.entities.Department;
import org.pirlo.entities.Hospital;
import org.pirlo.entities.Ticket;
import org.pirlo.enums.IACategoryEnum;
import org.pirlo.enums.IATypeEnum;
import org.pirlo.enums.MEEnum;
import org.pirlo.enums.MonthesEnum;
import org.pirlo.enums.SeverityEnum;
import org.pirlo.enums.TicketStatusEnum;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Stateless
public class TicketFacade extends AbstractFacade<Ticket>
{

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TicketFacade()
    {
        super(Ticket.class);
    }

    public List<Ticket> findByHospital(Hospital hospital)
    {
        return em.createNamedQuery(Ticket.FIND_BY_HOSPITAL, Ticket.class)
                .setParameter("hospital", hospital)
                .getResultList();
    }

    public List<Ticket> findCategoryCountByDepartment(Department department, IACategoryEnum IACategory, Date starDate, Date endDate)
    {
        return em.createNamedQuery(Ticket.FIND_BY_DEPARTMENT_CATEGORY, Ticket.class)
                .setParameter("department", department)
                .setParameter("IACategory", IACategory)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Ticket> findCategoryCountByType(IATypeEnum aTypeEnum, IACategoryEnum IACategory)
    {
        return em.createNamedQuery(Ticket.FIND_BY_TYPE_CATEGORY, Ticket.class)
                .setParameter("aTypeEnum", aTypeEnum)
                .setParameter("IACategory", IACategory)
                .getResultList();
    }

    public List<Ticket> findTypeCountByDepartment(Department department, IATypeEnum aTypeEnum, Date starDate, Date endDate)
    {
        return em.createNamedQuery(Ticket.FIND_BY_DEPARTMENT_TYPE, Ticket.class)
                .setParameter("department", department)
                .setParameter("aTypeEnum", aTypeEnum)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Ticket> findCategoryCountByMedicationErrors(MEEnum mEEnum, IACategoryEnum aCategoryEnum, Date starDate, Date endDate)
    {
        return em.createNamedQuery(Ticket.FIND_BY_MEDICATION_ERRORS_CATEGORY, Ticket.class)
                .setParameter("mEEnum", mEEnum)
                .setParameter("aCategoryEnum", aCategoryEnum)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Ticket> findMedicationErrorsCountByDepartment(Department department, MEEnum mEEnum, Date starDate, Date endDate)
    {
        return em.createNamedQuery(Ticket.FIND_BY_DEPARTMENT_MEDICATION_ERRORS, Ticket.class)
                .setParameter("department", department)
                .setParameter("mEEnum", mEEnum)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Ticket> findMedicationErrorsCountByType(MEEnum mEEnum, IATypeEnum aTypeEnum, Date starDate, Date endDate)
    {
        return em.createNamedQuery(Ticket.FIND_BY_MEDICATION_ERRORS_TYPE, Ticket.class)
                .setParameter("mEEnum", mEEnum)
                .setParameter("aTypeEnum", aTypeEnum)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Ticket> findTicketStatusCountByDepartment(Department department, TicketStatusEnum ticketStatusEnum, Date starDate, Date endDate)
    {
        return em.createNamedQuery(Ticket.FIND_BY_DEPARTMENT_TICKET_STATUS, Ticket.class)
                .setParameter("department", department)
                .setParameter("ticketStatusEnum", ticketStatusEnum)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Ticket> findTicketStatusCountBySeverity(TicketStatusEnum ticketStatusEnum, SeverityEnum severityEnum, Date starDate, Date endDate)
    {
        return em.createNamedQuery(Ticket.FIND_BY_TICKET_STATUS_SEVERITY, Ticket.class)
                .setParameter("ticketStatusEnum", ticketStatusEnum)
                .setParameter("severityEnum", severityEnum)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Ticket> findCategoryCountByMonth(MonthesEnum monthesEnum, IACategoryEnum aCategoryEnum, Date starDate, Date endDate)
    {

        return em.createNamedQuery(Ticket.FIND_BY_MONTH_CATEGORY, Ticket.class)
                .setParameter("monthesEnum", monthesEnum)
                .setParameter("aCategoryEnum", aCategoryEnum)
                .setParameter("starDate", starDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

}
