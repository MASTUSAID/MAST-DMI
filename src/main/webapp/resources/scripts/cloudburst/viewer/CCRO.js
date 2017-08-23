var projectname="";
var Id_Ccro=null;
var personlist=null;
var instituteNameList=null;
var naturalDownloadList=null;
var institutePersonList=null;
var personAdminList=null;
var dtoList=null;
var sharesave=true;

var documentByAdminList=null;

function get_Usin_detail(id,statusId)


{
	Id_Ccro=id;
	if(statusId==3 || statusId==5 )
	{

		jAlert('CCRO form cannot be genrated.','Alert');

	}

	else{


		jQuery.ajax({ 
			url: "landrecords/ccronew/"+id,
			async:false,							
			success: function (data) {

				dtoList=data;

			}
		});

		/*jQuery("#customShareDiv").empty();
		if(dtoList.ownership==1){
			sharePercentage();
		}
		else {*/
		ccroPrint('rest');

		/*}*/


	}
}


function ccroMap(ID) {
	if(ID!="" && ID!=null){
		var mapImg =new mapImage(ID);
	}
}
function print_tempCCRO() {
	var primeKey=$('#usin_primerykey').val();


	jQuery.ajax({ 
		url: "landrecords/changeccrostatus/"+primeKey,
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

//to make share pop-up with name and percentage field

function sharePercentage()
{
	for (var i = 0; i < dtoList.name.length; i++) {

		var personGid=dtoList.personwithGid[dtoList.name[i]];
		var selectedUnitText=dtoList.name[i];

		jQuery("#customShareDiv").append('<div class="fieldHolder"><div class="floatColumn02"> <label for="email" id="alias'+""+i+""+'" >'+""+selectedUnitText+""+'</label><div class="floatColumn01"><input  id="alias_person'+""+i+""+'" placeholder="Input share percentage" class="input-medium " name="alias_person'+""+i+""+'" type="text" value=""/><input type="hidden" name="length_person" id="length_person"/><input type="hidden" name="person_gid'+""+i+""+'" id="person_gid'+""+i+""+'"/></div></div></div>');
		jQuery("#length_person").val(dtoList.name.length);
		jQuery('#person_gid'+""+i+""+'').val(personGid);

	}

	shareDialog = $( "#share-dialog-form" ).dialog({
		autoOpen: false,
		height: 296,
		width: 296,
		resizable: true,
		modal: true,

		buttons: {
			"Update": function() 
			{
				onShareUpdate();

			},
			"Cancel": function() 
			{
				shareDialog.dialog( "destroy" );
				shareDialog.dialog( "close" );
			}
		},
		close: function() {
			shareDialog.dialog( "destroy" );

		}
	});
	shareDialog.dialog( "open" );	


}

function ccroPrint(check)
{



	if(check=='multiple'){

		jQuery.ajax({ 
			url: "landrecords/ccronew/"+Id_Ccro,
			async:false,							
			success: function (data) {

				dtoList=data;

			}
		});


	}

	/*	jQuery.ajax({ 
		url: "landrecords/getpersontype/"+id,
		async:false,							
		success: function (person) {

			personlist=person;

		}
	});*/


	/*		jQuery.ajax({ 
		url: "landrecords/personadmin/"+id,
		async:false,							
		success: function (adminlist) {

			personAdminList=adminlist;

		}
	});
	 */

	/*	jQuery.ajax({ 
		url: "landrecords/getCCRO/"+id,
		async:false,							
		success: function (data) {

			projectname=data[0].project;*/



	/*	if(personlist!=2 && personAdminList.toString()=="")
			{
				jQuery.ajax({ 
					url: "landrecords/ccrodownload/"+id,
					async:false,							
					success: function (result) {	

						naturalDownloadList=result;
						personAdminList=null;

					}
				});
			}*/

	/*	else if(personlist==2){

				jQuery.ajax({ 
					url: "landrecords/ccroinstituteperson/"+id,
					async:false,							
					success: function (instituteperson) {	
						naturalDownloadList=null;
						institutePersonList=instituteperson;


					}
				});


			}*/




	jQuery.ajax(
			{
				type: 'GET',
				url: 'resources/templates/viewer/ccronew.html',
				dataType: 'html',
				success: function (data1) 
				{
					jQuery("#printDiv").empty();
					jQuery("#printDiv").append(data1);
					ccroMap(Id_Ccro);


					if(dtoList.person_type==2){

						/*jQuery.ajax({ 
									url: "landrecords/getinstitutename/"+id,
									async:false,							
									success: function (data) {

										instituteNameList=data;
									}
								});*/

						$('#_idinstitute').text(dtoList.institute);
						$('#_idinstitute').css('font-weight', 'bold');
						$('#singleperson').hide();
						$('#multipleperson_joint').hide();
						$('#institutepicture').show();
						$('#instituteperson').show();
						$('#multipleperson_common').hide();
						$('#adminPerson').hide();

						$('#single_owner').text("Mmiliki (Mkazi) kwa :");
						$('#institutenaturalPerson').text(dtoList.institute);
						$('#institueName').text(dtoList.institute);
						$('#guardianPerson').hide();


					}
					// Ownership conditions

					//Multiple Occupancy(tenancy in common)
					else if(dtoList.ownership==1){
						$('#singleperson').hide();
						$('#multipleperson_joint').hide();
						$('#institutepicture').hide();
						$('#instituteperson').hide();
						$('#multipleperson_common').show();
						$('#adminPerson').hide();
						$('#single_owner').text("Wamiliki(Wakazi)");
						$('#guardianPerson').hide();
						/*if(dtoList.sharepercentage!=null)
							var share=arraytoname(dtoList.sharepercentage);
						$('#_idshare').text(share);*/
					}

					//Single ownership
					else if(dtoList.ownership==2){

						$('#singleperson').show();
						$('#multipleperson_joint').hide();
						$('#institutepicture').hide();
						$('#instituteperson').hide();
						$('#multipleperson_common').hide();
						$('#adminPerson').hide();
						$('#guardianPerson').hide();
						$('#single_owner').text("Mmiliki(Mkazi)");


					}
					//Multiple Ownership(Joint Occupancy)
					else if(dtoList.ownership==3){
						$('#singleperson').hide();
						$('#multipleperson_joint').show();
						$('#institutepicture').hide();
						$('#instituteperson').hide();
						$('#multipleperson_common').hide();
						$('#adminPerson').hide();
						$('#guardianPerson').hide();
						$('#single_owner').text("Wamiliki(Wakazi)");
					}


					//Tenancy in Probate(Admin)
					else if(dtoList.ownership==4){
						$('#singleperson').hide();
						$('#multipleperson_joint').hide();
						$('#institutepicture').hide();
						$('#instituteperson').hide();
						$('#multipleperson_common').hide();
						$('#guardianPerson').hide();
						$('#adminPerson').show();
						$('#single_owner').text("Mmiliki(Mkazi)");
					}


					//Tenancy in Minor(Guardian)
					else if(dtoList.ownership==5){
						// get guardian name from array
						var guardian=arraytoname(dtoList.guardian);

						$('#singleperson').hide();
						$('#multipleperson_joint').hide();
						$('#institutepicture').hide();
						$('#instituteperson').hide();
						$('#multipleperson_common').hide();
						$('#adminPerson').hide();
						$('#guardianPerson').show();
						$('#single_owner').text("Mmiliki(Mkazi)");
						$('#_idguardian').text(guardian);
					}


					//get Owner name from array
					var owner=arraytoname(dtoList.name);



					// setting for all common values

					$('._idowner').text(owner);
					$('._idowner').css('font-weight', 'bold');
					$("._idproposeduse").text(dtoList.proposeduse);
					$("#_idhamlet").text(dtoList.hamlet);
					$("#_villageChairman").text(dtoList.villagechairman);
					$("#_villageOfficer").text(dtoList.villageexecutive);
					$("#_villageDlo").text(dtoList.dlo);
					$("#_idneighbor_north").text(dtoList.neighbour_north);
					$("#_idneighbor_south").text(dtoList.neighbour_south);
					$("#_idneighbor_east").text(dtoList.neighbour_east);
					$("#_idneighbor_west").text(dtoList.neighbour_west);
					$("#_idccro").text(dtoList.usin);
					$("#_idukapin").text(dtoList.uka);
					$('._rights').text("33 miaka");
					if(dtoList.resident)
						$('._rights').text("kipindi kisicho na Kikomo");	

					if  (dtoList.adminName!=null && dtoList.adminName.toString()!="")

					{
						var admin =arraytoname(dtoList.adminName);
						if(dtoList.adminName.length==1){

							$('#single_owner').text("Msimamizi(Mkazi)");
							$('#_idadmin').text(admin);
						}

						else if(dtoList.adminName.length>1){

							$('#single_owner').text("Wasimamizi(Wakazi)");
							$('#_idadmin').text(admin);

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


					/*if(personAdminList!=null && personAdminList.length!=0)
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
							}*/

					/*		var count = Object.keys(dtoList.personName_url).length;
							console.log(count);
					 */
					if(dtoList.name!=null && dtoList.name.length!=0)
					{
						if(dtoList.person_type==2){
							jQuery("#documentId-div").empty();
						}
						else{
							//var array_owner=[];
							for (var int = 0; int <dtoList.name.length; int++) {
								var name=dtoList.name[int];
								var result_element = dtoList.personName_url[name];
								//array_owner.push(name);
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
						if(dtoList.guardian.length!=0){
							jQuery("#documentId-div").append('3.Msimamizi');
							for (var i = 0; i < dtoList.guardian.length; i++) {

								var guardianname=dtoList.guardian[i];
								var guardianurl = dtoList.guardianUrl[i+1];
								//array_owner.push(name);
								//	$( "#singlepicture" ).insertAfter( ".owner" );
								jQuery("#documentId-div").append('<div class="row-fluid"><div class="span8"><p></p>'+
										'<p> <span class="left-50-alt">Jina </span> <span class="left-50">Saini/dole gumba </span></p>'+'<p><br><br><br></p>'+
										'<p><span id="guardian_name'+""+i+""+'" class="left-50-alt"> </span> <span	class="left-50">.................... </span></p></div>'+
										'<div class="span4"><p class="type">PICHA</p><p class="type"><img id="_guardianImage'+""+i+""+'" /></p></div></div>');
								//jQuery("#documentId-div").append('<td><div class="fieldHolder"><div class="floatColumn02"><img id="_occuiperImage'+""+int+""+'" /></div></div></td>');

								jQuery('#_guardianImage'+""+i+""+'').attr("src","http://"+location.host+"/"+guardianurl);
								jQuery('#guardian_name'+""+i+""+'').text(guardianname);

								jQuery('#_guardianImage'+""+i+""+'').attr("height",126);
								jQuery('#_guardianImage'+""+i+""+'').attr("width",105);

							}
						}



					}
					//to be released soon
					/*		if(personlist!=2){
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
							}*/

					/*	var Uka_no=data[0].propertyno;
							if(Uka_no!=null){
								var hamlet_array=Uka_no.split("/");
								var arr = {"SGL":"Songambele","IPG":"Ipangani","ILL":"Ilalasimba","KLG":"Kalangali","IGG":"Igungandembwe"};

								var hamlet_name=arr[hamlet_array[1]];*/


					/*	
							$("#_idvillageadd1").text(data[0].address1);
							$("#_idvillageadd2").text(data[0].address2);
							$("#_idpincode").text(data[0].postal_code);
					$("#_idowner").text(data[0].landOwner); */


					var printWindow=window.open('','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
					printWindow.document.close();
					var html = $("#printDiv").html();
					printWindow.document.write ('<html><head><title>MAST</title>'+' <link rel="stylesheet" href="../resources/styles/viewer/styleccro.css" type="text/css" />'+
							'<script src="../resources/scripts/cloudburst/viewer/CCRO.js"></script>'+
							'<script src="../resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"></script>'+
							'</head><body> '+html+'<input type="hidden" id="usin_primerykey" value='+Id_Ccro+'></body></html>');

					printWindow.focus();


				}
			});
	/*		}







});*/



}

function arraytoname(arr)
{

	var ownersName1="";
	for(var i=0;i<arr.length;i++)
	{
		var ownersName=	arr[i];
		if(i==0)
			ownersName1=ownersName;
		else if(i==(arr.length-1))
			ownersName1=ownersName1+" na "+ownersName;
		else
			ownersName1=ownersName1+","+ownersName;
	}
	return ownersName1;

}


//update the share percentage in social_tenure table
function onShareUpdate() {

	var shartotal=0.000;
	for (var i = 0; i < dtoList.name.length; i++) {

		var sharetmp=jQuery('#alias_person'+""+i+""+'').val();
		shartotal=shartotal+parseFloat(sharetmp);
	}

	if(shartotal>=99.000 && shartotal<=100.000){
		jQuery.ajax({
			type:"POST",        
			url: "landrecords/updateshare/",
			data: jQuery("#shareformID").serialize(),
			success: function (result) 

			{
				if(result){
					shareDialog.dialog( "destroy" );
					shareDialog.dialog( "close" );
					ccroPrint('multiple');
				}
				else 
					jAlert("Request not completed","Error");

			}
		});

	}
	else{

		jAlert("Total share percentage is less than 100%","Share");
	}

}

//to check the inputed share is number
//will add in share dialog on share_value change
function checkasnumber(index)
{
	var sharetmp=jQuery('#alias_person'+""+i+""+'').val();
	var ck =/^\d*\.?\d*$/;
	if(ck.test(sharetmp))
		alert("Ok");
	else
		alert("Err");


}