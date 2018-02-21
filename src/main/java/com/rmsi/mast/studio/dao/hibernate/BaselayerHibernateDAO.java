
package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;












import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.dao.BaselayerDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.domain.Layer;


@Repository
public class BaselayerHibernateDAO extends GenericHibernateDAO<Baselayer, Long>
		implements BaselayerDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Baselayer findBaselayerById(Integer id) {
		List<Baselayer> baselayer = new ArrayList<Baselayer>();
		
		try {
			baselayer =getEntityManager().createQuery("Select b from Baselayer b where b.isactive =true and  b.baselayerid = :id").setParameter("id", id).getResultList();
				if(baselayer.size() > 0)
					return baselayer.get(0);
				else
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
