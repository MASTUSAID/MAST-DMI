var selectedItem=null;
var dataList=null;
var projList=null;
var spatialList=null;
var socialtenureList=null;
var associatednaturalPersonList=null;
var genderList=null;
var maritalList=null;
var tenuretypeList=null;
var multimediaList=null;
var educationsList=null;
var landUserList=null;
var tenureclassList=null;
var occtypeList=null;
var workflowhistoryList=null;
var socialEditTenureList=null;
var project=null;
var sourceDocList=null;
var usinId=null;
var read=null;
var soilQualityList=null;
var typeofLandList=null;
var slopeList=null;
var groupTypeList=null;
var attributeList=null;
var vectors=null;
var DeletedNaturalList=null;
var DeletedNonNaturalList=null;
var deletedAdminList=null;
var ProjectAreaList=null;
var personList=null;
var ID=null;
var year=null;
var activeProject="";
var URL=null;
var resultDeleteNatural=null;
var records_from=0;
var totalRecords=null;
var searchRecords=null;
var eduList=null;
var naturalPerson_gid=null
var administratorID=null;
var adminDataList=null;
var checkNewNatural=false;
var hamletList=null;
var person_subtype=null;
var selectedHamlet=null;
var deceasedPersonList=null;
var validator=null;
var adjudicatorList=null;
var checkpersonType=false;
function LandRecords(_selectedItem)
{

	selectedItem=_selectedItem;
	URL="landrecords/spatialunit/default/"+0;
	if(activeProject!="")
	{
		URL="landrecords/spatialunit/"+activeProject+"/"+0;
	}
	jQuery.ajax({ 
		url: URL,
		async:false,							
		success: function (data) {	

			dataList=data;
			records_from=0;
			searchRecords=null;

		}


	});

	jQuery.ajax({ 
		url: "landrecords/status/",
		async:false,							
		success: function (data) {	

			statusList=data;
		}


	});

	jQuery.ajax({ 
		url: "landrecords/spatialunit/"+activeProject,
		async:false,							
		success: function (data) {	

			totalRecords=data;
		}


	});

	displayRefreshedLandRecords('All');
	$('body').find("#editattribute-dialog-form").remove();

}

function displayRefreshedLandRecords(_landRecord)
{
	var URL="landrecords/";
	if(activeProject!=""){

		URL="landrecords/"+activeProject;
	}
	jQuery.ajax({
		url: URL,
		success: function (data) {
			projList=data;

			jQuery("#landrecords-div").empty();

			jQuery.get("resources/templates/viewer/" + selectedItem + ".html", function (template) {

				jQuery("#landrecords-div").append(template);

				jQuery('#landRecordsFormdiv').css("visibility", "visible");		    			    	

				jQuery("#landRecordsRowData").empty();

				jQuery("#landRecordsTable").show();	        	


				if(dataList.length!=0 && dataList.length!=undefined)
				{
					jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
				}

				jQuery("#status_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select")); 
				jQuery.each(statusList, function (i, statusobj) {
					jQuery("#status_id").append(jQuery("<option></option>").attr("value", statusobj.workflowStatusId).text(statusobj.workflowStatus)); 

				});


				jQuery("#records_from").val(records_from+1);

				jQuery("#records_to").val(records_from+10);
				if(totalRecords<=records_from+10)
					jQuery("#records_to").val(totalRecords);
				jQuery('#records_all').val(totalRecords);

				jQuery("#projectName").text(data.name);
				jQuery("#country").text(data.countryName);
				jQuery("#region").text(data.region);
				jQuery("#district").text(data.districtName);
				jQuery("#village").text(data.village);
				jQuery("#hamlet").text("--");
				if(data.hamlet!="" && data.hamlet!=null ){

					jQuery("#hamlet").text(data.hamlet);
				}
				$("#landRecordsTable").trigger("update");
				/*$("#landRecordsTable").tablesorter({
					headers: {5: {sorter: false  } },	
					debug: false, sortList: [[0, 1]], widgets: ['zebra'] })
					.tablesorterPager({ container: $("#landrecords_pagerDiv"), positionFixed: false });*/

				$("#landRecordsTable").tablesorter( {sortList: [[0,1], [1,0]]} ); 


			});		

		}
	});

}

function displayRefreshedTenure()
{
	var id=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/socialtenure/"+id,
		async:false,							
		success: function (data) {	

			jQuery("#tenureRowData").empty();
			if(data!="")
			{

				jQuery("#tenureinfoTemplate").tmpl(data).appendTo("#tenureRowData");

			}

		}

	});
}

function updateUKA(id,statusId)

{
	/*	if(statusId==1)
	{
		jQuery.ajax({ 
			url: "ukanumber/"+id,
			async:false,							
			success: function (data) {	
				jQuery("#uka_no").val("");
				if(data.length!=0 && data.length!=undefined)
					jQuery("#uka_no").val(data);

			}

		});

		jQuery("#primeryky").val(id);

		genAttrDialog = $( "#attribute-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 234,
			resizable: true,
			modal: true,

			buttons: {
				"Update": function() 
				{
					updateUkaNum();



				},
				"Cancel": function() 
				{
					genAttrDialog.dialog( "destroy" );
					genAttrDialog.dialog( "close" );
				}
			},
			close: function() {
				genAttrDialog.dialog( "destroy" );

			}
		});

		genAttrDialog.dialog( "open" );	

	}

	else{

		jAlert('Action is only applicable for New Status','Alert');
	}
	 */

}

function editAttributeNew(usin,statusId)

