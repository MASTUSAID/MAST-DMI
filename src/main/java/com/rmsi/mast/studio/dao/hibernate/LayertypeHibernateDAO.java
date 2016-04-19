

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LayertypeDAO;
import com.rmsi.mast.studio.domain.Layertype;
import com.rmsi.mast.studio.domain.User;

/**
 * @author Aparesh.Chakraborty
 *
 */

@Repository

public class LayertypeHibernateDAO extends GenericHibernateDAO<Layertype, Long>
implements LayertypeDAO {
	

@SuppressWarnings("unchecked")
public Layertype findByName(String name) {
	
	List<Layertype> layertype =
		getEntityManager().createQuery("Select lt from Layertype lt where lt.name = :name").setParameter("name", name).getResultList();
	
	if(layertype.size() > 0)
		return layertype.get(0);
	else
		return null;
}


}
