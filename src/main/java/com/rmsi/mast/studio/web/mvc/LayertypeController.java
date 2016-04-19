

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Layertype;
import com.rmsi.mast.studio.service.LayertypeService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class LayertypeController {

	@Autowired
	LayertypeService layertypeService;
	
	@RequestMapping(value = "/studio/layertype/", method = RequestMethod.GET)
	@ResponseBody
    public List<Layertype> list(){
		return 	layertypeService.findAllLayertype();	
	}
	
	
	@RequestMapping(value = "/studio/layertype/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Layertype getLayertypeById(@PathVariable String id){
		return 	layertypeService.findLayertypeByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/layertype/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteLayertypes(){
		layertypeService.deleteLayertype();
	}
	
	
	@RequestMapping(value = "/studio/layertype/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteLayertypeById(@PathVariable Long id){
		layertypeService.deleteLayertypeById(id);	
	}
	
	@RequestMapping(value = "/studio/layertype/create", method = RequestMethod.POST)
	@ResponseBody
    public void createLayertype(Layertype layertype){
		layertypeService.addLayertype(layertype);	
	}
	
	@RequestMapping(value = "/studio/layertype/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editLayertype(Layertype layertype){
		layertypeService.updateLayertype(layertype);	
	}
	
	
}
