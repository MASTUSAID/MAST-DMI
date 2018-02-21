package com.rmsi.mast.studio.mobile.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.CustomAttributes;
import com.rmsi.mast.studio.mobile.dao.CustomAttributesDAO;


@Repository
public class CustomAttributesHibernateDAO extends GenericHibernateDAO<CustomAttributes, Integer> implements CustomAttributesDAO{

	
	private static final Logger logger = Logger.getLogger(CustomAttributesHibernateDAO.class);
	@Override
	public CustomAttributes addResourceCustomAttributeValues(
			CustomAttributes customAttributes) {
		 try {
	            return makePersistent(customAttributes);

	        } catch (Exception ex) {
	            System.out.println("Exception while adding data...." + ex);
	            logger.error(ex);
	            throw ex;
	        }
	}
	
}
