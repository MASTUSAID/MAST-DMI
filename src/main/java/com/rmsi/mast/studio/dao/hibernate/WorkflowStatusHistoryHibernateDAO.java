package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.WorkflowStatusHistoryDAO;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;


@Repository
public class WorkflowStatusHistoryHibernateDAO extends GenericHibernateDAO<WorkflowStatusHistory, Long> implements WorkflowStatusHistoryDAO   {

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkflowStatusHistory> getWorkflowStatusHistoryBylandId(long landid) {
		
		List<WorkflowStatusHistory> lstWorkflowStatusHistory = new ArrayList<WorkflowStatusHistory>();

	try {
		lstWorkflowStatusHistory =getEntityManager().createQuery("Select w from WorkflowStatusHistory w where w.landid= :Id").setParameter("Id", landid).getResultList();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return lstWorkflowStatusHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkflowStatusHistory getWorkflowStatusHistoryById(Integer id) {
		List<WorkflowStatusHistory> lstWorkflowStatusHistory = new ArrayList<WorkflowStatusHistory>();


		try {
			lstWorkflowStatusHistory =getEntityManager().createQuery("Select w from WorkflowStatusHistory w where w.landworkflowhistoryid= :Id").setParameter("Id", id).getResultList();
			if(lstWorkflowStatusHistory.size()>0)
			{
				return lstWorkflowStatusHistory.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return null;
	
	}

	@Override
	public void addWorkflowStatusHistory(WorkflowStatusHistory workflowStatusHistory) {

		try {

			makePersistent(workflowStatusHistory);

		} catch (Exception ex) {
			throw ex;
		}

		
		
	}

	
	
}
