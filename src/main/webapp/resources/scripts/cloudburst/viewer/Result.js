

var uniqueField;
var featureNS;
var prefix;
var type;
var displayLayerName;
var actualLyrName;
var keyValue;
var isResultWndOpen = false;
var dynamic_array;
var maxFeatureCount = 25;
var totalColumnWidth = 0;
var width = 100;
var maxWidth = 800;
var url;
var propertyNames;
var featureGeom;
var queryFilter;
var dataPost;
var datapostResult=null;
var selRows;
var lyrType;
var layer;
var maxFeatureIndex = 0;
var minFeatureIndex = 0;
var minRecordIndex = 0;
var recordIndex = 0;
var ar_pgKeys;
var mygrid;
var displayGrid = true;
var relateLayer;
var distance;
var relateLayerName;
var geomFieldName;
var filter_history = null;
//$.jgrid.no_legacy_api = true;
//$.jgrid.useJSON = true;
//$.jgrid.defaults = $.extend($.jgrid.defaults, { loadui: "enable" });
var showDialog = false;
var isSpatialQryFilter = false;
var resultCount = 0;
var changedRows = [];
var g_gmlFeatures;
var info_layer=null;
var gridref='';

function Result(selectedLayer, featNS, filter1, _showDialog, _relateLayer,
		_distance, _count) {
	showDialog = _showDialog;
	featureNS = featNS;
	queryFilter = filter1;
	actualLyrName = selectedLayer;
	relateLayer = _relateLayer;
	distance = _distance;
	selRows = [];
	ar_pgKeys = [];
	resultCount = 0;

	displayLayerName = layerMap[selectedLayer];
	if (relateLayer == undefined) {
		url = map.getLayersByName(selectedLayer)[0].url;
		layer = map.getLayersByName(actualLyrName)[0]; /* Get the layer object */

		maxFeatureCount = 25;
		var pos = displayLayerName.indexOf(":");
		prefix = displayLayerName.substring(0, pos);
		type = displayLayerName.substring(++pos);
	} else {
		url = map.getLayersByName(relateLayer)[0].url;
		relateLayerName = layerMap[relateLayer];
		layer = map.getLayersByName(relateLayer)[0]; /* Get the layer object */
		maxFeatureCount = _count;

		var pos = relateLayerName.indexOf(":");
		prefix = relateLayerName.substring(0, pos);
		type = relateLayerName.substring(++pos);
	}
	url = replaceString(url, /wms/gi, 'wfs');
	// layer = map.getLayersByName(actualLyrName)[0]; /*Get the layer object*/
	getGeometryTyp(layer);
}

function showLastRecords() {
	var filter;
	var filters;

	if (relateLayer == undefined) {
		if (minFeatureIndex == minRecordIndex && maxFeatureIndex == recordIndex)
			return null;

		filter = new OpenLayers.Filter.Comparison({
			type : OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO,
			property : uniqueField,
			value : minFeatureIndex
		});

		if(filter_history != null){
			filter = new OpenLayers.Filter.Logical({
				type: OpenLayers.Filter.Logical.AND,
				filters:[filter, filter_history]
			});
		}

		if (queryFilter != null) {
			filters = new OpenLayers.Filter.Logical({
				type : OpenLayers.Filter.Logical.AND,
				filters : [ filter, queryFilter ]
			});
		} else {
			filters = filter;
		}
	} else {
		filters = null;
	}

	return filters;
}


var getLayerFields = function (actualLyrName) {
	var selectedLayer1 = layerMap[actualLyrName];
	var feature;
	$.ajax({
		url: PROXY_PATH + url + "request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + selectedLayer1 + "&maxFeatures=1",
		async:false,
		success: function (data) {
			var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
			var responseText = featureTypesParser.read(data);
			var featureTypes = responseText.featureTypes;
			feature = featureTypes[0].properties;
		}
	});
	return feature;
}

function showNextRecords() {
	var filter;
	var filters;

	if (relateLayer == undefined) {
		filter = new OpenLayers.Filter.Comparison({
			type : OpenLayers.Filter.Comparison.GREATER_THAN,
			property : uniqueField,
			value : recordIndex
		});

		fields = getLayerFields(actualLyrName);
		blnHistoryExists = false;
		for (var i = 0; i < fields.length; ++i){
			if(fields[i].name.toLowerCase() == 'ishistory'){
				blnHistoryExists = true;
				break;
			}
		}

		//if(actualLyrName != 'Complaint_Layer' && actualLyrName != 'Address_Point'){
		if(blnHistoryExists){
			filter_history = new OpenLayers.Filter.Comparison({
				type: OpenLayers.Filter.Comparison.EQUAL_TO,
				property: 'ishistory',
				value: false
			})
			filter = new OpenLayers.Filter.Logical({
				type: OpenLayers.Filter.Logical.AND,
				filters:[filter, filter_history]
			});
		}

		if (queryFilter != null) {
			filters = new OpenLayers.Filter.Logical({
				type : OpenLayers.Filter.Logical.AND,
				filters : [ filter, queryFilter ]
			});
		} else {
			filters = filter;
		}
	} else {
		filters = null;
	}

	return filters;
}

function showPreviousRecords() {
	var filter;
	var filters;

	dynamic_array.pop();

	if (relateLayer == undefined) {
		filter = new OpenLayers.Filter.Comparison({
			type : OpenLayers.Filter.Comparison.LESS_THAN,
			property : uniqueField,
			value : minRecordIndex
		});

		if(filter_history != null){
			filter = new OpenLayers.Filter.Logical({
				type: OpenLayers.Filter.Logical.AND,
				filters:[filter, filter_history]
			});
		}

		if (queryFilter != null) {
			filters = new OpenLayers.Filter.Logical({
				type : OpenLayers.Filter.Logical.AND,
				filters : [ filter, queryFilter ]
			});
		} else {
			filters = filter;
		}
	} else {
		filters = null;
	}
	return filters;
}

