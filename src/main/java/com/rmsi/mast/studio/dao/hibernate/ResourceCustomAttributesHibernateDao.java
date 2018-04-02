package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ResourceCustomAttributesDAO;
import com.rmsi.mast.studio.domain.ResourceCustomAttributes;

@Repository
public class ResourceCustomAttributesHibernateDao extends GenericHibernateDAO<ResourceCustomAttributes, Integer> implements
ResourceCustomAttributesDAO{

	@Override
	public List<ResourceCustomAttributes> getByProjectId(Integer Id) {
		 Query query = getEntityManager().createQuery("Select d from ResourceCustomAttributes d where d.projectid = :id");
		 List<ResourceCustomAttributes> editAttrib = query.setParameter("id", Id).getResultList();
         if(editAttrib != null){
         
        	 return editAttrib;
         }
         else{
             return null;

         	}
	}

	@Override
	public ResourceCustomAttributes getByCustomattributeId(Integer Id) {
		 Query query = getEntityManager().createQuery("Select d from ResourceCustomAttributes d where d.customattributeid = :id");
		 ResourceCustomAttributes editAttrib = (ResourceCustomAttributes)query.setParameter("id", Id).getSingleResult();
         if(editAttrib != null){
         
        	 return editAttrib;
         }
         else{
             return null;

         	}
	}

	@Override
	public List<Object[]> getResourceCustomAttributeValuesAndDatatypeBylandId(
			Integer projectId, Integer Id) {
		try{
			
		

		String query = "Select AM.fieldname, RA.customattributeid "+
						"from la_ext_resource_custom_attributevalue RA,la_ext_resource_custom_attribute AM,la_ext_attributecategory AC "+
						"Where RA.customattributeid=AM.customattributeid And AM.AttributeCategoryID=AC.AttributeCategoryID "+
						"and RA.projectid =" +projectId +" And RA.LandID="+Id+" order by AM.listing";
		
		
		
		List<Object[]> arrObject = getEntityManager().createNativeQuery(query).getResultList();
		if(arrObject.size()>0){
			
			return arrObject;
		}
		
		return null;
		
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	}

	

}
