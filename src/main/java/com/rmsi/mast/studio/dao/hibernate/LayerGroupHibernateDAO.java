

package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LayerGroupDAO;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;

@Repository
public class LayerGroupHibernateDAO extends GenericHibernateDAO<Layergroup, Long>
		implements LayerGroupDAO {
	private static final Logger logger = Logger.getLogger(LayerGroupHibernateDAO.class);
	
	

	public boolean deleteLayerGroupByName(String id){
		/*if(id != null){
			List<Layergroup> lstLayerGroups = findByName(id);
			if(lstLayerGroups.size() > 0){
				Layergroup layerGroup = lstLayerGroups.get(0);
				getEntityManager().remove(layerGroup);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}*/
		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from Layergroup lg where lg.name =:name")
					.setParameter("name", id);
			
			int count = query.executeUpdate();
			System.out.println("Delete Layergrp count: " + count);
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
	
	@SuppressWarnings("unchecked")
	public List<Layergroup> findLayerGroupByLayerName(String layerName){
		System.out.println("----param is: " + layerName);
		List<LayerLayergroup> layer_layerGroups = getEntityManager().createQuery(
				"Select llg from LayerLayergroup llg where llg.layer = :lyrName")
				.setParameter("lyrName", layerName).getResultList();
				
		List<Layergroup> lg = new ArrayList<Layergroup>();
		System.out.println("-----Layer_LayerGroup count is: " + layer_layerGroups.size());
		if(layer_layerGroups.size() > 0){
			for(LayerLayergroup l_lg: layer_layerGroups){
				//lg.add(l_lg.getLayergroupBean());
			}
		}
		return lg;
	}
	
	
	@SuppressWarnings("unchecked")	
	public List<Layergroup> findByName(String name){
		List<Layergroup> layerGroups =null;
				
				try{
					layerGroups=		getEntityManager().createQuery("Select lg from Layergroup lg where lg.name = :name")
			.setParameter("name", name).getResultList();
		
				}catch(Exception e){
					return null;
				}
		return layerGroups;
	}

	@Override
	public boolean deleteLayerGroupByLayerGroupID(Integer id) {
		// TODO Auto-generated method stub
		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from Layergroup lg where lg.layergroupid =:id")
					.setParameter("id", id);

			int count = query.executeUpdate();
			System.out.println("Delete Layergrp count: " + count);
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

	@SuppressWarnings("unchecked")
	@Override
	public Layergroup findLayergroupByName(String name) {
		
		List<Layergroup> layerGroups =null;
		
		try{
			layerGroups=		getEntityManager().createQuery("Select lg from Layergroup lg where lg.name = :name")
	.setParameter("name", name).getResultList();

			if(layerGroups.size()>0)
			{
				 return layerGroups.get(0);
			}
		}catch(Exception e){
			return null;
		}
		return null;

		

	}

	@SuppressWarnings("unchecked")
	@Override
	public Layergroup findLayerGroupsById(Integer id) {
		
		List<Layergroup> layergroup = new ArrayList<Layergroup>();
		
		try {
			layergroup=	getEntityManager().createQuery("Select l from Layergroup l where l.isactive=true and   l.layergroupid = :id").setParameter("id", id).getResultList();
				
				if(layergroup.size() > 0)
					return layergroup.get(0);
				
				else
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Layergroup> getLayergroupByid(Integer id) {
		
     List<Layergroup> layergroup = new ArrayList<Layergroup>();
		
		try {
			layergroup=	getEntityManager().createQuery("Select l from Layergroup l where l.isactive=true and   l.layergroupid = :id").setParameter("id", id).getResultList();
			 return layergroup;	
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}

	@Override
	public Layergroup findLayerGroupsByName(String name) {
	     List<Layergroup> layergroup = new ArrayList<Layergroup>();

		try {
			layergroup=	getEntityManager().createQuery("Select l from Layergroup l where l.isactive=true and   l.name = :name").setParameter("name", name).getResultList();
				
				if(layergroup.size() > 0)
					return layergroup.get(0);
				
				else
					return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}


	
}
