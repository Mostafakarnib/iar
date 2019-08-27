/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.pirlo.entities.RootCauseAnalysis;

@Stateless
public class RootCauseAnalysisFacade extends AbstractFacade<RootCauseAnalysis> {

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RootCauseAnalysisFacade() {
        super(RootCauseAnalysis.class);
    }

}
