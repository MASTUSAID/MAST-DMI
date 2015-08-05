
package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectLayergroupDAO;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.ProjectLayergroup;


@Repository
public class ProjectLayergroupHibernateDAO extends GenericHibernateDAO<ProjectLayergroup, Long>
		implements ProjectLayergroupDAO {
	private static final Logger logger = Logger.getLogger(ProjectLayergroupHibernateDAO.class);

	
	@SuppressWarnings("unchecked")
	public List<String> getProjectsByLayergroup(String name) {		
		
		List<String> projectLayergroup =
			getEntityManager().createQuery("Select plg.projectBean.name from ProjectLayergroup plg where plg.layergroupBean.name = :name").setParameter("name", name).getResultList();
		
			return projectLayergroup;		
	}	
	
	@SuppressWarnings("unchecked")
	public List<ProjectLayergroup> findAllProjectLayergroup(String name) {
		
		List<ProjectLayergroup> projectLayergroup =
			getEntityManager().createQuery("Select plg from ProjectLayergroup plg where plg.projectBean.name = :name").setParameter("name", name).getResultList();
		
			return projectLayergroup;		
	}	
	
	
	public void deleteProjectLayergroupByProjectName(String name) {
		System.out.println("HDAO DELETE >>>>>>>"+ name);
		List<ProjectLayergroup> projectLayergroupList=findAllProjectLayergroup(name);
		if(projectLayergroupList.size() > 0){
				for(int i=0;i<projectLayergroupList.size();i++){			
					ProjectLayergroup plg=new ProjectLayergroup();
					plg=projectLayergroupList.get(i);
					System.out.println("DELETEING ID >>>>>>>"+ Long.parseLong(plg.getId().toString()));				
					//makeTransientByID(long (plg.getId());			
					makeTransientByID(Long.parseLong(plg.getId().toString()));
				}
		}
	}
	
	public List<String> getLayersByProjectName(String project){
		
		String sql = "Select plg.layergroupBean.layerLayergroups " +
				"from ProjectLayergroup plg where plg.projectBean.name =:param";
		
		Query query = getEntityManager().createQuery(sql);
		query.setParameter("param", project);
		
		@SuppressWarnings("unchecked")
		List<LayerLayergroup> llg = query.getResultList();

		List<String> list = new ArrayList<String>();
		for(LayerLayergroup g: llg){
			list.add(g.getLayer());
		}
		return list;
	}
	
	
	public void deleteProjectLayergroupByLG(String layergroup) {
		System.out.println("DELETE PLG BY LG NAME..."+ layergroup);
		try{
			Query query = getEntityManager().createQuery(
					"Delete from ProjectLayergroup plg where plg.layergroupBean.name =:name")
					.setParameter("name", layergroup);
			
			int count = query.executeUpdate();
			System.out.println("Delete Projectlayergrp count: " + count);
			
		}catch(Exception e){
			logger.error(e);
			
		}
	}
}
