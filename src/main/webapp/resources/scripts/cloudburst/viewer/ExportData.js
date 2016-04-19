

var map;
var formats = [];
var i = 0;
var url;
var minextents;
var maxextents;
var filter;
var exportwfsurl = null;
var addedLayers;
var layerMap = {};
var export_wmsurl = "";
var exportRegionControls = null;
var _bbox=null;
var selFeatureBbox=null;
var wfs_markup_poly=null;
Cloudburst.ExportData = function (map, _searchdiv) {
    this.map = map;
    searchdiv = _searchdiv;
    //_bbox = map.getExtent().toBBOX();

   // removeChildElement("sidebar", "layerSwitcherContent");

    //$("#layerSwitcherContent").hide();

    $("#tabs-Tool").empty();
	
    jQuery.get('resources/templates/viewer/export.html', function (template) {
        
    	//$("#" + searchdiv).append(template);
    	
    	//Add tad
		addTab('Export',template);
        
		$("#export-help").tipTip({defaultPosition:"right"});
		
        $('#exportButtons').hide();
		$('#div-wkt').hide();
		$('#txtWkt').val('');
        // Event binding for options div
        $("#options-s-d").hide();
        
		$("#options-s-t").click(function () {
            $("#options-s-d").slideToggle('fast');
        });
		
		toggleButtons();
		
		$("#exportButtons button").bind("click", function (e) {
			
			//_bbox=null;	
			clearSelection(true);
            var activeTool = e.currentTarget.id;
            
			
			if(activeTool=='texportRegion_wkt'){
				$('#div-wkt').show();
				//_bbox=null;
				toggleExportRegion(activeTool);
				
			}
			else{
				$('#div-wkt').hide();
				$('#txtWkt').val('');
				toggleExportRegion(activeTool);
				
			}
			
			
			
			});
		
		
		
		$("#options-s-d input").click(function (e) {
           
            //_bbox=null;
			wfs_markup_poly.removeAllFeatures();
			
			var exportOpt = e.currentTarget.id;
            if(exportOpt=='selectRegion'){
				clearSelection(true);
				selFeatureBbox=null;		//clear selected feature's bbox value
				$('#exportButtons').show();
				toggleExportRegion('exportRegion_polygon');
			}
			else{
				$('#exportButtons').hide();
				$('#div-wkt').hide();
				//_bbox=null;
				toggleExportRegion('');
			}
			
			
			});
		
		
				
		$('#ExportLayers').text(OpenLayers.Map.activelayer.name);
		
		
		populateLayerFormat(OpenLayers.Map.activelayer.name);
		
		if(!OpenLayers.Map.activelayer.exportable){
			$("#div-exportable").css("visibility", "visible");
			$("#btnDownload").attr('disabled','disabled');

			
		}
		
		//////Add controls
	    // poly
    wfs_markup_poly = new OpenLayers.Layer.Vector("exportRegion_Poly", {
        reportError: true,
        projection: "EPSG:27700",
        isBaseLayer: false,
        visibility: true,        
        displayInLayerSwitcher: false
    });
    map.addLayers([wfs_markup_poly]);
	
	exportRegionControls = {
        
        exportRegion_polygon: new OpenLayers.Control.DrawFeature(
        wfs_markup_poly, OpenLayers.Handler.Polygon, {
			
            callbacks: {
                done: function (p) {
					wfs_markup_poly.removeAllFeatures();
                    var polyFeature = new OpenLayers.Feature.Vector(p);                   
                    
					wfs_markup_poly.addFeatures([polyFeature]);
					selFeatureBbox=polyFeature.geometry.getBounds().toBBOX();


                }
            }
        }),
        
        exportRegion_rectangle: new OpenLayers.Control.DrawFeature(
        wfs_markup_poly, OpenLayers.Handler.RegularPolygon, {
            handlerOptions: {
                sides: 4,
				irregular:true
            },
            callbacks: {
                done: function (p) {
                    
					wfs_markup_poly.removeAllFeatures();
					
					var rectangleFeature = new OpenLayers.Feature.Vector(p);                    
                    
					wfs_markup_poly.addFeatures([rectangleFeature]);
					selFeatureBbox=rectangleFeature.geometry.getBounds().toBBOX();
						
                }
            }
        })
    };

    for (var key in exportRegionControls) {
        map.addControl(exportRegionControls[key]);

    }
	
	
	////////
		
      

    });
};



var populateLayerFormat = function (_activeLayer) {

        export_wmsurl = (map.getLayersByName(_activeLayer)[0]).url;
        export_wmsurl=replaceString(export_wmsurl, /wms/gi, 'wfs');

        var _url = PROXY_PATH + export_wmsurl + "&request=GetCapabilities&service=WFS&version=1.0.0";

        $.ajax({
            url: _url,
            dataType: "text",
            success: function (data) {
                var parser = new OpenLayers.Format.WFSCapabilities();
                var res = parser.read(data);
                var formats = res.capability.request.getfeature.formats;
                $('#formats').empty();
                $('#formats').append($("<option></option>").attr("value", "").text("Please Select"));
                
                for (x = 0; x < formats.length; x++) {
					if(formats[x]!="GML2" && formats[x]!="GML3" && formats[x]!="JSON")
					$('#formats').append($("<option></option>").attr("value", formats[x]).text(formats[x]));
                }

            }
        });

    };



