

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
var dataSet = [];
var _featureNS;
var _featurePrefix;
var _featureTypes= [];
					
var extent="";

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
        
       // var layers = map.layers;
	   
        $('#Layers').empty();
        $('#Layers').append(
        $("<option></option>").attr("value", "Please Select").text("Please Select"));
    
		
		for (var i=0;i<map.getLayers().getLength();i++) {
			 layer =map.getLayers().getArray()[i];
			  if (layer.getSource() instanceof ol.source.Vector) {
                      if(layer.values_.visible==true){
						   $('#Layers').append($("<option></option>").attr("value", map.getLayers().getArray()[i].values_.aname).text(map.getLayers().getArray()[i].values_.aname));
					  }
               }
			 
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
				map.addInteraction(selectInteraction_search);
                map.addInteraction(intraction_dragBox_search);
                    intraction_dragBox_search.on('boxend', function(event) {
					selectedFeaturesSearch = selectInteraction_search.getFeatures();
					selectedFeaturesSearch.clear();
					 extent = intraction_dragBox_search.getGeometry().getExtent();
					map.getLayers().forEach(function(layer) {
						 if (layer instanceof ol.layer.Vector) {
						 layer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
							 
							 if(layer.values_.aname ==$("#Layers").val()){
						    	selectedFeaturesSearch.push(feature);
							}

							
						 });
						 
						 }
						 
					});

				 });
           
		 // clear selection when drawing a new box and when clicking on the map
		    intraction_dragBox_search.on('boxstart', function() {
			     if(selectedFeaturesSearch!=null){
					selectedFeaturesSearch.clear();
					}	
					extent="";
				  });

				break;
			case 'clearBounds':
				removeBoundExtentFeature();
				//selectionFilter = null;
				
			}
				
		});

        translateSearchStrings();
    });
};

function getLayerByAliesName(layer) {
    var _layer = false;
    for (var i=0;i<map.getLayers().getLength();i++) {
        if (map.getLayers().getArray()[i].get('aname') === layer) { //check if layer exists
            _layer = map.getLayers().getArray()[i];
        }
    }
    return _layer;
}


function make_basic_auth(user, password) {
	  var tok = user + ':' + password;
	  var hash = Base64.encode(tok);
	  return "Basic " + hash;
}

function translateSearchStrings(){
	$('#search_1').html("Search");
	$('#search_layer').html("layer");
	$('#search_fields').html("Fields");
	$('#options-search-extent').html("search Extent Boundary");
	$('#options-s-t').html("Search Type Options");
	$('#search_exact').html("Search Exact");
	$('#search_similar').html("Search Similar");
	$('#search_1').attr("value","Search");
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
	
	// Deactive markup controls*/
	for (key1 in markupControls) {
		var control = markupControls[key1];
		control.deactivate();
	}
	//Deactive map controls*/
	for (mapKey in mapControls) {
		var control = mapControls[mapKey];
		control.deactivate();
	}
}


