package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.mobile.dao.UserDataDao;

/**
 * @author shruti.thakur
 * 
 */
@Repository
public class UserDataHibernateDAO extends GenericHibernateDAO<User, Long>
		implements UserDataDao {
	

	@Override
	public User authenticateMobileUser(String email) {

		String query = "select u from User u where u.email = :email";

		@SuppressWarnings("unchecked")
		List<User> user = getEntityManager().createQuery(query)
				.setParameter("email", email).getResultList();

		if (user != null && user.size() > 0) {
			return user.get(0);
		}

		return null;
	}

	@Override
	public User getUserByUserId(int userId){

		String query = "select p from User p where p.id = :userId";

		@SuppressWarnings("unchecked")
		List<User> user = getEntityManager().createQuery(query)
				.setParameter("userId", userId).getResultList();

		if (user != null && user.size() > 0) {
			return user.get(0);
		}else{
			return null;
		}
	}

}
