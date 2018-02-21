package com.rmsi.mast.studio.service;

import java.util.List;

import com.rmsi.mast.studio.domain.Workflow;

public interface WorkflowService {

	
	public List<Workflow> getAllWorkflow();
	public Workflow getWorkflowByid(Integer id);
}
