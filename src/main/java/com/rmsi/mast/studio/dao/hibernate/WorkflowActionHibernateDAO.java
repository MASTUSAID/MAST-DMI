package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.WorkflowActionDAO;
import com.rmsi.mast.studio.domain.WorkflowActionmapping;


@Repository
public class WorkflowActionHibernateDAO extends GenericHibernateDAO<WorkflowActionmapping, Long> implements  WorkflowActionDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkflowActionmapping> getWorkflowActionmapping(Integer workflowid, Integer roleid,Integer worflowdefId) {

		List<WorkflowActionmapping> lstWorkflowActionmapping= new ArrayList<WorkflowActionmapping>();
		String queryString = "select w from WorkflowActionmapping w   where w.isactive= true  and w.workflow.workflowid = :workflowid  and w.roleid =:roleid  and w.workflow.laExtWorkflowdef.workflowdefid=:workflowdef  order by w.worder";
		
		try {
			lstWorkflowActionmapping= getEntityManager().createQuery(queryString).setParameter("workflowid", workflowid).setParameter("roleid", roleid). setParameter("workflowdef", worflowdefId).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return lstWorkflowActionmapping;
		
	} 

	
}
