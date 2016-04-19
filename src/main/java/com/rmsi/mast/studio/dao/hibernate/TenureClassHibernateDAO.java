
package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.TenureClassDAO;
import com.rmsi.mast.studio.domain.TenureClass;


@Repository
public class TenureClassHibernateDAO extends GenericHibernateDAO<TenureClass, Integer>
		implements TenureClassDAO {
	
}