var downloadFeature = function () {		
        var exporturl = "";
		var bbox=null;
		var outputformat = $("#formats").val();
        var selectedViewType = $("input[@name='exportView']:checked", "#exportdiv").attr('id')
        var selectedLayer = $('#ExportLayers').html();
		
		//var bbox = map.getExtent().toBBOX();		
		
		if (selectedLayer != "" && outputformat != "") {
            if (selectedViewType == 'all') {
			
				bbox = map.getExtent().toBBOX();
				exporturl = export_wmsurl + "&request=GetFeature&version=1.0.0&service=WFS&typename=" + layerMap[selectedLayer] + "&bbox=" + bbox + "&outputformat=" + outputformat;	
			}
			else if(selectedViewType == 'selectRegion'){
			
				if($('#div-wkt').is(":visible")){
					if(!$('#txtWkt').val()){	
						
						jAlert('Enter Coorinates', 'Export');
						return;
					}
					bbox=$('#txtWkt').val();
				}
				else{
						if(selFeatureBbox){
							bbox = selFeatureBbox;
						}
						else{
							
							jAlert('Please Select Region', 'Export');
							return;
						}
				}
				
				
				exporturl = export_wmsurl + "&request=GetFeature&version=1.0.0&service=WFS&typename=" + layerMap[selectedLayer] + "&bbox=" + bbox + "&outputformat=" + outputformat;
				
			}
			else if(selectedViewType == 'selected'){
					
					bbox = selFeatureBbox;
					
					if(OpenLayers.Map.activelayer.selectFilter){
						var xml = new OpenLayers.Format.XML();
						var filter_1_0 = new OpenLayers.Format.Filter({
							version: "1.0.0"
						});
						var xmlFilter = xml.write(filter_1_0.write(OpenLayers.Map.activelayer.selectFilter));
						exporturl = export_wmsurl + "&request=GetFeature&version=1.0.0&service=WFS&typename=" + layerMap[OpenLayers.Map.activelayer.name] + "&outputformat=" + outputformat + "&filter=" + xmlFilter;
					}
					else{
						
						jAlert('Please Select Feature', 'Export');
						
						return;
					}
			}
			
			window.open(exporturl, "Export");
		}
		else {

            
			jAlert('Please select Format', 'Export');

        }
		
		/*
		if(selFeatureBbox){
			bbox = selFeatureBbox;
		}
		else if(!selFeatureBbox && $('#txtWkt').val()){
		
		bbox=$('#txtWkt').val();
		
		}
		else if(!selFeatureBbox && selectedViewType != 'selected') {
			
			alert("Please Select Region");
			return;
			//bbox = map.getExtent().toBBOX();
		}
		
		alert("BBOX: "+ bbox);
		
        

        if (selectedLayer != "" && outputformat != "") {
            if (selectedViewType == 'all' || selectedViewType == 'selectRegion') {
				
                exporturl = export_wmsurl + "&request=GetFeature&version=1.0.0&service=WFS&typename=" + layerMap[selectedLayer] + "&bbox=" + bbox + "&outputformat=" + outputformat;
            } else {
                
				

				
				var xml = new OpenLayers.Format.XML();
                var filter_1_0 = new OpenLayers.Format.Filter({
                    version: "1.0.0"
                });
                var xmlFilter = xml.write(filter_1_0.write(OpenLayers.Map.activelayer.selectFilter));

					

                exporturl = export_wmsurl + "&request=GetFeature&version=1.0.0&service=WFS&typename=" + layerMap[OpenLayers.Map.activelayer.name] + "&outputformat=" + outputformat + "&filter=" + xmlFilter;
            }
            window.open(exporturl, "Export");

        } 
		
		else {

            alert("Please select Format");

        }
		*/
		
    };

var visible = function (status) {
        $("#" + searchdiv).empty();
    };
	
	
function toggleExportRegion(element) {
    
	for (key1 in mapControls) {
	var control = mapControls[key1];
	control.deactivate();
	}
	
	for (key2 in markupControls) {
	var control = markupControls[key2];
	control.deactivate();
	}
	
	for (key3 in exportRegionControls) {
	var control = exportRegionControls[key3];
	control.deactivate();
	}
	
	
	for (key in exportRegionControls) {
        var control = exportRegionControls[key];
        if (element == key) {
            control.activate();

            var draw = exportRegionControls[key];
            OpenLayers.Event.observe(document, "keydown", function (evt) {
                var handled = false;
                switch (evt.keyCode) {
                case 90:
                    // z
                    if (evt.metaKey || evt.ctrlKey) {
                        draw.undo();
                        handled = true;
                    }
                    break;
                case 89:
                    // y
                    if (evt.metaKey || evt.ctrlKey) {
                        draw.redo();
                        handled = true;
                    }
                    break;
                case 27:
                    // esc
                    draw.cancel();
                    handled = true;
                    break;
                }
                if (handled) {
                    OpenLayers.Event.stop(evt);
                }
            });

        } else {
            control.deactivate();

        }
    }
}	
	