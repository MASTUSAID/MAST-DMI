var selectedItem = null;
var dataList = null;
var projList = null;
var spatialList = null;
var socialtenureList = null;
var associatednaturalPersonList = null;
var genderList = null;
var maritalList = null;
var tenuretypeList = null;
var multimediaList = null;
var educationsList = null;
var landUserList = null;
var tenureclassList = null;
var occtypeList = null;
var workflowhistoryList = null;
var socialEditTenureList = null;
var project = null;
var sourceDocList = null;
var usinId = null;
var read = null;
var soilQualityList = null;
var typeofLandList = null;
var slopeList = null;
var groupTypeList = null;
var attributeList = null;
var vectors = null;
var DeletedNaturalList = null;
var DeletedNonNaturalList = null;
var deletedAdminList = null;
var ProjectAreaList = null;
var personList = null;
var ID = null;
var year = null;
var activeProject = "";
var URL = null;
var resultDeleteNatural = null;
var records_from = 0;
var totalRecords = null;
var searchRecords = null;
var eduList = null;
var naturalPerson_gid = null;
var administratorID = null;
var adminDataList = null;
var checkNewNatural = false;
var hamletList = null;
var person_subtype = null;
var selectedHamlet = null;
var deceasedPersonList = null;
var validator = null;
var adjudicatorList = null;
var claimTypes = null;
var acquisitionTypes = null;
var relationshipTypes = null;
var claimType = null;
var idTypesList = null;
var docTypesList = null;
var editList = null;
var disputeTypesList = null;

var RESPONSE_OK = "OK";
var CLAIM_TYPE_NEW = "newClaim";
var CLAIM_TYPE_EXISTING = "existingClaim";
var CLAIM_TYPE_DISPUTED = "dispute";
var CLAIM_TYPE_UNCLAIMED = "unclaimed";
var CLAIM_STATUS_NEW = 1;
var CLAIM_STATUS_APPROVED = 2;
var CLAIM_STATUS_VALIDATED = 3;
var CLAIM_STATUS_REFERRED = 4;
var CLAIM_STATUS_DENIED = 5;
var landRecordsInitialized = false;

