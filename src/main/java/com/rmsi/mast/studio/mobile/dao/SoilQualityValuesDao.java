/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SoilQualityValues;

/**
 * @author Shruti.Thakur
 *
 */
public interface SoilQualityValuesDao extends GenericDAO<SoilQualityValues, Integer>{

	/**
	 * This will get SoilQualityValues by Id
	 * @param soilQualityValuesId
	 * @return
	 */
	public SoilQualityValues getSoilQualityValuesById(int soilQualityValuesId);
	
}
