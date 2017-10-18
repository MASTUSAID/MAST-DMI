/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ShareType;

public interface ShareTypeDao extends GenericDAO<ShareType, Integer> {
	ShareType getTenureRelationshipTypeById(int tenureRelationshipTypeId);
}
