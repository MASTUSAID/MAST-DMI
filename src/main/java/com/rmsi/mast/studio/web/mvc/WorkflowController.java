package com.rmsi.mast.studio.web.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.WorkflowStatusHistoryDAO;
import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.domain.WorkflowActionmapping;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.domain.fetch.UserParcel;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.service.ClaimBasicService;
import com.rmsi.mast.studio.service.ProjectAreaService;
import com.rmsi.mast.studio.service.ProjectRegionService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.service.WorkflowActionService;
import com.rmsi.mast.studio.service.WorkflowService;
import com.rmsi.mast.viewer.service.LaExtRegistrationLandShareTypeService;


@Controller
public class WorkflowController {

	
	 @Autowired
	 private WorkflowService  workflowService;
	
	
	 @Autowired
	 private UserService userService;
	 
	 
	 @Autowired
	 private WorkflowActionService workflowActionService;
	 
	 
	 @Autowired
	 WorkflowStatusHistoryDAO  workflowStatusHistoryDAO;
	 
	 @Autowired
	 ProjectDAO projectDAO;
	
	@Autowired
	SpatialUnitDao spatialUnitDao;
	
	
     @Autowired
	 private SpatialUnitService spatialUnitService;
	
	 @Autowired
	 private ProjectRegionService  projectRegionService;
	 
	 
	 @Autowired
	 private ProjectAreaService projectAreaService;
	 
	 
	 @Autowired
	 private ClaimBasicService claimBasicService;
	 
	 
	 @Autowired
	 private LaExtRegistrationLandShareTypeService laExtRegistrationLandShareTypeService;
	 
	 
	@RequestMapping(value = "/viewer/landrecords/workflow", method = RequestMethod.GET)
    @ResponseBody
    public List<Workflow> getWorkflow() {
        return workflowService.getAllWorkflow();
    }
	
	
	
