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

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LayerLayergroupDAO;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;



@Repository
public class LayerLayergroupHibernateDAO extends GenericHibernateDAO<LayerLayergroup, Long>
		implements LayerLayergroupDAO {
	private static final Logger logger = Logger.getLogger(LayerLayergroupHibernateDAO.class);

	
	@SuppressWarnings("unchecked")
	public List<LayerLayergroup> findAllLayerLayergroup(String name) {
		
		List<LayerLayergroup> layerLayergroup =
			getEntityManager().createQuery("Select llg from LayerLayergroup llg where llg.layergroupBean.name = :name").setParameter("name", name).getResultList();
		
			return layerLayergroup;		
	}	

	@SuppressWarnings("unchecked")
	public void deleteLayerLayergroupByName(String name) {
		
	/*	List<LayerLayergroup> layerLayergroupList=findAllLayerLayergroup(name);
		System.out.println("HDAO SIZE >>>>>>>"+ layerLayergroupList.size());
		if(layerLayergroupList.size() > 0){
				for(int i=0;i<layerLayergroupList.size();i++){			
					LayerLayergroup llg=new LayerLayergroup();
					llg=layerLayergroupList.get(i);
					System.out.println("DELETEING ID >>>>>>>"+ Long.parseLong(llg.getId().toString()));				
					//makeTransientByID(long (plg.getId());			
					makeTransientByID(Long.parseLong(llg.getId().toString()));
				}
		}*/
		System.out.println("DELETE LLG BY LG NAME..."+ name);
		try{
			Query query = getEntityManager().createQuery(
					"Delete from LayerLayergroup llg where llg.layergroupBean.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			System.out.println("Delete count: " + count);
			
		}catch(Exception e){
			logger.error(e);
			
		}
	}
		
	
}
