

var map;
var FieldNames;
var featureNS;
var type;
var filter;
var actualLayerName;
var displayLayerName;
var lyrDetails;
var url;
var searchResult = [];
var displayableCols;
var uniqueField;
var searchdiv;
var boundControls = null;
var selected_vector = null;
var layerType = null;
var selectionFilter = null;

/*var selectionSymbolizer = {
		 "Polygon": {strokeWidth: 2, strokeOpacity: 1, strokeColor: "#CC9900", fillColor: "#CC9900", fillOpacity: 0.3, strokeLinecap: "square", strokeDashstyle: "solid"},
		 "RegularPolygon": {strokeWidth: 2, strokeOpacity: 1, strokeColor: "#CC9900", fillColor: "#CC9900", fillOpacity: 0.3}
		};*/

var style = new OpenLayers.Style();
style.addRules([new OpenLayers.Rule({ symbolizer: selectionSymbolizer })]);
var styleMap = new OpenLayers.StyleMap({ "default": style });

Cloudburst.Search = function (_map, _searchdiv) {

    //if($("#searchdiv").length<=0){
    map = _map;
    searchdiv = _searchdiv;
    showResultsinDialog = true;
    
//    removeChildElement("sidebar", "layerSwitcherContent");
	$("#tabs-Tool").empty();
    
    jQuery.get('resources/templates/viewer/search.html', function (template) {
        
		//Add tad
		addTab($._('Search'),template);
		
		toggleButtons();
		
		
		$("#search-help").tipTip({defaultPosition:"right"});
		
        // Event binding for options div
        $("#options-s-d").hide();
        $("#options-s-t").click(function () {
            $("#options-s-d").slideToggle('fast');
        });
        
        $("#boundButtons").hide();
        $("#options-search-extent").click(function () {
            $("#boundButtons").slideToggle('fast');
        });
        
        var layers = map.layers;

        $('#Layers').empty();
        $('#Layers').append(
        $("<option></option>").attr("value", "Please Select").text("Please Select"));
        for (var i = 0; i < layers.length; i++) {

        	if ((layers[i] instanceof OpenLayers.Layer.WMS) && 
        			layers[i].name != 'Cosmetic' && layers[i].name != 'Dummy' && 
        			layers[i].name != 'Markers' && layers[i].name != 'clone' && 
        			layers[i].name.indexOf("Google_") == -1) {
        		
        		if(layers[i].queryable){
        			$('#Layers').append($("<option></option>").attr("value", layers[i].name).text(
        					//layers[i].name));
        					($._(layers[i].name) == "")?layers[i].name:$._(layers[i].name)));
        		}
            }

        }
        
        //Remove the previously added selected_vector layer
        selected_vector = map.getLayersByName("selected_vector")[0];
    	if(selected_vector != undefined){
    		map.removeLayer(selected_vector);
    	}
        
        //Create Vector object for drawing extents
        objLayer = OpenLayers.Map.activelayer;
        var _wfsurl = objLayer.url.replace(new RegExp( "wms", "i" ), "wfs");
        var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + layerMap[objLayer.name];
        
        var auth = make_basic_auth('admin@eryri-npa.gov.uk','P4rC3ryr1');
        /*Get Geometry column name, featureTypes, targetNamespace for the selected layer object*/
        $.ajax({
            url: PROXY_PATH + _wfsSchema,
            //headers : { Authorization : auth },
            dataType: "xml",
            async:false,
            success: function (data) {
                var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
                var responseText = featureTypesParser.read(data);
                var featureTypes = responseText.featureTypes;
                targetNamespace = responseText.targetNamespace;
                featPrefix = responseText.targetPrefix;
                featureTypesFields = featureTypes[0].properties;

                for (var i = 0; i < featureTypes[0].properties.length; ++i) {
                    if (featureTypes[0].properties[i].type.indexOf('gml')>=0) {
                        geometryColName = featureTypes[0].properties[i].name;
                        break;
                    }
                }
                
                //Get the layer type
                getLayerType(objLayer, _wfsurl);
            }
        });
        var actualLayerName = layerMap[objLayer.name];
        var pos = actualLayerName.indexOf(":");
        var featType = null;
        if (pos > 1)
            featType = actualLayerName.substring(pos + 1);
        else
            featType = actualLayerName;
        
        var _projection = new OpenLayers.Projection(objLayer.projection.projCode);
        var _protocol = new OpenLayers.Protocol.WFS({
        	//headers: { Authorization : "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE="},
            version: "1.1.0",
            srsName: objLayer.projection.projCode,
            url: _wfsurl,
            featureType: featType,
            geometryName: geometryColName,
            featurePrefix: featPrefix,
            featureNS: targetNamespace,
            schema: _wfsSchema
        });
        
        selected_vector = new OpenLayers.Layer.Vector(
                "selected_vector", {
                    reportError: true,
                    projection: _projection,
                    protocol: _protocol,
                    isBaseLayer: false,
                    visibility: true,
                    styleMap: styleMap,
                    displayInLayerSwitcher: false
                }
            );
            map.addLayers([selected_vector]);
            
        
        boundControls = {        
        		polygon: new OpenLayers.Control.DrawFeature(
        				selected_vector, OpenLayers.Handler.Polygon, {
        					displayClass:"olControlDefault",
    	                    callbacks: {
    	                        done: function (p) {
    	                        	var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
    	                            var polyFeature = new OpenLayers.Feature.Vector(multipolygon);
    	                            removeBoundExtentFeature();
    	                            selected_vector.addFeatures([polyFeature]);
    	                          
    	                            selectionFilter = new OpenLayers.Filter.Spatial({
    	                		        type:  OpenLayers.Filter.Spatial.INTERSECTS,
    	                		        value: polyFeature.geometry,
    	                		        property: geometryColName,
    	                		        projection: _projection
    	                		    });
    	                        }
    	                    }
    	                }),
        		rectangle: new OpenLayers.Control.DrawFeature(
        				selected_vector, OpenLayers.Handler.RegularPolygon, {
        					handlerOptions: {
        					sides: 4,
        					irregular:true
		            },
		            displayClass:"olControlDefault",
		            callbacks: {
		                done: function (p) {
		                	var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
		                	var polyFeature = new OpenLayers.Feature.Vector(multipolygon);
		                	removeBoundExtentFeature();
		                	selected_vector.addFeatures([polyFeature]);
		                	
		                	var bounds = polyFeature.geometry.getBounds();
		                	selectionFilter = new OpenLayers.Filter.Spatial({
		        		        type: OpenLayers.Filter.Spatial.BBOX,
		        		        value: bounds,
		        		        property: geometryColName,
		        		        projection: _projection
		        		    });
		                }
		            }
		        })
            };
        
        for (var key in boundControls) {
            map.addControl(boundControls[key]);
        }
        
        $("#boundButtons button").bind("click", function(e) {
			for ( var key in boundControls) {
				var control = boundControls[key];
				control.deactivate();
			}
			switch (e.currentTarget.id) {
			case 'polygon':
				toggleBoundControl('polygon');
				break;
			case 'rectangle':
				toggleBoundControl('rectangle');
				break;
			case 'clearBounds':
				removeBoundExtentFeature();
				selectionFilter = null;
			}
				
		});

        translateSearchStrings();
    });
};

