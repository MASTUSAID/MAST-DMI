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
var financialagency_R = null;
var monthoflease_R = null;
// var statusList_R = null;
var attributeEditDialog = null;
var documenttype_R = null;
var communeList=null;
var currentdiv = null;

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

	jQuery.ajax({
		url : "registryrecords/spatialunitcount/" + activeProject_R + "/" + 0,
		async : false,
		success : function(data) {
			totalRecords_R = data;
		}
	});

	jQuery.ajax({
		url : URL,
		async : false,
		success : function(data) {
			dataList_R = data;

		}
	});

	jQuery.ajax({
		url : "registration/maritalstatus/",
		async : false,
		success : function(data) {
			maritalStatus_R = data;
		}
	});

	jQuery.ajax({
		url : "landrecords/claimtypes/",
		async : false,
		success : function(data) {
			claimTypes_R = data;
		}
	});
	/*
	 * jQuery.ajax({ url: "landrecords/status/", async: false, success: function
	 * (data) { statusList_R = data; } });
	 */

	jQuery.ajax({
		url : "registration/genderstatus/",
		async : false,
		success : function(data) {
			genderStatus_R = data;
		}
	});

	jQuery.ajax({
		url : "registration/idtype/",
		async : false,
		success : function(data) {
			idtype_R = data;
		}
	});

	jQuery.ajax({
		url : "registration/landsharetypes/",
		async : false,
		success : function(data) {
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
		url : "registration/processdetails/",
		async : false,
		success : function(data) {
			processdetails_R = data;
		}
	});

	   jQuery.ajax({
        url: "landrecords/spatialunit/commune/" + activeProject,
        async: false,
        success: function (data) {
            communeList=data;
        }
 });
 
 
	// displayRefreshedRegistryRecords();

	displayRefreshedRegistryRecords_ABC();
}

