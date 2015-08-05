package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;





import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.PersonAdministrator;
import com.rmsi.mast.viewer.dao.PersonAdministratorDao;



@Repository
public class PersonAdministratorHibernateDAO extends GenericHibernateDAO<PersonAdministrator, Long>
implements PersonAdministratorDao {

	@Override
	public PersonAdministrator findAdminById(Long admiLong) {
	
		@SuppressWarnings("unchecked")
		List<PersonAdministrator> personlist =
				getEntityManager().createQuery("Select pa from PersonAdministrator pa where pa.active = true and pa.adminid = :adminid").setParameter("adminid", admiLong).getResultList();

		if(personlist.size() > 0)
			return personlist.get(0);
		else
			return null;
	}

	@Override
	public boolean deleteAdminById(Long id) {
		
		try {
			
			Query query = getEntityManager().createQuery("UPDATE PersonAdministrator pa SET pa.active = false  where pa.adminid = :adminid");
			
			query.setParameter("adminid",id);

			int rows = query.executeUpdate();
			
			if(rows>0)
			{
				return true;
			}
			else{
				return false;
}
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	

	}

	@Override
	public boolean addAdmin(Long adminId) {
		
		try {
			
			Query query = getEntityManager().createQuery("UPDATE PersonAdministrator pa SET pa.active = true  where pa.adminid = :adminid");
			
			query.setParameter("adminid",adminId);

			int rows = query.executeUpdate();
			
			if(rows>0)
			{
				return true;
			}
			else{
				return false;
}
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	

	}
	
	
}




