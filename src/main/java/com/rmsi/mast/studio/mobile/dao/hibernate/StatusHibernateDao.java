/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.mobile.dao.StatusDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class StatusHibernateDao extends GenericHibernateDAO<Status, Integer>
		implements StatusDao {
	private static final Logger logger = Logger.getLogger(StatusHibernateDao.class);

	@Override
	public Status getStatusById(Integer statusId) {

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
