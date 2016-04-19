

package com.rmsi.mast.studio.service;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.LayerField;

public interface LayerFieldService {

	@Transactional
	public String create(LayerField layerField);
	
	@Transactional
	public String edit(String id, LayerField layerField);
	
	@Transactional
	public String delete(String id);
}
