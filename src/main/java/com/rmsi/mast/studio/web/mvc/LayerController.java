
package com.rmsi.mast.studio.web.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.Layertype;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.studio.service.LayertypeService;
import com.rmsi.mast.studio.service.OutputformatService;
import com.rmsi.mast.studio.service.ProjectionService;
import com.rmsi.mast.studio.service.UnitService;

@Controller
public class LayerController {
	
	private static final Logger logger = Logger.getLogger(LayerController.class);
	
	@Autowired
	private LayerService layerService;
	@Autowired
	private LayertypeService layertypeService;
	@Autowired
	private ProjectionService projectionService;
	@Autowired
	private OutputformatService outputformatService;
	@Autowired
	private UnitService unitService;
	private final String TENANT_ID = "1";
	
	@RequestMapping(value = "/studio/layer/", method = RequestMethod.GET)
	@ResponseBody
	public List<Layer> list(){
		return layerService.findAllLayers();
	}
	
	@RequestMapping(value = "/studio/layer/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Layer details(@PathVariable("id") String id){
		
			return layerService.findLayerByName(id);
		
	}
	
	@RequestMapping(value = "/studio/layer/detailsByOrder/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> detailsByOrder(@PathVariable("id") String id){
		//String id = "LPIS_Output";
		return layerService.detailsByOrder(id);
	}
	
	@RequestMapping(value="/studio/layer/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean delete(@PathVariable("id") String id){
		return layerService.deleteLayerById(id);
	}
	
