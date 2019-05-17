var Global = Global || {};
var project = null;
var baseLayers = [];
var cosmeticStatus = false;
var DisclaimerMsg = null;
var cookieProjectName = null;
var sldExists = false;
var minScale;
var maxScale;
var layerDispName = {};
var arr_Layers = [];
var displayInLayerMgr = {};
var projectName = null;
Global.PROJECT_ID = null;
Global.PROJECT_AREA = null;
Global.PROJECT = null;
var projectId = null; // for old version backward compatibility
var osm_map;
var Bing_Road;
var Bing_Aerial;
var MapQuest_OSM;
var map;
var _projectExtent;
var extent = ol.proj.transformExtent([35.739998, -7.900000999970367, 35.83000249996666, -7.82], "EPSG:4326", "EPSG:3857");
var bounds = [34.9655456095934, -8.57657546620732,
    35.9042312577367, -7.83167176245347];
var TYPE_BOUNDARY_POINTS = "Boundary_Points"
var L_BOUNDARY_POINTS = "Mast:" + TYPE_BOUNDARY_POINTS;
var TYPE_BOUNDARY = "Village_Boundary"
var L_BOUNDARY = "Mast:" + TYPE_BOUNDARY;
Cloudburst.loadMap = function (mapdiv, options, callback) {
    _projectExtent = "";
    windowResize();
    $('#_loader').hide();
    $('#maptips').hide();
    project = options.project;
    var projection = new ol.proj.Projection({
        code: 'EPSG:4326',
        units: 'degrees',
        axisOrientation: 'neu',
        global: true
    })


    $.ajax({
        url: STUDIO_URL + "project/" + project + "?" + token,
        async: true,
        success: function (data) {
            projectName = data.name;
            Global.PROJECT = data;
            Global.PROJECT_ID = data.projectnameid;
            Global.PROJECT_AREA = data.projectArea[0];
            projectId = data.projectnameid;
            _projectExtent = data.maxextent;
            DisclaimerMsg = (lang == 'en') ? data.disclaimer : $._('home_page_disclaimer_info');
            if (data.active) {
                cookieProjectName = data.name + '|' + user;
                if (DisclaimerMsg) {
                    if ($.cookie(cookieProjectName) == null) {
                        $('#DisclaimerDiv').css("visibility", "visible");
                        $("#hidProjectName").val(data.name);
                        $('#DisclaimerMsgDiv').html(DisclaimerMsg);
                        $("#DisclaimerDiv").dialog({
                            width: '520',
                            minHeight: '100',
                            resizable: false,
                            modal: true,
                            zIndex: '70000',
                            closeText: 'hide',
                            closeOnEscape: false,
                            open: function (event, ui) {
                                $(".ui-dialog-titlebar-close").hide();
                            }
                        });
                    }
                }

                var apiKey = "AqTGBsziZHIJYYxgivLBf0hVdrAk9mWO5cQcb8Yux8sW5M8c8opEC2lZqKR1ZZXf";
                //base layer buttons
                if (data.projectBaselayers.length > 0) {

                    for (_i = 0; _i < data.projectBaselayers.length; _i++) {

                        baseLayerName = data.projectBaselayers[_i].baselayers.baselayerEn;
                        if (baseLayerName == "Google_Streets") {
                            var Google_Streets = new ol.layer.Tile({
                                source: new ol.source.TileImage({
                                    url: 'http://mt1.google.com/vt/lyrs=m&x={x}&y={y}&z={z}',
                                    projection: "EPSG:3857",
                                    crossOrigin: 'null',
                                })
                            })
                            arr_Layers.push(Google_Streets);
                        }

                        if (baseLayerName == "Google_Physical") {

                            Google_Streets = new ol.layer.Tile({
                                source: new ol.source.TileImage({
                                    url: 'http://mt1.google.com/vt/lyrs=p&x={x}&y={y}&z={z}',
                                    projection: "EPSG:3857",
                                    crossOrigin: 'null',
                                })
                            })
                            arr_Layers.push(Google_Streets);
                        }

                        if (baseLayerName == "Google_Satellite") {

                            Google_Streets = new ol.layer.Tile({
                                source: new ol.source.TileImage({
                                    url: 'http://mt1.google.com/vt/lyrs=y&x={x}&y={y}&z={z}',
                                    projection: "EPSG:3857",
                                    crossOrigin: 'null',
                                })
                            })
                            arr_Layers.push(Google_Streets);
                        }

                        if (baseLayerName == "Google_Hybrid") {

                            Google_Streets = new ol.layer.Tile({
                                source: new ol.source.TileImage({
                                    url: 'http://mt1.google.com/vt/lyrs=p&x={x}&y={y}&z={z}',
                                    projection: "EPSG:3857",
                                    crossOrigin: 'null',
                                })
                            })
                            arr_Layers.push(Google_Streets);
                        }

                        if (baseLayerName == "Bing_Road") {

                            Bing_Road = new ol.layer.Tile({
                                title: 'Bing Maps road',
                                type: 'base',
                                visible: false,
                                source: new ol.source.BingMaps({
                                    imagerySet: 'Road',
                                    key: '123'
                                }),
                                crossOrigin: 'null',
                            })
                            arr_Layers.push(Bing_Road);
                        }

                        if (baseLayerName == "Bing_Aerial") {

                            Bing_Aerial = new ol.layer.Tile({
                                title: 'Bing Maps aerial',
                                type: 'base',
                                visible: false,
                                source: new ol.source.BingMaps({
                                    imagerySet: 'AerialWithLabels',
                                    key: '123'
                                }),
                                crossOrigin: 'null',
                            })
                            arr_Layers.push(Bing_Aerial);
                        }

                        if (baseLayerName == "Open_Street_Map") {

                            osm_map = new ol.layer.Tile({
                                source: new ol.source.OSM(),
                                crossOrigin: 'null',
                            }),
                                    arr_Layers.push(osm_map);
                        }

                        if (baseLayerName == "MapQuest_OSM") {

                            MapQuest_OSM = new ol.layer.Tile({
                                style: 'Aerial',
                                visible: false,
                                source: new ol.source.MapQuest({layer: 'sat'}),
                                crossOrigin: 'null',
                            }),
                                    arr_Layers.push(MapQuest_OSM);
                        }

                        break;
                    }

                    //******************* baselayer button ******************************************
                    $(".mapbl-button:not(.ui-state-disabled)")
                            .hover(
                                    function () {
                                        $(this).addClass("ui-state-hover-map");
                                    },
                                    function () {
                                        $(this).removeClass("ui-state-hover-map");
                                    }
                            )
                            .mousedown(function () {
                                $(this).parents('.mapbl-buttonset-single:first').find(".mapbl-button.ui-state-active-map").removeClass("ui-state-active-map");
                                if ($(this).is('.ui-state-active-map.mapbl-button-toggleable, .mapbl-buttonset-multi .ui-state-active-map')) {
                                    $(this).removeClass("ui-state-active-map");
                                } else {
                                    $(this).addClass("ui-state-active-map");
                                }
                            })
                            .mouseup(function () {
                                if (!$(this).is('.mapbl-button-toggleable_map, .mapbl-buttonset-single .mapbl-button,  .mapbl-buttonset-multi .mapbl-button')) {
                                    $(this).removeClass("ui-state-active-map");
                                }
                            });
                }


                /// base layer end 
                var highlightStyle = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#f00',
                        width: 2
                    })
                });
                var boundaryPointStyle = function (feature) {
                    if (feature.get("confidence_level") === 1) {
                        // Conflict
                        return new ol.style.Style({
                            image: new ol.style.RegularShape({
                                fill: new ol.style.Fill({color: '#FFFF00'}),
                                stroke: new ol.style.Stroke({
                                    color: 'red', width: 2
                                }),
                                points: 3,
                                radius: 7,
                                angle: 0
                            })
                        });
                    } else if (feature.get("confidence_level") === 2) {
                        // Medium conflict
                        return new ol.style.Style({
                            image: new ol.style.RegularShape({
                                fill: new ol.style.Fill({color: '#FFFF00'}),
                                stroke: new ol.style.Stroke({
                                    color: 'red', width: 2
                                }),
                                points: 4,
                                radius: 6,
                                angle: Math.PI / 4
                            })
                        });
                    }
                    // Default
                    return new ol.style.Style({
                        image: new ol.style.Circle({
                            radius: 5,
                            fill: new ol.style.Fill({color: '#FFFF00'}),
                            stroke: new ol.style.Stroke({
                                color: 'red', width: 2
                            })
                        })
                    });
                };

                var verifiedBoundaryPointStyle = function (feature) {
                    if (feature.get("confidence_level") === 1) {
                        // Conflict
                        return new ol.style.Style({
                            image: new ol.style.RegularShape({
                                fill: new ol.style.Fill({color: '#FFFFFF'}),
                                stroke: new ol.style.Stroke({
                                    color: 'red', width: 2
                                }),
                                points: 3,
                                radius: 7,
                                angle: 0
                            })
                        });
                    } else if (feature.get("confidence_level") === 2) {
                        // Medium conflict
                        return new ol.style.Style({
                            image: new ol.style.RegularShape({
                                fill: new ol.style.Fill({color: '#FFFFFF'}),
                                stroke: new ol.style.Stroke({
                                    color: 'red', width: 2
                                }),
                                points: 4,
                                radius: 6,
                                angle: Math.PI / 4
                            })
                        });
                    }
                    // Default
                    return new ol.style.Style({
                        image: new ol.style.Circle({
                            radius: 5,
                            fill: new ol.style.Fill({color: '#FFFFFF'}),
                            stroke: new ol.style.Stroke({
                                color: 'red', width: 2
                            })
                        })
                    });
                };

                var neighborBoundaryPointStyle = new ol.style.Style({
                    image: new ol.style.Circle({
                        radius: 5,
                        fill: new ol.style.Fill({color: '#FFFF00'}),
                        stroke: new ol.style.Stroke({
                            color: 'green', width: 2
                        })
                    })
                });
                var villageBorderStyle = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#ff6e00',
                        width: 3
                    })
                });
                var villageBorderStyles = [
                    new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: '#ff6e00',
                            width: 3
                        })
                    }),
                    new ol.style.Style({
                        image: new ol.style.Circle({
                            radius: 5,
                            fill: new ol.style.Fill({
                                color: 'red'
                            })
                        }),
                        geometry: function (feature) {
                            var coordinates = feature.getGeometry().getCoordinates()[0];
                            return new ol.geom.MultiPoint(coordinates);
                        }
                    })
                ];
                var neighborVillageBorderStyle = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#ffd800',
                        width: 2
                    })
                });
                var highlightStyle1 = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#FFFF00',
                        width: 2
                    })
                });
                var highlightStyle2 = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#3333FF',
                        width: 2
                    })
                });
                var highlightStyle3 = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#800080',
                        width: 2
                    })
                });
                // wms wfs load start
                for (var i = data.projectLayergroups.length - 1; i >= 0; i--) {
                    var lg = data.projectLayergroups[i].layergroupBean;
                    for (var j = lg.layerLayergroups.length - 1; j >= 0; j--) {
                        var lyr = lg.layerLayergroups[j].layers;
                        $.ajax({
                            url: STUDIO_URL + "layer/" + lyr.alias + "?" + token,
                            async: false,
                            success: function (data) {
                                layerMap[data.alias] = data.name;
                                layerDispName[data.alias] = data.displayname;
                                displayInLayerMgr[data.alias] = data.displayinlayermanager;
                                //******************* Load Layers ******************************************
                                if (data.layertype.description == 'WMS') {


                                    var _wms = new ol.layer.Tile({
                                        name: data.name,
                                        source: new ol.source.TileWMS({
                                            url: data.url,
                                            params: {
                                                'LAYERS': data.name,
                                                'TILED': true,
                                                'FORMAT': data.outputformat.documentformat,
                                            },
                                            serverType: 'geoserver',
                                            crossOrigin: 'null',
                                        })
                                    })


                                    _wms.set('aname', data.alias);
                                    _wms.set('url', data.url);
                                    _wms.set('selectable', false);
                                    arr_Layers.push(_wms);
                                } else if (data.layertype.description == 'Tilecache') {


                                } else if (data.layertype.description == 'WFS') {

                                    var _mapStyle;
                                    var cqlFilter = 'isactive=true';
                                    if (data.name === L_BOUNDARY_POINTS) {
                                        //cqlFilter += " and project_id=" + Global.PROJECT_ID;
                                    }
                                    var vectorSource = new ol.source.Vector({
                                        format: new ol.format.GeoJSON(),
                                        url: function (extent) {
                                            var url = data.url + "&service=WFS&version=1.1.0&request=GetFeature&typename=" + data.name + '&outputFormat=application/json' + '&CQL_FILTER={{CQLFILTER}}';
                                            return url.replace('{{CQLFILTER}}', cqlFilter);
                                        },
                                        crossOrigin: 'null',
                                        strategy: ol.loadingstrategy.bbox
                                    });
                                    var wms_vector = new ol.layer.Vector({
                                        name: data.name,
                                        style: styleFunction,
                                        source: vectorSource
                                    });
                                    wms_vector.set('aname', data.alias);
                                    wms_vector.set('url', data.url);
                                    wms_vector.set('selectable', true);
                                    arr_Layers.push(wms_vector);
                                }
                            }
                        });
                    }
                }

                // Add GFW layers
                var d = new Date();
                d.setMonth(d.getMonth() - 3);
                
                var gladLayer = new ol.layer.Tile({
                    name: "glad",
                    source: new ol.source.XYZ({
                        url: 'http://production-api.globalforestwatch.org/v1/true-color-tiles/glad/{z}/{x}/{y}?startDate=' + d.toISOString().split('T')[0],
                        opaque: false
                    })
                });
                gladLayer.set('aname', "glad");
                
                var forestLossLayer = new ol.layer.Tile({
                    name: "forestloss",
                    source: new ol.source.XYZ({
                        url: 'http://production-api.globalforestwatch.org/v1/true-color-tiles/loss/{z}/{x}/{y}?thresh=75&startYear=' + (d.getFullYear() - 1) + '&endYear=' + d.getFullYear(),
                        opaque: false
                    })
                });
                forestLossLayer.set('aname', "forestloss");
               
                arr_Layers.push(gladLayer);
                arr_Layers.push(forestLossLayer);
                
                function styleFunction(feature, resolution) {
                    if (feature.id_) {
                        if (feature.id_.split('.')[0] == "la_spatialunit_land") {

                            return highlightStyle;
                        }

                        if (feature.id_.split('.')[0] == "la_spatialunit_resource_land") {

                            return highlightStyle1;
                        }

                        if (feature.id_.split('.')[0] == "la_spatialunit_resource_line") {

                            return highlightStyle1;
                        }

                        if (feature.id_.split('.')[0] == "la_spatialunit_resource_point") {

                            return highlightStyle1;
                        }

                        if (feature.id_.split('.')[0] == "la_spatialunit_aoi") {
                            if (feature.values_.userid > 0)
                                return highlightStyle3;
                            else
                                return highlightStyle2;
                        }

                        if (feature.id_.split('.')[0].toLowerCase() == TYPE_BOUNDARY_POINTS.toLowerCase()) {
                            if (feature.values_.project_id === Global.PROJECT_ID) {
                                if (feature.values_.verified) {
                                    return verifiedBoundaryPointStyle(feature);
                                } else {
                                    return boundaryPointStyle(feature);
                                }
                            } else {
                                return neighborBoundaryPointStyle;
                            }
                        }

                        if (feature.id_.split('.')[0] == TYPE_BOUNDARY) {
                            if (feature.values_.project_id === Global.PROJECT_ID) {
                                return villageBorderStyles;
                            } else {
                                return neighborVillageBorderStyle;
                            }
                        }

                    } else {
                        return highlightStyle1;
                    }

                }

                // slider load start
                var target = $('#zoom-slider');
                target.slider({
                    orientation: 'horizontal',
                    value: 2,
                    min: 1,
                    max: 10,
                    step: 1,
                    animate: true,
                    stop: function () {

                    }
                });
                $(".colorpicker").css("z-index", 9999);
                $('#changeBgColor').ColorPicker({
                    onSubmit: function (
                            hsb, hex, rgb, el) {

                        //$( el).val( hex);
                        $(el).ColorPickerHide();
                    },
                    onBeforeShow: function () {
                        $(
                                this).ColorPickerSetColor(
                                this.value);
                    },
                    onChange: function (
                            hsb, hex, rgb) {

                        $("#map-tab").css("background-color", "#" + hex);
                        $("#changeBgColor").css("background-color", "#" + hex);
                    }
                }).bind('keyup', function () {
                    $(this).ColorPickerSetColor(this.value);
                });
            }

            map = new ol.Map({
                layers: arr_Layers,
                controls: [
                    //Define the default controls
                    new ol.control.Zoom(),
                    new ol.control.Rotate(),
                    new ol.control.Attribution(),
                    //Define some new controls
                    new ol.control.ZoomSlider(),
                    new ol.control.MousePosition(),
                    new ol.control.ScaleLine(),
                    new ol.control.OverviewMap()
                ],
                target: mapdiv,
                view: new ol.View({
                    center: [0, 0],
                    //zoom: 10,
                    projection: projection

                })

            });
            var array = _projectExtent.split(',');
            var myextent = ol.proj.transformExtent(
                    array,
                    "EPSG:4326", "EPSG:4326"
                    );
            map.getView().fit(myextent, map.getSize());
            maploaded(map); // this is method callback
        }
    });
};
var windowResize = function () {
    var windowHeight = $(window).height();
    var headerHeight = $("#header").height();
    var toolbarHeight = $("#toolbar").height();
    var adjustedWinHeight = windowHeight - (headerHeight + toolbarHeight);
    $("#map").height(adjustedWinHeight - 160);
    $("#sidebar").height(adjustedWinHeight - 180);
};
jQuery(document).ready(function () {
    $("#mainTabs").tabs();
    $.ajaxSetup({cache: false});
    $("#tab1").click(function (event) {
        $('#sidebar').show();
        $('#collapse').show();
    });
    $("#tab2").click(function (event) {
        var landRecords = new LandRecords("landRecords");
        hideMapComponents();
    });
    $("#tab3").click(function (event) {
        hideMapComponents();
    });
    $("#tab4").click(function (event) {
        hideMapComponents();
        $.ajax({
            url: "landrecordsdetails/getAllProjectsdetailsCall/",
            async: false,
            success: function (data) {
                $("#selectProjectsForSummary").empty();
                $("#selectProjectsForDetailSummary").empty();
                $("#selectProjectsForDetailSummaryForCommune").empty();
                $("#selectProjectsForAppStatusSummary").empty();
                $("#selectProjectsForAppTypeSummary").empty();
                $("#selectProjectsForWorkFlowSummary").empty();
                $("#selectProjectsForTenureTypesLandUnitsSummary").empty();
                $("#selectProjectsForLiberaFarmSummary").empty();
                $("#selectProjectsForSummary").append($("<option></option>").attr("value", "").text("Select Project"));
                $("#selectProjectsForDetailSummary").append($("<option></option>").attr("value", "").text("Select Project"));
                $("#selectProjectsForDetailSummaryForCommune").append($("<option></option>").attr("value", "").text("Select Project"));
                $("#selectProjectsForAppStatusSummary").append($("<option></option>").attr("value", "").text("Select Project"));
                $("#selectProjectsForAppTypeSummary").append($("<option></option>").attr("value", "").text("Select Project"));
                $("#selectProjectsForWorkFlowSummary").append($("<option></option>").attr("value", "").text("Select Project"));
                $("#selectProjectsForTenureTypesLandUnitsSummary").append($("<option></option>").attr("value", "").text("Select Project"));
                $("#selectProjectsForLiberaFarmSummary").append($("<option></option>").attr("value", "").text("Select Project"));
                $.each(data, function (i, projectName) {
                    $("#selectProjectsForSummary").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                    $("#selectProjectsForDetailSummary").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                    $("#selectProjectsForDetailSummaryForCommune").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                    $("#selectProjectsForAppStatusSummary").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                    $("#selectProjectsForAppTypeSummary").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                    $("#selectProjectsForWorkFlowSummary").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                    $("#selectProjectsForTenureTypesLandUnitsSummary").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                    $("#selectProjectsForLiberaFarmSummary").append($("<option></option>").attr("value", projectName.projectnameid).text(projectName.name));
                });
            }
        });
        $("#reportsAccordion").accordion();
        $("#selectProjects").val(activeProject);
    });
    $("#tab5").click(function (event) {
        var registrationRecords = new RegistrationRecords("registrationRecords");
        hideMapComponents();
    });
    $("#tab6").click(function (event) {
        var resourceRecords = new resource("resource");
        hideMapComponents();
    });
    // Load projects list
    $.ajax({
        url: "landrecords/allprojects/",
        async: false,
        success: function (data) {
            $("#selectProjects").empty();
            $("#selectProjects").append($("<option></option>").attr("value", "ALL").text("All villages"));
            $.each(data, function (i, projectName) {
                $("#selectProjects").append($("<option></option>").attr("value", projectName).text(projectName));
            });
        }
    });
    var landRecords = new LandRecords("landRecords");
});
function hideMapComponents() {
    $('#sidebar').hide();
    $('#collapse').hide();
    $('#bottomstatusbar').hide();
}