function displayRefreshedRegistryRecords_ABC() {
	// var URL = "landrecords/";
	// if (activeProject_R != "") {
	// URL = "landrecords/" + activeProject_R;
	// }

	if (!landRecordsInitialized_R) {
		landRecordsInitialized_R = true;
		jQuery("#registryTab-div").empty();
		jQuery.get("resources/templates/viewer/" + selectedItem_R + ".html",
				function(template) {
					jQuery("#registryTab-div").append(template);
					jQuery('#registryRecordsFormdiv').css("visibility",
							"visible");
					jQuery("#registryRecordsTable").show();

					jQuery("#registryRecordsRowData1").empty();
					jQuery("#community_id_R").empty();
				jQuery("#community_id_R").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
				jQuery.each(communeList, function (i, CommuneObj) {
                 jQuery("#community_id_R").append(jQuery("<option></option>").attr("value", CommuneObj.communeid).text(CommuneObj.commune));
             });
			 
			 

					if (dataList_R.length != 0
							&& dataList_R.length != undefined) {
						jQuery("#registryRecordsAttrTemplate1")
								.tmpl(dataList_R).appendTo(
										"#registryRecordsRowData1");
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
									"Please Select"));
					jQuery.each(claimTypes_R, function(i, claimType_R) {
						jQuery("#claim_type_R").append(
								jQuery("<option></option>").attr("value",
										claimType_R.code)
										.text(claimType_R.name));
					});

				});
	}

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
				function(template) {
					jQuery("#registryTab-div").append(template);
					jQuery('#registryRecordsFormdiv').css("visibility",
							"visible");
					jQuery("#registryRecordsTable").show();
				});
	}

	jQuery.ajax({
		url : URL,
		success : function(data) {
			projList_R = data;

			jQuery("#registryRecordsRowData").empty();

			if (dataList_R.length != 0 && dataList_R.length != undefined) {
				jQuery("#registryRecordsAttrTemplate").tmpl(dataList_R[0])
						.appendTo("#registryRecordsRowData");
			}

			jQuery("#status_id").empty();

			jQuery("#status_id").append(
					jQuery("<option></option>").attr("value", 0).text(
							"Please Select"));

			jQuery.each(statusList, function(i, statusobj) {
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
					sortList : [ [ 0, 1 ], [ 1, 0 ] ]
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
		alert("No records found");
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
				alert("No records found");
			}
		}
		RegistrationRecords_R(records_from_R);

	} else {
		alert("No records found");
	}

}

function RegistrationRecords_R(records_from_R) {

	jQuery.ajax({
		url : "registryrecords/spatialunit/" + activeProject_R + "/"
				+ records_from_R,
		async : false,
		success : function(data) {
			jQuery("#registryRecordsRowData1").empty();

			if (data.length != 0 && data.length != undefined) {
				jQuery("#registryRecordsRowData1").empty();
				jQuery("#registryRecordsAttrTemplate1").tmpl(data).appendTo(
						"#registryRecordsRowData1");
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

	jQuery("#registryRecordsRowData1").empty();

	if (dataList_R.length != 0 && dataList_R.length != undefined) {
		jQuery("#registryRecordsAttrTemplate1").tmpl(dataList_R).appendTo(
				"#registryRecordsRowData1");
	}

}

function search_R() {
	
	var transactionidIdf1 = $("#usinstr_id_R").val();
	var community_id_R=$("#community_id_R").val();
	var parce_id_R=$("#parce_id_R").val();


	searchRecords_R = null;
	records_from_R = 0;
	if (transactionidIdf1 == "" && parce_id_R == "" &&  community_id_R =="") {

		alert("Please Enter search criteria");
		return;
	}
	jQuery("#registryRecordsRowData1").empty();

	jQuery.ajax({
		type : "POST",
		async : false,
		url : "registration/search1Count/" + activeProject_R + "/"
				+ records_from_R,
		data : jQuery("#registryrecordsform").serialize(),
		success : function(result) {
			searchRecords_R = result;
		}
	});

	RegistrationSearch_R(records_from_R);
}

function RegistrationSearch_R(records_from_R) {

	jQuery.ajax({
		type : "POST",
		async : false,
		url : "registration/search1/" + activeProject_R + "/" + records_from_R,
		data : jQuery("#registryrecordsform").serialize(),
		success : function(result) {
			if (result.length != undefined && result.length != 0) {
				jQuery("#registryRecordsAttrTemplate1").tmpl(result).appendTo(
						"#registryRecordsRowData1");
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
		function() {
			// Add date field
			var DateField = function(config) {
				jsGrid.Field.call(this, config);
			};

			jQuery.ajax({
				url : "registration/landtype/",
				async : false,
				success : function(data) {
					landtype_r = data;
					if (data.length > 0) {

					}
				}
			});

			jQuery.ajax({
				url : "registration/allcountry/",
				async : false,
				success : function(data) {
					allcountry = data;
					if (data.length > 0) {
						// data.xyz.name_en for getting the data
					}
				}
			});

			DateField.prototype = new jsGrid.Field({
				sorter : function(date1, date2) {
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
				itemTemplate : function(value) {
					if (value !== null && value !== "") {
						return formatDate_R(value);
					}
					return "";
				},
				editTemplate : function(value) {
					if (value === null || value === "")
						return this._editPicker = $("<input>").datepicker({
							dateFormat : "yy-mm-dd"
						});
					else
						return this._editPicker = $("<input>").datepicker({
							dateFormat : "yy-mm-dd"
						}).datepicker("setDate", new Date(value));
				},
				insertValue : function() {
//					return this._insertPicker.datepicker("getDate");
					alert("zdfdfssdbfgc");
				},
				editValue : function() {
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

	var lease = document.getElementById("Tab_1");
	var sale = document.getElementById("Tab_2");
	var mortgage = document.getElementById("Tab_3");
	lease.style.display = "none"; // lease.style.display = "block";
	sale.style.display = "none";
	mortgage.style.display = "none";

	var selectedprocess = $("#registration_process").val();
	
	
	
	
	selectedlandid = landid;
	$("#landidhide").val(landid);
	/*
	 * jQuery.ajax({ url: "registration/landsharetype111111/" , async: false,
	 * success: function (data) { landsharetype=data; alert('1');
	 * if(data.length>0){
	 *  } } });
	 */

	jQuery.ajax({
		url : "registration/doctype/",
		async : false,
		success : function(data) {
			documenttype_R = data;
		}
	});

	jQuery.ajax({
		url : "registration/monthoflease/",
		async : false,
		success : function(data) {
			monthoflease_R = data;
		}
	});

	jQuery.ajax({
		url : "registration/financialagency/",
		async : false,
		success : function(data) {
			financialagency_R = data;
		}
	});

	jQuery.ajax({
		url : "registration/laspatialunitland/" + landid,
		async : false,
		success : function(data) {
			laspatialunitland_R = data;
			if (data.length > 0) {

			}
		}
	});
	
	jQuery.ajax({
		url : "registration/relationshiptypes/",
		async : false,
		success : function(data) {
			relationtypes_R = data;
			
		}
	});

	// -------------------------------------------------------------------------------------------------------------------
	// onchange of Country pass this select id for country_r_id --
	jQuery.ajax({
		url : "registration/allregion/" + country_r_id,
		async : false,
		success : function(data) {
			region_r = data;
			if (data.length > 0) {
				// data.xyz.name_en for getting the data
			}
		}
	});

	jQuery.ajax({
		url : "registration/allprovince/" + region_r_id,
		async : false,
		success : function(data) {
			province_r = data;
		}
	});
	
	
	jQuery.ajax({
		url : "registration/islandunderlease/" + landid,
		async : false,
		success : function(data) 
		{
			if(data == true)
			{	
				jConfirm('This Land is under Lease. Do you want to continue ?','Confirmation',
						function(response) {
							if (response) {

								//jAlert('This Land is under Lease.');	

								jQuery
								.ajax({
									url : "registration/partydetails/sale/" + landid,
									async : false,
									success : function(objdata) 
									{
										data = objdata[0];
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

										jQuery.each(maritalStatus_R, function(i, obj) {
											jQuery("#sale_martial_status").append(
													jQuery("<option></option>").attr("value",
															obj.maritalstatusid).text(
																	obj.maritalstatusEn));
										});

										jQuery("#sale_martial_status").val(data.maritalstatusid);

										jQuery.each(genderStatus_R, function(i, obj1) {
											jQuery("#sale_gender").append(
													jQuery("<option></option>").attr("value",
															obj1.genderId).text(obj1.gender_en));
										});

										jQuery("#sale_gender").val(data.genderid);

										jQuery.each(idtype_R, function(i, obj1) {
											jQuery("#sale_id_type").append(
													jQuery("<option></option>").attr("value",
															obj1.identitytypeid).text(
																	obj1.identitytypeEn));
										});
										jQuery("#sale_id_type").val(data.identitytypeid);

										
									
										if(objdata != null && objdata.length >1)
											{
												
											if(objdata[1] != null)
											{
												data1 = objdata[1];
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

												jQuery.each(maritalStatus_R, function(i, obj) {
													jQuery("#sale_martial_status1second").append(
															jQuery("<option></option>").attr("value",
																	obj.maritalstatusid).text(
																			obj.maritalstatusEn));
												});

												jQuery("#sale_martial_status1second").val(data.maritalstatusid);

												jQuery.each(genderStatus_R, function(i, obj1) {
													jQuery("#sale_gender1second").append(
															jQuery("<option></option>").attr("value",
																	obj1.genderId).text(obj1.gender_en));
												});

												jQuery("#sale_gender1second").val(data.genderid);

												jQuery.each(idtype_R, function(i, obj1) {
													jQuery("#sale_id_type1second").append(
															jQuery("<option></option>").attr("value",
																	obj1.identitytypeid).text(
																			obj1.identitytypeEn));
												});
												jQuery("#sale_id_type1second").val(data.identitytypeid);
												
												$("#SecondOwner").css("display","block");
											
											}
											
											
											jQuery("#Owner_Elimanated").empty();
												jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
												
												jQuery.each(objdata, function(i, obj1) 
												{
													jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value",obj1.personid).text(obj1.firstname));
												});
											}
										else if(objdata != null && objdata.length ==1) 
											{
												jQuery("#Owner_Elimanated").empty();
												jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
												
												jQuery.each(objdata, function(i, obj1) 
												{
													jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value",obj1.personid).text(obj1.firstname));
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
												"Please Select"));
										jQuery("#sale_gender_buyer").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));
										jQuery("#sale_idtype_buyer").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));
										jQuery("#doc_Type_Sale").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));

										jQuery.each(maritalStatus_R, function(i, obj1) {
											jQuery("#sale_marital_buyer").append(
													jQuery("<option></option>").attr("value",
															obj1.maritalstatusid).text(
																	obj1.maritalstatusEn));
										});
										jQuery.each(genderStatus_R, function(i, obj1) {
											jQuery("#sale_gender_buyer").append(
													jQuery("<option></option>").attr("value",
															obj1.genderId).text(obj1.gender_en));
										});
										jQuery.each(idtype_R, function(i, obj1) {
											jQuery("#sale_idtype_buyer").append(
													jQuery("<option></option>").attr("value",
															obj1.identitytypeid).text(
																	obj1.identitytypeEn));
										});

										jQuery.each(documenttype_R, function(i, obj) {
											jQuery("#doc_Type_Sale").append(
													jQuery("<option></option>").attr("value",
															obj.code).text(obj.nameOtherLang));
										});

										// Land Details landtype_r
										jQuery("#sale_land_type").empty();
										jQuery("#sale_country").empty();
										jQuery("#sale_region").empty();
										jQuery("#province_r").empty();
										jQuery("#sale_land_Share_type").empty();
										jQuery("#sale_province").empty();

										jQuery("#sale_country").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));
										jQuery("#sale_region").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));
										jQuery("#sale_province").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));
										jQuery("#sale_land_type").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));
										jQuery("#sale_land_Share_type").append(
												jQuery("<option></option>").attr("value", 0).text(
												"Please Select"));

										jQuery.each(landtype_r,
												function(i, obj1) {
											jQuery("#sale_land_type").append(
													jQuery("<option></option>").attr(
															"value", obj1.landtypeid).text(
																	obj1.landtypeEn));
										});
										jQuery.each(allcountry, function(i, obj) {
											jQuery("#sale_country").append(
													jQuery("<option></option>").attr("value",
															obj.hierarchyid).text(obj.nameEn));
										});

										jQuery.each(region_r, function(i, obj) {
											jQuery("#sale_region").append(
													jQuery("<option></option>").attr("value",
															obj.hierarchyid).text(obj.nameEn));
										});
										jQuery.each(province_r, function(i, obj) {
											jQuery("#sale_province").append(
													jQuery("<option></option>").attr("value",
															obj.hierarchyid).text(obj.nameEn));
										});

										jQuery.each(landsharetype_r, function(i, obj) {
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

										jQuery("#parcel_r").val(laspatialunitland_R[0].landno);
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

										// processdetails_R

										jQuery("#registration_process").empty();
										jQuery("#registration_process").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
										jQuery.each(processdetails_R, function(i, obj) 
										{
											if(obj.processid == 5)
												{
													jQuery("#registration_process").append(jQuery("<option></option>").attr("value", obj.processid).text(obj.processname_en));
												}
											
										});

									}
								});

								$(function() {

									$("#Tab_1").hide();
									$("#Tab_2").hide();
									$("#Tab_3").hide();

									attributeEditDialog = $("#lease-dialog-form").dialog({
										autoOpen : false,
										height : 600,
										width : 1000,
										resizable : true,
										modal : true,

										buttons : [ {
											text : "Next",
											"id" : "comment_Next",
											click : function()
											{
												if(currentdiv == "Sale")
												{
													//var selectedtab = document.getElementsByClassName("aria-selected");
													if($('#Seller_Details').css('display') == 'block')
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

													}
													else if($('#Land_Details_Sale').css('display') == 'block')
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

													}
													else if($('#Buyer_Details').css('display') == 'block')
													{
														$("#Seller_Details").hide();
														$("#Buyer_Details").hide();
														$("#Land_Details_Sale").hide();
														$("#Upload_Document_Sale").show();
														$("#selectedbuyer").removeClass("ui-tabs-active");
														$("#selectedbuyer").removeClass("ui-state-active");	
														$("#selecteddoc").addClass("ui-tabs-active");
														$("#selecteddoc").addClass("ui-state-active");
														$("#comment_Save").show();
														$("#comment_Next").hide();
													}	
												}
												else if (currentdiv == "Lease")
												{
													alert("Under Lease");
												}
												else
												{
													alert("Under Mortage");
												}					
											}

										} ,
										{
											text : "Save",
											"id" : "comment_Save",
											click : function()
											{
												if(currentdiv == "Sale")
												{							
													saveattributessale();
												}
												else if (currentdiv == "Lease")
												{
													saveattributesLease();
												}
												else
												{
													saveattributesMortgage();
												}					
											}

										},

										{
											text : "Cancel",
											"id" : "comment_cancel",
											click : function() {
												setInterval(function() {

												}, 4000);

												attributeEditDialog.dialog("close");

											}

										}


										],
										Cancel : function() {

											attributeEditDialog.dialog("close");

										}
									});
									$("#comment_cancel").html('<span class="ui-button-text">Cancel</span>');
									attributeEditDialog.dialog("open");
									$("#comment_Save").hide();
									$("#comment_Next").hide();

								});
							}
						});
				
			}
			else
			{
				// jAlert('This Land is not under Lease.');

				jQuery
				.ajax({
					url : "registration/partydetails/sale/" + landid,
					async : false,
					success : function(objdata) {
						data = objdata[0];
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

						jQuery.each(maritalStatus_R, function(i, obj) {
							jQuery("#sale_martial_status").append(
									jQuery("<option></option>").attr("value",
											obj.maritalstatusid).text(
													obj.maritalstatusEn));
						});

						jQuery("#sale_martial_status").val(data.maritalstatusid);

						jQuery.each(genderStatus_R, function(i, obj1) {
							jQuery("#sale_gender").append(
									jQuery("<option></option>").attr("value",
											obj1.genderId).text(obj1.gender_en));
						});

						jQuery("#sale_gender").val(data.genderid);

						jQuery.each(idtype_R, function(i, obj1) {
							jQuery("#sale_id_type").append(
									jQuery("<option></option>").attr("value",
											obj1.identitytypeid).text(
													obj1.identitytypeEn));
						});
						jQuery("#sale_id_type").val(data.identitytypeid);
						
						
						
						if(objdata != null && objdata.length >1)
						{
							if(objdata[1] != null)
							{
								data1 = objdata[1];
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

								jQuery.each(maritalStatus_R, function(i, obj) {
									jQuery("#sale_martial_status1second").append(
											jQuery("<option></option>").attr("value",
													obj.maritalstatusid).text(
															obj.maritalstatusEn));
								});

								jQuery("#sale_martial_status1second").val(data.maritalstatusid);

								jQuery.each(genderStatus_R, function(i, obj1) {
									jQuery("#sale_gender1second").append(
											jQuery("<option></option>").attr("value",
													obj1.genderId).text(obj1.gender_en));
								});

								jQuery("#sale_gender1second").val(data.genderid);

								jQuery.each(idtype_R, function(i, obj1) {
									jQuery("#sale_id_type1second").append(
											jQuery("<option></option>").attr("value",
													obj1.identitytypeid).text(
															obj1.identitytypeEn));
								});
								jQuery("#sale_id_type1second").val(data.identitytypeid);
							
							}
							
							jQuery("#Owner_Elimanated").empty();
							jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
							
							jQuery.each(objdata, function(i, obj1) 
							{
								jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value",obj1.personid).text(obj1.firstname));
							});
							
							$("#SecondOwner").css("display","block");
						}
					else if(objdata != null && objdata.length ==1) 
						{
							jQuery("#Owner_Elimanated").empty();
							jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value", "").text("Please Select"));							
							jQuery.each(objdata, function(i, obj1) 
							{
								jQuery("#Owner_Elimanated").append(jQuery("<option></option>").attr("value",obj1.personid).text(obj1.firstname));
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
								"Please Select"));
						jQuery("#sale_gender_buyer").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));
						jQuery("#sale_idtype_buyer").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));
						jQuery("#doc_Type_Sale").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));

						jQuery.each(maritalStatus_R, function(i, obj1) {
							jQuery("#sale_marital_buyer").append(
									jQuery("<option></option>").attr("value",
											obj1.maritalstatusid).text(
													obj1.maritalstatusEn));
						});
						jQuery.each(genderStatus_R, function(i, obj1) {
							jQuery("#sale_gender_buyer").append(
									jQuery("<option></option>").attr("value",
											obj1.genderId).text(obj1.gender_en));
						});
						jQuery.each(idtype_R, function(i, obj1) {
							jQuery("#sale_idtype_buyer").append(
									jQuery("<option></option>").attr("value",
											obj1.identitytypeid).text(
													obj1.identitytypeEn));
						});

						jQuery.each(documenttype_R, function(i, obj) {
							jQuery("#doc_Type_Sale").append(
									jQuery("<option></option>").attr("value",
											obj.code).text(obj.nameOtherLang));
						});

						// Land Details landtype_r
						jQuery("#sale_land_type").empty();
						jQuery("#sale_country").empty();
						jQuery("#sale_region").empty();
						jQuery("#province_r").empty();
						jQuery("#sale_land_Share_type").empty();
						jQuery("#sale_province").empty();

						jQuery("#sale_country").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));
						jQuery("#sale_region").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));
						jQuery("#sale_province").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));
						jQuery("#sale_land_type").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));
						jQuery("#sale_land_Share_type").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));

						jQuery.each(landtype_r,
								function(i, obj1) {
							jQuery("#sale_land_type").append(
									jQuery("<option></option>").attr(
											"value", obj1.landtypeid).text(
													obj1.landtypeEn));
						});
						jQuery.each(allcountry, function(i, obj) {
							jQuery("#sale_country").append(
									jQuery("<option></option>").attr("value",
											obj.hierarchyid).text(obj.nameEn));
						});

						jQuery.each(region_r, function(i, obj) {
							jQuery("#sale_region").append(
									jQuery("<option></option>").attr("value",
											obj.hierarchyid).text(obj.nameEn));
						});
						jQuery.each(province_r, function(i, obj) {
							jQuery("#sale_province").append(
									jQuery("<option></option>").attr("value",
											obj.hierarchyid).text(obj.nameEn));
						});

						jQuery.each(landsharetype_r, function(i, obj) {
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

						jQuery("#parcel_r").val(laspatialunitland_R[0].landno);
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

						// processdetails_R

						/*jQuery("#registration_process").empty();
						jQuery("#registration_process").append(
								jQuery("<option></option>").attr("value", 0).text(
								"Please Select"));
						jQuery.each(processdetails_R, function(i, obj) {
							jQuery("#registration_process")
							.append(
									jQuery("<option></option>").attr(
											"value", obj.processid).text(
													obj.processname_en));
						});*/
						jQuery("#registration_process").empty();
						jQuery("#registration_process").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
						jQuery.each(processdetails_R, function(i, obj) 
						{
							if(obj.processid != 5)
								{
									jQuery("#registration_process").append(jQuery("<option></option>").attr("value", obj.processid).text(obj.processname_en));
								}
							
						});
						

					}
				});

				$(function() {

					$("#Tab_1").hide();
					$("#Tab_2").hide();
					$("#Tab_3").hide();

					attributeEditDialog = $("#lease-dialog-form").dialog({
						autoOpen : false,
						height : 600,
						width : 1000,
						resizable : true,
						modal : true,

						buttons : [ {
							text : "Next",
							"id" : "comment_Next",
							click : function()
							{
								if(currentdiv == "Sale")
								{
									//var selectedtab = document.getElementsByClassName("aria-selected");
									if($('#Seller_Details').css('display') == 'block')
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

									}
									else if($('#Land_Details_Sale').css('display') == 'block')
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

									}
									else if($('#Buyer_Details').css('display') == 'block')
									{
										$("#Seller_Details").hide();
										$("#Buyer_Details").hide();
										$("#Land_Details_Sale").hide();
										$("#Upload_Document_Sale").show();
										$("#selectedbuyer").removeClass("ui-tabs-active");
										$("#selectedbuyer").removeClass("ui-state-active");	
										$("#selecteddoc").addClass("ui-tabs-active");
										$("#selecteddoc").addClass("ui-state-active");
										$("#comment_Save").show();
										$("#comment_Next").hide();
									}	
								}
								else if (currentdiv == "Lease")
								{
									alert("Under Lease");
								}
								else
								{
									alert("Under Mortage");
								}					
							}

						} ,
						{
							text : "Save",
							"id" : "comment_Save",
							click : function()
							{
								if(currentdiv == "Sale")
								{							
									saveattributessale();
								}
								else if (currentdiv == "Lease")
								{
									saveattributesLease();
								}
								else
								{
									saveattributesMortgage();
								}					
							}

						},

						{
							text : "Cancel",
							"id" : "comment_cancel",
							click : function() {
								setInterval(function() {

								}, 4000);

								attributeEditDialog.dialog("close");

							}

						}


						],
						Cancel : function() {

							attributeEditDialog.dialog("close");

						}
					});
					$("#comment_cancel").html('<span class="ui-button-text">Cancel</span>');
					attributeEditDialog.dialog("open");
					$("#comment_Save").hide();
					$("#comment_Next").hide();

				});

			}
		}
	});

	

}


