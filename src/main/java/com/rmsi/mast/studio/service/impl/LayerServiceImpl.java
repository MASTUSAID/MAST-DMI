

package com.rmsi.mast.studio.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;






import com.rmsi.mast.studio.dao.LayerDAO;
import com.rmsi.mast.studio.dao.LayerFieldDAO;
import com.rmsi.mast.studio.dao.LayerGroupDAO;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.Layergroup;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.studio.util.ConfigurationUtil;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

@Service
public class LayerServiceImpl implements LayerService{
	
	
	private static final Logger logger = Logger.getLogger(LayerServiceImpl.class);
	
	@Autowired
	private LayerDAO layerDao;
	@Autowired
	private LayerFieldDAO layerFieldDao;
	@Autowired
	private LayerGroupDAO layerGroupDao;
	
	//@Cacheable(cacheName="layerFBNCache")
	public List<Layer> findAllLayers(){
		List<Layer> lstLayers = layerDao.findAll();
		return lstLayers;
	}
	
	//@Cacheable(cacheName="layerFBNCache")
	public Layer findLayerByName(String layerid){		
		Layer layer = layerDao.findByAliasName(layerid);		
		return layer;
	}
	
	//@Cacheable(cacheName="layerFBNCache")
	public List<String> detailsByOrder(String id){
		return layerDao.getLayerByLayerOrder();
	}
	
	//@TriggersRemove(cacheName="layerFBNCache", removeAll=true)	    
	public Layer updateLayer(Layer layer, Set<LayerField> layerFieldSet){
			
		return layerDao.makePersistent(layer);
	}
	
	//@TriggersRemove(cacheName="layerFBNCache", removeAll=true)	    
	public Layer createLayer(Layer layer){
		try {
			return layerDao.makePersistent(layer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public String saveSLD(String layerName, String sld){
		 try {
			sld = URLDecoder.decode(sld, "UTF-8");
			return layerDao.saveSLD(layerName, sld);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return "fail";
	}
	
	
	
	public List<Object[]> getLayersVisibility(String[] layers){
		List<Object[]> lstVisibility = layerDao.getVisibilityStatus(layers);
		return lstVisibility;
	}

	@Override
	public boolean deleteLayerById(Long id) {
		// TODO Auto-generated method stub
		layerFieldDao.deleteFeildByLayerId(id);
		return layerDao.deleteLayerById(id);
	}

	@Override
	public Layer findLayerById(long layerid) {
		// TODO Auto-generated method stub
		return layerDao.findLayerById(layerid);
	}

	@Override
	public String checklayerByid(Long id) {
		// TODO Auto-generated method stub
		return layerDao.checklayerByid(id);
	}

	@Override
	public Set<LayerField> getLayerFieldsByLayerName(String alias) {
		
		Layer layer = layerDao.findByAliasName(alias);
		return layer.getLayerField();
	}


	
	
	
	
}
