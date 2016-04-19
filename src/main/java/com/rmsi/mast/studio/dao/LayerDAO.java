

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.Layergroup;

public interface LayerDAO extends GenericDAO<Layer, Long>{
	public Layer findByAliasName(String alias);
	public List<String> getLayerByLayerOrder();
	public boolean deleteLayerByAliasName(String id, List<Layergroup> layerGroup);
	public String getGeometryType(String id);
	public String saveSLD(String layerName, String sld);
	public List<Object[]> getVisibilityStatus(String[] layers);
	
	public List<Layer> getSurveyLayer(String layerList);
}
