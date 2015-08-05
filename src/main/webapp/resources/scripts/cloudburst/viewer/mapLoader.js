var vector_point;
var map_static;
var gmap;
var wmsurl = null;

function mapLoader(){
	
	this.displaymap = function(wdpaid){
		
		
		var styleGeo = {
			    fillColor: '#000f',
			    fillOpacity: 0.1,
			    strokeWidth: 0
			};
		// pink tile avoidance
        OpenLayers.IMAGE_RELOAD_ATTEMPTS = 5;
        // make OL compute scale according to WMS spec
        OpenLayers.DOTS_PER_INCH = 25.4 / 0.28;

		map_static = new OpenLayers.Map('map2',
				{
				numZoomLevels: 20,
				controls: [],
				displayProjection: new OpenLayers.Projection("EPSG:4326"),
				allOverlays: true
				//numZoomLevel:18
				//projection: new OpenLayers.Projection("EPSG:900913")
				
				}
			);
	
		/*map_static.addControl(new OpenLayers.Control.Attribution());*/
		//map_static.addControl(new OpenLayers.Control.Pan());
		//map_static.addControl(new OpenLayers.Control.LayerSwitcher());
		//map_static.addControl(new OpenLayers.Control.PanZoomBar());
		
		//wmsurl=map.getLayersByName("spatial_unit")[0].url;
		wmsurl="http://localhost:8080/geoserver/wms?";
			sp_unit = new OpenLayers.Layer.WMS("spatial_unit", 
					wmsurl, 
					{ layers: "spatial_unit",
					transparent: "true",
					format: "image/png",
					allOverlays: true
					} ,
					{singleTile: true, ratio: 1}
					);
			
				/*	mbtile = new OpenLayers.Layer.WMS("vector_mbtiles", 
					wmsurl, 
					{ layers: "vector_mbtiles",
					transparent: "true",
					format: "image/png",
					isBaseLayer:true
					} ,
					{singleTile: true, ratio: 1}
					);
			vector_layer = new OpenLayers.Layer.WMS("World Heritage Sites", 
					wmsurl, 
					{ layers: "spatial_unit",
					transparent: "true",
					format: "image/png"
					} ,
					{singleTile: true,ratio: 1}
					);*/
				
			
					
	/*			gmap = new OpenLayers.Layer.Google('Google Streets',
						{type: google.maps.MapTypeId.STREETS}
						);
						gmap.animationEnabled=true;
					*/
				//var vector = new OpenLayers.Layer.Vector('vector');
				
				//map_static.addLayers([vector_layer,vector_point,vector,gmap]);
				map_static.addLayer(sp_unit);

			//console.log(map.getProjection());
		    
			
		    //addMarkerLayer(new OpenLayers.Geometry.Point(15677636.505529493, -4452363.047975198));
			//registerEvents(vector_point,wdpaid);
			zoomToSite('spatial_unit','Polygon','usin',wdpaid);
			//add google layer at the end
			
	};
	
	
}



function zoomToSite(relLayerName,layerType,fieldName,fieldVal){
	
	
	var layer = map_static.getLayersByName(relLayerName)[0];
	
	if(layer==null)
		return;
	
	lyrType=layerType;
	var wmsurl=map_static.getLayersByName(relLayerName)[0].url;
	var layerName='spatial_unit';
	var type;	
	var featureNS=getNS(layerName,wmsurl);
	var prefix;	
	var geomFieldName='the_geom';
	var featuresGeom1=null;
	var datapostResult = createmapXML();
	//var geometryName = 'the_geom';
	
	var pos = layerName.indexOf(":");
	prefix = layerName.substring(0, pos);
	type = layerName.substring(++pos);
	
	var filters=getStaticFilter(fieldName,fieldVal);
	var filter_1_0 = new OpenLayers.Format.Filter({
			version : "1.1.0"
		});


	var xml = new OpenLayers.Format.XML();
			var xmlFilter = xml.write(filter_1_0.write(filters));
			datapostResult = datapostResult.replace("##layer", '"' + "spatial_unit" + '"');
			datapostResult = datapostResult.replace("##featureNS", '"' + featureNS + '"');
			datapostResult = datapostResult.replace("##filter", xmlFilter);
			datapostResult = datapostResult.replace("##uniqueFld", fieldName);
		    //dataPost = dataPost.replace("${SortOrder}", "ASC");
			

		var mapProj = map_static.projection;
		var reverseCoords = false;
		if (mapProj == "EPSG:4326") {
			reverseCoords = false;
		}

		$.ajax({
					url : wmsurl,
					dataType : "xml",
					contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
					type : "POST",
					data : datapostResult,
					success : function(data) {
						var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
							xy : reverseCoords,
							featureType : 'spatial_unit',
							gmlns : 'http://www.opengis.net/gml',
							featureNS : featureNS,
							geometryName : geomFieldName, 
							featurePrefix : 'mast',
							extractAttributes : true
						}).read(data);
						
						if(gmlFeatures.length>0){
						   featuresGeom1=gmlFeatures[0].geometry;
						   zoomToActiveSite(featuresGeom1);
						}else{
							alert("Site not found on Map");
						}
	               }
	      });
					
}

function getStaticFilter(fieldName,fieldVal) {
	var filter;
	
	filter = new OpenLayers.Filter.Comparison({
        type: OpenLayers.Filter.Comparison.EQUAL_TO,
        matchCase: false,
        property: fieldName,
        value: fieldVal
    });
	
	return filter;
}

function getNS(layerName,url){
	
	if(url==null)
	return url;
var _wfsurl = url.replace(new RegExp( "wms", "i" ), "wfs");
var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + layerName;        

var featureNS='';
$.ajax({
	url: _wfsSchema,
	dataType: "xml",
	async:false,
	success: function (data) {
		var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
		var responseText = featureTypesParser.read(data);         
		featureNS = responseText.targetNamespace;
	}
});
return featureNS;

}
function zoomToActiveSite(curGeom) {
		var bounds = null;
		bounds = curGeom.getBounds();
		
		
		/*var newBounds = bounds.transform(new OpenLayers.Projection("EPSG:4326"),
				map_static.getProjectionObject());  */
	map_static.zoomToExtent(bounds,true);
	jQuery("#noth_coord").text(bounds.left);
	jQuery("#east_coord").text(bounds.bottom);
	//map_static.setCenter(new OpenLayers.LonLat(bounds.bottom,bounds.left),12);
}
function createmapXML() {
	var datapost = '';
	var maxFeatureCount = 1;
	datapost = "<wfs:GetFeature service=\"WFS\" version=\"1.1.0\" maxFeatures =\"" + maxFeatureCount + "\" " +
	"xmlns:gml=\"http://www.opengis.net/gml\" " +
	"xmlns:wfs=\"http://www.opengis.net/wfs\" " +
	"xmlns:ogc=\"http://www.opengis.net/ogc\" " +
	"xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\" " +
	"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +

	"<wfs:Query typeName=##layer xmlns:feature=##featureNS> ##filter" +
	"</wfs:Query>"+
	"</wfs:GetFeature>";

	

	return datapost;

}
