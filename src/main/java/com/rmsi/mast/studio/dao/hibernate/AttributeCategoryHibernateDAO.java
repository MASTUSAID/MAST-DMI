package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.AttributeCategoryDAO;
import com.rmsi.mast.studio.domain.AttributeCategory;

@Repository
public class AttributeCategoryHibernateDAO extends GenericHibernateDAO<AttributeCategory, Long> implements 	AttributeCategoryDAO {
    private static final Logger logger = Logger.getLogger(AttributeCategoryHibernateDAO.class);

	@Override
	public List<AttributeCategory> findAttributeCategoryByTypeId(Integer id) {
		
		try {
			@SuppressWarnings("unchecked")
			
			
			List<AttributeCategory> lstAttributeCat =	getEntityManager().createQuery("Select u from AttributeCategory u where   u.categorytype.categorytypeid = :id").setParameter("id", id ).getResultList();

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
	public List<AttributeCategory> findAllAttributeCategory() {
		
		try {
			@SuppressWarnings("unchecked")
			
			
			List<AttributeCategory> lstAttributeCat =	getEntityManager().createQuery("Select u from AttributeCategory u ORDER BY u.categorydisplayorder").getResultList();

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
