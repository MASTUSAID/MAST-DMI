

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;

public interface LayerGroupService {
	
	public List<Layergroup> findAllLayerGroups();

	@Transactional
	public boolean deleteLayerGroupByName(String id);
	
	@Transactional
	void addLayergroup(Layergroup layergroup);
	
	@Transactional
	public List<Layergroup> findLayerGroupByName(String name);
	
	@Transactional
	public boolean deleteLayerGroupByLayerGroupId(Integer id);
	
	
	public Layergroup findLayerGroupsById(Integer id);
	
	public List<Layergroup> getLayergroupByid(Integer id);
	
	public Layergroup findLayerGroupsByName(String name);
	
	
	
}
