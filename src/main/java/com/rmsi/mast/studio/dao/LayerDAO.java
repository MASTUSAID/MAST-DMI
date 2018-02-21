

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.Layergroup;

public interface LayerDAO extends GenericDAO<Layer, Long>{
	public Layer findByAliasName(String layerid);
	public List<String> getLayerByLayerOrder();
	public String saveSLD(String layerName, String sld);
	public List<Object[]> getVisibilityStatus(String[] layers);
	
	public List<Layer> getSurveyLayer(String layerList);
	
	public Layer findLayerById(long layerid);
	
	public String checklayerByid(Long id);
	public boolean deleteLayerById(Long id);
}