	@RequestMapping(value = "/studio/layer/create", method = RequestMethod.POST)
	@ResponseBody
	public Layer create(HttpServletRequest request, HttpServletResponse response) {
		try{
			/*Create new Layer object*/
			Layer layer = new Layer();
			
			//Set Layer Type
			String strLayerType = ServletRequestUtils.getRequiredStringParameter(request, "layertype");
			System.out.println("LayerType Name ---- " + strLayerType);
			Layertype lyrType = layertypeService.findLayertypeByName(strLayerType);
			layer.setLayertype(lyrType);
			
			if(strLayerType.equalsIgnoreCase("wms")){
				
				//Set URL
				layer.setUrl(ServletRequestUtils.getRequiredStringParameter(request, "url"));

				//Set Layer Name
				
					layer.setName(ServletRequestUtils.getRequiredStringParameter(request, "name"));				
				
				//Set WFS name
				layer.setWfsname(ServletRequestUtils.getRequiredStringParameter(request, "wfsname"));
				
				//Set Display Name
				layer.setDisplayname(ServletRequestUtils.getStringParameter(request, "displayname"));
				
				//Set Alias
				layer.setAlias(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				//Set Projection
				
				String strProjection = null;
				
				
					strProjection = ServletRequestUtils.getRequiredStringParameter(request, "projectionBean");			
				
				
				System.out.println("Projection ---- " + strProjection);
				layer.setProjectionBean(projectionService.findProjectionByName(strProjection));
			
				//Set Output Format
				String strFormat = null;
				
				
					strFormat = ServletRequestUtils.getRequiredStringParameter(request, "outputformat");
				
				
				System.out.println("Output Format ---- " + strFormat);
				layer.setOutputformat(outputformatService.findOutputformatByName(strFormat));
				
				//Set MaxExtent
				layer.setMaxextent(ServletRequestUtils.getRequiredStringParameter(request, "maxextent"));
				
				
				//Set Numzoom
				int intNumzoomlevels=0;
				try{
					intNumzoomlevels = ServletRequestUtils.getIntParameter(request, "numzoomlevels");
					System.out.println("MinScale --- " + intNumzoomlevels);
					layer.setNumzoomlevels(new Integer(intNumzoomlevels));
				}catch(Exception e){logger.error(e);}
				
				// set Displayinlayermanager
				boolean blnDisplayinlayermanager = ServletRequestUtils.getRequiredBooleanParameter(request, "displayinlayermanager");
				layer.setDisplayinlayermanager(new Boolean(blnDisplayinlayermanager));
				
				// set layer visibility
				boolean blnVisibility = ServletRequestUtils.getRequiredBooleanParameter(request, "visibility");
				layer.setVisibility(new Boolean(blnVisibility));
				
				//Set Queryable
				boolean blnQuerybale = ServletRequestUtils.getRequiredBooleanParameter(request, "queryable");
				System.out.println("Queryable ---- " + blnQuerybale);
				layer.setQueryable(new Boolean(blnQuerybale));
				
				//Set Editable
				boolean blnEditable = ServletRequestUtils.getRequiredBooleanParameter(request, "editable");
				layer.setEditable(new Boolean(blnEditable));
				
				//set selectable
				boolean blnSelectable = ServletRequestUtils.getRequiredBooleanParameter(request, "selectable");
				layer.setSelectable(new Boolean(blnSelectable));
				
				
				//Set Unit
				String strUnit = ServletRequestUtils.getRequiredStringParameter(request, "unitBean.name");
				System.out.println("Unit ---- " + strUnit);
				layer.setUnitBean(unitService.findUnitByName(strUnit));
				
				//Set MinExtent
				layer.setMinextent(ServletRequestUtils.getRequiredStringParameter(request, "minextent"));
				
				//Set Layer Style
				try{
				String strLayerStyle = ServletRequestUtils.getStringParameter(request, "style");
				System.out.println("--" + strLayerStyle + " -- ");
				if(strLayerStyle != null && strLayerStyle.trim() != "")
				{
					layer.setStyle(strLayerStyle);
				}
				}catch(Exception e){logger.error(e);}
				
				//Set Exportable
				boolean blnExportable = ServletRequestUtils.getRequiredBooleanParameter(request, "exportable");
				System.out.println("Exportable ---- " + blnExportable);
				layer.setExportable(new Boolean(blnExportable));
				
				//Set Minscale
				try{
					int intMinScale = ServletRequestUtils.getIntParameter(request, "minscale");
					System.out.println("MinScale --- " + intMinScale);
					layer.setMinscale(new Integer(intMinScale));
				}catch(Exception e){logger.error(e);}
				
				//Set MaxScale
				try{
					int intMaxScale = ServletRequestUtils.getIntParameter(request, "maxscale");
					System.out.println("MaxScale --- " + intMaxScale);
					layer.setMaxscale(new Integer(intMaxScale));
				}catch(Exception e){logger.error(e);}
				
				//Set Tile Size
				//int intTileSize = ServletRequestUtils.getRequiredIntParameter(request, "tilesize");
				//System.out.println("TileSize --- " + intTileSize);
				//layer.setTilesize(new Integer(intTileSize));
				
				//Set Buffer
				int intBuffer = ServletRequestUtils.getRequiredIntParameter(request, "buffer");
				System.out.println("Buffer --- " + intBuffer);
				layer.setBuffer(new Integer(intBuffer));
				
				//Set Spherical Mercator
				//boolean blnSphericalMercator = ServletRequestUtils.getRequiredBooleanParameter(request, "sphericalmercator");
				//System.out.println("Spherical Mercator --- " + blnSphericalMercator);
				//layer.setSphericalmercator(new Boolean(blnSphericalMercator));
				
				//Set Display Outside Max Extent
				boolean blnDisplayOutsideMaxExtent = ServletRequestUtils.getRequiredBooleanParameter(request, "displayoutsidemaxextent");
				System.out.println("Spherical Mercator --- " + blnDisplayOutsideMaxExtent);
				layer.setDisplayoutsidemaxextent(new Boolean(blnDisplayOutsideMaxExtent));
				
				//Set Transition Effect
				layer.setTransitioneffect(ServletRequestUtils.getStringParameter(request, "transitioneffect"));
				
				//Set Api Key
				layer.setApikey(ServletRequestUtils.getStringParameter(request, "apikey"));
				
				//Set isBase Layer
				boolean blnIsBaseLayer = ServletRequestUtils.getRequiredBooleanParameter(request, "isbaselayer");
				System.out.println("Is Base Layer --- " +  blnIsBaseLayer);
				layer.setIsbaselayer(new Boolean(blnIsBaseLayer));
				
				//Set Geometry Type
				String strGeomType = ServletRequestUtils.getStringParameter(request, "geomtype");
				System.out.println("Geometry Type --- " + strGeomType);
				layer.setGeomtype(strGeomType);
				
				//Set Filter
				layer.setFilter(ServletRequestUtils.getStringParameter(request, "filter"));
				
				//Set version
				layer.setVersion(ServletRequestUtils.getStringParameter(request, "version"));
				
				//Set Geometry Name
				layer.setGeometryname(ServletRequestUtils.getStringParameter(request, "geometryname"));
				
				//Set Tiled
				boolean blnTiled= ServletRequestUtils.getRequiredBooleanParameter(request, "tiled");
				System.out.println("Tiled ---- " + blnTiled);
				layer.setTiled(new Boolean(blnTiled));							
			}
			
			if(strLayerType.equalsIgnoreCase("wfs")){
				
				//Set URL
				layer.setUrl(ServletRequestUtils.getRequiredStringParameter(request, "url"));

				//Set Layer Name
				
					layer.setName(ServletRequestUtils.getRequiredStringParameter(request, "name"));				
				
				//Set WFS name
				//layer.setWfsname(ServletRequestUtils.getRequiredStringParameter(request, "wfsname"));
				
				//Set Display Name
				layer.setDisplayname(ServletRequestUtils.getStringParameter(request, "displayname"));
				
				//Set Alias
				layer.setAlias(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				//Set Projection
				
				String strProjection = null;
				
				
					strProjection = ServletRequestUtils.getRequiredStringParameter(request, "projectionBean");			
				
				
				System.out.println("Projection ---- " + strProjection);
				layer.setProjectionBean(projectionService.findProjectionByName(strProjection));
			
				//Set Output Format
				String strFormat = null;
				
				
					strFormat = ServletRequestUtils.getRequiredStringParameter(request, "outputformat");
				
				
				System.out.println("Output Format ---- " + strFormat);
				layer.setOutputformat(outputformatService.findOutputformatByName(strFormat));
				
				//Set MaxExtent
				layer.setMaxextent(ServletRequestUtils.getRequiredStringParameter(request, "maxextent"));
				
				//Set Geometry Type
				String strGeomType = ServletRequestUtils.getStringParameter(request, "geomtype");
				System.out.println("Geometry Type --- " + strGeomType);
				layer.setGeomtype(strGeomType);
				
			}
			else if(strLayerType.equalsIgnoreCase("Tilecache")){
				
				//Set URL
				layer.setUrl(ServletRequestUtils.getRequiredStringParameter(request, "url"));

				//Set Layer Name
				
				layer.setName(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				
				//Set Alias
				layer.setAlias(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				//Set Projection
				
				String strProjection = null;
										
				
				strProjection = ServletRequestUtils.getRequiredStringParameter(request, "projectionBean");
					
				
				layer.setProjectionBean(projectionService.findProjectionByName(strProjection));
			
				//Set Output Format
				String strFormat = null;
				
				
					strFormat = ServletRequestUtils.getRequiredStringParameter(request, "outputformat");
				
				
				System.out.println("Output Format ---- " + strFormat);
				layer.setOutputformat(outputformatService.findOutputformatByName(strFormat));
				
				//Set MaxExtent
				layer.setMaxextent(ServletRequestUtils.getRequiredStringParameter(request, "maxextent"));
				
				
				//Set Numzoom
				int intNumzoomlevels=0;
				try{
					intNumzoomlevels = ServletRequestUtils.getIntParameter(request, "numzoomlevels");
					System.out.println("MinScale --- " + intNumzoomlevels);
					layer.setNumzoomlevels(new Integer(intNumzoomlevels));
				}catch(Exception e){logger.error(e);}
				
				
				layer.setDisplayinlayermanager(false);
				layer.setQueryable(false);
				layer.setEditable(false);
				layer.setSelectable(false);
				layer.setExportable(false);
				layer.setSphericalmercator(false);
				layer.setIsbaselayer(false);
				layer.setDisplayoutsidemaxextent(false);
				
			}
			
			
		
			//Get Layer Field alias
			String[] lyrFields_alias = ServletRequestUtils.getStringParameters(request, "FieldAlias");
			//Get the Layer Fields
			String[] lyrFields = ServletRequestUtils.getStringParameters(request, "Displayable");
			//Get the Key
			String[] key = request.getParameterValues("Key");
			//System.out.println("-- Key -- " + key[0]);
			
			//Create Layer Field and attach it to the layer object
			Set<LayerField> lyrFieldSet = new HashSet<LayerField>();
			for(int i=0; i<lyrFields_alias.length; i++){
				LayerField layerField = new LayerField();
				layerField.setAlias(lyrFields_alias[i]);
				layerField.setField(lyrFields[i]);
				layerField.setKeyfield(key[0]);
				layerField.setTenantid(TENANT_ID);
				layerField.setLayerBean(layer);
				lyrFieldSet.add(layerField);
			}
			layer.setLayerFields(lyrFieldSet);
			
			layerService.createLayer(layer);
			
		}catch(ServletRequestBindingException e){
			logger.error(e);
		}catch(Exception ex){
			logger.error(ex);
		}
		return null;
	}
	
	@RequestMapping(value = "/studio/layer/edit", method = RequestMethod.POST)
	@ResponseBody
	public Layer edit(@ModelAttribute("layerfrm") Layer postLayer, HttpServletRequest request){
		if(postLayer != null){
			Layer lstLayers = layerService.findLayerByName(postLayer.getAlias());
			
				Layer layer = lstLayers;
				
				//Set Layer Type
				String strLayerType = postLayer.getLayertype().getName();
				//System.out.println("LayerType Name ---- " + strLayerType);
				Layertype lyrType = layertypeService.findLayertypeByName(strLayerType);
				layer.setLayertype(lyrType);
				
				if(strLayerType.equalsIgnoreCase("wms")){
					//Set URL
					layer.setUrl(postLayer.getUrl());
	
					//Set Layer Name
					layer.setName(postLayer.getName());
					
					//Set WFS name
					layer.setWfsname(postLayer.getWfsname());
					
					//Set Display Name
					layer.setDisplayname(postLayer.getDisplayname());
					
					//Set Alias
					layer.setAlias(postLayer.getAlias());
					
					//Set Projection
					String strProjection = postLayer.getProjectionBean().getCode();
					System.out.println("Projection ---- " + strProjection);
					layer.setProjectionBean(projectionService.findProjectionByName(strProjection));
				
					//Set Output Format
					String strFormat = postLayer.getOutputformat().getName();
					System.out.println("Output Format ---- " + strFormat);
					layer.setOutputformat(outputformatService.findOutputformatByName(strFormat));
					
					// set Displayinlayermanager
					layer.setDisplayinlayermanager(postLayer.getDisplayinlayermanager());
					
					
					// set layer visibility
					
					layer.setVisibility(postLayer.getVisibility());
					
									
					//Set MaxExtent
					layer.setMaxextent(postLayer.getMaxextent());
					
					//set numzoom
					layer.setNumzoomlevels(postLayer.getNumzoomlevels());
					
					//Set Queryable
					layer.setQueryable(postLayer.getQueryable());
					
					//Set Editable
					layer.setEditable(postLayer.getEditable());
					
					//set selectable
					layer.setSelectable(postLayer.getSelectable());
									
					//Set Unit
					String strUnit = postLayer.getUnitBean().getName();
					System.out.println("Unit ---- " + strUnit);
					layer.setUnitBean(unitService.findUnitByName(strUnit));
					
					//Set MinExtent
					layer.setMinextent(postLayer.getMinextent());
					
					//Set Layer Style
					try{
					String strLayerStyle = postLayer.getStyle();
					System.out.println("--" + strLayerStyle + " -- ");
					if(strLayerStyle != null && strLayerStyle.trim() != "")
					{
						layer.setStyle(strLayerStyle);
					}
					}catch(Exception e){logger.error(e);}
					
					//Set Exportable
					layer.setExportable(postLayer.getExportable());
					
					//Set Minscale
					layer.setMinscale(postLayer.getMinscale());
					
					//Set MaxScale
					layer.setMaxscale(postLayer.getMaxscale());
					
					//Set Tile Size
					layer.setTilesize(postLayer.getTilesize());
					
					//Set Buffer
					layer.setBuffer(postLayer.getBuffer());
					
					//Set Spherical Mercator
					layer.setSphericalmercator(postLayer.getSphericalmercator());
					
					//Set Display Outside Max Extent
					layer.setDisplayoutsidemaxextent(postLayer.getDisplayoutsidemaxextent());
					
					//Set Transition Effect
					layer.setTransitioneffect(postLayer.getTransitioneffect());
					
					//Set Api Key
					layer.setApikey(postLayer.getApikey());
					
					//Set isBase Layer
					layer.setIsbaselayer(postLayer.getIsbaselayer());
					
					//Set Geometry Type
					layer.setGeomtype(postLayer.getGeomtype());
					
					//Set Filter
					layer.setFilter(postLayer.getFilter());
					
					//Set version
					layer.setVersion(postLayer.getVersion());
					
					//Set Geometry Name
					layer.setGeometryname(postLayer.getGeometryname());
					
					layer.setTiled(postLayer.getTiled());
				}
				
				else if(strLayerType.equalsIgnoreCase("wfs")){
					//Set URL
					layer.setUrl(postLayer.getUrl());
	
					//Set Layer Name
					layer.setName(postLayer.getName());
					
					//Set WFS name
					layer.setWfsname(postLayer.getWfsname());
					
					//Set Display Name
					layer.setDisplayname(postLayer.getDisplayname());
					
					//Set Alias
					layer.setAlias(postLayer.getAlias());
					
					//Set Projection
					String strProjection = postLayer.getProjectionBean().getCode();
					System.out.println("Projection ---- " + strProjection);
					layer.setProjectionBean(projectionService.findProjectionByName(strProjection));
				
					//Set Output Format
					String strFormat = postLayer.getOutputformat().getName();
					System.out.println("Output Format ---- " + strFormat);
					layer.setOutputformat(outputformatService.findOutputformatByName(strFormat));
					
														
					//Set MaxExtent
					layer.setMaxextent(postLayer.getMaxextent());
					
					//Set Geometry Type
					layer.setGeomtype(postLayer.getGeomtype());					
					
				}
				
				else if(strLayerType.equalsIgnoreCase("Tilecache")){
					
					layer.setUrl(postLayer.getUrl());
					
					//Set Layer Name
					layer.setName(postLayer.getAlias());
										
					//Set Alias
					layer.setAlias(postLayer.getAlias());
					
					//Set Projection
					String strProjection = postLayer.getProjectionBean().getCode();
					System.out.println("Projection ---- " + strProjection);
					layer.setProjectionBean(projectionService.findProjectionByName(strProjection));
				
					//Set Output Format
					String strFormat = postLayer.getOutputformat().getName();
					System.out.println("Output Format ---- " + strFormat);
					layer.setOutputformat(outputformatService.findOutputformatByName(strFormat));
					
					
					
					/*String strProjection=null;
					try{
					strProjection = ServletRequestUtils.getRequiredStringParameter(request, "hid-projectionBean");
										
					layer.setProjectionBean(projectionService.findProjectionByName(strProjection));
					}
					catch(Exception e){}
					//Set Output Format
					String strFormat = null;
					try{
					strFormat = ServletRequestUtils.getRequiredStringParameter(request, "hid-outputformat");
					layer.setOutputformat(outputformatService.findOutputformatByName(strFormat));
					}
					catch(Exception e){}*/
					
									
					//Set MaxExtent
					layer.setMaxextent(postLayer.getMaxextent());
					
					//set numzoom
					layer.setNumzoomlevels(postLayer.getNumzoomlevels());
					
					
					layer.setDisplayinlayermanager(false);
					layer.setQueryable(false);
					layer.setEditable(false);
					layer.setSelectable(false);
					layer.setExportable(false);
					layer.setSphericalmercator(false);
					layer.setIsbaselayer(false);
					layer.setDisplayoutsidemaxextent(false);
					
				}
				
				
				//Get Layer Field alias
				String[] lyrFields_alias = ServletRequestUtils.getStringParameters(request, "FieldAlias");
				//Get the Layer Fields
				String[] lyrFields = ServletRequestUtils.getStringParameters(request, "Displayable");
				//Get the Key
				String[] key = null;
				try{
				key = request.getParameterValues("Key");
				}
				catch(Exception e){logger.error(e);}
				
				if(key!=null)System.out.println("-- Key -- " + key[0]);
				
				//Create Layer Field and attach it to the layer object
				Set<LayerField> lyrFieldSet = new HashSet<LayerField>();
				for(int i=0; i<lyrFields_alias.length; i++){
					LayerField layerField = new LayerField();
					layerField.setAlias(lyrFields_alias[i]);
					layerField.setField(lyrFields[i]);
					layerField.setKeyfield(key[0]);
					layerField.setTenantid(TENANT_ID);
					//layerField.setLayerBean(layer);
					lyrFieldSet.add(layerField);
				}
				layerService.updateLayer(layer, lyrFieldSet);
				
				return layer;
			}
			return null;
		}
		
	
	@RequestMapping(value="/studio/layer/getGeometryType/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String getGeometryType(@PathVariable("id") String id){
		return layerService.getGeometryType(id);
	}
	
	@RequestMapping(value="/studio/layer/saveSLD", method = RequestMethod.POST)
	@ResponseBody
	public String saveSLD(@PathVariable("layerName") String layerName, @PathVariable("sld") String sld ){
		return layerService.saveSLD(layerName, sld);
	}
	
	@RequestMapping(value="/studio/layer/createSLD", method=RequestMethod.POST)
	@ResponseBody
	 public String createSLD(@PathVariable String layerName){
		 return null;
	 }
	
	@RequestMapping(value = "/studio/layer/{alias}/layerField", method = RequestMethod.GET)
	@ResponseBody
	public Set<LayerField> layerField(@PathVariable("alias") String alias){
		return layerService.getLayerFieldsByLayerName(alias);
	}
	
	@RequestMapping(value="/studio/layer/wms", method=RequestMethod.POST)
	@ResponseBody
	public String getWMSData(String id){
		System.out.println("url --------- " + id);
		StringWriter sw = new StringWriter();
		try{
			
			String line;
			java.net.URLConnection connection =
				new URL(id).openConnection();
		
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
			while( (line = br.readLine()) != null )   
	        {   
	          sw.write( line );   
	        }    
			
			br.close();
			sw.close();
			
			//System.out.println(sw.toString());

		}catch(MalformedURLException me){
			logger.error(me);
		}catch(IOException ioe){
			logger.error(ioe);
		}
		
		return sw.toString();
	}
	
}
