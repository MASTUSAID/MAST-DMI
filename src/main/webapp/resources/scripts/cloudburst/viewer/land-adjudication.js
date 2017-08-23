
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
var validator="";
var vertextmp=[];
var deceasedList=null;



function get_Adjuticator_detail(id,lang)

{
	
	jQuery.ajax({ 
		url: "landrecords/validator/"+id,
		async:false,							
		success: function (data) {	

			validator=data;
			console.log(validator);
		}

	});
	
	if(validator!='Success')
		{
		jAlert(validator,'Validation Error');
		
		}
	
	else {
		
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
		url: "landrecords/personbyusin/"+id,
		async:false,							
		success: function (data) {	

			personList=data;

		}

	});
	

	
	jQuery.ajax({ 
		url: "landrecords/personofinterest/"+id,
		async:false,							
		success: function (data) {	
			
			personInterestList=data;

		}

	});
	
	jQuery.ajax({ 
		url: "landrecords/deceasedpersonbyid/"+id,
		async:false,							
		success: function (data) {	
			
			deceasedList=data;
			console.log(deceasedList);

		}

	});

	jQuery.ajax({ 
		url: "landrecords/Adjuticator/"+id,
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
							
							
							//document.getElementById("vertexList").style.pageBreakAfter = "always";
							
							var personarr=[];
							for (i=0;i<personList.length;i++)
							{
						
								if(personList[i].person_gid.person_type_gid.person_type_gid==1)
								{


									jQuery("#_non_natural").hide();
									jQuery("#_adjudicator").show();
									if(lang=='Sw'){
									var name=personList[i].person_gid.firstName+" "+personList[i].person_gid.lastName+"("+personList[i].person_gid.personSubType.personType_sw+")";
									if(personList[i].person_gid.middleName!=null && personList[i].person_gid.middleName!="")
									name=personList[i].person_gid.firstName+" "+personList[i].person_gid.middleName+" "+personList[i].person_gid.lastName+"("+personList[i].person_gid.personSubType.personType_sw+")";
									personarr.push(name);
									}
									else if(lang=="En"){
									var name=personList[i].person_gid.firstName+" "+personList[i].person_gid.lastName+"("+personList[i].person_gid.personSubType.personType+")";
									if(personList[i].person_gid.middleName!=null && personList[i].person_gid.middleName!="")
									name=personList[i].person_gid.firstName+" "+personList[i].person_gid.middleName+" "+personList[i].person_gid.lastName+"("+personList[i].person_gid.personSubType.personType+")";
									personarr.push(name);
									}
									

									if(lang=='En'){
									
										if(personList[i].person_gid.personSubType!=null){
											jQuery("#_naturalpersonTemplateEn").tmpl(personList[i].person_gid).appendTo("#_naturalperson");
											var cells = Array.prototype.slice.call(document.getElementById("_naturalperson").getElementsByTagName("td"));
											var j=i*13+11;
											if(personList[i].resident)
											cells[j].innerHTML="Yes";
											else
												cells[j].innerHTML="No";

											
										}
								
									}
									else if(lang=='Sw') {
										if(personList[i].person_gid.personSubType!=null){
											jQuery("#_naturalpersonTemplateSw").tmpl(personList[i].person_gid).appendTo("#_naturalperson");
											var cells = Array.prototype.slice.call(document.getElementById("_naturalperson").getElementsByTagName("td"));
											var j=i*13+11;
											if(personList[i].resident)
											cells[j].innerHTML="Ndiyo";
											else
											cells[j].innerHTML="Hapana";
											
										}
								
									}

									$("._age").text(year);

								}

								if(personList[i].person_gid.person_type_gid.person_type_gid==2)
								{
									
									jQuery("#_adjudicator").hide();
									jQuery("#_non_natural").show();

									jQuery("#adudicatorDetails_template").tmpl(personList[i]).appendTo("#_nonnaturalperson");
								}

							}
							
							
							// insert name in adjudication_personnameCustomDiv div
							jQuery("#adjudication_personnameCustomDiv").empty();
							
							for (var int = 0; int < personarr.length; int++) {
								if(lang=='En'){
								jQuery("#adjudication_personnameCustomDiv").append('<div class="row-fluid"><div class="span6">Msimamizi/Mmiliki wa Ardhi Jina :<strong>'+personarr[int]+'</strong> </div><div class="span3">Signature _ _ _ _ _ _ _ _ _ _ _ _ </div><div class="span2">Date_ _ _ _ _ _ _ </div></div>');
								}
								else if(lang=='Sw'){
									jQuery("#adjudication_personnameCustomDiv").append('<div class="row-fluid"><div class="span6">Msimamizi/Mmiliki wa Ardhi Jina :<strong>'+personarr[int]+'</strong> </div><div class="span3">Saini _ _ _ _ _ _ _ _ _ _ _ _ </div><div class="span2">Tarehe_ _ _ _ _ _ _ </div></div>');
									
								}
								
								
								}
							
							
							
							if(lang=='En'){
								
								if(data[0].hamlet_Id!="" && data[0].hamlet_Id!=null)
								$("#_hamletName").text(data[0].hamlet_Id.hamletName);
								
								if(data[0].landType!="" && data[0].landType!=null)
									$("#_landType").text(data[0].landType.landTypeValue);

								if(data[0].existingUse!="" && data[0].existingUse!=null)
									$("#_existingLanduse").text(data[0].existingUse.landUseType);

								if(data[0].proposedUse!="" && data[0].proposedUse!=null)
									$("#_proposedUse").text(data[0].proposedUse.landUseType);
							}

							else if(lang=='Sw')

							{
								if(data[0].hamlet_Id!="" && data[0].hamlet_Id!=null)
									$("#_hamletName").text(data[0].hamlet_Id.hamletNameSecondLanguage);

								if(data[0].landType!="" && data[0].landType!=null)
									$("#_landType").text(data[0].landType.landTypeValue_sw);

								if(data[0].existingUse!="" && data[0].existingUse!=null)
									$("#_existingLanduse").text(data[0].existingUse.landUseType_sw);

								if(data[0].proposedUse!="" && data[0].proposedUse!=null)
									$("#_proposedUse").text(data[0].proposedUse.landUseType_sw);

							}
							
							// for vertex table
							
							vertextmp=[];
							for (var int = 1; int <= vertexlist.length; int++) {
								var tmp=[];
								tmp["index"]=int;
								tmp["x"]=vertexlist[int-1].x;
								tmp["y"]=vertexlist[int-1].y;
								vertextmp.push(tmp);
							}
							
							jQuery("#vertexTemp").tmpl(vertextmp).appendTo("#vertexBody");
							
							$("#_plotID").text(data[0].propertyno);
							$("#_northName").text(data[0].neighbor_north);
							$("#_southName").text(data[0].neighbor_south);
							$("#_eastName").text(data[0].neighbor_east);
							$("#_westName").text(data[0].neighbor_west);

							$("#witness1_id").text(data[0].witness_1);
							$("#witness2_id").text(data[0].witness_2);
							$("#_ownership").text(data[0].landOwner);
							//$("#_applicationDate").text(data[0].surveyDate);
							$("#_applicationDate").text("_____________");
							//$("#area_id").text(data[0].area);
							if(lang=='Sw')
							$("#_ownershipType").text(personList[0].share_type.shareType_sw);
						if(lang=='En')
							$("#_ownershipType").text(personList[0].share_type.shareType);

							
							$("#trustedInter").text(data[0].user.name);
							if(data[0].propertyno!="" && data[0].propertyno!=null){
							var Uka_no=data[0].propertyno;
							var hamlet_array=Uka_no.split("/");
							var project = data[0].user.defaultproject;
							}
							jQuery.ajax({
								url: "landrecords/projectarea/",
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
							if(personInterestList.length!=0)
								{
								var pwitmp1=[];
								for (var int = 1; int <= personInterestList.length; int++) {
										if(lang=='Sw'){
									var pwitmp=[]

									pwitmp["number"]="Mtu mwenye Maslahi"+int;
									pwitmp["name"]=personInterestList[int-1];
									pwitmp1.push(pwitmp);
								}
								else if(lang=='En'){
									var pwitmp=[]

									pwitmp["number"]="Person of interest"+int;
									pwitmp["name"]=personInterestList[int-1];
									pwitmp1.push(pwitmp);

								}
								}
							//$("#personwithint").text(personInterestList);
								jQuery("#_personwithInterestTemp").tmpl(pwitmp1).appendTo("#_personwithInterset");
								}
							else{
								var pwitmp2={number:"&nbsp;",name:"&nbsp;"};
								jQuery("#_personwithInterestTemp").tmpl(pwitmp2).appendTo("#_personwithInterset");
								
							}
							
							$("#_deceasedPerson").empty();
							if(deceasedList.length!=0)
								{
								var deceasedtmp1=[];
								for (var int = 1; int <= deceasedList.length; int++) {
									if(lang=='Sw'){
									var deceasedtmp=[]

									deceasedtmp["number"]="Marehemu"+int;
									deceasedtmp["name"]=deceasedList[int-1];
									deceasedtmp1.push(deceasedtmp);
								}
								else if(lang=='En'){
									var deceasedtmp=[]

									deceasedtmp["number"]="Deceased Person"+int;
									deceasedtmp["name"]=deceasedList[int-1];
									deceasedtmp1.push(deceasedtmp);

								}
								}
							//$("#personwithint").text(personInterestList);
								jQuery("#_deceasedPersonTemp").tmpl(deceasedtmp1).appendTo("#_deceasedPerson");
								}
							else{
								var deceasedtmp2={number:"&nbsp;",name:"&nbsp;"};
								jQuery("#_deceasedPersonTemp").tmpl(deceasedtmp2).appendTo("#_deceasedPerson");
								
							}
							var popUpwindow=id;

							var printWindow=window.open('',popUpwindow,'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no,location=no');
							printWindow.document.close();
							var html = $("#printDiv").html();

							printWindow.document.write ('<html><head><title>MAST</title>'
									+' <link rel="stylesheet" href="../resources/styles/viewer/adjudication.css" type="text/css" />'
									+'<script src="../resources/scripts/cloudburst/viewer/land-adjudication.js"></script>'
									+'</head><body> '+html+' </body></html>');

							printWindow.focus();

						}
					});

		}


	});

}
}

function print_templ()

{
	location.reload();
	
	document.getElementById("print-btn").style.visibility = "hidden";
	
	window.print();
}


function getonmap(usin)

{
	var map = new mapImage(usin);



} 
