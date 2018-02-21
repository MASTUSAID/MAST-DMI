package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.WorkflowDAO;
import com.rmsi.mast.studio.domain.Workflow;
import com.rmsi.mast.studio.service.WorkflowService;


@Service
public class WorkflowServiceImpl  implements WorkflowService{

	@Autowired
	WorkflowDAO workflowDAO;
	
	@Override
	public List<Workflow> getAllWorkflow() {
		// TODO Auto-generated method stub
		return workflowDAO.getAllWorkflow();
	}

	@Override
	public Workflow getWorkflowByid(Integer id) {
		// TODO Auto-generated method stub
		return workflowDAO.getWorkflowByid(id);
	}

}
