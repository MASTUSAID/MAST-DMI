

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.OutputformatDAO;
import com.rmsi.mast.studio.domain.Outputformat;

@Repository
public class OutputformatHibernateDAO extends GenericHibernateDAO<Outputformat, Long> implements
		OutputformatDAO {

	@SuppressWarnings("unchecked")
	public Outputformat findByName(String name) {
		
		List<Outputformat> outputformat =
			getEntityManager().createQuery("Select c from Outputformat c where c.name = :name").setParameter("name", name).getResultList();
		
		if(outputformat.size() > 0)
			return outputformat.get(0);
		else
			return null;
	}
	
	
}