function fillSurrenderLeaseDetails(landid)
{
	jQuery
	.ajax({
		url : "registration/partydetailssurrenderlease/" + landid,
		async : false,
		success : function(data) {
			
			jQuery("#firstname_r_applicant").val(data.firstname);
			jQuery("#middlename_r_applicant").val(data.middlename);
			jQuery("#lastname_r_applicant").val(data.lastname);
			jQuery("#id_r_applicant").val(data.identityno);
			jQuery("#contact_no_applicant").val(data.contactno);
			jQuery("#address_lease_applicant").val(data.address);
			jQuery("#date_Of_birth_applicant").val(data.dob);
			jQuery("#lease_Amount").val(data.hierarchyid1);
			jQuery("#no_Of_month_Lease").val(data.hierarchyid2);
			jQuery("#martial_sts_applicant").empty();
			jQuery("#gender_type_applicant").empty();
			jQuery("#id_type_applicant").empty();

			jQuery.each(maritalStatus_R, function(i, obj) {
				jQuery("#martial_sts_applicant").append(
						jQuery("<option></option>").attr("value",
								obj.maritalstatusid).text(
								obj.maritalstatusEn));
			});

			jQuery("#martial_sts_applicant").val(data.maritalstatusid);

			jQuery.each(genderStatus_R, function(i, obj1) {
				jQuery("#gender_type_applicant").append(
						jQuery("<option></option>").attr("value",
								obj1.genderId).text(obj1.gender_en));
			});

			jQuery("#gender_type_applicant").val(data.genderid);

			jQuery.each(idtype_R, function(i, obj1) {
				jQuery("#id_type_applicant").append(
						jQuery("<option></option>").attr("value",
								obj1.identitytypeid).text(
								obj1.identitytypeEn));
			});
			jQuery("#id_type_applicant").val(data.identitytypeid);
		
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

function fillMortgageDetails(data) {

	jQuery("#firstname_r_mortgage").val(data.firstname);
	jQuery("#middlename_r_mortgage").val(data.middlename);
	jQuery("#lastname_r_mortgage").val(data.lastname);
	jQuery("#id_r_mortgage").val(data.identityno);
	jQuery("#contact_no_mortgage").val(data.contactno);
	jQuery("#address_mortgage").val(data.address);
	jQuery("#date_Of_birth_mortgage").val(data.dob);

	jQuery("#Martial_sts_mortgage").empty();
	jQuery("#gender_mortgage").empty();
	jQuery("#id_type_mortgage").empty();
	jQuery("#mortgage_Financial_Agencies").empty();
	jQuery("#doc_Type_Mortgage").empty();

	jQuery("#Martial_sts_mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#gender_mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#id_type_mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#mortgage_Financial_Agencies").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#doc_Type_Mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));

	jQuery.each(documenttype_R, function(i, obj) {
		jQuery("#doc_Type_Mortgage").append(
				jQuery("<option></option>").attr("value", obj.code).text(
						obj.nameOtherLang));
	});

	jQuery.each(financialagency_R, function(i, obj1) {
		jQuery("#mortgage_Financial_Agencies").append(
				jQuery("<option></option>").attr("value",
						obj1.financialagencyid).text(obj1.financialagency_en));
	});

	jQuery.each(maritalStatus_R, function(i, obj) {
		jQuery("#Martial_sts_mortgage").append(
				jQuery("<option></option>").attr("value", obj.maritalstatusid)
						.text(obj.maritalstatusEn));
	});

	jQuery("#Martial_sts_mortgage").val(data.maritalstatusid);

	jQuery.each(genderStatus_R, function(i, obj1) {
		jQuery("#gender_mortgage").append(
				jQuery("<option></option>").attr("value", obj1.genderId).text(
						obj1.gender_en));
	});

	jQuery("#gender_mortgage").val(data.genderid);

	jQuery.each(idtype_R, function(i, obj1) {
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
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#Region_mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#Provience_mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#Land_type_mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#Ownership_type_mortgage").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));

	jQuery.each(allcountry, function(i, obj) {
		jQuery("#Country_mortgage").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});

	jQuery.each(region_r, function(i, obj) {
		jQuery("#Region_mortgage").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});
	jQuery.each(province_r, function(i, obj) {
		jQuery("#Provience_mortgage").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});

	jQuery.each(landsharetype_r, function(i, obj) {
		jQuery("#Ownership_type_mortgage").append(
				jQuery("<option></option>").attr("value", obj.landsharetypeid)
						.text(obj.landsharetypeEn));
	});
	jQuery.each(landtype_r, function(i, obj1) {
		jQuery("#Land_type_mortgage").append(
				jQuery("<option></option>").attr("value", obj1.landtypeid)
						.text(obj1.landtypeEn));
	});

	jQuery("#Country_mortgage").val(laspatialunitland_R[0].hierarchyid1);
	jQuery("#Region_mortgage").val(laspatialunitland_R[0].hierarchyid2);
	jQuery("#Provience_mortgage").val(laspatialunitland_R[0].hierarchyid3);
	jQuery("#Land_type_mortgage").val(laspatialunitland_R[0].landtypeid);
	jQuery("#parcel_r_mortgage").val(laspatialunitland_R[0].landno);
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
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#lease_gender").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#lease_id_type").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#doc_Type_Lease").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	// jQuery("#no_Of_month_Lease").append(jQuery("<option></option>").attr("value",
	// 0).text("Please Select"));

	/*
	 * jQuery.each(monthoflease_R, function (i, obj) {
	 * jQuery("#no_Of_month_Lease").append(jQuery("<option></option>").attr("value",
	 * obj.monthid).text(obj.month)); });
	 */

	jQuery.each(documenttype_R, function(i, obj) {
		jQuery("#doc_Type_Lease").append(
				jQuery("<option></option>").attr("value", obj.code).text(
						obj.nameOtherLang));
	});

	jQuery.each(maritalStatus_R, function(i, obj) {
		jQuery("#lease_martial_sts").append(
				jQuery("<option></option>").attr("value", obj.maritalstatusid)
						.text(obj.maritalstatusEn));
	});

	jQuery("#lease_martial_sts").val(data.maritalstatusid);

	jQuery.each(genderStatus_R, function(i, obj1) {
		jQuery("#lease_gender").append(
				jQuery("<option></option>").attr("value", obj1.genderId).text(
						obj1.gender_en));
	});

	jQuery("#lease_gender").val(data.genderid);

	jQuery.each(idtype_R, function(i, obj1) {
		jQuery("#lease_id_type").append(
				jQuery("<option></option>").attr("value", obj1.identitytypeid)
						.text(obj1.identitytypeEn));
	});
	jQuery("#lease_id_type").val(data.identitytypeid);

	jQuery("#id_type_applicant").empty();
	jQuery("#gender_type_applicant").empty();
	jQuery("#martial_sts_applicant").empty();
	jQuery("#id_type_applicant").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#gender_type_applicant").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#martial_sts_applicant").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));

	jQuery.each(maritalStatus_R, function(i, obj) {
		jQuery("#martial_sts_applicant").append(
				jQuery("<option></option>").attr("value", obj.maritalstatusid)
						.text(obj.maritalstatusEn));
	});

	jQuery.each(genderStatus_R, function(i, obj1) {
		jQuery("#gender_type_applicant").append(
				jQuery("<option></option>").attr("value", obj1.genderId).text(
						obj1.gender_en));
	});

	jQuery.each(idtype_R, function(i, obj1) {
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
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#lease_Region").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#lease_Provience").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#lease_Land_Type").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#lease_Ownership_Type").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));

	jQuery.each(allcountry, function(i, obj) {
		jQuery("#lease_Country").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});

	jQuery.each(region_r, function(i, obj) {
		jQuery("#lease_Region").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});
	jQuery.each(province_r, function(i, obj) {
		jQuery("#lease_Provience").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});

	jQuery.each(landsharetype_r, function(i, obj) {
		jQuery("#lease_Ownership_Type").append(
				jQuery("<option></option>").attr("value", obj.landsharetypeid)
						.text(obj.landsharetypeEn));
	});
	jQuery.each(landtype_r, function(i, obj1) {
		jQuery("#lease_Land_Type").append(
				jQuery("<option></option>").attr("value", obj1.landtypeid)
						.text(obj1.landtypeEn));
	});

	jQuery("#lease_Country").val(laspatialunitland_R[0].hierarchyid1);
	jQuery("#lease_Region").val(laspatialunitland_R[0].hierarchyid2);
	jQuery("#lease_Provience").val(laspatialunitland_R[0].hierarchyid3);
	jQuery("#lease_Land_Type").val(laspatialunitland_R[0].landtypeid);
	jQuery("#parcel_r_lease").val(laspatialunitland_R[0].landno);
	jQuery("#lease_Ownership_Type").val(laspatialunitland_R[0].landusetypeid);
	jQuery("#lease_area").val(laspatialunitland_R[0].area);
	//jQuery("#lease_land_use").val(laspatialunitland_R[0].landusetype_en);

	jQuery("#neighbor_west_lease").val(laspatialunitland_R[0].neighbor_west);
	jQuery("#neighbor_north_lease").val(laspatialunitland_R[0].neighbor_north);
	jQuery("#neighbor_south_lease").val(laspatialunitland_R[0].neighbor_south);
	jQuery("#neighbor_east_lease").val(laspatialunitland_R[0].neighbor_east);

}
function saveattributessale() {
	
	var selectedprocess = $("#registration_process").val();
	if(selectedprocess== 7)
	{
		var eliminatedowner = $("#Owner_Elimanated").val();
		if(eliminatedowner == null || eliminatedowner == "")
			{
				jAlert("Please select Eliminated owner ", "Alert");
				return false;
			}
		
	}
	
	$("#editprocessAttributeformID").validate({
		rules : {
			doc_name_sale : "required",
			doc_date_sale : "required",
			doc_desc_sale : "required",
			doc_Type_Sale : "required",
			remrks_sale : "required"
		},
		messages : {
			doc_name_sale : "Please Enter Document name",
			doc_date_sale : "Please Enter Document uploded date",
			doc_desc_sale : "Please Document description",
			doc_Type_Sale : "Please Select Document type",
			remrks_sale : "Please enter remrks"
		}
	});

	if ($("#editprocessAttributeformID").valid()) {

		if ((doc_name_sale.value.length == 0)
				|| (doc_date_sale.value.length == 0)
				|| (doc_desc_sale.value.length == 0)
				|| (remrks_sale.value.length == 0)) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}

		if ($("#fileUploadNewDowcumentss").val() === '') {
			jAlert("Select File for upload.");
			return;
		}

		var val1 = 0;
		$.each($('#fileUploadNewDowcumentss')[0].files, function(ind2, obj2) {

			$.each($('#fileUploadNewDowcumentss')[0].files[val1], function(
					ind3, obj3) {
				if (ind3 == "type") {

					if (obj3 == "image/png" || obj3 == "image/jpeg"
							|| obj3 == "image/gif") {
						return false;

					} else {
						jAlert("Select File should be of type png,jpeg,gif.");
						return false;
					}
				}

			});
			val1 = val1 + 1;
		});
		if ((firstname_r_sale1.value.length == 0)
				|| (lastname_r_sale1.value.length == 0)
				|| (id_r1.value.length == 0) || (contact_no1.value.length == 0)
				|| (date_Of_birth_sale1.value.length == 0)
				|| (address_sale1.value.length == 0)) { // ||
														// (middlename_r_sale1.value.length
														// == 0)
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		} else if ((sale_idtype_buyer.value == "0")
				|| (sale_gender_buyer.value == "0")) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}

		if ((doc_Type_Sale.value == "0")) {
			jAlert("Please Select Document type", "Alert");
			return false;
		}

		/*
		 * var flagsale = validateSale(); if(!flagsale){ jAlert("Please Fill
		 * Mandatory Details", "Alert"); return false; }
		 */
		jConfirm(
				'If he/she wants to commit data to land record would be nice',
				'Save Confirmation',
				function(response) {
					if (response) {

						jQuery
								.ajax({
									type : "POST",
									url : "registration/saveprocessdata",
									data : jQuery("#editprocessAttributeformID")
											.serialize(),
									success : function(result) {
										if (result!=null && result != undefined) {

											var formData = new FormData();
											var file = [];
											// var file =
											// $('#fileUploadNewDowcumentss')[0].files[0];
											// var file =
											// $('#fileUploadNewDowcumentss')[0].files;
											$
													.each(
															$('#fileUploadNewDowcumentss')[0].files,
															function(ind3, obj3) {
																formData
																		.append(
																				"newFile"
																						+ ind3,
																				obj3);
															});

											formData.append("doc_name_sale", $(
													"#doc_name_sale").val());
											formData.append("doc_desc_sale", $(
													"#doc_desc_sale").val());
											formData.append("doc_date_sale", $(
													"#doc_date_sale").val());
											formData.append("doc_Type_Sale", $(
													"#doc_Type_Sale").val());
											formData.append("selectedlandid",
													selectedlandid);
											
											formData.append("transactionid",
													result);
											
											jQuery
													.ajax({
														type : "POST",
														url : "registration/savedocument",
														mimeType : "multipart/form-data",
														contentType : false,
														cache : false,
														processData : false,
														data : formData,
														success : function(data) {
															jAlert('Attributes were successfully saved');

															// $("#lease-dialog-form").hide();
															attributeEditDialog
																	.dialog("close");
														},
														error : function(
																XMLHttpRequest,
																textStatus,
																errorThrown) {
															jAlert('Failed to save document');
														}
													});

											// jAlert('Attributes were
											// successfully saved');
											// editAttrDialog.dialog("destroy");
										} else {
											jAlert('Request not completed');
										}
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										jAlert('Request not completed');
									}
								});
					}
				});

	} else {
		jAlert("Please Fill Mandatory Details", "Alert");
	}
}
function saveattributesLease()
{
	var selectedprocess = $("#registration_process").val();
	if(selectedprocess == 5)
		{
			saveattributesSurrenderLease();
		}
	else
		{
			saveattributesLeaseData();
		}

}

function saveattributesLeaseData() {
	$("#editprocessAttributeformID").validate({
		rules : {
			doc_name_Lease : {
				required : true
			},
			doc_date_Lease : "required",
			doc_desc_Lease : "required",
			remrks_lease : {
				required : true
			},
			// no_Of_years_Lease: "required",
			doc_Type_Lease : "required",
			no_Of_month_Lease : "required",
			lease_Amount : "required"
		},
		messages : {
			doc_name_Lease : "Please Enter Document name",
			doc_date_Lease : "Please Enter Document uploded date",
			doc_desc_Lease : "Please Document description",
			remrks_lease : "Please enter remrks",
			// no_Of_years_Lease: "Enter No of Years",
			doc_Type_Lease : "Please Select Document Type",
			no_Of_month_Lease : "Enter No of Month",
			lease_Amount : "Please enter Lease  Amount"
		}
	});

	if ($("#editprocessAttributeformID").valid()) {

		if ((doc_name_Lease.value.length == 0)
				|| (doc_date_Lease.value.length == 0)
				|| (doc_desc_Lease.value.length == 0)
				|| (remrks_lease.value.length == 0)) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}

		if ($("#fileUploadNewDowcumentsLease").val() === '') {
			jAlert("Select File for upload.");
			return;
		}

		var val1 = 0;
		$
				.each(
						$('#fileUploadNewDowcumentsLease')[0].files,
						function(ind2, obj2) {

							$
									.each(
											$('#fileUploadNewDowcumentsLease')[0].files[val1],
											function(ind3, obj3) {
												if (ind3 == "type") {

													if (obj3 == "image/png"
															|| obj3 == "image/jpeg"
															|| obj3 == "image/gif") {
														// flag =true;
														return false;

													} else {
														// flag =true;
														jAlert("Select File should be of type png,jpeg,gif.");
														return false;
													}
												}

											});
							val1 = val1 + 1;
						});

		if ((firstname_r_applicant.value.length == 0)
				|| (lastname_r_applicant.value.length == 0)
				|| (id_r_applicant.value.length == 0)
				|| (contact_no_applicant.value.length == 0)
				|| (date_Of_birth_applicant.value.length == 0)
				|| (address_lease_applicant.value.length == 0)) { // ||
																	// (middlename_r_applicant.value.length
																	// == 0)
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		} else if ((id_type_applicant.value == "0")
				|| (gender_type_applicant.value == "0")
				|| (martial_sts_applicant.value == "0")) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}

		if ((no_Of_month_Lease.value.length == 0)
				|| (lease_Amount.value.length == 0)) {// (no_Of_years_Lease.value.length
														// == 0) ||
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}/*
			 * else if((no_Of_month_Lease.value == "0")){ jAlert("Please Fill
			 * Mandatory Details", "Alert"); return false; }
			 */
		if ((doc_Type_Lease.value == "0")) {
			jAlert("Please Select Document Type", "Alert");
			return false;
		}

		jConfirm(
				'If he/she wants to commit data to land record would be nice',
				'Save Confirmation',
				function(response) {
					if (response) {
						jQuery
								.ajax({
									type : "POST",
									url : "registration/saveleasedata",
									data : jQuery("#editprocessAttributeformID")
											.serialize(),
									success : function(result) {
										if (result!=null && result != undefined) {

											// var file =
											// $('#fileUploadNewDowcumentsLease')[0].files[0];
											var formData = new FormData();

											$
													.each(
															$('#fileUploadNewDowcumentsLease')[0].files,
															function(ind3, obj3) {
																formData
																		.append(
																				"newFile"
																						+ ind3,
																				obj3);
															});

											// formData.append("newFileLease",
											// file);
											formData.append("doc_name_Lease",
													$("#doc_name_Lease").val());
											formData.append("doc_desc_Lease",
													$("#doc_desc_Lease").val());
											formData.append("doc_date_Lease",
													$("#doc_date_Lease").val());
											formData.append("doc_Type_Lease",
													$("#doc_Type_Lease").val());
											formData.append("selectedlandid",
													selectedlandid);
											formData.append("transactionid",
													result);

											jQuery
													.ajax({
														type : "POST",
														url : "registration/savedocumentlease",
														mimeType : "multipart/form-data",
														contentType : false,
														cache : false,
														processData : false,
														data : formData,
														success : function(data) {
															jAlert('Attributes were successfully saved');

															// $("#lease-dialog-form").hide();
															attributeEditDialog
																	.dialog("close");
														},
														error : function(
																XMLHttpRequest,
																textStatus,
																errorThrown) {
															jAlert('Failed to save document');
														}
													});

										} else {
											jAlert('Request not completed');
										}
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										jAlert('Request not completed');
									}
								});
					}
				});

	} else {
		jAlert("Please Fill Mandatory Details", "Alert");
	}
}

function saveattributesSurrenderLease() {
	/*$("#editprocessAttributeformID").validate({
		rules : {
			doc_name_Lease : {
				required : true
			},
			doc_date_Lease : "required",
			doc_desc_Lease : "required",
			remrks_lease : {
				required : true
			},
			// no_Of_years_Lease: "required",
			doc_Type_Lease : "required",
			no_Of_month_Lease : "required",
			lease_Amount : "required"
		},
		messages : {
			doc_name_Lease : "Please Enter Document name",
			doc_date_Lease : "Please Enter Document uploded date",
			doc_desc_Lease : "Please Document description",
			remrks_lease : "Please enter remrks",
			// no_Of_years_Lease: "Enter No of Years",
			doc_Type_Lease : "Please Select Document Type",
			no_Of_month_Lease : "Enter No of Month",
			lease_Amount : "Please enter Lease  Amount"
		}
	});*/

	if ($("#editprocessAttributeformID").valid()) {

		if ((doc_name_Lease.value.length == 0)
				|| (doc_date_Lease.value.length == 0)
				|| (doc_desc_Lease.value.length == 0)
				|| (remrks_lease.value.length == 0)) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}

		if ($("#fileUploadNewDowcumentsLease").val() === '') {
			jAlert("Select File for upload.");
			return;
		}

		var val1 = 0;
		$
				.each(
						$('#fileUploadNewDowcumentsLease')[0].files,
						function(ind2, obj2) {

							$
									.each(
											$('#fileUploadNewDowcumentsLease')[0].files[val1],
											function(ind3, obj3) {
												if (ind3 == "type") {

													if (obj3 == "image/png"
															|| obj3 == "image/jpeg"
															|| obj3 == "image/gif") {
														// flag =true;
														return false;

													} else {
														// flag =true;
														jAlert("Select File should be of type png,jpeg,gif.");
														return false;
													}
												}

											});
							val1 = val1 + 1;
						});

		/*if ((firstname_r_applicant.value.length == 0)
				|| (lastname_r_applicant.value.length == 0)
				|| (id_r_applicant.value.length == 0)
				|| (contact_no_applicant.value.length == 0)
				|| (date_Of_birth_applicant.value.length == 0)
				|| (address_lease_applicant.value.length == 0)) { // ||
																	// (middlename_r_applicant.value.length
																	// == 0)
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		} else if ((id_type_applicant.value == "0")
				|| (gender_type_applicant.value == "0")
				|| (martial_sts_applicant.value == "0")) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}

		if ((no_Of_month_Lease.value.length == 0)
				|| (lease_Amount.value.length == 0)) {// (no_Of_years_Lease.value.length
														// == 0) ||
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}*//*
			 * else if((no_Of_month_Lease.value == "0")){ jAlert("Please Fill
			 * Mandatory Details", "Alert"); return false; }
			 */
		if ((doc_Type_Lease.value == "0")) {
			jAlert("Please Select Document Type", "Alert");
			return false;
		}

		jConfirm(
				'If he/she wants to commit data to surrender lease would be nice',
				'Save Confirmation',
				function(response) {
					if (response) {
						jQuery
								.ajax({
									type : "POST",
									//url : "registration/saveleasedata",
									url : "registration/savesurrenderleasedata",
									data : jQuery("#editprocessAttributeformID")
											.serialize(),
									success : function(result) {
										if (result!=null && result != undefined) {

											// var file =
											// $('#fileUploadNewDowcumentsLease')[0].files[0];
											var formData = new FormData();

											$
													.each(
															$('#fileUploadNewDowcumentsLease')[0].files,
															function(ind3, obj3) {
																formData
																		.append(
																				"newFile"
																						+ ind3,
																				obj3);
															});

											// formData.append("newFileLease",
											// file);
											formData.append("doc_name_Lease",
													$("#doc_name_Lease").val());
											formData.append("doc_desc_Lease",
													$("#doc_desc_Lease").val());
											formData.append("doc_date_Lease",
													$("#doc_date_Lease").val());
											formData.append("doc_Type_Lease",
													$("#doc_Type_Lease").val());
											formData.append("selectedlandid",
													selectedlandid);
											formData.append("transactionid",
													result);

											jQuery
													.ajax({
														type : "POST",
														url : "registration/savedocumentsurrenderlease",
														mimeType : "multipart/form-data",
														contentType : false,
														cache : false,
														processData : false,
														data : formData,
														success : function(data) {
															jAlert('Attributes were successfully saved');

															// $("#lease-dialog-form").hide();
															attributeEditDialog
																	.dialog("close");
														},
														error : function(
																XMLHttpRequest,
																textStatus,
																errorThrown) {
															jAlert('Failed to save document');
														}
													});

										} else {
											jAlert('Request not completed');
										}
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										jAlert('Request not completed');
									}
								});
					}
				});

	} else {
		jAlert("Please Fill Mandatory Details", "Alert");
	}
}


function saveattributesMortgage() {

	/*
	 * $("#editprocessAttributeformID").validate({ rules: { doc_name_mortgage:
	 * {required: true}, doc_date_mortgage: "required", doc_desc_mortgage:
	 * "required", remrks_mortgage: {required: true} }, messages: {
	 * doc_name_mortgage: "Please Enter Document name", doc_date_mortgage:
	 * "Please Enter Document uploded date", doc_desc_mortgage: "Please Document
	 * description", remrks_mortgage: "Please enter remrks"
	 *  } });
	 */

	$("#editprocessAttributeformID").validate({
		rules : {
			/*
			 * mortgage_from: "required", mortgage_to: "required",
			 * amount_mortgage: "required",
			 */
			// mortgage_Financial_Agencies: {required: true},
			doc_name_mortgage : {
				required : true
			},
			doc_date_mortgage : {
				required : true
			},
			doc_desc_mortgage : {
				required : true
			},
			NewfilesMortgage : {
				required : true
			},
			remrks_mortgage : "required"
		},
		messages : {
			/*
			 * mortgage_from: "Please Enter mortgage from", mortgage_to: "Please
			 * enter mortgage to", amount_mortgage: "Please enter amount of
			 * mortgage",
			 */
			// mortgage_Financial_Agencies: "Enter First name",
			doc_name_mortgage : "Please Enter Document name",
			doc_date_mortgage : "Please Enter Document date",
			doc_desc_mortgage : "Please Enter Document Descreption",
			NewfilesMortgage : "Please select Files",
			remrks_mortgage : "Please enter remarks"
		}
	});

	if ($("#editprocessAttributeformID").valid()) {
		if ((mortgage_from.value.length == 0)
				|| (mortgage_to.value.length == 0)
				|| (amount_mortgage.value.length == 0)) {
			setTimeout(function() {
				$("#Tab_3").tabs({
					active : 2
				});
				$("#editprocessAttributeformID").validate({
					rules : {
						mortgage_from : "required",
						mortgage_to : "required",
						amount_mortgage : "required"

					},
					messages : {
						mortgage_from : "Please Enter mortgage from",
						mortgage_to : "Please enter mortgage to",
						amount_mortgage : "Please enter amount of mortgage"
					}
				});
			}, 500);
		} else if ((mortgage_Financial_Agencies.value == "0")) {
			setTimeout(function() {
				$("#Tab_3").tabs({
					active : 2
				});
				$("#editprocessAttributeformID").validate({
					rules : {
						mortgage_Financial_Agencies : {
							required : true
						},

					},
					messages : {
						mortgage_Financial_Agencies : "Enter First name"

					}
				});
			}, 500);
		}
	}/*
		 * else{
		 * 
		 * setTimeout( function() { $("#Tab_3" ).tabs( { active: 2 } ); //do
		 * something special }, 500);
		 * 
		 * return false;
		 *  }
		 */

	if ($("#editprocessAttributeformID").valid()) {

		if ((doc_name_mortgage.value.length == 0)
				|| (doc_date_mortgage.value.length == 0)
				|| (doc_desc_mortgage.value.length == 0)
				|| (remrks_mortgage.value.length == 0)) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}
		if ($("#fileUploadNewDowcumentsMortgage").val() === '') {
			jAlert("Select file for upload.");
			return;
		}
		var val1 = 0;
		$
				.each(
						$('#fileUploadNewDowcumentsMortgage')[0].files,
						function(ind2, obj2) {

							$
									.each(
											$('#fileUploadNewDowcumentsMortgage')[0].files[val1],
											function(ind3, obj3) {
												if (ind3 == "type") {

													if (obj3 == "image/png"
															|| obj3 == "image/jpeg"
															|| obj3 == "image/gif") {
														// flag =true;
														return false;

													} else {
														// flag =true;
														jAlert("Select File should be of type png,jpeg,gif.");
														return false;
													}
												}

											});
							val1 = val1 + 1;
						});

		if ((mortgage_from.value.length == 0)
				|| (mortgage_to.value.length == 0)
				|| (amount_mortgage.value.length == 0)) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		} else if ((mortgage_Financial_Agencies.value == "0")) {
			jAlert("Please Fill Mandatory Details", "Alert");
			return false;
		}
		/*
		 * var flagmortgage = validateMortgage(); if(!flagmortgage){
		 * jAlert("Please Fill Mandatory Details", "Alert"); return false; }
		 */
		if ((doc_Type_Mortgage.value == "0")) {
			jAlert("Please Select Document Type", "Alert");
			return false;
		}

		jConfirm(
				'If he/she wants to commit data to land record would be nice',
				'Save Confirmation',
				function(response) {
					if (response) {
						jQuery
								.ajax({
									type : "POST",
									url : "registration/savemortgagedata",
									data : jQuery("#editprocessAttributeformID")
											.serialize(),
									success : function(result) {
										if (result!=null && result != undefined) {
											// var file =
											// $('#fileUploadNewDowcumentsMortgage')[0].files[0];
											var formData = new FormData();
											$
													.each(
															$('#fileUploadNewDowcumentsMortgage')[0].files,
															function(ind3, obj3) {
																formData
																		.append(
																				"newFile"
																						+ ind3,
																				obj3);
															});

											// formData.append("newFileMortgage",
											// file);
											formData.append(
													"doc_name_mortgage",
													$("#doc_name_mortgage")
															.val());
											formData.append(
													"doc_desc_mortgage",
													$("#doc_desc_mortgage")
															.val());
											formData.append(
													"doc_date_mortgage",
													$("#doc_date_mortgage")
															.val());
											formData.append(
													"doc_Type_Mortgage",
													$("#doc_Type_Mortgage")
															.val());
											formData.append("selectedlandid",
													selectedlandid);
											
											formData.append("transactionid",
													result);

											jQuery
													.ajax({
														type : "POST",
														url : "registration/savedocumentmortgage",
														mimeType : "multipart/form-data",
														contentType : false,
														cache : false,
														processData : false,
														data : formData,
														success : function(data) {
															jAlert('Attributes were successfully saved');
															// $("#lease-dialog-form").hide();
															attributeEditDialog
																	.dialog("close");
														},
														error : function(
																XMLHttpRequest,
																textStatus,
																errorThrown) {
															jAlert('Failed to save document');
														}
													});
										} else {
											jAlert('Request not completed');
										}
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										jAlert('Request not completed');
									}
								});
					}

				});

	} else {
		jAlert("Please Fill Mandatory Details", "Alert");
	}
}

function getRegionOnChangeCountry(id) {
	if (id != '') {

		$("#sale_region").empty();
		jQuery("#sale_region").append(
				jQuery("<option></option>").attr("value", "").text("Select"));
		jQuery.ajax({
			url : "registration/allregion/" + id,
			async : false,
			success : function(regiondata) {
				var proj_region = regiondata;
				jQuery.each(proj_region, function(i, value) {
					jQuery("#sale_region").append(
							jQuery("<option></option>").attr("value",
									value.hierarchyid).text(value.nameEn));
				});
			}
		});
	}
}

function getprocessvalue(id) {

	var lease = document.getElementById("Tab_1");
	var sale = document.getElementById("Tab_2");
	var mortgage = document.getElementById("Tab_3");
	lease.style.isplay = "none"; // lease.style.display = "block";
	sale.style.display = "none";
	mortgage.style.display = "none";
	$("#RelationWithOwner").css("display","none");
	$("#comment_Next").show();
	if (id == 1) // Lease
	{

		lease.style.display = "block";
		sale.style.display = "none";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Lease";
		
		jQuery("#doc_Type_Lease").empty();
		jQuery("#doc_Type_Lease").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		jQuery.each(documenttype_R, function(i, obj) 
		{
			if(obj.processid==1)
			{
				jQuery("#doc_Type_Lease").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
			}
		});
	} 
	else if (id == 5) // "Surrender of Lease"
	{

		lease.style.display = "block";
		sale.style.display = "none";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		// clear_Lease();
		currentdiv = "Lease";
		
		jQuery("#doc_Type_Lease").empty();
		jQuery("#doc_Type_Lease").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		jQuery.each(documenttype_R, function(i, obj) 
		{
			if(obj.processid==5)
			{
				jQuery("#doc_Type_Lease").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
			}
		});
	}		
	else if (id == 2) // "Sale"
	{
		lease.style.display = "none";
		sale.style.display = "block";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Sale";
		
		$("#selectedselleranchor").text("Seller Details");
		$("#selectedbuyeranchor").text("Buyer Details");
		$("#selectedsellerlabel").text("Seller Details");
		$("#selectedbuyerlabel").text("Buyer Details");
		
		jQuery("#doc_Type_Sale").empty();
		jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		jQuery.each(documenttype_R, function(i, obj) 
		{
			if(obj.processid==2)
			{
				jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
			}
		});
	} 
	else if (id == 4) // "Change of Ownership"
	{
		lease.style.display = "none";
		sale.style.display = "block";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Sale";
		
		$("#selectedselleranchor").text("Owner Details");
		$("#selectedbuyeranchor").text("New Owner Details");
		$("#selectedsellerlabel").text("Owner Details");
		$("#selectedbuyerlabel").text("New Owner Details");	
		
		jQuery("#doc_Type_Sale").empty();
		jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		jQuery.each(documenttype_R, function(i, obj) 
		{
			if(obj.processid==4)
			{
				jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
			}
		});
		
	} 
	
	else if (id == 6) // "Gift/Inheritance"
	{
		lease.style.display = "none";
		sale.style.display = "block";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Sale";
		
		$("#selectedselleranchor").text("Owner Details");
		$("#selectedbuyeranchor").text("New Owner Details");
		$("#selectedsellerlabel").text("Owner Details");
		$("#selectedbuyerlabel").text("New Owner Details");	
		
		jQuery("#doc_Type_Sale").empty();
		jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		
		jQuery.each(documenttype_R, function(i, obj) 
				{
					if(obj.processid==6)
					{
						jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
					}
				});
		
		$("#RelationWithOwner").css("display","block");
		
		jQuery("#sale_relation_buyer").empty();
		jQuery("#sale_relation_buyer").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		jQuery.each(relationtypes_R, function(i, obj) 
		{
			jQuery("#sale_relation_buyer").append(jQuery("<option></option>").attr("value", obj.relationshiptypeid).text(obj.relationshiptype));
		});
	}
	
	else if (id == 7) // "Change of Joint Owner"
	{
		lease.style.display = "none";
		sale.style.display = "block";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Sale";
		
		$("#selectedselleranchor").text("Owner Details");
		$("#selectedbuyeranchor").text("New Owner Details");
		$("#selectedsellerlabel").text("Owner Details");
		$("#selectedbuyerlabel").text("New Owner Details");	
		$("#RelationWithOwner").css("display","none");
		$("#EliminatedOwner").css("display","block");
		
		jQuery("#doc_Type_Sale").empty();
		jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		
		$("#RelationWithOwner").css("display","none");
		
		jQuery.each(documenttype_R, function(i, obj) 
				{
					if(obj.processid==7)
					{
						jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
					}
				});
		
		
	}
	
	else if (id == 3) // "Mortgage"
	{
		lease.style.display = "none";
		sale.style.display = "none";
		mortgage.style.display = "block";
		clear_Lease();
		clearMortgage();
		currentdiv = "Mortgage";
		
		jQuery("#doc_Type_Mortgage").empty();
		jQuery("#doc_Type_Mortgage").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		jQuery.each(documenttype_R, function(i, obj) 
		{
			if(obj.processid==3)
			{
				jQuery("#doc_Type_Mortgage").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
			}
		});
	}
	
}
/*function getprocessvalue(id) {

	var lease = document.getElementById("Tab_1");
	var sale = document.getElementById("Tab_2");
	var mortgage = document.getElementById("Tab_3");
	lease.style.isplay = "none"; // lease.style.display = "block";
	sale.style.display = "none";
	mortgage.style.display = "none";
	$("#RelationWithOwner").css("display","none");	
	$("#EliminatedOwner").css("display","none");	

	$("#comment_Next").show();
	if (id == 1) 
	{

		lease.style.display = "block";
		sale.style.display = "none";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Lease";
		
		jQuery("#doc_Type_Lease").empty();
		jQuery("#doc_Type_Lease").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		
		jQuery.each(documenttype_R, function(i, obj) 
				{
					if(obj.processid==1)
					{
						jQuery("#doc_Type_Lease").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
					}
				});
	} 
	else if (id == 5) 
	{

		lease.style.display = "block";
		sale.style.display = "none";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		// clear_Lease();
		currentdiv = "Lease";
	}		
	else if (id == 2) 
	{
		lease.style.display = "none";
		sale.style.display = "block";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Sale";
		
		$("#selectedselleranchor").text("Seller Details");
		$("#selectedbuyeranchor").text("Buyer Details");
		$("#selectedsellerlabel").text("Seller Details");
		$("#selectedbuyerlabel").text("Buyer Details");
		
		jQuery("#doc_Type_Sale").empty();
		jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		//$("#RelationWithOwner").css("display","block");
		jQuery.each(documenttype_R, function(i, obj) 
				{
					if(obj.processid==2)
					{
						jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
					}
				});
	} 
	else if (id == 4) 
	{
		lease.style.display = "none";
		sale.style.display = "block";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Sale";
		
		$("#selectedselleranchor").text("Owner Details");
		$("#selectedbuyeranchor").text("New Owner Details");
		$("#selectedsellerlabel").text("Owner Details");
		$("#selectedbuyerlabel").text("New Owner Details");		
		
		jQuery("#doc_Type_Sale").empty();
		jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		//$("#RelationWithOwner").css("display","block");
		jQuery.each(documenttype_R, function(i, obj) 
				{
					if(obj.processid==4)
					{
						jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
					}
				});
	} 
	else if (id == 7) 
	{
		lease.style.display = "none";
		sale.style.display = "block";
		mortgage.style.display = "none";
		clearBuyerDetails_sale();
		clear_Lease();
		currentdiv = "Sale";
		
		$("#selectedselleranchor").text("Owner Details");
		$("#selectedbuyeranchor").text("New Owner Details");
		$("#selectedsellerlabel").text("Owner Details");
		$("#selectedbuyerlabel").text("New Owner Details");	
		$("#RelationWithOwner").css("display","none");
		$("#EliminatedOwner").css("display","block");
		
		jQuery("#doc_Type_Sale").empty();
		jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));	
		$("#RelationWithOwner").css("display","block");
		jQuery.each(documenttype_R, function(i, obj) 
				{
					if(obj.processid==7)
					{
						jQuery("#doc_Type_Sale").append(jQuery("<option></option>").attr("value", obj.code).text(obj.nameOtherLang));
					}
				});
		
		
	}
	
	else if (id == 3) 
	{
		lease.style.display = "none";
		sale.style.display = "none";
		mortgage.style.display = "block";
		clear_Lease();
		clearMortgage();
		currentdiv = "Mortgage";
	}
}*/

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
		rules : {
			firstname_r_sale1 : {
				required : true
			},
			// middlename_r_sale1: "required",
			lastname_r_sale1 : "required",
			id_r1 : "required",
			sale_idtype_buyer : {
				required : true
			},
			contact_no1 : {
				required : true
			},
			date_Of_birth_sale1 : {
				required : true
			},
			sale_gender_buyer : {
				required : true
			},
			sale_marital_buyer : "required",
			address_sale1 : "required"
		},
		messages : {
			firstname_r_sale1 : "Enter First name",
			// middlename_r_sale1: "Enter middle name",
			lastname_r_sale1 : "Please enter last name",
			id_r1 : "Please enter ID",
			sale_idtype_buyer : "Please select ID type",
			contact_no1 : "Please Enter contact_no",
			date_Of_birth_sale1 : "Please Enter Date of Birth",
			sale_gender_buyer : "Please select gender",
			sale_marital_buyer : "Please select Marital status",
			address_sale1 : "Please enter address"
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
			setTimeout(function() {
				$("#Tab_2").tabs({
					active : 2
				});

			}, 500);
			return false;
		} else if ((sale_idtype_buyer.value == "0")
				|| (sale_gender_buyer.value == "0")) {
			setTimeout(function() {
				$("#Tab_2").tabs({
					active : 2
				});

			}, 500);
			return false;
		}
	} else {

		setTimeout(function() {
			$("#Tab_2").tabs({
				active : 2
			});
			// do something special
		}, 500);
		// $("#Buyer_Details").show();
		return false;

	}
}

