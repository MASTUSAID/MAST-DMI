var infoMarker;
var lon;
var lat;
var style = new OpenLayers.Style();
var infoLayer;
var gid;
var spatialType;
var spatialType;
var activeProject = null;

var onClick = function (e) {
    OpenLayers.Element.addClass(map.viewPortDiv, "olCursorWait");
    lonLat = map.getLonLatFromPixel(e.xy);

    lon = lonLat.lon.toFixed(2);
    lat = lonLat.lat.toFixed(2);

    var visible_layers = new Array();

    for (var vislyr  in layerMap) {
        if (vislyr != 'Cosmetic')
            if (map.getLayersByName(vislyr)[0].visibility && map.getLayersByName(vislyr)[0].queryable) {
                visible_layers.push(layerMap[vislyr]);
            }
    }

    this.protocol.read({
        params: {
            REQUEST: "GetFeatureInfo",
            EXCEPTIONS: "application/vnd.ogc.se_xml",
            VERSION: "1.1.1",
            BBOX: map.getExtent().toBBOX(),
            X: Math.round(e.xy.x),
            Y: Math.round(e.xy.y),
            INFO_FORMAT: 'application/vnd.ogc.gml',
            LAYERS: visible_layers, //layerMap[OpenLayers.Map.activelayer.name],
            QUERY_LAYERS: visible_layers, //layerMap[OpenLayers.Map.activelayer.name],
            WIDTH: map.size.w,
            HEIGHT: map.size.h,
            SRS: map.getProjection(),
            FEATURE_COUNT: 10
        },
        callback: onResponse
    });
};

function onPopupClose(evt) {
    this.hide();
    OpenLayers.Event.stop(evt);
}

/*
 * This method apply sld on selected layer feature
 * by creating clone
 */
function applySymbol(layerName, featureId) {
    var symbol;
    var _layer = map.getLayersByName(layerName)[0];

    var _layerType = getGeomType(_layer);

    var highlightSymbolizer;

    if (_layerType == 'Point') {
        highlightSymbolizer = {
            Point: {
                graphicName: 'circle',
                fill: false,
                strokeWidth: 2,
                strokeColor: '#07FCFB',
                pointRadius: 5
            }
        };
    } else if (_layerType == 'Polygon') {
        highlightSymbolizer = {
            Polygon: {
                strokeColor: '#07FCFB',
                fill: true,
                fillColor: "#FFFFFF",
                strokeWidth: 2,
                fillOpacity: 0.1
            }
        };
    } else if (_layerType == 'LineString') {
        highlightSymbolizer = {
            Line: {
                strokeColor: '#07FCFB',
                strokeWidth: 2
            }
        };
    } else {
        highlightSymbolizer = {
            Polygon: {
                strokeColor: '#07FCFB',
                fill: true,
                fillColor: "#FFFFFF",
                strokeWidth: 2,
                fillOpacity: 0.1
            }
        };
    }

    var _gid = featureId; //split and convert to integer
    var pos = _gid.indexOf(".");
    if (pos >= -1) {
        _gid = parseInt(_gid.substring(++pos));
    }

    if (layerName == 'spatial_unit')
    {
        console.log(activeProject);
        var rule = new OpenLayers.Rule({
            title: "default",
            filter: new OpenLayers.Filter.Logical({
                type: OpenLayers.Filter.Logical.AND,
                filters: [new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "usin",
                        value: _gid
                    }),
                    new OpenLayers.Filter.Comparison({
                        type: OpenLayers.Filter.Comparison.EQUAL_TO,
                        property: "project_name",
                        value: activeProject
                    })
                ]
            }),
            symbolizer: highlightSymbolizer
        });
    } else {

        var rule = new OpenLayers.Rule({
            title: "default",
            filter: new OpenLayers.Filter.Comparison({
                type: OpenLayers.Filter.Comparison.EQUAL_TO,
                property: "ID",
                value: _gid
            }),
            symbolizer: highlightSymbolizer
        });
    }
    var sld_rules = [];
    var sld = {
        version: "1.0.0",
        namedLayers: {}
    };

    var actualLyrName = layerName;
    if (layerName == 'spatial_unit')
        actualLyrName = layerMap[layerName];
    sld.namedLayers[actualLyrName] = {
        name: actualLyrName,
        userStyles: []
    };

    sld_rules.push(rule);
    sld.namedLayers[actualLyrName].userStyles.push({
        rules: sld_rules
    });

    //Remove the clone layer if it exists
    var clonedLayer = map.getLayersByName("clone")[0];
    if (clonedLayer != undefined) {
        map.removeLayer(clonedLayer);
    }

    sld_body = new OpenLayers.Format.SLD().write(sld);
    applySldOnFeature(sld_body, _layer, clonedLayer);
}

function applySldOnFeature(sld, layer, clonedLayer) {
    //Post the SLD
    $.ajax({
        type: 'POST',
        url: "theme/createSLD",
        dataType: "text",
        data: {data: escape(sld)},
        success: function (result) {
            sld_result = result;

            var layerOptions = null;
            layerOptions = OpenLayers.Util.applyDefaults(layerOptions, {
                displayInLayerSwitcher: false,
                tileOptions: {
                    maxGetUrlLength: 2048
                }
            });

            var clonedLayer = layer.clone();
            clonedLayer.setName("clone");
            map.addLayers([clonedLayer]);
            clonedLayer.mergeNewParams({
                SLD: sld_result
            });
            clonedLayer.redraw(true);
        },
        error: function (xhr, status) {
            jAlert('Sorry, there is a problem!');
        }
    });
}

function getOrederdFeatureList(featuredate, fetureorderList) {
    var tempFeature = {};
    var fieldName = null;
    var featurelist = fetureorderList.split(',');
    for (var i = 0; i < featurelist.length; i++)
    {
        fieldName = featurelist[i];
        for (var j = 0; j < featuredate.length; j++)
        {
            var dispField = featuredate[j];
            if (dispField.field == fieldName) {
                tempFeature[i] = dispField;
                break;
            }
        }
    }
    return tempFeature;
}

var onResponse = function (response) {
    if (response.features.length > 0) {
        var features = response.features;
        for (var int = 0; int < features.length; int++) {
            if (features[int].gml.featureType == 'spatial_unit' && features[int].attributes.project_name != activeProject)
                features.splice(int, 1);
        }

        if (features.length > 0) {
            var fid = features[0].fid.substr(features[0].fid.indexOf('.') + 1);
            var status = features[0].data.current_workflow_status_id;
            applySymbol('spatial_unit', fid);
            editAttributeNew(fid, status);
        }
    }
    OpenLayers.Element.removeClass(map.viewPortDiv, "olCursorWait");
};

function viewProjectName(project) {
    activeProject = project;
}