

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;

public interface LayerGroupService {
	
	public List<Layergroup> findAllLayerGroups();
	public List<Layergroup> findLayerGroupByName(String name);
	
	@Transactional
	public boolean deleteLayerGroupByName(String id);
	
	@Transactional
	void addLayergroup(Layergroup layergroup);
}
