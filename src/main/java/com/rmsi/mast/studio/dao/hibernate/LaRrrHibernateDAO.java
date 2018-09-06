package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LaRrrDAO;
import com.rmsi.mast.studio.domain.LaRrr;


@Repository
public class LaRrrHibernateDAO extends GenericHibernateDAO<LaRrr, Integer> implements LaRrrDAO 
{
	
	
	 @Override
	    public LaRrr addLaRrr(LaRrr spatialUnit) {

	        try {
	            return makePersistent(spatialUnit);

	        } catch (Exception ex) {
	            System.out.println("Exception while adding data...." + ex);
	            throw ex;
	        }
	    }

}
