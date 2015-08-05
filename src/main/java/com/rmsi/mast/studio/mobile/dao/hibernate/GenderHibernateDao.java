/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.mobile.dao.GenderDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class GenderHibernateDao extends GenericHibernateDAO<Gender, Integer>
		implements GenderDao {
	private static final Logger logger = Logger.getLogger(GenderHibernateDao.class);

	@Override
	public Gender getGenderById(long genderId) {

		try {

			String query = "select gn.* from gender gn inner join attribute_options ao	"
					+ "on ao.parent_id = gn.gender_id where ao.id = "
					+ genderId;

			@SuppressWarnings("unchecked")
			List<Gender> gender = getEntityManager().createNativeQuery(query,
					Gender.class).getResultList();

			if (gender != null && gender.size() > 0) {
				return gender.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

}
