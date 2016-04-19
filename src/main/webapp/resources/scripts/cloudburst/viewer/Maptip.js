

var maptipField;
Cloudburst.Maptip =  function(){
	
	$.ajax({
        url: STUDIO_URL + "maptip/" + project + "/" + OpenLayers.Map.activelayer.name + "?" + token,
        success: function (data) {
            var wfsurl = OpenLayers.Map.activelayer.url.replace(new RegExp("wms", "i"), "wfs");

            var wfsSchema = wfsurl + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typename=" + layerMap[OpenLayers.Map.activelayer.name];
            var targetNamespace = null;
            var featPref = null;
            var geometryColName = null;

            $.ajax({
                url: PROXY_PATH + wfsSchema,
                dataType: "xml",
                async: false,
                success: function (data) {
                    var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
                    var responseText = featureTypesParser.read(data);
                    var featureTypes = responseText.featureTypes;
                    targetNamespace = responseText.targetNamespace;
                    featPref = responseText.targetPrefix;
                    featureTypesFields = featureTypes[0].properties;

                    for (var i = 0; i < featureTypes[0].properties.length; ++i) {
                        if (featureTypes[0].properties[i].type.indexOf('gml')>=0) {
                            geometryColName = featureTypes[0].properties[i].name;
                            break;
                        }
                    }
                }
            });

            var actualLayerName = layerMap[OpenLayers.Map.activelayer.name];
            var pos = actualLayerName.indexOf(":");
            var featType = null;
            if (pos > 1)
                featType = actualLayerName.substring(pos + 1);
            else
                featType = actualLayerName;

            var proj = new OpenLayers.Projection(OpenLayers.Map.activelayer.projection.projCode);

            var protocol = new OpenLayers.Protocol.WFS({
            	headers: { Authorization : "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE="},
                version: "1.0.0",
                srsName: OpenLayers.Map.activelayer.projection.projCode,
                url: wfsurl,
                featureType: featType,
                geometryName: geometryColName,
                featurePrefix: featPref,
                featureNS: targetNamespace,
                schema: wfsSchema
            });
            field = data;
            maptipField=data.field		
            //mapTipOptions = { "version": "1.0.0", "geometryName": layerRequiredParams.geomField, "featurePrefix": layerRequiredParams.targetPrefix, "typeName": layerRequiredParams.targetPrefix + activeLayer.name };
            for (var key in mapControls) {
                if (key == 'maptip') {
                    control = mapControls[key];
                    control.protocol = protocol;// OpenLayers.Protocol.WFS.fromWMSLayer(activeLayer, mapTipOptions);
                    //control.protocol.options.url = replaceString(control.protocol.options.url, /wms/gi, 'wfs');

                    //map.addControl(control);
                }
            }

            Cloudburst.Navi.prototype.toggleControl("maptip");
        }
    });
	
	
};

var hoverResponse = function(response) {
	
	
    var _xy = response.object.handlers.hover.evt.xy;
    $('#maptips').html(response.feature.attributes[maptipField]);
    $('#maptips').css('position', 'absolute');
    $('#maptips').css('top', (_xy.y) + $("#map").position().top);
    $('#maptips').css('left', _xy.x + $("#map").position().left);
	
    $('#maptips').show();
   
}

var hoverOutResponse = function(response) {
    $('#maptips').html('');
    $('#maptips').hide();
}



function getMaptipField(_project,_layer){
	jQuery.ajax({
			url: STUDIO_URL + "maptip/"+_project+"/"+_layer + "?" + token,
			success: function(data){			
				maptipField=data.field			
			}
	});

}