function LandRecords(_selectedItem) {
    if(projList !== null && projList !== ""){
        return;
    }
    
    selectedItem = _selectedItem;
    URL = "landrecords/spatialunit/default/" + 0;
    if (activeProject !== null && activeProject !== "") {
        URL = "landrecords/spatialunit/" + activeProject + "/" + 0;
    }
    jQuery.ajax({
        url: URL,
        async: false,
        success: function (data) {
            dataList = data;
            records_from = 0;
            searchRecords = null;
        }
    });

    jQuery.ajax({
        url: "landrecords/status/",
        async: false,
        success: function (data) {
            statusList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/claimtypes/",
        async: false,
        success: function (data) {
            claimTypes = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/spatialunit/" + activeProject,
        async: false,
        success: function (data) {
            totalRecords = data;
        }
    });

    displayRefreshedLandRecords();
}

function displayRefreshedLandRecords() {
    var URL = "landrecords/";
    if (activeProject != "") {
        URL = "landrecords/" + activeProject;
    }

    if (!landRecordsInitialized) {
        landRecordsInitialized = true;
        jQuery("#landrecords-div").empty();
        jQuery.get("resources/templates/viewer/" + selectedItem + ".html", function (template) {
            jQuery("#landrecords-div").append(template);
            jQuery('#landRecordsFormdiv').css("visibility", "visible");
            jQuery("#landRecordsTable").show();
        });
    }

    jQuery.ajax({
        url: URL,
        success: function (data) {
            projList = data;

            jQuery("#landRecordsRowData").empty();

            if (dataList.length != 0 && dataList.length != undefined) {
                jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
            }

            jQuery("#status_id").empty();
            jQuery("#claim_type").empty();

            jQuery("#status_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery("#claim_type").append(jQuery("<option></option>").attr("value", "").text("Please Select"));

            jQuery.each(statusList, function (i, statusobj) {
                jQuery("#status_id").append(jQuery("<option></option>").attr("value", statusobj.workflowStatusId).text(statusobj.workflowStatus));
            });

            jQuery.each(claimTypes, function (i, claimType) {
                jQuery("#claim_type").append(jQuery("<option></option>").attr("value", claimType.code).text(claimType.name));
            });

            jQuery("#records_from").val(records_from + 1);
            jQuery("#records_to").val(records_from + 20);

            if (totalRecords <= records_from + 20)
                jQuery("#records_to").val(totalRecords);

            jQuery('#records_all').val(totalRecords);
            jQuery("#projectName").text(data.name);
            jQuery("#country").text(data.countryName);
            jQuery("#region").text(data.region);
            jQuery("#district").text(data.districtName);
            jQuery("#village").text(data.village);
            jQuery("#hamlet").text("--");

            if (data.hamlet != "" && data.hamlet != null) {
                jQuery("#hamlet").text(data.hamlet);
            }

            $("#landRecordsTable").trigger("update");
            $("#landRecordsTable").tablesorter({
                sortList: [[0, 1], [1, 0]]
            });
        }
    });
}

function displayRefreshedTenure() {
    var id = editList[0].usin;
    jQuery.ajax({
        url: "landrecords/socialtenure/" + id,
        async: false,
        success: function (data) {
            jQuery("#tenureRowData").empty();
            if (data != "") {
                jQuery("#tenureinfoTemplate").tmpl(data).appendTo("#tenureRowData");
            }
        }
    });
}

function editAttributeNew(usin, statusId) {
    if (statusId === CLAIM_STATUS_NEW || statusId === CLAIM_STATUS_REFERRED || statusId === CLAIM_STATUS_VALIDATED) {
        read = false;
    } else {
        read = true;
    }
    editAttribute(usin);
}

function editAttribute(id) {
    displayAttributeCategory(1, id);
    jQuery.ajax({
        url: "landrecords/landusertype/",
        async: false,
        success: function (data) {
            landUserList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/soilquality/",
        async: false,
        success: function (data) {
            soilQualityList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/typeofland/",
        async: false,
        success: function (data) {
            typeofLandList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/slope/",
        async: false,
        success: function (data) {
            slopeList = data;
        }
    });

    jQuery.ajax({
        url: STUDIO_URL + "adjudicators/" + activeProject,
        async: false,
        success: function (data) {
            adjudicatorList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/personwithinterest/" + id,
        async: false,
        success: function (data) {
            nxtTokin = data;
            jQuery("#nxtTokinRowData").empty();
            if (nxtTokin.length != 0 && nxtTokin.length != undefined) {
                jQuery("#nxtTokinTemplate").tmpl(nxtTokin).appendTo("#nxtTokinRowData");
                jQuery("#hideTable1").show();
            } else {
                jQuery("#hideTable1").hide();
            }
        }
    });

    jQuery.ajax({
        url: "landrecords/deceasedperson/" + id,
        async: false,
        success: function (data) {
            deceasedPersonList = data;
            jQuery("#deceasedRowData").empty();
            if (deceasedPersonList.length != 0 && deceasedPersonList.length != undefined) {
                jQuery("#deceasedTemplate").tmpl(deceasedPersonList).appendTo("#deceasedRowData");
                jQuery("#hideTable").show();
            } else {
                jQuery("#hideTable").hide();
            }
        }
    });

    jQuery.ajax({
        url: "landrecords/multimedia/edit/" + id,
        async: false,
        success: function (data) {
            multimediaList = data;
            jQuery("#multimediaRowData").empty();
            if (data.length != 0 && data.length != undefined) {
                jQuery("#multimediaTemplate").tmpl(data).appendTo("#multimediaRowData");
            }
        }
    });

    $("#primary").val(id);
    $('#liNonNatural').hide();
    $('#liNatural').hide();
    $("#btnNewTenure").show();
    $("#btnNewNonNatural").show();
    $("#btnAddExistingPerson").hide();
    $(".addRepresentative").hide();
    $("#divRegisterDispute").show();
    associatednaturalPersonList = null;

    jQuery.ajax({
        url: "landrecords/socialtenure/" + id,
        async: false,
        success: function (data) {
            socialtenureList = data;
            jQuery("#tenureRowData").empty();
            jQuery("#naturalpersonRowData").empty();
            jQuery("#non_naturalpersonRowData").empty();
            jQuery("#associatedNaturalRowData").empty();

            if (data !== null && data.length > 0) {
                $("#btnNewTenure").hide();
                jQuery("#tenureinfoTemplate").tmpl(data[0]).appendTo("#tenureRowData");
                for (i = 0; i < data.length; i++) {
                    if (data[i].share_type.gid === 6) {
                        // Non-natural
                        $('#liNonNatural').show();
                        if (data[i].person_gid !== null && data[i].person_gid.active === true) {
                            jQuery("#non_naturalpersonTemplate").tmpl(data[i]).appendTo("#non_naturalpersonRowData");

                            $("#btnNewNonNatural").hide();
                            $(".addRepresentative").hide();

                            var poc_gid = data[i].person_gid.poc_gid;

                            if (poc_gid !== null) {
                                jQuery.ajax({
                                    url: "landrecords/naturalpersondata/" + poc_gid,
                                    async: false,
                                    success: function (result) {
                                        if (result === null || result === "") {
                                            $(".addRepresentative").show();
                                        }
                                        associatednaturalPersonList = result;
                                        jQuery("#naturalpersonTemplate_2").tmpl(associatednaturalPersonList).appendTo("#associatedNaturalRowData");
                                    }
                                });
                            } else {
                                // Show add representative button
                                $(".addRepresentative").show();
                            }
                        }
                    } else {
                        // Natural person
                        $('#liNatural').show();
                        if (data[i].person_gid !== null && data[i].person_gid.active === true) {
                            $(".showNatural").show();
                            jQuery("#naturalpersonTemplate").tmpl(data[i]).appendTo("#naturalpersonRowData");
                        }
                    }
                }
            }
        }
    });

    jQuery.ajax({
        url: "landrecords/editattribute/" + id,
        async: false,
        success: function (data) {
            editList = data;
            jQuery("#claimUka").text(data[0].propertyno === null ? "" : data[0].propertyno);
            jQuery("#claimType").text(data[0].claimType.name);
            jQuery("#hamletName").text(data[0].hamlet_Id.hamletName);
            jQuery("#claimNumber").val(data[0].claimNumber);
            jQuery("#claimStatus").val(data[0].status.workflowStatus);
            jQuery("#spatialid").text(data[0].usinStr);
            jQuery("#existing_use").empty();
            jQuery("#existing_use").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            claimType = data[0].claimType.code;

            jQuery.each(landUserList, function (i, landuseobj) {
                jQuery("#existing_use").append(jQuery("<option></option>").attr("value", landuseobj.landUseTypeId).text(landuseobj.landUseType));
            });

            jQuery("#proposed_use").empty();
            jQuery("#proposed_use").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(landUserList, function (i, landuseobj) {
                jQuery("#proposed_use").append(jQuery("<option></option>").attr("value", landuseobj.landUseTypeId).text(landuseobj.landUseType));
            });

            jQuery("#land_type").empty();
            jQuery("#land_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));

            jQuery.each(typeofLandList, function (i, landTypeobj) {
                jQuery("#land_type").append(jQuery("<option></option>").attr("value", landTypeobj.landTypeId).text(landTypeobj.landTypeValue));
            });

            jQuery("#witness_1").empty();
            jQuery("#witness_1").append(jQuery("<option></option>").attr("value", "").text("Please Select"));

            if (adjudicatorList.length !== 0 && adjudicatorList.length !== undefined) {
                jQuery.each(adjudicatorList, function (i, adjobj) {
                    jQuery("#witness_1").append(jQuery("<option></option>").attr("value", adjobj.adjudicatorName).text(adjobj.adjudicatorName));
                });
            }

            jQuery("#witness_2").empty();
            jQuery("#witness_2").append(jQuery("<option></option>").attr("value", "").text("Please Select"));

            if (adjudicatorList.length !== 0 && adjudicatorList.length !== undefined) {
                jQuery.each(adjudicatorList, function (i, adjobj) {
                    jQuery("#witness_2").append(jQuery("<option></option>").attr("value", adjobj.adjudicatorName).text(adjobj.adjudicatorName));
                });
            }

            if (data[0].landType !== null) {
                jQuery("#land_type").val(data[0].landType.landTypeId);
            }

            jQuery("#existing_use").val(0);
            jQuery("#proposed_use").val(0);

            if (data[0].existingUse !== null) {
                jQuery("#existing_use").val(data[0].existingUse.landUseTypeId);
            }

            if (data[0].proposedUse !== null) {
                jQuery("#proposed_use").val(data[0].proposedUse.landUseTypeId);
            }

            jQuery("#survey_date").val(data[0].surveyDate);

            if (adjudicatorList.length !== 0 && adjudicatorList.length !== undefined) {
                jQuery("#witness_1").val(data[0].witness_1);
                jQuery("#witness_2").val(data[0].witness_2);
            } else {
                jQuery("#witness_1").append(jQuery("<option></option>").attr("value", data[0].witness_1).text(data[0].witness_1));
                jQuery("#witness_2").append(jQuery("<option></option>").attr("value", data[0].witness_2).text(data[0].witness_2));
                jQuery("#witness_1").val(data[0].witness_1);
                jQuery("#witness_2").val(data[0].witness_2);
            }

            jQuery("#neighbor_north").val(data[0].neighbor_north);
            jQuery("#neighbor_south").val(data[0].neighbor_south);
            jQuery("#neighbor_east").val(data[0].neighbor_east);
            jQuery("#neighbor_west").val(data[0].neighbor_west);
        }
    });

    jQuery.ajax({
        url: "landrecords/disputes/" + id,
        async: false,
        success: function (data) {
            $("#divDisputes").empty();
            jQuery("#disputeTemplate").tmpl(data).appendTo("#divDisputes");
            if (data && data.length > 0) {
                $("#btnAddExistingPerson").show();
                $.each(data, function (i, dispute) {
                    if (dispute.status.code === 1) {
                        $("#divRegisterDispute").hide();
                        return;
                    }
                });
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to get list of disputes');
        }
    });

    editAttrDialog = $("#editattribute-dialog-form").dialog({
        autoOpen: false,
        height: 520,
        closed: false,
        cache: false,
        width: 880,
        resizable: false,
        modal: true,
        close: function () {
            editAttrDialog.dialog("destroy");
            $('#tabs').tabs("option", "active", $('#tabs a[href="#tabGeneralInfo"]').parent().index());
        }
    });

    // Hide general attributes and tabs for unclaimed and set read only mode
    if (claimType === CLAIM_TYPE_UNCLAIMED) {
        $("#tableGeneralAttributes tr:not(#rowClaimNumber)").hide();
        jQuery('#liTenure').hide();
        jQuery('#liNatural').hide();
        jQuery('#liNonNatural').hide();
        jQuery('#liPoi').hide();
        jQuery('#liDeceasedPerson').hide();
        jQuery('#liDisputes').hide();
        jQuery('#liMultimedia').hide();
        read = true;
    } else if (claimType === CLAIM_TYPE_EXISTING) {
        // Hide disputes for existing rights
        jQuery('#liDisputes').hide();
    } else {
        $("#tableGeneralAttributes tr").show();
        jQuery('#liTenure').show();
        jQuery('#liPoi').show();
        jQuery('#liDeceasedPerson').show();
        jQuery('#liDisputes').show();
        jQuery('#liMultimedia').show();
    }

    editAttrDialog.dialog("open");

    $('.gendisable').attr('disabled', false);
    $('.justread').attr('readonly', false);
    $(".showNon").show();
    $(".showAssociate").show();
    $(".hideNatural").hide();
    $(".hideAssociate").hide();
    $('#associatedNaturalTable td:nth-child(7)').hide();
    $(".showNatural").show();
    $('#naturalTable td:nth-child(9)').hide();
    $('#naturalTable td:nth-child(7)').show();
    $('#naturalTable td:nth-child(8)').show();
    $('#naturalTable td:nth-child(10)').show();
    $('#naturalTable td:nth-child(11)').show();
    $(".hideNon").hide();
    $('#non_naturalTable td:nth-child(8)').hide();
    $(".showTenure").show();
    $('#tenureTable td:nth-child(7)').show();
    $('#tenureTable td:nth-child(8)').hide();
    $('#tenureTable th:nth-child(8)').hide();
    $(".showMul").show();
    $('#multimediaTable td:nth-child(6)').show();
    $('#multimediaTable td:nth-child(5)').show();
    $(".showkin").show();
    $('#nxtTokinTable td:nth-child(5)').show();
    $('#nxtTokinTable td:nth-child(6)').show();
    $(".hidekin").hide();
    $('#nxtTokinTable td:nth-child(7)').hide();
    $(".hidedeceased").hide();
    $('#deceasedTable td:nth-child(6)').hide();
    $(".showdeceased").show();
    $('#deceasedTable td:nth-child(5)').show();
    $('#deceasedTable td:nth-child(4)').show();
    $(".hideTenure").hide();
    $('#tenureTable td:nth-child(9)').hide();
    $(".hideMul").hide();
    $('#multimediaTable td:nth-child(8)').hide();
    $(".showMul").show();
    $(".showAddPerson").show();
    $(".hideNew").show();

    if (read) {
        $('.ui-dialog-buttonpane button').button().hide();
        $(".hideNatural").show();
        $(".hideAssociate").show();
        $('#associatedNaturalTable td:nth-child(7)').show();
        $(".hideNon").show();
        $('#non_naturalTable td:nth-child(8)').show();
        $(".hideTenure").show();
        $('#tenureTable td:nth-child(9)').show();
        $(".hideMul").show();
        $('#multimediaTable td:nth-child(8)').show();
        jQuery('.gendisable').attr('disabled', true);
        jQuery('.justread').attr('readonly', true);
        $(".showNatural").hide();
        $('#naturalTable td:nth-child(9)').show();
        $('#naturalTable td:nth-child(7)').hide();
        $('#naturalTable td:nth-child(8)').hide();
        $('#naturalTable td:nth-child(10)').hide();
        $('#naturalTable td:nth-child(11)').hide();
        $(".showAssociate").hide();
        $('#associatedNaturalTable td:nth-child(6)').hide();
        $('#associatedNaturalTable td:nth-child(8)').hide();
        $('#associatedNaturalTable td:nth-child(9)').hide();
        $(".showNon").hide();
        $('#non_naturalTable td:nth-child(5)').hide();
        $('#non_naturalTable td:nth-child(6)').hide();
        $(".showTenure").hide();
        $('#tenureTable td:nth-child(7)').hide();
        $('#tenureTable td:nth-child(8)').hide();
        $('#tenureTable th:nth-child(8)').hide();
        $(".showMul").hide();
        $('#multimediaTable td:nth-child(6)').hide();
        $('#multimediaTable td:nth-child(5)').hide();
        $(".hidekin").show();
        $('#nxtTokinTable td:nth-child(7)').show();
        $(".showkin").hide();
        $('#nxtTokinTable td:nth-child(5)').hide();
        $('#nxtTokinTable td:nth-child(6)').hide();
        $(".hidedeceased").show();
        $('#deceasedTable td:nth-child(6)').show();
        $(".showdeceased").hide();
        $('#deceasedTable td:nth-child(5)').hide();
        $('#deceasedTable td:nth-child(4)').hide();
        $(".showAddPerson").hide();
        $(".hideNew").hide();
        $(".addRepresentative").hide();
        $("#btnNewNonNatural").hide();
        $("#btnAddExistingPerson").hide();
        $("#btnNewTenure").hide();
        $("#divRegisterDispute").hide();
    }
}

function editHamlet(usin) {
    jQuery.ajax({
        url: "landrecords/hamletbyusin/" + usin,
        async: false,
        success: function (data) {
            selectedHamlet = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/hamletname/" + activeProject,
        async: false,
        success: function (data) {
            hamletList = data;
            jQuery("#cbxHamlets").empty();
            if (selectedHamlet[0].hamlet_Id != null) {
                jQuery.each(hamletList, function (i, hamletobj) {
                    jQuery("#cbxHamlets").append(jQuery("<option></option>").attr("value", hamletobj.id).text(hamletobj.hamletName));
                });
                $("#cbxHamlets").val(selectedHamlet[0].hamlet_Id.id);
            } else {
                jQuery("#cbxHamlets").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
                jQuery.each(hamletList, function (i, hamletobj) {
                    jQuery("#cbxHamlets").append(jQuery("<option></option>").attr("value", hamletobj.id).text(hamletobj.hamletName));
                });
            }
        }
    });

    genAttrDialog = $("#attribute-dialog-form").dialog({
        autoOpen: false,
        height: 200,
        width: 234,
        resizable: true,
        modal: true,
        buttons: {
            "Update": function () {
                if (selectedHamlet[0].hamlet_Id !== null)
                    updateHamlet(usin, selectedHamlet[0].hamlet_Id.id);
                else {
                    updateHamlet(usin, "0");
                }
            }
        }
    });
    genAttrDialog.dialog("open");
}

function referClaim(usin) {
    jConfirm('Are you sure you want to refer this claim to VC/VLC?', 'Confirmation', function (response) {
        if (response) {
            $.ajax({
                url: "landrecords/refer/" + usin,
                async: false,
                success: function (result) {
                    if (result === RESPONSE_OK) {
                        jAlert('Claim has been successfully referred to VC/VLC', 'Info');
                        refreshLandRecords();
                    } else {
                        jAlert(result, 'Error');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Failed to refer claim');
                }
            });
        }
    });
}

function markClaimValidated(usin) {
    jConfirm('Are you sure you want to mark this claim as validated?<br>UKA number will be assigned.', 'Confirmation', function (response) {
        if (response) {
            $.ajax({
                url: "landrecords/makevalidated/" + usin,
                async: false,
                success: function (result) {
                    if (result === "" || result.length === 0) {
                        jAlert('Claim has been successfully marked as validated.');
                        if (searchRecords !== null) {
                            spatialSearch(records_from);
                        } else {
                            spatialRecords(records_from);
                        }
                    } else {
                        showErrors(result);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Failed to refer claim');
                }
            });
        }
    });
}

function refreshLandRecords() {
    if (searchRecords !== null) {
        spatialSearch(records_from);
    } else {
        spatialRecords(records_from);
    }
}

function updateHamlet(usin, hamId) {
    if ($("#cbxHamlets").val() === "" + hamId || $("#cbxHamlets").val() === "0") {
        jAlert("Select another hamlet", "Alert");
    } else {
        jQuery("#primeryky").val(usin);
        jQuery.ajax({
            type: "POST",
            url: "landrecords/updatehamlet/" + activeProject,
            data: jQuery("#addAttributeformID").serialize(),
            success: function (result) {
                if (result) {
                    jAlert("Data Successfully saved", "Hamlet");
                    refreshLandRecords();
                    genAttrDialog.dialog("destroy");
                } else {
                    jAlert("Request not completed");
                }
            }
        });
    }
}

function updateattributesGen() {
    $("#editAttributeformID").validate({
        rules: {
            claimNumber: "required",
            survey_date: "required",
            neighbor_north: "required",
            witness_1: {required: true},
            neighbor_south: "required",
            witness_2: {required: true},
            neighbor_east: "required",
            neighbor_west: "required"
        },
        messages: {
            claimNumber: "Enter claim number",
            survey_date: "Enter claim date",
            neighbor_north: "Please enter neighbour name",
            witness_1: "Please select adjudicator",
            neighbor_south: "Please enter neighbour name",
            witness_2: "Please select adjudicator",
            neighbor_east: "Please enter neighbour name",
            neighbor_west: "Please enter neighbour name"
        }
    });

    if ($("#editAttributeformID").valid()) {
        if ($('#proposed_use').val() === "0" && (claimType === CLAIM_TYPE_NEW || claimType === CLAIM_TYPE_EXISTING)) {
            alert("Please select Proposed Use");
            return;
        }

        if ($('#land_type').val() !== "0") {
            updateattributes();
        } else {
            alert("Please select Land Type");
        }
    } else {
        jAlert("Please Fill Mandatory Details", "Alert");
    }
}

function updateattributes() {
    var length_gen = attributeList.length;
    jQuery("#general_length").val(0);
    if (length_gen != 0 && length_gen != undefined)
        jQuery("#general_length").val(length_gen);
    jQuery.ajax({
        type: "POST",
        url: "landrecords/updateattributes",
        data: jQuery("#editAttributeformID").serialize(),
        success: function (result) {
            if (result) {
                jAlert('Attributes were successfully updated');
                editAttrDialog.dialog("destroy");
            } else {
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
}

function approveClaim(id) {
    jConfirm('Do you want to <strong>Approve</strong> selected claim?<br>No further changes will be possible.', 'Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: "GET",
                url: "landrecords/approve/" + id,
                success: function (result) {
                    if (result === "" || result.length === 0) {
                        jAlert('Claim has been successfully approved.');
                        if (searchRecords !== null) {
                            spatialSearch(records_from);
                        } else {
                            spatialRecords(records_from);
                        }
                    } else {
                        showErrors(result);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Failed to approve claim');
                }
            });
        }
    });
}

function rejectClaim(id) {
    jConfirm('Do you want to <strong>Deny</strong> selected claim?<br>No further changes will be possible.', 'Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: "GET",
                url: "landrecords/reject/" + id,
                success: function (result) {
                    if (result) {
                        jAlert('Claim has been successfully denied.');
                        if (searchRecords !== null) {
                            spatialSearch(records_from);
                        } else {
                            spatialRecords(records_from);
                        }
                    } else {
                        jAlert('Failed to deny claim');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Failed to deny claim');
                }
            });
        }
    });
}

function showErrors(errors) {
    if (errors !== null && errors.length > 0) {
        combinedErrors = "";
        for (i = 0; i < errors.length; i++) {
            combinedErrors = combinedErrors + "- " + errors[i] + "<br>"
        }
        jAlert(combinedErrors);
    }
}

function clearSearch(){
    $("#usinstr_id").val("");
    $("#claim_type").val("");
    $("#uka_id").val("");
    $("#status_id").val("0");
    $("#from_id").val("");
    $("#to_id").val("");
    search();
}

function search() {
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "landrecords/search/" + activeProject,
        data: jQuery("#landrecordsform").serialize(),
        success: function (result) {
            searchRecords = result;
        }
    });

    jQuery.ajax({
        type: "POST",
        url: "landrecords/search/" + activeProject + "/" + 0,
        data: jQuery("#landrecordsform").serialize(),
        success: function (result) {
            if (result.length != undefined && result.length != 0) {
                if (result != "") {
                    records_from = 0;
                    jQuery("#landRecordsRowData").empty();
                    jQuery("#landRecordsAttrTemplate").tmpl(result).appendTo("#landRecordsRowData");

                    jQuery("#records_from").val(records_from + 1);
                    jQuery("#records_to").val(searchRecords);
                    if (records_from + 20 <= searchRecords)
                        $('#records_to').val(records_from + 20);
                    jQuery('#records_all').val(searchRecords);

                    $("#landRecordsTable").trigger("update");
                    $("#landRecordsTable").tablesorter({
                        sortList: [[0, 1], [1, 0]]
                    });
                } else {
                    jAlert("No Data Available", "Search");
                }
            } else {
                jAlert("No Data Available", "Search");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
}

function editNaturalData(id, viewOnly) {
    jQuery(".hidden_alias").hide();
    displayAttributeCategory(2, id);

    $.ajax({
        url: "landrecords/idtype/",
        async: false,
        success: function (data) {
            idTypesList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/gendertype/",
        async: false,
        success: function (data) {
            genderList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/personsubtype/",
        async: false,
        success: function (data) {
            person_subtype = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/maritalstatus/",
        async: false,
        success: function (data) {
            maritalList = data;
        }
    });

    $("#imgPersonPhoto").attr("src", "");

    jQuery.ajax({
        url: "landrecords/citizenship/",
        async: false,
        success: function (data) {
            jQuery("#citizenship").empty();
            jQuery("#citizenship").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(data, function (i, citizenobj) {
                if (citizenobj.id != 0)
                    jQuery("#citizenship").append(jQuery("<option></option>").attr("value", citizenobj.id).text(citizenobj.citizenname));
            });
        }
    });

    jQuery.ajax({
        url: "landrecords/naturalperson/" + id,
        async: false,
        success: function (data) {
            jQuery("#gender").empty();
            jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(genderList, function (i, genderobj) {
                jQuery("#gender").append(jQuery("<option></option>").attr("value", genderobj.genderId).text(genderobj.gender));

            });

            jQuery("#idType").empty();
            jQuery("#idType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(idTypesList, function (i, idType) {
                jQuery("#idType").append(jQuery("<option></option>").attr("value", idType.code).text(idType.name));

            });

            jQuery("#marital_status").empty();
            jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(maritalList, function (i, maritalobj) {
                jQuery("#marital_status").append(jQuery("<option></option>").attr("value", maritalobj.maritalStatusId).text(maritalobj.maritalStatus));

            });

            jQuery("#person_subType").empty();
            jQuery("#person_subType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(person_subtype, function (i, personSubtypeobj) {
                if (personSubtypeobj.person_type_gid != 1 && personSubtypeobj.person_type_gid != 2)
                    jQuery("#person_subType").append(jQuery("<option></option>").attr("value", personSubtypeobj.person_type_gid).text(personSubtypeobj.personType));

            });

            if (data !== null && data !== "" && data !== undefined) {
                if (data[0].marital_status !== null) {
                    jQuery("#marital_status").val(data[0].marital_status.maritalStatusId);
                }
                if (data[0].gender !== null) {
                    jQuery("#gender").val(data[0].gender.genderId);
                }
                if (data[0].idType !== null) {
                    jQuery("#idType").val(data[0].idType.code);
                }
                jQuery("#idNumber").val(data[0].idNumber);
                if (data[0].personSubType !== null) {
                    jQuery("#person_subType").val(data[0].personSubType.person_type_gid);
                }
                if (data[0].citizenship_id !== null) {
                    jQuery("#citizenship").val(data[0].citizenship_id.id);
                }

                jQuery("#personResident").prop( "checked", data[0].resident_of_village);
                jQuery("#natural_key").val(id);
                jQuery("#name").val(data[0].alias);
                jQuery("#fname").val(data[0].firstName);
                jQuery("#mname").val(data[0].middleName);
                jQuery("#lname").val(data[0].lastName);
                jQuery("#mobile_natural").val(data[0].mobile);
                $("#shareSize").val(data[0].share);

                if (data[0].dob !== null) {
                    jQuery("#dob").val(formatDate(data[0].dob));
                } else {
                    jQuery("#dob").val("");
                }

                $("#dob").live('click', function () {
                    $(this).datepicker({dateFormat: 'yy-mm-dd'}).focus();
                });

                $("#imgPersonPhoto").attr("src", "landrecords/personphoto/" + id + "?rnd=" + Math.floor(Math.random() * 1000));
            }
        }
    });

    naturalPersonDialog = $("#naturalperson-dialog-form").dialog({
        autoOpen: false,
        height: 450,
        width: 725,
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                updateAttributeNaturalPerson(false);
            }
        },
        close: function () {
            naturalPersonDialog.dialog("destroy");
            $('#tab-natural').tabs("option", "active", $('#tab-natural a[href="#tabs-1"]').parent().index());
        }
    });

    naturalPersonDialog.dialog("open");
    jQuery('.justread').attr('readonly', false);
    jQuery('.justdisable').attr('disabled', false);

    if (read || viewOnly) {
        $('.ui-dialog-buttonpane button').button().hide();
        jQuery('.justread').attr('readonly', true);
        jQuery('.justdisable').attr('disabled', true);
    }
}

function updateAttributeNaturalPerson(newPerson) {
    $("#editNaturalPersonformID").validate({
        rules: {
            fname: "required",
            lname: "required",
            idType: "required",
            idNumber: "required",
            dob: "required"
        },
        messages: {
            fname: "Please enter  First Name",
            lname: "Please enter Last Type",
            idType: "Select ID type",
            idNumber: "Enter ID number",
            dob: "Enter date of birth"
        },
        ignore: []
    });

    if ($("#editNaturalPersonformID").valid()) {
        if (calculateAge($("#dob").val()) < 18 && $('#person_subType').val() != "3") {
            jAlert("person age must be more than or equal to 18", "Info");
            return;
        }

        if ($("#person_subType").val() == "0") {
            alert("Select Owner type");
            return;
        }

        if ($("#idType").val() == "0") {
            alert("Select ID type");
        }

        if ($('#gender').val() == "0") {
            alert("Please select gender");
            return;
        }

        if ($('#marital_status').val() == "0") {
            alert("Please select Marital Status");
            return;
        }

        if (newPerson) {
            updateNewNaturalPerson();
            naturalPersonDialog.dialog("destroy");
        } else {
            updateNaturalPerson();
        }

    } else {
        jAlert("Please Fill Mandatory fields in all tabs", "Message");
    }
}

function updateNaturalPerson() {
    var id = editList[0].usin;
    project = editList[0].project;
    var length_natural = attributeList.length;
    jQuery("#natual_length").val(0);
    if (length_natural != 0 && length_natural != undefined)
        jQuery("#natual_length").val(length_natural);
    jQuery("#natural_usin").val(id);
    jQuery("#projectname_key").val(project);

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatenatural",
        data: jQuery("#editNaturalPersonformID").serialize(),
        success: function (data) {
            if (data) {
                naturalPersonDialog.dialog("destroy");
                jAlert('Data Sucessfully Saved', ' Natural Person');
                editAttribute(id);
            } else {
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
}

function editNonNatural(id) {
    displayAttributeCategory(5, id);
    jQuery.ajax({
        url: "landrecords/grouptype/",
        async: false,
        success: function (data) {
            groupTypeList = data;
        }
    });

    // reset form
    $("#nonnatural_usin").val(editList[0].usin);
    $("#group_type").empty();
    $("#group_type").append($("<option></option>").attr("value", 0).text("Please Select"));
    $.each(groupTypeList, function (i, groupTypeobj) {
        $("#group_type").append($("<option></option>").attr("value", groupTypeobj.groupId).text(groupTypeobj.groupValue));
    });
    $("#institution").val("");
    $("#mobile_no").val("");
    $("#address").val("");
    $("#non_natural_key").val(id);
    $("#poc_id").val(0);
    $("#mobileGroupId").val("");
    $("#group_type").val(0);

    if (id > 0) {
        jQuery.ajax({
            url: "landrecords/nonnaturalperson/" + id,
            async: false,
            success: function (data) {
                $("#institution").val(data[0].institutionName);
                $("#mobile_no").val(data[0].phoneNumber);
                $("#address").val(data[0].address);
                $("#non_natural_key").val(data[0].person_gid);
                if (data[0].poc_gid !== null && data[0].poc_gid !== "") {
                    $("#poc_id").val(data[0].poc_gid);
                }
                $("#mobileGroupId").val(data[0].mobileGroupId);

                if (data[0].groupType !== null) {
                    jQuery("#group_type").val(data[0].groupType.groupId);
                }
            }
        });
    }

    nonnaturalPersonDialog = $("#non_naturalperson-dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                updateAttributeNonNaturalPerson();
            }
        },
        close: function () {
            nonnaturalPersonDialog.dialog("destroy");
            $('#tab-nonnatural').tabs("option", "active", $('#tab-nonnatural a[href="#tabs-3"]').parent().index());
        }
    });

    nonnaturalPersonDialog.dialog("open");
    jQuery('.read-non').attr('readonly', false);
    jQuery('.disablenon').attr('disabled', false);

    if (read) {
        $('.ui-dialog-buttonpane button').button().hide();
        jQuery('.read-non').attr('readonly', true);
        jQuery('.disablenon').attr('disabled', true);
    }
}

function updateAttributeNonNaturalPerson() {
    $("#editNonNaturalPersonformID").validate({
        rules: {
            institution: "required",
            mobile_no: {
                required: true,
                number: true
            },
            address: "required"
        },
        messages: {
            institution: "Please enter Institute Name",
            address: "Please enter Address",
            mobile_no: "Please enter Numeric value for Phone Number"
        },
        ignore: []
    });

    if ($("#editNonNaturalPersonformID").valid()) {
        if ($('#group_type').val() !== 0) {
            updateNonNaturalPerson();
        } else {
            alert("Please select type");
        }
    } else {
        jAlert("Please Fill Mandatory fields in all tabs", "Message");
    }
}

function updateNonNaturalPerson() {
    var id = editList[0].usin;
    var length_nonnatural = attributeList.length;
    jQuery("#nonnatual_length").val(0);
    if (length_nonnatural !== 0 && length_nonnatural !== undefined)
        jQuery("#nonnatual_length").val(length_nonnatural);
    jQuery("#projectname_key2").val(project);

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatenonnatural",
        data: jQuery("#editNonNaturalPersonformID").serialize(),
        success: function (data) {
            if (data) {
                nonnaturalPersonDialog.dialog("destroy");
                editAttribute(id);
                jAlert('Data Sucessfully Saved', 'Non Natural Person');
            } else {
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });

}

function editTenure(id) {
    displayAttributeCategory(4, id);
    jQuery.ajax({
        url: "landrecords/tenuretype/",
        async: false,
        success: function (data) {
            tenuretypeList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/occupancytype/",
        async: false,
        success: function (data) {
            occtypeList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/relationshiptypes/",
        async: false,
        success: function (data) {
            relationshipTypes = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/acquisitiontypes/",
        async: false,
        success: function (data) {
            acquisitionTypes = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/tenureclass/",
        async: false,
        success: function (data) {
            tenureclassList = data;
        }
    });

    // Reset tenure fields
    $("#tenure_key").val(id);
    $("#usin").val($("#primary").val());
    jQuery('.read-tenure').attr('disabled', false);
    jQuery('.tenure-read').attr('readonly', false);
    $("#tenureclass_id").empty();
    $("#lstAcquisitionTypes").empty();
    $("#tenure_type").empty();
    $("#lstRelationshipTypes").empty();
    $("#txtCertNumber").val("");
    $("#txtCertDate").val("");
    $("#tenureDuration").val("");
    $("#txtJuridicalArea").val("");
    $("#txtCertNumber").val("");

    $("#tenure_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
    $("#lstAcquisitionTypes").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
    $("#lstRelationshipTypes").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));

    $('#lstRelationshipTypes').attr('disabled', false);
    $('#tenureclass_id').attr('disabled', true);

    if (claimType !== null) {
        if (claimType === CLAIM_TYPE_EXISTING) {
            $("#txtCertDate").live('click', function () {
                $(this).datepicker({dateFormat: 'yy-mm-dd'}).focus();
            });
        } else {
            $('#txtCertNumber').attr('disabled', true);
            $('#txtCertDate').attr('disabled', true);
            $("#txtJuridicalArea").attr('disabled', true);
        }
    }

    jQuery.each(tenureclassList, function (i, tenureclassobj) {
        if (tenureclassList[i].active)
            jQuery("#tenureclass_id").append(jQuery("<option></option>").attr("value", tenureclassobj.tenureId).text(tenureclassobj.tenureClass));
    });

    $("#tenureclass_id").val("2");

    jQuery.each(tenuretypeList, function (i, tenureobj) {
        jQuery("#tenure_type").append(jQuery("<option></option>").attr("value", tenureobj.gid).text(tenureobj.shareType));
    });

    jQuery.each(acquisitionTypes, function (i, acquisitionType) {
        jQuery("#lstAcquisitionTypes").append(jQuery("<option></option>").attr("value", acquisitionType.code).text(acquisitionType.name));
    });

    jQuery.each(relationshipTypes, function (i, relationshipType) {
        jQuery("#lstRelationshipTypes").append(jQuery("<option></option>").attr("value", relationshipType.code).text(relationshipType.name));
    });

    $('#lstRelationshipTypes').attr('disabled', true);
    jQuery("#tenure_type").live('change', function () {
        // Allow relationship selection only for joint tenancy (3)
        if ($(this).val() === "3")
            $('#lstRelationshipTypes').attr('disabled', false);
        else {
            $('#lstRelationshipTypes').val(0);
            $('#lstRelationshipTypes').attr('disabled', true);
        }
    });

    socialEditTenureList = null;

    if (id > 0) {
        jQuery.ajax({
            url: "landrecords/socialtenure/edit/" + id,
            async: false,
            success: function (data) {
                if (data === "") {
                    return;
                }

                socialEditTenureList = data;
                jQuery("#tenure_key").val(data[0].gid);
                jQuery("#usin").val(data[0].usin);

                if (data[0].certNumber !== null) {
                    jQuery("#txtCertNumber").val(data[0].certNumber);
                }

                if (data[0].certIssueDate !== null) {
                    jQuery("#txtCertDate").val(formatDate(data[0].certIssueDate));
                }

                if (data[0].tenureDuration !== null) {
                    jQuery("#tenureDuration").val(data[0].tenureDuration);
                }

                if (data[0].juridicalArea !== null) {
                    jQuery("#txtJuridicalArea").val(data[0].juridicalArea);
                }

                if (data[0].share_type !== null) {
                    jQuery("#tenure_type").val(data[0].share_type.gid);
                    jQuery("#tenure_type").attr('disabled', true);
                    // Allow relationship selection only for joint tenancy (3)
                    if (data[0].share_type.gid === 3)
                        $('#lstRelationshipTypes').attr('disabled', false);
                }

                if (data[0].tenureclassId !== null) {
                    jQuery("#tenureclass_id").val(data[0].tenureclassId.tenureId);
                }

                if (data[0].acquisitionType !== null) {
                    jQuery("#lstAcquisitionTypes").val(data[0].acquisitionType.code);
                }

                if (data[0].relationshipType !== null) {
                    jQuery("#lstRelationshipTypes").val(data[0].relationshipType.code);
                }
            }
        });
    }

    tenureDialog = $("#tenureinfo-dialog-form").dialog({
        autoOpen: false,
        height: 450,
        width: 500,
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                updateAttributeTenure();
            }
        },
        close: function () {
            tenureDialog.dialog("destroy");
        }
    });

    tenureDialog.dialog("open");

    if (read) {
        $('.ui-dialog-buttonpane button').button().hide();
        jQuery('.read-tenure').attr('disabled', true);
        jQuery('.tenure-read').attr('readonly', true);
        $('#tenureclass_id').attr('disabled', true);
    }
}

function updateAttributeTenure() {
    $("#edittenureinfoformID").validate({
        rules: {
            tenure_type: "required",
            tenureDuration: "required"
        },
        messages: {
            tenure_type: "Please enter Tenure Type",
            tenureDuration: "Enter Length of Occupancy"
        }
    });

    if ($("#edittenureinfoformID").valid()) {
        if ($("#tenure_type").val() == 0) {
            alert("Please select Type of Right");
        } else if ($("#lstAcquisitionTypes").val() == 0) {
            alert("Select Acquisition Type");
        } else {
            updateTenure();
        }
    } else {
        jAlert("Please Fill Mandatory fields in all tabs", "Message");
    }
}

function updateTenure() {
    var id = editList[0].usin;
    var length = attributeList.length;
    jQuery("#tenure_length").val(0);
    if (length !== 0 && length !== undefined)
        jQuery("#tenure_length").val(length);

    jQuery("#projectname_key3").val(project);

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatetenure",
        data: jQuery("#edittenureinfoformID").serialize(),
        success: function (data) {
            if (data) {
                tenureDialog.dialog("destroy");
                editAttribute(id);
                jAlert('Data Sucessfully Saved', 'Tenure Info');
            } else {
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
}

function editMultimedia(id, usin, reloadPropForm) {
    displayAttributeCategory(3, id);

    jQuery.ajax({
        url: "landrecords/doctypes/",
        async: false,
        success: function (data) {
            docTypesList = data;
        }
    });

    // Reset form
    $("#docType").attr('readonly', false);
    $("#docName").attr('readonly', false);
    $("#docType").attr('disabled', false);
    $("#docName").attr('disabled', false);
    $("#docType").empty();
    $("#docType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
    $.each(docTypesList, function (i, docType) {
        if (docTypesList[i].active)
            $("#docType").append(jQuery("<option></option>").attr("value", docType.code).text(docType.name));
    });
    $("#recordation").val(formatDate(new Date().getTime()));
    $("#comments_multimedia").val("");
    $("#primary_key").val(id);
    if (usin === 0)
        $("#usink").val(editList[0].usin);
    else
        $("#usink").val(usin);
    $("#docName").val("");
    $("#divNewFile").show();

    if (id > 0) {
        $("#divNewFile").hide();
        $.ajax({
            url: "landrecords/multimedia/" + id,
            async: false,
            success: function (data) {
                $("#recordation").val(data[0].recordation);
                $("#comments_multimedia").val(data[0].comments);
                $("#primary_key").val(data[0].gid);
                $("#usink").val(data[0].usin);
                $("#docName").val(data[0].entity_name);
                if (data[0].documentType !== null) {
                    $("#docType").val(data[0].documentType.code);
                }
            }
        });
    }

    multimediaDialog = $("#multimedia-dialog-form").dialog({
        autoOpen: false,
        height: 450,
        width: 350,
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                updateMultimedia(reloadPropForm);
            }
        },
        close: function () {
            multimediaDialog.dialog("destroy");
        }
    });

    multimediaDialog.dialog("open");
    $('.read-mul').attr('readonly', false);
    $('.disablemul').attr('disabled', false);

    if (reloadPropForm && read) {
        $('.ui-dialog-buttonpane button').button().hide();
        $('.read-mul').attr('readonly', true);
        $('.disablemul').attr('disabled', true);
        $("#docType").attr('disabled', true);
        $("#docName").attr('disabled', true);
        $("#docType").attr('readonly', true);
        $("#docName").attr('readonly', true);
    }
}

function updateMultimedia(reloadPropForm) {
    var length_multimedia = attributeList.length;
    if (length_multimedia !== undefined)
        length_multimedia = 0;

    var file = $('#fileUploadNewDowcument')[0].files[0];

    // Validate
    if ($("#divNewFile").is(":visible")) {
        if (typeof (file) === "undefined") {
            jAlert('Please select the file to upload', 'Warning');
            return;
        } else if ((file.size) / 1024 >= 5120) {
            jAlert('Please select file with size below 5Mb', 'Warning');
            return;
        }
    }

    if ($("#docName").val() === "") {
        jAlert('Please enter document name', 'Warning');
        return;
    }

    var formData = new FormData();

    formData.append("newFile", file);
    formData.append("primary_key", $("#primary_key").val());
    formData.append("docType", $("#docType").val());
    formData.append("docName", $("#docName").val());
    formData.append("comments_multimedia", $("#comments_multimedia").val());
    formData.append("usink", $("#usink").val());
    formData.append("projectname_multimedia_key", project);
    formData.append("multimedia_length", length_multimedia);

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatemultimedia",
        mimeType: "multipart/form-data",
        contentType: false,
        cache: false,
        processData: false,
        data: formData,
        success: function (data) {
            if (data) {
                multimediaDialog.dialog("destroy");
                if (reloadPropForm) {
                    editAttribute(editList[0].usin);
                }
                jAlert('Document has been sucessfully saved', 'Info');
            } else {
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to save document');
        }
    });
}

var deleteMultimedia = function (id, name) {
    var usinid = editList[0].usin;
    jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: 'GET',
                url: "landrecords/delete/" + id,
                success: function (result) {
                    if (result == true) {
                        jAlert('Data Successfully Deleted', 'Multimedia');
                        editAttribute(usinid);
                    }

                    if (result == false) {
                        jAlert('Data can not be deleted..already used by project', 'Multimedia');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Request not completed');
                }
            });
        }
    });
};

var deleteNatural = function (id, name) {
    var usinid = editList[0].usin;

    if (socialtenureList.length == 1) {
        jAlert('Person can not be deleted..single person exists', 'Person');
    } else {
        jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {
            if (response) {
                jQuery.ajax({
                    type: 'GET',
                    url: "landrecords/deleteNatural/" + id,
                    success: function (result) {
                        resultDeleteNatural = result;
                        if (resultDeleteNatural == true) {
                            jAlert('Data Successfully Deleted', 'Person');
                            editAttribute(usinid);
                        }

                        if (resultDeleteNatural == false) {
                            jAlert('Data can not be deleted..already used by project', 'Person');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        jAlert('Request not completed');
                    }
                });
            }
        });
    }
};

var deleteNonNatural = function (id, name) {
    var usinid = editList[0].usin;
    jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: 'GET',
                url: "landrecords/deletenonnatural/" + id,
                success: function (result) {
                    if (result == true) {
                        jAlert('Data Successfully Deleted', 'Info');
                        $('#tabs').tabs("option", "active", $('#tabs a[href="#tabGeneralInfo"]').parent().index());
                        editAttribute(usinid);
                    }

                    if (result == false) {
                        jAlert('Data Can not be deleted..Used by Project', 'Info');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Request not completed');
                }
            });
        }
    });
};

var deleteTenure = function (id) {
    var usinid = editList[0].usin;
    jConfirm('Are You Sure You Want To Delete', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: 'GET',
                url: "landrecords/deleteTenure/" + id,
                success: function (result) {
                    if (result == true) {
                        jAlert('Data Successfully Deleted', 'Multimedia');
                        editAttribute(usinid);
                    }

                    if (result == false) {
                        jAlert('Data Can not be deleted..Used by Project', 'Multimedia');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Request not completed');
                }
            });
        }
    });
};

