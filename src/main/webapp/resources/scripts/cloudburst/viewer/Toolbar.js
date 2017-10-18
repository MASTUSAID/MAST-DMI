var downloadFilePath = window.location.protocol + '//' + window.location.host + window.location.pathname + 'resources/temp/uploads/user/' + 'project.pdf';
//var loggedin_user_role='ROLE_ADMIN';
var loggedin_user_role = null;
var cara = null;
var defaultButtons = ['zoomin', 'zoomout', 'pan', 'zoomtolayer', 'fixedzoomin',
    'fixedzoomout', 'zoomprevious', 'zoomnext', 'fullview', 'info'];

Cloudburst.Toolbar = OpenLayers
        .Class({
            map: null,
            initialize: function (_map) {
                this.map = _map;
                loggedin_user_role = roles;

                $("#baselayer button").click(
                        function (e) {

                            for (x = 0; x < baseLayers.length; x++) {
                                map.getLayersByName(baseLayers[x])[0]
                                        .setVisibility(false);
                            }

                            var baseLayer = map
                                    .getLayersByName(e.currentTarget.id)[0];
                            baseLayer.setVisibility(true);
                            map.setBaseLayer(baseLayer);

                        });

                $("#bottombar").hide();
                $("#bottomcollapse").click(
                        function () {

                            if ($("#bottombar").children().length > 0) {
                                $("#bottombar").toggle();
                            }

                            if ($('#bottombar:visible').length > 0) {
                                var bottombarheight = $("#bottombar").height();
                                $("#bottomcollapse").css("bottom",
                                        bottombarheight + "px");
                                $("#bottomcollapse").removeClass(
                                        "bottom_collapse");
                                $("#bottomcollapse").addClass(
                                        "bottom_collapse_down");
                            } else {
                                $("#bottomcollapse").css("bottom", "0px");
                                $("#bottomcollapse").removeClass(
                                        "bottom_collapse_down");
                                $("#bottomcollapse")
                                        .addClass("bottom_collapse");
                            }
                        });

                $("#collapse")
                        .click(
                                function () {

                                    $("#sidebar").toggle();
                                    if ($('#sidebar:visible').length > 0) {
                                        console.log('the element is visible');
                                        $("#map").css("width", "100%");
                                        $("#statusbar").css("width", "100%");
                                        // $("#collapse").text("<");
                                        $("#collapse").removeClass(
                                                "collapse_right");
                                        $("#collapse")
                                                .addClass("collapse_left");
                                        $("#bottombar")
                                                .css(
                                                        "width",
                                                        $(window).width()
                                                        - $("#sidebar")
                                                        .width());
                                        $("#bottombar").css("right", "0");
                                        $("#bottombar").css("left", "");

                                        // $("#bottombar").removeAttr("left");

                                    } else {
                                        console
                                                .log('the element is not visible');
                                        $("#map").css("width", "100%");
                                        $("#statusbar").css("width", "100%");
                                        // $("#collapse").text(">");
                                        $("#collapse").removeClass(
                                                "collapse_left");
                                        $("#collapse").addClass(
                                                "collapse_right");
                                        $("#bottombar").css("left", "0");
                                        $("#bottombar").css("width",
                                                $(window).width());

                                    }

                                    map.updateSize();
                                });

                toggleButtons();

                cara = jQuery('#mycarousel').jcarousel({
                    scroll: 9
                });

                var at = ['bottom center'];
                my = ['top center'];

                var navi = new Cloudburst.Navi(this.map);

                var toolbutton = showToolButtons(loggedin_user_role);

                var layermanager = new Cloudburst.LayerManager(map,
                        "tabs-LayerManager");
                $("#sidebar").tabs();

                $("#toolbar button")
                        .bind(
                                "click",
                                function (e) {
                                    // remove unsaved markup and deactive
                                    // current tool
                                    removeDeactiveMarkupTool();

                                    switch (e.currentTarget.id) {
                                        case 'navigate':
                                            navi.toggle();
                                            break;

                                        case 'dynalayer':
                                            var dynalayer = new Cloudburst.DynaLayer(
                                                    map, "sidebar");
                                            break;

                                        case 'print':
                                            var print = new Cloudburst.Print(map,
                                                    "sidebar");
                                            break;

                                        case 'layermanager':
                                            layermanager.toggle();
                                            break;

                                        case 'bookmark':
                                            // bookmark.toggle();
                                            var bookmark = new Cloudburst.Bookmark(
                                                    map, "sidebar");
                                            break;
                                        case 'export':
                                            // exportdata.toggle();
                                            var exportdata = new Cloudburst.ExportData(
                                                    map, "sidebar");
                                            break;
                                        case 'query':
                                            var query = new Cloudburst.Query(true);
                                            break;
                                        case 'thematic':
                                            var thematic = new Thematic(map);
                                            break;
                                        case 'markup':
                                            var markup = new Cloudburst.Markup(map,
                                                    "sidebar");
                                            break;
                                        case 'fileupload':
                                            //var fileupload = new Cloudburst.FileUpload(map, "sidebar");
                                            //window.open('http://nw028lim:8080/spatialvue/viewer/resources/temp/uploads/user/Sunset.jpg');
                                            window.open(downloadFilePath);
                                            break;
                                        case 'textstyle':
                                            var textstyle = new Cloudburst.TextStyle(
                                                    map, "sidebar");
                                            break;
                                        case 'editing':
                                            var activeLayer = OpenLayers.Map.activelayer;
                                            if (activeLayer == null || activeLayer == undefined) {
                                                jAlert("Please select active layer", "Layer Editing");
                                                return;
                                            } else {
                                                if (activeLayer.editable) {
                                                    var wfsurl = activeLayer.url.replace(new RegExp("wms", "i"), "wfs");
                                                    $.ajax({
                                                        url: PROXY_PATH + wfsurl + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + layerMap[activeLayer.name],
                                                        dataType: "text",
                                                        async: false,
                                                        success: function (text) {
                                                            var editing = new Cloudburst.Editing(
                                                                    map, "sidebar", undefined, undefined, undefined,
                                                                    activeLayer.name, undefined);
                                                        },
                                                        error: function (xhr, status) {
                                                            if (layerMap[activeLayer.name].indexOf("OSMM_") > -1) {
                                                                jAlert("WFS operation on " + activeLayer.name + " layer is restricted");
                                                                return;
                                                            } else {
                                                                jAlert('Sorry, there is a problem!');
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    jAlert("Selected layer is not directly editable", "Layer Editing");
                                                }

                                                //}

                                            }
                                            break;
                                        case 'saveproject':
                                            var saveproject = new Cloudburst.SaveProject(
                                                    map, layermanager);
                                            break;
                                        case 'openproject':
                                            jQuery("#defaultbutton").css("visibility", "visible");
                                            //var openproject = new Cloudburst.OpenProject(map, "sidebar");
                                            var openproject = new Cloudburst.UserProjects(map, "sidebar");
                                            break;
                                        case 'exportmap':
                                            var exportmap = new Cloudburst.ExportMap(
                                                    map, "sidebar");
                                            break;
                                        case 'searchComplaint':
                                            var complaint = new Cloudburst.Complaints(map, "sidebar");

                                            break;
                                        case 'complaint':
                                            var complaint = new Cloudburst.Complaints(map, "sidebar");
                                            break;

                                        case 'report':
                                            var report = new Cloudburst.Report(map, "sidebar");
                                            break;

                                        case 'importdata':
                                            var importdata = new ImportData('importdata');
                                            break;

                                        default:
                                    }
                                });
            },
            CLASS_NAME: "Cloudburst.Toolbar"
        });

var showToolButtons = function (_roles) {

    var toolbuttons = $("#toolbar button");
    var visiblebuttons = null;
    var visiblebuttonsArr = new Array();

    var role_array = _roles.split(",");
    var size = 0;
    for (var r = 0; r < role_array.length; r++) {

        jQuery
                .ajax({
                    // url: "/spatialvue-studio/role/" + _roles + "?" + token,
                    url: STUDIO_URL + "role/" + role_array[r] + "?" + token,
                    async: false,
                    success: function (data) {
                        if (data.modules) {
                            visiblebuttons = data.modules;
                            for (var mod = 0; mod < visiblebuttons.length; mod++) {
                                visiblebuttonsArr[size + mod] = visiblebuttons[mod].name;
                            }
                            size = size + mod;
                        }
                    }
                });
    }
    if (visiblebuttonsArr.length <= 0) {
        for (var db = 0; db < defaultButtons.length; db++) {
            visiblebuttonsArr[db] = defaultButtons[db];
        }
    }

    for (var m = 0; m < toolbuttons.length; m++) {
        if (jQuery.inArray(toolbuttons[m].id, visiblebuttonsArr) <= -1) {
            $("#li-" + toolbuttons[m].id).css("display", "none");
        }
    }

    if (visiblebuttonsArr.length <= 13) {
        $(".jcarousel-prev").css("display", "none");
        $(".jcarousel-next").css("display", "none");
    }
};

function toggleGrid() {
    $("#bottombar").show();
    var bottombarheight = $("#bottombar").height();
    $("#bottomcollapse").css("bottom", bottombarheight + "px");
    $("#bottomcollapse").removeClass("bottom_collapse");
    $("#bottomcollapse").addClass("bottom_collapse_down");
}
