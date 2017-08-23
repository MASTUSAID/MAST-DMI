



var infoMarker;
var lon;
var lat;
var style = new OpenLayers.Style();

//temp amitabh
var infoLayer;
var gid;
var spatialType;	
var spatialType;	
var activeProject=null;

var onClick = function (e) {

	OpenLayers.Element.addClass(map.viewPortDiv, "olCursorWait");
	lonLat = map.getLonLatFromPixel(e.xy);

	lon = lonLat.lon.toFixed(2);
	lat = lonLat.lat.toFixed(2);

	var size = new OpenLayers.Size(24, 24);
	var offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
	var info_icon = new OpenLayers.Icon('resources/images/info_marker.png', size, offset);
	/* hide marker

	if (infoMarker) {
		map.getLayersByName("Markers")[0].removeMarker(infoMarker);
	}
	infoMarker = new OpenLayers.Marker(lonLat, info_icon)
	map.getLayersByName("Markers")[0].addMarker(infoMarker);
	 */
	var visible_layers=new Array();

	for(var vislyr  in layerMap) {
		//alert(map.getLayersByName(vislyr)[0].name+'-'+map.getLayersByName(vislyr)[0].visibility);
		
		if(vislyr!='Cosmetic')
		if (map.getLayersByName(vislyr)[0].visibility && map.getLayersByName(vislyr)[0].queryable)
		{
			visible_layers.push(layerMap[vislyr]);
		}

	}

	this.protocol.read({
		params: {
			REQUEST: "GetFeatureInfo",
			EXCEPTIONS: "application/vnd.ogc.se_xml",
			VERSION: "1.1.1",
			BBOX: map.getExtent().toBBOX(),
			X: Math.round(e.xy.x),
			Y: Math.round(e.xy.y),
			INFO_FORMAT: 'application/vnd.ogc.gml',
			LAYERS: visible_layers,			//layerMap[OpenLayers.Map.activelayer.name],
			QUERY_LAYERS: visible_layers,	//layerMap[OpenLayers.Map.activelayer.name],
			WIDTH: map.size.w,
			HEIGHT: map.size.h,
			SRS: map.getProjection(),
			FEATURE_COUNT:10
		},
		callback: onResponse
	});
}



function onPopupClose(evt) {
	this.hide();
	OpenLayers.Event.stop(evt);

	/* 
	if (infoMarker) {
				map.getLayersByName("Markers")[0].removeMarker(infoMarker);
	}
	 */
}



/*
 * This method apply sld on selected layer feature
 * by creating clone
 */
