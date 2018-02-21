package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeMasterResourcePOI;
import com.rmsi.mast.studio.domain.Project;

public interface AttributeMasterResourcePoiDAO extends GenericDAO<AttributeMasterResourcePOI, Integer>{
	
	AttributeMasterResourcePOI getPOIAttributteMasterById(Integer attributeId);
	
	 List<AttributeMasterResourcePOI> getAttributeMasterResourcePOIByProject(Integer projectId);

}
