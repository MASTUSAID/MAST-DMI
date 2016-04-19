
package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ShareTypeDAO;
import com.rmsi.mast.studio.domain.ShareType;


@Repository
public class ShareTypeHibernateDAO extends GenericHibernateDAO<ShareType, Integer>
		implements ShareTypeDAO {
	
}
