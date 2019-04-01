var mapControls;
var lonLat;
var mapTipOptions = {geometryName: ""};
var activeLayerURL = null;
var nav_his = [];
var size = -1;
var undo_redo = false;
var user_ist = null;
var region_list = null;
var project_list = null;
var projectVillages = null;
var featureTypes = null;
var confidenceLevels = null;
var boundaryPointDialog;
var villageBoundaryDialog;

var landtypeid = [
    {"id": "1", "value": "Flat/Plain"},
    {"id": "2", "value": "Sloping"},
    {"id": "3", "value": "Mountainous"},
    {"id": "4", "value": "Valley"},
    {"id": "9999", "value": "NA"}
];

var landsoilqualityid =
        [
            {"id": "1", "value": "Very good"},
            {"id": "2", "value": "Moderate good"},
            {"id": "3", "value": "Poor"},
            {"id": "4", "value": "Very poor"}
        ];

var landusetypeid = [
    {"id": "1", "value": "Agriculture"},
    {"id": "2", "value": "Settlement"},
    {"id": "3", "value": "Livestock (intensive/ stationary)"},
    {"id": "4", "value": "Livestock (pastoralism)"},
    {"id": "5", "value": "Forest/ Woodlands"},
    {"id": "6", "value": "Forest Reserve"},
    {"id": "7", "value": "Grassland"},
    {"id": "8", "value": "Facility (church/mosque/recreation)"},
    {"id": "9", "value": "Commercial/Service"},
    {"id": "10", "value": "Minning"},
    {"id": "11", "value": "Wildlife (hunting)"},
    {"id": "12", "value": "Wildlife (tourism)"},
    {"id": "13", "value": "Industrial"},
    {"id": "14", "value": "Conservation"},
    {"id": "9999", "value": ""}

];

var acquisitiontypeid = [
    {"id": "1", "value": "Kupewa na Halmashauri ya Kijiji"},
    {"id": "2", "value": "Zawadi"},
    {"id": "3", "value": "Urithi"},
    {"id": "4", "value": "Kununua"}
];

var claimtypeid = [
    {"id": "1", "value": "New claim"},
    {"id": "2", "value": "Existing Claim or Right"},
    {"id": "3", "value": "Disputed Claim"},
    {"id": "4", "value": "No Claim"}
];

var landsharetypeid = [
    {"id": "1", "value": "Co-occupancy (Tenancy in Common)"},
    {"id": "2", "value": "Single Occupant"},
    {"id": "3", "value": "Co-occupancy (Joint tenancy)"},
    {"id": "4", "value": "Customary(Individual)"},
    {"id": "5", "value": "Customary(Collective)"},
    {"id": "6", "value": "Single Tenancy"},
    {"id": "7", "value": "Joint Tenancy"},
    {"id": "8", "value": "Common Tenancy"},
    {"id": "9", "value": "Collective Tenancy"},
    {"id": "9999", "value": ""},
];

var tenureclassid = [
    {"id": "1", "value": "Derivative Right"},
    {"id": "2", "value": "Customary Right of Occupancy"},
    {"id": "3", "value": "Right to Ownership"},
    {"id": "4", "value": "Right of Use"},
    {"id": "5", "value": "Formal Ownership (Free-hold)"},
    {"id": "6", "value": "Granted Right of Occupancy"},
    {"id": "7", "value": "Right to Manage"},
    {"id": "9999", "value": ""}
];

var spatialunitgroupid = [
    {"id": "1", "value": "Country"},
    {"id": "2", "value": "Region"},
    {"id": "3", "value": "Province"},
    {"id": "4", "value": "Commune"},
    {"id": "5", "value": "Place"}
];

var unitid = [
    {"id": "1", "value": "Foot"},
    {"id": "2", "value": "Inches"},
    {"id": "3", "value": "Kilometer"},
    {"id": "4", "value": "Meter"},
    {"id": "5", "value": "Miles"},
    {"id": "5", "value": "dd"}
];

