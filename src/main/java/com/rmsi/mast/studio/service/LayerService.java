

package com.rmsi.mast.studio.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerField;

public interface LayerService {

	public List<Layer> findAllLayers();
	public Layer findLayerByName(String alias);
	public List<String> detailsByOrder(String id);
	public List<Object[]> getLayersVisibility(String[] layers);
	
	@Transactional
	public Layer updateLayer(Layer layer, Set<LayerField> layerFieldSet);
	@Transactional
	public Layer createLayer(Layer layer);
	
	@Transactional
	public boolean deleteLayerById(String id);
	public String getGeometryType(String id);
	
	@Transactional
	public String saveSLD(String layerName, String sld);
	public Set<LayerField> getLayerFieldsByLayerName(String alias);
}
