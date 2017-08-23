

saveStrategy = null;
var selectedFeature = null;
var splitvector=null;
var wfs = null;
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
var snap=null;
var activeLayer=null;
var validationRuleParams = { required: false, number: false };
var editSelectionSymbolizer = {
		 "Point": {pointRadius: 4, graphicName: "square", fillColor: "#CC9900", fillOpacity: 1, strokeWidth: 1, strokeOpacity: 1, strokeColor: "#333333"},
		 "Line": {strokeWidth: 3, strokeOpacity: 1, strokeColor: "#666666", strokeLinecap: "square", strokeDashstyle: "dash"},
		 "Polygon": {strokeWidth: 2, strokeOpacity: 1, strokeColor: "#CC9900", fillColor: "#CC9900", fillOpacity: 0.3, strokeLinecap: "square", strokeDashstyle: "solid"},
		 "RegularPolygon": {strokeWidth: 2, strokeOpacity: 1, strokeColor: "#CC9900", fillColor: "#CC9900", fillOpacity: 0.3}
		};

var style = new OpenLayers.Style();
style.addRules([new OpenLayers.Rule({ symbolizer: editSelectionSymbolizer })]);
var styleMap = new OpenLayers.StyleMap({ "default": style });

var deleteSelectionSymbolizers = {
	    "Point": {pointRadius: 4, graphicName: "square", fillColor: "#ff0000", fillOpacity: 1, strokeWidth: 1, strokeOpacity: 1, strokeColor: "#ff0000"},
	    "Line": {strokeWidth: 3, strokeOpacity: 1, strokeColor: "#ff0000", strokeLinecap: "square", strokeDashstyle: "dash"},
	    "Polygon": {strokeWidth: 2, strokeOpacity: 1, strokeColor: "#ff0000", fillColor: "#ff0000", fillOpacity: 0.3, strokeLinecap: "square", strokeDashstyle: "solid"},
	    "RegularPolygon": {strokeWidth: 2, strokeOpacity: 1, strokeColor: "#ff0000", fillColor: "#ff0000", fillOpacity: 0.3}
	};
var delStyle = new OpenLayers.Style();
delStyle.addRules([new OpenLayers.Rule({ symbolizer: deleteSelectionSymbolizers })]);
var delStyleMap = new OpenLayers.StyleMap({ "default": delStyle });