{
	if(statusId==1 || statusId==4 || statusId==6)
	{
		read=false;
		editAttribute(usin);


	}

	else{
		read=true;
		editAttribute(usin);

	}

}
function editAttribute(id)
{
	//jQuery(".hideButton").show();
	displayAttributeCategory(1,id);


	jQuery.ajax({ 
		url: "landrecords/landusertype/",
		async:false,							
		success: function (data) {	

			landUserList=data;
		}


	});


	jQuery.ajax({ 
		url: "landrecords/soilquality/",
		async:false,							
		success: function (data) {	

			soilQualityList=data;
		}


	});


	jQuery.ajax({ 
		url: "landrecords/typeofland/",
		async:false,							
		success: function (data) {	

			typeofLandList=data;
		}


	});

	jQuery.ajax({ 
		url: "landrecords/slope/",
		async:false,							
		success: function (data) {	

			slopeList=data;
		}


	});

	jQuery.ajax({ 
		url: STUDIO_URL + "adjudicators/"+activeProject,
		async:false,							
		success: function (data) {	

			adjudicatorList=data;
		}


	});
	/*	jQuery.ajax({ 
		url: "adminfetch/"+id,
		async:false,							
		success: function (data) {	
			adminDataList=data;
			jQuery("#adminRowData").empty();
			if(adminDataList.length!=0 && adminDataList.length!=undefined)
			{

				jQuery("#adminTemplate").tmpl(adminDataList).appendTo("#adminRowData");
				jQuery("#hideTable").show();
			}
			else{

				jQuery("#hideTable").hide();
			}
		}
	});*/



	jQuery.ajax({ 
		url: "landrecords/personwithinterest/"+id,
		async:false,							
		success: function (data) {	

			nxtTokin=data;

			jQuery("#nxtTokinRowData").empty();
			if(nxtTokin.length!=0 && nxtTokin.length!=undefined)
			{

				jQuery("#nxtTokinTemplate").tmpl(nxtTokin).appendTo("#nxtTokinRowData");
				jQuery("#hideTable1").show();
			}
			else{

				jQuery("#hideTable1").hide();
			}

		}


	});

	jQuery.ajax({ 
		url: "landrecords/deceasedperson/"+id,
		async:false,							
		success: function (data) {	

			deceasedPersonList=data;

			jQuery("#deceasedRowData").empty();
			if(deceasedPersonList.length!=0 && deceasedPersonList.length!=undefined)
			{

				jQuery("#deceasedTemplate").tmpl(deceasedPersonList).appendTo("#deceasedRowData");
				jQuery("#hideTable").show();
			}
			else{

				jQuery("#hideTable").hide();
			}

		}


	});
	jQuery.ajax({ 
		url: "landrecords/multimedia/edit/"+id,
		async:false,							
		success: function (data) {
			multimediaList=data;
			jQuery("#multimediaRowData").empty();
			if(data.length!=0 && data.length!=undefined)
			{
				jQuery("#multimediaTemplate").tmpl(data).appendTo("#multimediaRowData");

			}

		}

	});

	jQuery("#primary").val(id);

	jQuery.ajax({ 
		url: "landrecords/socialtenure/"+id,
		async:false,							
		success: function (data) {	

			socialtenureList=data;

			jQuery("#tenureRowData").empty();
			jQuery("#naturalpersonRowData").empty();
			jQuery("#non_naturalpersonRowData").empty();
			jQuery("#associatedNaturalRowData").empty();
			if (data!='' )
			{

				jQuery("#tenureinfoTemplate").tmpl(data[0]).appendTo("#tenureRowData");

				for(i=0;i<data.length;i++)
				{	

					//jQuery("#tenureinfoTemplate").tmpl(data[i]).appendTo("#tenureRowData");

					if(data[i].person_gid.person_type_gid.person_type_gid==1)
					{
						checkpersonType=false;
						jQuery('.hidenonTable').hide();
						if(data[i].person_gid.active==true)
						{	
							jQuery("#naturalTable").show();
							$(".showNatural").show();

							jQuery("#associatedNaturalTable").hide();
							jQuery("#naturalpersonTemplate").tmpl(data[i]).appendTo("#naturalpersonRowData");

						}



					}

					if(data[i].person_gid.person_type_gid.person_type_gid==2)
					{
						checkpersonType=true;
						jQuery('#person_subType').hide();
						jQuery('#subtypehide').hide();
						if(data[i].person_gid.active==true)
						{

							jQuery("#non_naturalpersonTemplate").tmpl(data[i]).appendTo("#non_naturalpersonRowData");

							jQuery('.hidenonTable').show();
							jQuery('#person_subType').attr('disabled', true);
							var poc_gid=data[i].person_gid.poc_gid;

							jQuery.ajax({ 
								url: "landrecords/naturalpersondata/"+poc_gid,
								async:false,							
								success: function (result) {

									associatednaturalPersonList=result;
									$(".delHide").hide();
									$('#naturalTable td:nth-child(8)').hide();
									jQuery("#naturalTable").hide();

									jQuery("#associatedNaturalTable").show();
									jQuery("#naturalpersonTemplate_2").tmpl(associatednaturalPersonList).appendTo("#associatedNaturalRowData");
								}


							});
						}
					}
				}
			}

		}
	});



	jQuery.ajax({ 
		url: "landrecords/editattribute/"+id,
		async:false,							
		success: function (data) {	

			editList=data;

			jQuery("#projname").text(projList.name);
			jQuery("#spatialid").text(data[0].usinStr);
			jQuery("#usinStr_key").val(data[0].usinStr);
			jQuery("#household").val(data[0].househidno);
			jQuery("#identity").val(data[0].identity);
			jQuery("#landowner").val(data[0].landOwner);

			jQuery("#house_type").empty();
			jQuery("#type").empty();
			jQuery("#person_type").empty();

			jQuery("#existing_use").empty();
			jQuery("#existing_use").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(landUserList, function (i, landuseobj) {
				jQuery("#existing_use").append(jQuery("<option></option>").attr("value", landuseobj.landUseTypeId).text(landuseobj.landUseType)); 

			});

			jQuery("#proposed_use").empty();
			jQuery("#proposed_use").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(landUserList, function (i, landuseobj) {
				jQuery("#proposed_use").append(jQuery("<option></option>").attr("value", landuseobj.landUseTypeId).text(landuseobj.landUseType)); 

			});

			jQuery("#soil_quality").empty();
			jQuery("#soil_quality").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(soilQualityList, function (i, soilQualityobj) {
				jQuery("#soil_quality").append(jQuery("<option></option>").attr("value", soilQualityobj.id).text(soilQualityobj.quality)); 

			});

			jQuery("#slope").empty();
			jQuery("#slope").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(slopeList, function (i, slopeobj) {
				jQuery("#slope").append(jQuery("<option></option>").attr("value", slopeobj.id).text(slopeobj.slope_value)); 

			});

			jQuery("#land_type").empty();
			jQuery("#land_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(typeofLandList, function (i, landTypeobj) {
				jQuery("#land_type").append(jQuery("<option></option>").attr("value", landTypeobj.landTypeId).text(landTypeobj.landTypeValue)); 

			});

			jQuery("#witness_1").empty();
			jQuery("#witness_1").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			if(adjudicatorList.length!=0 && adjudicatorList.length!=undefined){

				jQuery.each(adjudicatorList, function (i, adjobj) {

					jQuery("#witness_1").append(jQuery("<option></option>").attr("value", adjobj.adjudicatorName).text(adjobj.adjudicatorName)); 

				});
			}


			jQuery("#witness_2").empty();
			jQuery("#witness_2").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			if(adjudicatorList.length!=0 && adjudicatorList.length!=undefined){
				jQuery.each(adjudicatorList, function (i, adjobj) {

					jQuery("#witness_2").append(jQuery("<option></option>").attr("value", adjobj.adjudicatorName).text(adjobj.adjudicatorName)); 

				});

			}



			if(data[0].landType!=null){
				jQuery("#land_type").val(data[0].landType.landTypeId);
			}

			if(data[0].slopeValues!=null){
				jQuery("#slope").val(data[0].slopeValues.id);
			}
			if(data[0].soilQualityValues!=null){
				jQuery("#soil_quality").val(data[0].soilQualityValues.id);
			}



			jQuery("#house_type").val(data[0].houseType);
			jQuery("#type").val(data[0].type);
			jQuery("#person_type").val(data[0].otherUseType);
			jQuery("#existing_use").val(0);
			jQuery("#proposed_use").val(0);
			if(data[0].existingUse!=null){
				jQuery("#existing_use").val(data[0].existingUse.landUseTypeId);
			}
			if(data[0].proposedUse!=null){
				jQuery("#proposed_use").val(data[0].proposedUse.landUseTypeId);
			}
			jQuery("#survey_date").val(data[0].surveyDate);
			jQuery("#type_name").val(data[0].typeName);
			jQuery("#perimeter").val(data[0].perimeter);
			jQuery("#house_shape").val(data[0].houseshape);
			jQuery("#area").val(data[0].area);
			jQuery("#measurement_unit").val(data[0].measurementUnit);

			jQuery("#comments").val(data[0].comments);
			jQuery("#gtype").val(data[0].gtype);
			jQuery("#imei_no").val(data[0].imeiNumber);
			jQuery("#postal_code").val(data[0].postal_code);
			jQuery("#address_1").val(data[0].address1);
			jQuery("#address_2").val(data[0].address2);
			jQuery("#usertable_id").val(data[0].user.id);
			jQuery("#project_key").val(data[0].project);
			jQuery("#uka_no_gen").val(data[0].propertyno);
			jQuery("#workflow_status").val(data[0].status.workflowStatusId);
			if(adjudicatorList.length!=0 && adjudicatorList.length!=undefined){
				jQuery("#witness_1").val(data[0].witness_1);
				jQuery("#witness_2").val(data[0].witness_2);	
			}
			else{
				jQuery("#witness_1").append(jQuery("<option></option>").attr("value", data[0].witness_1).text(data[0].witness_1));
				jQuery("#witness_2").append(jQuery("<option></option>").attr("value", data[0].witness_2).text(data[0].witness_2));
				jQuery("#witness_1").val(data[0].witness_1);
				jQuery("#witness_2").val(data[0].witness_2);
				/*jQuery("#witness_1").text(data[0].witness_1);
				jQuery("#witness_2").text(data[0].witness_2);*/
			}

			/*jQuery("#witness_3").val(data[0].witness_3);
			jQuery("#witness_4").val(data[0].witness_4);*/
			jQuery("#neighbor_north").val(data[0].neighbor_north);
			jQuery("#neighbor_south").val(data[0].neighbor_south);
			jQuery("#neighbor_east").val(data[0].neighbor_east);
			jQuery("#neighbor_west").val(data[0].neighbor_west);

		}

	});


	editAttrDialog = $( "#editattribute-dialog-form" ).dialog({
		autoOpen: false,
		height: 520,
		closed: false,
		cache: false,
		width: 850,
		resizable: false,
		modal: true,

		buttons: {


			"Update Attributes": function() 
			{

				updateattributesGen();

			},
			"Cancel": function() 
			{
				editAttrDialog.dialog( "destroy" ); 
				$('#tabs').tabs("select","#tabs-1");

			}
		},

		close : function() {
			//editAttrDialog.dialog( "close" );
			editAttrDialog.dialog( "destroy" ); 
			$('#tabs').tabs("select","#tabs-1");

		}

	});

	editAttrDialog.dialog( "open" );	
	jQuery('.gendisable').attr('disabled', false);
	jQuery('.justread').attr('readonly', false);
	$(".hideNatural").hide();
	$('#naturalTable td:nth-child(10)').hide();
	/*$('#naturalTable td:nth-child(11)').hide();
	$('#naturalTable td:nth-child(12)').hide();*/

	$(".hideAssociate").hide();
	$('#associatedNaturalTable td:nth-child(8)').hide();

	$(".showNatural").show();
	$('#naturalTable td:nth-child(8)').show();
	$('#naturalTable td:nth-child(9)').show();
	$('#naturalTable td:nth-child(11)').show();
	$('#naturalTable td:nth-child(12)').show();


	$(".hideNon").hide();
	$('#non_naturalTable td:nth-child(7)').hide();


	$(".showTenure").show();
	$('#tenureTable td:nth-child(5)').show();
	$('#tenureTable td:nth-child(6)').show();

	$(".showMul").show();
	$('#multimediaTable td:nth-child(6)').show();
	$('#multimediaTable td:nth-child(7)').show();

	$(".showkin").show();
	$('#nxtTokinTable td:nth-child(3)').show();
	$('#nxtTokinTable td:nth-child(4)').show();
	$(".hidekin").hide();
	$('#nxtTokinTable td:nth-child(5)').hide();

	$(".hidedeceased").hide();
	$('#deceasedTable td:nth-child(7)').hide();
	$(".showdeceased").show();
	$('#deceasedTable td:nth-child(5)').show();
	$('#deceasedTable td:nth-child(6)').show();

	$(".hideTenure").hide();
	$('#tenureTable td:nth-child(7)').hide();
	$(".showTenure").show();

	$(".hideMul").hide();
	$('#multimediaTable td:nth-child(9)').hide();
	$(".showMul").show();

	$(".showAddPerson").show();
	$(".showNonAddPerson").show();

	$(".hideNew").show();


	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update")').button().hide();

		$(".hideNatural").show();
		$('#naturalTable td:nth-child(10)').show();


		$(".hideAssociate").show();
		$('#associatedNaturalTable td:nth-child(8)').show();

		$(".hideNon").show();
		$('#non_naturalTable td:nth-child(7)').show();

		$(".hideTenure").show();
		$('#tenureTable td:nth-child(7)').show();

		$(".hideMul").show();
		$('#multimediaTable td:nth-child(9)').show();

		jQuery('.gendisable').attr('disabled', true);
		jQuery('.justread').attr('readonly', true);
		$(".showNatural").hide();
		$('#naturalTable td:nth-child(8)').hide();
		$('#naturalTable td:nth-child(9)').hide();
		$('#naturalTable td:nth-child(11)').hide();
		$('#naturalTable td:nth-child(12)').hide();

		$(".showAssociate").hide();
		$('#associatedNaturalTable td:nth-child(7)').hide();
		$('#associatedNaturalTable td:nth-child(9)').hide();
		$('#associatedNaturalTable td:nth-child(10)').hide();


		$(".showNon").hide();
		$('#non_naturalTable td:nth-child(5)').hide();
		$('#non_naturalTable td:nth-child(6)').hide();

		$(".showTenure").hide();
		$('#tenureTable td:nth-child(5)').hide();
		$('#tenureTable td:nth-child(6)').hide();

		$(".showMul").hide();
		$('#multimediaTable td:nth-child(6)').hide();
		$('#multimediaTable td:nth-child(7)').hide();

		$(".hidekin").show();
		$('#nxtTokinTable td:nth-child(5)').show();
		$(".showkin").hide();
		$('#nxtTokinTable td:nth-child(3)').hide();
		$('#nxtTokinTable td:nth-child(4)').hide();

		$(".hidedeceased").show();
		$('#deceasedTable td:nth-child(7)').show();
		$(".showdeceased").hide();
		$('#deceasedTable td:nth-child(5)').hide();
		$('#deceasedTable td:nth-child(6)').hide();
		
		




		$(".showAddPerson").hide();
		$(".showNonAddPerson").hide();
		$(".hideNew").hide();

	}

}

function editHamlet(Id,statusId)
{
	if(statusId==1)
	{
		jQuery.ajax({ 
			url: "landrecords/hamletbyusin/"+Id,
			async:false,							
			success: function (data) {	
				selectedHamlet=data;
			}
		});


		jQuery.ajax({ 
			url: "landrecords/hamletname/"+activeProject,
			async:false,							
			success: function (data) {	
				hamletList=data;
				jQuery("#hamlet_id").empty();
				if(selectedHamlet[0].hamlet_Id!=null){
					jQuery("#hamlet_id").append(jQuery("<option></option>").attr("value",selectedHamlet[0].hamlet_Id.id).text(selectedHamlet[0].hamlet_Id.hamletName));
					jQuery.each(hamletList, function (i, hamletobj) {
						if(hamletobj.hamletName!=selectedHamlet[0].hamlet_Id.hamletName)
							jQuery("#hamlet_id").append(jQuery("<option></option>").attr("value", hamletobj.id).text(hamletobj.hamletName)); 


					});	
				}
				else{
					jQuery("#hamlet_id").append(jQuery("<option></option>").attr("value",0).text("Please Select"));
					jQuery.each(hamletList, function (i, hamletobj) {

						jQuery("#hamlet_id").append(jQuery("<option></option>").attr("value", hamletobj.id).text(hamletobj.hamletName)); 


					});	

				}


			}
		});

		genAttrDialog = $( "#attribute-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 234,
			resizable: true,
			modal: true,

			buttons: {
				"Update": function() 
				{
					if(selectedHamlet[0].hamlet_Id!=null)
						updateHamlet(Id,selectedHamlet[0].hamlet_Id.id);
					else{
						updateHamlet(Id,0);
					}
				},
				"Cancel": function() 
				{
					genAttrDialog.dialog( "destroy" );
					genAttrDialog.dialog( "close" );
				}
			},
			close: function() {
				genAttrDialog.dialog( "destroy" );

			}
		});

		genAttrDialog.dialog( "open" );	
	}
	else{
		jAlert("Update is only applicable for New Status","Alert");
	}

	/*	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updateuka" ,
		data: jQuery("#addAttributeformID").serialize(),
		success: function (result) 
		{  
			if(searchRecords!=null)
			{
				spatialSearch(records_from);

			}
			else
			{

				spatialRecords(records_from);
			}
			//LandRecords("landRecords");
			genAttrDialog.dialog("destroy");

			//$("#attribute-dialog-form").remove();



		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});*/


}
function updateHamlet(Id,hamId)
{
	var dropdownId=jQuery("#hamlet_id").val();
	if(dropdownId==hamId || dropdownId==0)
	{
		jAlert("Select another hamlet","Alert");
	}
	else{
		jQuery("#primeryky").val(Id);
		jQuery.ajax({
			type:"POST",        
			url: "landrecords/updatehamlet/"+activeProject ,
			data: jQuery("#addAttributeformID").serialize(),
			success: function (result) 
			{  
				if(result)
				{
					jAlert("Data Successfully saved","Hamlet");
					if(searchRecords!=null)
					{
						spatialSearch(records_from);

					}
					else
					{

						spatialRecords(records_from);
					}
					genAttrDialog.dialog( "destroy" );
					genAttrDialog.dialog( "close" );
				}
				else
				{
					jAlert("Request not completed");
				}
			}
		});
	}
}

function updateattributesGen()
{	
	$("#editAttributeformID").validate({

		rules: {

			household: "required",
			gtype: "required",
			/*	postal_code:{

				required: true,
				number: true,
				min : 1,
				max :9999999999
			},*/
			neighbor_north:"required",
			witness_1:"required",
			neighbor_south:"required",
			witness_2:"required",
			neighbor_east:"required",
			neighbor_west:"required"


		},
		messages: {
			household: "Please enter number of Household",
			gtype: "Please enter  Geometry Type",
			//postal_code: "Please enter valid Postal Code",
			neighbor_north:"Please enter neighbour name",
			witness_1:"Please enter witness",
			neighbor_south:"Please enter neighbour name",
			witness_2:"Please enter witness",
			neighbor_east:"Please enter neighbour name",
			neighbor_west:"Please enter neighbour name"

		}

	});

	if ($("#editAttributeformID").valid())
	{
		//if($('#existing_use').val()!=0){

		if($('#proposed_use').val()!=0){

			//if($('#soil_quality').val()!=0){

			//if($('#slope').val()!=0){

			if($('#land_type').val()!=0){

				updateattributes();

			}
			else{
				alert("Please select Land Type");
			}

			/*}
					else{
						alert("Please select Slope");
					}*/

			/*}
				else{
					alert("Please select Soil Quality");
				}*/
		}
		else{
			alert("Please select Proposed Use");
		}
		//}
		/*else{
			alert("Please Select Existing Use");
		}*/

	}

	else{

		jAlert("Please Fill Mandatory Details","Alert");
	}

} 



