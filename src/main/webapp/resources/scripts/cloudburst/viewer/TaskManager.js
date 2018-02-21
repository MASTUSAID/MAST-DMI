var featureState_t = null;
var layerType_t= null;
var geom_type;
var source;
insertedArr = [];
updateArr = [];
deleteArr = [];
featureId=[];
var _layer;
var adjudicationDialog=null;
var editControls_t = null;
var flag=false;
var aoitxtName = null;
var map;
Cloudburst.TaskManager = function (_map, _searchdiv) {
	
	map=null;
    map = _map;
    searchdiv = _searchdiv;
    showResultsinDialog = true;

    $("#tabs-Tool").empty();
	

    jQuery.get('resources/templates/viewer/taskmanager.html', function (template) {
		
		 $("#tabs-Tool").empty();
		addTab('Task Manager', template);
		$("#options-s-d_t").hide();
		
		$('#edit_layer_t').change(onEditLayerChange);
        populateEditableLayers_t();
		
		$("#options-s-t_t").click(function () {
            $("#options-s-d_t").slideToggle('fast');
        });
		
		$("#options1-s-a").click(function () {
            $("#options1-s-b").slideToggle('fast');
        });
		
		toggleButtons();
		
		$("#suballocation_t button").bind("click", function (e) {
			insertedArr=[];
			map.removeInteraction(selectInteraction_edit);
            map.removeInteraction(intraction_dragBox_aoi1);		
			map.removeInteraction(draw);
			
			 _layer = getLayerByAliesName($("#edit_layer_t").val());
			
			 if ($('#edit_layer_t').val() != "") {
				
				
                geom_type = 'Polygon';
				
				switch (e.currentTarget.id) {
					case 'polygon':
						 draw = new ol.interaction.Draw({
							source: _layer.getSource(),
							type: geom_type,
							geometryName: 'geometry'
						});
						map.addInteraction(draw);
						draw.on('drawend', function (e) {
								var features = e.feature;
								features.setProperties({'isactive':'true'})
								insertedArr.push(features);
								
								// remove Intraction on drae end event
								map.removeInteraction(draw);

								
                         });
                        break;
						
						case 'AllocationBox_t':
						flag=false;
							featureId=[];
					map.addInteraction(selectInteraction_edit);
                    map.addInteraction(intraction_dragBox_aoi1);
                    toggleEditControl('AllocationBox_t');
					intraction_dragBox_aoi1.on('boxend', function(event) {
					selectedFeaturesEdit = selectInteraction_edit.getFeatures();
					selectedFeaturesEdit.clear();
					var extent = intraction_dragBox_aoi1.getGeometry().getExtent();
					map.getLayers().forEach(function(layer) {
						 if (layer instanceof ol.layer.Vector) {
						 layer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
							 
							 if(layer.values_.aname ==$("#edit_layer_t").val()){
						    	selectedFeaturesEdit.push(feature);
								if(feature.getProperties().aoiid){
								featureId.push(feature.getProperties().aoiid);
								$("#hid_allocid").val(featureId);
								$("#hid_projectID").val(activeProject);
								
								flag=true;
								}else{
									
								 flag=false;
								}
							}

							
						 });
						 
						 }
						 
					});
					
					
					if(flag){
					flag=false;
					
					jQuery.ajax({
						    		url: "user/" ,
						    		async: false,
						    		success: function (data) {
						    			
										$("#AllocationRowData").empty();
						    			  if (data != null && data != "" && data != undefined)
						                  {
						                      jQuery("#AllocationTemplate").tmpl(data).appendTo("#AllocationRowData");
						                  }
						adjudicationDialog = $("#allocation-dialog-form").dialog({
		                         autoOpen: false,
									 height: 300,
                                     width: 250,
									resizable: false,
									modal: true,
									buttons: {
										"Ok": function () {
											saveAllocation();
										},
										"Cancel": function () {
											adjudicationDialog.dialog("destroy");
											if(selectedFeaturesEdit!=null){
												selectedFeaturesEdit.clear();
												}
											map.removeInteraction(selectInteraction_edit);
											map.removeInteraction(intraction_dragBox_aoi1);												
										}
									},
									close: function () {
										adjudicationDialog.dialog("destroy");
										if(selectedFeaturesEdit!=null){
												selectedFeaturesEdit.clear();
												}
											map.removeInteraction(selectInteraction_edit);
											map.removeInteraction(intraction_dragBox_aoi1);		
									}
								});
								
							
				
										 
										 
								adjudicationDialog.dialog("open");

						    		}
						    	
						    
						    	});
					}else{
					   
					   jAlert("Please Save  Feature before alloction", "Info");
						return false;
					}	
								

			 });
           
		 // clear selection when drawing a new box and when clicking on the map
		    intraction_dragBox_aoi1.on('boxstart', function() {
			     if(selectedFeaturesEdit!=null){
					selectedFeaturesEdit.clear();
					}				 
				  });

				
                    break;
				}
				} else {
                jAlert("Please Selected Layer", "Error");
            }
		});
		
		$("#subcelladjustedit_t button").bind("click", function (e) {
            featureState_t = "";
           
		   _layer = getLayerByAliesName($("#edit_layer_t").val());
		  
		    clearEditTool();
			snapInteraction_aoi = new ol.interaction.Snap({
				     source: _layer.getSource(),
					 edge:true,
					 vertex:true
                   });
			
			map.un('singleclick', mapClickCallbackInfo_resource);			
            switch (e.currentTarget.id) {
                case 'move_t':
                    toggleEditControl('modify');
                    modifyMode_t('move_t');
					break;
                case 'reshape_t':
                   toggleEditControl('modify');
				   modifyMode_t('reshape_t');
                    break;
                case 'resize':
                    toggleEditControl('modify');
                    //modifyMode('resize');
				    break;
                case 'rotate':
                    toggleEditControl('modify');
                    // modifyMode('rotate');
                    break;
                case 'removeFeature_t':
                    toggleEditControl('deleteFeature')
					 modifyMode_t('removeFeature_t');;
				    break;
                case 'split':
                    //featureState = "insert";
                    toggleEditControl('split');
                    break;
                case 'merge':
                    mergeFeatures();
                    break;
					
					case 'selectionBox_t':

					map.removeInteraction(draw);
					map.addInteraction(selectInteraction_edit);
                    map.addInteraction(intraction_dragBox_aoi);
                    toggleEditControl('selectionBox_t');
					intraction_dragBox_aoi.on('boxend', function(event) {
					selectedFeaturesEdit = selectInteraction_edit.getFeatures();
					selectedFeaturesEdit.clear();
					var extent = intraction_dragBox_aoi.getGeometry().getExtent();
					map.getLayers().forEach(function(layer) {
						 if (layer instanceof ol.layer.Vector) {
						 layer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
							 
							 if(layer.values_.aname ==$("#edit_layer_t").val()){
						    	selectedFeaturesEdit.push(feature);
						    	
							}

							
						 });
						 
						 }
						 
					});
					
								

			 });
           
		 // clear selection when drawing a new box and when clicking on the map
		    intraction_dragBox_aoi.on('boxstart', function() {
			     if(selectedFeaturesEdit!=null){
					selectedFeaturesEdit.clear();
					}				 
				  });

				
                    break;
                case 'clearselection_t':
				
                   // onEditLayerChange();
				    map.removeInteraction(selectInteraction_edit);
                    map.removeInteraction(intraction_dragBox_aoi);
					map.removeInteraction(intraction_dragBox_aoi1);
					map.removeInteraction(modifyInteraction_aoi);
					map.removeInteraction(snapInteraction_aoi);
					map.removeInteraction(deleteInteraction_aoi);
					map.removeInteraction(draw);
					map.removeInteraction(selectInteraction_edit);
                    map.removeInteraction(intraction_dragBox);
					if(selectedFeaturesEdit!=null)
					selectedFeaturesEdit.clear();
                    break;
					
                    case 'selectSave_t':
                    saveEdit_t();
                    break;
					
					case 'selectinfo_aoi':
                    map.on('singleclick', mapClickCallbackInfo_resource);
                    break;
					
					

            }
        });
		
		
    });
	
	
	
}


	function mapClickCallbackInfo_resource(evt)	{
map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
		
		if (layer instanceof ol.layer.Vector) {
			   var _features =feature;
			if(_features.id_.split('.')[0]=="la_spatialunit_aoi")
				{

				  
				  jQuery.ajax({
						    		url: "landrecords/resourceinfo"+"/"+_features.values_.aoiid ,
						    		async: false,
						    		success: function (data) {
						    			if(data!=null && data!="")
											$("#InfoRowData").empty();
										
										  if (data != null && data != "" && data != undefined)
						                  {
						                      jQuery("#InfoTemplate").tmpl(data).appendTo("#InfoRowData");
						                  }
						adjudicationDialog = $("#info-dialog-form").dialog({
		                         autoOpen: false,
									 height: 200,
                                     width: 600,
									resizable: false,
									modal: true,
									buttons: {
										"Cancel": function () {
											adjudicationDialog.dialog("destroy");
											if(selectedFeaturesEdit!=null){
												selectedFeaturesEdit.clear();
												}
											// map.un('singleclick', mapClickCallbackInfo_resource);	
												
										}
									},
									close: function () {
										adjudicationDialog.dialog("destroy");
										// map.un('singleclick', mapClickCallbackInfo_resource);	
										
									}
								});
								
							adjudicationDialog.dialog("open");
										

						    		}
						    	
						    
						    	});
								
				  
			
				} 
		} 						
                       				
				});
				
				
				
	}
