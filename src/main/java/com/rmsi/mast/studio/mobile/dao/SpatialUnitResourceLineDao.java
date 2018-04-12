/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.Date;
import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.SpatialUnitResourceLine;
import com.rmsi.mast.studio.domain.SpatialUnitResourcePolygon;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.domain.fetch.DisputeBasic;
import com.rmsi.mast.studio.domain.fetch.MediaBasic;
import com.rmsi.mast.studio.domain.fetch.RightBasic;

/**
 * @author Shruti.Thakur
 *
 */
public interface SpatialUnitResourceLineDao extends GenericDAO<SpatialUnitResourceLine, Long> {

	/**
	 * Can be used to add spatial unit to database
	 * 
	 * @param spatialUnit
	 * @return
	 */
	SpatialUnitResourceLine addSpatialUnitResourceLine(SpatialUnitResourceLine spatialUnit);

	
	
	Object getLandObject(Long  landId);
	
	
	
	
	/**
	 * Can be used to get the list of Spatial Unit By ProjectID
	 * 
	 * @param projectId
	 * @return
	 */
	List<SpatialUnit> getSpatialUnitByProject(String projectId);

	/**
	 * Can be used to find Spatial Unit by IMEI and Time Stamp
	 * 
	 * @param imeiNumber
	 * @param surveyDate
	 * @return
	 */
	SpatialUnit findByImeiandTimeStamp(String imeiNumber, Date surveyDate);

	/**
	 * Can be used to get spatial unit by USIN
	 * 
	 * @param usin
	 * @return
	 */
	ClaimBasic getSpatialUnitByUsin(long usin);

	/**
	 * It will fetch List of spatial units based on project id and work flow
	 * status id
	 * 
	 * @return
	 */
	List<SpatialUnit> findSpatialUnitByStatusId(String projectId, int statusId);
        
        List<ClaimBasic> getClaimsBasicByStatus(Integer projectId, int statusId);
        
        List<ClaimBasic> getClaimsBasicByProject(Integer projectId);
        
        List<ClaimBasic> getClaimsBasicByLandId(Long landid);
}
