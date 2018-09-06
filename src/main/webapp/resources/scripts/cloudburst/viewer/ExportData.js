

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
var extent="";
Cloudburst.ExportData = function (map, _searchdiv) {
    this.map = map;
    searchdiv = _searchdiv;
    //_bbox = map.getExtent().toBBOX();

   // removeChildElement("sidebar", "layerSwitcherContent");

    //$("#layerSwitcherContent").hide();

    $("#tabs-Tool").empty();
	if(active_layerMap == null){
					jAlert('Please Select A layer First', 'Selection');
			return false;
	
		}
	
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
			//wfs_markup_poly.removeAllFeatures();
			
			var exportOpt = e.currentTarget.id;
            if(exportOpt=='selectRegion'){
				//clearSelection(true);
				selFeatureBbox=null;		//clear selected feature's bbox value
				$('#exportButtons').show();
				toggleExportRegion('exportRegion_polygon');
				
					map.addInteraction(selectInteraction_export);
                    map.addInteraction(intraction_dragBox_export);
                    intraction_dragBox_export.on('boxend', function(event) {
					selectedFeaturesExport = selectInteraction_export.getFeatures();
					selectedFeaturesExport.clear();
					 extent = intraction_dragBox_export.getGeometry().getExtent();
					map.getLayers().forEach(function(layer) {
						 if (layer instanceof ol.layer.Vector) {
						 layer.getSource().forEachFeatureIntersectingExtent(extent, function(feature) {

						 if(layer.values_.name ==$("#ExportLayers").text()){
						    	selectedFeaturesExport.push(feature);
							}

							
						 });
						 
						 }
						 
					});

			 });
           
		 // clear selection when drawing a new box and when clicking on the map
		    intraction_dragBox.on('boxstart', function() {
			     if(selectedFeaturesExport!=null){
					selectedFeaturesExport.clear();
					}				 
				  });
				
			}
			else{
				$('#exportButtons').hide();
				$('#div-wkt').hide();
				//_bbox=null;
				toggleExportRegion('');
			}
			
			
			});
		
		
				
	
		
		if (active_layerMap != null){
			$('#ExportLayers').text(active_layerMap.get("name"));		
		     layerMap=[];
			 layerMap.push(active_layerMap.get("name"));
			 populateLayerFormat()

		}else{
			
			jAlert('Please Select A layer First', 'Selection');	
		}
		
		
	   $("#clearselection_export").button().click(function(){
		   
	  		toggleButtons();
       	if (selectInteraction_export !== null) {
		     map.removeInteraction(selectInteraction_export);
			
	}	
	
	
	 if (intraction_dragBox_export !== null) {
		     map.removeInteraction(intraction_dragBox_export);
			
	}	
	
		 
	if(selectedFeaturesExport!=null){
		selectedFeaturesExport.clear()
	}	
    });   

    });
};



var populateLayerFormat = function () {
	    export_wmsurl = active_layerMap.get("url");
        var _url = PROXY_PATH + export_wmsurl + "&request=GetCapabilities&service=WFS&version=1.0.0";

        $.ajax({
            url: _url,
            dataType: "xml",
            success: function (data) {
                $('#formats').empty();
				var _length=data.getElementsByTagName("Capability")[0].getElementsByTagName("ResultFormat")[0].childNodes.length
                $('#formats').append($("<option></option>").attr("value", "").text("Please Select"));
                for (x = 0; x < _length; x++) {
					if(data.getElementsByTagName("Capability")[0].getElementsByTagName("ResultFormat")[0].childNodes[x].tagName!="GML2" && 
					data.getElementsByTagName("Capability")[0].getElementsByTagName("ResultFormat")[0].childNodes[x].tagName!="GML3" &&
					data.getElementsByTagName("Capability")[0].getElementsByTagName("ResultFormat")[0].childNodes[x].tagName!="JSON")
		             $('#formats').append($("<option></option>").attr("value", data.getElementsByTagName("Capability")[0].getElementsByTagName("ResultFormat")[0].childNodes[x].tagName).text(data.getElementsByTagName("Capability")[0].getElementsByTagName("ResultFormat")[0].childNodes[x].tagName));
                }

            }
        });

    };



var downloadFeature = function () {		
        var exporturl = "";
		var bbox=null;
		var outputformat = $("#formats").val();
        var selectedLayer = $('#ExportLayers').html();

		if (selectedLayer != "" && outputformat != "") {
            if ($("#all").is(":checked")) {
				bbox=map.getView().calculateExtent(map.getSize());
			   exporturl = export_wmsurl + "&request=GetFeature&version=1.0.0&service=WFS&typename=" + layerMap[0] + "&bbox=" + bbox + "&outputformat=" + outputformat;
			    window.open(exporturl, "Export");
				return
			} if($("#selectRegion").is(":checked")){
			  if(extent!=""){
			   exporturl = export_wmsurl + "&request=GetFeature&version=1.0.0&service=WFS&typename=" + layerMap[0] + "&bbox=" + extent + "&outputformat=" + outputformat;
			    window.open(exporturl, "Export");
				return
				}else{
					jAlert('Please Select Region', 'Export');
							return;
				}
			}
				
		}
		
		

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
	