function applySymbol(layerName, featureId){
	var symbol;
	var _layer = map.getLayersByName(layerName)[0];

	var _layerType = getGeomType(_layer);

	var highlightSymbolizer;

	if(_layerType == 'Point'){
		highlightSymbolizer = {
				Point : {
					graphicName : 'circle',
					fill : false,
					strokeWidth : 2,
					strokeColor : '#07FCFB',
					pointRadius : 5
				}
		};
	}else if(_layerType == 'Polygon'){
		highlightSymbolizer = {
				Polygon : {
					strokeColor : '#07FCFB',
					fill : true,
					fillColor : "#FFFFFF",
					strokeWidth : 2,
					fillOpacity : 0.1
				}
		};
	}else if(_layerType == 'LineString'){
		highlightSymbolizer = {
				Line : {
					strokeColor : '#07FCFB',
					strokeWidth : 2
				}
		};
	}
	else{
		highlightSymbolizer = {
				Polygon : {
					strokeColor : '#07FCFB',
					fill : true,
					fillColor : "#FFFFFF",
					strokeWidth : 2,
					fillOpacity : 0.1
				}
		};
	}


	var _gid = featureId; //split and convert to integer
	var pos = _gid.indexOf(".");
	if(pos >= -1){
		_gid = parseInt(_gid.substring(++pos));
	}



	if(layerName=='spatial_unit')
	{	
		console.log(activeProject);
		var rule = new OpenLayers.Rule({
			title : "default",
			filter:new OpenLayers.Filter.Logical({
				type: OpenLayers.Filter.Logical.AND,
				filters: [ new OpenLayers.Filter.Comparison({
					type: OpenLayers.Filter.Comparison.EQUAL_TO,
					property: "usin",
					value: _gid
				}),
				new OpenLayers.Filter.Comparison({
					type: OpenLayers.Filter.Comparison.EQUAL_TO,
					property:  "project_name",
					value: activeProject
				})
				]
			}),
		symbolizer: highlightSymbolizer
		});
	}

		else{

			var rule = new OpenLayers.Rule({
				title : "default",
				filter: new OpenLayers.Filter.Comparison({
					type: OpenLayers.Filter.Comparison.EQUAL_TO,
					property: "ID",
					value: _gid
				}),
				symbolizer: highlightSymbolizer
			});
		}
	var sld_rules = [];
	var sld = {
			version: "1.0.0",
			namedLayers: {}
	};

	var actualLyrName = layerName;
	if(layerName=='spatial_unit')
		actualLyrName = layerMap[layerName];
	sld.namedLayers[actualLyrName] = {
			name: actualLyrName,
			userStyles: []
	};

	sld_rules.push(rule);
	sld.namedLayers[actualLyrName].userStyles.push({
		rules: sld_rules
	});

	//Remove the clone layer if it exists
	var clonedLayer = map.getLayersByName("clone")[0];
	if(clonedLayer != undefined){
		map.removeLayer(clonedLayer);
	}

	/*clonedLayer = _layer.clone();
    clonedLayer.setName("clone");
    map.addLayers([clonedLayer]);
    sld_body = new OpenLayers.Format.SLD().write(sld);
    clonedLayer.mergeNewParams({
        SLD: OpenLayers.Format.SLD().write(sld)
    });
    clonedLayer.redraw(true);*/
	sld_body = new OpenLayers.Format.SLD().write(sld);
	applySldOnFeature(sld_body, _layer, clonedLayer);
}