dragInteraction_aoi.on('translateend', function(e) {

		  
			updateArr=[];
			var features = e.features.getArray();
			for (var i=0;i<features.length;i++){
			updateArr.push(features[i]);
			}
		
			})
			
	modifyInteraction_aoi.on('modifyend', function(e) {
			updateArr=[];
			var features = e.features.getArray();
			for (var i=0;i<features.length;i++){
			updateArr.push(features[i]);
			}

			})	
			
			
	deleteInteraction_aoi.getFeatures().on('add', function (e) {
		if(e.target.array_[0].id_.split('.')[0]=="la_spatialunit_aoi")
		{
      var geometry= e.target.array_[0].getGeometry().clone();
           newFeature = new ol.Feature({
                geometry: geometry,
				
          });			
		 var properties =  e.target.array_[0].getProperties();
		 if(properties.userid>0)
		 {
			 alert("Can not be deleted assign to a user")
			 map.removeInteraction(deleteInteraction_aoi);
			deleteInteraction_aoi.getFeatures().clear();
			return false;
			 
		 }
         newFeature.setGeometryName("geometry");
         newFeature.setProperties(properties);
         newFeature.setId(e.target.array_[0].getId());
		 newFeature.set('isactive', false);
		 deleteInteraction_aoi.getFeatures().clear();
		 
		 var _confirm=confirm('Are you sure you want to delete this feature?');
if (_confirm) {
       
	  
       updateArr=[];
	   updateArr.push(newFeature);
       saveEdit_t();

    } else{
		map.removeInteraction(deleteInteraction_aoi);
	    deleteInteraction_aoi.getFeatures().clear();
		 updateArr=[];
	}
	
		}else{
			
			alert("Please select Layer AOI");
			map.removeInteraction(deleteInteraction_aoi);
			deleteInteraction_aoi.getFeatures().clear();
		}
				
            });

