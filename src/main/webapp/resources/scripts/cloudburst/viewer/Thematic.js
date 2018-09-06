

var map;
var wfsurl = null;
var lyrType = null;
var wfsCache = {};
var layerCache = {};
var layerOptions = null;
var selectedField = null;
var fillColor1 = "0xFFFF00";
var fillColor2 = "0x0000FF";
var outColor = "#000000";
var lineColor1 = "0xFFFF00";
var lineColor2 = "0x0000FF";
var markerColor = 16514824;
var filters = [];
var result = [];
var symbology = [];
var arClass;
var prefix;
var type;
var featureNS;
var uniqueField;
var qntValue_1;
var qntValue_2;
var opacity = 50;
var markerSizeFrom = 4;
var markerSizeTo = 18;
var sld_result;
var priority_keyField;

var defaultPointImgUrl;
var defaultLineImgUrl;
var defaultPolygonImgUrl;
var defaultPolygonOutlineImgUrl;

var selectedPointImgUrl;
var selectedLineImgUrl;
var selectedPolygonImgUrl;
var selectedPolygonOutlineImgUrl;

var pointExtImgUrl;
var lineExtImgUrl;
var polygonExtImgUrl;
var polygonExtOutlineUrl;
var selectedPatternId;
var selectedLinePatternId;

//Amitabh (Temporary)
var activeLayer;
var _filterjoin;
var displayableCols;

function Thematic(_map) {
	this.map = _map;
    defaultPointImgUrl =""; // "resources/images/symbols/markers/mar00.png"
    defaultLineImgUrl = ""; //"resources/images/symbols/line/line01.png"
    defaultPolygonImgUrl = ""; // "resources/images/symbols/patterns/pat00.png"

    selectedPointImgUrl = defaultPointImgUrl;
    selectedLineImgUrl = defaultLineImgUrl;
    selectedPolygonImgUrl = defaultPolygonImgUrl;
    //selectedPolygonOutlineImgUrl = defaultPolygonOutlineImgUrl;

    var pointExtImgRoration = "0";
    pointExtImgUrl = symURL + defaultPointImgUrl; //defaultPointImgUrl.replace("../Content", "/Content");
    lineExtImgUrl = symURL + defaultLineImgUrl; //defaultLineImgUrl.replace("../Content", "/Content");
    polygonExtImgUrl = symURL + defaultPolygonImgUrl; //defaultPolygonImgUrl.replace("../Content", "/Content");

    uniqueField = null;
    activeLayer = OpenLayers.Map.activelayer;
    if(activeLayer == null || activeLayer == undefined){
    	jAlert("Please select active layer", "Thematic Map");
    	return;
    }else{
    	//Amitabh
    	 layerMap[activeLayer.name] = activeLayer.params.LAYERS;
    }
    
    var selectedLayer = layerMap[activeLayer.name];
    var pos = selectedLayer.indexOf(":");
    prefix = selectedLayer.substring(0, pos);
    type = selectedLayer.substring(++pos);

    outColor = "#000000";

	searchdiv = 'sidebar';
    
	
	//removeChildElement("sidebar","layerSwitcherContent");	
	//$("#layerSwitcherContent").hide();
	
	$("#tabs-Tool").empty();
	
    //$('#thematiccontent').load('resources/templates/Thematics.html', function () {
	jQuery.get('resources/templates/viewer/Thematics.html', function (template) {
		
		//$("#" + searchdiv).append(template);
		//Add tad
		addTab($._('thematic'),template);
		
		$("#thematic-help").tipTip({defaultPosition:"right"});
		
		wfsurl = replaceString(activeLayer.url, /wms/gi, 'wfs');
        getGeometryTypo(activeLayer);

        var oHandler;
        oHandler = $("#outlineStyle").msDropDown().data("dd");

        isDefaultPolygon = 'default';
        isDefaultPoint = 'default';
        isDefaultLine = 'default';

        $(document).ready(function () {
            $("#slider").slider();
            $("#opacity").text($._('theme_opacity') + ':' + opacity + "%");
        });

        $("#slider").slider({
            range: "max",
            min: 0,
            max: 100,
            value: 0,
            slide: function (event, ui) {
                opacity = ui.value;
                $("#opacity").text($._('theme_opacity') + ':' + opacity + "%");
            }
        });

        $("#slider").slider("value", opacity);
        opacity = $("#slider").slider("value");
        $("#opacity").text($._('theme_opacity') + ':' + opacity + "%");

        /*Initialize the drop-downs*/
        $("#poly_pattern").dropdown('poly_Patterns', applyPolygonPattern);
        $("#img_poly_pattern").css("background-image", 'url("' + selectedPolygonImgUrl + '")');
        $("#point_style").dropdown('marker_symbols', applyMarkerStyle);
        $("#img_point_style").css("background-image", 'url("' + selectedPointImgUrl + '")');
        $("#segment_style").dropdown('line_symbols', applyLineStyle);
        $("#img_segment_style").css("background-image", 'url("' + selectedLineImgUrl + '")');

        $('#mode').change(onModeChange);
        $('#classification_field').change(onFieldChange);

        $("#fillColor1").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#fillColor1").css("background-color", "#" + hex);
                fillColor1 = parseInt("0x" + hex);
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });

        $("#fillColor2").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#fillColor2").css("background-color", "#" + hex);
                fillColor2 = parseInt("0x" + hex);
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });

        $("#outcolor").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#outcolor").css("background-color", "#" + hex);
                outColor = "#" + hex;
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });

        $("#markerColor").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#markerColor").css("background-color", "#" + hex);
                markerColor = parseInt("0x" + hex);
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });

        $("#lineColor1").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#lineColor1").css("background-color", "#" + hex);
                lineColor1 = parseInt("0x" + hex);
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });

        $("#lineColor2").ColorPicker({
            onSubmit: function (hsb, hex, rgb, el) {
                $(el).ColorPickerHide();
            },
            onBeforeShow: function () {
                $(this).ColorPickerSetColor(this.value);
            },
            onChange: function (hsb, hex, rgb) {
                $("#lineColor2").css("background-color", "#" + hex);
                lineColor2 = parseInt("0x" + hex);
            }
        }).bind('keyup', function () {
            $(this).ColorPickerSetColor(this.value);
        });

        $(".colorpicker").css("z-index", 1000000);

        $("#symbolApply").click(function () { //handler of button symbolApply
            layerOptions = OpenLayers.Util.applyDefaults(layerOptions, {
                displayInLayerSwitcher: false,
                tileOptions: {
                    maxGetUrlLength: 2048
                }
            });

            clearSelection(true);
            if ($('#cbValues tr').length > 1) { //As one empty rows is added by default
                activeLayer.mergeNewParams({
                    SLD: sld_result
                    //,STYLES: 'multiple_rules' //NOTE: Need to enable for ArcGIS 10
                });
                activeLayer.redraw(true);
                refreshLegends(); 
            }
            
            //Check if layer is RoW_Path an selected field is _class(priority)
            if(activeLayer.name == 'RoW_Path' && selectedField == '_class'){
	            var result = new Result(activeLayer.name, featureNS, _filterjoin, true);
	            sortInDesc(_filterjoin, result, layerMap[activeLayer.name]);
	            result.displayResult(displayableCols, priority_keyField, undefined, true, true);
            }

        });
	/*
        $("#thematiccontent").dialog({
            width: 460,
            height: 525,
            resizable: false,
            title: '<img style="vertical-align: middle;" src="resources/images/toolbar/thematic.png" /><span style="vertical-align: middle;"> Thematic </span>',
            close: function (ev, ui) {
                $('#thematiccontent').dialog('destroy');
            }
        });
		*/
    	$('#symbolType').html($._('theme_symbol') + ':');
    	$('#mode1').html($._('theme_mode') + ':');
    	$('#classification').html($._('theme_classification_field') + ':');
    	$('#interval_field').html($._('theme_interval') + ':');
    	$('#theme_header').html($._('theme_classification'));
    	$('#style_header').html($._('theme_style'));
    	$('#theme_line_outline').html($._('theme_outline') + ':');
    	$('#theme_line_startcolor').html($._('theme_start') + ':');
    	$('#theme_line_endcolor').html($._('theme_end') + ':');
    	$('#theme_linesize').html($._('theme_size') + ':');
    	$('#theme_classinterval').html($._('theme_class_interval'));
    	$('#classify').attr("value", $._('theme_classify'));
    	$('#removeInterval').attr("value", $._('theme_delete_class'));
    	$('#symbolApply').attr("value", $._('theme_apply'));
    	$('#theme_fill').html($._('theme_fill'));
    	$('#theme_poly_startcolor').html($._('theme_start') + ':');
    	$('#theme_poly_endcolor').html($._('theme_end') + ':');
    	$('#theme_poly_outline').html($._('theme_outline') + ':');
    	$('#theme_poly_outcolor').html($._('color') + ':');
    	$('#theme_poly_outwidth').html($._('theme_outline_width') + ':');
    	$('#theme_pointsymbol').html($._('theme_symbol') + ':');
    	$('#theme_pointstartcolor').html($._('theme_start') + ':');
    	$('#theme_pointsize').html($._('theme_size') + ':');
    });
}

