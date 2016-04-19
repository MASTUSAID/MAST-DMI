

package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.LayerFieldDAO;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.service.LayerFieldService;

@Service
public class LayerFieldServiceImpl implements LayerFieldService {
	@Autowired
	private LayerFieldDAO layerFieldDao;
	
	public String create(LayerField layerField){
		return layerFieldDao.create(layerField);
	}
	
	public String edit(String id, LayerField layerField){
		return layerFieldDao.edit(id, layerField);
	}
	
	public String delete(String id){
		return layerFieldDao.delete(id);
	}
	
}
