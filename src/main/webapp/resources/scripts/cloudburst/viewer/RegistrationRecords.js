var selectedItem_R = null;
var dataList_R = null;
var projList_R = null;
var activeProject_R = "";
var records_from_R = 0;
var records_to_R = 0;
var records_all_R = 0;
var totalRecords_R = null;
var searchRecords_R = null;
var claimTypes_R = null;
// var claimType_R = null;
var landRecordsInitialized_R = false;
var maritalStatus_R = null;
var genderStatus_R = null;
surrendermortagagedata = null;
var idtype_R = null;
var country_r_id = 1;// would be null
var region_r_id = 2; // would be null
var allcountry = null;
var landtype_r = null;
var region_r = null;
var landsharetype_r = null;
var province_r = null;
var processdetails_R = null;
var selectedlandid = null;
var laspatialunitland_R = null;
var laMortgage_R = null;
var financialagency_R = null;
var monthoflease_R = null;
// var statusList_R = null;
var attributeEditDialog = null;
var documenttype_R = null;
var communeList = null;
var currentdiv = null;
var isVisible = true;
var TransLandId = null;
var processid = 0;
var persontypeid = 0;
var transid = 0;
var relationShips = null;
var finallandid = null;
var firstselectedprocess = null;
var arry_Sellerbyprocessid = [];
var arry_Buyerbyprocessid = [];
var editlease = false;

var leaseepersondata = [];

var process_id = 0;

var alertmessage = "";

var edit = 0;

var finaltransid = 0;

var finalbuyerarray = [];

$("#transactionId").val(0);

