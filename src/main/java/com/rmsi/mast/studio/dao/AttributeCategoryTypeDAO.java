package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.AttributeCategoryType;

public interface AttributeCategoryTypeDAO {

	List<AttributeCategoryType> getAllAttributeCategoryType();
	 List<AttributeCategoryType> getAllAttributeCategoryTypeById(Integer id);
}
