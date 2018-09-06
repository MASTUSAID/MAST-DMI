

var markupControls = null;
var wfs_markup_point;
var wfs_markup_line;
var wfs_markup_poly;
var markup_save_point_strategy;
var markup_save_line_strategy;
var markup_save_poly_strategy;
var url;
var projection;
var featureNS;
var geomFieldName;

var pointSymbology = [];
var lineSymbology = [];
var polySymbology = [];
var cosmeticlayers=[];
var cosmetic_sld_file=null;


function polySaveSuccess(event){
    wfs_markup_poly.removeAllFeatures();
    //var markup_lyr =  map.getLayersByName("Cosmetic")[0];
   // markup_lyr.redraw(true);
}

function polySaveFail(event) {
    alert('Error! Poly changes not saved');
}

function lineSaveSuccess(event){
    wfs_markup_line.removeAllFeatures();
   // var markup_lyr =  map.getLayersByName("Cosmetic")[0];
   // markup_lyr.redraw(true);
}

function lineSaveFail(event) {
    alert('Error! Line changes not saved');
}

var markupSketchSymbolizers = {
    "Point": {
        pointRadius: 4,
        graphicName: "square",
        fillColor: "#ffffff",
        fillOpacity: 1,
        strokeWidth: 1,
        strokeOpacity: 1,
        strokeColor: "#333333",
        strokeDashstyle: ""
    },
    "Line": {
        strokeWidth: 3,
        strokeOpacity: 1,
        strokeColor: "#666666",
        strokeLinecap: "square",
        strokeDashstyle: "dash"
    },
    "Polygon": {
        strokeWidth: 2,
        strokeOpacity: 1,
        strokeColor: "#CC9900",
        fillColor: "#CC9900",
        fillOpacity: 0.3,
        strokeLinecap: "square",
        strokeDashstyle: "solid"
    },
    "RegularPolygon": {
        strokeWidth: 2,
        strokeOpacity: 1,
        strokeColor: "#CC9900",
        fillColor: "#CC9900",
        fillOpacity: 0.3,
		strokeLinecap: "square",
        strokeDashstyle: "solid"
    },
	"Text": {
        fontNames: "Arial",
        fontStyle: "Normal",
        fontWeight: "Normal",
        fontColor: "#000000",
        fontSize: 10
    }
};

var markupGenericSketchSymbolisers = {
        strokeDashstyle: ""
};
var markupTextSketchSymbolisers = {    
	    label: "$(label)",
	    labelXOffset: 10,   //  positive value moves the label to the right
	    labelYOffset: 0   //  negative value moves the label down
	};


