

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Layergroup;

public interface LayerGroupDAO extends GenericDAO<Layergroup, Long> {
	public List<Layergroup> findByName(String name);
	
	public boolean deleteLayerGroupByName(String id);
	
	public List<Layergroup> findLayerGroupByLayerName(String layerName);
}
