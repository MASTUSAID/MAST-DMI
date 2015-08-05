
var ProjectAreaList=null;
var personList=null;
var map;
var year=null;
var URL=null;
var ID=null;
var boundleft=null;
var boundsbottom=null;
var lyrs = "";
var url = "";
var layer = null;
var bbox = null;
var map_static;
var ownershipType="";
var personInterestList=null;


function get_Adjuticator_detail(id,lang)

{
	ID=id;

	if(lang=='En')
	{
		URL='resources/templates/viewer/land-adjudication.html';
	}
	else if(lang=='Sw')
	{
		URL='resources/templates/viewer/land-adjudication-sw.html';
	}

	jQuery.ajax({ 
		url: "personbyusin/"+id,
		async:false,							
		success: function (data) {	

			personList=data;

		}

	});
	jQuery.ajax({ 
		url: "personofinterest/"+id,
		async:false,							
		success: function (data) {	
			
			personInterestList=data.toString();
			//console.log(personList);

			

		}

	});

	jQuery.ajax({ 
		url: "Adjuticator/"+id,
		async:false,							
		success: function (data) {	

			jQuery.ajax(
					{
						type: 'GET',
						url: URL,
						dataType: 'html',
						success: function (data1) 
						{


							jQuery("#printDiv").empty();
							jQuery("#printDiv").append(data1);

							getonmap(ID);

							var admin_array=[];

							for (i=0;i<personList.length;i++)
							{
								//Added by Prashant
								var json_obj={};
								if(personList[i].person_gid.administator!=null && personList[i].person_gid.administator!='' && personList[i].person_gid.administator!='null')
								{
									json_obj['firstName'] = personList[i].person_gid.administator;
									admin_array.push(json_obj);
								}
								//$("#_ownershipDuration").text(personList[i].tenure_duration);
								if(personList[i].person_gid.person_type_gid.person_type_gid==1)
								{


									jQuery("#_non_natural").hide();
									jQuery("#_adjudicator").show();


									if(lang=='En'){
										ownershipType="Single";
										if(i>=1)
										{
											ownershipType="Multiple";

										}
										jQuery("#_naturalpersonTemplateEn").tmpl(personList[i].person_gid).appendTo("#_naturalperson");

									}
									else if(lang=='Sw') {
										ownershipType="Binafsi";
										if(i>=1)
										{
											ownershipType="Pamoja";

										}
										jQuery("#_naturalpersonTemplateSw").tmpl(personList[i].person_gid).appendTo("#_naturalperson");
									}

									$("._age").text(year);

								}

								if(personList[i].person_gid.person_type_gid.person_type_gid==2)
								{
									if(lang=='Sw')
										ownershipType="Taasisi";


									if(lang=='En')
										ownershipType="Institution";

									jQuery("#_adjudicator").hide();
									jQuery("#_non_natural").show();

									jQuery("#adudicatorDetails_template").tmpl(personList[i]).appendTo("#_nonnaturalperson");
								}

							}

							//Added by Prashant
							if(admin_array.toString()!="" && admin_array.length>0 )
								jQuery("#_naturalpersonTemplateSw_admin").tmpl(admin_array).appendTo("#_naturalperson");

							//Commented by Prashant
							/*if(admin_array.toString()!="" && admin_array.length>0 ){
							for (var int = 0; int <admin_array.length; int++) {
							if(lang=="En"){
							$('#admin_details').append('<td><div class="rTableRow"><div class="rTableCell"><span id="admin_natrual'+""+int+""+'"></span></div><div class="rTableCell-1"></div><div class="rTableCell-2"></div><div class="rTableCell-3"></div><div class="rTableCell-4"></div><div class="rTableCell-5"></div><div class="rTableCell-6"></div><div class="rTableCell-7"></div><div class="rTableCell-8"></div><div class="rTableCell-9"></div></div></td>');
							jQuery('#admin_natrual'+""+int+""+'').text(admin_array[int]);
							}
							else if(lang=="Sw"){
							if(personList[i].person_gid.mobile=="null")
							{
							$('#admin_details').append('<td><div class="rTableRow"><div class="rTableCell_sw"><span id="admin_natrual'+""+int+""+'"></span></div><div class="rTableCell-1_sw"></div><div class="rTableCell-2_sw"></div><div class="rTableCell-3_sw"></div><div class="rTableCell-4_sw"></div><div class="rTableCell-5_sw"></div><div class="rTableCell-6_sw"></div><div class="rTableCell-7_sw"></div><div class="rTableCell-8_sw"></div><div class="rTableCell-9_sw"></div></div></td>');
							jQuery('#admin_natrual'+""+int+""+'').text(admin_array[int]);
							}
							else{
							$('#admin_details').append('<td><div class="rTableRow"><div class="rTableCell_sw"><span id="admin_natrual'+""+int+""+'"></span></div><div class="rTableCell-1_sw"></div><div class="rTableCell-2_sw"></div><div class="rTableCell-3_sw"></div><div class="rTableCell-4_sw"></div><div class="rTableCell-5_sw"></div><div class="rTableCell-6_sw"></div><div class="rTableCell-7_sw"></div><div class="rTableCell-8_sw"></div><div class="rTableCell-9_sw"></div></div></td>');
							jQuery('#admin_natrual'+""+int+""+'').text(admin_array[int]);

							}

							}

							}
							}*/

							if(lang=='En'){

								if(data[0].landType!="" && data[0].landType!=null)
									$("#_landType").text(data[0].landType.landTypeValue);

								if(data[0].existingUse!="" && data[0].existingUse!=null)
									$("#_existingLanduse").text(data[0].existingUse.landUseType);

								if(data[0].proposedUse!="" && data[0].proposedUse!=null)
									$("#_proposedUse").text(data[0].proposedUse.landUseType);
							}

							else if(lang=='Sw')

							{


								if(data[0].landType!="" && data[0].landType!=null)
									$("#_landType").text(data[0].landType.landTypeValue_sw);

								if(data[0].existingUse!="" && data[0].existingUse!=null)
									$("#_existingLanduse").text(data[0].existingUse.landUseType_sw);

								if(data[0].proposedUse!="" && data[0].proposedUse!=null)
									$("#_proposedUse").text(data[0].proposedUse.landUseType_sw);

							}

							$("#_plotID").text(data[0].propertyno);
							$("#_northName").text(data[0].neighbor_north);
							$("#_southName").text(data[0].neighbor_south);
							$("#_eastName").text(data[0].neighbor_east);
							$("#_westName").text(data[0].neighbor_west);

							$("#witness1_id").text(data[0].witness_1);
							$("#witness2_id").text(data[0].witness_2);
							$("#_ownership").text(data[0].landOwner);
							$("#_applicationDate").text(data[0].surveyDate);
							//$("#area_id").text(data[0].area);
							$("#_ownershipType").text(ownershipType);
							
							$("#trustedInter").text(data[0].user.name);
							
							/*year = (new Date(data[0].surveyDate)).getFullYear();*/
							if(data[0].propertyno!="" && data[0].propertyno!=null){
							var Uka_no=data[0].propertyno;
							var hamlet_array=Uka_no.split("/");
							var arr = {"SGL":"Songambele","IPG":"Ipangani","ILL":"Ilalasimba","KLG":"Kalangali","IGG":"Igungandembwe"};

							var hamlet_name=arr[hamlet_array[1]];

							var project = data[0].user.defaultproject;

							$("#_hamletName").text(hamlet_name);
							}
							jQuery.ajax({
								url: "projectarea/",
								async:false,
								success: function (result) {
									ProjectAreaList = result;    

								}
							});

							$("#_villageName").text(ProjectAreaList[0].village);
							$("#chairperson").text(ProjectAreaList[0].villageChairman);
							$("#secretary").text(ProjectAreaList[0].approvingExecutive);
							$(".vill_address").text(ProjectAreaList[0].address);
							$("#personwithint").empty();
							if(personInterestList!="")
								{
							$("#personwithint").text(personInterestList);
								}
							var popUpwindow=id;

							var printWindow=window.open('',popUpwindow,'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no,location=no');
							printWindow.document.close();
							var html = $("#printDiv").html();

							printWindow.document.write ('<html><head><title>MAST</title>'
									+' <link rel="stylesheet" href="../resources/styles/viewer/adjudication.css" type="text/css" />'

									/*+'<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>'*/
									/*+'<script src="../resources/scripts/openlayers/OpenLayers.js"></script>'*/
									/*+'<script src="../resources/scripts/cloudburst/viewer/mapLoader.js"></script>'*/
									+'<script src="../resources/scripts/cloudburst/viewer/land-adjudication.js"></script>'
									+'</head><body> '+html+' </body></html>');

							printWindow.focus();
							//printWindow.location.reload();

						}
					});

		}


	});


}

function print_templ()

{



	/*var divToPrint = document.getElementById('adjudicationdiv');
	document.getElementById("print-btn").style.visibility = "hidden";
	var popupWin = window.open('', '_blank', 'width=600,height=600');
	//  popupWin.document.open();
	popupWin.document.write('<html><head><title>MAST</title>'	
			+' <link rel="stylesheet" href="../resources/styles/viewer/CCRO.css" type="text/css" />'
			+' <link rel="stylesheet" href="../resources/styles/viewer/style-new.css" type="text/css" />'

			+'</head><body> '+divToPrint.innerHTML+' </body></html>');

	popupWin.document.close(); 
	location.reload(true);
	popupWin.print();
	 */
	document.getElementById("print-btn").style.visibility = "hidden";
	location.reload();
	window.print();
}


function getonmap(usin)

{
	var map = new mapImage(usin);
//	map.printDrawing(ID);


} 