function updateattributes()
{

	var length_gen=attributeList.length;
	jQuery("#general_length").val(0);
	if(length_gen!=0 && length_gen!=undefined)
		jQuery("#general_length").val(length_gen);
	jQuery("#projectname_key1").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updateattributes" ,
		data: jQuery("#editAttributeformID").serialize(),
		success: function (result) 
		{  
			if(result)
			{

				finalValidation();

			}
			else
			{
				jAlert('Request not completed');
			}


		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});

}


function updateStatus(id,statusId)

{

	if(statusId==4)

	{
		jAlert('Status is already Approved','Status');

	}

	else if(statusId==1)
	{
		jConfirm('Do you want to <strong> Approve </strong> selected Spatial Unit? ', 'Update Confirmation', function (response) {

			if (response) {
				jQuery.ajax({
					type:"GET",        

					url : "landrecords/updateapprove/"+id ,
					success: function (result) 
					{  

						if(result)
						{
							if(searchRecords!=null)
							{
								spatialSearch(records_from);

							}
							else
							{

								spatialRecords(records_from);
							}
						}

						else{
							jAlert('Request not completed');
						}			

					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {

						jAlert('Request not completed');
					}
				});
			}

		});

	}
	else{
		jAlert('Action is only applicable for New Status','Alert');
	}
}

function rejectStatus(id,statusId)

{

	if(statusId==5)

	{
		jAlert('Status is already Rejected','Status');

	}

	else if (statusId==1){
		jConfirm('Do you want to <strong> Reject </strong> selected Spatial Unit?', 'Update Confirmation', function (response) {

			if (response) {
				jQuery.ajax({
					type:"GET",        
					url: "landrecords/rejectstatus/"+id ,
					success: function (result) 
					{  

						if(result)
						{
							if(searchRecords!=null)
							{
								spatialSearch(records_from);

							}
							else
							{

								spatialRecords(records_from);
							}
						}

						else {
							jAlert('Request not completed');
						}			

					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {

						jAlert('Request not completed');
					}
				});
			}

		});
	}
	else{
		jAlert('Action is only applicable for New Status','Alert');
	}
}

function search()

{
	/*var usinid=$('#usin_id').val();*/
	var usinStrid=$('#usinstr_id').val();
	var ukaid=$('#uka_id').val();
	var statusid=$('#status_id').val();
	var todate=$('#to_id').val();
	var fromdate=$('#from_id').val();

	if(usinStrid=='' && ukaid=='' && statusid==0 && todate=='' && fromdate=='')
	{

		jAlert('Please Enter Parameters to Search','Search');

	}

	else
	{


		jQuery.ajax({
			type:"POST",   
			async:false,
			url: "landrecords/search/"+activeProject,
			data: jQuery("#landrecordsform").serialize(),
			success: function (result) 
			{

				searchRecords=result;
			}


		});

		jQuery.ajax({

			type:"POST",        
			url: "landrecords/search/"+activeProject+"/"+0,

			data: jQuery("#landrecordsform").serialize(),
			success: function (result) 
			{

				if(result.length!=undefined && result.length!=0 ){
					if(result!="")
					{
						records_from=0;
						jQuery("#landrecords-div").empty();

						jQuery.get("resources/templates/viewer/" + selectedItem + ".html;", function (template) {

							jQuery("#landrecords-div").append(template);

							jQuery('#landRecordsFormdiv').css("visibility", "visible");		    			    	

							jQuery("#landRecordsRowData").empty();

							$('#usinstr_id').val(usinStrid);
							$('#uka_id').val(ukaid);
							$('#status_id').val(statusid);
							$('#to_id').val(todate);
							$('#from_id').val(fromdate);



							jQuery("#landRecordsTable").show();
							jQuery("#landRecordsAttrTemplate").tmpl(result).appendTo("#landRecordsRowData");

							jQuery("#status_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select")); 
							jQuery.each(statusList, function (i, statusobj) {
								jQuery("#status_id").append(jQuery("<option></option>").attr("value", statusobj.workflowStatusId).text(statusobj.workflowStatus)); 

							});


							jQuery("#records_from").val(records_from+1);
							jQuery("#records_to").val(searchRecords);
							if(records_from+10<=searchRecords)
								$('#records_to').val(records_from+10);
							jQuery('#records_all').val(searchRecords);


							jQuery("#status_id").val(statusid);
							jQuery("#projectName").text(projList.name);

							jQuery("#country").text(projList.countryName);
							jQuery("#region").text(projList.region);
							jQuery("#district").text(projList.districtName);
							jQuery("#village").text(projList.village);
							jQuery("#hamlet").text("--");
							if(projList.hamlet!="" && projList.hamlet!=null ){

								jQuery("#hamlet").text(projList.hamlet);
							}

							$("#landRecordsTable").trigger("update");	
							/*$("#landRecordsTable").tablesorter({

								headers: {5: {sorter: false  } },	
								debug: false, sortList: [[0, 1]], widgets: ['zebra'] })

								.tablesorterPager({ container: $("#landrecords_pagerDiv"), positionFixed: false })
							 */

							$("#landRecordsTable").tablesorter( {sortList: [[0,1], [1,0]]} );

						}); 

					}
					else{
						jAlert("No Data Available","Search");
					}
				}
				else{


					jAlert("No Data Available","Search");
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {

				jAlert('Request not completed');
			}
		});

	}

}


function editNaturalData(id)

{

	jQuery(".hidden_alias").hide();

	displayAttributeCategory(2,id);

	/*	jQuery.ajax({ 
		url: "landrecords/educationlevel/",
		async:false,							
		success: function (data) {	

			educationsList=data;
		}


	});*/


	jQuery.ajax({ 
		url: "landrecords/gendertype/",
		async:false,							
		success: function (data) {	

			genderList=data;
		}


	});


	jQuery.ajax({ 
		url: "landrecords/personsubtype/",
		async:false,							
		success: function (data) {	

			person_subtype=data;
		}


	});

	jQuery.ajax({ 
		url: "landrecords/maritalstatus/",
		async:false,							
		success: function (data) {	

			maritalList=data;
		}


	});
	jQuery.ajax({ 
		url: "landrecords/citizenship/",
		async:false,							
		success: function (data) {	

			jQuery("#citizenship").empty();
			jQuery("#citizenship").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(data, function (i, citizenobj) {
				if(citizenobj.id!=0)
				jQuery("#citizenship").append(jQuery("<option></option>").attr("value",citizenobj.id ).text(citizenobj.citizenname)); 

			});
		}


	});


	jQuery.ajax({ 
		url: "landrecords/naturalperson/"+id,
		async:false,							
		success: function (data) {
			jQuery("#gender").empty();
			jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(genderList, function (i, genderobj) {
				jQuery("#gender").append(jQuery("<option></option>").attr("value",genderobj.genderId ).text(genderobj.gender)); 

			});

			jQuery("#marital_status").empty();
			jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(maritalList, function (i, maritalobj) {
				jQuery("#marital_status").append(jQuery("<option></option>").attr("value",maritalobj.maritalStatusId ).text(maritalobj.maritalStatus)); 

			});

		


			/*
			jQuery("#edu").empty();
			jQuery("#edu").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(educationsList, function (i, eduobj) {
				jQuery("#edu").append(jQuery("<option></option>").attr("value",eduobj.levelId ).text(eduobj.educationLevel)); 

			});*/

			jQuery("#person_subType").empty();
			jQuery("#person_subType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(person_subtype, function (i, personSubtypeobj) {
				if(personSubtypeobj.person_type_gid!=1 && personSubtypeobj.person_type_gid!=2)
					jQuery("#person_subType").append(jQuery("<option></option>").attr("value",personSubtypeobj.person_type_gid ).text(personSubtypeobj.personType)); 

			});

			/*	jQuery("#owner_nat").empty();
			jQuery("#owner_nat").append(jQuery("<option></option>").attr("value", 1).text("Yes"));
			jQuery("#owner_nat").append(jQuery("<option></option>").attr("value", 0).text("No"));


			jQuery("#resident").empty();
			jQuery("#resident").append(jQuery("<option></option>").attr("value", 1).text("Yes"));
			jQuery("#resident").append(jQuery("<option></option>").attr("value", 0).text("No"));*/


			if(data!=null && data!="" && data!=undefined )
			{
				if(data[0].marital_status!=null)
				{
					jQuery("#marital_status").val(data[0].marital_status.maritalStatusId);
				}
				if(data[0].gender!=null){
					jQuery("#gender").val(data[0].gender.genderId);
				}
				if(data[0].personSubType!=null){
					jQuery("#person_subType").val(data[0].personSubType.person_type_gid);
				}
				if(data[0].citizenship_id!=null)
				{
					jQuery("#citizenship").val(data[0].citizenship_id.id);
				}

				jQuery("#natural_key").val(id);	
				jQuery("#name").val(data[0].alias);
				//jQuery("#tenure").val(data[0].tenure_Relation);
				jQuery("#fname").val(data[0].firstName);
				jQuery("#mname").val(data[0].middleName);
				jQuery("#lname").val(data[0].lastName);
				jQuery("#mobile_natural").val(data[0].mobile);
				if(data[0].age!=0){
					jQuery("#age").val(data[0].age);
				}
				else{
					jQuery("#age").val( );
				}
				jQuery("#occ_age").val(data[0].occAgeBelow);
				/*jQuery("#occ").val(data[0].occupation);
				if(data[0].education!=null)
				{
					jQuery("#edu").val(data[0].education.levelId);
				}*/


				/*	jQuery("#resident").val(0);
				if(data[0].resident_of_village==true){
					jQuery("#resident").val(1);
				}

				jQuery("#owner_nat").val(0);
				if(data[0].personSubType.person_type_gid==3){
					jQuery("#owner_nat").val(1);
				}*/

				/*jQuery("#administrator").val(data[0].administator);*/
				/*jQuery("#citizenship").val(data[0].citizenship);*/
			}
		}

	});


	naturalPersonDialog = $( "#naturalperson-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Update Natural": function() 
			{
				updateAttributeNaturalPerson(false);

			},
			"Cancel": function() 
			{
				naturalPersonDialog.dialog( "destroy" );
				naturalPersonDialog.dialog( "close" );
				$('#tab-natural').tabs("select","#tabs-1");

			}
		},
		close: function() {
			naturalPersonDialog.dialog( "destroy" );
			$('#tab-natural').tabs("select","#tabs-1");

		}
	});

	naturalPersonDialog.dialog( "open" );	
	jQuery('.justread').attr('readonly', false);
	jQuery('.justdisable').attr('disabled', false);
	if(checkpersonType==true){
		jQuery('#person_subType').hide();
		jQuery('#subtypehide').hide();
		
	
	}
	else{
		jQuery('#person_subType').show();
		jQuery('#subtypehide').show();
	}
	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update Natural")').button().hide();
		jQuery('.justread').attr('readonly', true);
		jQuery('.justdisable').attr('disabled', true);
	}

}

function updateAttributeNaturalPerson(newPerson)

{
	$("#editNaturalPersonformID").validate({

		rules: {
			//name: "required",
			fname: "required",

			lname: "required",

			/*		age: {

				required: true,
				number: true,
				min : 1,
				max : 100
			},*/
			/*	mobile_natural: {

				required: true,
				number: true,


			},*/
			//occ:"required",
			//tenure:"required"

		},
		messages: {
			//name: "Please enter Alias Name",
			fname: "Please enter  First Name",
			lname: "Please enter Last Type",

			/*		age: "Please enter valid Age",
			mobile_natural: "Please enter Numeric value of Mobile NO.",*/
			//occ: "Please enter  Occupation",
			//tenure: "Please enter  Tenure"

		},


		ignore:[]
	});

	if ($("#editNaturalPersonformID").valid())
	{

		/*	var mobilelength=$('#mobile_natural').val();*/

		/*	if(mobilelength.length==10){*/
		var numeric_check=jQuery('#age').val();
		var ck =/^\d*\.?\d*$/;

		if(ck.test(numeric_check))
			{
		if($('#age').val()<18 && $('#person_subType').val()!=3){
			jAlert("person age must be more than or equal to 18","Info");

		}
		
		else{
			if($('#gender').val()!=0){
				/*if($('#edu').val()!=0)
					{*/
				if($('#marital_status').val()!=0)
				{

				/*	if($('#citizenship').val()!=0)
					{	*/


						if(newPerson){
							updateNewNaturalPerson();
							naturalPersonDialog.dialog( "destroy" );
							naturalPersonDialog.dialog( "close" );
						}
						else{
							updateNaturalPerson();

						}
				/*	}

					else{
						alert("Please select citizenship");
					}*/

				}
				else{
					alert("Please select Marital Status");
				}
				/*}
					else{
						alert("Please select Educational Qualification");
					}*/

			}
			else{
				alert("Please select gender");
			}
		}
			}
		else
			{
			alert("Enter numeric value of age");
			}

			
/*	}
		else{

			alert("Please Enter 10 digit mobile no.");

		}*/
	}

	else {

		jAlert("Please Fill Mandatory fields in all tabs","Message");
	}

}

