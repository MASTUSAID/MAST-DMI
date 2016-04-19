

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.service.UnitService;


/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class UnitController {

	@Autowired
	UnitService unitService;
	
	@RequestMapping(value = "/studio/unit/", method = RequestMethod.GET)
	@ResponseBody
    public List<Unit> list(){
		return 	unitService.findAllUnit();	
	}
	
	
	@RequestMapping(value = "/studio/unit/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Unit getUnitById(@PathVariable String id){		
		return 	unitService.findUnitByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/unit/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteUnit(){
		unitService.deleteUnit();
	}
	
	
	@RequestMapping(value = "/studio/unit/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public boolean deleteUnitByname(@PathVariable String id){
		return unitService.deleteUnitByName(id);	
		
	}
	
	@RequestMapping(value = "/studio/unit/create", method = RequestMethod.POST)
	@ResponseBody
    public void createUnit(Unit unit){
		
		unitService.addUnit(unit);	
	}
	
	@RequestMapping(value = "/studio/unit/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editUnit(Unit unit){
		unitService.updateUnit(unit);	
	}
	
	
}
