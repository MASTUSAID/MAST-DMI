

var map;
Cloudburst.DynaLayer = function (_map, _searchdiv) {
    map = _map;
    searchdiv = _searchdiv;

   // removeChildElement("sidebar", "layerSwitcherContent");
  //  $("#layerSwitcherContent").hide();
    $("#tabs-Tool").empty();
    jQuery.get('resources/templates/viewer/dynalayer.html', function (template) {
        //$("#" + searchdiv).append(template);
    	//Add tad
		addTab('Remote Layer',template);
		
		$("#dynalayer-help").tipTip({defaultPosition:"right"});
    });
};


var remote_url = null;

function getWmsLayers() {
    if (!($('#conn_url').val())) {
        jAlert('Enter Url', 'Layer');

        return;
    }
    remote_url = $('#conn_url').val();
    var type = 'WMS';
    var wms_layers = {};
   
    var wms_url = remote_url + '?&service=' + type + '&version=1.1.1&request=GetCapabilities';
    var capability = null;

    $.ajax({
        type: "GET",
        cache: false,
        url: PROXY_PATH + wms_url,
        async: false,
        dataType: "xml",
        success: function (xml) {

            var capabilityXML = new OpenLayers.Format.WMSCapabilities();
            var obj = capabilityXML.read(xml);
            capability = obj.capability;

            jQuery.get('resources/templates/viewer/dynalayer.html', function (template) {

                jQuery("#wmslayerBody").empty();

                jQuery("#wmslayerTemplate").tmpl(null,

                {
                    capabilityList: capability.layers
                }

                ).appendTo("#wmslayerBody");

                setLayerImg(capability.layers);
            });

            


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Error Connecting To Remote Server', 'Layer');

        }
    });



}


function addDynalayer(_layername) {
    var layername = _layername;
    var layeralias = _layername.replace(":", "_");
	
	if($('#overlay-tr-'+layeralias).length<=0){
	
		var htmlStr = '';
		htmlStr = htmlStr + '<tr id="overlay-tr-' +layeralias+ '"><td  width="100%" class=" rowcolor" colspan="2"><table width="100%" cellspacing="0" cellpadding="0"><tr><td id="' + layeralias + '" width="100%" class="layerTR">';
		htmlStr = htmlStr + '<table width="100%" ><tr><td width="70%" class="leftshift">'+ layeralias + '</td>';
		
		htmlStr = htmlStr + '<td><div><img src="resources/images/viewer/hoversmallcross.png" onclick="javascript:removeRemoteLayer('+ "'" +layeralias + "'" +');"/></div></td>';
		
		htmlStr = htmlStr + '<td><div id="SliderSingle__' + layeralias + '" title="'+layeralias+'" class="slider-c"></div></td></tr></table></td>';

		htmlStr = htmlStr + '<td id="chk-'+layeralias+'" align="center" class="layer-td layerTR">';
		htmlStr = htmlStr + '<input type="checkbox" id="Visibility__' + layeralias + '" name="Visibility" value="' + layeralias + '" checked class="overlays" onchange="manageLayer(this);" />';
		htmlStr = htmlStr + '</td></tr></table>	</td></tr>';

		htmlStr = htmlStr + '<tr id="overlay-legend-' +layeralias+ '" ><td class="leftshift rowcolor_two" colspan="2"><img  id="' + layeralias + '_legend" src=""> </img></tr>';

		jQuery('#overlaysBody').append(htmlStr);

		$("#SliderSingle__" + layeralias).slider({

			min: 1,
			max: 9,
			value: 9,
			step: 1,
			slide: function (event, ui) {

				//OpenLayers.Map.activelayer.setOpacity(ui.value / 10);
				map.getLayersByName(event.target.title)[0].setOpacity(ui.value / 10);
			}
		});




		/////Add to map
		var wms_layer = null;
		wms_layer = new OpenLayers.Layer.WMS(layeralias, remote_url + '?', {
			layers: layername,
			STYLES: '',
			format: 'image/png',
			transparent: true
		},

		{
			transitionEffect: 'resize',
			isBaseLayer: false,
			selectable: false,
			queryable: false,
			exportable: false,
			editable: false,
			displayInLayerManager: true
		}

		);
		map.addLayers([wms_layer]);


		activateLayerClick();
		layerMap[layeralias] = layername;
		
		var legendurl = remote_url + "?REQUEST=GetMap&SRS="+ map.projection +"&STYLES=&FORMAT=image/png&SERVICE=WMS&VERSION=1.1.1&TRANSPARENT=TRUE";
			legendurl = legendurl.replace("GetMap", "GetLegendGraphic") + "&Layer=" + layername;
		
			$("#" + layeralias + "_legend").attr('src', legendurl);

			
	}	
	else{
	
	jAlert('Layer already added', 'Overlay layer');	
	return false;
	}
		
}



function setLayerImg(layers) {

    
    for (var lyr = 0; lyr < layers.length; lyr++) {
        var layerSrs;
        $.each(layers[lyr].bbox, function (val, text) {

            layerSrs = val;
            return false;
        });


        var wmsurl = remote_url + "?REQUEST=GetMap&SRS=" + layerSrs + "&STYLES=&FORMAT=image/png&SERVICE=WMS&VERSION=1.1.1&TRANSPARENT=TRUE&width=64&height=64";

        var imgurl = wmsurl + '&BBOX=' + layers[lyr].bbox[layerSrs].bbox[0] + ',' + layers[lyr].bbox[layerSrs].bbox[1] + ',' + layers[lyr].bbox[layerSrs].bbox[2] + ',' + layers[lyr].bbox[layerSrs].bbox[3] + '&LAYERS=' + layers[lyr].name;
		var templyr = layers[lyr].name.replace(':', '_')

        $("#" + templyr + "_img").attr('src', imgurl);
		
		
    }

}


function removeRemoteLayer(_remotelayer){

$("#overlay-tr-"+_remotelayer).remove();
$("#overlay-legend-"+_remotelayer).remove();


var layer=map.getLayersByName(_remotelayer)[0];
map.removeLayer(layer,true);

}
