package com.rmsi.mast.studio.dao.hibernate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ResourceLandClassificationMappingDAO;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.ResourceLandClassificationMapping;
import com.rmsi.mast.studio.mobile.dao.ResourceAttributeValuesDAO;
import com.rmsi.mast.studio.mobile.dao.hibernate.ResourceAttributeValuesHibernateDAO;


@Repository
public class ResourceLandClassificationMappingHibernateDAO extends GenericHibernateDAO<ResourceLandClassificationMapping, Integer> implements ResourceLandClassificationMappingDAO {

	 private static final Logger logger = Logger
	            .getLogger(ResourceLandClassificationMappingHibernateDAO.class);

	    @Override
	    public ResourceLandClassificationMapping addResourceLandClassifications (ResourceLandClassificationMapping resourceAttributevalues) {

	        try {
	            return makePersistent(resourceAttributevalues);

	        } catch (Exception ex) {
	            System.out.println("Exception while adding data...." + ex);
	            logger.error(ex);
	            throw ex;
	        }
	    }
}
