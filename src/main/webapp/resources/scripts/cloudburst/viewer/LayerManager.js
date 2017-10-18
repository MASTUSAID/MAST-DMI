
var lyrmgroptions = {
    "editable": {
        yes: "resources/images/viewer/editable-yes.png",
        no: "resources/images/viewer/editable-no.png"
    },
    "selectable": {
        yes: "resources/images/viewer/selectable-yes.png",
        no: "resources/images/viewer/selectable-no.png"
    },
	"queryable": {
        yes: "resources/images/viewer/queryable-yes.png",
        no: "resources/images/viewer/queryable-no.png"
    },
    "exportable": {
        yes: "resources/images/viewer/exportable-yes.png",
        no: "resources/images/viewer/exportable-no.png"
    }
	
};
  		
var map;
var maptipField = null;
Cloudburst.LayerManager = function (_map, _searchdiv) {
    map = _map;

    searchdiv = _searchdiv;
    $('#pan').click(); //To make pan as active tool on load

    //removeChildElement("sidebar","layerSwitcherContent");		
    $("#sidebar-Layers").empty();
    //$("#layerSwitcherContent").hide();
    $.ajax({

        url: STUDIO_URL + "project/" + project + "?" + token,
        success: function (projects) {

            jQuery.get('resources/templates/viewer/layermanager.html', function (template) {
            	 $("#" + searchdiv).empty();
                $("#" + searchdiv).append(template);
				for (m in projects.projectLayergroups) {
                	
                	
                    for (n in projects.projectLayergroups[m].layergroups.layers) {									
					projects.projectLayergroups[m].layergroups.layers[n].displayInLyrMgr=displayInLayerMgr[projects.projectLayergroups[m].layergroups.layers[n].layer]					
					}	
				}
                
	               /* Layer grp heading traslation for en and cy
	                for(lyrGrp in projects.projectLayergroups){
	                	if($._(projects.projectLayergroups[lyrGrp].layergroups.name) != ""){
	                		projects.projectLayergroups[lyrGrp].layergroups.alias = 
	                			$._(projects.projectLayergroups[lyrGrp].layergroups.name);
	                	}
	                }
	                */

                jQuery("#layermanagerBody").empty();


                var $tt = jQuery("#layermanagerTemplate").tmpl(null,

                {
                    projectLayergroup: projects.projectLayergroups,
                    layerDispNameList:layerDispName
                });

				

                $("#layermanagerBody").html($tt);
				
				
				if(!cosmeticStatus){
					$("#tr-cosmetic").remove();				
				}
				else if(cosmeticStatus && sldExists){
					$('#grpVisibility__overlays').attr('checked', true);	
					$('#Visibility__Cosmetic').attr('checked', true);					
				}
				else{
					$('#grpVisibility__overlays').attr('checked', false);
					$('#Visibility__Cosmetic').attr('checked', false);
					
				}
				//check overlay checkbox if layer exists
				//if($('#overlaysBody').children().length>1){
				//	$('#grpVisibility__overlays').attr('checked', true);
				//}
				
                for (x in projects.projectLayergroups) {
                	
                	var bGrpVisibilityState = false;
                    for (y in projects.projectLayergroups[x].layergroups.layers) {
						
						//Aparesh, Implement layer visibility checkbox
						
						$('#Visibility__'+projects.projectLayergroups[x].layergroups.layers[y].layer).attr('checked', projects.projectLayergroups[x].layergroups.layers[y].layervisibility);					
						if(projects.projectLayergroups[x].layergroups.layers[y].layervisibility){
							//$('#grpVisibility__'+projects.projectLayergroups[x].layergroups.name).attr('checked', false);
							bGrpVisibilityState = true;
						}
					
                        $("#SliderSingle__" + projects.projectLayergroups[x].layergroups.layers[y].layer).slider({

                            min: 1,
                            max: 9,
                            value: 9,
                            step: 1,
                            slide: function (event, ui) {
								
                                //OpenLayers.Map.activelayer.setOpacity(ui.value / 10);								
								map.getLayersByName(event.target.title)[0].setOpacity(ui.value / 10);
								
                            }
                        });
						
                      //checkbox enabled/disabled accoding to scale
						if(!map.getLayersByName(projects.projectLayergroups[x].layergroups.layers[y].layer)[0].visibility){
							//$('#Visibility__'+projects.projectLayergroups[x].layergroups.layers[y].layer).attr("disabled", "disabled");;
						}
                        
						//set image   
						if(map.getLayersByName(projects.projectLayergroups[x].layergroups.layers[y].layer)[0].editable){
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_editable").attr('src', lyrmgroptions.editable.yes);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_editable").attr('title', $._('layermanager_editable_layer'));
						}
						else{
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_editable").attr('src', lyrmgroptions.editable.no);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_editable").attr('title', $._('layermanager_non-editable_layer'));
						}
						if(map.getLayersByName(projects.projectLayergroups[x].layergroups.layers[y].layer)[0].selectable){
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_selectable").attr('src', lyrmgroptions.selectable.yes);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_selectable").attr('title', $._('layermanager_selectable_layer'));
						}
						else{
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_selectable").attr('src', lyrmgroptions.selectable.no);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_selectable").attr('title', $._('layermanager_non-selectable_layer'));
						}
						if(map.getLayersByName(projects.projectLayergroups[x].layergroups.layers[y].layer)[0].queryable){
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_queryable").attr('src', lyrmgroptions.queryable.yes);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_queryable").attr('title', $._('layermanager_queryable_layer'));
						}
						else{
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_queryable").attr('src', lyrmgroptions.queryable.no);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_queryable").attr('title', $._('layermanager_non-queryable_layer'));
						}
						if(map.getLayersByName(projects.projectLayergroups[x].layergroups.layers[y].layer)[0].exportable){
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_exportable").attr('src', lyrmgroptions.exportable.yes);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_exportable").attr('title', $._('layermanager_exportable_layer'));
						}
						else{
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_exportable").attr('src', lyrmgroptions.exportable.no);
							$("#" + projects.projectLayergroups[x].layergroups.layers[y].layer + "_exportable").attr('title', $._('layermanager_non-exportable_layer'));
						}
						
						
					
                    };
                    if(!bGrpVisibilityState){
                        $('#grpVisibility__' + projects.projectLayergroups[x].layergroups.name).attr('checked', false);
                    }
                };

			$("#SliderSingle__" + "Cosmetic").slider({

				min: 1,
				max: 9,
				value: 9,
				step: 1,
				slide: function (event, ui) {

					OpenLayers.Map.activelayer.setOpacity(ui.value / 10);
				}
			});	

                var toggleMinus = 'resources/images/viewer/fade-in-arrow.png';
                var togglePlus = 'resources/images/viewer/fade-out-arrow.png';
                var $subHead = $('#layermanagerBody tbody th:first-child');

                $subHead.prepend('<img src="' + toggleMinus + '"alt="" />');
                $('img', $subHead).addClass('clickable').click(function () {

                    var toggleSrc = $(this).attr('src')
                    if (toggleSrc == toggleMinus) {
                        $(this).attr('src', togglePlus).parents('tr').siblings().fadeOut('fast');
                    } else {
                        $(this).attr('src', toggleMinus).parents('tr').siblings().fadeIn('fast');
                    }
                });

                activateLayerClick();

                refreshLegends();

                //set project's Active layer


                var activelyr = map.getLayersByName(projects.activelayer)[0];
                OpenLayers.Map.activelayer = activelyr;
                activeLayerURL = activelyr.url;
                $('.layerTR').removeClass("rowclick");
                $("#" + projects.activelayer).addClass("rowclick");
				$("#chk-" + projects.activelayer).addClass("rowclick");
				

                //Get layer's maptip
                getMaptipField(project, OpenLayers.Map.activelayer.name);


                //mapTipOptions.featureType="lpis_output";
                //mapTipOptions.featureType=layerMap[OpenLayers.Map.activelayer.name];
                mapControls["maptip"].protocol = OpenLayers.Protocol.WFS.fromWMSLayer(OpenLayers.Map.activelayer, mapTipOptions);


                mapControls["selectbox"].layers = [OpenLayers.Map.activelayer];
                mapControls["selectpolygon"].layers = [OpenLayers.Map.activelayer];
                
                
                $(".layer_info").tipTip({
		        	fadeIn:0,
		        	fadeOut:0			        	
		        });

                $('#overlays').html($._('Overlays'));
            	$('#cosmetic').html($._('Cosmetic'));
            	
            	//Explicitly collapse the reference and raster groups
            	$('#Reference_Layers').find('img').click();
            	$('#Os_Raster_Group').find('img').click();
            	
            });
        }
    });



};

