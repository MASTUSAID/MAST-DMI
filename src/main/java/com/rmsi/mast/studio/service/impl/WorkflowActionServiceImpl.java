package com.rmsi.mast.studio.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.LASpatialUnitDAO;
import com.rmsi.mast.studio.dao.WorkflowActionDAO;
import com.rmsi.mast.studio.dao.WorkflowDAO;
import com.rmsi.mast.studio.dao.WorkflowStatusHistoryDAO;
import com.rmsi.mast.studio.domain.WorkflowActionmapping;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;
import com.rmsi.mast.studio.service.WorkflowActionService;
import com.rmsi.mast.viewer.dao.StatusDAO;

@Service
public class WorkflowActionServiceImpl implements WorkflowActionService {

	
	
	@Autowired 
	WorkflowActionDAO WorkflowActionDAO;
	
	@Autowired
	WorkflowStatusHistoryDAO workflowStatusHistoryDAO;
	
	
	@Autowired
	StatusDAO statusDAO;
	
	
	@Autowired
	WorkflowDAO  workflowDAO;
	
	@Autowired
	LASpatialUnitDAO spatialUnitDAO;
	
	
	@Override
	public List<WorkflowActionmapping> getWorkflowActionmapping(Integer workflowid, Integer roleid,Integer worflowdefId) {
	
		return WorkflowActionDAO.getWorkflowActionmapping(workflowid, roleid,worflowdefId);
	}

	@Override
	public Integer actionApprove(Long id, long userid, Integer workflowId,String comments) {
		
       boolean historyUpdate=true;
		int appstatus =0;
		int workstatus =0;
		try {

			WorkflowStatusHistory sunitHistory=new WorkflowStatusHistory();
			sunitHistory.setComments(comments);
			sunitHistory.setCreatedby((int)userid);
			sunitHistory.setIsactive(true);
			sunitHistory.setLandid(id);
			sunitHistory.setCreateddate(new Date());
			sunitHistory.setUserid((int)userid);
			sunitHistory.setStatuschangedate(new Date());

			if(workflowId==1){
				sunitHistory.setStatus(statusDAO.getStatusById(4));
				sunitHistory.setWorkflow(workflowDAO.getWorkflowByid(1));
				appstatus=4;
				workstatus=2;
			}else if(workflowId==2)
			{
				sunitHistory.setStatus(statusDAO.getStatusById(4));
				sunitHistory.setWorkflow(workflowDAO.getWorkflowByid(2));
				appstatus=4;
				workstatus=3;
				
			}else if(workflowId==3)
			{
				sunitHistory.setStatus(statusDAO.getStatusById(4));
				sunitHistory.setWorkflow(workflowDAO.getWorkflowByid(3));
				appstatus=2;
				workstatus=4;
				
			}
			
			
			workflowStatusHistoryDAO.addWorkflowStatusHistory(sunitHistory);
			
		} catch (Exception e) {
			historyUpdate=false;
		}               
		
		
		if(historyUpdate)
		{
			
			boolean flag=spatialUnitDAO.updateSpatialUnit(id, appstatus, workstatus);
			if(flag){
				return 1;
			}
			
		}
		
		
		return null;
	}

	@Override
	public Integer actionReject(Long id, long userid, Integer workflowId,String comments) {
		 boolean historyUpdate=true;
			int appstatus =0;
			int workstatus =0;
			try {

				WorkflowStatusHistory sunitHistory=new WorkflowStatusHistory();
				sunitHistory.setComments(comments);
				sunitHistory.setCreatedby((int)userid);
				sunitHistory.setIsactive(true);
				sunitHistory.setLandid(id);
				sunitHistory.setCreateddate(new Date());
				sunitHistory.setUserid((int)userid);
				sunitHistory.setStatuschangedate(new Date());

				if(workflowId==1)
				{
					sunitHistory.setStatus(statusDAO.getStatusById(3));
					sunitHistory.setWorkflow(workflowDAO.getWorkflowByid(2));
					appstatus=3;
					workstatus=5;
					
				}
				workflowStatusHistoryDAO.addWorkflowStatusHistory(sunitHistory);
				
			} catch (Exception e) {
				historyUpdate=false;
			}               
			
			
			if(historyUpdate)
			{
				
				boolean flag=spatialUnitDAO.updateSpatialUnit(id, appstatus, workstatus);
				if(flag){
					return 1;
				}
				
			}
			
			
			return null;
		
	}

