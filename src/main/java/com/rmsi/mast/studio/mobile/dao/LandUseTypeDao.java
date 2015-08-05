/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LandUseType;

/**
 * @author shruti.thakur
 *
 */
public interface LandUseTypeDao extends GenericDAO<LandUseType, Integer>{

	LandUseType getLandUseTypeById(int landUseTypeId);
	
}