Cloudburst.Markup = function (map, _searchdiv) {

    this.map = map;
    searchdiv = _searchdiv;
    
    
    
//    OpenLayers.Event.observe(document, "keydown", function (evt) {
//        var handled = false;
//        switch (evt.keyCode) {
//        case 90:
//            // z
//            if (evt.metaKey || evt.ctrlKey) {
//                draw.undo();
//                handled = true;
//            }
//            break;
//        case 89:
//            // y
//            if (evt.metaKey || evt.ctrlKey) {
//                draw.redo();
//                handled = true;
//            }
//            break;
//        case 27:
//            // esc
//            draw.cancel();
//            handled = true;
//            break;
//        }
//        if (handled) {
//            OpenLayers.Event.stop(evt);
//        }
//    });
	
    
    
    
    var coslayers = map.getLayersByName("Cosmetic")[0];
    cosmeticlayers = coslayers.params.LAYERS.split(",");
    url = coslayers.url;
    projection = coslayers.projection;

    // removeChildElement(_parentDiv,excludedDiv)
    
    //removeChildElement("sidebar", "layerSwitcherContent");

    //$("#layerSwitcherContent").hide();
    
    

    var markupStylePoint = new OpenLayers.Style();
    var markupStyleMapPoint = new OpenLayers.StyleMap({
        "default": markupStylePoint
    });

    var markupStyleLine = new OpenLayers.Style();
    var markupStyleMapLine = new OpenLayers.StyleMap({
        "default": markupStyleLine
    });

    var markupStylePolygon = new OpenLayers.Style();
    markupStyleMapPolygon = new OpenLayers.StyleMap({
        "default": markupStylePolygon
    });

    // ************* Add Markup layer ******************************//
    // Point
    markup_save_point_strategy = new OpenLayers.Strategy.Save();
    markup_save_point_strategy.events.register('success', null, saveSuccess);
    markup_save_point_strategy.events.register('fail', null, saveFail);

    //var markup_point_url = replaceString("http://cp947sw:9999/geoserver/wms?", /wms/gi, 'wfs');
    var markup_point_url = replaceString(url, /wms/gi, 'wfs');
    var typeName = workspace + ":Cosmetic_Point";
    var markup_point_schema = markup_point_url + "request=DescribeFeatureType&version=1.1.0&service=WFS&typename=" + typeName;
    //var markup_point_schema = markup_point_url + "/DescribeFeatureType?typename=topp:tasmania_roads";
    
    $.ajax({
        url: PROXY_PATH + markup_point_schema,
        async: false,
        success: function (data) {
            var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
            var responseText = featureTypesParser.read(data);
            var featureTypes = responseText.featureTypes;
            featureNS = responseText.targetNamespace;
            featPrefix = responseText.targetPrefix;
            
            for (var i = 0; i < featureTypes[0].properties.length; ++i) {
                if (featureTypes[0].properties[i].type.indexOf("gml")>=0) {
                	geomFieldName = featureTypes[0].properties[i].name;
                	break;
				}
			}
        }
    });

    var _projection = new OpenLayers.Projection(projection.projCode);
    var proto_wfs_markup_point = new OpenLayers.Protocol.WFS({
    	//headers: { Authorization : "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE="},
    	version: "1.1.0",
        srsName: projection.projCode, //"EPSG:27700",
        url: markup_point_url,
        featureType: "Cosmetic_Point",
        geometryName: geomFieldName,
        featurePrefix: featPrefix,
        featureNS: featureNS, //"rega",
        schema: markup_point_schema
    });
    
    wfs_markup_point = new OpenLayers.Layer.Vector("Markup_Point", {
        reportError: true,
        projection: _projection, //"EPSG:27700",
        strategies: [markup_save_point_strategy],
        protocol: proto_wfs_markup_point,
        isBaseLayer: false,
        visibility: true,
        styleMap: markupStyleMapPoint,
        displayInLayerSwitcher: false
    });
    map.addLayers([wfs_markup_point]);

    // line
    wfs_markup_line = new OpenLayers.Layer.Vector("Markup_Line", {
        reportError: true,
        projection: _projection, //"EPSG:27700",
        isBaseLayer: false,
        visibility: true,
        styleMap: markupStyleMapLine,
        displayInLayerSwitcher: false
    });
    map.addLayers([wfs_markup_line]);

    // poly
    markup_save_poly_strategy = new OpenLayers.Strategy.Save();
    markup_save_poly_strategy.events.register('success', null, polySaveSuccess);
    markup_save_poly_strategy.events.register('fail', null, polySaveFail);

    var typePoly = workspace + ":Cosmetic_Poly";
    var markup_poly_url = replaceString(url, /wms/gi, 'wfs');
    var markup_poly_schema = markup_poly_url + "request=DescribeFeatureType&version=1.1.0&service=WFS&typename=" + typePoly;
    
    var proto_wfs_markup_poly = new OpenLayers.Protocol.WFS({
    	//headers: { Authorization : "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE="},
        version: "1.1.0",
        srsName: projection.projCode, //"EPSG:27700",
        url: markup_poly_url,
        featureType: "Cosmetic_Poly",
        geometryName: geomFieldName,  /*Assuming poly has same geometry field name as point*/
        featurePrefix: featPrefix,
        featureNS: featureNS,
        schema: markup_poly_schema
    });
    wfs_markup_poly = new OpenLayers.Layer.Vector("Markup_Poly", {
        reportError: true,
        projection: _projection, //"EPSG:27700",
        strategies: [markup_save_poly_strategy],
        protocol: proto_wfs_markup_poly,
        isBaseLayer: false,
        visibility: true,
        styleMap: markupStyleMapPolygon,
        displayInLayerSwitcher: false
    });
    map.addLayers([wfs_markup_poly]);
    
    // Line
    markup_save_line_strategy = new OpenLayers.Strategy.Save();
    markup_save_line_strategy.events.register('success', null, lineSaveSuccess);
    markup_save_line_strategy.events.register('fail', null, lineSaveFail);

    var markup_line_url = replaceString(url, /wms/gi, 'wfs');
    var type_line = workspace + ":Cosmetic_Line";
    var markup_line_schema = markup_line_url + "request=DescribeFeatureType&version=1.1.0&service=WFS&typename=" + type_line;
    
    var proto_wfs_markup_line = new OpenLayers.Protocol.WFS({
    	//headers: { Authorization : "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE="},
        version: "1.1.0",
        srsName: projection.projCode, //"EPSG:27700",
        url: markup_line_url,
        featureType: "Cosmetic_Line",
        geometryName: geomFieldName,   /*Assuming line has same geometry field name as point*/
        featurePrefix: featPrefix,
        featureNS: featureNS, //"rega",
        schema: markup_line_schema
    });
        wfs_markup_line = new OpenLayers.Layer.Vector("Markup_Line", {
        reportError: true,
        projection: _projection, //"EPSG:27700",
        strategies: [markup_save_line_strategy],
        protocol: proto_wfs_markup_line,
        isBaseLayer: false,
        visibility: true,
        styleMap: markupStyleMapLine,
        displayInLayerSwitcher: false
    });
    map.addLayers([wfs_markup_line]);

    var attributes = {
        uid: "",
        username:""
    };
    
    wfs_markup_point.events.on({
        "featureselected": function(e) {
        	wfs_markup_point.removeFeatures([e.feature]);
        }
    });

    wfs_markup_line.events.on({
        "featureselected": function(e) {
        	wfs_markup_line.removeFeatures([e.feature]);
        }
    });

    wfs_markup_poly.events.on({
        "featureselected": function(e) {
        	wfs_markup_poly.removeFeatures([e.feature]);
        }
    });
    
    markupControls = {
    	deletemarkup:new OpenLayers.Control.SelectFeature(
            [wfs_markup_point,wfs_markup_line,wfs_markup_poly],
            {
                clickout: true,
                toggle: false,
                multiple: false,
                hover: false,
                toggleKey: "ctrlKey", // ctrl key removes from selection
                multipleKey: "shiftKey" // shift key adds to selection
            }
        ),
        point: new OpenLayers.Control.DrawFeature(
        wfs_markup_point, OpenLayers.Handler.Point, {
        	displayClass:"olControlDefault",
            callbacks: {
                done: function (p) {
                    var pointFeature = new OpenLayers.Feature.Vector(p, attributes);
                    pointFeature.state = OpenLayers.State.INSERT;
                    pointFeature.attributes["uid"] = pointFeature.id;
                    pointFeature.attributes["username"] = user;
                    // add point style
                    fltr = new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "uid",
                        value: pointFeature.id
                    });
                    
                    
                    symbology = {
                    		Point:{
		                        pointRadius: markupSketchSymbolizers.Point.pointRadius,
		                        graphicName: markupSketchSymbolizers.Point.graphicName,
		                        fillColor: markupSketchSymbolizers.Point.fillColor,
		                        fillOpacity: markupSketchSymbolizers.Point.fillOpacity,
		                        strokeWidth: markupSketchSymbolizers.Point.strokeWidth,
		                        strokeColor: markupSketchSymbolizers.Point.strokeColor,
		                        strokeOpacity: markupSketchSymbolizers.Point.strokeOpacity,
		                        strokeDashstyle: markupSketchSymbolizers.strokeDashstyle
                    		}
                    };
                    markupStyleMapPoint.styles['default'].addRules([new OpenLayers.Rule({
                       filter:fltr,
                        symbolizer: symbology.Point
                    })])
                    var rule = createRule(pointFeature.id, fltr, symbology);
                    pointSymbology.push(rule);
                    wfs_markup_point.addFeatures([pointFeature]);
                }
            }
        }),
        line: new OpenLayers.Control.DrawFeature(
        wfs_markup_line, OpenLayers.Handler.Path, {
        	displayClass:"olControlDefault",
            callbacks: {
                done: function (p) {
                	var multiLine = new OpenLayers.Geometry.MultiLineString([p]);
                    var lineFeature = new OpenLayers.Feature.Vector(multiLine, attributes);
                    //var lineFeature = new OpenLayers.Feature.Vector(p, attributes);
                    lineFeature.state = OpenLayers.State.INSERT;
                    lineFeature.attributes["uid"] = lineFeature.id;
                    lineFeature.attributes["username"] = user;
                    fltr = new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "uid",
                        value: lineFeature.id
                    });
                    symbology = {
                    		Line:{
                            strokeWidth: markupSketchSymbolizers.Line.strokeWidth,
                            strokeColor: markupSketchSymbolizers.Line.strokeColor,
                            strokeLinecap: markupSketchSymbolizers.Line.strokeLinecap,
                            strokeOpacity: markupSketchSymbolizers.Line.strokeOpacity,
                            strokeDashstyle: markupSketchSymbolizers.Line.strokeDashstyle
                    		}
                        };
                    
                    markupStyleMapLine.styles['default'].addRules([new OpenLayers.Rule({
                    	 filter: fltr,
                        symbolizer: symbology.Line
                    })])
                    var rule = createRule(lineFeature.id, fltr, symbology);
                    lineSymbology.push(rule);
                    wfs_markup_line.addFeatures([lineFeature]);
                }
            }
        }),
        polygon: new OpenLayers.Control.DrawFeature(
        wfs_markup_poly, OpenLayers.Handler.Polygon, {
        	displayClass:"olControlDefault",
            callbacks: {
                done: function (p) {
                	
                	var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
                    var polyFeature = new OpenLayers.Feature.Vector(multipolygon, attributes);
                    polyFeature.state = OpenLayers.State.INSERT;
                    polyFeature.attributes["uid"] = polyFeature.id;
                    polyFeature.attributes["username"] = user;
                    //polyFeature.attributes["fid"] = polyFeature.id;
                    fltr = new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "uid",
                        value: polyFeature.id
                    });
                    symbology = {
                    		Polygon:{
                            strokeWidth: markupSketchSymbolizers.Polygon.strokeWidth,
                            strokeOpacity: markupSketchSymbolizers.Polygon.strokeOpacity,
                            strokeColor: markupSketchSymbolizers.Polygon.strokeColor,
                            fillColor: markupSketchSymbolizers.Polygon.fillColor,
                            fillOpacity: markupSketchSymbolizers.Polygon.fillOpacity,
                            strokeLinecap: markupSketchSymbolizers.Polygon.strokeLinecap,
                            strokeDashstyle: markupSketchSymbolizers.Polygon.strokeDashstyle
                    		}
                        };
                    markupStyleMapPolygon.styles['default'].addRules([new OpenLayers.Rule({
                        filter: fltr,
                        symbolizer: symbology.Polygon
                    })])
                    var rule = createRule(polyFeature.id, fltr, symbology);
                    polySymbology.push(rule);
                    wfs_markup_poly.addFeatures([polyFeature]);
                }
            }
        }),
		
        circle: new OpenLayers.Control.DrawFeature(
        wfs_markup_poly, OpenLayers.Handler.RegularPolygon, {
        	displayClass:"olControlDefault",
            handlerOptions: {
                sides: 40
            },
            callbacks: {
                done: function (p) {
                	var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
                    var circleFeature = new OpenLayers.Feature.Vector(multipolygon, attributes);
                    circleFeature.state = OpenLayers.State.INSERT;
                    circleFeature.attributes["uid"] = circleFeature.id;
                    circleFeature.attributes["username"] = user;
                    fltr = new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "uid",
                        value: circleFeature.id
                    });
                    symbology = {
                    		Polygon:{
	                            strokeWidth: markupSketchSymbolizers.RegularPolygon.strokeWidth,
	                            strokeOpacity: markupSketchSymbolizers.RegularPolygon.strokeOpacity,
	                            strokeColor: markupSketchSymbolizers.RegularPolygon.strokeColor,
	                            fillColor: markupSketchSymbolizers.RegularPolygon.fillColor,
	                            fillOpacity: markupSketchSymbolizers.RegularPolygon.fillOpacity,
	                            strokeDashstyle: markupSketchSymbolizers.RegularPolygon.strokeDashstyle,
								strokeLinecap: markupSketchSymbolizers.RegularPolygon.strokeLinecap
                    		}
                        };
                    markupStyleMapPolygon.styles['default'].addRules([new OpenLayers.Rule({
                        filter: fltr,
                        symbolizer: symbology.Polygon
                    })])
                    var rule = createRule(circleFeature.id, fltr, symbology);
                    polySymbology.push(rule);
                    wfs_markup_poly.addFeatures([circleFeature]);
                }
            }
        }),
        rectangle: new OpenLayers.Control.DrawFeature(
        wfs_markup_poly, OpenLayers.Handler.RegularPolygon, {
        	displayClass:"olControlDefault",
            handlerOptions: {
                sides: 4,
				irregular:true
            },
            callbacks: {
                done: function (p) {
                	var multipolygon = new OpenLayers.Geometry.MultiPolygon([p]);
                    var rectangleFeature = new OpenLayers.Feature.Vector(multipolygon, attributes);
//                    var rectangleFeature = new OpenLayers.Feature.Vector(
//                    p, attributes);
                    rectangleFeature.state = OpenLayers.State.INSERT;
                    rectangleFeature.attributes["uid"] = rectangleFeature.id;
                    rectangleFeature.attributes["username"] = user;
                    fltr = new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "uid",
                        value: rectangleFeature.id
                    });
                    symbology = {
                    		Polygon:{
	                            strokeWidth: markupSketchSymbolizers.RegularPolygon.strokeWidth,
	                            strokeOpacity: markupSketchSymbolizers.RegularPolygon.strokeOpacity,
	                            strokeColor: markupSketchSymbolizers.RegularPolygon.strokeColor,
	                            fillColor: markupSketchSymbolizers.RegularPolygon.fillColor,
	                            fillOpacity: markupSketchSymbolizers.RegularPolygon.fillOpacity,
	                            strokeDashstyle: markupSketchSymbolizers.strokeDashstyle,
								strokeDashstyle: markupSketchSymbolizers.RegularPolygon.strokeDashstyle,
								strokeLinecap: markupSketchSymbolizers.RegularPolygon.strokeLinecap
                    		}
                    	};
                    markupStyleMapPolygon.styles['default'].addRules([new OpenLayers.Rule({
                        filter: fltr,
                        symbolizer: symbology.Polygon
                    })])
                    var rule = createRule(rectangleFeature.id, fltr, symbology);
                    polySymbology.push(rule);
                    wfs_markup_poly.addFeatures([rectangleFeature]);
                }
            }
        }),
        text: new OpenLayers.Control.DrawFeature(
        		wfs_markup_point, OpenLayers.Handler.Point, { 
        			displayClass:"olControlDefault",
        			callbacks: {
                    done: function (p) {
                        pointGeometry = p;
                        var textFeature = new OpenLayers.Feature.Vector(pointGeometry, attributes);
						textFeature.attributes["uid"] = textFeature.id;
                        textFeature.attributes["username"] = user;
                        var latLon = new OpenLayers.LonLat(p.x, p.y);
                        var pixel = map.getPixelFromLonLat(latLon);
                        var left = pixel.x;//+ $("#map").position().left;
                        var top = pixel.y;//+ $("#map").position().top;
                        $("#MarkupTextTooltip").css("left", left + 10);
                        $("#MarkupTextTooltip").css("top", top + 20);
                        $("#MarkupTextTooltip").val('');
                        $("#MarkupTextTooltip").show();
                    }
                }
           })
    };
    
    
    //**************** handle esc key to cancel markup drawing ***************/
    var esc_hndl_poly = new OpenLayers.Handler.Keyboard(markupControls['polygon'], {
            keydown: handleKeypress
    });
    esc_hndl_poly.activate();
    
	var esc_hndl_line = new OpenLayers.Handler.Keyboard(markupControls['line'], {
        keydown: handleKeypress
	});
	esc_hndl_line.activate();
	
	var esc_hndl_text = new OpenLayers.Handler.Keyboard(markupControls['text'], {
        keydown: handleKeypress
	});
	esc_hndl_text.activate();

    //********************************************************/
    
    

    $('#MarkupTextTooltip').keyup(function (e) {
        var code = (e.keyCode ? e.keyCode : e.which);
        
		if (code == 13) {            
			var labelText = $('#MarkupTextTooltip').val();
            var pointFeature = new OpenLayers.Feature.Vector(pointGeometry, attributes);
            pointFeature.state = OpenLayers.State.INSERT;
            pointFeature.attributes = {
                label: labelText,                        
                uid: pointFeature.id,
                username: user
            };
            markupTextSketchSymbolisers.label = labelText;
            fltr = new OpenLayers.Filter.Comparison({
                type: OpenLayers.Filter.Comparison.EQUAL_TO,
                property: "uid",
                value: pointFeature.id
            });
            symbology = {
            		Text:{
            			pointRadius: "20",
                        label: markupTextSketchSymbolisers.label,
                        fillOpacity: 0,
                        strokeOpacity: 0,
                        labelXOffset: markupTextSketchSymbolisers.labelXOffset,
                        labelYOffset: markupTextSketchSymbolisers.labelYOffset,
                        labelAlign: "cc", 
                        fontColor: markupSketchSymbolizers.Text.fontColor, 
                        fontOpacity: 0.7, 
						fillColor: markupSketchSymbolizers.Text.fontColor,
						fillOpacity: 0.7,
                        fontFamily: markupSketchSymbolizers.Text.fontNames,  
                        fontSize: markupSketchSymbolizers.Text.fontSize,
                        fontWeight: markupSketchSymbolizers.Text.fontWeight
                        	
            		}
            };
            symbology.Text.fillOpacity = 0.0;
            markupStyleMapPoint.styles['default'].addRules([new OpenLayers.Rule({
            	filter: fltr,
                symbolizer: symbology.Text                   
            })]);
            var rule = createRule(pointFeature.id, fltr, symbology);
            pointSymbology.push(rule);
            wfs_markup_point.addFeatures([pointFeature]);
            symbology.Text.fillOpacity = 0.7;
            var rule = createRule(pointFeature.id, fltr, symbology);
            $("#MarkupTextTooltip").hide();
        }
    });
    
    
    for (var key in markupControls) {
        map.addControl(markupControls[key]);

    }
    
    $("#tabs-Tool").empty();
    
    jQuery.get('resources/templates/viewer/markup.html', function (template) {
        
    	//$("#" + searchdiv).append(template);
    	
    	//Add tag
		addTab($._('markup'),template);
		
		$("#markup-help").tipTip({defaultPosition:"right"});
        
        toggleButtons();

        $("#markupButtons button").bind("click", function (e) {

            loadDefaultMarkupStyle();
			
			// alert(e.currentTarget.id)
        	var _selectedTool = null;
            var tool = e.currentTarget.id;
      
            if(tool == "deleteall"){
            	wfs_markup_point.removeAllFeatures();
            	wfs_markup_line.removeAllFeatures();
            	wfs_markup_poly.removeAllFeatures();
            	
            	toggleMarkupControl(tool);
            	$("#markupContentBody").empty();
            	return false;
            }
			
			if(tool == "newmarkup"){												
				
				newmarkup();
				return false;				
			}
			
            
            var activeMarkupTool = tool;
            	
            _selectedTool = tool;
            
           /* if (tool == 'text') {
            	_selectedTool = 'point';
            }else */
            	
            if(tool == 'circle'){
            	_selectedTool = 'polygon';
            }else if(tool == 'rectangle'){
            	_selectedTool = 'polygon';
            }else if(tool == 'save'){
            	//Save all the features added as markup
            	markup_save_point_strategy.save();
            	markup_save_line_strategy.save();
            	markup_save_poly_strategy.save();
            	
            	var sld = create_SLD();
            	//Post the SLD
                $.ajax({
                    type: 'POST',
                    url: "theme/createMarkupSLD?" + token,
                    dataType: "text",
                    data: { data: escape(sld) },
                    success: function (result) {
                        sld_result = result;
                        cosmetic_sld_file=result;
                        var layerOptions = null;
                        layerOptions = OpenLayers.Util.applyDefaults(layerOptions, {
                            displayInLayerSwitcher: false,
                            tileOptions: {
                                maxGetUrlLength: 2048
                            }
                        });

                        var layer = map.getLayersByName("Cosmetic")[0];
                        layer.mergeNewParams({
                            //SLD: sld_result
                        });
                        layer.redraw(true);
                       
                        //Clear the array
                        lineSymbology.length = 0;
                        pointSymbology.length = 0;
                        polySymbology.length = 0;
                    },
                    error: function (xhr, status) {
                        jAlert('Sorry, there is a problem!');
                    }
                });
            	return;
            }
            // markupRectangleMode();
            toggleMarkupControl(tool);
            $("#markupContentBody").empty();

            if(_selectedTool == 'deletemarkup')return;
            $("#markupContentBody").load('resources/templates/viewer/' + _selectedTool + 'markupstyle.html', function () {

                
            if (tool == 'polygon' || tool == 'circle' || tool == 'rectangle') {
            		translatePolyMarkupformLabels();
                    $('#fill_color_poly').val('CC9900');
                    $("#fill_color_poly").css("background-color", "#CC9900");
                    $('#stroke_color_poly').val('CC9900');
                    $("#stroke_color_poly").css("background-color", "#CC9900");

                    if (activeMarkupTool == 'polygon' || activeMarkupTool == 'polygone_hole') {
                        $('#fill_Opacity_poly').val(
                        markupSketchSymbolizers.Polygon.fillOpacity);
                        $('#stroke_opacity_poly').val(
                        markupSketchSymbolizers.Polygon.strokeOpacity);
                        $('#stroke_width_poly').val(
                        markupSketchSymbolizers.Polygon.strokeWidth);
                        $('#stroke_linecap_poly').val(
                        markupSketchSymbolizers.Polygon.strokeLinecap);
                        $('#stroke_dashstyle_poly').val(
                        markupSketchSymbolizers.Polygon.strokeDashstyle);
                    } else {
                        $('#fill_Opacity_poly').val(
                        markupSketchSymbolizers.RegularPolygon.fillOpacity);
                        $('#stroke_opacity_poly').val(
                        markupSketchSymbolizers.RegularPolygon.strokeOpacity);
                        $('#stroke_width_poly').val(
                        markupSketchSymbolizers.RegularPolygon.strokeWidth);
                        $('#stroke_dashstyle_poly').val(
                        markupSketchSymbolizers.RegularPolygon.strokeDashstyle);
                    }

                    $("#fill_Opacity_poly").spinner({
                        min: 0,
                        max: 1,
                        step: 0.1
                    });
                    $("#stroke_opacity_poly").spinner({
                        min: 0,
                        max: 1,
                        step: 0.1
                    });
                    $("#stroke_width_poly").spinner({
                        min: 1,
                        max: 10
                    });

                    $('#fill_color_poly').ColorPicker({
                        onSubmit: function (
                        hsb, hex, rgb, el) {

                            $(
                            el).val(
                            hex);
                            $(
                            el).ColorPickerHide();
                        },
                        onBeforeShow: function () {
                            $(
                            this).ColorPickerSetColor(
                            this.value);
                        },
                        onChange: function (
                        hsb, hex, rgb) {
                            $('#fill_color_poly').val(
                            hex);
                            $("#fill_color_poly").css("background-color", "#" + hex);
							
							if (tool == 'polygon'){							
								markupSketchSymbolizers.Polygon.fillColor = "#" + $('#fill_color_poly').val();
							}
							
							if (tool == 'circle' || tool == 'rectangle'){
								markupSketchSymbolizers.RegularPolygon.fillColor="#" + $('#fill_color_poly').val();
							}

                        }
                    }).bind('keyup', function () {
                        $(
                        this).ColorPickerSetColor(
                        this.value);
                    });
                    
					$('#stroke_color_poly').ColorPicker({
                        onSubmit: function (
                        hsb, hex, rgb, el) {

                            $(
                            el).val(
                            hex);
                            $(
                            el).ColorPickerHide();
                        },
                        onBeforeShow: function () {
                            $(
                            this).ColorPickerSetColor(
                            this.value);
                        },
                        onChange: function (
                        hsb, hex, rgb) {
                            $('#stroke_color_poly').val(
                            hex);
                            $("#stroke_color_poly").css("background-color", "#" + hex);
							
							if (tool == 'polygon'){							
								markupSketchSymbolizers.Polygon.strokeColor = "#" + $('#stroke_color_poly').val();
							}
							
							if (tool == 'circle' || tool == 'rectangle'){
								markupSketchSymbolizers.RegularPolygon.strokeColor = "#" + $('#stroke_color_poly').val();
							}
								
							
                        }
                    }).bind('keyup', function () {
                        $(
                        this).ColorPickerSetColor(
                        this.value);
                    });
					
					if (tool == 'polygon'){	
					
						$('#fill_Opacity_poly').spinner().change(function () {
							markupSketchSymbolizers.Polygon.fillOpacity = $('#fill_Opacity_poly').val();
						});
						
						$('#stroke_opacity_poly').spinner().change(function () {
							markupSketchSymbolizers.Polygon.strokeOpacity = $('#stroke_opacity_poly').val();
						});
						
						$('#stroke_width_poly').spinner().change(function () {
							markupSketchSymbolizers.Polygon.strokeWidth = $('#stroke_width_poly').val();
						});
						
						
						$('#stroke_linecap_poly').change(function () {
							markupSketchSymbolizers.Polygon.strokeLinecap = $('#stroke_linecap_poly').val();
						});
						
						$('#stroke_dashstyle_poly').change(function () {
							markupSketchSymbolizers.Polygon.strokeDashstyle = $('#stroke_dashstyle_poly').val();
						});
					}
					else if (tool == 'circle' || tool == 'rectangle'){
					
						$('#fill_Opacity_poly').spinner().change(function () {
							markupSketchSymbolizers.RegularPolygon.fillOpacity = $('#fill_Opacity_poly').val();
						});
						
						$('#stroke_opacity_poly').spinner().change(function () {
							markupSketchSymbolizers.RegularPolygon.strokeOpacity = $('#stroke_opacity_poly').val();
						});
						
						$('#stroke_width_poly').spinner().change(function () {
							markupSketchSymbolizers.RegularPolygon.strokeWidth = $('#stroke_width_poly').val();
						});
						
						
						$('#stroke_linecap_poly').change(function () {
							markupSketchSymbolizers.RegularPolygon.strokeLinecap = $('#stroke_linecap_poly').val();
						});
						
						$('#stroke_dashstyle_poly').change(function () {
							markupSketchSymbolizers.RegularPolygon.strokeDashstyle = $('#stroke_dashstyle_poly').val();
						});
					
					}
					
            } else if (tool == 'line') {
            		translateLineMarkupformLabels();
                    $('#stroke_color_line').val('333333');
                    $("#stroke_color_line").css("background-color", "#333333");

                    $('#stroke_opacity_line').val(
                    markupSketchSymbolizers.Line.strokeOpacity);
                    $('#stroke_width_line').val(
                    markupSketchSymbolizers.Line.strokeWidth);
                    $('#stroke_linecap_line').val(
                    markupSketchSymbolizers.Line.strokeLinecap);
                    $('#stroke_dashstyle_line').val(
                    markupSketchSymbolizers.Line.strokeDashstyle);

                    $("#stroke_opacity_line").spinner({
                        min: 0,
                        max: 1,
                        step: 0.1
                    });
                    $("#stroke_width_line").spinner({
                        min: 1,
                        max: 10
                    });
                    $('#stroke_color_line').ColorPicker({
                        onSubmit: function (
                        hsb, hex, rgb, el) {

                            $(
                            el).val(
                            hex);
                            $(
                            el).ColorPickerHide();
                        },
                        onBeforeShow: function () {
                            $(
                            this).ColorPickerSetColor(
                            this.value);
                        },
                        onChange: function (
                        hsb, hex, rgb) {
                            $('#stroke_color_line').val(
                            hex);
                            $("#stroke_color_line").css("background-color", "#" + hex);
							markupSketchSymbolizers.Line.strokeColor = "#" + $('#stroke_color_line').val();
                        }
                    }).bind('keyup', function () {
                        $(
                        this).ColorPickerSetColor(
                        this.value);
                    });
					
					$('#stroke_opacity_line').spinner().change(function () {
						markupSketchSymbolizers.Line.strokeOpacity = $('#stroke_opacity_line').val();
					});
					
					$('#stroke_width_line').spinner().change(function () {
						markupSketchSymbolizers.Line.strokeWidth = $('#stroke_width_line').val();
					});
					
					$('#stroke_linecap_line').change(function () {
						markupSketchSymbolizers.Line.strokeLinecap = $('#stroke_linecap_line').val();
					});
					
					$('#stroke_dashstyle_line').change(function () {
						markupSketchSymbolizers.Line.strokeDashstyle = $('#stroke_dashstyle_line').val();
					});
					
					
            } else if (tool == 'point') {
            		translatePointMarkupformLabels();
                    $("#fill_color_point").val('ffffff');
                    $("#fill_color_point").css("background-color", "#ffffff");

                    $('#stroke_color_point').val('333333');
                    $("#stroke_color_point").css("background-color", "#333333");

                    $('#fill_Opacity_point').val(
                    markupSketchSymbolizers.Polygon.fillOpacity);
                    $('#stroke_opacity_point').val(
                    markupSketchSymbolizers.Polygon.strokeOpacity);

                    $('#point_radius_point').val(
                    markupSketchSymbolizers.Point.pointRadius);

                    $('#stroke_width_point').val(
                    markupSketchSymbolizers.Polygon.strokeWidth);
                    $('#stroke_linecap_point').val(
                    markupSketchSymbolizers.Polygon.strokeLinecap);

                    // $('#stroke_dashstyle_point').val(markupSketchSymbolizers.Polygon.strokeDashstyle);
                    $("#fill_Opacity_point").spinner({
                        min: 0,
                        max: 1,
                        step: 0.1
                    });
                    $("#stroke_opacity_point").spinner({
                        min: 0,
                        max: 1,
                        step: 0.1
                    });
                    $("#stroke_width_point").spinner({
                        min: 1,
                        max: 10
                    });
                    $('#point_radius_point').spinner({
                        min: 1,
                        max: 100
                    });

                    $('#fill_color_point').ColorPicker({
                        onSubmit: function (
                        hsb, hex, rgb, el) {

                            $(
                            el).val(
                            hex);
                            $(
                            el).ColorPickerHide();
                        },
                        onBeforeShow: function () {
                            $(
                            this).ColorPickerSetColor(
                            this.value);
                        },
                        onChange: function (
                        hsb, hex, rgb) {
                            $('#fill_color_point').val(
                            hex);
                            $("#fill_color_point").css("background-color", "#" + hex);
							
							//Point onchange function
							markupSketchSymbolizers.Point.fillColor = "#" + $('#fill_color_point').val();
							
                        }
                    }).bind('keyup', function () {
                        $(
                        this).ColorPickerSetColor(
                        this.value);
                    });
                    $('#stroke_color_point').ColorPicker({
                        onSubmit: function (
                        hsb, hex, rgb, el) {

                            $(
                            el).val(
                            hex);
                            $(
                            el).ColorPickerHide();
                        },
                        onBeforeShow: function () {
                            $(
                            this).ColorPickerSetColor(
                            this.value);
                        },
                        onChange: function (
                        hsb, hex, rgb) {
                            $("#stroke_color_point").val(
                            hex);
                            $("#stroke_color_point").css("background-color", "#" + hex);
							//Point onchange function
							markupSketchSymbolizers.Point.strokeColor = "#" + $('#stroke_color_point').val();
                        }
                    }).bind('keyup', function () {
                        $(
                        this).ColorPickerSetColor(
                        this.value);
                    });
					
				///////////Point onchange function/////////////////////	
		
				$('#fill_Opacity_point').spinner().change(function () {
					markupSketchSymbolizers.Point.fillOpacity = $('#fill_Opacity_point').val();
				});
				
				$('#point_radius_point').spinner().change(function () {
					markupSketchSymbolizers.Point.pointRadius = $('#point_radius_point').val();
				});
				
				$('#stroke_opacity_point').spinner().change(function () {
					markupSketchSymbolizers.Point.strokeOpacity = $('#stroke_opacity_point').val();
				});
				
				$('#stroke_width_point').spinner().change(function () {
					markupSketchSymbolizers.Point.strokeWidth = $('#stroke_width_point').val();
				});
				$('#stroke_linecap_point').change(function () {
					markupSketchSymbolizers.Point.graphicName = $('#stroke_linecap_point').val();
				});
				
///////////////////////  
					
					
            }
			
			else if (tool == 'text') {
		
				translateTextMarkupformLabels();
				$("#text_font_color").val(markupSketchSymbolizers.Text.fontColor);
				$("#text_font_color").css("background-color", markupSketchSymbolizers.Text.fontColor);
				$('#text_font_size').val(markupSketchSymbolizers.Text.fontSize);
				$('#text_font_weight').val(markupSketchSymbolizers.Text.fontWeight);
				$('#text_font_style').val(markupSketchSymbolizers.Text.fontStyle);
				
			
				$('#text_font_size').spinner({
							min: 1,
							max: 100
				});
				
				$('#text_font_color').ColorPicker({
							onSubmit: function (
							hsb, hex, rgb, el) {

								$(
								el).val(
								hex);
								$(
								el).ColorPickerHide();
							},
							onBeforeShow: function () {
								$(
								this).ColorPickerSetColor(
								this.value);
							},
							onChange: function (
							hsb, hex, rgb) {
								$('#text_font_color').val(
								hex);
								$("#text_font_color").css("background-color", "#" + hex);
								
								//Text onchange function
							
							markupSketchSymbolizers.Text.fontColor = "#" + $('#text_font_color').val();
								
							}
						}).bind('keyup', function () {
							$(
							this).ColorPickerSetColor(
							this.value);
						});
				
				
					$('#text_font_size').spinner().change(function () {
						markupSketchSymbolizers.Text.fontSize = $('#text_font_size').val();
					});
					
					$('#text_font_weight').change(function () {
						markupSketchSymbolizers.Text.fontWeight = $('#text_font_weight').val();
					});
					
					$('#text_font_style').change(function () {
						markupSketchSymbolizers.Text.fontStyle = $('#text_font_style').val();
					});
					
					$('#text_font_names').change(function () {
						markupSketchSymbolizers.Text.fontNames = $('#text_font_names').val();
					});
					
				
				$.ajax({
            
					url: "font/getfonts/" + "?" + token,
					success: function (data) {

						 $.each(data, function (key, val) {
							$('#text_font_names').append($("<option></option>").attr("value", val).text(val));
						});
						$("#text_font_names").val(markupSketchSymbolizers.Text.fontNames);
					}
				});
				
				
			
			}
			
			
			
            $(".colorpicker").css("z-index", 100000);

               
            });
			
		
			
        });

        $('#point').click();
        
        translateMarkupToolbarStrings();
    });
};

