package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ResourceSubClassificationDAO;
import com.rmsi.mast.studio.domain.ResourceClassification;
import com.rmsi.mast.studio.domain.ResourceSubClassification;


@Repository
public class ResourceSubClassificationHibernateDAO extends GenericHibernateDAO<ResourceSubClassification, Integer> implements ResourceSubClassificationDAO {

	@Override
	public List<ResourceSubClassification> getAllSubClassifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceSubClassification getById(Integer Id) {
		 Query query = getEntityManager().createQuery("Select d from ResourceSubClassification d where d.subclassificationid = :id");
		 ResourceSubClassification editAttrib = (ResourceSubClassification) query.setParameter("id", Id).getSingleResult();
         if(editAttrib != null){
         
        	 return editAttrib;
         }
         else{
             return null;

         	}
	}

}
