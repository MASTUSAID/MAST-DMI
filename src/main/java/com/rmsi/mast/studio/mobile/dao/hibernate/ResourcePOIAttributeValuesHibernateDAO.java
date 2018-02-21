package com.rmsi.mast.studio.mobile.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ResourcePOIAttributeValues;
import com.rmsi.mast.studio.mobile.dao.ResourcePOIAttributeValuesDAO;

@Repository
public class ResourcePOIAttributeValuesHibernateDAO extends GenericHibernateDAO<ResourcePOIAttributeValues, Integer> implements ResourcePOIAttributeValuesDAO {

	@Override
	public ResourcePOIAttributeValues addResourcePOIAttributeValues(
			ResourcePOIAttributeValues resourceAttributevalues) {

        try {
            return makePersistent(resourceAttributevalues);

        } catch (Exception ex) {
            System.out.println("Exception while adding data...." + ex);
            throw ex;
        }
    }

}
