
package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.Collection;
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
	
	
	public void deleteProjectLayergroupByProjectId(Integer id) {
		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from ProjectLayergroup plg where plg.projectBean.projectnameid =:id").setParameter("id", id);
			
			int count = query.executeUpdate();
			System.out.println("Delete Projectlayergrp count: " + count);
			
		}catch(Exception e){
			logger.error(e);
			
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
			list.addAll((Collection<? extends String>) g.getLayers());
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

	@Override
	public String checkProjectLayergroupByLayergroupId(Integer id) {
		
		try {
			@SuppressWarnings("unchecked")
			List<ProjectLayergroup> projectLayergroup =
					getEntityManager().createQuery("Select plg from ProjectLayergroup plg where plg.layergroupBean.layergroupid = :id").setParameter("id", id).getResultList();
				
			if(projectLayergroup.size()>0){
				
				return "LayerGroup linked with project :" + "<b>" +projectLayergroup.get(0).getProjectBean().getName() +"</b> ";
			}else{
				
				return "No";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
				
	}
}
