

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
	
	public List<Layergroup> findAllLayerGroups(){
		try {
			List<Layergroup> layergroupList=layerGroupDao.findAll();
			
			return layergroupList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	

	

	public boolean deleteLayerGroupByName(String id){
		
	//	projectLayergroupDAO.deleteProjectLayergroupByLG(id);		
		layerLayergroupDao.deleteLayerLayergroupByName(id);		
		return layerGroupDao.deleteLayerGroupByName(id);
		
	}

	@Override
	
	public void addLayergroup(Layergroup layergroup) {
		//delete layerlayergroup
		
		try {
			
			try {
				if(null!=layergroup.getLayergroupid()){
				layerLayergroupDao.deleteLayerLayergroupByLayerGroupId(layergroup.getLayergroupid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			layerGroupDao.makePersistent(layergroup);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Layergroup> findLayerGroupByName(String name){
		return layerGroupDao.findByName(name);
	}

	@Override
	public boolean deleteLayerGroupByLayerGroupId(Integer id) {
		//rmsi
		layerLayergroupDao.deleteLayerLayergroupByLayerGroupId(id);	
		return layerGroupDao.deleteLayerGroupByLayerGroupID(id);
		
	}

	@Override
	public Layergroup findLayerGroupsById(Integer id) {
		// TODO Auto-generated method stub
		return layerGroupDao.findLayerGroupsById(id);
	}




	@Override
	public List<Layergroup> getLayergroupByid(Integer id) {
		// TODO Auto-generated method stub
		return layerGroupDao.getLayergroupByid(id);
	}




	@Override
	public Layergroup findLayerGroupsByName(String name) {
		// TODO Auto-generated method stub
		return layerGroupDao.findLayerGroupsByName(name);
	}
	
	
	
}