function RegistrationRecords(_selectedItem) {
    /*
     * if(projList_R !== null && projList_R !== ""){ return; }
     */

    records_from_R = 0;
    searchRecords_R = null;
    selectedItem_R = _selectedItem;
    activeProject_R = activeProject;
    URL = "registryrecords/spatialunit/default/" + 0;
    if (activeProject_R !== null && activeProject_R !== "") {
        URL = "registryrecords/spatialunit/" + activeProject_R + "/" + 0;
    }

    /*jQuery.ajax({
     url : "registryrecords/spatialunitcount/" + activeProject_R + "/" + 0,
     async : false,
     success : function(data) {
     totalRecords_R = data;
     }
     });*/

    jQuery.ajax({
        url: URL,
        async: false,
        success: function (data) {
            dataList_R = data;

        }
    });

    jQuery.ajax({
        url: "registration/maritalstatus/",
        async: false,
        success: function (data) {
            maritalStatus_R = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/claimtypes/",
        async: false,
        success: function (data) {
            claimTypes_R = data;
        }
    });
    /*
     * jQuery.ajax({ url: "landrecords/status/", async: false, success: function
     * (data) { statusList_R = data; } });
     */

    jQuery.ajax({
        url: "registration/genderstatus/",
        async: false,
        success: function (data) {
            genderStatus_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/idtype/",
        async: false,
        success: function (data) {
            idtype_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/landsharetypes/",
        async: false,
        success: function (data) {
            landsharetype_r = data;
        }
    });

    // jQuery.ajax({
    // url: "registration/land/sharetype/" ,
    // async: false,
    // success: function (data) {
    // alert('1');
    // landsharetype_r = data;
    // if(data.length>0){

    // }
    // }
    // });

    jQuery.ajax({
        url: "registration/processdetails/",
        async: false,
        success: function (data) {
            processdetails_R = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/spatialunit/commune/" + activeProject,
        async: false,
        success: function (data) {
            communeList = data;
        }
    });

    displayRefreshedRegistryRecords_ABC();
}

function displayRefreshedRegistryRecords_ABC() {
    jQuery("#registryTab-div").empty();
    jQuery.get("resources/templates/viewer/" + selectedItem_R + ".html",
            function (template) {



                jQuery("#registryTab-div").append(template);
                jQuery("#registryTab-div").i18n();
                jQuery('#registryRecordsFormdiv').css("visibility", "visible");
                jQuery("#registryRecordsTable").show();


                jQuery.ajax({
                    url: "registryrecords/spatialunitcount/" + activeProject_R + "/" + 0,
                    async: false,
                    success: function (data) {
                        totalRecords_R = data;
                    }
                });




                jQuery.ajax({
                    url: URL,
                    async: false,
                    success: function (data) {
                        dataList_R = data;
                        jQuery("#registryRecordsRowData1").empty();
                        jQuery("#community_id_R").empty();
                        jQuery("#community_id_R").append(jQuery("<option></option>").attr("value", "").text($.i18n("gen-please-select")));
                        jQuery.each(communeList, function (i, CommuneObj) {
                            jQuery("#community_id_R").append(jQuery("<option></option>").attr("value", CommuneObj.communeid).text(CommuneObj.commune));
                        });

                        if (dataList_R.length != 0 && dataList_R.length != undefined) {
                            jQuery("#registryRecordsAttrTemplate1").tmpl(dataList_R).appendTo("#registryRecordsRowData1");
                            $("#registryRecordsRowData1").i18n();
                            records_from_R = 0;
                            $('#records_from_R').val(records_from_R + 1);
                            $('#records_to_R').val(totalRecords_R);
                            if (records_from_R + 10 <= totalRecords_R)
                                $('#records_to_R').val(records_from_R + 10);
                            $('#records_all_R').val(totalRecords_R);
                        } else {
                            $('#records_from_R').val(0);
                            $('#records_to_R').val(0);
                            $('#records_all_R').val(0);
                        }

                        jQuery("#claim_type_R").empty();
                        jQuery("#claim_type_R").append(
                                jQuery("<option></option>").attr("value", "").text(
                                $.i18n("gen-please-select")));
                        jQuery.each(claimTypes_R, function (i, claimType_R) {
                            jQuery("#claim_type_R").append(
                                    jQuery("<option></option>").attr("value",
                                    claimType_R.code)
                                    .text(claimType_R.name));
                        });

                    }
                });




            });


}

function displayRefreshedRegistryRecords() {
    var URL = "landrecords/";
    if (activeProject_R != "") {
        URL = "landrecords/" + activeProject_R;
    }

    if (!landRecordsInitialized_R) {
        landRecordsInitialized_R = true;
        jQuery("#registryTab-div").empty();
        jQuery.get("resources/templates/viewer/" + selectedItem_R + ".html",
                function (template) {
                    jQuery("#registryTab-div").append(template);
                    jQuery('#registryRecordsFormdiv').css("visibility",
                            "visible");
                    jQuery("#registryRecordsTable").show();
                });
    }

    jQuery.ajax({
        url: URL,
        success: function (data) {
            projList_R = data;

            jQuery("#registryRecordsRowData").empty();

            if (dataList_R.length != 0 && dataList_R.length != undefined) {
                jQuery("#registryRecordsAttrTemplate").tmpl(dataList_R[0])
                        .appendTo("#registryRecordsRowData");
            }

            jQuery("#status_id").empty();

            jQuery("#status_id").append(
                    jQuery("<option></option>").attr("value", 0).text(
                    $.i18n("gen-please-select")));

            jQuery.each(statusList, function (i, statusobj) {
                jQuery("#status_id").append(
                        jQuery("<option></option>").attr("value",
                        statusobj.workflowStatusId).text(
                        statusobj.workflowStatus));
            });

            jQuery("#records_from_R").val(records_from_R + 1);
            jQuery("#records_to_R").val(records_from_R + 20);

            if (totalRecords_R <= records_from_R + 20)
                jQuery("#records_to_R").val(totalRecords_R);

            jQuery('#records_all_R').val(totalRecords_R);
            jQuery("#projectName_R").text(data.name);
            jQuery("#country_R").text(data.countryName);
            jQuery("#region_R").text(data.region);
            jQuery("#district_R").text(data.districtName);
            jQuery("#village_R").text(data.village);
            jQuery("#hamlet").text("--");

            if (data.hamlet != "" && data.hamlet != null) {
                jQuery("#hamlet").text(data.hamlet);
            }
            if (dataList_R.length != 0 && dataList_R.length != undefined) {
                $("#registryRecordsTable").trigger("update");
                $("#registryRecordsTable").tablesorter({
                    sortList: [[0, 1], [1, 0]]
                });

            }
        }
    });
}

function previousRecords_R() {

    records_from_R = $('#records_from_R').val();
    records_from_R = parseInt(records_from_R);
    records_from_R = records_from_R - 11;
    if (records_from_R >= 0) {
        if (searchRecords_R != null) {
            RegistrationSearch_R(records_from_R);
        } else {
            RegistrationRecords_R(records_from_R);
        }
    } else {
        alert($.i18n("err-no-records"));
    }

}

function nextRecords_R() {

    records_from_R = $('#records_from_R').val();
    records_from_R = parseInt(records_from_R);
    records_from_R = records_from_R + 9;

    if (records_from_R <= totalRecords_R - 1) {
        if (searchRecords_R != null) {
            if (records_from_R <= searchRecords_R - 1) {
                RegistrationSearch_R(records_from_R);
            } else {
                alert($.i18n("err-no-records"));
            }
        }
        RegistrationRecords_R(records_from_R);

    } else {
        alert($.i18n("err-no-records"));
    }

}

function RegistrationRecords_R(records_from_R) {

    jQuery.ajax({
        url: "registryrecords/spatialunit/" + activeProject_R + "/"
                + records_from_R,
        async: false,
        success: function (data) {
            jQuery("#registryRecordsRowData1").empty();

            if (data.length != 0 && data.length != undefined) {
                jQuery("#registryRecordsRowData1").empty();
                jQuery("#registryRecordsAttrTemplate1").tmpl(data).appendTo(
                        "#registryRecordsRowData1");
                $("#registryRecordsRowData1").i18n();
                $('#records_from_R').val(records_from_R + 1);
                $('#records_to_R').val(totalRecords_R);
                if (records_from_R + 10 <= totalRecords_R)
                    $('#records_to_R').val(records_from_R + 10);
                $('#records_all_R').val(totalRecords_R);
            } else {
                $('#records_from_R').val(0);
                $('#records_to_R').val(0);
                $('#records_all_R').val(0);
            }

        }
    });

}

function clearSearch_R() {
    $("#usinstr_id_R").val("");
    $("#claim_type_R").val("");
    $("#app_status_R").val("");
    $("#parce_id_R").val("");
    $('#community_id_R').prop('selectedIndex', 0);
    jQuery("#registryRecordsRowData1").empty();

    if (dataList_R.length != 0 && dataList_R.length != undefined) {
        jQuery("#registryRecordsAttrTemplate1").tmpl(dataList_R).appendTo(
                "#registryRecordsRowData1");
        $("#registryRecordsRowData1").i18n();
    }

}

function search_R() {

    var transactionidIdf1 = $("#usinstr_id_R").val();
    var community_id_R = $("#community_id_R").val();
    var parce_id_R = $("#parce_id_R").val();


    searchRecords_R = null;
    records_from_R = 0;
    if (transactionidIdf1 == "" && parce_id_R == "" && community_id_R == "") {

        alert($.i18n("err-select-search-criteria"));
        return;
    }
    jQuery("#registryRecordsRowData1").empty();

    jQuery.ajax({
        type: "POST",
        async: false,
        url: "registration/search1Count/" + activeProject_R + "/"
                + records_from_R,
        data: jQuery("#registryrecordsform").serialize(),
        success: function (result) {
            searchRecords_R = result;
        }
    });

    RegistrationSearch_R(records_from_R);
}

function RegistrationSearch_R(records_from_R) {

    jQuery.ajax({
        type: "POST",
        async: false,
        url: "registration/search1/" + activeProject_R + "/" + records_from_R,
        data: jQuery("#registryrecordsform").serialize(),
        success: function (result) {
            if (result.length != undefined && result.length != 0) {
                jQuery("#registryRecordsAttrTemplate1").tmpl(result).appendTo(
                        "#registryRecordsRowData1");
                $("#registryRecordsRowData1").i18n();
                $('#records_from_R').val(records_from_R + 1);
                $('#records_to_R').val(totalRecords_R);
                if (records_from_R + 10 <= totalRecords_R)
                    $('#records_to_R').val(records_from_R + 10);
                $('#records_all_R').val(totalRecords_R);
            } else {
                $('#records_from_R').val(0);
                $('#records_to_R').val(0);
                $('#records_all_R').val(0);
            }

        }
    });

}
$(document).ready(
        function () {
            // Add date field
            var DateField = function (config) {
                jsGrid.Field.call(this, config);
            };

            jQuery.ajax({
                url: "registration/landtype/",
                async: false,
                success: function (data) {
                    landtype_r = data;
                    if (data.length > 0) {

                    }
                }
            });

            jQuery.ajax({
                url: "registration/allcountry/",
                async: false,
                success: function (data) {
                    allcountry = data;
                    if (data.length > 0) {
                        // data.xyz.name_en for getting the data
                    }
                }
            });

            DateField.prototype = new jsGrid.Field({
                sorter: function (date1, date2) {
                    if ((date1 === null || date1 === "")
                            && (date2 === null || date2 === "")) {
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
                        return formatDate_R(value);
                    }
                    return "";
                },
                editTemplate: function (value) {
                    if (value === null || value === "")
                        return this._editPicker = $("<input>").datepicker({
                            dateFormat: "yy-mm-dd"
                        });
                    else
                        return this._editPicker = $("<input>").datepicker({
                            dateFormat: "yy-mm-dd"
                        }).datepicker("setDate", new Date(value));
                },
                insertValue: function () {
//					return this._insertPicker.datepicker("getDate");
                    alert("zdfdfssdbfgc");
                },
                editValue: function () {
                    return this._editPicker.datepicker("getDate");
                }
            });

            jsGrid.fields.date = DateField;

            // init dropdown lists
            /*
             * var idTypes; var maritalStatuses;
             */
            hamletList = [];

        });

function leaseAttribute(landid) {
    edit = 0;





    $("#editflag").val(edit);
//	 isVisible = $('#buyersavebutton').is(':visible');
    $(".signin_menu").hide();
    var lease = document.getElementById("Tab_1");
    var sale = document.getElementById("Tab_2");
    var mortgage = document.getElementById("Tab_3");
    var split = document.getElementById("Tab_4");
    lease.style.display = "none"; // lease.style.display = "block";
    sale.style.display = "none";
    mortgage.style.display = "none";
    split.style.display = "none";
    loadResPersonsOfEditingForEditing();
    clearBuyerDetails_sale();

    $("#registration_process").show();


    $("#buyersavebutton").prop("disabled", false).hide();
    selectedlandid = landid;
    $("#landidhide").val(landid);
    /*
     * jQuery.ajax({ url: "registration/landsharetype111111/" , async: false,
     * success: function (data) { landsharetype=data; alert('1');
     * if(data.length>0){
     *  } } });
     */

    jQuery.ajax({
        url: "registration/doctype/",
        async: false,
        success: function (data) {
            documenttype_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/monthoflease/",
        async: false,
        success: function (data) {
            monthoflease_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/financialagency/",
        async: false,
        success: function (data) {
            financialagency_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/laspatialunitland/" + landid,
        async: false,
        success: function (data) {
            laspatialunitland_R = data;
            if (data.length > 0) {

            }
        }
    });

    jQuery.ajax({
        url: "registration/laMortgage/" + landid,
        async: false,
        success: function (data) {
            laMortgage_R = data;
            if (data.length > 0) {

            }
        }
    });

    jQuery.ajax({
        url: "registration/relationshiptypes/",
        async: false,
        success: function (data) {
            relationtypes_R = data;

        }
    });
    jQuery.ajax({
        type: "GET",
        url: "landrecords/landPOI/" + transid + "/" + landid,
        async: false,
        success: function (data) {

            jQuery("#POIRecordsRowDataSale1").empty();
            jQuery("#POIRecordsRowDataMortgage1").empty();
            jQuery("#POIownerRecordsRowDataLease1").empty();
            if (data.length > 0) {

                for (var i = 0; i < data.length; i++) {

                    var relationship = "";
                    var gender = "";
                    for (var j = 0; j < relationtypes_R.length; j++) {

                        if (data[i].relation == relationtypes_R[j].relationshiptypeid) {

                            relationship = relationtypes_R[j].relationshiptype;
                        }
                    }

                    for (var k = 0; k < genderStatus_R.length; k++) {

                        if (genderStatus_R[k].genderId == data[i].gender) {

                            gender = genderStatus_R[k].gender;
                        }
                    }

                    data[i].relation = relationship;
                    data[i].gender = gender;
                    jQuery("#POIRecordsAttrTemplateSale1").tmpl(data[i]).appendTo("#POIRecordsRowDataSale1");
                    jQuery("#POIRecordsAttrTemplateMortgage1").tmpl(data[i]).appendTo("#POIRecordsRowDataMortgage1");
                    jQuery("#POIownerRecordsAttrTemplateLease1").tmpl(data[i]).appendTo("#POIownerRecordsRowDataLease1");

                }
                /*for(var i=0; i<data.length; i++){
                 
                 
                 jQuery("#POIRecordsAttrTemplateSale1").tmpl(data[i]).appendTo("#POIRecordsRowDataSale1");
                 jQuery("#POIRecordsAttrTemplateMortgage1").tmpl(data[i]).appendTo("#POIRecordsRowDataMortgage1");
                 jQuery("#POIownerRecordsAttrTemplateLease1").tmpl(data[i]).appendTo("#POIownerRecordsRowDataLease1");
                 }*/
            }

        }
    });


    jQuery.ajax({
        type: "GET",
        url: "registration/landLeaseePOI/" + landid,
        async: false,
        success: function (data) {


            jQuery("#POIRecordsRowDataLease1").empty();

            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    jQuery("#POIRecordsAttrTemplateLease1").tmpl(data[i]).appendTo("#POIRecordsRowDataLease1");
                }
            }

        }
    });

    $.ajax({
        url: "resource/relationshipTypes/",
        async: false,
        success: function (data1) {

            relationShips = data1;
        }
    });



    // -------------------------------------------------------------------------------------------------------------------
    // onchange of Country pass this select id for country_r_id --
    jQuery.ajax({
        url: "registration/allregion/" + country_r_id,
        async: false,
        success: function (data) {
            region_r = data;
            if (data.length > 0) {
                // data.xyz.name_en for getting the data
            }
        }
    });

    jQuery.ajax({
        url: "registration/allprovince/" + region_r_id,
        async: false,
        success: function (data) {
            province_r = data;
        }
    });



    jQuery("#registration_process").empty();
    jQuery("#registration_process").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery.each(processdetails_R, function (i, obj)
    {
        jQuery("#registration_process").append(jQuery("<option></option>").attr("value", obj.processid).text(obj.processname_en));

    });

    // jAlert('This Land is not under Lease.');
    SaleOwnerdBuyeretails(landid);


    $(function () {

        $("#Tab_1").hide();
        $("#Tab_2").hide();
        $("#Tab_3").hide();
//		var isVisible = $('#buyersavebutton').is(':visible')height : 600,width : 1000, ;
        attributeEditDialog = $("#lease-dialog-form").dialog({
            autoOpen: false,
            height: 700,
            width: 1000,
            resizable: true,
            modal: true,
            close: function () {
                attributeEditDialog.dialog("destroy");
                $("input,select,textarea").removeClass('addBg');
            },
            buttons: [{
                    text: $.i18n("gen-save-next"),
                    "id": "comment_Next",
                    click: function ()
                    {
                        $("input,select,textarea").removeClass('addBg');
                        if (currentdiv == "Sale")
                        {
                            //var selectedtab = document.getElementsByClassName("aria-selected");

                            if ($('#Seller_Details').css('display') == 'block')
                            {
                                $("#Seller_Details").hide();
                                $("#Land_Details_Sale").show();
                                $("#Buyer_Details").hide();
                                $("#regPoi").hide();
                                $("#Upload_Document_Sale").hide();
                                $("#selectedseller").removeClass("ui-tabs-active");
                                $("#selectedseller").removeClass("ui-state-active");
                                $("#selectedland").addClass("ui-tabs-active");
                                $("#selectedland").addClass("ui-state-active");
                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Land_Details_Sale').css('display') == 'block')
                            {
                                $("#Buyer_Details").show();
                                $("#Seller_Details").hide();
                                $("#Land_Details_Sale").hide();
                                $("#Upload_Document_Sale").hide();

                                $("#selectedland").removeClass("ui-tabs-active");
                                $("#selectedland").removeClass("ui-state-active");
                                $("#selectedbuyer").addClass("ui-tabs-active");
                                $("#selectedbuyer").addClass("ui-state-active");



                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Buyer_Details').css('display') == 'block')
                            {
//							savebuyerdetails();
                                if (null != arry_Buyerbyprocessid && null != finalbuyerarray[i]) {
                                    $("#Seller_Details").hide();
                                    $("#Buyer_Details").hide();
                                    $("#Land_Details_Sale").hide();
                                    $("#regPoi").show();
                                    $("#Upload_Document_Sale").hide();
                                    $("#selectedbuyer").removeClass("ui-tabs-active");
                                    $("#selectedbuyer").removeClass("ui-state-active");
                                    $("#selectedpoi").addClass("ui-tabs-active");
                                    $("#selectedpoi").addClass("ui-state-active");
                                    $("#comment_Save").hide();
                                    $("#comment_Next").show();
                                } else {

                                    jAlert($.i18n("err-save-buyer"), $.i18n("gen-info"));
                                }
                            } else if ($('#regPoi').css('display') == 'block')
                            {

                                $("#Seller_Details").hide();
                                $("#Buyer_Details").hide();
                                $("#Land_Details_Sale").hide();
                                $("#regPoi").hide();
                                $("#Upload_Document_Sale").show();
                                $("#selectedpoi").removeClass("ui-tabs-active");
                                $("#selectedpoi").removeClass("ui-state-active");
                                $("#selecteddoc").addClass("ui-tabs-active");
                                $("#selecteddoc").addClass("ui-state-active");
                                $("#comment_Save").show();
                                $("#comment_Next").hide();
                            }
                        } else if (currentdiv == "Lease")
                        {
                            if ($('#Owner_Details').css('display') == 'block')
                            {
                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").show();
                                $("#Applicant_Details").hide();
                                $("#Lease_Details").hide();
                                $("#regLeasePoi").hide();
                                $("#Upload_Documents_Lease").hide();
                                $("#selectedowner").removeClass("ui-tabs-active");
                                $("#selectedowner").removeClass("ui-state-active");
                                $("#selectedLanddetails").addClass("ui-tabs-active");
                                $("#selectedLanddetails").addClass("ui-state-active");



                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Land_Details_lease').css('display') == 'block')
                            {
                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#Applicant_Details").show();
                                $("#Lease_Details").hide();
                                $("#regLeasePoi").hide();
                                $("#Upload_Documents_Lease").hide();
                                $("#selectedLanddetails").removeClass("ui-tabs-active");
                                $("#selectedLanddetails").removeClass("ui-state-active");
                                $("#selectedApplicant").addClass("ui-tabs-active");
                                $("#selectedApplicant").addClass("ui-state-active");


                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Applicant_Details').css('display') == 'block')
                            {
//							saveattributesLeasePersonData();
                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#regLeasePoi").show();
                                $("#Lease_Details").hide();
                                $("#Applicant_Details").hide();

                                $("#Upload_Documents_Lease").hide();
                                $("#selectedApplicant").removeClass("ui-tabs-active");
                                $("#selectedApplicant").removeClass("ui-state-active");

                                $("#selectedLeasepoi").addClass("ui-tabs-active");
                                $("#selectedLeasepoi").addClass("ui-state-active");




                                $("#comment_Save").hide();
                                $("#comment_Next").show();
                            } else if ($('#regLeasePoi').css('display') == 'block')
                            {



                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#Applicant_Details").hide();
                                $("#Lease_Details").show();
                                $("#regLeasePoi").hide();

                                $("#Upload_Documents_Lease").hide();
                                $("#selectedLeasepoi").removeClass("ui-tabs-active");
                                $("#selectedLeasepoi").removeClass("ui-state-active");
                                $("#selectedsleasedetails").addClass("ui-tabs-active");
                                $("#selectedsleasedetails").addClass("ui-state-active");




                                $("#comment_Save").hide();
                                $("#comment_Next").show();
                            } else if ($('#Lease_Details').css('display') == 'block')
                            {

                                if (processid == "5")
                                {
                                    saveattributesSurrenderLease();
                                } else
                                {
                                    saveattributesLeaseDetails();
                                }

                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#Applicant_Details").hide();







                                $("#comment_Save").show();
                                $("#comment_Next").hide();
                            }

                        } else if (currentdiv == "Mortgage")
                        {

                            if ($('#MortgageOwner_Details').css('display') == 'block')
                            {
                                $("#MortgageOwner_Details").hide();
                                $("#Land_Details_Mortgage").show();
                                $("#Mortgage_Details").hide();
                                $("#Upload_Document_Mortgage").hide();
                                $("#selectedownerdetails").removeClass("ui-tabs-active");
                                $("#selectedownerdetails").removeClass("ui-state-active");
                                $("#selectelandmortgage").addClass("ui-tabs-active");
                                $("#selectelandmortgage").addClass("ui-state-active");
                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Land_Details_Mortgage').css('display') == 'block')
                            {
                                $("#MortgageOwner_Details").hide();
                                $("#Land_Details_Mortgage").hide();
                                $("#Mortgage_Details").show();
                                $("#Upload_Document_Mortgage").hide();
                                $("#selectelandmortgage").removeClass("ui-tabs-active");
                                $("#selectelandmortgage").removeClass("ui-state-active");
                                $("#selectemortgage").addClass("ui-tabs-active");
                                $("#selectemortgage").addClass("ui-state-active");



                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Mortgage_Details').css('display') == 'block')
                            {

                                if (processid == "9")
                                {
                                    saveattributesSurrenderMortagage();
                                } else
                                {
                                    saveMortgage();
                                }

                                $("#MortgageOwner_Details").hide();
                                $("#Land_Details_Mortgage").hide();
                                $("#Mortgage_Details").hide();
                                $("#Upload_Document_Mortgage").show();
                                $("#selectemortgage").removeClass("ui-tabs-active");
                                $("#selectemortgage").removeClass("ui-state-active");
                                $("#selectemortgagedocs").addClass("ui-tabs-active");
                                $("#selectemortgagedocs").addClass("ui-state-active");
                                $("#comment_Save").show();
                                $("#comment_Next").hide();
                            }

                        } else if (currentdiv == "split") {
                            $("#comment_Save").hide();
                            $("#comment_Next").show();
                            attributeEditDialog.dialog("close");
                            showOnMap(selectedlandid, 0, "split");
                        }
                    }

                },
                {
                    text: $.i18n("gen-save"),
                    "id": "comment_Save",
                    click: function ()
                    {

//					$("#buyersavebutton").prop("disabled", false).hide();
//					 $("input,select,textarea").removeClass('addBg');
                        if (currentdiv == "Sale")
                        {
                            saveattributessale();

                        } else if (currentdiv == "Lease")
                        {

                            saveattributesLease();
                        } else
                        {
                            saveattributesMortagage()

                        }
                    }

                },
                {
                    text: $.i18n("gen-cancel"),
                    "id": "comment_cancel",
                    click: function () {
                        $("input,select,textarea").removeClass('addBg');
                        setInterval(function () {

                        }, 4000);

                        attributeEditDialog.dialog("close");

                    }

                }


            ],
            Cancel: function () {

                attributeEditDialog.dialog("close");
                $("input,select,textarea").removeClass('addBg');

            }
        });
        $("#comment_cancel").html('<span class="ui-button-text">' + $.i18n("gen-cancel") + '</span>');
        attributeEditDialog.dialog("open");
        $("#comment_Save").hide();
        $("#comment_Next").hide();

    });
}

function FillDataforRegistration(selectedlandid) {
//	 isVisible = $('#buyersavebutton').is(':visible');

    if (laMortgage_R.length == 0) {
        jQuery("#mortgage_to").val("");
        jQuery("#mortgage_from").val("");

    }

    var lease = document.getElementById("Tab_1");
    var sale = document.getElementById("Tab_2");
    var mortgage = document.getElementById("Tab_3");
    lease.style.display = "none"; // lease.style.display = "block";
    sale.style.display = "none";
    mortgage.style.display = "none";

    firstselectedprocess = $("#registration_process").val();
    clearBuyerDetails_sale();


    $("#buyersavebutton").prop("disabled", false).hide();
    landid = selectedlandid;
    $("#landidhide").val(landid);
    /*
     * jQuery.ajax({ url: "registration/landsharetype111111/" , async: false,
     * success: function (data) { landsharetype=data; alert('1');
     * if(data.length>0){
     *  } } });
     */

    jQuery.ajax({
        url: "registration/doctype/",
        async: false,
        success: function (data) {
            documenttype_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/monthoflease/",
        async: false,
        success: function (data) {
            monthoflease_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/financialagency/",
        async: false,
        success: function (data) {
            financialagency_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/laspatialunitland/" + landid,
        async: false,
        success: function (data) {
            laspatialunitland_R = data;
            if (data.length > 0) {

            }
        }
    });

    jQuery.ajax({
        url: "registration/relationshiptypes/",
        async: false,
        success: function (data) {
            relationtypes_R = data;

        }
    });

    // -------------------------------------------------------------------------------------------------------------------
    // onchange of Country pass this select id for country_r_id --
    jQuery.ajax({
        url: "registration/allregion/" + country_r_id,
        async: false,
        success: function (data) {
            region_r = data;
            if (data.length > 0) {
                // data.xyz.name_en for getting the data
            }
        }
    });

    jQuery.ajax({
        url: "registration/allprovince/" + region_r_id,
        async: false,
        success: function (data) {
            province_r = data;
        }
    });





    // jAlert('This Land is not under Lease.');

    fillBuyerandSellerpage(landid);
}


function fillSurrenderLeaseDetails(landid)
{
    $('#newLeaseeRecordsRowDataSale').empty();

    var URL_lease = "";

    if (edit == 0) {
        URL_lease = "registration/partydetailssurrenderlease/" + landid;
    } else if (edit == 1) {
        URL_lease = "registration/editpartydetailssurrenderlease/" + landid + "/" + finaltransid;

    }

    jQuery
            .ajax({
                url: URL_lease,
                async: false,
                success: function (data) {
                    if (data != "") {

                        leaseepersondata = data;

                        $('#newLeaseeRecordsAttrTemplateSale').tmpl(data).appendTo('#newLeaseeRecordsRowDataSale');
                        $('#newLeaseeRecordsRowDataSale').i18n();
                        /*
                         $("#leaseepersonid").val(data.personid);
                         $("#leaseeperson").val(data.personid);*/

                        jQuery("#lease_Amount").val(leaseepersondata[0].hierarchyid1);
                        jQuery("#no_Of_month_Lease").val(leaseepersondata[0].hierarchyid2);
                        jQuery("#Start_date_Lease").val(leaseepersondata[0].leaseStartdate);
                        jQuery("#End_date_Lease").val(leaseepersondata[0].leaseEnddate);

                    }

                    jQuery("#martial_sts_applicant").empty();
                    jQuery("#gender_type_applicant").empty();
                    jQuery("#id_type_applicant").empty();

                    jQuery("#id_type_applicant").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#gender_type_applicant").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#martial_sts_applicant").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));

                    jQuery.each(maritalStatus_R, function (i, obj) {
                        jQuery("#martial_sts_applicant").append(
                                jQuery("<option></option>").attr("value",
                                obj.maritalstatusid).text(
                                obj.maritalstatusEn));
                    });


                    jQuery.each(genderStatus_R, function (i, obj1) {
                        jQuery("#gender_type_applicant").append(
                                jQuery("<option></option>").attr("value",
                                obj1.genderId).text(obj1.gender_en));
                    });


                    jQuery.each(idtype_R, function (i, obj1) {
                        jQuery("#id_type_applicant").append(
                                jQuery("<option></option>").attr("value",
                                obj1.identitytypeid).text(
                                obj1.identitytypeEn));
                    });                    
                }
            });


}

function Hidedocumentgrid()
{
    $("#Upload_Document_Sale").hide();
}

function ShowSavebutton()
{
    $("#comment_Save").show();
    $("#comment_Next").hide();
}

function SellerTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Seller_Details").show();
    $("#Upload_Document_Sale").hide();
    $("#Land_Details_Sale").hide();
    $("#Buyer_Details").hide();
    $("#regPoi").hide();
    $("#selectedseller").addClass("ui-tabs-active");
    $("#selectedseller").addClass("ui-state-active");
    $("#selectedland").removeClass("ui-tabs-active");
    $("#selectedland").removeClass("ui-state-active");
    $("#selecteddoc").removeClass("ui-tabs-active");
    $("#selecteddoc").removeClass("ui-state-active");
    $("#selectedbuyer").removeClass("ui-tabs-active");
    $("#selectedbuyer").removeClass("ui-state-active");
    $("#selectedpoi").removeClass("ui-tabs-active");
    $("#selectedpoi").removeClass("ui-state-active");


}


function LandTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();
    $("#Seller_Details").hide();
    $("#Upload_Document_Sale").hide();
    $("#Land_Details_Sale").show();
    $("#Buyer_Details").hide();
    $("#regPoi").hide();
    $("#selectedseller").removeClass("ui-tabs-active");
    $("#selectedseller").removeClass("ui-state-active");
    $("#selectedland").addClass("ui-tabs-active");
    $("#selectedland").addClass("ui-state-active");
    $("#selecteddoc").removeClass("ui-tabs-active");
    $("#selecteddoc").removeClass("ui-state-active");
    $("#selectedbuyer").removeClass("ui-tabs-active");
    $("#selectedbuyer").removeClass("ui-state-active");
    $("#selectedpoi").removeClass("ui-tabs-active");
    $("#selectedpoi").removeClass("ui-state-active");

}

function BuyerTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Seller_Details").hide();
    $("#Upload_Document_Sale").hide();
    $("#Land_Details_Sale").hide();
    $("#Buyer_Details").show();
    $("#regPoi").hide();
    $("#selectedseller").removeClass("ui-tabs-active");
    $("#selectedseller").removeClass("ui-state-active");
    $("#selectedland").removeClass("ui-tabs-active");
    $("#selectedland").removeClass("ui-state-active");
    $("#selecteddoc").removeClass("ui-tabs-active");
    $("#selecteddoc").removeClass("ui-state-active");
    $("#selectedbuyer").addClass("ui-tabs-active");
    $("#selectedbuyer").addClass("ui-state-active");
    $("#selectedpoi").removeClass("ui-tabs-active");
    $("#selectedpoi").removeClass("ui-state-active");

}

function poiTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Seller_Details").hide();
    $("#Upload_Document_Sale").hide();
    $("#Land_Details_Sale").hide();
    $("#Buyer_Details").hide();
    $("#regPoi").show();
    $("#selectedseller").removeClass("ui-tabs-active");
    $("#selectedseller").removeClass("ui-state-active");
    $("#selectedland").removeClass("ui-tabs-active");
    $("#selectedland").removeClass("ui-state-active");
    $("#selecteddoc").removeClass("ui-tabs-active");
    $("#selecteddoc").removeClass("ui-state-active");
    $("#selectedbuyer").removeClass("ui-tabs-active");
    $("#selectedbuyer").removeClass("ui-state-active");
    $("#selectedpoi").addClass("ui-tabs-active");
    $("#selectedpoi").addClass("ui-state-active");

}

function SaleDocTabClick()
{
    $("#comment_Save").show();
    $("#comment_Next").hide();

    $("#Seller_Details").hide();
    $("#Upload_Document_Sale").show();
    $("#Land_Details_Sale").hide();
    $("#Buyer_Details").hide();
    $("#regPoi").hide();
    $("#selectedseller").removeClass("ui-tabs-active");
    $("#selectedseller").removeClass("ui-state-active");
    $("#selectedland").removeClass("ui-tabs-active");
    $("#selectedland").removeClass("ui-state-active");
    $("#selecteddoc").addClass("ui-tabs-active");
    $("#selecteddoc").addClass("ui-state-active");
    $("#selectedbuyer").removeClass("ui-tabs-active");
    $("#selectedbuyer").removeClass("ui-state-active");
    $("#selectedpoi").removeClass("ui-tabs-active");
    $("#selectedpoi").removeClass("ui-state-active");


}

function fillMortgageDetails(data) {

    jQuery("#firstname_r_mortgage").val(data.firstname);
    jQuery("#middlename_r_mortgage").val(data.middlename);
    jQuery("#lastname_r_mortgage").val(data.lastname);
    jQuery("#id_r_mortgage").val(data.identityno);
    jQuery("#contact_no_mortgage").val(data.contactno);
    jQuery("#address_mortgage").val(data.address);
    jQuery("#date_Of_birth_mortgage").val(data.dob);

    if (laMortgage_R.mortgagefrom != null || laMortgage_R.mortgagefrom != "") {
        jQuery("#mortgage_from").val(formatDate_R(laMortgage_R.mortgagefrom));
    } else {
        jQuery("#mortgage_from").val("");
    }
    if (laMortgage_R.mortgageto != null || laMortgage_R.mortgageto != "") {
        jQuery("#mortgage_to").val(formatDate_R(laMortgage_R.mortgageto));
    } else {
        jQuery("#mortgage_to").val("");
    }
    jQuery("#amount_mortgage").val(laMortgage_R.mortgageamount);


    jQuery("#Martial_sts_mortgage").empty();
    jQuery("#gender_mortgage").empty();
    jQuery("#id_type_mortgage").empty();
    jQuery("#mortgage_Financial_Agencies").empty();
    jQuery("#doc_Type_Mortgage").empty();

    jQuery("#Martial_sts_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#gender_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#id_type_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#mortgage_Financial_Agencies").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#doc_Type_Mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));

    jQuery.each(documenttype_R, function (i, obj) {
        jQuery("#doc_Type_Mortgage").append(
                jQuery("<option></option>").attr("value", obj.code).text(
                obj.nameOtherLang));
    });

    jQuery.each(financialagency_R, function (i, obj1) {
        jQuery("#mortgage_Financial_Agencies").append(
                jQuery("<option></option>").attr("value",
                obj1.financialagencyid).text(obj1.financialagency_en));
    });

    if (laMortgage_R != "" && laMortgage_R != null)
    {
        if (laMortgage_R.laExtFinancialagency != null && laMortgage_R.laExtFinancialagency != "")
        {
            jQuery("#mortgage_Financial_Agencies").val(laMortgage_R.laExtFinancialagency.financialagencyid);
        }
    }

    jQuery.each(maritalStatus_R, function (i, obj) {
        jQuery("#Martial_sts_mortgage").append(
                jQuery("<option></option>").attr("value", obj.maritalstatusid)
                .text(obj.maritalstatusEn));
    });

    jQuery("#Martial_sts_mortgage").val(data.maritalstatusid);

    jQuery.each(genderStatus_R, function (i, obj1) {
        jQuery("#gender_mortgage").append(
                jQuery("<option></option>").attr("value", obj1.genderId).text(
                obj1.gender_en));
    });

    jQuery("#gender_mortgage").val(data.genderid);

    jQuery.each(idtype_R, function (i, obj1) {
        jQuery("#id_type_mortgage").append(
                jQuery("<option></option>").attr("value", obj1.identitytypeid)
                .text(obj1.identitytypeEn));
    });
    jQuery("#id_type_mortgage").val(data.identitytypeid);

    // Land Details landtype_r
    jQuery("#Country_mortgage").empty();
    jQuery("#Region_mortgage").empty();
    jQuery("#Provience_mortgage").empty();
    jQuery("#Land_type_mortgage").empty();
    jQuery("#Ownership_type_mortgage").empty();

    jQuery("#Country_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#Region_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#Provience_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#Land_type_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#Ownership_type_mortgage").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));

    jQuery.each(allcountry, function (i, obj) {
        jQuery("#Country_mortgage").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });

    jQuery.each(region_r, function (i, obj) {
        jQuery("#Region_mortgage").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });
    jQuery.each(province_r, function (i, obj) {
        jQuery("#Provience_mortgage").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });

    jQuery.each(landsharetype_r, function (i, obj) {
        jQuery("#Ownership_type_mortgage").append(
                jQuery("<option></option>").attr("value", obj.landsharetypeid)
                .text(obj.landsharetypeEn));
    });
    jQuery.each(landtype_r, function (i, obj1) {
        jQuery("#Land_type_mortgage").append(
                jQuery("<option></option>").attr("value", obj1.landtypeid)
                .text(obj1.landtypeEn));
    });

    jQuery("#Country_mortgage").val(laspatialunitland_R[0].hierarchyid1);
    jQuery("#Region_mortgage").val(laspatialunitland_R[0].hierarchyid2);
    jQuery("#Provience_mortgage").val(laspatialunitland_R[0].hierarchyid3);
    jQuery("#Land_type_mortgage").val(laspatialunitland_R[0].landtypeid);
    jQuery("#parcel_r_mortgage").val("000000" + laspatialunitland_R[0].landid);
    jQuery("#Ownership_type_mortgage")
            .val(laspatialunitland_R[0].landusetypeid);
    jQuery("#mortgage_area").val(laspatialunitland_R[0].area);
    jQuery("#mortgage_land_use").val(laspatialunitland_R[0].landusetype_en);

    jQuery("#neighbor_west_mortgage").val(laspatialunitland_R[0].neighbor_west);
    jQuery("#neighbor_north_mortgage").val(
            laspatialunitland_R[0].neighbor_north);
    jQuery("#neighbor_south_mortgage").val(
            laspatialunitland_R[0].neighbor_south);
    jQuery("#neighbor_east_mortgage").val(laspatialunitland_R[0].neighbor_east);
}

function fillLeaseDetails(data) {
    jQuery("#firstname_r_lease").val(data.firstname);
    jQuery("#middlename_r_lease").val(data.middlename);
    jQuery("#lastname_r_lease").val(data.lastname);
    jQuery("#id_r_lease").val(data.identityno);
    jQuery("#contact_no_lease").val(data.contactno);
    jQuery("#address_lease").val(data.address);
    jQuery("#date_Of_birth_lease").val(data.dob);

    jQuery("#lease_martial_sts").empty();
    jQuery("#lease_gender").empty();
    jQuery("#lease_id_type").empty();
    jQuery("#doc_Type_Lease").empty();
    // jQuery("#no_Of_month_Lease").empty();

    jQuery("#lease_martial_sts").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#lease_gender").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#lease_id_type").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#doc_Type_Lease").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    
    jQuery.each(documenttype_R, function (i, obj) {
        jQuery("#doc_Type_Lease").append(
                jQuery("<option></option>").attr("value", obj.code).text(
                obj.nameOtherLang));
    });

    jQuery.each(maritalStatus_R, function (i, obj) {
        jQuery("#lease_martial_sts").append(
                jQuery("<option></option>").attr("value", obj.maritalstatusid)
                .text(obj.maritalstatusEn));
    });

    jQuery("#lease_martial_sts").val(data.maritalstatusid);

    jQuery.each(genderStatus_R, function (i, obj1) {
        jQuery("#lease_gender").append(
                jQuery("<option></option>").attr("value", obj1.genderId).text(
                obj1.gender_en));
    });

    jQuery("#lease_gender").val(data.genderid);

    jQuery.each(idtype_R, function (i, obj1) {
        jQuery("#lease_id_type").append(
                jQuery("<option></option>").attr("value", obj1.identitytypeid)
                .text(obj1.identitytypeEn));
    });
    jQuery("#lease_id_type").val(data.identitytypeid);
    jQuery("#identity_no").val(data.identitytype)

    jQuery("#id_type_applicant").empty();
    jQuery("#gender_type_applicant").empty();
    jQuery("#martial_sts_applicant").empty();
    jQuery("#id_type_applicant").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#gender_type_applicant").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#martial_sts_applicant").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));

    jQuery.each(maritalStatus_R, function (i, obj) {
        jQuery("#martial_sts_applicant").append(
                jQuery("<option></option>").attr("value", obj.maritalstatusid)
                .text(obj.maritalstatusEn));
    });

    jQuery.each(genderStatus_R, function (i, obj1) {
        jQuery("#gender_type_applicant").append(
                jQuery("<option></option>").attr("value", obj1.genderId).text(
                obj1.gender_en));
    });

    jQuery.each(idtype_R, function (i, obj1) {
        jQuery("#id_type_applicant").append(
                jQuery("<option></option>").attr("value", obj1.identitytypeid)
                .text(obj1.identitytypeEn));
    });

    // Land Details landtype_r
    jQuery("#lease_Country").empty();
    jQuery("#lease_Region").empty();
    jQuery("#lease_Provience").empty();
    jQuery("#lease_Land_Type").empty();
    jQuery("#lease_Ownership_Type").empty();

    jQuery("#lease_Country").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#lease_Region").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#lease_Provience").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#lease_Land_Type").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#lease_Ownership_Type").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));

    jQuery.each(allcountry, function (i, obj) {
        jQuery("#lease_Country").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });

    jQuery.each(region_r, function (i, obj) {
        jQuery("#lease_Region").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });
    jQuery.each(province_r, function (i, obj) {
        jQuery("#lease_Provience").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });

    jQuery.each(landsharetype_r, function (i, obj) {
        jQuery("#lease_Ownership_Type").append(
                jQuery("<option></option>").attr("value", obj.landsharetypeid)
                .text(obj.landsharetypeEn));
    });
    jQuery.each(landtype_r, function (i, obj1) {
        jQuery("#lease_Land_Type").append(
                jQuery("<option></option>").attr("value", obj1.landtypeid)
                .text(obj1.landtypeEn));
    });

    jQuery("#lease_Country").val(laspatialunitland_R[0].hierarchyid1);
    jQuery("#lease_Region").val(laspatialunitland_R[0].hierarchyid2);
    jQuery("#lease_Provience").val(laspatialunitland_R[0].hierarchyid3);
    jQuery("#lease_Land_Type").val(laspatialunitland_R[0].landtypeid);
    jQuery("#parcel_r_lease").val("000000" + laspatialunitland_R[0].landid);
    jQuery("#lease_Ownership_Type").val(laspatialunitland_R[0].landusetypeid);
    jQuery("#lease_area").val(laspatialunitland_R[0].area);

    jQuery("#neighbor_west_lease").val(laspatialunitland_R[0].neighbor_west);
    jQuery("#neighbor_north_lease").val(laspatialunitland_R[0].neighbor_north);
    jQuery("#neighbor_south_lease").val(laspatialunitland_R[0].neighbor_south);
    jQuery("#neighbor_east_lease").val(laspatialunitland_R[0].neighbor_east);

}

function savebuyerdetails()
{
    isVisible = true;
    var selectedprocess = $("#registration_process").val();
    if (selectedprocess == 7)
    {
        var eliminatedowner = $("#Owner_Elimanated").val();
        if (eliminatedowner == null || eliminatedowner == "")
        {
            jAlert($.i18n("err-select-eliminated-owner"), $.i18n("err-alert"));
            return false;
        }

    }

    if (selectedprocess == 6)
    {
        var ownerrelation = $("#sale_relation_buyer").val();
        if (ownerrelation == 0)
        {
            jAlert($.i18n("err-select-rel-with-owner"), $.i18n("err-alert"));
            return false;
        }

    }


    if ($("#editprocessAttributeformID").valid()) {

        if ((firstname_r_sale1.value.length == 0)
                || (lastname_r_sale1.value.length == 0)
                || (id_r1.value.length == 0) || (contact_no1.value.length == 0)
                || (date_Of_birth_sale1.value.length == 0)
                || (address_sale1.value.length == 0)) { // ||
            // (middlename_r_sale1.value.length
            // == 0)
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        } else if ((sale_idtype_buyer.value == "0")
                || (sale_gender_buyer.value == "0")
                || (sale_marital_buyer.value == "0")) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        }





        jQuery
                .ajax({
                    type: "POST",
                    url: "registration/savebuyerdata",
                    data: jQuery("#editprocessAttributeformID")
                            .serialize(),
                    success: function (result) {


                        if (result != null && result != undefined) {
                            $("input,select,textarea").removeClass('addBg');
                            $("#buyersavebutton").prop("disabled", false).hide();
                            jQuery("#personid").val(0);
                            jQuery("#firstname_r_sale1").val("");
                            jQuery("#middlename_r_sale1").val("");
                            jQuery("#lastname_r_sale1").val("");
                            jQuery("#id_r1").val("");
                            jQuery("#contact_no1").val("");
                            jQuery("#address_sale1").val("");
                            jQuery("#date_Of_birth_sale1").val("");

                            jQuery("#sale_gender_buyer").val("");
                            jQuery("#sale_idtype_buyer").val("");
                            jQuery("#sale_marital_buyer").val("");


                            SaleOwnerdBuyeretails(finallandid);
                            salebuyerdetails(selectedprocess);
                            $("#Seller_Details").hide();
                            $("#Buyer_Details").show();
                            $("#Land_Details_Sale").hide();
                            $("#regPoi").hide();
                            $("#Upload_Document_Sale").hide();
                            $("#selectedbuyer").addClass("ui-tabs-active");
                            $("#selectedbuyer").addClass("ui-state-active");
                            $("#selectedpoi").removeClass("ui-tabs-active");
                            $("#selectedpoi").removeClass("ui-state-active");
                            $("#comment_Save").hide();
                            $("#comment_Next").show();
                            /*$("#editRegpersonsEditingGrid").hide();
                             $("#RegLeasepersonsEditingGrid").show();
                             $("#editRegpersonsEditingGridLeasee").hide();*/

                            jAlert($.i18n("reg-buyer-saved"));
                        } else {
                            jAlert($.i18n("err-request-not-completed"));
                        }
                    },
                    error: function (XMLHttpRequest,
                            textStatus, errorThrown) {
                        jAlert($.i18n("err-request-not-completed"));
                    }
                });



    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }

}

function saveattributessale() {
//	 var isVisible = $('#buyersavebutton').is(':visible');
    if (isVisible) {

        var selectedprocess = $("#registration_process").val();
        if (selectedprocess == 7)
        {
            var eliminatedowner = $("#Owner_Elimanated").val();
            if (eliminatedowner == null || eliminatedowner == "")
            {
                jAlert($.i18n("err-select-eliminated-owner"), $.i18n("err-alert"));
                return false;
            }

        }

        $("#editprocessAttributeformID").validate({
            rules: {
                remrks_sale: "required"
            },
            messages: {
                remrks_sale: $.i18n("err-enter-comments")
            }
        });

        if ($("#editprocessAttributeformID").valid()) {

            if (arry_Buyerbyprocessid == null) { // ||
                // (middlename_r_sale1.value.length
                // == 0)
                jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
                return false;
            }

            jConfirm(
                    $.i18n("gen-save-confirmation"),
                    $.i18n("gen-confirmation"),
                    function (response) {
                        if (response) {

                            jQuery
                                    .ajax({
                                        type: "POST",
                                        url: "registration/savefinalsaledata",
                                        data: jQuery("#editprocessAttributeformID")
                                                .serialize(),
                                        success: function (result) {
                                            if (result != null && result != undefined) {
                                                landRecordsInitialized_R = false;
                                                displayRefreshedRegistryRecords_ABC();
                                                //RegistrationRecords("registrationRecords");
                                                jAlert($.i18n("gen-data-saved"));
                                                attributeEditDialog.dialog("close");
                                            } else {
                                                jAlert($.i18n("err-request-not-completed"));
                                            }
                                        },
                                        error: function (XMLHttpRequest,
                                                textStatus, errorThrown) {
                                            jAlert($.i18n("err-request-not-completed"));
                                        }
                                    });
                        }
                    });

        } else {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
        }
    } else if (selectedprocess == 2) {
        jAlert($.i18n("err-save-new-buyer"), $.i18n("gen-info"));
    } else {
        jAlert($.i18n("err-save-new-owner"), $.i18n("gen-info"));
    }
}
function saveattributesLease()
{
    var selectedprocess = $("#registration_process").val();
    if (selectedprocess == 5)
    {
        approveSurrenderLease();
    } else
    {
        saveattributesLeaseData();
    }

}


function saveattributesMortagage()
{
    var selectedprocess = $("#registration_process").val();
    if (selectedprocess == 9)
    {
        approveSurrenderMortagage();
    } else
    {
        saveattributesMortgage();
    }

}

function saveattributesLeaseData() {

    if ($("#editprocessAttributeformID").valid()) {



        if ((no_Of_month_Lease.value.length == 0)
                || (lease_Amount.value.length == 0)) {// (no_Of_years_Lease.value.length
            // == 0) ||
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        }

        jConfirm(
                $.i18n("gen-save-confirmation"),
                $.i18n("gen-confirmation"),
                function (response) {
                    if (response) {
                        jQuery
                                .ajax({
                                    type: "POST",
                                    url: "registration/saveleasedata",
                                    data: jQuery("#editprocessAttributeformID")
                                            .serialize(),
                                    success: function (result) {
                                        jAlert($.i18n("gen-data-saved"));
                                        $('#leaseeperson').val(0);
                                        landRecordsInitialized_R = false;
                                        displayRefreshedRegistryRecords_ABC();
                                        attributeEditDialog.dialog("close");
                                        //	RegistrationRecords("registrationRecords");
                                    },
                                    error: function (XMLHttpRequest,
                                            textStatus, errorThrown) {
                                        jAlert($.i18n("err-request-not-completed"));
                                    }
                                });
                    }
                });

    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}

function saveattributesSurrenderLease() {

    if ($("#editprocessAttributeformID").valid() && leaseepersondata.length > 0) {

        jQuery
                .ajax({
                    type: "POST",
                    url: "registration/savesurrenderleasedata",
                    data: jQuery("#editprocessAttributeformID")
                            .serialize(),
                    success: function (result) {
                        if (result != null && result != undefined) {
                            $('#leaseeperson').val(0);
                            $("#Lease_Details").hide();
                            $("#regLeasePoi").hide();
                            $("#Upload_Documents_Lease").show();
                            $("#Lease_Details").hide();
                            $("#selectedsleasedetails").removeClass("ui-tabs-active");
                            $("#selectedsleasedetails").removeClass("ui-state-active");
                            $("#selecteddocs").addClass("ui-tabs-active");
                            $("#selecteddocs").addClass("ui-state-active");

                        } else {
                            jAlert($.i18n("err-request-not-completed"));
                        }
                    },
                    error: function (XMLHttpRequest,
                            textStatus, errorThrown) {
                        jAlert($.i18n("err-request-not-completed"));
                    }
                });


    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}

function approveSurrenderLease() {

    if ($("#editprocessAttributeformID").valid()) {

        if ($("#surrender_reason").val() == "") {
            jAlert($.i18n("err-fill-surender-reason"), $.i18n("err-alert"));
            return false;
        }


        jConfirm(
                $.i18n("gen-save-confirmation"),
                $.i18n("gen-confirmation"),
                function (response) {
                    if (response) {
                        jQuery
                                .ajax({
                                    type: "POST",
                                    url: "registration/approvesurrenderleasedata",
                                    data: jQuery("#editprocessAttributeformID")
                                            .serialize(),
                                    success: function (result) {
                                        if (result != null && result != undefined) {
                                            $('#leaseeperson').val(0);
                                            landRecordsInitialized_R = false;
                                            displayRefreshedRegistryRecords_ABC();
                                            attributeEditDialog.dialog("close");
                                            //  RegistrationRecords("registrationRecords");

                                        } else {
                                            jAlert($.i18n("err-request-not-completed"));
                                        }
                                    },
                                    error: function (XMLHttpRequest,
                                            textStatus, errorThrown) {
                                        jAlert($.i18n("err-request-not-completed"));
                                    }
                                });
                    }
                });

    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}



function saveattributesMortgage() {


    /*	$("#editprocessAttributeformID").validate({
     rules : {
     remrks_mortgage : "required"
     },
     messages : {
     remrks_mortgage : "Please enter remarks"
     }
     });
     */
    if ($("#editprocessAttributeformID").valid()) {
        if ((mortgage_from.value.length == 0)
                || (mortgage_to.value.length == 0)
                || (amount_mortgage.value.length == 0)) {
            setTimeout(function () {
                $("#Tab_3").tabs({
                    active: 2
                });
                $("#editprocessAttributeformID").validate({
                    rules: {
                        mortgage_from: "required",
                        mortgage_to: "required",
                        amount_mortgage: "required"

                    },
                    messages: {
                        mortgage_from: "Please Enter mortgage from",
                        mortgage_to: "Please enter mortgage to",
                        amount_mortgage: "Please enter amount of mortgage"
                    }
                });
            }, 500);
        } else if ((mortgage_Financial_Agencies.value == "0")) {
            setTimeout(function () {
                $("#Tab_3").tabs({
                    active: 2
                });
                $("#editprocessAttributeformID").validate({
                    rules: {
                        mortgage_Financial_Agencies: {
                            required: true
                        },
                    },
                    messages: {
                        mortgage_Financial_Agencies: "Enter First name"

                    }
                });
            }, 500);
        }
    }

    if ($("#editprocessAttributeformID").valid()) {

        jConfirm(
                $.i18n("gen-save-confirmation"),
                $.i18n("gen-confirmation"),
                function (response) {
                    if (response) {
                        jQuery
                                .ajax({
                                    type: "POST",
                                    url: "registration/updateMortgageData",
                                    data: jQuery("#editprocessAttributeformID")
                                            .serialize(),
                                    success: function (result) {
                                        if (result != null && result != undefined) {
                                            landRecordsInitialized_R = false;
                                            displayRefreshedRegistryRecords_ABC();
                                            //RegistrationRecords("registrationRecords");
                                            attributeEditDialog.dialog("close");

                                        } else {
                                            jAlert($.i18n("err-request-not-completed"));
                                        }
                                    },
                                    error: function (XMLHttpRequest,
                                            textStatus, errorThrown) {
                                        jAlert($.i18n("err-request-not-completed"));
                                    }
                                });
                    }

                });

    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}

function getRegionOnChangeCountry(id) {
    if (id != '') {

        $("#sale_region").empty();
        jQuery("#sale_region").append(
                jQuery("<option></option>").attr("value", "").text($.i18n("gen-select")));
        jQuery.ajax({
            url: "registration/allregion/" + id,
            async: false,
            success: function (regiondata) {
                var proj_region = regiondata;
                jQuery.each(proj_region, function (i, value) {
                    jQuery("#sale_region").append(
                            jQuery("<option></option>").attr("value",
                            value.hierarchyid).text(value.nameEn));
                });
            }
        });
    }
}

function getprocessvalue(id) {

    FillDataforRegistration(selectedlandid);
    var lease = document.getElementById("Tab_1");
    var sale = document.getElementById("Tab_2");
    var mortgage = document.getElementById("Tab_3");
    var split = document.getElementById("Tab_4");
    lease.style.isplay = "none"; // lease.style.display = "block";
    sale.style.display = "none";
    mortgage.style.display = "none";
    $("#RelationWithOwner").css("display", "none");
    $("#comment_Next").show();
    $("#comment_Save").hide();


    if (id == 1) // Lease
    {

        $("#leaseeperson").val(0);
        jQuery("#firstname_r_applicant").val('');
        jQuery("#middlename_r_applicant").val('');
        jQuery("#lastname_r_applicant").val('');
        jQuery("#id_r_applicant").val('');
        jQuery("#contact_no_applicant").val('');
        jQuery("#address_lease_applicant").val('');
        jQuery("#date_Of_birth_applicant").val('');


        $("#doc_name_Lease").val('');
        $("#doc_date_Lease").val('');
        $("#doc_desc_Lease").val('');
        $("#tabs_registry").tabs({active: 0});
        $("#Owner_Details").show();
        $("#Land_Details_lease").hide();
        $("#Applicant_Details").hide();
        $("#Lease_Details").hide();
        $("#Upload_Documents_Lease").hide();
        $("#selectedowner").addClass("ui-tabs-active");
        $("#selectedowner").addClass("ui-state-active");
        $("#selectedLanddetails").removeClass("ui-tabs-active");
        $("#selectedLanddetails").removeClass("ui-state-active");
        $("#selectedApplicant").removeClass("ui-tabs-active");
        $("#selectedApplicant").removeClass("ui-state-active");
        $("#selectedsleasedetails").removeClass("ui-tabs-active");
        $("#selectedsleasedetails").removeClass("ui-state-active");
        $("#selecteddocs").removeClass("ui-tabs-active");
        $("#selecteddocs").removeClass("ui-state-active");
        $('.trhideclass1').hide();
        $("#surrender_reason").val('');

        jQuery("#lease_land_use").val(
                laspatialunitland_R[0].landusetype_en);
        processid = id;
        isVisible = true;
        lease.style.display = "block";
        sale.style.display = "none";
        mortgage.style.display = "none";
        split.style.display = "none";
        clearBuyerDetails_sale();
//		clear_Lease();
        currentdiv = "Lease";

        fetchDocument(selectedlandid, 1, id);

        loadResleaseePersonsOfEditingForEditing();




    } else if (id == 5) // "Surrender of Lease"
    {
        $("#doc_name_Lease").val('');
        $("#doc_date_Lease").val('');
        $("#doc_desc_Lease").val('');

        $("#tabs_registry").tabs({active: 0});
        $("#Owner_Details").show();
        $("#Land_Details_lease").hide();
        $("#Applicant_Details").hide();
        $("#Lease_Details").hide();
        $("#Upload_Documents_Lease").hide();
        $("#selectedowner").addClass("ui-tabs-active");
        $("#selectedowner").addClass("ui-state-active");
        $("#selectedLanddetails").removeClass("ui-tabs-active");
        $("#selectedLanddetails").removeClass("ui-state-active");
        $("#selectedApplicant").removeClass("ui-tabs-active");
        $("#selectedApplicant").removeClass("ui-state-active");
        $("#selectedsleasedetails").removeClass("ui-tabs-active");
        $("#selectedsleasedetails").removeClass("ui-state-active");
        $("#selecteddocs").removeClass("ui-tabs-active");
        $("#selecteddocs").removeClass("ui-state-active");
        $('.trhideclass1').show();
        $("#surrender_reason").val('');
        jQuery("#lease_land_use").val(
                laspatialunitland_R[0].landusetype_en);
        processid = id;
        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "block";
        sale.style.display = "none";
        mortgage.style.display = "none";
        split.style.display = "none";
        clearBuyerDetails_sale();
        currentdiv = "Lease";

        fetchDocument(selectedlandid, 1, id);
        loadResPersonsOfEditingForEditing();

    } else if (id == 2) // "Sale"
    {
        salebuyerdetails(id);
        $("#tabs_registry1").tabs({active: 0});
        $("#Seller_Details").show();
        $("#Land_Details_Sale").hide();
        $("#regPoi").hide();
        $("#Buyer_Details").hide();
        $("#Upload_Document_Sale").hide();
        $("#selectedseller").addClass("ui-tabs-active");
        $("#selectedseller").addClass("ui-state-active");
        $("#selectedland").removeClass("ui-tabs-active");
        $("#selectedland").removeClass("ui-state-active");
        $("#selectedbuyer").removeClass("ui-tabs-active");
        $("#selectedbuyer").removeClass("ui-state-active");
        $("#selecteddoc").removeClass("ui-tabs-active");
        $("#selecteddoc").removeClass("ui-state-active");
        $("#selectedpoi").removeClass("ui-tabs-active");
        $("#selectedpoi").removeClass("ui-state-active");

        processid = id;
        persontypeid = 11;
        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "none";
        sale.style.display = "block";
        mortgage.style.display = "none";
        split.style.display = "none";

        currentdiv = "Sale";

        fetchDocument(selectedlandid, 11, id);
        $("#EliminatedOwner").css("display", "none");

        $("#selectedselleranchor").text($.i18n("reg-seller-details"));
        $("#selectedbuyeranchor").text($.i18n("reg-buyer-details"));
        $("#selectedsellerlabel").text($.i18n("reg-seller-details"));
        $("#selectedbuyerlabel").text($.i18n("reg-buyer-details"));

        loadResPersonsOfEditingForEditing();


    } else if (id == 4) // "Change of Ownership"
    {
        salebuyerdetails(id);
        $("#tabs_registry1").tabs({active: 0});

        $("#Seller_Details").show();
        $("#Land_Details_Sale").hide();
        $("#Buyer_Details").hide();
        $("#regPoi").hide();
        $("#Upload_Document_Sale").hide();
        $("#selectedseller").addClass("ui-tabs-active");
        $("#selectedseller").addClass("ui-state-active");
        $("#selectedland").removeClass("ui-tabs-active");
        $("#selectedland").removeClass("ui-state-active");
        $("#selectedbuyer").removeClass("ui-tabs-active");
        $("#selectedbuyer").removeClass("ui-state-active");
        $("#selecteddoc").removeClass("ui-tabs-active");
        $("#selecteddoc").removeClass("ui-state-active");
        $("#selectedpoi").removeClass("ui-tabs-active");
        $("#selectedpoi").removeClass("ui-state-active");

        processid = id;
        persontypeid = 11;
        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "none";
        sale.style.display = "block";
        mortgage.style.display = "none";
        split.style.display = "none";
        currentdiv = "Sale";

        fetchDocument(selectedlandid, 11, id);
        $("#EliminatedOwner").css("display", "none");

        $("#selectedselleranchor").text($.i18n("reg-owner-details"));
        $("#selectedbuyeranchor").text($.i18n("reg-new-owner-details"));
        $("#selectedsellerlabel").text($.i18n("reg-owner-details"));
        $("#selectedbuyerlabel").text($.i18n("reg-new-owner-details"));

        loadResPersonsOfEditingForEditing();

    } else if (id == 6) // "Gift/Inheritance"
    {
        salebuyerdetails(id);
        $("#tabs_registry1").tabs({active: 0});

        $("#Seller_Details").show();
        $("#Land_Details_Sale").hide();
        $("#Buyer_Details").hide();
        $("#regPoi").hide();
        $("#Upload_Document_Sale").hide();
        $("#selectedseller").addClass("ui-tabs-active");
        $("#selectedseller").addClass("ui-state-active");
        $("#selectedland").removeClass("ui-tabs-active");
        $("#selectedland").removeClass("ui-state-active");
        $("#selectedbuyer").removeClass("ui-tabs-active");
        $("#selectedbuyer").removeClass("ui-state-active");
        $("#selecteddoc").removeClass("ui-tabs-active");
        $("#selecteddoc").removeClass("ui-state-active");
        $("#selectedpoi").removeClass("ui-tabs-active");
        $("#selectedpoi").removeClass("ui-state-active");

        processid = id;
        persontypeid = 11;
        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "none";
        sale.style.display = "block";
        mortgage.style.display = "none";
        split.style.display = "none";
        currentdiv = "Sale";

        fetchDocument(selectedlandid, 11, id);

        $("#selectedselleranchor").text($.i18n("reg-owner-details"));
        $("#selectedbuyeranchor").text($.i18n("reg-new-owner-details"));
        $("#selectedsellerlabel").text($.i18n("reg-owner-details"));
        $("#selectedbuyerlabel").text($.i18n("reg-new-owner-details"));
        $("#EliminatedOwner").css("display", "none");
        $("#RelationWithOwner").css("display", "block");

        jQuery("#sale_relation_buyer").empty();
        jQuery("#sale_relation_buyer").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
        jQuery.each(relationtypes_R, function (i, obj)
        {
            jQuery("#sale_relation_buyer").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
        });

        loadResPersonsOfEditingForEditing();
      
    } else if (id == 7) // "Change of Joint Owner"
    {
        salebuyerdetails(id);
        $("#tabs_registry1").tabs({active: 0});

        $("#Seller_Details").show();
        $("#Land_Details_Sale").hide();
        $("#Buyer_Details").hide();
        $("#regPoi").hide();
        $("#Upload_Document_Sale").hide();
        $("#selectedseller").addClass("ui-tabs-active");
        $("#selectedseller").addClass("ui-state-active");
        $("#selectedland").removeClass("ui-tabs-active");
        $("#selectedland").removeClass("ui-state-active");
        $("#selectedbuyer").removeClass("ui-tabs-active");
        $("#selectedbuyer").removeClass("ui-state-active");
        $("#selecteddoc").removeClass("ui-tabs-active");
        $("#selecteddoc").removeClass("ui-state-active");
        $("#selectedpoi").removeClass("ui-tabs-active");
        $("#selectedpoi").removeClass("ui-state-active");

        processid = id;
        persontypeid = 11;
        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "none";
        sale.style.display = "block";
        mortgage.style.display = "none";
        split.style.display = "none";
        currentdiv = "Sale";

        fetchDocument(selectedlandid, 11, id);

        $("#selectedselleranchor").text($.i18n("reg-owner-details"));
        $("#selectedbuyeranchor").text($.i18n("reg-new-owner-details"));
        $("#selectedsellerlabel").text($.i18n("reg-owner-details"));
        $("#selectedbuyerlabel").text($.i18n("reg-new-owner-details"));
        $("#RelationWithOwner").css("display", "none");
        $("#EliminatedOwner").css("display", "block");
        $("#RelationWithOwner").css("display", "none");
        if (edit == 0) {
            loadResPersonsOfEditingForEditing();
        }

    } else if (id == 3) // "Mortgage"
    {

        $("#tabs_registry2").tabs({active: 0});

        $("#MortgageOwner_Details").show();
        $("#Land_Details_Mortgage").hide();
        $("#Mortgage_Details").hide();
        $("#Upload_Document_Mortgage").hide();
        $("#selectedownerdetails").addClass("ui-tabs-active");
        $("#selectedownerdetails").addClass("ui-state-active");
        $("#selectelandmortgage").removeClass("ui-tabs-active");
        $("#selectelandmortgage").removeClass("ui-state-active");
        $("#selectemortgage").removeClass("ui-tabs-active");
        $("#selectemortgage").removeClass("ui-state-active");
        $("#selectemortgagedocs").removeClass("ui-tabs-active");
        $("#selectemortgagedocs").removeClass("ui-state-active");

        $('.trhideclassmortgage2').hide();

        processid = id;
        persontypeid = 1;
        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "none";
        sale.style.display = "none";
        mortgage.style.display = "block";
        split.style.display = "none";
        currentdiv = "Mortgage";

        fetchDocument(selectedlandid, 1, id);

        loadResPersonsOfEditingForEditing();

    } else if (id == 9) // "Surrender of Mortgage"
    {

        $("#tabs_registry2").tabs({active: 0});


        $("#MortgageOwner_Details").show();
        $("#Land_Details_Mortgage").hide();
        $("#Mortgage_Details").hide();
        $("#Upload_Document_Mortgage").hide();
        $("#selectedownerdetails").addClass("ui-tabs-active");
        $("#selectedownerdetails").addClass("ui-state-active");
        $("#selectelandmortgage").removeClass("ui-tabs-active");
        $("#selectelandmortgage").removeClass("ui-state-active");
        $("#selectemortgage").removeClass("ui-tabs-active");
        $("#selectemortgage").removeClass("ui-state-active");
        $("#selectemortgagedocs").removeClass("ui-tabs-active");
        $("#selectemortgagedocs").removeClass("ui-state-active");
        $('.trhideclassmortgage2').show();

        processid = id;
        persontypeid = 1;
        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "none";
        sale.style.display = "none";
        mortgage.style.display = "block";
        split.style.display = "none";
        currentdiv = "Mortgage";

        fetchDocument(selectedlandid, 1, id);

        jQuery.ajax({
            url: "registration/SurrenderMortagagedata/" + selectedlandid + "/" + id,
            async: false,
            success: function (data) {
                surrendermortagagedata = data;

            }
        });

        $("#mortgagesurrender_reason").val(surrendermortagagedata.surrenderreason);

        loadResPersonsOfEditingForEditing();

    } else if (id == 8) //split
    {

        processid = id;


        $("#comment_Save").hide();

        $("#buyersavebutton").prop("disabled", false).hide();
        lease.style.display = "none";
        sale.style.display = "none";
        mortgage.style.display = "none";
        split.style.display = "block";
        currentdiv = "split";
        jQuery("#doc_name_split").val("");
        jQuery("#doc_date_split").val("");
        jQuery("#doc_desc_split").val("");
        fetchDocumentSplit(selectedlandid);

    }

}

function clearBuyerDetails_sale() {

    $("#firstname_r_sale1").val('');
    $("#middlename_r_sale1").val('');
    $("#lastname_r_sale1").val('');
    $("#id_r1").val('');
    $("#sale_idtype_buyer").val('');
    $("#contact_no1").val('');
    $("#sale_gender_buyer").val('');
    $("#address_sale1").val('');
    $("#date_Of_birth_sale1").val('');
    $("#sale_marital_buyer").val('');
    $("#doc_name_sale").val('');
    $("#doc_date_sale").val('');
    $("#doc_desc_sale").val('');
    $("#Newfiles").val('');
    $("#fileUploadNewDowcumentss").val('');
    $("#remrks_sale").val('');

}

function clear_Lease() {

    $("#firstname_r_applicant").val('');
    $("#middlename_r_applicant").val('');
    $("#lastname_r_applicant").val('');
    $("#id_r_applicant").val('');
    $("#id_type_applicant").val('');
    $("#contact_no_applicant").val('');
    $("#gender_type_applicant").val('');
    $("#address_lease_applicant").val('');
    $("#date_Of_birth_applicant").val('');
    $("#martial_sts_applicant").val('');
    // $("#no_Of_years_Lease").val('');
    $("#no_Of_month_Lease").val('');
    $("#lease_Amount").val('');
    $("#NewfilesLease").val('');
    $("#fileUploadNewDowcumentsLease").val('');
    $("#doc_name_Lease").val('');
    $("#doc_date_Lease").val('');
    $("#doc_desc_Lease").val('');
    $("#remrks_lease").val('');
    $("#doc_Type_Lease").val('');

}

function clearMortgage() {

    $("#mortgage_Financial_Agencies").val('');
    $("#mortgage_from").val('');
    $("#mortgage_to").val('');
    $("#amount_mortgage").val('');
    $("#doc_name_mortgage").val('');
    $("#doc_date_mortgage").val('');
    $("#doc_desc_mortgage").val('');
    $("#NewfilesMortgage").val('');
    $("#fileUploadNewDowcumentsMortgage").val('');
    $("#remrks_mortgage").val('');
    $("#doc_Type_Mortgage").val('');

}

function formatDate_R(date) {
    return jQuery.datepicker.formatDate('yy-mm-dd', new Date(date));
}

function formatDate2_R(intDate) {
    return jQuery.datepicker
            .formatDate('yy-mm-dd', new Date(parseInt(intDate)));
}

function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function validateSale() {
    $("#editprocessAttributeformID").validate({
        rules: {
            firstname_r_sale1: {
                required: true
            },
            // middlename_r_sale1: "required",
            lastname_r_sale1: "required",
            id_r1: "required",
            sale_idtype_buyer: {
                required: true
            },
            contact_no1: {
                required: true
            },
            date_Of_birth_sale1: {
                required: true
            },
            sale_gender_buyer: {
                required: true
            },
            sale_marital_buyer: "required",
            address_sale1: "required"
        },
        messages: {
            firstname_r_sale1: $.i18n("err-select-firstname"),
            lastname_r_sale1: $.i18n("err-select-lastname"),
            id_r1: $.i18n("err-enter-idnumber"),
            sale_idtype_buyer: $.i18n("err-select-id-type"),
            contact_no1: $.i18n("err-enter-phone-number"),
            date_Of_birth_sale1: $.i18n("err-enter-dob"),
            sale_gender_buyer: $.i18n("err-select-gender"),
            sale_marital_buyer: $.i18n("err-select-marital-status"),
            address_sale1: $.i18n("err-enter-address")
        }
    });

    if ($("#editprocessAttributeformID").valid()) {
        if ((firstname_r_sale1.value.length == 0)
                || (lastname_r_sale1.value.length == 0)
                || (id_r1.value.length == 0) || (contact_no1.value.length == 0)
                || (date_Of_birth_sale1.value.length == 0)
                || (address_sale1.value.length == 0)) { // ||
            // (middlename_r_sale1.value.length
            // == 0)
            setTimeout(function () {
                $("#Tab_2").tabs({
                    active: 2
                });

            }, 500);
            return false;
        } else if ((sale_idtype_buyer.value == "0")
                || (sale_gender_buyer.value == "0")) {
            setTimeout(function () {
                $("#Tab_2").tabs({
                    active: 2
                });

            }, 500);
            return false;
        }
    } else {

        setTimeout(function () {
            $("#Tab_2").tabs({
                active: 2
            });
            // do something special
        }, 500);
        // $("#Buyer_Details").show();
        return false;

    }
}

function validateMortgage() {
    $("#editprocessAttributeformID").validate({
        rules: {
            mortgage_from: "required",
            mortgage_to: "required",
            amount_mortgage: "required",
            mortgage_Financial_Agencies: {
                required: true
            },
            doc_name_mortgage: {
                required: true
            },
            doc_date_mortgage: {
                required: true
            },
            doc_desc_mortgage: {
                required: true
            },
            doc_Type_Mortgage: {
                required: true
            },
            NewfilesMortgage: {
                required: true
            },
            remrks_mortgage: "required"
        },
        messages: {
            mortgage_from: $.i18n("err-enter-mortgage-from"),
            mortgage_to: $.i18n("err-enter-mortgage-to"),
            amount_mortgage: $.i18n("err-enter-amount"),
            mortgage_Financial_Agencies: $.i18n("err-enter-fin-agency"),
            doc_name_mortgage: $.i18n("err-enter-doc-name"),
            doc_date_mortgage: $.i18n("err-enter-doc-date"),
            doc_desc_mortgage: $.i18n("err-enter-doc-desc"),
            doc_Type_Mortgage: $.i18n("err-select-doc-type"),
            NewfilesMortgage: $.i18n("err-select-file"),
            remrks_mortgage: $.i18n("err-enter-remarks")
        }
    });

    if ($("#editprocessAttributeformID").valid()) {
        if ((mortgage_from.value.length == 0)
                || (mortgage_to.value.length == 0)
                || (amount_mortgage.value.length == 0)) {
            setTimeout(function () {
                $("#Tab_3").tabs({
                    active: 2
                });
                // do something special
            }, 500);
        } else if ((mortgage_Financial_Agencies.value == "0")) {
            setTimeout(function () {
                $("#Tab_3").tabs({
                    active: 2
                });
                // do something special
            }, 500);
        }
    } else {

        setTimeout(function () {
            $("#Tab_3").tabs({
                active: 2
            });
            // do something special
        }, 500);

        return false;

    }
}

function validateLease() {
    $("#editprocessAttributeformID").validate({
        rules: {
            firstname_r_applicant: {
                required: true
            },
            // middlename_r_applicant: "required",
            lastname_r_applicant: "required",
            id_r_applicant: "required",
            id_type_applicant: {
                required: true
            },
            contact_no_applicant: {
                required: true
            },
            gender_type_applicant: {
                required: true
            },
            address_lease_applicant: {
                required: true
            },
            date_Of_birth_applicant: "required",
            martial_sts_applicant: "required"
                    /* NewfilesLease: {required: true} */

        },
        messages: {
            firstname_r_applicant: $.i18n("err-select-firstname"),
            lastname_r_applicant: $.i18n("err-select-lastname"),
            id_r_applicant: $.i18n("err-enter-idnumber"),
            id_type_applicant: $.i18n("err-select-id-type"),
            contact_no_applicant: $.i18n("err-enter-phone-number"),
            gender_type_applicant: $.i18n("err-select-gender"),
            address_lease_applicant: $.i18n("err-enter-address"),
            date_Of_birth_applicant: $.i18n("err-enter-dob"),
            martial_sts_applicant: $.i18n("err-select-marital-status")
        }
    });

    if ($("#editprocessAttributeformID").valid()) {
        if ((firstname_r_applicant.value.length == 0)
                || (lastname_r_applicant.value.length == 0)
                || (id_r_applicant.value.length == 0)
                || (contact_no_applicant.value.length == 0)
                || (date_Of_birth_applicant.value.length == 0)
                || (address_lease_applicant.value.length == 0)) { // ||
            // (middlename_r_applicant.value.length
            // == 0)
            setTimeout(function () {
                $("#Tab_1").tabs({
                    active: 1
                });

            }, 500);
        } else if ((id_type_applicant.value == "0")
                || (gender_type_applicant.value == "0")
                || (martial_sts_applicant.value == "0")) {
            setTimeout(function () {
                $("#Tab_1").tabs({
                    active: 1
                });

            }, 500);
        }
    } else {

        setTimeout(function () {
            $("#Tab_1").tabs({
                active: 1
            });
            // do something special
        }, 500);

        return false;

    }
}

function validateLeasedata() {
    $("#editprocessAttributeformID").validate({
        rules: {
            // no_Of_years_Lease: "required",
            no_Of_month_Lease: "required",
            lease_Amount: "required"
        },
        messages: {
            // no_Of_years_Lease: "Enter No of Years",
            no_Of_month_Lease: $.i18n("err-enter-no-of-month"),
            lease_Amount: $.i18n("err-enter-lease-amount")
        }
    });

    if ($("#editprocessAttributeformID").valid()) {

        if ((no_Of_month_Lease.value.length == 0)
                || (lease_Amount.value.length == 0)) {// (no_Of_years_Lease.value.length
            // == 0) ||
            setTimeout(function () {
                $("#Tab_1").tabs({
                    active: 2
                });

            }, 500);
        }/*
         * else if((no_Of_month_Lease.value == "0")){ setTimeout( function() {
         * $("#Tab_1" ).tabs( { active: 2 } );
         *  }, 500); }
         */

    } else {

        setTimeout(function () {
            $("#Tab_1").tabs({
                active: 2
            });
            // do something special
        }, 500);

        return false;

    }
}

function isDecNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
        return false;

    return true;
}



function ViewHistory(landid) {
    $(".signin_menu").hide();
    TransLandId = landid;
    jQuery('#PropertyHistTrackingBody').empty();
    jQuery('#PropertyLeaseHistTrackingBody').empty();
    jQuery('#PropertyMortageHistTrackingBody').empty();
    jQuery('#TransactionHistTrackingBody').empty();
    jQuery.ajax({
        type: 'GET',
        url: "landrecords/parcelhistory/" + landid,
        async: false,
        success: function (data) {
            if (data.length > 0) {
                if (data[0] != null) {
                    jQuery("#commentsTablePop").show();
                    $.each(data[0], function (index, optionData) {
                        jQuery("#PropertyHistTrackingTemplate")
                                .tmpl(optionData).appendTo(
                                "#PropertyHistTrackingBody");
                    });
                }



                if (data[3] != null) {
                    jQuery("#commentsTablePopTransactionHist").show();
                    $.each(data[3], function (index, optionData) {
                        jQuery("#TransactionHistTrackingTemplate").tmpl(
                                optionData).appendTo("#TransactionHistTrackingBody");
                        $("#TransactionHistTrackingBody").i18n();
                    });
                }

                commentHistoryDialogPop = $("#commentsDialogpopup").dialog({
                    autoOpen: false,
                    height: 600,
                    width: 800,
                    resizable: true,
                    modal: true,
                    buttons: [{
                            text: $.i18n("gen-cancel"),
                            "id": "comment_cancel",
                            click: function () {
                                setInterval(function () {

                                }, 4000);
                                jQuery('#PropertyHistTrackingBody').empty();
                                jQuery('#PropertyLeaseHistTrackingBody').empty();
                                jQuery('#PropertyMortageHistTrackingBody').empty();
                                jQuery("#commentsTablePop").hide();
                                jQuery("#commentsTablePopLease").hide();
                                jQuery("#commentsTablePopMortage").hide();
                                jQuery("#commentsTablePopTransactionHist").hide();
                                commentHistoryDialogPop.dialog("destroy");

                            }
                        }],
                    close: function () {

                        // commentHistoryDialogPop.dialog( "destroy" );
                        jQuery("#commentsTablePop").hide();
                        jQuery("#commentsTablePopLease").hide();
                        jQuery("#commentsTablePopMortage").hide();

                    }
                });
                $("#comment_cancel").html(
                        '<span class="ui-button-text">' + $.i18n("gen-cancel") + '</span>');
                commentHistoryDialogPop.dialog("open");

            } else {
                jAlert("No Records", $.i18n("gen-info"));
            }
        }

    });
}


function viewleasedetail(transactionid, landid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findleasedetailbylandid/' + transactionid + "/" + landid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' + $.i18n("reg-lease-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' + $.i18n("reg-name") + '</th><th>' + $.i18n("reg-address") + '</th><th>' + $.i18n("reg-id-number") + '</th><th>' + $.i18n("reg-lease-start-date") + '</th><th>' + $.i18n("reg-lease-end-date") + '</th><th>' + $.i18n("reg-lease-month") + '</th><th>' + $.i18n("reg-lease-amount") + '</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');

                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' + $.i18n("reg-doc-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' + $.i18n("reg-doc-name") + '</th><th>' + $.i18n("reg-doc-date") + '</th><th>' + $.i18n("reg-doc-desc") + '</th><th>' + $.i18n("reg-view-doc") + '</th></tr></thead><tbody id="popupBodydocument"></tbody></table></div></br>');

                if (contactObj[0] != null && contactObj[0].length > 0)
                {
                    jQuery("#PropertyLeaseTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupBody");
                }

                if (contactObj[1] != null && contactObj[1].length > 0)
                {
                    jQuery("#DocumentTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupBodydocument");
                    $("#popupBodydocument").i18n();
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-lease"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}


function viewOwnerDetails(transactionid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findsaledetailbytransid/' + transactionid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-current-owner") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date") 
                        + '</th></tr></thead><tbody id="popupCurrentBody"></tbody></table></div></br>');

                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-old-owner") 
                        + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date") 
                        + '</th></tr></thead><tbody id="popupoldBody"></tbody></table></div></br>');


                if (contactObj[0] != null)
                {
                    jQuery("#PropertyOldSaleTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupoldBody");
                }

                if (contactObj[1] != null)
                {
                    jQuery("#PropertyCurrentSaleTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupCurrentBody");
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-changeof-owner"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}
function viewSaleDetails(transactionid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findsaledetailbytransid/' + transactionid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-current-owner") 
                        + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date") 
                        + '</th></tr></thead><tbody id="popupCurrentBody"></tbody></table></div></br>');

                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-old-owner") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date") 
                        + '</th></tr></thead><tbody id="popupoldBody"></tbody></table></div></br>');

                if (contactObj[0] != null)
                {
                    jQuery("#PropertyOldSaleTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupoldBody");
                }

                if (contactObj[1] != null)
                {
                    jQuery("#PropertyCurrentSaleTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupCurrentBody");
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-sale"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}
function viewGiftDetails(transactionid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findsaledetailbytransid/' + transactionid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-current-owner") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date")  
                        + '</th></tr></thead><tbody id="popupCurrentBody"></tbody></table></div></br>');

                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-old-owner") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date") 
                        + '</th></tr></thead><tbody id="popupoldBody"></tbody></table></div></br>');

                if (contactObj[0] != null)
                {
                    jQuery("#PropertyOldSaleTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupoldBody");
                }

                if (contactObj[1] != null)
                {
                    jQuery("#PropertyCurrentSaleTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupCurrentBody");
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-gift"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}
function viewJointDetails(transactionid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findsaledetailbytransid/' + transactionid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-current-owner")  + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date") + '</th></tr></thead><tbody id="popupCurrentBody"></tbody></table></div></br>');

                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-old-owner") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-ownership-date") 
                        + '</th></tr></thead><tbody id="popupoldBody"></tbody></table></div></br>');

                if (contactObj[0] != null)
                {
                    jQuery("#PropertyOldSaleTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupoldBody");
                }

                if (contactObj[1] != null)
                {
                    jQuery("#PropertyCurrentSaleTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupCurrentBody");
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-change-of-joint-owner"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}
function viewsurrenderleasedetail(transactionid, landid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findsurrenderleasedetailbylandid/' + transactionid + "/" + landid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-lease-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-name") + '</th><th>' 
                        + $.i18n("reg-address") + '</th><th>' 
                        + $.i18n("reg-id-number") + '</th><th>' 
                        + $.i18n("reg-lease-start-date") + '</th><th>' 
                        + $.i18n("reg-lease-end-date") + '</th><th>' 
                        + $.i18n("reg-lease-month") + '</th><th>' 
                        + $.i18n("reg-lease-amount")
                        + '</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');
                
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-doc-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-doc-name") + '</th><th>' 
                        + $.i18n("reg-doc-date") + '</th><th>' 
                        + $.i18n("reg-doc-desc") + '</th><th>' 
                        + $.i18n("reg-view-doc")
                        + '</th></tr></thead><tbody id="popupBodydocument"></tbody></table></div></br>');

                if (contactObj[0] != null && contactObj[0].length > 0)
                {
                    jQuery("#PropertyLeaseTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupBody");
                }

                if (contactObj[1] != null && contactObj[1].length > 0)
                {
                    jQuery("#DocumentTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupBodydocument");
                    $("#popupBodydocument").i18n();
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-lease"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}

function viewMortagagedetail(transactionid, landid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findmortagagedetailbylandid/' + transactionid + "/" + landid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-mortgage-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-fin-agency") + '</th><th>' 
                        + $.i18n("reg-mortgage-from") + '</th><th>' 
                        + $.i18n("reg-mortgage-to") + '</th><th>' 
                        + $.i18n("reg-mortgage-amount")
                        + '</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');
                
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-doc-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-doc-name") + '</th><th>' 
                        + $.i18n("reg-doc-date") + '</th><th>' 
                        + $.i18n("reg-doc-desc") + '</th><th>' 
                        + $.i18n("reg-view-doc") 
                        + '</th></tr></thead><tbody id="popupBodydocument"></tbody></table></div></br>');
                if (contactObj[0] != null && contactObj[0].length > 0)
                {
                    jQuery("#PropertyMortageTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupBody");
                }

                if (contactObj[1] != null && contactObj[1].length > 0)
                {
                    jQuery("#DocumentTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupBodydocument");
                    $("#popupBodydocument").i18n();
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-mortgage"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}


function viewSurrenderMortagagedetail(transactionid, landid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: 'landrecords/findSurrendermortagagedetailbylandid/' + transactionid + "/" + landid,
        async: false,
        success: function (contactObj) {

            if (contactObj != null && contactObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-mortgage-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-fin-agency") + '</th><th>' 
                        + $.i18n("reg-mortgage-from") + '</th><th>' 
                        + $.i18n("reg-mortgage-to") + '</th><th>' 
                        + $.i18n("reg-mortgage-amount")
                        + '</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');
                
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-doc-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-doc-name") + '</th><th>' 
                        + $.i18n("reg-doc-date") + '</th><th>' 
                        + $.i18n("reg-doc-desc") + '</th><th>' 
                        + $.i18n("reg-view-doc")
                        + '</th></tr></thead><tbody id="popupBodydocument"></tbody></table></div></br>');
                if (contactObj[0] != null && contactObj[0].length > 0)
                {
                    jQuery("#PropertyMortageTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupBody");
                }

                if (contactObj[1] != null && contactObj[1].length > 0)
                {
                    jQuery("#DocumentTrackingTemplate").tmpl(contactObj[1]).appendTo("#popupBodydocument");
                    $("#popupBodydocument").i18n();
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 400,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-surrender-mortgage"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}



function viewdocumentdetailForTransaction(personid, transactionid) {
    window.open("registrationdetails/viewdoc/" + personid + "/" + transactionid, 'popUp', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
}

function viewdocumentdetail(transactionid)
{
    jQuery.ajax({
        type: 'GET',
        //url:'viewArchivalChangesDetail/'+requestid,
        url: "landrecords/viewdocumentdetail/" + transactionid,
        async: false,
        success: function (docObj) {

            if (docObj != null && docObj.length > 0)
            {
                $('#ViewPopuupDiv').empty();
                $('#ViewPopuupDiv').css("visibility", "visible");

                //To show person Property Changes
                $('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>' 
                        + $.i18n("reg-doc-details") + '</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>' 
                        + $.i18n("reg-doc-name") + '</th><th>' 
                        + $.i18n("reg-doc-date") + '</th><th>' 
                        + $.i18n("reg-doc-desc") + '</th><th>' 
                        + $.i18n("reg-view-doc") 
                        + '</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');

                if (docObj[0] != null && docObj[0].length > 0)
                {
                    jQuery("#DocumentTrackingTemplate").tmpl(docObj[0]).appendTo("#popupBody");
                    $("#popupBody").i18n();
                }

                $("#ViewPopuupDiv").dialog(
                        {
                            height: 220,
                            width: 700,
                            modal: true,
                            buttons:
                                    {
                                        "Cancel": {
                                            text: $.i18n("gen-cancel"),
                                            "id": "comment_Trans_cancel",
                                            click: function () {
                                                setInterval(function () {

                                                }, 4000);
                                                $(this).dialog("close");

                                            }
                                        }
                                    },
                            close: function (ev, ui)
                            {
                                $(this).dialog("close");
                            }
                        });
                jQuery('#ViewPopuupDiv').dialog('option', 'title', $.i18n("reg-doc-details"));

            } else
            {
                alertMsg = $.i18n("err-no-contact-assosiated");
                jAlert('info', alertMsg, alertInfoHeader);
            }
        },
        error: function () {

        }
    });
}

function makeFileListLease() {
    var fi = document.getElementById('fileUploadNewDowcumentsLease');

    if (fi.files.length > 0) {

        for (var i = 0; i <= fi.files.length - 1; i++) {
            var fname = fi.files.item(i).name; // THE NAME OF THE FILE.
            // var fsize = fi.files.item(i).size; // THE SIZE OF THE FILE.

            document.getElementById('fp_lease').innerHTML = document
                    .getElementById('fp_lease').innerHTML
                    + fname + '<br /> ';
        }
    }
}
function makeFileList() {


    $('#landRegistration_docs').empty();

    var jsonObjItems = [];
    var val1 = 0;
    $.each($('#fileUploadNewDowcumentss')[0].files, function (ind2, obj2) {
        var myObject = new Object();
        $.each($('#fileUploadNewDowcumentss')[0].files[val1], function (ind3,
                obj3) {
            myObject.name = $("#doc_name_sale").val();
            myObject.description = $("#doc_desc_sale").val();
            myObject.docType = $("#doc_Type_Sale option:selected").text();
            jsonObjItems.push(myObject);

        });
        val1 = val1 + 1;
    });

    jQuery("#landRegistration_docsTemplate").tmpl(jsonObjItems).appendTo(
            "#landRegistration_docs");

}

function makeFileList_mortgage() {
    var fi = document.getElementById('fileUploadNewDowcumentsMortgage');

    if (fi.files.length > 0) {

        for (var i = 0; i <= fi.files.length - 1; i++) {
            var fname = fi.files.item(i).name; // THE NAME OF THE FILE.
            // var fsize = fi.files.item(i).size; // THE SIZE OF THE FILE.

            document.getElementById('fp_mortgage').innerHTML = document
                    .getElementById('fp_mortgage').innerHTML
                    + fname + '<br /> ';
        }
    }
}

function ActionfillRegistration(landid, workflowId, transactionid, parcelno, regno) {
    transid = transactionid;

    var parcelnumwithpadding = pad(parcelno, 9);
    //var regno = '123';

    jQuery("#parcelnovalue").text(parcelnumwithpadding);
    jQuery("#regnovalue").text(regno);

    var appid = '#' + landid + "_registration";
    $("" + appid + "").empty();
    $(".containerDiv").empty();

    var html = "";
    html += "<li> <a title='" + $.i18n("reg-view-land-record") + "' id='' name=''  href='#' onclick='viewLandAttribute("
            + landid + ")'>" + $.i18n("reg-view-land-record") + "</a></li>";

    html += "<li> <a title='" + $.i18n("reg-view-spatial-data") + "' id='' name=''  href='#' onclick='showOnMap(" + landid + ", " + workflowId + ")'>" + $.i18n("reg-view-spatial-data") + "</a></li>";

    html += "<li> <a title='" + $.i18n("reg-init-tran") + "' id='' name=''  href='#' onclick='leaseAttribute("
            + landid + ")'>" + $.i18n("reg-init-tran") + "</a></li>";

    html += "<li> <a title='" + $.i18n("reg-view-tran-history") + "' id='' name=''  href='#' onclick='ViewHistory(" + landid + ")'>" + $.i18n("reg-view-tran-history") + "</a></li>";
    /*html += "<li> <a title='Print land certificate' id='' name=''  href='#' onclick='generateCcro("+ transactionid + ")'>Print land certificate</a></li>";*/
    html += "<li> <a title='" + $.i18n("reg-print-cert") + "' id='' name=''  href='#' onclick='_generateFinalLandForm(" + transactionid + ", " + landid + ")'>" + $.i18n("reg-print-cert") + "</a></li>";



    $("" + appid + "").append(
            '<div class="signin_menu"><div class="signin"><ul>' + html
            + '</ul></div></div>');

    $(".signin_menu").toggle();
    $(".signin").toggleClass("menu-open");

    $(".signin_menu").mouseup(function () {
        return false
    });
    $(document).mouseup(function (e) {
        if ($(e.target).parent("a.signin").length == 0) {
            $(".signin").removeClass("menu-open");
            $(".signin_menu").hide();
        }
    });
}
var land_RV = null;

function viewLandAttribute(landid) {
    $(".signin_menu").hide();
    selectedlandid = landid;
    FillRegistrationPersonDataNew();
    FillResourceNonNaturalPersonDataNew();
    loadPOIEditing();
    $("#landidhide").val(landid);
    province_r = null;
    region_r = null;
    jQuery.ajax({
        url: "registration/allregion/" + country_r_id,
        async: false,
        success: function (data) {
            region_r = data;
            if (data.length > 0) {
                // data.xyz.name_en for getting the data
            }
        }
    });

    jQuery.ajax({
        url: "registration/allprovince/" + region_r_id,
        async: false,
        success: function (data) {
            province_r = data;
        }
    });

    jQuery.ajax({
        url: "registration/laspatialunitland/" + landid,
        async: false,
        success: function (data) {
            land_RV = data;

        }
    });
    jQuery.ajax({
        url: "landrecords/landusertype/",
        async: false,
        success: function (data) {
            landUserList = data;
        }
    });

    jQuery.ajax({
        url: "landrecords/claimtypes/",
        async: false,
        success: function (data) {
            claimTypes_R = data;
        }
    });

    // Land Details landtype_r
    jQuery("#Land_Type_l_record").empty();
    jQuery("#country_l_record").empty();
    jQuery("#region_l_record").empty();
    jQuery("#province_r").empty();
    jQuery("#Ownership_Type_l_record").empty();
    jQuery("#Claim_Type_l_record").empty();
    jQuery("#provience_l_record").empty();
    jQuery("#existing_use_LR").empty();
    jQuery("#proposed_use_LR").empty();


    jQuery("#country_l_record").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#region_l_record").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#provience_l_record").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#Land_Type_l_record").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#Ownership_Type_l_record").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery("#Claim_Type_l_record").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery.each(landtype_r, function (i, obj1) {
        jQuery("#Land_Type_l_record").append(
                jQuery("<option></option>").attr("value", obj1.landtypeid)
                .text(obj1.landtypeEn));
    });
    jQuery.each(allcountry, function (i, obj) {
        jQuery("#country_l_record").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });

    jQuery.each(region_r, function (i, obj) {
        jQuery("#region_l_record").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });
    jQuery.each(province_r, function (i, obj) {
        jQuery("#provience_l_record").append(
                jQuery("<option></option>").attr("value", obj.hierarchyid)
                .text(obj.nameEn));
    });

    jQuery.each(landsharetype_r, function (i, obj) {
        jQuery("#Ownership_Type_l_record").append(
                jQuery("<option></option>").attr("value", obj.landsharetypeid)
                .text(obj.landsharetypeEn));
    });

    jQuery.each(claimTypes_R, function (i, obj) {
        jQuery("#Claim_Type_l_record").append(
                jQuery("<option></option>").attr("value", obj.code)
                .text(obj.name));
    });

    if (land_RV != null && land_RV[0] != null)
    {
        jQuery("#Land_Type_l_record").val(land_RV[0].landtypeid);
        jQuery("#Claim_Type_l_record").val(land_RV[0].claimtypeid);
        jQuery("#country_l_record").val(land_RV[0].hierarchyid1);
        jQuery("#region_l_record").val(land_RV[0].hierarchyid2);
        jQuery("#provience_l_record").val(land_RV[0].hierarchyid3);

        jQuery("#parcel_l_record").val("000000" + land_RV[0].landid);
        jQuery("#Ownership_Type_l_record").val(land_RV[0].landsharetypeid);// landsharetypeid
        jQuery("#area_LR").val(land_RV[0].area);
        if (land_RV[0].other_use != "" && land_RV[0].other_use != null) {
            jQuery("#other_useregistrationpage").val(land_RV[0].other_use);
        } else {
            jQuery("#other_useregistrationpage").val("");
        }

        jQuery("#lease_area_View").val(land_RV[0].area);

        jQuery("#neighbor_west_LR").val(land_RV[0].neighbor_west);
        jQuery("#neighbor_north_LR").val(land_RV[0].neighbor_north);
        jQuery("#neighbor_south_LR").val(land_RV[0].neighbor_south);
        jQuery("#neighbor_east_LR").val(land_RV[0].neighbor_east);


        jQuery("#existing_use_LR").empty();
        jQuery("#existing_use_LR").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
        jQuery.each(landUserList, function (i, landuseobj) {
            if (landuseobj.landusetypeid != '9999')
                jQuery("#existing_use_LR").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn));

        });
        jQuery("#existing_use_LR").val(land_RV[0].landusetypeid);

        jQuery("#proposed_use_LR").empty();
        jQuery("#proposed_use_LR").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
        jQuery.each(landUserList, function (i, landuseobj) {
            if (landuseobj.landusetypeid != '9999')
                jQuery("#proposed_use_LR").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn));

        });
        jQuery("#proposed_use_LR").val(land_RV[0].proposedused);

    }



    /*jQuery("#existing_use_LR").empty();
     jQuery("#existing_use_LR").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
     jQuery.each(landUserList, function (i, landuseobj) {
     if(landuseobj.landusetypeid!='9999')
     jQuery("#existing_use_LR").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn)); 
     
     });
     
     jQuery("#proposed_use_LR").empty();
     jQuery("#proposed_use_LR").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
     jQuery.each(landUserList, function (i, landuseobj) {
     if(landuseobj.landusetypeid!='9999')
     jQuery("#proposed_use_LR").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn)); 
     
     });
     
     if(laspatialunitland_R!=null && laspatialunitland_R[0]!=null)
     {
     jQuery("#proposed_use_LR").val(laspatialunitland_R[0].proposedused);
     }
     */

    commentviewLandDialogPop = $("#landrecordsview").dialog({
        autoOpen: false,
        height: 700,
        width: 1200,
        resizable: true,
        modal: true,
        buttons: [{
                text: $.i18n("gen-cancel"),
                "id": "comment_cancel",
                click: function () {
                    setInterval(function () {

                    }, 4000);
                    commentviewLandDialogPop.dialog("destroy");

                }
            }],
        close: function () {

        }
    });
    $("#comment_cancel").html('<span class="ui-button-text">' + $.i18n("gen-cancel") + '</span>');
    commentviewLandDialogPop.dialog("open");
}


function saveattributesLeasePersonData() {

    if (!editlease) {
        $("#leaseeperson").val(0);
    }


    if ($("#editprocessAttributeformID").valid()) {



        if ((firstname_r_applicant.value.length == 0)
                || (lastname_r_applicant.value.length == 0)
                || (id_r_applicant.value.length == 0)
                || (contact_no_applicant.value.length == 0)
                || (date_Of_birth_applicant.value.length == 0)
                || (address_lease_applicant.value.length == 0)) { // ||
            // (middlename_r_applicant.value.length
            // == 0)
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        } else if ((id_type_applicant.value == "0")
                || (gender_type_applicant.value == "0")
                || (martial_sts_applicant.value == "0")) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        }


        jQuery
                .ajax({
                    type: "POST",
                    url: "registration/saveLeaseeDetails",
                    data: jQuery("#editprocessAttributeformID")
                            .serialize(),
                    success: function (result) {
//										$("#leaseeperson").val(0);
                        jQuery("#firstname_r_applicant").val('');
                        jQuery("#middlename_r_applicant").val('');
                        jQuery("#lastname_r_applicant").val('');
                        jQuery("#id_r_applicant").val('');
                        jQuery("#contact_no_applicant").val('');
                        jQuery("#address_lease_applicant").val('');
                        jQuery("#date_Of_birth_applicant").val('');

                        jQuery("#martial_sts_applicant").empty();
                        jQuery("#gender_type_applicant").empty();
                        jQuery("#id_type_applicant").empty();

                        jQuery("#id_type_applicant").append(
                                jQuery("<option></option>").attr("value", 0).text(
                                $.i18n("gen-please-select")));
                        jQuery("#gender_type_applicant").append(
                                jQuery("<option></option>").attr("value", 0).text(
                                $.i18n("gen-please-select")));
                        jQuery("#martial_sts_applicant").append(
                                jQuery("<option></option>").attr("value", 0).text(
                                $.i18n("gen-please-select")));

                        jQuery.each(maritalStatus_R, function (i, obj) {
                            jQuery("#martial_sts_applicant").append(
                                    jQuery("<option></option>").attr("value",
                                    obj.maritalstatusid).text(
                                    obj.maritalstatusEn));
                        });


                        jQuery.each(genderStatus_R, function (i, obj1) {
                            jQuery("#gender_type_applicant").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.genderId).text(obj1.gender_en));
                        });


                        jQuery.each(idtype_R, function (i, obj1) {
                            jQuery("#id_type_applicant").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.identitytypeid).text(
                                    obj1.identitytypeEn));
                        });

                        fillSurrenderLeaseDetails(selectedlandid);



                    },
                    error: function (XMLHttpRequest,
                            textStatus, errorThrown) {
                        jAlert($.i18n("err-request-not-completed"));
                    }
                });
    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}


function initiateTransaction() {
    edit = 0;
    $("#registration_process").show();
    leaseAttribute(TransLandId);
    commentHistoryDialogPop.dialog("close");



}

function editTransactionDetails(id, landid) {

    edit = 1;

    $("#editflag").val(edit);
    editAttributeRegistration(id, landid);
    commentHistoryDialogPop.dialog("close");


}



function FillRegistrationPersonDataNew()
{
    // Init editing grid
    $("#registration_personsEditingGrid1").jsGrid({
        width: "100%",
        height: "200px",
        inserting: false,
        editing: false,
        sorting: false,
        filtering: false,
        paging: true,
        autoload: false,
        controller: ResourcepersonsEditingControllerForPerson,
        pageSize: 50,
        pageButtonCount: 20,
        fields: [
            {name: "firstname", title: $.i18n("reg-firstname"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-select-firstname")}},
            {name: "middlename", title: $.i18n("reg-middlename"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-enter-middle-name")}},
            {name: "lastname", title: $.i18n("reg-lastname"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-select-lastname")}},
            {name: "address", title: $.i18n("reg-address"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-enter-address")}},
            {name: "identityno", title: $.i18n("reg-id-number"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-enter-idnumber")}},
            {name: "dateofbirth", title: $.i18n("reg-dob"), type: "date", width: 120, validate: {validator: "required", message: $.i18n("err-enter-dob")}},
            {name: "contactno", title: $.i18n("reg-mobile-num"), type: "text", width: 120},
            {name: "genderid", title: $.i18n("reg-gender"), align: "left", type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}], valueField: "id", textField: "name", width: 80, editing: false, filtering: false},
            {name: "laPartygroupIdentitytype.identitytypeid", title: $.i18n("reg-id-type"), type: "select", items: [{id: 1, name: "Voter ID"}, {id: 2, name: "Driving license"}, {id: 3, name: "Passport"}, {id: 4, name: "ID card"}, {id: 5, name: "Other"}, {id: 6, name: "None"}], valueField: "id", textField: "name", width: 80, editing: false, filtering: false},
            {name: "laPartygroupMaritalstatus.maritalstatusid", title: $.i18n("reg-marital-status"), type: "select", items: [{id: 1, name: "Single"}, {id: 2, name: "Married"}, {id: 3, name: "Divorced"}, {id: 4, name: "Widow"}, {id: 5, name: "Widower"}], valueField: "id", textField: "name", width: 80, editing: false, filtering: false},
            {name: "laPartygroupEducationlevel.educationlevelid", title: $.i18n("reg-edulevel"), type: "select", items: [{id: 1, name: "None"}, {id: 2, name: "Primary"}, {id: 3, name: "Secondary"}, {id: 4, name: "University"}], valueField: "id", textField: "name", width: 80, editing: false, filtering: false},
        ]
    });

    $("#registration_personsEditingGrid1 .jsgrid-table th:first-child :button").click();

    $("#registration_personsEditingGrid1").jsGrid("loadData");




}




var ResourcepersonsEditingControllerForPerson = {
    loadData: function (filter) {
        return $.ajax({
            type: "GET",
            url: "landrecords/personsDataNew/" + selectedlandid,
            data: filter,
            success: function (data)
            {

            }
        });
    },
    insertItem: function (item) {


    },
    updateItem: function (item) {
        var dob = null;
        if (item != null)
        {
            if (item.dateofbirth != null && item.dateofbirth != '')
            {
                var d = new Date(item.dateofbirth);
                dob = d.getFullYear() + '-' + d.getMonth() + '-' + d.getDate();
            }
        }


        var ajaxdata = {
            "personid": item.partyid,
            "firstname": item.firstname,
            "middlename": item.middlename,
            "lastname": item.lastname,
            "address": item.address,
            "identityno": item.identityno,
            "dateofbirth": dob,
            "mobileno": item.mobileno,
            "genderid": item.genderid,
            "identitytypeid": item.laPartygroupIdentitytype.identitytypeid,
            "maritalstatusid": item.laPartygroupMaritalstatus.maritalstatusid,
            "educationlevel": item.laPartygroupEducationlevel.educationlevelid,
            "transactionid": _transactionid,
            "landid": Personusin,
            "contactno": item.contactno
        }

        return $.ajax({
            type: "POST",
            //contentType: "application/json; charset=utf-8",
            traditional: true,
            url: "landrecords/updateNaturalPersonDataForEdit",
            //data: JSON.stringify(item),
            data: ajaxdata,
            success: function () {
                FillPersonDataNew();
//	            	 
            },
            error: function (request, textStatus, errorThrown) {
                jAlert(request.responseText);
            }
        });
    },
    deleteItem: function (item) {
        alert("removed");
        return false;
    }
};




function loadPOIEditing() {
    $("#registration_personsEditingGrid").jsGrid({
        width: "100%",
        height: "200px",
        inserting: false,
        editing: false,
        sorting: false,
        filtering: false,
        paging: true,
        autoload: false,
        controller: ResourcepersonsEditingController,
        pageSize: 50,
        pageButtonCount: 20,
        fields: [
            // {name: "landid", title: "LandID", type: "number", width: 70, align: "left", editing: false, filtering: true},
            {name: "firstName", title: $.i18n("reg-firstname"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-select-firstname")}},
            {name: "middleName", title: $.i18n("reg-middlename"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-enter-middle-name")}},
            {name: "lastName", title: $.i18n("reg-lastname"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-select-lastname")}},
            {name: "gender", title: $.i18n("reg-gender"), type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}, {id: 3, name: "Other"}], valueField: "id", textField: "name", width: 120, validate: {validator: "required", message: $.i18n("err-select-gender")}},
            {name: "dob", title: $.i18n("reg-dob"), type: "date", width: 120, validate: {validator: "required", message: $.i18n("err-enter-dob")}},
            {name: "relation", title: $.i18n("reg-relation"), type: "select", items: [{id: 1, name: "Spouse"}, {id: 2, name: "Son"}, {id: 3, name: "Daughter"}, {id: 4, name: "Grandson"}, {id: 5, name: "Granddaughter"}, {id: 6, name: "Brother"},
                    {id: 7, name: "Sister"}, {id: 8, name: "Father"}, {id: 9, name: "Mother"}, {id: 10, name: "Grandmother"}, {id: 11, name: "Grandfather"}, {id: 12, name: "Aunt"},
                    {id: 13, name: "Uncle"}, {id: 14, name: "Niece"}, {id: 15, name: "Nephew"}, {id: 16, name: "Other"}, {id: 17, name: "Other relatives"}, {id: 18, name: "Associate"},
                    {id: 19, name: "Parents and children"}, {id: 20, name: "Siblings"}], valueField: "id", textField: "name", width: 120, validate: {validator: "required", message: $.i18n("err-select-relation-type")}},
        ]
    });
    $("#registration_personsEditingGrid .jsgrid-table th:first-child :button").click();
    $("#registration_personsEditingGrid").jsGrid("loadData");
}

var ResourcepersonsEditingController = {
    loadData: function (filter) {
        $("#btnLoadPersons").val($.i18n("gen-reload"));
        return $.ajax({
            type: "GET",
            url: "landrecords/personwithinterest/" + selectedlandid + "/" + transid,
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
            url: "landrecords/savePersonOfInterestForEditing",
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



function FillResourceNonNaturalPersonDataNew()
{
    // Init editing grid
    $("#registration_personsEditingGrid2").jsGrid({
        width: "100%",
        height: "200px",
        inserting: false,
        editing: false,
        sorting: false,
        filtering: false,
        paging: true,
        autoload: false,
        controller: RegistrationNonNaturalPerson,
        pageSize: 50,
        pageButtonCount: 20,
        fields: [
            {name: "organizationname", title: $.i18n("reg-org-name"), type: "text", width: 120, validate: {validator: "required", message: $.i18n("err-enter-org-name")}},
            {name: "groupType.grouptypeid", title: $.i18n("reg-group-type"), type: "select", items: [{id: 1, name: "Civic"}, {id: 2, name: "Mosque"}, {id: 3, name: "Association"}, {id: 4, name: "Cooperative"}, {id: 5, name: "Informal"}], valueField: "id", textField: "name", width: 80, editing: false, filtering: false},
            {name: "contactno", title: $.i18n("reg-mobile-num"), type: "text", width: 120},
        ]
    });

    $("#registration_personsEditingGrid2 .jsgrid-table th:first-child :button").click();

    $("#registration_personsEditingGrid2").jsGrid("loadData");


}




var RegistrationNonNaturalPerson = {
    loadData: function (filter) {
        $("#btnLoadPersons").val($.i18n("gen-reload"));
        return $.ajax({
            type: "GET",
            url: "landrecords/NonNaturalpersonsDataNew/" + selectedlandid,
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
            url: "landrecords/saveNonNaturalPersonForEditing",
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

$(window).resize(function () {


});



function  AddDocInfoSplit() {

    $("#split_selectedlandid").val(selectedlandid);
    jQuery.ajax({
        type: 'POST',
        url: "registration/savedocumentInfo/split",
        data: jQuery("#editprocessAttributeformID").serialize(),
        async: false,
        success: function (data) {
            if (data) {
                jAlert($.i18n("reg-doc-added"), $.i18n("gen-info"));
                jQuery("#doc_name_split").val("");
                jQuery("#doc_date_split").val("");
                jQuery("#doc_desc_split").val("");
                fetchDocumentSplit(selectedlandid);
            }

        }


    });

}




function AddDocInfoRegistration() {
    $("#landidhide").val(selectedlandid);
    $("#processidhide").val(processid);
    $("#editprocessAttributeformID").valid();
    if (processid == 1 || processid == 5) {
        if ((doc_name_Lease.value.length == 0)
                || (doc_date_Lease.value.length == 0)
                || (doc_desc_Lease.value.length == 0)) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        } else {
            jQuery.ajax({
                type: 'POST',
                url: "registration/savedocumentInfo/Registration",
                data: jQuery("#editprocessAttributeformID").serialize(),
                async: false,
                success: function (data) {
                    if (data) {
                        jAlert($.i18n("reg-doc-added"), $.i18n("gen-info"));
                        fetchDocument(selectedlandid, data, processid);
                        clearDocuments();


                    }

                }


            });
        }
    } else if (processid == 2 || processid == 4 || processid == 6 || processid == 7) {
        if ((doc_name_sale.value.length == 0)
                || (doc_date_sale.value.length == 0)
                || (doc_desc_sale.value.length == 0)) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        } else {
            jQuery.ajax({
                type: 'POST',
                url: "registration/savedocumentInfo/Registration",
                data: jQuery("#editprocessAttributeformID").serialize(),
                async: false,
                success: function (data) {
                    if (data) {
                        jAlert($.i18n("reg-doc-added"), $.i18n("gen-info"));
                        fetchDocument(selectedlandid, data, processid);
                        clearDocuments();


                    }

                }


            });
        }
    } else if (processid == 3 || processid == 9) {
        if ((doc_name_mortgage.value.length == 0)
                || (doc_date_mortgage.value.length == 0)
                || (doc_desc_mortgage.value.length == 0)) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        } else {
            jQuery.ajax({
                type: 'POST',
                url: "registration/savedocumentInfo/Registration",
                data: jQuery("#editprocessAttributeformID").serialize(),
                async: false,
                success: function (data) {
                    if (data) {
                        jAlert($.i18n("reg-doc-added"), $.i18n("gen-info"));
                        fetchDocument(selectedlandid, data, processid);
                        clearDocuments();


                    }

                }


            });
        }
    }

    fetchDocEdit();



}


function clearDocuments()
{
    $("#doc_name_sale").val('');
    $("#doc_date_sale").val('');
    $("#doc_desc_sale").val('');
    $("#doc_name_Lease").val('');
    $("#doc_date_Lease").val('');
    $("#doc_desc_Lease").val('');
    $("#doc_name_mortgage").val('');
    $("#doc_date_mortgage").val('');
    $("#doc_desc_mortgage").val('');

}


function fetchDocument(landId, TypeId, processId)
{
    jQuery.ajax({
        type: 'GET',
        url: "registryrecords/getprocessDocument/" + landId + "/" + TypeId + "/" + processId,
        async: false,
        success: function (data) {
            if (data != null && data != "")
            {
                if (processId == 1 || processId == 5)
                {
                    if (edit == 0) {
                        $("#LeaseDocRowData").empty();
                        $("#salesdocumentTemplate_add").tmpl(data).appendTo("#LeaseDocRowData");
                    } else if (edit == 1) {
                        $("#LeaseDocRowData").empty();
                        $("#salesdocumentTemplate_add").tmpl(data).appendTo("#LeaseDocRowData");

                    }
                    $("#LeaseDocRowData").i18n();
                }

                if (processId == 2 || processId == 4 || processId == 6 || processId == 7)
                {

                    if (edit == 0) {
                        $("#salesDocRowData").empty();
                        $("#salesdocumentTemplate_add").tmpl(data).appendTo("#salesDocRowData");
                    } else if (edit == 1) {
                        $("#salesDocRowData").empty();
                        $("#salesdocumentTemplate_add").tmpl(data).appendTo("#salesDocRowData");

                    }
                    $("#salesDocRowData").i18n();
                }

                if (processId == 3 || processId == 9)
                {

                    if (edit == 0) {
                        $("#MortagageDocRowData").empty();
                        $("#salesdocumentTemplate_add").tmpl(data).appendTo("#MortagageDocRowData");
                    } else if (edit == 1) {
                        $("#MortagageDocRowData").empty();
                        $("#salesdocumentTemplate_add").tmpl(data).appendTo("#MortagageDocRowData");

                    }
                    $("#MortagageDocRowData").i18n();
                }

            } else {

                if (processId == 3 || processId == 9)
                {
                    $("#MortagageDocRowData").empty();
                }
                if (processId == 2 || processId == 4 || processId == 6 || processId == 7)
                {
                    $("#salesDocRowData").empty();
                }
                if (processId == 1 || processId == 5)
                {
                    $("#LeaseDocRowData").empty();
                }

            }

        }
    });
}







function fetchDocumentSplit(landId)
{
    jQuery.ajax({
        type: 'GET',
        url: "registryrecords/getsplitDocument/" + landId,
        async: false,
        success: function (data) {
            if (data != null) {
                $("#splitDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#splitDocRowData");
                $("#splitDocRowData").i18n();
            } else {
                $("#splitDocRowData").empty();
            }
        }
    });
}


function ApplicantTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Owner_Details").hide();
    $("#Land_Details_lease").hide();
    $("#Applicant_Details").show();
    $("#Lease_Details").hide();
    $("#Upload_Documents_Lease").hide();
    $("#regLeasePoi").hide();

    $("#selectedApplicant").addClass("ui-tabs-active");
    $("#selectedApplicant").addClass("ui-state-active");
    $("#selectedowner").removeClass("ui-tabs-active");
    $("#selectedowner").removeClass("ui-state-active");
    $("#selecteddocs").removeClass("ui-tabs-active");
    $("#selecteddocs").removeClass("ui-state-active");
    $("#selectedsleasedetails").removeClass("ui-tabs-active");
    $("#selectedsleasedetails").removeClass("ui-state-active");
    $("#selectedLanddetails").removeClass("ui-tabs-active");
    $("#selectedLanddetails").removeClass("ui-state-active");
    $("#selectedLeasepoi").removeClass("ui-tabs-active");
    $("#selectedLeasepoi").removeClass("ui-state-active");

}

function LeaseeDetailsTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Owner_Details").hide();
    $("#Land_Details_lease").hide();
    $("#Applicant_Details").hide();
    $("#Lease_Details").show();
    $("#Upload_Documents_Lease").hide();
    $("#regLeasePoi").hide();


    $("#selectedLeasepoi").removeClass("ui-tabs-active");
    $("#selectedLeasepoi").removeClass("ui-state-active");
    $("#selectedApplicant").removeClass("ui-tabs-active");
    $("#selectedApplicant").removeClass("ui-state-active");
    $("#selectedowner").removeClass("ui-tabs-active");
    $("#selectedowner").removeClass("ui-state-active");
    $("#selecteddocs").removeClass("ui-tabs-active");
    $("#selecteddocs").removeClass("ui-state-active");
    $("#selectedsleasedetails").addClass("ui-tabs-active");
    $("#selectedsleasedetails").addClass("ui-state-active");
    $("#selectedLanddetails").removeClass("ui-tabs-active");
    $("#selectedLanddetails").removeClass("ui-state-active");
}


function leasePoiTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Owner_Details").hide();
    $("#Land_Details_lease").hide();
    $("#Applicant_Details").hide();
    $("#Lease_Details").hide();
    $("#Upload_Documents_Lease").hide();
    $("#regLeasePoi").show();

    $("#selectedApplicant").removeClass("ui-tabs-active");
    $("#selectedApplicant").removeClass("ui-state-active");
    $("#selectedowner").removeClass("ui-tabs-active");
    $("#selectedowner").removeClass("ui-state-active");
    $("#selecteddocs").removeClass("ui-tabs-active");
    $("#selecteddocs").removeClass("ui-state-active");
    $("#selectedsleasedetails").removeClass("ui-tabs-active");
    $("#selectedsleasedetails").removeClass("ui-state-active");
    $("#selectedLanddetails").removeClass("ui-tabs-active");
    $("#selectedLanddetails").removeClass("ui-state-active");
    $("#selectedLeasepoi").addClass("ui-tabs-active");
    $("#selectedLeasepoi").addClass("ui-state-active");
}
function landdetailsTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Owner_Details").hide();
    $("#Land_Details_lease").show();
    $("#Applicant_Details").hide();
    $("#Lease_Details").hide();
    $("#Upload_Documents_Lease").hide();
    $("#regLeasePoi").hide();

    $("#selectedApplicant").removeClass("ui-tabs-active");
    $("#selectedApplicant").removeClass("ui-state-active");
    $("#selectedowner").removeClass("ui-tabs-active");
    $("#selectedowner").removeClass("ui-state-active");
    $("#selecteddocs").removeClass("ui-tabs-active");
    $("#selecteddocs").removeClass("ui-state-active");
    $("#selectedsleasedetails").removeClass("ui-tabs-active");
    $("#selectedsleasedetails").removeClass("ui-state-active");
    $("#selectedLanddetails").addClass("ui-tabs-active");
    $("#selectedLanddetails").addClass("ui-state-active");
    $("#selectedLeasepoi").removeClass("ui-tabs-active");
    $("#selectedLeasepoi").removeClass("ui-state-active");
}

function OwnerTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#Owner_Details").show();
    $("#Land_Details_lease").hide();
    $("#Applicant_Details").hide();
    $("#Lease_Details").hide();
    $("#Upload_Documents_Lease").hide();
    $("#regLeasePoi").hide();

    $("#selectedApplicant").removeClass("ui-tabs-active");
    $("#selectedApplicant").removeClass("ui-state-active");
    $("#selectedowner").addClass("ui-tabs-active");
    $("#selectedowner").addClass("ui-state-active");
    $("#selecteddocs").removeClass("ui-tabs-active");
    $("#selecteddocs").removeClass("ui-state-active");
    $("#selectedsleasedetails").removeClass("ui-tabs-active");
    $("#selectedsleasedetails").removeClass("ui-state-active");
    $("#selectedLanddetails").removeClass("ui-tabs-active");
    $("#selectedLanddetails").removeClass("ui-state-active");
    $("#selectedLeasepoi").removeClass("ui-tabs-active");
    $("#selectedLeasepoi").removeClass("ui-state-active");
}

function DocsTabClick()
{
    $("#comment_Save").show();
    $("#comment_Next").hide();

    $("#Owner_Details").hide();
    $("#Land_Details_lease").hide();
    $("#Applicant_Details").hide();
    $("#Lease_Details").hide();
    $("#Upload_Documents_Lease").show();
    $("#regLeasePoi").hide();

    $("#selectedApplicant").removeClass("ui-tabs-active");
    $("#selectedApplicant").removeClass("ui-state-active");
    $("#selectedowner").removeClass("ui-tabs-active");
    $("#selectedowner").removeClass("ui-state-active");
    $("#selecteddocs").addClass("ui-tabs-active");
    $("#selecteddocs").addClass("ui-state-active");
    $("#selectedsleasedetails").removeClass("ui-tabs-active");
    $("#selectedsleasedetails").removeClass("ui-state-active");
    $("#selectedLanddetails").removeClass("ui-tabs-active");
    $("#selectedLanddetails").removeClass("ui-state-active");
    $("#selectedLeasepoi").removeClass("ui-tabs-active");
    $("#selectedLeasepoi").removeClass("ui-state-active");
}


function saveattributesLeaseDetails() {


    if ($("#editprocessAttributeformID").valid()) {



        if ((no_Of_month_Lease.value.length == 0)
                || (lease_Amount.value.length == 0)) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        }



        jQuery
                .ajax({
                    type: "POST",
                    url: "registration/updateLeaseeDetails",
                    data: jQuery("#editprocessAttributeformID")
                            .serialize(),
                    success: function (result) {

                        $('#leaseeperson').val(0);
                        $("#Lease_Details").hide();
                        $("#regLeasePoi").hide();
                        $("#Upload_Documents_Lease").show();


                    },
                    error: function (XMLHttpRequest,
                            textStatus, errorThrown) {
                        jAlert($.i18n("err-request-not-completed"));
                    }
                });

    } else {

        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}




function saveMortgage() {

    if ($("#editprocessAttributeformID").valid()) {

        if ((mortgage_from.value.length == 0)
                || (mortgage_to.value.length == 0)
                || (amount_mortgage.value.length == 0)) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        } else if ((mortgage_Financial_Agencies.value == "0")) {
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        }
        jQuery
                .ajax({
                    type: "POST",
                    url: "registration/savemortgagedata",
                    data: jQuery("#editprocessAttributeformID")
                            .serialize(),
                    success: function (result) {
                        if (result != null && result != undefined) {
//											
                        } else {
                            jAlert($.i18n("err-request-not-completed"));
                        }
                    },
                    error: function (XMLHttpRequest,
                            textStatus, errorThrown) {
                        jAlert($.i18n("err-request-not-completed"));
                    }
                });


    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}


function MortgageTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#MortgageOwner_Details").hide();
    $("#Land_Details_Mortgage").hide();
    $("#Mortgage_Details").show();
    $("#Upload_Document_Mortgage").hide();

    $("#selectemortgage").addClass("ui-tabs-active");
    $("#selectemortgage").addClass("ui-state-active");
    $("#selectedownerdetails").removeClass("ui-tabs-active");
    $("#selectedownerdetails").removeClass("ui-state-active");
    $("#selectelandmortgage").removeClass("ui-tabs-active");
    $("#selectelandmortgage").removeClass("ui-state-active");
    $("#selectemortgagedocs").removeClass("ui-tabs-active");
    $("#selectemortgagedocs").removeClass("ui-state-active");
}



function MortgagelanddetailsTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#MortgageOwner_Details").hide();
    $("#Land_Details_Mortgage").show();
    $("#Mortgage_Details").hide();
    $("#Upload_Document_Mortgage").hide();

    $("#selectemortgage").removeClass("ui-tabs-active");
    $("#selectemortgage").removeClass("ui-state-active");
    $("#selectedownerdetails").removeClass("ui-tabs-active");
    $("#selectedownerdetails").removeClass("ui-state-active");
    $("#selectelandmortgage").addClass("ui-tabs-active");
    $("#selectelandmortgage").addClass("ui-state-active");
    $("#selectemortgagedocs").removeClass("ui-tabs-active");
    $("#selectemortgagedocs").removeClass("ui-state-active");
}

function MortgageOwnerTabClick()
{
    $("#comment_Save").hide();
    $("#comment_Next").show();

    $("#MortgageOwner_Details").show();
    $("#Land_Details_Mortgage").hide();
    $("#Mortgage_Details").hide();
    $("#Upload_Document_Mortgage").hide();

    $("#selectemortgage").removeClass("ui-tabs-active");
    $("#selectemortgage").removeClass("ui-state-active");
    $("#selectedownerdetails").addClass("ui-tabs-active");
    $("#selectedownerdetails").addClass("ui-state-active");
    $("#selectelandmortgage").removeClass("ui-tabs-active");
    $("#selectelandmortgage").removeClass("ui-state-active");
    $("#selectemortgagedocs").removeClass("ui-tabs-active");
    $("#selectemortgagedocs").removeClass("ui-state-active");
}

function MortgageDocsTabClick()
{
    $("#comment_Save").show();
    $("#comment_Next").hide();

    $("#MortgageOwner_Details").hide();
    $("#Land_Details_Mortgage").hide();
    $("#Mortgage_Details").hide();
    $("#Upload_Document_Mortgage").show();

    $("#selectemortgage").removeClass("ui-tabs-active");
    $("#selectemortgage").removeClass("ui-state-active");
    $("#selectedownerdetails").removeClass("ui-tabs-active");
    $("#selectedownerdetails").removeClass("ui-state-active");
    $("#selectelandmortgage").removeClass("ui-tabs-active");
    $("#selectelandmortgage").removeClass("ui-state-active");
    $("#selectemortgagedocs").addClass("ui-tabs-active");
    $("#selectemortgagedocs").addClass("ui-state-active");
}



function uploadMediaFile_sales(id)
{


    var flag = false;
    var val1 = 0;
    var formData = new FormData();
    var appid = '#' + "mediafileUpload" + id;
    var file = $("" + appid + "")[0].files[0];

    var alias = $("#alias").val();

    if (typeof (file) === "undefined")
    {

        jAlert($.i18n("err-select-file-to-upload"), $.i18n("gen-info"));
    } else
    {

        $.each($("" + appid + "")[0].files,
                function (ind2, obj2) {
                    $.each($("" + appid + "")[0].files[val1],
                            function (ind3, obj3) {
                                if (ind3 == "type") {
                                    if (obj3 == "application/x-msdownload") {
                                        flag = false;
                                        jAlert($.i18n("err-file-mustbe-exe"));
                                    } else {
                                        flag = true;


                                    }
                                }

                            });
                    val1 = val1 + 1;
                });

        if (flag) {
            formData.append("spatialdata", file);
            formData.append("documentId", id);
            $.ajax({
                url: 'upload/media_sale/',
                type: 'POST',
                data: formData,
                mimeType: "multipart/form-data",
                contentType: false,
                cache: false,
                processData: false,
                success: function (data, textStatus, jqXHR)
                {
                    if (edit == 1)
                    {
                        documentEditFetch();
                        jAlert($.i18n("reg-file-uploaded"), $.i18n("gen-info"));
                        $('#fileUploadSpatial').val('');
                        $('#alias').val('');
                    } else {
                        fetchDocument(parseInt(data), persontypeid, processid);
                        jAlert($.i18n("reg-file-uploaded"), $.i18n("gen-info"));
                        $('#fileUploadSpatial').val('');
                        $('#alias').val('');
                    }


                }

            });
        }

    }
}


function viewMultimediaByTransid_sales(id) {

    var flag = false;
    jQuery.ajax({
        type: 'GET',
        async: false,
        url: "registration/mediaavail/" + id,
        success: function (result) {
            if (result == true) {
                flag = true;
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert($.i18n("err-request-not-completed"));
        }
    });

    if (flag) {
        window.open("registration/download/" + id, 'popUp', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
    } else {

        jAlert($.i18n("err-file-not-found"), $.i18n("gen-info"));
    }
}



function deleteMediaFile_sales(id) {
    var landid = 0;
    var formData = new FormData();
    formData.append("documentId", id);

    $.ajax({
        url: 'delete/media_sale/',
        type: 'POST',
        data: formData,
        mimeType: "multipart/form-data",
        contentType: false,
        cache: false,
        processData: false,
        success: function (data, textStatus, jqXHR)
        {

            fetchDocument(parseInt(data), persontypeid, processid);

            landid = data;
            fetchDocumentSplit(landid);
            jAlert($.i18n("reg-file-deleted"));



        }

    });
}




function saveattributesSurrenderMortagage() {

    if ($("#editprocessAttributeformID").valid()) {

        /*if ((remrks_lease.value.length == 0)) {
         jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
         return false;
         }*/


        /*jConfirm(
         'If he/she wants to commit data to surrender lease would be nice',
         $.i18n("gen-confirmation"),
         function(response) {
         if (response) {}
         });*/


        jQuery
                .ajax({
                    type: "POST",
                    url: "registration/savesurrenderMortgagedata",
                    data: jQuery("#editprocessAttributeformID")
                            .serialize(),
                    success: function (result) {
                        if (result != null && result != undefined) {
                            $("#Lease_Details").hide();
                            $("#Upload_Documents_Lease").show();

                        } else {
                            jAlert($.i18n("err-request-not-completed"));
                        }
                    },
                    error: function (XMLHttpRequest,
                            textStatus, errorThrown) {
                        jAlert($.i18n("err-request-not-completed"));
                    }
                });


    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}




function approveSurrenderMortagage() {




    if ($("#editprocessAttributeformID").valid()) {

        if ($("#mortgagesurrender_reason").val() == "") {
            jAlert($.i18n("err-fill-surender-reason"), $.i18n("err-alert"));
            return false;
        }

        jConfirm(
                $.i18n("gen-save-confirmation"),
                $.i18n("gen-confirmation"),
                function (response) {
                    if (response) {
                        jQuery
                                .ajax({
                                    type: "POST",
                                    url: "registration/approveSurenderMortgageData",
                                    data: jQuery("#editprocessAttributeformID")
                                            .serialize(),
                                    success: function (result) {
                                        if (result != null && result != undefined) {
                                            landRecordsInitialized_R = false;
                                            displayRefreshedRegistryRecords_ABC();
                                            //RegistrationRecords("registrationRecords");
                                            attributeEditDialog.dialog("close");

                                        } else {
                                            jAlert($.i18n("err-request-not-completed"));
                                        }
                                    },
                                    error: function (XMLHttpRequest,
                                            textStatus, errorThrown) {
                                        jAlert($.i18n("err-request-not-completed"));
                                    }
                                });
                    }

                });

    } else {
        jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
    }
}


function editNewBuyer(id) {
    if (arry_Buyerbyprocessid.length > 0) {
        for (var i = 0; i < arry_Buyerbyprocessid.length; i++) {

            if (id == arry_Buyerbyprocessid[i].personid) {


                jQuery("#firstname_r_sale1").val(arry_Buyerbyprocessid[i].firstname);
                jQuery("#middlename_r_sale1").val(arry_Buyerbyprocessid[i].middlename);
                jQuery("#lastname_r_sale1").val(arry_Buyerbyprocessid[i].lastname);
                jQuery("#id_r1").val(arry_Buyerbyprocessid[i].identityno);
                jQuery("#contact_no1").val(arry_Buyerbyprocessid[i].contactno);
                jQuery("#address_sale1").val(arry_Buyerbyprocessid[i].address);
                jQuery("#date_Of_birth_sale1").val(arry_Buyerbyprocessid[i].dob);

                jQuery("#sale_gender_buyer").val(arry_Buyerbyprocessid[i].genderid);
                jQuery("#sale_idtype_buyer").val(arry_Buyerbyprocessid[i].identitytypeid);
                jQuery("#sale_marital_buyer").val(arry_Buyerbyprocessid[i].maritalstatusid);
                jQuery("#personid").val(arry_Buyerbyprocessid[i].personid);



            }

        }

    }

}

function editNewLeasee(id) {
    if (leaseepersondata.length > 0) {
        for (var i = 0; i < leaseepersondata.length; i++) {

            if (id == leaseepersondata[i].personid) {

                editlease = true;

                //$("#leaseepersonid").val(leaseepersondata.personid);
                $("#leaseeperson").val(leaseepersondata[i].personid);
                jQuery("#firstname_r_applicant").val(leaseepersondata[i].firstname);
                jQuery("#middlename_r_applicant").val(leaseepersondata[i].middlename);
                jQuery("#lastname_r_applicant").val(leaseepersondata[i].lastname);
                jQuery("#id_r_applicant").val(leaseepersondata[i].identityno);
                jQuery("#contact_no_applicant").val(leaseepersondata[i].contactno);
                jQuery("#address_lease_applicant").val(leaseepersondata[i].address);
                jQuery("#date_Of_birth_applicant").val(leaseepersondata[i].dob);
                jQuery("#lease_Amount").val(leaseepersondata[i].hierarchyid1);
                jQuery("#no_Of_month_Lease").val(leaseepersondata[i].hierarchyid2);
                jQuery("#Start_date_Lease").val(leaseepersondata[i].leaseStartdate);
                jQuery("#End_date_Lease").val(leaseepersondata[i].leaseEnddate);
                jQuery("#martial_sts_applicant").empty();
                jQuery("#gender_type_applicant").empty();
                jQuery("#id_type_applicant").empty();

                jQuery("#id_type_applicant").append(
                        jQuery("<option></option>").attr("value", 0).text(
                        $.i18n("gen-please-select")));
                jQuery("#gender_type_applicant").append(
                        jQuery("<option></option>").attr("value", 0).text(
                        $.i18n("gen-please-select")));
                jQuery("#martial_sts_applicant").append(
                        jQuery("<option></option>").attr("value", 0).text(
                        $.i18n("gen-please-select")));

                jQuery.each(maritalStatus_R, function (i, obj) {
                    jQuery("#martial_sts_applicant").append(
                            jQuery("<option></option>").attr("value",
                            obj.maritalstatusid).text(
                            obj.maritalstatusEn));
                });

                jQuery("#martial_sts_applicant").val(leaseepersondata[i].maritalstatusid);

                jQuery.each(genderStatus_R, function (i, obj1) {
                    jQuery("#gender_type_applicant").append(
                            jQuery("<option></option>").attr("value",
                            obj1.genderId).text(obj1.gender_en));
                });

                jQuery("#gender_type_applicant").val(leaseepersondata[i].genderid);

                jQuery.each(idtype_R, function (i, obj1) {
                    jQuery("#id_type_applicant").append(
                            jQuery("<option></option>").attr("value",
                            obj1.identitytypeid).text(
                            obj1.identitytypeEn));
                });
                jQuery("#id_type_applicant").val(leaseepersondata[i].identitytypeid);



            }
        }
    }


}

function editSeller(id) {
    if (arry_Sellerbyprocessid.length > 0) {
        for (var i = 0; i < arry_Sellerbyprocessid.length; i++) {

            if (id == arry_Sellerbyprocessid[i].personid) {


                jQuery("#firstname_r_sale1").val(arry_Sellerbyprocessid[i].firstname);
                jQuery("#middlename_r_sale1").val(arry_Sellerbyprocessid[i].middlename);
                jQuery("#lastname_r_sale1").val(arry_Sellerbyprocessid[i].lastname);
                jQuery("#id_r1").val(arry_Sellerbyprocessid[i].identityno);
                jQuery("#contact_no1").val(arry_Sellerbyprocessid[i].contactno);
                jQuery("#address_sale1").val(arry_Sellerbyprocessid[i].address);
                jQuery("#date_Of_birth_sale1").val(arry_Sellerbyprocessid[i].dob);

                jQuery("#sale_gender_buyer").val(arry_Sellerbyprocessid[i].genderid);
                jQuery("#sale_idtype_buyer").val(arry_Sellerbyprocessid[i].identitytypeid);
                jQuery("#sale_marital_buyer").val(arry_Sellerbyprocessid[i].maritalstatusid);
                jQuery("#personid").val(arry_Sellerbyprocessid[i].personid);



            }

        }

    }

}




function fillBuyerandSellerpage(landid) {
    finallandid = landid;
    jQuery("#personid").val(0);

    var arry_Seller = [];
    var arry_Buyer = [];
    jQuery
            .ajax({
                //url : "registration/partydetails/sale/" + landid,

                url: "registration/partydetails/filldetails/" + landid + "/" + firstselectedprocess,
                async: false,
                success: function (objdata) {
                    jQuery.each(objdata, function (i, obj)
                    {
                        if (obj.persontype == 1)  // sler
                        {
                            arry_Seller.push(obj);
                        } else if (obj.persontype == 11)  //buyer
                        {
                            arry_Buyer.push(obj);
                        }

                    });

                    if (arry_Seller != null && arry_Seller.length == 1)
                    {
                        data = arry_Seller[0];
                        fillLeaseDetails(data);
                        fillMortgageDetails(data);
                        fillSurrenderLeaseDetails(landid);

                        jQuery("#firstname_r_sale").val(data.firstname);
                        jQuery("#middlename_r_sale").val(data.middlename);
                        jQuery("#lastname_r_sale").val(data.lastname);
                        jQuery("#id_r").val(data.identityno);
                        jQuery("#contact_no_sale").val(data.contactno);
                        jQuery("#address_sale").val(data.address);
                        jQuery("#date_Of_birth_sale").val(data.dob);

                        jQuery("#sale_martial_status").empty();
                        jQuery("#sale_gender").empty();
                        jQuery("#sale_id_type").empty();

                        jQuery.each(maritalStatus_R, function (i, obj) {
                            jQuery("#sale_martial_status").append(
                                    jQuery("<option></option>").attr("value",
                                    obj.maritalstatusid).text(
                                    obj.maritalstatusEn));
                        });

                        jQuery("#sale_martial_status").val(data.maritalstatusid);

                        jQuery.each(genderStatus_R, function (i, obj1) {
                            jQuery("#sale_gender").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.genderId).text(obj1.gender_en));
                        });

                        jQuery("#sale_gender").val(data.genderid);

                        jQuery.each(idtype_R, function (i, obj1) {
                            jQuery("#sale_id_type").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.identitytypeid).text(
                                    obj1.identitytypeEn));
                        });
                        jQuery("#sale_id_type").val(data.identitytypeid);
                    }




                    if (arry_Seller != null && arry_Seller.length > 1)
                    {

                        data = arry_Seller[0];
                        fillLeaseDetails(data);
                        fillMortgageDetails(data);
                        fillSurrenderLeaseDetails(landid);

                        jQuery("#firstname_r_sale").val(data.firstname);
                        jQuery("#middlename_r_sale").val(data.middlename);
                        jQuery("#lastname_r_sale").val(data.lastname);
                        jQuery("#id_r").val(data.identityno);
                        jQuery("#contact_no_sale").val(data.contactno);
                        jQuery("#address_sale").val(data.address);
                        jQuery("#date_Of_birth_sale").val(data.dob);

                        jQuery("#sale_martial_status").empty();
                        jQuery("#sale_gender").empty();
                        jQuery("#sale_id_type").empty();

                        jQuery.each(maritalStatus_R, function (i, obj) {
                            jQuery("#sale_martial_status").append(
                                    jQuery("<option></option>").attr("value",
                                    obj.maritalstatusid).text(
                                    obj.maritalstatusEn));
                        });

                        jQuery("#sale_martial_status").val(data.maritalstatusid);

                        jQuery.each(genderStatus_R, function (i, obj1) {
                            jQuery("#sale_gender").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.genderId).text(obj1.gender_en));
                        });

                        jQuery("#sale_gender").val(data.genderid);

                        jQuery.each(idtype_R, function (i, obj1) {
                            jQuery("#sale_id_type").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.identitytypeid).text(
                                    obj1.identitytypeEn));
                        });
                        jQuery("#sale_id_type").val(data.identitytypeid);

                        if (arry_Seller[1] != null)
                        {
                            data1 = arry_Seller[1];
                            jQuery("#firstname_r_sale1second").val(data1.firstname);
                            jQuery("#middlename_r_sale1second").val(data1.middlename);
                            jQuery("#lastname_r_sale1second").val(data1.lastname);
                            jQuery("#id_r1second").val(data1.identityno);
                            jQuery("#contact_no_sale1second").val(data1.contactno);
                            jQuery("#address_sale1second").val(data1.address);
                            jQuery("#date_Of_birth_sale1second").val(data1.dob);

                            jQuery("#sale_martial_status1second").empty();
                            jQuery("#sale_gender1second").empty();
                            jQuery("#sale_id_type1second").empty();

                            jQuery.each(maritalStatus_R, function (i, obj) {
                                jQuery("#sale_martial_status1second").append(
                                        jQuery("<option></option>").attr("value",
                                        obj.maritalstatusid).text(
                                        obj.maritalstatusEn));
                            });

                            jQuery("#sale_martial_status1second").val(data.maritalstatusid);

                            jQuery.each(genderStatus_R, function (i, obj1) {
                                jQuery("#sale_gender1second").append(
                                        jQuery("<option></option>").attr("value",
                                        obj1.genderId).text(obj1.gender_en));
                            });

                            jQuery("#sale_gender1second").val(data.genderid);

                            jQuery.each(idtype_R, function (i, obj1) {
                                jQuery("#sale_id_type1second").append(
                                        jQuery("<option></option>").attr("value",
                                        obj1.identitytypeid).text(
                                        obj1.identitytypeEn));
                            });
                            jQuery("#sale_id_type1second").val(data.identitytypeid);

                        }

                        jQuery("#Owner_Elimanated").empty();
                        jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text($.i18n("gen-please-select")));

                        jQuery.each(objdata, function (i, obj1)
                        {
                            jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", obj1.personid).text(obj1.firstname));
                        });

                        $("#SecondOwner").css("display", "block");
                    }

                    if (arry_Seller != null && arry_Seller.length == 1)
                    {
                        jQuery("#Owner_Elimanated").empty();
                        jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text($.i18n("gen-please-select")));
                        jQuery.each(arry_Seller, function (i, obj1)
                        {
                            jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", obj1.personid).text(obj1.firstname));
                        });
                        jQuery("#Owner_Elimanated").val(objdata[0].personid);
                    }

                    // Buyer

                    jQuery("#sale_marital_buyer").empty();
                    jQuery("#sale_gender_buyer").empty();
                    jQuery("#sale_idtype_buyer").empty();

                    jQuery("#doc_Type_Sale").empty();

                    jQuery("#sale_marital_buyer").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_gender_buyer").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_idtype_buyer").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#doc_Type_Sale").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));

                    jQuery.each(maritalStatus_R, function (i, obj1) {
                        jQuery("#sale_marital_buyer").append(
                                jQuery("<option></option>").attr("value",
                                obj1.maritalstatusid).text(
                                obj1.maritalstatusEn));
                    });
                    jQuery.each(genderStatus_R, function (i, obj1) {
                        jQuery("#sale_gender_buyer").append(
                                jQuery("<option></option>").attr("value",
                                obj1.genderId).text(obj1.gender_en));
                    });
                    jQuery.each(idtype_R, function (i, obj1) {
                        jQuery("#sale_idtype_buyer").append(
                                jQuery("<option></option>").attr("value",
                                obj1.identitytypeid).text(
                                obj1.identitytypeEn));
                    });

                    jQuery.each(documenttype_R, function (i, obj) {
                        jQuery("#doc_Type_Sale").append(
                                jQuery("<option></option>").attr("value",
                                obj.code).text(obj.nameOtherLang));
                    });

                    if (arry_Buyer != null && arry_Buyer.length > 0)
                    {
                        databuyer = arry_Buyer[0];

                        /*  jQuery("#firstname_r_sale1").val(databuyer.firstname);
                         jQuery("#middlename_r_sale1").val(databuyer.middlename);
                         jQuery("#lastname_r_sale1").val(databuyer.lastname);
                         jQuery("#id_r1").val(databuyer.identityno);
                         jQuery("#contact_no1").val(databuyer.contactno);
                         jQuery("#address_sale1").val(databuyer.address);
                         jQuery("#date_Of_birth_sale1").val(databuyer.dob);
                         
                         jQuery("#sale_gender_buyer").val(databuyer.genderid);
                         jQuery("#sale_idtype_buyer").val(databuyer.identitytypeid);
                         jQuery("#sale_marital_buyer").val(databuyer.maritalstatusid);*/

                    }



                    // Land Details landtype_r
                    jQuery("#sale_land_type").empty();
                    jQuery("#sale_country").empty();
                    jQuery("#sale_region").empty();
                    jQuery("#province_r").empty();
                    jQuery("#sale_land_Share_type").empty();
                    jQuery("#sale_province").empty();

                    jQuery("#sale_country").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_region").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_province").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_land_type").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_land_Share_type").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));

                    jQuery.each(landtype_r,
                            function (i, obj1) {
                                jQuery("#sale_land_type").append(
                                        jQuery("<option></option>").attr(
                                        "value", obj1.landtypeid).text(
                                        obj1.landtypeEn));
                            });
                    jQuery.each(allcountry, function (i, obj) {
                        jQuery("#sale_country").append(
                                jQuery("<option></option>").attr("value",
                                obj.hierarchyid).text(obj.nameEn));
                    });

                    jQuery.each(region_r, function (i, obj) {
                        jQuery("#sale_region").append(
                                jQuery("<option></option>").attr("value",
                                obj.hierarchyid).text(obj.nameEn));
                    });
                    jQuery.each(province_r, function (i, obj) {
                        jQuery("#sale_province").append(
                                jQuery("<option></option>").attr("value",
                                obj.hierarchyid).text(obj.nameEn));
                    });

                    jQuery.each(landsharetype_r, function (i, obj) {
                        jQuery("#sale_land_Share_type").append(
                                jQuery("<option></option>").attr("value",
                                obj.landsharetypeid).text(
                                obj.landsharetypeEn));
                    });

                    jQuery("#sale_land_type").val(
                            laspatialunitland_R[0].landtypeid);
                    jQuery("#sale_country").val(
                            laspatialunitland_R[0].hierarchyid1);
                    jQuery("#sale_region").val(
                            laspatialunitland_R[0].hierarchyid2);
                    jQuery("#sale_province").val(
                            laspatialunitland_R[0].hierarchyid3);

                    jQuery("#parcel_r").val("000000" + laspatialunitland_R[0].landid);
                    jQuery("#sale_land_Share_type").val(
                            laspatialunitland_R[0].landusetypeid);
                    jQuery("#sale_area").val(laspatialunitland_R[0].area);
                    jQuery("#sale_land_use").val(
                            laspatialunitland_R[0].landusetype_en);

                    jQuery("#neighbor_west_sale").val(
                            laspatialunitland_R[0].neighbor_west);
                    jQuery("#neighbor_north_sale").val(
                            laspatialunitland_R[0].neighbor_north);
                    jQuery("#neighbor_south_sale").val(
                            laspatialunitland_R[0].neighbor_south);
                    jQuery("#neighbor_east_sale").val(
                            laspatialunitland_R[0].neighbor_east);
                }
            });


}


function SaleOwnerdBuyeretails(landid) {
    var arry_Seller = [];
    var arry_Buyer = [];
    var URL = "";
    if (edit == 0) {

        URL = "registration/partydetails/sale/" + landid

    } else if (edit == 1) {

        URL = "registration/editpartydetails/" + finaltransid


    }

    jQuery
            .ajax({
                url: URL,
                async: false,
                success: function (objdata) {
                    jQuery.each(objdata, function (i, obj)
                    {
                        if (obj.persontype == 1)  // sler
                        {
                            arry_Seller.push(obj);
                        } else if (obj.persontype == 11)  //buyer
                        {

                            arry_Buyer.push(obj);
                        }

                    });
                    arry_Sellerbyprocessid = arry_Seller;
                    arry_Buyerbyprocessid = arry_Buyer;

                    jQuery("#OwnerRecordsRowDataSale").empty();
                    jQuery("#OwnerRecordsRowDataLease").empty();
                    jQuery("#OwnerRecordsRowDataMortgage").empty();

                    jQuery("#newOwnerRecordsRowDataSale").empty();
                    jQuery("#OwnerRecordsRowDataSaleEdit").empty();
                    if (edit == 0) {
                        $("#SellerEdit").hide();
                        $("#newOwner").show();
                        $("#RegpersonsEditingGrid").show();
                        $("#editRegpersonsEditingGrid").hide();
                        $("#RegLeasepersonsEditingGrid").show();
                        $("#editRegpersonsEditingGridLeasee").hide();

                        $("#salesDocRowData").empty();
                        $("#MortagageDocRowData").empty();
                        $("#LeaseDocRowData").empty();
                        $("#editflag").val(edit);

                        $("#Seller_Details").show();
                        $("#Land_Details_Sale").show();
                        $("#Owner_Details").show();
                        $("#Land_Details_lease").show();
                        $("#MortgageOwner_Details").show();
                        $("#Land_Details_Mortgage").show();
                        $("#selectedseller").show();
                        $("#selectedland").show();
                        $("#selectedowner").show();
                        $("#selectedLanddetails").show();
                        $("#selectedownerdetails").show();
                        $("#selectelandmortgage").show();



                    }
                    if (edit == 1) {
                        $("#SellerEdit").show();
                        $("#newOwner").hide();
                        $("#RegpersonsEditingGrid").hide();
                        $("#editRegpersonsEditingGrid").show();
                        $("#editflag").val(edit);

                        if (process_id == 2 || process_id == 4 || process_id == 6 || process_id == 7) {
                            $("#Seller_Details").hide();
                            $("#Land_Details_Sale").hide();
                            $("#Buyer_Details").show();
                            $("#selectedseller").hide();
                            $("#selectedland").hide();
                        } else if (process_id == 1 || process_id == 5) {
                            $("#Owner_Details").hide();
                            $("#Land_Details_lease").hide();
                            $("#selectedowner").hide();
                            $("#selectedLanddetails").hide();
                            $("#Applicant_Details").show();
                        } else if (process_id == 3 || process_id == 9) {
                            $("#MortgageOwner_Details").hide();
                            $("#Land_Details_Mortgage").hide();
                            $("#Mortgage_Details").show();
                            $("#selectedownerdetails").hide();
                            $("#selectelandmortgage").hide();
                        }

                    }


                    if (arry_Seller.length > 0) {
                        for (var i = 0; i < arry_Seller.length; i++) {
                            jQuery("#OwnerRecordsAttrTemplateSale").tmpl(arry_Seller[i]).appendTo("#OwnerRecordsRowDataSale");
                            jQuery("#OwnerRecordsAttrTemplateLease").tmpl(arry_Seller[i]).appendTo("#OwnerRecordsRowDataLease");
                            jQuery("#OwnerRecordsAttrTemplateMortgage").tmpl(arry_Seller[i]).appendTo("#OwnerRecordsRowDataMortgage");

                            jQuery("#OwnerRecordsAttrTemplateSaleEdit").tmpl(arry_Seller[i]).appendTo("#OwnerRecordsRowDataSaleEdit");
                            $("#OwnerRecordsRowDataSaleEdit").i18n();

                        }
                    }


                    finalbuyerarray = arry_Buyer;



                    if (arry_Seller != null && arry_Seller.length == 1)
                    {

                        if (arry_Seller[1] != null) {

                            $("#SecondOwner").show();
                        }
                        $("#SecondOwner").hide();

                        data = arry_Seller[0];
                        fillLeaseDetails(data);
                        fillMortgageDetails(data);
                        fillSurrenderLeaseDetails(landid);

                        jQuery("#firstname_r_sale").val(data.firstname);
                        jQuery("#middlename_r_sale").val(data.middlename);
                        jQuery("#lastname_r_sale").val(data.lastname);
                        jQuery("#id_r").val(data.identityno);
                        jQuery("#contact_no_sale").val(data.contactno);
                        jQuery("#address_sale").val(data.address);
                        jQuery("#date_Of_birth_sale").val(data.dob);

                        jQuery("#sale_martial_status").empty();
                        jQuery("#sale_gender").empty();
                        jQuery("#sale_id_type").empty();

                        jQuery.each(maritalStatus_R, function (i, obj) {
                            jQuery("#sale_martial_status").append(
                                    jQuery("<option></option>").attr("value",
                                    obj.maritalstatusid).text(
                                    obj.maritalstatusEn));
                        });

                        jQuery("#sale_martial_status").val(data.maritalstatusid);

                        jQuery.each(genderStatus_R, function (i, obj1) {
                            jQuery("#sale_gender").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.genderId).text(obj1.gender_en));
                        });

                        jQuery("#sale_gender").val(data.genderid);

                        jQuery.each(idtype_R, function (i, obj1) {
                            jQuery("#sale_id_type").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.identitytypeid).text(
                                    obj1.identitytypeEn));
                        });
                        jQuery("#sale_id_type").val(data.identitytypeid);
                    }




                    if (arry_Seller != null && arry_Seller.length > 1)
                    {

                        if (arry_Seller[1] != null) {

                            $("#SecondOwner").show();
                        }
                        $("#SecondOwner").hide();
                        data = arry_Seller[0];
                        fillLeaseDetails(data);
                        fillMortgageDetails(data);
                        fillSurrenderLeaseDetails(landid);

                        jQuery("#firstname_r_sale").val(data.firstname);
                        jQuery("#middlename_r_sale").val(data.middlename);
                        jQuery("#lastname_r_sale").val(data.lastname);
                        jQuery("#id_r").val(data.identityno);
                        jQuery("#contact_no_sale").val(data.contactno);
                        jQuery("#address_sale").val(data.address);
                        jQuery("#date_Of_birth_sale").val(data.dob);

                        jQuery("#sale_martial_status").empty();
                        jQuery("#sale_gender").empty();
                        jQuery("#sale_id_type").empty();

                        jQuery.each(maritalStatus_R, function (i, obj) {
                            jQuery("#sale_martial_status").append(
                                    jQuery("<option></option>").attr("value",
                                    obj.maritalstatusid).text(
                                    obj.maritalstatusEn));
                        });

                        jQuery("#sale_martial_status").val(data.maritalstatusid);

                        jQuery.each(genderStatus_R, function (i, obj1) {
                            jQuery("#sale_gender").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.genderId).text(obj1.gender_en));
                        });

                        jQuery("#sale_gender").val(data.genderid);

                        jQuery.each(idtype_R, function (i, obj1) {
                            jQuery("#sale_id_type").append(
                                    jQuery("<option></option>").attr("value",
                                    obj1.identitytypeid).text(
                                    obj1.identitytypeEn));
                        });
                        jQuery("#sale_id_type").val(data.identitytypeid);

                        if (arry_Seller[1] != null)
                        {
                            data1 = arry_Seller[1];
                            jQuery("#firstname_r_sale1second").val(data1.firstname);
                            jQuery("#middlename_r_sale1second").val(data1.middlename);
                            jQuery("#lastname_r_sale1second").val(data1.lastname);
                            jQuery("#id_r1second").val(data1.identityno);
                            jQuery("#contact_no_sale1second").val(data1.contactno);
                            jQuery("#address_sale1second").val(data1.address);
                            jQuery("#date_Of_birth_sale1second").val(data1.dob);

                            jQuery("#sale_martial_status1second").empty();
                            jQuery("#sale_gender1second").empty();
                            jQuery("#sale_id_type1second").empty();

                            jQuery.each(maritalStatus_R, function (i, obj) {
                                jQuery("#sale_martial_status1second").append(
                                        jQuery("<option></option>").attr("value",
                                        obj.maritalstatusid).text(
                                        obj.maritalstatusEn));
                            });

                            jQuery("#sale_martial_status1second").val(data.maritalstatusid);

                            jQuery.each(genderStatus_R, function (i, obj1) {
                                jQuery("#sale_gender1second").append(
                                        jQuery("<option></option>").attr("value",
                                        obj1.genderId).text(obj1.gender_en));
                            });

                            jQuery("#sale_gender1second").val(data.genderid);

                            jQuery.each(idtype_R, function (i, obj1) {
                                jQuery("#sale_id_type1second").append(
                                        jQuery("<option></option>").attr("value",
                                        obj1.identitytypeid).text(
                                        obj1.identitytypeEn));
                            });
                            jQuery("#sale_id_type1second").val(data.identitytypeid);

                        }

                        jQuery("#Owner_Elimanated").empty();
                        jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text($.i18n("gen-please-select")));

                        jQuery.each(objdata, function (i, obj1)
                        {
                            jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", obj1.personid).text(obj1.firstname));
                        });

                        $("#SecondOwner").css("display", "block");
                    }

                    if (arry_Seller != null && arry_Seller.length == 1)
                    {
                        jQuery("#Owner_Elimanated").empty();
                        jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text($.i18n("gen-please-select")));
                        jQuery.each(arry_Seller, function (i, obj1)
                        {
                            jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", obj1.personid).text(obj1.firstname));
                        });
                        jQuery("#Owner_Elimanated").val(objdata[0].personid);
                    }

                    // Buyer

                    jQuery("#sale_marital_buyer").empty();
                    jQuery("#sale_gender_buyer").empty();
                    jQuery("#sale_idtype_buyer").empty();

                    jQuery("#doc_Type_Sale").empty();

                    jQuery("#sale_marital_buyer").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_gender_buyer").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_idtype_buyer").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#doc_Type_Sale").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));

                    jQuery.each(maritalStatus_R, function (i, obj1) {
                        jQuery("#sale_marital_buyer").append(
                                jQuery("<option></option>").attr("value",
                                obj1.maritalstatusid).text(
                                obj1.maritalstatusEn));
                    });
                    jQuery.each(genderStatus_R, function (i, obj1) {
                        jQuery("#sale_gender_buyer").append(
                                jQuery("<option></option>").attr("value",
                                obj1.genderId).text(obj1.gender_en));
                    });
                    jQuery.each(idtype_R, function (i, obj1) {
                        jQuery("#sale_idtype_buyer").append(
                                jQuery("<option></option>").attr("value",
                                obj1.identitytypeid).text(
                                obj1.identitytypeEn));
                    });

                    jQuery.each(documenttype_R, function (i, obj) {
                        jQuery("#doc_Type_Sale").append(
                                jQuery("<option></option>").attr("value",
                                obj.code).text(obj.nameOtherLang));
                    });

                    if (arry_Buyer != null && arry_Buyer.length > 0)
                    {
                        databuyer = arry_Buyer[0];

                        /*jQuery("#firstname_r_sale1").val(databuyer.firstname);
                         jQuery("#middlename_r_sale1").val(databuyer.middlename);
                         jQuery("#lastname_r_sale1").val(databuyer.lastname);
                         jQuery("#id_r1").val(databuyer.identityno);
                         jQuery("#contact_no1").val(databuyer.contactno);
                         jQuery("#address_sale1").val(databuyer.address);
                         jQuery("#date_Of_birth_sale1").val(databuyer.dob);
                         
                         jQuery("#sale_gender_buyer").val(databuyer.genderid);
                         jQuery("#sale_idtype_buyer").val(databuyer.identitytypeid);
                         jQuery("#sale_marital_buyer").val(databuyer.maritalstatusid);*/

                    }



                    // Land Details landtype_r
                    jQuery("#sale_land_type").empty();
                    jQuery("#sale_country").empty();
                    jQuery("#sale_region").empty();
                    jQuery("#province_r").empty();
                    jQuery("#sale_land_Share_type").empty();
                    jQuery("#sale_province").empty();

                    jQuery("#sale_country").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_region").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_province").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_land_type").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));
                    jQuery("#sale_land_Share_type").append(
                            jQuery("<option></option>").attr("value", 0).text(
                            $.i18n("gen-please-select")));

                    jQuery.each(landtype_r,
                            function (i, obj1) {
                                jQuery("#sale_land_type").append(
                                        jQuery("<option></option>").attr(
                                        "value", obj1.landtypeid).text(
                                        obj1.landtypeEn));
                            });
                    jQuery.each(allcountry, function (i, obj) {
                        jQuery("#sale_country").append(
                                jQuery("<option></option>").attr("value",
                                obj.hierarchyid).text(obj.nameEn));
                    });

                    jQuery.each(region_r, function (i, obj) {
                        jQuery("#sale_region").append(
                                jQuery("<option></option>").attr("value",
                                obj.hierarchyid).text(obj.nameEn));
                    });
                    jQuery.each(province_r, function (i, obj) {
                        jQuery("#sale_province").append(
                                jQuery("<option></option>").attr("value",
                                obj.hierarchyid).text(obj.nameEn));
                    });

                    jQuery.each(landsharetype_r, function (i, obj) {
                        jQuery("#sale_land_Share_type").append(
                                jQuery("<option></option>").attr("value",
                                obj.landsharetypeid).text(
                                obj.landsharetypeEn));
                    });

                    jQuery("#sale_land_type").val(
                            laspatialunitland_R[0].landtypeid);
                    if (laspatialunitland_R[0].firstname != "") {
                        jQuery("#proposedUse_lease").val(laspatialunitland_R[0].firstname)
                        jQuery("#sale_proposeduse").val(laspatialunitland_R[0].firstname)
                        jQuery("#mortgage_proposed_use").val(laspatialunitland_R[0].firstname)
                    }
                    jQuery("#sale_country").val(
                            laspatialunitland_R[0].hierarchyid1);
                    jQuery("#sale_region").val(
                            laspatialunitland_R[0].hierarchyid2);
                    jQuery("#sale_province").val(
                            laspatialunitland_R[0].hierarchyid3);

                    jQuery("#parcel_r").val("000000" + laspatialunitland_R[0].landid);
                    jQuery("#sale_land_Share_type").val(
                            laspatialunitland_R[0].landusetypeid);
                    jQuery("#sale_area").val(laspatialunitland_R[0].area);
                    jQuery("#sale_land_use").val(
                            laspatialunitland_R[0].landusetype_en);



                    jQuery("#neighbor_west_sale").val(
                            laspatialunitland_R[0].neighbor_west);
                    jQuery("#neighbor_north_sale").val(
                            laspatialunitland_R[0].neighbor_north);
                    jQuery("#neighbor_south_sale").val(
                            laspatialunitland_R[0].neighbor_south);
                    jQuery("#neighbor_east_sale").val(
                            laspatialunitland_R[0].neighbor_east);





                }
            });
}




