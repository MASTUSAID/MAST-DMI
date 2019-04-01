var width = 17.5;
var height = 26.2;
var headerSize = 1.8;
var boundaryWidth = 3;
var headerFontSize = 18;
var beaconSize = 4;
var beaconFontSize = 12;
var beaconHeight = 15;
var beaconBorderSize = 1;
var legendWidth = 217;
var legendFontSize = 10;
var scaleBarHeight = 10;
var stepLineWidth = 1;
var stepLineHeight = 7;
var coordsPanelHeight = 20;
var hSteps = 6;
var vSteps = 7;
var mapBorderSize = 2;
var northImage;
var map;
var layers = [];
var wkt;
var boundaryFeature;
var mapParamsDialog;

var scaleFactor = 0.15; // 15%
var scaleMultiplier = 0;

var paperSize;
var orientation;

sourceCrs = "EPSG:4326";
destCrs = "EPSG:3857";
var printCrs = sourceCrs;
var printProj4 = "";
var featureForPrint;
prepareProjection();

function showParams(feature, layers) {
    featureForPrint = feature;

    if (layers !== null && layers.length > 0) {
        var layerOptions = "";
        for (var i = 0; i < layers.length; i++) {
            layerOptions += '<input type="checkbox" value="' + layers[i].name + '" /> ' + layers[i].alias + '<br />';
        }
        $("#pnlUserLayers").html(layerOptions);
    }

    // Show parameters dialog
    mapParamsDialog = $("#mapParamsDialog").dialog({
        autoOpen: false,
        height: 350,
        width: 500,
        closed: false,
        cache: false,
        resizable: false,
        modal: true,
        close: function () {
        },
        buttons: [
            {
                text: "OK",
                "id": "btnRenderMap",
                click: function () {
                    renderMap(feature, layers);
                    mapParamsDialog.dialog("destroy");
                }
            }
        ]
    });

    mapParamsDialog.dialog("open");
}

function printMap() {
    $("#btnPrint").hide();
    print();
    $("#btnPrint").show();
    return false;
}