Cloudburst.Editing = function (_map, _searchdiv) {    
    map = _map;
    searchdiv = _searchdiv;
    showResultsinDialog = true;
    
    if (saveStrategy != null){
        this.Unregister();
    }
    saveStrategy = new OpenLayers.Strategy.Save();
        
    $("#tabs-Tool").empty();
    
    jQuery.get('resources/templates/viewer/editing.html', function (template) {

      //Add tad
		addTab('Editing',template);
		$("#editing-help").tipTip({defaultPosition:"right"});
		
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
		$('#options3-s-t').click(function() {
  			$('#options3-s-d').slideToggle('fast', function() {
    		// Animation complete.
  			});
		});
		
		$("#options4-s-d").hide();
		$('#options4-s-t').click(function() {
  			$('#options4-s-d').slideToggle('fast', function() {
    		// Animation complete.
  			});
		});
		
		toggleButtons();
		
		$("#subcelladjust button").bind("click", function(e) {
			featureState = "";
			for ( var key in editControls) {
				var control = editControls[key];
				control.deactivate();

			}
			switch (e.currentTarget.id) {
			case 'selectionBox':
				toggleEditControl('selectionBox');
				break;
			case 'clearselection':
				/*if(wfs != undefined){
					wfs.removeAllFeatures();
				}
				if(wfs_del != undefined){
					wfs_del.removeAllFeatures();
				}*/
				onEditLayerChange();
				break;
			case 'selectUndo':
				if(undoredo != undefined){
					undoredo.undo();
				}
				break;
			case 'selectRedo':
				if(undoredo != undefined){
					undoredo.redo();
				}
				break;
			case 'selectSave':
				saveEdit();
				break;
			}
		});
		$("#subcelladjustcreate button").bind("click", function(e) {
			
			if($('#edit_layer').val()!=""){
				featureState = "";
				for ( var key in editControls) {
					var control = editControls[key];
					control.deactivate();
				}
				switch (e.currentTarget.id) {
				case 'point':
					if(layerType == 'Point'){
						featureState = "insert"
						toggleEditControl('point');
					}else{
						jAlert("Layer selected is not point type", "Error");
					}
					break;
				case 'line':
					if(layerType == 'LineString'){
						featureState = "insert";
						toggleEditControl('line');
					}else{
						jAlert("Layer selected is not line type", "Error");
					}
					break;
				case 'polygon':
					if(layerType == 'Polygon'){
						featureState = "insert";
						toggleEditControl('polygon');
					}else{
						jAlert("Layer selected is not polygon type", "Error");
					}
					break;
				}
			}
			else{
				jAlert("Please Selected Layer", "Error");
			}
			
		});
		
		$("#subcelladjustedit button").bind("click", function(e) {
			featureState = "";
			for ( var key in editControls) {
				var control = editControls[key];
				control.deactivate();

			}
			switch (e.currentTarget.id) {
			case 'importFeature':
				toggleEditControl('importFeature');
				break;
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
				modifyMode('resize');
				break;
			case 'rotate':
				toggleEditControl('modify');
				modifyMode('rotate');
				break;
			//case 'removeVertex':
				//toggleEditControl('modify');
				//break;
			case 'removeFeature':
				toggleEditControl('deleteFeature');
				break;
			case 'editAttribute':
				toggleEditControl('editAttribute');
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


function mergeFeatures(){
	
	
	
	var mergegml = new OpenLayers.Format.WKT().write(wfs.features);
	$.ajax({
		type: 'POST',
		url: "http://localhost:8080/spatialvue-editingservices/editingservice",
		dataType: "text",
		data: { process:'merge', mergegml:mergegml},
		success: function (result) {
			alert(result);			
			var mergefeature = new OpenLayers.Format.WKT().read(result);
			//splitfeatures.state=OpenLayers.State.INSERT;
			
			
				var multipolygon = new OpenLayers.Geometry.MultiPolygon([mergefeature.geometry]);
				var polyFeature = new OpenLayers.Feature.Vector(multipolygon);
				polyFeature.state = OpenLayers.State.INSERT;
				wfs.addFeatures([polyFeature]);
				
			
			
		},
		error: function (xhr, status) {
			jAlert('Sorry, there is a problem!');
		}
	});
}


function populateEditableLayers() {
    var lyrCount = map.getNumLayers();
    $('#edit_layer').empty();
    //$('#edit_layer').append($("<option></option>").attr("value", "Select Layer").text("Select Layer"));
	$('#edit_layer').append($("<option></option>").attr("value", "").text("Select Layer"));
    for (var i = 0; i < lyrCount; i++) {
        if (map.layers[i] instanceof OpenLayers.Layer.WMS && map.layers[i].name.indexOf("Cosmetic") == -1 && map.layers[i].name != 'clone') {
            if (map.layers[i].visibility == true && map.layers[i].editable == true) {
                if (doesLayerExists(map.layers[i].name)) {
                    //if (map.layers[i].queryable) {
                        $('#edit_layer').append($("<option></option>").attr("value", map.layers[i].name).text(map.layers[i].name));
                   // }else{
                    	if(map.layers[i].name == 'Cosmetic'){
                    		 $('#edit_layer').append($("<option></option>").attr("value", map.layers[i].name).text(map.layers[i].name));
                    	}
                    //}
                }
            }
        }
    }

    if (lyrCount > 0) {
       // $('#edit_layer').get(0).selectedIndex = 0;
    	//$("#edit_layer").val($("#edit_layer option:first").val());
    }
}

function doesLayerExists(lyrName) {
    var bFlag = true;

    var count = $('#edit_layer option').size();
    for (var i = 0; i < count; i++) {
        var value = $("#layers option[value=" + lyrName + "]").text();
        if (value == lyrName) {
            bFlag = false;
            break;
        }
    }
    return bFlag;
}

function getLayerType(layer, wfsurl) {
	
	activeLayer=layer;
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
            	layerType = 'Polygon';
            }
        }
    });
}

function onEditLayerChange(){
	hideEditForm();
	//reset tolerance
	$('#edit_tolerance_two').val(10);
	
	//Remove the previously add WFS and WFS_DEL layers
	wfs = map.getLayersByName("WFS")[0];
	if(wfs != undefined){
		map.removeLayer(wfs);
	}
	wfs_del = map.getLayersByName("WFS_DEL")[0];
	if(wfs_del != undefined){
		map.removeLayer(wfs_del);
	}
	deactivateControls();
	saveStrategy = new OpenLayers.Strategy.Save();
	
	var selected = $("#edit_layer option:selected");
    if (selected.text() == CONST_SELECT_LAYER) {
    }else{
    	
    	//Get the Layer object
    	var layerName = selected.text();
    	objLayer = map.getLayersByName(layerName)[0];
        var _wfsurl = objLayer.url.replace(new RegExp( "wms", "i" ), "wfs");
        var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + layerMap[objLayer.name];
        
        /*Get Geometry column name, featureTypes, targetNamespace for the selected layer object*/
        $.ajax({
            url: PROXY_PATH + _wfsSchema,
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
                
                if(layerType == 'Point'){
                	$("#resize").attr("disabled", true);
                	$("#reshape").attr("disabled", true);
                	$("#rotate").attr("disabled", true);
                	$("#removeVertex").attr("disabled", true);
                }else{
                	$("#resize").removeAttr("disabled");
                	$("#reshape").removeAttr("disabled");
                	$("#rotate").removeAttr("disabled");
                	$("#removeVertex").removeAttr("disabled");
                }
            }
        });
        
        var actualLayerName = layerMap[objLayer.name];
        var pos = actualLayerName.indexOf(":");
        var featType = null;
        if (pos > 1)
            featType = actualLayerName.substring(pos + 1);
        else
            featType = actualLayerName;
        
        //Create Vector object for WFS(Selected feature) and WFS_DEL(Selected for delete feature)
        var _projection = new OpenLayers.Projection(objLayer.projection.projCode);
        var _protocol = new OpenLayers.Protocol.WFS({
            version: "1.1.0",
            srsName: objLayer.projection.projCode,
            url: _wfsurl,
            featureType: featType,
            geometryName: geometryColName,
            featurePrefix: featPrefix,
            featureNS: targetNamespace,
            schema: _wfsSchema
        });
        
        wfs = new OpenLayers.Layer.Vector(
                "WFS", {
                    reportError: true,
                    strategies: [saveStrategy],
                    projection: _projection,
                    protocol: _protocol,
                    isBaseLayer: false,
                    visibility: true,
                    styleMap: styleMap,
                    displayInLayerSwitcher: false
                }
            );
            map.addLayers([wfs]);
			
			splitvector = new OpenLayers.Layer.Vector(
                "SplitVector", {
                    reportError: true,

                    projection: _projection,

                    isBaseLayer: false,
                    visibility: true,
                    styleMap: styleMap,
                    displayInLayerSwitcher: false
                }
            );
            map.addLayers([splitvector]);
            
			snap = new OpenLayers.Control.Snapping({
                layer: wfs,
                targets: [wfs],
                greedy: false
            });
            snap.activate();
			
			map.addControl(snap);
			
            wfs_del = new OpenLayers.Layer.Vector(
            "WFS_DEL", {
                reportError: true,
                projection: _projection,
                protocol: _protocol,
                isBaseLayer: false,
                visibility: true,
                styleMap: delStyleMap,
                displayInLayerSwitcher: false
            }
           );
            map.addLayers([wfs_del]);
            undoredo = new UndoRedo([wfs, wfs_del]);
            
            /*For Markup*/
            var lyrCount = map.getNumLayers();
            var cosmetic_protocol;
            for (var i = 0; i < lyrCount; i++) {
                if (map.layers[i].name.indexOf("Cosmetic") != -1) {
                	var typeName;
                	if(layerType == 'Point'){
                		typeName = workspace + ":" +  "Cosmetic_Point";
                	}else if(layerType == 'LineString'){
                		typeName = workspace + ":" + 'Cosmetic_Line';
                	}else if(layerType == 'Polygon'){
                		typeName = workspace + ":" + 'Cosmetic_Poly';
                	}
                	
                	var cosmeticLayer = map.getLayersByName("Cosmetic")[0];
                    //cosmeticlayers = cosmeticLayer.params.LAYERS.split(",");
                    var url = cosmeticLayer.url;
                    var cosmeticProjection = cosmeticLayer.projection;
                    var url = replaceString(url, /wms/gi, 'wfs');
                    var cosmetic_schema = url + "&request=DescribeFeatureType&version=1.0.0&service=WFS&typename=" + typeName;
                    var cosmetic_featureNS;
                    $.ajax({
                        url: PROXY_PATH + cosmetic_schema,
                        async: false,
                        success: function (data) {
                        	var cosmetic_featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
                            var cosmetic_responseText = cosmetic_featureTypesParser.read(data);
                            var cosmetic_featureTypes = cosmetic_responseText.featureTypes;
                             cosmetic_featureNS = cosmetic_responseText.targetNamespace;
                            var cosmetic_featPrefix = cosmetic_responseText.targetPrefix;
                            
                            for (var i = 0; i < cosmetic_featureTypes[0].properties.length; ++i) {
                                if (cosmetic_featureTypes[0].properties[i].type.indexOf("gml")>=0) {
                                	cosmetic_geometryColName = cosmetic_featureTypes[0].properties[i].name;
                                	break;
                				}
                			}
                            
                            var _pos = typeName.indexOf(":");
                            var _featType = null;
                            if (pos > 1)
                                _featType = typeName.substring(pos + 1);
                            else
                                _featType = typeName;
                            
                            //Create Vector object for WFS(Selected feature) and WFS_DEL(Selected for delete feature)
                            cosmetic_protocol = new OpenLayers.Protocol.WFS({
                                version: "1.1.0",
                                srsName: cosmeticProjection.projCode,
                                url: url,
                                featureType: _featType,
                                geometryName: cosmetic_geometryColName,
                                featurePrefix: cosmetic_featPrefix,
                                featureNS: cosmetic_featureNS,
                                schema: cosmetic_schema
                            });
                        }
                    });
                	break;
                }
            }
            
            
            
            wfs.events.on({
                "beforefeaturemodified": showEditForm
            });
            
            editControls = {        
                    modify: new OpenLayers.Control.ModifyFeature(wfs, {
                    	displayClass:"olControlDefault",
                        clickout: false,
                        toggle: false,
                        //enforceTopology: true,
                        deleteCodes: [46, 68, 27]
                    }),
                    deleteFeature: new OpenLayers.Control.SelectFeature([wfs], {
                    	displayClass:"olControlDefault",
                    	renderIntent: "temporary",
                        //selectStyle:deleteSelectionSymbolizers,
                        onSelect: onFeatureSelection
                    }),
                    importFeature: new OpenLayers.Control.GetFeature({
                    	displayClass:"olControlDefault",
                        protocol: cosmetic_protocol
                    }),
                    editAttributes: new OpenLayers.Control.SelectFeature([wfs], {
                       // onSelect: onFeatureAttributeSelection
                    }),
                    selectionBox: new OpenLayers.Control.GetFeature({
                    	displayClass:"olControlDefault",
                        protocol: _protocol,
                        click:true,
                        single:false,
						filterType:'INTERSECTS',
                        box:true,
                        multiple:false,
                        clickout:true,
                        //maxFeatures: $('#max_features').val(),
						toggle:false,
						hover:false
                    }),
                    editAttribute:new OpenLayers.Control.SelectFeature([wfs],{
                    	displayClass:"olControlDefault",
                        protocol: _protocol,
                        click:true,
                        single:false,
                        box:false,
                        multiple:false,
                        clickout:true,
						toggle:false,
						onSelect: onFeatureSelect,
						hover:false
                    }),
    		        point: new OpenLayers.Control.DrawFeature(
    		                wfs, OpenLayers.Handler.Point, {
    		                	displayClass:"olControlDefault",
    		                    callbacks: {
    		                        done: function (p) {
    		                            var pointFeature = new OpenLayers.Feature.Vector(p);
    		                            pointFeature.state = OpenLayers.State.INSERT;
    		                            wfs.addFeatures([pointFeature]);
										showEditForm(pointFeature);
    		                        }
    		                    }
    		           }),
		           line: new OpenLayers.Control.DrawFeature(
		        	        wfs, OpenLayers.Handler.Path, {
		        	        	displayClass:"olControlDefault",
		        	            callbacks: {
		        	                done: function (p) {
		        	                	var multiLine = new OpenLayers.Geometry.MultiLineString([p]);
		        	                    var lineFeature = new OpenLayers.Feature.Vector(multiLine);
		        	                    lineFeature.state = OpenLayers.State.INSERT;
		        	                    wfs.addFeatures([lineFeature]);
										showEditForm(lineFeature);
		        	                }
		        	            }
		        	        }),
		        	        split: new OpenLayers.Control.DrawFeature(
				        	        splitvector, OpenLayers.Handler.Path, {
				        	        	displayClass:"olControlDefault",
				        	            callbacks: {
				        	                done: function (p) {
				        	                	var multiLine = new OpenLayers.Geometry.MultiLineString([p]);
				        	                    var lineFeature = new OpenLayers.Feature.Vector(multiLine);
				        	                    //lineFeature.state = OpenLayers.State.INSERT;
												splitvector.destroyFeatures();
				        	                    splitvector.addFeatures([lineFeature]);
												var gmlOptions = {featureType: "feature",featureNS: "http://www.spatialvue.com/feature"};
											
												var linegml = new OpenLayers.Format.WKT().write([lineFeature]);
												var forsplitgml = new OpenLayers.Format.WKT().write(wfs.features);
												$.ajax({
													type: 'POST',
													url: "http://localhost:8080/spatialvue-editingservices/editingservice",
													dataType: "text",
													data: { process:'split', forsplitgml:forsplitgml, linegml: linegml,  forupdategml:forsplitgml  },
													success: function (result) {
														alert(result);
														splitvector.destroyFeatures();
														var splitfeatures = new OpenLayers.Format.WKT().read(result);
														//splitfeatures.state=OpenLayers.State.INSERT;
														
														for(x=0 ; x< splitfeatures.length ; x++){
															var multipolygon = new OpenLayers.Geometry.MultiPolygon([splitfeatures[x].geometry]);
															var polyFeature = new OpenLayers.Feature.Vector(multipolygon);
															polyFeature.state = OpenLayers.State.INSERT;
															wfs.addFeatures([polyFeature]);
															
														}														
														
													},
													error: function (xhr, status) {
														jAlert('Sorry, there is a problem!');
													}
												});									
												
				        	                }
				        	            }
				        	        }),
									
									
        	        polygon: new OpenLayers.Control.DrawFeature(
        	                wfs, OpenLayers.Handler.Polygon, {
        	                	displayClass:"olControlDefault",
        	                    callbacks: {
        	                        done: function (p) {
        	                        	var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
        	                            var polyFeature = new OpenLayers.Feature.Vector(multipolygon);
        	                            polyFeature.state = OpenLayers.State.INSERT;
        	                            wfs.addFeatures([polyFeature]);
										showEditForm(polyFeature);
        	                        }
        	                    }
        	                })
                };
            
            for (var key in editControls) {
                map.addControl(editControls[key]);
            }
            
            if(layerType == 'Polygon'){
            	editControls["deleteFeature"].selectStyle = deleteSelectionSymbolizers.Polygon;
            }else if(layerType == 'Point'){
            	editControls["deleteFeature"].selectStyle = deleteSelectionSymbolizers.Point;
            }else if(layerType == 'LineString'){
            	editControls["deleteFeature"].selectStyle = deleteSelectionSymbolizers.Line;
            }

            saveStrategy.events.register('success', null, onSave);
            editControls["selectionBox"].events.register("featuresselected", this, selectFeature);
            editControls["importFeature"].events.register("featureselected", this, onFeatureImport);
            //editControls["editAttribute"].events.register("featureselected", this, onFeatureSelect)
           /* drawControls["select"].events.register("clickout", this, function (e) {
                wfs.removeAllFeatures(true);
            });*/
    }
	
	$('#edit_tolerance').spinner().change(function () {
		editControls["selectionBox"].clickTolerance= $('#edit_tolerance').val();
	});
	
	$('#max_features').spinner().change(function () {		
		editControls["selectionBox"].maxFeatures= $('#max_features').val();
	});
	
	
	$('#edit_tolerance_two').spinner().change(function () {
		//snap.targets[0].tolerance= $('#edit_tolerance_two').val();
		//target[type + "Tolerance"] = Number($('#edit_tolerance_two').val()) || 0; 
		var types = ["node", "vertex", "edge"];
		var target,type;	
		for(var i=0; i<snap.targets.length; ++i) {
			target = snap.targets[i];
			for(var j=0; j<types.length; ++j) {
				type = types[j];			
				target[type + "Tolerance"] = Number($('#edit_tolerance_two').val()) || 0; 
			}
		}
	});
	
	var esc_hndl_edit_poly = new OpenLayers.Handler.Keyboard(editControls['polygon'], {
        keydown: handleKeypress_edit
    });
	esc_hndl_edit_poly.activate();
    
    var esc_hndl_edit_line = new OpenLayers.Handler.Keyboard(editControls['line'], {
    	keydown: handleKeypress_edit
    });
    esc_hndl_edit_line.activate();
}

function onFeatureImport(e) {
    var feats = [];
    var bFeatureExists = false;
    
    hideEditForm();
    selectedFeature = e.feature;
    selectedFeature.state = "_blank";
    wfs.addFeatures([selectedFeature]);
    selectedFeature.state = OpenLayers.State.INSERT;
}

//On selecting feature to display attribute
function onFeatureSelect(feature){
	selectedFeature = feature;
	showEditForm(selectedFeature);
	 $("#options2-s-d").show('fast');
	 selectedFeature.state = OpenLayers.State.UPDATE;
	 featureState = "modify";
}

function selectFeature(e){
	var mode=$('#selectionMode').val();
	if(mode=='single'){
		if(wfs != undefined){
			wfs.removeAllFeatures();
		}
		if(wfs_del != undefined){
			wfs_del.removeAllFeatures();
		}
	
	}
	
	var selectedFeatures = e.features;
	 $.ajax({
         url: STUDIO_URL + 'layer/' + objLayer.name + "?" + token,
         success: function (data) {
             var lyrDetails = data;
             var uniqueField = lyrDetails.keyField;
			 for(var i=0; i<selectedFeatures.length; i++){
				 delete selectedFeatures[i].attributes[uniqueField];
				 if(objLayer.name=='spatial_unit'){
					 if(selectedFeatures[i].attributes.project_name==activeProject && selectedFeatures[i].attributes.active=='true')
						 wfs.addFeatures(selectedFeatures[i]);	 
				 }
				 else{
					 wfs.addFeatures(selectedFeatures[i]);
				 }
				 
			 }
		 }
	 });
}

toggleEditControl = function(element) {
	
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
    if (editControls != null) {
    	featureState = "modify";
        switch (mode) {
            case "move":
            	editControls.modify.mode = OpenLayers.Control.ModifyFeature.DRAG;
                break;
            case "resize":
            	editControls.modify.mode = OpenLayers.Control.ModifyFeature.RESIZE;
                break;
            case "rotate":
            	editControls.modify.mode = OpenLayers.Control.ModifyFeature.ROTATE;
                break;
            case "reshape":
            	editControls.modify.mode = OpenLayers.Control.ModifyFeature.RESHAPE;
                break;
            default:
            	editControls.modify.mode = OpenLayers.Control.ModifyFeature.RESHAPE;
                break;
        }
    }
}

function onFeatureSelection(feature){
    selectedFeature = feature;

    if (confirm('Are you sure you want to delete this feature?')) {
    /*    selectedFeature.state = "Insert";
        //wfs_del.addFeatures([selectedFeature]);
        feature.state = OpenLayers.State.DELETE;
        
    */
    	var pos = feature.fid.indexOf(".");
    	if(pos >= -1){
    		var _gid = parseInt(feature.fid.substring(++pos));
    	}
    	
    	jQuery.ajax({ 
    		url: "landrecords/spatialfalse/"+_gid,
    		async:false,							
    		success: function (data) {	

    			if(data==true)
    				{
					
    				saveEdit();
    				alert("Delete Successful");
    				
    				}
    			
    			else{
    				alert("Delete UnSuccessful");
    				
    			}
    		}


    	});
    	
    	
    	
    } else {
    	editControls["deleteFeature"].unselect(feature);
    }
}

function saveEdit() {
    saveStrategy.save();
    wfs_del.removeAllFeatures(true);
	wfs.removeAllFeatures(true); 		
	undoredo.resetEditIndex();
	$('#edit_content').empty();
	$("#editApply").hide();
	
	
	
	
}

function onSave(event){
	jAlert("Data Successfully Saved");
	objLayer.redraw(true);
}

Cloudburst.Editing.prototype.Unregister = function () {
    saveStrategy.deactivate();
    
    if(undoredo != null)
        undoredo.resetEditIndex();

    if(wfs != undefined){
	    wfs.events.un({
	        "beforefeaturemodified": showEditForm
	    });
	    deactivateControls();

	    wfs.removeAllFeatures(true);
	    wfs_del.removeAllFeatures(true);

	    map.removeLayer(wfs);
	    map.removeLayer(wfs_del);

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

function hideEditForm(){
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
     if(featureTypesFields.length > 0){
 		$("#editApply").show('fast');
 	 }
     for (var i = 0; i < featureTypesFields.length; ++i) {
         //if (!featureTypesFields[i].type.indexOf('gml')>=0) {
    	 if (featureTypesFields[i].type.indexOf('gml')== -1) {
             if (featureTypesFields[i].name != keyfield) {
            	 htmlContent = htmlContent + '<div class="celladjust_one">' + featureTypesFields[i].name + '</div>';
            	 if (currentFeature.attributes) {
                     if (currentFeature.attributes[featureTypesFields[i].name]) {
                    	 htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="' + currentFeature.attributes[featureTypesFields[i].name] + '" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name +'"/></div>';
                     }else{
                    	 htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name +'"/></div>';
                     }
                 }else if (currentFeature.feature.attributes) {
                	 if (currentFeature.feature.attributes[featureTypesFields[i].name]) {
                		 htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="' + currentFeature.feature.attributes[featureTypesFields[i].name] + '" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name +'"/></div>';
                     }else{
                    	 htmlContent = htmlContent + '<div class="subcelladjust"><input type="text"  class="value_one" value="" name="' + featureTypesFields[i].name + '" id="' + featureTypesFields[i].name +'"/></div>';
                     }
                 }
            	 if (featureTypesFields[i].localType == 'int' || featureTypesFields[i].localType == 'decimal') {
                     validationRuleParams.number = true;
					  //$('#'+featureTypesFields[i].name).attr('title', 'Enter Number');
					  
                 }else{
                     validationRuleParams.number = false;
					 //$('#'+featureTypesFields[i].name).attr('title', 'Enter Text');
                 }
                 if (eval(featureTypesFields[i].nillable)) {
                     validationRuleParams.required = false;
                 }else{
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
            	 if(featureState == "insert"){
            		 currentFeature.state = OpenLayers.State.INSERT;
                     wfs.events.triggerEvent("featureadded",
                             { feature: currentFeature });
            	 }else{
            		 currentFeature.state = OpenLayers.State.UPDATE;
            		 wfs.events.triggerEvent("featuremodified",
                         { feature: currentFeature });
            	 }
             } else {
            	 if(featureState == "insert"){
            		 currentFeature.feature.state = OpenLayers.State.INSERT;
            		 wfs.events.triggerEvent("featureadded",
            				 { feature: currentFeature.feature });
            	 }else{
            		 currentFeature.feature.state = OpenLayers.State.UPDATE;
            		 wfs.events.triggerEvent("featuremodified",
            				 { feature: currentFeature.feature });
            	 }
             }
            
        }
     });
}


function updateSelectionMode(){	
	var mode=$('#selectionMode').val();
	//alert(mode);
	editControls["selectionBox"].multiple =false;
	editControls["selectionBox"].single =false;
	
	editControls.selectionBox[mode]=true;	
	
	if(wfs != undefined){
		wfs.removeAllFeatures();
	}
	if(wfs_del != undefined){
		wfs_del.removeAllFeatures();
	}
	

	
}

function toggleSnap(_this){
	//alert(_this.checked);		
	var types = ["node", "vertex", "edge"];
    var target,type;
	
	$(".cls-snap").attr('checked', _this.checked);
	
	if(_this.checked){
		snap.activate();
		$(".cls-snap").removeAttr("disabled");
	}
	else{
		snap.deactivate();
		$(".cls-snap").attr("disabled", true);
	}
		
	for(var i=0; i<snap.targets.length; ++i) {
		target = snap.targets[i];
		for(var j=0; j<types.length; ++j) {
			type = types[j];
			target[type] = _this.checked;
			target[type + "Tolerance"] = Number($('#edit_tolerance_two').val()) || 0; 
		}
	}
	
	

}
function toggleSnapAttr(_this){
	//alert(_this.value+"-"+_this.checked);		
	var type=_this.value;
	//snap.targets[0][type]=_this.checked;
	
	var target,type;
	for(var i=0; i<snap.targets.length; ++i) {
		target = snap.targets[i];
		target[type] = _this.checked;	
	}
}
function handleKeypress_edit(evt){
    var code = evt.keyCode;
    if(code == OpenLayers.Event.KEY_ESC){    	
    	editControls['polygon'].cancel();       
    	editControls['line'].cancel();
    }
}
