package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.viewer.dao.MaritalStatusaDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class MaritalStatusaHibernateDao extends GenericHibernateDAO<MaritalStatus, Integer> implements MaritalStatusaDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<MaritalStatus> getMaritalStatus() {
		try {
			Query query = getEntityManager().createQuery(
			        "select lpm from MaritalStatus lpm where isactive = true");
			List<MaritalStatus> lstMaritalStatus1 = query.getResultList();

			return lstMaritalStatus1;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		

}

	@SuppressWarnings("unchecked")
	@Override
	public MaritalStatus getMaritalStatusByID(Integer id) {
		try {
			Query query = getEntityManager().createQuery(
			        "select lpm from MaritalStatus lpm where lpm.isactive = true and lpm.maritalstatusid =:Id ");
			List<MaritalStatus> lstMaritalStatus1 = query.setParameter("Id", id).getResultList();

			return lstMaritalStatus1.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