function make_basic_auth(user, password) {
	  var tok = user + ':' + password;
	  var hash = Base64.encode(tok);
	  return "Basic " + hash;
}

function translateSearchStrings(){
	$('#search_1').html($._('Search'));
	$('#search_layer').html($._('search_layer'));
	$('#search_fields').html($._('search_fields'));
	$('#options-search-extent').html($._('search_extentboundary'));
	$('#options-s-t').html($._('search_typeoptions'));
	$('#search_exact').html($._('search_exact'));
	$('#search_similar').html($._('search_similar'));
	$('#search_1').attr("value",$._('Search'));
}

function getLayerType(layer, wfsurl) {
    $.ajax({
        url: PROXY_PATH + wfsurl + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + layerMap[layer.name],
        dataType: "text",
        async: false,
        success: function (text) {
            if (text.search(/gml:MultiPolygon/i) >= 0 || text.search(/gml:Polygon/i) >= 0) {
            	layerType = 'Polygon';
            } else if (text.search(/gml:MultiLineString/i) >= 0 || text.search(/gml:LineString/i) >= 0) {
            	layerType = 'LineString';
            } else if (text.search(/gml:MultiPoint/i) >= 0 || text.search(/gml:Point/i) >= 0) {
            	layerType = 'Point';
            } else{
            	lyrType = 'Polygon';
            }
        }
    });
}

var toggleBoundControl = function(element) {
	
	for (key in boundControls) {
		var control = boundControls[key];
		if (element == key) {
			control.activate();
		} else {
			control.deactivate();
		}

	}
	
	/* Deactive markup controls*/
	for (key1 in markupControls) {
		var control = markupControls[key1];
		control.deactivate();
	}
	/*Deactive map controls*/
	for (mapKey in mapControls) {
		var control = mapControls[mapKey];
		control.deactivate();
	}
}