function validateMortgage() {
	$("#editprocessAttributeformID").validate({
		rules : {
			mortgage_from : "required",
			mortgage_to : "required",
			amount_mortgage : "required",
			mortgage_Financial_Agencies : {
				required : true
			},
			doc_name_mortgage : {
				required : true
			},
			doc_date_mortgage : {
				required : true
			},
			doc_desc_mortgage : {
				required : true
			},
			doc_Type_Mortgage : {
				required : true
			},
			NewfilesMortgage : {
				required : true
			},
			remrks_mortgage : "required"
		},
		messages : {
			mortgage_from : "Enter Mortgage From",
			mortgage_to : "Please enter Mortgage To",
			amount_mortgage : "Please enter Amount",
			mortgage_Financial_Agencies : "Enter Financial Agencies",
			doc_name_mortgage : "Please Enter Document Name",
			doc_date_mortgage : "Please Enter Document Date",
			doc_desc_mortgage : "Please Enter Document Description",
			doc_Type_Mortgage : "Please Select Document Type",
			NewfilesMortgage : "Please select Files",
			remrks_mortgage : "Please enter Remarks"
		}
	});

	if ($("#editprocessAttributeformID").valid()) {
		if ((mortgage_from.value.length == 0)
				|| (mortgage_to.value.length == 0)
				|| (amount_mortgage.value.length == 0)) {
			setTimeout(function() {
				$("#Tab_3").tabs({
					active : 2
				});
				// do something special
			}, 500);
		} else if ((mortgage_Financial_Agencies.value == "0")) {
			setTimeout(function() {
				$("#Tab_3").tabs({
					active : 2
				});
				// do something special
			}, 500);
		}
	} else {

		setTimeout(function() {
			$("#Tab_3").tabs({
				active : 2
			});
			// do something special
		}, 500);

		return false;

	}
}

