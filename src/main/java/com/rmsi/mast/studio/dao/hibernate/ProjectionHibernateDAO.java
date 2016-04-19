

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectionDAO;
import com.rmsi.mast.studio.domain.Projection;

@Repository
public class ProjectionHibernateDAO extends GenericHibernateDAO<Projection, Long> implements
ProjectionDAO {

	@SuppressWarnings("unchecked")
	public Projection findByName(String code) {
		
		List<Projection> projection =
			getEntityManager().createQuery("Select p from Projection p where p.code = :code").setParameter("code", code).getResultList();
		
		if(projection.size() > 0)
			return projection.get(0);
		else
			return null;
	}
	
	
}