var GetFields_Search = function (a) {
        var selected = $("#Layers").val();

        //actualLayerName = selected.val();
        if (selected.toUpperCase() == "PLEASE SELECT") {
            $('#LayersFields').empty();
            // $('#LayersFields').append(new Option("Please Select", "Please
            // Select", true, true));
            $('#LayersFields').append(
            $("<option></option>").attr("value", "Please Select").text("Please Select"));
        } else {
			
			var _layer =getLayerByAliesName(selected);
			
            $.ajax({
                url: STUDIO_URL + 'layer/' + _layer.values_.aname	 + "?" + token,
                success: function (data) {
                    url = _layer.values_.url
;
                    /*
                     * if (url.indexOf('wms?') > -1) url = url.replace("wms",
                     * "wfs");
                     */
                    url = replaceString(url, /wms/gi, 'wfs');

                    lyrDetails = data;
                    if(lyrDetails.layerField.length != 0){
	                    displayableCols = lyrDetails.layerField;
	                    uniqueField = lyrDetails.layerField[0].keyfield;
	
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
      //$('#LayersFields').append($("<option></option>").attr("value", "All").text("All"));
      for (var i = 0; i < FieldNames1.length; ++i) {
           if (FieldNames1[i].type.indexOf("gml:GeometryPropertyType")>=0) {
               var l = displayableCols.length;
               displayableCols[l] = []
               var obj = new Object();
              obj.Name = FieldNames1[i];
              obj.Alias = FieldNames1[i].name;
               displayableCols[l] = obj;
              continue;
           }
         var _type  =  FieldNames1[i].type;
		 _type = _type.substring(_type.indexOf(":") + 1);
		 if(_type!="Polygon" && _type!="Line" && _type!="Point" )
          $('#LayersFields').append($("<option></option>").attr("value", _type).text(($._(FieldNames1[i].name) == "")?FieldNames1[i].name:$._(FieldNames1[i].name)));
      }
      if (FieldNames1.length > 0) {
          $('#LayersFields').get(0).selectedIndex = 0;
      }
}

var PopulateLayerFields_Search = function (FieldNames1) {
        $('#LayersFields').empty();
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
          
        	$('#LayersFields').append(
            $("<option></option>").attr("value", FieldNames1[i].field).text(($._(FieldNames1[i].alias) == "")?FieldNames1[i].alias:$._(FieldNames1[i].alias)));
        }
        if (FieldNames1.length > 0) {
            $('#LayersFields').get(0).selectedIndex = 0;
        }
    };

var GetLayerFields_search = function (selectedLayer1, displayableCols) {
	var _url=PROXY_PATH + url + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + selectedLayer1 + "&maxFeatures=1&outputFormat=application/json";
        $.ajax({
            url: _url,
            success: function (data) {
			_featureNS=data.targetNamespace;
			_featurePrefix=data.targetPrefix;
			_featureTypes.push(data.featureTypes[0].typeName);
		   // populateLayerFields(displayableCols);
		    populateLayerFields(data.featureTypes[0].properties); 
		   
            }
        });
    };

var SearchIn = function () {
        var selectedLayer = $("#Layers").val();
        var selectedFieldType = $("#LayersFields").val();
		var selectedField=$("#LayersFields option:selected").text();

        var searchText = $("#searchText").val();
        var selectedSearchType = $("#options-s-d").find("input:checked").attr("id");


        var pos;
        var prefix;

		
		
        if (selectedLayer.toUpperCase() == "PLEASE SELECT") {
            jAlert("Please Select Layer");
        } else {
           
		   if(searchText==""){
			 jAlert("Please Enter  Search Text");
			 return false;
		  }
                if (selectedSearchType.toUpperCase() == "SIMILAR") {
				var featureRequest=CreatefeatureRequest(selectedSearchType,selectedFieldType,selectedField,searchText);				
                    var _url=url+"geoserver/wfs"
				  fetch(_url, {
					method: 'POST',
					body: new XMLSerializer().serializeToString(featureRequest)
				  }).then(function(response) {
					return response.json();
				  }).then(function(json) {
				   var features = new ol.format.GeoJSON().readFeatures(json);
					fillGridTable(features);
				  });
	  
				  
                } else {
                   
				var featureRequest=CreatefeatureRequest(selectedSearchType,selectedFieldType,selectedField,searchText);	  
				   
				     var _url=url+"geoserver/wfs"
				  fetch(_url, {
					method: 'POST',
					body: new XMLSerializer().serializeToString(featureRequest)
				  }).then(function(response) {
					return response.json();
				  }).then(function(json) {
				   var features = new ol.format.GeoJSON().readFeatures(json);
					fillGridTable(features);
				  });
	  
				   
                }
              
        }
    };
   
var CreatefeatureRequest = function (selectedSearchType1, selectedFieldType,selectedField,searchText) {

        dataType = getSearchDataType(searchText);
        var featureRequest;
        switch (selectedSearchType1.toUpperCase()) {
        case "EXACT":
                 if(dataType!=selectedFieldType)  
				 {
					 var msg="Entered Feild type should be of type :: " +selectedFieldType;
					  jAlert(msg, "Error" );
					   
				 }else
				 {
					 
				  if(extent==""){
						featureRequest = new ol.format.WFS().writeGetFeature({
						srsName: 'EPSG:4326',
						featureNS: _featureNS,
						featurePrefix: _featurePrefix,
						featureTypes: _featureTypes,
						outputFormat: 'application/json',
						filter: ol.format.filter.equalTo(selectedField, searchText)
					  });
				  }else{
		           featureRequest = new ol.format.WFS().writeGetFeature({
							srsName: 'EPSG:4326',
							featureNS: _featureNS,
							featurePrefix: _featurePrefix,
							featureTypes: _featureTypes,
							outputFormat: 'application/json',
							filter: ol.format.filter.and(
							ol.format.filter.intersects('geometry', extent, 'EPSG:4326'),
							ol.format.filter.equalTo(selectedField, searchText)
							)
						  }); 				
		                   				
					  
				  }
				   
				 }
            
            break;
        case "SIMILAR":

              if(dataType!=selectedFieldType)  
				 {
					 var msg="Entered Feild type should be of type :: " +selectedFieldType;
					   jAlert(msg, "Error" );
					   return
				 }else
				 {
					  if(extent==""){
						featureRequest = new ol.format.WFS().writeGetFeature({
						srsName: 'EPSG:4326',
						featureNS: _featureNS,
						featurePrefix: _featurePrefix,
						featureTypes: _featureTypes,
						outputFormat: 'application/json',
						filter: ol.format.filter.like(selectedField, searchText+"*")
						 
					  });
				  }else{
						
					  featureRequest = new ol.format.WFS().writeGetFeature({
							srsName: 'EPSG:4326',
							featureNS: _featureNS,
							featurePrefix: _featurePrefix,
							featureTypes: _featureTypes,
							outputFormat: 'application/json',
							filter: ol.format.filter.and(
							ol.format.filter.intersects('geometry', extent, 'EPSG:4326'),
							ol.format.filter.like(selectedField, searchText+"*")
							)
						  }); 				
				  }
					 
				 }		
			
            break;
        default:
		
		    break;
        }

        
        return featureRequest;

}  

 
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
		 }else{
			 valid = false;
		 }
		
		 if(valid){
			 return 'date';
		 }else{
			 if(searchText.toUpperCase() == 'FALSE' || searchText.toUpperCase() == 'TRUE'){
				 return 'boolean';
			 } else if($.isNumeric (searchText))
			 {
				 return 'int';
			 }
			else if(typeof searchText === "string"){
				 return 'string';
			 }else
			 {
				 return "";
			 }
		 }
	 }
 }

var CreateFilterCreateFilter = function (selectedSearchType1, searchText1) {
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
    	        extent="";
				if(selectedFeaturesSearch!=null)
				selectedFeaturesSearch.clear();
			
				map.removeInteraction(selectInteraction_search);
				map.removeInteraction(intraction_dragBox_search);
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
	


function fillGridTable(features) {
	
		var _layer=$("#Layers").val();
     _layer=getLayerByAliesName(_layer);
	 var queryString = "";
		queryString = "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName="+ _layer.values_.name+"&maxFeatures=1&outputFormat=application/json";

	$("#tablegridContainer").html("");
    $("#bottombar").empty();
	 $("#tablegridContainer").append("<div id='grid'><table id='tablegrid1' style='z-index: 200000'></table></div>");
  
   
	$.ajax({
		url : PROXY_PATH + url + queryString,
		 async: false,
		success : function(data1) {
			var myColumns = [];
			var name_Columns = [];
			var myColumns1 = [];
			var ctr = 0;
			var lastsel;
			for ( var i = 0; i < data1.featureTypes[0].properties.length; ++i) {
				if (!data1.featureTypes[0].properties[i].type.indexOf("gml")>=0) {

					 if (!isColumnDisplayable(data1.featureTypes[0].properties[i].name))
						continue;

					name_Columns[ctr] = data1.featureTypes[0].properties[i].name;

					if(lang == 'cy'){
						myColumns[ctr] = $._(data1.featureTypes[0].properties[i].name);
					}else{
						myColumns[ctr] = getFieldAlias(data1.featureTypes[0].properties[i].name);
					}
					var val = new Object();
					val.name = name_Columns[ctr];
					val.index = name_Columns[ctr];
					val.width = width;
					val.editable = true;
					if (data1.featureTypes[0].properties[i].type
							.indexOf("int")>=0
							|| data1.featureTypes[0].properties[i].type
							.indexOf("decimal")>0) {
					} else if (data1.featureTypes[0].properties[i].type
							.indexOf("string")>=0) {
						val.sorttype = "text";
					} else if (data1.featureTypes[0].properties[i].type
							.indexOf("date")>=0) {
						val.sorttype = "date";
						val.formatter = dateFormatter;
					
					}
					myColumns1[ctr] = val;
					ctr++;
				}
			}

		
			
			var rowHeight = 200;


			mygrid = $("#tablegrid1")
			.jqGrid(
					{
						rowNum : maxFeatureCount,
						sortname : uniqueField,
						viewrecords : true,
						sortorder : "asc",
						datatype : "local",
						height : rowHeight,
						colNames : myColumns,
						colModel : myColumns1,
						multiselect : true,
						toolbar : [ true, "top" ],
						altRows : true,
						editurl : 'clientArray',
				     	loadComplete : function() {
							$('#gridloading').hide();
						}
					});

			jQuery("#tablegrid1").jqGrid('filterToolbar', {
				stringResult : true,
				searchOnEnter : false
			});

			var result_count = "<div style = 'margin-left: 3em; display: inline-block;'>Result Count: " + "<span id = 'result_cnt'></span></div>";
			var grid_toolbar = "<div class='gridtoolbar fg-buttonset fg-buttonset-single'>";
			grid_toolbar = grid_toolbar
			//+ "<button name='first' id='first' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='First Results' onclick='navigateFirstRecord();'><img src='resources/images/viewer/layouts/resultset_first.png' width='16px' height='16px'/></button>";
			//grid_toolbar = grid_toolbar
			///+ "<button name='previous' id='previous' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Previous Results' onclick='navigatePreviousRecord();'><img src='resources/images/viewer/layouts/resultset_previous.png' width='16px' height='16px'/></button>";
			//grid_toolbar = grid_toolbar
			//+ "<button name='next' id='next' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Next Results' onclick='navigateNextRecord();'><img src='resources/images/viewer/layouts/resultset_next.png' width='16px' height='16px'/></button>";
			//grid_toolbar = grid_toolbar
			//+ "<button name='last' id='last'  class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Last Results' onclick='navigateLastRecord();'><img src='resources/images/viewer/layouts/resultset_last.png' width='16px' height='16px'/></button>";
			//grid_toolbar = grid_toolbar
			//+ "<button name='zoom' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Zoom To Results' onclick='zoomToFeatures();'><img src='resources/images/viewer/layouts/zoom.png' width='16px' height='16px'/></button>";
			//grid_toolbar = grid_toolbar
			//+ "<button name='export' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Export Results' onclick='exportFeatures();'><img src='resources/images/viewer/layouts/export.png' width='16px' height='16px'/></button>";
			//grid_toolbar = grid_toolbar
			+ "<button name='tooglefilter' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Search' onclick='toggleFilterToolBar();'><img src='resources/images/viewer/layouts/toggle_search.png' width='16px' height='16px'/></button>";
			/*grid_toolbar = grid_toolbar
							+ "<button name='fileupload' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Upload File' onclick='fileupload();'><img src='resources/images/viewer/layouts/file-upload.png' width='16px' height='16px'/></button>";*/
			//grid_toolbar = grid_toolbar
			//+ "<button name='savegrid' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Save Grid' onclick='saveGrid();'><img src='resources/images/viewer/layouts/savegrid.png' width='16px' height='16px'/></button>";
			//if(roles.indexOf('ROLE_PUBLICUSER') == -1){
			//grid_toolbar = grid_toolbar 
			//	+ "<button name='rowInfo' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Detail Info' onclick='displayRowInfo();'><img src='resources/images/viewer/layouts/row-info.png' width='16px' height='16px'/></button>";
			//	}
			grid_toolbar = grid_toolbar +"</div>";
			$("#t_tablegrid1").append(grid_toolbar);

			$("#t_tablegrid1").append(result_count);
			//mygrid.toggleToolbar();

			$(".fg-button").tipTip({
				fadeIn : 0,
				fadeOut : 0
			});
			$("#result_cnt").text("");
			$("#result_cnt").text(features.length);

			 for ( var i = 0; i < features.length; ++i) {
			
			var attrs= new  Object();
			var objeto = features[i].getProperties();
			var propiedades;
				  for (propiedades in objeto) {
					  attrs[propiedades] = objeto[propiedades];
					}
					
			jQuery("#tablegrid1").jqGrid('addRowData', i, attrs);		
	         }
			 
	 
	     toggleGrid();
	 
			 
		}
	});

	

	$("#bottombar").empty();
  $("#bottombar").append($("#tablegridContainer").html());

	
}
	
	function populateGridSearch(features, isGridForwardNavigable) {
	
	  for ( var i = 0; i < features.length; ++i) {
			
			var attrs= new  Object();
			var objeto = features[i].getProperties();
			var propiedades;
				  for (propiedades in objeto) {
					  attrs[propiedades] = objeto[propiedades];
					}
					
			jQuery("#tablegrid1").jqGrid('addRowData', i, attrs);		
	
	 }
	 
	return true;
}

function fillGridTableOld(features) {
    dataSet = [];

   $("#bottombar").empty();
   $("#tablegridContainer").html('');

      //put header 
	  var _content="<div id='grid'><table id='tablegrid1' style='z-index: 200000'><thead><tr>";
	  
		  
		  if(features.length>0) {
			
			$.each(features[0].values_, function(key,val){
				 if(key!="geometry")
				 _content=_content+"<th> "+ key +"</th>";

            });
			
		  }
		  	_content=_content+"</tr></thead></table></div>";	

			
			 $("#tablegridContainer").append(_content);
          var row=[];
		  for ( var i = 0; i < features.length; ++i) {
				row=[];
				$.each(features[i].values_, function(key,val){
				 if(key!="geometry"){
				 if(val){
				row.push(val);
				}else{
					row.push("");
				}
				}
		
            });
			
			dataSet.push(row);
	
	 }
		 jQuery("#tablegrid1").DataTable({
                        data: dataSet,
                        bAutoWidth: false,
                        "responsive": true,
                        "bFilter": false,
                        "bPaginate": true,
                        "bAutoWidth": false,
                        "sPaginationType": "full_numbers",
                        "pageLength": 5,
                        "bSort": true,
                        "columnDefs": [ {
                            "targets": 'no-sort',
                            "orderable": false,
                        } ],
                        oLanguage: {
                        }
                    });
					

				toggleGrid();
			
			$("#bottombar").empty();
	        $("#bottombar").append($("#tablegridContainer").html());
		}
		
