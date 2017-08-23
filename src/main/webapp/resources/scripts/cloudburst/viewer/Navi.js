

var mapControls;
var lonLat;
var mapTipOptions =  {geometryName : ""};
var activeLayerURL = null;

/*var selectionSymbolizer = {
	    'Polygon': {fillColor: '#FFFFFF', fillOpacity:0.1, stroke: true, strokeColor:'#07FCFB', strokeWidth: 2},
	    'Line': {strokeColor: '#FFFFFF', strokeWidth: 2},
	    'Point': {graphicName: 'circle', fillOpacity:0,stroke: true,strokeColor:'#07FCFB', pointRadius: 5,strokeWidth: 2}
	};*/



Cloudburst.Navi = function(_map) {

	var selection_vector = new OpenLayers.Layer.Vector("selection_vector", {
		reportError: true,
		projection: "EPSG:4326",
		isBaseLayer: false,
		visibility: true,        
		displayInLayerSwitcher: false
	});
	_map.addLayers([selection_vector]);


	mapControls = {

			zoomin : new OpenLayers.Control.ZoomBox({
				title : "Zoom in box",
				out : false
			}),
			zoomout : new OpenLayers.Control.ZoomBox({
				title : "Zoom out box",
				out : true
			}),
			pan : new OpenLayers.Control.Pan({
				title : "Pan"
					//displayClass: "olControlDragPan"

			}),
			measurelength : new OpenLayers.Control.Measure(OpenLayers.Handler.Path,
					{
				persist : true,
				displayClass:"olControlDefault",
				eventListeners : {
					measure : function(evt) {
						handleFinalMeasurement(evt);
						// getActualMeasure(evt.measure,evt.units,evt.order,'t');
					},
					measurepartial : function(evt) {
						handlePartialMeasurement(evt);
						// getActualMeasure(evt.measure,evt.units,evt.order,'p');
					},
					activate : function(evt) {
						measurementToolActivated();

					}
				}
					}),
					measurearea : new OpenLayers.Control.Measure(
							OpenLayers.Handler.Polygon, {
								persist : true,
								displayClass:"olControlDefault",
								eventListeners : {
									measure : function(evt) {
										handleFinalMeasurement(evt);
										// getActualMeasure(evt.measure,evt.units,evt.order,'t');
									},
									measurepartial : function(evt) {
										handlePartialMeasurement(evt);
										// getActualMeasure(evt.measure,evt.units,evt.order,'p');
									},
									activate : function(evt) {
										measurementToolActivated();

									}
								}
							}),
							selectfeature:new OpenLayers.Control.DrawFeature(
									selection_vector, OpenLayers.Handler.Point, {
										displayClass:"olControlInfo",
										callbacks: {
											done: function (p) {
												var pointFeature = new OpenLayers.Feature.Vector(p);

												if(OpenLayers.Map.activelayer.selectable){
													var objSelect = new Selection(pointFeature, OpenLayers.Map.activelayer);
													filter = objSelect.creationSelectionCriteria(this);

													objSelect.displaySelection(filter, selectionSymbolizer, OpenLayers.Map.activelayer);
													objSelect.displayResult(filter, true);
													OpenLayers.Map.activelayer.selectFilter = filter;
												}else{
													jAlert('Layer is not selectable', 'Selection');
												}

											}
										}
									}),
									selectbox : new OpenLayers.Control.DrawFeature(
											selection_vector, OpenLayers.Handler.RegularPolygon, {
												handlerOptions: {
													sides: 4,
													irregular:true
												},
												displayClass:"olControlDefault",
												callbacks: {
													done: function (p) {
														var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
														if(OpenLayers.Map.activelayer.selectable){
															var objSelect = new Selection(multipolygon, OpenLayers.Map.activelayer);
															filter = objSelect.creationSelectionCriteria(this);
															objSelect.displaySelection(filter, selectionSymbolizer, OpenLayers.Map.activelayer);
															objSelect.displayResult(filter, true);
															OpenLayers.Map.activelayer.selectFilter = filter;
														}else{
															jAlert('Layer is not selectable', 'Selection');
														}

														//selFeatureBbox=objSelect.geometry.getBounds().toBBOX();
													}
												}
											}),
											selectpolygon : new OpenLayers.Control.DrawFeature(
													selection_vector, OpenLayers.Handler.Polygon, {
														displayClass:"olControlDefault",
														callbacks: {
															done: function (p) {
																//var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
																var multipolygon = new OpenLayers.Feature.Vector(p);
																var objSelect = new Selection(multipolygon, OpenLayers.Map.activelayer);
																if(OpenLayers.Map.activelayer.selectable){
																	filter = objSelect.creationSelectionCriteria(this);
																	objSelect.displaySelection(filter, selectionSymbolizer, OpenLayers.Map.activelayer);
																	objSelect.displayResult(filter);
																	OpenLayers.Map.activelayer.selectFilter = filter;
																}else{
																	jAlert('Layer is not selectable', 'Selection');
																}

																//selFeatureBbox=objSelect.geometry.getBounds().toBBOX();
															}
														}
													}),
													info : new OpenLayers.Control(
															{
																displayClass:"olControlInfo",	
																activate : function() {
																	var handlerOptions = {
																			'single' : true,
																			'double' : false,
																			'pixelTolerance' : 0,
																			'stopSingle' : false,
																			'stopDouble' : false
																	};
																	this.handler = new OpenLayers.Handler.Click(this, {
																		'click' : onClick
																	}, handlerOptions);
																	this.protocol = new OpenLayers.Protocol.HTTP({ /* "http://cp947sw:8080/geoserver/wms?" */
																		url : OpenLayers.Map.activelayer.url,
																		format : new OpenLayers.Format.WMSGetFeatureInfo()
																	});
																	OpenLayers.Control.prototype.activate.call(this);
																}

															}),

															maptip : new OpenLayers.Control.GetFeature({
																//protocol : OpenLayers.Protocol.WFS.fromWMSLayer(null,mapTipOptions),
																box : false,
																click : false,
																clickout : false,
																hover : true,
																clickTolerance : 6,
																maxFeatures : 1
															}),
															intersection :new OpenLayers.Control.DrawFeature(
																	selection_vector, OpenLayers.Handler.RegularPolygon, {
																		handlerOptions: {
																			sides: 4,
																			irregular:true
																		},
																		displayClass:"olControlDefault",
																		callbacks: {
																			done: function (p) {
																				var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
																				if(OpenLayers.Map.activelayer.selectable){
																					var objIntersection = new Intersection(multipolygon, OpenLayers.Map.activelayer);
																					filter = objIntersection.creationSelectionCriteria(this);
																					objIntersection.displayResult(filter, true);
																					OpenLayers.Map.activelayer.selectFilter = filter;
																				}else{
																					jAlert('Layer is not selectable', 'Selection');
																				}

																				//selFeatureBbox=objSelect.geometry.getBounds().toBBOX();
																			}
																		}
																	}),
	};

	mapControls["maptip"].events.register("hoverfeature", this, hoverResponse);
	mapControls["maptip"].events.register("outfeature", this, hoverOutResponse);

	//mapControls["measurelength"].setImmediate(true);
	//mapControls["measurearea"].setImmediate(true);

	var history = new OpenLayers.Control.NavigationHistory({
		id : "history"
	});
	_map.addControl(history);
	history.activate();

	for ( var key in mapControls) {
		control = mapControls[key];
		_map.addControl(control);
	}

	// ******************* Set selection layer

	var selectLayers = [];
	selectLayers.push(map.activelayer);
	//mapControls['selectbox'].setLayers(selectLayers);
	//mapControls['selectpolygon'].setLayers(selectLayers);

	//mapControls["selectbox"].events.register("selected", this, onSLDSelectResponse);
	//mapControls["selectpolygon"].events.register("selected", this, onSLDSelectResponse);

	// ***************************************

	//var zoomtoxy = new Cloudburst.ZoomToXY(_map);

	$("#toolbar button").bind("click", function(e) {

		/*for ( var key in mapControls) {
			var control = mapControls[key];
			control.deactivate();

		}*/

		//remove unsaved markup and deactive current tool
		$("#defaultbutton").css("visibility","hidden");
		removeDeactiveMarkupTool();
		tabSwitch();
		switch (e.currentTarget.id) {
		case 'zoomin':
			Cloudburst.Navi.prototype.toggleControl("zoomin");
			break;
		case 'zoomout':
			Cloudburst.Navi.prototype.toggleControl("zoomout");
			break;
		case 'pan':
			Cloudburst.Navi.prototype.toggleControl("pan");
			break;
		case 'info':
			removeDeactiveMarkupTool();

			Cloudburst.Navi.prototype.toggleControl("info");
			break;
		case 'measurelength':

			var measure = new Cloudburst.Measure(_map, "sidebar");
			// Cloudburst.Navi.prototype.toggleControl("measurelength");
			break;
		case 'measurearea':
			Cloudburst.Navi.prototype.toggleControl("measurearea");
			break;
		case 'selectfeature':
			Cloudburst.Navi.prototype.toggleControl("selectfeature");
			break;
		case 'selectbox':			
			//wfs_markup_poly.removeAllFeatures();selFeatureBbox=null;
			Cloudburst.Navi.prototype.toggleControl("selectbox");
			break;
		case 'selectpolygon':
			//wfs_markup_poly.removeAllFeatures();selFeatureBbox=null;
			Cloudburst.Navi.prototype.toggleControl("selectpolygon");
			break;
		case 'zoomprevious':
			history.previousTrigger();
			break;
		case 'zoomnext':
			history.nextTrigger();
			break;
		case 'fullview':

			_map.zoomToExtent( OpenLayers.Map.activelayer.getMaxExtent(),18);
			break;
		case 'zoomtolayer':
			_map.zoomToExtent( OpenLayers.Map.activelayer.getMaxExtent());
			break;
		case 'fixedzoomin':
			_map.zoomIn();
			break;
		case 'fixedzoomout':
			_map.zoomOut();
			break;
		case 'search':
			var search = new Cloudburst.Search(_map, "sidebar");
			break;
		case 'zoomtoxy':
			// zoomtoxy.toggle();
			var zoomtoxy = new Cloudburst.ZoomToXY(_map, "sidebar");
			break;
		case 'maptip':
			//Cloudburst.Navi.prototype.toggleControl("maptip");
			var maptip = new Cloudburst.Maptip();
			break;
		case 'clear_selection':			
			if(OpenLayers.Map.activelayer.name == 'Access_Land'){
				clearSelection(true, OpenLayers.Map.activelayer);
			}else{
				clearSelection(true);
			}
				break;
		case 'intersection' :
			spatialDialog = $( "#validation-dialog-form" ).dialog({
				autoOpen: false,
				height: 230,
				width: 234,
				resizable: true,
				modal: true,
				
				buttons: {
					"Ok": function() 
					{
						
						var selected = $("#radioSpatial input[type='radio']:checked");
					if (selected.length > 0) {
						spatial_validation= selected.val();
							if(spatial_validation=="2")
							{
							Cloudburst.Navi.prototype.toggleControl("intersection");
							spatialDialog.dialog( "destroy" );
							}
							
							else if(spatial_validation=="1")
							{
							
							var objIntersection = new Intersection("allBounds", OpenLayers.Map.activelayer);
							filter = objIntersection.creationSelectionCriteria("check");
							objIntersection.displayResult(filter, true);
							OpenLayers.Map.activelayer.selectFilter = filter;
							spatialDialog.dialog( "destroy" );
							}
							else{
								
								var hamlet_Id=$("#hamletSpatialId").val();
								if(hamlet_Id!=0){
									
									var objIntersection = new Intersection("allBounds", OpenLayers.Map.activelayer);
									filter = objIntersection.creationSelectionCriteria("check",hamlet_Id);
									objIntersection.displayResult(filter, true);
									OpenLayers.Map.activelayer.selectFilter = filter;
									spatialDialog.dialog( "destroy" );
										
								}
								
								else{
									
									
									alert("Please Select Hamlet ");
								}
								
							}
						}
						
						
					},
					"Cancel": function() 
					{
						spatialDialog.dialog( "destroy" );
						spatialDialog.dialog( "close" );
					}
				},
				close: function() {
					spatialDialog.dialog( "destroy" );

				}
			});
			$('input:radio[name="spatial_validation"][value="2"]').prop('checked', true);
			spatial_validType="Select by rectangle";
			$('#hamletSpatial').hide();
			spatialDialog.dialog( "open" );	
				


			/*
			var sel_clonedLayer = map.getLayersByName("clone")[0];
			if(sel_clonedLayer != undefined){
				map.removeLayer(sel_clonedLayer);
			}

        	 if(markers){
        		 markers.clearMarkers();
        	 }
			 */
			break;
		default:
		}
	});

	$("#navtoolbar").dialog({
		title : 'Tools',
		resizable : false,
		width : 69,
		minWidth : 69,
		minHeight : 33,
		autoOpen : true,
		position : [ 1184, 115 ]
	});

};