function displayAttributeCategory(id, gid) {
    jQuery.ajax({
        url: "landrecords/attributedata/" + id + "/" + gid + "/",
        async: false,
        success: function (data) {
            attributeList = data;
        }
    });

    jQuery("#customnewnatural-div").empty();
    jQuery("#custom-div").empty();
    jQuery("#customtenure-div").empty();
    jQuery("#customnatural-div").empty();
    jQuery("#customnonnatural-div").empty();
    jQuery("#customgeneral-div").empty();
    jQuery("#custommultimedia-div").empty();

    if (attributeList.length > 0) {
        $(".datepicker").datepicker();
        $(".datepicker").live('click', function () {
            $(this).datepicker({}).focus();
        });

        for (var i = 0; i < attributeList.length; i++) {
            selectedUnitText = attributeList[i].alias;
            selectedUnitValue = attributeList[i].value;
            var datatype = attributeList[i].datatypeid;
            selectedUnitValues = attributeList[i].attributevalueid;

            if (id == 1) {
                if (datatype == 2) {
                    jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_general' + "" + i + "" + '" class="input-medium justread datepicker"  name="alias_general' + "" + i + "" + '" type="text" value=""/><input  id="alias_general_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_general_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_general' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 3) {
                    jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><select  id="alias_general' + "" + i + "" + '" class="input-medium gendisable" name="alias_general' + "" + i + "" + '" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_general_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_general_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_general' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 4) {
                    jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_general' + "" + i + "" + '" type="number" pattern="[0-9]" class="input-medium justread" name="alias_general' + "" + i + "" + '" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_general_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_general_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                } else {
                    jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_general' + "" + i + "" + '" type="text" class="input-medium justread" name="alias_general' + "" + i + "" + '" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_general_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_general_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                }
            } else if (id == 4) {
                if (datatype == 2) {
                    jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_tenure' + "" + i + "" + '" class="input-medium tenure-read" name="alias_tenure' + "" + i + "" + '" type="Date" value=""/><input  id="alias_tenure_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_tenure_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_tenure' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 3) {
                    jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><select  id="alias_tenure' + "" + i + "" + '" class="input-medium read-tenure" name="alias_tenure' + "" + i + "" + '" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_tenure_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_tenure_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_tenure' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 4) {
                    jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_tenure' + "" + i + "" + '" type="number" pattern="[0-9]" class="input-medium tenure-read" name="alias_tenure' + "" + i + "" + '" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_tenure_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_tenure_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                } else {
                    jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_tenure' + "" + i + "" + '" class="inputField01 splitpropclass tenure-read" name="alias_tenure' + "" + i + "" + '" type="text" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_tenure_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_tenure_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                }
            } else if (id == 2) {
                if (datatype == 2) {
                    jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_natural' + "" + i + "" + '" class="input-medium justread" name="alias_natural' + "" + i + "" + '" type="Date" value=""/><input  id="alias_natural_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_natural_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_natural' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 3) {
                    jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><select  id="alias_natural' + "" + i + "" + '" class="input-medium justdisable" name="alias_natural' + "" + i + "" + '" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_natural_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_natural_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_natural' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 4) {
                    jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_natural' + "" + i + "" + '" type="number" pattern="[0-9]" class="input-medium justread" name="alias_natural' + "" + i + "" + '" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_natural_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_natural_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                } else {
                    jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_natural' + "" + i + "" + '" class="inputField01 splitpropclass justread" name="alias_natural' + "" + i + "" + '"  type="text" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_natural_key' + "" + i + "" + '"  name="alias_natural_key' + "" + i + "" + '"  type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                }
            } else if (id == 5) {
                if (datatype == 2) {
                    jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_nonnatural' + "" + i + "" + '" class="input-medium read-non" name="alias_nonnatural' + "" + i + "" + '" type="Date" value=""/><input  id="alias_nonnatural_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_nonnatural_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_nonnatural' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 3) {
                    jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><select  id="alias_nonnatural' + "" + i + "" + '" class="input-medium disablenon" name="alias_nonnatural' + "" + i + "" + '" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_nonnatural_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_nonnatural_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_nonnatural' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 4) {
                    jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_nonnatural' + "" + i + "" + '" type="number" pattern="[0-9]" class="input-medium read-non" name="alias_nonnatural' + "" + i + "" + '" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_nonnatural_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_nonnatural_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                } else {
                    jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_nonnatural' + "" + i + "" + '" class="inputField01 splitpropclass read-non" name="alias_nonnatural' + "" + i + "" + '"  type="text" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_nonnatural_key' + "" + i + "" + '"  name="alias_nonnatural_key' + "" + i + "" + '"  type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                }
            } else if (id == 3) {
                if (datatype == 2) {
                    jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_multimedia' + "" + i + "" + '" class="input-medium read-mul" name="alias_multimedia' + "" + i + "" + '" type="Date" value=""/><input  id="alias_multimedia_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_multimedia_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_multimedia' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 3) {

                    jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><select  id="alias_multimedia' + "" + i + "" + '" class="input-medium disablemul" name="alias_multimedia' + "" + i + "" + '" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_multimedia_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_multimedia_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                    jQuery('#alias_multimedia' + "" + i + "" + '').val(selectedUnitValue);
                } else if (datatype == 4) {

                    jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_multimedia' + "" + i + "" + '" type="number" pattern="[0-9]" class="input-medium read-mul" name="alias_multimedia' + "" + i + "" + '" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_multimedia_key' + "" + i + "" + '" class="inputField01 splitpropclass" name="alias_multimedia_key' + "" + i + "" + '" type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                } else {
                    jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + i + "" + '" >' + "" + selectedUnitText + "" + '</label></div><div class="floatColumn01"><input  id="alias_multimedia' + "" + i + "" + '" class="inputField01 splitpropclass read-mul" name="alias_multimedia' + "" + i + "" + '" type="text" value="' + "" + selectedUnitValue + "" + '"/><input  id="alias_multimedia_key' + "" + i + "" + '"  name="alias_multimedia_key' + "" + i + "" + '"  type="hidden" value="' + "" + selectedUnitValues + "" + '"/></div></div>');
                }
            }
        }
    }
}

function clearUploadDialog() {
    uploadDialog.dialog("destroy");
    $('#uploaddocumentformID')[0].reset();
}

function viewMultimedia(id) {
    window.open("landrecords/download/" + id, 'popUp', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
}

function showOnMap(usin, statusId) {
    $.ajaxSetup({
        cache: false
    });
    var relLayerName = "spatial_unit";
    var fieldName = "usin";
    var fieldVal = usin;
    zoomToLayerFeature(relLayerName, fieldName, fieldVal);
}

function zoomToLayerFeature(relLayerName, fieldName, fieldVal) {
    if (map.getLayersByName("vector")[0] != undefined)
        map.removeLayer(vectors);

    OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';

    vectors = new OpenLayers.Layer.Vector("vector", {
        isBaseLayer: true
    });

    map.addLayers([vectors]);
    var layer = map.getLayersByName(relLayerName)[0];

    if (layer == null)
        return;

    var url = map.getLayersByName(relLayerName)[0].url;
    var layerName = 'spatial_unit';
    var type;
    var featureNS = getFeatureNS(layerName, url);
    var prefix;
    var geomFieldName = 'the_geom';
    var featuresGeom = null;
    var pos = layerName.indexOf(":");
    prefix = layerName.substring(0, pos);
    type = layerName.substring(++pos);

    var filters = getFilter(fieldName, fieldVal);

    var filter_1_0 = new OpenLayers.Format.Filter({
        version: "1.1.0"
    });

    createXML();
    var xml = new OpenLayers.Format.XML();
    var xmlFilter = xml.write(filter_1_0.write(filters));

    dataPost = dataPost.replace("${layer}", "\"" + "spatial_unit" + "\"");
    dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
    dataPost = dataPost.replace("${filter}", xmlFilter);
    dataPost = dataPost.replace("${uniqueFld}", fieldName);
    valAsc = "ASC";
    dataPost = dataPost.replace("${SortOrder}", valAsc);
    var mapProj = map.projection;
    var reverseCoords = false;

    if (mapProj == "EPSG:4326") {
        reverseCoords = false;
    }

    $.ajax({
        url: url,
        dataType: "xml",
        contentType: "text/xml; subtype=gml/3.1.1; charset=utf-8",
        type: "POST",
        crossDomain: true,
        data: dataPost,
        success: function (data) {
            var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
                xy: reverseCoords,
                featureType: 'spatial_unit',
                gmlns: 'http://www.opengis.net/gml',
                featureNS: featureNS,
                geometryName: geomFieldName,
                featurePrefix: 'spatial_unit',
                extractAttributes: true
            }).read(data);

            if (gmlFeatures.length > 0) {
                featuresGeom = gmlFeatures[0].geometry;
                zoomToAnyFeature(featuresGeom);
            } else {
                alert("Site not found on Map");
            }
        }
    });
}

