/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LandType;

/**
 * @author Shruti.Thakur
 *
 */
public interface LandTypeDao extends GenericDAO<LandType, Integer>{

	/**
	 * This will get Land Type by Id
	 * 
	 * @param landTypeId
	 * @return
	 */
	LandType getLandTypeById(int landTypeId);
}