function translateMarkupToolbarStrings(){
	$('#newmarkup').attr("title",$._('new_markup'));
	$('#save').attr("title",$._('Save'));
	$('#deletemarkup').attr("title",$._('delete_markup'));
	$('#deleteall').attr("title",$._('delete_all_markup'));
	$('#point').attr("title",$._('point'));
	$('#line').attr("title",$._('line'));
	$('#polygon').attr("title",$._('polygon'));
	$('#circle').attr("title",$._('circle'));
	$('#rectangle').attr("title",$._('rectangle'));
	$('#text').attr("title",$._('text'));
}

function translatePointMarkupformLabels(){
	$('#lbl_markup_fill').html($._('fill'));
	$('#lbl_markup_fillcolor').html($._('fill_color'));
	$('#lbl_markup_fillopacity').html($._('fill_opacity'));
	$('#lbl_markup_pointradius').html($._('point_radius'));
	$('#lbl_markup_stroke').html($._('stroke'));
	$('#lbl_markup_strokecolor').html($._('stroke_color'));
	$('#lbl_markup_strokeopacity').html($._('stroke_opacity'));
	$('#lbl_markup_strokewidth').html($._('stroke_width'));
	$('#lbl_markup_strokelinecap').html($._('stroke_linecap'));
	
	$('#stroke_linecap_point').append($("<option></option>").attr("value", "square").text($._('square')));
	$('#stroke_linecap_point').append($("<option></option>").attr("value", "star").text($._('star')));
	$('#stroke_linecap_point').append($("<option></option>").attr("value", "x").text("x"));
	$('#stroke_linecap_point').append($("<option></option>").attr("value", "cross").text($._('cross')));
	$('#stroke_linecap_point').append($("<option></option>").attr("value", "triangle").text($._('triangle')));
}

