

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Outputformat;
import com.rmsi.mast.studio.service.OutputformatService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class OutputformatController {

	@Autowired
	OutputformatService outputformatService;
	
	@RequestMapping(value = "/studio/outputformat/", method = RequestMethod.GET)
	@ResponseBody
    public List<Outputformat> list(){
		return 	outputformatService.findAllOutputformat();	
	}
	
	
	@RequestMapping(value = "/studio/outputformat/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Outputformat getOutputformatById(@PathVariable String id){		
		return 	outputformatService.findOutputformatByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/outputformat/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteOutputformat(){
		outputformatService.deleteOutputformat();
	}
	
	
	@RequestMapping(value = "/studio/outputformat/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteOutputformatById(@PathVariable Long id){
		outputformatService.deleteOutputformatById(id);	
	}
	
	@RequestMapping(value = "/studio/outputformat/create", method = RequestMethod.POST)
	@ResponseBody
    public void createOutputformat(Outputformat outputformat){
		outputformatService.addOutputformat(outputformat);	
	}
	
	@RequestMapping(value = "/studio/outputformat/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editOutputformat(Outputformat outputformat){
		outputformatService.updateOutputformat(outputformat);	
	}
	
	
}