Cloudburst.Navi = function (_map) {

    map.addInteraction(zIn);
    map.addInteraction(zOut);

    $("#toolbar button").bind("click", function (e) {

        map.un('singleclick', mapInfoClick);
        removeDeactiveMarkupTool();
        $("#defaultbutton").css("visibility", "hidden");

        tabSwitch();
        switch (e.currentTarget.id) {
            case 'zoomin':
                zIn.setActive(true);
                zOut.setActive(false);
                break;
            case 'zoomout':
                zIn.setActive(false);
                zOut.setActive(true);
                break;
            case 'pan':
                break;
            case 'info':
                map.on('singleclick', mapInfoClick);
                break;
            case 'measurelength':

                var measure = new Cloudburst.Measure(_map, "sidebar");
                break;
            case 'measurearea':
                Cloudburst.Navi.prototype.toggleControl("measurearea");
                break;
            case 'selectfeature':
                var checked = $("#" + e.currentTarget.id).hasClass('ui-state-active1')
                if (checked) {
                    if (selectClick !== null) {
                        map.addInteraction(selectClick);
                        selectClick.on('select', myCallback);
                    }
                }
                break;
            case 'selectbox':
                dragBoxInteraction.on('boxend', function (event) {
                    selectedFeatures = selectInteraction.getFeatures();
                    selectedFeatures.clear();
                    var extent = dragBoxInteraction.getGeometry().getExtent();
                    map.getLayers().forEach(function (layer) {
                        if (layer instanceof ol.layer.Vector) {
                            layer.getSource().forEachFeatureIntersectingExtent(extent, function (feature) {
                                selectedFeatures.push(feature);
                            });
                        }
                    });

                });

                // clear selection when drawing a new box and when clicking on the map
                dragBoxInteraction.on('boxstart', function () {
                    if (selectedFeatures != null) {
                        selectedFeatures.clear();
                    }
                });

                var checked = $("#" + e.currentTarget.id).hasClass('ui-state-active1')
                if (checked) {
                    map.addInteraction(selectInteraction);
                    map.addInteraction(dragBoxInteraction);
                }

                break;
            case 'selectpolygon':
                break;
            case 'zoomprevious':
                if (size > 0) {
                    undo_redo = true;
                    map.getView().fit(nav_his[size - 1].extent, nav_his[size - 1].size);
                    map.getView().setZoom(nav_his[size - 1].zoom);
                    setTimeout(function () {
                        undo_redo = false;
                    }, 360);
                    size = size - 1;
                }
                break;
            case 'zoomnext':
                //history.nextTrigger();
                if (size < nav_his.length - 1) {
                    undo_redo = true;
                    map.getView().fit(nav_his[size + 1].extent, nav_his[size + 1].size);
                    map.getView().setZoom(nav_his[size + 1].zoom);
                    setTimeout(function () {
                        undo_redo = false;
                    }, 360);
                    size = size + 1;
                }
                break;
            case 'fullview':
                var extent = map.getView().calculateExtent(map.getSize());
                map.getView().fit(extent, 18);

                break;
            case 'zoomtolayer':
                if (active_layerMap != null) {
                    if (active_layerMap.getSource() instanceof ol.source.Vector) {
                        map.getView().fit(active_layerMap.getSource().getExtent(), map.getSize());
                    } else if (active_layerMap.getSource() instanceof ol.source.Tile) {
                        map.getView().calculateExtent(map.getSize());
                    }

                } else {
                    jAlert('Please Select A layer First', 'Selection');
                }

                break;
            case 'fixedzoomin':
                var view = map.getView();
                var newResolution = view.constrainResolution(view.getResolution(), -1);
                view.setResolution(newResolution);

                break;
            case 'fixedzoomout':
                var view = map.getView();
                var newResolution = view.constrainResolution(view.getResolution(), 1);
                view.setResolution(newResolution);
                break;
            case 'search':
                var search = new Cloudburst.Search(_map, "sidebar");
                break;
            case 'zoomtoxy':
                var zoomtoxy = new Cloudburst.ZoomToXY(_map, "sidebar");
                break;
            case 'maptip':
                var maptip = new Cloudburst.Maptip();
                break;
            case 'clear_selection':
                clearSelection(true);
                break;
            case 'intersection' :
                spatialDialog = $("#validation-dialog-form").dialog({
                    autoOpen: false,
                    height: 230,
                    width: 234,
                    resizable: true,
                    modal: true,
                    buttons: {
                        "Ok": function ()
                        {

                            var selected = $("#radioSpatial input[type='radio']:checked");
                            if (selected.length > 0) {
                                spatial_validation = selected.val();
                                if (spatial_validation == "2")
                                {
                                    Cloudburst.Navi.prototype.toggleControl("intersection");
                                    spatialDialog.dialog("destroy");
                                } else if (spatial_validation == "1")
                                {

                                    var objIntersection = new Intersection("allBounds", OpenLayers.Map.activelayer);
                                    filter = objIntersection.creationSelectionCriteria("check");
                                    objIntersection.displayResult(filter, true);
                                    OpenLayers.Map.activelayer.selectFilter = filter;
                                    spatialDialog.dialog("destroy");
                                } else {

                                    var hamlet_Id = $("#hamletSpatialId").val();
                                    if (hamlet_Id != 0) {
                                        var objIntersection = new Intersection("allBounds", OpenLayers.Map.activelayer);
                                        filter = objIntersection.creationSelectionCriteria("check", hamlet_Id);
                                        objIntersection.displayResult(filter, true);
                                        OpenLayers.Map.activelayer.selectFilter = filter;
                                        spatialDialog.dialog("destroy");
                                    } else {
                                        alert("Please Select Hamlet ");
                                    }

                                }
                            }
                        },
                        "Cancel": function ()
                        {
                            spatialDialog.dialog("destroy");
                            spatialDialog.dialog("close");
                        }
                    },
                    close: function () {
                        spatialDialog.dialog("destroy");

                    }
                });
                $('input:radio[name="spatial_validation"][value="2"]').prop('checked', true);
                spatial_validType = "Select by rectangle";
                $('#hamletSpatial').hide();
                spatialDialog.dialog("open");

                break;
            default:
        }
    });

    $("#navtoolbar").dialog({
        title: 'Tools',
        resizable: false,
        width: 69,
        minWidth: 69,
        minHeight: 33,
        autoOpen: true,
        position: [1184, 115]
    });


    map.on('moveend', function () {
        if (undo_redo === false) {
            if (size < nav_his.length - 1) {
                for (var i = nav_his.length - 1; i > size; i--) {
                    nav_his.pop();
                }
            }
            nav_his.push({
                extent: map.getView().calculateExtent(map.getSize()),
                size: map.getSize(),
                zoom: map.getView().getZoom()
            });
            size = size + 1;
        }
    });
};

