
package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.MaritalStatusDAO;
import com.rmsi.mast.studio.domain.MaritalStatus;


@Repository
public class MaritalStatusHibernateDAO extends GenericHibernateDAO<MaritalStatus, Integer>
		implements MaritalStatusDAO {
	
	
	
}
