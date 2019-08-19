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
import org.pirlo.entities.Operator;

@Stateless
public class OperatorFacade extends AbstractFacade<Operator> {

    @PersistenceContext(unitName = "IAR_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperatorFacade() {
        super(Operator.class);
    }

    public Operator getOperatorByUsername(String username) {
        try {
            return em.createNamedQuery(Operator.FIND_BY_USERNAME, Operator.class)
                    .setParameter("username", username)
                    .getResultList().get(0);
        } catch (Exception e) {

        }
        return null;
    }

    public List<Operator> findByHospital(Hospital hospital) {
        return em.createNamedQuery(Operator.FIND_BY_HOSPITAL, Operator.class)
                .setParameter("hospital", hospital)
                .getResultList();
    }

}
