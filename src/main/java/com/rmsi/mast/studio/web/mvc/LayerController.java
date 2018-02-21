
package com.rmsi.mast.studio.web.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Date;
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
import com.rmsi.mast.studio.domain.RoleModule;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.service.LayerFieldService;
import com.rmsi.mast.studio.service.LayerService;
import com.rmsi.mast.studio.service.LayertypeService;
import com.rmsi.mast.studio.service.OutputformatService;
import com.rmsi.mast.studio.service.ProjectionService;
import com.rmsi.mast.studio.service.RoleModuleService;
import com.rmsi.mast.studio.service.UnitService;
import com.rmsi.mast.studio.service.UserService;

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
	
	@Autowired
	private LayerFieldService layerFieldService;

	@Autowired
	private UserService userService;
	
	@Autowired 
	RoleModuleService roleModuleService;
	
	
	private final String TENANT_ID = "1";
	
	@RequestMapping(value = "/studio/layer/", method = RequestMethod.GET)
	@ResponseBody
	public List<Layer> list(){
		return layerService.findAllLayers();
	}
	
	@RequestMapping(value = "/studio/layer/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Layer details(@PathVariable("id") String  id){
		
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
	public String delete(@PathVariable("id") String id){
		
	 String status=	layerService.checklayerByid(Long.parseLong(id));
	 
	 if(status.equalsIgnoreCase("No")){
		boolean flag= layerService.deleteLayerById(Long.parseLong(id));
		if(flag)
		{
			status="success";
		}
		
	 }
	
	 return status;
	}
	
	@RequestMapping(value = "/studio/layer/create", method = RequestMethod.POST)
	@ResponseBody
	public Layer create(HttpServletRequest request, HttpServletResponse response,Principal principal) {
		
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		
		Long id = user.getId();
		
		
		try{
			/*Create new Layer object*/
			Layer layer = new Layer();
			long layerTypeId=0l;
			//Set Layer Type
			String strLayerType = ServletRequestUtils.getRequiredStringParameter(request, "layertype");
			if(strLayerType.equalsIgnoreCase("wms")){
				layerTypeId=2l;
			}else
			{
				layerTypeId=1l;
			}
			System.out.println("LayerType Name ---- " + strLayerType);
			Layertype lyrType = layertypeService.findLayertypeById(layerTypeId);
			layer.setLayertype(lyrType);
			
			if(strLayerType.equalsIgnoreCase("wms")){
				//Set URL
				layer.setUrl(ServletRequestUtils.getRequiredStringParameter(request, "url"));

				//Set Layer Name
				layer.setName(ServletRequestUtils.getRequiredStringParameter(request, "name"));				
				
				
				//Set Display Name
				layer.setDisplayname(ServletRequestUtils.getStringParameter(request, "displayname"));
				
				//Set Alias
				layer.setAlias(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				//Set Projection
				
				String strProjection = null;
				strProjection = ServletRequestUtils.getRequiredStringParameter(request, "projection_code");			
				System.out.println("Projection ---- " + strProjection);
				layer.setProjectionBean(projectionService.findProjectionById(Integer.parseInt(strProjection)));
				
				//Set Output Format
				String strFormat = null;
				strFormat = ServletRequestUtils.getRequiredStringParameter(request, "project_outputFormat");
			    layer.setOutputformat(outputformatService.findOutputformatById(Integer.parseInt(strFormat)));
			    
				
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
				//boolean blnQuerybale = ServletRequestUtils.getRequiredBooleanParameter(request, "queryable");
				//System.out.println("Queryable ---- " + blnQuerybale);
				layer.setQueryable(new Boolean(false));
				
				//Set Editable
				//boolean blnEditable = ServletRequestUtils.getRequiredBooleanParameter(request, "editable");
				layer.setEditable(new Boolean(false));
				
				//set selectable
				//boolean blnSelectable = ServletRequestUtils.getRequiredBooleanParameter(request, "selectable");
				layer.setSelectable(new Boolean(false));
				
				
				//Set Unit
				String strUnit = ServletRequestUtils.getRequiredStringParameter(request, "project_outputFormat");
				System.out.println("Unit ---- " + strUnit);
				layer.setUnitBean(unitService.findUnitById(Integer.parseInt(strUnit)));
				
				//Set MinExtent
				layer.setMinextent(ServletRequestUtils.getRequiredStringParameter(request, "minextent"));
				
								
				//Set Exportable
				//boolean blnExportable = ServletRequestUtils.getRequiredBooleanParameter(request, "exportable");
				//System.out.println("Exportable ---- " + blnExportable);
				layer.setExportable(new Boolean(false));
				
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
				
				
				//Set Buffer
				int intBuffer = ServletRequestUtils.getRequiredIntParameter(request, "buffer");
				System.out.println("Buffer --- " + intBuffer);
				layer.setBuffer(new Integer(intBuffer));
				
				
				
				//Set Display Outside Max Extent
				boolean blnDisplayOutsideMaxExtent = ServletRequestUtils.getRequiredBooleanParameter(request, "displayoutsidemaxextent");
				System.out.println("Spherical Mercator --- " + blnDisplayOutsideMaxExtent);
				layer.setDisplayoutsidemaxextent(new Boolean(blnDisplayOutsideMaxExtent));
				
			
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
				layer.setGeomtype(ServletRequestUtils.getStringParameter(request, "geometryname"));
				
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
				
				
				//Set Display Name
				layer.setDisplayname(ServletRequestUtils.getStringParameter(request, "displayname"));
				
				//Set Alias
				layer.setAlias(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				//Set Projection
				
				String strProjection = null;
				strProjection = ServletRequestUtils.getRequiredStringParameter(request, "projection_code");			
				System.out.println("Projection ---- " + strProjection);
				layer.setProjectionBean(projectionService.findProjectionById(Integer.parseInt(strProjection)));
				
				//Set Output Format
				String strFormat = null;
				strFormat = ServletRequestUtils.getRequiredStringParameter(request, "project_outputFormat");
				System.out.println("output ---- " + strFormat);
			    layer.setOutputformat(outputformatService.findOutputformatById(Integer.parseInt(strFormat)));
			    
				
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
				String strUnit = ServletRequestUtils.getRequiredStringParameter(request, "layer_unit");
				System.out.println("Unit ---- " + strUnit);
				layer.setUnitBean(unitService.findUnitById(Integer.parseInt(strUnit)));
				
				//Set MinExtent
				layer.setMinextent(ServletRequestUtils.getRequiredStringParameter(request, "minextent"));
				
								
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
				
				
				//Set Buffer
				int intBuffer = ServletRequestUtils.getRequiredIntParameter(request, "buffer");
				System.out.println("Buffer --- " + intBuffer);
				layer.setBuffer(new Integer(intBuffer));
				
				
				
				//Set Display Outside Max Extent
				boolean blnDisplayOutsideMaxExtent = ServletRequestUtils.getRequiredBooleanParameter(request, "displayoutsidemaxextent");
				System.out.println("Spherical Mercator --- " + blnDisplayOutsideMaxExtent);
				layer.setDisplayoutsidemaxextent(new Boolean(blnDisplayOutsideMaxExtent));
				
			
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
				layer.setGeomtype(ServletRequestUtils.getStringParameter(request, "geometryname"));
				
				//Set Tiled
				boolean blnTiled= ServletRequestUtils.getRequiredBooleanParameter(request, "tiled");
				System.out.println("Tiled ---- " + blnTiled);
				layer.setTiled(new Boolean(blnTiled));		
				
			
				
			
			}
			
			layer.setVisibility(true);
			layer.setCreatedby(id);
			layer.setCreateddate(new Date());
			
			
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
				layerField.setLayerfield(lyrFields[i]);
				layerField.setLayerfieldEn(lyrFields[i]);
				layerField.setKeyfield(key[0]);
				layerField.setLayer(layer);
				layerField.setIsactive(true);
				lyrFieldSet.add(layerField);
			}
			layer.setLayerField(lyrFieldSet);
			
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
	public Layer edit(HttpServletRequest request, HttpServletResponse response,Principal principal) {
		
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		
		Long id = user.getId();
		
		
		try{
			/*Create new Layer object*/
			Layer layer = null;
			
			try{
			String _layerId=	ServletRequestUtils.getRequiredStringParameter(request, "layer_id");
			layer=layerService.findLayerById(Long.parseLong(_layerId));
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			long layerTypeId=0l;
			//Set Layer Type
			String strLayerType = ServletRequestUtils.getRequiredStringParameter(request, "layertype");
			if(strLayerType.equalsIgnoreCase("wms")){
				layerTypeId=2l;
			}else
			{
				layerTypeId=1l;
			}
			System.out.println("LayerType Name ---- " + strLayerType);
			Layertype lyrType = layertypeService.findLayertypeById(layerTypeId);
			layer.setLayertype(lyrType);
			
			
			if(strLayerType.equalsIgnoreCase("wms")){
				//Set URL
				layer.setUrl(ServletRequestUtils.getRequiredStringParameter(request, "url"));

				//Set Layer Name
				layer.setName(ServletRequestUtils.getRequiredStringParameter(request, "name_layer"));				
				
				
				//Set Display Name
				layer.setDisplayname(ServletRequestUtils.getStringParameter(request, "displayname"));
				
				//Set Alias
				layer.setAlias(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				//Set Projection
				
				String strProjection = null;
				strProjection = ServletRequestUtils.getRequiredStringParameter(request, "projection_code");			
				System.out.println("Projection ---- " + strProjection);
				layer.setProjectionBean(projectionService.findProjectionById(Integer.parseInt(strProjection)));
				
				//Set Output Format
				String strFormat = null;
				strFormat = ServletRequestUtils.getRequiredStringParameter(request, "project_outputFormat");
			    layer.setOutputformat(outputformatService.findOutputformatById(Integer.parseInt(strFormat)));
			    
				
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
				//boolean blnQuerybale = ServletRequestUtils.getRequiredBooleanParameter(request, "queryable");
			//	System.out.println("Queryable ---- " + blnQuerybale);
				layer.setQueryable(new Boolean(false));
				
				//Set Editable
				//boolean blnEditable = ServletRequestUtils.getRequiredBooleanParameter(request, "editable");
				layer.setEditable(new Boolean(false));
				
				//set selectable
				//boolean blnSelectable = ServletRequestUtils.getRequiredBooleanParameter(request, "selectable");
				layer.setSelectable(new Boolean(false));
				
				
				//Set Unit
				String strUnit = ServletRequestUtils.getRequiredStringParameter(request, "project_outputFormat");
				System.out.println("Unit ---- " + strUnit);
				layer.setUnitBean(unitService.findUnitById(Integer.parseInt(strUnit)));
				
				//Set MinExtent
				layer.setMinextent(ServletRequestUtils.getRequiredStringParameter(request, "minextent"));
				
								
				//Set Exportable
				//boolean blnExportable = ServletRequestUtils.getRequiredBooleanParameter(request, "exportable");
				//System.out.println("Exportable ---- " + blnExportable);
				layer.setExportable(new Boolean(false));
				
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
				
				
				//Set Buffer
				int intBuffer = ServletRequestUtils.getRequiredIntParameter(request, "buffer");
				System.out.println("Buffer --- " + intBuffer);
				layer.setBuffer(new Integer(intBuffer));
				
				
				
				//Set Display Outside Max Extent
				boolean blnDisplayOutsideMaxExtent = ServletRequestUtils.getRequiredBooleanParameter(request, "displayoutsidemaxextent");
				System.out.println("Spherical Mercator --- " + blnDisplayOutsideMaxExtent);
				layer.setDisplayoutsidemaxextent(new Boolean(blnDisplayOutsideMaxExtent));
				
			
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
				layer.setGeomtype(ServletRequestUtils.getStringParameter(request, "geometryname"));
				
				//Set Tiled
				boolean blnTiled= ServletRequestUtils.getRequiredBooleanParameter(request, "tiled");
				System.out.println("Tiled ---- " + blnTiled);
				layer.setTiled(new Boolean(blnTiled));		
				
				
			}
			
			if(strLayerType.equalsIgnoreCase("wfs")){
				
				//Set URL
				layer.setUrl(ServletRequestUtils.getRequiredStringParameter(request, "url"));

				//Set Layer Name
				layer.setName(ServletRequestUtils.getRequiredStringParameter(request, "name_layer"));				
				
				
				//Set Display Name
				layer.setDisplayname(ServletRequestUtils.getStringParameter(request, "displayname"));
				
				//Set Alias
				layer.setAlias(ServletRequestUtils.getRequiredStringParameter(request, "alias"));
				
				//Set Projection
				
				String strProjection = null;
				strProjection = ServletRequestUtils.getRequiredStringParameter(request, "projection_code");			
				System.out.println("Projection ---- " + strProjection);
				layer.setProjectionBean(projectionService.findProjectionById(Integer.parseInt(strProjection)));
				
				//Set Output Format
				String strFormat = null;
				strFormat = ServletRequestUtils.getRequiredStringParameter(request, "project_outputFormat");
				System.out.println("output ---- " + strFormat);
			    layer.setOutputformat(outputformatService.findOutputformatById(Integer.parseInt(strFormat)));
			    
				
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
				String strUnit = ServletRequestUtils.getRequiredStringParameter(request, "layer_unit");
				System.out.println("Unit ---- " + strUnit);
				layer.setUnitBean(unitService.findUnitById(Integer.parseInt(strUnit)));
				
				//Set MinExtent
				layer.setMinextent(ServletRequestUtils.getRequiredStringParameter(request, "minextent"));
				
								
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
				
				
				//Set Buffer
				int intBuffer = ServletRequestUtils.getRequiredIntParameter(request, "buffer");
				System.out.println("Buffer --- " + intBuffer);
				layer.setBuffer(new Integer(intBuffer));
				
				
				
				//Set Display Outside Max Extent
				boolean blnDisplayOutsideMaxExtent = ServletRequestUtils.getRequiredBooleanParameter(request, "displayoutsidemaxextent");
				System.out.println("Spherical Mercator --- " + blnDisplayOutsideMaxExtent);
				layer.setDisplayoutsidemaxextent(new Boolean(blnDisplayOutsideMaxExtent));
				
			
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
				layer.setGeomtype(ServletRequestUtils.getStringParameter(request, "geometryname"));
				
				//Set Tiled
				boolean blnTiled= ServletRequestUtils.getRequiredBooleanParameter(request, "tiled");
				System.out.println("Tiled ---- " + blnTiled);
				layer.setTiled(new Boolean(blnTiled));		
				
			
				
			
			}
			
			layer.setVisibility(true);
			layer.setModifiedby(id);
			layer.setModifieddate(new Date());
			
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
				layerField.setLayerfield(lyrFields[i]);
				layerField.setLayerfieldEn(lyrFields[i]);
				layerField.setKeyfield(key[0]);
				layerField.setLayer(layer);
				layerField.setIsactive(true);
				lyrFieldSet.add(layerField);
			}
			
			layer.setLayerField(lyrFieldSet);
			
			layerFieldService.deleteFeildByLayerId(layer.getLayerid());
			layerService.createLayer(layer);
			
		}catch(ServletRequestBindingException e){
			logger.error(e);
		}catch(Exception ex){
			logger.error(ex);
		}
		return null;
	}
	
/*	@RequestMapping(value="/studio/layer/getGeometryType/{id}", method=RequestMethod.GET)
	@ResponseBody
	public String getGeometryType(@PathVariable("id") String id){
		return layerService.getGeometryType(id);
	}*/
	
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
	
	@RequestMapping(value = "/studio/role/Allmodule/{id}", method = RequestMethod.GET)
	@ResponseBody
    public List<RoleModule> getAllRolesModule(@PathVariable String id){
		return 	roleModuleService.getRoleModuleByroleId(Integer.parseInt(id));
	}
	
}
