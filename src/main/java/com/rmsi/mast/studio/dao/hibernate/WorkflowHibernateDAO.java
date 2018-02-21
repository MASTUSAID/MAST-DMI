package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.WorkflowDAO;
import com.rmsi.mast.studio.domain.Workflow;


@Repository
public class WorkflowHibernateDAO   extends GenericHibernateDAO<Workflow, Long> implements  WorkflowDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<Workflow> getAllWorkflow() {
	
		List<Workflow> lstWorkflow= new ArrayList<Workflow>();
		String queryString = "select w from Workflow w   where w.isactive= true  and w.workflowid!=6 order by w.workfloworder";
		
		try {
			lstWorkflow= getEntityManager().createQuery(queryString).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return lstWorkflow;

	
	}

	@Override
	public Workflow getWorkflowByid(Integer id) {
	
		try {
			@SuppressWarnings("unchecked")
			List<Workflow> lstWorkflow =
					getEntityManager().createQuery("select w from Workflow w   where w.isactive= true and w.workflowid = :id" ).setParameter("id", id).getResultList();

			if(lstWorkflow.size()>0){
				return lstWorkflow.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
