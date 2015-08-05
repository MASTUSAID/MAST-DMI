/**
 * 
 */
package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;

public interface SpatialUnitTempDao extends GenericDAO<SpatialUnitTemp, Long> {

	List<SpatialUnitTemp> findOrderedSpatialUnit(String defaultProject,int startfrom);

	Integer AllSpatialUnitTemp(String defaultProject);

	List<SpatialUnitTemp> findSpatialUnitforUKAGeneration(String project);

	List<Long> findUsinforUKAGeneration(String project, String hamletCode);

	boolean updateUKAnumber(Long long1, String uka);
	
}