function displayStyle() {

    if (lyrType == "Point") {
        $("#polystylediv").hide();
        $("#linestylediv").hide();
        $("#markerstylediv").show('fast');
    } else if (lyrType == "Polygon") {
        $("#markerstylediv").hide();
        $("#linestylediv").hide();
        $("#polystylediv").show('fast');
    } else { //Linestring
        $("#polystylediv").hide();
        $("#markerstylediv").hide();
        $("#linestylediv").show('fast');
    }
}

function applyPolygonPattern(imgUrl, id) {
    if (imgUrl == 'default') {
        isDefaultPolygon = 'default';
        selectedPolygonImgUrl = defaultPolygonImgUrl;
    } else {
        imgUrl = imgUrl.replace("..resources", "resources");
        polygonExtImgUrl = symURL + imgUrl;
        selectedPolygonImgUrl = polygonExtImgUrl;
        selectedPatternId = id;
        isDefaultPolygon = null;
    }
}

function applyMarkerStyle(imgUrl, id) {
    if (imgUrl == 'default') {
        isDefaultPoint = 'default';
        selectedPointImgUrl = defaultPointImgUrl;
    } else {
        pointExtImgUrl = symURL + imgUrl;
        selectedPointImgUrl = pointExtImgUrl;
        selectedPatternId = id;
        isDefaultPoint = null;
    }
}

function applyLineStyle(imgUrl, id) {
    if (imgUrl == 'default') {
        isDefaultLine = 'default';
        selectedLineImgUrl = defaultLineImgUrl;
    } else {
        lineExtImgUrl = symURL + imgUrl;
        selectedLineImgUrl = lineExtImgUrl;
        selectedLinePatternId = id;
        isDefaultLine = null;
    }
}

function onModeChange() {
    var selected = $('#mode option:selected');
    if (selected.val() == "Equal Interval" || selected.val() == "Quantiles") {
        $('#classification_field').empty();
        if (activeLayer != null) {
            if (selected.val() == "Quantiles") {
                //get the keyfield
            	getKeyField(activeLayer.name);
            } else {
                uniqueField = null;
            }
            populateFieldsOfTypeInt(activeLayer);
        }
        else
            jAlert('Please select an active layer');
    }
}