function updateNaturalPerson()
{	
	
	var id=editList[0].usin;
	project=editList[0].project;
	var length_natural=attributeList.length;
	jQuery("#natual_length").val(0);
	if(length_natural!=0 && length_natural!=undefined)
		jQuery("#natual_length").val(length_natural);
	jQuery("#natural_usin").val(id);

	jQuery("#projectname_key").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatenatural" ,
		data: jQuery("#editNaturalPersonformID").serialize(),
		success: function (data) 
		{
			if(data)
			{
				naturalPersonDialog.dialog( "destroy" );
				jAlert('Data Sucessfully Saved',' Natural Person');
				editAttribute(id);
			}

			else
			{

				jAlert('Request not completed');

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});
	
}

function editNonNatural(id)
{

	displayAttributeCategory(5,id);

	jQuery.ajax({ 
		url: "landrecords/grouptype/",
		async:false,							
		success: function (data) {	

			groupTypeList=data;
		}

	});


	jQuery.ajax({ 
		url: "landrecords/nonnaturalperson/"+id,
		async:false,							
		success: function (data) {


			jQuery("#institution").val(data[0].institutionName);

			jQuery("#mobile_no").val(data[0].phoneNumber);
			jQuery("#address").val(data[0].address);
			jQuery("#non_natural_key").val(data[0].person_gid);
			jQuery("#poc_id").val(data[0].poc_gid);
			jQuery("#mobileGroupId").val(data[0].mobileGroupId);

			jQuery("#group_type").empty();
			jQuery("#group_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(groupTypeList, function (i, groupTypeobj) {
				jQuery("#group_type").append(jQuery("<option></option>").attr("value",groupTypeobj.groupId ).text(groupTypeobj.groupValue)); 

			});
			jQuery("#group_type").val(0);
			if(data[0].groupType!=null){
				jQuery("#group_type").val(data[0].groupType.groupId);
			}
		}


	});


	nonnaturalPersonDialog = $( "#non_naturalperson-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Update Non-Natural": function() 
			{
				updateAttributeNonNaturalPerson();

			},
			"Cancel": function() 
			{
				nonnaturalPersonDialog.dialog( "destroy" );
				nonnaturalPersonDialog.dialog( "close" );
				$('#tab-nonnatural').tabs("select","#tabs-3");

			}
		},
		close: function() {
			nonnaturalPersonDialog.dialog( "destroy" );
			$('#tab-nonnatural').tabs("select","#tabs-3");


		}
	});

	nonnaturalPersonDialog.dialog( "open" );
	jQuery('.read-non').attr('readonly', false);
	jQuery('.disablenon').attr('disabled', false);

	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update Non-Natural")').button().hide();
		jQuery('.read-non').attr('readonly', true);
		jQuery('.disablenon').attr('disabled', true);
	}


}

function updateAttributeNonNaturalPerson()

{

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
			mobile_no: "Please enter Numeric value of Mobile NO."

		},

		ignore:[]

	});

	if ($("#editNonNaturalPersonformID").valid())
	{

		var mobilelengthnon=$('#mobile_no').val();
		if(mobilelengthnon.length==10){
			if($('#group_type').val()!=0)
			{

				updateNonNaturalPerson();	
			}
			else{

				alert("Please select group type");
			}

		}

		else{

			alert("Please Enter 10 digit mobile no.");

		}
	}

	else{

		jAlert("Please Fill Mandatory fields in all tabs","Message");
	}



}

function updateNonNaturalPerson()

{


	var id=editList[0].usin;
	var length_nonnatural=attributeList.length;
	jQuery("#nonnatual_length").val(0);
	if(length_nonnatural!=0 && length_nonnatural!=undefined)
		jQuery("#nonnatual_length").val(length_nonnatural);
	jQuery("#projectname_key2").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatenonnatural" ,
		data: jQuery("#editNonNaturalPersonformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				nonnaturalPersonDialog.dialog( "destroy" );
				editAttribute(id);


				jAlert('Data Sucessfully Saved','Non Natural Person');
			}

			else
			{

				jAlert('Request not completed');
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});





}



function editTenure(id)
{
	jQuery('.hide_duration').hide();	

	displayAttributeCategory(4,id);

	jQuery.ajax({ 
		url: "landrecords/tenuretype/",
		async:false,							
		success: function (data) {	

			tenuretypeList=data;
		}

	});

	jQuery.ajax({ 
		url: "landrecords/occupancytype/",
		async:false,							
		success: function (data) {	

			occtypeList=data;
		}

	});

	jQuery.ajax({ 
		url: "landrecords/tenureclass/",
		async:false,							
		success: function (data) {	

			tenureclassList=data;

		}

	});

	jQuery.ajax({ 
		url: "landrecords/socialtenure/edit/"+id,
		async:false,							
		success: function (data) {

			socialEditTenureList=data;
			jQuery("#start_date").val(data[0].social_tenure_startdate);
			jQuery("#end_date").val(data[0].social_tenure_enddate);
			jQuery("#duration").val(data[0].tenure_duration);
			jQuery("#tenure_key").val(data[0].gid);
			jQuery("#usin").val(data[0].usin);
			jQuery("#person_gid").val(data[0].person_gid.person_gid);
			jQuery("#tenureclass_id").empty();
			jQuery("#tenure_type").empty();
			/*jQuery("#occtype_id").empty();*/

			jQuery("#tenureclass_id").append(jQuery("<option></option>").attr("value", 2).text(tenureclassList.tenureClass));
			jQuery.each(tenureclassList, function (i, tenureclassobj) {
				if(tenureclassList[i].active)
					jQuery("#tenureclass_id").append(jQuery("<option></option>").attr("value",tenureclassobj.tenureId ).text(tenureclassobj.tenureClass)); 

			});
			jQuery("#tenure_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(tenuretypeList, function (i, tenureobj) {
				jQuery("#tenure_type").append(jQuery("<option></option>").attr("value",tenureobj.gid ).text(tenureobj.shareType)); 

			});


			jQuery("#tenure_resident").empty();
			jQuery("#tenure_resident").append(jQuery("<option></option>").attr("value", 2).text("Yes"));
			jQuery("#tenure_resident").append(jQuery("<option></option>").attr("value", 1).text("No"));
			/*	jQuery("#occtype_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(occtypeList, function (i, occupancyobj) {
				jQuery("#occtype_id").append(jQuery("<option></option>").attr("value",occupancyobj.occId ).text(occupancyobj.description)); 

			});*/
			jQuery("#tenure_resident").val(1);
			if(data[0].resident==true){
				jQuery("#tenure_resident").val(2);
			}

			if(data[0].share_type!=null){
				jQuery("#tenure_type").val(data[0].share_type.gid);

			}
			if(data[0].tenureclass_id!=null){
				jQuery("#tenureclass_id").val(data[0].tenureclass_id.tenureId);
			}
			/*	if(data[0].occupancy_type_id!=null){
				jQuery("#occtype_id").val(data[0].occupancy_type_id.occId);
			}*/
		}

	});


	tenureDialog = $( "#tenureinfo-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Update Tenure": function() 
			{
				updateAttributeTenure();

			},
			"Cancel": function() 
			{
				tenureDialog.dialog( "destroy" );
				tenureDialog.dialog( "close" );
				$('#tab-tenure').tabs("select","#tabs-5");
			}
		},
		close: function() {
			tenureDialog.dialog( "destroy" );
			$('#tab-tenure').tabs("select","#tabs-5");

		}
	});

	tenureDialog.dialog( "open" );	
	jQuery('.read-tenure').attr('disabled', false);
	jQuery('.tenure-read').attr('readonly', false);

	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update Tenure")').button().hide();
		jQuery('.read-tenure').attr('disabled', true);
		jQuery('.tenure-read').attr('readonly', true);

	}
	$('#tenureclass_id').attr('disabled', true);
}

function updateAttributeTenure()
{

	$("#edittenureinfoformID").validate({

		rules: {
			tenure_type: "required",

			/*tenureclass_id: "required",*/
			/*occtype_id: "required"*/

		},
		messages: {
			tenure_type: "Please enter Tenure Type",

			/*tenureclass_id: "Please enter Tenure Class",*/
			/*occtype_id: "Please enter Occupation Type "*/
		}

	});

	if ($("#edittenureinfoformID").valid())
	{
		if($("#tenure_type").val()==0){
			alert("Please Select Tenure Type");
		}


		/*else if($("#tenureclass_id").val()==0)
		{

			alert("Please Select Tenure Class");

		}*/

		/*else if($("#occtype_id").val()==0)
		{

			alert("Please Select Occupancy Type");

		}*/

		else
		{
			updateTenure();

		}

	}

	else{

		jAlert("Please Fill Mandatory fields in all tabs","Message");
	}

}

function updateTenure()

{
	var id=editList[0].usin;
	var length=attributeList.length;
	jQuery("#tenure_length").val(0);
	if(length!=0 && length!=undefined)
		jQuery("#tenure_length").val(length);
	jQuery("#projectname_key3").val(project);


	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatetenure" ,
		data: jQuery("#edittenureinfoformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				tenureDialog.dialog( "destroy" );
				editAttribute(id);


				jAlert('Data Sucessfully Saved','Tenure Info');
			}

			else
			{

				jAlert('Request not completed');

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});

}

function editMultimedia(id)
{
	displayAttributeCategory(3,id);
	jQuery.ajax({ 
		url: "landrecords/multimedia/"+id,
		async:false,							
		success: function (data) {	

			jQuery("#entity_name").val(data[0].entity_name);
			jQuery("#scanned_srs").val(data[0].scanedSourceDoc);
			jQuery("#quality_type").val(data[0].qualityType);
			jQuery("#recordation").val(data[0].recordation);
			jQuery("#inventory_type").val(data[0].socialTenureInvantoryType);
			jQuery("#comments_multimedia").val(data[0].comments);
			jQuery("#primary_key").val(data[0].gid);
			jQuery("#multimedia_id").val(data[0].id);
			jQuery("#source_path").val(data[0].locScannedSourceDoc);
			jQuery("#usink").val(data[0].usin);
			jQuery("#person_gidk").val(data[0].person_gid);
			jQuery("#social_gid").val(data[0].social_tenure_gid);

		}

	});

	multimediaDialog = $( "#multimedia-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Update Multimedia": function() 
			{

				updateAttributeMultimedia();

			},
			"Cancel": function() 
			{
				multimediaDialog.dialog( "destroy" );
				multimediaDialog.dialog( "close" );
				$('#tab-multimedia').tabs("select","#tabs-7");
			}
		},
		close: function() {
			multimediaDialog.dialog( "destroy" );
			$('#tab-multimedia').tabs("select","#tabs-7");

		}
	});

	multimediaDialog.dialog( "open" );	
	jQuery('.read-mul').attr('readonly', false);
	jQuery('.disablemul').attr('disabled', false);

	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update Multimedia")').button().hide();
		jQuery('.read-mul').attr('readonly', true);
		jQuery('.disablemul').attr('disabled', true);

	}

}

function updateAttributeMultimedia()

{
	$("#editmultimediaformID").validate({

		rules: {
			scanned_srs: "required",
			//inventory_type: "required"

		},
		messages: {
			scanned_srs: "Please enter Source Document Name",
			//inventory_type: "Please enter  Inventory Type"
		},


		ignore:[]

	});

	if ($("#editmultimediaformID").valid())
	{
		updateMultimedia();
	}
	else
	{

		jAlert("Please Fill Mandatory fields in all tabs","Message");
	}

}
function updateMultimedia()

{
	var id=editList[0].usin;

	var length_multimedia=attributeList.length;
	jQuery("#multimedia_length").val(0);
	if(length_multimedia!=0 && length_multimedia!=undefined)
		jQuery("#multimedia_length").val(length_multimedia);
	jQuery("#projectname_multimedia_key").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatemultimedia" ,
		data: jQuery("#editmultimediaformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				multimediaDialog.dialog( "destroy" );
				editAttribute(id);


				jAlert('Data Sucessfully Saved','Multimedia');
			}

			else
			{

				jAlert('Request not completed');

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});

}


var deleteMultimedia= function (id,name) 
{	

	var usinid=editList[0].usin;

	jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {

		if (response) {
			jQuery.ajax({          
				type: 'GET',
				url: "landrecords/delete/"+id,
				success: function (result) 
				{ 
					if(result==true)
					{
						jAlert('Data Successfully Deleted', 'Multimedia');	                  
						editAttribute(usinid);
					}

					if(result==false)

					{
						jAlert('Data can not be deleted..already used by project', 'Multimedia');	                  

					}
				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Request not completed');
				}
			});
		}

	});

}

