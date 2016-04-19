

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Module;
import com.rmsi.mast.studio.service.ModuleService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class ModuleController {

	@Autowired
	ModuleService moduleService;
	
	@RequestMapping(value = "/studio/module/", method = RequestMethod.GET)
	@ResponseBody
    public List<Module> list(){
		return 	moduleService.findAllModule();	
	}
	
	
	@RequestMapping(value = "/studio/module/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Module getModuleById(@PathVariable String id){
		return 	moduleService.findModuleByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/module/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteModule(){
		moduleService.deleteModule();
	}
	
	
	@RequestMapping(value = "/studio/module/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteModuleById(@PathVariable Long id){
		moduleService.deleteModuleById(id);	
	}
	
	@RequestMapping(value = "/studio/module/create", method = RequestMethod.POST)
	@ResponseBody
    public void createModule(Module module){
		moduleService.addModule(module);	
	}
	
	@RequestMapping(value = "/studio/module/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editModule(Module module){
		moduleService.updateModule(module);	
	}
	
	
}
