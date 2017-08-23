

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
var printChoice = function(){

	Dialog = $( "#print-dialog-form" ).dialog({
		autoOpen: false,
		height: 200,
		width: 232,
		resizable: false,
		modal: true,
		buttons: {
			"Ok": function() 
			{


				var option = "";
				var selected = $("#radioPrint input[type='radio']:checked");
				if (selected.length > 0) {
					option = selected.val();

				}

				if(option!='0')
				{
					if(option=="1")
					{
						printMap();
						Dialog.dialog( "destroy" );
					}
					else if(option=="2")
					{
						jAlert("Under Construction");
						Dialog.dialog( "destroy" );
					}
				}else
				{
					jAlert("Please select an option","Alert");
				}


			},
			"Cancel": function() 
			{

				Dialog.dialog( "destroy" );

			}
		},

		close: function() {

			Dialog.dialog( "destroy" );

		}
	});
	Dialog.dialog("open");
}

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
	var width_url=null;
	var height_url=null;
	var bbox_temp=null;

	for (l = 0; l < map.layers.length; l++) {
		layer = map.layers[l];
		if (map.layers[l] instanceof OpenLayers.Layer.WMS
				&& map.layers[l].getVisibility() && layer.name != 'Dummy'
					&& layer.name != 'Markers' && layer.name != 'markup'
						&& layer.name.indexOf("Google_") == -1) {

			var boundsNew=createSquareBounds(layer.getExtent());
			bbox_temp = boundsNew.left + ',' + boundsNew.bottom
			+ ',' + boundsNew.right + ','
			+ boundsNew.top;
			bbox = layer.getExtent().left + ',' + layer.getExtent().bottom
			+ ',' + layer.getExtent().right + ','
			+ layer.getExtent().top;

			if(layer.getExtent().right>layer.getExtent().left){
				width_url= layer.getExtent().right-layer.getExtent().left;
			}
			else{
				width_url= layer.getExtent().right-layer.getExtent().left;
			}
			if(layer.getExtent().top>layer.getExtent().bottom){
				height_url=layer.getExtent().top-layer.getExtent().bottom;
			}
			else{
				height_url=layer.getExtent().bottom-layer.getExtent().top;
			}
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
	jQuery("#project_name").val(projectName);
	jQuery("#cosmetic_sld_file").val(cosmetic_sld_file);

	jQuery.ajax({
		type:"GET",        
		url: "printlayer/" +activeProject,
		async:false,
		success: function (result) 
		{  
			printlayer=result;
		}
	});



	jQuery.ajax({
		type:"POST",        
		url: "createreport/" ,
		async:false,
		data: jQuery("#printForm").serialize(),
		success: function (result) 
		{  
			spatialList=result
		}
	});

	var arr=[];
	for (var int = 0; int < spatialList.length; int++) {
		if($.inArray(spatialList[int].usin, arr) == -1)
			arr.push(spatialList[int].usin);	
	}

	//$("#printForm").attr("action", "print/?" + token);

	/*var format = new OpenLayers.Format.WMC({
		'layerOptions' : {
			buffer : 0
		}
	});
	var wmc_text = format.write(map);
	document.getElementById('wmc').value = wmc_text;
	document.forms["printForm"].submit();*/

	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/viewer/printLabel.html',
				dataType: 'html',
				success: function (data1) 
				{
					jQuery("#printDiv").empty();
					jQuery("#printDiv").append(data1);
					jQuery("#title1").text(title);

					var wmsurl="http://"+location.host+"/geoserver/wms?";

					//var layerurl_ccro = wmsurl +"bbox="+bbox_temp+"&FORMAT=image/png&REQUEST=GetMap&layers=mast:itagutwa_images,mast:spatial_unit&width=700&height=700&srs=EPSG:4326&cql_Filter=INCLUDE;project_name='"+projectName+"';active=true";
					var layerurl_ccro = wmsurl +"bbox="+bbox_temp+"&FORMAT=image/png&REQUEST=GetMap&layers="+printlayer+"&width=600&height=600&srs=EPSG:4326&cql_Filter=INCLUDE;project_name='"+projectName+"';usin%20in("+arr.toString()+")";



					$('#print_map').append('<img id="theImg" src='+layerurl_ccro+'>');

					jQuery("#printDetailsTemplate").tmpl(spatialList).appendTo("#printDetailsBodyData");

					var popUpwindow=1;

					var printWindow=window.open('',popUpwindow,'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no,location=no');
					printWindow.document.close();
					var html = $("#printDiv").html();

					printWindow.document.write ('<html><head><title>MAST</title>'
							+' <link rel="stylesheet" href="../resources/styles/viewer/styleccro.css" type="text/css" />'
							+'</head><body> '+html+' </body></html>');

					printWindow.focus();
				}
			});

}

var printMapDetails=function(){

	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/viewer/ccronew.html',
				dataType: 'html',
				success: function (data1) 
				{
					jQuery("#printTemplateDiv").empty();
					jQuery("#printTemplateDiv").append(data1);

					var printWindow=window.open('','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
					printWindow.document.close();
					var html = $("#printTemplateDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/styleccro.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/CCRO.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'"<input type="hidden" id="usin_primerykey" value='+id+'>"</body></html>');

					printWindow.focus();


				}
			});



}

/*function createSquareBounds1(inBounds) {

	//get centroid of bounds
	var newLeft;
	var newRight;
	var newBottom;
	var newTop;
	var outBounds;
	var centroidLatLong = inBounds.getCenterLonLat();

	//get the larger side of the bound(Rectangle)
	if (inBounds.getWidth() > inBounds.getHeight()){
		//calculate square bounds
		newLeft = inBounds.toArray()[0];
		newBottom = centroidLatLong.lat - inBounds.getWidth()/2;
		newRight = inBounds.toArray()[2];
		newTop = centroidLatLong.lat + inBounds.getWidth()/2;
		outBounds = new OpenLayers.Bounds(String(newLeft), String(newBottom), String(newRight), String(newTop));
		return outBounds;

	}else if (inBounds.getWidth() < inBounds.getHeight()){
		//calculate square bounds
		newLeft =  centroidLatLong.lon - inBounds.getHeight()/2;
		newBottom = inBounds.toArray()[1];
		newRight = centroidLatLong.lon + inBounds.getHeight()/2;
		newTop = inBounds.toArray()[3];
		outBounds =new OpenLayers.Bounds(String(newLeft), String(newBottom), String(newRight), String(newTop));
		return outBounds;

	} else{
		outBounds = inBounds;
		return outBounds;
	}


}*/
