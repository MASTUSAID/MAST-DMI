

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.PrintTemplateDAO;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.domain.Printtemplate;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.User;

@Repository
public class PrintTemplateHibernateDAO extends GenericHibernateDAO<Printtemplate, Long> implements
		PrintTemplateDAO {

	@SuppressWarnings("unchecked")
	public List<Printtemplate> findByProjectName(String name) {
		
		List<Printtemplate> printtemplate =
			getEntityManager().createQuery("Select pt from Printtemplate pt where pt.projectBean.name = :name").setParameter("name", name).getResultList();
		
		return printtemplate;
	}

	
	
	
}