function refreshLegends() {

    for (i = 0; i < map.layers.length; i++) {

        var layer = map.layers[i];
        if (layer instanceof OpenLayers.Layer.WMS) {
            var legendurl;
            var wmsurl = layer.getFullRequestString({});
            legendurl = wmsurl.replace("GetMap", "GetLegendGraphic") + "&Layer=" + layerMap[layer.name];

            if(lang == "cy" && layer.name == "Furniture"){
            	$("#" + layer.name + "_legend").attr('src', "resources/images/viewer/furniture_legend_cy.png");
            }else{
            	$("#" + layer.name + "_legend").attr('src', legendurl);
            }

        }
    }
}

function manageLayer(_layer) {
    
	var clsname=_layer.className;
	//Commented by Alok
	//var tolLen=$('.'+clsname).length;
	//if($('.'+clsname+':checked').length==tolLen){
	
	//$('#grpVisibility__'+clsname).attr('checked', true);
	//}
	//else{
	//$('#grpVisibility__'+clsname).attr('checked', false);
	//}
	//enabling disabling group layer check box based on whether all the layers are off or not.
	var layername = _layer.id.split("__")[1];
	var tolLen=$('.'+clsname).length;
	var anyLayerOn = false;
	for(var i=0;i<tolLen;i++){
		if($('.'+clsname)[i].checked)
			{
			anyLayerOn = true;
			}
		}
	if(anyLayerOn){
		
		$('#grpVisibility__'+clsname).attr('checked', true);
	}
	else {
	    map.getLayersByName(layername)[0].setVisibility(_layer.checked);
		$('#grpVisibility__'+clsname).attr('checked', false);
		}
		
	if($('#grpVisibility__'+clsname)[0].checked==true){
	//if($("input[id='grpVisibility__"+clsname+"']")[0].checked==true){   
	
		//var layername = _layer.id.split("__")[1];
	    //map.getLayersByName(layername)[0].setVisibility(_layer.checked);
		if(clsname.toUpperCase() == "OVERLAYS"){
			/*
			 $.ajax({
				   url: "theme/checkSldExists/",
				   async: false,
				   success: function (flag) {
        	
					   map.getLayersByName(layername)[0].setVisibility(flag);
        	
				   }});*/
			 map.getLayersByName(layername)[0].setVisibility(_layer.checked);
        }
		ScaleRangeView();
	}

}