function extractFeatures(filters, isGridForwardNavigable, btnClicked, displayInGrid, sortByPriority) {
	var valAsc = "ASC";
	var valDesc = "DESC";

	var filter_1_0 = new OpenLayers.Format.Filter({
		version : "1.1.0"
	});

	isSpatialQryFilter = false;
	if (relateLayer == undefined) {
		for ( var _i = 0; _i < filters.filters.length; _i++) {
			if (filters.filters[_i].CLASS_NAME == "OpenLayers.Filter.Spatial") {
				isSpatialQryFilter = true;
				break;
			}
		}
		if (isSpatialQryFilter) {
			createSpatialQryXML();
			dataPost = dataPost.replace("${uniqueFld}", uniqueField);
		} else {
			if(sortByPriority == undefined){


				if(displayInGrid)
				{
					createXML();

				}

				else
				{
					createSpatialQryXML();
				}

				dataPost = dataPost.replace("${uniqueFld}", uniqueField);
			}else{
				dataPost=createThematicXML();
				dataPost = dataPost.replace("${uniqueFld}", "_class");
			}
		}

		var xml = new OpenLayers.Format.XML();
		var xmlFilter = xml.write(filter_1_0.write(filters));
		dataPost = dataPost.replace("${layer}", "\"" + displayLayerName + "\"");
		dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
		dataPost = dataPost.replace("${filter}", xmlFilter);
		//dataPost = dataPost.replace("${uniqueFld}", uniqueField);

		if (isGridForwardNavigable) {
			dataPost = dataPost.replace("${SortOrder}", valAsc);
		} else {
			dataPost = dataPost.replace("${SortOrder}", valDesc);
		}
	} else {
		relateXML();
		dataPost = dataPost.replace("${relatelayer}", "\"" + relateLayerName
				+ "\"");
		dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
		dataPost = dataPost.replace("${layer}", displayLayerName);
		dataPost = dataPost.replace("${cql}", queryFilter);
		dataPost = dataPost.replace("${distance}", distance);

		var bounds = map.getExtent();
		var lowerCorner = bounds.left + " " + bounds.bottom;
		var upperCorner = bounds.right + " " + bounds.top;
		dataPost = dataPost.replace("${projection}", map.projection);
		dataPost = dataPost.replace("${lowerCorner}", lowerCorner);
		dataPost = dataPost.replace("${upperCorner}", upperCorner)
		dataPost = dataPost.replace(/_geom/gi, geomFieldName);
	}

	var mapProj = map.projection;
	var reverseCoords = true;
	if (mapProj == "EPSG:4326") {
		reverseCoords = false;
	}


	$.ajax({
		url : PROXY_PATH + url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		async : false,
		data : dataPost,
		success : function(data) {
			var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
				xy : reverseCoords,
				featureType : type,
				gmlns : 'http://www.opengis.net/gml',
				featureNS : featureNS,
				geometryName : geomFieldName, // displayableCols[displayableCols.length
				// - 1],
				featurePrefix : prefix,
				extractAttributes : true
			}).read(data);

			// Check if sorting error is thrown
			if (data.childNodes[0].nodeName == "ows:ExceptionReport") {
				performRequery(filters, reverseCoords,
						isGridForwardNavigable, displayInGrid);

			} else {
				if (btnClicked == undefined) {
					resultCount = gmlFeatures.length;
				}
				if(! displayInGrid){
					/*Case where gmlfeatures has to be returned 
					 * and not displayed in result grid*/
					g_gmlFeatures = gmlFeatures;
					getGmlFeatures(gmlFeatures);
					return ;
				}

				getGmlFeatures(gmlFeatures);


				if (gmlFeatures.length > 0) {
					//populateLookupValues(gmlFeatures);
					if (!isResultWndOpen) {
						fillGrid1(gmlFeatures);
						// centerLayout.open("south", true);
					} else {
						populateGrid(gmlFeatures,
								isGridForwardNavigable);
						// centerLayout.open("south", true);
					}
					var featureIds = "";
					for ( var i = 0; i < gmlFeatures.length; i++) {
						featureIds += gmlFeatures[i].attributes[uniqueField]
						+ ",";
					}
					featureIds = featureIds.substring(0,
							featureIds.length - 1);
					associationIds = featureIds;
					// Amitabh
					// loadAttachment(featureIds, actualLyrName);
				} else {
					//$("#bottombar").toggle();
					//$("#bottombar").empty()
					//$("#bottombar").hide();

					jAlert("No records found");
					if (btnClicked != undefined) {
						if (btnClicked == 'NextRecord'
							|| btnClicked == 'LastRecord') {
							$('#next').attr("disabled", true);
							$('#last').attr("disabled", true);
						} else if (btnClicked == 'FirstRecord'
							|| btnClicked == 'PreviousRecord') {
							$('#previous').attr("disabled", true);
							$('#first').attr("disabled", true);
						}
					}
				}
			}
		},
		error : function(xhr, status) {
			jAlert('Sorry, there is a problem!');
		}
	});
}

function performRequery(filters, reverseCoords, isGridForwardNavigable, displayInGrid) {
	var filter_1_0 = new OpenLayers.Format.Filter({
		version : "1.1.0"
	});

	rePost = createQueryRequest();
	var xml = new OpenLayers.Format.XML();
	var xmlFilter = xml.write(filter_1_0.write(filters));
	rePost = rePost.replace("${layer}", "\"" + displayLayerName + "\"");
	rePost = rePost.replace("${featureNS}", "\"" + featureNS + "\"");
	rePost = rePost.replace("${filter}", xmlFilter);
	rePost = rePost.replace("${uniqueFld}", uniqueField);

	$.ajax({
		url : PROXY_PATH + url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		data : rePost,
		success : function(data) {
			var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
				xy : reverseCoords,
				featureType : type,
				gmlns : 'http://www.opengis.net/gml',
				featureNS : featureNS,
				geometryName : geomFieldName, // displayableCols[displayableCols.length
				// - 1],
				featurePrefix : prefix,
				extractAttributes : true
			}).read(data);

			if(! displayInGrid){
				/*Case where gmlfeatures has to be returned 
				 * and not displayed in result grid*/
				g_gmlFeatures = gmlFeatures;
				return;
			}

			if (gmlFeatures.length > 0) {
				//populateLookupValues(gmlFeatures);
				if (!isResultWndOpen) {
					fillGrid1(gmlFeatures);
					// centerLayout.open("south", true);
				} else {
					populateGrid(gmlFeatures, isGridForwardNavigable);
					// centerLayout.open("south", true);
				}
				var featureIds = "";
				for ( var i = 0; i < gmlFeatures.length; i++) {
					featureIds += gmlFeatures[i].attributes[uniqueField] + ",";
				}
				featureIds = featureIds.substring(0, featureIds.length - 1);
				// Amitabh
				// loadAttachment(featureIds, actualLyrName);
			} else {
				jAlert("No records found");
			}
		},
		error : function(xhr, status) {
			jAlert('Sorry, there is a problem!');
		}
	});
}

Result.prototype.min_maxIndex = function(maxFeatureIdx, minFeatureIdx) {
	maxFeatureIndex = maxFeatureIdx;
	minFeatureIndex = minFeatureIdx;
}

Result.prototype.getFeatures = function(){
	return g_gmlFeatures;
}

