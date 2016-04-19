

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.RoleModuleDAO;
import com.rmsi.mast.studio.domain.RoleModule;



@Repository
public class RoleModuleHibernateDAO extends GenericHibernateDAO<RoleModule, Long>
		implements RoleModuleDAO {
	private static final Logger logger = Logger.getLogger(RoleModuleHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public boolean deleteRoleModuleByRole(String role) {
		System.out.println(">>>>>>Deleteed from role-module: " + role);
		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from RoleModule rm where rm.roleBean.name =:name")
					.setParameter("name", role);
			
			int count = query.executeUpdate();
			System.out.println("Delete RoleModule count: " + count);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error(e);
			return false;
	}
	}

	
}
