/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

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
