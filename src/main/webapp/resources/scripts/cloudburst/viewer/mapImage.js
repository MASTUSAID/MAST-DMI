function mapImage(ID)
{

	var highlightLayer;


	map_static = new OpenLayers.Map('',
			{
		numZoomLevels: 20,
		controls: [],
		displayProjection: new OpenLayers.Projection("EPSG:4326"),
		allOverlays: true
		//numZoomLevel:18
		//projection: new OpenLayers.Projection("EPSG:900913")

			});
	sp_unit = new OpenLayers.Layer.WMS("spatial_unit", 
			wmsurl, 
			{ layers: "spatial_unit",
		transparent: "true",
		format: "image/png",
		allOverlays: true
			} ,
			{singleTile: true, ratio: 1}
	);
	highlightLayer = new OpenLayers.Layer.Vector("Highlighted Features", {
		displayInLayerSwitcher: false, 
		isBaseLayer: false 
	}
	);
	map_static.addLayers([sp_unit,highlightLayer]);
	//zoomToSite('spatial_unit',,'usin',ID);

	var layer = map_static.getLayersByName('spatial_unit')[0];
	var layerType='Polygon';
	var fieldName='usin';
	var fieldVal=ID;
	//var layer = map_static.layers[0];
	//url=layer.url;
	var wmsurl="http://"+location.host+"/geoserver/wms?";
	var layerName='spatial_unit';
	var featureNS=getNS(layerName,wmsurl);
	var prefix;	
	var geomFieldName='the_geom';
	var featuresGeom1=null;
	createXML();
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
	dataPost = dataPost.replace("${layer}", "\"" + "spatial_unit" + "\"");
	dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
	dataPost = dataPost.replace("${filter}", xmlFilter);
	dataPost = dataPost.replace("${uniqueFld}", fieldName);
	valAsc = "ASC";
	dataPost = dataPost.replace("${SortOrder}", valAsc);


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
		async: false,
		data : dataPost,
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

			/* if(gmlFeatures.length>0){ */
			featuresGeom1=gmlFeatures[0].geometry;
			var vertexlist1=featuresGeom1.getVertices();
			var vertextemp=[];
			var bounds = null;

			//Proj4js.defs["EPSG:21036","+proj=utm +zone=36 +south +ellps=clrk80 +units=m +no_defs"];
			Proj4js.defs["EPSG:21036"] = "+proj=utm +zone=36 +south +ellps=clrk80 +units=m +no_defs";
			//calculate area from DD to meter sq and convert to Ha
			var epsg21036 = new OpenLayers.Projection('EPSG:21036');

			var epsg4326   = new OpenLayers.Projection('EPSG:4326');

			var meterGeom = gmlFeatures[0].geometry.clone();
			meterGeom = meterGeom.transform(epsg4326,epsg21036);
			vertexlist=meterGeom.getVertices();
			/*    for (var int = 0; int < vertexlist1.length; int++) {
					    	 vertextemp.push(vertexlist1[int].x);
							vertextemp.push(vertexlist1[int].y);
						}
					     vertextemp.push(9.000);

					     var serverData = {"cordinateString": vertexlist};
					    	jQuery.ajax({ 
					    		url: "landrecords/vertexlabel/",
					    		contentType: "application/json; charset=utf-8",
					    		 dataType: "json",
					    		async:false,	
					    		type: 'POST',
					    		data:  serverData,
					    		success: function (data) {	

					    			console.log(data);
					    		}


					    	});*/
			var tempStr="";
			for(var i=0;i<vertexlist1.length;i++)
			{
				if (tempStr=="") {
					tempStr = vertexlist1[i].x + "," + vertexlist1[i].y;

				} 
				else {
					tempStr = tempStr + "," + vertexlist1[i].x + "," + vertexlist1[i].y;
				}
			}
			var serverData = {"vertexList":tempStr};
			$.ajax({

				type : 'POST',
				url: "landrecords/vertexlabel",
				data: serverData,
				async:false,
				success: function(data){

				}
			});

			var area=0.000247105*(meterGeom.getArea());
			area=area.toFixed(3); 
			$("#area_id").text(area);
			$("#_idarea").text(area);

			bounds = featuresGeom1.getBounds();

			var newBounds=createSquareBounds(bounds);
			newBounds.left=newBounds.left-0.00017;
			newBounds.bottom=newBounds.bottom-0.00017;
			newBounds.right=newBounds.right+0.00017;
			newBounds.top=newBounds.top+0.00017;
			bbox = newBounds.left + ',' + newBounds.bottom
			+ ',' + newBounds.right + ','
			+ newBounds.top;
			var lyrs="";
			lyrs = lyrs + layerMap[layer.name];


			//var layerurl_ccro = wmsurl +"bbox="+bbox+"&FORMAT=image/png&REQUEST=GetMap&layers=" + lyrs + "width=357&height=267&srs=EPSG:4326";
			var layerurl_ccro = wmsurl +"bbox="+bbox+"&FORMAT=image/png&REQUEST=GetMap&layers=" + lyrs + ",mast:vertexlabel&width=250&height=250&srs=EPSG:4326"+"&cql_filter=usin="+ID+";INCLUDE";
			//var layerurl_adj = wmsurl +"bbox="+bbox+"&FORMAT=image/png&REQUEST=GetMap&layers=" + lyrs + "width=248&height=248&srs=EPSG:4326";
			var layerurl_adj = wmsurl +"bbox="+bbox+"&FORMAT=image/png&REQUEST=GetMap&layers=" + lyrs + ",mast:vertexlabel&width=180&height=180&srs=EPSG:4326"+"&cql_filter=usin="+ID+";INCLUDE";



			$('#noth_coord').text(meterGeom.getBounds().getCenterLonLat().lat.toFixed(3));
			$('#east_coord').text(meterGeom.getBounds().getCenterLonLat().lon.toFixed(3));
			$('#map1').append('<img id="theImg" src='+layerurl_adj+'>');
			$('#map-ccro').append('<img id="theImg" src='+layerurl_ccro+'>');


			/* 	}else{
							alert("Site not found on Map");
						} */
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
/*function createXML() {
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

}*/


function createSquareBounds(inBounds) {

	//get centroid of bounds
	var newLeft;
	var newRight;
	var newBottom;
	var newTop;
	var outBounds;
	var centroidLatLong = inBounds.getCenterLonLat();

	//get the larger side of the bound(Rectangle)
	if (inBounds.getWidth() > inBounds.getHeight()){
		//calculate square bounds
		newLeft = inBounds.toArray()[0];
		newBottom = centroidLatLong.lat - inBounds.getWidth()/2;
		newRight = inBounds.toArray()[2];
		newTop = centroidLatLong.lat + inBounds.getWidth()/2;
		outBounds = new OpenLayers.Bounds(String(newLeft), String(newBottom), String(newRight), String(newTop));
		return outBounds;

	}else if (inBounds.getWidth() < inBounds.getHeight()){
		//calculate square bounds
		newLeft =  centroidLatLong.lon - inBounds.getHeight()/2;
		newBottom = inBounds.toArray()[1];
		newRight = centroidLatLong.lon + inBounds.getHeight()/2;
		newTop = inBounds.toArray()[3];
		outBounds =new OpenLayers.Bounds(String(newLeft), String(newBottom), String(newRight), String(newTop));
		return outBounds;

	} else{
		outBounds = inBounds;
		return outBounds;
	}


}

