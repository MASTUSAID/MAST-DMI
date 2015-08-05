/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SlopeValues;

/**
 * @author Shruti.Thakur
 *
 */
public interface SlopeValuesDao extends GenericDAO<SlopeValues, Integer>{
	
	/**
	 * This will get Slope Values by Id
	 * 
	 * @param slopeValues
	 * @return
	 */
	SlopeValues getSlopeValuesById(int slopeValues);

}