/* Initiates query and displays result in a JQGrid */
Result.prototype.displayResult = function(displayCols, uniqueCol,
		_geomFieldName, displayInGrid, sortByPriority) {
	info_layer=layer.name;
	dynamic_array = [];
	recordIndex = 0; // re-initalize
	geomFieldName = _geomFieldName;

	displayableCols = displayCols;
	uniqueField = uniqueCol;
	if (isResultWndOpen) {
		$("#grid").remove();
		isResultWndOpen = false;
	}

	if(displayInGrid == undefined)
		displayInGrid = true;

	// prepare propertyname string
	if(displayableCols != undefined && displayableCols != null){
		for ( var j = 0; j < displayableCols.length; j++) {
			if (j == 0)
				propertyNames = displayableCols[j].field;
			else
				propertyNames = propertyNames + "," + displayableCols[j].field;
		}
	}

	totalColumnWidth = (displayableCols.length) * width;
	if (totalColumnWidth > maxWidth)
		totalColumnWidth = maxWidth + 50;

	$("#tablegridContainer")
	.append(
	"<div id='grid'><table id='tablegrid1' style='z-index: 200000'></table></div>");
	if (relateLayer == undefined) {
		var filters = showNextRecords();
	}
	extractFeatures(filters, true, undefined, displayInGrid, sortByPriority);
}


/*
 * This has been included to return gmlFeatures resulting from
 * spatial/sql query
 */
/*Result.prototype.getOverlayQueryResult = function(displayCols, uniqueCol,
	_geomFieldName, displayInGrid){*/

Result.prototype.getQueryResult = function(displayCols, uniqueCol,
		_geomFieldName, displayInGrid){

	geomFieldName = _geomFieldName;
	recordIndex = 0;

	displayableCols = displayCols;
	uniqueField = uniqueCol;
	var gmlFeatures = null;
	if(displayInGrid == undefined)
		displayInGrid = true;

	// prepare propertyname string
	for ( var j = 0; j < displayableCols.length; j++) {
		if (j == 0)
			propertyNames = displayableCols[j].field;
		else
			propertyNames = propertyNames + "," + displayableCols[j].field;
	}
	//extractFeatures(filters, true, undefined, displayInGrid);
	var filters = showNextRecords();

	var filter_1_0 = new OpenLayers.Format.Filter({
		version : "1.1.0"
	});

	/*	isSpatialQryFilter = false;
	for ( var _i = 0; _i < filters.filters.length; _i++) {
		if (filters.filters[_i].CLASS_NAME == "OpenLayers.Filter.Spatial") {
			isSpatialQryFilter = true;
			break;
		}
	}

	if (isSpatialQryFilter) {*/
	createSpatialQryXML();
	/*}else{
		createXML();
	}
	 */
	var xml = new OpenLayers.Format.XML();
	var xmlFilter = xml.write(filter_1_0.write(filters));

	dataPost = dataPost.replace("${layer}", "\"" + displayLayerName + "\"");
	dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
	dataPost = dataPost.replace("${filter}", xmlFilter);
	dataPost = dataPost.replace("${uniqueFld}", uniqueField);
	valAsc = "ASC";
	dataPost = dataPost.replace("${SortOrder}", valAsc);

	var mapProj = map.projection;
	var reverseCoords = true;
	if (mapProj == "EPSG:4326") {
		reverseCoords = false;
	}

	$.ajax({
		url : PROXY_PATH + url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		data : dataPost,
		async:false,
		success : function(data) {
			gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
				xy : reverseCoords,
				featureType : type,
				gmlns : 'http://www.opengis.net/gml',
				featureNS : featureNS,
				geometryName : geomFieldName, // displayableCols[displayableCols.length
				// - 1],
				featurePrefix : prefix,
				extractAttributes : true
			}).read(data);	
		},
		error : function(xhr, status) {
			jAlert('Sorry, there is a problem!');
		}
	});
	return gmlFeatures;
}


//added by saurabh
Result.prototype.getQueryResultWithHistoryRecord = function(displayCols, uniqueCol,
		_geomFieldName, displayInGrid){

	geomFieldName = _geomFieldName;
	recordIndex = 0;

	displayableCols = displayCols;
	uniqueField = uniqueCol;
	var gmlFeatures = null;
	if(displayInGrid == undefined)
		displayInGrid = true;

//	prepare propertyname string
	for ( var j = 0; j < displayableCols.length; j++) {
		if (j == 0)
			propertyNames = displayableCols[j].field;
		else
			propertyNames = propertyNames + "," + displayableCols[j].field;
	}
//	extractFeatures(filters, true, undefined, displayInGrid);
	var filters = showNextRecords_WithHistory();

	var filter_1_0 = new OpenLayers.Format.Filter({
		version : "1.1.0"
	});

	isSpatialQryFilter = false;
	for ( var _i = 0; _i < filters.filters.length; _i++) {
		if (filters.filters[_i].CLASS_NAME == "OpenLayers.Filter.Spatial") {
			isSpatialQryFilter = true;
			break;
		}
	}

	if (isSpatialQryFilter) {
		createSpatialQryXML();
	}else{
		createXML();
	}

	var xml = new OpenLayers.Format.XML();
	var xmlFilter = xml.write(filter_1_0.write(filters));
	dataPost = dataPost.replace("${layer}", "\"" + displayLayerName + "\"");
	dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
	dataPost = dataPost.replace("${filter}", xmlFilter);
	dataPost = dataPost.replace("${uniqueFld}", uniqueField);
	valAsc = "ASC";
	dataPost = dataPost.replace("${SortOrder}", valAsc);

	var mapProj = map.projection;
	var reverseCoords = true;
	if (mapProj == "EPSG:4326") {
		reverseCoords = false;
	}

	$.ajax({
		url : PROXY_PATH + url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		data : dataPost,
		async:false,
		success : function(data) {
			gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
				xy : reverseCoords,
				featureType : type,
				gmlns : 'http://www.opengis.net/gml',
				featureNS : featureNS,
				geometryName : geomFieldName, // displayableCols[displayableCols.length
				// - 1],
				featurePrefix : prefix,
				extractAttributes : true
			}).read(data);	
		},
		error : function(xhr, status) {
			jAlert('Sorry, there is a problem!');
		}
	});
	return gmlFeatures;
}