function translateLineMarkupformLabels(){
	$('#lbl_linemarkup_stroke').html($._('stroke'));
	$('#lbl_linemarkup_strokecolor').html($._('stroke_color'));
	$('#lbl_linemarkup_strokeopacity').html($._('stroke_opacity'));
	$('#lbl_linemarkup_strokewidth').html($._('stroke_width'));
	$('#lbl_linemarkup_strokelinecap').html($._('stroke_linecap'));
	
	$('#stroke_linecap_line').append($("<option></option>").attr("value", "square").text($._('square')));
	$('#stroke_linecap_line').append($("<option></option>").attr("value", "round").text($._('round')));
	$('#stroke_linecap_line').append($("<option></option>").attr("value", "butt").text($._('butt')));
}

function translatePolyMarkupformLabels(){
	$('#lbl_polymarkup_fill').html($._('fill'));
	$('#lbl_polymarkup_fillcolor').html($._('fill_color'));
	$('#lbl_polymarkup_fillopacity').html($._('fill_opacity'));
	$('#lbl_polymarkup_pointradius').html($._('point_radius'));
	$('#lbl_polymarkup_stroke').html($._('stroke'));
	$('#lbl_polymarkup_strokecolor').html($._('stroke_color'));
	$('#lbl_polymarkup_strokeopacity').html($._('stroke_opacity'));
	$('#lbl_polymarkup_strokewidth').html($._('stroke_width'));
	$('#lbl_polymarkup_strokelinecap').html($._('stroke_linecap'));
	$('#stroke_linecap_poly').append($("<option></option>").attr("value", "square").text($._('square')));
	$('#stroke_linecap_poly').append($("<option></option>").attr("value", "round").text($._('round')));
	$('#stroke_linecap_poly').append($("<option></option>").attr("value", "butt").text($._('butt')));
}