function saveAllocation()
{
	
		var _flag1=$('input[name="userId"]:checked').length
		if(_flag1==0)
			{
				
			 jAlert('Please Select User');
				return false;
				
			}
		
	jQuery.ajax({
        type: "POST",
        url: "landrecords/updateResourceaoi",
        data: jQuery("#allocationformID").serialize(),
        success: function (data) {
            if (data) {
                adjudicationDialog.dialog("destroy");
				 if(selectedFeaturesEdit!=null){
					selectedFeaturesEdit.clear();
					_layer.getSource().clear();
						map.removeInteraction(selectInteraction_edit);
        				map.removeInteraction(intraction_dragBox_aoi1);		
					}				 

                jAlert('Data Sucessfully Saved', ' Resource Allocation');
                
            } else {
            	adjudicationDialog.dialog("destroy");
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
	
}



function getLayerByAliesName(layer) {
    var _layer = false;
    for (var i=0;i<map.getLayers().getLength();i++) {
        if (map.getLayers().getArray()[i].get('aname') === layer) { //check if layer exists
            _layer = map.getLayers().getArray()[i];
        }
    }
    return _layer;
}
	
		
function saveEdit_t() {
var featureNS_= window.location.protocol+'//'+window.location.host+'/';
var featureType_="la_spatialunit_aoi";


   formatWFS = new ol.format.WFS();
	   node = formatWFS.writeTransaction(insertedArr, updateArr, deleteArr, {
		   featureNS: featureNS_,
			featureType: featureType_,
			version: '1.1.0',
			gmlOptions: {srsName: "CRS:84"},
			srsName: "EPSG:4326"
		});	
			
		url = featureNS_+"geoserver/ows";
				
				
 $.ajax(url,{
                type: 'POST',
                dataType: 'xml',
                processData: false,
                contentType: 'text/xml',
                data:  s.serializeToString(node)
                }).done(function() {
			  
			insertedArr = [];
			updateArr = [];
			deleteArr = [];
			
			map.removeInteraction(selectInteraction_edit);
            map.removeInteraction(intraction_dragBox);
			map.removeInteraction(modifyInteraction_aoi);
			map.removeInteraction(deleteInteraction_aoi);
		    map.removeInteraction(intraction_dragBox_aoi1);
			map.removeInteraction(draw);
			map.removeInteraction(snapInteraction_aoi)   ; 
			if(selectedFeaturesEdit!=null)
			   selectedFeaturesEdit.clear();
			_layer.getSource().clear();
			
		
    });
	
    $('#edit_content').empty();
    $("#editApply").hide();
}


function populateEditableLayers_t() {

 $('#edit_layer_t').empty();
    $('#edit_layer_t').append($("<option></option>").attr("value", "").text("Select Layer"));
         for (var i=0;i<map.getLayers().getLength();i++) {
			 layer =map.getLayers().getArray()[i];
			  if (layer.getSource() instanceof ol.source.Vector) {
                      if(layer.values_.visible==true){
						   $('#edit_layer_t').append($("<option></option>").attr("value", map.getLayers().getArray()[i].values_.aname).text(map.getLayers().getArray()[i].values_.aname));
					  }
               }
			 
		 }
		 
		 $("#edit_layer_t").val(active_layerMap.values_.aname);
		 
}

toggleEditControl = function (element) {

    for (key in editControls_t) {
        var control = editControls_t[key];
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

    /* Deactive markup controls*/
    for (key1 in markupControls) {
        var control = markupControls[key1];
        control.deactivate();
    }
}

function modifyMode_t(mode) {
    
	
        switch (mode) {
            case "move_t":
               map.addInteraction(dragInteraction_aoi);
                break;
            case "resize":
                break;
            case "rotate":
                break;
            case "reshape_t":
               map.addInteraction(modifyInteraction_aoi);
			   map.addInteraction(snapInteraction_aoi);
			    break;
			case "removeFeature_t":
			   map.addInteraction(deleteInteraction_aoi);
                break;	
            default:
              
                break;
        }
    //}
}

function deactivateControls_t() {
    for (key in editControls_t) {
        var control = editControls_t[key];
        control.deactivate();
    }
}