function zoomSiteOnMap(usin) {
    zoomToLayerFeature('World Heritage Sites', 'Polygon', 'wdpa_id', wdpaid);
}

function getFeatureNS(layerName, url) {
    if (url == null)
        return url;
    var _wfsurl = url.replace(new RegExp("wms", "i"), "wfs");
    var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + layerName;

    var featureNS = '';
    $.ajax({
        url: _wfsSchema,
        dataType: "xml",
        async: false,
        success: function (data) {
            var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
            var responseText = featureTypesParser.read(data);
            featureNS = responseText.targetNamespace;
        }
    });
    return featureNS;
}

function zoomToAnyFeature(geom) {
    var biggerArea = 0.0;
    var biggerPart = 0;
    if (geom.components != undefined && geom.components != null) {
        $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#map-tab"]').parent().index());
        $('#sidebar').show();
        $('#collapse').show();

        for (intpartCnt = 0; intpartCnt < geom.components.length; intpartCnt++) {
            if (biggerArea < geom.components[intpartCnt].getArea()) {
                biggerArea = geom.components[intpartCnt].getArea();
                biggerPart = intpartCnt;
            }
        }

        var bounds = null;
        bounds = geom.components[biggerPart].getBounds();

        var feature = new OpenLayers.Feature.Vector(
                OpenLayers.Geometry.fromWKT(
                        geom.toString()));
        vectors.addFeatures([feature]);
        map.zoomToExtent(bounds, true);
    } else {
        $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#landrecords-div"]').parent().index());
        $('#sidebar').hide();
        $('#collapse').hide();
        jAlert('Site not found on Map', 'Alert');
    }
}

