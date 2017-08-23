

var project = null;
var baseLayers = [];
var cosmeticStatus = false; 
var DisclaimerMsg = null;
var cookieProjectName = null;
var sldExists=false;
var minScale;
var maxScale;
var layerDispName={};
var arr_Layers = [];
var displayInLayerMgr={};
var projectName=null;

OpenLayers.Map.prototype.activelayer = null; 
OpenLayers.Layer.prototype.selectFilter = null;

Cloudburst.Viewer = OpenLayers.Class({

	map: null,


	initialize: function (mapdiv, options, callback) {

		windowResize();

		$('#_loader').hide();
		$('#maptips').hide();
		//Aparesh 
		var $tabs = $( "#tab").tabs({
			tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close'>Remove Tab</span></li>"
		});

		$( "#tab span.ui-icon-close" ).live( "click", function() {
			var index = $( "li", $tabs ).index( $( this ).parent() );
			$tabs.tabs( "remove", index );



		});

		//End 

		project = options.project;

		OpenLayers.ProxyHost = PROXY_PATH; 
		//"resources/proxy.jsp?url=";

		//************* Fetching Project Data *****************************
		$.ajax({
			url: STUDIO_URL + "project/" + project + "?" + token,
			async: true,
			success: function (data) {
				projectName=data.name;

				DisclaimerMsg = (lang=='en')?data.disclaimer:$._('home_page_disclaimer_info');

				if(data.active){

					//DisclaimerMsg
					//cookieProjectName=data.name;
					cookieProjectName=data.name+'|'+user;
					if (DisclaimerMsg) {
						if ($.cookie(cookieProjectName) == null) {
							$('#DisclaimerDiv').css("visibility", "visible");
							$("#hidProjectName").val(data.name);
							$('#DisclaimerMsgDiv').html(DisclaimerMsg);

							$("#DisclaimerDiv").dialog({
								width: '520',
								minHeight: '100',
								resizable: false,
								modal: true,
								zIndex : '70000',
								closeText: 'hide',
								closeOnEscape: false,							
								open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); }

							});

						}
					}	
					//end

					var baseLayer=false;
					var indexMap = data.overlaymap;
					var bbox = data.maxextent.split(',');
					var bounds = new OpenLayers.Bounds(bbox[0], bbox[1], bbox[2], bbox[3]);
					var maxRes=(bbox[2]- bbox[0])/256;                
					cosmeticStatus=data.cosmetic;

					var Scale = new OpenLayers.Control.Scale();

					//******************* Add Map to DIV ******************************************
					var options = {
							scales: [10000000, 9000000, 8000000, 7000000, 6000000, 5000000, 1000000, 500000, 100000, 50000, 10000, 5000, 2500, 1000,500,100,50],
							//scales: [600000, 500000, 250000, 100000, 50000, 25000, 10000, 5000, 2500, 1000, 500],
							//scales: [100000, 50000, 25000, 10000, 5000, 2500, 1000, 500],
							//minScale: 50000,
							//maxScale: 500,
							div: mapdiv,
							panDuration: 100,
							maxExtent: bounds,
							//maxResolution: maxRes, 
							projection: data.projection.code,
							units: data.unit.name,
							allOverlays: true,
							controls: [
							           new OpenLayers.Control.Navigation({
							        	   dragPanOptions: {
							        		   enableKinetic: true
							        	   }
							           }),

							           new OpenLayers.Control.ScaleLine({div: document.getElementById("scaleline")}), 
							           new OpenLayers.Control.MousePosition({div: document.getElementById("mousepos")}), 
							           //new OpenLayers.Control.Scale(), 
							           Scale,
							           new OpenLayers.Control.PanZoomBar({
							        	   panIcons: false
							           })],
							           numZoomLevels: 12
					};
					OpenLayers.Lang.setCode(lang);
					var activelayer = data.activelayer;

					map = new OpenLayers.Map(options);

					var dummy = new OpenLayers.Layer("Dummy", {
						isBaseLayer: true
					});
					map.addLayers([dummy]);

					var apiKey = "AqTGBsziZHIJYYxgivLBf0hVdrAk9mWO5cQcb8Yux8sW5M8c8opEC2lZqKR1ZZXf";

					//base layer buttons
					if (data.projectBaselayers.length > 0) {
						console.log(data.projectBaselayers);
						for (var bl = 0, len = data.projectBaselayers.length; bl < len; bl++) {
							var baseLayerName = data.projectBaselayers[bl].baselayers.name;
							var baseDisplayLayerName = data.projectBaselayers[bl].baselayers.description;
							// $('#baselayer').append("<button id='" + baseLayerName + "' type='button'>" + baseDisplayLayerName + "</button>");

							if(data.projectBaselayers.length > 1){
								if(bl==0){
									$('#baselayer').append('<button id=' + baseLayerName + ' class="mapbl-button ui-state-default-map ui-corner-right-map">'+baseDisplayLayerName+'</button>');
								}
								else if(bl==(len-1)){

									$('#baselayer').append('<button id=' + baseLayerName + ' class="mapbl-button ui-state-default-map ui-corner-left-map">'+baseDisplayLayerName+'</button>');
								}
								else{
									$('#baselayer').append('<button id=' + baseLayerName + ' class="mapbl-button ui-state-default-map">'+baseDisplayLayerName+'</button>');
								}

							}

							baseLayers[bl] = baseLayerName;

							if (baseLayerName == "Google_Streets") {
								var gmap = new OpenLayers.Layer.Google(baseLayerName, // the default
										{
									numZoomLevels: 20
										});
								gmap.animationEnabled=true;
								map.addLayers([gmap]);
								gmap.setVisibility(false);

							}



							if (baseLayerName == "Google_Physical") {
								var gsat = new OpenLayers.Layer.Google(baseLayerName, {
									type: google.maps.MapTypeId.TERRAIN,
									numZoomLevels: 20
								});
								gsat.animationEnabled=true;
								map.addLayers([gsat]);
								gsat.setVisibility(false);
							}

							if (baseLayerName == "Google_Satellite") {
								var gsat = new OpenLayers.Layer.Google(baseLayerName, {
									type: google.maps.MapTypeId.SATELLITE,
									numZoomLevels: 20
								});
								gsat.animationEnabled=true;
								map.addLayers([gsat]);
								gsat.setVisibility(false);
							}

							if (baseLayerName == "Google_Hybrid") {
								var gmap = new OpenLayers.Layer.Google(baseLayerName, // the default
										{
									type: google.maps.MapTypeId.HYBRID,
									numZoomLevels: 20
										});
								gmap.animationEnabled=true;
								map.addLayers([gmap]);
								gmap.setVisibility(false);

							}

							if (baseLayerName == "Bing_Road") {
								var road = new OpenLayers.Layer.Bing({
									name:baseLayerName,
									key: apiKey,
									type: "Road",
									// custom metadata parameter to request the new map style - only useful
									// before May 1st, 2011
									metadataParams: {mapVersion: "v1"}
								});
								map.addLayers([road]);
								road.setVisibility(false);
							}

							if (baseLayerName == "Bing_Aerial") {
								var aerial = new OpenLayers.Layer.Bing({
									name:baseLayerName,
									key: apiKey,
									type: "Aerial",
									// custom metadata parameter to request the new map style - only useful
									// before May 1st, 2011
									metadataParams: {mapVersion: "v1"}
								});
								map.addLayers([aerial]);
								aerial.setVisibility(false);
							}

							if (baseLayerName == "Open_Street_Map") {									
								var osm = new OpenLayers.Layer.OSM(baseLayerName);
								map.addLayer(osm);									
								//map.addLayers([osm]);
								osm.setVisibility(false);
							}

							if (baseLayerName == "MapQuest_OSM") {									
								var mapquestosm = new OpenLayers.Layer.MapQuestOSM(baseLayerName);                   
								map.addLayer(mapquestosm);									
								mapquestosm.setVisibility(false);
							}




						}

						//******************* baselayer button ******************************************
						$(".mapbl-button:not(.ui-state-disabled)")
						.hover(
								function(){ 
									$(this).addClass("ui-state-hover-map"); 
								},
								function(){ 
									$(this).removeClass("ui-state-hover-map"); 
								}
						)
						.mousedown(function(){
							$(this).parents('.mapbl-buttonset-single:first').find(".mapbl-button.ui-state-active-map").removeClass("ui-state-active-map");
							if( $(this).is('.ui-state-active-map.mapbl-button-toggleable, .mapbl-buttonset-multi .ui-state-active-map') ){ $(this).removeClass("ui-state-active-map"); }
							else { $(this).addClass("ui-state-active-map"); }	
						})
						.mouseup(function(){
							if(! $(this).is('.mapbl-button-toggleable_map, .mapbl-buttonset-single .mapbl-button,  .mapbl-buttonset-multi .mapbl-button') ){
								$(this).removeClass("ui-state-active-map");
							}
						});
						//******************* baselayer button ******************************************


					}

					for (var i = data.projectLayergroups.length-1; i >=0; i--) {
						var lg = data.projectLayergroups[i].layergroups;

						//for (var j = 0, len1 = lg.layers.length; j < len1; j++) {
						for (var j = lg.layers.length-1; j >=0; j--) {	
							var lyr = lg.layers[j];
//							var ol_wms=null;
							var LayerObj = new Object();
							$.ajax({
								url: STUDIO_URL + "layer/" + lyr.layer + "?" + token,
								async: false,
								success: function (data) {

									//                            	if(data.name == indexMap){
									//                            		baseLayer=true;
									//                            		alert(baseLayer);
									//                            	}else{
									//                            		baseLayer=false;
									//                            	}
									layerMap[data.alias] = data.name;
									//	layerDispName[data.alias] = ($._(data.alias)=="")? data.displayname:$._(data.alias);
									layerDispName[data.alias] = data.displayname;
									displayInLayerMgr[data.alias]=data.displayinlayermanager;
									//******************* Load Layers ******************************************
									if(data.layertype.name=='WMS'){

										if (data.maxscale) {
											maxScale = data.maxscale
										}
										else {
											maxScale = 1;
										}

										if (data.minscale) {
											minScale = data.minscale
										}
										else {
											minScale = 99999999;
										}


										ol_wms = new OpenLayers.Layer.WMS(data.alias, data.url, {
											layers: data.name,
											STYLES: '',
											format: data.outputformat.name,
											transparent: true,
											tilesOrigin: map.maxExtent.left + ',' + map.maxExtent.bottom
										},

										{
											transitionEffect: 'resize',
											isBaseLayer: baseLayer,
											selectable: data.selectable,
											queryable: data.queryable,
											exportable: data.exportable,
											editable: data.editable,
											singleTile: !(Boolean(data.tiled)),
											displayInLayerManager:data.displayinlayermanager
											//maxScale: maxScale,
											//minScale:minScale											
										}

										);

										var lyr_bbox = data.maxextent.split(',');
										var lyr_maxextent = new OpenLayers.Bounds(lyr_bbox[0], lyr_bbox[1], lyr_bbox[2], lyr_bbox[3]);
										ol_wms.maxExtent = lyr_maxextent;

										if(data.alias == 'spatial_unit')
										{
											ol_wms.mergeNewParams({'CQL_FILTER': "project_name='"+projectName+"'"});
										}

										ol_wms.visibility=lyr.layervisibility;	//Aparesh, Implement layer visibility
										map.addLayers([ol_wms]);
										LayerObj['grouplayername'] = lg.name;
										LayerObj['layername'] = lyr.layer;
										LayerObj['definedmaxscale'] = data.maxscale;
										LayerObj['definedminscale'] = data.minscale;
										LayerObj['layervisibility'] = lyr.layervisibility;
										arr_Layers.push(LayerObj);
										/*
										if(lyr.layer == "Access_Land"){ //ABA

											   var al_layer = map.getLayersByName(lyr.layer)[0];
											   var al_theme = new AccessLand_Theme(al_layer);
											   var ap_filter = al_theme.createDisplayCriteria();
											   al_theme.applySLD(ap_filter);
										}
										 */
									}
									else if (data.layertype.name=='Tilecache')	{
										var servRes=calcServRes(maxRes,data.numzoomlevels);

										ol_wms = new OpenLayers.Layer.TileCache(data.alias,
												[data.url],
												data.name,
												{
											serverResolutions: servRes,
											buffer:1,
											transitionEffect:'resize'
												}
										);

										map.addLayers([ol_wms]);
									}
									else if (data.layertype.name=='WFS')	{

										var _wfsurl = data.url + "request=DescribeFeatureType&version=1.1.0&typename=" + data.name;

										$.ajax({
											url: PROXY_PATH + _wfsurl,
											dataType: "xml",
											async:false,
											success: function (schemadata) {
												var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
												var responseText = featureTypesParser.read(schemadata);
												var featureTypes = responseText.featureTypes;
												targetNamespace = responseText.targetNamespace;
												featPrefix = responseText.targetPrefix;
												featureTypesFields = featureTypes[0].properties;



												var wfs = new OpenLayers.Layer.Vector(data.alias, {
													strategies: [new OpenLayers.Strategy.BBOX()],
													protocol: new OpenLayers.Protocol.WFS({
														//headers: { Authorization : "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE="},
														url: data.url,
														version: "1.1.0",
														srsName: data.projectionBean.code,
														featureType: data.name,
														featurePrefix: featPrefix,
														maxFeatures: SpatialVue_Constants.WFS_MaxFeatures,
														featureNS: targetNamespace,
														resFactor:1
													})
												});
												map.addLayer(wfs);


											}
										});

									}


								}
							});
						}


					}



					var indexMapLayer = map.getLayersByName(indexMap)[0];
					if(indexMapLayer){
						var ovLayer = indexMapLayer.clone();
						ovLayer.setIsBaseLayer(true);
						var controlOptions = {
								maximized: false,                        
								layers: [ovLayer],	                        
								mapOptions:{numZoomLevels: 1}
						};
						var overview2 = new OpenLayers.Control.OverviewMap(controlOptions);
						overview2.isSuitableOverview = function() {
							return true;
						};
						map.addControl(overview2);
					}


					var markers = new OpenLayers.Layer.Markers("Markers");
					map.addLayer(markers);

					//******************* End Load Layers ******************************************
					if(baseLayers.length> 0){
						for (x = 0; x < baseLayers.length; x++) {
							map.getLayersByName(baseLayers[x])[0].setVisibility(false);
						}

						var baseLayer = map.getLayersByName(baseLayers[0])[0];
						baseLayer.setVisibility(true);
						map.setBaseLayer(baseLayer);
						$("#"+baseLayers[0]).addClass("ui-state-active-map");
					}

					// create an overview map control with the default options
					//var controlOptions = {
					//      minimized: true,
					//    mapOptions:options
					//};
					//postion the overview map above status bar
					var statusbarheight = $("#statusbar").height();
					//                $(".olControlOverviewMapElement").css("position","relative");
					//                $(".olControlOverviewMapElement").css("bottom",statusbarheight +"px");
					//                
					//                $(".olControlOverviewMapMaximizeButton").css("bottom",statusbarheight +"px");
					//                $(".olControlOverviewMapMinimizeButton").css("bottom",statusbarheight +"px");


					//map.zoomToExtent(bounds);

					var zooms = [];
					var resolutions = map.baseLayer.resolutions;
					var units = map.baseLayer.units;

					for (var i=resolutions.length-1; i >= 0; i--) {
						var res = resolutions[i];				           
						$('#scale-interval').append($("<option></option>").attr("value", i).text(parseInt(Math.round(OpenLayers.Util.getScaleFromResolution(res, units)))));

					}

					//implementation done for Layer Visibility based on min and max extents----------------------------------
					map.events.on({ "zoomend": function(e) {
						if(lang == 'cy'){
							var scale = map.getScale();
							if (scale >= 9500 && scale <= 950000) {
								scale = Math.round(scale / 1000) + "K";
							} else if (scale >= 950000) {
								scale = Math.round(scale / 1000000) + "M";
							} else {
								scale = Math.round(scale);
							}    
							Scale.element.innerHTML = OpenLayers.i18n("Graddfa = 1 : ${scaleDenom}", {'scaleDenom':scale});
						}
						ScaleRangeView();
					}
					});

					/* map.events.on({"moveend": function(e){
	                    	alert(minbbox[0], minbbox[1], minbbox[2], minbbox[3]);
	                    }})*/

					map.events.register('zoomend', this, function() {
						var zoomlevel = map.getZoom();

						$('#scale-interval').val(zoomlevel);
					});

					var minbbox = data.minextent.split(',');
					var minbounds = new OpenLayers.Bounds(minbbox[0], minbbox[1], minbbox[2], minbbox[3]);
					//var minbounds = new OpenLayers.Bounds(6528415.2216891,1293946.8492689,10906728.201255,3891582.8181507);	 //indus tower
					map.zoomToExtent(minbounds);
					//alert(minbbox[0], minbbox[1], minbbox[2], minbbox[3]);

					//$('#scale-interval').val(1);
					map.zoomTo($("#scale-interval option:selected").val());

					$('#scale-interval').change(function() { 
						map.zoomTo($("#scale-interval option:selected").val());
					});

					$("#OpenLayers_Control_MaximizeDiv").remove();
					$("#OpenLayers_Control_MinimizeDiv").remove();

					var SLD_URL =  "" + window.location;
					var pos = SLD_URL.indexOf("?");
					if(pos > -1){
						SLD_URL = SLD_URL.substring(0, pos) + "resources/temp/sld/" + user + "_sld.xml";
					}else{
						SLD_URL = SLD_URL + "resources/temp/sld/" + user + "_sld.xml";
					}

					//******* Add Cosmetic Layer ********************
					if(cosmeticStatus){
						var cosmetic_wms;
						$.ajax({
							url: "theme/checkSldExists/",
							async: false,
							success: function (flag) {
								sldExists=flag;
								if(flag){
									//SLD_URL = SLD_URL.substring(0, pos) + "resources/temp/sld/" + user + "_sld.xml";
									//******* Add Cosmetic Layer ********************
									cosmetic_wms = new OpenLayers.Layer.WMS("Cosmetic", SpatialVue_Constants.Cosmetic_URL, {
										layers: "Cosmetic_Point,Cosmetic_Line,Cosmetic_Poly",
										STYLES: '',
										format: 'image/png',
										transparent: true,          
										SLD: SLD_URL
									});
								}else{
									cosmetic_wms = new OpenLayers.Layer.WMS("Cosmetic", SpatialVue_Constants.Cosmetic_URL, {
										layers: "Cosmetic_Point,Cosmetic_Line,Cosmetic_Poly",
										STYLES: '',
										format: 'image/png',
										transparent: true     
									});
								}	
								map.addLayer(cosmetic_wms);

								if(!flag){
									cosmetic_wms.setVisibility(false);
								}

							}
						});
						layerMap["Cosmetic"] = "Cosmetic_Point,Cosmetic_Line,Cosmetic_Poly";
					}	
					callback(map);
				}
				else{
					$("body").hide();

					alert("Not Active");


				}

			}
		});


		//******************* Context Menu ******************************************
		/*
		$("#map").contextMenu({
            menu: 'myMenu'
        }, function (action, el, pos) {
           // alert('Action: ' + action + '\n\n' + 'Element ID: ' + $(el).attr('id') + '\n\n');

			Cloudburst.Navi.prototype.toggleControl("");
			switch (action) {

				case 'zoomin':
				case 'zoomout':
				case 'pan':
				case 'info':
				case 'selectbox':
				Cloudburst.Navi.prototype.toggleControl(action);
				break;

				case 'zoomprevious':
					map.getControl("history").previousTrigger();
					break;
				case 'zoomnext':
					map.getControl("history").nextTrigger();
					break;
				case 'fullview':
					map.zoomToMaxExtent();
					break;
				case 'zoomtolayer':
					map.zoomToExtent( OpenLayers.Map.activelayer.getMaxExtent());
					break;
				case 'fixedzoomin':
					map.zoomIn();
					break;
				case 'fixedzoomout':
					map.zoomOut();
					break;
				default:
			}	


        });
		 */
		//******************* End Context Menu ******************************************





		//******* zoom slider ****************

		// slider target
		var target = $('#zoom-slider');

		// create the slider
		target.slider({
			orientation: 	'horizontal',
			value: 			2,
			min: 			1,
			max: 			10,
			step: 			1,
			animate: 		true,
			stop: function() {

			}
		});

		//**************************

		/////////////////

		$(".colorpicker").css("z-index", 9999);


		$('#changeBgColor').ColorPicker({

			onSubmit: function (
					hsb, hex, rgb, el) {

				//$( el).val( hex);
				$(el).ColorPickerHide();
			},
			onBeforeShow: function () {
				$(
						this).ColorPickerSetColor(
								this.value);
			},
			onChange: function (
					hsb, hex, rgb) {

				$("#map-tab").css("background-color", "#" + hex);
				$("#changeBgColor").css("background-color", "#" + hex);
				//$(".colorpicker").css("z-index", 9999);

			}
		}).bind('keyup', function () {
			$(this).ColorPickerSetColor(this.value);
		});

		/////////////////////


	},

	CLASS_NAME: "Cloudburst.Viewer"
});

