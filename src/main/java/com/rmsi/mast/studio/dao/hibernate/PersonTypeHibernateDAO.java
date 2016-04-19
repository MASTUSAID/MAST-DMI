
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.dao.PersonTypeDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.PersonType;


@Repository
public class PersonTypeHibernateDAO extends GenericHibernateDAO<PersonType, Long>
		implements PersonTypeDAO {
	
	
	
}