function translateTextMarkupformLabels(){
	$('#lbl_textmarkup_font').html($._('font'));
	$('#lbl_textmarkup_fontstyle').html($._('font-style'));
	$('#lbl_textmarkup_fontweight').html($._('font-weight'));
	$('#lbl_textmarkup_color').html($._('color'));
	$('#lbl_textmarkup_size').html($._('size'));
}

function applyMarkupStyle(markup) {
    // alert(markup);
    if (markup == 'polygon') {
        var fillColor_poly = $('#fill_color_poly').val();
        var fillOpacity_poly = $('#fill_Opacity_poly').val();
        var strokeColor_poly = $('#stroke_color_poly').val();
        var strokeOpacity_poly = $('#stroke_opacity_poly').val();
        var strokeWidth_poly = $('#stroke_width_poly').val();
        var strokeLinecap_poly = $('#stroke_linecap_poly').val();
        var strokeDashstyle_poly = $('#stroke_dashstyle_poly').val();

        markupSketchSymbolizers.Polygon.fillColor = "#" + fillColor_poly;
        markupSketchSymbolizers.Polygon.fillOpacity = fillOpacity_poly;
        markupSketchSymbolizers.Polygon.strokeColor = "#" + strokeColor_poly;
        markupSketchSymbolizers.Polygon.strokeOpacity = strokeOpacity_poly;
        markupSketchSymbolizers.Polygon.strokeWidth = strokeWidth_poly;
        markupSketchSymbolizers.Polygon.strokeLinecap = strokeLinecap_poly;
        markupSketchSymbolizers.Polygon.strokeDashstyle = strokeDashstyle_poly;

        markupSketchSymbolizers.RegularPolygon.fillColor = "#" + fillColor_poly;
        markupSketchSymbolizers.RegularPolygon.fillOpacity = fillOpacity_poly;
        markupSketchSymbolizers.RegularPolygon.strokeColor = "#" + strokeColor_poly;
        markupSketchSymbolizers.RegularPolygon.strokeOpacity = strokeOpacity_poly;
        markupSketchSymbolizers.RegularPolygon.strokeWidth = strokeWidth_poly;
		markupSketchSymbolizers.RegularPolygon.strokeLinecap = strokeLinecap_poly;
        markupSketchSymbolizers.RegularPolygon.strokeDashstyle = strokeDashstyle_poly;
    } else if (markup == 'line') {

        var strokeColor_line = $('#stroke_color_line').val();
        var strokeOpacity_line = $('#stroke_opacity_line').val();
        var strokeWidth_line = $('#stroke_width_line').val();
        var strokeLinecap_line = $('#stroke_linecap_line').val();
        var strokeDashstyle_line = $('#stroke_dashstyle_line').val();

        markupSketchSymbolizers.Line.strokeColor = "#" + strokeColor_line;
        markupSketchSymbolizers.Line.strokeOpacity = strokeOpacity_line;
        markupSketchSymbolizers.Line.strokeWidth = strokeWidth_line;
        markupSketchSymbolizers.Line.strokeLinecap = strokeLinecap_line;
        markupSketchSymbolizers.Line.strokeDashstyle = strokeDashstyle_line;
    } else if (markup == 'point') {
        var fillColor_point = $('#fill_color_point').val();
        var fillOpacity_point = $('#fill_Opacity_point').val();
        var strokeColor_point = $('#stroke_color_point').val();
        var strokeOpacity_point = $('#stroke_opacity_point').val();
        var strokeWidth_point = $('#stroke_width_point').val();
        var strokeLinecap_point = $('#stroke_linecap_point').val();
        var pointRadius_point = $('#point_radius_point').val();
        // var strokeDashstyle_point = $('#stroke_dashstyle_point').val();
        // var graphicName_point = $('#stroke_linecap_point').val();
        markupSketchSymbolizers.Point.fillColor = "#" + fillColor_point;
        markupSketchSymbolizers.Point.fillOpacity = fillOpacity_point;
        markupSketchSymbolizers.Point.strokeColor = "#" + strokeColor_point;
        markupSketchSymbolizers.Point.strokeOpacity = strokeOpacity_point;
        markupSketchSymbolizers.Point.strokeWidth = strokeWidth_point;
        markupSketchSymbolizers.Point.graphicName = strokeLinecap_point;
        markupSketchSymbolizers.Point.pointRadius = pointRadius_point;
        // markupSketchSymbolizers.Polygon.strokeDashstyle =
        // strokeDashstyle_point;
    }
}

