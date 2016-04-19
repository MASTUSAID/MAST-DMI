
package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.service.ActionService;

@Controller
public class ActionController {

	@Autowired
	ActionService actionService;
	
	@RequestMapping(value = "/studio/action/", method = RequestMethod.GET)
	@ResponseBody
    public List<Action> list(){
		return 	actionService.findAllActions();	
	}
	
	
	@RequestMapping(value = "/studio/action/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Action getActionById(@PathVariable String id){
		//return 	actionService.findActionById(id);
		return 	actionService.findActionByName(id);
	}
    	
	
	@RequestMapping(value = "/studio/action/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteActions(){
		actionService.deleteAction();
	}
	
	
	@RequestMapping(value = "/studio/action/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteActionById(@PathVariable String id){
		actionService.deleteActionByName(id);	
	}
	
	@RequestMapping(value = "/studio/action/create", method = RequestMethod.POST)
	@ResponseBody
    public void createAction(Action action){
		actionService.addAction(action);	
	}
	
	@RequestMapping(value = "/studio/action/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editAction(Action action){
		actionService.updateAction(action);	
	}
	
		
}
