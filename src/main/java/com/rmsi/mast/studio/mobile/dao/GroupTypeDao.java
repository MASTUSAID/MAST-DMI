/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.GroupType;

/**
 * @author Shruti.Thakur
 *
 */
public interface GroupTypeDao extends GenericDAO<GroupType, Integer>{

	/**
	 * This will get Group Type by Id
	 * 
	 * @param groupTypeId
	 * @return
	 */
	GroupType getGroupTypeById(int groupTypeId);
	
}