function onMarkupFeatureSelection(feat) {
    if (confirm("Are you want to delete the selected feature?")) {
        var featuresArrPoly = cswfs_polygon.features;
        if (featuresArrPoly.length > 0) {
            for (var i = 0; i < featuresArrPoly.length; i++) {
                if (featuresArrPoly[i].attributes.uid == feat.attributes.uid) {
                    cswfs_polygon.removeFeatures([featuresArrPoly[i]]);
                    break;
                }
            }
        }
        var featuresArrLine = cswfs_line.features;
        if (featuresArrLine.length > 0) {
            for (var i = 0; i < featuresArrLine.length; i++) {
                if (featuresArrLine[i].attributes.uid == feat.attributes.uid) {
                    cswfs_line.removeFeatures([featuresArrLine[i]]);
                    break;
                }
            }
        }
        var featuresArrPoint = cswfs_point.features;
        if (featuresArrPoint.length > 0) {
            for (var i = 0; i < featuresArrPoint.length; i++) {
                if (featuresArrPoint[i].attributes.uid == feat.attributes.uid) {
                    cswfs_point.removeFeatures([featuresArrPoint[i]]);
                    break;
                }
            }
        }
    }
}

function toggleMarkupControl(element) {
    
	/*Deactivate Map Controls*/
	for (key1 in mapControls) {
		var control = mapControls[key1];
		control.deactivate();
	}
	/*Deactivate Edit Controls*/
	for (editKey in editControls) {
		var control = editControls[editKey];
		control.deactivate();
	}
	
	/* Deactive search bound controls*/
	for (boundCtrlKey in boundControls) {
		var ctrl = boundControls[boundCtrlKey];
		ctrl.deactivate();
	}
		
	for (key in markupControls) {
        var control = markupControls[key];
        if (element == key) {
            control.activate();

            var draw = markupControls[key];


        } else {
            control.deactivate();

        }
    }
}