function getFilter(fieldName, fieldVal) {
    var filter;
    filter = new OpenLayers.Filter.Comparison({
        type: OpenLayers.Filter.Comparison.EQUAL_TO,
        matchCase: false,
        property: fieldName,
        value: fieldVal
    });
    return filter;
}

function adjudicationFormDialog(usinID, statusId) {
    if (statusId == 5 || statusId == 3) {
        jAlert('Adjudication form can be generated for New Status', 'Alert');
    } else {
        adjudicationDialog = $("#adjudication-dialog-form").dialog({
            autoOpen: false,
            height: 200,
            width: 232,
            resizable: false,
            modal: true,
            buttons: {
                "Ok": function () {
                    var lang = "";
                    var selected = $("#radioLang input[type='radio']:checked");
                    if (selected.length > 0) {
                        lang = selected.val();
                    }

                    if (lang != '0') {
                        get_Adjuticator_detail(usinID, lang);
                        adjudicationDialog.dialog("destroy");
                    } else {
                        jAlert("Please select language", "Alert");
                    }
                },
                "Cancel": function () {
                    adjudicationDialog.dialog("destroy");
                }
            },
            close: function () {
                adjudicationDialog.dialog("destroy");
            }
        });

        $('input[type=radio][name=lang]').change(function () {
            if (this.value == 'Sw') {
                $('input:radio[name="lang"][value="Sw"]').prop('checked', true);
            } else if (this.value == 'En') {
                $('input:radio[name="lang"][value="En"]').prop('checked', true);
            }
        });

        $('input:radio[name="lang"][value="Sw"]').prop('checked', true);
        adjudicationDialog.dialog("open");
    }
}

