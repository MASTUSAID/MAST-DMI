

package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.RoleDAO;
import com.rmsi.mast.studio.domain.Role;

/**
 * @author Aparesh.Chakraborty
 * 
 */

@Repository
public class RoleHibernateDAO extends GenericHibernateDAO<Role, Long> implements
		RoleDAO {
	private static final Logger logger = Logger.getLogger(RoleHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public Role findByName(String name) {

		List<Role> role = getEntityManager()
				.createQuery("Select r from Role r where r.name = :name")
				.setParameter("name", name).getResultList();

		if (role.size() > 0)
			return role.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteRole(String name) {

		try {
			Query query = getEntityManager().createQuery(
					"Delete from Role r where r.name =:name").setParameter(
					"name", name);

			int count = query.executeUpdate();
			System.out.println("Delete count ROLE: " + count);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}

	@Override
	public List<Role> findAll(int roleId) 
	{
		List<Role> roles = new ArrayList<Role>();
		try {
			
			if(roleId!=1)
			{ 
				String query = "Select r from Role r where r.id not in (1)";
				roles=getEntityManager().createQuery(query).getResultList();
			}
			else
			{
				roles = findAll();
			}
			
			if(roles.size() > 0){
				return roles;
			}		
			else
			{
				return null;
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}
