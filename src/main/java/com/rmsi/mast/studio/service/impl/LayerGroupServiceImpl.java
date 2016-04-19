

package com.rmsi.mast.studio.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;

import com.rmsi.mast.studio.dao.LayerGroupDAO;
import com.rmsi.mast.studio.dao.LayerLayergroupDAO;
import com.rmsi.mast.studio.dao.ProjectLayergroupDAO;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.service.LayerGroupService;

@Service
public class LayerGroupServiceImpl implements LayerGroupService {
	@Autowired
	private LayerGroupDAO layerGroupDao;
	
	@Autowired
	private ProjectLayergroupDAO projectLayergroupDAO;
	
	@Autowired
	private LayerLayergroupDAO layerLayergroupDao;
	
	//@Cacheable(cacheName="layerGroupFBNCache")
	public List<Layergroup> findAllLayerGroups(){
		List<Layergroup> layergroupList=layerGroupDao.findAll();
		for(int i=0;i<layergroupList.size();i++){
			
			List<String> lgProjects = projectLayergroupDAO.getProjectsByLayergroup(layergroupList.get(i).getName());
			
			//Set<ProjectLayergroup> layergroupProjects=(Set<ProjectLayergroup>) projectLayergroupDAO.getProjectsByLayergroup(layergroupList.get(i).getName());
			
			String[] sl = (String[]) lgProjects.toArray(new String[0]);
			
			layergroupList.get(i).setLayergroupProjects(sl);
			
			
		}
		
		return layergroupList;
		
	}
	
	//@Cacheable(cacheName="layerGroupFBNCache")
	public List<Layergroup> findLayerGroupByName(String name){
		return layerGroupDao.findByName(name);
	}
	
	//@TriggersRemove(cacheName="layerGroupFBNCache", removeAll=true)
	public boolean deleteLayerGroupByName(String id){
		
		projectLayergroupDAO.deleteProjectLayergroupByLG(id);		
		layerLayergroupDao.deleteLayerLayergroupByName(id);		
		return layerGroupDao.deleteLayerGroupByName(id);
		
	}

	@Override
	//@TriggersRemove(cacheName={"layerGroupFBNCache","projectFBNCache"}, removeAll=true)	
	public void addLayergroup(Layergroup layergroup) {
		//delete layerlayergroup
		
		layerLayergroupDao.deleteLayerLayergroupByName(layergroup.getName());
		
		layerGroupDao.makePersistent(layergroup);
	}
}
