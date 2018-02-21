package com.rmsi.mast.studio.web.mvc;

import java.security.Principal;
import java.util.ArrayList;
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
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.domain.WorkflowActionmapping;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.service.WorkflowActionService;
import com.rmsi.mast.studio.service.WorkflowService;


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
			long userid = user.getId();
			return workflowActionService.actionRegister(land, userid,workflowId,comments);
			

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
	
}
