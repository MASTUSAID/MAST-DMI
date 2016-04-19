

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface ProjectLayerGroupService {
	@Transactional
	void deleteProjectLayergroupById(Long id);
	
	List<String> getProjectLayers(String project);
}
