

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Printtemplate;
import com.rmsi.mast.studio.service.PrintTemplateService;

@Controller
public class PrintTemplateController {

	@Autowired
	PrintTemplateService printTemplateService;
	
	
	@RequestMapping(value = "/studio/printtmpl/", method = RequestMethod.GET)
	@ResponseBody
    public List<Printtemplate> getAllPrintTemplates(){
		
		return 	printTemplateService.findAllPrintTemplates();
	}
	
	
	@RequestMapping(value = "/studio/printtmpl/{projectname}", method = RequestMethod.GET)
	@ResponseBody
    public List<Printtemplate> getActionById(@PathVariable String projectname){
		
		return 	printTemplateService.findByProjectName(projectname);
	}
    	
}
