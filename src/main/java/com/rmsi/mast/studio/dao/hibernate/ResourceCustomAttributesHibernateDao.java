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

}