var deleteNatural= function (id,name) 
{	
	var usinid=editList[0].usin;

	if(socialtenureList.length==1)
	{

		jAlert('Person can not be deleted..single person exists', 'Person');	

	}

	else {




		jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {

			if (response) {
				jQuery.ajax({          
					type: 'GET',
					url: "landrecords/deleteNatural/"+id,
					success: function (result) 
					{ 
						resultDeleteNatural=result;
						if(resultDeleteNatural==true)
						{
							jAlert('Data Successfully Deleted', 'Person');	                  
							editAttribute(usinid);
						}

						if(resultDeleteNatural==false)

						{
							jAlert('Data can not be deleted..already used by project', 'Person');	                  

						}
					},

					error: function (XMLHttpRequest, textStatus, errorThrown) 
					{	                    
						jAlert('Request not completed');
					}
				});
			}

		});
	}
}


var deleteNonNatural= function (id,name) 
{	
	var usinid=editList[0].usin;


	jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {

		if (response) {
			jQuery.ajax({          
				type: 'GET',
				url: "landrecords/deletenonnatural/"+id,
				success: function (result) 
				{ 
					if(result==true)
					{
						jAlert('Data Successfully Deleted', 'Multimedia');	                  
						editAttribute(usinid);
					}

					if(result==false)

					{

						jAlert('Data Can not be deleted..Used by Project', 'Multimedia');	                  


					}
				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Request not completed');
				}
			});
		}

	});

}


var deleteTenure= function (id) 
{	
	var usinid=editList[0].usin;


	jConfirm('Are You Sure You Want To Delete', 'Delete Confirmation', function (response) {

		if (response) {
			jQuery.ajax({          
				type: 'GET',
				url: "landrecords/deleteTenure/"+id,
				success: function (result) 
				{ 
					if(result==true)
					{
						jAlert('Data Successfully Deleted', 'Multimedia');	                  
						editAttribute(usinid);
					}

					if(result==false)

					{

						jAlert('Data Can not be deleted..Used by Project', 'Multimedia');	                  


					}
				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Request not completed');
				}
			});
		}

	});

}


