

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.UnitDAO;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.Unit;

@Repository
public class UnitHibernateDAO extends GenericHibernateDAO<Unit, Long> implements
		UnitDAO {

	@SuppressWarnings("unchecked")
	public Unit findByName(String name) {
		
		List<Unit> unit =
			getEntityManager().createQuery("Select u from Unit u where u.name = :name").setParameter("name", name).getResultList();
		
		if(unit.size() > 0)
			return unit.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean deleteUnitByName(String name){
		Unit unit= findByName(name);		
		if(unit != null){
			getEntityManager().remove(unit);
			return true;
		}else{
			return false;
		}
	}
	
}