function onFieldChange() {
    var selected = $('#classification_field option:selected');
    selectedField = selected.val();
}

function populateFieldsOfTypeInt(layer) {

    var options = {
        url: wfsurl,
        params: {
            SERVICE: "WFS",
            TYPENAME: layerMap[layer.name],
            REQUEST: "DescribeFeatureType",
            VERSION: "1.0.0"
        },
        callback: function (request) {
            var format = new OpenLayers.Format.WFSDescribeFeatureType();
            var doc = request.responseXML;
            if (!doc || !doc.documentElement) {
                doc = request.responseText;
            }
            var describeFeatureType = format.read(doc);
            featureNS = describeFeatureType.targetNamespace;
            //             wfsCache[layer.id] = describeFeatureType;
            //             var cache = wfsCache[layer.id];
            var cache = describeFeatureType;
            for (var i = 0, len = cache.featureTypes.length; i < len; i++) {
                var typeName = cache.featureTypes[i];
                var properties = typeName.properties;
                for (var j = 0, lenj = properties.length; j < lenj; j++) {
                    var property = properties[j];
                    var type = property.type;
                    if ((type.indexOf('Integer') >= 0) || (type.indexOf('int') >= 0) || (type.indexOf('long') >= 0) || (type.indexOf('double') >= 0) || (type.indexOf('decimal') >= 0) || (type.indexOf('short') >= 0)) {
                        if (j == 0) {
                            selectedField = property.name;
                        }
                        $('#classification_field').append($("<option></option>").attr("value", property.name).text(property.name));
                    } else if ((type.indexOf('LineString') >= 0) || (type.indexOf('MultiLineString') >= 0) || (type.indexOf('GeometryAssociationType') >= 0) || (type.indexOf('GeometryPropertyType') >= 0) || (type.indexOf('Point') >= 0) || (type.indexOf('MultiPoint') >= 0) || (type.indexOf('Polygon') >= 0) || (type.indexOf('MultiPolygon') >= 0)) {
                        result.push(property);
                    }
                }

                getGeometryTypo(layer);
            }
        },
        scope: this
    };
    OpenLayers.Request.GET(options);
}

function getGeometryTypo(layer) {
	//var exp = new RegExp(/geom/g);
    $.ajax({
        url: PROXY_PATH + wfsurl + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + layerMap[layer.name],
        dataType: "text",
        success: function (text) {

            if (text.search(/gml:MultiPolygon/i) >= 0 || text.search(/gml:Polygon/i) >= 0) {
                lyrType = 'Polygon';
            } else if (text.search(/gml:MultiLineString/i) >= 0 || text.search(/gml:LineString/i) >= 0) {
                lyrType = 'LineString';
            } else if (text.search(/gml:MultiPoint/i) >= 0 || text.search(/gml:Point/i) >= 0) {
                lyrType = 'Point';
            }else{
            	lyrType = 'Polygon';
            }
            displayStyle();
        },
        error: function (xhr, status) {
        	if(layerMap[layer.name].indexOf("OSMM_") > -1){
        		jAlert("WFS operation on " + layerMap[layer.name] + " layer is restricted");
        	}else{
        		jAlert('Sorry, there is a problem!');
        	}
        }
    });
}

function create_filter(user){
	role = user.functionalRole;
	$.ajax({
        type: 'GET',
        url: "theme/date",
        dataType: "text",
        async: false,
        success: function (data) {
            current_date = data;
            $.ajax({
                type: 'GET',
                url: "theme/promptdate",
                dataType: "text",
                async: false,
                success: function (data) {
                	prompt_date = data;
                }
            });
        }
    });
	
	//Case Supervisor
	//if(role == 12 || role == 7 || role == 5 || role == 6 || role == 4 || role == 1){
		/* _filter1 = new OpenLayers.Filter.Comparison({
             type: OpenLayers.Filter.Comparison.EQUAL_TO,
             property: "ishistory",
             value: 'false'
         })
		 
		 _filter2 = new OpenLayers.Filter.Comparison({
			 type: OpenLayers.Filter.Comparison.BETWEEN,
			 property: "dateofnextsurvey",
			 lowerBoundary: current_date,
			 upperBoundary: prompt_date
		 })
		 
		 _filterjoin = new OpenLayers.Filter.Logical({
     	    type: OpenLayers.Filter.Logical.AND,
    	    filters:[_filter1, _filter2]
    	});*/
	//} 
	if(role == 8){//Case Head Warden
		var _filters = [];

		//Find out the user
		 $.ajax({
			 type: "GET",
	         url: STUDIO_URL + 'user/reportees/' + user.id,
	         async:false,
	         success: function (data) {
	        	 for(var usr in data){
	        		_filter = new OpenLayers.Filter.Comparison({
                         type: OpenLayers.Filter.Comparison.EQUAL_TO,
                         property: "assigned_to",
                         value: data[usr].email
                     });
	        		_filters.push(_filter);
	        	 }
	        	 
	        	 //Perform Filter OR  Join
	        	 if(_filters.length > 0){
	        		 _filterOrjoin = new OpenLayers.Filter.Logical({
	        	     	    type: OpenLayers.Filter.Logical.OR,
	        	    	    filters: _filters
	        		 });
	        		 
	        		 _filterjoin = new OpenLayers.Filter.Logical({
	        	     	    type: OpenLayers.Filter.Logical.AND,
	        	    	    filters:[_filterjoin,  _filterOrjoin]
	        	    	});
	        	 }
	         }
		 });
	}else if (role == 9 || role == 10){
		_filter = new OpenLayers.Filter.Comparison({
            type: OpenLayers.Filter.Comparison.EQUAL_TO,
            property: "assigned_to",
            value: user.email
        });
		
		 _filterjoin = new OpenLayers.Filter.Logical({
	     	    type: OpenLayers.Filter.Logical.AND,
	    	    filters:[_filterjoin,  _filter]
	    	});
	}
}

