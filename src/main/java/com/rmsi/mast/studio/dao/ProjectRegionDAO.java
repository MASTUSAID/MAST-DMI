
/* ************@RMSI/NK add for region to district*******************************/

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;

public interface ProjectRegionDAO extends GenericDAO<Project, Long> {
	
	
	
	List<ProjectRegion> findAllCountry();
	
	List<ProjectRegion> findRegionByCountry(String countryname);
	
	List<ProjectRegion> findDistrictByRegion(String countryname);
	
	List<ProjectRegion> findVillageByDistrict(String countryname);
	
	List<ProjectRegion> findHamletByVillage(String countryname);
	
	
	
	
	
	
	
	
	
}
