

package com.rmsi.mast.studio.web.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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

import com.rmsi.mast.studio.dao.LayerGroupDAO;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.service.LayerGroupService;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.studio.service.ProjectLayerGroupService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

@Controller
public class LayerGroupController {
	
	private static final Logger logger = Logger.getLogger(LayerGroupController.class);
	
	@Autowired
	private LayerGroupService layerGroupService;
	@Autowired
	private LayerService layerService;
	
	
	@Autowired
	ProjectLayerGroupService projectLayerGroupService;
	
	@Autowired
	private LayerGroupDAO LayerGroupDAO;
	
	
	@Autowired
	private UserService userService;
	
	
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
	
	
	@RequestMapping(value = "/studio/layergroupName/{name}", method = RequestMethod.GET)
	@ResponseBody
	public List<Layergroup> detailsGroup(@PathVariable("name") String name){
		
		return layerGroupService.getLayergroupByid(Integer.parseInt(name));	
	}	
	
	
	@RequestMapping(value = "/studio/layergroup/create", method = RequestMethod.POST)
	public String create(HttpServletRequest request,HttpServletResponse response,Principal principal) {
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		Long id = user.getId();
		
		
		String layergroupname;
		Layergroup layergroup;
		//Long ll = new Long(1);
		try {
			layergroupname = ServletRequestUtils.getRequiredStringParameter(
					request, "name");
			
			if(null!=layerGroupService.findLayerGroupsByName(layergroupname)){
				layergroup = layerGroupService.findLayerGroupsByName(layergroupname);
			}
			else{
			
			
				layergroup=new Layergroup();				
			}
			
		layergroup.setName(layergroupname);
		layergroup.setAlias(ServletRequestUtils
				.getRequiredStringParameter(request, "alias"));
			

		layergroup.setIsactive(true);
		layergroup.setCreatedby(id);
		layergroup.setModifiedby(id);
		layergroup.setModifieddate(new Date());
		layergroup.setCreateddate(new Date());
		
			String layers[] = request
					.getParameterValues("selectedLayers");

					
			List<LayerLayergroup> llgList = new ArrayList<LayerLayergroup>();
		
			List<Object[]> visStatus =  layerService.getLayersVisibility(layers);
			
			for (int i = 0; i < layers.length; i++) {				
				
				
				Layer objlayer = layerService.findLayerByName(layers[i]);
				
				LayerLayergroup objlayerGroup= new LayerLayergroup();
			
				objlayerGroup.setLayergroupBean(layergroup);
				objlayerGroup.setLayerorder(i + 1);
				objlayerGroup.setLayervisibility(true);
				objlayerGroup.setCreatedby(1);
				objlayerGroup.setCreateddate(new Date());
				objlayerGroup.setModifiedby(1);
				objlayerGroup.setModifieddate(new Date());
				objlayerGroup.setLayers(objlayer);
				
				llgList.add(objlayerGroup);
				
				
			}
			layergroup.setLayerLayergroups(llgList);
		
			layerGroupService.addLayergroup(layergroup);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}catch(Exception e)
		{
			e.printStackTrace();
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
	public String delete(@PathVariable("name") String name){

		Layergroup objLayergroup=LayerGroupDAO.findLayergroupByName(name);

		// check LayerGroup linked with any project
		String staus =projectLayerGroupService.checkProjectLayergroupByLayergroupId(objLayergroup.getLayergroupid());
		if(staus.equalsIgnoreCase("NO")){
			boolean flag= layerGroupService.deleteLayerGroupByLayerGroupId(objLayergroup.getLayergroupid());
			if(flag)
			{ 
				return "success";
			}
		}else{

			return staus;
		}

		return null;


	}
}
