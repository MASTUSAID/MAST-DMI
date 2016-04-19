

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Projection;
import com.rmsi.mast.studio.service.ProjectionService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class ProjectionController {

	@Autowired
	ProjectionService userService;
	
	@RequestMapping(value = "/studio/projection/", method = RequestMethod.GET)
	@ResponseBody
    public List<Projection> list(){
		return 	userService.findAllProjection();	
	}
	
	
	@RequestMapping(value = "/studio/projection/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Projection getProjectionById(@PathVariable String id){		
		return 	userService.findProjectionByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/projection/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteProjection(){
		userService.deleteProjection();
	}
	
	
	@RequestMapping(value = "/studio/projection/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteProjectionById(@PathVariable Long id){
		userService.deleteProjectionById(id);	
	}
	
	@RequestMapping(value = "/studio/projection/create", method = RequestMethod.POST)
	@ResponseBody
    public void createProjection(Projection projection){
		userService.addProjection(projection);	
	}
	
	@RequestMapping(value = "/studio/projection/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editProjection(Projection projection){
		userService.updateProjection(projection);	
	}
	
	
}
