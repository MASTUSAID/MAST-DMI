
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
				// for (m in projects.projectLayergroups) {
                	
                	
                    // for (n in projects.projectLayergroups[m].layergroups.layers) {									
					// projects.projectLayergroups[m].layergroups.layers[n].displayInLyrMgr=displayInLayerMgr[projects.projectLayergroups[m].layergroups.layers[n].layer]					
					// }	
				// }
                
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
                    for (y in projects.projectLayergroups[x].layergroupBean.layerLayergroups) {
						
						//Aparesh, Implement layer visibility checkbox
						
						$('#Visibility__'+projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias).attr('checked', projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.visibility);					
						if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.visibility){
							//$('#grpVisibility__'+projects.projectLayergroups[x].layergroups.name).attr('checked', false);
							bGrpVisibilityState = true;
						}
					
                        $("#SliderSingle__" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias).slider({

                            min: 1,
                            max: 9,
                            value: 9,
                            step: 1,
                            slide: function (event, ui) {
							var _layer=getLayerByAliesName(event.target.title);
								_layer.setOpacity(ui.value / 10);
                            }
                        });
						
                      //checkbox enabled/disabled accoding to scale
				       //		if(!map.getLayersByName(projects.projectLayergroups[x].layergroups.layers[y].layer)[0].visibility){
							//$('#Visibility__'+projects.projectLayergroups[x].layergroups.layers[y].layer).attr("disabled", "disabled");;
					  //	}
                        
						//set image   
						
						if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.editable){
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editable").attr('src', lyrmgroptions.editable.yes);
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editable").attr('title', $._('layermanager_editable_layer'));
						}else{
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editable").attr('src', lyrmgroptions.editable.no);
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editable").attr('title', $._('layermanager_editable_layer'));
						 }
						
						if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.selectable){
						    $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectable").attr('src', lyrmgroptions.selectable.yes);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectable").attr('title', $._('layermanager_selectable_layer'));
						 }
						else{
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectable").attr('src', lyrmgroptions.selectable.no);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectable").attr('title', $._('layermanager_non-selectable_layer'));
						}
						
					  if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.queryable){
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryable").attr('src', lyrmgroptions.queryable.yes);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryable").attr('title', $._('layermanager_queryable_layer'));
						 }
						else{
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryable").attr('src', lyrmgroptions.queryable.no);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryable").attr('title', $._('layermanager_non-queryable_layer'));
						 }
						
						if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.exportable){
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportable").attr('src', lyrmgroptions.exportable.yes);
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportable").attr('title', $._('layermanager_exportable_layer'));
						 }
						else{
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportable").attr('src', lyrmgroptions.exportable.no);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportable").attr('title', $._('layermanager_non-exportable_layer'));
						 }
						
						
					
                    };
                    if(!bGrpVisibilityState){
                        $('#grpVisibility__' + projects.projectLayergroups[x].layergroupBean.name).attr('checked', false);
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

                //refreshLegends();

                
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

    for (i = 0; i < map.getLayers().getLength(); i++) {

        var layer = map.getLayers().array_[i];
        if (layer instanceof ol.layer.Vector) {
            var legendurl;
           var _url=replaceString(layer.values_.url, /wfs/, 'wms')
			legendurl =_url+ "&request=GetLegendGraphic&service=WMS&version=1.0.0&Layer=" + layer.values_.name + "&Format=image/png&WIDTH=20&HEIGHT=20";
			//$("#" + layer.values_.aname + "_legend").attr('src', legendurl);
			
           
        }
    }
}

function manageLayer(_layer) {
    
	var _id=_layer.id;
	var layername = _layer.id.split("__")[1];
    var _layer=getLayerByAliesName(layername);
  
    if($('#'+_id).prop('checked'))
		{
		_layer.setVisible(true);
		}else{
		_layer.setVisible(false);
		}
		
		

	/*
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
			if(clsname.toUpperCase() == "OVERLAYS"){
				 map.getLayersByName(layername)[0].setVisibility(_layer.checked);
			}
			ScaleRangeView();
	}
	
	*/

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


function LayerExist(layer) {
    var res = false;
    for (var i=0;i<map.getLayers().getLength();i++) {
        if (map.getLayers().getArray()[i] === layer) { //check if layer exists
            res = true; //if exists, return true
        }
    }
    return res;
}


function activateLayerClick() {
    $('.layerTR').click(function (e) {
        var layer_id = e.currentTarget.id;
		if (layer_id.indexOf('-') > -1)
		{
			layer_id=layer_id.substring(layer_id.indexOf("-") + 1); 
		}
       var layer = getLayerByAliesName(layer_id);
        if (layer.getSource() instanceof ol.source.Vector) {
            $('.layerTR').removeClass("rowclick");
            $("#" + layer_id).addClass("rowclick");
			$("#chk-" + layer_id).addClass("rowclick");
			if($('#ExportLayers').length>0){			
				$('#ExportLayers').text(layer_id);
			}
			active_layerMap=layer;
        }  else if (layer.getSource() instanceof ol.source.Tile) {
            $('.layerTR').removeClass("rowclick");
            $("#" + layer_id).addClass("rowclick");
			$("#chk-" + layer_id).addClass("rowclick");
			if($('#ExportLayers').length>0){			
				$('#ExportLayers').text(layer_id);
			}
			active_layerMap=layer;
        }
    });

	
}

function manageLyrGrp(_this){
	
	var chkbox=$('.'+'grpVisibility__'+_this.value);

	if(_this.checked){
	    for (var i = 0; i < chkbox.length; i++) {
	        var layername = chkbox[i].value;
	        $('#Visibility__' + layername).attr("checked", true);
			var _layer=getLayerByAliesName(layername);
	        _layer.setVisible(true);
	    }
	}
	else{
		for(var i=0;i<chkbox.length;i++){
			var layername = chkbox[i].value;
			$('#Visibility__' + layername).attr("checked", false);
			var _layer=getLayerByAliesName(layername);
	        _layer.setVisible(false);
		}
	}
}