function mapInfoClick(evt) {
    var isBoundary = false;
    if (user_ist === null) {
        jQuery.ajax({
            url: STUDIO_URL + "user/info",
            async: false,
            success: function (data) {
                user_ist = data;
            }
        });
    }

    map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
        if (layer.get('name').toLowerCase() === L_BOUNDARY_POINTS.toLowerCase() && !isBoundary) {
            if (projectVillages === null) {
                $.ajax({
                    url: "resource/getProjectNeighborVillages/" + Global.PROJECT_ID,
                    async: false,
                    success: function (data) {
                        projectVillages = data;
                        if (data !== null) {
                            $("#cbxPointVillageId").append($("<option></option>").attr("value", "0").text(" "));
                            $.each(data, function (i, village) {
                                $("#cbxPointVillageId").append($("<option></option>").attr("value", village.hierarchyid).text(village.name));
                            });
                        }
                    }
                });
            }

            if (featureTypes === null) {
                $.ajax({
                    url: "resource/getFeatureTypes",
                    async: false,
                    success: function (data) {
                        featureTypes = data;
                        if (data !== null) {
                            $.each(data, function (i, ft) {
                                $("#cbxPointFeatureType").append($("<option></option>").attr("value", ft.id).text(ft.name));
                            });
                        }
                    }
                });
            }

            if (confidenceLevels === null) {
                $.ajax({
                    url: "resource/getConfidenceLevels",
                    async: false,
                    success: function (data) {
                        confidenceLevels = data;
                        if (data !== null) {
                            $("#cbxPointConfidenceLevel").append($("<option></option>").attr("value", "0").text(" "));
                            $.each(data, function (i, level) {
                                $("#cbxPointConfidenceLevel").append($("<option></option>").attr("value", level.id).text(level.name));
                            });
                        }
                    }
                });
            }

            // Get list of point photos
            var pointDocs = [];

            $.ajax({
                url: "resource/getBoundaryPointDocs/" + feature.get("id"),
                async: false,
                success: function (data) {
                    pointDocs = data;
                }
            });

            // Show dialog
            boundaryPointDialog = $("#boundary-point-dialog").dialog({
                autoOpen: false,
                height: 520,
                width: 600,
                closed: false,
                cache: false,
                resizable: false,
                modal: true,
                close: function () {
                    boundaryPointDialog.dialog("destroy");
                },
                buttons: [
                    {
                        text: "Close",
                        click: function () {
                            boundaryPointDialog.dialog("destroy");
                        }
                    },
                    {
                        text: "Save",
                        "id": "btnSaveBoundaryPoint",
                        click: function () {
                            saveBoundaryPoint();
                        }
                    }
                ]
            });

            boundaryPointDialog.dialog("open");

            // Fill in the fields
            $("#hPointId").val(feature.get("id"));
            $('#cbxPointVillageId').prop('selectedIndex', 0);
            $('#cbxPointConfidenceLevel').prop('selectedIndex', 0);

            if (feature.get("neighbor_village_id") !== null && feature.get("neighbor_village_id") !== "") {
                $("#cbxPointVillageId").val(feature.get("neighbor_village_id"));
            }
            if (feature.get("confidence_level") !== null && feature.get("confidence_level") !== "") {
                $("#cbxPointConfidenceLevel").val(feature.get("confidence_level"));
            }
            if (feature.get("verified")) {
                $("#chbxVerified").prop("checked", true);
            } else {
                $("#chbxVerified").prop("checked", false);
            }
            if (feature.get("confidence_level") !== null && feature.get("confidence_level") !== "") {
                $("#cbxPointConfidenceLevel").val(feature.get("confidence_level"));
            }
            $("#cbxPointFeatureType").val(feature.get("feature_type"));
            $("#txtPointFeatureDescription").val(feature.get("feature_description"));

            $('#bodyBoundaryPointDocs').empty();
            if (pointDocs !== null && pointDocs.length > 0) {
                $("#templateBoundaryPointDocs").tmpl(pointDocs).appendTo("#bodyBoundaryPointDocs");
            }
            $("#txtPointSurveyor").val(getUserFullName(feature.get("created_by")));
            $("#txtPointSurveyDate").val($.datepicker.formatDate('yy-mm-dd', new Date(feature.get("create_date"))));

            // Disable/enable save button
            var editingEnabled = true;
            if ($("#li-editing").css("display") === "none") {
                editingEnabled = false;
            }

            if (feature.get("approved") || feature.get("project_id") !== Global.PROJECT_ID || !editingEnabled) {
                $("#btnSaveBoundaryPoint").prop("disabled", true).hide();
                $("#cbxPointVillageId").prop("disabled", true);
                $("#cbxPointFeatureType").prop("disabled", true);
                $("#txtPointFeatureDescription").prop("disabled", true);
                $("#cbxPointConfidenceLevel").prop("disabled", true);
                $("#txtPointConfidenceLevelDescription").prop("disabled", true);
                $("#chbxVerified").prop("disabled", true);
            } else {
                $("#btnSaveBoundaryPoint").prop("disabled", false).show();
                $("#cbxPointVillageId").prop("disabled", false);
                $("#cbxPointFeatureType").prop("disabled", false);
                $("#txtPointFeatureDescription").prop("disabled", false);
                $("#cbxPointConfidenceLevel").prop("disabled", false);
                $("#txtPointConfidenceLevelDescription").prop("disabled", false);
                $("#chbxVerified").prop("disabled", false);
            }
            isBoundary = true;
        } else if (layer.get('name').toLowerCase() === L_BOUNDARY.toLowerCase() && !isBoundary) {
            openVillageBoundaryDialog(feature, null, function () {
                layer.getSource().clear();
            });
            isBoundary = true;
        }
    });

    if (isBoundary) {
        return;
    }
