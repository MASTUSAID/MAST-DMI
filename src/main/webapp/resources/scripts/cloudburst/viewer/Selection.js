

var geometry;
var geomFieldName;
var clonedLayer;
var featureNS;
var activeLayer;
var resultFeatures;
var myfilter;
var activeProject=null;

function Selection(geom, layer){
	this.geometry = geom;
	this.resultFeatures = null;
	activeLayer = layer;
	getGeomFieldName();
	
}

function getGeomType(layer) {
	var layerType;
	var url = layer.url;
	url = replaceString(url, /wms/gi, 'wfs');
    $.ajax({
        url: PROXY_PATH + url + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + layerMap[layer.name],
        dataType: "text",
        async: false,
        success: function (text) {
            if (text.search(/gml:MultiPolygon/i) >= 0 || text.search(/gml:Polygon/i) >= 0) {
            	layerType = 'Polygon';
            } else if (text.search(/gml:MultiLineString/i) >= 0 || text.search(/gml:LineString/i) >= 0) {
            	layerType = 'LineString';
            } else if (text.search(/gml:MultiPogetGeomTypeint/i) >= 0 || text.search(/gml:Point/i) >= 0) {
            	layerType = 'Point';
            }else{
            	layerType = 'Polygon';
            }
        },
        error: function (xhr, status) {
        	if(layerMap[layer.name].indexOf("OSMM_") > -1){
        		jAlert("WFS operation on " + layerMap[layer.name] + " layer is restricted");
        	}else{
        		jAlert('Sorry, there is a problem!');
        	}
        }
    });
    return layerType;
}

function getGeomFieldName(){
	//var activeLayer = OpenLayers.Map.activelayer;
	var url = activeLayer.url;
	url = replaceString(url, /wms/gi, 'wfs');
    url = url + "&request=DescribeFeatureType&version=1.0.0&service=WFS&typename=" + layerMap[activeLayer.name];
    
    $.ajax({
        url: PROXY_PATH + url,
        async: false,
        success: function (data) {
            var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
            var responseText = featureTypesParser.read(data);
            var featureTypes = responseText.featureTypes;
            featureNS = responseText.targetNamespace;
            
            for (var i = 0; i < featureTypes[0].properties.length; ++i) {
                if (featureTypes[0].properties[i].type.indexOf("gml")>=0) {
                	geomFieldName = featureTypes[0].properties[i].name;
                	break;
				}
			}
        },
        error: function (xhr, status) {
        	if(layerMap[activeLayer.name].indexOf("OSMM_") > -1){
        		jAlert("WFS operation on " + layerMap[activeLayer.name] + " layer is restricted");
        	}else{
        		jAlert('Sorry, there is a problem!');
        	}
        }
    });
}

Selection.prototype.creationSelectionCriteria = function(object){
	var filter = null;
	 if (object.handler instanceof OpenLayers.Handler.RegularPolygon) {
		 var bounds = this.geometry.getBounds();
		    filter = new OpenLayers.Filter.Spatial({
		        type: OpenLayers.Filter.Spatial.INTERSECTS,//OpenLayers.Filter.Spatial.BBOX,
		        value: bounds,
		        property: geomFieldName,
		        projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
		    });
	 }else if (object.handler instanceof OpenLayers.Handler.Polygon){
		 filter = new OpenLayers.Filter.Spatial({
		        type:  OpenLayers.Filter.Spatial.INTERSECTS,
		        value:  this.geometry.geometry,
		        property: geomFieldName,
		        projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
		    });
	 }else if (object.handler instanceof OpenLayers.Handler.Point){
			  if(object.handler.CLASS_NAME=="OpenLayers.Handler.Path"){
					filter = new OpenLayers.Filter.Spatial({
						type:  OpenLayers.Filter.Spatial.INTERSECTS,
						value:  this.geometry.geometry,
						property: geomFieldName,
						projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
					});
				}
				else{
					var point = this.geometry.geometry;
				  var lonlat = new OpenLayers.LonLat(point.x, point.y);
				  var pixel = map.getPixelFromLonLat(lonlat);
				  
				  var _x1 = pixel.x - 4;
				  var _y1 = pixel.y - 4;
				  var _x2 = pixel.x + 4;
				  var _y2 = pixel.y + 4;

				  var _lonlat1 = map.getLonLatFromPixel(new OpenLayers.Pixel(_x1, _y1));
				  var _lonlat2 = map.getLonLatFromPixel(new OpenLayers.Pixel(_x2, _y2));

				  var bounds = new OpenLayers.Bounds(); 
				  bounds.extend(_lonlat1); 
				  bounds.extend(_lonlat2);
				  
				 filter = new OpenLayers.Filter.Spatial({
						type:  OpenLayers.Filter.Spatial.INTERSECTS, //OpenLayers.Filter.Spatial.BBOX,
						value:  bounds, //this.geometry.geometry.getBounds(),
						property: geomFieldName,
						projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
					});
				}
	 }
	 else{
		 filter = new OpenLayers.Filter.Spatial({
		        type:  OpenLayers.Filter.Spatial.INTERSECTS,
		        value:  this.geometry,
		        property: geomFieldName,
		        projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
		    });
	 }
	 
	 return filter;
}