function viewProjectName(project) {
    activeProject = project;
}

function defaultProject() {
    document.location.href = "http://" + location.host + "/mast/viewer/";
}

function addNaturalPerson() {
    $("#deletedNaturalpersonRowData").empty();

    jQuery.ajax({
        url: "landrecords/showndisputers/" + editList[0].usin,
        async: false,
        success: function (persons) {
            if (persons === "" || persons.length < 0) {
                jAlert("No existing persons found", "Info");
            } else {
                $("#naturalpersonTemplate_add").tmpl(persons).appendTo("#deletedNaturalpersonRowData");
                addDeletedNaturalDialog = $("#deletednat-dialog-form").dialog({
                    autoOpen: false,
                    height: 300,
                    width: 500,
                    resizable: false,
                    modal: true,
                    buttons: {
                        "Close": function () {
                            addDeletedNaturalDialog.dialog("destroy");
                        }
                    }
                });
                addDeletedNaturalDialog.dialog("open");
            }
        }
    });
}

function addExistingPerson(personGid) {
    var id = editList[0].usin;
    jQuery.ajax({
        url: "landrecords/addexistingperson/" + editList[0].usin + "/" + personGid,
        async: false,
        success: function (result) {
            if (result) {
                jAlert('Person has been successfully added', 'Info');
                addDeletedNaturalDialog.dialog("destroy");
                editAttribute(id);
            } else {
                jAlert('Failed to add person', 'Error');
            }
        }
    });
}

function addNonNaturalPerson(empty) {
    var idUsin_non = editList[0].usin;
    jQuery.ajax({
        url: "landrecords/shownonnatural/" + idUsin_non,
        async: false,
        success: function (data) {
            DeletedNonNaturalList = data;
        }
    });

    jQuery("#deletedNonNaturalpersonRowData").empty();

    if (DeletedNonNaturalList.toString() == "") {
        if (empty != "empty")
            jAlert("No Record Exists", "Non Natural Person");
    } else {
        jQuery("#nonnaturalpersonTemplate_add").tmpl(DeletedNonNaturalList).appendTo("#deletedNonNaturalpersonRowData");
        addDeletedNonNaturalDialog = $("#deletednon-dialog-form").dialog({
            autoOpen: false,
            height: 200,
            width: 500,
            resizable: false,
            modal: true,
            buttons: {
                "Close": function () {
                    addDeletedNonNaturalDialog.dialog("destroy");
                }
            }
        });
        addDeletedNonNaturalDialog.dialog("open");
    }
}

function addDeletedNonNatural(nonnatGid) {
    var idNon = editList[0].usin;
    jQuery.ajax({
        url: "landrecords/addnonnatural/" + nonnatGid,
        async: false,
        success: function (result) {
            if (result) {
                jAlert('Data Successfully Added', 'Non Natural Person');
                if (DeletedNonNaturalList.length == 1) {
                    addDeletedNonNaturalDialog.dialog("destroy");
                }
                addNonNaturalPerson("empty");
                editAttribute(idNon);
            } else {
                jAlert('Error in Adding Data', 'Non Natural Person');
            }
        }
    });
}

function previousRecords() {
    records_from = $('#records_from').val();
    records_from = parseInt(records_from);
    records_from = records_from - 21;
    if (records_from >= 0) {
        if (searchRecords != null) {
            spatialSearch(records_from);
        } else {
            spatialRecords(records_from);
        }
    } else {
        alert("Request Can Not Be Done");
    }
}

function nextRecords() {
    records_from = $('#records_from').val();
    records_from = parseInt(records_from);
    records_from = records_from + 19;

    if (records_from <= totalRecords - 1) {
        if (searchRecords != null) {
            if (records_from <= searchRecords - 1)
                spatialSearch(records_from);
            else
                alert("Request Can Not Be Done");
        } else {
            spatialRecords(records_from);
        }
    } else {
        alert("Request Can Not Be Done");
    }
}

function spatialRecords(records_from) {
    jQuery.ajax({
        url: "landrecords/spatialunit/" + activeProject + "/" + records_from,
        async: false,
        success: function (data) {
            dataList = data;
            $('#landRecordsRowData').empty();
            if (dataList != "" && dataList != null) {
                jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
                $("#landRecordsTable").trigger("update");
                $('#records_from').val(records_from + 1);
                $('#records_to').val(totalRecords);
                if (records_from + 20 <= totalRecords)
                    $('#records_to').val(records_from + 20);
                $('#records_all').val(totalRecords);
            }
        }
    });
}

function spatialSearch(records_from) {
    jQuery.ajax({
        type: "POST",
        url: "landrecords/search/" + activeProject + "/" + records_from,
        data: jQuery("#landrecordsform").serialize(),
        success: function (result) {
            dataList = null;
            dataList = result;
            $('#landRecordsRowData').empty();
            if (dataList != "" && dataList != null) {
                jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
                $("#landRecordsTable").trigger("update");
                $('#records_from').val(records_from + 1);
                $('#records_to').val(searchRecords);
                if (records_from + 20 <= searchRecords)
                    $('#records_to').val(records_from + 20);
                $('#records_all').val(searchRecords);
            }
        }
    });
}

function addPerson() {
    addPersonDialog = $("#add_person-dialog-form").dialog({
        autoOpen: false,
        height: 138,
        width: 255,
        resizable: false,
        modal: true,
        buttons: {
            "Close": function () {
                addPersonDialog.dialog("destroy");
            }
        }
    });
    addPersonDialog.dialog("open");
}

function addNewNaturalPerson(parentNonNaturalId, disputeId) {
    naturalAdditonalAttributes();
    $(".hidden_alias").show();
    $("#parentNonNaturalId").val(parentNonNaturalId);
    $("#personDisputeId").val(disputeId);

    $.ajax({
        url: "landrecords/idtype/",
        async: false,
        success: function (data) {
            jQuery("#idType").empty();
            jQuery("#idType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(data, function (i, idType) {
                jQuery("#idType").append(jQuery("<option></option>").attr("value", idType.code).text(idType.name));
            });
        }
    });

    jQuery.ajax({
        url: "landrecords/gendertype/",
        async: false,
        success: function (data) {
            jQuery("#gender").empty();
            jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(data, function (i, genderobj) {
                jQuery("#gender").append(jQuery("<option></option>").attr("value", genderobj.genderId).text(genderobj.gender));
            });
        }
    });

    jQuery.ajax({
        url: "landrecords/maritalstatus/",
        async: false,
        success: function (data) {
            jQuery("#marital_status").empty();
            jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(data, function (i, maritalobj) {
                jQuery("#marital_status").append(jQuery("<option></option>").attr("value", maritalobj.maritalStatusId).text(maritalobj.maritalStatus));
            });
        }
    });

    jQuery.ajax({
        url: "landrecords/citizenship/",
        async: false,
        success: function (data) {
            jQuery("#citizenship").empty();
            jQuery("#citizenship").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(data, function (i, citizenobj) {
                if (citizenobj.id != 0)
                    jQuery("#citizenship").append(jQuery("<option></option>").attr("value", citizenobj.id).text(citizenobj.citizenname));
            });
        }
    });

    jQuery.ajax({
        url: "landrecords/personsubtype/",
        async: false,
        success: function (person_subtype) {
            jQuery("#person_subType").empty();
            jQuery("#person_subType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
            jQuery.each(person_subtype, function (i, personSubtypeobj) {
                if (personSubtypeobj.person_type_gid !== 1 && personSubtypeobj.person_type_gid !== 2)
                    jQuery("#person_subType").append(jQuery("<option></option>").attr("value", personSubtypeobj.person_type_gid).text(personSubtypeobj.personType));
            });
        }
    });

    $("#dob").live('click', function () {
        $(this).datepicker({dateFormat: 'yy-mm-dd'}).focus();
    });

    document.getElementById("editNaturalPersonformID").reset();

    naturalPersonDialog = $("#naturalperson-dialog-form").dialog({
        autoOpen: false,
        height: 450,
        width: 725,
        resizable: false,
        modal: true,
        stack: false,
        buttons: {
            "Save": function () {
                updateAttributeNaturalPerson(true);
            }
        },
        close: function () {
            naturalPersonDialog.dialog("destroy");
            $('#tab-natural').tabs("option", "active", $('#tab-natural a[href="#tabs-1"]').parent().index());
        }
    });
    naturalPersonDialog.dialog("open");
}

function updateNewNaturalPerson() {
    jQuery("#natural_key").val(0);
    var id = editList[0].usin;
    project = editList[0].project;
    var length_natural = attributeList.length;
    jQuery("#natural_usin").val(id);
    jQuery("#natual_length").val(0);
    if (length_natural != 0 && length_natural != undefined)
        jQuery("#natual_length").val(length_natural);
    jQuery("#projectname_key").val(project);

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatenatural",
        data: jQuery("#editNaturalPersonformID").serialize(),
        success: function (data) {
            if (data.toString() != "") {
                naturalPerson_gid = data.person_gid;
                editAttribute(id);
                jAlert('Data Sucessfully Saved', 'Tenure Info');
            } else {
                jAlert("Request Not Completed", "Natural Person");
            }
        }
    });
}

function naturalPersonImage(person_gid, admin_id) {
    jQuery.ajax({
        url: "landRecords/check/naturalimage/" + person_gid + "/" + admin_id,
        cache: false,
        success: function (result) {
            jQuery("#naturalImage-div").empty();

            if (result != "") {
                jQuery("#naturalImage-div").append('<div id= "image_upload"><p class="type"><strong>Person Image</strong></p><p class="type"><img id="current_img" /></p></div>');
                jQuery('#document_natural').val(result[0]);
                $("#current_img").attr("src", "landrecords/personphoto/" + person_gid + "?rnd=" + Math.floor(Math.random() * 1000));
                jQuery('#current_img').attr("height", 125);
                jQuery('#current_img').attr("width", 125);
                $('#check_img').text("Upate Existing Image");
            } else {
                jQuery("#naturalImage-div").append('<div id= "image_upload"><p class="type"><strong>No Image</strong></p><p class="type"><img id="current_img" /></p></div>');
                jQuery('#current_img').attr("src", "resources/images/studio/noImage.png");
                $('#check_img').text("Add New Image");
            }
        }
    });

    uploadNaturalImage = $("#upload-natural-Image").dialog({
        autoOpen: false,
        height: 430,
        width: 350,
        resizable: false,
        modal: true,
        buttons: {
            "Upload": function () {
                uploadNaturalImg(person_gid, admin_id);
            },
            "Cancel": function () {
                uploadNaturalImage.dialog("destroy");
                $('#uploadNaturalImage')[0].reset();
            }
        },
        close: function () {
            uploadNaturalImage.dialog("destroy");
            $('#uploadNaturalImage')[0].reset();
        }
    });
    uploadNaturalImage.dialog("open");
}