function applySldOnFeature(sld, layer, clonedLayer){

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

			//var layer = activeLayer; //OpenLayers.Map.activelayer;
			var clonedLayer = layer.clone();
			clonedLayer.setName("clone");
			map.addLayers([clonedLayer]);
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

function getOrederdFeatureList(featuredate, fetureorderList)
{
	var tempFeature={};
	var fieldName=null;
	var featurelist = fetureorderList.split(',');
	for(var i = 0; i < featurelist.length; i++)
	{
		fieldName = featurelist[i];
		for(var j = 0; j < featuredate.length; j++)
		{
			var dispField=featuredate[j];
			if(dispField.field==fieldName){	
				tempFeature[i]=dispField;
				break;
			}
		}
	}
	return tempFeature;
}



var onResponse = function (response) {	
	if (response.features.length > 0) {
		var popupInfo = "";
		var features=response.features;
		for (var int = 0; int < features.length; int++) {	
			if(features[int].gml.featureType =='spatial_unit' && features[int].attributes.project_name!=activeProject)
				features.splice(int, 1);
			
		}

		var popupInfo = '';
		popupInfo += '<html><body>';				//Info diplay in popup
		for(var i=0;i<features.length;i++){
			var _actuallayer=features[i].gml.featureNSPrefix+':'+features[i].gml.featureType;
			infoLayer = features[i].gml.featureType;
			var attrs = features[i].attributes;
			var _layer;
			var _fid=features[i].fid;
			var _gid=_fid.substr(_fid.indexOf('.')+1);
			for(var index  in layerMap) {
				if(layerMap[index ]==_actuallayer){
					//alert(index);
					_layer=index;
					infoLayer = _layer;
					break;
				}
				//document.write( lyrname + " : " + layerMap[lyrname] + "<br />");

			}

			//populateLayerLookups(_layer);

			$.ajax({
				async:false,

				url: STUDIO_URL + "layer/" + _layer + "/layerField" + "?" + token,
				success: function (displayableFields) {



					period_pos = _fid.indexOf('.');
					left_period_pos = _fid.substring(0, period_pos);
					right_period_pos = _fid.substring(period_pos + 1);
					popupInfo += '<h3 class="" ><a id="'+ _layer+'-'+_gid +'" href="#">'+ $._(left_period_pos) + '.' + right_period_pos + '<br>(' + $._('Location') + ': ' + lon + ", " + lat+')</a></h3>';
					popupInfo += '<table class="featureInfo">';
					$.each(displayableFields, function (i, dispField) {

						popupInfo += '<tr>';

						popupInfo += '<th>' + dispField.alias + '</th>';

						var attrValue = attrs[dispField.field];
						if(!attrValue){
							attrValue ='';
						}
						var httpidx = attrValue.indexOf("http");
						if(httpidx >= 0){	

							var slashidx = attrValue.lastIndexOf("/");
							var link="";
							if(slashidx > 0){

								link = attrValue.substr(slashidx+1,attrValue.length);
							}

							attrValue ='<a href="'+ attrValue +'" target="_blank">'+ link +'</a>';
						}

						popupInfo += '<td>' + attrValue + '</td>';

						popupInfo += '</tr>';

						//}	
					});


					popupInfo += '</table>'; 	

				}           
			});

		}
		popupInfo += '</body></html>'; 

		/********************popupr*****************************/
		/*var popup = new OpenLayers.Popup.FramedCloud("identify", lonLat, new OpenLayers.Size(250, 150), popupInfo, null, true, onPopupClose);
        popup.autoSize = false;
        map.addPopup(popup);*/
		/********************end*****************************/


		/********************Side Bar*****************************/

		jQuery.get('resources/templates/viewer/info.html', function(template) {


			addTab($._('home_page_info'),template);

			jQuery("#info_accordion").empty();
			jQuery("#info_accordion").html(popupInfo);	
			jQuery("#info_accordion").accordion({fillSpace: true});

			defaultTab=$('#info_accordion h3 a')[0].id;
			arrdefaultTab = defaultTab.split("-");
			applySymbol(arrdefaultTab[0], arrdefaultTab[1]);



			$('#info_accordion h3 a').click(function(event) {

				var str=event.currentTarget.id;
				var arrstr = str.split("-");
				applySymbol(arrstr[0], arrstr[1]);
			});

		});

		/********************end*****************************/   


	}

	OpenLayers.Element.removeClass(map.viewPortDiv, "olCursorWait");

}

var multi_onResponse = function (response) {	
	if (response.features.length > 0) {
		var popupInfo = "";
		var features=response.features;
		var popupInfo = '';
		popupInfo += '<html><body>';				//Info diplay in popup
		for(var i=0;i<features.length;i++){
			var _actuallayer=features[i].gml.featureNSPrefix+':'+features[i].gml.featureType;
			infoLayer = features[i].gml.featureType;
			var attrs = features[i].attributes;
			if(infoLayer=='spatial_unit'){
				spatialType=attrs.gtype;
			}
			var _layer;
			var _fid=features[i].fid;
			var _gid=_fid.substr(_fid.indexOf('.')+1);
			for(var index  in layerMap) {
				if(layerMap[index ]==_actuallayer){
					//alert(index);
					_layer=index;
					infoLayer = _layer;
					break;
				}
				//document.write( lyrname + " : " + layerMap[lyrname] + "<br />");

			}

			//populateLayerLookups(_layer);

			$.ajax({
				async:false,

				url: STUDIO_URL + "layer/" + _layer + "/layerField" + "?" + token,
				success: function (displayableFields) {



					period_pos = _fid.indexOf('.');
					left_period_pos = _fid.substring(0, period_pos);
					right_period_pos = _fid.substring(period_pos + 1);
					popupInfo += '<h3 class="" ><a id="'+ _layer+'-'+_gid +'" href="#">'+ $._(left_period_pos) + '.' + right_period_pos + '<br>(' + $._('Location') + ': ' + lon + ", " + lat+')</a></h3>';
					popupInfo += '<table class="featureInfo">';
					$.each(displayableFields, function (i, dispField) {

						popupInfo += '<tr>';

						popupInfo += '<th>' + dispField.alias + '</th>';

						var attrValue = attrs[dispField.field];
						if(!attrValue){
							attrValue ='';
						}
						var httpidx = attrValue.indexOf("http");
						if(httpidx >= 0){	

							var slashidx = attrValue.lastIndexOf("/");
							var link="";
							if(slashidx > 0){

								link = attrValue.substr(slashidx+1,attrValue.length);
							}

							attrValue ='<a href="'+ attrValue +'" target="_blank">'+ link +'</a>';
						}

						popupInfo += '<td>' + attrValue + '</td>';

						popupInfo += '</tr>';

						//}	
					});


					popupInfo += '</table>'; 	

				}           
			});

		}
		popupInfo += '</body></html>'; 

		/********************popupr*****************************/
		var popup = new OpenLayers.Popup.FramedCloud("identify", lonLat, new OpenLayers.Size(250, 150), popupInfo, null, true, onPopupClose);
		popup.autoSize = false;
		map.addPopup(popup);
		/********************end*****************************/


		/********************Side Bar*****************************/
		/*
			jQuery.get('resources/templates/viewer/info.html', function(template) {


				addTab($._('home_page_info'),template);

				jQuery("#info_accordion").empty();
				jQuery("#info_accordion").html(popupInfo);	
				jQuery("#info_accordion").accordion({fillSpace: true});

				defaultTab=$('#info_accordion h3 a')[0].id;
				arrdefaultTab = defaultTab.split("-");
				applySymbol(arrdefaultTab[0], arrdefaultTab[1]);



				$('#info_accordion h3 a').click(function(event) {

					var str=event.currentTarget.id;
					var arrstr = str.split("-");
					applySymbol(arrstr[0], arrstr[1]);
				});

			});
		 */
		/********************end*****************************/   


	}

	OpenLayers.Element.removeClass(map.viewPortDiv, "olCursorWait");

}


var single_onResponse = function (response) {
	if (response.features.length > 0) {
		var popupInfo = "";
		var attrs = response.features[0].attributes;

		$.ajax({
			url: STUDIO_URL + "layer/" + OpenLayers.Map.activelayer.name + "/layerField" + "?" + token,
			success: function (displayableFields) {

				// for display the result in popup
				var popupInfo = '';
				popupInfo += '<html><body><table class="featureInfo"><caption class="popupcaption">' + OpenLayers.Map.activelayer.name + '<br>(Location: ' + lon + ", " + lat + ')</caption>';

				$.each(displayableFields, function (i, dispField) {

					popupInfo += '<tr>';
					popupInfo += '<th>' + dispField.alias + '</th>';


					var attrValue = attrs[dispField.field];
					if(!attrValue)
						attrValue ='';

					var httpidx = attrValue.indexOf("http");
					if(httpidx >= 0){	

						var slashidx = attrValue.lastIndexOf("/");
						var link="";
						if(slashidx > 0){

							link = attrValue.substr(slashidx+1,attrValue.length);
						}

						attrValue ='<a href="'+ attrValue +'" target="_blank">'+ link +'</a>';
					}

					popupInfo += '<td>' + attrValue + '</td>';
					popupInfo += '</tr>';
				});

				popupInfo += '</table></body></html>';

				var popup = new OpenLayers.Popup.FramedCloud("identify", lonLat, new OpenLayers.Size(250, 150), popupInfo, null, true, onPopupClose);
				popup.autoSize = false;
				map.addPopup(popup);
			},
			error: function (){


				// for display the result in popup
				var popupInfo = '';
				popupInfo += '<html><body><table class="featureInfo"><caption class="featureInfo">' + OpenLayers.Map.activelayer.name + '<br>(Location: ' + lon + ", " + lat + ')</caption>';

				for(var key in attrs) {

					popupInfo += '<tr>';
					popupInfo += '<th>' + key + '</th>';

					var attrValue = attrs[key];
					if(attrValue == 'undefined')
						attrValue ='';

					popupInfo += '<td>' + attrValue + '</td>';
					popupInfo += '</tr>';
				};

				popupInfo += '</table></body></html>';

				var popup = new OpenLayers.Popup.FramedCloud("identify", lonLat, new OpenLayers.Size(250, 150), popupInfo, null, true, onPopupClose);
				popup.autoSize = false;
				map.addPopup(popup);


			}            
		});




	}

	OpenLayers.Element.removeClass(map.viewPortDiv, "olCursorWait");
}



function viewProjectName(project)

{
activeProject=project;	

}