var GetFields_Search = function (a) {
        var selected = $("#Layers option:selected");
        actualLayerName = selected.val();
        if (actualLayerName.toUpperCase() == "PLEASE SELECT") {
            $('#LayersFields').empty();
            // $('#LayersFields').append(new Option("Please Select", "Please
            // Select", true, true));
            $('#LayersFields').append(
            $("<option></option>").attr("value", "Please Select").text("Please Select"));
        } else {
            $.ajax({
                url: STUDIO_URL + 'layer/' + actualLayerName + "?" + token,
                success: function (data) {
                	console.log(data);
                    url = map.getLayersByName(actualLayerName)[0].url;
                    /*
                     * if (url.indexOf('wms?') > -1) url = url.replace("wms",
                     * "wfs");
                     */
                    url = replaceString(url, /wms/gi, 'wfs');

                    lyrDetails = data;
                    if(lyrDetails.layerFields.length != 0){
	                    displayableCols = lyrDetails.layerFields;
	                    uniqueField = lyrDetails.layerFields[0].keyfield;
	
	                    //Amitabh (Temporary)
	                    layerMap[actualLayerName] = data.name;
	                    displayLayerName = layerMap[actualLayerName];
	                    var pos = displayLayerName.indexOf(":");
	                    prefix = displayLayerName.substring(0, pos);
	                    type = displayLayerName.substring(++pos);
	                    var selectedLayer = layerMap[$("#Layers").val()];
	                   // populateLayerFields(displayableCols);
	                    GetLayerFields_search(selectedLayer, displayableCols);
                    }else{
                    	if(layerMap[actualLayerName].indexOf("OSMM_") > -1){
                    		jAlert("WFS operation on " + layerMap[actualLayerName] + " layer is restricted");
                    	}else{
                    		jAlert('Sorry, there is a problem!');
                    	}
                    }
                }
            });
        }
    };
    
var populateLayerFields = function(FieldNames1){
	  $('#LayersFields').empty();
      // $('#LayersFields').append(new Option("All", "All", true, true));
      $('#LayersFields').append(
      $("<option></option>").attr("value", "All").text("All"));
      for (var i = 0; i < FieldNames1.length; ++i) {
          /*if (FieldNames1[i].type.indexOf("gml:GeometryPropertyType")>=0) {
              var l = displayableCols.length;
              displayableCols[l] = []
              var obj = new Object();
              obj.Name = FieldNames1[i];
              obj.Alias = FieldNames1[i].name;
              displayableCols[l] = obj;
              continue;
          }*/
          // $('#LayersFields').append(new Option(FieldNames1[i].name,
          // FieldNames1[i].name, true, true));
          $('#LayersFields').append(
          $("<option></option>").attr("value", FieldNames1[i].field).text(
          		//FieldNames1[i].name));
          		($._(FieldNames1[i].alias) == "")?FieldNames1[i].alias:$._(FieldNames1[i].alias)));
      }
      if (FieldNames1.length > 0) {
          $('#LayersFields').get(0).selectedIndex = 0;
      }
}

var PopulateLayerFields_Search = function (FieldNames1) {
        $('#LayersFields').empty();
        // $('#LayersFields').append(new Option("All", "All", true, true));
        $('#LayersFields').append(
        $("<option></option>").attr("value", "All").text("All"));
        for (var i = 0; i < FieldNames1.length; ++i) {
            if (FieldNames1[i].type.indexOf("gml:GeometryPropertyType")>=0) {
                var l = displayableCols.length;
                displayableCols[l] = []
                var obj = new Object();
                obj.Name = FieldNames1[i].name;
                obj.Alias = FieldNames1[i].name;
                displayableCols[l] = obj;
                continue;
            }
            // $('#LayersFields').append(new Option(FieldNames1[i].name,
            // FieldNames1[i].name, true, true));
            $('#LayersFields').append(
            $("<option></option>").attr("value", FieldNames1[i].field).text(
            		//FieldNames1[i].name));
            		($._(FieldNames1[i].alias) == "")?FieldNames1[i].alias:$._(FieldNames1[i].alias)));
        }
        if (FieldNames1.length > 0) {
            $('#LayersFields').get(0).selectedIndex = 0;
        }
    };

var GetLayerFields_search = function (selectedLayer1, displayableCols) {
        $.ajax({
            url: PROXY_PATH + url + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + selectedLayer1 + "&maxFeatures=1",
            success: function (data) {
                var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
                var responseText = featureTypesParser.read(data);
                var featureTypes = responseText.featureTypes;
                FieldNames = featureTypes[0].properties;
                featureNS = responseText.targetNamespace;
                //PopulateLayerFields_Search(FieldNames);
                populateLayerFields(displayableCols);
            }
        });
    };

