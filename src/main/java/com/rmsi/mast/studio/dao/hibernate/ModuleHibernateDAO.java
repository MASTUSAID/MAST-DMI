

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ModuleDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Module;

/**
 * @author Aparesh.Chakraborty
 *
 */

@Repository

public class ModuleHibernateDAO extends GenericHibernateDAO<Module, Long>
implements ModuleDAO {

@SuppressWarnings("unchecked")

public Module findByName(String name) {
	List<Module> module =
		getEntityManager().createQuery("Select m from Module m where m.name = :name").setParameter("name", name).getResultList();
	
	if(module.size() > 0)
		return module.get(0);
	else
		return null;
}
}