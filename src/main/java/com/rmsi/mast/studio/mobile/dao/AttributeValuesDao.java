/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.AttributeValues;

/**
 * @author Shruti.Thakur
 *
 */
public interface AttributeValuesDao extends GenericDAO<AttributeValues, Long> {

	/**
	 * Used to add list of AttributeValues to database
	 * 
	 * @param attributeValues
	 */
	void addAttributeValues(List<AttributeValues> attributeValuesList,
			Long parentuid);

	void updateAttributeValues(List<AttributeValues> attributeValuesList);
	
	/**
	 * It will fetch attribute value, id and listing 
	 * 
	 * @param parentUid
	 * @param attributeCategoryId
	 * @return
	 */
	List<Object> getAttributeValueandId(long parentUid, int attributeCategoryId);
	
	boolean checkEntieswithUid(List<Long> uids);

	Long getAttributeKeyById(long person_gid, long uid);
	
}