function markupRectangleMode() {
    markupControls.rectangle.handler.irregular = true;
}

function saveSuccess(event) {
   // alert('Point changes saved');
    wfs_markup_point.removeAllFeatures();
   // var markup_lyr =  map.getLayersByName("Cosmetic")[0];
   // markup_lyr.redraw(true);
}

function saveFail(event) {
    alert('Error! Point changes not saved');
}

function createRule(uid, filter, symbolizer){
	object = new Object();
	object.uid = uid;
	object.filter = filter;
	object.symbolizer = symbolizer;
	
	return object;
}

function create_SLD(){
	//var rules = [];
    var sld = {
        version: "1.0.0",
        namedLayers: {}
    };
    
    //Point
      var layerName = workspace + ":Cosmetic_Point";
      sld.namedLayers[layerName] = {
		        name: layerName,
		        userStyles: []
		      };
	  if(pointSymbology.length > 0){
		var point_rules = [];
	    //Array for point rules
	    for(var i=0; i<pointSymbology.length; i++){
	    	var symbol = pointSymbology[i];
		    var rule = new OpenLayers.Rule({
	            title: "Point" + i,
	            symbolizer: symbol.symbolizer,
	            filter: symbol.filter
	            //maxScaleDenominator: 999
	        })
		    point_rules[i] = rule;
	    }
	    sld.namedLayers[layerName].userStyles.push({
	        rules: point_rules
	    });
    }else{
    	sld.namedLayers[layerName].userStyles.push({
	        rules: ""
	    });
    }
	  //Line
	  var layerName = workspace + ":Cosmetic_Line";
	  sld.namedLayers[layerName] = {
		        name: layerName,
		        userStyles: []
		      };
	  if(lineSymbology.length > 0){
		var line_rules = [];
	    //Array for Line rules
	    for(var i=0; i<lineSymbology.length; i++){
	    	var symbol = lineSymbology[i];
	    	var dashStyle = getStrokeDashStyle(symbol.symbolizer.Line.strokeDashstyle);
	    	if(dashStyle != ""){
	    		symbol.symbolizer.Line.strokeDashstyle = dashStyle;
	    	}
		    var rule = new OpenLayers.Rule({
	            title: "Line" + i,
	            symbolizer: symbol.symbolizer,
	            filter: symbol.filter
	            //maxScaleDenominator: 999
	        })
		    line_rules[i] = rule;
	    }
	    sld.namedLayers[layerName].userStyles.push({
	        rules: line_rules
	    });
    }else{
    	sld.namedLayers[layerName].userStyles.push({
	        rules: ""
	    });
    }
    
    //Poly
    var layerName = workspace + ":Cosmetic_Poly";  
    sld.namedLayers[layerName] = {
	        name: layerName,
	        userStyles: []
	    };
	 if(polySymbology.length > 0){
		 
		var poly_rules = [];
	    //Array for poly rules
	    for(var i=0; i<polySymbology.length; i++){
	    	var symbol = polySymbology[i];
	    	var dashStyle = getStrokeDashStyle(symbol.symbolizer.Polygon.strokeDashstyle);
	    	if(dashStyle != ""){
	    		symbol.symbolizer.Polygon.strokeDashstyle = dashStyle;
	    	}
		    var rule = new OpenLayers.Rule({
	            title: "Poly" + i,
	            symbolizer: symbol.symbolizer,
	            filter: symbol.filter
	            //maxScaleDenominator: 999
	        })
		    poly_rules[i] = rule;
	    }
	    sld.namedLayers[layerName].userStyles.push({
	        rules: poly_rules
	    });
    }else{
    	 sld.namedLayers[layerName].userStyles.push({
 	        rules: ""
 	    });
    }
    return new OpenLayers.Format.SLD().write(sld);
}

