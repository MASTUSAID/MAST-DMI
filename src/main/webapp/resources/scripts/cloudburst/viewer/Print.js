

var map;
Cloudburst.Print = function(_map, _searchdiv) {
	map = _map;
	searchdiv = _searchdiv;
	
	$("#tabs-Tool").empty();

	//$.ajax({

		
	//	url : STUDIO_URL + "printtmpl/" + "?" + token,
	//	success : function(printtemplate) {

			jQuery.get('resources/templates/viewer/print.html', function(
					template) {
				
				addTab($._("print"), template);

				$("#print-help").tipTip({
					defaultPosition : "right"
				});
				$('#map_title').html($._('print_title'));
				$('#btnPrint').attr("value", $._('print'))
				/*$.each(printtemplate, function(val, tmpl) {
					
					$("#templateType").append(
							$("<option></option>").attr("value",
									tmpl.templatefile).text(tmpl.name));
				});
				
				var resolutions = map.baseLayer.resolutions;
				var units = map.baseLayer.units;
				for (var i=resolutions.length-1; i >= 0; i--) {
				var res = resolutions[i];
				var ddval=parseInt(Math.round(OpenLayers.Util.getScaleFromResolution(res, units)));
				$('#mapScale').append($("<option></option>").attr("value",ddval ).text(ddval));
				}
				
				$('#lblexportformat').html($._('Hello'));
				*/	
			});
			
			 


		
	//	 	}
	//	});
	
	
	

};

var printMap = function() {
	var templateType = jQuery("#templateType").val();
	var exportFormat = jQuery("#exportFormat").val();
	// var mapScale = jQuery("#mapScale").val();
	// var resolution = jQuery("#resolution").val();
	var title = jQuery("#title").val();
	var description = jQuery("#description").val();
	var lyrs = "";
	// map.layers[0].getVisibility()
	var layerurl = "";
	var legendurl = "";
	var layer = null;
	var bbox = null;

	for (l = 0; l < map.layers.length; l++) {
		layer = map.layers[l];
		if (map.layers[l] instanceof OpenLayers.Layer.WMS
				&& map.layers[l].getVisibility() && layer.name != 'Dummy'
				&& layer.name != 'Markers' && layer.name != 'markup'
				&& layer.name.indexOf("Google_") == -1) {

			bbox = layer.getExtent().left + ',' + layer.getExtent().bottom
					+ ',' + layer.getExtent().right + ','
					+ layer.getExtent().top;

			lyrs = lyrs + layerMap[layer.name] + ',';

			// layerurl=layerurl+layer.getFullRequestString({})+"&BBOX="+layer.getExtent().left+','+layer.getExtent().bottom+','+layer.getExtent().right+','+layer.getExtent().top+"|";
			layerurl = layerurl + layer.getFullRequestString({}) + "|";

			legendurl = legendurl
					+ layer.name
					+ "~"
					+ layer.getFullRequestString({}).replace("GetMap",
							"GetLegendGraphic") + "&Layer="
					+ layerMap[layer.name] + "|";

		}

	}

	layerurl = layerurl.substring(0, layerurl.length - 1);
	legendurl = legendurl.substring(0, legendurl.length - 1);

	jQuery("#hid-layerUrl").val(layerurl);
	jQuery("#hid-legendUrl").val(legendurl);
	jQuery("#hid-bbox").val(bbox);
	jQuery("#hid-compiledBy").val(loggedUser.name);
	jQuery("#mapScale").val($("#scale-interval option:selected").text());
	jQuery("#access_land_sld_file").val(access_land_sld);
	jQuery("#cosmetic_sld_file").val(cosmetic_sld_file);
	/*
	 * $.ajax({ type: "POST", url: "/spatialvue-report/createreport", data:
	 * $("#printForm").serialize(), success: function () { alert('here'); },
	 * error: function (XMLHttpRequest, textStatus, errorThrown) {
	 * 
	 * alert("ERROR") } });
	 */
	// set Action
	$("#printForm").attr("action", "print/?" + token);

	var format = new OpenLayers.Format.WMC({
		'layerOptions' : {
			buffer : 0
		}
	});
	var wmc_text = format.write(map);
	document.getElementById('wmc').value = wmc_text;
	document.forms["printForm"].submit();

}



