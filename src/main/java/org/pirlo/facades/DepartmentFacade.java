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
import org.pirlo.entities.Department;
import org.pirlo.entities.Hospital;
import org.pirlo.enums.IACategoryEnum;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Stateless
public class DepartmentFacade extends AbstractFacade<Department>
{

    @PersistenceContext(unitName = "PIRCS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DepartmentFacade()
    {
        super(Department.class);
    }

    public List<Department> findByHospital(Hospital hospital)
    {
        return em.createNamedQuery(Department.FIND_BY_HOSPITAL, Department.class)
                .setParameter("hospital", hospital)
                .getResultList();
    }

}
