package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.viewer.dao.SpatialUnitDAO;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class SpatialUnitHibernateDAO extends
GenericHibernateDAO<SpatialUnit, Long> implements SpatialUnitDAO{

	Logger logger = Logger.getLogger(SpatialUnitHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpatialUnit> getSpatialUnitLandMappingDetails(Long landid) {
		
		//long landids =Long.parseLong(landid+"");
				
		Query query= getEntityManager().createQuery("select SU from SpatialUnit SU where SU.landid =:landId" );
		try {
			List<SpatialUnit> lstSpatialUnit = query.setParameter("landId", landid).getResultList();
			return lstSpatialUnit;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
		
	}

}