function renderMap(feature, userLayers) {
    // Set map height and width
    paperSize = $("#paperSize").val();
    orientation = $('input[name=orientation]:checked').val();

    if (paperSize === "a3") {
        width = 26.2;
        height = 38.5;
        scaleMultiplier = 1;
    } else if (paperSize === "a2") {
        width = 38.5;
        height = 55.9;
        scaleMultiplier = 2;
    } else if (paperSize === "a1") {
        width = 55.9;
        height = 80.6;
        scaleMultiplier = 3;
    } else if (paperSize === "a0") {
        width = 80.6;
        height = 115.4;
        scaleMultiplier = 4;
    }

    northImage = "../images/viewer/north_" + paperSize + ".png";

    // Fill information
    $("#coatOfArms").attr("src", "../images/viewer/LiberiaCoatOfArms_" + paperSize + ".png");
    $("#usaidLogo").attr("src", "../images/viewer/USAID_" + paperSize + ".png");
    $("#lblVillageName").text(feature.village);
    $("#lblDistrictName").text(feature.district);
    $("#lblClanName").text(feature.clan);
    $("#lblVillageLeader").text(feature.villageLeader);
    $("#lblQuartersNum").text(feature.quartersNum);
    $("#lblPopulation").text(feature.population);
    $(".mapHeaderLabel").append(feature.area);
    $("#btnPrint").show();

    if (orientation === "landscape") {
        var tmpHeight = height;
        height = width;
        width = tmpHeight;
        var tmpHSteps = hSteps;
        hSteps = vSteps;
        vSteps = tmpHSteps;
    }

    prepareMapDefaults();
    layers = [];
    wkt = new ol.format.WKT();

    // Add WMS layers
    $('#pnlUserLayers :checkbox:checked').each(function () {
        if (this.value === "bingAreal") {
            layers.push(new ol.layer.Tile({
                visible: true,
                preload: Infinity,
                source: new ol.source.BingMaps({
                    key: 'Aln9vuwvZ5OXaB7LaPhpy4Z3DyMEKTzIMxJG07aYluJNdMQukZZhFIA5Dllousf-',
                    imagerySet: 'Aerial'
                })
            }));
        }

        if (this.value === "googleAreal") {
            layers.push(new ol.layer.Tile({
                source: new ol.source.TileImage({
                    url: 'http://mt1.google.com/vt/lyrs=y&x={x}&y={y}&z={z}',
                    projection: "EPSG:3857",
                    crossOrigin: 'null'
                })
            }));
        }

        // Add WMS layers
        if (userLayers !== null && userLayers.length > 0) {
            for (var i = 0; i < userLayers.length; i++) {
                if (this.value === userLayers[i].name) {
                    layers.push(
                            new ol.layer.Tile({
                                source: new ol.source.TileWMS({
                                    url: userLayers[i].url,
                                    params: {
                                        'LAYERS': userLayers[i].name,
                                        'TILED': true,
                                        'FORMAT': userLayers[i].outputformat.documentformat
                                    },
                                    serverType: 'geoserver'
                                })
                            })
                            );
                    break;
                }
            }
        }
    });

    // Add boundary
    boundaryFeature = wkt.readFeature(feature.wktGeom,
            {dataProjection: sourceCrs, featureProjection: destCrs}
    );
    boundaryFeature.setProperties({
        name: "VILLAGE NAME"
    });

    var initialZoomBounds = boundaryFeature.getGeometry().getExtent();
    addPoints();

    map = new ol.Map({
        layers: layers,
        loadTilesWhileInteracting: true,
        target: 'map',
        controls: [],
        interactions: ol.interaction.defaults({
            doubleClickZoom: false,
            dragAndDrop: false,
            keyboardPan: false,
            dragPan: false,
            keyboardZoom: false,
            mouseWheelZoom: false,
            pointer: false,
            select: false
        })
    });

    if (initialZoomBounds) {
        map.getView().fit(initialZoomBounds, map.getSize());
    }

    drawScaleAndGrid();

    var d = new Date();
    $("#lblToday").text(d.toLocaleString('en-us', {month: 'long'}) + " " + d.getFullYear());
}

function prepareProjection() {
    if (printProj4 !== null && printProj4.length > 1) {
        printCrs = "printSrs";
        proj4.defs(printCrs, printProj4);
    }
}

