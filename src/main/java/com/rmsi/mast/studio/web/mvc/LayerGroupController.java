

package com.rmsi.mast.studio.web.mvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.service.LayerGroupService;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

@Controller
public class LayerGroupController {
	
	private static final Logger logger = Logger.getLogger(LayerGroupController.class);
	
	@Autowired
	private LayerGroupService layerGroupService;
	@Autowired
	private LayerService layerService;
	
	@RequestMapping(value = "/studio/layergroup/", method = RequestMethod.GET)
	@ResponseBody
	public List<Layergroup> list(){
		return layerGroupService.findAllLayerGroups();
	}
	
	
	@RequestMapping(value = "/studio/layergroup/{name}", method = RequestMethod.GET)
	@ResponseBody
	public List<Layergroup> details(@PathVariable("name") String name){
		
			return layerGroupService.findLayerGroupByName(name);		
	}
	
	@RequestMapping(value = "/studio/layergroup/create", method = RequestMethod.POST)
	public String create(HttpServletRequest request,
			HttpServletResponse response) {
		
		String layergroupname;
		Layergroup layergroup;
		try {
			layergroupname = ServletRequestUtils.getRequiredStringParameter(
					request, "name");
			
			if(details(layergroupname).size()>0){
				layergroup = details(layergroupname).get(0);	
			}
			else{
			
			
				layergroup=new Layergroup();				
			}
			
			layergroup.setName(layergroupname);
			
			layergroup.setAlias(ServletRequestUtils
					.getRequiredStringParameter(request, "alias"));
			

			String layers[] = request
					.getParameterValues("selectedLayers");

					
			Set<LayerLayergroup> llgList = new HashSet<LayerLayergroup>();
			
			Set<ProjectLayergroup> plgList = new HashSet<ProjectLayergroup>();
			
			//Set<ProjectLayergroup> plgList = project.getProjectLayergroups();
			//Layergroup lg=new Layergroup();
			List<Object[]> visStatus =  layerService.getLayersVisibility(layers);
			
			for (int i = 0; i < layers.length; i++) {				
				
				LayerLayergroup llg = new LayerLayergroup();
				
				
				llg.setLayer(layers[i]);
				llg.setLayergroupBean(layergroup);
				
				llg.setLayerorder(i + 1);
				
				//System.out.println("-----Layer Visibility status: " + layers[i] + " ---> " + visStatus.get(i));
				llg.setLayervisibility(getVisibilityStatus(visStatus, layers[i]));
				llgList.add(llg);
			}

			layergroup.setLayerLayergroups(llgList);
			layergroup.setProjectLayergroups(plgList);
			layerGroupService.addLayergroup(layergroup);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
		return null;
	}
	
	private boolean getVisibilityStatus(List<Object[]> lstVisStatus, String layer){
		boolean status = false;
		for(int i=0; i<lstVisStatus.size(); i++){
			Object[] o = lstVisStatus.get(i);
			System.out.println("----Object[0]: " + o[0] + "   " + "Object[1]:" +  o[1]);
			if(o[0].equals(layer)){
				status = (Boolean)o[1];
				break;
			}
		}
		return status;
	}
	
	@RequestMapping(value = "/studio/layergroup/edit", method = RequestMethod.PUT)
	public String edit(@ModelAttribute("layergroup") Layergroup layerGroup){
		return null;
	}
	
	@RequestMapping(value="/studio/layergroup/delete/{name}", method = RequestMethod.GET)
	@ResponseBody
	public boolean delete(@PathVariable("name") String name){
		return layerGroupService.deleteLayerGroupByName(name);
	}
}
