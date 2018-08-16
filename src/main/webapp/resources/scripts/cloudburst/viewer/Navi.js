var mapControls;
var lonLat;
var mapTipOptions =  {geometryName : ""};
var activeLayerURL = null;
var nav_his = [];
var size = -1;
var undo_redo = false;
var user_ist;
var region_list;
var project_list;

/*var selectionSymbolizer = {
	    'Polygon': {fillColor: '#FFFFFF', fillOpacity:0.1, stroke: true, strokeColor:'#07FCFB', strokeWidth: 2},
	    'Line': {strokeColor: '#FFFFFF', strokeWidth: 2},
	    'Point': {graphicName: 'circle', fillOpacity:0,stroke: true,strokeColor:'#07FCFB', pointRadius: 5,strokeWidth: 2}
	};*/





var landtypeid=[
    {"id":"1", "value":"Flat/Plain"},
    {"id":"2", "value":"Sloping"},
    {"id":"3", "value":"Mountainous"},
	{"id":"4", "value":"Valley"},
	{"id":"9999", "value":"NA"}
]



var landsoilqualityid=
[
   {"id":"1", "value":"Very good"},
   {"id":"2", "value":"Moderate good"},
   {"id":"3", "value":"Poor"},
   {"id":"4", "value":"Very poor"}
   ]



var landusetypeid=[
	    {"id":"1", "value":"Agriculture"},
	    {"id":"2", "value":"Settlement"},
	    {"id":"3", "value":"Livestock (intensive/ stationary)"},
		{"id":"4", "value":"Livestock (pastoralism)"},
		{"id":"5", "value":"Forest/ Woodlands"},
		{"id":"6", "value":"Forest Reserve"},
		{"id":"7", "value":"Grassland"},
		{"id":"8", "value":"Facility (church/mosque/recreation)"},
		{"id":"9", "value":"Commercial/Service"},
		{"id":"10", "value":"Minning"},
		{"id":"11", "value":"Wildlife (hunting)"},
		{"id":"12", "value":"Wildlife (tourism)"},
		{"id":"13", "value":"Industrial"},
		{"id":"14", "value":"Conservation"},
		{"id":"9999", "value":""}
		
	]





var acquisitiontypeid=[
    {"id":"1", "value":"Kupewa na Halmashauri ya Kijiji"},
    {"id":"2", "value":"Zawadi"},
    {"id":"3", "value":"Urithi"},
	{"id":"4", "value":"Kununua"}
]



var claimtypeid=[
	    {"id":"1", "value":"New claim"},
	    {"id":"2", "value":"Existing Claim or Right"},
	    {"id":"3", "value":"Disputed Claim"},
		{"id":"4", "value":"No Claim"}
	]

var landsharetypeid=[
    {"id":"1", "value":"Co-occupancy (Tenancy in Common)"},
    {"id":"2", "value":"Single Occupant"},
	{"id":"3", "value":"Co-occupancy (Joint tenancy)"},
	{"id":"4", "value":"Customary(Individual)"},
	{"id":"5", "value":"Customary(Collective)"},
	{"id":"6", "value":"Single Tenancy"},
	{"id":"7", "value":"Joint Tenancy"},
	{"id":"8", "value":"Common Tenancy"},
	{"id":"9", "value":"Collective Tenancy"},
    {"id":"9999", "value":""},
	
	
]




var tenureclassid=	[
	    {"id":"1", "value":"Derivative Right"},
	    {"id":"2", "value":"Customary Right of Occupancy"},
		{"id":"3", "value":"Right to Ownership"},
		{"id":"4", "value":"Right of Use"},
		{"id":"5", "value":"Formal Ownership (Free-hold)"},
		{"id":"6", "value":"Granted Right of Occupancy"},
		{"id":"7", "value":"Right to Manage"},
		{"id":"9999", "value":""}
		
		
		
	]
	
var spatialunitgroupid=	[
	    {"id":"1", "value":"Country"},
	    {"id":"2", "value":"Region"},
		{"id":"3", "value":"Province"},
		{"id":"4", "value":"Commune"},
		{"id":"5", "value":"Place"}
		
		
	]

	

var unitid=[

        {"id":"1", "value":"Foot"},
	    {"id":"2", "value":"Inches"},
		{"id":"3", "value":"Kilometer"},
		{"id":"4", "value":"Meter"},
		{"id":"5", "value":"Miles"},
		{"id":"5", "value":"dd"}

]
	




Cloudburst.Navi = function(_map) {

		map.addInteraction(zIn);
        map.addInteraction(zOut);

	


$("#toolbar button").bind("click", function(e) {

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
					jAlert($.i18n("err-select-layer"), $.i18n("gen-selection"));
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
	jQuery.ajax({
        url: STUDIO_URL +"user/info",
        async: false,
        success: function (data) {
            user_ist = data;

        }
    });

	
	jQuery.ajax({
        url: STUDIO_URL +"region/info",
        async: false,
        success: function (data) {
            region_list = data;

        }
    });
	
	jQuery.ajax({
			url: STUDIO_URL +"project/info",
			async: false,
			success: function (data) {
				project_list = data;

			}
		});
	
	var  popupInfo="";
     $("#tabs-Tool").empty();
				jQuery('#infoDiv').remove();
				jQuery.get("resources/templates/viewer/info.html", function(template) {
					jQuery('#infoDiv').css("visibility", "visible");
					addTab($.i18n("viewer-info"),template);
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
							
						     var a_feild = dispField.layerfield;
						    if(a_feild=="landtypeid"){
						    	
								$.each(landtypeid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							if(a_feild=="acquisitiontypeid"){
						    	
								$.each(acquisitiontypeid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							if(a_feild=="claimtypeid"){
						    	
								$.each(claimtypeid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							if(a_feild=="landsharetypeid"){
						    	
								$.each(landsharetypeid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							if(a_feild=="landusetypeid"){
						    	
								$.each(landusetypeid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							if(a_feild=="spatialunitgroupid1"){
						    	
								$.each(spatialunitgroupid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							if(a_feild=="spatialunitgroupid2"){
						    	
								$.each(spatialunitgroupid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							
							if(a_feild=="spatialunitgroupid3"){
						    	
								$.each(spatialunitgroupid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							
							if(a_feild=="spatialunitgroupid4"){
						    	
								$.each(spatialunitgroupid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							if(a_feild=="spatialunitgroupid5"){
						    	
								$.each(spatialunitgroupid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							
							if(a_feild=="unitid"){
						    	
								$.each(unitid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
						    }
							
							if(a_feild=="projectnameid"){
								
								$.each(project_list, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.name;
										return false; 
									}
								});

							}
							
							if(a_feild=="createdby"){
						    	
								$.each(user_ist, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.name;
										return false; 
									}
								});
						    }
							
							if(a_feild=="modifiedby"){
						    	
								$.each(user_ist, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.name;
										return false; 
									}
								});
						    }
							
							if(a_feild=="tenureclassid"){
						    	
								$.each(tenureclassid, function(idx, obj) {
									if(obj.id==attrValue){
									    attrValue=obj.value;
										return false; 
									}
								});
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