var SearchIn = function () {
        var selectedLayer = $("#Layers").val();
        var selectedField = $("#LayersFields").val();
        var searchText = $("#searchText").val();
        var selectedSearchType = $("#options-s-d input[@name=option]:checked").attr('id');
        var pos;
        var prefix;

        if (selectedLayer.toUpperCase() == "PLEASE SELECT") {
            jAlert("Please select the layer");
        } else {
            var lyrName = layerMap[selectedLayer];
            pos = lyrName.indexOf(":");
            prefix = lyrName.substring(0, pos);
            type = lyrName.substring(++pos);
            if (selectedField.toUpperCase() == "ALL") {
                filter = CreateFilter(selectedSearchType, searchText);
                
                if(selectionFilter != null || selectionFilter != undefined){
                	filter = new OpenLayers.Filter.Logical({
                	    type: OpenLayers.Filter.Logical.AND,
                	    filters:[filter, selectionFilter]
                	});
                }
                
                var result = new Result(actualLayerName, featureNS, filter, showResultsinDialog);
                sortInDesc(filter, result, selectedLayer);
                result.displayResult(displayableCols, uniqueField);
                
            } else {
                if (selectedSearchType.toUpperCase() == "SIMILAR") {
                    filter = new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.LIKE,
                        matchCase: false,
                        property: selectedField,
                        value: searchText + '%'
                    })
                } else {
                    filter = new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        matchCase: false,
                        property: selectedField,
                        value: searchText
                    })
                }
                if(selectionFilter != null || selectionFilter != undefined){
                	filter = new OpenLayers.Filter.Logical({
                	    type: OpenLayers.Filter.Logical.AND,
                	    filters:[filter, selectionFilter]
                	});
                }
                // getFeatures_search([lyrName], selectedField);
                var result = new Result(actualLayerName, featureNS, filter, showResultsinDialog);
                sortInDesc(filter, result, selectedLayer);
                result.displayResult(displayableCols, uniqueField);
            }
        }
    };
    
 function getSearchDataType(searchText){
	 var pos = searchText.indexOf('.');
	 if (pos > -1){
		 return 'DOUBLE';
	 }else{
		 var valid = true;  
		 var replaceSep = "";
		 var parseDate = false;
		 date = searchText;
		 if(date.indexOf('-') > -1){
			 replaceSep = '-';
			 parseDate = true;
		 }else if(date.indexOf('/') > -1){
			 replaceSep = '/';
			 parseDate = true;
		 }else{
			 parseDate = false;
		 }
			
		 if(parseDate == true){
			 date = date.replace(replaceSep, ''); 
			 var year  = parseInt(date.substring(0, 4)); 
			 var month = parseInt(date.substring(4, 6));         
			 var day   = parseInt(date.substring(7, 9));         
			          
				
			 if((month < 1) || (month > 12)) valid = false;         
			 else if((day < 1) || (day > 31)) valid = false;        
			 else if(((month == 4) || (month == 6) || (month == 9) || (month == 7) || (month == 11)) && (day > 30)) valid = false;        
			 else if((month == 1) && (((year % 400) == 0) || ((year % 4) == 0)) && ((year % 100) != 0) && (day > 29)) valid = false;         
			 //else if((month == 1) && ((year % 100) == 0) || (day > 28)) valid = false;
		 }else{
			 valid = false;
		 }
		
		 if(valid){
			 return 'DATE';
		 }else{
			 if(searchText.toUpperCase() == 'FALSE' || searchText.toUpperCase() == 'TRUE'){
				 return 'BOOLEAN';
			 }else{
				 return 'INT';
			 }
		 }
	 }
 }