function displayAttributeCategory(id,gid)
{


	jQuery.ajax({ 
		url: "landrecords/attributedata/"+id+"/"+gid+"/",
		async:false,							
		success: function (data) {	

			attributeList=data;

		}


	});
	jQuery("#customnewnatural-div").empty();

	jQuery("#custom-div").empty();
	jQuery("#customtenure-div").empty();
	jQuery("#customnatural-div").empty();
	jQuery("#customnonnatural-div").empty();
	jQuery("#customgeneral-div").empty();
	jQuery("#custommultimedia-div").empty();


	if(attributeList.length>0)
	{	
		//jQuery('.datepicker').attr('readonly', true);
		$(".datepicker").datepicker();
		$(".datepicker").live('click', function() {
			$(this).datepicker({
				/*dateFormat : 'MM/dd/yyyy'*/
			}).focus();
		});


		for (var i = 0; i < attributeList.length; i++) {
			selectedUnitText=attributeList[i].alias;

			selectedUnitValue=attributeList[i].value;
			var datatype=attributeList[i].datatypeid;
			selectedUnitValues=attributeList[i].attributevalueid;

			if(id==1)
			{
				if(datatype==2){
					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_general'+""+i+""+'" class="input-medium justread datepicker"  name="alias_general'+""+i+""+'" type="text" value=""/><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_general'+""+i+""+'').val(selectedUnitValue);

				}
				else if(datatype==3){

					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_general'+""+i+""+'" class="input-medium gendisable" name="alias_general'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_general'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_general'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium justread" name="alias_general'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else {

					jQuery("#customgeneral-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_general'+""+i+""+'" type="text" class="input-medium justread" name="alias_general'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_general_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_general_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}

			else if(id==4)
			{
				if(datatype==2)
				{
					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_tenure'+""+i+""+'" class="input-medium tenure-read" name="alias_tenure'+""+i+""+'" type="Date" value=""/><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_tenure'+""+i+""+'').val(selectedUnitValue);

				}

				else if(datatype==3)

				{

					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_tenure'+""+i+""+'" class="input-medium read-tenure" name="alias_tenure'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_tenure'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{
					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_tenure'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium tenure-read" name="alias_tenure'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');

				}
				else{
					jQuery("#customtenure-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_tenure'+""+i+""+'" class="inputField01 splitpropclass tenure-read" name="alias_tenure'+""+i+""+'" type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_tenure_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_tenure_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}
			else if(id==2)
			{
				if(datatype==2)
				{

					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_natural'+""+i+""+'" class="input-medium justread" name="alias_natural'+""+i+""+'" type="Date" value=""/><input  id="alias_natural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_natural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==3)
				{

					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_natural'+""+i+""+'" class="input-medium justdisable" name="alias_natural'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_natural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_natural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_natural'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium justread" name="alias_natural'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_natural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_natural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else
				{
					jQuery("#customnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_natural'+""+i+""+'" class="inputField01 splitpropclass justread" name="alias_natural'+""+i+""+'"  type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_natural_key'+""+i+""+'"  name="alias_natural_key'+""+i+""+'"  type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}
			else if(id==5)
			{
				if(datatype==2)
				{

					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_nonnatural'+""+i+""+'" class="input-medium read-non" name="alias_nonnatural'+""+i+""+'" type="Date" value=""/><input  id="alias_nonnatural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_nonnatural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_nonnatural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==3)
				{

					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_nonnatural'+""+i+""+'" class="input-medium disablenon" name="alias_nonnatural'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_nonnatural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_nonnatural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_nonnatural'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_nonnatural'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium read-non" name="alias_nonnatural'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_nonnatural_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_nonnatural_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else{
					jQuery("#customnonnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_nonnatural'+""+i+""+'" class="inputField01 splitpropclass read-non" name="alias_nonnatural'+""+i+""+'"  type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_nonnatural_key'+""+i+""+'"  name="alias_nonnatural_key'+""+i+""+'"  type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}
			else if(id==3)
			{

				if(datatype==2)
				{

					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_multimedia'+""+i+""+'" class="input-medium read-mul" name="alias_multimedia'+""+i+""+'" type="Date" value=""/><input  id="alias_multimedia_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_multimedia_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_multimedia'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==3)
				{

					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><select  id="alias_multimedia'+""+i+""+'" class="input-medium disablemul" name="alias_multimedia'+""+i+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_multimedia_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_multimedia_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
					jQuery('#alias_multimedia'+""+i+""+'').val(selectedUnitValue);
				}
				else if(datatype==4)
				{

					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_multimedia'+""+i+""+'" type="number" pattern="[0-9]" class="input-medium read-mul" name="alias_multimedia'+""+i+""+'" value="'+""+selectedUnitValue+""+'"/><input  id="alias_multimedia_key'+""+i+""+'" class="inputField01 splitpropclass" name="alias_multimedia_key'+""+i+""+'" type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
				else{
					jQuery("#custommultimedia-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label></div><div class="floatColumn01"><input  id="alias_multimedia'+""+i+""+'" class="inputField01 splitpropclass read-mul" name="alias_multimedia'+""+i+""+'" type="text" value="'+""+selectedUnitValue+""+'"/><input  id="alias_multimedia_key'+""+i+""+'"  name="alias_multimedia_key'+""+i+""+'"  type="hidden" value="'+""+selectedUnitValues+""+'"/></div></div>');
				}
			}

		}	

	}

	else{

		$("#customtenure-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#customnatural-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#customnonnatural-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#custommultimedia-div").append("<strong style='color:#1366c5'>No Attributes</strong>");
		$("#customgeneral-div").append("<strong style='color:#1366c5'>No Attributes</strong>");

	}

}


function updatefinal(id,statusId)

{
	if(statusId==7)

	{
		jAlert('Status is already Final','Status');

	}
	else{
		jConfirm('Do you want to set selected Spatial Unit as <strong> Final </strong>?', 'Update Confirmation', function (response) {

			if (response) {
				jQuery.ajax({
					type:"GET",        
					url: "landrecords/finalstatus/"+id ,
					success: function (result) 
					{  

						if(result)
						{

							if(searchRecords!=null)
							{
								spatialSearch(records_from);

							}
							else
							{

								spatialRecords(records_from);
							}
						}


						else {
							jAlert('Request not completed');
						}			

					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {

						jAlert('Request not completed');
					}
				});
			}

		});

	}

}

function uploadDocuments(id)
{
	usinId = id;

	uploadDialog = $( "#upload-dialog-form" ).dialog({
		autoOpen: false,
		height: 350,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Upload": function() 
			{
				uploadWebDocument();

			},
			"Cancel": function() 
			{

				displayRefreshedLandRecords("landRecords");
				uploadDialog.dialog( "destroy" );
				uploadDialog.dialog( "close" );
				$('body').find("#upload-dialog-form").remove();
			}
		},
		close: function() {
			displayRefreshedLandRecords("landRecords");
			uploadDialog.dialog( "destroy" );
			uploadDialog.dialog( "close" );
			$('body').find("#upload-dialog-form").remove();
		}
	});

	uploadDialog.dialog( "open" );	

}


function uploadWebDocument()
{

	var formData = new FormData();
	var file = $( '#fileUploadWeb')[0].files[0];
	var name=$("#document_name").val();
	var comments=$("#document_comments").val();

	if(typeof(file)==="undefined")
	{

		jAlert('Please Select file to upload','Upload Web Document ');
	}
	else if(name==="")
	{
		jAlert('Please Enter Document Name','Upload Web Document');
	}
	else if((file.size)/1024>=5120)
	{
		jAlert('Please Enter file size below 5Mb','Upload Web Document');
	}



	else
	{
		formData.append("spatialdata",file); 
		formData.append("document_name",name);
		formData.append("document_comments",comments);
		formData.append("Usin_Upload",usinId);

		$.ajax({
			url: 'landrecords/uploadweb/',
			type: 'POST',
			data:  formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(result, textStatus, jqXHR)
			{	

				if(result=="Success")
				{
					jAlert('File uploaded','upload');
					if(searchRecords!=null)
					{
						spatialSearch(records_from);

					}
					else
					{

						spatialRecords(records_from);
					}

					//uploadDialog.dialog( "close" );
					uploadDialog.dialog( "destroy" );
					$('#uploaddocumentformID')[0].reset();
					//$('body').find("#upload-dialog-form").remove();

				}
				else if(result=="Error")
				{

					jAlert("Error in Uploading",'Upload');
					clearUploadDialog();

				}
				else{
					jAlert("Data cannot be uploaded","Upload");
					clearUploadDialog();
				}
			}

		});
	}

}
function clearUploadDialog()
{
	uploadDialog.dialog( "close" );
	uploadDialog.dialog( "destroy" );
	$('#uploaddocumentformID')[0].reset();


}

function adjudicateStatus(id,statusId)
{

	if(statusId==2)

	{
		jAlert('Status is already Adjudicated','Status');

	}
	else if(statusId==1 || statusId==4){
		jConfirm('Do you want <strong> Adjudicate </strong> selected Spatial Unit?', 'Update Confirmation', function (response) {

			if (response) {
				jQuery.ajax({
					type:"GET",        
					url: "landrecords/adjudicatestatus/"+id ,
					success: function (result) 
					{  

						if(result)
						{

							if(searchRecords!=null)
							{
								spatialSearch(records_from);

							}
							else
							{

								spatialRecords(records_from);
							}
						}


						else {
							jAlert('Request not completed');
						}			

					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {

						jAlert('Request not completed');
					}
				});
			}

		});


	}
	else{
		jAlert('Action is only applicable for New and Approved Status','Alert');
	}

}

function viewMultimedia(id)
{
	window.open("landrecords/download/"+id,'popUp','height=500,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no,menubar=no,status=no,replace=false');		

}


function showOnMap(usin,statusId)

{

	$.ajaxSetup ({cache: false});

	var relLayerName="spatial_unit";
	var fieldName="usin";
	var fieldVal=usin;

	zoomToLayerFeature(relLayerName,fieldName,fieldVal);

}

function zoomToLayerFeature(relLayerName,fieldName,fieldVal){// relLayerName=’spatial_unit’, fieldname=”usin”, fieldval= usin value

	if(map.getLayersByName("vector")[0]!=undefined)
		map.removeLayer(vectors);
	OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';
	vectors = new OpenLayers.Layer.Vector("vector", {isBaseLayer: true});
	map.addLayers([vectors]);
	var layer = map.getLayersByName(relLayerName)[0];

	if(layer==null)
		return;

	var url=map.getLayersByName(relLayerName)[0].url;
	var layerName='spatial_unit';
	var type;   
	var featureNS=getFeatureNS(layerName,url);
	var prefix; 
	var geomFieldName='the_geom';
	var featuresGeom=null;

	var pos = layerName.indexOf(":");
	prefix = layerName.substring(0, pos);
	type = layerName.substring(++pos);

	var filters=getFilter(fieldName,fieldVal);

	var filter_1_0 = new OpenLayers.Format.Filter({
		version : "1.1.0"
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
		url : url,
		dataType : "xml",
		contentType : "text/xml; subtype=gml/3.1.1; charset=utf-8",
		type : "POST",
		data : dataPost,
		success : function(data) {
			var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
				xy : reverseCoords,
				featureType : 'spatial_unit',
				gmlns : 'http://www.opengis.net/gml',
				featureNS : featureNS,
				geometryName : geomFieldName, 
				featurePrefix : 'spatial_unit',
				extractAttributes : true
			}).read(data);

			if(gmlFeatures.length>0){
				featuresGeom=gmlFeatures[0].geometry;
				zoomToAnyFeature(featuresGeom);
			}else{
				alert("Site not found on Map");
			}
		}
	});

}


function zoomSiteOnMap(usin){
	zoomToLayerFeature('World Heritage Sites','Polygon','wdpa_id',wdpaid);
}


function getFeatureNS(layerName,url){
	if(url==null)
		return url;
	var _wfsurl = url.replace(new RegExp( "wms", "i" ), "wfs");
	var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + layerName;        

	var featureNS='';
	$.ajax({
		url: _wfsSchema,
		dataType: "xml",
		async:false,
		success: function (data) {
			var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
			var responseText = featureTypesParser.read(data);         
			featureNS = responseText.targetNamespace;
		}
	});
	return featureNS;
}

function zoomToAnyFeature(geom) {

	var biggerArea=0.0;
	var biggerPart=0;
	if(geom.components!=undefined && geom.components!=null)
	{
		$('#tab').tabs("select","#map-tab");
		$('#sidebar').show();
		$('#collapse').show();

		for (intpartCnt = 0; intpartCnt<geom.components.length;intpartCnt++){
			if (biggerArea < geom.components[intpartCnt].getArea()){
				biggerArea = geom.components[intpartCnt].getArea();
				biggerPart = intpartCnt;
			}
		}

		var bounds = null;
		//bounds = geom.getBounds();
		bounds = geom.components[biggerPart].getBounds();

		var feature = new OpenLayers.Feature.Vector(
				OpenLayers.Geometry.fromWKT(
						geom.toString()
				));
		vectors.addFeatures([feature]);
		/*var newBounds = bounds.transform(new OpenLayers.Projection("EPSG:4326"),
		map.getProjectionObject());
		map.setCenter(new OpenLayers.LonLat(bounds.bottom,bounds.left),12);
		map.adjustZoom(6);*/
		map.zoomToExtent(bounds,true);


	}
	else{
		$('#tab').tabs("select","#landrecords-div");
		$('#sidebar').hide();
		$('#collapse').hide();
		jAlert('Site not found on Map','Alert');
	}
}

function getFilter(fieldName,fieldVal) {
	var filter;

	filter = new OpenLayers.Filter.Comparison({
		type: OpenLayers.Filter.Comparison.EQUAL_TO,
		matchCase: false,
		property: fieldName,
		value: fieldVal
	});

	return filter;
}
/*function createXML() {
	var datapost = '';
	var maxFeatureCount = 1;
	datapost = "<wfs:GetFeature service=\"WFS\" version=\"1.1.0\" maxFeatures =\"" + maxFeatureCount + "\" " +
	"xmlns:gml=\"http://www.opengis.net/gml\" " +
	"xmlns:wfs=\"http://www.opengis.net/wfs\" " +
	"xmlns:ogc=\"http://www.opengis.net/ogc\" " +
	"xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\" " +
	"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +

	"<wfs:Query typeName=##layer xmlns:feature=##featureNS> ##filter" +
	"</wfs:Query>"+
	"</wfs:GetFeature>";

	return datapost;

}*/
function adjudicationFormDialog(usinID,statusId)
{
	if(statusId==5 || statusId==3)
	{

		jAlert('Adjudication form can be generated for New Status','Alert');

	}
	else{
		adjudicationDialog = $( "#adjudication-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 232,
			resizable: false,
			modal: true,

			buttons: {
				"Ok": function() 
				{


					var lang = "";
					var selected = $("#radioLang input[type='radio']:checked");
					if (selected.length > 0) {
						lang = selected.val();
					}

					if(lang!='0')
					{

						get_Adjuticator_detail(usinID,lang);

						adjudicationDialog.dialog( "destroy" );
					}else
					{
						jAlert("Please select language","Alert");
					}


				},
				"Cancel": function() 
				{

					adjudicationDialog.dialog( "destroy" );

				}
			},
			close: function() {

				adjudicationDialog.dialog( "destroy" );

			}
		});

		$('input[type=radio][name=lang]').change(function() {
			if (this.value == 'Sw') {
				$('input:radio[name="lang"][value="Sw"]').prop('checked', true);
			}
			else if (this.value == 'En') {
				$('input:radio[name="lang"][value="En"]').prop('checked', true);
			}
		});


		$('input:radio[name="lang"][value="Sw"]').prop('checked', true);
		adjudicationDialog.dialog( "open" );	
	}


}

function viewProjectName(project){

	activeProject=project;

}

function defaultProject()

{
	document.location.href ="http://"+location.host+"/mast/viewer/";

}
/* To add a natural person if deleted from Edit Attribute Natural Person Tab */

function addNaturalPerson(empty)
{

	var idUsin=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/shownatural/"+idUsin,
		async:false,							
		success: function (shownaturaldata) {	



			DeletedNaturalList=shownaturaldata;

		}


	});
	jQuery("#deletedNaturalpersonRowData").empty();
	if(DeletedNaturalList.toString()==""){
		if(empty!="empty")
			jAlert("No Records Exists","Natural Person");
	}
	else{

		jQuery("#naturalpersonTemplate_add").tmpl(DeletedNaturalList).appendTo("#deletedNaturalpersonRowData");
		addDeletedNaturalDialog = $( "#deletednat-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 500,
			resizable: false,
			modal: true,
			buttons: {
				"Close": function() 
				{
					addDeletedNaturalDialog.dialog( "close" );
					addDeletedNaturalDialog.dialog( "destroy" );
				}
			},

		});
		addDeletedNaturalDialog.dialog( "open" );	

	}
}

function addDeletedNatural(personGid)
{
	var id=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/addnatural/"+personGid,
		async:false,							
		success: function (result) {	

			if(result)
			{
				jAlert('Data Successfully Added', 'Natural Person');
				if(DeletedNaturalList.length==1)
				{

					addDeletedNaturalDialog.dialog( "close" );
					addDeletedNaturalDialog.dialog( "destroy" );
				}
				addNaturalPerson("empty");
				editAttribute(id);
			}
			else{
				jAlert('Error in Adding Data', 'Natural Person');
			}

		}


	});
}


/* To add a non natural person if deleted from Edit Attribute Non Natural Person Tab */

function addNonNaturalPerson(empty)
{

	var idUsin_non=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/shownonnatural/"+idUsin_non,
		async:false,							
		success: function (data) {	



			DeletedNonNaturalList=data;

		}


	});
	jQuery("#deletedNonNaturalpersonRowData").empty();
	if(DeletedNonNaturalList.toString()==""){
		if(empty!="empty")
			jAlert("No Record Exists","Non Natural Person");

	}
	else{



		jQuery("#nonnaturalpersonTemplate_add").tmpl(DeletedNonNaturalList).appendTo("#deletedNonNaturalpersonRowData");

		addDeletedNonNaturalDialog = $( "#deletednon-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 500,
			resizable: false,
			modal: true,
			buttons: {
				"Close": function() 
				{
					addDeletedNonNaturalDialog.dialog( "close" );
					addDeletedNonNaturalDialog.dialog( "destroy" );
				}
			},

		});
		addDeletedNonNaturalDialog.dialog( "open" );	

	}
}

function addDeletedNonNatural(nonnatGid)
{
	var idNon=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/addnonnatural/"+nonnatGid,
		async:false,							
		success: function (result) {	

			if(result)
			{
				jAlert('Data Successfully Added', 'Non Natural Person');
				if(DeletedNonNaturalList.length==1)
				{

					addDeletedNonNaturalDialog.dialog( "close" );
					addDeletedNonNaturalDialog.dialog( "destroy" );
				}
				addNonNaturalPerson("empty");
				editAttribute(idNon);
			}
			else{
				jAlert('Error in Adding Data', 'Non Natural Person');
			}

		}


	});
}



function previousRecords()

{
	records_from= $('#records_from').val();
	records_from=parseInt(records_from);
	records_from=records_from-11;
	if(records_from>=0)
	{

		if(searchRecords!=null)
		{

			spatialSearch(records_from);
		}
		else{

			spatialRecords(records_from);	
		}

	}

	else{

		alert("Request Can Not Be Done");

	}

}

function nextRecords()

{
	records_from= $('#records_from').val();
	records_from=parseInt(records_from);
	records_from=records_from+9;

	if(records_from<=totalRecords-1)
	{

		if(searchRecords!=null)
		{
			if(records_from<=searchRecords-1)
				spatialSearch(records_from);
			else
				alert("Request Can Not Be Done");

		}
		else{

			spatialRecords(records_from);	
		}

	}

	else
	{
		alert("Request Can Not Be Done");
	}

}

function spatialRecords(records_from)
{
	jQuery.ajax({ 
		url: "landrecords/spatialunit/"+activeProject+"/"+records_from,
		async:false,							
		success: function (data) {	

			dataList=data;

			$('#landRecordsRowData').empty();
			if(dataList!="" && dataList!=null)
			{

				jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
				$("#landRecordsTable").trigger("update");
				$('#records_from').val(records_from+1);
				$('#records_to').val(totalRecords);
				if(records_from+10<=totalRecords)
					$('#records_to').val(records_from+10);
				$('#records_all').val(totalRecords);
			}
		}

	});


}

function spatialSearch(records_from)

{

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/search/"+activeProject+"/"+records_from ,
		data: jQuery("#landrecordsform").serialize(),
		success: function (result) 

		{
			dataList=null;
			dataList=result;
			$('#landRecordsRowData').empty();
			if(dataList!="" && dataList!=null)
			{

				jQuery("#landRecordsAttrTemplate").tmpl(dataList).appendTo("#landRecordsRowData");
				$("#landRecordsTable").trigger("update");
				$('#records_from').val(records_from+1);
				$('#records_to').val(searchRecords);
				if(records_from+10<=searchRecords)
					$('#records_to').val(records_from+10);
				$('#records_all').val(searchRecords);
			}

		}


	});

}

function addPerson()
{

	addPersonDialog = $( "#add_person-dialog-form" ).dialog({
		autoOpen: false,
		height: 138,
		width: 255,
		resizable: false,
		modal: true,
		buttons: {
			"Close": function() 
			{
				addPersonDialog.dialog( "close" );
				addPersonDialog.dialog( "destroy" );
			}
		},

	});
	addPersonDialog.dialog( "open" );	

}

function addNewNaturalPerson()
{

	naturalAdditonalAttributes();
	jQuery(".hidden_alias").show();
	/*	jQuery.ajax({ 
		url: "landrecords/educationlevel/",
		async:false,							
		success: function (data) {
			eduList=data;

		}

	});*/
	/*	jQuery("#edu").empty();
	jQuery("#edu").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery.each(eduList, function (i, eduobj) {
		jQuery("#edu").append(jQuery("<option></option>").attr("value",eduobj.levelId ).text(eduobj.educationLevel)); 

	});*/

	jQuery.ajax({ 
		url: "landrecords/gendertype/",
		async:false,							
		success: function (data) {	

			jQuery("#gender").empty();
			jQuery("#gender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(data, function (i, genderobj) {
				jQuery("#gender").append(jQuery("<option></option>").attr("value",genderobj.genderId ).text(genderobj.gender)); 

			});
		}

	});

	jQuery.ajax({ 
		url: "landrecords/maritalstatus/",
		async:false,							
		success: function (data) {	
			jQuery("#marital_status").empty();
			jQuery("#marital_status").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(data, function (i, maritalobj) {
				jQuery("#marital_status").append(jQuery("<option></option>").attr("value",maritalobj.maritalStatusId ).text(maritalobj.maritalStatus)); 

			});

		}

	});

	jQuery.ajax({ 
		url: "landrecords/citizenship/",
		async:false,							
		success: function (data) {	
			jQuery("#citizenship").empty();
			jQuery("#citizenship").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(data, function (i, citizenobj) {
				if(citizenobj.id!=0)
				jQuery("#citizenship").append(jQuery("<option></option>").attr("value",citizenobj.id ).text(citizenobj.citizenname)); 

			});

		}

	});


	jQuery.ajax({ 
		url: "landrecords/personsubtype/",
		async:false,							
		success: function (person_subtype) {	

			jQuery("#person_subType").empty();
			jQuery("#person_subType").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(person_subtype, function (i, personSubtypeobj) {
				if(personSubtypeobj.person_type_gid!=1 && personSubtypeobj.person_type_gid!=2)
					jQuery("#person_subType").append(jQuery("<option></option>").attr("value",personSubtypeobj.person_type_gid).text(personSubtypeobj.personType)); 

			});

		}


	});


	/*	jQuery("#owner_nat").empty();
	jQuery("#owner_nat").append(jQuery("<option></option>").attr("value", 1).text("Yes"));
	jQuery("#owner_nat").append(jQuery("<option></option>").attr("value", 0).text("No"));


	jQuery("#resident").empty();
	jQuery("#resident").append(jQuery("<option></option>").attr("value", 1).text("Yes"));
	jQuery("#resident").append(jQuery("<option></option>").attr("value", 0).text("No"));*/

	//jQuery("#editNaturalPersonformID")[0].reset();
	document.getElementById("editNaturalPersonformID").reset();
	naturalPersonDialog = $( "#naturalperson-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Save": function() 
			{

				updateAttributeNaturalPerson(true);
				//updateNewNaturalPerson();



			},
			"Cancel": function() 
			{
				naturalPersonDialog.dialog( "destroy" );
				naturalPersonDialog.dialog( "close" );
				$('#tab-natural').tabs("select","#tabs-1");

			}
		},
		close: function() {
			naturalPersonDialog.dialog( "destroy" );
			$('#tab-natural').tabs("select","#tabs-1"); 

		}
	});

	naturalPersonDialog.dialog( "open" );	
}

function updateNewNaturalPerson()
{
	
	jQuery("#natural_key").val(0);	
	var id=editList[0].usin;
	project=editList[0].project;
	var length_natural=attributeList.length;
	jQuery("#natural_usin").val(id);
	jQuery("#natual_length").val(0);
	if(length_natural!=0 && length_natural!=undefined)
		jQuery("#natual_length").val(length_natural);
	jQuery("#projectname_key").val(project);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatenatural" ,
		data: jQuery("#editNaturalPersonformID").serialize(),
		success: function (data) 
		{
			if(data.toString()!="")
			{
				naturalPerson_gid=data.person_gid;
				//updateNewTenure();

				editAttribute(id);
				//addPersonDialog.dialog("destroy");
				jAlert('Data Sucessfully Saved','Tenure Info');
			}
			else{

				jAlert("Request Not Completed","Natural Person");
			}

		}

	});	
	

}

