
/* ************@RMSI/NK add for region to district*******************************/

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;

public interface ProjectRegionDAO extends GenericDAO<Project, Long> {
	
	
	
	List<ProjectRegion> findAllCountry();
	
	List<ProjectRegion> findRegionByCountry(Integer countryname);
	
	List<ProjectRegion> findDistrictByRegion(Integer countryname);
	
	List<ProjectRegion> findVillageByDistrict(Integer countryname);
	
	List<ProjectRegion> findPlaceByVillage(Integer countryname);
	
	ProjectRegion findProjectRegionById(Integer id);   
	
	
	
	
	
	
	
}