function prepareMapDefaults() {
    if (scaleMultiplier > 0) {
        // Re-calculate all defaults
        headerSize += (headerSize * scaleMultiplier * scaleFactor);
        boundaryWidth += Math.floor(boundaryWidth * scaleMultiplier * scaleFactor);
        headerFontSize += Math.floor(headerFontSize * scaleMultiplier * scaleFactor);
        beaconSize += Math.floor(beaconSize * scaleMultiplier * scaleFactor);
        beaconFontSize += Math.floor(beaconFontSize * scaleMultiplier * scaleFactor);
        beaconHeight += Math.floor(beaconHeight * scaleMultiplier * scaleFactor);
        beaconBorderSize += Math.ceil(beaconBorderSize * scaleMultiplier * scaleFactor);
        legendWidth += Math.floor(legendWidth * scaleMultiplier * scaleFactor);
        legendFontSize += Math.floor(legendFontSize * scaleMultiplier * scaleFactor);
        scaleBarHeight += Math.floor(scaleBarHeight * scaleMultiplier * scaleFactor);
        stepLineWidth += Math.ceil(stepLineWidth * scaleMultiplier * scaleFactor);
        stepLineHeight += Math.floor(stepLineHeight * scaleMultiplier * scaleFactor);
        coordsPanelHeight += Math.floor(coordsPanelHeight * scaleMultiplier * scaleFactor);
    }

    // Calculate width for map 
    var gap = 0;
    if (scaleMultiplier > 1) {
        // Add 1cm gap for A2/1/0
        gap = (1 / 2.54) * 96;
    }
    var mapContainerWidth = Math.round(((width / 2.54) * 96) - gap - legendWidth);
    var mapWidth = mapContainerWidth - (coordsPanelHeight * 2);

    var mapContainerHeight = Math.round(((height - headerSize) / 2.54 * 96));
    var mapHeight = Math.round(mapContainerHeight - (coordsPanelHeight * 2));

    $("#mapContainer").css("width", mapContainerWidth + "px");
    $("#mapContainer").css("height", mapContainerHeight + "px");
    $("#map").css("width", mapWidth + "px");
    $("#map").css("height", mapHeight + "px");

    $("#hTopCoords").css("width", mapWidth + "px");
    $("#hTopCoords").css("margin-left", coordsPanelHeight + "px");
    $("#hTopCoords").css("height", coordsPanelHeight + "px");
    $("#hTopCoords").css("font-size", (legendFontSize - 2) + "pt");

    $("#hBottomCoords").css("width", mapWidth + "px");
    $("#hBottomCoords").css("margin-left", coordsPanelHeight + "px");
    $("#hBottomCoords").css("height", coordsPanelHeight + "px");
    $("#hBottomCoords").css("font-size", (legendFontSize - 2) + "pt");

    $("#vLeftCoords").css("height", mapHeight + "px");
    $("#vLeftCoords").css("width", coordsPanelHeight + "px");
    $("#vLeftCoords").css("font-size", (legendFontSize - 2) + "pt");

    $("#vRightCoords").css("height", mapHeight + "px");
    $("#vRightCoords").css("width", coordsPanelHeight + "px");
    $("#vRightCoords").css("font-size", (legendFontSize - 2) + "pt");

    $(".mapHeaderLabel").css("font-size", headerFontSize + "pt");
    $(".mapHeader").css("width", width + "cm");
    $("#legend").css("width", legendWidth + "px");
    $(".legendBlock").css("font-size", (legendFontSize) + "pt");
    $(".smallText").css("font-size", (legendFontSize - 2) + "pt");
    $(".coordsTable").css("font-size", (legendFontSize - 2) + "pt");
    $(".boundary").css("border-width", boundaryWidth + "px");
    $(".boundary").css("height", beaconHeight + "px");
    $(".boundary").css("width", (beaconHeight * 2) + "px");
    $(".circle").css("height", beaconHeight + "px");
    $(".circle").css("width", beaconHeight + "px");
    $(".circle").css("border-width", beaconBorderSize + "px");
    $("#scaleBarTable tr td").css("height", scaleBarHeight + "px");

    $("#content").css("width", width + "cm");

    if ($("#map").width() > $("body").width()) {
        $("body").css("width", width + "cm");
    }

    $(".mapHeader").show();
    $("#legend").show();
    $("#mapContainer").show();
}

