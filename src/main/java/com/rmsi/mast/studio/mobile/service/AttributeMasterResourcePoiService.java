package com.rmsi.mast.studio.mobile.service;

import java.util.List;

import com.rmsi.mast.studio.domain.AttributeMasterResourcePOI;
import com.rmsi.mast.studio.domain.Project;

public interface AttributeMasterResourcePoiService {
	
	List<AttributeMasterResourcePOI> getAllPOIAttributteMaster(Integer projectId);

}
