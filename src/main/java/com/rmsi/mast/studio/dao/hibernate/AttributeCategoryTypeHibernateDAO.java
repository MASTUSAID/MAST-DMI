package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.AttributeCategoryTypeDAO;
import com.rmsi.mast.studio.domain.AttributeCategoryType;

@Repository
public class AttributeCategoryTypeHibernateDAO extends GenericHibernateDAO<AttributeCategoryType, Integer> implements  AttributeCategoryTypeDAO {
    private static final Logger logger = Logger.getLogger(AttributeCategoryTypeHibernateDAO.class);

	@Override
	public List<AttributeCategoryType> getAllAttributeCategoryType() {
	
		try {
			@SuppressWarnings("unchecked")
			List<AttributeCategoryType> lstAttributeCat =
					getEntityManager().createQuery("Select u from AttributeCategoryType u where u.isactive =true").getResultList();

			if(lstAttributeCat.size() > 0)
			{
				return lstAttributeCat;
			}
			else
			{	return null;
			}
	
		}catch(Exception e)
		{
			logger.error(e);
			return null;
		}
    
		
	}

	@Override
	public List<AttributeCategoryType> getAllAttributeCategoryTypeById(Integer id) {

		try {
			@SuppressWarnings("unchecked")
			List<AttributeCategoryType> lstAttributeCat =
					getEntityManager().createQuery("Select u from AttributeCategoryType u where u.isactive =true  and   u.categorytypeid = :id").setParameter("id", id ).getResultList();

			if(lstAttributeCat.size() > 0)
			{
				return lstAttributeCat;
			}
			else
			{	return null;
			}
	
		}catch(Exception e)
		{
			logger.error(e);
			return null;
		}
		
		
	}

}