/*function updateNewTenure()
{
	jQuery('.hide_duration').hide();	


	jQuery.ajax({ 
		url: "tenuretype/",
		async:false,							
		success: function (data) {	

			tenuretypeList=data;
		}

	});

	jQuery.ajax({ 
		url: "occupancytype/",
		async:false,							
		success: function (data) {	

			occtypeList=data;
		}

	});

	jQuery.ajax({ 
		url: "tenureclass/",
		async:false,							
		success: function (data) {	

			tenureclassList=data;
		}

	});

	jQuery("#tenureclass_id").empty();
	jQuery("#tenure_type").empty();
	jQuery("#occtype_id").empty();

	jQuery("#tenureclass_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery.each(tenureclassList, function (i, tenureclassobj) {
		jQuery("#tenureclass_id").append(jQuery("<option></option>").attr("value",tenureclassobj.tenureId ).text(tenureclassobj.tenureClass)); 

	});
	jQuery("#tenure_type").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery.each(tenuretypeList, function (i, tenureobj) {
		jQuery("#tenure_type").append(jQuery("<option></option>").attr("value",tenureobj.gid ).text(tenureobj.shareType)); 

	});

	jQuery("#occtype_id").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
	jQuery.each(occtypeList, function (i, occupancyobj) {
		jQuery("#occtype_id").append(jQuery("<option></option>").attr("value",occupancyobj.occId ).text(occupancyobj.description)); 

	});


	newtenureDialog = $( "#tenureinfo-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Update Tenure": function() 
			{

				updateTenureInfo();



			},
			"Cancel": function() 
			{
				newtenureDialog.dialog( "destroy" );
				newtenureDialog.dialog( "close" );
				//$('#tab-tenure').tabs("select","#tabs-5");
			}
		},
		close: function() {
			newtenureDialog.dialog( "destroy" );
			//$('#tab-tenure').tabs("select","#tabs-5");

		}
	});

	newtenureDialog.dialog( "open" );		

}

function updateTenureInfo()
{

	var id=editList[0].usin;

	var length=attributeList.length;
	jQuery("#tenure_length").val(0);
	if(length!=0 && length!=undefined)
		jQuery("#tenure_length").val(length);

	jQuery("#projectname_key3").val(activeProject);
	jQuery("#person_gid").val(naturalPerson_gid);
	jQuery("#usin").val(id);
	jQuery("#tenure_key").val(0);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatetenure" ,
		data: jQuery("#edittenureinfoformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				newtenureDialog.dialog("destroy");

				editAttribute(id);
				addPersonDialog.dialog("destroy");
				jAlert('Data Sucessfully Saved','Tenure Info');
			}

			else
			{

				jAlert('Request not completed');

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});

}
 */

function editAdminData(adminId)
{
	administratorID=adminId;

	jQuery.ajax({ 
		url: "landrecords/gendertype/",
		async:false,							
		success: function (data) {	
			genderList=null;
			genderList=data
		}
	});

	jQuery.ajax({ 
		url: "landrecords/maritalstatus/",
		async:false,							
		success: function (data) {	
			maritalList=null;
			maritalList=data;
		}
	});

	jQuery.ajax({ 
		url: "landrecords/administrator/"+administratorID,
		async:false,							
		success: function (data) {

			jQuery("#admin_gender").empty();
			jQuery("#admin_gender").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(genderList, function (i, genderobj) {
				jQuery("#admin_gender").append(jQuery("<option></option>").attr("value",genderobj.genderId ).text(genderobj.gender)); 

			});

			jQuery("#admin_marital_status").empty();
			jQuery("#admin_marital_status").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
			jQuery.each(maritalList, function (i, maritalobj) {
				jQuery("#admin_marital_status").append(jQuery("<option></option>").attr("value",maritalobj.maritalStatusId ).text(maritalobj.maritalStatus)); 

			});

			jQuery("#admin_resident").empty();
			jQuery("#admin_resident").append(jQuery("<option></option>").attr("value", 1).text("Yes"));
			jQuery("#admin_resident").append(jQuery("<option></option>").attr("value", 0).text("No"));


			if(data!=null && data!="" && data!=undefined )
			{
				if(data.maritalstatus!=null)
				{
					jQuery("#admin_marital_status").val(data.maritalstatus.maritalStatusId);
				}
				if(data.gender!=null){
					jQuery("#admin_gender").val(data.gender.genderId);
				}

				jQuery("#adminId").val(adminId);	

				jQuery("#admin_fname").val(data.firstname);
				jQuery("#admin_mname").val(data.middlename);
				jQuery("#admin_lname").val(data.lastname);
				jQuery("#admin_mobile").val(data.phonenumber);
				if(data.age!=0){
					jQuery("#admin_age").val(data.age);
				}
				else{
					jQuery("#admin_age").val( );
				}

				jQuery("#admin_resident").val(0);
				if(data.resident==true){
					jQuery("#admin_resident").val(1);
				}

				jQuery("#admin_citizenship").val(data.citizenship);
				jQuery("#admin_address").val(data.address);

			}
		}

	});

	if(adminId==0)
		document.getElementById("editadminformID").reset();

	adminDialog = $( "#admin-dialog-form" ).dialog({
		autoOpen: false,
		height: 450,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Update": function() 
			{
				validateAdminData();

			},
			"Cancel": function() 
			{
				adminDialog.dialog( "destroy" );
				adminDialog.dialog( "close" );

			}
		},
		close: function() {
			adminDialog.dialog( "destroy" );

		}
	});

	adminDialog.dialog( "open" );	
	jQuery('.readAdmin').attr('readonly', false);
	jQuery('.disableAdmin').attr('disabled', false);


	if(read)
	{
		//jQuery(".hideButton").hide();
		$('.ui-dialog-buttonpane button:contains("Update")').button().hide();
		jQuery('.readAdmin').attr('readonly', true);
		jQuery('.disableAdmin').attr('disabled', true);
	}

}

function validateAdminData()
{

	$("#editadminformID").validate({

		rules: {
			admin_fname: "required",
			admin_mname: "required",
			admin_lname: "required",
			admin_citizenship: "required",
			admin_address: "required",

		},
		messages: {
			admin_fname: "Enter First Name",
			admin_mname: "Enter  Middle Name",
			admin_lname: "Enter Last Type",
			admin_citizenship: "Enter Citizenship Details",
			admin_address: "Enter Address",


		},

	});

	if ($("#editadminformID").valid())
	{

		if($('#admin_gender').val()!=0){

			if($('#admin_marital_status').val()!=0)
			{
				updateAdminData(administratorID);
			}
			else{
				alert("Please select Marital Status");
			}
		}
		else{
			alert("Please select gender");
		}

	}

	else {

		jAlert("Please Fill Mandatory fields","Message");
	}

}

function updateAdminData(id)
{

	var usin=editList[0].usin;

	project=editList[0].project;
	jQuery("#projectname_key").val(project);
	jQuery("#admin_usin").val(usin);
	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updateadmin/"+id,
		async: false,
		data: jQuery("#editadminformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				adminDialog.dialog("destroy");
				editAttribute(usin);
				jAlert('Data Sucessfully Saved','Admin Info');
			}

			else
			{

				jAlert('Request not completed');

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});

}

function addAdmin()
{
	add_adminDialog = $( "#add_admin-dialog-form" ).dialog({
		autoOpen: false,
		height: 138,
		width: 255,
		resizable: false,
		modal: true,
		buttons: {
			"Close": function() 
			{
				add_adminDialog.dialog( "close" );
				add_adminDialog.dialog( "destroy" );
			}
		},

	});
	add_adminDialog.dialog( "open" );	

}

function deleteAdmin(adminId,name)
{
	var usinid= editList[0].usin;
	jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {

		if (response) {
			jQuery.ajax({          
				type: 'GET',
				url: "landrecords/deleteadmin/"+adminId,
				success: function (result) 
				{ 
					if(result){
						jAlert('Data Successfully Deleted', 'Admin');	                  
						editAttribute(usinid);
					}
					else{

						jAlert("Request not completed");
					}
				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Request not completed');
				}
			});
		}

	});

}

function addExistingAdmin(empty)
{

	var idUsin=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/existingadmin/"+idUsin,
		async:false,							
		success: function (data) {	

			deletedAdminList=data;
			jQuery("#deletedAdminTemplate").tmpl(deletedAdminList).appendTo("#deletedAdminRowData");
		}


	});
	jQuery("#deletedAdminRowData").empty();
	if(deletedAdminList.toString()==""){
		if(empty!="empty")
			jAlert("No Records Exists","Admin Info");
	}
	else{

		jQuery("#deletedAdminTemplate").tmpl(deletedAdminList).appendTo("#deletedAdminRowData");
		addExistingAdminDialog = $( "#deletedadmin-dialog-form" ).dialog({
			autoOpen: false,
			height: 200,
			width: 500,
			resizable: false,
			modal: true,
			buttons: {
				"Close": function() 
				{
					addExistingAdminDialog.dialog( "close" );
					addExistingAdminDialog.dialog( "destroy" );
				}
			},

		});
		addExistingAdminDialog.dialog( "open" );	

	}
}


function naturalPersonImage(person_gid,admin_id)
{

	jQuery.ajax({ 
		url: "landRecords/check/naturalimage/"+person_gid+"/"+admin_id,
		cache:false,
		success: function (result) {	
			jQuery("#naturalImage-div").empty();

			if(result!="")
			{	jQuery("#naturalImage-div").append('<div id= "image_upload"><p class="type"><strong>Person Image</strong></p><p class="type"><img id="current_img" /></p></div>');
			//To refresh the image
			jQuery('#document_natural').val(result[0]);

			jQuery('#current_img').attr("src","http://"+location.host+"/"+result[1]+"?"+Math.floor(Math.random()*1000));
			jQuery('#current_img').attr("height",126);
			jQuery('#current_img').attr("width",105);
			$('#check_img').text("Upate Existing Image");
			}
			else{

				jQuery("#naturalImage-div").append('<div id= "image_upload"><p class="type"><strong>No Image</strong></p><p class="type"><img id="current_img" /></p></div>');
				jQuery('#current_img').attr("src","resources/images/studio/noImage.png");
				$('#check_img').text("Add New Image");
			}
		}

	});

	uploadNaturalImage = $( "#upload-natural-Image" ).dialog({
		autoOpen: false,
		height: 350,
		width: 350,
		resizable: false,
		modal: true,

		buttons: {
			"Upload": function() 
			{
				uploadNaturalImg(person_gid,admin_id);



			},
			"Cancel": function() 
			{

				//displayRefreshedLandRecords("landRecords");
				uploadNaturalImage.dialog( "destroy" );
				uploadNaturalImage.dialog( "close" );
				$('#uploadNaturalImage')[0].reset();

				//$('body').find("#upload-dialog-form").remove();
			}
		},
		close: function() {
			//displayRefreshedLandRecords("landRecords");
			uploadNaturalImage.dialog( "destroy" );
			uploadNaturalImage.dialog( "close" );
			$('#uploadNaturalImage')[0].reset();
			//$('body').find("#upload-dialog-form").remove();
		}
	});

	uploadNaturalImage.dialog( "open" );	
}

