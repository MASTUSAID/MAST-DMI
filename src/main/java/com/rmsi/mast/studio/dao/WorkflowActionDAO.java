package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.WorkflowActionmapping;

public interface WorkflowActionDAO {

	public List<WorkflowActionmapping> getWorkflowActionmapping(Integer workflowid,Integer roleid,Integer worflowdefId);
	
	
	
}