var windowResize = function(){

	var windowHeight = $(window).height();
	var headerHeight= $("#header").height(); 
	var toolbarHeight= $("#toolbar").height(); 

	var adjustedWinHeight = windowHeight - (headerHeight + toolbarHeight);
	$("#map").height(adjustedWinHeight - 120);

	$("#sidebar").height(adjustedWinHeight - 120);

}


function calcServRes(maxRes,numzoomlevels){


	var resultArr = [];

	var _maxRes=maxRes;

	for(var i=0;i<numzoomlevels;i++){

		resultArr[i]=_maxRes;

		_maxRes=_maxRes/2;		
	}
	return resultArr;

}

function calcScaleRange(maxRes,numzoomlevels){


	var resultArr = [];

	var _maxRes=maxRes;

	for(var i=0;i<numzoomlevels;i++){

		resultArr[i]=_maxRes;

		_maxRes=_maxRes*2;		
	}
	return resultArr;

}


function acceptDisclaimer(){
	$.cookie(cookieProjectName, cookieProjectName, { expires: 7 });
	$('#DisclaimerDiv').dialog('close'); 
}
function cancelDisclaimer() {
	$('#DisclaimerDiv').dialog('close');	 
	$('#freezeDiv').css("visibility", "visible");

}



//*******************************************************/



