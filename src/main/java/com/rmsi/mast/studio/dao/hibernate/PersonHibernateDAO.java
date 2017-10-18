package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;
import com.rmsi.mast.studio.dao.PersonDAO;
import com.rmsi.mast.studio.domain.Person;

@Repository
public class PersonHibernateDAO extends GenericHibernateDAO<Person, Long> implements PersonDAO {
	
}
