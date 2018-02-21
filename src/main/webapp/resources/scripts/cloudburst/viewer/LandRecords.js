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
var Personusin = "";
var URL = null;
var landId = null;
var resultDeleteNatural = null;
var records_from = 0;
var totalRecords = null;
var searchRecords = null;
var workflowRecords = null;
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
var disputeStatusList = null;
var _transactionid=0;
var _parcelNumber=0;
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
var landRecordsInitialized = true;
var workFlowLst=null;
var actionList=null

var communeList=null

function LandRecords(_selectedItem) {
    if(projList !== null && projList !== ""){
        return;
    }
    
	   jQuery.ajax({
        url: "landrecords/spatialunit/totalRecord/" + activeProject,
        async: false,
        success: function (data) {
            totalRecords=data;
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
        url: "landrecords/workflow/",
        async: false,
        success: function (data) {
        	workFlowLst = data;
        }
    });
   
    jQuery.ajax({
        url: "landrecords/spatialunit/commune/" + activeProject,
        async: false,
        success: function (data) {
            communeList=data;
        }
 });
    displayRefreshedLandRecords(_selectedItem);
}



function issavedisabled(){
	$("ul.tabs").tabs();

	
	if($("#neighbor_north,#neighbor_east,#neighbor_west,#neighbor_south,#tenureclass_id,#tenure_type,#tenureDuration,#existing_use,#proposed_use").hasClass('addBg')){
		jAlert("Please Save the Data First.");
		$('#tabs').tabs("option", "active", $('#tabs a[href="#tabGeneralInfo"]').parent().index());
		return false;
	}else{
	 return true;
	
	}
	
		

}


function displayRefreshedLandRecords(_selectedItem) {
    // var URL = "landrecords/";
     // if (activeProject != "") {
        // URL = "landrecords/" + activeProject;
     // }
      //landrecords
	  searchRecords = null
    workflowRecords=null
	selectedItem = _selectedItem;
    URL = "landrecords/spatialunit/landrecord/default/" + 0;
    if (activeProject !== null && activeProject !== "") {
        URL = "landrecords/spatialunit/landrecord/" + activeProject + "/" + 0;
    }
    jQuery.ajax({
        url: URL,
        async: false,
        success: function (data) {
            dataList = data;
            records_from = 0;
            searchRecords = null;
			
			
			 jQuery("#landrecords-div").empty();
             jQuery.get("resources/templates/viewer/" + selectedItem + ".html", function (template) {
            jQuery("#landrecords-div").append(template);
            jQuery('#landRecordsFormdiv').css("visibility", "visible");
            jQuery("#landRecordsTable").show();
			
			
			//add for applicatyon statu and claim type
			jQuery("#status_id").empty();
            jQuery("#claim_type").empty();
			jQuery("#community_id").empty();
			 
			 
			 

             jQuery("#status_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
             jQuery("#claim_type").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
			  jQuery("#community_id").append(jQuery("<option></option>").attr("value", "").text("Please Select"));

             jQuery.each(statusList, function (i, statusobj) {
                 jQuery("#status_id").append(jQuery("<option></option>").attr("value", statusobj.workflowStatusId).text(statusobj.workflowStatus));
             });

             jQuery.each(claimTypes, function (i, claimType) {
                 jQuery("#claim_type").append(jQuery("<option></option>").attr("value", claimType.code).text(claimType.name));
             });
			 
			   jQuery.each(communeList, function (i, CommuneObj) {
                 jQuery("#community_id").append(jQuery("<option></option>").attr("value", CommuneObj.communeid).text(CommuneObj.commune));
             });
			 
			 
	  	   jQuery("#newtbl").empty();
		   jQuery("#newtb2").empty();
           jQuery("#newtb3").empty();
		   

           	jQuery("#workFlowTemplate").tmpl(workFlowLst).appendTo("#newtbl");
			jQuery("#TransactionTemplate").tmpl(claimTypes).appendTo("#newtb2");
			jQuery("#AppstatusTemplate").tmpl(statusList).appendTo("#newtb3");
			
			  $( "#workflow-accordion" ).accordion(

			 );
				
				jQuery("#landRecordsRowData").empty();
				

            if (dataList.length != 0 && dataList.length != undefined) {
                 jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
				   $('#records_from').val(records_from + 1);
                $('#records_to').val(totalRecords);
                if (records_from + 15 <= totalRecords)
                    $('#records_to').val(records_from + 15);
                $('#records_all').val(totalRecords);
             }else{
				    $('#records_from').val(0);
                $('#records_to').val(0);
				 $('#records_all').val(0);
			 }
			 

			 $("#landRecordsTable").trigger("update");/* 
                    $("#landRecordsTable").tablesorter({
                        sortList: [[0, 1], [1, 0]]
                    }); */
					
					
					
			
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

function nonnaturalperson(usin)
{
	landId = usin;
	FillNonNaturalPersonDataNew();
	
	}
function personwithinterest(usin)
{
	landId = usin;
	loadPersonsOfEditingForEditing();
	
}
function deceasedperson(usin)
{
	jQuery.ajax({
        url: "landrecords/deceasedperson/" + usin,
        async: false,
        success: function (data) {
            deceasedPersonList = data;
            jQuery("#deceasedRowData").empty();
            if (deceasedPersonList.length != 0 && deceasedPersonList.length != undefined) {
                jQuery("#deceasedTemplate").tmpl(deceasedPersonList).appendTo("#deceasedRowData");
            } 
			  jQuery("#hideTable").show();
        }
    });
	
	
}


function Person(usin){
	
	Personusin = usin;
	FillPersonDataNew();
		
}


function editAttributeNew(usin, statusId) {
     read=false;
     $("#parcelsavebutton").prop("disabled",false).hide();
	 editAttribute(usin);
     
}

function disputes(id)
{
	landId = id;
	loadDisputeForEditing();
	
	}


function disputePerson(id){
	landId = id;
	loadDisputePersonForEditing();
	
}

function media(id){
	landId = id;
	loadMultimediaForEditing();
	
}




function editAttribute(id) {
   personwithinterest(id);
   nonnaturalperson(id);
   disputes(id);
   disputePerson(id);
   
  
   
	jQuery.ajax({
        url: "landrecords/tenuretype/",
        async: false,
        success: function (data) {
            tenuretypeList = data;
        }
    });
	
	jQuery.ajax({
        url: "landrecords/tenureclass/",
        async: false,
        success: function (data) {
            tenureclassList = data;
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

    $("#primary").val(id);
    $('#liNonNatural').hide();
    $('#liNatural').hide();
    $("#btnNewTenure").show();
    $("#btnNewNonNatural").show();
    $("#btnAddExistingPerson").hide();
    $(".addRepresentative").hide();
    $("#divRegisterDispute").show();
    
	
	$("#tenureclass_id").empty();
	 $("#tenure_type").empty();
	 
	 $("#tenure_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
	 $("#tenureclass_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
	
	 jQuery.each(tenureclassList, function (i, tenureclassobj) {
		 if(tenureclassobj.tenureclassid!='9999')
            jQuery("#tenureclass_id").append(jQuery("<option></option>").attr("value", tenureclassobj.tenureclassid).text(tenureclassobj.tenureclassEn));
    });

   
    jQuery.each(tenuretypeList, function (i, tenureobj) {
		if(tenureobj.landsharetypeid!='9999')
        jQuery("#tenure_type").append(jQuery("<option></option>").attr("value", tenureobj.landsharetypeid).text(tenureobj.landsharetypeEn));
    });
	
 associatednaturalPersonList = null;	
    jQuery.ajax({
        url: "landrecords/editattribute/" + id,
        async: false,
        success: function (data) {
            editList = data;
            
			
            jQuery("#survey_date").val(data[0].surveydate);
			jQuery("#claimNumber").val(data[0].claimno);
            
			jQuery("#neighbor_north").val(data[0].neighborNorth);
            jQuery("#neighbor_south").val(data[0].neighborSouth);
            jQuery("#neighbor_east").val(data[0].neighborEast);
            jQuery("#neighbor_west").val(data[0].neighborWest);
			
			

			   jQuery("#claimStatus").empty();
				jQuery("#claimStatus").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
				jQuery.each(claimTypes, function (i, claimType) {
						jQuery("#claimStatus").append(jQuery("<option></option>").attr("value", claimType.code).text(claimType.name));
				});
				
			jQuery("#land_type").empty();
			jQuery("#land_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(typeofLandList, function (i, landTypeobj) {
				if(landTypeobj.landtypeid!='9999')
				  jQuery("#land_type").append(jQuery("<option></option>").attr("value", landTypeobj.landtypeid).text(landTypeobj.landtypeEn)); 

			});

			
			jQuery("#existing_use").empty();
			jQuery("#existing_use").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(landUserList, function (i, landuseobj) {
					if(landuseobj.landusetypeid!='9999')
				     jQuery("#existing_use").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn)); 

			});

			jQuery("#proposed_use").empty();
			jQuery("#proposed_use").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(landUserList, function (i, landuseobj) {
				if(landuseobj.landusetypeid!='9999')
				jQuery("#proposed_use").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn)); 

			});
				$("#claimStatus").val(data[0].claimtypeid);
				 
				 
				 if(data[0].laBaunitLandtype!=null)
				 jQuery("#land_type").val(data[0].laBaunitLandtype.landtypeid);
				 
				  if(data[0].laRightLandsharetype!=null)
				 jQuery("#tenure_type").val(data[0].laRightLandsharetype.landsharetypeid);
				 
				  if(data[0].laBaunitLandusetype!=null){
					 jQuery("#existing_use").val(data[0].laBaunitLandusetype.landusetypeid);
					
				 }
					
				if (data[0].occupancylength !== null) {
                    jQuery("#tenureDuration").val(data[0].occupancylength);
                }
				
				if (data[0].tenureclassid !== null) {
                    jQuery("#tenureclass_id").val(data[0].tenureclassid);
                }
				jQuery("#proposed_use").val(data[0].proposedused);
				 
				$("#claimUka").text(_transactionid);
				$("#spatialid").text(_parcelNumber);
				var _claim=jQuery("#claimStatus option:selected").text();
				$("#claimType").text(_claim);
				
				


        }
    });

   
    editAttrDialog = $("#editattribute-dialog-form").dialog({
        autoOpen: false,
        height: 600,
        closed: false,
        cache: false,
        width: 1300,
        resizable: false,
        modal: true,
		close: function () {
            editAttrDialog.dialog("destroy");
            $("input,select,textarea").removeClass('addBg');
            $('#tabs').tabs("option", "active", $('#tabs a[href="#tabGeneralInfo"]').parent().index());
        },
       buttons: [
			{
				text: "Cancel",
				"id": "info_cancel",
				click: function () {

				   editAttrDialog.dialog( "destroy" );
				    $('#tabs').tabs("option", "active", $('#tabs a[href="#tabGeneralInfo"]').parent().index());
				    $("input,select,textarea").removeClass('addBg');

				}
			},
			{
				text: "Save",
				"id": "parcelsavebutton",
				click: function () {
                 updateattributesGen();
				   

				}
				
				
			},
			
			],
    });
	$("#parcelsavebutton").html('<span class="ui-button-text"> Save </span>');
	$("#info_cancel").html('<span class="ui-button-text"> Close </span>');
	
    Person(id);
    editAttrDialog.dialog("open");
    $("#parcelsavebutton").prop("disabled",true).hide();
   

  
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



function refreshLandRecords() {
    if (searchRecords !== null) {
        spatialSearch(records_from);
    } else {
        spatialRecords(records_from);
    }
}

function updateattributesGen() {
    $("#editAttributeformID").validate({
        rules: {
            claimNumber: "required",
            survey_date: "required",
            neighbor_north: "required",
            neighbor_south: "required",
            neighbor_east: "required",
            neighbor_west: "required",
			tenureDuration:{
				required: true,
				number: true
			},
        },
        messages: {
            claimNumber: "Enter claim number",
            survey_date: "Enter claim date",
            neighbor_north: "Please enter neighbour name",
            neighbor_south: "Please enter neighbour name",
            neighbor_east: "Please enter neighbour name",
            neighbor_west: "Please enter neighbour name",
			tenureDuration: {
            required: "Enter Mobile Number",
			number: jQuery.format("Only Numeric Allowed"),
            }
        }
    });

    if ($("#editAttributeformID").valid()) {
       		updateattributes();
    } else {
        jAlert("Please Fill Mandatory Details", "Alert");
    }
}

function updateattributes() {
  //  var length_gen = attributeList.length;
    jQuery("#general_length").val(0);
    // if (length_gen != 0 && length_gen != undefined)
        // jQuery("#general_length").val(length_gen);
    jQuery.ajax({
        type: "POST",
        url: "landrecords/updateattributes",
        data: jQuery("#editAttributeformID").serialize(),
        success: function (result) {
            if (result) {
                jAlert('Attributes were successfully updated');
                $("input,select,textarea").removeClass('addBg');
                $("#parcelsavebutton").prop("disabled", false).hide();
    
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
	

    $("#claim_type").val("");
	$("#community_id").val("");
    $("#status_id").val("0");
	$("#transaction_id").val("");
	$("#parce_id").val("");

	displayRefreshedLandRecords("landRecords");
}

function search() {

	var _community=$("#community_id").val();
	var _transaction_id=$("#transaction_id").val();
	var _parce_id=$("#parce_id").val();
	
	if( _community=="" && _transaction_id=="" && _parce_id=="" )
	{
		 jAlert('Please Select Search criteria');
		return false;
	}
    
	
	workflowRecords=null;
	records_from=0;
	jQuery.ajax({
        type: "POST",
        async: false,
        url: "landrecords/search1count" + "/" + activeProject,
        data: jQuery("#landrecordsform").serialize(),
        success: function (result) {
            searchRecords = result;
        }
    })
	
	spatialSearch(records_from);
	
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

       /*  if ($("#person_subType").val() == "0") {
            alert("Select Owner type");
            return;
        } */

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


var deleteNatural = function (id, name) {
    var usinid = editList[0].landid;

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
                            Person(usinid);
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
    var usinid = editList[0].landid;
    jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                type: 'GET',
                url: "landrecords/deletenonnatural/" + id,
                success: function (result) {
                    if (result == true) {
                        jAlert('Data Successfully Deleted', 'Info');
                        Person(usinid)
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


function clearUploadDialog() {
    uploadDialog.dialog("destroy");
    $('#uploaddocumentformID')[0].reset();
}

function viewMultimedia(id) {
    window.open("landrecords/download/" + id, 'popUp', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
}

function viewMultimediaByTransid(id) {
	
	var flag=false;
    jQuery.ajax({
        type: 'GET',
        async:false,
        url: "landrecords/mediaavail/" + id+"/" + _transactionid,
        success: function (result) {
            if (result == true) {
            	flag=true; 
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Request not completed');
        }
    });
    
    if(flag){
    window.open("landrecords/download/" + id +"/" + _transactionid, 'popUp', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
    }else{

    	 jAlert('File not Found',"Info");
    }
}

function showOnMap(usin, statusId) {
    $.ajaxSetup({
        cache: false
    });
    var relLayerName = "Mast:la_spatialunit_land";
    var fieldName = "landid";
    var fieldVal = usin;
    zoomToLayerFeature(relLayerName, fieldName, fieldVal);
}
function zoomToLayerFeature(relLayerName, fieldName, fieldVal) {
 
var _featureNS= window.location.protocol+'//'+window.location.host+'/';
var _featureTypes= [];
 _featureTypes.push("la_spatialunit_land");
var _featurePrefix="Mast";
 featureRequest = new ol.format.WFS().writeGetFeature({
						srsName: 'EPSG:4326',
						featureNS: _featureNS,
						featurePrefix: _featurePrefix,
						featureTypes: _featureTypes,
						outputFormat: 'application/json',
						filter: ol.format.filter.equalTo(fieldName, fieldVal)
					  });
					  
					  
	  var _url= window.location.protocol+'//'+window.location.host+'/geoserver/wfs';
				  fetch(_url, {
					method: 'POST',
					body: new XMLSerializer().serializeToString(featureRequest)
				  }).then(function(response) {
					return response.json();
				  }).then(function(json) {
				   var features = new ol.format.GeoJSON().readFeatures(json);
					zoomToAnyFeature(features);
				  });
	  				  
				
}
function zoomToAnyFeature(geom) {
	 if (geom != undefined &&  geom!= null) {
	  
	  
	   map.getLayers().forEach(function (layer) {
			if (layer.get('aname') != undefined & layer.get('aname') === 'featureWorkflow') {
				map.removeLayer(layer);
			}
		});
		
		
		var highlightStyle = new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: '00008B',
          width: 1
        }),
        fill: new ol.style.Fill({
          color: 'rgba(0,0,139,0.2)'
        })
      });

      var featureOverlay = new ol.layer.Vector({
		 name:"featureWorkflow",
        source: new ol.source.Vector(),
        style: highlightStyle,
      });
		featureOverlay.set('aname', "featureWorkflow");	
		featureOverlay.getSource().addFeature(geom[0]);
		map.addLayer(featureOverlay)
		map.getView().fit(featureOverlay.getSource().getExtent(), map.getSize());
		map.getView().setZoom(9);
		 $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#map-tab"]').parent().index());
        $('#sidebar').show();
        $('#collapse').show();
		
		
		
	 }else{
		 
		 $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#landrecords-div"]').parent().index());
        $('#sidebar').hide();
        $('#collapse').hide();
        jAlert('Site not found on Map', 'Alert'); 
		 
	 }
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





function updatespatialwork (_type) {
	
	records_from=0;
	searchRecords=null;
   	var _flag1=$('input[name="workflow"]:checked').length
   	var _flag2= $('input[name="claimType"]:checked').length
    var _flag3=$('input[name="statusType"]:checked').length
	
	if(_flag1==0 && _flag2==0 && _flag3 ==0 )
	{
		
     jAlert('Please Select Search Filter');
		return false;
		
	}
	jQuery.ajax({
		type:'POST',
		url: "landrecords/spatialunitbyworkflow/count/"+activeProject,
		async:false,
		data: jQuery("#updatebyWorkflow").serialize(),
		success: function (data) {	
          
			workflowRecords=data;
		}
	   
    });

	
	spatialSearchWorkfow(records_from);
	
}

function clearFilter(){
	searchRecords = null
    workflowRecords=null
		$(".roleCheckbox").prop("checked",false);
        $("#select_all").prop("checked",false);
    	$(".roleCheckboxTrans").prop("checked",false);
     	$(".roleCheckboxStatus").prop("checked",false);
        $("#select_all_as").prop("checked",false);
	
	displayRefreshedLandRecords("landRecords");
	
}


function previousRecords() {
    records_from = $('#records_from').val();
    records_from = parseInt(records_from);
    records_from = records_from - 16;
    if (records_from >= 0) {
        if (searchRecords != null) {
            spatialSearch(records_from);
        } else if(workflowRecords!=null){
			spatialSearchWorkfow(records_from);
		}else {
            spatialRecords(records_from);
        }
    } else {
        alert("No records found");
    }
}

function nextRecords() {
    records_from = $('#records_from').val();
    records_from = parseInt(records_from);
    records_from = records_from + 14;

    if (records_from <= totalRecords - 1) {
        if (searchRecords != null) {
            if (records_from <= searchRecords - 1)
                spatialSearch(records_from);
            else
                alert("No records found");
        }  else if(workflowRecords!=null){
			  if (records_from <= workflowRecords - 1)
                spatialSearchWorkfow(records_from);
            else
                alert("No records found");
			
		}else {
            spatialRecords(records_from);
        }
    } else {
        alert("No records found");
    }
}

function spatialRecords(records_from) {
    jQuery.ajax({
        url: "landrecords/spatialunit/landrecord/" + activeProject + "/" + records_from,
        async: false,
        success: function (data) {
            dataList = data;
            $('#landRecordsRowData').empty();
            if (dataList != "" && dataList != null) {
                jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
                $("#landRecordsTable").trigger("update");
                $('#records_from').val(records_from + 1);
                $('#records_to').val(totalRecords);
                if (records_from + 15 <= totalRecords)
                    $('#records_to').val(records_from + 15);
                $('#records_all').val(totalRecords);
            }else{
				 $('#records_from').val(0);
                $('#records_to').val(0);
                $('#records_all').val(0);
				
			}
        }
    });
}

function spatialSearch(records_from) {
    jQuery.ajax({
        type: "POST",
        url: "landrecords/search1/" + activeProject + "/" + records_from,
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
                if (records_from + 15 <= searchRecords)
                    $('#records_to').val(records_from +15);
                $('#records_all').val(searchRecords);
            }else{
				 $('#records_from').val(0);
                $('#records_to').val(searchRecords);
                $('#records_all').val(searchRecords);
				
			}
        }
    });
}

function spatialSearchWorkfow(records_from) {
   
   jQuery.ajax({ 
		type:'POST',
		url: "landrecords/spatialunitbyworkflow/"+activeProject+"/"+records_from,
		data: jQuery("#updatebyWorkflow").serialize(),
		async:false,							
		success: function (data) {
			  dataList = data;
            $('#landRecordsRowData').empty();
            if (dataList != "" && dataList != null) {
               jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
                $("#landRecordsTable").trigger("update");
                $('#records_from').val(records_from + 1);
                $('#records_to').val(workflowRecords);
                if (records_from + 15 <= workflowRecords)
                    $('#records_to').val(records_from +15);
                $('#records_all').val(workflowRecords);
            }else{
				 $('#records_from').val(0);
                $('#records_to').val(workflowRecords);
                $('#records_all').val(workflowRecords);
				
			}

		}


	});
	
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

function generateDataCorrectionReport(usin) {
	
	if(usin == null)
		{
			usin = $("#ccroStartTransid").val();
		}
	
	result = RESPONSE_OK;
    if (result === RESPONSE_OK) {
        var w = window.open("landrecords/landverification/" + usin, 'CcroForm', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
        if (window.focus) {
            w.focus();
        }
    } else {
        jAlert(result, 'Error');
    }
}

function generateCcro(usin) {
	
	if(usin == null)
		{
			usin = $("#ccroStart").val();
		}
	
   /* $.ajax({
        type: "GET",
        url: "landrecords/checkvcdate/" + activeProject,
        async: false,
        success: function (result) {
        	result = RESPONSE_OK;
            if (result === RESPONSE_OK) {
                var w = window.open("landrecords/ccroformladm/" + usin, 'CcroForm', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
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
    });*/
	result = RESPONSE_OK;
    if (result === RESPONSE_OK) {
        var w = window.open("landrecords/ccroformladm/" + usin, 'CcroForm', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
        if (window.focus) {
            w.focus();
        }
    } else {
        jAlert(result, 'Error');
    }
}

/*function generateCcros() {
    $.ajax({
        type: "GET",
        url: "landrecords/checkvcdate/" + activeProject,
        async: false,
        success: function (result) {
            if (result === RESPONSE_OK) {
            	result = RESPONSE_OK;
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
}*/

function generateProjectTenureTypesLandUnitsSummaryReport()
{
	projectid= $("#selectProjectsForTenureTypesLandUnitsSummary").val();
	if(projectid == "" || projectid == null)
	{
		alert ("Select Project");
		return false;

	}
	var rep = "1";
	var reportTmp=new reports();
	if(rep=="1")
	{
		//console.log(villageSelected);
		reportTmp.ProjectTenureTypesLandUnitsSummaryReport("NEW",projectid, "1");
		// reportDialog.dialog( "destroy" );
	}

}

reports.prototype.ProjectTenureTypesLandUnitsSummaryReport=function(tag,projectid,villageId){
	//alert ("Under Function");
		window.open("landrecords/projectdetailedTenureTypesLandUnitSummaryreport/"+projectid+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

	}

function generateCcros() 
{
	result = RESPONSE_OK;
	if (result === RESPONSE_OK) {
		result = RESPONSE_OK;
		var fromRecord = $("#ccroStartbatch").val();
		var endRecord = $("#ccroEndbatch").val();

		/*if (!checkIntNumber(fromRecord))
            fromRecord = 1;

        if (!checkIntNumber(endRecord))
            endRecord = 100000;*/

		var w = window.open("landrecords/ccroformsinbatch/" + activeProject + "/" + fromRecord + "/" + endRecord, 'CcroForms', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
		if (window.focus) {
			w.focus();
		}
	} else {
		jAlert(result, 'Error');
	}	

}

function generateLandForm() 
{
	result = RESPONSE_OK;
	if (result === RESPONSE_OK) {
		result = RESPONSE_OK;
		var fromRecord = $("#landformStart").val();
		var endRecord = $("#landformEnd").val();

		/*if (!checkIntNumber(fromRecord))
            fromRecord = 1;

        if (!checkIntNumber(endRecord))
            endRecord = 100000;*/

		var w = window.open("landrecords/landformsinbatch/" + activeProject + "/" + fromRecord + "/" + endRecord, 'CcroForms', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
		if (window.focus) {
			w.focus();
		}
	} else {
		jAlert(result, 'Error');
	}	

}

function _generateLandForm(_transId) 
{
	result = RESPONSE_OK;
	if (result === RESPONSE_OK) {
		result = RESPONSE_OK;

		var w = window.open("landrecords/landformsinbatch/" + activeProject + "/" + _transId + "/" + _transId, 'CcroForms', 'left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=true');
		if (window.focus) {
			w.focus();
		}
	} else {
		jAlert(result, 'Error');
	}	

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
   

    $("#personsEditingGrid .jsgrid-table th:first-child :button").click();
});



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

function Actionfill(usin,workflowId,transactionid,parcelnum ){
    _transactionid=0;
    _parcelNumber=0;
   _parcelNumber=parcelnum;
   parcelnum=""+parcelnum+"";

    $(".containerDiv").empty();  
	var appid='#'+usin+"_land";
	jQuery.ajax({ 
		url: "landrecords/workflowAction/"+workflowId + "/" +activeProject + "/" + usin,
		async:false,							
		success: function (data) {	

			actionList=data;
		}


	});


	$(""+appid+"").empty();
	html="";

	if (actionList.length==undefined){
		jAlert(No_action_req);
	}
	else{
		
		for(var i=0;i<actionList.length;i++){
			html+="<li> <a title='"+(actionList[i].action)+"' id="+workflowId+" name="+usin+"  href='#' onclick='CustomAction(this,"+usin+","+workflowId+" ,"+transactionid+","+parcelnum+")'>"+actionList[i].actionnameEn+"</a></li>";
		}
		html =html+="<li> <a title='View Comment' id="+workflowId+"  name="+usin+"  href='#' onclick='commentsDialog("+usin+")'>Comments</a></li>";
		$(""+appid+"").append('<div class="signin_menu"><div class="signin"><ul>'+html+'</ul></div></div>');


		$(".signin_menu").toggle();
		$(".signin").toggleClass("menu-open");


		$(".signin_menu").mouseup(function() {
			return false
		});
		$(document).mouseup(function(e) {
			if($(e.target).parent("a.signin").length==0) {
				$(".signin").removeClass("menu-open");
				$(".signin_menu").hide();
			}
		});
	}
}

function CustomAction(refrence,landid,workflowid,transactionid,parcelnum){
_transactionid =transactionid;
$('#commentsStatusWorkflow').val("");

if((refrence.title).trim() == "approve")
{
   dialogueAction(landid,workflowid,(refrence.title).trim())
		
}else if((refrence.title).trim()  == "reject"){
	dialogueAction(landid,workflowid,(refrence.title).trim())

}else if((refrence.title).trim()  == "edit"){
     editAttributeNew(landid,workflowid);
}else if((refrence.title).trim()  == "view"){
     editAttributeNew(landid,workflowid);
}else if((refrence.title).trim()  == "verification"){
	dialogueAction(landid,workflowid,(refrence.title).trim())

}else if((refrence.title).trim() == "print"){
	_printReport(workflowid,transactionid);
}else if((refrence.title).trim() == "register"){
	registerParcel(landid,workflowid,parcelnum)

}else if((refrence.title).trim() == "delete"){
	deleteParcel(landid,workflowid)

}else if((refrence.title).trim() == "edit  spatial"){
	showOnMap(landid,workflowid)

}
		
 

}


function _printReport(workflowid,transactionid){
	
	if(workflowid==1){
		generateDataCorrectionReport(transactionid);
	}else if(workflowid==2){
		_generateLandForm(transactionid);
	}else if(workflowid==3){
		_generateLandForm(transactionid);
	}else if(workflowid==4){
		generateCcro(transactionid);
	}else if(workflowid==5){
		generateDataCorrectionReport(transactionid);
	}
	
}



function RefreshedLandRecordsgrid()
{
	records_from = $('#records_from').val();
    records_from = parseInt(records_from);
	records_from=records_from-1;
    if (records_from >= 0) {
        if (searchRecords != null) {
            spatialSearch(records_from);
        } else if(workflowRecords!=null){
			spatialSearchWorkfow(records_from);
		}else {
            spatialRecords(records_from);
        }
    } else {
        alert("No records found");
    }
}
function dialogueAction(usin,workId,actionName){
	
	approveInfoDialog = $( "#approveInfo-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 390,
			resizable: false,
			modal: true,

			buttons: [{
				text: "Ok",
				"id": "info_ok",
				click: function () {
					var comment=$('#commentsStatusWorkflow').val();
					if((comment==undefined || comment=='')){
						jAlert("Please Enter Comment","Error");
					}
					else{
						if(actionName== "approve"){
						  var status=  approveParcel(usin,workId);
						  if(status!=0){
							  approveInfoDialog.dialog( "destroy" );
							  RefreshedLandRecordsgrid();
						  }
						} else if(actionName== "reject"){
							var status=rejectParcel(usin,workId);
							if(status!=0){
								approveInfoDialog.dialog( "destroy" );
								RefreshedLandRecordsgrid();
						  }
						} else if(actionName== "verification"){
							var status=verifyParcel(usin,workId);
							if(status!=0){
								approveInfoDialog.dialog( "destroy" );
								RefreshedLandRecordsgrid();
						  }
						}/*  else if(actionName== "register"){
							var status=registerParcel(usin,workId);
							if(status!=0){
								approveInfoDialog.dialog( "destroy" );
								RefreshedLandRecordsgrid();
						  }
						} */
						
					}
					
					
					

				},
			},
			{
				text: "Cancel",
				"id": "info_cancel",
				click: function () {

				   // approveInfoDialog.dialog( "close" ); 
					approveInfoDialog.dialog( "destroy" );
					

				}
			}],
			close: function() {

				approveInfoDialog.dialog( "destroy" );

			}
		});
		$("#info_ok").html('<span class="ui-button-text"> Save </span>');
		$("#info_cancel").html('<span class="ui-button-text"> Cancel </span>');
		approveInfoDialog.dialog( "open" );
}




function  approveParcel(usin,workId){

	var approve=false;

	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/action/approve/"+usin+"/"+workId,
		data: jQuery("#aworkflowformID").serialize(),
		async:false,							
		success: function (data) {

			approve=data;
		}


	});


	return approve;
}
function  rejectParcel(usin,workId){

	var reject=false;

	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/action/reject/"+usin+"/"+workId,
		data: jQuery("#aworkflowformID").serialize(),
		async:false,							
		success: function (data) {

			reject=data;
		}


	});


	return reject;
}

function  verifyParcel(usin,workId){

	var verify=false;

	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/action/verify/"+usin+"/"+workId,
		data: jQuery("#aworkflowformID").serialize(),
		async:false,							
		success: function (data) {

			verify=data;
		}


	});


	return verify;
}

function pad (str, max) 
{
	  str = str.toString();
	  return str.length < max ? pad("0" + str, max) : str;
	}

function  registerParcel(usin,workId,parcelnum){

var parcelnumwithpadding = pad(parcelnum, 9);

 jConfirm('Do you want to finalize the land record and commit the record to the land registry with parcel no ' + parcelnumwithpadding , 'Approve Confirmation', function (response) {

        if (response) {
			
			approveInfoDialog = $( "#approveInfo-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 390,
			resizable: false,
			modal: true,

			buttons: [{
				text: "Ok",
				"id": "info_ok",
				click: function () {
					var comment=$('#commentsStatusWorkflow').val();
					if((comment==undefined || comment=='')){
						jAlert("Please Enter Comment","Error");
					}else
					{
						 jQuery.ajax({
							type:'POST',
							url: "landrecords/action/register/"+usin+"/"+workId,
							data: jQuery("#aworkflowformID").serialize(),
							success: function (result) {
									jAlert('Data Approved Successfully ', 'Workflow');
									approveInfoDialog.dialog( "destroy" );
								RefreshedLandRecordsgrid();
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {

								alert('err.Message');
							}
						});
						
					}
					

				},
			},
			{
				text: "Cancel",
				"id": "info_cancel",
				click: function () {

				   // approveInfoDialog.dialog( "close" ); 
					approveInfoDialog.dialog( "destroy" );
					

				}
			}],
			close: function() {

				approveInfoDialog.dialog( "destroy" );

			}
		});
		$("#info_ok").html('<span class="ui-button-text"> Save </span>');
		$("#info_cancel").html('<span class="ui-button-text"> Cancel </span>');
		approveInfoDialog.dialog( "open" );
		
           
        }

    });
	
}

function  deleteParcel(usin,workId){


   jConfirm('Before you delete this land record, please print map report, as this record will be archived in the database', 'Delete Confirmation', function (response) {

        if (response) {
			
			approveInfoDialog = $( "#approveInfo-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 390,
			resizable: false,
			modal: true,

			buttons: [{
				text: "Ok",
				"id": "info_ok",
				click: function () {
					var comment=$('#commentsStatusWorkflow').val();
					if((comment==undefined || comment=='')){
						jAlert("Please Enter Comment","Error");
					}else
					{
						 jQuery.ajax({
							type: 'POST',
							url: "landrecords/action/delete/"+usin+"/"+workId,
							data: jQuery("#aworkflowformID").serialize(),
							success: function (result) {
									jAlert('Data Successfully Deleted', 'User');
									approveInfoDialog.dialog( "destroy" );
								RefreshedLandRecordsgrid();
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {

								alert('err.Message');
							}
						});
						
					}
					

				},
			},
			{
				text: "Cancel",
				"id": "info_cancel",
				click: function () {

				   // approveInfoDialog.dialog( "close" ); 
					approveInfoDialog.dialog( "destroy" );
					

				}
			}],
			close: function() {

				approveInfoDialog.dialog( "destroy" );

			}
		});
		$("#info_ok").html('<span class="ui-button-text"> Save </span>');
		$("#info_cancel").html('<span class="ui-button-text"> Cancel </span>');
		approveInfoDialog.dialog( "open" );
		
           
        }

    });
	

	
}

function commentsDialog(usin)
{
	jQuery.ajax({ 
		type:'POST',
		url: "landrecords/workflow/comment/"+usin,
		async:false,							
		success: function (data) {

		 if(data.length>0){
			jQuery('#commentsHistoryTableBody').empty();
			 jQuery("#commentsTable").show();
           if(data!=null || data!=undefined || data.toString()!=""){
				
					for (var i=0;i<data.length;i++)
						{
							jQuery("#commentstemplate").tmpl(data[i]).appendTo("#commentsHistoryTableBody");
							jQuery("#commentsTable").show();
						}
				

				//	
				commentHistoryDialog = $( "#commentsDialog" ).dialog({
					autoOpen: false,
					height: 242,
					width: 666,
					resizable: true,
					modal: true,

					buttons: [{
						text: "Cancel",
						"id": "comment_cancel",
						click: function () {
							 setInterval(function(){ 

							 }, 4000);
							 jQuery('#commentsHistoryTableBody').empty();
            				 jQuery("#commentsTable").hide();
							commentHistoryDialog.dialog( "destroy" );

						}
					}],
					close: function() {
							
						commentHistoryDialog.dialog( "destroy" );


					}
				});
				$("#comment_cancel").html('<span class="ui-button-text">cancel</span>');
				commentHistoryDialog.dialog( "open" );

			}
			
			
		}else{
				jAlert("No Record","Info");
			}
		}


	});



	

}


function generateSummaryReport()
{
	projectid= $("#selectProjectsForSummary").val();
	if(projectid == "" || projectid == null)
		{
			alert ("Select Project");
			return false;
		
		}
	var rep = "1";
	var reportTmp=new reports();
	if(rep=="1")
	{
		//console.log(villageSelected);
		reportTmp.ParcelCountByTenure("NEW",projectid, "1");
		//reportDialog.dialog( "destroy" );
	}
	}

function reports(){


}

reports.prototype.ParcelCountByTenure=function(tag,projectid,villageId){
//alert ("Under Function");
	window.open("landrecords/parcelcountbytenure/"+projectid+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

}

function reportButtonClick(){

	$('#reportByTenure').show();
	$('#registryReport').hide();
}

function registryButtonClick(){

	$('#reportByTenure').hide();
	$('#registryReport').show();
}

function generateProjectDetailedSummaryReport()
{
	projectid= $("#selectProjectsForDetailSummary").val();
	if(projectid == "" || projectid == null)
	{
		alert ("Select Project");
		return false;

	}
	var rep = "1";
	var reportTmp=new reports();
	if(rep=="1")
	{
		//console.log(villageSelected);
		reportTmp.ProjectDetailedSummaryReport("NEW",projectid, "1");
		// reportDialog.dialog( "destroy" );
	}

}

reports.prototype.ProjectDetailedSummaryReport=function(tag,projectid,villageId){
	//alert ("Under Function");
		window.open("landrecords/projectdetailedsummaryreport/"+projectid+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

	}

function generateProjectAppStatusSummaryReport()
{
	projectid= $("#selectProjectsForAppStatusSummary").val();
	if(projectid == "" || projectid == null)
	{
		alert ("Select Project");
		return false;

	}
	var rep = "1";
	var reportTmp=new reports();
	if(rep=="1")
	{
		//console.log(villageSelected);
		reportTmp.ProjectAppStatusSummaryReport("NEW",projectid, "1");
		// reportDialog.dialog( "destroy" );
	}

}

reports.prototype.ProjectAppStatusSummaryReport=function(tag,projectid,villageId){
	//alert ("Under Function");
		window.open("landrecords/projectdetailedappstatussummaryreport/"+projectid+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

	}


function generateProjectTypeStatusSummaryReport()
{
	projectid= $("#selectProjectsForAppTypeSummary").val();
	if(projectid == "" || projectid == null)
	{
		alert ("Select Project");
		return false;

	}
	var rep = "1";
	var reportTmp=new reports();
	if(rep=="1")
	{
		//console.log(villageSelected);
		reportTmp.ProjectTypeStatusSummaryReport("NEW",projectid, "1");
		// reportDialog.dialog( "destroy" );
	}

}

reports.prototype.ProjectTypeStatusSummaryReport=function(tag,projectid,villageId){
	//alert ("Under Function");
		window.open("landrecords/projectdetailedtypestatussummaryreport/"+projectid+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

	}

function generateProjectWorkFlowSummaryReport()
{
	projectid= $("#selectProjectsForWorkFlowSummary").val();
	if(projectid == "" || projectid == null)
	{
		alert ("Select Project");
		return false;

	}
	var rep = "1";
	var reportTmp=new reports();
	if(rep=="1")
	{
		//console.log(villageSelected);
		reportTmp.ProjectWorkFlowSummaryReport("NEW",projectid, "1");
		// reportDialog.dialog( "destroy" );
	}

}

reports.prototype.ProjectWorkFlowSummaryReport=function(tag,projectid,villageId){
	//alert ("Under Function");
		window.open("landrecords/projectdetailedworkflowsummaryreport/"+projectid+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

	}


function generateProjectDetailedSummaryReportForCommune()
{
	communeidbyhierarchyid= $("#CommuneId").val();
	if(communeidbyhierarchyid == "" || communeidbyhierarchyid == null)
	{
		alert ("Select Commune");
		return false;

	}
	var rep = "1";
	var reportTmp=new reports();
	if(rep=="1")
	{
		//console.log(villageSelected);
		reportTmp.ProjectDetailedSummaryReportForCommune("NEW",communeidbyhierarchyid, "1");
		// reportDialog.dialog( "destroy" );
	}

}


function getRegionOnCountryChange(id) {
	
	$("#districtId").empty();
	jQuery("#districtId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
	$("#CommuneId").empty();
	jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
	$("#placeId").empty();
	jQuery("#placeId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
	$("#regionId").empty();
    jQuery("#regionId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	

if (id != '') {
	jQuery.ajax({
        url: "projectregion/" + id,
        async: false,
        success: function (regiondata) {
            var proj_region = regiondata;
            jQuery.each(proj_region, function (i, value) {
                jQuery("#regionId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
            });
        }
    });
}



}

function getDistrictOnRegionChange(id) {

	$("#CommuneId").empty();
	jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
	$("#placeId").empty();
	jQuery("#placeId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
    $("#districtId").empty();
    jQuery("#districtId").append(jQuery("<option></option>").attr("value", "").text("Select"));


	
if (id != '') {

    jQuery.ajax({
        url: "projectdistrict/" + id,
        async: false,
        success: function (regiondata) {
            var proj_region = regiondata;
            jQuery.each(proj_region, function (i, value) {
                jQuery("#districtId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
            });
        }
    });
}


}



function getCommuneOnProvinceChange(id) {

$("#CommuneId").empty();
jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", "").text("Select"));

$("#placeId").empty();
jQuery("#placeId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
	
	
if (id != '') {
	
	
    jQuery.ajax({
        url: "projectvillage/" + id,
        async: false,
        success: function (regiondata) {
            var proj_coomune = regiondata;
            jQuery.each(proj_coomune, function (i, value) {
                jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
            });
        }
    });
}


}
function getHamletOnVillageChange(id) {
	
$("#villageId").empty();
jQuery("#villageId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
if (id != '') {
	
    jQuery.ajax({
        url: "projecthamlet/" + id,
        async: false,
        success: function (regiondata) {
            var proj_coomune = regiondata;
            jQuery.each(proj_coomune, function (i, value) {
                jQuery("#villageId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
            });
        }
    });
}


}


reports.prototype.ProjectDetailedSummaryReportForCommune=function(tag,communeidbyhierarchyid,villageId){
	//alert ("Under Function");
		window.open("landrecords/projectdetailedsummaryreportForCommune/"+communeidbyhierarchyid+"/"+tag+"/"+villageId,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');

	}
	
	
	function checkNumber(evt) {
	
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
	function loadDesceadPersonsForEditing() {
	    
		 $("#DesceadpersonsEditingGrid").jsGrid({
		        width: "100%",
		        height: "200px",
		        inserting: false,
		        editing: true,
		        sorting: true,
		        filtering: false,
		        paging: true,
		        autoload: false,
		        controller: DesceadpersonsEditingController,
		        pageSize: 50,
		        pageButtonCount: 20,
		        fields: [
		            {type: "control", deleteButton: false},
		            {name: "landid", title: "USIN", type: "number", width: 70, align: "left", editing: false, filtering: true},

		            {name: "firstName", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
		            {name: "middleName", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
		            {name: "lastName", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
		            {name: "gender", title: "Gender", type: "number", width: 120, validate: {validator: "required", message: "Enter Gender"}},
		            {name: "dob", title: "Date of birth", type: "date", width: 120 ,validate: {validator: "required", message: "Enter Date of birth"}},
		            {name: "relation", title: "Relation", type: "number", width: 120, validate: {validator: "required", message: "Enter Relation"}},

		            
		        ]
		    });
		 $("#DesceadpersonsEditingGrid .jsgrid-table th:first-child :button").click();
		$("#DesceadpersonsEditingGrid").jsGrid("loadData");
	}

	var DesceadpersonsEditingController = {
	    loadData: function (filter) {
	        $("#btnLoadPersons").val("Reload");
	        return $.ajax({
	            type: "GET",
	            url: "landrecords/deceasedperson/" + landId,
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

	
	
	
	
	function FillPersonDataNew()
	{
		// Init editing grid
	    $("#personsEditingGrid1").jsGrid({
	        width: "100%",
	        height: "200px",
	        inserting: false,
	        editing: true,
	        sorting: true,
	        filtering: false,
	        paging: true,
	        autoload: false,
	        controller: personsEditingControllerForPerson,
	        pageSize: 50,
	        pageButtonCount: 20,
	        fields: [
	            {type: "control", deleteButton: false},
	            {name: "firstname", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
	            {name: "middlename", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
	            {name: "lastname", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
	            {name: "address", title: "Address", type: "text", width: 120, validate: {validator: "required", message: "Enter Address"}},
	            {name: "identityno", title: "ID number", type: "text", width: 120, validate: {validator: "required", message: "Enter ID number"}},
	            
	            {name: "dateofbirth", title: "Date of birth", type: "date", width: 120, validate: {validator: "required", message: "Enter Date of birth"}},
	            {name: "contactno", title: "Mobile Number", type: "text", width: 120},
	            {name: "genderid", title: "Gender", align: "left", type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
	            {name: "laPartygroupIdentitytype.identitytypeid", title: "Id Type", type: "select", items: [{id: 1, name: "Voter ID"}, {id: 2, name: "Driving license"},{id: 3, name: "Passport"}, {id: 4, name: "ID card"},{id: 5, name: "Other"}, {id: 6, name: "None"}],valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
	            {name: "laPartygroupMaritalstatus.maritalstatusid", title: "Marital status", type: "select", items: [{id: 1, name: "Single"}, {id: 2, name: "Married"},{id: 3, name: "Divorced"}, {id: 4, name: "Widow"},{id: 5, name: "Widower"}], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
				{name: "laPartygroupEducationlevel.educationlevelid", title: "Education Level", type: "select", items: [{id: 1, name: "None"}, {id: 2, name: "Primary"},{id: 3, name: "Secondary"}, {id: 4, name: "University"}], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
				
     

	        ]
	    });

	    $("#personsEditingGrid1 .jsgrid-table th:first-child :button").click();
	   
	    $("#personsEditingGrid1").jsGrid("loadData");
	    
	    
	}
	
	
	
	
	var personsEditingControllerForPerson = {
		    loadData: function (filter) {
		        return $.ajax({
		            type: "GET",
		            url: "landrecords/personsDataNew/" + Personusin,
		            data: filter,
		            success: function(data)
		            {
		            	  $("#multimediaRowData").empty();
				          $("#naturalpersonmediaTemplate_add").tmpl(data).appendTo("#multimediaRowData");		  
		 }
		        });
		    },
		    insertItem: function (item) {
		    	
		    	
		    },
		    updateItem: function (item) {
		    	var dob = null;
		    	if(item!=null)
		    	{
		    		if(item.dateofbirth!=null && item.dateofbirth!='')
		    		{
		    			var d = new Date(item.dateofbirth);
		    			dob = d.getFullYear()+ '-' + d.getMonth() + '-' + d.getDate();
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
		            success:function(){
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
	
	
	// code for non-natural person
	
	function FillNonNaturalPersonDataNew()
	{
		// Init editing grid
	    $("#personsEditingGrid2").jsGrid({
	        width: "100%",
	        height: "200px",
	        inserting: false,
	        editing: true,
	        sorting: true,
	        filtering: false,
	        paging: true,
	        autoload: false,
	        controller: personsEditingControllerForNonNaturalPerson,
	        pageSize: 50,
	        pageButtonCount: 20,
	        fields: [
	            {type: "control", deleteButton: false},
	            {name: "organizationname", title: "organization Name", type: "text", width: 120, validate: {validator: "required", message: "Enter organization Name"}},
                {name: "groupType.grouptypeid", title: "GroupType", type: "select", items: [{id: 1, name: "Civic"}, {id: 2, name: "Mosque"},{id: 3, name: "Association"}, {id: 4, name: "Cooperative"},{id: 5, name: "Informal"}], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
	            {name: "contactno", title: "Mobile Number", type: "text", width: 120},
     

	        ]
	    });

	    $("#personsEditingGrid2 .jsgrid-table th:first-child :button").click();
	   
	    $("#personsEditingGrid2").jsGrid("loadData");
	    
	    
	}
	
	
	
	
	var personsEditingControllerForNonNaturalPerson = {
			 loadData: function (filter) {
			        $("#btnLoadPersons").val("Reload");
			        return $.ajax({
			            type: "GET",
			            url: "landrecords/NonNaturalpersonsDataNew/" + landId,
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
		
		$(window).resize(function() {

			 
			});
		
		
		function loadDisputeForEditing()
		{
			// Init editing grid
		    $("#DisputesGrid").jsGrid({
		        width: "100%",
		        height: "200px",
		        inserting: false,
		        editing: true,
		        sorting: true,
		        filtering: false,
		        paging: true,
		        autoload: true,
		        controller: DisputeEditingController,
		        pageSize: 50,
		        pageButtonCount: 20,
		        fields: [
		            {type: "control", deleteButton: false},
		            {name: "landid", title: "LandId", type: "text", width: 120,editing: false, validate: {validator: "required", message: "Enter LandId"}},
		            {name: "disputetypeid", title: "Dispute Type", align: "left", type: "select", items: [{id: 1, name: "Boundary"}, {id: 2, name: "Counter claim (Inter family)"},{id: 3, name: "Counter claim (Intra family)"},{id: 4, name: "Counter claim (Others)"},{id: 5, name: "Other interests"},], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
		            {name: "comment", title: "Comments", type: "text", width: 120, validate: {validator: "required", message: "Enter Comments"}},
		            {name: "status", title: "Status", type: "select", items: [{id: 1, name: "Active"}, {id: 2, name: "Resolved"}],valueField: "id", textField: "name", width: 80,editing: true, filtering: false}

		        ]
		    });

		    $("#DisputesGrid .jsgrid-table th:first-child :button").click();
		   
		    $("#DisputesGrid").jsGrid("loadData");
		    
		    
		}

		var DisputeEditingController = {
				 loadData: function (filter) {
				        $("#btnLoadPersons").val("Reload");
				        return $.ajax({
				            type: "GET",
				            url: "landrecords/disputes/" + landId,
				            async: false,
				            data: filter,
				            success: function(data)
				            {
//				            	$("#personsEditingGrid1").jsGrid("loadData");
				            	//console.log(data);
				            }
				        }).then(function(result){
				        	var persondataArray=[];
				        	if(result.disputeid){
				        		persondataArray.push(result);
				        		return persondataArray;
				        	}
				        	
				        });
				    },
			    
			    insertItem: function (item) {
			        return false;
			    },
			    updateItem: function (item) {
			    	
			    	var ajaxdata = {
			    			"disputeUsin": item.landid,
			    			"cbxDisputeTypes": item.disputetypeid,
			    			"txtDisputeDescription": item.comment,
			    			"Status": item.status
							}
			    	
			    	
			        return $.ajax({
			            type: "POST",
			            traditional: true,
			            url: "landrecords/updatedispute",
			            data: ajaxdata,
			            success:function(){
			            	$("#personsEditingGrid1").jsGrid("loadData");
							 $("#DisputesPersonGrid").jsGrid("loadData");
							 loadDisputeForEditing();
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
		
		
		
		function loadDisputePersonForEditing()
		{
			// Init editing grid
		    $("#DisputesPersonGrid").jsGrid({
		        width: "100%",
		        height: "200px",
		        inserting: false,
		        editing: true,
		        sorting: true,
		        filtering: false,
		        paging: true,
		        autoload: false,
		        controller: DisputepersonsEditingController,
		        pageSize: 50,
		        pageButtonCount: 20,
		        fields: [
		 	            {type: "control", deleteButton: false},
			            {name: "firstname", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
			            {name: "middlename", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
			            {name: "lastname", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
			            {name: "address", title: "Address", type: "text", width: 120, validate: {validator: "required", message: "Enter Address"}},
			            {name: "identityno", title: "ID number", type: "text", width: 120, validate: {validator: "required", message: "Enter ID number"}},
			            
			            {name: "dateofbirth", title: "Date of birth", type: "date", width: 120, validate: {validator: "required", message: "Enter Date of birth"}},
			            {name: "contactno", title: "Mobile Number", type: "text", width: 120},
			            {name: "genderid", title: "Gender", align: "left", type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
			            {name: "laPartygroupIdentitytype.identitytypeid", title: "Id Type", type: "select", items: [{id: 1, name: "Voter ID"}, {id: 2, name: "Driving license"},{id: 3, name: "Passport"}, {id: 4, name: "ID card"},{id: 5, name: "Other"}, {id: 6, name: "None"}],valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
			            {name: "laPartygroupMaritalstatus.maritalstatusid", title: "Marital status", type: "select", items: [{id: 1, name: "Single"}, {id: 2, name: "Married"},{id: 3, name: "Divorced"}, {id: 4, name: "Widow"},{id: 5, name: "Widower"}], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
						{name: "laPartygroupEducationlevel.educationlevelid", title: "Education Level", type: "select", items: [{id: 1, name: "None"}, {id: 2, name: "Primary"},{id: 3, name: "Secondary"}, {id: 4, name: "University"}], valueField: "id", textField: "name", width: 80,editing: true, filtering: false},
						
		     

			        ]
		    });

		    $("#DisputesPersonGrid .jsgrid-table th:first-child :button").click();
		   
		    $("#DisputesPersonGrid").jsGrid("loadData");
		    
		    
		}

		var DisputepersonsEditingController = {
				 loadData: function (filter) {
				        $("#btnLoadPersons").val("Reload");
				        return $.ajax({
				            type: "GET",
				            url: "landrecords/Disputeperson/" + landId,
				            async: false,
				            data: filter,
				            success: function(data)
				            {
				            	console.log(data);
				            }
				        })
				    },
			    
			    insertItem: function (item) {
			        return false;
			    },
			    updateItem: function (item) {
			    	var dob = null;
			    	if(item!=null)
			    	{
			    		if(item.dateofbirth!=null && item.dateofbirth!='')
			    		{
			    			var d = new Date(item.dateofbirth);
			    			dob = d.getFullYear()+ '-' + d.getMonth() + '-' + d.getDate();
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
							"educationlevel": item.laPartygroupEducationlevel.educationlevelid
							}
			    	
			    	return $.ajax({
			            type: "POST",
			            //contentType: "application/json; charset=utf-8",
			            traditional: true,
			            url: "landrecords/updateNaturalPersonDataForEdit",
			            //data: JSON.stringify(item),
			            data: ajaxdata,
			            success:function(){
			            	$("#personsEditingGrid1").jsGrid("loadData");
			            	$("#DisputesPersonGrid").jsGrid("loadData");
			            	
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
		
		
		function loadPersonsOfEditingForEditing() {
			 $("#personsEditingGrid").jsGrid({
			        width: "100%",
			        height: "200px",
			        inserting: false,
			        editing: true,
			        sorting: true,
			        filtering: false,
			        paging: true,
			        autoload: false,
			        controller: personsEditingController,
			        pageSize: 50,
			        pageButtonCount: 20,
			        fields: [
			            {type: "control", deleteButton: false},
			           // {name: "landid", title: "LandID", type: "number", width: 70, align: "left", editing: false, filtering: true},
			            {name: "firstName", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
			            {name: "middleName", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
			            {name: "lastName", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
			            {name: "gender", title: "Gender", type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}, {id: 3, name: "Other"}], valueField: "id", textField: "name", width: 120, validate: {validator: "required", message: "Enter first name"}},
			            {name: "dob", title: "Date of birth", type: "date", width: 120 ,validate: {validator: "required", message: "Enter first name"}},
			            {name: "relation", title: "Relation", type: "select", items: [{id: 1, name: "Father"}, {id: 2, name: "Mother"}, {id: 3, name: "Sister"}, {id: 4, name: "Brother"}, {id: 5, name: "Son"}], valueField: "id", textField: "name", width: 120, validate: {validator: "required", message: "Enter first name"}},
			          
			        ]
			    });
			 $("#personsEditingGrid .jsgrid-table th:first-child :button").click();
			$("#personsEditingGrid").jsGrid("loadData");
		}

		var personsEditingController = {
		    loadData: function (filter) {
		        $("#btnLoadPersons").val("Reload");
		        return $.ajax({
		            type: "GET",
		            url: "landrecords/personwithinterest/" + landId,
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
	
		
		function uploadMediaFile(id)
		{


			var flag=false;
			var val1 = 0;
			var formData = new FormData();
			var appid='#'+"mediafileUpload"+id;
			var file = $(""+appid+"")[0].files[0];

			var alias=$("#alias").val();

			 if(typeof(file)==="undefined")
			{
			
				jAlert('Please Select file to upload','upload');
			}

			

			else
			{
				
				$.each($(""+appid+"")[0].files,
						function(ind2, obj2) {
                        		$.each(	$(""+appid+"")[0].files[val1],
											function(ind3, obj3) {
												if (ind3 == "type") {
													if (obj3 == "image/png"
															|| obj3 == "image/jpeg"
															|| obj3 == "image/gif") {
														 flag =true;
													} else {
														 flag =false;
														jAlert("Select File should be of type png,jpeg,gif.");
														
													}
												}

											});
							val1 = val1 + 1;
						});
				
				if(flag){
				formData.append("spatialdata",file);
				formData.append("partyid", id);
				formData.append("transid", _transactionid);
				formData.append("landid", Personusin);
				$.ajax({
					url: 'upload/media/',
					type: 'POST',
					data:  formData,
					mimeType:"multipart/form-data",
					contentType: false,
					cache: false,
					processData:false,
					success: function(data, textStatus, jqXHR)
					{	
						
						
						if(data=="jpg"){
							jAlert('Please Enter multimedia file','Upload');
							
						}
						else
							{
							$(""+appid+"").val(null);

						jAlert('File uploaded','upload');
						//displayRefreshedProjectData(project);
//						displaySelectedCategory(project);
						$('#fileUploadSpatial').val('');
						$('#alias').val('');
							}

					}
					
				});
				}
				
			}
		}
		
		
		
		function deleteMediaFile(id){
			
			var formData = new FormData();
			
			formData.append("partyid", id);
			formData.append("transid", _transactionid);
			formData.append("landid", Personusin);
			
			$.ajax({
				url: 'delete/media/',
				type: 'POST',
				data:  formData,
				mimeType:"multipart/form-data",
				contentType: false,
				cache: false,
				processData:false,
				success: function(data, textStatus, jqXHR)
				{	
					

					jAlert('File Deleted Successfull');
					

				}
				
			});
		}
		
		
		
		function addnewNaturalperson(){
			            
		$("#personsEditingGrid1").jsGrid("insertItem",{});
		}