//var mapquestosm = new OpenLayers.Layer.MapQuestOSM();                   
//map.addLayer(mapquestosm);

//************************************************************************/

/*function changeMapScale(_scale){
	map.zoomToScale(parseInt(_scale));
}*/

function ScaleRangeView(){


	for (var i = 0; i < arr_Layers.length; i++) 
	{     
		var lyrdetailobj = arr_Layers[i];
		if(lyrdetailobj.grouplayername.toUpperCase() != 'OVERLAYS')
		{
			var CurMapLayer = map.getLayersByName(lyrdetailobj.layername)[0];
			if (Math.round(map.getScale()) > lyrdetailobj.definedmaxscale || Math.round(map.getScale()) < lyrdetailobj.definedminscale) {

				if($('#grpVisibility__' + lyrdetailobj.grouplayername)[0]==undefined){
					CurMapLayer.setVisibility(false);
				}
				else{
					if($('#grpVisibility__' + lyrdetailobj.grouplayername)[0].checked){
						if(lyrdetailobj.definedmaxscale >= Math.round(map.getScale()) && Math.round(map.getScale())>= lyrdetailobj.definedminscale){
							CurMapLayer.setVisibility(true);
						}else{
							CurMapLayer.setVisibility(false);
						}
					}
					else if(!$('#grpVisibility__' + lyrdetailobj.grouplayername)[0].checked){
						CurMapLayer.setVisibility(false);
					}else{
						CurMapLayer.setVisibility(true);
					}
				}
			}
			else {
				if($('#grpVisibility__' + lyrdetailobj.grouplayername)[0]==undefined){
					//if($("input[id='grpVisibility__"+lyrdetailobj.grouplayername+"']")[0]==undefined){
					CurMapLayer.setVisibility(lyrdetailobj.layervisibility);
				}
				else if($('#Visibility__' + CurMapLayer.name)[0]!=undefined){

					if($('#grpVisibility__' + lyrdetailobj.grouplayername)[0].checked==true && $('#Visibility__' + CurMapLayer.name)[0].checked==true){
						//if(($("input[id='grpVisibility__"+lyrdetailobj.grouplayername+"']")[0].checked==true) && ($('#Visibility__' + CurMapLayer.name)[0].checked==true)){
						CurMapLayer.setVisibility(true);
					}
					else {
						CurMapLayer.setVisibility(false);
					}
				}else if($('#Visibility__' + CurMapLayer.name)[0]==undefined){
					//if (Math.round(map.getScale()) >= lyrdetailobj.definedmaxscale || Math.round(map.getScale()) <= lyrdetailobj.definedminscale) {
					if(lyrdetailobj.definedmaxscale >= Math.round(map.getScale()) && Math.round(map.getScale())>= lyrdetailobj.definedminscale){
						if(CurMapLayer.visibility == false){
							CurMapLayer.setVisibility(true);
						}
					}else{
						if(CurMapLayer.visibility == true){
							CurMapLayer.setVisibility(false);
						}
					}
				}
				else{

				}


			}
		}

	}

}


jQuery(document).ready(function() {

	$.ajaxSetup ({cache: false});	//Always Refresh From server

	var $mapitems = $("#tab1")
	$mapitems.click(function(event) {


		$('#sidebar').show();
		$('#collapse').show();
	});


	var $itemsland = $("#tab2")

	$itemsland.click(function(event) {
		$("#defaultbutton").css("visibility","hidden");
		var landRecords = new LandRecords("landRecords");
		$('#sidebar').hide();
		$('#collapse').hide();

		$('#bottomstatusbar').hide();
	});



});