function validateLease() {
	$("#editprocessAttributeformID").validate({
		rules : {
			firstname_r_applicant : {
				required : true
			},
			// middlename_r_applicant: "required",
			lastname_r_applicant : "required",
			id_r_applicant : "required",
			id_type_applicant : {
				required : true
			},
			contact_no_applicant : {
				required : true
			},
			gender_type_applicant : {
				required : true
			},
			address_lease_applicant : {
				required : true
			},
			date_Of_birth_applicant : "required",
			martial_sts_applicant : "required"
		/* NewfilesLease: {required: true} */

		},
		messages : {
			firstname_r_applicant : "Enter First name",
			// middlename_r_applicant: "Enter middle name",
			lastname_r_applicant : "Please enter last name",
			id_r_applicant : "Please enter ID",
			id_type_applicant : "Please select ID type",
			contact_no_applicant : "Please Enter contact_no",
			gender_type_applicant : "Please Enter gender",
			address_lease_applicant : "Please select address",
			date_Of_birth_applicant : "Please enter date_Of_birth",
			martial_sts_applicant : "Please enter martial status"
		/* NewfilesLease: "Please select Files" */
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
			setTimeout(function() {
				$("#Tab_1").tabs({
					active : 1
				});

			}, 500);
		} else if ((id_type_applicant.value == "0")
				|| (gender_type_applicant.value == "0")
				|| (martial_sts_applicant.value == "0")) {
			setTimeout(function() {
				$("#Tab_1").tabs({
					active : 1
				});

			}, 500);
		}
	} else {

		setTimeout(function() {
			$("#Tab_1").tabs({
				active : 1
			});
			// do something special
		}, 500);

		return false;

	}
}

function validateLeasedata() {
	$("#editprocessAttributeformID").validate({
		rules : {
			// no_Of_years_Lease: "required",
			no_Of_month_Lease : "required",
			lease_Amount : "required"
		},
		messages : {
			// no_Of_years_Lease: "Enter No of Years",
			no_Of_month_Lease : "Enter No of Month",
			lease_Amount : "Please enter lease amount"
		}
	});

	if ($("#editprocessAttributeformID").valid()) {

		if ((no_Of_month_Lease.value.length == 0)
				|| (lease_Amount.value.length == 0)) {// (no_Of_years_Lease.value.length
														// == 0) ||
			setTimeout(function() {
				$("#Tab_1").tabs({
					active : 2
				});

			}, 500);
		}/*
			 * else if((no_Of_month_Lease.value == "0")){ setTimeout( function() {
			 * $("#Tab_1" ).tabs( { active: 2 } );
			 *  }, 500); }
			 */

	} else {

		setTimeout(function() {
			$("#Tab_1").tabs({
				active : 2
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

/*
 * function ViewHistory(landid) { jQuery.ajax({ type:'GET', url:
 * "landrecords/parcelhistory/"+landid, async:false, success: function (data) {
 * 
 * $('#ViewPopuupDiv').empty(); $('#ViewPopuupDiv').css("visibility",
 * "visible");
 * 
 * $('#ViewPopuupDiv').append('<div class="tableContainerDeed"><h3 class="gridTitle"><span>Parcel
 * OwnerShip History</span></h3><table width=100%" border="0" cellspacing="1"
 * cellpadding="0" class="grid01"><thead><tr><th>First Name</th></tr></thead><tbody
 * id="PropertyHistTrackingBody"></tbody></table></div></br>');
 * 
 * if(data[0] !=null) { $.each(data[0], function(index,optionData) {
 * jQuery("#PropertyHistTrackingTemplate").tmpl(optionData).appendTo("#PropertyHistTrackingBody");
 * }); }
 * 
 * $("#ViewPopuupDiv").dialog( { height: 400, width: 500, modal: true, buttons: {
 * Cancel: { //text:cancel, click: function(){ $("label.error").remove();
 * $(".error").removeClass("error"); $(this).dialog("close"); } } }, close:
 * function (ev, ui) { $(this).dialog("close"); } });
 * //jQuery('#ViewPopuupDiv').dialog('option', 'title',
 * $._('PropertyHistory_Report'));
 * 
 *  }
 * 
 * 
 * });
 * 
 * 
 * 
 * 
 *  }
 */

function ViewHistory(landid) {
	jQuery('#PropertyHistTrackingBody').empty();
	jQuery('#PropertyLeaseHistTrackingBody').empty();
	jQuery('#PropertyMortageHistTrackingBody').empty();
	jQuery('#TransactionHistTrackingBody').empty();
	jQuery.ajax({
		type : 'GET',
		url : "landrecords/parcelhistory/" + landid,
		async : false,
		success : function(data) {
			if (data.length > 0) {
				if (data[0] != null) {
					jQuery("#commentsTablePop").show();
					$.each(data[0], function(index, optionData) {
						jQuery("#PropertyHistTrackingTemplate")
								.tmpl(optionData).appendTo(
										"#PropertyHistTrackingBody");
					});
				}

				/*if (data[1] != null) {
					jQuery("#commentsTablePopLease").show();
					$.each(data[1], function(index, optionData) {
						jQuery("#PropertyLeaseTrackingTemplate").tmpl(
								optionData).appendTo(
								"#PropertyLeaseHistTrackingBody");
					});
				}

				if (data[2] != null) {
					jQuery("#commentsTablePopMortage").show();
					$.each(data[2], function(index, optionData) {
						jQuery("#PropertyMortageTrackingTemplate").tmpl(
								optionData).appendTo(
								"#PropertyMortageHistTrackingBody");
					});
				}*/
				
				if (data[3] != null) {
					jQuery("#commentsTablePopTransactionHist").show();
					$.each(data[3], function(index, optionData) {
						jQuery("#TransactionHistTrackingTemplate").tmpl(
								optionData).appendTo(
								"#TransactionHistTrackingBody");
					});
				}
				
				commentHistoryDialogPop = $("#commentsDialogpopup").dialog({
					autoOpen : false,
					height : 600,
					width : 800,
					resizable : true,
					modal : true,

					buttons : [ {
						text : "Cancel",
						"id" : "comment_cancel",
						click : function() {
							setInterval(function() {

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
					} ],
					close : function() {

						// commentHistoryDialogPop.dialog( "destroy" );
						jQuery("#commentsTablePop").hide();
						jQuery("#commentsTablePopLease").hide();
						jQuery("#commentsTablePopMortage").hide();

					}
				});
				$("#comment_cancel").html(
						'<span class="ui-button-text">Cancel</span>');
				commentHistoryDialogPop.dialog("open");

			} else {
				jAlert("No Record", "Info");
			}
		}

	});
}


function viewleasedetail(transactionid,landid)
{
	jQuery.ajax({
		type: 'GET',
		//url:'viewArchivalChangesDetail/'+requestid,
		url : 'landrecords/findleasedetailbylandid/' + transactionid + "/" + landid,
		async:false,
		success:function(contactObj){

			if(contactObj != null && contactObj.length>0)
			{
				$('#ViewPopuupDiv').empty();
				$('#ViewPopuupDiv').css("visibility", "visible");					

				//To show person Property Changes
				$('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>'+"Lease Detail"+'</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>'+"Name"+'</th><th>'+"Address"+'</th><th>'+"Id No"+'</th><th>'+"Lease Date"+'</th><th>'+"Lease Month"+'</th><th>'+"Lease Amount"+'</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');				
				
				if(contactObj[0] != null && contactObj[0].length > 0)
				{
					jQuery("#PropertyLeaseTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupBody");
				}
				
				$("#ViewPopuupDiv").dialog(
						{									
							height: 220,
							width: 700,
							modal: true,	
							buttons: 
							{								
								"Cancel": {
									text : "Cancel",
									"id" : "comment_Trans_cancel",
									click : function() {
										setInterval(function() {

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
				jQuery('#ViewPopuupDiv').dialog('option', 'title', "Lease");
				
			}
			else
			{
				alertMsg=$._('No_Contact_Associated_Property_MSG');
				jAlert('info', alertMsg, alertInfoHeader);
			}
		},
		error:function(){

		}
	});
}

function viewsurrenderleasedetail(transactionid,landid)
{
	jQuery.ajax({
		type: 'GET',
		//url:'viewArchivalChangesDetail/'+requestid,
		url : 'landrecords/findsurrenderleasedetailbylandid/' + transactionid + "/" + landid,
		async:false,
		success:function(contactObj){

			if(contactObj != null && contactObj.length>0)
			{
				$('#ViewPopuupDiv').empty();
				$('#ViewPopuupDiv').css("visibility", "visible");					

				//To show person Property Changes
				$('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>'+"Lease Detail"+'</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>'+"Name"+'</th><th>'+"Address"+'</th><th>'+"Id No"+'</th><th>'+"Lease Date"+'</th><th>'+"Lease Month"+'</th><th>'+"Lease Amount"+'</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');				
				
				if(contactObj[0] != null && contactObj[0].length > 0)
				{
					jQuery("#PropertyLeaseTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupBody");
				}
				
				$("#ViewPopuupDiv").dialog(
						{									
							height: 220,
							width: 700,
							modal: true,	
							buttons: 
							{								
								"Cancel": {
									text : "Cancel",
									"id" : "comment_Trans_cancel",
									click : function() {
										setInterval(function() {

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
				jQuery('#ViewPopuupDiv').dialog('option', 'title', "Lease");
				
			}
			else
			{
				alertMsg=$._('No_Contact_Associated_Property_MSG');
				jAlert('info', alertMsg, alertInfoHeader);
			}
		},
		error:function(){

		}
	});
}

function viewMortagagedetail(transactionid,landid)
{
	jQuery.ajax({
		type: 'GET',
		//url:'viewArchivalChangesDetail/'+requestid,
		url : 'landrecords/findmortagagedetailbylandid/' + transactionid + "/" + landid,
		async:false,
		success:function(contactObj){

			if(contactObj != null && contactObj.length>0)
			{
				$('#ViewPopuupDiv').empty();
				$('#ViewPopuupDiv').css("visibility", "visible");					

				//To show person Property Changes
				$('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>'+"Mortgage Detail"+'</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>'+"Financial Agency"+'</th><th>'+"Mortgage From"+'</th><th>'+"Mortgage to"+'</th><th>'+"Mortgage Amount"+'</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');				
				
				if(contactObj[0] != null && contactObj[0].length > 0)
				{
					jQuery("#PropertyMortageTrackingTemplate").tmpl(contactObj[0]).appendTo("#popupBody");
				}
				
				$("#ViewPopuupDiv").dialog(
						{									
							height: 220,
							width: 700,
							modal: true,	
							buttons: 
							{								
								"Cancel": {
									text : "Cancel",
									"id" : "comment_Trans_cancel",
									click : function() {
										setInterval(function() {

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
				jQuery('#ViewPopuupDiv').dialog('option', 'title', "Mortage");
				
			}
			else
			{
				alertMsg=$._('No_Contact_Associated_Property_MSG');
				jAlert('info', alertMsg, alertInfoHeader);
			}
		},
		error:function(){

		}
	});
}


function viewdocumentdetailForTransaction(personid,transactionid) {
window.open("registrationdetails/viewdoc/" + personid +"/" + transactionid, 'popUp', 'height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');
}

function viewdocumentdetail(transactionid)
{
	jQuery.ajax({
		type: 'GET',
		//url:'viewArchivalChangesDetail/'+requestid,
		url : "landrecords/viewdocumentdetail/" + transactionid,
		async:false,
		success:function(docObj){

			if(docObj != null && docObj.length>0)
			{
				$('#ViewPopuupDiv').empty();
				$('#ViewPopuupDiv').css("visibility", "visible");					

				//To show person Property Changes
				$('#ViewPopuupDiv').append('<div class="amendbeforeAfterTable"><h3 class="gridTitle"><span>'+"Document Detail"+'</span></h3><table width=100%" border="0" cellspacing="1" cellpadding="0" class="grid01"><thead><tr id="LandownerPersonTR" ><th>'+"Document Name"+'</th><th>'+"Document Date"+'</th><th>'+"Document Description"+'</th><th>'+"Document Type"+'</th><th>'+"View Document"+'</th></tr></thead><tbody id="popupBody"></tbody></table></div></br>');				
				
				if(docObj[0] != null && docObj[0].length > 0)
				{
					jQuery("#DocumentTrackingTemplate").tmpl(docObj[0]).appendTo("#popupBody");
				}
				
				$("#ViewPopuupDiv").dialog(
						{									
							height: 220,
							width: 700,
							modal: true,	
							buttons: 
							{								
								"Cancel": {
									text : "Cancel",
									"id" : "comment_Trans_cancel",
									click : function() {
										setInterval(function() {

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
				jQuery('#ViewPopuupDiv').dialog('option', 'title', "Document details");
				
			}
			else
			{
				alertMsg=$._('No_Contact_Associated_Property_MSG');
				jAlert('info', alertMsg, alertInfoHeader);
			}
		},
		error:function(){

		}
	});
}

function makeFileListLease() {
	var fi = document.getElementById('fileUploadNewDowcumentsLease');

	if (fi.files.length > 0) {

		for ( var i = 0; i <= fi.files.length - 1; i++) {
			var fname = fi.files.item(i).name; // THE NAME OF THE FILE.
			// var fsize = fi.files.item(i).size; // THE SIZE OF THE FILE.

			document.getElementById('fp_lease').innerHTML = document
					.getElementById('fp_lease').innerHTML
					+ fname + '<br /> ';
		}
	}
}
function makeFileList() {

	/*
	 * var fi = document.getElementById('fileUploadNewDowcumentss');
	 * 
	 * if (fi.files.length > 0) {
	 * 
	 * for (var i = 0; i <= fi.files.length - 1; i++) { var fname =
	 * fi.files.item(i).name; // THE NAME OF THE FILE. //var fsize =
	 * fi.files.item(i).size; // THE SIZE OF THE FILE.
	 * 
	 * document.getElementById('fp').innerHTML =
	 * document.getElementById('fp').innerHTML + fname + '<br /> '; } }
	 */

	$('#landRegistration_docs').empty();

	var jsonObjItems = [];
	var val1 = 0;
	$.each($('#fileUploadNewDowcumentss')[0].files, function(ind2, obj2) {
		var myObject = new Object();
		$.each($('#fileUploadNewDowcumentss')[0].files[val1], function(ind3,
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

		for ( var i = 0; i <= fi.files.length - 1; i++) {
			var fname = fi.files.item(i).name; // THE NAME OF THE FILE.
			// var fsize = fi.files.item(i).size; // THE SIZE OF THE FILE.

			document.getElementById('fp_mortgage').innerHTML = document
					.getElementById('fp_mortgage').innerHTML
					+ fname + '<br /> ';
		}
	}
}

function ActionfillRegistration(landid, workflowId, transactionid,parcelno,regno) {

	var parcelnumwithpadding = pad(parcelno, 9);
	//var regno = '123';
	
	jQuery("#parcelnovalue").text(parcelnumwithpadding);
	jQuery("#regnovalue").text(regno);
	
	var appid = '#' + landid + "";
	$("" + appid + "").empty();
	$(".containerDiv").empty();

	var html = "";
	html += "<li> <a title='View Land Record' id='' name=''  href='#' onclick='viewLandAttribute("
			+ landid + ")'>View Land Record</a></li>";

	html += "<li> <a title=' Review spatial data' id='' name=''  href='#' onclick='showOnMap("
			+ landid + ", " + workflowId + ")'>View Spatial</a></li>";

	html += "<li> <a title='initiate Registration Transaction' id='' name=''  href='#' onclick='leaseAttribute("
			+ landid + ")'>Initiate Registration Transaction</a></li>";

	html += "<li> <a title='View transaction history' id='' name=''  href='#' onclick='ViewHistory("+ landid + ")'>View transaction history</a></li>";
	html += "<li> <a title='Print land certificate' id='' name=''  href='#' onclick='generateCcro("
			+ transactionid + ")'>Print land certificate</a></li>";
			
			

	$("" + appid + "").append(
			'<div class="signin_menu"><div class="signin"><ul>' + html
					+ '</ul></div></div>');

	$(".signin_menu").toggle();
	$(".signin").toggleClass("menu-open");

	$(".signin_menu").mouseup(function() {
		return false
	});
	$(document).mouseup(function(e) {
		if ($(e.target).parent("a.signin").length == 0) {
			$(".signin").removeClass("menu-open");
			$(".signin_menu").hide();
		}
	});
}
var land_RV = null;

function viewLandAttribute(landid) {
	selectedlandid = landid;
	$("#landidhide").val(landid);
	province_r = null;
	region_r = null;
	jQuery.ajax({
		url : "registration/allregion/" + country_r_id,
		async : false,
		success : function(data) {
			region_r = data;
			if (data.length > 0) {
				// data.xyz.name_en for getting the data
			}
		}
	});

	jQuery.ajax({
		url : "registration/allprovince/" + region_r_id,
		async : false,
		success : function(data) {
			province_r = data;
		}
	});

	jQuery.ajax({
		url : "registration/laspatialunitland/" + landid,
		async : false,
		success : function(data) {
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

	// Land Details landtype_r
	jQuery("#Land_Type_l_record").empty();
	jQuery("#country_l_record").empty();
	jQuery("#region_l_record").empty();
	jQuery("#province_r").empty();
	jQuery("#Ownership_Type_l_record").empty();
	jQuery("#provience_l_record").empty();
	jQuery("#existing_use_LR").empty();
	jQuery("#proposed_use_LR").empty();
	
	
	jQuery("#country_l_record").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#region_l_record").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#provience_l_record").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#Land_Type_l_record").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery("#Ownership_Type_l_record").append(
			jQuery("<option></option>").attr("value", 0).text("Please Select"));

	jQuery.each(landtype_r, function(i, obj1) {
		jQuery("#Land_Type_l_record").append(
				jQuery("<option></option>").attr("value", obj1.landtypeid)
						.text(obj1.landtypeEn));
	});
	jQuery.each(allcountry, function(i, obj) {
		jQuery("#country_l_record").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});

	jQuery.each(region_r, function(i, obj) {
		jQuery("#region_l_record").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});
	jQuery.each(province_r, function(i, obj) {
		jQuery("#provience_l_record").append(
				jQuery("<option></option>").attr("value", obj.hierarchyid)
						.text(obj.nameEn));
	});

	jQuery.each(landsharetype_r, function(i, obj) {
		jQuery("#Ownership_Type_l_record").append(
				jQuery("<option></option>").attr("value", obj.landsharetypeid)
						.text(obj.landsharetypeEn));
	});

	jQuery("#Land_Type_l_record").val(land_RV[0].landtypeid);
	jQuery("#country_l_record").val(land_RV[0].hierarchyid1);
	jQuery("#region_l_record").val(land_RV[0].hierarchyid2);
	jQuery("#provience_l_record").val(land_RV[0].hierarchyid3);

	jQuery("#parcel_l_record").val(land_RV[0].landno);
	jQuery("#Ownership_Type_l_record").val(land_RV[0].landsharetypeid);// landsharetypeid
	jQuery("#area_LR").val(land_RV[0].area);
	jQuery("#land_use_LR").val(land_RV[0].landusetype_en);

	jQuery("#neighbor_west_LR").val(land_RV[0].neighbor_west);
	jQuery("#neighbor_north_LR").val(land_RV[0].neighbor_north);
	jQuery("#neighbor_south_LR").val(land_RV[0].neighbor_south);
	jQuery("#neighbor_east_LR").val(land_RV[0].neighbor_east);
	
		jQuery("#existing_use_LR").empty();
			jQuery("#existing_use_LR").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(landUserList, function (i, landuseobj) {
					if(landuseobj.landusetypeid!='9999')
				     jQuery("#existing_use_LR").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn)); 

			});

			jQuery("#proposed_use_LR").empty();
			jQuery("#proposed_use_LR").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(landUserList, function (i, landuseobj) {
				if(landuseobj.landusetypeid!='9999')
				jQuery("#proposed_use_LR").append(jQuery("<option></option>").attr("value", landuseobj.landusetypeid).text(landuseobj.landusetypeEn)); 

			});
			jQuery("#existing_use_LR").val(land_RV[0].landusetypeid);
			jQuery("#proposed_use_LR").val(land_RV[0].proposedused);

	commentviewLandDialogPop = $("#landrecordsview").dialog({
		autoOpen : false,
		height : 450,
		width : 1200,
		resizable : true,
		modal : true,

		buttons : [ {
			text : "Cancel",
			"id" : "comment_cancel",
			click : function() {
				setInterval(function() {

				}, 4000);
				commentviewLandDialogPop.dialog("destroy");

			}
		} ],
		close : function() {

		}
	});
	$("#comment_cancel").html('<span class="ui-button-text">cancel</span>');
	commentviewLandDialogPop.dialog("open");
}
