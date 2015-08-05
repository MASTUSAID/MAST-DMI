/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;
import com.rmsi.mast.studio.mobile.dao.WorkflowStatusHistoryDao;

/**
 * @author shruti.thakur
 *
 */
@Repository
public class WorkflowStatusHistoryHibernateDao extends
		GenericHibernateDAO<WorkflowStatusHistory, Long> implements
		WorkflowStatusHistoryDao {
	private static final Logger logger = Logger
			.getLogger(WorkflowStatusHistoryHibernateDao.class);

	@Override
	public void addWorkflowStatusHistory(
			WorkflowStatusHistory workflowStatusHistory) {

		try {

			makePersistent(workflowStatusHistory);

		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}

	}

}
