package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.viewer.dao.StatusDAO;

@Repository
public class StatusHibernateDAO extends GenericHibernateDAO<Status, Integer>
implements StatusDAO{

	Logger logger = Logger.getLogger(StatusHibernateDAO.class);
	@Override
	public Status getStatusById(int statusId) {
		
		try {
			String query = "select s from Status s where s.workflowStatusId = :statusId";

			@SuppressWarnings("unchecked")
			List<Status> status = getEntityManager().createQuery(query)
					.setParameter("statusId", statusId).getResultList();

			if (status != null && status.size() > 0) {
				return status.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		
		return null;
	}

}
