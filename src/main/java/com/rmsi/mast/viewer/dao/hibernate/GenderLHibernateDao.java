package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.viewer.dao.GenderLDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class GenderLHibernateDao extends GenericHibernateDAO<Gender, Integer>
implements GenderLDao{

	@Override
	public Gender getGenderById(long genderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gender> getGenderDetails() {
		try {
			Query query = getEntityManager().createQuery(
			        "select gen from Gender gen where isactive = true");
			List<Gender> lstGender = query.getResultList();

			return lstGender;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	
}
