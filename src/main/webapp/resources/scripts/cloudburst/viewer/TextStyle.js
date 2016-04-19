

var textSymbolizer = {
    "Text": {
        label: "${OBJECTID}",
        fontFamily: "Arial",
        fontStyle: "normal",
        fontWeight:"normal",
        fontSize: "10",
        fillColor: "#000000",
        haloColor: "#ffff00",
        haloRadius:3,
        haloOpacity:1
    }
};

Cloudburst.TextStyle = function (_map, _searchdiv) { 
    
	var labelLayer = null;
	var wfsurl=null;
	var _activelayer=null;
	var map = _map;
	
	
	
	_activelayer=OpenLayers.Map.activelayer;
	
	
	labelLayer = _activelayer.clone();
	labelLayer.name = _activelayer.name + "_Clone";			
	wfsurl = replaceString(labelLayer.url, /wms/gi, 'wfs');
	
    searchdiv = _searchdiv;
    showResultsinDialog = true;
    
	
    //removeChildElement("sidebar", "layerSwitcherContent");

    //$("#layerSwitcherContent").hide();
    $("#tabs-Tool").empty();
    jQuery.get('resources/templates/viewer/textstyle.html', function (template) {

       // $("#" + searchdiv).append(template);
    	
    	//Add tad
		addTab($._("home_page_textstyle"),template);
		
		$("#textstyle-help").tipTip({defaultPosition:"right"});
		//set default
		
		$("#label_color").css("background-color", textSymbolizer['Text'].fillColor);
		$("#label_halo_fillcolor").css("background-color", textSymbolizer['Text'].haloColor);
		$("#label_size").val(textSymbolizer['Text'].fontSize);
		$("#label_halo_radius").val(textSymbolizer['Text'].haloRadius);
		
		
		
		$("#label_size").spinner({
            min: 1
        });
        $("#label_halo_radius").spinner({
            min: 0,
            max: 360
        });
        //textSymbolizer['Text'].label = "${" + $('#fields option:selected').text() + "}";

        $("#labelvisibilty").click(function () {

            //labelLayer.setVisibility(!labelLayer.visibility);
        });
		
		
		$("#sliderDiv").slider({
            range: "max",
            min: 0,
            max: 10,
            value: 1,
            slide: function (event, ui) {
                textSymbolizer['Text'].haloOpacity = ui.value * 0.1;
            }
        });
		
		
		$("#label_halo_fillcolor").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).val(hex);
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#label_halo_fillcolor").val(hex);
                $("#label_halo_fillcolor").css("background-color", "#" + hex);
                textSymbolizer['Text'].haloColor = "#" + hex;
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });

        $("#label_color").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).val(hex);
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#label_color").val(hex);
                $("#label_color").css("background-color", "#" + hex);
                textSymbolizer['Text'].fillColor = "#" + hex;
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });


        $(".colorpicker").css("z-index", 1000000);
		
		
		$.ajax({
            
            url: "font/getfonts/" + "?" + token,
			success: function (data) {

                 $.each(data, function (key, val) {
                    $('#label_font_names').append($("<option></option>").attr("value", val).text(val));
                });
				$("#label_font_names").val(textSymbolizer['Text'].fontFamily);
            }
        });
		
		////
		
		///// column
		var _url = PROXY_PATH + wfsurl + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + layerMap[_activelayer.name];
		
		$.ajax({
            url: _url,            
            success: function (data) {
                var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
                var responseText = featureTypesParser.read(data);
                var featureTypes = responseText.featureTypes;

                for (var i = 0; i < featureTypes[0].properties.length; ++i) {                    
                    $('#fields').append($("<option></option>").attr("value", featureTypes[0].properties[i].name).text(featureTypes[0].properties[i].name));
                }

            },
            error: function (xhr, status) {
            	if(layerMap[_activelayer.name].indexOf("OSMM_") > -1){
            		jAlert("WFS operation on " + layerMap[_activelayer.name] + " layer is restricted");
            	}else{
            		jAlert('Sorry, there is a problem!');
            	}
            }
        });
		

		
		////
		
		
		$("#fields ,#label_font_names , #label_font_styles").change(function () {

            textSymbolizer['Text'].label = "${" + $('#fields option:selected').text() + "}";
            textSymbolizer['Text'].fontFamily = $('#label_font_names option:selected').text();
            textSymbolizer['Text'].fontStyle = $('#label_font_styles option:selected').text();
            textSymbolizer['Text'].fontWeight = $('#label_font_styles option:selected').text();

        });


        $('#label_size, #label_halo_radius').spinner().change(function () {

            textSymbolizer['Text'].fontSize = $('#label_size').val();
            textSymbolizer['Text'].haloRadius = $('#label_halo_radius').val();

        });

  
        $("#textstyle_apply").click(function () {
			if($("#fields").val()){   
				var filters = [];
				 /*var filter = new OpenLayers.Filter.Comparison({
                     type: OpenLayers.Filter.Comparison.EQUAL_TO,
                     property: 'ishistory',
                     value: 'false'
                 });
							
			 filters.push(filter);*/
			 
            var sld = createTextSLD(labelLayer, filters);
            labelLayer.mergeNewParams({
                SLD: sld,
                STYLES: layerMap[_activelayer.name]
            });
            if (map.getLayersByName(labelLayer.name)[0]) {
                map.removeLayer(labelLayer);
            }
            map.addLayer(labelLayer);
            labelLayer.redraw(true);
			}
			else{
				jAlert('Please Select Column Name', 'Text Style');
			return;
			}			
        });
        
        $('#text_column').html($._('column') + ':');
        $('#text_font').html($._('font'));
        $('#text_fontname').html($._('Name') + ':');
        $('#text_color').html($._('color') + ':');
        $('#text_size').html($._('size') + ':');
        $('#text_halo').html($._('halo'));
        $('#text_radius').html($._('radius') + ':');
        $('#text_fill').html($._('theme_fill') + ':');
        $('#opacity').html($._('theme_opacity') + ':');
        $('#textstyle_apply').attr("value", $._('theme_apply'));
    });
    
};

function createTextSLD(layer, filters) {
    var sld = {
        version: "1.0.0",
        namedLayers: {}
    };

    var layerNames = [layer.params.LAYERS].join(",").split(",");
    for (var i = 0, len = layerNames.length; i < len; i++) {
        var name = layerNames[i];
        sld.namedLayers[name] = {
            name: name,
            userStyles: []
        };
        var symbolizer = textSymbolizer;

        var filter = filters[i];
        sld.namedLayers[name].userStyles.push({
            name: name,
            rules: [
            new OpenLayers.Rule({
                symbolizer: symbolizer,
                filter: filter,
                maxScaleDenominator: layer.options.minScale
            })]
        });
    }

    //Post the SLD
    var sld_url = null;

    $.ajax({
        type: 'POST',
        url: "theme/createSLD",
        async: false,
        dataType: "text",
        data: { data: escape(new OpenLayers.Format.SLD().write(sld)) },
        success: function (result) {
            sld_url = result;//.substring(1, result.length - 1); /*For removing additional quotes*/
        },
        error: function (xhr, status) {
            alert('Sorry, there is a problem!');
        }
    });
    //return new OpenLayers.Format.SLD().write(sld);
    return sld_url;

   // return new OpenLayers.Format.SLD().write(sld);
}