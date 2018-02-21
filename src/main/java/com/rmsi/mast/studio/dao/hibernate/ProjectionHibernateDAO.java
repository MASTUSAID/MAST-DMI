

package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
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
			getEntityManager().createQuery("Select p from Projection p where p.projection = :code").setParameter("code", code).getResultList();
		
		if(projection.size() > 0)
			return projection.get(0);
		else
			return null;
	}

	@Override
	public List<Projection> findAllProjection() {
	

		try {
			@SuppressWarnings("unchecked")
			List<Projection> projection =
				getEntityManager().createQuery("Select p from Projection p where p.isactive = true").getResultList();
			
			 return projection;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Projection findProjectionById(Integer id) {
		List<Projection> projection = new  ArrayList<Projection>();
		
		try {
			projection =	getEntityManager().createQuery("Select p from Projection p where p.isactive = true and  p.projectionid = :Id").setParameter("Id", id).getResultList();
			
			if(projection.size() > 0)
				return projection.get(0);
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
