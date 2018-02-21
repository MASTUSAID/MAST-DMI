package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.WorkflowStatusHistory;

public interface WorkflowStatusHistoryDAO {

	
	List<WorkflowStatusHistory> getWorkflowStatusHistoryBylandId(long landid);
	WorkflowStatusHistory getWorkflowStatusHistoryById(Integer landworkflowhistoryid);
	void addWorkflowStatusHistory(WorkflowStatusHistory workflowStatusHistory);
}
