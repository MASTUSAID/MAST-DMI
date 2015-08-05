
package com.rmsi.mast.studio.dao.hibernate;


import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.GroupTypeDAO;
import com.rmsi.mast.studio.domain.GroupType;


@Repository
public class GroupTypeHibernateDAO extends GenericHibernateDAO<GroupType, Integer>
		implements GroupTypeDAO {
	
}