function addPoints() {
    var borderStyle = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: '#FF5500',
            width: boundaryWidth
        }),
        fill: new ol.style.Fill({
            color: 'rgba(255,255,255,0)'
        })
    });

    layers.push(new ol.layer.Vector({
        source: new ol.source.Vector({
            features: [boundaryFeature]
        }),
        style: borderStyle
    }));

    // Get survey points
    var surveyPoints = null;
    var printAllPoints = $('#printAll').prop('checked');

    if (!printAllPoints) {
        $.ajax({
            url: "../../viewer/resource/getBoundaryPointsByProject/" + featureForPrint.projectId,
            async: false,
            success: function (data) {
                surveyPoints = data;
            }
        });
    }

    var points = boundaryFeature.getGeometry().getCoordinates();
    var pointFeatures = [];
    var pintNum = 0;

    for (var i = 0; i < points[0].length - 1; i++) {
        var printPoint = true;

        if (!printAllPoints) {
            printPoint = false;
            var transformedPoint = ol.proj.transform(points[0][i], destCrs, printCrs);

            // Search for matching points from survey points list
            var coords = getCoordinatesFromWktPointsList(surveyPoints);
            for (var j = 0; j < coords.length - 1; j++) {
                if (coords[j].x === transformedPoint[0].toFixed(4) && coords[j].y === transformedPoint[1].toFixed(4)) {
                    printPoint = true;
                    break;
                }
            }
        }

        var pointLabel = "";
        if (printPoint) {
            pintNum += 1;
            pointLabel = "BP " + pintNum;
        }

        pointFeatures.push(new ol.Feature({
            geometry: new ol.geom.Point(points[0][i]),
            name: pointLabel,
            print: printPoint
        }));
    }

    var pointStyle = function (feature, resolution) {
        return new ol.style.Style({
            image: new ol.style.Circle({
                radius: beaconSize,
                fill: new ol.style.Fill({color: feature.get('print') ? 'rgba(0, 0, 0, 1)' : 'rgba(255, 255, 255, 1)'}),
                stroke: new ol.style.Stroke({color: 'rgba(0, 0, 0, 1)', width: beaconBorderSize})
            }),
            text: new ol.style.Text({
                text: feature.get('name'),
                textAlign: 'left',
                textBaseline: 'middle',
                font: beaconFontSize + 'px arial',
                fill: new ol.style.Fill({color: 'black'}),
                stroke: new ol.style.Stroke({color: 'white', width: 3}),
                offsetX: Math.ceil(scaleFactor * scaleMultiplier) + 10,
                offsetY: (Math.ceil(scaleFactor * scaleMultiplier) * -1) - 10,
                placement: 'Point'
            })
        });
    };

    layers.push(new ol.layer.Vector({
        source: new ol.source.Vector({
            features: pointFeatures
        }),
        style: pointStyle
    }));

    // Add point coordinates
    $("#tableCoords tr").remove("tr:gt(0)");
    var rows = ""

    pointFeatures.forEach(function (p, i) {
        var transformedPoint = ol.proj.transform(p.getGeometry().getCoordinates(), destCrs, printCrs);
        var xCoord;
        var yCoord;

        var printPoint = true;
        if (!printAllPoints) {
            printPoint = false;
            // Search for matching points from survey points list
            var coords = getCoordinatesFromWktPointsList(surveyPoints);
            for (var j = 0; j < coords.length - 1; j++) {
                if (coords[j].x === transformedPoint[0].toFixed(4) && coords[j].y === transformedPoint[1].toFixed(4)) {
                    printPoint = true;
                    break;
                }
            }
        }

        if (printPoint) {
            if (printCrs === sourceCrs) {
                var dmsCoord = ddToDms(transformedPoint[1].toFixed(4), transformedPoint[0].toFixed(4));
                xCoord = dmsCoord[1];
                yCoord = dmsCoord[0];
            } else {
                xCoord = transformedPoint[0].toFixed(4);
                yCoord = transformedPoint[1].toFixed(4);
            }

            rows += "<tr><td>" + p.get("name") + "</td>" +
                    "<td>" + xCoord + "</td>" +
                    "<td>" + yCoord + "</td></tr>";
        }
    });

    $('#tableCoords > tbody:last-child').append(rows);
    $("#legend").show();

    // Add area
    /*var transformedPloy = boundaryFeature.clone();
     var boundaryArea;
     
     if (printCrs === sourceCrs) {
     boundaryArea = ol.Sphere.getArea(transformedPloy.getGeometry());
     } else {
     boundaryArea = ol.Sphere.getArea(transformedPloy.getGeometry().transform(destCrs, printCrs));
     }
     
     var boundaryAreaFormated = Math.round(boundaryArea) + " m<sup>2</sup>";
     if (boundaryArea >= 1000) {
     // Hectares
     boundaryAreaFormated = (boundaryArea / 10000).toFixed(2) + " ha";
     }
     
     $(".mapHeaderLabel").append(boundaryAreaFormated);*/
}

