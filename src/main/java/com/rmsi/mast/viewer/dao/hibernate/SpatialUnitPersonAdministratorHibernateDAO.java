package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonadministrator;
import com.rmsi.mast.viewer.dao.SpatialUnitPersonAdministratorDao;



@Repository
public class SpatialUnitPersonAdministratorHibernateDAO extends GenericHibernateDAO<SpatialunitPersonadministrator, Long>
implements SpatialUnitPersonAdministratorDao {

	@Override
	public List<Long> findAdminIdbyUsin(Long id) {
		
		
		try {
			Query query = getEntityManager().createQuery("Select spa.personAdministrator.adminid from SpatialunitPersonadministrator spa where spa.usin = :usin");
			List<Long> personadmin = query.setParameter("usin", id).getResultList();		

			if(personadmin.size() > 0){
				return personadmin;
			}		
			else
			{
				return null;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	
		
		
	}
	
	
}




