saveStrategy = null;
var selectedFeature = null;
var splitvector = null;
var wfs = null;
var verticesLayer = null;
var wfs_del = null;
var wfsurl = null;
var featPrefix = null;
var targetNamespace = null;
var geometryColName = null;
var cosmetic_geometryColName = null;
var objLayer = null;
var layerType;
var featureState = "";
var currentFeature;
var editControls = null;
var snap = null;
var activeLayer = null;
var validationRuleParams = {required: false, number: false};
var featureNS_;
var featureType_;
var formatWFS ;
var formatGML ;
var node;
var insertedArr = [];
var updateArr = [];
var deleteArr = [];
var s = new XMLSerializer();
var url;
var drawLine=null;
var selectSingleClick=null;
var vectorSource;
var featureArr = [];
var _landid;
var _flagSplit=false;
Cloudburst.Editing = function (_map, _searchdiv) {
    map = _map;
    searchdiv = _searchdiv;
    showResultsinDialog = true;

  //  if (saveStrategy != null) {
  ///      this.Unregister();
  //  }
 //   saveStrategy = new OpenLayers.Strategy.Save();

   

    jQuery.get('resources/templates/viewer/editing.html', function (template) {
      $("#tabs-Tool").empty();
        //Add tad
        addTab($.i18n("viewer-edit"), template);
        $("#editing-help").tipTip({defaultPosition: "right"});

        $("#options-s-d").hide();

        $('#edit_layer').change(onEditLayerChange);
        populateEditableLayers();

        $("#options-s-t").click(function () {
            $("#options-s-d").slideToggle('fast');
        });


        $("#options1-s-d").hide();

        $("#options1-s-t").click(function () {
            $("#options1-s-d").slideToggle('fast');
        });

		$("#options1-s-a").click(function () {
            $("#options1-s-b").slideToggle('fast');
        });
		
		
        $("#options2-s-d").hide();

        $("#options2-s-t").click(function () {
            $("#options2-s-d").slideToggle('fast');
        });

        $("#edit_tolerance").spinner({
            min: 1,
            max: 10

        });
        $("#max_features").spinner({
            min: 1,
            max: 10
        });
        $("#edit_tolerance_two").spinner({
            min: 1,
            max: 100
        });

        $("#options3-s-d").hide();
        $('#options3-s-t').click(function () {
            $('#options3-s-d').slideToggle('fast', function () {
                // Animation complete.
            });
        });

        $("#options4-s-d").hide();
        $('#options4-s-t').click(function () {
            $('#options4-s-d').slideToggle('fast', function () {
                // Animation complete.
            });
        });

        toggleButtons();
		_flagSplit=false;

        $("#subcelladjust button").bind("click", function (e) {
            featureState = "";
           			
            switch (e.currentTarget.id) {
                case 'selectionBox':
					map.getLayers().forEach(function (layer) {
						if (layer.get('aname') != undefined & layer.get('aname') === 'featureWorkflow') {
							map.removeLayer(layer);
						}
					});
				
				    map.getLayers().forEach(function (layer) {
						if (layer.get('aname') != undefined & layer.get('aname') === 'featureaoilayer') {
							map.removeLayer(layer);
						}
					});
					
					map.addInteraction(selectInteraction_edit);
                    map.addInteraction(intraction_dragBox);
                    toggleEditControl('selectionBox');
					intraction_dragBox.on('boxend', function(event) {
					selectedFeaturesEdit = selectInteraction_edit.getFeatures();
					selectedFeaturesEdit.clear();
					var extent = intraction_dragBox.getGeometry().getExtent();
					map.getLayers().forEach(function(layer) {
						 if (layer instanceof ol.layer.Vector) {
						 layer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {
							 
							 if(layer.values_.aname ==$("#edit_layer").val()){
						    	selectedFeaturesEdit.push(feature);
								
							}

							
						 });
						 
						 }
						 
					});

			 });
           
		 // clear selection when drawing a new box and when clicking on the map
		    intraction_dragBox.on('boxstart', function() {
			     if(selectedFeaturesEdit!=null){
					selectedFeaturesEdit.clear();
					
					}				 
				  });

				
                    break;
                case 'clearselection':
                   // onEditLayerChange();
				    map.removeInteraction(selectSingleClick);
				    map.removeInteraction(drawLine);
				    map.removeInteraction(selectInteraction_edit);
                    map.removeInteraction(intraction_dragBox);
					map.removeInteraction(modifyInteraction);
					map.removeInteraction(deleteInteraction);
					map.removeInteraction(selectSingleClick);
					if(selectedFeaturesEdit!=null)
					selectedFeaturesEdit.clear();
                    break;
                case 'selectUndo':
                    if (undoredo != undefined) {
                        undoredo.undo();
                    }
                    break;
                case 'selectRedo':
                    if (undoredo != undefined) {
                        undoredo.redo();
                    }
                    break;
                case 'selectSave':
                    saveEdit();
                    break;
            }
        });
        $("#subcelladjustcreate button").bind("click", function (e) {

			map.removeInteraction(drawLine);
			map.removeInteraction(selectSingleClick);
		 	if ($('#edit_layer').val() != "") {
                featureState = "";
             var _layer = getLayerByAliesName($("#edit_layer").val());
                switch (e.currentTarget.id) {
                    case 'point':
                         selectSingleClick = new ol.interaction.Select({
						layers: function (layer) {
							return layer.get('aname') == $("#edit_layer").val();
						}
					
					});
                    map.addInteraction(selectSingleClick);
					selectSingleClick.on('select', function(e) {
                       console.log(  e.target.getFeatures());
					   featureArr=[];
					   featureArr.push(e.target.getFeatures());
		             
                      });
					  
					 snapInteraction = new ol.interaction.Snap({
				     source: _layer.getSource(),
					 edge:true,
					 vertex:true
                   });
				    map.addInteraction(snapInteraction);
						  
                        break;
                    case 'line':
                    	  toggleEditControl('line');
                    	  drawLine = new ol.interaction.Draw({
  							source: _layer.getSource(),
  							type: "LineString",
  							geometryName: 'geometry'
  						});
  						map.addInteraction(drawLine);
  							drawLine.on('drawend', function (e) {
  							var features = e.feature;
							cut(featureArr[0],features)	;
                           });
						   
						   drawLine.on('drawstart', function (e) {
							   vectorSource.clear(); 

							});

                        break;
                    case 'polygon':
                        if (layerType == 'Polygon') {
                            featureState = "insert";
                            toggleEditControl('polygon');
                        } else {
                            jAlert($.i18n("err-layer-not-polygon-type"), $.i18n("err-error"));
                        }
                        break;
                }
            } else {
                jAlert($.i18n("err-select-layer"), $.i18n("err-error"));
            }

        });
        
        
        function cut(polygons, blade) {
        	if (blade && polygons) {
        		var claimtype= polygons.array_[0].getProperties().claimtypeid;
				if(claimtype==3 && claimtype==4){
					 jAlert($.i18n("err-cant-split-unclaimed"));
				   map.removeInteraction(selectSingleClick);
				   map.removeInteraction(drawLine);
				   setTimeout(
					  function() 
					  {
					     vectorSource.clear();
					  }, 2000);
				  _flagSplit=false;
				  
				}else{
					split(polygons, blade);
				}
        	}else{
				   jAlert($.i18n("err-select-feature-for-split"));
				   map.removeInteraction(selectSingleClick);
				   map.removeInteraction(drawLine);
				   setTimeout(
					  function() 
					  {
					     vectorSource.clear();
					  }, 2000);
				  _flagSplit=false;

			}
        }

        function split(polygon, blade) {
			var _polygon=polygon;
			insertedArr=[];
			updateArr=[];
			_landid=0;
        	var parser = new jsts.io.OL3Parser();
        	var writer = new jsts.io.WKTWriter();

        	var jstsGeompoly = parser.read(polygon.array_[0].getGeometry());
        	var jstsGeomline = parser.read(blade.getGeometry());

        	var union = jstsGeompoly.getExteriorRing().union(jstsGeomline);


        	var polygonizer = new jsts.operation.polygonize.Polygonizer();
        	polygonizer.add(union);

			var _j=0;
			
        	var polygons = polygonizer.getPolygons();
			if(polygons.size()==2){
				for (var i = polygons.iterator(); i.hasNext();) {
        		var polygon = i.next();
					console.log(writer.write(polygon));
				var geometry=parser.write(polygon);
				newFeature = new ol.Feature({
					geometry: geometry,
					
			  });
			   var properties = _polygon.array_[0].getProperties();
			   _landid=properties.landid;
			   delete properties.geometry;
			   if( _j==0){
				newFeature.setProperties(properties);
				newFeature.setId(_polygon.array_[0].id_);
				updateArr.push(newFeature);
				console.log(newFeature);
			 	}else{
					delete properties.landid;
					newFeature.setProperties(properties);
					newFeature.setId('undefine');
					newFeature.setProperties({'oldlandid':_landid})
					newFeature.setProperties({'applicationstatusid':1})
					newFeature.setProperties({'workflowstatusid':1})
					insertedArr.push(newFeature);
					console.log(newFeature);
				}
				
				_j=_j+1;
        	}
             _flagSplit=true;
        	map.removeInteraction(drawLine);
			
				}else{
				  map.removeInteraction(selectSingleClick);
				  insertedArr=[],
			      updateArr=[];
				  featureArr=[];
				  jAlert($.i18n("err-split-into-2"), $.i18n("err-error"));
				   setTimeout(
					  function() 
					  {
					     vectorSource.clear();
					  }, 2000);
				  _flagSplit=false;
				}

			
        	
        }

        $("#subcelladjustedit button").bind("click", function (e) {
            featureState = "";
           
		
		  var _layer = getLayerByAliesName($("#edit_layer").val());
		  
		  clearEditTool();
		  snapInteraction = new ol.interaction.Snap({
				     source: _layer.getSource(),
					 edge:true,
					 vertex:true
                   });
			map.addInteraction(snapInteraction);
			
            switch (e.currentTarget.id) {
                case 'move':
                    toggleEditControl('modify');
                    modifyMode('move');
					break;
                case 'reshape':
                   toggleEditControl('modify');
				   modifyMode('reshape');
                    break;
                case 'resize':
                    toggleEditControl('modify');
                    //modifyMode('resize');
				    break;
                case 'rotate':
                    toggleEditControl('modify');
                    // modifyMode('rotate');
                    break;
                case 'removeFeature':
                    toggleEditControl('deleteFeature')
					 modifyMode('removeFeature');;
				    break;
                case 'split':
                    //featureState = "insert";
                    toggleEditControl('split');
                    break;
                case 'merge':
                    mergeFeatures();
                    break;

            }
        });
    });
}


