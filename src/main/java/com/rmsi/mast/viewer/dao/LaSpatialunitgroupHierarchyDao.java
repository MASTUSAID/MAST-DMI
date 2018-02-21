package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ProjectRegion;

public interface LaSpatialunitgroupHierarchyDao extends GenericDAO<ProjectRegion, Integer> {

	List<ProjectRegion> getAllCountry();
	List<ProjectRegion> getAllRegion(Integer country_r_id);
	List<ProjectRegion> getAllProvience(Integer region_r_id);
}