function classify() { //Handler of classify button
	 _filterjoin = undefined;
	 var selected = $('#mode option:selected');
	 if (selected.val() != "Quantiles"){
		 	uniqueField = null;
	
	var fieldName;
    var minValue = null;
    var maxValue = null;
    var min = 0;
    var max = 0;

    removeRow();
    if (selectedField == null) {
        jAlert("Please select classification field");
        return;
    }
    if ($('#interval').val() == "" || $('#interval').val() == 0) {
        jAlert("Please specify interval");
        return;
    }
    if (isNumeric($('#interval').val()) == false) {
        jAlert("Please enter numeric value");
        return;
    }

   /* if (uniqueField == null) { //Case class interval
        fieldName = selectedField;
    } else { //case class quantile
        fieldName = uniqueField;
        }*/
    fieldName = selectedField;

    if (lyrType == 'Point') {
        markerSizeFrom = parseInt($("#szfrom").val());
        //markerSizeTo = parseInt($("#szto").val());
    }

    if ($("#polystylediv").is(':visible')) {
        if ($("#outWidth").val() == "") {
            jAlert("Please enter outline width");
            return;
        }
    } else if ($("#markerstylediv").is(':visible')) {
        if ($("#szfrom").val() == "" || $("#szfrom").val() == 0) {
            jAlert("Please enter point size");
            return;
        }
    } else if ($("#linestylediv").is(':visible')) {
        if ($("#lineWidth").val() == "") {
            jAlert("Please enter line width");
            return;
        }
    }

    $.ajax({
        url: PROXY_PATH + wfsurl + "&request=GetFeature&service=WFS&version=1.0.0&typeName=" + layerMap[activeLayer.name] + "&propertyname=" + fieldName,
        dataType: "xml",
        success: function (data) {
            var response = new OpenLayers.Format.GML.v2({
                featureType: type,
                gmlns: 'http://www.opengis.net/gml',
                featureNS: featureNS,
                featurePrefix: prefix,
                extractAttributes: true
            }).read(data);

            arClass = [];
            if (data != null) {
                if (response[0] == null) {
                    jAlert("Server not responding");
                    return;
                } else {
                    /*Store all query features in array*/
                    var features = new Array(response.length);
                    for (var cnt = 0; cnt < response.length; cnt++) {
                        features[cnt] = Number(response[cnt].data[fieldName]);
                    }

                    /*Sort feature values*/
                    //features = sortAttributeValues(features);
                    features = features.sort(function (a, b) { return a - b });

                    /*Get min and max value*/
                    for (min = 0; min < features.length; min++) { //Get min value unless value is not undefined
                        if (features[min] != null && !isNaN(features[min])) {
                            minValue = features[min];
                            break;
                        }
                    }

                    for (max = features.length - 1; max >= 0; max--) {//Get max value unless value is not undefined
                        if (features[max] != null && !isNaN(features[max])) {
                            maxValue = features[max];
                            break;
                        }
                    }

                    if (minValue == null || maxValue == null) {
                        jAlert('Missing data in classification field');
                        return;
                    }

                    var interval = parseInt($('#interval').val());
                    if (maxValue < interval) {
                        jAlert("Interval is greater than selected field value");
                        return;
                    } else {
                        sld_result = null;

                        if (lyrType == 'Polygon') {
                            colors = getColorRange(interval, fillColor1, fillColor2); //Get color range
                        } else if (lyrType == 'LineString' || lyrType == 'Line') {
                            colors = getColorRange(interval, lineColor1, lineColor2); //Get color range
                        } else {
                            colors = markerColor;
                        }

                        var factor;
                        var lastVal;
                        if (uniqueField == null) {
                            factor = ((maxValue - minValue) / interval);
                        } else {
                            factor = ((max - min) / interval);
                            minValue = min;
                        }

                        var filter = null;
                        filters = [];
                        symbology = [];
                        lastVal = minValue;

                        for (i = 0; i < interval; i++) {
                            arClass[i] = new Array(3);

                            // var currVal = roundNumber(factor * (i + 1), 3);
                            var currVal = minValue + roundNumber(factor * (i + 1), 3);

                            if (lyrType != 'Point') {
                                var hex = "#" + colors[i].toString(16).toUpperCase(); //Convert dec to hex
                            } else {
                                var hex = "#" + colors.toString(16).toUpperCase();
                            }

                            if (uniqueField != null) {
                                getQuantile(lastVal, currVal, features);
                                if (i == 0) {
                                    arClass[i][0] = qntValue_1;
                                    arClass[i][1] = qntValue_2;
                                    arClass[i][2] = hex;
                                } else {
                                    if (qntValue_1 == arClass[i - 1][0] && qntValue_2 == arClass[i - 1][1]) {
                                        arClass[i][0] = qntValue_1;
                                        arClass[i][1] = qntValue_2;
                                        arClass[i][2] = arClass[i - 1][2];
                                    } else {
                                        arClass[i][0] = qntValue_1;
                                        arClass[i][1] = qntValue_2;
                                        arClass[i][2] = hex;
                                    }
                                }

                                //Create filter
                                var filter1 = new OpenLayers.Filter.Comparison({ type: OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO, property: selectedField, value: qntValue_1 });
                                var filter2 = new OpenLayers.Filter.Comparison({ type: OpenLayers.Filter.Comparison.LESS_THAN_OR_EQUAL_TO, property: selectedField, value: qntValue_2 });
                                var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter1, filter2] });
                                /*var filter_history = new OpenLayers.Filter.Comparison({
                        	        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        	        property: 'ishistory',
                        	        value: 'false'
                        	    });
                            
                                var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter_history, filter1, filter2] });
                                
                                if(activeLayer.name == 'RoW_Path' && selectedField == '_class'){
                                	var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter, _filterjoin] });
                                }
                                
                                if(activeLayer.name == 'Access_Land'){
                                	var filter_agreement = new OpenLayers.Filter.Comparison({
                            	        type: OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO,
                            	        property: 'agreement_end_date',
                            	        value: current_date
                            	    });
                                	
                                	var filter3 = new OpenLayers.Filter.Comparison({
                            	        type: OpenLayers.Filter.Comparison.IS_NULL,
                            	        property: 'agreement_end_date'
                            	    });
                                	
                                	filter_agreement = new OpenLayers.Filter.Logical({
                                 	    type: OpenLayers.Filter.Logical.OR,
                                	    filters:[filter_agreement, filter3]
                                	});
                                	
                                	var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter, filter_agreement] });
                                }*/

                            } else {
                                if (i == 0) {
                                    arClass[i][0] = lastVal;
                                    arClass[i][1] = currVal;
                                    arClass[i][2] = hex;
                                } else {
                                    if (lastVal == arClass[i - 1][0] && currVal == arClass[i - 1][1]) {
                                        arClass[i][0] = lastVal;
                                        arClass[i][1] = currVal;
                                        arClass[i][2] = arClass[i - 1][2];
                                    } else {
                                        arClass[i][0] = lastVal;
                                        arClass[i][1] = currVal;
                                        arClass[i][2] = hex;
                                    }
                                }

                                //Create filter
                                var filter1 = new OpenLayers.Filter.Comparison({ type: OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO, property: selectedField, value: lastVal });
                                var filter2 = new OpenLayers.Filter.Comparison({ type: OpenLayers.Filter.Comparison.LESS_THAN_OR_EQUAL_TO, property: selectedField, value: currVal });
                                var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter1, filter2] });
                                /*var filter_history = new OpenLayers.Filter.Comparison({
                        	        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        	        property: 'ishistory',
                        	        value: 'false'
                        	    });
                                var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter_history, filter1, filter2] });
                                
                                if(activeLayer.name == 'RoW_Path' && selectedField == '_class'){
                                	var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter, _filterjoin] });
                                }
                                if(activeLayer.name == 'Access_Land'){
                                	var filter_agreement = new OpenLayers.Filter.Comparison({
                            	        type: OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO,
                            	        property: 'agreement_end_date',
                            	        value: current_date
                            	    });
                                	
                                	var filter3 = new OpenLayers.Filter.Comparison({
                            	        type: OpenLayers.Filter.Comparison.IS_NULL,
                            	        property: 'agreement_end_date'
                            	    });
                                	
                                	filter_agreement = new OpenLayers.Filter.Logical({
                                 	    type: OpenLayers.Filter.Logical.OR,
                                	    filters:[filter_agreement, filter3]
                                	});
                                	var filter = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.AND, filters: [filter, filter_agreement] });
                                }*/
                                
                            }

                            filters[i] = filter;

                            //Create symbol
                            var symbol = new Object();
                            if (lyrType == 'Polygon') {
                                symbol.fillColor = arClass[i][2];
                                symbol.stroke = true;
                                symbol.strokeColor = outColor;
                                symbol.strokeWidth = parseInt($('#outWidth').val());
                                symbol.stokeDashStyle = $('#outlineStyle option:selected').val();
                            } else if (lyrType == 'LineString') {
                                symbol.strokeColor = arClass[i][2];
                                symbol.strokeWidth = parseInt($('#lineWidth').val());
                                symbol.stokeDashStyle = $('#linePattern option:selected').val();
                            } else if (lyrType == 'Point') {
                                symbol.graphicName = $("#markerPattern").val();
                                symbol.fillColor = arClass[i][2];
                                var nfactor;
                                if (markerSizeFrom > 8)
                                    markerSizeFrom = 8;

                                if (markerSizeFrom > 6) {
                                    nfactor = 0.40;
                                } else {
                                   nfactor = 0.90;
                                }
                                symbol.pointRadius = markerSizeFrom + (nfactor * i) //markerSizeTo - (markerSizeTo - (markerSizeFrom + (i + 1) * 2));
                            }
                            symbology[i] = symbol;

                            lastVal = currVal;
                        }
                    }
                }

                var sld = createSLD(activeLayer, filters, result);
                //if ($('#fillPattern').val() != "solid") {
                if (isDefaultPolygon != 'default') {
                    var val = modifySLDPolyDOM(sld);
                    if (val != null)
                        sld = val;
                } else if (isDefaultPoint != 'default') {
                    var val = modifySLDMarkerDOM(sld);
                    if (val != null)
                        sld = val;
                } else if (isDefaultLine != 'default') {
                    var val = modifySLDLineDOM(sld);
                    if (val != null)
                        sld = val;
                }

                //Post the SLD
                $.ajax({
                    type: 'POST',
                    url: "theme/createSLD",
                    dataType: "text",
                    data: { data: escape(sld) },
                    success: function (result) {
                        sld_result = result;
                        drawSample();
                    },
                    error: function (xhr, status) {
                        jAlert('Sorry, there is a problem!');
                    }
                });
            }
        }
    });
}
}
function getQuantile(lastVal, currVal, features) {
//    var filter1 = new OpenLayers.Filter.Comparison({ type: OpenLayers.Filter.Comparison.EQUAL_TO,
//        property: uniqueField, value: parseInt(lastVal)
//    });

//    var filter2 = new OpenLayers.Filter.Comparison({ type: OpenLayers.Filter.Comparison.EQUAL_TO,
//        property: uniqueField, value: parseInt(currVal)
//    });

//    var filterJoin = new OpenLayers.Filter.Logical({ type: OpenLayers.Filter.Logical.OR,
//        filters: [filter1, filter2]
//    });

//    var filter_1_0 = new OpenLayers.Format.Filter({
//        version: "1.0.0"
//    });

//    var xml = new OpenLayers.Format.XML();
//    var xmlFilter = xml.write(filter_1_0.write(filterJoin));

//    $.ajax({
//        url: "../Proxy.ashx?" + wfsurl + "&request=GetFeature&service=WFS&version=1.0.0&typeName=" + layerMap[activeLayer.name] + "&propertyname=" + selectedField + "&filter=" + xmlFilter + "&maxFeatures=2" + "&sortBy=" + selectedField + "+A",
//        async: false,
//        success: function (data) {
//            var features = new OpenLayers.Format.GML.v2({
//                featureType: type,
//                gmlns: 'http://www.opengis.net/gml',
//                featureNS: featureNS,
//                featurePrefix: prefix,
//                extractAttributes: true
//            }).read(data);

//            if (features.length > 0) {
//                var i = 0;

//                for (var feat in features) {
//                    if (features.length == 1) {
//                        qntValue_1 = 0;
//                        eval("qntValue_2 = features[feat].attributes." + selectedField);
//                    } else {
//                        if (i == 0) {
//                            eval("qntValue_1 = features[feat].attributes." + selectedField);
//                        } else {
//                            eval("qntValue_2 = features[feat].attributes." + selectedField);

//                            if (Number(qntValue_1) > Number(qntValue_2)) {
//                                var tmp = qntValue_1;
//                                qntValue_1 = qntValue_2;
//                                qntValue_2 = tmp;
//                            }
//                        }
//                        i++;
//                    }
//                }
//                if (qntValue_1 == null) {
//                    qntValue_1 = qntValue_2;
//                    //qntValue_2 = "";
//                }
//            }
//        },
//        error: function (xhr, status) {
//            jAlert('Sorry, there is a problem!');
//        }
//    });
    if (features.length > 0) {
        qntValue_1 = features[parseInt(lastVal)];
        qntValue_2 = features[parseInt(currVal)];
    }
}

