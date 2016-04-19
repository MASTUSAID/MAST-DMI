

package com.rmsi.mast.studio.dao.hibernate;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;







import com.rmsi.mast.studio.dao.LayerDAO;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.domain.ProjectLayergroup;

@Repository
public class LayerHibernateDAO extends GenericHibernateDAO<Layer, Long> implements
		LayerDAO {
	
	
	@SuppressWarnings("unchecked")	
	public Layer findByAliasName(String alias){
		List<Layer> layers =
			getEntityManager().createQuery("Select l from Layer l where l.alias = :aliasName").setParameter("aliasName", alias).getResultList();
		
		if(layers != null && layers.size() > 0){
			Iterator iter = layers.get(0).getLayerFields().iterator();
			String keyfield="";
			while (iter.hasNext()) {
		      
		    	LayerField lf=new LayerField();
		    	lf=(LayerField) iter.next();
		    	keyfield=lf.getKeyfield();
		    	break;
		    }
			layers.get(0).setKeyField(keyfield);
	
			return layers.get(0);
		}else{
			return new Layer();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLayerByLayerOrder(){
		String queryString = "select l.alias from Layer l, " +
				"LayerLayergroup lg where l.alias = lg.layer " +
				"order by lg.layerorder";
		return getEntityManager().createQuery(queryString).getResultList();
	}
	
	public boolean deleteLayerByAliasName(String id, List<Layergroup> layerGroup){
		Layer layer = findByAliasName(id);
		if(layer != null){
			//Check if layer is part of more than one layergroup
			System.out.println(" ---- Size is :" + layerGroup.size());
			if(layerGroup.size() <= 1){
				if(layerGroup.size() != 0){
					Layergroup lg = layerGroup.get(0);
					System.out.println("params: id -- " + id + " layergroupname: " + lg.getName());
					
					//Remove the layer from layer_layergroup
					Query query = getEntityManager().createQuery(
							"delete from LayerLayergroup llg where llg.layer = :lyrName " +
							"and llg.layergroupBean.name = :lgName")
							.setParameter("lyrName", id).setParameter("lgName", lg.getName());
					
					int deleted = query.executeUpdate();
					System.out.println("---Delete Count: " + deleted);
				
					//Get the project
					//Note: the layer need to be associated with Layergroup for getting Project
					Iterator<ProjectLayergroup> itrProjectLayerGroup =
							lg.getProjectLayergroups().iterator(); 
					if(itrProjectLayerGroup.hasNext()){
						ProjectLayergroup projectLayerGroup = itrProjectLayerGroup.next();
						System.out.println("---Project Name: " + projectLayerGroup.getProjectBean().getName());
						
						//Remove the layer from Maptip
						query = getEntityManager().createQuery("delete from Maptip m where m.layerBean.alias = :alias " +
							"and m.projectBean.name = :name")
							.setParameter("alias", id)
							.setParameter("name", projectLayerGroup.getProjectBean().getName());
						
						deleted = query.executeUpdate();
						System.out.println("---Delete Maptip Count: " + deleted);
					}
				}
				//Remove the layer from attachment
				Query query1 = getEntityManager().createQuery("delete from Attachment a where a.layer.alias = :alias ")
					.setParameter("alias", id);
				
				int deleted1 = query1.executeUpdate();
				System.out.println("---Delete attachment Count: " + deleted1);
				
				//Remove the layer
				getEntityManager().remove(layer);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public String getGeometryType(String id){
		Layer layer = findByAliasName(id);
		if(layer != null){
			return layer.getGeomtype();
		}else{
			return null;
		}
	}
	
	public String saveSLD(String layerName, String sld){
		String status = "success";
		if(sld != null){
			Layer layer = findByAliasName(layerName);
			if(layer != null){
				layer.setStyle(sld);
				layer = getEntityManager().merge(layer);
				if(layer.getStyle() != null){
					status = "success";
				}else{
					status = "fail";
				}
			}else{
				status = "fail";
			}
		}
		return status;
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Boolean> getVisibilityStatus(String[] layers){
		String s = "";
		
		for(int i=0; i<layers.length; i++){
			if(i != layers.length - 1){
				s = s + "\'" + layers[i] + "\'" +", ";
			}else{
				s = s + "\'" + layers[i] + "\'";
			}
		}
		//System.out.println("---- layers visibility --- " + s);
		String queryString = "select l.visibility from Layer l " +
		"where l.alias in (" + s + ")";
		//System.out.println("--QueryString: " + queryString);
		return getEntityManager().createQuery(queryString).getResultList();
	}*/
	
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
			//.setParameter("inList", layerList)
			.getResultList();
	
		return layer;
	}

}
