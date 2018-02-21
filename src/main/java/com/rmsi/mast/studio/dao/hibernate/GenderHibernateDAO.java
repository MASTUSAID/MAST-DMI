
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.dao.GenderDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Gender;


@Repository
public class GenderHibernateDAO extends GenericHibernateDAO<Gender, Long>
		implements GenderDAO {

	@Override
	public List<Gender> getAllGender() {
		
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