function uploadNaturalImg(gid_Person,id_admin)

{
	var formData = new FormData();
	var file = $( '#natural_image')[0].files[0];
	var usinId= editList[0].usin;
	var name=$("#document_natural").val();
	//var comments=$("#document_comments").val();

	if(typeof(file)==="undefined")
	{

		jAlert('Please Select file to upload','Upload Web Document ');
	}

	else if((file.size)/1024>=5120)
	{
		jAlert('Please Enter file size below 5Mb','Upload Web Document');
	}

	else
	{
		formData.append("natural_image",file); 
		formData.append("proj_name",activeProject);
		formData.append("document_name",name);
		formData.append("Usin_Upload",usinId);
		formData.append("person_gid",gid_Person);
		formData.append("admin_id",id_admin);

		$.ajax({
			url: 'landrecords/upload/personimage/',
			type: 'POST',
			data:  formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(result, textStatus, jqXHR)
			{	

				if(result=="Success")
				{
					jAlert('File uploaded','upload');

					editAttribute(usinId);
					uploadNaturalImage.dialog( "destroy" );
					uploadNaturalImage.dialog( "close" );
					$('#uploadNaturalImage')[0].reset();
					$('#image_upload').remove();
				}
				else if(result=="Error")
				{

					jAlert("Error in Uploading",'Upload');
					clearUploadDialog();

				}
				else{
					jAlert("Data cannot be uploaded","Upload");
					clearUploadDialog();
				}
			}

		});
	}
}


function addExisitingAdmin(adminId)
{
	var id=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/addadmin/"+adminId,
		async:false,							
		success: function (result) {	
			if(result)
			{
				jAlert('Data Successfully Added', 'Admin Info');
				if(deletedAdminList.length==1)
				{

					addExistingAdminDialog.dialog( "close" );
					addExistingAdminDialog.dialog( "destroy" );
				}
				addExistingAdmin("empty");
				editAttribute(id);
			}
			else{
				jAlert('Error in Adding Data', 'Admin Info');
			}
		}

	});
}

function naturalAdditonalAttributes()
{
	jQuery.ajax({ 
		url: "landrecords/naturalcustom/"+activeProject,
		async:false,							
		success: function (data) {	

			customList=data;

			$(".datepicker").datepicker();
			$(".datepicker").live('click', function() {
				$(this).datepicker('destroy').datepicker({
					dateFormat : 'yy-mm-dd'
				}).focus();
			});
		}


	});
	var length_new=(customList.length)/3;

	var j=0;
	jQuery("#customnatural-div").empty();
	jQuery("#customnewnatural-div").empty();
	jQuery("#customnewnatural-div").append('<div><input type="hidden" name="newnatural_length" value='+length_new+'></div>');
	for (var i = 0; i < customList.length; i++) {
		j=j+1;
		var selectedcustomText=customList[i];
		var datatype=customList[i+2];
		var custom_uid=customList[i+1];

		if(datatype=='2')
		{

			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'" >'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><input  id="alias_nat_custom'+""+j+""+'" class="input-medium justread datepicker" readonly name="alias_nat_custom'+""+j+""+'" type="Date" value=""/><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+""+custom_uid+""+'"/></div></div>');
			//jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
		}
		else if(datatype=='3')
		{

			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'" >'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><select  id="alias_nat_custom'+""+j+""+'" class="input-medium justdisable" name="alias_nat_custom'+""+j+""+'" value=""><option value="Yes">Yes</option><option value="No">No</option></select><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+""+custom_uid+""+'"/></div></div>');
			//jQuery('#alias_natural'+""+i+""+'').val(selectedUnitValue);
		}
		else if(datatype=='4')
		{

			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'" >'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><input  id="alias_nat_custom'+""+j+""+'" type="number" pattern="[0-9]" class="input-medium justread" name="alias_nat_custom'+""+j+""+'" value=""/><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+""+custom_uid+""+'"/></div></div>');
		}
		else
		{
			jQuery("#customnewnatural-div").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+j+""+'">'+""+selectedcustomText+""+'</label></div><div class="floatColumn01"><input  id="alias_nat_custom'+""+j+""+'" class="inputField01 splitpropclass" name="alias_nat_custom'+""+j+""+'" type="text" value=""/><input  id="alias_uid'+""+j+""+'" class="inputField01 splitpropclass" name="alias_uid'+""+j+""+'" type="hidden" value="'+custom_uid+'"/></div></div>');
		}

		i=i+2;	
	}


}

function autogenerateUKA()
{

	jQuery.ajax({ 
		url: "landrecords/autogenerateuka/"+activeProject,
		async:false,							
		success: function (data) {
			if(data)
			{
				if(searchRecords!=null)
				{
					spatialSearch(records_from);

				}
				else
				{

					spatialRecords(records_from);
				}

			}
			else{
				jAlert("Error in UKA Generation","Alert");
			}
		}
	});
}


function editnxtTokin(id,person_name)

{

	if(id!=0)
	{

		var name= person_name.split(" ");
		if(name.length==1)
		{
			jQuery("#fname_kin").val(name[0]);
		}

		else if(name.length==2)
		{
			jQuery("#fname_kin").val(name[0]);
			jQuery("#lname_kin").val(name[1]);
		}
		else if(name.length==3)
		{
			jQuery("#fname_kin").val(name[0]);
			jQuery("#mname_kin").val(name[1]);
			jQuery("#lname_kin").val(name[2]);
		}

	}



	nxtTokinDialog = $( "#nxtTokin-dialog-form" ).dialog({
		autoOpen: false,
		height: 284,
		width: 243,
		resizable: false,
		modal: true,

		buttons: {
			"Update": function() 
			{
				validateUpdatePWI(id,person_name);



			},
			"Cancel": function() 
			{


				nxtTokinDialog.dialog( "destroy" );
				nxtTokinDialog.dialog( "close" );
				$('#editnxtTokinformID')[0].reset();


			}
		},
		close: function() {

			nxtTokinDialog.dialog( "destroy" );
			nxtTokinDialog.dialog( "close" );
			$('#editnxtTokinformID')[0].reset();

		}
	});

	nxtTokinDialog.dialog( "open" );
	jQuery('.justread').attr('readonly', false);


	if(read)
	{
		$('.ui-dialog-buttonpane button:contains("Update")').button().hide();
		jQuery('.justread').attr('readonly', true);

	}


}
function validateUpdatePWI(pwi_id)

{

	$("#editnxtTokinformID").validate({

		rules: {

			fname_kin:"required",
			lname_kin:"required"

		},
		messages: {

			fname_kin:"Please enter First name",
			lname_kin:"Please enter Last name"

		}

	});


	if ($("#editnxtTokinformID").valid())
	{
		updatePWI(pwi_id)
	}
}


function updatePWI(pwi_id)
{
	var usin=editList[0].usin;
	var fname=jQuery("#fname_kin").val();
	var mname=jQuery("#mname_kin").val();
	var lname=jQuery("#lname_kin").val();
	var name= fname+" "+lname;
	if(mname!='')
		name= fname+" "+mname+" "+lname;
	jQuery("#id_kin").val(pwi_id);
	jQuery("#usin_kin").val(usin);

	jQuery("#name_kin").val(name);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatepwi",
		async: false,
		data: jQuery("#editnxtTokinformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				nxtTokinDialog.dialog("destroy");
				nxtTokinDialog.dialog( "close" );
				$('#editnxtTokinformID')[0].reset();
				editAttribute(usin);
				jAlert('Data Sucessfully Saved','Person of Interest');
			}

			else
			{

				jAlert('Request not completed');

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});	

}

function deletenxtTokin(id,name)
{		
	var usin=editList[0].usin;
	jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {

		if (response) {
			jQuery.ajax({

				url: "landrecords/deletekin/"+id,
				async: false,
				success: function (result) 
				{
					if(result==true)
					{
						jAlert('Data Successfully Deleted', 'Deceased Person');	                  
						editAttribute(usin);
					}

				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Request not completed');
				}
			});
		}

	});


}


function editDeceasedPerson(id)

{
	if(id==0 && deceasedPersonList.length>=1)
	{
		jAlert("User can add only one deceased person","Deceased Person");
	}
	else{
		if(id!=0){
			jQuery("#d_firstname").val(deceasedPersonList[0].firstname);
			jQuery("#d_middlename").val(deceasedPersonList[0].middlename);
			jQuery("#d_lastname").val(deceasedPersonList[0].lastname);
		}
		deceasedPersonDialog = $( "#deceased-dialog-form" ).dialog({
			autoOpen: false,
			height: 275,
			width: 320,
			resizable: false,
			modal: true,

			buttons: {
				"Update": function() 
				{
					updateDeceasedPerson(id);

				},
				"Cancel": function() 
				{


					deceasedPersonDialog.dialog( "destroy" );
					deceasedPersonDialog.dialog( "close" );
					$('#editdeceasedformID')[0].reset();


				}
			},
			close: function() {

				deceasedPersonDialog.dialog( "destroy" );
				deceasedPersonDialog.dialog( "close" );
				$('#editdeceasedformID')[0].reset();

			}
		});

		deceasedPersonDialog.dialog( "open" );
		jQuery('.justread').attr('readonly', false);

		if(read)
		{
			$('.ui-dialog-buttonpane button:contains("Update")').button().hide();
			jQuery('.justread').attr('readonly', true);

		}
	}
}

function updateDeceasedPerson(id)
{
	var usin=editList[0].usin;

	jQuery("#deceased_key").val(id);
	jQuery("#usin_deceased").val(usin);

	jQuery.ajax({
		type:"POST",        
		url: "landrecords/updatedeceased",
		async: false,
		data: jQuery("#editdeceasedformID").serialize(),
		success: function (data) 
		{

			if(data)
			{
				deceasedPersonDialog.dialog("destroy");
				deceasedPersonDialog.dialog( "close" );
				$('#editdeceasedformID')[0].reset();
				editAttribute(usin);
				jAlert('Data Sucessfully Saved','Deceased Person');
			}

			else
			{

				jAlert('Request not completed');

			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			jAlert('Request not completed');
		}
	});			
}

function deleteDeceased(id,name)
{		
	var usin=editList[0].usin;
	jConfirm('Are You Sure You Want To Delete : <strong>' + name + '</strong>', 'Delete Confirmation', function (response) {

		if (response) {
			jQuery.ajax({

				url: "landrecords/deletedeceased/"+id,
				async: false,
				success: function (result) 
				{
					if(result==true)
					{
						jAlert('Data Successfully Deleted', '');	                  
						editAttribute(usin);
					}

				},
				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Request not completed');
				}
			});
		}

	});


}

var finalValidation= function(){

	$('#validationCheck').show();
	$(function() {
		$( "#dialog-confirm" ).dialog({
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"Validate and Update": function() {

					validationchecks();
					$( this ).dialog( "close" );
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			}
		});
	});
}

var validationchecks = function(){

	var id=editList[0].usin;
	jQuery.ajax({ 
		url: "landrecords/validator/"+id,
		async:false,							
		success: function (data) {	

			validator=data;
			if(validator=='Success')
				jAlert('Data successfully validated','Validation Info');
			else
				jAlert(validator,'Validation Error','Validation Error');

		}

	});

	if(validator=='Success')
	{

		editAttrDialog.dialog( "destroy" ); 
		$('#tabs').tabs("select","#tabs-1");
	}

}
function changeResident(val) {
	$('#tenureclass_id').val(val.value);
}

function naturalPersonDeleteImage(person_gid,person_name)
{
	var usin=editList[0].usin;
	jConfirm('Are You Sure You Want To Delete the Image of Person: <strong>' + person_name + '</strong>', 'Delete Confirmation', function (response) {

		if (response) {
			jQuery.ajax({          
				type: 'GET',
				async:false,
				url: "landrecords/deletenaturalphoto/"+person_gid,
				success: function (result) 
				{ 
					if(result=="true")
					{
						jAlert('Data successfully deleted','Natural person Image');	 
						editAttribute(usin);
						
						
					}

					if(result=="false")

					{
						jAlert('No image exists','Alert');	                  

					}
				},


				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Request not completed');
				}
			});
		}

	});

		}