function drawSample(lastVal, currVal, hex, symbol) {
    var imgSrc;

    //url = activeLayer.url + "&TRANSPARENT=true&FORMAT=image%2Fpng&VERSION=1.1.1&SERVICE=WMS&REQUEST=GetLegendGraphic&STYLES=multiple_rules&EXCEPTIONS=application%2Fvnd.ogc.se_inimage"; //&SRS=EPSG%3A4326";
    url = activeLayer.url + "&TRANSPARENT=true&FORMAT=image%2Fpng&VERSION=1.1.1&SERVICE=WMS&REQUEST=GetLegendGraphic&STYLES=&EXCEPTIONS=application%2Fvnd.ogc.se_inimage"; //&SRS=EPSG%3A4326";
    url = url + "&SLD=" + sld_result + "&Layer=" + layerMap[activeLayer.name]
    $("table#cbValues tr:last").after('<tr><td>' + '<img src="' + url + '"/>' + '</td></tr>');
}

function createSLD(layer, filters, geometryAttributes) {
    var rules = [];
    var sld = {
        version: "1.0.0",
        namedLayers: {}
    };

    var layerName = layer.params.LAYERS;
    sld.namedLayers[layerName] = {
        name: layerName,
        userStyles: []
    };
    for (var i = 0; i < filters.length; i++) {
        if (lyrType.indexOf('Polygon') >= 0) {
            symbolizer = {
                Polygon: {
                    fillColor: symbology[i].fillColor,
                    stroke: symbology[i].stroke,
                    strokeColor: symbology[i].strokeColor,
                    strokeWidth: symbology[i].strokeWidth,
                    strokeLinecap: 'round',
                    strokeDashstyle: symbology[i].stokeDashStyle,
                    strokeOpacity: 1.0,
                    fillOpacity: opacity / 100
                }
            };
        } else if (lyrType.indexOf('LineString') >= 0) {
            symbolizer = {
                Line: {
                    strokeColor: symbology[i].strokeColor,
                    strokeWidth: symbology[i].strokeWidth,
                    strokeLinecap: 'round',
                    strokeDashstyle: symbology[i].stokeDashStyle,
                    strokeOpacity: 1.0
                }
            };

        } else if (lyrType.indexOf('Point') >= 0) {
            symbolizer = {
                Point: {
                    graphicName: symbology[i].graphicName,
                    fillColor: symbology[i].fillColor,
                    pointRadius: symbology[i].pointRadius
                }
            };
        }

        var filter = filters[i];
        var rule = new OpenLayers.Rule({
            title: arClass[i][0] + " - " + arClass[i][1],
            symbolizer: symbolizer,
            filter: filter,
            maxScaleDenominator: layer.options.minScale
        })
        rules[i] = rule;

    } //For loop close

    sld.namedLayers[layerName].userStyles.push({
        //name: 'multiple_rules',
        rules: rules
    });
    return new OpenLayers.Format.SLD().write(sld);
}

