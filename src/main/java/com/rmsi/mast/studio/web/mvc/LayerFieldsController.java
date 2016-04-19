

package com.rmsi.mast.studio.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.service.LayerFieldService;

public class LayerFieldsController {
	@Autowired
	private LayerFieldService layerFieldService;
	
	@RequestMapping(value="layerfield/create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@ModelAttribute("layerField") LayerField layerField){
		return layerFieldService.create(layerField);
	}
	
	@RequestMapping(value = "layerfield/edit", method = RequestMethod.POST)
	@ResponseBody
	public String edit(@ModelAttribute("layerField") LayerField layerField){
		return layerFieldService.edit(layerField.getAlias(), layerField);
	}
	
	@RequestMapping(value="layerfield/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable("id") String id){
		return layerFieldService.delete(id);
	}
}