function uploadNaturalImg(gid_Person, id_admin) {
    var formData = new FormData();
    var file = $('#natural_image')[0].files[0];
    var usinId = editList[0].usin;
    var name = $("#document_natural").val();

    if (typeof (file) === "undefined") {
        jAlert('Please Select file to upload', 'Upload Web Document ');
    } else if ((file.size) / 1024 >= 5120) {
        jAlert('Please Enter file size below 5Mb', 'Upload Web Document');
    } else {
        formData.append("natural_image", file);
        formData.append("proj_name", activeProject);
        formData.append("document_name", name);
        formData.append("Usin_Upload", usinId);
        formData.append("person_gid", gid_Person);
        formData.append("admin_id", id_admin);

        $.ajax({
            url: 'landrecords/upload/personimage/',
            type: 'POST',
            data: formData,
            mimeType: "multipart/form-data",
            contentType: false,
            cache: false,
            processData: false,
            success: function (result, textStatus, jqXHR) {
                if (result === "Success") {
                    jAlert('File uploaded', 'upload');
                    editAttribute(usinId);
                    uploadNaturalImage.dialog("destroy");
                    $('#uploadNaturalImage')[0].reset();
                    $('#image_upload').remove();
                } else if (result == "Error") {
                    jAlert("Error in Uploading", 'Upload');
                    clearUploadDialog();
                } else {
                    jAlert("Data cannot be uploaded", "Upload");
                    clearUploadDialog();
                }
            }
        });
    }
}

function naturalAdditonalAttributes() {
    jQuery.ajax({
        url: "landrecords/naturalcustom/" + activeProject,
        async: false,
        success: function (data) {
            customList = data;
            $(".datepicker").datepicker();
            $(".datepicker").live('click', function () {
                $(this).datepicker('destroy').datepicker({
                    dateFormat: 'yy-mm-dd'
                }).focus();
            });
        }
    });

    var length_new = (customList.length) / 3;
    var j = 0;

    jQuery("#customnatural-div").empty();
    jQuery("#customnewnatural-div").empty();
    jQuery("#customnewnatural-div").append('<div><input type="hidden" name="newnatural_length" value=' + length_new + '></div>');
    for (var i = 0; i < customList.length; i++) {
        j = j + 1;
        var selectedcustomText = customList[i];
        var datatype = customList[i + 2];
        var custom_uid = customList[i + 1];

        if (datatype == '2') {
            jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + j + "" + '" >' + "" + selectedcustomText + "" + '</label></div><div class="floatColumn01"><input  id="alias_nat_custom' + "" + j + "" + '" class="input-medium justread datepicker" readonly name="alias_nat_custom' + "" + j + "" + '" type="Date" value=""/><input  id="alias_uid' + "" + j + "" + '" class="inputField01 splitpropclass" name="alias_uid' + "" + j + "" + '" type="hidden" value="' + "" + custom_uid + "" + '"/></div></div>');
        } else if (datatype == '3') {
            jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + j + "" + '" >' + "" + selectedcustomText + "" + '</label></div><div class="floatColumn01"><select  id="alias_nat_custom' + "" + j + "" + '" class="input-medium justdisable" name="alias_nat_custom' + "" + j + "" + '" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_uid' + "" + j + "" + '" class="inputField01 splitpropclass" name="alias_uid' + "" + j + "" + '" type="hidden" value="' + "" + custom_uid + "" + '"/></div></div>');
        } else if (datatype == '4') {
            jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + j + "" + '" >' + "" + selectedcustomText + "" + '</label></div><div class="floatColumn01"><input  id="alias_nat_custom' + "" + j + "" + '" type="number" pattern="[0-9]" class="input-medium justread" name="alias_nat_custom' + "" + j + "" + '" value=""/><input  id="alias_uid' + "" + j + "" + '" class="inputField01 splitpropclass" name="alias_uid' + "" + j + "" + '" type="hidden" value="' + "" + custom_uid + "" + '"/></div></div>');
        } else {
            jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias' + "" + j + "" + '">' + "" + selectedcustomText + "" + '</label></div><div class="floatColumn01"><input  id="alias_nat_custom' + "" + j + "" + '" class="inputField01 splitpropclass" name="alias_nat_custom' + "" + j + "" + '" type="text" value=""/><input  id="alias_uid' + "" + j + "" + '" class="inputField01 splitpropclass" name="alias_uid' + "" + j + "" + '" type="hidden" value="' + custom_uid + '"/></div></div>');
        }
        i = i + 2;
    }
}

function editnxtTokin(id) {
    jQuery.ajax({
        url: "landrecords/relationshiptypes/",
        async: false,
        success: function (data) {
            relationshipTypes = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/gendertype/",
        async: false,
        success: function (data) {
            genderList = data;
        }
    });

    jQuery("#poiRelType").empty();
    jQuery("#poiRelType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
    jQuery.each(relationshipTypes, function (i, relationshipType) {
        jQuery("#poiRelType").append(jQuery("<option></option>").attr("value", relationshipType.code).text(relationshipType.name));
    });

    jQuery("#poiGender").empty();
    jQuery("#poiGender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
    jQuery.each(genderList, function (i, genderobj) {
        jQuery("#poiGender").append(jQuery("<option></option>").attr("value", genderobj.genderId).text(genderobj.gender));

    });

    jQuery("#poiDob").val("");
    $("#poiDob").live('click', function () {
        $(this).datepicker({dateFormat: 'yy-mm-dd'}).focus();
    });

    if (id > 0) {
        $.ajax({
            url: "landrecords/poi/" + id,
            async: false,
            success: function (data) {
                if (data) {
                    var name = data.personName.split(" ");
                    if (name.length == 1) {
                        jQuery("#fname_kin").val(name[0]);
                    } else if (name.length == 2) {
                        jQuery("#fname_kin").val(name[0]);
                        jQuery("#lname_kin").val(name[1]);
                    } else if (name.length == 3) {
                        jQuery("#fname_kin").val(name[0]);
                        jQuery("#mname_kin").val(name[1]);
                        jQuery("#lname_kin").val(name[2]);
                    }

                    if (data.relationshipType !== null) {
                        jQuery("#poiRelType").val(data.relationshipType.code);
                    }

                    if (data.gender != null) {
                        jQuery("#poiGender").val(data.gender.genderId);
                    }

                    if (data.dob != null) {
                        jQuery("#poiDob").val(data.dob);
                    }
                } else {
                    jAlert("Person of interest was not found", "Alert");
                }
            }
        });
    }

    nxtTokinDialog = $("#nxtTokin-dialog-form").dialog({
        autoOpen: false,
        height: 270,
        width: 725,
        resizable: false,
        modal: true,
        buttons: {
            "Update": function () {
                validateUpdatePWI(id);
            }
        },
        close: function () {
            nxtTokinDialog.dialog("destroy");
            $('#editnxtTokinformID')[0].reset();
        }
    });

    nxtTokinDialog.dialog("open");
    jQuery('.justread').attr('readonly', false);

    if (read) {
        $('.ui-dialog-buttonpane button').button().hide();
        jQuery('.justread').attr('readonly', true);
    }
}

function validateUpdatePWI(pwi_id) {
    $("#editnxtTokinformID").validate({
        rules: {
            fname_kin: "required",
            lname_kin: "required"
        },
        messages: {
            fname_kin: "Please enter First name",
            lname_kin: "Please enter Last name"
        }
    });

    if ($("#editnxtTokinformID").valid()) {
        updatePWI(pwi_id)
    }
}

function updatePWI(pwi_id) {
    var usin = editList[0].usin;
    var fname = jQuery("#fname_kin").val();
    var mname = jQuery("#mname_kin").val();
    var lname = jQuery("#lname_kin").val();
    var name = fname + " " + lname;
    if (mname !== '')
        name = fname + " " + mname + " " + lname;
    jQuery("#id_kin").val(pwi_id);
    jQuery("#usin_kin").val(usin);
    jQuery("#name_kin").val(name);

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatepwi",
        async: false,
        data: jQuery("#editnxtTokinformID").serialize(),
        success: function (data) {
            if (data) {
                nxtTokinDialog.dialog("destroy");
                $('#editnxtTokinformID')[0].reset();
                editAttribute(usin);
                jAlert('Data Sucessfully Saved', 'Person of Interest');
            } else {
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
}

function deletenxtTokin(id, name) {
    var usin = editList[0].usin;
    jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                url: "landrecords/deletekin/" + id,
                async: false,
                success: function (result) {
                    if (result == true) {
                        jAlert('Data Successfully Deleted', 'Deceased Person');
                        editAttribute(usin);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Request not completed');
                }
            });
        }
    });
}

function editDeceasedPerson(id) {
    if (id == 0 && deceasedPersonList.length >= 1) {
        jAlert("User can add only one deceased person", "Deceased Person");
    } else {
        if (id != 0) {
            jQuery("#d_firstname").val(deceasedPersonList[0].firstname);
            jQuery("#d_middlename").val(deceasedPersonList[0].middlename);
            jQuery("#d_lastname").val(deceasedPersonList[0].lastname);
        }

        deceasedPersonDialog = $("#deceased-dialog-form").dialog({
            autoOpen: false,
            height: 320,
            width: 320,
            resizable: false,
            modal: true,
            buttons: {
                "Save": function () {
                    updateDeceasedPerson(id);
                }
            },
            close: function () {
                deceasedPersonDialog.dialog("destroy");
                $('#editdeceasedformID')[0].reset();

            }
        });

        deceasedPersonDialog.dialog("open");
        jQuery('.justread').attr('readonly', false);

        if (read) {
            $('.ui-dialog-buttonpane button').button().hide();
            jQuery('.justread').attr('readonly', true);
        }
    }
}

function updateDeceasedPerson(id) {
    var usin = editList[0].usin;
    jQuery("#deceased_key").val(id);
    jQuery("#usin_deceased").val(usin);

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatedeceased",
        async: false,
        data: jQuery("#editdeceasedformID").serialize(),
        success: function (data) {
            if (data) {
                deceasedPersonDialog.dialog("destroy");
                $('#editdeceasedformID')[0].reset();
                editAttribute(usin);
                jAlert('Data Sucessfully Saved', 'Deceased Person');
            } else {
                jAlert('Request not completed');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
}

function deleteDeceased(id, name) {
    var usin = editList[0].usin;
    jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                url: "landrecords/deletedeceased/" + id,
                async: false,
                success: function (result) {
                    if (result == true) {
                        jAlert('Data Successfully Deleted', '');
                        editAttribute(usin);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Request not completed');
                }
            });
        }
    });
}

function naturalPersonDeleteImage(person_gid, person_name) {
    var usin = editList[0].usin;
    jConfirm('Are You Sure You Want To Delete the Image of Person: <strong>' + person_name + '</strong>', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: 'GET',
                async: false,
                url: "landrecords/deletenaturalphoto/" + person_gid,
                success: function (result) {
                    if (result == "true") {
                        jAlert('Data successfully deleted', 'Natural person Image');
                        editAttribute(usin);
                    }
                    if (result == "false")
                    {
                        jAlert('No image exists', 'Alert');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Request not completed');
                }
            });
        }
    });
}

function editDispute(id) {
    if (disputeTypesList === null) {
        jQuery.ajax({
            url: "landrecords/disputetypes/",
            async: false,
            success: function (data) {
                disputeTypesList = data;
            }
        });
    }

    // Reset form
    $("#disputeId").val(id);
    $("#disputeUsin").val(editList[0].usin);
    $("#cbxDisputeTypes").val(0);
    $("#txtDisputeDescription").val("");

    // Fill the list
    $("#cbxDisputeTypes").empty();
    $("#cbxDisputeTypes").append($("<option></option>").attr("value", 0).text("Please Select"));
    $.each(disputeTypesList, function (i, disputeType) {
        $("#cbxDisputeTypes").append($("<option></option>").attr("value", disputeType.code).text(disputeType.name));
    });

    if (id > 0) {
        $.ajax({
            url: "landrecords/dispute/" + id,
            async: false,
            success: function (data) {
                $("#txtDisputeDescription").val(data.description);
                if (data.disputeType !== null) {
                    $("#cbxDisputeTypes").val(data.disputeType.code);
                }
            }
        });
    }

    disputeDialog = $("#dispute-dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 450,
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                updateDispute();
            }
        },
        close: function () {
            disputeDialog.dialog("destroy");
        }
    });

    disputeDialog.dialog("open");
}