//    jQuery.ajax({
//        url: STUDIO_URL + "region/info",
//        async: false,
//        success: function (data) {
//            region_list = data;
//        }
//    });

    if (project_list === null) {
        jQuery.ajax({
            url: STUDIO_URL + "project/info",
            async: false,
            success: function (data) {
                project_list = data;
            }
        });
    }

    var popupInfo = "";
    $("#tabs-Tool").empty();
    jQuery('#infoDiv').remove();
    jQuery.get("resources/templates/viewer/info.html", function (template) {
        jQuery('#infoDiv').css("visibility", "visible");
        addTab($._("info"), template);
        jQuery("#info_accordion").empty();
        jQuery('#infoDiv').css("visibility", "visible");

        map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
            if (layer instanceof ol.layer.Vector) {
                var _features = feature;

                console.log(_features);
                var attrs = new Object();

                var objeto = feature.getProperties();
                var propiedades;
                for (propiedades in objeto) {
                    attrs[propiedades] = objeto[propiedades];
                }

                $.ajax({
                    async: false,
                    url: STUDIO_URL + "layer/" + layer.get('aname') + "/layerField" + "?" + token,
                    success: function (displayableFields) {
                        popupInfo += '<h3 class="" ><a id="' + layer.get('aname') + '" href="#">' + layer.get('aname') + '</a></h3>';

                        popupInfo += '<table class="featureInfo">';
                        $.each(displayableFields, function (i, dispField) {

                            popupInfo += '<tr>';

                            popupInfo += '<th>' + dispField.alias + '</th>';

                            var attrValue = attrs[dispField.layerfield];
                            if (!attrValue) {
                                attrValue = '';
                            }

                            var a_feild = dispField.layerfield;
                            if (a_feild == "landtypeid") {

                                $.each(landtypeid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }


                            if (a_feild == "acquisitiontypeid") {

                                $.each(acquisitiontypeid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }


                            if (a_feild == "claimtypeid") {

                                $.each(claimtypeid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }


                            if (a_feild == "landsharetypeid") {

                                $.each(landsharetypeid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }

                            if (a_feild == "landusetypeid") {

                                $.each(landusetypeid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }


                            if (a_feild == "spatialunitgroupid1") {

                                $.each(spatialunitgroupid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }


                            if (a_feild == "spatialunitgroupid2") {

                                $.each(spatialunitgroupid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }



                            if (a_feild == "spatialunitgroupid3") {

                                $.each(spatialunitgroupid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }



                            if (a_feild == "spatialunitgroupid4") {

                                $.each(spatialunitgroupid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }


                            if (a_feild == "spatialunitgroupid5") {

                                $.each(spatialunitgroupid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }


                            if (a_feild == "unitid") {

                                $.each(unitid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }

                            if (a_feild == "projectnameid") {

                                $.each(project_list, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.name;
                                        return false;
                                    }
                                });

                            }

                            if (a_feild == "createdby") {

                                $.each(user_ist, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.name;
                                        return false;
                                    }
                                });
                            }

                            if (a_feild == "modifiedby") {

                                $.each(user_ist, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.name;
                                        return false;
                                    }
                                });
                            }

                            if (a_feild == "tenureclassid") {

                                $.each(tenureclassid, function (idx, obj) {
                                    if (obj.id == attrValue) {
                                        attrValue = obj.value;
                                        return false;
                                    }
                                });
                            }

                            popupInfo += '<td>' + attrValue + '</td>';
                            popupInfo += '</tr>';
                        });

                        popupInfo += '</table>';
                        popupInfo += '</body></html>';
                    }
                });
            }
        });

        setTimeout(function () {
            jQuery("#info_accordion").html(popupInfo);
            jQuery("#info_accordion").accordion({fillSpace: true});
        }, 3000);
    });
}

function getUserFullName(id) {
    if (user_ist === null) {
        return null;
    }

    var userName = null;

    $.each(user_ist, function (idx, user) {
        if (user.id === id) {
            userName = user.name;
            return false;
        }
    });

    return userName;
}

function viewBoundaryPointDoc(id) {
    window.open("landrecords/downloadBoundaryDoc/" + id, 'popUp', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
}

function saveVillageBoundary() {
    var result = false;
    $.ajax({
        type: 'POST',
        async: false,
        url: "resource/saveBoundary",
        data: $("#formVillageBoundary").serialize(),
        success: function (response) {
            if (response) {
                jAlert('Village boundary has been saved successfully', 'Info');
                // Update feature attributes
                villageBoundaryDialog.dialog("destroy");
                result = true;
            } else {
                jAlert("Failed to save village boundary", "Error");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert("Failed to save village boundary", "Error");
        }
    });
    return result;
}

function saveBoundaryPoint() {
    $.ajax({
        type: 'POST',
        url: "resource/saveBoundaryPoint",
        data: $("#formBoundaryPoint").serialize(),
        success: function (result) {
            if (result) {
                jAlert('Boundary  point information has been updated successfully', 'Info');
                getLayerByAliesName(TYPE_BOUNDARY_POINTS).getSource().clear();
                boundaryPointDialog.dialog("destroy");
            } else {
                jAlert("Failed to save boundary point", "Error");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert("Failed to save boundary point", "Error");
        }
    });
}

function openVillageBoundaryDialog(feature, closeCallBack, saveCallback) {
    // Get projet details if selected boundary is not of the current project
    if (Global.PROJECT_ID !== feature.get("project_id")){
        $.ajax({
            url: "landrecords/getProjectLocation/" + feature.get("project_id"),
            async: false,
            success: function (data) {
                $('#txtBoundaryDistrict').val(data.district);
                $('#txtBoundaryClan').val(data.clan);
                $('#txtBoundaryVillage').val(data.village);
            }
        });
    } else {
        $('#txtBoundaryDistrict').val(Global.PROJECT_AREA.laSpatialunitgroupHierarchy3.name);
        $('#txtBoundaryClan').val(Global.PROJECT_AREA.laSpatialunitgroupHierarchy4.name);
        $('#txtBoundaryVillage').val(Global.PROJECT_AREA.laSpatialunitgroupHierarchy5.name);
    }
    
    // Show dialog
    villageBoundaryDialog = $("#boundary-dialog").dialog({
        autoOpen: false,
        height: 380,
        width: 600,
        closed: false,
        cache: false,
        resizable: false,
        modal: true,
        close: function () {
            villageBoundaryDialog.dialog("destroy");
            if (closeCallBack !== null && typeof closeCallBack !== 'undefined') {
                closeCallBack();
            }
        },
        buttons: [
            {
                text: "Close",
                click: function () {
                    villageBoundaryDialog.dialog("destroy");
                    if (closeCallBack !== null && typeof closeCallBack !== 'undefined') {
                        closeCallBack();
                    }
                }
            },
            {
                text: "Print",
                "id": "btnPrintVillageBoundary",
                click: function () {
                    printVillageBoundary(feature);
                }
            },
            {
                text: "Save",
                "id": "btnSaveVillageBoundary",
                click: function () {
                    if (saveVillageBoundary(feature)) {
                        if (saveCallback !== null && typeof saveCallback !== 'undefined') {
                            saveCallback();
                        }
                    }
                }
            }
        ]
    });

    villageBoundaryDialog.dialog("open");

    // Fill in the fields
    var format = new ol.format.WKT();
    $("#hVillageBoundaryId").val(feature.get("id"));
    $("#hVillageBoundaryProjectId").val(feature.get("project_id"));
    $("#hVillageBoundaryGeom").val(format.writeGeometry(feature.getGeometry()));
    $("#txtVillageLeader").val(feature.get("village_leader"));
    $("#txtVillageQuarters").val(feature.get("quarters_num"));
    $("#txtVillagePopulation").val(feature.get("population"));
    var surveyDate = new Date(feature.get("create_date"));
    $("#txtBoundaryCreateDate").val($.datepicker.formatDate('yy-mm-dd', surveyDate));
    $("#txtBoundarySize").val(formatArea(feature.getGeometry()));

    // Disable/enable save button
    var editingEnabled = true;
    if ($("#li-editing").css("display") === "none") {
        editingEnabled = false;
    }

    if (feature.get("approved") || feature.get("project_id") !== Global.PROJECT_ID || !editingEnabled) {
        $("#btnSaveVillageBoundary").prop("disabled", true).hide();
        $("#txtVillageLeader").prop("disabled", true);
        $("#txtVillageQuarters").prop("disabled", true);
        $("#txtVillagePopulation").prop("disabled", true);
    } else {
        $("#btnSaveVillageBoundary").prop("disabled", false).show();
        $("#txtVillageLeader").prop("disabled", false);
        $("#txtVillageQuarters").prop("disabled", false);
        $("#txtVillagePopulation").prop("disabled", false);
    }
}

function printVillageBoundary(feature) {
    // Prepare feature for printing
    var printFeature = {};
    printFeature["id"] = feature.get("id");
    printFeature["projectId"] = feature.get("project_id");
    //printFeature["geometry"] = feature.getGeometry();
    printFeature["villageLeader"] = feature.get("village_leader");
    printFeature["quartersNum"] = feature.get("quarters_num");
    printFeature["population"] = feature.get("population");
    printFeature["district"] = $('#txtBoundaryDistrict').val();
    printFeature["clan"] = $('#txtBoundaryClan').val();
    printFeature["village"] = $('#txtBoundaryVillage').val();
    printFeature["area"] = $('#txtBoundarySize').val();
    printFeature["surveyDate"] = $('#txtBoundaryCreateDate').val();
    printFeature["wktGeom"] = $('#hVillageBoundaryGeom').val();

    // Compile list of layers
    var layers = [];

    for (var i = Global.PROJECT.projectLayergroups.length - 1; i >= 0; i--) {
        var lg = Global.PROJECT.projectLayergroups[i].layergroupBean;

        for (var j = lg.layerLayergroups.length - 1; j >= 0; j--) {
            var layer = lg.layerLayergroups[j].layers;
            if (layer.layertype.description == 'WMS' && layer.name !== L_BOUNDARY_POINTS && layer.name !== L_BOUNDARY) {
                layers.push(lg.layerLayergroups[j].layers);
            }
        }
    }

    var w = window.open("../resources/html/BoundaryPrint.html", "Village Boundary", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
    w.onload = function () {
        w.showParams(printFeature, layers);
    };
}

function myCallback(evt) {
    var feature = evt.target.getFeatures();
}

var onSLDSelectResponse = function (response) {
    OpenLayers.Map.activelayer.selectFilter = response.filters[0];
    alert(OpenLayers.Map.activelayer.selectFilter);
};


var onResponse = function (response) {
    var popupInfo = "";
    var attrs = response.features[0].attributes;

    // ************** Info Popup ***************************
    jQuery('#infoDiv').remove();

    jQuery.get("resources/templates/viewer/info.html", function (template) {

        $("#sidebar").append(template);
        jQuery('#infoDiv').css("visibility", "visible");
        jQuery("#infoBody").empty();

        jQuery("#InfoTemplate").tmpl(null,
                {
                    attrsList: attrs
                }

        ).appendTo("#infoBody");

        alert(jQuery("#infoDetails").html());
    });
};

Cloudburst.Navi.prototype.toggle = function () {
    if ($('#navtoolbar').dialog('isOpen')) {
        $('#navtoolbar').dialog('close');
    } else {
        $('#navtoolbar').dialog('open');
    }
};

Cloudburst.Navi.prototype.toggleControl = function (element) {

};

function tabSwitch() {
    $('#tab').tabs("select", "#map-tab");
    $('#sidebar').show();
    $('#collapse').show();
}