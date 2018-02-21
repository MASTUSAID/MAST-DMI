package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitgroup;

/**
 * 
 * @author Abhay.Pandey
 *
 */
public interface LaSpatialunitgroupDAO extends GenericDAO<LaSpatialunitgroup, Integer>{

	public LaSpatialunitgroup findLaSpatialunitgroupById(Integer id) ;
}
