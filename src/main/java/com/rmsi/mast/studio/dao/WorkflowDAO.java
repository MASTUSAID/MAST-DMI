package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Workflow;

public interface WorkflowDAO {

	public List<Workflow> getAllWorkflow();
	public Workflow getWorkflowByid(Integer id);
}
