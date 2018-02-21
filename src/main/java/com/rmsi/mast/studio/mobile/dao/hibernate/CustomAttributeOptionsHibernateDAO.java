package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.domain.CustomAttributeOptions;
import com.rmsi.mast.studio.mobile.dao.CustomAttributeOptionsDAO;

@Repository
public class CustomAttributeOptionsHibernateDAO extends GenericHibernateDAO<CustomAttributeOptions, Integer> implements CustomAttributeOptionsDAO{

	@Override
	public List<CustomAttributeOptions> getAttributeOptions(Integer attributeId) {
		String query = "select a from CustomAttributeOptions a where a.customattributeid.customattributeid = :attributeId";

        try {
            @SuppressWarnings("unchecked")
            List<CustomAttributeOptions> attributeOptions = getEntityManager()
                    .createQuery(query)
                    .setParameter("attributeId",attributeId)
                    .getResultList();

            if (!attributeOptions.isEmpty()) {
                return attributeOptions;
            }
        } catch (Exception ex) {

        }
        return null;
	}

}
