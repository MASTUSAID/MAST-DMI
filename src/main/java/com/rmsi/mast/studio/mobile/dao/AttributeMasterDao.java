/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.domain.AttributeMaster;

/**
 * @author Shruti.Thakur
 *
 */
public interface AttributeMasterDao {

	/**
	 * This will get master attribute based on the attributeid
	 * 
	 * @param attributeId
	 * @return
	 */
	AttributeMaster getAttributteMasterById(long attributeId);

}
