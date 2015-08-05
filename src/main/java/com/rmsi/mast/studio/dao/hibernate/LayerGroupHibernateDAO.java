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

package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LayerGroupDAO;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;

@Repository
public class LayerGroupHibernateDAO extends GenericHibernateDAO<Layergroup, Long>
		implements LayerGroupDAO {
	private static final Logger logger = Logger.getLogger(LayerGroupHibernateDAO.class);
	
	@SuppressWarnings("unchecked")	
	public List<Layergroup> findByName(String name){
		List<Layergroup> layerGroups =
			getEntityManager().createQuery("Select lg from Layergroup lg where lg.name = :name")
			.setParameter("name", name).getResultList();
		
		return layerGroups;
	}

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
				lg.add(l_lg.getLayergroupBean());
			}
		}
		return lg;
	}
}
