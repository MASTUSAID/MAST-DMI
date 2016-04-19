

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