/* Initiates execution of query and returns the feature geometry collection */
Result.prototype.getQueryFeatures = function(displayCols, uniqueCol) {
	// dynamic_array = [];
	var arr_feats = [];
	recordIndex = 0; // re-initalize
	displayGrid = false;

	displayableCols = displayCols;
	uniqueField = uniqueCol;

	// prepare propertyname string
	for ( var j = 0; j < displayableCols.length; j++) {
		if (j == 0)
			propertyNames = displayableCols[j].field;
		else
			propertyNames = propertyNames + "," + displayableCols[j].field;
	}

	var filters = showNextRecords();

	var valAsc = "ASC";

	var filter_1_0 = new OpenLayers.Format.Filter({
		version : "1.1.0"
	});

	createXML();
	var xml = new OpenLayers.Format.XML();
	var xmlFilter = xml.write(filter_1_0.write(filters));
	dataPost = dataPost.replace("${layer}", "\"" + displayLayerName + "\"");
	dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
	dataPost = dataPost.replace("${filter}", xmlFilter);
	dataPost = dataPost.replace("${uniqueFld}", uniqueField);

	dataPost = dataPost.replace("${SortOrder}", valAsc);

	var mapProj = map.projection;
	var reverseCoords = true;
	if (mapProj == "EPSG:4326") {
		reverseCoords = false;
	}

	$.ajax({
		url : PROXY_PATH + url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		data : dataPost,
		async : false,
		success : function(data) {
			var gmlFeats = new OpenLayers.Format.WFST.v1_1_0({
				xy : reverseCoords,
				featureType : type,
				gmlns : 'http://www.opengis.net/gml',
				featureNS : featureNS,
				geometryName : geomFieldName, // displayableCols[displayableCols.length
				// - 1],
				featurePrefix : prefix,
				extractAttributes : true
			}).read(data);

			if (gmlFeats.length > 0) {
				for ( var feat in gmlFeats) {
					// var geom = gmlFeats[feat].geometry;
					// var vector = new OpenLayers.Feature.Vector(geom);
					arr_feats.push(gmlFeats[feat]);
				}
			}
		}
	})
	return arr_feats;
}

function populateGrid(features, isGridForwardNavigable) {
	var lowerIndex = 0;
	var upperIndex = 0;
	var i = 0;
	arr_attrib = [];

	featureGeom = []; // re-initailize the array each time grid is
	// poopulated
	if (features.length > 0)
		$("#tablegrid1").jqGrid().clearGridData();
	$("#result_cnt").text(features.length);

	for ( var feat in features) {
		if (feat == 0 || feat == features.length - 1) {
			var attrbs = features[feat].attributes;
			for ( var attrb in attrbs) {
				if (attrb == uniqueField) {
					if (feat == 0) {
						lowerIndex = attrbs[attrb];
						break;
					} else {
						upperIndex = attrbs[attrb];
						break;
					}
				}
			}
		}

		// Store feature Geometry
		var len = featureGeom.length;
		featureGeom[len] = new Array(2);
		featureGeom[len][0] = features[feat].geometry;
		featureGeom[len][1] = features[feat].attributes[uniqueField];
		// add feature record to JQGrid
		if (isGridForwardNavigable) {
			jQuery("#tablegrid1").jqGrid('addRowData', ++i,
					features[feat].attributes);
		} else {
			arr_attrib[i] = features[feat].attributes;
			i++;
		}
	}

	if (!isGridForwardNavigable) {
		i = 0;
		for ( var k = arr_attrib.length - 1; k >= 0; k--) {
			jQuery("#tablegrid1").jqGrid('addRowData', ++i, arr_attrib[k]);
		}
		recordIndex = arr_attrib[0][uniqueField];
		minRecordIndex = arr_attrib[arr_attrib.length - 1][uniqueField];
	}

	// Push the mix and max value for unique field
	if (isGridForwardNavigable) {
		if (lowerIndex > 0 && upperIndex >= 0) {
			var o = new Object();
			o.lowerIndex = lowerIndex;
			o.upperIndex = upperIndex <= 0 ? lowerIndex : upperIndex;
			recordIndex = o.upperIndex;
			minRecordIndex = o.lowerIndex;
			dynamic_array.push(o);
		}
		/* Set the selected indexes from the array in the navigated next page */
		if (selRows != null) {
			var key = minRecordIndex + "-" + recordIndex;
			if (selRows[key] != null) {
				for ( var i = 0; i < selRows[key].length; i++) {
					$('#tablegrid1').setSelection(selRows[key][i].selRowId,
							true);
				}
			}
		}
	} else {

		/*
		 * Set the selected indexes from the array in the navigated previous
		 * page
		 */
		if (dynamic_array.length >= 1 && selRows != null) {
			var key = minRecordIndex + "-" + recordIndex;
			if (selRows[key] != null) {
				for ( var i = 0; i < selRows[key].length; i++) {
					$('#tablegrid1').setSelection(selRows[key][i].selRowId,
							true);
				}
			}
		}
	}
	return true;
}

function navigateFirstRecord() {
	var selArr = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');
	var key = minRecordIndex + "-" + recordIndex;
	selRows[key] = [];
	for ( var i = 0; i < selArr.length; i++) {
		var gridData = new Object();
		gridData.selRowId = selArr[i];
		gridData.rowData = $("#tablegrid1").getRowData(selArr[i]);

		selRows[key][i] = gridData;
	}
	ar_pgKeys[key] = key;

	recordIndex = 0;
	var filters = showNextRecords();
	if (filters != null) {
		extractFeatures(filters, true, 'FirstRecord', true);
		// }else{
		$('#previous').attr("disabled", true);
		$('#first').attr("disabled", true);
		// jAlert("No records found");
	}
	$('#next').removeAttr("disabled");
	$('#last').removeAttr("disabled");
}

function navigateLastRecord() {
	/* Get the selected row indexes of current page and store it in array */
	var selArr = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');
	var key = minRecordIndex + "-" + recordIndex;
	selRows[key] = [];
	for ( var i = 0; i < selArr.length; i++) {
		var gridData = new Object();
		gridData.selRowId = selArr[i];
		gridData.rowData = $("#tablegrid1").getRowData(selArr[i]);

		selRows[key][i] = gridData;
	}
	ar_pgKeys[key] = key;

	/* Navigate to next page */
	var filters = showLastRecords();
	if (filters != null) {
		extractFeatures(filters, true, 'LastRecord', true);
		// }else{
		$('#next').attr("disabled", true);
		$('#last').attr("disabled", true);
		// jAlert("No records found");
	}
	$('#previous').removeAttr("disabled");
	$('#first').removeAttr("disabled");
}

function navigateNextRecord() {
	/* Get the selected row indexes of current page and store it in array */
	var selArr = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');
	var key = minRecordIndex + "-" + recordIndex;
	selRows[key] = [];
	for ( var i = 0; i < selArr.length; i++) {
		var gridData = new Object();
		gridData.selRowId = selArr[i];
		gridData.rowData = $("#tablegrid1").getRowData(selArr[i]);

		selRows[key][i] = gridData;
	}
	ar_pgKeys[key] = key;

	/* Navigate to next page */
	var filters = showNextRecords();
	if (filters != null) {
		extractFeatures(filters, true, 'NextRecord', true);
	} else {
		// $('#next').attr("disabled", true);
		// $('#last').attr("disabled", true);
		jAlert("No records found");
	}
	$('#previous').removeAttr("disabled");
	$('#first').removeAttr("disabled");
}

