package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.viewer.dao.IDTypeDAO;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class IDTypeHibernateDAO extends GenericHibernateDAO<IdType, Integer>
implements IDTypeDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<IdType> getIDTypeDetails() {
		try {
			Query query = getEntityManager().createQuery("select idt from IdType idt where isactive = true");
			List<IdType> lstIdType = query.getResultList();
			return lstIdType;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IdType getIDTypeDetailsByID(Integer id) {
		
		try {
			Query query = getEntityManager().createQuery(
			        "select idt from IdType idt where idt.isactive = true and idt.identitytypeid =:Id ");
			List<IdType> lstIdType = query.setParameter("Id", id).getResultList();

			return lstIdType.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
