package com.rmsi.mast.viewer.dao.hibernate;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;
import com.rmsi.mast.viewer.dao.LaExtRegistrationLandShareTypeDao;

@Repository
public class LaExtRegistrationLandShareTypeHibernateDao extends GenericHibernateDAO<LaExtRegistrationLandShareType, Long> implements LaExtRegistrationLandShareTypeDao {

	
	
	@Override
	public LaExtRegistrationLandShareType getShareTypeObjectByLandId(Long landid) {
		 
		String query="Select s from LaExtRegistrationLandShareType s where landid="+landid;
		
		LaExtRegistrationLandShareType object =	(LaExtRegistrationLandShareType) getEntityManager().createQuery(query).getSingleResult();
		if(null!=object){
			
			return object;
		}
		
		
		
		// TODO Auto-generated method stub
		else{ 
			return null;
		}
	}

	@Override
	public boolean updateRegistrationSharetype(
			Long sharetypeid, Long landId) {
		
		try {
			String query="Update LaExtRegistrationLandShareType s set s.landsharetypeid="+sharetypeid+" where landid="+landId;
			
			 int i=getEntityManager().createQuery(query).executeUpdate();
				if(i>0){
						return true;
					}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		// TODO Auto-generated method stub
		return true;
	}

	
	
	
}
