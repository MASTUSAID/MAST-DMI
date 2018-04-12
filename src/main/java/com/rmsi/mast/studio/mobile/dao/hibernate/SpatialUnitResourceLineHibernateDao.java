/**
 *
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.SpatialUnitResourceLine;
import com.rmsi.mast.studio.domain.SpatialUnitResourcePoint;
import com.rmsi.mast.studio.domain.SpatialUnitResourcePolygon;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.domain.fetch.DisputeBasic;
import com.rmsi.mast.studio.domain.fetch.MediaBasic;
import com.rmsi.mast.studio.domain.fetch.RightBasic;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitResourceLineDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitResourcePolygonDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class SpatialUnitResourceLineHibernateDao extends
        GenericHibernateDAO<SpatialUnitResourceLine, Long> implements SpatialUnitResourceLineDao {

    private static final Logger logger = Logger
            .getLogger(SpatialUnitResourceLineHibernateDao.class);

	@Override
	public SpatialUnitResourceLine addSpatialUnitResourceLine(
			SpatialUnitResourceLine spatialUnit) {
		try {
	            return makePersistent(spatialUnit);

	        } catch (Exception ex) {
	            System.out.println("Exception while adding data...." + ex);
	            logger.error(ex);
	            throw ex;
	        }
		}

	@Override
	public List<SpatialUnit> getSpatialUnitByProject(String projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpatialUnit findByImeiandTimeStamp(String imeiNumber, Date surveyDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
		// TODO Auto-generated method stub
	public ClaimBasic getSpatialUnitByUsin(long usin) {
		return null;
	}

	@Override
	public List<SpatialUnit> findSpatialUnitByStatusId(String projectId,
			int statusId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClaimBasic> getClaimsBasicByStatus(Integer projectId,
			int statusId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClaimBasic> getClaimsBasicByProject(Integer projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClaimBasic> getClaimsBasicByLandId(Long landid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLandObject(Long landId) {
		 List<Date> line = new ArrayList<>();
		 List<Date> polygon = new ArrayList<>();
		 List<Date> point = new ArrayList<>();
		 Object obj =null;
		 
	        try {
	     	        	String query = "select s.createddate, s.area from SpatialUnitResourceLine  s where s.landid = :id";
	     	        	line = getEntityManager().createQuery(query).setParameter("id", landId).getResultList();
	     	        	if(line.size() >0 ){
	     	        		obj =(Object) line;
	     	        		return obj;
	     	        	}
	     	        	else{
	     	        		 query = "select s.createddate, s.area from SpatialUnitResourcePolygon  s where s.landid = :id";
		     	        	polygon = getEntityManager().createQuery(query).setParameter("id", landId).getResultList();
		     	        	if(polygon.size() >0 ){
		     	        		obj =(Object) polygon;
		     	        		return obj;
		     	        	}
		     	        	else{
		     	        		 query = "select s.createddate, s.area from SpatialUnitResourcePoint  s where s.landid = :id";
		     	        		point = getEntityManager().createQuery(query).setParameter("id", landId).getResultList();
				     	        	if(point.size() >0 ){
				     	        		obj =(Object) point;
				     	        		return obj;
				     	        	}
		     	        	}

	     	        	}
	            return obj;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
	}

	

}