function roundNumber(num, dec) {
    var result = Math.round(num * Math.pow(10, dec)) / Math.pow(10, dec);
    return result;
}

function getKeyField(actualLayerName) {
    $.ajax({
        url: STUDIO_URL + 'layer/' + actualLayerName + "?" + token,
		async: false,
        success: function (data) {
            uniqueField = data.keyField;
        }
    });

}

function getColorRange(interval, color1, color2) {
    var theR0 = (color1 & 0xff0000) >> 16;
    var theG0 = (color1 & 0x00ff00) >> 8;
    var theB0 = (color1 & 0x0000ff) >> 0;

    var theR1 = (color2 & 0xff0000) >> 16;
    var theG1 = (color2 & 0x00ff00) >> 8;
    var theB1 = (color2 & 0x0000ff) >> 0;

    return generateColor(interval, theR0, theG0, theB0, theR1, theG1, theB1);
}

function interpolate(pBegin, pEnd, pStep, pMax) {
    if (pBegin < pEnd) {
        return ((pEnd - pBegin) * (pStep / pMax)) + pBegin;
    } else {
        return ((pBegin - pEnd) * (1 - (pStep / pMax))) + pEnd;
    }
}

function generateColor(interval, theR0, theG0, theB0, theR1, theG1, theB1) {
    var theR;
    var theG;
    var theB;
    var theVal;
    var color = [];

    for (i = 0; i < interval; i++) {
        theR = interpolate(theR0, theR1, i, interval);
        theG = interpolate(theG0, theG1, i, interval);
        theB = interpolate(theB0, theB1, i, interval);

        theVal = (((theR << 8) | theG) << 8) | theB;
        color[i] = theVal;
    }
    return color;
}