function populateEditableLayers() {

 $('#edit_layer').empty();
    $('#edit_layer').append($("<option></option>").attr("value", "").text($.i18n("viewer-select-layer")));
         for (var i=0;i<map.getLayers().getLength();i++) {
			 layer =map.getLayers().getArray()[i];
			  if (layer.getSource() instanceof ol.source.Vector) {
                      if(layer.values_.visible==true){
						   $('#edit_layer').append($("<option></option>").attr("value", map.getLayers().getArray()[i].values_.aname).text(map.getLayers().getArray()[i].values_.aname));
					  }
               }
			 
		 }
		 
		 $("#edit_layer").val(active_layerMap.values_.aname);
		 vectorSource= active_layerMap.getSource();
         onEditLayerChange() ;   
       


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


function getLayerType(layer, wfsurl) {

    activeLayer = layer;
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
            } else {
                layerType = 'Polygon';
            }
        }
    });
}

function onEditLayerChange() {
    hideEditForm();
    //reset tolerance
    $('#edit_tolerance_two').val(10);

    deactivateControls();

    var selected = $("#edit_layer option:selected");
    if (selected.text() == CONST_SELECT_LAYER) {
    } else {

        //Get the Layer object
        var layerName = selected.text();
		objLayer=getLayerByAliesName(layerName);
	
	 var _wfsurl=objLayer.values_.url;
        var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + objLayer.values_.name +"&maxFeatures=1&outputFormat=application/json";;

        //Get Geometry column name, featureTypes, targetNamespace for the selected layer object //
        $.ajax({
            url: PROXY_PATH + _wfsSchema,
            async: false,
            success: function (data) {
				 featureNS_=data.targetNamespace;
                 featureType_=data.featureTypes[0].typeName;
				
               // getLayerType(objLayer, _wfsurl);

                if (layerType == 'Point') {
                    $("#resize").attr("disabled", true);
                    $("#reshape").attr("disabled", true);
                    $("#rotate").attr("disabled", true);
                    $("#removeVertex").attr("disabled", true);
                } else {
                    $("#resize").removeAttr("disabled");
                    $("#reshape").removeAttr("disabled");
                    $("#rotate").removeAttr("disabled");
                    $("#removeVertex").removeAttr("disabled");
                }
            }
        });

	
    }

    $('#edit_tolerance').spinner().change(function () {
        editControls["selectionBox"].clickTolerance = $('#edit_tolerance').val();
    });

    $('#max_features').spinner().change(function () {
        editControls["selectionBox"].maxFeatures = $('#max_features').val();
    });


    $('#edit_tolerance_two').spinner().change(function () {
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

function onFeatureSelect(feature) {
    selectedFeature = feature;
    showEditForm(selectedFeature);
    $("#options2-s-d").show('fast');
    selectedFeature.state = OpenLayers.State.UPDATE;
    featureState = "modify";
}

function selectFeature(e) {
    var mode = $('#selectionMode').val();
    if (mode == 'single') {
        if (wfs != undefined) {
            wfs.removeAllFeatures();
        }
        if (wfs_del != undefined) {
            wfs_del.removeAllFeatures();
        }
    }

    if (verticesLayer !== null) {
        verticesLayer.removeAllFeatures();
    }

    var selectedFeatures = e.features;
    $.ajax({
        url: STUDIO_URL + 'layer/' + objLayer.name + "?" + token,
        success: function (data) {
            var lyrDetails = data;
            var uniqueField = lyrDetails.keyField;
            for (var i = 0; i < selectedFeatures.length; i++) {
                delete selectedFeatures[i].attributes[uniqueField];
                if (objLayer.name == 'spatial_unit') {
                    if (selectedFeatures[i].attributes.project_name === activeProject && selectedFeatures[i].attributes.active === 'true')
                        wfs.addFeatures(selectedFeatures[i]);
                } else {
                    wfs.addFeatures(selectedFeatures[i]);
                }

                var coordinates = selectedFeatures[i].geometry.getVertices();
                if (coordinates.length > 1) {
                    var points = [];
                    for (j = 0; j < coordinates.length; j++) {
                        points.push(new OpenLayers.Geometry.Point(coordinates[j].x, coordinates[j].y));
                        //verticesLayer.addFeatures([new OpenLayers.Feature.Vector(new OpenLayers.Geometry.Point(coordinates[j].x + 0.001, coordinates[j].y + 0.001), attributes)]);
                    }

                    var vertices = new OpenLayers.Geometry.MultiPoint(points);
                    var attributes = {id: selectedFeatures[i].data.usin, name: "vertices", editable: "f"};

                    //var attributes = {name: "vertices", editable: "f"};
                    verticesLayer.addFeatures([new OpenLayers.Feature.Vector(vertices, attributes)]);
                }
            }
        }
    });
}

toggleEditControl = function (element) {

    for (key in editControls) {
        var control = editControls[key];
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

function modifyMode(mode) {
    
	
        switch (mode) {
            case "move":
               map.addInteraction(dragInteraction);
                break;
            case "resize":
                break;
            case "rotate":
                break;
            case "reshape":
               map.addInteraction(modifyInteraction);
			   map.addInteraction(snapInteraction);
			    break;
			case "removeFeature":
			   map.addInteraction(deleteInteraction);
                break;	
            default:
              
                break;
        }
    //}
}

  dragInteraction.on('translateend', function(e) {

		  _flagSplit=false;
			updateArr=[];
			var features = e.features.getArray();
			for (var i=0;i<features.length;i++){
			updateArr.push(features[i]);
			}
		
			})
			
	modifyInteraction.on('modifyend', function(e) {
		 _flagSplit=false;
			updateArr=[];
			var features = e.features.getArray();
			for (var i=0;i<features.length;i++){
			updateArr.push(features[i]);
			}

			})	
			
			
	deleteInteraction.getFeatures().on('add', function (e) {
		 _flagSplit=false;
      var geometry= e.target.array_[0].getGeometry().clone();
           newFeature = new ol.Feature({
                geometry: geometry,
				
          });			
		 var properties =  e.target.array_[0].getProperties();
         newFeature.setGeometryName("geometry");
         newFeature.setProperties(properties);
         newFeature.setId(e.target.array_[0].getId());
		 newFeature.set('isactive', false);
		
		 deleteInteraction.getFeatures().clear();
		 
		 var _confirm=confirm($.i18n("viewer-confirm-delete-feature"));
if (_confirm) {
       
	  
       updateArr=[];
	   updateArr.push(newFeature);
       saveEdit();

    } else{
		map.removeInteraction(deleteInteraction);
	    deleteInteraction.getFeatures().clear();
		 updateArr=[];
	}
	
				
            });		



function saveEdit() {
   for (var i=0;i<updateArr.length;i++){
	   
	   if(updateArr[i].values_.geometrytype=="Polygon"){
		   
		   if(updateArr[i].values_.geometry.flatCoordinates[0]!= updateArr[i].values_.geometry.flatCoordinates[updateArr[i].values_.geometry.flatCoordinates.length-2] 
		     && 
			 updateArr[i].values_.geometry.flatCoordinates[1]!= updateArr[i].values_.geometry.flatCoordinates[updateArr[i].values_.geometry.flatCoordinates.length-1] 
		   
		   ){
			   alert($.i18n("err-invalid-geom"));
			    insertedArr = [];
				updateArr = [];
				deleteArr = [];
				featureArr=[];
				map.removeInteraction(selectInteraction_edit);
				map.removeInteraction(intraction_dragBox);
				map.removeInteraction(modifyInteraction);
				map.removeInteraction(deleteInteraction);
			
			if(selectedFeaturesEdit!=null)
				{
			    selectedFeaturesEdit.clear();
			     vectorSource.clear();
				}
				
				
				return false;
			   
		   }
		   
	   }
			
            

			
			}
			
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
                }).done(function(response) {
			  console.log(response);
			
			 insertedArr = [];
			updateArr = [];
			deleteArr = [];
			featureArr=[];
			map.removeInteraction(selectInteraction_edit);
            map.removeInteraction(intraction_dragBox);
			map.removeInteraction(modifyInteraction);
			map.removeInteraction(deleteInteraction);
			
			if(selectedFeaturesEdit!=null)
				{
			    selectedFeaturesEdit.clear();
			     vectorSource.clear();
				}
			
			if( _flagSplit){
			 var result = formatWFS.readTransactionResponse(response);
             console.log(result.insertIds[0]);
			 var landId=result.insertIds[0].toString().split(".")[1]

			    $.ajax({
					url: "landrecords/updateParcelNumberSplit/" + landId,
					async: false,
					success: function (data) {
					     vectorSource.clear();
						jAlert(""+data +"","info");
					}
				});
			}			
			
		
    }); 
	     
              
			  
			  
    $('#edit_content').empty();
    $("#editApply").hide();
}

function onSave(event) {
    jAlert($.i18n("gen-data-saved"));
    objLayer.redraw(true);
}

function onSaveFailed(event) {
    jAlert($.i18n("err-not-saved"));
    objLayer.redraw(true);
}

Cloudburst.Editing.prototype.Unregister = function () {
    saveStrategy.deactivate();

    if (undoredo != null)
        undoredo.resetEditIndex();

    if (wfs != undefined) {
        wfs.events.un({
            "beforefeaturemodified": showEditForm
        });
        deactivateControls();

        wfs.removeAllFeatures(true);
        wfs_del.removeAllFeatures(true);

        map.removeLayer(wfs);
        map.removeLayer(wfs_del);
        if (verticesLayer !== null) {
            map.removeLayer(verticesLayer);
        }
    }
    saveStrategy.destroy();
    saveStrategy = null;
}

function deactivateControls() {
    for (key in editControls) {
        var control = editControls[key];
        control.deactivate();
    }
}

function hideEditForm() {
    $('#edit_content').empty();
    $("#editApply").hide();
}

function showEditForm(feature) {
    currentFeature = feature;

    var keyfield;
    $.ajax({
        url: STUDIO_URL + 'layer/' + objLayer.name + "?" + token,
        success: function (data) {
            keyfield = data.keyField;
        },
        async: false
    });


    htmlContent = '';
    $('#edit_content').empty();
    var elementRule = [];
    if (featureTypesFields.length > 0) {
        $("#editApply").show('fast');
    }
    for (var i = 0; i < featureTypesFields.length; ++i) {
        //if (!featureTypesFields[i].type.indexOf('gml')>=0) {
        if (featureTypesFields[i].type.indexOf('gml') == -1) {
            if (featureTypesFields[i].name != keyfield) {
                htmlContent = htmlContent + '<div class="celladjust_one">' + featureTypesFields[i].name + '</div>';
                if (currentFeature.attributes) {
                    if (currentFeature.attributes[featureTypesFields[i].name]) {
                        htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="' + currentFeature.attributes[featureTypesFields[i].name] + '" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name + '"/></div>';
                    } else {
                        htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name + '"/></div>';
                    }
                } else if (currentFeature.feature.attributes) {
                    if (currentFeature.feature.attributes[featureTypesFields[i].name]) {
                        htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="' + currentFeature.feature.attributes[featureTypesFields[i].name] + '" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name + '"/></div>';
                    } else {
                        htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name + '"/></div>';
                    }
                }
                if (featureTypesFields[i].localType == 'int' || featureTypesFields[i].localType == 'decimal') {
                    validationRuleParams.number = true;
                    //$('#'+featureTypesFields[i].name).attr('title', 'Enter Number');

                } else {
                    validationRuleParams.number = false;
                    //$('#'+featureTypesFields[i].name).attr('title', 'Enter Text');
                }
                if (eval(featureTypesFields[i].nillable)) {
                    validationRuleParams.required = false;
                } else {
                    validationRuleParams.required = true;
                }

                var validationRuleArr = new Object();
                validationRuleArr['required'] = validationRuleParams.required;
                validationRuleArr['number'] = validationRuleParams.number;
                elementRule[featureTypesFields[i].name] = validationRuleArr;
            }
        }
    }
    $('#edit_content').append(htmlContent);
    $("#editApply").show('fast');

    $("#editor_apply").click(function () {

        $("#editor_form").validate({
            rules: elementRule
        });

        if ($("#editor_form").valid()) {
            $("#edit_content input[type=text]").each(function () {

                if (currentFeature.attributes) {
                    currentFeature.attributes[$(this)[0].id] = $(this).val();
                } else {
                    currentFeature.feature.attributes[$(this)[0].id] = $(this).val();
                }
            });

            if (currentFeature.feature == null) {
                if (featureState == "insert") {
                    currentFeature.state = OpenLayers.State.INSERT;
                    wfs.events.triggerEvent("featureadded",
                            {feature: currentFeature});
                } else {
                    currentFeature.state = OpenLayers.State.UPDATE;
                    wfs.events.triggerEvent("featuremodified",
                            {feature: currentFeature});
                }
            } else {
                if (featureState == "insert") {
                    currentFeature.feature.state = OpenLayers.State.INSERT;
                    wfs.events.triggerEvent("featureadded",
                            {feature: currentFeature.feature});
                } else {
                    currentFeature.feature.state = OpenLayers.State.UPDATE;
                    wfs.events.triggerEvent("featuremodified",
                            {feature: currentFeature.feature});
                }
            }

        }
    });
}

function updateSelectionMode() {
    var mode = $('#selectionMode').val();
    //alert(mode);
    editControls["selectionBox"].multiple = false;
    editControls["selectionBox"].single = false;

    editControls.selectionBox[mode] = true;

    if (wfs != undefined) {
        wfs.removeAllFeatures();
    }
    if (verticesLayer !== null) {
        verticesLayer.removeAllFeatures();
    }
    if (wfs_del != undefined) {
        wfs_del.removeAllFeatures();
    }
}

function toggleSnap(_this) {
    //alert(_this.checked);		
    var types = ["node", "vertex", "edge"];
    var target, type;

    $(".cls-snap").attr('checked', _this.checked);

    if (_this.checked) {
        snap.activate();
        $(".cls-snap").removeAttr("disabled");
    } else {
        snap.deactivate();
        $(".cls-snap").attr("disabled", true);
    }

    for (var i = 0; i < snap.targets.length; ++i) {
        target = snap.targets[i];
        for (var j = 0; j < types.length; ++j) {
            type = types[j];
            target[type] = _this.checked;
            target[type + "Tolerance"] = Number($('#edit_tolerance_two').val()) || 0;
        }
    }
}
function toggleSnapAttr(_this) {
    //alert(_this.value+"-"+_this.checked);		
    var type = _this.value;
    //snap.targets[0][type]=_this.checked;

    var target, type;
    for (var i = 0; i < snap.targets.length; ++i) {
        target = snap.targets[i];
        target[type] = _this.checked;
    }
}
function handleKeypress_edit(evt) {
    var code = evt.keyCode;
    if (code == OpenLayers.Event.KEY_ESC) {
        editControls['polygon'].cancel();
        editControls['line'].cancel();
    }
}
