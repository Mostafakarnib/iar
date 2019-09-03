/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.facades;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.pirlo.entities.Operator;
import org.pirlo.entities.RootCauseAnalysis;
import org.pirlo.entities.Ticket;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Stateless
public class RootCauseAnalysisFacade extends AbstractFacade<RootCauseAnalysis>
{

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public RootCauseAnalysisFacade()
    {
        super(RootCauseAnalysis.class);
    }

    public List<RootCauseAnalysis> findByOperator(Operator targetOperator, Operator senderOperator, Ticket ticket)
    {
        return em.createNamedQuery(RootCauseAnalysis.FIND_BY_OPERATOR, RootCauseAnalysis.class)
                .setParameter("sender", senderOperator)
                .setParameter("target", targetOperator)
                .setParameter("ticket", ticket)
                .getResultList();
    }

    public List<RootCauseAnalysis> findByTargetOperatorAndTicket(Operator targetOperator, Ticket ticket)
    {
        return em.createNamedQuery(RootCauseAnalysis.FIND_BY_TARGET_OPERATOR_AND_TICKET, RootCauseAnalysis.class)
                .setParameter("target", targetOperator)
                .setParameter("ticket", ticket)
                .getResultList();
    }

    public List<RootCauseAnalysis> findByTargetOperator(Operator targetOperator)
    {
        return em.createNamedQuery(RootCauseAnalysis.FIND_BY_TARGET_OPERATOR, RootCauseAnalysis.class)
                .setParameter("target", targetOperator)
                .getResultList();
    }

}
