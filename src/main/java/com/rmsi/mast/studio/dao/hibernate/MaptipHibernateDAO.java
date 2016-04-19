

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.MaptipDAO;
import com.rmsi.mast.studio.domain.Maptip;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;

@Repository
public class MaptipHibernateDAO extends GenericHibernateDAO<Maptip, Long> implements
	MaptipDAO {
	private static final Logger logger = Logger.getLogger(MaptipHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public Maptip findByName(String name) {
		
		List<Maptip> maptip =
			getEntityManager().createQuery("Select m from Maptip m where m.name = :name").setParameter("name", name).getResultList();
		
		if(maptip.size() > 0)
			return maptip.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")	
	public List<Maptip> getMaptipsByLayer(String layer) {
		
		List<Maptip> maptip =
			getEntityManager().createQuery("Select l.maptips from Layer l where l.name = :layer").setParameter("layer", layer).getResultList();
		
		if(maptip.size() > 0)
			return maptip;
		else
			return null;
	}
	
	public boolean deleteMaptipByName(String name){
		//Maptip maptip = findByName(name);
		//System.out.println("--Maptip Name: " + maptip.getName() + " " + maptip.getProjectBean().getName() + " " + maptip.getLayerBean().getAlias());
		try{
			Query query = getEntityManager().createQuery(
					"Delete from Maptip m where m.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			System.out.println("Delete count: " + count);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error(e);
			return false;
		}
	}


	public boolean deleteMaptipByProject(String name) {
		try{
			Query query = getEntityManager().createQuery(
					"Delete from Maptip m where m.projectBean.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			System.out.println("Delete count: " + count);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error(e);
			return false;
		}
	}
	
	public boolean checkIfKeyExists(String project, String layer){
//		Query query = getEntityManager().createQuery(
//				"Select m from Maptip m where m.id.project =:project " +
//				"and m.id.layer =:layer")
//				.setParameter("project", project)
//				.setParameter("layer", layer);
		
//		if(query.getResultList().size() > 0){
//			System.out.println("--Key exists");
//			return true;
//		}else{
//			System.out.println("--Key doesn't exists");
//			return false;
//		}
		
		Maptip maptip = getMaptipByPK(project, layer);
		if(maptip == null){
			return false;
		}else{
			return true;
		}
	}
	
	public Maptip getMaptipByPK(String project, String layer){
		Query query = getEntityManager().createQuery(
				"Select m from Maptip m where m.id.project =:project " +
				"and m.id.layer =:layer")
				.setParameter("project", project)
				.setParameter("layer", layer);
		
		@SuppressWarnings("unchecked")
		List<Maptip> lstMaptip = query.getResultList();
		if(lstMaptip.size() > 0){
			return lstMaptip.get(0);
		}else{
			return null;
		}
			
	}

	@SuppressWarnings("unchecked")
	public List<Maptip> getMaptipsByProject(String project) {
		List<Maptip> maptip =
			getEntityManager().createQuery("Select m from Maptip m where m.id.project =:project ").setParameter("project", project).getResultList();
		
		if(maptip.size() > 0)
			return maptip;
		else
			return null;
	}

}