function navigatePreviousRecord() {
	/* Get the selected row indexes of current page and store it in array */
	var selArr = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');
	var key = minRecordIndex + "-" + recordIndex;
	selRows[key] = [];
	for ( var i = 0; i < selArr.length; i++) {
		var gridData = new Object();
		gridData.selRowId = selArr[i];
		gridData.rowData = $("#tablegrid1").getRowData(selArr[i]);

		selRows[key][i] = gridData;
	}
	ar_pgKeys[key] = key;

	/* Navigate to previous page */
	var filters = showPreviousRecords();
	if (filters != null) {
		extractFeatures(filters, false, 'PreviousRecord', true);
	} else {
		$('#previous').attr("disabled", true);
		$('#first').attr("disabled", true);
		jAlert("No records found");
	}
	$('#next').removeAttr("disabled");
	$('#last').removeAttr("disabled");
}

function zoomToFeatures() {
	var bounds = null;
	var filters1 = [];
	var selArr = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');

	if (selArr.length < 1)
		return;
	selArr = sortAttributeValues(selArr); /*
	 * Sort and order the selected row
	 * index of grid
	 */
	// vectorLayer.removeAllFeatures(); /*Remove previously selected features if
	// any*/
	// clearAllSelections();
	clearSelection(false);
	var features = [];
	for ( var i = 0; i < selArr.length; i++) {
		var geom = featureGeom[selArr[i] - 1][0];

		/* Applying filter for selected features */
		eval("var filter = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.EQUAL_TO, property: uniqueField, value: featureGeom[selArr[i] - 1][1]});");
		filters1[i] = eval(filter);
		var vector = new OpenLayers.Feature.Vector(geom);
		features.push(vector);
		// vectorLayer.addFeatures(vector);

		if (bounds == null){
			if(lyrType == 'Point'){
				var lonlat = new OpenLayers.LonLat(geom.x, geom.y);
				var pixel = map.getPixelFromLonLat(lonlat);

				/* var _x1 = pixel.x - 4;
				  var _y1 = pixel.y - 4;
				  var _x2 = pixel.x + 4;
				  var _y2 = pixel.y + 4;

				  var _lonlat1 = map.getLonLatFromPixel(new OpenLayers.Pixel(_x1, _y1));
				  var _lonlat2 = map.getLonLatFromPixel(new OpenLayers.Pixel(_x2, _y2));

				  var bounds = new OpenLayers.Bounds(); 
				  bounds.extend(_lonlat1); 
				  bounds.extend(_lonlat2);*/
				bounds = new OpenLayers.Bounds();
				bounds.extend(lonlat);
			}else{
				bounds = geom.getBounds();
			}
		}else{
			bounds.extend(geom.getBounds());
		}
	}

	if(lyrType == 'Point'){
		if(bounds.left == bounds.right && bounds.top == bounds.bottom){
			var lonlat = new OpenLayers.LonLat(bounds.left, bounds.top);
			map.setCenter(lonlat, map.baseLayer.resolutions.length - 2, false, false);
		}else{
			map.zoomToExtent(bounds, false);
		}
	}else{
		map.zoomToExtent(bounds, false);
	}


	var filterJoin;
	if (filters1.length > 1) {
		filterJoin = new OpenLayers.Filter.Logical({
			type : OpenLayers.Filter.Logical.OR,
			filters : filters1
		});
	} else {
		filterJoin = filters1;
	}

	cloneLayer = layer.clone();
	cloneLayer.setName("clone");
	map.addLayer(cloneLayer);
	var sld = createHighlightSLD(cloneLayer, filterJoin);

	// Post the SLD
	$.ajax({
		type : 'POST',
		url : "theme/createSLD",
		dataType : "text",
		data : {
			data : escape(sld)
		},
		success : function(result) {
			var sld_result = result; // result.substring(1, result.length - 1);
			// /*For removing additional quotes*/
			cloneLayer.mergeNewParams({
				SLD : sld_result
				// STYLES: 'multiple_rules'
			});
			cloneLayer.redraw(true);
		},
		error : function(xhr, status) {
			jAlert('Sorry, there is a problem!');
		}
	});

	/* Store the selected record filter in global object for drill-down query */
	featureSelection = new Object();
	featureSelection.layerName = actualLyrName;
	featureSelection.filter = filterJoin;
	featureSelection.features = features;

	//map.zoomToExtent(bounds, false);
	// flashFeatures(features);
}

function toggleFilterToolBar() {

	mygrid[0].toggleToolbar();
}

function exportFeatures() {
	var uniqFldIndex = -1;
	var mya = [];
	mya = $("#tablegrid1").getDataIDs(); // Get All IDs
	var data = $("#tablegrid1").getRowData(mya[0]); // Get First row to get
	// the labels
	var colNames = [];
	var ii = 0;
	for ( var i in data) {
		colNames[ii++] = i;
	} // capture col names

	var html = "";
	for (k = 0; k < colNames.length; k++) {
		html = html + colNames[k] + "\t"; // output each Column as tab
		// delimited
		if (colNames[k] == uniqueField)
			uniqFldIndex = k;
	}
	html = html + "\n"; // Output header with end of line

	var data_html = "";
	if (selRows != null && ar_pgKeys != null) {
		for ( var key in ar_pgKeys) {
			if (key != null) {
				for ( var j = 0; j < selRows[key].length; j++) {
					var data = selRows[key][j].rowData;
					for (k = 0; k < colNames.length; k++) {
						data_html = data_html + data[colNames[k]] + "\t"; // output
						// each
						// Row
						// as
						// tab
						// delimited
					}
					data_html = data_html + "\n"; // output each row with end
					// of line
				}
			}
		}

		/*
		 * Condition gets executed if there are no selected rows in SelRows
		 * array (page-navigation not performed)
		 */
		if (data_html == "") {
			var ar = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');
			for ( var ii = 0; ii < ar.length; ii++) {
				var data = $("#tablegrid1").getRowData(ar[ii]);
				for ( var k = 0; k < colNames.length; k++) {
					data_html = data_html + data[colNames[k]] + "\t"; // output
					// each
					// Row
					// as
					// tab
					// delimited
				}
				data_html = data_html + "\n"; // output each row with end of
				// line
			}
		} else {
			/* This case checks for selected page records not picked in SelRows */
			var bContained = false;
			var ar = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow')
			if (ar.length > 0) {
				var data = $("#tablegrid1").getRowData(ar[0]);
				var id = data[colNames[uniqFldIndex]];

				for ( var key in ar_pgKeys) {
					if (key != null) {
						var ar_key = key.split("-");
						var minIndex = parseInt(ar_key[0]);
						var maxIndex = parseInt(ar_key[1]);

						if (id >= minIndex && id <= maxIndex) {
							bContained = true;
							break;
						}
					}
				}

				if (!bContained) {
					var ar = $('#tablegrid1').jqGrid('getGridParam',
					'selarrrow');
					for ( var ii = 0; ii < ar.length; ii++) {
						var data = $("#tablegrid1").getRowData(ar[ii]);
						for ( var k = 0; k < colNames.length; k++) {
							data_html = data_html + data[colNames[k]] + "\t"; // output
							// each
							// Row
							// as
							// tab
							// delimited
						}
						data_html = data_html + "\n"; // output each row with
						// end of line
					}
				}
			}
		}
	}
	if (data_html == "") {
		jAlert('No records selected for export', 'Export');
		return;
	}
	html = html + data_html + "\n"; // end of line at the end

	$('input[name=csvBuffer]').val(html);
	// Commented by Aparesh..not working in google chrome

	// document.forms['exportFrm'].method = 'POST';
	// document.forms['exportFrm'].action = 'export/'; // send it to server
	// which will open this contents in excel file
	// document.forms['exportFrm'].target = '_blank';
	// document.forms['exportFrm'].submit();

	$("#exportFrm").attr("method", "POST");
	$("#exportFrm").attr("action", "export/");
	$("#exportFrm").attr("target", "_blank");
	$('#exportFrm').submit();

}

