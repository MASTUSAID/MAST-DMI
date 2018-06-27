package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ResourceCustomAttributesDAO;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.ResourceCustomAttributes;
import com.rmsi.mast.studio.domain.ResourcePOIAttributeValues;

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
			
		

		String query = "Select cust.optiontext, RA.customattributeid, cust.attributeoptionsid "+
						"from la_ext_resource_custom_attributevalue RA,la_ext_resource_custom_attribute AM,la_ext_attributecategory AC, la_ext_customattributeoptions cust "+
						"Where RA.customattributeid=AM.customattributeid And AM.AttributeCategoryID=AC.AttributeCategoryID And cust.attributeoptionsid=RA.attributeoptionsid "+
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

	@Override
	public List<Object[]> getResourcePoiDatatypeBylandId(Integer projectId,
			Integer Id) {
		try{
			
		

		String query = "Select AM.fieldname, RA.attributemasterid, RA.groupid "+
						"from la_ext_resourcepoiattributevalue RA,la_ext_resourcepoiattributemaster AM "+
						"Where RA.attributemasterid=AM.poiattributemasterid "+
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

	@Override
	public List<ResourcePOIAttributeValues> getResourcePoiDataBylandId(Integer projectId,
			Integer Id) {
		
		List<ResourcePOIAttributeValues> lstResourceDetails= new ArrayList<>();
		
		try{
			
			String query = "Select Distinct RA.LandID,RA.attributemasterid,RA.attributevalue,RA.poiattributevalueid,RA.groupid "+
					"from la_ext_resourcepoiattributevalue RA,la_ext_resourcepoiattributemaster AM "+
					"Where RA.attributemasterid=AM.poiattributemasterid  "+
					"and RA.projectid ="+projectId+" And RA.LandID="+Id + " order by RA.groupid";

	List<Object[]> arrObject = getEntityManager().createNativeQuery(query).getResultList();
	
	if(arrObject.size()>0){
		
		for(Object [] object : arrObject){
			ResourcePOIAttributeValues resourceattributes = new ResourcePOIAttributeValues();
			resourceattributes.setLandid(Integer.valueOf(object[0].toString()));
			
			if(null!=object[4])
			resourceattributes.setGroupid(Integer.valueOf(object[4].toString()));
			
			resourceattributes.setPoiattributevalueid(Integer.valueOf(object[1].toString()));
			resourceattributes.setAttributevalue(object[2].toString());
//			resourceattributes.setFieldname(object[4].toString());
//			resourceattributes.setFieldAliasName(object[6].toString());;
			
			lstResourceDetails.add(resourceattributes);
		}
		
		return lstResourceDetails;
	}
	
	return null;
	
} catch (Exception e) {
	e.printStackTrace();
	return null;
}
	
	
	}

	

}
