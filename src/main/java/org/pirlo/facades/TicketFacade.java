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
import org.pirlo.entities.Hospital;
import org.pirlo.entities.Ticket;

@Stateless
public class TicketFacade extends AbstractFacade<Ticket> {

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketFacade() {
        super(Ticket.class);
    }

    public List<Ticket> findByHospital(Hospital hospital) {
        return em.createNamedQuery(Ticket.FIND_BY_HOSPITAL, Ticket.class)
                .setParameter("hospital", hospital)
                .getResultList();
    }

}
