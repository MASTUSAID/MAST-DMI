var map;
Cloudburst.ExportMap = function(_map, _searchdiv) {
var userList=null;
	
	map = _map
	searchdiv = _searchdiv;
	showResultsinDialog = true;
	
	$("#tabs-Tool").empty();
	
	jQuery.get('resources/templates/viewer/exportmap.html', function(template) {

		// Add tad
		addTab('Export Map', template);		
		
		$("#exportmap-help").tipTip({defaultPosition:"right"});
		
		$("#imgdiv").hide();
        $("#export_img").click(function () {
            //$("#imgdiv").slideToggle('fast');
			$("#imgdiv").show();
        });
		
        $("#export_wmc").click(function () {
            
			$("#imgdiv").hide();
        });
		
		
 		$("#btnExportMap").click(function() {
 			exportMap(_map);
		});

	});

}

function exportMap(_map){
	
	var exportType=$("input[@name='exportType']:checked", "#exportmapdiv").val();
	//var name=$('#exportName').val();
	//var email=$('#exportEmail').val();
	var imgHeight=$('#img-height').val();
	var imgWidth=$('#img-width').val();
	
	var format = new OpenLayers.Format.WMC({'layerOptions': {buffer: 0}});
	var text = format.write(_map);	
	/*
	if(!name){
	
	jAlert("Please Enter Name","Export Map");
	return;
	}
	
	if(email){
		alert('SEND EMAIL');
		return;
	}
	*/
	
	if(exportType=='wmc'){
		exportmapWmc(text);			
	}
	
	else if(exportType=='img'&& imgHeight && imgWidth){				
		
			exportmapImg(imgHeight,imgWidth);		
	}
	
	else if((exportType=='img') && (!imgHeight || !imgWidth)){
			alert("Enter values");
			return false;
	}
	
	
	
}

function exportmapWmc(_text){
	
	jQuery("#xmlText").val(_text);
	
	$("#exportmapForm").attr("action", "exportmap/wmc");
	
	document.forms["exportmapForm"].submit();
	
}




function exportmapImg(_imgHeight,_imgWidth)
{
	var layerurl='';
	var bbox='';
	for(l=0;l<map.layers.length;l++){		
			var layer=map.layers[l];
			if(map.layers[l] instanceof OpenLayers.Layer.WMS && map.layers[l].getVisibility() 
				&& layer.name != 'Dummy' && layer.name != 'Markers' 
				&& layer.name != 'markup' && layer.name.indexOf("Google_") == -1){
		
					bbox=layer.getExtent().left+','+layer.getExtent().bottom+','+layer.getExtent().right+','+layer.getExtent().top;
					
					//lyrs=lyrs+layerMap[layer.name]+',';		
					
					//layerurl=layerurl+layer.getFullRequestString({})+"&BBOX="+layer.getExtent().left+','+layer.getExtent().bottom+','+layer.getExtent().right+','+layer.getExtent().top+"|";
					layerurl=layerurl+layer.getFullRequestString({})+"|";
					
					
					
					//legendurl = legendurl+layer.name+"~"+layer.getFullRequestString({}).replace("GetMap", "GetLegendGraphic") + "&Layer=" + layerMap[layer.name]+"|";
						
			}

	}
	/*
	$.ajax({
        	type: "POST",			
            url: "exportmap/image",
            data: {lyrurl:layerurl},
            async:false,
			success: function () {           
                
            }

        });
	*/
	
	jQuery("#lyrurl").val(layerurl);
	jQuery("#bbox").val(bbox);
	
	$("#exportmapForm").attr("action", "exportmap/image");
	
	document.forms["exportmapForm"].submit();
	
}