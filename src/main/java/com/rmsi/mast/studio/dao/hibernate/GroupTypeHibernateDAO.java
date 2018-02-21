
package com.rmsi.mast.studio.dao.hibernate;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.GroupTypeDAO;
import com.rmsi.mast.studio.domain.GroupType;


@Repository
public class GroupTypeHibernateDAO extends GenericHibernateDAO<GroupType, Integer> implements GroupTypeDAO {

	@Override
	public List<GroupType> findAllGroupType() {
		
		try{
			
			List<GroupType> grpType =
					getEntityManager().createQuery("Select g from GroupType g where g.isactive = true").getResultList();
				return grpType;
				
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
	}
	
	
	
	
}