function updateDispute() {
    if ($("#cbxDisputeTypes").val() === "" || $("#cbxDisputeTypes").val() === "0") {
        jAlert("Select dispute type");
        return;
    }

    jQuery.ajax({
        type: "POST",
        url: "landrecords/updatedispute",
        async: false,
        data: $("#formDispute").serialize(),
        success: function (data) {
            if (data) {
                if (data === RESPONSE_OK) {
                    disputeDialog.dialog("destroy");
                    editAttribute(editList[0].usin);
                    jAlert('Data Sucessfully Saved', 'Dispute');
                } else {
                    jAlert(data, 'Error');
                }
            } else {
                jAlert('Failed to save dispute');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to save dispute.');
        }
    });
}

function deleteDispute(id) {
    jConfirm('Are you sure you want to delete dispute?', 'Delete Confirmation', function (response) {
        if (response) {
            $.ajax({
                url: "landrecords/deletedispute/" + id,
                async: false,
                success: function (result) {
                    if (result === RESPONSE_OK) {
                        editAttribute(editList[0].usin);
                        jAlert('Dispute has been successfully deleted', 'Info');
                    } else {
                        jAlert(result, 'Error');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Failed to delete dispute');
                }
            });
        }
    });
}

function showResolveDispute(id) {
    $("#resolveDisputeId").val(id);
    $("#resolutionText").val("");

    disputeResolveDialog = $("#dispute-resolve-dialog-form").dialog({
        autoOpen: false,
        height: 350,
        width: 450,
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                resolveDispute();
            }
        },
        close: function () {
            disputeResolveDialog.dialog("destroy");
        }
    });

    disputeResolveDialog.dialog("open");
}

function resolveDispute() {
    $.ajax({
        type: "POST",
        url: "landrecords/resolvedispute",
        async: false,
        data: $("#formResolveDispute").serialize(),
        success: function (result) {
            if (result === RESPONSE_OK) {
                disputeResolveDialog.dialog("destroy");
                editAttribute(editList[0].usin);
                jAlert('Dispute has been successfully resolved', 'Info');
            } else {
                jAlert(result, 'Error');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to delete dispute');
        }
    });
}

function deleteDisputingParty(disputeId, partyId) {
    var usinid = editList[0].usin;

    jConfirm('Are you sure you want to delete disputing person?', 'Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: 'GET',
                url: "landrecords/deleteDisputant/" + disputeId + "/" + partyId,
                success: function (result) {
                    if (result) {
                        editAttribute(usinid);
                        jAlert('Disputant has been successfully deleted', 'Info');
                    } else {
                        jAlert('Failed to delete disputant', 'Info');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Failed to delete disputant', 'Info');
                }
            });
        }
    });
}

function printDenialLetter(usin) {
    window.open("landrecords/denialletter/" + usin, 'Report', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
}

function generateAdjudicationForm(usin) {
    $.ajax({
        type: "GET",
        url: "landrecords/checkvcdate/" + activeProject,
        async: false,
        success: function (result) {
            if (result === RESPONSE_OK) {
                var w = window.open("landrecords/adjudicationform/" + usin, 'AdjudicationForm', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
                if (window.focus) {
                    w.focus();
                }
            } else {
                jAlert(result, 'Error');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to validate Village Council date');
        }
    });
}

function generateAdjudicationForms() {
    $.ajax({
        type: "GET",
        url: "landrecords/checkvcdate/" + activeProject,
        async: false,
        success: function (result) {
            if (result === RESPONSE_OK) {
                var fromRecord = $("#adjStart").val();
                var endRecord = $("#adjEnd").val();

                if (!checkIntNumber(fromRecord))
                    fromRecord = 1;

                if (!checkIntNumber(endRecord))
                    endRecord = 100000;

                var w = window.open("landrecords/adjudicationforms/" + activeProject + "/" + fromRecord + "/" + endRecord, 'AdjudicationForms', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
                if (window.focus) {
                    w.focus();
                }
            } else {
                jAlert(result, 'Error');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to validate Village Council date');
        }
    });
}

function generateCcro(usin) {
    $.ajax({
        type: "GET",
        url: "landrecords/checkvcdate/" + activeProject,
        async: false,
        success: function (result) {
            if (result === RESPONSE_OK) {
                var w = window.open("landrecords/ccroform/" + usin, 'CcroForm', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
                if (window.focus) {
                    w.focus();
                }
            } else {
                jAlert(result, 'Error');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to validate Village Council date');
        }
    });
}

function generateCcros() {
    $.ajax({
        type: "GET",
        url: "landrecords/checkvcdate/" + activeProject,
        async: false,
        success: function (result) {
            if (result === RESPONSE_OK) {
                var fromRecord = $("#ccroStart").val();
                var endRecord = $("#ccroEnd").val();

                if (!checkIntNumber(fromRecord))
                    fromRecord = 1;

                if (!checkIntNumber(endRecord))
                    endRecord = 100000;

                var w = window.open("landrecords/ccroforms/" + activeProject + "/" + fromRecord + "/" + endRecord, 'CcroForms', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
                if (window.focus) {
                    w.focus();
                }
            } else {
                jAlert(result, 'Error');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to validate Village Council date');
        }
    });
}

function generateDistrictRegBook() {
    var w = window.open("landrecords/districtregbook/" + activeProject, 'DistrictRegistryBook', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
    if (window.focus) {
        w.focus();
    }
}

function generateVillageRegBook() {
    var w = window.open("landrecords/villageregbook/" + activeProject, 'DistrictRegistryBook', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
    if (window.focus) {
        w.focus();
    }
}

function generateVillageIssuanceBook() {
    var w = window.open("landrecords/villageissuancebook/" + activeProject, 'VillageIssuanceBook', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
    if (window.focus) {
        w.focus();
    }
}

function generateTransactionSheet(usin) {
    var w = window.open("landrecords/transactionsheet/" + usin + "/" + activeProject, 'TransactionSheet', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
    if (window.focus) {
        w.focus();
    }
}

function generateClaimsProfile() {
    var w = window.open("landrecords/claimsprofile/" + $("#selectProjects").val(), 'ClaimsProfile', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
    if (window.focus) {
        w.focus();
    }
}

$(document).ready(function () {
    // Add date field
    var DateField = function (config) {
        jsGrid.Field.call(this, config);
    };

    DateField.prototype = new jsGrid.Field({
        sorter: function (date1, date2) {
            if ((date1 === null || date1 === "") && (date2 === null || date2 === "")) {
                return 0;
            }
            if (date1 === null || date1 === "") {
                return -1;
            }
            if (date2 === null || date2 === "") {
                return 1;
            }
            return new Date(date2) - new Date(date1);
        },
        itemTemplate: function (value) {
            if (value !== null && value !== "") {
                return formatDate(value);
            }
            return "";
        },
        editTemplate: function (value) {
            if (value === null || value === "")
                return this._editPicker = $("<input>").datepicker({dateFormat: "yy-mm-dd"});
            else
                return this._editPicker = $("<input>").datepicker({dateFormat: "yy-mm-dd"}).datepicker("setDate", new Date(value));
        },
        insertValue: function () {
            return this._insertPicker.datepicker("getDate");
        },
        editValue: function () {
            return this._editPicker.datepicker("getDate");
        }
    });

    jsGrid.fields.date = DateField;

    // init dropdown lists
    var idTypes;
    var maritalStatuses;
    hamletList = [];

    $.ajax({
        url: "landrecords/idtype/",
        async: false,
        success: function (data) {
            idTypes = data;
            if (idTypes !== null && idTypes.length > 0)
                idTypes = [{code: "", name: " "}].concat(idTypes);
        }
    });

    $.ajax({
        url: "landrecords/maritalstatus/",
        async: false,
        success: function (data) {
            maritalStatuses = data;
            if (maritalStatuses !== null && maritalStatuses.length > 0)
                maritalStatuses = [{maritalStatusId: "", maritalStatus: " "}].concat(maritalStatuses);
        }
    });

    // Init editing grid
    $("#personsEditingGrid").jsGrid({
        width: "100%",
        height: "550px",
        inserting: false,
        editing: true,
        sorting: true,
        filtering: true,
        paging: true,
        autoload: false,
        controller: personsEditingController,
        pageSize: 50,
        pageButtonCount: 20,
        fields: [
            {type: "control", deleteButton: false},
            {name: "usin", title: "USIN", type: "number", width: 70, align: "left", editing: false, filtering: true},
            {name: "uka", title: "UKA", type: "text", width: 140, editing: false, filtering: false},
            {name: "hamletId", title: "Hamlet", align: "left", type: "select",
                items: hamletList, valueField: "id", textField: "hamletName", width: 120, filtering: false,
                editTemplate: function (value, item) {
                    var isReadOnly = (item.uka !== null && item.uka !== '');
                    var $result = jsGrid.fields.select.prototype.editTemplate.apply(this, [value]);
                    if(isReadOnly)
                        $result.prop("disabled", "disabled");
                    return $result;
                }
            },
            {name: "firstName", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
            {name: "middleName", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
            {name: "lastName", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
            {name: "idType", title: "ID type", align: "left", type: "select", items: idTypes, valueField: "code", textField: "name", width: 90, validate: {validator: "required", message: "Select ID type"}, filtering: false},
            {name: "idNumber", title: "ID number", type: "text", width: 130, validate: {validator: "required", message: "Fill in ID number"}},
            {name: "dob", title: "Date of birth", type: "date", width: 110, align: "left", validate: {validator: "required", message: "Select date of birth"}, filtering: false},
            {name: "age", title: "Age", type: "number", width: 50, align: "left", editing: false, filtering: false},
            {name: "gender", title: "Gender", align: "left", type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}], valueField: "id", textField: "name", width: 80, filtering: false},
            {name: "shareType", title: "Share type", type: "text", width: 170, editing: false, filtering: false},
            {name: "maritalStatus", title: "Marital status", align: "left", type: "select", items: maritalStatuses, valueField: "maritalStatusId", textField: "maritalStatus", width: 130, filtering: false},
            {name: "claimNumber", title: "Claim number", type: "text", width: 120, editing: false, validate: {validator: "required", message: "Enter claim number"}, filtering: true},
            {name: "claimDate", title: "Claim date", type: "date", width: 100, align: "left", editing: false, filtering: false},
            {name: "neighborNorth", title: "Neighbor north", type: "text", width: 120, validate: {validator: "required", message: "Enter neighbor north"}, filtering: true},
            {name: "neighborSouth", title: "Neighbor south", type: "text", width: 120, validate: {validator: "required", message: "Enter neighbor south"}, filtering: true},
            {name: "neighborEast", title: "Neighbor east", type: "text", width: 120, validate: {validator: "required", message: "Enter neighbor east"}, filtering: true},
            {name: "neighborWest", title: "Neighbor west", type: "text", width: 120, validate: {validator: "required", message: "Enter neighbor west"}, filtering: true}
        ]
    });

    $("#personsEditingGrid .jsgrid-table th:first-child :button").click();
});

function loadPersonsForEditing() {
    if (hamletList === null || hamletList === "" || hamletList.length < 1) {
        $.ajax({
            url: "landrecords/hamletname/" + activeProject,
            async: false,
            success: function (data) {
                hamletList = data;
            }
        });
        $("#personsEditingGrid").jsGrid("fieldOption", "hamletId", "items", hamletList);
    }

    $("#personsEditingGrid").jsGrid("loadData");
}

var personsEditingController = {
    loadData: function (filter) {
        $("#btnLoadPersons").val("Reload");
        return $.ajax({
            type: "GET",
            url: "landrecords/personsforediting/" + activeProject,
            data: filter
        });
    },
    insertItem: function (item) {
        return false;
    },
    updateItem: function (item) {
        return $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            traditional: true,
            url: "landrecords/savepersonforediting",
            data: JSON.stringify(item),
            error: function (request, textStatus, errorThrown) {
                jAlert(request.responseText);
            }
        });
    },
    deleteItem: function (item) {
        return false;
    }
};

function formatDate(date) {
    return jQuery.datepicker.formatDate('yy-mm-dd', new Date(date));
}

function formatDate2(intDate) {
    return jQuery.datepicker.formatDate('yy-mm-dd', new Date(parseInt(intDate)));
}

function calculateAge(birthday) {
    var ageDifMs = Date.now() - (new Date(birthday)).getTime();
    var ageDate = new Date(ageDifMs);
    return Math.abs(ageDate.getUTCFullYear() - 1970);
}

function checkIntNumber(data) {
    if (data === "" + parseInt(data, 10))
        return true;
    else
        return false;
}