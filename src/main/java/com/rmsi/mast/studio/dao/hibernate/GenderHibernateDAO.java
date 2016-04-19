
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.dao.GenderDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Gender;


@Repository
public class GenderHibernateDAO extends GenericHibernateDAO<Gender, Long>
		implements GenderDAO {
	
}
