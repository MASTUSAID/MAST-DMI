

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.LayerFieldDAO;
import com.rmsi.mast.studio.domain.LayerField;

@Repository
public class LayerFieldHibernateDAO extends GenericHibernateDAO<LayerField, Long> implements 
		LayerFieldDAO{

	public String create(LayerField layerField){
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String edit(String id, LayerField layerField){
		if((id != null && id.trim().length()> 0) && layerField != null){
			String queryString = "Select lf from LayerField lf where lf.alias = :aliasName" +
					" or lf.field = :fieldName";
			
			List<LayerField> lyrFields = getEntityManager().createQuery(queryString).setParameter("aliasName", id)
			.setParameter("fieldName", id).getResultList();
			
			if(lyrFields.size() > 0){
				LayerField lyrField = lyrFields.get(0);
				lyrField.setField(layerField.getField());
				getEntityManager().merge(lyrField);
				return "success";
			}else{
				return "fail";
			}
		}else{
			return "fail";
		}
	}
	
	@SuppressWarnings("unchecked")
	public String delete(String id){
		if(id != null && id.trim().length()> 0){
			String queryString = "Select lf from LayerField lf where lf.layerBean.alias = :layerName" +
					" or lf.field = :fieldName" +
					" or lf.alias = :aliasName";
			
			List<LayerField> lyrFields = getEntityManager().createQuery(queryString).setParameter("layerName", id)
			.setParameter("fieldName", id)
			.setParameter("aliasName", id).getResultList();
			
			if(lyrFields.size() > 0){
				for(int i=0; i<lyrFields.size(); i++){
					LayerField lyrField = lyrFields.get(i);
					getEntityManager().remove(lyrField);
				}
				return "success";
			}else{
				return "fail";
			}
		}else{
			return "fail";
		}
	}
}