	 @RequestMapping(value = "/viewer/landrecords/workflowAction/{id}/{project}/{landid}", method = RequestMethod.GET)
     @ResponseBody
     public List<WorkflowActionmapping> getworkflowAction(@PathVariable String id, @PathVariable String project,@PathVariable Long landid, Principal principal) {
		User objuser= userService.findByUniqueName( principal.getName());
		Integer roleid =objuser.getUserRole().iterator().next().getRoleBean().getRoleid();
	    Project objproject= projectDAO.findByName(project);
	   Integer workflowdefId= objproject.getWorkflowdefid();
	   
	   List<WorkflowActionmapping>  lst= workflowActionService.getWorkflowActionmapping(Integer.parseInt(id), roleid ,workflowdefId);
	
	   return lst;
	   
		
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/viewer/landrecords/action/approve/{land}/{workflowId}", method = RequestMethod.POST)
		@ResponseBody
		public Integer actionApprove(@PathVariable Long land, @PathVariable Integer workflowId, Principal principal,HttpServletRequest request, HttpServletResponse response) {

			
			
			String comments="";
			try {
				comments = ServletRequestUtils.getRequiredStringParameter(request, "commentsStatusWorkflow");
			} catch (ServletRequestBindingException e) {
				
			}

			String username = principal.getName();
			User user = userService.findByUniqueName(username);
			long userid = user.getId();
			return workflowActionService.actionApprove(land, userid,workflowId,comments);
			

		}
	 
	 
	 @RequestMapping(value = "/viewer/landrecords/action/reject/{land}/{workflowId}", method = RequestMethod.POST)
		@ResponseBody
		public Integer actionReject(@PathVariable Long land, @PathVariable Integer workflowId, Principal principal,HttpServletRequest request, HttpServletResponse response) {

			
			
			String comments="";
			try {
				comments = ServletRequestUtils.getRequiredStringParameter(request, "commentsStatusWorkflow");
			} catch (ServletRequestBindingException e) {
				
			}

			String username = principal.getName();
			User user = userService.findByUniqueName(username);
			long userid = user.getId();
			return workflowActionService.actionReject(land, userid,workflowId,comments);
			

		}
	 
	 
	 
	 @RequestMapping(value = "/viewer/landrecords/action/register/{land}/{workflowId}", method = RequestMethod.POST)
		@ResponseBody
		public Integer actionRegister(@PathVariable Long land, @PathVariable Integer workflowId, Principal principal,HttpServletRequest request, HttpServletResponse response) {

			
			
			String comments="";
			try {
				comments = ServletRequestUtils.getRequiredStringParameter(request, "commentsStatusWorkflow");
			} catch (ServletRequestBindingException e) {
				
			}

			String username = principal.getName();
			User user = userService.findByUniqueName(username);
			Long userid = user.getId();
			
			if(workflowId==4){

				String  _userDefineparcelnum="";

				try {
					ClaimBasic spatialUnit = spatialUnitService.getClaimsBasicByLandId(land).get(0);
					if(null!=spatialUnit){

						ProjectArea objProjectArea= projectAreaService.findProjectAreaByProjectId(spatialUnit.getProjectnameid()); 

						if(null!=objProjectArea && null!=objProjectArea.getLaSpatialunitgroupHierarchy1()){

							_userDefineparcelnum=_userDefineparcelnum+objProjectArea.getLaSpatialunitgroupHierarchy1().getAreaCode() ;
						}

						if(null!=objProjectArea && null!=objProjectArea.getLaSpatialunitgroupHierarchy2()){

							_userDefineparcelnum=_userDefineparcelnum+objProjectArea.getLaSpatialunitgroupHierarchy2().getAreaCode() ;
						}

						if(null!=objProjectArea && null!=objProjectArea.getLaSpatialunitgroupHierarchy3()){

							_userDefineparcelnum=_userDefineparcelnum+objProjectArea.getLaSpatialunitgroupHierarchy3().getAreaCode() ;

						}
						_userDefineparcelnum=_userDefineparcelnum+spatialUnit.getLandid();

						spatialUnit.setUdparcelno(_userDefineparcelnum);

						claimBasicService.saveClaimBasicDAO(spatialUnit);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


			int i= workflowActionService.actionRegister(land, userid,workflowId,comments);
			if(i>0){
				
				ClaimBasic spatialUnit = spatialUnitService.getClaimsBasicByLandId(land).get(0);
				
				LaExtRegistrationLandShareType objLaExtRegistrationLandShareType = new LaExtRegistrationLandShareType();
				objLaExtRegistrationLandShareType.setLandid(spatialUnit.getLandid());
				objLaExtRegistrationLandShareType.setLandsharetypeid((long)spatialUnit.getLaRightLandsharetype().getLandsharetypeid());
				objLaExtRegistrationLandShareType.setIsactive(true);
				objLaExtRegistrationLandShareType.setCreateddate(new Date());
				objLaExtRegistrationLandShareType.setCreatedby(userid.intValue());
				try{
				laExtRegistrationLandShareTypeService.addLaExtRegistrationLandShareType(objLaExtRegistrationLandShareType);
				}catch(Exception e){
					e.printStackTrace();
					return 0;
				}
			}
			return 1;
			

		}
	
	 
	    @RequestMapping(value = "/viewer/landrecords/action/verify/{land}/{workflowId}", method = RequestMethod.POST)
		@ResponseBody
		public Integer actionVerification(@PathVariable Long land, @PathVariable Integer workflowId, Principal principal,HttpServletRequest request, HttpServletResponse response) {

			
			
			String comments="";
			try {
				comments = ServletRequestUtils.getRequiredStringParameter(request, "commentsStatusWorkflow");
			} catch (ServletRequestBindingException e) {
				
			}

			String username = principal.getName();
			User user = userService.findByUniqueName(username);
			long userid = user.getId();
			return workflowActionService.actionVerification(land, userid,workflowId,comments);
			

		}
	 
	    @RequestMapping(value = "/viewer/landrecords/action/delete/{land}/{workflowId}", method = RequestMethod.POST)
		@ResponseBody
		public Integer actionDelete(@PathVariable Long land, @PathVariable Integer workflowId, Principal principal,HttpServletRequest request, HttpServletResponse response) {

			
			
			String comments="";
			try {
				comments = ServletRequestUtils.getRequiredStringParameter(request, "commentsStatusWorkflow");
			} catch (ServletRequestBindingException e) {
				
			}

			String username = principal.getName();
			User user = userService.findByUniqueName(username);
			long userid = user.getId();
			return workflowActionService.actiondelete(land, userid,workflowId,comments);
			

		}
	    
	    
	    @RequestMapping(value = "/viewer/landrecords/workflow/comment/{land}", method = RequestMethod.POST)
		@ResponseBody
		public List<WorkflowStatusHistory> getWorkflowStatusHistory(@PathVariable Long land)
		{
	    	List<WorkflowStatusHistory> lstWorkflowStatusHistory= new ArrayList<WorkflowStatusHistory>();
	    	lstWorkflowStatusHistory =workflowStatusHistoryDAO.getWorkflowStatusHistoryBylandId(land);
	    	if(lstWorkflowStatusHistory.size()>0)
	    	{
	    	
	    		for(WorkflowStatusHistory objWorkflowStatusHistory :lstWorkflowStatusHistory)
	    		{
	    			
	    			User objuser=userService.findUserByUserId(objWorkflowStatusHistory.getUserid());
	    			objWorkflowStatusHistory.setUser(objuser);
	    		}
	    		
	    	}
	    	return lstWorkflowStatusHistory;
	    	
		}
	
	    
	    @RequestMapping(value = "/viewer/landrecords/action/getUserParcel/{land}", method = RequestMethod.GET)
	  		@ResponseBody
	  		public UserParcel getUserParcel(@PathVariable Long land, Principal principal,HttpServletRequest request, HttpServletResponse response) {
	  	    	
	  	    	UserParcel objUserParcel = new UserParcel();
	  	    	
	  	   	 try {
	  				ClaimBasic spatialUnit = spatialUnitService.getClaimsBasicByLandId(land).get(0);
	  				 if(null!=spatialUnit){
	  					 
	  					 ProjectArea objProjectArea= projectAreaService.findProjectAreaByProjectId(spatialUnit.getProjectnameid()); 
	  					 
	  					 if(null!=objProjectArea && null!=objProjectArea.getLaSpatialunitgroupHierarchy1()){
	  						 
	  						 objUserParcel.setHierarchy1(objProjectArea.getLaSpatialunitgroupHierarchy1().getAreaCode());
	  					 }
	  					 
	  					 if(null!=objProjectArea && null!=objProjectArea.getLaSpatialunitgroupHierarchy2()){
	  						 
	  						 objUserParcel.setHierarchy2(objProjectArea.getLaSpatialunitgroupHierarchy2().getAreaCode());
	  					 }
	  					 
	  				    if(null!=objProjectArea && null!=objProjectArea.getLaSpatialunitgroupHierarchy3()){
	  						 
	  				    	 objUserParcel.setHierarchy3(objProjectArea.getLaSpatialunitgroupHierarchy3().getAreaCode());
	  					
	  				    }
	  				    
	  				    if(spatialUnit.getUdparcelno()==""){
	  				    objUserParcel.setLandid(spatialUnit.getLandid());
	  				    }else{
	  				    
	  				    	String str=	spatialUnit.getUdparcelno();
	  				    	StringBuilder sb = new StringBuilder();
	  				    	for (int i = str.length() - 1; i >= 0; i --) {
	  				    	    char c = str.charAt(i);
	  				    	    if (Character.isDigit(c)) {
	  				    	        sb.insert(0, c);
	  				    	    } else {
	  				    	        break;
	  				    	    }
	  				    	}
	  				    	String result = sb.toString();
	  				    	 objUserParcel.setLandid(Long.parseLong(result)); 
	  				    
	  				    }

	  				    
	  					 
	  				 }
	  			} catch (Exception e) {
	  				// TODO Auto-generated catch block
	  				e.printStackTrace();
	  			}
	  	   	 
	  	   	 
	  	    	return objUserParcel;
	  	    	
	  	    }

	  	   
	  		 @RequestMapping(value = "/viewer/landrecords/action/savenewparcel/{land}", method = RequestMethod.POST)
	  			@ResponseBody
	  			public boolean updateParcel(@PathVariable Long land, Principal principal,HttpServletRequest request, HttpServletResponse response) {

	  				
	  				
	  				String hierarchy1="";
	  				String hierarchy2="";
	  				String hierarchy3="";
	  				String parcelId="";
	  				
	  				String userDefineParcel="";
	  				try {
	  					hierarchy1 = ServletRequestUtils.getRequiredStringParameter(request, "hierarchy1");
	  					userDefineParcel=userDefineParcel+hierarchy1;
	  				} catch (ServletRequestBindingException e) {
	  					
	  				}
	  				
	  			
	  				try {
	  					hierarchy2 = ServletRequestUtils.getRequiredStringParameter(request, "hierarchy1");
	  					userDefineParcel=userDefineParcel+hierarchy2;
	  				} catch (ServletRequestBindingException e) {
	  					
	  				}

	  				
	  				
	  				try {
	  					hierarchy3 = ServletRequestUtils.getRequiredStringParameter(request, "hierarchy1");
	  					userDefineParcel=userDefineParcel+hierarchy3;
	  				} catch (ServletRequestBindingException e) {
	  					
	  				}

	  				try {
	  					parcelId = ServletRequestUtils.getRequiredStringParameter(request, "parcelId");
	  					userDefineParcel=userDefineParcel+parcelId;
	  				} catch (ServletRequestBindingException e) {
	  					
	  				}
	  				
	  				
	  				try{
	  				 ClaimBasic spatialUnit = spatialUnitService.getClaimsBasicByLandId(land).get(0);
	  				 if(null!=spatialUnit){
	  					 spatialUnit.setUdparcelno(userDefineParcel);
	  					 claimBasicService.saveClaimBasicDAO(spatialUnit);
	  				 }
	  				
	  				}catch(Exception e){
	  					e.printStackTrace();
	  					return false;
	  				}
	  				 
	  				
	  				
	  				return true;

	  			}
	  		 
}
