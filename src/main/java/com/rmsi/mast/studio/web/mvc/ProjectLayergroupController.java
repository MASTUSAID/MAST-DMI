
package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.service.ProjectLayerGroupService;

@Controller
public class ProjectLayergroupController {
	@Autowired
	private ProjectLayerGroupService projectLayergroupService;

	@RequestMapping(value = "/studio/projectlayergroup/{project}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getProjectLayers(@PathVariable String project) {
		List<String> result = projectLayergroupService.getProjectLayers(project);
		for(String s: result){
			System.out.println("---Layer: " + s);
		}
		
		return result;
	}
}