function getFieldAlias(name) {
	for ( var i = 0; i < displayableCols.length; i++) {
		if (name == displayableCols[i].field) {
			return displayableCols[i].alias
		}
	}
}


function dateFormatter(cellvalue, options, rowObject){
	if(cellvalue != undefined && cellvalue != null){
		//return cellvalue.substr(0, cellvalue.length - 1);
		var dateString = cellvalue.substr(0, cellvalue.length - 1);
		return convertDateToEuropeanFormat(dateString);
	}else{
		return "";
	}
}
/* 
 function populateLookupValues(gmlfeatures){
	 populateLayerLookups(actualLyrName);
	 if(actualLyrName == 'RoW_Path'){
		 for (var feat in gmlfeatures){
			 var pathConditionAndIssueCount=getPathConditionIssueCount(gmlfeatures[feat].attributes['row_id']);

			 gmlfeatures[feat].attributes['type'] = lkp_Row_Path_type[gmlfeatures[feat].attributes['type']];
			 gmlfeatures[feat].attributes['_class'] = lkp_Row_Path_class[gmlfeatures[feat].attributes['_class']];
			 //gmlfeatures[feat].attributes['condition'] = lkp_Row_Path_condition[gmlfeatures[feat].attributes['condition']];
			 gmlfeatures[feat].attributes['condition'] = lkp_Row_Path_condition[pathConditionAndIssueCount[1]];
			 gmlfeatures[feat].attributes['legalstatus'] = lkp_Row_Path_status[gmlfeatures[feat].attributes['legalstatus']];
			 gmlfeatures[feat].attributes['responsibility'] = lkp_Row_Path_dept[gmlfeatures[feat].attributes['responsibility']];
			 gmlfeatures[feat].attributes['surveyed_by'] = lkp_Row_Path_surveyor[gmlfeatures[feat].attributes['surveyed_by']];
			 gmlfeatures[feat].attributes['ishistory'] = (gmlfeatures[feat].attributes['ishistory']=='True')?'Yes':'No';

		 }
	 }else if(actualLyrName == 'Access_Land'){
		 for (var feat in gmlfeatures){
			 gmlfeatures[feat].attributes['type'] = lkp_Access_Land_type[gmlfeatures[feat].attributes['type']];
			 gmlfeatures[feat].attributes['ishistory'] = (gmlfeatures[feat].attributes['ishistory'] == 'True')?'Yes':'No';
		 }
	 }else if(actualLyrName == 'Furniture'){
		 for (var feat in gmlfeatures){
			 gmlfeatures[feat].attributes['type'] = lkp_Furniture_type[gmlfeatures[feat].attributes['type']];
			 gmlfeatures[feat].attributes['condition'] = lkp_Furniture_condition[gmlfeatures[feat].attributes['condition']];
			 gmlfeatures[feat].attributes['surveyor'] = lkp_Furniture_surveyor[gmlfeatures[feat].attributes['surveyor']];
			 gmlfeatures[feat].attributes['unresolved_issues'] = (gmlfeatures[feat].attributes['unresolved_issues'] == 'true')?'Yes':'No';
		 } 
	 }else if(actualLyrName == 'Issue'){
		 for (var feat in gmlfeatures){
			 gmlfeatures[feat].attributes['issuetype'] = lkp_Issue_issuetype[gmlfeatures[feat].attributes['issuetype']];
			 gmlfeatures[feat].attributes['actionstatus'] = lkp_Issue_actionstatus[gmlfeatures[feat].attributes['actionstatus']];
			 gmlfeatures[feat].attributes['urgency'] = lkp_Issue_urgency[gmlfeatures[feat].attributes['urgency']];
			 gmlfeatures[feat].attributes['responsibility'] = lkp_Issue_responsibility[gmlfeatures[feat].attributes['responsibility']]
			 gmlfeatures[feat].attributes['why'] = lkp_Issue_why[gmlfeatures[feat].attributes['why']];
			 gmlfeatures[feat].attributes['ishistory'] = (gmlfeatures[feat].attributes['ishistory'] == 'True')?'Yes':'No';
			 gmlfeatures[feat].attributes['assigned_to'] = (gmlfeatures[feat].attributes['assigned_to'] != "")?getNameFromEMail(gmlfeatures[feat].attributes['assigned_to']):'';
		 } 
	 }else if(actualLyrName == 'Surface'){
		 for (var feat in gmlfeatures){
			 gmlfeatures[feat].attributes['type'] = lkp_Surface_type[gmlfeatures[feat].attributes['type']];
			 gmlfeatures[feat].attributes['condition'] = lkp_Surface_condition[gmlfeatures[feat].attributes['condition']];
			 gmlfeatures[feat].attributes['surveyor'] = lkp_Surface_surveyor[gmlfeatures[feat].attributes['surveyor']];
			 gmlfeatures[feat].attributes['unresolved_status'] = (gmlfeatures[feat].attributes['unresolved_status'] == 'true')?'Yes':'No';
		 } 
	 }else if(actualLyrName == 'Complaint_Layer'){
		 for (var feat in gmlfeatures){
			 gmlfeatures[feat].attributes['enquiry_type'] = lkp_Complaint_enquiryType[gmlfeatures[feat].attributes['enquiry_type']];
			 gmlfeatures[feat].attributes['complainantid'] = lkp_Complaint_complainant[gmlfeatures[feat].attributes['complainantid']];
			 gmlfeatures[feat].attributes['contactid'] = lkp_Complaint_contacts[gmlfeatures[feat].attributes['contactid']];
			 gmlfeatures[feat].attributes['ishistory'] = (gmlfeatures[feat].attributes['ishistory'] == 'True')?'Yes':'No';
		 }
	 }

 }
 */
