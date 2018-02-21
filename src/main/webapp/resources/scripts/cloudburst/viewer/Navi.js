var mapControls;
var lonLat;
var mapTipOptions =  {geometryName : ""};
var activeLayerURL = null;
var nav_his = [];
var size = -1;
var undo_redo = false;


/*var selectionSymbolizer = {
	    'Polygon': {fillColor: '#FFFFFF', fillOpacity:0.1, stroke: true, strokeColor:'#07FCFB', strokeWidth: 2},
	    'Line': {strokeColor: '#FFFFFF', strokeWidth: 2},
	    'Point': {graphicName: 'circle', fillOpacity:0,stroke: true,strokeColor:'#07FCFB', pointRadius: 5,strokeWidth: 2}
	};*/



Cloudburst.Navi = function(_map) {

		map.addInteraction(zIn);
        map.addInteraction(zOut);

/*
	var selection_vector = new OpenLayers.Layer.Vector("selection_vector", {
		reportError: true,
		projection: "EPSG:4326",
		isBaseLayer: false,
		visibility: true,        
		displayInLayerSwitcher: false
	});
	_map.addLayers([selection_vector]);

*/

/*
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
																	this.protocol = new OpenLayers.Protocol.HTTP({ 
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

	
*/



	//mapControls["maptip"].events.register("hoverfeature", this, hoverResponse);
	//mapControls["maptip"].events.register("outfeature", this, hoverOutResponse);

	//mapControls["measurelength"].setImmediate(true);
	//mapControls["measurearea"].setImmediate(true);
/*
	var history = new OpenLayers.Control.NavigationHistory({
		id : "history"
	});
	_map.addControl(history);
	history.activate();

	for ( var key in mapControls) {
		control = mapControls[key];
		_map.addControl(control);
	}
*/
	// ******************* Set selection layer

	//var selectLayers = [];
	//selectLayers.push(map.activelayer);
	
	
	//mapControls['selectbox'].setLayers(selectLayers);
	//mapControls['selectpolygon'].setLayers(selectLayers);

	//mapControls["selectbox"].events.register("selected", this, onSLDSelectResponse);
	//mapControls["selectpolygon"].events.register("selected", this, onSLDSelectResponse);

	// ***************************************

	//var zoomtoxy = new Cloudburst.ZoomToXY(_map);

	$("#toolbar button").bind("click", function(e) {

	//map.getViewport().removeEventListener('click',myFunction);
		//for ( var key in mapControls) {
			//var control = mapControls[key];
			//control.deactivate();

		//}
		
	 map.un('singleclick', mapClickCallback);	
	 removeDeactiveMarkupTool();
	 $("#defaultbutton").css("visibility","hidden");
      
	  tabSwitch();
		switch (e.currentTarget.id) {
		case 'zoomin':
			zIn.setActive(true);
			zOut.setActive(false);
			break;
		case 'zoomout':
     		zIn.setActive(false);
 			zOut.setActive(true);
			break;
		case 'pan':
			break;
		case 'info':
		  map.on('singleclick', mapClickCallback);
		//map.getViewport().addEventListener('click',myFunction);
			break;
		case 'measurelength':

			var measure = new Cloudburst.Measure(_map, "sidebar");
			break;
		case 'measurearea':
			Cloudburst.Navi.prototype.toggleControl("measurearea");
			break;
		case 'selectfeature':
			  var checked = $("#" + e.currentTarget.id).hasClass('ui-state-active1')
                if(checked) {
					if (selectClick !== null) {
					  map.addInteraction(selectClick);
					   selectClick.on('select', myCallback);
					}	  
				}  
			break;
		case 'selectbox':			
            dragBoxInteraction.on('boxend', function(event) {
                selectedFeatures = selectInteraction.getFeatures();
                selectedFeatures.clear();
                var extent = dragBoxInteraction.getGeometry().getExtent();
				map.getLayers().forEach(function(layer) {
					 if (layer instanceof ol.layer.Vector) {
					 layer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
						selectedFeatures.push(feature);
											
					 });
					 
					 }
					 
				});

			 });
           
		 // clear selection when drawing a new box and when clicking on the map
		    dragBoxInteraction.on('boxstart', function() {
			     if(selectedFeatures!=null){
					selectedFeatures.clear();
					}				 
				  });

				 var checked = $("#" + e.currentTarget.id).hasClass('ui-state-active1')
                if(checked) {
                    map.addInteraction(selectInteraction);
                    map.addInteraction(dragBoxInteraction);
                }
     
			break;
		case 'selectpolygon':
			//wfs_markup_poly.removeAllFeatures();selFeatureBbox=null;
			break;
		case 'zoomprevious':
			//history.previousTrigger();
			  if (size > 0) {
				undo_redo = true;
				map.getView().fit(nav_his[size - 1].extent, nav_his[size - 1].size);
				map.getView().setZoom(nav_his[size - 1].zoom);
				setTimeout(function() {
					undo_redo = false;
				}, 360);
				size = size - 1;
            }
			break;
		case 'zoomnext':
			//history.nextTrigger();
			 if (size < nav_his.length - 1) {
				undo_redo = true;
				map.getView().fit(nav_his[size + 1].extent, nav_his[size + 1].size);
				map.getView().setZoom(nav_his[size + 1].zoom);
				setTimeout(function() {
					undo_redo = false;
				}, 360);
				size = size + 1;
			}
			break;
		case 'fullview':
			var extent = map.getView().calculateExtent(map.getSize());
			 map.getView().fit( extent,18 );
	  
			break;
		case 'zoomtolayer':
		      if (active_layerMap != null){
						 if (active_layerMap.getSource() instanceof ol.source.Vector) {
						     map.getView().fit(active_layerMap.getSource().getExtent(), map.getSize());
						 } else if (active_layerMap.getSource() instanceof ol.source.Tile) {
							map.getView().calculateExtent(map.getSize());
						 }
			       
				}else{
					jAlert('Please Select A layer First', 'Selection');
				}
			
			break;
		case 'fixedzoomin':
			var view = map.getView();
			var newResolution = view.constrainResolution(view.getResolution(), -1);
			view.setResolution(newResolution);
		 
			break;
		case 'fixedzoomout':
			 var view = map.getView();
			 var newResolution = view.constrainResolution(view.getResolution(), 1);
			 view.setResolution(newResolution);
			break;
		case 'search':
			var search = new Cloudburst.Search(_map, "sidebar");
			break;
		case 'zoomtoxy':
			var zoomtoxy = new Cloudburst.ZoomToXY(_map, "sidebar");
			break;
		case 'maptip':
			var maptip = new Cloudburst.Maptip();
			break;
		case 'clear_selection':			
				clearSelection(true);
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
	
	
   map.on('moveend', function() {
    if (undo_redo === false) {
        if (size < nav_his.length - 1) {
            for (var i = nav_his.length - 1; i > size; i--) {
                nav_his.pop();
            }
        }
        nav_his.push({
            extent: map.getView().calculateExtent(map.getSize()),
            size: map.getSize(),
            zoom: map.getView().getZoom()
        });
        size = size + 1;
    }
}); 
	

	
};


function mapClickCallback(evt)
{
	var  popupInfo="";
     $("#tabs-Tool").empty();
				jQuery('#infoDiv').remove();
				jQuery.get("resources/templates/viewer/info.html", function(template) {
					jQuery('#infoDiv').css("visibility", "visible");
					addTab($._("info"),template);
                   jQuery("#info_accordion").empty();
				   jQuery('#infoDiv').css("visibility", "visible");

				   
				  /*
    var resolution = map.getView().getResolution();
    var layerWithWmsSource = map.forEachLayerAtPixel(evt.pixel,function(layer) {
        var source = layer.getSource();
        if (source instanceof ol.source.TileWMS) {
        	return layer;
        }
    });
    if (layerWithWmsSource) {
		
	  var url = layerWithWmsSource.getSource().getGetFeatureInfoUrl(evt.coordinate,resolution, 'EPSG:3857', {'INFO_FORMAT': 'application/json'});
	  var attrs= new  Object();
	    if (url) {
		    
			var parser = new ol.format.GeoJSON();
			$.ajax(url).then(function(response) {

			 var result = parser.readFeatures(response);
          if (result.length) {
            var info = [];
            for (var i = 0, ii = result.length; i < ii; ++i) {
              
			 var objeto = result[0].getProperties();
			  var propiedades;
			  popupInfo += '<h3 class="" ><a id="" href="#">Tiles layer</a></h3>';
              popupInfo += '<table class="featureInfo">';
				  for (propiedades in objeto) {
					    popupInfo += '<tr>';
                         popupInfo += '<th>' + propiedades + '</th>';
						 popupInfo += '<td>' + objeto[propiedades] + '</td>';
                         popupInfo += '</tr>';
						 
					}
					
				popupInfo += '</table>'; 	
				popupInfo += '</body></html>'; 	
            }
            
          } else {
			  
            
          }
          });
	
            }
			
		
      }
     */
    
			
			
					
	map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
		
		if (layer instanceof ol.layer.Vector) {
			var _features =feature;
			
			console.log(_features);
			var attrs= new  Object();

			var objeto = feature.getProperties();
			var propiedades;
				  for (propiedades in objeto) {
					  attrs[propiedades] = objeto[propiedades];
					}
	
			
				$.ajax({
					async:false,
					url: STUDIO_URL + "layer/" + layer.get('aname') + "/layerField" + "?" + token,
					success: function (displayableFields) {
						popupInfo += '<h3 class="" ><a id="'+ layer.get('aname')+'" href="#">'+ layer.get('aname')+'</a></h3>';

						popupInfo += '<table class="featureInfo">';
						$.each(displayableFields, function (i, dispField) {

							popupInfo += '<tr>';

							popupInfo += '<th>' + dispField.alias + '</th>';

							var attrValue = attrs[dispField.layerfield];
							if(!attrValue){
								attrValue ='';
							}
							

							popupInfo += '<td>' + attrValue + '</td>';

							popupInfo += '</tr>';

							//}	
						});


						popupInfo += '</table>'; 	
						popupInfo += '</body></html>'; 
						

					} 
                 						
                       				
				});
				
				
				
				
				
		}               
		

				
	
		
	    	
		 

	});
	
	 setTimeout(function () {
    jQuery("#info_accordion").html(popupInfo);
	jQuery("#info_accordion").accordion({fillSpace: true});
	
    }, 3000);
	

	
	});

}
								
	

function myCallback(evt){
     var feature=evt.target.getFeatures();
	 
	 
	 
    }	

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
/*
	for (key in mapControls) {
		var control = mapControls[key];

		if (element == key) {

			control.activate();
			//break;
		} else {
			control.deactivate();
		}

	}
*/
	/* Deactive search bound controls*/
	/*
	for (boundCtrlKey in boundControls) {
		var ctrl = boundControls[boundCtrlKey];
		ctrl.deactivate();
	}
*/
	/* Deactive markup controls*/
	/*
	for (key1 in markupControls) {
		var control = markupControls[key1];
		control.deactivate();
	}
*/
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