function cancelThematicDialog() {
    $('#thematiccontent').dialog('destroy');
}

function removeRow() {
    $("#cbValues").find("tr:gt(0)").remove();
}

function modifySLDPolyDOM(sld) {
    var fillColor;
    var bFlag = false;
    var doc;
    var parser = new DOMImplementation();
    var domDoc = parser.loadXML(sld);
    
    var docRoot = domDoc.getDocumentElement();
    var o = docRoot.selectNodeSet("//sld:PolygonSymbolizer");
    var strokeNode;
    
    //Find the Fill element and insert GraphicFill
    for (var k = 0; k < o.length; k++) { /*Symbolizer length*/
        for (var i = 0; i <= o.item(k).childNodes.length; i++) { /*Symbolizer child length (Fill and stroke)*/
            var node = o.item(k).getChildNodes(i);
            //for (var j = 0; j < node._nodes.length; j++) {
            for (var j = node._nodes.length - 1; j>=0; j--) {
                if (node.item(j).nodeName == "sld:Fill") {
                    if (node.item(j).getFirstChild().nodeName == "sld:CssParameter") {
                        fillColor = (node.item(j).getFirstChild().getFirstChild().getNodeValue()).toString();
                        o.item(k).removeChild(o.item(k).getFirstChild());
                        bFlag = true;
                        
                    }
                }else{
                	if(node.item(j).nodeName == "sld:Stroke"){
                		strokeNode = node.item(j);
                		o.item(k).removeChild(node.item(j));
                	}
                }
            }
            if (bFlag == true) {
                bFlag = false;
                //parentNode = o.item(k).getParentNode();
                break;
            }
        }
        insertPolyExtGraphicFillFragment(fillColor, o.item(k), domDoc, strokeNode);
        //Remove Child Nodes of Polygon Symbolizer
        /*if (parentNode != null) {
        parentNode.removeChild(o.item(k));
        }*/

        //Get image
        /*$.ajax({
            url: "Bitmap/Convert?url=" + polygonExtImgUrl + "&color=" + fillColor.substring(1),
            async: false,
            success: function (data) {
                polygonExtImgUrl = data;
                insertPolyExtGraphicFillFragment(polygonExtImgUrl, polygonExtOutlineUrl, o.item(k), domDoc);
            }
        });*/
    }
    var s = domDoc.toString();
   // s = s.replace(/xlink=/g, 'xmlns:xlink=');
    return s;
}

function modifySLDMarkerDOM(sld) {
    var markerColor;
    var doc;
    var parser = new DOMImplementation();
    var domDoc = parser.loadXML(sld);
    var imgUrl = "";

    var docRoot = domDoc.getDocumentElement();
    var o = docRoot.selectNodeSet("//sld:PointSymbolizer");

    for (var k = 0; k < o.length; k++) { /*Symbolizer length*/
        //var nodeFill = docRoot.selectNodeSet("//sld:PointSymbolizer[" + indx + "]/sld:Graphic/sld:Mark/sld:Fill");
        var nodeFill = o.item(k).getFirstChild().getFirstChild().getFirstChild();
        if (nodeFill.getFirstChild().nodeName == "sld:CssParameter") {
            markerColor = (nodeFill.getFirstChild().getFirstChild().getNodeValue()).toString();
           
            var graphicNode = o.item(k).getChildNodes(0);
            var sizeNode = graphicNode.item(0).getLastChild();
            var size = (sizeNode.getFirstChild().getNodeValue()).toString();

            /*Remove the Grpahic node*/
            o.item(k).removeChild(o.item(k).getFirstChild());

            //Get image
           /* $.ajax({
                url: "Bitmap/Convert?url=" + pointExtImgUrl + "&color=" + markerColor.substring(1),
                async: false,
                success: function (data) {
                    imgUrl = data;
                    insertMarkerExtGraphicFragment(imgUrl, o.item(k), domDoc, size);
                }
            });*/
            insertMarkerExtGraphicFragment(markerColor, o.item(k), domDoc, size);
        }
    }
    var s = domDoc.toString();
    //s = s.replace(/xlink=/g, 'xmlns:xlink=');
    return s;
}