function fillGrid1(features) {
	var queryString = "";
	if (relateLayer != undefined) {
		queryString = "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName="
			+ relateLayerName + "&maxFeatures=" + maxFeatureCount;
	} else {
		queryString = "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName="
			+ displayLayerName + "&maxFeatures=" + maxFeatureCount;
	}
	$
	.ajax({
		url : PROXY_PATH + url + queryString,
		success : function(data1) {
			var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
			var responseText = featureTypesParser.read(data1);
			var featureTypes = responseText.featureTypes;
			var myColumns = [];
			var name_Columns = [];
			var myColumns1 = [];
			var ctr = 0;
			var lastsel;
			for ( var i = 0; i < featureTypes[0].properties.length; ++i) {
				if (!featureTypes[0].properties[i].type.indexOf("gml")>=0) {

					if (!isColumnDisplayable(featureTypes[0].properties[i].name))
						continue;

					name_Columns[ctr] = featureTypes[0].properties[i].name;

					if(lang == 'cy'){
						myColumns[ctr] = $._(featureTypes[0].properties[i].name);
					}else{
						myColumns[ctr] = getFieldAlias(featureTypes[0].properties[i].name);
					}
					var val = new Object();
					val.name = name_Columns[ctr]; // name_Columns[ctr];
					val.index = name_Columns[ctr];
					; // myColumns[ctr];
					val.width = width;
					// Debs
					val.editable = true;
					if (featureTypes[0].properties[i].type
							.indexOf("int")>=0
							|| featureTypes[0].properties[i].type
							.indexOf("decimal")>0) {
						//val.sorttype = "int";
						//val.formatter = lookupFormatter;
					} else if (featureTypes[0].properties[i].type
							.indexOf("string")>=0) {
						val.sorttype = "text";
					} else if (featureTypes[0].properties[i].type
							.indexOf("date")>=0) {
						val.sorttype = "date";
						val.formatter = dateFormatter;
						//val.formatoptions = "newformat:'Y-M-d'";
					}
					myColumns1[ctr] = val;
					ctr++;
				}
			}

			//var rowHeight = 400; //625;
			//if (resultCount > maxFeatureCount) {
			//rowHeight = rowHeight * resultCount / maxFeatureCount;
			//}
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
						/* Bt APARESH- change editable grid to normal
										  onSelectRow : function(id) {
											if (id && id !== lastsel) {
												// jQuery('#tablegrid1').jqGrid('restoreRow',lastsel);
												// jQuery("#tablegrid1").jqGrid('saveRow',id,
												// false,'clientArray');
												// jQuery('#tablegrid1').jqGrid('editRow',id,true);
												var a = $('#tablegrid1')
														.saveRow(id, false,
																'clientArray');
												changedRows[id] = $(
														'#tablegrid1')
														.getRowData(id);
												jQuery('#tablegrid1').editRow(
														id, true);
												lastsel = id;
											}
										},*/
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
			+ "<button name='first' id='first' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='First Results' onclick='navigateFirstRecord();'><img src='resources/images/viewer/layouts/resultset_first.png' width='16px' height='16px'/></button>";
			grid_toolbar = grid_toolbar
			+ "<button name='previous' id='previous' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Previous Results' onclick='navigatePreviousRecord();'><img src='resources/images/viewer/layouts/resultset_previous.png' width='16px' height='16px'/></button>";
			grid_toolbar = grid_toolbar
			+ "<button name='next' id='next' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Next Results' onclick='navigateNextRecord();'><img src='resources/images/viewer/layouts/resultset_next.png' width='16px' height='16px'/></button>";
			grid_toolbar = grid_toolbar
			+ "<button name='last' id='last'  class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Last Results' onclick='navigateLastRecord();'><img src='resources/images/viewer/layouts/resultset_last.png' width='16px' height='16px'/></button>";
			grid_toolbar = grid_toolbar
			+ "<button name='zoom' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Zoom To Results' onclick='zoomToFeatures();'><img src='resources/images/viewer/layouts/zoom.png' width='16px' height='16px'/></button>";
			grid_toolbar = grid_toolbar
			+ "<button name='export' class='vtip fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right' title='Export Results' onclick='exportFeatures();'><img src='resources/images/viewer/layouts/export.png' width='16px' height='16px'/></button>";
			grid_toolbar = grid_toolbar
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
			mygrid[0].toggleToolbar();

			$(".fg-button").tipTip({
				fadeIn : 0,
				fadeOut : 0
			});

			if (isSpatialQryFilter || relateLayer != undefined) {
				$('#next').attr("disabled", true);
				$('#last').attr("disabled", true);
				$('#previous').attr("disabled", true);
				$('#first').attr("disabled", true);
			} else {
				$('#next').removeAttr("disabled");
				$('#last').removeAttr("disabled");
				$('#previous').removeAttr("disabled");
				$('#first').removeAttr("disabled");
			}

			if (populateGrid(features, true)) {
				isResultWndOpen = true;
				toggleGrid();
			}
		}
	});

	function flashFeatures(features, index) {
		if (!index) {
			index = 0;
		}
		var current = features[index];
		if (current && current.layer === vectorLayer) {
			vectorLayer.drawFeature(features[index], "select");
		}
		var prev = features[index - 1];
		if (prev && prev.layer === vectorLayer) {
			vectorLayer.drawFeature(prev, "default");
		}
		++index;
		if (index <= features.length) {
			window.setTimeout(function() {
				flashFeatures(features, index)
			}, 100);
		}
	}

	$("#bottombar").empty();
	$("#bottombar").append($("#tablegridContainer").html());

	// if (showDialog) {
	//
	// $("#tablegridContainer").dialog({
	// width: totalColumnWidth, // + 50,
	// height: 550,
	// title: "Result",
	// resizable: false,
	// close: function (ev, ui) {
	// $("#grid").remove();
	// isResultWndOpen = false;
	//
	// }
	// });
	// }
}

