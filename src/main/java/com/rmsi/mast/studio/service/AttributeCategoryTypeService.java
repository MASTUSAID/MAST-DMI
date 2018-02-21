package com.rmsi.mast.studio.service;

import java.util.List;

import com.rmsi.mast.studio.domain.AttributeCategoryType;

public interface AttributeCategoryTypeService {
	
	List<AttributeCategoryType> getAllAttributeCategoryType();
	List<AttributeCategoryType> getAllAttributeCategoryTypeById(Integer id);
}