function modifySLDLineDOM(sld) {
    var lineColor;
    var doc;
    var parser = new DOMImplementation();
    var domDoc = parser.loadXML(sld);
    var imgUrl = "";
    var size;

    var docRoot = domDoc.getDocumentElement();
    var o = docRoot.selectNodeSet("//sld:LineSymbolizer");

    for (var k = 0; k < o.length; k++) { /*Symbolizer length*/
        var stroke = o.item(k).getFirstChild();

        for (var i = 0; i < stroke.childNodes.length; i++) {
            if (stroke.getChildNodes(0).item(i).nodeName == "sld:CssParameter" && 
                    (stroke.getChildNodes(0).item(i).getAttributes().item(0).getNodeValue()).toString() == "stroke") {
                lineColor = (stroke.getChildNodes(0).item(i).getFirstChild().getNodeValue()).toString();
            }
            if (stroke.getChildNodes(0).item(i).nodeName == "sld:CssParameter" &&
                    (stroke.getChildNodes(0).item(i).getAttributes().item(0).getNodeValue()).toString() == "stroke-width") {
                size = (stroke.getChildNodes(0).item(i).getFirstChild().getNodeValue()).toString();
            }
        }
        insertLineExtGraphicFragment(lineColor, o.item(k), domDoc, size);
        //Remove the Stroke Node
        o.item(k).removeChild(stroke);

        //Get image
        /*$.ajax({
            url: "Bitmap/Convert?url=" + lineExtImgUrl + "&color=" + lineColor.substring(1),
            async: false,
            success: function (data) {
                imgUrl = data;
                insertLineExtGraphicFragment(imgUrl, o.item(k), domDoc, size);
            }
        });*/
    }
    var s = domDoc.toString();
    //s = s.replace(/xlink=/g, 'xmlns:xlink=');
    return s;

}

function insertPolyExtGraphicFillFragment(fillColor, node, domDoc, strokeNode) {
    var polygonGraphic = null;

    polygonGraphic = "<sld:Fill xmlns:sld=\"http://www.opengis.net/sld\">"
        + "<sld:GraphicFill>"
			+ "<sld:Graphic>"
				+ "<sld:Mark>"
				  + "<sld:WellKnownName>"+selectedPatternId+"</sld:WellKnownName>"
				  + "<sld:Stroke>"
				  	+ "<sld:CssParameter name=\"stroke\">" + fillColor + "</sld:CssParameter>"
				  	+ "<sld:CssParameter name=\"stroke-width\">1</sld:CssParameter>"
				  +	"</sld:Stroke>"
				+ "</sld:Mark>"
			+ "</sld:Graphic>"
     + "</sld:GraphicFill>"
     + "</sld:Fill>";

    var parser = new DOMImplementation();
    var doc = parser.loadXML(polygonGraphic);
    var docRoot = doc.getDocumentElement();

    var nodeToImport = node.importNode(docRoot, true);
    node.appendChild(nodeToImport);
    
    var strokeNodeImport = node.importNode(strokeNode, true);
    node.appendChild(strokeNodeImport);
}

function insertMarkerExtGraphicFragment(markerColor, node, domDoc, size) {
    var polygonGraphic = null;

    var markerGraphic = "<sld:Graphic xmlns:sld=\"http://www.opengis.net/sld\">"
				+ "<sld:Mark>"
					+ "<sld:WellKnownName>" + selectedPatternId + "</sld:WellKnownName>"
					+ "<sld:Fill>"
						+ "<sld:CssParameter name=\"fill\">" + markerColor + "</sld:CssParameter>" 
					+ "</sld:Fill>"
				+ "</sld:Mark>"
				+ "<sld:Size>" + size +"</sld:Size>"
				+ "</sld:Graphic>";

    var parser = new DOMImplementation();
    var doc = parser.loadXML(markerGraphic);
    var docRoot = doc.getDocumentElement();

    var nodeToImport = node.importNode(docRoot, true);
    node.appendChild(nodeToImport);
}

function insertLineExtGraphicFragment(lineColor, node, domDoc, size) {
    var polygonGraphic = null;
  
    lineGraphic = "<sld:Stroke xmlns:sld=\"http://www.opengis.net/sld\">"
            + "<sld:GraphicStroke>"
				+ "<sld:Graphic>"
					+ "<sld:Mark>"
						+ "<sld:WellKnownName>" + selectedLinePatternId + "</sld:WellKnownName>"
						+ "<sld:Stroke>"
							+ "<sld:CssParameter name=\"stroke\">" + lineColor + "</sld:CssParameter>"
							+ "<sld:CssParameter name=\"stroke-width\">" + size + "</sld:CssParameter>"
						+ "</sld:Stroke>"
					+ "</sld:Mark>"
					+ "<sld:Size>12</sld:Size>"
				+ "</sld:Graphic>"
            + "</sld:GraphicStroke>"
         + "</sld:Stroke>";

    var parser = new DOMImplementation();
    var doc = parser.loadXML(lineGraphic);
    var docRoot = doc.getDocumentElement();

    var nodeToImport = node.importNode(docRoot, true);
    node.appendChild(nodeToImport);
}

function validateNumericInput(field) {
    var re = /^[0-9]*$/;
    if (!re.test(field.value)) {
        jAlert('Value must be all numeric charcters');
        field.value = field.value.replace(/[^0-9]/g, "");
    }
}

