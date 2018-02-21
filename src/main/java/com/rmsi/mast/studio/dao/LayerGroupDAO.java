

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Layergroup;

public interface LayerGroupDAO extends GenericDAO<Layergroup, Long> {
	
	public boolean deleteLayerGroupByName(String id);
	
	public List<Layergroup> findLayerGroupByLayerName(String layerName);
	
	
	public List<Layergroup> findByName(String name);
	
	public boolean deleteLayerGroupByLayerGroupID(Integer id);
	
	public Layergroup findLayergroupByName(String name);
	
	public Layergroup findLayerGroupsById(Integer id);
	
	public List<Layergroup> getLayergroupByid(Integer id); 
	public Layergroup findLayerGroupsByName(String name);


	
}
