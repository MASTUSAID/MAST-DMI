package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitgroup;
import com.rmsi.mast.studio.mobile.dao.LaSpatialunitgroupDao;

@Repository
public class LaSpatialunitgroupHibernateDao extends GenericHibernateDAO<LaSpatialunitgroup, Integer> implements LaSpatialunitgroupDao  {

	@SuppressWarnings("unchecked")
	@Override
	public LaSpatialunitgroup findLaSpatialunitgroupById(Integer id) {
	     List<LaSpatialunitgroup> lstspatialUnitgroup= new ArrayList<LaSpatialunitgroup>();
		 
	        try {
	     
	        	String query = "select s from LaSpatialunitgroup  s where s.isactive = true and  s.spatialunitgroupid = :id";
	        	lstspatialUnitgroup = getEntityManager().createQuery(query).setParameter("id", id)
	                    .getResultList();

	            if (lstspatialUnitgroup.size()>0) {
	                return lstspatialUnitgroup.get(0);
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
	        
	        
	        return null;
	}

	
	
	
}
