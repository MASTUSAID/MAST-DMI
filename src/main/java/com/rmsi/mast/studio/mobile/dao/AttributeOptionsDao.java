/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.AttributeOptions;

/**
 * @author Shruti.Thakur
 *
 */
public interface AttributeOptionsDao extends GenericDAO<AttributeOptions, Integer> {
	
	/**
	 * This will be used for getting Attribute Options by its ID
	 * 
	 * @param attributeId
	 * @return
	 */
	List<AttributeOptions> getAttributeOptions(Long attributeId);

	String getAttributeOptionsId(Integer attributeId, int parentid);

}