function getCoordinatesFromWktPointsList(surveyPoints) {
    var coords = [];

    if (surveyPoints !== null) {
        for (var j = 0; j < surveyPoints.length - 1; j++) {
            var surveyPointCoords = surveyPoints[j].geometry.replace("POINT(", "").replace(")", "").split(" ");
            coords.push({"x": parseFloat(surveyPointCoords[0]).toFixed(4), "y": parseFloat(surveyPointCoords[1]).toFixed(4)});
        }
    }
    return coords;
}

function drawScaleAndGrid() {
    var INCHES_PER_UNIT = {
        'm': 39.37,
        'dd': 4374754
    };
    var DOTS_PER_INCH = 96;

    var getScaleFromResolution = function (resolution, units, opt_round) {
        var scale = INCHES_PER_UNIT[units] * DOTS_PER_INCH * resolution;
        if (opt_round) {
            scale = Math.round(scale);
        }
        return scale;
    };

    var getResolutionFromScale = function (scale, units) {
        return scale / (INCHES_PER_UNIT[units] * DOTS_PER_INCH);
    };

    var formatNumber = function (num) {
        return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1 ");
    };

    // Set scale
    var scales = [100, 200, 500, 800, 1000, 1500, 2000, 3000, 5000, 7000, 10000, 12000, 15000, 20000, 25000, 30000, 35000, 50000, 75000, 100000, 120000, 150000, 180000, 200000, 250000, 300000, 500000, 800000, 1000000, 1500000];
    // Find best scale
    var currentScale = getScaleFromResolution(map.getView().getResolution(), 'm', true);
    var newScale = null;

    for (var i = 0; i < scales.length; i++) {
        if (scales[i] > currentScale) {
            newScale = scales[i];
            break;
        }
    }

    if (newScale === null) {
        newScale = scales[scales.length - 1];
    }

    var divider = 100;
    var decimalPart = 0;
    if (newScale > 10000) {
        divider = 100000;
        decimalPart = 1;
        $("#lblScaleUnits").text("Kilometers");
    } else {
        $("#lblScaleUnits").text("Meters");
    }

    $("#lblScale").text("1:" + formatNumber(newScale));
    $("#lblScale1").text("0");
    $("#lblScale2").text((newScale / divider).toFixed(decimalPart));
    $("#lblScale3").text(((newScale * 2) / divider).toFixed(decimalPart));
    $("#lblScale4").text(((newScale * 3) / divider).toFixed(decimalPart));
    $("#lblScale5").text(((newScale * 4) / divider).toFixed(decimalPart));

    map.getView().setResolution(getResolutionFromScale(newScale, 'm'));

    map.once('postrender', function () {
        // Draw coordinates grid
        var hStepSize = Math.round($("#map").width() / hSteps);
        var startX = Math.round(hStepSize / 2);
        hStepSize = (Math.round($("#map").width() + hStepSize) / hSteps);

        var vStepSize = Math.round($("#map").height() / vSteps);
        var startY = Math.round(vStepSize / 2);
        vStepSize = (Math.round($("#map").height() + vStepSize) / vSteps);

        $("#hTopCoords").html("");
        $("#hBottomCoords").html("");
        $("#vLeftCoords").html("");
        $("#vRightCoords").html("");

        // Draw horizontal
        for (var i = 1; hSteps > i; i++) {
            var xPos = i * hStepSize - startX;
            var xCoord;
            var transformedPoint = ol.proj.transform(map.getCoordinateFromPixel([xPos - mapBorderSize, 1]), destCrs, printCrs);

            if (printCrs === sourceCrs) {
                var dmsCoord = ddToDms(transformedPoint[1].toFixed(4), transformedPoint[0].toFixed(4));
                xCoord = dmsCoord[1];
            } else {
                xCoord = transformedPoint[0].toFixed(4);
            }

            $("#hTopCoords").append("<span class='gridCoord' style='left:" + (xPos) + "px;width: " + stepLineWidth + "px;height: " + stepLineHeight + "px;top: " + (coordsPanelHeight - stepLineHeight) + "px;'></span>");
            $("#hTopCoords").append("<span id='topX" + i + "' class='gridCoordLabel'>" + xCoord + "</span>");

            $("#hBottomCoords").append("<span class='gridCoord' style='left:" + (xPos) + "px;width: " + stepLineWidth + "px;height: " + stepLineHeight + "px;top: 0px;'></span>");
            $("#hBottomCoords").append("<span id='bottomX" + i + "' class='gridCoordLabel'>" + xCoord + "</span>");

            var leftPos = xPos + Math.round((stepLineWidth / 2)) - Math.round($("#topX" + i).width() / 2);
            var topPos = (coordsPanelHeight - stepLineHeight) - $("#topX" + i).height();

            $("#topX" + i).css("left", leftPos + "px");
            $("#topX" + i).css("top", topPos + "px");
            $("#bottomX" + i).css("left", leftPos + "px");
            $("#bottomX" + i).css("top", stepLineHeight + "px");
        }

        // Draw vertical
        for (var i = 1; vSteps > i; i++) {
            var yPos = i * vStepSize - startY;
            var yCoord;
            var transformedPoint = ol.proj.transform(map.getCoordinateFromPixel([1, yPos - mapBorderSize]), destCrs, printCrs);

            if (printCrs === sourceCrs) {
                var dmsCoord = ddToDms(transformedPoint[1].toFixed(4), transformedPoint[0].toFixed(4));
                yCoord = dmsCoord[0];
            } else {
                yCoord = transformedPoint[1].toFixed(4);
            }

            $("#vLeftCoords").append("<span class='gridCoord' style='right:0px;height: " + stepLineWidth + "px;width: " + stepLineHeight + "px;top: " + yPos + "px;'></span>");
            $("#vLeftCoords").append("<span id='leftY" + i + "' class='gridCoordLabel gridCoordLabelRight'>" + yCoord + "</span>");

            $("#vRightCoords").append("<span class='gridCoord' style='left:0px;height: " + stepLineWidth + "px;width: " + stepLineHeight + "px;top: " + yPos + "px;'></span>");
            $("#vRightCoords").append("<span id='rightY" + i + "' class='gridCoordLabel gridCoordLabelRight'>" + yCoord + "</span>");

            var topPos = yPos + Math.round((stepLineWidth / 2)) - Math.round($("#leftY" + i).height() / 2);

            $("#leftY" + i).css("right", stepLineHeight + "px");
            $("#leftY" + i).css("top", topPos + "px");
            $("#rightY" + i).css("left", stepLineHeight + "px");
            $("#rightY" + i).css("top", topPos + "px");
        }

        // Add north arrow
        $("#imgNorth").attr("src", northImage);
        $("#imgNorth").css("top", (coordsPanelHeight + 10) + "px");
        $("#imgNorth").css("right", (coordsPanelHeight + 10) + "px");
    });
}

function ddToDms(lat, lng) {
    var lat = lat;
    var lng = lng;
    var latResult, lngResult;

    lat = parseFloat(lat);
    lng = parseFloat(lng);

    latResult = (lat >= 0) ? 'N' : 'S';
    latResult = getDms(lat) + latResult;

    lngResult = (lng >= 0) ? 'E' : 'W';
    lngResult = getDms(lng) + lngResult;

    return [latResult, lngResult];
}

function getDms(val) {
    var valDeg, valMin, valSec, result;
    val = Math.abs(val);

    valDeg = Math.floor(val);
    result = valDeg + "º";

    valMin = Math.floor((val - valDeg) * 60);
    result += valMin + "'";

    valSec = Math.round((val - valDeg - valMin / 60) * 3600);
    result += valSec + '"';

    return result;
}