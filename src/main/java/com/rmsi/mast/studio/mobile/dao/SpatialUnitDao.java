/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.Date;
import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SpatialUnit;

/**
 * @author Shruti.Thakur
 *
 */
public interface SpatialUnitDao extends GenericDAO<SpatialUnit, Long> {

	/**
	 * Can be used to add spatial unit to database
	 * 
	 * @param spatialUnit
	 * @return
	 */
	SpatialUnit addSpatialUnit(SpatialUnit spatialUnit);

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
	SpatialUnit getSpatialUnitByUsin(long usin);

	/**
	 * It will fetch List of spatial units based on project id and work flow
	 * status id
	 * 
	 * @return
	 */
	List<SpatialUnit> findSpatialUnitByStatusId(String projectId, int statusId);
}
