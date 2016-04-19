
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.domain.Action;


@Repository
public class ActionHibernateDAO extends GenericHibernateDAO<Action, Long>
		implements ActionDAO {

	@SuppressWarnings("unchecked")
	
	public Action findByName(String name) {
		List<Action> action =
			getEntityManager().createQuery("Select a from Action a where a.name = :name").setParameter("name", name).getResultList();
		
		if(action.size() > 0)
			return action.get(0);
		else
			return null;
	}
	
	
	public boolean deleteByName(String id){
		Action action = findByName(id);
		System.out.println("##### deleteByName: "+ id);
		if(action != null){
			getEntityManager().remove(action);
			return true;
		}else{
			return false;
		}
	}
	
}
