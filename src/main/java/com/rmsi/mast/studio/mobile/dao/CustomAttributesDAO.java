package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.CustomAttributes;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;

public interface CustomAttributesDAO extends GenericDAO<CustomAttributes, Integer>{

	CustomAttributes addResourceCustomAttributeValues (CustomAttributes resourceAttributevalues);
}
