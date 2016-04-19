

package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.LayerField;

public interface LayerFieldDAO extends GenericDAO<LayerField, Long> {
	
	public String create(LayerField layerField);
	public String edit(String id, LayerField layerField);
	public String delete(String id);
}