var onSLDSelectResponse = function(response){

	OpenLayers.Map.activelayer.selectFilter=response.filters[0];
	alert(OpenLayers.Map.activelayer.selectFilter);

};

var onResponse = function(response) {
	var popupInfo = "";
	var attrs = response.features[0].attributes;
	/*
	 * for(var index in attrs) { console.log( index + " : " + attrs[index]); }
	 */

	// ************** Info Popup ***************************
	jQuery('#infoDiv').remove();

	// if( jQuery("#infoDiv").length<=0){
	jQuery.get("resources/templates/viewer/info.html", function(template) {

		$("#sidebar").append(template);
		jQuery('#infoDiv').css("visibility", "visible");
		jQuery("#infoBody").empty();

		jQuery("#InfoTemplate").tmpl(null,

				{
			attrsList : attrs
				}

		).appendTo("#infoBody");

		alert(jQuery("#infoDetails").html());
		/*
		 * popupInfo = "<html>"; popupInfo += "<body>"+jQuery("#infoDetails").html()+"</body></html>";
		 * var popup = new OpenLayers.Popup.FramedCloud("identify", lonLat, new
		 * OpenLayers.Size(250, 150), popupInfo, null, true); popup.autoSize =
		 * false; map.addPopup(popup);
		 * 
		 * OpenLayers.Element.removeClass(map.viewPortDiv, "olCursorWait");
		 */
	});
	// }

}

Cloudburst.Navi.prototype.toggle = function() {
	if ($('#navtoolbar').dialog('isOpen')) {
		$('#navtoolbar').dialog('close');
	} else {
		$('#navtoolbar').dialog('open');
	}
};

Cloudburst.Navi.prototype.toggleControl = function(element) {

	for (key in mapControls) {
		var control = mapControls[key];

		if (element == key) {

			control.activate();
			//break;
		} else {
			control.deactivate();
		}

	}

	/* Deactive search bound controls*/
	for (boundCtrlKey in boundControls) {
		var ctrl = boundControls[boundCtrlKey];
		ctrl.deactivate();
	}

	/* Deactive markup controls*/
	for (key1 in markupControls) {
		var control = markupControls[key1];
		control.deactivate();
	}

	/* Deactive editControls_issue controls of issue
	for (key_issue in editControls_issue) {
		var control = editControls_issue[key_issue];
			control.deactivate();
	}
	 */	
};

function tabSwitch(){

	$('#tab').tabs("select","#map-tab");
	$('#sidebar').show();
	$('#collapse').show();
}