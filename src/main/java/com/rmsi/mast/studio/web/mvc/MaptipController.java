

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.Maptip;
import com.rmsi.mast.studio.domain.MaptipPK;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.studio.service.MaptipService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class MaptipController {

	private static final Logger logger = Logger.getLogger(MaptipController.class);
	
	@Autowired
	private MaptipService maptipService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private LayerService layerService;
	
	@RequestMapping(value = "/studio/maptip/", method = RequestMethod.GET)
	@ResponseBody
    public List<Maptip> list(){
		return 	maptipService.findAllMaptip();	
	}
	
	
	@RequestMapping(value = "/studio/maptip/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Maptip getMaptipById(@PathVariable String id){		
		return 	maptipService.findMaptipByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/maptip/delete", method = RequestMethod.GET)
	@ResponseBody
    public void deleteMaptip(){
		maptipService.deleteMaptip();
	}
	
	
	@RequestMapping(value = "/studio/maptip/delete/{name}", method = RequestMethod.GET)
	@ResponseBody
    public boolean deleteMaptipById(@PathVariable String name){
		return maptipService.deleteMaptipByName(name);	
	}
	
	@RequestMapping(value = "/studio/maptip/create", method = RequestMethod.POST)
	@ResponseBody
    public boolean createMaptip(HttpServletRequest request){
		try{
			String name = ServletRequestUtils.getStringParameter(request, "name");
			String queryExpr = ServletRequestUtils.getStringParameter(request, "QueryExpression");
			String projectName = ServletRequestUtils.getStringParameter(request, "projectBean");
			String layerName = ServletRequestUtils.getStringParameter(request, "layerBean");
			String field = ServletRequestUtils.getStringParameter(request, "field");
			
			/*System.out.println("--Printing name: " + name + " query expression: " + queryExpr + 
					" Project: " + projectName + " Layer: " + layerName + " Field: " + field);
			*/
			Maptip maptip = new Maptip();
			MaptipPK pk = new MaptipPK();
			pk.setProject(projectName);
			pk.setLayer(layerName);
			
			maptip.setId(pk);
			maptip.setName(name);
			maptip.setQueryexpression(queryExpr);
			maptip.setField(field);
			
			Project project = projectService.findProjectByName(projectName);
			maptip.setProjectBean(project);
			
			Layer layer = layerService.findLayerByName(layerName);
			maptip.setLayerBean(layer);
			
			maptip = maptipService.addMaptip(maptip);
			if(maptip == null){
				return false;
			}else{
				return true;
			}
			
		}catch(Exception e){
			logger.error(e);
			return false;
		}
	}
	
	@RequestMapping(value = "/studio/maptip/edit", method = RequestMethod.POST)
	@ResponseBody
    public Maptip editMaptip(HttpServletRequest request){
		try{
			String name = ServletRequestUtils.getStringParameter(request, "_hidname");
			String queryExpr = ServletRequestUtils.getStringParameter(request, "QueryExpression");
			//String projectName = ServletRequestUtils.getStringParameter(request, "_hidproject");
			//String layerName = ServletRequestUtils.getStringParameter(request, "_hidlayer");
			String field = ServletRequestUtils.getStringParameter(request, "field");
			
			/*System.out.println("--Printing name: " + name + " query expression: " + queryExpr + 
					" Project: " + projectName + " Layer: " + layerName + " Field: " + field);
			*/
			Maptip maptip = maptipService.findMaptipByName(name);
			maptip.setQueryexpression(queryExpr);
			maptip.setField(field);
			
			//Project project = projectService.findProjectByName(projectName);
			//maptip.setProjectBean(project);
			
			//List<Layer> lstLayer = layerService.findLayerByName(layerName);
			//maptip.setLayerBean(lstLayer.get(0));
			
			
			return maptipService.updateMaptip(maptip);
			
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	

	@RequestMapping(value = "/studio/maptip/{id}/layer", method = RequestMethod.GET)
	@ResponseBody
	public List<Maptip> getMaptipsByLayer(@PathVariable String id){
		
		return maptipService.getMaptipsByLayer(id);
	}
	
	@RequestMapping(value="/studio/maptip/{project}/{layer}", method = RequestMethod.GET)
	@ResponseBody
	public Maptip getMaptipByPK(@PathVariable String project, @PathVariable String layer){
		return maptipService.findMaptipbyPK(project, layer);
	}
	
}
