package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.fetch.ResourceDetails;
import com.rmsi.mast.studio.mobile.dao.ResourceAttributeValuesDAO;

@Repository
public class ResourceAttributeValuesHibernateDAO extends GenericHibernateDAO<ResourceAttributeValues, Integer> implements ResourceAttributeValuesDAO {

	 private static final Logger logger = Logger.getLogger(ResourceAttributeValuesHibernateDAO.class);

	    @Override
	    public ResourceAttributeValues addResourceAttributeValues(ResourceAttributeValues resourceAttributevalues) {

	        try {
	            return makePersistent(resourceAttributevalues);

	        } catch (Exception ex) {
	            System.out.println("Exception while adding data...." + ex);
	            logger.error(ex);
	            throw ex;
	        }
	    }

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceAttributeValues> getResourceAttributeValuesBylandId(Integer projectId, Integer Id) {
		try{
			List<ResourceAttributeValues> lstResourceDetails= new ArrayList<ResourceAttributeValues>();
		

		String query = "Select Distinct RA.LandID,RA.groupID,RA.AttributeValueID,RA.AttributeValue,AM.FieldName,AC.categoryName "+
						"from la_ext_resourceattributevalue RA,la_ext_attributemaster AM,la_ext_attributecategory AC "+
						"Where RA.AttributeMasterID=AM.AttributeMasterID And AM.AttributeCategoryID=AC.AttributeCategoryID "+
						"and RA.projectid ="+projectId+" And RA.LandID="+Id+" order by groupid";

		List<Object[]> arrObject = getEntityManager().createNativeQuery(query).getResultList();
		
		for(Object [] object : arrObject){
			ResourceAttributeValues resourceattributes = new ResourceAttributeValues();
			resourceattributes.setLandid(Integer.valueOf(object[0].toString()));
			resourceattributes.setGroupid(Integer.valueOf(object[1].toString()));
			resourceattributes.setAttributevalueid(Integer.valueOf(object[2].toString()));
			resourceattributes.setAttributevalue(object[3].toString());
			resourceattributes.setFieldname(object[4].toString());
			
			lstResourceDetails.add(resourceattributes);
		}
		return lstResourceDetails;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	}
//			
//		Query query= getEntityManager().createQuery("select re from ResourceAttributeValues re where re.landid =:landId order by re.groupid" );
//		try {
//			List<ResourceAttributeValues> lstResourceAttributeValues = query.setParameter("landId", Id).getResultList();
//			return lstResourceAttributeValues;
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
			
//		}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceDetails> getAllresouceByproject(String project,Integer startfrom) {
		
		List<ResourceDetails> lstResourceDetails= new ArrayList<ResourceDetails>();
		try {
			/*String query = "Select Distinct RC.LandID,RC.ProjectName,RC.ClassificationName,RC.SubClassificationName,AC.CategoryName,RA.GeomType as GeometryName, " +
					" (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1063,1017) and groupid=RA.groupid  ||' '|| "+
					" (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1065,1018) and groupid=RA.groupid ||' '|| "+
					" (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1066,1019) and groupid=RA.groupid  as OwnerName "+
					 "from la_ext_resourceattributevalue RA, " +
							"(Select RLM.LandID,Pr.ProjectName,RC.ClassificationName,RSC.SubClassificationName,GT.GeometryName "+
							"from la_ext_resourceclassification RC, la_ext_resourcesubclassification RSC,la_ext_resourcelandclassificationmapping RLM " +
							",la_spatialsource_projectname Pr,la_ext_GeometryType GT " +
							"Where RC.ClassificationID=RSC.ClassificationID And RSC.SubClassificationID=RLM.SubClassificationID And RLM.ProjectID=Pr.ProjectNameID "+
							"And RSC.GeometryTypeID=GT.GeometryTypeID) RC " +
							",la_ext_attributemaster AM,la_ext_attributecategory AC "+
							"Where RA.landID=RC.LandID AND RA.AttributeMasterID=AM.AttributeMasterID And AM.AttributeCategoryID=AC.AttributeCategoryID and RA.projectid ="+project ;*/
			
			/*String query = "Select Distinct RC.LandID,RC.ProjectName,RC.ClassificationName,RC.SubClassificationName,AC.CategoryName,RA.GeomType as GeometryName, " +
					" (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1063,1017,1035,1079,1088,1097,1108) and groupid=RA.groupid) ||' '|| "+
					" (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1065,1018,1036,1080,1089,1109,1098) and groupid=RA.groupid) ||' '|| "+
					" (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1066,1019,1037,1081,1090,1099,1110) and groupid=RA.groupid) as OwnerName "+
					 "from la_ext_resourceattributevalue RA, " +
							"(Select RLM.LandID,Pr.ProjectName,RC.ClassificationName,RSC.SubClassificationName,GT.GeometryName "+
							"from la_ext_resourceclassification RC, la_ext_resourcesubclassification RSC,la_ext_resourcelandclassificationmapping RLM " +
							",la_spatialsource_projectname Pr,la_ext_GeometryType GT " +
							"Where RC.ClassificationID=RSC.ClassificationID And RSC.SubClassificationID=RLM.SubClassificationID And RLM.ProjectID=Pr.ProjectNameID "+
							"And RSC.GeometryTypeID=GT.GeometryTypeID) RC " +
							",la_ext_attributemaster AM,la_ext_attributecategory AC "+
							"Where RA.landID=RC.LandID AND RA.AttributeMasterID=AM.AttributeMasterID And AM.AttributeCategoryID=AC.AttributeCategoryID and RA.projectid ="+project ;*/
			
			
			
			String query = "Select Distinct RA.LandID,RC.ProjectName,RC.ClassificationName,RC.SubClassificationName,AC.CategoryName,RA.GeomType as GeometryName,  "
					+ "  (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1063,1017,1035,1079,1088,1097,1108) and groupid=RA.groupid) ||' '||"
					+ "  (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1065,1018,1036,1080,1089,1109,1098) and groupid=RA.groupid) ||' '||"
					+ "  (Select attributevalue from la_ext_resourceattributevalue Where landid=RA.landID and attributemasterid in (1066,1019,1037,1081,1090,1099,1110) and groupid=RA.groupid) as OwnerName"
					+ "  from la_ext_resourceattributevalue RA,"
					+ " (Select RLM.LandID,Pr.ProjectName,RC.ClassificationName,RSC.SubClassificationName,GT.GeometryName"
					+ " from la_ext_resourceclassification RC, la_ext_resourcesubclassification RSC,la_ext_resourcelandclassificationmapping RLM"
					+ " ,la_spatialsource_projectname Pr,la_ext_GeometryType GT"
					+ " Where RC.ClassificationID=RSC.ClassificationID And RSC.SubClassificationID=RLM.SubClassificationID And RLM.ProjectID=Pr.ProjectNameID"
					+ " And RSC.GeometryTypeID=GT.GeometryTypeID) RC"
					+ " ,la_ext_attributemaster AM,la_ext_attributecategory AC"
					+ " Where RA.landID=RC.LandID And RA.GeomType=RC.GeometryName AND RA.AttributeMasterID=AM.AttributeMasterID And AM.AttributeCategoryID=AC.AttributeCategoryID and RA.projectid ="+project ;

			
			
			List<Object[]> arrObject = getEntityManager().createNativeQuery(query).setFirstResult(startfrom).setMaxResults(10).getResultList();
			
			for(Object [] object : arrObject){
				ResourceDetails resourceDetails = new ResourceDetails();
				resourceDetails.setLandid(Integer.valueOf(object[0].toString()));
				resourceDetails.setProjectName(object[1].toString());
				resourceDetails.setClassificationName(object[2].toString());
				resourceDetails.setSubclassificationName(object[3].toString());
				resourceDetails.setCategoryName(object[4].toString());
				resourceDetails.setGeometryName(object[5].toString());
				resourceDetails.setPersonName(object[6].toString());
				resourceDetails.setProjectId(Integer.parseInt(project));
				
				lstResourceDetails.add(resourceDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
        
        
		return lstResourceDetails;
	}

	@Override
	public Integer getAllresouceCountByproject(String project) {
		
		try {
			String query = "select count(*) from (Select Distinct RC.LandID,RC.ProjectName,RC.ClassificationName,RC.SubClassificationName,AC.CategoryName,RA.GeomType as GeometryName from la_ext_resourceattributevalue RA, " +
							"(Select RLM.LandID,Pr.ProjectName,RC.ClassificationName,RSC.SubClassificationName,GT.GeometryName "+
							"from la_ext_resourceclassification RC, la_ext_resourcesubclassification RSC,la_ext_resourcelandclassificationmapping RLM " +
							",la_spatialsource_projectname Pr,la_ext_GeometryType GT " +
							"Where RC.ClassificationID=RSC.ClassificationID And RSC.SubClassificationID=RLM.SubClassificationID And RLM.ProjectID=Pr.ProjectNameID "+
							"And RSC.GeometryTypeID=GT.GeometryTypeID) RC " +
							",la_ext_attributemaster AM,la_ext_attributecategory AC "+
							"Where RA.landID=RC.LandID AND RA.AttributeMasterID=AM.AttributeMasterID And AM.AttributeCategoryID=AC.AttributeCategoryID and RA.projectid="+project +")as abc" ; 
			
			
           List<BigInteger> arrObject = getEntityManager().createNativeQuery(query).getResultList();
			
			if(arrObject.size()>0)
			{
			  return Integer.parseInt(arrObject.get(0).toString());
			}else{
				return 0;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
		
		
	}

	@Override
	public List<ResourceAttributeValues> getResourceAttributeValuesByMasterlandid(
			Integer Id) {
		
	Query query= getEntityManager().createQuery("select re from ResourceAttributeValues re where re.landid =:landId order by re.groupid" );
	try {
		List<ResourceAttributeValues> lstResourceAttributeValues = query.setParameter("landId", Id).getResultList();
		return lstResourceAttributeValues;
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e);
		return null;
	}
}

	@Override
	public List<Object[]> getResourceAttributeValuesAndDatatypeBylandId(Integer projectId, 
			Integer Id) {
		try{
			
		

		String query = "Select AM.FieldName, RA.AttributeMasterID, RA.groupid "+
						"from la_ext_resourceattributevalue RA,la_ext_attributemaster AM,la_ext_attributecategory AC "+
						"Where RA.AttributeMasterID=AM.AttributeMasterID And AM.AttributeCategoryID=AC.AttributeCategoryID "+
						"and RA.projectid =" +projectId +" And RA.LandID="+Id+" order by AM.listing \\:\\: int";
		
		
		
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
