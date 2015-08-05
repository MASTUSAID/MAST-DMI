var projectname="";
var Id_Ccro=null;
var personlist=null;
var instituteNameList=null;
var naturalDownloadList=null;
var institutePersonList=null;
var personAdminList=null;

var documentByAdminList=null;

function get_Usin_detail(id,statusId)


{
	//Id_Ccro=id;
	if(statusId==1 || statusId==2 ||statusId==3 || statusId==5 )
	{

		jAlert('CCRO form can be genrated for Approved Status','Alert');

	}

	else{

		jQuery.ajax({ 
			url: "getpersontype/"+id,
			async:false,							
			success: function (person) {

				personlist=person;

			}
		});

		
		jQuery.ajax({ 
			url: "personadmin/"+id,
			async:false,							
			success: function (adminlist) {

				personAdminList=adminlist;

			}
		});


		jQuery.ajax({ 
			url: "getCCRO/"+id,
			async:false,							
			success: function (data) {

				projectname=data[0].project;



				if(personlist!=2 && personAdminList.toString()=="")
				{
					jQuery.ajax({ 
						url: "ccrodownload/"+id,
						async:false,							
						success: function (result) {	

							naturalDownloadList=result;
							personAdminList=null;

						}
					});
				}
				
				else if(personlist==2){
					
					jQuery.ajax({ 
						url: "ccroinstituteperson/"+id,
						async:false,							
						success: function (instituteperson) {	
							naturalDownloadList=null;
							institutePersonList=instituteperson;
							

						}
					});
					
					
				}




				jQuery.ajax(
						{
							type: 'GET',
							url: 'resources/templates/viewer/ccronew.html',
							dataType: 'html',
							success: function (data1) 
							{
								jQuery("#printDiv").empty();
								jQuery("#printDiv").append(data1);
								ccroMap(id);


								if(personlist==2){

									jQuery.ajax({ 
										url: "getinstitutename/"+id,
										async:false,							
										success: function (data) {

											instituteNameList=data;
										}
									});
									$('#_idinstitute').text(instituteNameList);
									$('#_idinstitute').css('font-weight', 'bold');
									$('#multipleperson').hide();
									$('#singleperson').hide();
									$('#institutepicture').show();
									$('#instituteperson').show();
									$('#single_owner').text("Mmiliki(Mkazi)");
									$('#institutenaturalPerson').text(institutePersonList);
									$('#institueName').text(instituteNameList);
									

								}
								else if(personlist==0){
									$('#multipleperson').hide();
									$('#singleperson').show();
									$('#institutepicture').hide();
									$('#instituteperson').hide();
									$('#single_owner').text("Mmiliki(Mkazi)");
									
									
								}
								else if(personlist==1){
									$('#multipleperson').show();
									$('#singleperson').hide();
									$('#institutepicture').hide();
									$('#instituteperson').hide();
									$('#single_owner').text("Wamiliki(Wakazi)");
									}
									
								if  (personAdminList!=null && personAdminList.toString()!="")
									
									{
									
									
									if(personAdminList.length==2){
										
										$('#multipleperson').hide();
										$('#singleperson').show();
										$('#institutepicture').hide();
										$('#instituteperson').hide();
										$('#single_owner').text("Msimamizi(Mkazi)");
										
											
									}
									
									else if(personAdminList.length>=4){
										
										$('#multipleperson').show();
										$('#singleperson').hide();
										$('#institutepicture').hide();
										$('#instituteperson').hide();
										$('#single_owner').text("Wasimamizi(Wakazi)");
										
									}
									
									}
									/* if(personAdminList.length==2 ){
										$('#single_owner').text("Mmiliki(Mkazi)");
									}
									else if(personAdminList.length>2){
									$('#single_owner').text("Wamiliki(Wakazi)");
									}
									else
										{
										$('#single_owner').text("Wamiliki(Wakazi)");
										} */
								

								if(personAdminList!=null && personAdminList.length!=0)
								{
								
								var adminName=[];
								for (var j = 0; j <personAdminList.length; j++) {
									var name=personAdminList[j];
									var result_element = personAdminList[j+1];
									adminName.push(name);
									j=j+1;
									//	$( "#singlepicture" ).insertAfter( ".owner" );
									jQuery("#documentId-div").append('<div class="row-fluid"><div class="span8"><p></p>'+
											'<p> <span class="left-50-alt">Jina </span> <span class="left-50">Saini/dole gumba </span></p>'+'<p><br><br><br></p>'+
											'<p><span id="occupier_name'+""+j+""+'" class="left-50-alt"> </span> <span	class="left-50">.................... </span></p></div>'+
											'<div class="span4"><p class="type">PICHA</p><p class="type"><img id="_occuiperImage'+""+j+""+'" /></p></div></div>');
									//jQuery("#documentId-div").append('<td><div class="fieldHolder"><div class="floatColumn02"><img id="_occuiperImage'+""+int+""+'" /></div></div></td>');

									jQuery('#_occuiperImage'+""+j+""+'').attr("src","http://"+location.host+"/"+result_element);
									jQuery('#occupier_name'+""+j+""+'').text(name);





									jQuery('#_occuiperImage'+""+j+""+'').attr("height",126);
									jQuery('#_occuiperImage'+""+j+""+'').attr("width",105);

								}
								}
								
								else if(naturalDownloadList!=null && naturalDownloadList.length!=0)
									{
									var array_owner=[];
								for (var int = 0; int <naturalDownloadList.length; int++) {
									var name=naturalDownloadList[int];
									var result_element = naturalDownloadList[int+1];
									array_owner.push(name);
									int=int+1;
									//	$( "#singlepicture" ).insertAfter( ".owner" );
									jQuery("#documentId-div").append('<div class="row-fluid"><div class="span8"><p></p>'+
											'<p> <span class="left-50-alt">Jina </span> <span class="left-50">Saini/dole gumba </span></p>'+'<p><br><br><br></p>'+
											'<p><span id="occupier_name'+""+int+""+'" class="left-50-alt"> </span> <span	class="left-50">.................... </span></p></div>'+
											'<div class="span4"><p class="type">PICHA</p><p class="type"><img id="_occuiperImage'+""+int+""+'" /></p></div></div>');
									//jQuery("#documentId-div").append('<td><div class="fieldHolder"><div class="floatColumn02"><img id="_occuiperImage'+""+int+""+'" /></div></div></td>');

									jQuery('#_occuiperImage'+""+int+""+'').attr("src","http://"+location.host+"/"+result_element);
									jQuery('#occupier_name'+""+int+""+'').text(name);





									jQuery('#_occuiperImage'+""+int+""+'').attr("height",126);
									jQuery('#_occuiperImage'+""+int+""+'').attr("width",105);


								}
									}
						
								if(personlist!=2){
									if(personAdminList!=null && personAdminList.length!=0) 
									{
										if(personAdminList.length==2)
										{
											
											$('._idowner').text(adminName.toString()+"(msimamizi)");	
										}
										else{
											$('._idowner').text(adminName.toString()+"(wasimamizi)");
											
										}
										
										
										$('._idowner').css('font-weight', 'bold');
									}	
									else{
									$('._idowner').text(array_owner.toString());
									$('._idowner').css('font-weight', 'bold');
									}
									}

								var Uka_no=data[0].propertyno;
								if(Uka_no!=null){
									var hamlet_array=Uka_no.split("/");
									var arr = {"SGL":"Songambele","IPG":"Ipangani","ILL":"Ilalasimba","KLG":"Kalangali","IGG":"Igungandembwe"};

									var hamlet_name=arr[hamlet_array[1]];
									$("#_idhamlet").text(hamlet_name);
								}
								//$("#_idccro").text(data[0].usin);
								$("#_idukapin").text(data[0].propertyno);

								$("#_idvillageadd1").text(data[0].address1);
								$("#_idvillageadd2").text(data[0].address2);
								$("#_idpincode").text(data[0].postal_code);
								$("#_idowner").text(data[0].landOwner);

								if(data[0].proposedUse!="" && data[0].proposedUse!=undefined && data[0].proposedUse!=null)
									$("._idproposeduse").text(data[0].proposedUse.landUseType_sw);


								$("#_idneighbor_north").text(data[0].neighbor_north);
								$("#_idneighbor_south").text(data[0].neighbor_south);
								$("#_idneighbor_east").text(data[0].neighbor_east);
								$("#_idneighbor_west").text(data[0].neighbor_west);


								var printWindow=window.open('','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
								printWindow.document.close();
								var html = $("#printDiv").html();
								printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/styleccro.css" type="text/css" />'+
										'<script src="../resources/scripts/cloudburst/viewer/CCRO.js"></script>'+
											'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
											'</head><body> '+html+'"<input type="hidden" id="usin_primerykey" value='+id+'>"</body></html>');

								printWindow.focus();


							}
						});
			}







		});


	}
}


function ccroMap(ID) {
	if(ID!="" && ID!=null){
		//$('#map-ccro').empty(); 
		var mapImg =new mapImage(ID);
	}
}
function print_tempCCRO() {
		var primeKey=$('#usin_primerykey').val();
		

		jQuery.ajax({ 
			url: "changeccrostatus/"+primeKey,
			async:false,							
			success: function (result) {
			if(result==true)
			alert("CCRO generated");
			}
		});
		
		
	document.getElementById("print-btn").style.visibility = "hidden";
	location.reload();
	window.print();

}