	@Override
	public Integer actionRegister(Long id, long userid, Integer workflowId,String comments) {
		
		 boolean historyUpdate=true;
			int appstatus =0;
			int workstatus =0;
			try {

				WorkflowStatusHistory sunitHistory=new WorkflowStatusHistory();
				sunitHistory.setComments(comments);
				sunitHistory.setCreatedby((int)userid);
				sunitHistory.setIsactive(true);
				sunitHistory.setLandid(id);
				sunitHistory.setCreateddate(new Date());
				sunitHistory.setUserid((int)userid);
				sunitHistory.setStatuschangedate(new Date());

				if(workflowId==4)
				{
					sunitHistory.setStatus(statusDAO.getStatusById(2));
					sunitHistory.setWorkflow(workflowDAO.getWorkflowByid(4));
					appstatus=5;
					workstatus=6;
					
				}
				workflowStatusHistoryDAO.addWorkflowStatusHistory(sunitHistory);
				
			} catch (Exception e) {
				historyUpdate=false;
			}               
			
			
			if(historyUpdate)
			{
				
				boolean flag=spatialUnitDAO.updateSpatialUnit(id, appstatus, workstatus);
				if(flag){
					return 1;
				}
				
			}
			
			
			return null;
		
	}

	@Override
	public Integer actionVerification(Long id, long userid, Integer workflowId,String comments) {
		
		 boolean historyUpdate=true;
			int appstatus =0;
			int workstatus =0;
			try {

				WorkflowStatusHistory sunitHistory=new WorkflowStatusHistory();
				sunitHistory.setComments(comments);
				sunitHistory.setCreatedby((int)userid);
				sunitHistory.setIsactive(true);
				sunitHistory.setLandid(id);
				sunitHistory.setCreateddate(new Date());
				sunitHistory.setUserid((int)userid);
				sunitHistory.setStatuschangedate(new Date());

				if(workflowId==3)
				{
					sunitHistory.setStatus(statusDAO.getStatusById(4));
					sunitHistory.setWorkflow(workflowDAO.getWorkflowByid(3));
					appstatus=4;
					workstatus=4;
				}
				workflowStatusHistoryDAO.addWorkflowStatusHistory(sunitHistory);
				
			} catch (Exception e) {
				historyUpdate=false;
			}               
			
			
			if(historyUpdate)
			{
				
				boolean flag=spatialUnitDAO.updateSpatialUnit(id, appstatus, workstatus);
				if(flag){
					return 1;
				}
				
			}
			
			
			return null;
		
	}

	@Override
	public Integer actiondelete(Long id, long userid, Integer workflowId,String comments) {
		 boolean historyUpdate=true;
		try {

			WorkflowStatusHistory sunitHistory=new WorkflowStatusHistory();
			sunitHistory.setComments(comments);
			sunitHistory.setCreatedby((int)userid);
			sunitHistory.setIsactive(true);
			sunitHistory.setLandid(id);
			sunitHistory.setCreateddate(new Date());
			sunitHistory.setUserid((int)userid);
			sunitHistory.setStatuschangedate(new Date());
			sunitHistory.setStatus(statusDAO.getStatusById(3));
			sunitHistory.setWorkflow(workflowDAO.getWorkflowByid(5));
			
			workflowStatusHistoryDAO.addWorkflowStatusHistory(sunitHistory);
			
		} catch (Exception e) {
			historyUpdate=false;
		}               
		
		
		if(historyUpdate)
		{
			
			boolean flag=spatialUnitDAO.deleteSpatialUnit(id, 3, 5);
			if(flag){
				return 1;
			}
			
		}
		
		
		return null;
		
	}

	
	
}
