
var selectedItem=null;
var ProjectCategoryList=null;
var DataList=null;
var attributeCategory = null;
var proj_name = null;
var projname=null;
var Mobilelist=null;
var idorder=[];

function ProjectAttribute(_selectedItem)
{
	
	proj_name = null;
	selectedItem=_selectedItem;
	

	jQuery.ajax({
		url: "projecttype/",
		async:false,
		success: function (data) {
			DataList = data;    
		}
	});



	displayRefreshedProjectAttr();
	$('body').find("#attr-dialog-form").remove();
}

function displayRefreshedProjectAttr()
{
	
	jQuery("#projectattribute-div").empty();
	jQuery.get("resources/templates/mobile/" + selectedItem + ".html", function (template) 
			{

		jQuery("#projectattribute-div").append(template);

		jQuery('#projectAttrFormDiv').css("visibility", "visible");	


		jQuery("#projectAttr_project").empty();



		jQuery("#projectAttr_project").append(jQuery("<option></option>").attr("value", 0).text("Please Select"));
		jQuery.each(DataList, function (i, _project) {    	
			jQuery("#projectAttr_project").append(jQuery("<option></option>").attr("value", _project.name).text(_project.name));        
		});
		$('#projectAttr_project').val(proj_name);
		jQuery("#projectAttr-accordion").accordion({fillSpace: true});

		if(proj_name!=null)
			fillAccordians(proj_name);

			});	


}

function fillAccordians(_proj_name)
{
	proj_name = _proj_name;
	

	projname=$('#projectAttr_project').val();
	if(projname==0)
	{
		
		jQuery(".btnprojectattr").hide();
		jQuery("#generaltbl").empty();
		jQuery("#general_property_tbl").empty();
		jQuery("#natural_persontbl").empty();
		jQuery("#multimediatbl").empty();
		jQuery("#tenuretbl").empty();
		jQuery("#non_Natural_Persontbl").empty();
		jQuery("#customtbl").empty();
	}

	else{
		jQuery(".btnprojectattr").show();
		
		jQuery.ajax({          
			type: 'GET',
			url: "projectattrib/display/1/"+proj_name+"/",
			success: function (categorydata) 
			{ 	   			
				jQuery("#generaltbl").empty();				

				if(categorydata!= null && categorydata!="" && typeof categorydata != "undefined")
					jQuery("#categoryattrTemplate").tmpl(categorydata).appendTo("#generaltbl");			
			}        
		});
		jQuery.ajax({          
			type: 'GET',
			url: "projectattrib/display/2/"+proj_name+"/",
			success: function (categorydata) 
			{ 	 
				jQuery("#natural_persontbl").empty();
				if(categorydata!=null && categorydata!="" && typeof categorydata != "undefined")
					jQuery("#categoryattrTemplate").tmpl(categorydata).appendTo("#natural_persontbl");

				//

				jQuery("#upLayer").live('click', function () {
					jQuery('#addedLayerList option:selected').each(function () {
						var newPos = jQuery('#addedLayerList option').index(this) - 1;
						if (newPos > -1) {
							jQuery('#addedLayerList option').eq(newPos).before("<option value='" + jQuery(this).val() + "' selected='selected'>" + jQuery(this).text() + "</option>");
							jQuery(this).remove();
						}
					});
				});


				jQuery("#downLayer").live('click', function () {
					var countOptions = jQuery('#addedLayerList option').size();
					jQuery('#addedLayerList option:selected').each(function () {
						var newPos = jQuery('#addedLayerList option').index(this) + 1;
						if (newPos < countOptions) {
							jQuery('#addedLayerList option').eq(newPos).after("<option value='" + jQuery(this).val() + "' selected='selected'>" + jQuery(this).text() + "</option>");
							jQuery(this).remove();
						}
					});
				});

				//

			}         
		});
		jQuery.ajax({          
			type: 'GET',
			url: "projectattrib/display/3/"+proj_name+"/",
			success: function (categorydata) 
			{ 	   	
				jQuery("#multimediatbl").empty();
				if(categorydata!=null && categorydata!="" && typeof categorydata != "undefined")
					jQuery("#categoryattrTemplate").tmpl(categorydata).appendTo("#multimediatbl");
			}         
		});
		jQuery.ajax({          
			type: 'GET',
			url: "projectattrib/display/4/"+proj_name+"/",
			success: function (categorydata) 
			{ 		
				jQuery("#tenuretbl").empty();
				if(categorydata!=null && categorydata!="" && typeof categorydata != "undefined")
					jQuery("#categoryattrTemplate").tmpl(categorydata).appendTo("#tenuretbl");
			}         
		});
		jQuery.ajax({          
			type: 'GET',
			url: "projectattrib/display/5/"+proj_name+"/",
			success: function (categorydata) 
			{ 	  			
				jQuery("#non_Natural_Persontbl").empty();
				if(categorydata!=null && categorydata!="" && typeof categorydata != "undefined")
					jQuery("#categoryattrTemplate").tmpl(categorydata).appendTo("#non_Natural_Persontbl");
			}         
		});

		jQuery.ajax({          
			type: 'GET',
			url: "projectattrib/display/6/"+proj_name+"/",
			success: function (categorydata) 
			{ 	 			
				jQuery("#customtbl").empty();
				if(categorydata!=null && categorydata!="" && typeof categorydata != "undefined")
					jQuery("#categoryattrTemplate").tmpl(categorydata).appendTo("#customtbl");
			}         
		});

		

		jQuery.ajax({          
			type: 'GET',
			url: "projectattrib/display/7/"+proj_name+"/",
			success: function (categorydata) 
			{ 	
				console.log(categorydata);
				jQuery("#general_property_tbl").empty();
				if(categorydata!=null && categorydata!="" && typeof categorydata != "undefined")
					jQuery("#categoryattrTemplate").tmpl(categorydata).appendTo("#general_property_tbl");
			}         
		});

	}



}