function createHighlightSLD(layer, filters) {
	var highlight_rules = [];

	var sld = {
			version : "1.0.0",
			namedLayers : {}
	};

	var layerName = layer.params.LAYERS;
	sld.namedLayers[layerName] = {
			name : layerName,
			userStyles : []
	};

	var highlightSymbolizer;
	if (lyrType.indexOf('Polygon') >= 0) {
		highlightSymbolizer = {
				Polygon : {
					strokeColor : '#07FCFB',
					fill : true,
					fillColor : "#FFFFFF",
					strokeWidth : 2,
					fillOpacity : 0.1
				}
		};
	} else if (lyrType.indexOf('Line') >= 0) {
		highlightSymbolizer = {
				Line : {
					strokeColor : '#07FCFB',
					strokeWidth : 2
				}
		};

	} else if (lyrType.indexOf('Point') >= 0) {
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

	var rule = new OpenLayers.Rule({
		title : "default",
		symbolizer : highlightSymbolizer,
		filter : filters.filters != null ? filters : filters[0],
				maxScaleDenominator : layer.options.minScale
	});
	highlight_rules[0] = rule;

	sld.namedLayers[layerName].userStyles.push({
		// name: 'multiple_rules',
		rules : highlight_rules
	});
	return new OpenLayers.Format.SLD().write(sld);
}

function getGeometryTyp(layer) {


	$.ajax({
		url : PROXY_PATH
		+ url
		+ "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName="
		+ layerMap[layer.name],
		dataType : "text",
		async : false,
		success : function(text) {

			if (text.search(/gml:MultiPolygon/i) >= 0
					|| text.search(/gml:Polygon/i) >= 0) {
				lyrType = 'Polygon';
			} else if (text.search(/gml:MultiLineString/i) >= 0
					|| text.search(/gml:LineString/i) >= 0) {
				lyrType = 'LineString';
			} else if (text.search(/gml:MultiPoint/i) >= 0
					|| text.search(/gml:Point/i) >= 0) {
				lyrType = 'Point';
			} else{
				lyrType = 'Polygon';
			}

		},
		error: function (xhr, status) {
			if(selectedLayer.indexOf("OSMM_") > -1){
				jAlert("WFS operation on " + layerMap[layer.name] + " layer is restricted");
			}else{
				jAlert('Sorry, there is a problem!');
			}
		}
	});
}

/*function fileupload() {

	var selRow = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');

	if (selRow.length < 1 || selRow.length > 1) {
		alert('Select one row');
		return;
	} else {
		var layerData = $("#tablegrid1").getRowData(selRow[0]); // Get First row to get
		var featureGid=layerData.gid;
		var layerName=OpenLayers.Map.activelayer.name;
		var fileupload = new Cloudburst.FileUpload(map, "sidebar",layerName,featureGid);
	}

}*/

function saveGrid() {

	var wfsRequest = "<wfs:Transaction service=\"WFS\" version=\"1.0.0\""
		+ " xmlns:" + prefix + "=" + "\"" + featureNS + "\""
		+ " xmlns:ogc=\"http://www.opengis.net/ogc\""
		+ " xmlns:wfs=\"http://www.opengis.net/wfs\">";

	$.each(changedRows, function(intIndex, objValue) {
		if (objValue) {
			wfsRequest = wfsRequest + " <wfs:Update typeName=\""
			+ displayLayerName + "\">";
			var id = objValue[uniqueField];
			var row = $("#tablegrid1").getRowData(intIndex);
			wfsRequest = wfsRequest
			+ "<ogc:Filter><ogc:PropertyIsEqualTo><ogc:PropertyName>"
			+ uniqueField + "</ogc:PropertyName><ogc:Literal>" + id
			+ "</ogc:Literal></ogc:PropertyIsEqualTo></ogc:Filter>";

			$.each(row, function(key, value) {
				wfsRequest = wfsRequest + "<wfs:Property><wfs:Name>" + key
				+ "</wfs:Name><wfs:Value>" + value
				+ "</wfs:Value></wfs:Property>";
			});
			wfsRequest = wfsRequest + " </wfs:Update>";
		}
	});
	wfsRequest = wfsRequest + " </wfs:Transaction>";

	$.ajax({
		url : PROXY_PATH + url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		data : wfsRequest,
		success : function(data) {
			alert("Saved successfully.");
		}
	});
}

function displayRowInfo(){
	var _currentLayer=info_layer;
	//alert('AL:'+OpenLayers.Map.activelayer.name+'-INFO:'+info_layer);
	//zoomToFeatures();

	var selRow = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');




	if (selRow.length < 1 || selRow.length > 1) {
		jAlert('Select one record');
		return;
	} else {
		//var mya = new Array();
		//mya = $("#tablegrid1").getDataIDs(); // Get All IDs
		var data = $("#tablegrid1").getRowData(selRow[0]); // Get First row to get

		//var info_Layer = displayLayerName.split(":");
		//var currentLayer = info_Layer[info_Layer.length - 1];


		//alert("ACTIVE LAYER: "+ OpenLayers.Map.activelayer.name);
		if(OpenLayers.Map.activelayer.name== "Access_Land" && _currentLayer == "Access_Land"){
			zoomToFeatures();
			var polygon = featureGeom[selRow[0]-1][0];
			/*var polygon = new OpenLayers.Geometry.MultiPolygon([geom]);*/
			var accessLane = new Cloudburst.AccessLand(map, "sidebar",data,polygon);		
		}
		else if(OpenLayers.Map.activelayer.name=="RoW_Path" && _currentLayer== "RoW_Path"){
			zoomToFeatures();
			var line = featureGeom[selRow[0]-1][0];
			var rowInfo = new Cloudburst.RowInfo(map, "sidebar",data,line);
		}
		else if(OpenLayers.Map.activelayer.name.toUpperCase()=="FURNITURE" && _currentLayer.toUpperCase() == "FURNITURE"){
			zoomToFeatures();
			var point = featureGeom[selRow[0]-1][0];
			var furniture = new Cloudburst.Furniture(map, "sidebar",data,point);
		}
		else if(OpenLayers.Map.activelayer.name.toUpperCase()=="ISSUE" && _currentLayer.toUpperCase() == "ISSUE"){
			zoomToFeatures();
			var point = featureGeom[selRow[0]-1][0];
			gridref=parseInt(point.x)+','+parseInt(point.y);
			var issue = new Cloudburst.Issue(map, "sidebar",data,point, featureGeom[selRow[0]-1][1]);

		}
		else if(OpenLayers.Map.activelayer.name.toUpperCase()=="SURFACE" && _currentLayer.toUpperCase() == "SURFACE"){
			zoomToFeatures();
			var line = featureGeom[selRow[0]-1][0];
			var surface = new Cloudburst.Surface(map, "sidebar",data,line);
		}
		else if(OpenLayers.Map.activelayer.name=="Complaint_Layer" && _currentLayer == "Complaint_Layer"){
			zoomToFeatures();
			if(data){
				var complaintid = data.complaintid;
				showComplaintDetails(complaintid);
			}			
		}
		else {
			if(_currentLayer != OpenLayers.Map.activelayer.name){
				jAlert('Please make Layer ' + _currentLayer + ' as active from Layer Manager Tab');
			}else{
				jAlert("Info not configured for " + _currentLayer);
			}
		}
	}

}

function showNextRecords_WithHistory() {
	var filter;
	var filters;

	if (relateLayer == undefined) {
		filter = new OpenLayers.Filter.Comparison({
			type : OpenLayers.Filter.Comparison.GREATER_THAN,
			property : uniqueField,
			value : recordIndex
		});

		if (queryFilter != null) {
			filters = new OpenLayers.Filter.Logical({
				type : OpenLayers.Filter.Logical.AND,
				filters : [ filter, queryFilter ]
			});
		} else {
			filters = filter;
		}
	} else {
		filters = null;
	}

	return filters;

}

