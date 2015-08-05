/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.mobile.dao.MaritalStatusDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class MaritalStatusHibernateDao extends
		GenericHibernateDAO<MaritalStatus, Integer> implements MaritalStatusDao {
	private static final Logger logger = Logger.getLogger(MaritalStatusHibernateDao.class);

	@Override
	public MaritalStatus getMaritalStatusById(int maritalStatusId) {

		try {
			String query = "select ms.* from marital_status ms inner join attribute_options ao	"
					+ "on ao.parent_id = ms.maritalstatus_id where ao.id = "
					+ maritalStatusId;
			@SuppressWarnings("unchecked")
			List<MaritalStatus> maritalStatus = getEntityManager()
					.createNativeQuery(query, MaritalStatus.class)
					.getResultList();

			if (maritalStatus != null && maritalStatus.size() > 0) {
				return maritalStatus.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}

		return null;
	}
}