function activateLayerClick() {
    $('.layerTR').click(function (e) {
        //alert(e.currentTarget.id);
        var layer_id = e.currentTarget.id;
        var layer = map.getLayersByName(layer_id)[0];
        if (layer instanceof OpenLayers.Layer.WMS) {
            OpenLayers.Map.activelayer = layer;
            activeLayerURL = layer.url;
            $('.layerTR').removeClass("rowclick");
            $("#" + layer_id).addClass("rowclick");
			$("#chk-" + layer_id).addClass("rowclick");
			//reflect layer name in export data when layer changed
			if($('#ExportLayers').length>0){			
				$('#ExportLayers').text(OpenLayers.Map.activelayer.name);
			}
			
            //Get layer's maptip
            getMaptipField(project, OpenLayers.Map.activelayer.name);

            var actualLayerName = layerMap[OpenLayers.Map.activelayer.name];

            if (actualLayerName.indexOf("rega:") > -1) {
                var a1 = [];
                a1 = actualLayerName.split(":");
                actualLayerName = a1[1];
            }

            mapTipOptions.featureType = actualLayerName;

            mapControls["maptip"].protocol = new OpenLayers.Protocol.WFS.fromWMSLayer(OpenLayers.Map.activelayer, mapTipOptions);
            mapControls["selectbox"].layers = [OpenLayers.Map.activelayer];
            mapControls["selectpolygon"].layers = [OpenLayers.Map.activelayer];
        }
    });

}

function manageLyrGrp(_this){
	
	var chkbox=$('.'+_this.value);

	if(_this.checked){
	    for (var i = 0; i < chkbox.length; i++) {
	        var layername = chkbox[i].value;
	        $('#Visibility__' + layername).attr("checked", true);
	        if(_this.value.toUpperCase() == "OVERLAYS"){
	        	map.getLayersByName(layername)[0].setVisibility(true);
	        }
	    }
		ScaleRangeView();
	}
	else{
		for(var i=0;i<chkbox.length;i++){
			var layername = chkbox[i].value;
			$('#Visibility__' + layername).attr("checked", false);
			map.getLayersByName(layername)[0].setVisibility(false);
		}
	}
}