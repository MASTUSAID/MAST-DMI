/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;

/**
 * @author shruti.thakur
 *
 */
public interface WorkflowStatusHistoryDao extends
		GenericDAO<WorkflowStatusHistory, Long> {

	/**
	 * Will be used to store work flow status history
	 * 
	 * @param workflowStatuHsistory
	 */
	void addWorkflowStatusHistory(
			WorkflowStatusHistory workflowStatusHistory);
}
