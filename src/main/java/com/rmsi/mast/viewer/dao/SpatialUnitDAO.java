package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SpatialUnit;

public interface SpatialUnitDAO extends GenericDAO<SpatialUnit, Long>{

	List<SpatialUnit> getSpatialUnitLandMappingDetails(Long landid);
}