var CreateFilter = function (selectedSearchType1, searchText1) {
        var filterArray = [];
        var searchText1Checking = searchText1.search(/[a-zA-Z]/);
        var searchTextDataTypeString = false;
        if (searchText1Checking > -1) {
            searchTextDataTypeString = true;
        }

        dataType = getSearchDataType(searchText1);
        switch (selectedSearchType1.toUpperCase()) {
        case "EXACT":
            for (var k = 0; k < FieldNames.length; ++k) {
                if (FieldNames[k].localType != "GeometryPropertyType" && 
                		FieldNames[k].localType != "MultiPolygonPropertyType" && 
                		FieldNames[k].localType != "MultiPointPropertyType" &&
                		FieldNames[k].localType != "MultiLinePropertyType" &&
                		FieldNames[k].localType != "Polygon" &&
                		FieldNames[k].localType != "Line" &&
                		FieldNames[k].localType != "Point") {
                    if (searchTextDataTypeString) {
                        if (FieldNames[k].localType.toUpperCase() == 'STRING') {
                            var filter1 = new OpenLayers.Filter.Comparison({
                                type: OpenLayers.Filter.Comparison.EQUAL_TO,
                                matchCase: false,
                                property: FieldNames[k].name,
                                value: searchText1
                            });
                            filterArray.push(filter1);
                        }
                    } else {
                    	 if (FieldNames[k].localType.toUpperCase() != 'STRING') {
                    		 if(FieldNames[k].localType.toUpperCase() == dataType){
			                        var filter1 = new OpenLayers.Filter.Comparison({
			                            type: OpenLayers.Filter.Comparison.EQUAL_TO,
			                            matchCase: false,
			                            property: FieldNames[k].name,
			                            value: searchText1
			                        });
			                        filterArray.push(filter1);
                    		 }
                    	 }
                    }
                }
            }
            break;
        case "SIMILAR":
            for (var k = 0; k < FieldNames.length; ++k) {
                if (FieldNames[k].localType != "GeometryPropertyType") {
                    if (searchTextDataTypeString) {
                        if (FieldNames[k].localType.toUpperCase() == 'STRING') {
                            var filter1 = new OpenLayers.Filter.Comparison({
                                type: OpenLayers.Filter.Comparison.LIKE,
                                matchCase: false,
                                property: FieldNames[k].name,
                                value: searchText1 + '%'
                            });
                            filterArray.push(filter1);
                        }
                    } else {
                        var filter1 = new OpenLayers.Filter.Comparison({
                            type: OpenLayers.Filter.Comparison.LIKE,
                            matchCase: false,
                            property: FieldNames[k].name,
                            value: searchText1 + '*'
                        });
                        filterArray.push(filter1);
                    }
                }
            }
            break;
        default:
            break;
        }

        filter = new OpenLayers.Filter.Logical({
            type: OpenLayers.Filter.Logical.OR,
            filters: filterArray
        });
        return filter;
    };
    
    function removeBoundExtentFeature(){
    	selected_vector.removeAllFeatures();
    	selectionFilter = null;
    }

    var visible = function (status) {
        $("#" + searchdiv).empty();
    };
	
   function sortInDesc(filters, result, selectedLayer) {

        var filter_1_0 = new OpenLayers.Format.Filter({
            version: "1.1.0"
        });

        var dataPost=createThematicXML();
        var xml = new OpenLayers.Format.XML();
        var xmlFilter = xml.write(filter_1_0.write(filters));

        dataPost = dataPost.replace("${layer}", "\"" + layerMap[selectedLayer] + "\"");
        dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
        dataPost = dataPost.replace("${filter}", xmlFilter);
        dataPost = dataPost.replace("${uniqueFld}", uniqueField);

        dataPost = dataPost.replace("${SortOrder}", "DESC");

        var mapProj = map.projection;
        var reverseCoords = true;
        if (mapProj == "EPSG:4326") {
            reverseCoords = false;
        }

        var _url = PROXY_PATH + url;
        $.ajax({
            url: _url,
            dataType: "xml",
            contentType: "text/xml; subtype=gml/3.1.1; charset=utf-8",
            type: "POST",
            data: dataPost,
            async:false,
            success: function (data) {
                var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
                    xy: reverseCoords,
                    featureType: type,
                    gmlns: 'http://www.opengis.net/gml',
                    featureNS: featureNS,
                    geometryName: geomFieldName, //displayableCols[displayableCols.length - 1],
                    featurePrefix: prefix,
                    extractAttributes: true
                }).read(data);

                if (gmlFeatures.length > 0) {
                    var maxVal;
                    var minVal;
                    var dataObjMaxRange = gmlFeatures[0].data;
                    var dataObjMinRange = gmlFeatures[gmlFeatures.length - 1].data;
                    for (var propertyName in dataObjMaxRange) {
                        if (propertyName == uniqueField) {
                            maxVal = parseInt(dataObjMaxRange[propertyName]);
                        }
                    }
                    for (var propertyName in dataObjMinRange) {
                        if (propertyName == uniqueField) {
                            minVal = parseInt(dataObjMinRange[propertyName]);
                        }
                    }

                    result.min_maxIndex(maxVal, minVal);
                } else {
                    result.min_maxIndex(0, 0);
                }
            },
            error: function (xhr, status) {
                jAlert('Sorry, there is a problem!');
            }
        });
    }
