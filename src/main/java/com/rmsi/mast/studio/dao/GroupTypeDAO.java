package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.GroupType;

public interface GroupTypeDAO extends GenericDAO<GroupType, Integer> {
	
	List<GroupType> findAllGroupType();
	
	
}
