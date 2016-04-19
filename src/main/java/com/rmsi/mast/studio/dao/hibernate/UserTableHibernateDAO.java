
package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.UsertableDAO;
import com.rmsi.mast.studio.domain.fetch.Usertable;


@Repository
public class UserTableHibernateDAO extends GenericHibernateDAO<Usertable, Integer>
		implements UsertableDAO {
	
}
