/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.pirlo.entities.MedicationError;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Stateless
public class MedicationErrorFacade extends AbstractFacade<MedicationError>
{

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public MedicationErrorFacade()
    {
        super(MedicationError.class);
    }

}
