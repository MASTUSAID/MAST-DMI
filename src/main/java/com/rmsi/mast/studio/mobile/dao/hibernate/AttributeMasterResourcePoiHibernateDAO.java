package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeMasterResourcePOI;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ResourceCustomAttributes;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.mobile.dao.AttributeMasterResourcePoiDAO;

@Repository
public class AttributeMasterResourcePoiHibernateDAO extends GenericHibernateDAO<AttributeMasterResourcePOI, Integer> implements AttributeMasterResourcePoiDAO{

	@Override
	public AttributeMasterResourcePOI getPOIAttributteMasterById(
			Integer attributeId) {

		String query = "select a from AttributeMasterResourcePOI a where a.poiattributemasterid = :attributeId";

		@SuppressWarnings("unchecked")
		List<AttributeMasterResourcePOI> attributeMaster = getEntityManager()
				.createQuery(query).setParameter("attributeId", attributeId)
				.getResultList();

		if (attributeMaster.size() > 0) {
			return attributeMaster.get(0);
		}

		return null;
	}

	@Override
	public List<AttributeMasterResourcePOI> getAttributeMasterResourcePOIByProject(
			Integer projectId) {
		 Query query = getEntityManager().createQuery("Select d from AttributeMasterResourcePOI d where d.projectid = :id");
		 List<AttributeMasterResourcePOI> editAttrib = query.setParameter("id", projectId).getResultList();
        if(editAttrib != null){
        
       	 return editAttrib;
        }
        else{
            return null;

        	}
	}

}
