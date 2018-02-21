

package com.rmsi.mast.studio.dao.hibernate;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;


















import com.rmsi.mast.studio.dao.LayerDAO;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.LayerLayergroup;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.ProjectLayergroup;

@Repository
public class LayerHibernateDAO extends GenericHibernateDAO<Layer, Long> implements
		LayerDAO {
	
	
	@SuppressWarnings("unchecked")	
	public Layer findByAliasName(String layerid){
		try {
			List<Layer> layers =
					getEntityManager().createQuery("Select l from Layer l where l.alias = :aliasName").setParameter("aliasName", layerid+"").getResultList();
			//getEntityManager().createQuery("Select l from Layer l where l.layerid = :layerid").setParameter("layerid", layerid).getResultList();

			if(layers != null && layers.size() > 0){

				return layers.get(0);
			}
			else
			{
				return new Layer();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLayerByLayerOrder(){
		String queryString = "select l.alias from Layer l, " +
				"LayerLayergroup lg where l.alias = lg.layer " +
				"order by lg.layerorder";
		return getEntityManager().createQuery(queryString).getResultList();
	}
	
	
	
	public String saveSLD(String layerName, String sld){
		String status = "success";
		if(sld != null){

		}
		return status;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getVisibilityStatus(String[] layers){
		String s = "";

		for(int i=0; i<layers.length; i++){
			if(i != layers.length - 1){
				s = s + "\'" + layers[i] + "\'" +", ";
			}else{
				s = s + "\'" + layers[i] + "\'";
			}
		}
		//System.out.println("---- layers visibility --- " + s);
		String queryString = "select l.alias, l.visibility from Layer l " +
				"where l.alias in (" + s + ")";
		//System.out.println("--QueryString: " + queryString);
		return getEntityManager().createQuery(queryString).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Layer> getSurveyLayer(String layerList){
		
		List<Layer> layer =
			getEntityManager().createQuery("select l from Layer l where l.name in ("+layerList+")")
			.getResultList();
	
		return layer;
	}

	@Override
	public Layer findLayerById(long layerid) 
	{
		try {
			@SuppressWarnings("unchecked")
			List<Layer> layer =
					getEntityManager().createQuery("Select l from Layer l where l.layerid = :id").setParameter("id", layerid).getResultList();

			if(layer.size()>0){
				return layer.get(0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
	return null;

		
	}

	@Override
	public String checklayerByid(Long id) {
		
		String status="";
		
		try {
			@SuppressWarnings("unchecked")
			List<LayerLayergroup> Layergroup =
					getEntityManager().createQuery("from LayerLayergroup llg where llg.layers.layerid =:id").setParameter("id", id).getResultList();
				
			if(Layergroup.size()>0)
			{
				status ="Layer is Linked with LayerGroup :  <b>" +Layergroup.get(0).getLayergroupBean().getName() +"</b>" ;
				
				@SuppressWarnings("unchecked")
				List<ProjectLayergroup> projectLayergroup =
						getEntityManager().createQuery("Select plg from ProjectLayergroup plg where plg.layergroupBean.layergroupid = :id").setParameter("id", Layergroup.get(0).getLayergroupBean().getLayergroupid()).getResultList();
					
						if (projectLayergroup.size()>0)
						{
							status =status +"<br/> Layer is Linked with Project :   <b>"+  projectLayergroup.get(0).getProjectBean().getName()+"</b>" ;
						}
				
			}else
			{
				status="No";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
		return status;	
	}

	@Override
	public boolean deleteLayerById(Long id) {


		try{
			Query query = getEntityManager().createQuery(
					"Delete from Layer lg where lg.layerid =:id")
					.setParameter("id", id);

			int count = query.executeUpdate();
			System.out.println("Delete Layer count: " + count);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return false;

}

	
}