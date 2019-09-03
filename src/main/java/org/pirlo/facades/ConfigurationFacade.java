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
import org.pirlo.entities.Configuration;
import org.pirlo.entities.Operator;

/**
 *
 * @author mostafa
 */
@Stateless
public class ConfigurationFacade extends AbstractFacade<Configuration>
{

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public ConfigurationFacade()
    {
        super(Configuration.class);
    }

    public Configuration findByOperator(Operator operator)
    {
        List<Configuration> configurationList = em.createNamedQuery(Configuration.FIND_BY_OPERATOR, Configuration.class)
                .setParameter("operator", operator).getResultList();
        if (configurationList.size() > 0)
        {
            return configurationList.get(0);
        }
        return new Configuration();
    }

}