Selection.prototype.displaySelection = function(filter, style, activeLayer){
	//layerType = getGeomType(OpenLayers.Map.activelayer);
	layerType = getGeomType(activeLayer);
	
	clonedLayer = map.getLayersByName("clone")[0];
	if(clonedLayer != undefined){
		map.removeLayer(clonedLayer);
	}
	
	var sld = createSelectionSLD(filter, style, layerType, activeLayer);
	
	//Post the SLD
    $.ajax({
        type: 'POST',
        url: "theme/createSLD",
        dataType: "text",
        data: { data: escape(sld) },
        success: function (result) {
            sld_result = result;
             var layerOptions = null;
            layerOptions = OpenLayers.Util.applyDefaults(layerOptions, {
                displayInLayerSwitcher: false,
                tileOptions: {
                    maxGetUrlLength: 2048
                }
            });

            var layer = activeLayer; //OpenLayers.Map.activelayer;
            var clonedLayer = layer.clone();
            clonedLayer.setName("clone");
            map.addLayer(clonedLayer);
            clonedLayer.mergeNewParams({
                SLD: sld_result
            });
            clonedLayer.redraw(true);
        },
        error: function (xhr, status) {
            jAlert('Sorry, there is a problem!');
        }
    });
}

Selection.prototype.displayResult = function(filter, displayInGrid){
	var layerName = activeLayer.name; //OpenLayers.Map.activelayer.name;
	 $.ajax({
         url: STUDIO_URL + 'layer/' + layerName + "?" + token,
         async:false,
         success: function (data) {
        	 var uniqueField;
        	 var displayableCols = data.layerFields;
        	    if(displayableCols.length > 0)
        	    	uniqueField = displayableCols[0].keyfield;
        	    
        	   if(displayableCols.length > 0 && uniqueField != undefined){
        		   result = new Result(layerName, featureNS, myfilter, true, undefined, undefined);
        		   if(activeLayer.name!='spatial_unit')
        		   result = new Result(layerName, featureNS, filter, true, undefined, undefined);
        		   
                 //  sortResultInDesc(filter, result, layerMap[layerName]);
                   result.displayResult(displayableCols, uniqueField, geomFieldName, displayInGrid);
        	   }
         }
	 });
}

Selection.prototype.getResult = function(filter, displayInGrid){
	var layerName = activeLayer.name; //OpenLayers.Map.activelayer.name;
	var result_data = null;
	 $.ajax({
        url: STUDIO_URL + 'layer/' + layerName + "?" + token,
        async:false,
        success: function (data) {
       	 var uniqueField;
       	 var displayableCols = data.layerFields;
       	    if(displayableCols.length > 0)
       	    	uniqueField = displayableCols[0].keyfield;
       	    
       	   if(displayableCols.length > 0 && uniqueField != undefined){
       		   result = new Result(layerName, featureNS, filter, true, undefined, undefined);
                  //sortResultInDesc(filter, result);
                  result_data = result.getQueryResult(displayableCols, uniqueField, 
                		  	geomFieldName, displayInGrid);
                  
       	   }
        }
	 });
	 return result_data;
}

function createSelectionSLD(filter, style, layerType, activeLayer){
/* 	var symbolizer;
	//var activeLayer = activeLayer; //OpenLayers.Map.activelayer;

	if(layerType == 'Point'){
		symbolizer = {Point: style['Point']};
	}else if(layerType == 'Polygon'){
		symbolizer = {Polygon: style['Polygon']};
	}else if(layerType == 'LineString'){
		symbolizer = {Line: style['Line']};
	} */
	
		var highlightSymbolizer;
	if (layerType.indexOf('Polygon') >= 0) {
		highlightSymbolizer = {
			Polygon : {
				strokeColor : '#07FCFB',
				fill : true,
				fillColor : "#FFFFFF",
				strokeWidth : 2,
				fillOpacity : 0.1
			}
		};
	} else if (layerType.indexOf('Line') >= 0) {
		highlightSymbolizer = {
			Line : {
				strokeColor : '#07FCFB',
				strokeWidth : 2
			}
		};

	} else if (layerType.indexOf('Point') >= 0) {
		highlightSymbolizer = {
			Point : {
				graphicName : 'circle',
				fill : false,
				strokeWidth : 2,
				strokeColor : '#07FCFB',
				pointRadius : 5
			}
		};
	}
	var sld_rules = [];
	var sld = {
	        version: "1.0.0",
	        namedLayers: {}
	    };
	
	var layerName = layerMap[activeLayer.name];
    sld.namedLayers[layerName] = {
		        name: layerName,
		        userStyles: []
		      };
    
    var rule = new OpenLayers.Rule({
        title: "Feature Selected",
        symbolizer: highlightSymbolizer,
        filter: filter
    });
    
    if(activeLayer.name=='spatial_unit')
    	{
    	
    	 myfilter = new OpenLayers.Filter.Logical({
    	        type: OpenLayers.Filter.Logical.AND,
    	        filters: [filter,
    	           new OpenLayers.Filter.Comparison({
    	              type: OpenLayers.Filter.Comparison.EQUAL_TO,
    	              property:  "project_name",
    	              value: activeProject
    	            })
    	          ]
    	     });
    	
    	 
    	  var rule = new OpenLayers.Rule({
    	        title: "Feature Selected",
    	        symbolizer: highlightSymbolizer,
    	        filter: myfilter
    	    });
    	}
    
    
    
    
  
    sld_rules.push(rule);
    sld.namedLayers[layerName].userStyles.push({
        rules: sld_rules
    });
    return new OpenLayers.Format.SLD().write(sld);
}

function viewProjectName(project)

{
activeProject=project;	

}
