package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ProjectRegion;

/**
 * 
 * @author Abhay.Pandey
 *
 */
public interface ProjectRegionDao extends GenericDAO<ProjectRegion, Integer> {
	
	ProjectRegion findProjectRegionById(Integer id);   

}