function editAttributeRegistration(transactionid, landid) {
//	 isVisible = $('#buyersavebutton').is(':visible');
    $(".signin_menu").hide();
    var lease = document.getElementById("Tab_1");
    var sale = document.getElementById("Tab_2");
    var mortgage = document.getElementById("Tab_3");
    var split = document.getElementById("Tab_4");
    lease.style.display = "none"; // lease.style.display = "block";
    sale.style.display = "none";
    mortgage.style.display = "none";
    split.style.display = "none";

    clearBuyerDetails_sale();

    var process = 0;
    $("#editflag").val(edit);

    $("#transactionId").val(transactionid);

    $("#registration_process").hide();

    finaltransid = transactionid;

    $("#buyersavebutton").prop("disabled", false).hide();
    selectedlandid = landid;
    $("#landidhide").val(landid);
    editPersonsOfEditingForEditing();
    editPersonsOfEditingForEditingLeasee();

    $("#RegLeasepersonsEditingGrid").hide();
    $("#editRegpersonsEditingGridLeasee").show();









    /*
     * jQuery.ajax({ url: "registration/landsharetype111111/" , async: false,
     * success: function (data) { landsharetype=data; alert('1');
     * if(data.length>0){
     *  } } });
     */

    jQuery.ajax({
        url: "registration/doctype/",
        async: false,
        success: function (data) {
            documenttype_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/monthoflease/",
        async: false,
        success: function (data) {
            monthoflease_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/financialagency/",
        async: false,
        success: function (data) {
            financialagency_R = data;
        }
    });

    jQuery.ajax({
        url: "registration/laspatialunitland/" + landid,
        async: false,
        success: function (data) {
            laspatialunitland_R = data;
            if (data.length > 0) {

            }
        }
    });

    jQuery.ajax({
        url: "registration/laMortgage/" + landid,
        async: false,
        success: function (data) {
            laMortgage_R = data;
            if (data.length > 0) {

            }
        }
    });

    jQuery.ajax({
        url: "registration/relationshiptypes/",
        async: false,
        success: function (data) {
            relationtypes_R = data;

        }
    });



    /*jQuery.ajax({
     type: "GET",
     url: "registration/landLeaseePOI/"+ landid,
     async : false,
     success : function(data) {
     
     
     jQuery("#POIRecordsRowDataLease1").empty();
     
     if(data.length >0){
     for(var i=0; i<data.length; i++){
     jQuery("#POIRecordsAttrTemplateLease1").tmpl(data[i]).appendTo("#POIRecordsRowDataLease1");
     }
     }
     
     }
     });*/

    $.ajax({
        url: "resource/relationshipTypes/",
        async: false,
        success: function (data1) {

            relationShips = data1;
        }
    });



    // -------------------------------------------------------------------------------------------------------------------
    // onchange of Country pass this select id for country_r_id --
    jQuery.ajax({
        url: "registration/allregion/" + country_r_id,
        async: false,
        success: function (data) {
            region_r = data;
            if (data.length > 0) {
                // data.xyz.name_en for getting the data
            }
        }
    });

    jQuery.ajax({
        url: "registration/allprovince/" + region_r_id,
        async: false,
        success: function (data) {
            province_r = data;
        }
    });


    jQuery("#registration_process").empty();
    jQuery("#registration_process").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));

    jQuery.each(processdetails_R, function (i, obj)
    {
        jQuery("#registration_process").append(jQuery("<option></option>").attr("value", obj.processid).text(obj.processname_en));

    });


    jQuery.ajax({
        url: "registration/processid/" + transactionid,
        async: false,
        success: function (data) {
            process = data.processid;
            jQuery("#registration_process").val(data.processid);
            process_id = process;


        }
    });

    $(function () {

        $("#Tab_1").hide();
        $("#Tab_2").hide();
        $("#Tab_3").hide();
//		var isVisible = $('#buyersavebutton').is(':visible')height : 600,width : 1000, ;
        attributeEditDialog = $("#lease-dialog-form").dialog({
            autoOpen: false,
            height: 700,
            width: 1000,
            resizable: true,
            modal: true,
            close: function () {
                attributeEditDialog.dialog("destroy");
                $("input,select,textarea").removeClass('addBg');
            },
            buttons: [{
                    text: "Save & Next",
                    "id": "comment_Next",
                    click: function ()
                    {
                        $("input,select,textarea").removeClass('addBg');
                        if (currentdiv == "Sale")
                        {
                            //var selectedtab = document.getElementsByClassName("aria-selected");

                            if ($('#Seller_Details').css('display') == 'block')
                            {
                                $("#Seller_Details").hide();
                                $("#Land_Details_Sale").show();
                                $("#Buyer_Details").hide();
                                $("#Upload_Document_Sale").hide();
                                $("#selectedseller").removeClass("ui-tabs-active");
                                $("#selectedseller").removeClass("ui-state-active");
                                $("#selectedland").addClass("ui-tabs-active");
                                $("#selectedland").addClass("ui-state-active");
                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Land_Details_Sale').css('display') == 'block')
                            {
                                $("#Buyer_Details").show();
                                $("#Seller_Details").hide();
                                $("#Land_Details_Sale").hide();
                                $("#Upload_Document_Sale").hide();

                                $("#selectedland").removeClass("ui-tabs-active");
                                $("#selectedland").removeClass("ui-state-active");
                                $("#selectedbuyer").addClass("ui-tabs-active");
                                $("#selectedbuyer").addClass("ui-state-active");



                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Buyer_Details').css('display') == 'block')
                            {
//							savebuyerdetails();
                                if (null != arry_Buyerbyprocessid) {
                                    $("#Seller_Details").hide();
                                    $("#Buyer_Details").hide();
                                    $("#Land_Details_Sale").hide();
                                    $("#regPoi").show();
                                    $("#Upload_Document_Sale").hide();
                                    $("#selectedbuyer").removeClass("ui-tabs-active");
                                    $("#selectedbuyer").removeClass("ui-state-active");
                                    $("#selectedpoi").addClass("ui-tabs-active");
                                    $("#selectedpoi").addClass("ui-state-active");
                                    $("#comment_Save").hide();
                                    $("#comment_Next").show();
                                } else {

                                    jAlert($.i18n("err-save-buyer"), $.i18n("gen-info"));
                                }
                            } else if ($('#regPoi').css('display') == 'block')
                            {

                                $("#Seller_Details").hide();
                                $("#Buyer_Details").hide();
                                $("#Land_Details_Sale").hide();
                                $("#regPoi").hide();
                                $("#Upload_Document_Sale").show();
                                $("#selectedpoi").removeClass("ui-tabs-active");
                                $("#selectedpoi").removeClass("ui-state-active");
                                $("#selecteddoc").addClass("ui-tabs-active");
                                $("#selecteddoc").addClass("ui-state-active");
                                $("#comment_Save").show();
                                $("#comment_Next").hide();
                            }
                        } else if (currentdiv == "Lease")
                        {
                            if ($('#Owner_Details').css('display') == 'block')
                            {
                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").show();
                                $("#Applicant_Details").hide();
                                $("#Lease_Details").hide();
                                $("#regLeasePoi").hide();
                                $("#Upload_Documents_Lease").hide();
                                $("#selectedowner").removeClass("ui-tabs-active");
                                $("#selectedowner").removeClass("ui-state-active");
                                $("#selectedLanddetails").addClass("ui-tabs-active");
                                $("#selectedLanddetails").addClass("ui-state-active");



                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Land_Details_lease').css('display') == 'block')
                            {
                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#Applicant_Details").show();
                                $("#Lease_Details").hide();
                                $("#regLeasePoi").hide();
                                $("#Upload_Documents_Lease").hide();
                                $("#selectedLanddetails").removeClass("ui-tabs-active");
                                $("#selectedLanddetails").removeClass("ui-state-active");
                                $("#selectedApplicant").addClass("ui-tabs-active");
                                $("#selectedApplicant").addClass("ui-state-active");


                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Applicant_Details').css('display') == 'block')
                            {
//							saveattributesLeasePersonData();
                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#regLeasePoi").show();
                                $("#Lease_Details").hide();
                                $("#Applicant_Details").hide();

                                $("#Upload_Documents_Lease").hide();
                                $("#selectedApplicant").removeClass("ui-tabs-active");
                                $("#selectedApplicant").removeClass("ui-state-active");

                                $("#selectedLeasepoi").addClass("ui-tabs-active");
                                $("#selectedLeasepoi").addClass("ui-state-active");




                                $("#comment_Save").hide();
                                $("#comment_Next").show();
                            } else if ($('#regLeasePoi').css('display') == 'block')
                            {



                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#Applicant_Details").hide();
                                $("#Lease_Details").show();
                                $("#regLeasePoi").hide();

                                $("#Upload_Documents_Lease").hide();
                                $("#selectedLeasepoi").removeClass("ui-tabs-active");
                                $("#selectedLeasepoi").removeClass("ui-state-active");
                                $("#selectedsleasedetails").addClass("ui-tabs-active");
                                $("#selectedsleasedetails").addClass("ui-state-active");




                                $("#comment_Save").hide();
                                $("#comment_Next").show();
                            } else if ($('#Lease_Details').css('display') == 'block')
                            {

                                if (processid == "5")
                                {
                                    saveattributesSurrenderLease();
                                } else
                                {
                                    saveattributesLeaseDetails();
                                }

                                $("#Owner_Details").hide();
                                $("#Land_Details_lease").hide();
                                $("#Applicant_Details").hide();







                                $("#comment_Save").show();
                                $("#comment_Next").hide();
                            }

                        } else if (currentdiv == "Mortgage")
                        {

                            if ($('#MortgageOwner_Details').css('display') == 'block')
                            {
                                $("#MortgageOwner_Details").hide();
                                $("#Land_Details_Mortgage").show();
                                $("#Mortgage_Details").hide();
                                $("#Upload_Document_Mortgage").hide();
                                $("#selectedownerdetails").removeClass("ui-tabs-active");
                                $("#selectedownerdetails").removeClass("ui-state-active");
                                $("#selectelandmortgage").addClass("ui-tabs-active");
                                $("#selectelandmortgage").addClass("ui-state-active");
                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Land_Details_Mortgage').css('display') == 'block')
                            {
                                $("#MortgageOwner_Details").hide();
                                $("#Land_Details_Mortgage").hide();
                                $("#Mortgage_Details").show();
                                $("#Upload_Document_Mortgage").hide();
                                $("#selectelandmortgage").removeClass("ui-tabs-active");
                                $("#selectelandmortgage").removeClass("ui-state-active");
                                $("#selectemortgage").addClass("ui-tabs-active");
                                $("#selectemortgage").addClass("ui-state-active");



                                $("#comment_Save").hide();
                                $("#comment_Next").show();

                            } else if ($('#Mortgage_Details').css('display') == 'block')
                            {

                                if (processid == "9")
                                {
                                    saveattributesSurrenderMortagage();
                                } else
                                {
                                    saveMortgage();
                                }

                                $("#MortgageOwner_Details").hide();
                                $("#Land_Details_Mortgage").hide();
                                $("#Mortgage_Details").hide();
                                $("#Upload_Document_Mortgage").show();
                                $("#selectemortgage").removeClass("ui-tabs-active");
                                $("#selectemortgage").removeClass("ui-state-active");
                                $("#selectemortgagedocs").addClass("ui-tabs-active");
                                $("#selectemortgagedocs").addClass("ui-state-active");
                                $("#comment_Save").show();
                                $("#comment_Next").hide();
                            }

                        } else if (currentdiv == "split") {
                            $("#comment_Save").hide();
                            $("#comment_Next").show();
                            attributeEditDialog.dialog("close");
                            showOnMap(selectedlandid, 0, "split");
                        }
                    }

                },
                {
                    text: $.i18n("gen-save"),
                    "id": "comment_Save",
                    click: function ()
                    {

//					$("#buyersavebutton").prop("disabled", false).hide();
//					 $("input,select,textarea").removeClass('addBg');
                        if (currentdiv == "Sale")
                        {
                            saveattributessale();

                        } else if (currentdiv == "Lease")
                        {

                            saveattributesLease();
                        } else
                        {
                            saveattributesMortagage()

                        }
                    }

                },
                {
                    text: $.i18n("gen-cancel"),
                    "id": "comment_cancel",
                    click: function () {
                        $("input,select,textarea").removeClass('addBg');
                        setInterval(function () {

                        }, 4000);

                        attributeEditDialog.dialog("close");

                    }

                }


            ],
            Cancel: function () {

                attributeEditDialog.dialog("close");
                $("input,select,textarea").removeClass('addBg');

            }
        });
        $("#comment_cancel").html('<span class="ui-button-text">Cancel</span>');
        attributeEditDialog.dialog("open");
        $("#comment_Save").hide();
        $("#comment_Next").hide();

    });

//	$("#Seller_Details").hide();
//	$("#Land_Details_Sale").hide();				
//	$("#Owner_Details").hide();
//	$("#Land_Details_lease").hide();							
//	$("#MortgageOwner_Details").hide();
//	$("#Land_Details_Mortgage").hide();

    if (process == 2 || process == 4 || process == 6 || process == 7) {
        getprocessvalue(2);
        SaleOwnerdBuyeretails(landid);
        salebuyerdetails(process);

    } else if (process == 1 || process == 5) {
        getprocessvalue(1);
        SaleOwnerdBuyeretails(landid);
        fillSurrenderLeaseDetails(landid);


    } else if (process == 3 || process == 9) {
        getprocessvalue(3);
        SaleOwnerdBuyeretails(landid);
        fillMortgageDetails(landid);


    }

    jQuery.ajax({
        type: 'GET',
        url: "registryrecords/editDocuments/" + landid + "/" + transactionid,
        async: false,
        success: function (data) {
            if (data != null && data != "")
            {
                $("#salesDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#salesDocRowData");
                $("#salesDocRowData").i18n();
                
                $("#LeaseDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#LeaseDocRowData");
                $("#LeaseDocRowData").i18n();
                
                $("#MortagageDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#MortagageDocRowData");
                $("#MortagageDocRowData").i18n();
            }

        }
    });

    jQuery.ajax({
        url: "registration/financialagency/",
        async: false,
        success: function (data) {
            financialagency_R = data;
        }
    });

    jQuery("#mortgage_to").val("");
    jQuery("#mortgage_from").val("");
    jQuery("#mortgage_Financial_Agencies").empty();
    jQuery("#amount_mortgage").val("");

    jQuery("#mortgage_Financial_Agencies").append(
            jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery.each(financialagency_R, function (i, obj1) {
        jQuery("#mortgage_Financial_Agencies").append(
                jQuery("<option></option>").attr("value",
                obj1.financialagencyid).text(obj1.financialagency_en));
    });

    jQuery.ajax({
        url: "registration/editlaMortgage/" + landid + "/" + transactionid,
        async: false,
        success: function (data) {
            laMortgage_R = data;
            if (laMortgage_R != null) {


                if (laMortgage_R != "" && laMortgage_R != null)
                {
                    if (laMortgage_R.laExtFinancialagency != null && laMortgage_R.laExtFinancialagency != "")
                    {
                        jQuery("#mortgage_Financial_Agencies").val(laMortgage_R.laExtFinancialagency.financialagencyid);
                    }

                }

                if (laMortgage_R.mortgagefrom != null || laMortgage_R.mortgagefrom != "") {
                    jQuery("#mortgage_from").val(formatDate_R(laMortgage_R.mortgagefrom));
                } else {
                    jQuery("#mortgage_from").val("");
                }
                if (laMortgage_R.mortgageto != null || laMortgage_R.mortgageto != "") {
                    jQuery("#mortgage_to").val(formatDate_R(laMortgage_R.mortgageto));
                } else {
                    jQuery("#mortgage_to").val("");
                }
                jQuery("#amount_mortgage").val(laMortgage_R.mortgageamount);


            }
        }
    });

    jQuery.ajax({
        type: "GET",
        url: "landrecords/landPOI/" + finaltransid + "/" + landid,
        async: false,
        success: function (data) {


            jQuery("#BuyerPOIRecordsRowDataSale").empty();
            jQuery("#POIRecordsRowDataMortgage1").empty();
            jQuery("#BuyerPOIRecordsRowDataLease").empty();


            if (null != data || data != "") {
                for (var i = 0; i < data.length; i++) {

                    var relation = "";
                    var gender = "";
                    for (var j = 0; j < relationtypes_R.length; j++) {

                        if (data[i].relation == relationtypes_R[j].relationshiptypeid) {

                            relation = relationtypes_R[j].relationshiptype;
                        }
                    }

                    for (var k = 0; k < genderStatus_R.length; k++) {

                        if (genderStatus_R[k].genderId == data[i].gender) {

                            gender = genderStatus_R[k].gender;
                        }
                    }

                    data[i].relation = relation;
                    data[i].gender = gender;
                    jQuery("#BuyerPOIRecordsAttrTemplateSale").tmpl(data[i]).appendTo("#BuyerPOIRecordsRowDataSale");
                    $("#BuyerPOIRecordsRowDataSale").i18n();
                    jQuery("#POIRecordsAttrTemplateMortgage1").tmpl(data[i]).appendTo("#POIRecordsRowDataMortgage1");
                    jQuery("#BuyerPOIRecordsAttrTemplateLease").tmpl(data[i]).appendTo("#BuyerPOIRecordsRowDataLease");
                    $("#BuyerPOIRecordsRowDataLease").i18n();
                }


            }




        }
    });


    jQuery("#Relationship_sale_POI").empty();
    jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery.each(relationtypes_R, function (i, obj)
    {
        jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
    });

    jQuery("#gender_sale_POI").empty();
    jQuery("#gender_sale_POI").append(
            jQuery("<option></option>").attr("value", 0).text(
            $.i18n("gen-please-select")));
    jQuery.each(genderStatus_R, function (i, obj1) {
        jQuery("#gender_sale_POI").append(
                jQuery("<option></option>").attr("value",
                obj1.genderId).text(obj1.gender_en));
    });

    jQuery("#Relationship_POI").empty();
    jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
    jQuery.each(relationtypes_R, function (i, obj)
    {
        jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
    });

    jQuery("#gender_type_POI").empty();
    jQuery("#gender_type_POI").append(
            jQuery("<option></option>").attr("value", 0).text(
            $.i18n("gen-please-select")));
    jQuery.each(genderStatus_R, function (i, obj1) {
        jQuery("#gender_type_POI").append(
                jQuery("<option></option>").attr("value",
                obj1.genderId).text(obj1.gender_en));
    });


}





function editPersonsOfEditingForEditing() {
    $("#editRegpersonsEditingGrid").jsGrid({
        width: "100%",
        height: "200px",
        inserting: false,
        editing: true,
        sorting: false,
        filtering: false,
        paging: true,
        autoload: false,
        controller: editRegpersonsEditingController,
        pageSize: 50,
        pageButtonCount: 20,
        fields: [
            {type: "control", deleteButton: false},
            {name: "firstName", title: $.i18n("reg-firstname"), type: "text", width: 210, validate: {validator: "required", message: $.i18n("err-select-firstname")}},
            {name: "middleName", title: $.i18n("reg-middlename"), type: "text", width: 210, validate: {validator: "required", message: $.i18n("err-enter-middle-name")}},
            {name: "lastName", title: $.i18n("reg-lastname"), type: "text", width: 210, validate: {validator: "required", message: $.i18n("err-select-lastname")}},
            {name: "gender", title: $.i18n("reg-gender"), type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}, {id: 3, name: "Other"}], valueField: "id", textField: "name", width: 210, validate: {validator: "required", message: $.i18n("err-select-gender")}},
            {name: "dob", title: $.i18n("reg-dob"), type: "date", width: 210, validate: {validator: "required", message: $.i18n("err-enter-dob")}},
            {name: "relation", title: $.i18n("reg-relation"), type: "select", items: [{id: 1, name: "Spouse"}, {id: 2, name: "Son"}, {id: 3, name: "Daughter"}, {id: 4, name: "Grandson"}, {id: 5, name: "Granddaughter"}, {id: 6, name: "Brother"},
                    {id: 7, name: "Sister"}, {id: 8, name: "Father"}, {id: 9, name: "Mother"}, {id: 10, name: "Grandmother"}, {id: 11, name: "Grandfather"}, {id: 12, name: "Aunt"},
                    {id: 13, name: "Uncle"}, {id: 14, name: "Niece"}, {id: 15, name: "Nephew"}, {id: 16, name: "Other"}, {id: 17, name: "Other relatives"}, {id: 18, name: "Associate"},
                    {id: 19, name: "Parents and children"}, {id: 20, name: "Siblings"}], valueField: "id", textField: "name", width: 210, validate: {validator: "required", message: $.i18n("err-select-relation-type")}},
            {name: "landid", title: $.i18n("reg-landid"), type: "number", width: 70, align: "left", editing: false, filtering: true, visible: false},
            {name: "id", title: $.i18n("reg-id"), type: "number", width: 70, align: "left", editing: false, filtering: true, visible: false}
        ]
    });
    $("#editRegpersonsEditingGrid .jsgrid-table th:first-child :button").click();
    $("#editRegpersonsEditingGrid").jsGrid("loadData");
}

var editRegpersonsEditingController = {
    loadData: function (filter) {
        $("#btnLoadPersons").val("Reload");
        return $.ajax({
            type: "GET",
            url: "landrecords/editlandPOIBuyer/" + selectedlandid + "/" + finaltransid,
            data: filter,
            success: function (data) {
                if (data == "" || data == null) {





                }
            }
        });
    },
    insertItem: function (item) {

    },
    updateItem: function (item) {

        var ajaxdata = {
            "firstName": item.firstName,
            "middleName": item.middleName,
            "lastName": item.lastName,
            "gender": item.gender,
            "dob": item.dob,
            "relation": item.relation,
            "id": item.id

        }

        return $.ajax({
            type: "POST",
//           contentType: "application/json; charset=utf-8",
            traditional: true,
            url: "landrecords/editRegPersonOfInterestForEditing/" + selectedlandid + "/" + finaltransid,
            data: ajaxdata,
            async: false,
            success: function (data) {
                if (data == "" || data == null) {
                    editPersonsOfEditingForEditing();
                    jAlert($.i18n("err-add-buyer-toadd-poi"));

                } else {
                    editPersonsOfEditingForEditing();
                    jAlert($.i18n("reg-poi-added"), $.i18n("gen-info"));
                }
            },
            error: function (request, textStatus, errorThrown) {
                jAlert(request.responseText);
            }
        });
    },
    deleteItem: function (item) {
        return false;
    }
};

function addRegPOI() {
    if (edit == 1) {

        $("#editRegpersonsEditingGrid").jsGrid("insertItem", {});
    } else if (edit == 0) {
        $.ajax({
            type: "GET",
            url: "landrecords/landPOIstatus/" + selectedlandid + "/" + processid,
            data: filter,
            success: function (data) {

                alertmessage = data;

                if (alertmessage == "true") {
                    $("#RegpersonsEditingGrid").jsGrid("insertItem", {});
                } else {
                    jAlert(alertmessage, $.i18n("gen-info"));
                }
            }
        });


    }

}







function editPersonsOfEditingForEditingLeasee() {
    $("#editRegpersonsEditingGridLeasee").jsGrid({
        width: "100%",
        height: "200px",
        inserting: false,
        editing: true,
        sorting: false,
        filtering: false,
        paging: true,
        autoload: false,
        controller: editRegpersonsEditingLeaseeController,
        pageSize: 50,
        pageButtonCount: 20,
        fields: [
            {type: "control", deleteButton: false},
            {name: "firstName", title: $.i18n("reg-firstname"), type: "text", width: 210, validate: {validator: "required", message: $.i18n("err-select-firstname")}},
            {name: "middleName", title: $.i18n("reg-middlename"), type: "text", width: 210, validate: {validator: "required", message: $.i18n("err-select-middle-name")}},
            {name: "lastName", title: $.i18n("reg-lastname"), type: "text", width: 210, validate: {validator: "required", message: $.i18n("err-select-lastname")}},
            {name: "gender", title: $.i18n("reg-gender"), type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}, {id: 3, name: "Other"}], valueField: "id", textField: "name", width: 210, validate: {validator: "required", message: $.i18n("err-select-gender")}},
            {name: "dob", title: $.i18n("reg-dob"), type: "date", width: 210, validate: {validator: "required", message: $.i18n("err-enter-dob")}},
            {name: "relation", title: $.i18n("reg-relation"), type: "select", items: [{id: 1, name: "Spouse"}, {id: 2, name: "Son"}, {id: 3, name: "Daughter"}, {id: 4, name: "Grandson"}, {id: 5, name: "Granddaughter"}, {id: 6, name: "Brother"},
                    {id: 7, name: "Sister"}, {id: 8, name: "Father"}, {id: 9, name: "Mother"}, {id: 10, name: "Grandmother"}, {id: 11, name: "Grandfather"}, {id: 12, name: "Aunt"},
                    {id: 13, name: "Uncle"}, {id: 14, name: "Niece"}, {id: 15, name: "Nephew"}, {id: 16, name: "Other"}, {id: 17, name: "Other relatives"}, {id: 18, name: "Associate"},
                    {id: 19, name: "Parents and children"}, {id: 20, name: "Siblings"}], valueField: "id", textField: "name", width: 210, validate: {validator: "required", message: $.i18n("err-enter-relation")}},
            {name: "landid", title: $.i18n("reg-landid"), type: "number", width: 70, align: "left", editing: false, filtering: true, visible: false},
            {name: "id", title: $.i18n("reg-id"), type: "number", width: 70, align: "left", editing: false, filtering: true, visible: false},
        ]
    });
    $("#editRegpersonsEditingGridLeasee .jsgrid-table th:first-child :button").click();
    $("#editRegpersonsEditingGridLeasee").jsGrid("loadData");
}

var editRegpersonsEditingLeaseeController = {
    loadData: function (filter) {
        $("#btnLoadPersons").val("Reload");
        return $.ajax({
            type: "GET",
            url: "landrecords/editlandPOIBuyer/" + selectedlandid + "/" + finaltransid,
            data: filter,
            success: function (data) {
                if (data == "" || data == null) {





                }
            }
        });
    },
    insertItem: function (item) {

    },
    updateItem: function (item) {

        var ajaxdata = {
            "firstName": item.firstName,
            "middleName": item.middleName,
            "lastName": item.lastName,
            "gender": item.gender,
            "dob": item.dob,
            "relation": item.relation,
            "id": item.id

        }

        return $.ajax({
            type: "POST",
//          contentType: "application/json; charset=utf-8",
            traditional: true,
            url: "landrecords/editRegPersonOfInterestForEditing/" + selectedlandid + "/" + finaltransid,
            data: ajaxdata,
            async: false,
            success: function (data) {
                if (data == "" || data == null) {
                    editPersonsOfEditingForEditingLeasee();
                    jAlert($.i18n("err-add-buyer-toadd-poi"));

                } else {
                    editPersonsOfEditingForEditingLeasee();
                    jAlert($.i18n("reg-poi-added"), $.i18n("gen-info"));
                }
            },
            error: function (request, textStatus, errorThrown) {
                jAlert(request.responseText);
            }
        });
    },
    deleteItem: function (item) {
        return false;
    }
};





function addRegLeasePOI() {
    if (edit == 1) {

        $("#editRegpersonsEditingGridLeasee").jsGrid("insertItem", {});
    } else if (edit == 0) {
        $.ajax({
            type: "GET",
            url: "registrion/addLeaseePoi/" + selectedlandid,
            data: filter,
            success: function (data) {

                alertmessage = data;

                if (alertmessage == "true") {
                    $("#RegLeasepersonsEditingGrid").jsGrid("insertItem", {});
                } else {
                    jAlert(alertmessage, $.i18n("gen-info"));
                }
            }
        });

    }


}

function fetchDocEdit() {

    jQuery.ajax({
        type: 'GET',
        url: "registryrecords/editDocuments/" + selectedlandid + "/" + finaltransid,
        async: false,
        success: function (data) {
            if (data != null && data != "")
            {
                $("#salesDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#salesDocRowData");
                $("#salesDocRowData").i18n();
                
                $("#LeaseDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#LeaseDocRowData");
                $("#LeaseDocRowData").i18n();

                $("#MortagageDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#MortagageDocRowData");
                $("#MortagageDocRowData").i18n();
            }

        }
    });
}






function loadResleaseePersonsOfEditingForEditing() {

    if (edit == 0)
    {
        $.ajax({
            type: "GET",
            url: "landrecords/landPOILeasee/" + selectedlandid,
            data: filter,
            success: function (data) {
                $('#BuyerPOIRecordsRowDataLease').empty();

                jQuery("#Relationship_POI").empty();
                jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                jQuery.each(relationtypes_R, function (i, obj)
                {
                    jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                });

                jQuery("#gender_type_POI").empty();
                jQuery("#gender_type_POI").append(
                        jQuery("<option></option>").attr("value", 0).text(
                        $.i18n("gen-please-select")));
                jQuery.each(genderStatus_R, function (i, obj1) {
                    jQuery("#gender_type_POI").append(
                            jQuery("<option></option>").attr("value",
                            obj1.genderId).text(obj1.gender_en));
                });


                if (null != data || data != "") {
                    for (var i = 0; i < data.length; i++) {
                        var relation = "";
                        var gender = "";
                        for (var j = 0; j < relationtypes_R.length; j++) {

                            if (data[i].relation == relationtypes_R[j].relationshiptypeid) {

                                relation = relationtypes_R[j].relationshiptype;
                            }
                        }

                        for (var k = 0; k < genderStatus_R.length; k++) {

                            if (genderStatus_R[k].genderId == data[i].gender) {

                                gender = genderStatus_R[k].gender;
                            }
                        }

                        data[i].relation = relation;
                        data[i].gender = gender;
                        $('#BuyerPOIRecordsAttrTemplateLease').tmpl(data[i]).appendTo('#BuyerPOIRecordsRowDataLease');
                        $("#BuyerPOIRecordsRowDataLease").i18n();
                    }


                }
            }
        });
    } else if (edit == 1) {

        jQuery.ajax({
            type: "GET",
            url: "landrecords/landPOI/" + finaltransid + "/" + landid,
            async: false,
            success: function (data) {


                jQuery("#BuyerPOIRecordsRowDataSale").empty();
                jQuery("#POIRecordsRowDataMortgage1").empty();
                jQuery("#BuyerPOIRecordsRowDataLease").empty();


                if (null != data || data != "") {
                    for (var i = 0; i < data.length; i++) {

                        var relation = "";
                        var gender = "";
                        for (var j = 0; j < relationtypes_R.length; j++) {

                            if (data[i].relation == relationtypes_R[j].relationshiptypeid) {

                                relation = relationtypes_R[j].relationshiptype;
                            }
                        }

                        for (var k = 0; k < genderStatus_R.length; k++) {

                            if (genderStatus_R[k].genderId == data[i].gender) {

                                gender = genderStatus_R[k].gender;
                            }
                        }

                        data[i].relation = relation;
                        data[i].gender = gender;
                        jQuery("#BuyerPOIRecordsAttrTemplateSale").tmpl(data[i]).appendTo("#BuyerPOIRecordsRowDataSale");
                        $("#BuyerPOIRecordsRowDataSale").i18n();
                        jQuery("#POIRecordsAttrTemplateMortgage1").tmpl(data[i]).appendTo("#POIRecordsRowDataMortgage1");
                        jQuery("#BuyerPOIRecordsAttrTemplateLease").tmpl(data[i]).appendTo("#BuyerPOIRecordsRowDataLease");
                        $("#BuyerPOIRecordsRowDataLease").i18n();
                    }


                }




            }
        });
    }

}




function editLeaseePOI(id) {

    $.ajax({
        type: "GET",
        url: "registration/getPOIbyId/" + id,
        data: filter,
        success: function (data) {

            if (data != "") {
                jQuery("#Relationship_POI").empty();
                jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                jQuery.each(relationtypes_R, function (i, obj)
                {
                    jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                });

                jQuery("#gender_type_POI").empty();
                jQuery("#gender_type_POI").append(
                        jQuery("<option></option>").attr("value", 0).text(
                        $.i18n("gen-please-select")));
                jQuery.each(genderStatus_R, function (i, obj1) {
                    jQuery("#gender_type_POI").append(
                            jQuery("<option></option>").attr("value",
                            obj1.genderId).text(obj1.gender_en));
                });
                $('#firstname_r_poi').val(data.firstName);
                $('#middlename_r_poi').val(data.middleName);
                $('#lastname_r_poi').val(data.lastName);
                $('#gender_type_POI').val(data.gender);
                $('#Relationship_POI').val(data.relation);
                $('#date_Of_birthPOI').val(data.dob);
                $('#leaseepoiid').val(id);





            }
        }

    });

}


function saveattributesLeasePOIData() {

    if ($("#editprocessAttributeformID").valid()) {

        if ((firstname_r_poi.value.length == 0)
                || (middlename_r_poi.value.length == 0)
                || (lastname_r_poi.value.length == 0)
                || (gender_type_POI.value.length == 0)
                || (Relationship_POI.value.length == 0)
                || (date_Of_birthPOI.value.length == 0)) {// (no_Of_years_Lease.value.length
            // == 0) ||
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        }

        jConfirm(
                $.i18n("gen-save-confirmation"),
                $.i18n("gen-confirmation"),
                function (response) {
                    if (response && edit == 0) {
                        $.ajax({
                            type: "GET",
                            url: "registrion/addLeaseePoi/" + selectedlandid,
                            data: filter,
                            success: function (data) {

                                alertmessage = data;

                                if (alertmessage == "true") {
                                    $.ajax({
                                        type: "POST",
//         		    contentType: "application/json; charset=utf-8",
                                        traditional: true,
                                        url: "registration/saveRegPersonOfInterestForLeasee/" + selectedlandid,
                                        data: jQuery("#editprocessAttributeformID")
                                                .serialize(),
                                        async: false,
                                        success: function (data) {
                                            if (null != data && data != "") {
                                                jQuery("#Relationship_POI").empty();
                                                jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                                                jQuery.each(relationtypes_R, function (i, obj)
                                                {
                                                    jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                                                });

                                                jQuery("#gender_type_POI").empty();
                                                jQuery("#gender_type_POI").append(
                                                        jQuery("<option></option>").attr("value", 0).text(
                                                        $.i18n("gen-please-select")));
                                                jQuery.each(genderStatus_R, function (i, obj1) {
                                                    jQuery("#gender_type_POI").append(
                                                            jQuery("<option></option>").attr("value",
                                                            obj1.genderId).text(obj1.gender_en));
                                                });
                                                $('#firstname_r_poi').val("");
                                                $('#middlename_r_poi').val("");
                                                $('#lastname_r_poi').val("");

                                                $('#date_Of_birthPOI').val("");
                                                $('#leaseepoiid').val("");
                                                loadResleaseePersonsOfEditingForEditing();

                                            }
                                        }
                                    });
                                } else {
                                    jAlert(alertmessage, $.i18n("gen-info"));
                                }
                            }
                        });
                    } else if (response && edit == 1) {
                        $.ajax({
                            type: "POST",
//		         		    contentType: "application/json; charset=utf-8",
                            traditional: true,
                            url: "registration/saveRegPersonOfInterestForLeasee/" + finaltransid,
                            data: jQuery("#editprocessAttributeformID")
                                    .serialize(),
                            async: false,
                            success: function (data) {
                                if (null != data && data != "") {
                                    jQuery("#Relationship_POI").empty();
                                    jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                                    jQuery.each(relationtypes_R, function (i, obj)
                                    {
                                        jQuery("#Relationship_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                                    });

                                    jQuery("#gender_type_POI").empty();
                                    jQuery("#gender_type_POI").append(
                                            jQuery("<option></option>").attr("value", 0).text(
                                            $.i18n("gen-please-select")));
                                    jQuery.each(genderStatus_R, function (i, obj1) {
                                        jQuery("#gender_type_POI").append(
                                                jQuery("<option></option>").attr("value",
                                                obj1.genderId).text(obj1.gender_en));
                                    });
                                    $('#firstname_r_poi').val("");
                                    $('#middlename_r_poi').val("");
                                    $('#lastname_r_poi').val("");

                                    $('#date_Of_birthPOI').val("");
                                    $('#leaseepoiid').val("");
                                    loadResleaseePersonsOfEditingForEditing();

                                }
                            }
                        });
                    }
                });
    }
}


function editSalePOI(id) {

    $.ajax({
        type: "GET",
        url: "registration/getPOIbyId/" + id,
        data: filter,
        success: function (data) {

            if (data != "") {
                jQuery("#Relationship_sale_POI").empty();
                jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                jQuery.each(relationtypes_R, function (i, obj)
                {
                    jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                });

                jQuery("#gender_sale_POI").empty();
                jQuery("#gender_sale_POI").append(
                        jQuery("<option></option>").attr("value", 0).text(
                        $.i18n("gen-please-select")));
                jQuery.each(genderStatus_R, function (i, obj1) {
                    jQuery("#gender_sale_POI").append(
                            jQuery("<option></option>").attr("value",
                            obj1.genderId).text(obj1.gender_en));
                });
                $('#firstname_sale_poi').val(data.firstName);
                $('#middlename_sale_poi').val(data.middleName);
                $('#lastname_sale_poi').val(data.lastName);
                $('#gender_sale_POI').val(data.gender);
                $('#Relationship_sale_POI').val(data.relation);
                $('#date_Of_birthPOI_sale').val(data.dob);
                $('#leaseepoiid').val(id);





            }
        }

    });

}

function loadResPersonsOfEditingForEditing() {


    if (edit == 0) {

        $.ajax({
            type: "GET",
            url: "landrecords/landPOIBuyer/" + selectedlandid + "/" + processid,
            data: filter,
            success: function (data) {
                $('#BuyerPOIRecordsRowDataSale').empty();

                jQuery("#Relationship_sale_POI").empty();
                jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                jQuery.each(relationtypes_R, function (i, obj)
                {
                    jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                });

                jQuery("#gender_sale_POI").empty();
                jQuery("#gender_sale_POI").append(
                        jQuery("<option></option>").attr("value", 0).text(
                        $.i18n("gen-please-select")));
                jQuery.each(genderStatus_R, function (i, obj1) {
                    jQuery("#gender_sale_POI").append(
                            jQuery("<option></option>").attr("value",
                            obj1.genderId).text(obj1.gender_en));
                });


                if (null != data || data != "") {
                    for (var i = 0; i < data.length; i++) {

                        var relationship = "";
                        var gender = "";
                        for (var j = 0; j < relationtypes_R.length; j++) {

                            if (data[i].relation == relationtypes_R[j].relationshiptypeid) {

                                relationship = relationtypes_R[j].relationshiptype;
                            }
                        }

                        for (var k = 0; k < genderStatus_R.length; k++) {

                            if (genderStatus_R[k].genderId == data[i].gender) {

                                gender = genderStatus_R[k].gender;
                            }
                        }

                        data[i].relation = relationship;
                        data[i].gender = gender;
                        $('#BuyerPOIRecordsAttrTemplateSale').tmpl(data[i]).appendTo('#BuyerPOIRecordsRowDataSale');
                        $("#BuyerPOIRecordsRowDataSale").i18n();
                    }


                }
            }
        });

    } else if (edit == 1) {

        jQuery.ajax({
            type: "GET",
            url: "landrecords/landPOI/" + finaltransid + "/" + landid,
            async: false,
            success: function (data) {


                jQuery("#BuyerPOIRecordsRowDataSale").empty();
                jQuery("#POIRecordsRowDataMortgage1").empty();
                jQuery("#BuyerPOIRecordsRowDataLease").empty();


                if (null != data || data != "") {
                    for (var i = 0; i < data.length; i++) {

                        var relation = "";
                        var gender = "";
                        for (var j = 0; j < relationtypes_R.length; j++) {

                            if (data[i].relation == relationtypes_R[j].relationshiptypeid) {

                                relation = relationtypes_R[j].relationshiptype;
                            }
                        }

                        for (var k = 0; k < genderStatus_R.length; k++) {

                            if (genderStatus_R[k].genderId == data[i].gender) {

                                gender = genderStatus_R[k].gender;
                            }
                        }

                        data[i].relation = relation;
                        data[i].gender = gender;
                        jQuery("#BuyerPOIRecordsAttrTemplateSale").tmpl(data[i]).appendTo("#BuyerPOIRecordsRowDataSale");
                        $("#BuyerPOIRecordsRowDataSale").i18n();
                        jQuery("#POIRecordsAttrTemplateMortgage1").tmpl(data[i]).appendTo("#POIRecordsRowDataMortgage1");
                        jQuery("#BuyerPOIRecordsAttrTemplateLease").tmpl(data[i]).appendTo("#BuyerPOIRecordsRowDataLease");
                        $("#BuyerPOIRecordsRowDataLease").i18n();
                    }


                }




            }
        });
    }




}




function saveattributesSalePOIData() {

    if ($("#editprocessAttributeformID").valid()) {

        if ((firstname_sale_poi.value.length == 0)
                || (middlename_sale_poi.value.length == 0)
                || (lastname_sale_poi.value.length == 0)
                || (gender_sale_POI.value.length == 0)
                || (Relationship_sale_POI.value.length == 0)
                || (date_Of_birthPOI_sale.value.length == 0)) {// (no_Of_years_Lease.value.length
            // == 0) ||
            jAlert($.i18n("err-fill-details"), $.i18n("err-alert"));
            return false;
        }

        jConfirm(
                $.i18n("gen-save-confirmation"),
                $.i18n("gen-confirmation"),
                function (response) {
                    if (response && edit == 0) {
                        $.ajax({
                            type: "GET",
                            url: "landrecords/landPOIstatus/" + selectedlandid + "/" + processid,
                            data: filter,
                            success: function (data) {

                                alertmessage = data;

                                if (alertmessage == "true") {
                                    $.ajax({
                                        type: "POST",
//         		    contentType: "application/json; charset=utf-8",
                                        traditional: true,
                                        url: "landrecords/saveRegPersonOfInterestForEditing/" + selectedlandid + "/" + processid,
                                        data: jQuery("#editprocessAttributeformID")
                                                .serialize(),
                                        async: false,
                                        success: function (data) {
                                            if (null != data && data != "") {
                                                jQuery("#Relationship_sale_POI").empty();
                                                jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                                                jQuery.each(relationtypes_R, function (i, obj)
                                                {
                                                    jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                                                });

                                                jQuery("#gender_sale_POI").empty();
                                                jQuery("#gender_sale_POI").append(
                                                        jQuery("<option></option>").attr("value", 0).text(
                                                        $.i18n("gen-please-select")));
                                                jQuery.each(genderStatus_R, function (i, obj1) {
                                                    jQuery("#gender_sale_POI").append(
                                                            jQuery("<option></option>").attr("value",
                                                            obj1.genderId).text(obj1.gender_en));
                                                });
                                                $('#firstname_sale_poi').val("");
                                                $('#middlename_sale_poi').val("");
                                                $('#lastname_sale_poi').val("");

                                                $('#date_Of_birthPOI_sale').val("");
                                                $('#leaseepoiid').val("");
                                                loadResPersonsOfEditingForEditing();

                                            }
                                        }
                                    });
                                } else {
                                    jAlert(alertmessage, $.i18n("gen-info"));
                                }
                            }
                        });
                    } else if (response && edit == 1) {

                        $.ajax({
                            type: "POST",
//		         		    contentType: "application/json; charset=utf-8",
                            traditional: true,
                            url: "landrecords/saveRegPersonOfInterestForEditing/" + selectedlandid + "/" + finaltransid,
                            data: jQuery("#editprocessAttributeformID")
                                    .serialize(),
                            async: false,
                            success: function (data) {
                                if (null != data && data != "") {
                                    jQuery("#Relationship_sale_POI").empty();
                                    jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", 0).text($.i18n("gen-please-select")));
                                    jQuery.each(relationtypes_R, function (i, obj)
                                    {
                                        jQuery("#Relationship_sale_POI").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
                                    });

                                    jQuery("#gender_sale_POI").empty();
                                    jQuery("#gender_sale_POI").append(
                                            jQuery("<option></option>").attr("value", 0).text(
                                            $.i18n("gen-please-select")));
                                    jQuery.each(genderStatus_R, function (i, obj1) {
                                        jQuery("#gender_sale_POI").append(
                                                jQuery("<option></option>").attr("value",
                                                obj1.genderId).text(obj1.gender_en));
                                    });
                                    $('#firstname_sale_poi').val("");
                                    $('#middlename_sale_poi').val("");
                                    $('#lastname_sale_poi').val("");

                                    $('#date_Of_birthPOI_sale').val("");
                                    $('#leaseepoiid').val("");
                                    loadResPersonsOfEditingForEditing();

                                }
                            }
                        });


                    }
                });
    }
}

function salebuyerdetails(id) {
    jQuery("#newOwnerRecordsRowDataSale").empty();

    if (finalbuyerarray.length > 0) {
        for (var i = 0; i < finalbuyerarray.length; i++) {

            var personid = finalbuyerarray[i].personid;

            jQuery.ajax({
                type: "GET",
                url: "registration/salebuyerdetails/" + personid + "/" + landid,
                async: false,
                success: function (data) {
                    if (data == id) {
                        jQuery("#newOwnerRecordsAttrTemplateSale").tmpl(finalbuyerarray[i]).appendTo("#newOwnerRecordsRowDataSale");
                        $("#newOwnerRecordsRowDataSale").i18n();
                    }
                }
            });



        }
    }
}





function documentEditFetch() {
    jQuery.ajax({
        type: 'GET',
        url: "registryrecords/editDocuments/" + selectedlandid + "/" + finaltransid,
        async: false,
        success: function (data) {
            if (data != null && data != "")
            {

                $("#salesDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#salesDocRowData");
                $("#salesDocRowData").i18n();
                
                $("#LeaseDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#LeaseDocRowData");
                $("#LeaseDocRowData").i18n();

                $("#MortagageDocRowData").empty();
                $("#salesdocumentTemplate_add").tmpl(data).appendTo("#MortagageDocRowData");
                $("#MortagageDocRowData").i18n();

            }

        }
    });

}