function getStrokeDashStyle(dashStyle){
	var returnValue;
	
	switch(dashStyle){
		case "dot":
			returnValue = "1 4";
			break;
		case "dash":
			returnValue = "4 4";
			break;
		case "dashdot":
			returnValue = "4 4 1 4";
			break;
		case "longdash":
			returnValue = "8 4";
			break;
		case "longdashdot":
			returnValue = "8 4 1 4";
			break;
		default:
			returnValue = "";
	}
	return returnValue;
}


var wfs_delete_cosmetic = '<wfs:Transaction service="WFS" version="1.0.0" '+
	  'xmlns:cdf="http://www.opengis.net/cite/data" '+
	  'xmlns:ogc="http://www.opengis.net/ogc" '+
	  'xmlns:wfs="http://www.opengis.net/wfs" '+
	  'xmlns:topp="http://www.openplans.org/topp">'+
	  '<wfs:Delete typeName="rega:Cosmetic_Point">'+
	    '<ogc:Filter>'+
	      '<ogc:And>'+
		      '<ogc:PropertyIsGreaterThan>'+
		          '<ogc:PropertyName>gid</ogc:PropertyName>'+
		          '<ogc:Literal>0</ogc:Literal>'+
		      '</ogc:PropertyIsGreaterThan>'+
		      '<ogc:PropertyIsEqualTo>'+
		        '<ogc:PropertyName>username</ogc:PropertyName>'+
		        '<ogc:Literal>' + user +'</ogc:Literal>'+
		      '</ogc:PropertyIsEqualTo>'+
	      '</ogc:And>'+
	    '</ogc:Filter>'+
	  '</wfs:Delete>'+
	  '<wfs:Delete typeName="rega:Cosmetic_Poly">'+
	      '<ogc:Filter>'+
	        '<ogc:PropertyIsGreaterThan>'+
	            '<ogc:PropertyName>gid</ogc:PropertyName>'+
	            '<ogc:Literal>0</ogc:Literal>'+
	        '</ogc:PropertyIsGreaterThan>'+
	        '<ogc:PropertyIsEqualTo>'+
	        '<ogc:PropertyName>username</ogc:PropertyName>'+
	        '<ogc:Literal>' + user +'</ogc:Literal>'+
	      '</ogc:PropertyIsEqualTo>'+
	      '</ogc:Filter>'+
	    '</wfs:Delete>'+
	    '<wfs:Delete typeName="rega:Cosmetic_Line">'+
	    '<ogc:Filter>'+
	      '<ogc:PropertyIsGreaterThan>'+
	          '<ogc:PropertyName>gid</ogc:PropertyName>'+
	          '<ogc:Literal>0</ogc:Literal>'+
	      '</ogc:PropertyIsGreaterThan>'+
	      '<ogc:PropertyIsEqualTo>'+
	        '<ogc:PropertyName>username</ogc:PropertyName>'+
	        '<ogc:Literal>' + user +'</ogc:Literal>'+
	      '</ogc:PropertyIsEqualTo>'+
	    '</ogc:Filter>'+
	  '</wfs:Delete>'+
	'</wfs:Transaction>';
	
	
function newmarkup(){	
		
	
	$.ajax({ 
		type: "POST",
		//url: STUDIO_URL + "attachment/layer/"+_layer.name+ "?" + token,
		url: PROXY_PATH+SpatialVue_Constants.Cosmetic_URL,
		data: wfs_delete_cosmetic,
		contentType: "text/xml",
		success: function () {		

            var layer = map.getLayersByName("Cosmetic")[0];           
            layer.redraw(true);
		}
		});	
}



function loadDefaultMarkupStyle(){
	//point
	markupSketchSymbolizers.Point.pointRadius=4;
	markupSketchSymbolizers.Point.graphicName="square";
	markupSketchSymbolizers.Point.fillColor="#ffffff";
	markupSketchSymbolizers.Point.fillOpacity=1;
	markupSketchSymbolizers.Point.strokeWidth=1;
	markupSketchSymbolizers.Point.strokeColor="#333333";
	markupSketchSymbolizers.Point.strokeOpacity=1;
	markupSketchSymbolizers.Point.strokeDashstyle="";
	//line
	markupSketchSymbolizers.Line.strokeWidth=3;
	markupSketchSymbolizers.Line.strokeColor="#666666";
	markupSketchSymbolizers.Line.strokeLinecap="square";
	markupSketchSymbolizers.Line.strokeOpacity=1;
	markupSketchSymbolizers.Line.strokeDashstyle="dash";
	//polygon
	markupSketchSymbolizers.Polygon.strokeWidth=2;
	markupSketchSymbolizers.Polygon.strokeOpacity=1;
	markupSketchSymbolizers.Polygon.strokeColor="#CC9900";
	markupSketchSymbolizers.Polygon.fillColor="#CC9900";
	markupSketchSymbolizers.Polygon.fillOpacity=0.3,
	markupSketchSymbolizers.Polygon.strokeLinecap="square";
	markupSketchSymbolizers.Polygon.strokeDashstyle="solid";
	//rectangle							
	markupSketchSymbolizers.RegularPolygon.strokeWidth=2;
	markupSketchSymbolizers.RegularPolygon.strokeOpacity=1;
	markupSketchSymbolizers.RegularPolygon.strokeColor="#CC9900";
	markupSketchSymbolizers.RegularPolygon.fillColor="#CC9900";
	markupSketchSymbolizers.RegularPolygon.fillOpacity=0.3
	markupSketchSymbolizers.RegularPolygon.strokeLinecap="square";
	markupSketchSymbolizers.RegularPolygon.strokeDashstyle="solid";
	//Text
	markupSketchSymbolizers.Text.fontNames="Arial";
	markupSketchSymbolizers.Text.fontStyle="Normal";
	markupSketchSymbolizers.Text.fontWeight="Normal";
	markupSketchSymbolizers.Text.fontColor="#000000",   
	markupSketchSymbolizers.Text.fontSize=10;

}



function handleKeypress(evt){
    var code = evt.keyCode;
    if(code == OpenLayers.Event.KEY_ESC){    	
        markupControls['polygon'].cancel();       
        markupControls['line'].cancel();
		markupControls['text'].cancel();
		$("#MarkupTextTooltip").hide();
    }
}