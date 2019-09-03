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
import org.pirlo.entities.Task;
import org.pirlo.entities.Ticket;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Stateless
public class TaskFacade extends AbstractFacade<Task>
{

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TaskFacade()
    {
        super(Task.class);
    }

    public List<Task> findTaskByOperatorAndTicket(Operator operator, Ticket ticket)
    {
        return em.createNamedQuery(Task.FIND_TASK_BY_OPERATOR_AND_TICKET, Task.class)
                .setParameter("operator", operator)
                .setParameter("ticket", ticket)
                .getResultList();
    }

    public List<Task> findTaskByOperator(Operator operator)
    {
        return em.createNamedQuery(Task.FIND_TASK_BY_OPERATOR, Task.class)
                .setParameter("operator", operator)
                .getResultList();
    }

}
