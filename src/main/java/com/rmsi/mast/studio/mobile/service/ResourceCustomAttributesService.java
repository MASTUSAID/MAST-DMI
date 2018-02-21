package com.rmsi.mast.studio.mobile.service;

import java.util.List;

import com.rmsi.mast.studio.domain.ResourceCustomAttributes;

public interface ResourceCustomAttributesService {

	List<ResourceCustomAttributes> getByProjectId(Integer Id);
	ResourceCustomAttributes getByCustomattributeId(Integer Id);
}
