
package com.rmsi.mast.studio.dao.hibernate;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.AttributeValueFetchDAO;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;


@Repository
public class AttributeValueFetchHibernateDAO extends GenericHibernateDAO<AttributeValuesFetch, Long>
		implements AttributeValueFetchDAO {

	@Override
	public boolean updateValue(Long value_key, String alias) {
		
		Query query = getEntityManager().createQuery("UPDATE AttributeValuesFetch d SET d.value = :alias where d.attributevalueid = :id");

		query.setParameter("id",value_key);
		query.setParameter("alias",alias);
		int rows = query.executeUpdate();

		if(rows>0)
		{
			return true;
		}
		else
			return false;
	
	}
	
	
}