function openAttrDialog(_category)
{
	var category = _category;
	switch (category) {
	case "general":	
		genAttrDialog = $( "#attr-dialog-form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 300,
			resizable: false,
			modal: true,

			buttons: {
				"Add": function() 
				{
					saveprojectAttr();
					//genAttrDialog.dialog( "close" );
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



		var project=$("#projectAttr_project").val();
		if(project==0){
			jQuery("#dialogBody").empty();
			jAlert("Please Select Project First","Alert");

		}
		else{
			genAttrDialog.dialog( "open" );
			jQuery.ajax({          
				type: 'GET',
				url: "projectattrib/displaypop/1/"+project+"/",
				success: function (popdata) 
				{
					jQuery("#dialogBody").empty();

					jQuery("#generalAttrTemplate").tmpl(popdata).appendTo("#dialogBody");
					attributeCategory = 1;
					$('.roleCheckbox').change(function() 
							{
						console.log(this);
						if(this.checked) {
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',true);
						}else{
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',false);
						}
							});						
				}         
			});
		}
		break;

	case "naturalperson":
		genAttrDialog = $( "#attr-dialog-form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 300,
			resizable: false,
			modal: true,

			buttons: {
				"Add": function() 
				{
					saveprojectAttr();
					//genAttrDialog.dialog( "close" );
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



		var project=$("#projectAttr_project").val();
		if(project==0){
			jQuery("#dialogBody").empty();
			jAlert("Please Select Project First","Alert");
		}
		else{
			genAttrDialog.dialog( "open" );	
			jQuery.ajax({          
				type: 'GET',
				url: "projectattrib/displaypop/2/"+project+"/",
				success: function (popdata) 
				{
					jQuery("#dialogBody").empty();
					jQuery("#naturalpersonAttrTemplate").tmpl(popdata).appendTo("#dialogBody");
					attributeCategory = 2;
					$('.roleCheckbox').change(function() 
							{
						console.log(this);
						if(this.checked) {
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',true);
						}else{
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',false);
						}
							});	
				}         
			});
		}
		break;

	case "multimedia":

		genAttrDialog = $( "#attr-dialog-form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 300,
			resizable: false,
			modal: true,

			buttons: {
				"Add": function() 
				{
					saveprojectAttr();
					//genAttrDialog.dialog( "close" );
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



		var project=$("#projectAttr_project").val();
		if(project==0){
			jQuery("#dialogBody").empty();
			jAlert("Please Select Project First","Alert");
		}
		else{
			genAttrDialog.dialog( "open" );		
			jQuery.ajax({          
				type: 'GET',
				url: "projectattrib/displaypop/3/"+project+"/",
				success: function (popdata) 
				{ 
					jQuery("#dialogBody").empty();
					jQuery("#propertyAttrTemplate").tmpl(popdata).appendTo("#dialogBody");
					attributeCategory = 3;
					$('.roleCheckbox').change(function() 
							{
						console.log(this);
						if(this.checked) {
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',true);
						}else{
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',false);
						}
							});
				}         
			});
		}
		break;

	case "tenure":

		genAttrDialog = $( "#attr-dialog-form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 300,
			resizable: false,
			modal: true,

			buttons: {
				"Add": function() 
				{
					saveprojectAttr();
					//genAttrDialog.dialog( "close" );
				},
				"Cancel": function() 
				{
					genAttrDialog.dialog( "close" );
				}
			},
			close: function() {
				genAttrDialog.dialog( "destroy" );
				genAttrDialog.dialog( "destroy" );

			}
		});


		var project=$("#projectAttr_project").val();
		if(project==0){
			jQuery("#dialogBody").empty();
			jAlert("Please Select Project First","Alert");
		}
		else{
			genAttrDialog.dialog( "open" );
			jQuery.ajax({          
				type: 'GET',
				url: "projectattrib/displaypop/4/"+project+"/",
				success: function (popdata) 
				{
					jQuery("#dialogBody").empty();
					jQuery("#tenureAttrTemplate").tmpl(popdata).appendTo("#dialogBody");
					attributeCategory = 4;
					$('.roleCheckbox').change(function() 
							{
						console.log(this);
						if(this.checked) {
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',true);
						}else{
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',false);
						}
							});
				}         
			});
		}
		break;	

	case "nonnatural_person":

		genAttrDialog = $( "#attr-dialog-form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 300,
			resizable: false,
			modal: true,

			buttons: {
				"Add": function() 
				{
					saveprojectAttr();
					//genAttrDialog.dialog( "close" );
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


		var project=$("#projectAttr_project").val();
		if(project==0){
			jQuery("#dialogBody").empty();
			jAlert("Please Select Project First","Alert");
		}
		else{
			genAttrDialog.dialog( "open" );		
			jQuery.ajax({          
				type: 'GET',
				url: "projectattrib/displaypop/5/"+project+"/",
				success: function (popdata) 
				{
					jQuery("#dialogBody").empty();
					jQuery("#multimediaAttrTemplate").tmpl(popdata).appendTo("#dialogBody");
					attributeCategory = 5;
					$('.roleCheckbox').change(function() 
							{
						console.log(this);
						if(this.checked) {
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',true);
						}else{
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',false);
						}
							});
				}         
			});
		}
		break;

	case "custom":

		genAttrDialog = $( "#attr-dialog-form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 300,
			resizable: false,
			modal: true,

			buttons: {
				"Add": function() 
				{
					saveprojectAttr();
					//genAttrDialog.dialog( "close" );
				},
				"Cancel": function() 
				{
					genAttrDialog.dialog( "close" );
				}
			},
			close: function() {
				genAttrDialog.dialog( "destroy" );
				genAttrDialog.dialog( "destroy" );

			}
		});


		var project=$("#projectAttr_project").val();
		if(project==0){
			jQuery("#dialogBody").empty();
			jAlert("Please Select Project First","Alert");
		}
		else{
			genAttrDialog.dialog( "open" );
			jQuery.ajax({          
				type: 'GET',
				url: "projectattrib/displaypop/6/"+project+"/",
				success: function (popdata) 
				{
					jQuery("#dialogBody").empty();
					jQuery("#customAttrTemplate").tmpl(popdata).appendTo("#dialogBody");
					attributeCategory = 6;
					$('.roleCheckbox').change(function() 
							{
						console.log(this);
						if(this.checked) {
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',true);
						}else{
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',false);
						}
							});
				}         
			});
		}
		break;
		
	case "general_property":

		genAttrDialog = $( "#attr-dialog-form" ).dialog({
			autoOpen: false,
			height: 400,
			width: 300,
			resizable: false,
			modal: true,

			buttons: {
				"Add": function() 
				{
					saveprojectAttr();
					//genAttrDialog.dialog( "close" );
				},
				"Cancel": function() 
				{
					genAttrDialog.dialog( "close" );
				}
			},
			close: function() {
				genAttrDialog.dialog( "destroy" );
				genAttrDialog.dialog( "destroy" );

			}
		});


		var project=$("#projectAttr_project").val();
		if(project==0){
			jQuery("#dialogBody").empty();
			jAlert("Please Select Project First","Alert");
		}
		else{
			genAttrDialog.dialog( "open" );
			jQuery.ajax({          
				type: 'GET',
				url: "projectattrib/displaypop/7/"+project+"/",
				success: function (popdata) 
				{
					jQuery("#dialogBody").empty();
					jQuery("#customAttrTemplate").tmpl(popdata).appendTo("#dialogBody");
					attributeCategory = 7;
					$('.roleCheckbox').change(function() 
							{
						console.log(this);
						if(this.checked) {
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',true);
						}else{
							var chkid = this.id;
							$('#uid'+chkid).prop('checked',false);
						}
							});
				}         
			});
		}
		break;
	default:
	}
}


var saveprojectAttrData= function () 
{
	$('.funcCheckbox').show();
	var project=$("#projectAttr_project").val();
	$("#project").val(project);
	$('#attributecategory').val(attributeCategory);


	jQuery.ajax({
		type: "POST",              
		url: "projectattrib/create/",
		data: jQuery("#addProjectAttributeformID").serialize(),
		success: function (result) {        

			if(result=='true'){
				genAttrDialog.dialog("destroy");
				$('body').find("#attr-dialog-form").remove();
				$("#attr-dialog-form").remove();
				proj_name = project;
				displayRefreshedProjectAttr();
				jAlert('Data Successfully Saved', 'MasterAttribute');
			}
			else if(result=='false'){
				jAlert('Data not saved','Error');
			}
			else if(result=='mapping')
			{
				jAlert('Existing mapped attributes cannot be unmapped from the project', 'Message');
			}
			else if(result=='null')
			{
				jAlert('Please Select atleast One Attribute', 'Message');
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert('err.Message');
		}
	});
	$('.funcCheckbox').hide();		
}




function saveprojectAttr()
{	
	/* $("#addProjectAttributeformID").validate({
	        rules: {
	        	alias: "required",
	            fieldName: "required",
	            type: "required",
	            size: "required",
	            category: "required",

	        },
	        messages: {
	        	alias: "Please enter Alias Name",
	        	fieldName: "Please enter  Field Name",
	        	type: "Please enter Attribute Type",
	        	size: "Please enter Attribute Size",
	        	category: "Please enter  Category",

	        }

	    });

	 if ($("#addProjectAttributeformID").valid())
		 {*/
	saveprojectAttrData();

	//}  
}


//added to up down for save  row by  RMSI NK  start

//case 1-6
function saveLayergroup(_option)
{

	var _attributecategory=0;

	idorder=[];
	$('#_optionOrder').val('');



	if (_option=='general') {


		$('#generaltbl tr').each(function() {
			var test=$(this).find("td:first").attr("id");
			idorder.push(test);

			$("#_optionOrder").val(idorder);


		});

		_attributecategory=1;

		$("#attributecategory").val(_attributecategory);




	}

	// case no 2 for natural personstart
	else if 



	(_option=='naturalperson') {

		$('#natural_persontbl tr').each(function() {
			var test=$(this).find("td:first").attr("id");
			idorder.push(test);

			$("#_optionOrder").val(idorder);

		});

		_attributecategory=2;

		$("#attributecategory").val(_attributecategory);

	}

	// case no 2 for natural person end	


	// case no 3 for natural multimedia
	else if 



	(_option=='multimedia') {

		$('#multimediatbl tr').each(function() {
			var test=$(this).find("td:first").attr("id");
			idorder.push(test);

			$("#_optionOrder").val(idorder);

		});

		_attributecategory=3;

		$("#attributecategory").val(_attributecategory);

	}

	// case no 3 for multimedian end	


	// case no 4 for tenure  start
	else if 



	(_option=='tenure') {

		$('#tenuretbl tr').each(function() {
			var test=$(this).find("td:first").attr("id");
			idorder.push(test);

			$("#_optionOrder").val(idorder);

		});

		_attributecategory=4;

		$("#attributecategory").val(_attributecategory);

	}

	// case no 4 for tenure  end

	// case no 5 for nonnatural_person start
	else if 



	(_option=='nonnatural_person') {

		$('#non_Natural_Persontbl tr').each(function() {
			var test=$(this).find("td:first").attr("id");
			idorder.push(test);

			$("#_optionOrder").val(idorder);

		});

		_attributecategory=5;

		$("#attributecategory").val(_attributecategory);

	}

	// case no 5 for nonnatural_person end

	// case no 6 for custom start
	else if 



	(_option=='custom') {

		$('#customtbl tr').each(function() {
			var test=$(this).find("td:first").attr("id");
			idorder.push(test);

			$("#_optionOrder").val(idorder);

		});

		_attributecategory=6;

		$("#attributecategory").val(_attributecategory);

	}

	// case no 6 for custom end
//case no 7 for general(property) start
	else if 



	(_option=='general_property') {

		$('#general_property_tbl tr').each(function() {
			var test=$(this).find("td:first").attr("id");
			idorder.push(test);

			$("#_optionOrder").val(idorder);

		});

		_attributecategory=7;

		$("#attributecategory").val(_attributecategory);

	}


	//case no 7 for general(property) end


	var project=$("#projectAttr_project").val();

	$("#project").val(project);

	jQuery.ajax({
		type: "POST",              
		url: "projectattrib/update/",
		data: jQuery("#addProjectAttributeformID").serialize(),
		success: function (result) {        

			if(result){
				jAlert('Data Successfully Saved', 'MasterAttribute');
				fillAccordians(project);

			}
			else{
				alert('Data not saved');
			}


			/*genAttrDialog.dialog("destroy");
			$('body').find("#attr-dialog-form").remove();
			$("#attr-dialog-form").remove();
			fillAccordians(project);*/

		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {

			alert('err.Message');
		}
	});


	// add code end
	//alert("successfully save");



}


//for save up and down start
jQuery(function () {

	$(".lgup,.lgdown").live('click',function(){
		var row = $(this).parents("tr:first");
		if ($(this).is(".lgup")) {
			row.insertBefore(row.prev());
		} else {
			row.insertAfter(row.next());
		}
	});




});
//for save up and down start