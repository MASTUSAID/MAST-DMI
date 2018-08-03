var selectedItem_R=null;
var dataList=null;
var landId = null;
var totalRecords_Res = null;
var records_from_Res = 0;
var LandId =0;
var Firstname = "";
var Middlename = "";
var Lastname= "";
var Mobile = "";
var Dob = "";
var Maritalstatus =""; 
var GroupId="";
var genderid="";
var  Citizenship="";
var Ethnicity="";
var Resident_of_Community="";
var Address="";
var dob = null;
var Institution_Name = "";
var Registration_No = "";
var Registration_Date = "";
var registartion_date_date = null;
var No_Of_members = "";
var Other_details = ""; 
var Agency = ""; 
var land_handled = ""; 
var Community = ""; 
var Authority = ""; 
var collective_members_no = ""; 
var projectId = 0;
var Custom1 = "";
var Custom2 = "";

var Custom3 = "";

var Custom4 = "";

var Custom5 = "";
var Custom6 = "";
var Custom7 = "";
var Custom8 = "";
var Custom9 = "";

var Community_Area = "";

var poiFirstName = "";
var poiMiddleName = "";
var poiLastName ="";
var poiDob= "";
var poiRelation= "";
var poiGender="";
var isAddPerson = 0;
var geomName= "";





function resource(_selectedItem) {
    selectedItem_R = _selectedItem;
    
	jQuery.ajax({
        url: "resource/getAllresouceCount/" + activeProject,
        async: false,
        success: function (data) {
            totalRecords_Res=data;
        }
		
    });
	
	displayRefreshedResourceRecords();
	
	
}

function displayRefreshedResourceRecords() {

	records_from_Res=0;
	
       jQuery.ajax({
        url: "resource/getAllresouce/" + activeProject + "/" + 0,
        async: false,
        success: function (data) {
            dataList = data;
            if(null==dataList && dataList==""){
           
            }else{
            	projectId =  dataList[0].projectId;
            }
			

        jQuery("#landresource-div").empty();
        jQuery.get("resources/templates/viewer/" + selectedItem_R + ".html", function (template) {
			
            jQuery("#landresource-div").append(template);
            jQuery('#resourceRecordsFormdiv').css("visibility", "visible");
              jQuery("#registryRecordsTable").show();
			 jQuery("#resourceRecordsRowData1").empty();

			 if (dataList.length != 0 && dataList.length != undefined) {
                 jQuery("#resourceRecordsAttrTemplate1").tmpl(dataList).appendTo("#resourceRecordsRowData1");
             	   $('#records_from_Res').val(records_from_Res + 1);
                $('#records_to_Res').val(totalRecords_Res);
                if (records_from_Res + 10 <= totalRecords_Res)
                    $('#records_to_Res').val(records_from_Res + 10);
                     $('#records_all_Res').val(totalRecords_Res);
            }else{
				 $('#records_from_Res').val(0);
                $('#records_to_Res').val(0);
				 $('#records_all_Res').val(0);
			}
           
        });
		
		
		}
		
		});
    

}

function previousRecords_Res(){
	
	records_from_Res = $('#records_from_Res').val();
    records_from_Res = parseInt(records_from_Res);
    records_from_Res = records_from_Res - 11;
    if (records_from_Res >= 0) {
        RegistrationRecords_Res(records_from_Res);
    } else {
        alert("No Records Found");
    }
	
}
	
function nextRecords_Res(){
	
	records_from_Res = $('#records_from_Res').val();
    records_from_Res = parseInt(records_from_Res);
    records_from_Res = records_from_Res + 9;

    if (records_from_Res <= totalRecords_Res - 1) {
           RegistrationRecords_Res(records_from_Res);
		
    } else {
        alert("No Records Found");
    }
	
}


function RegistrationRecords_Res(records_from_Res){
	
		jQuery.ajax({
        url: "resource/getAllresouce/" + activeProject + "/" + records_from_Res,
        async: false,
        success: function (data) {
        	dataList=data;
             jQuery("#resourceRecordsRowData1").empty();

			 if (data.length != 0 && data.length != undefined) {
                 jQuery("#resourceRecordsAttrTemplate1").tmpl(data).appendTo("#resourceRecordsRowData1");
             	   $('#records_from_Res').val(records_from_Res + 1);
                $('#records_to_Res').val(totalRecords_Res);
                if (records_from_Res + 10 <= totalRecords_Res)
                    $('#records_to_Res').val(records_from_Res + 10);
                     $('#records_all_Res').val(totalRecords_Res);
            }else{
				 $('#records_from_Res').val(0);
                $('#records_to_Res').val(0);
				 $('#records_all_Res').val(0);
			}
        }
		
    });
	
}

function ActionfillResource(landid,geomType){
	
		var appid='#'+landid+"_resource";
		$(""+appid+"").empty();
		$(".containerDiv").empty();
		
	    var html="";
    	html+="<li> <a title='Edit Attribute' id='' name=''  href='#' onclick='viewAttribute("+landid+")'>Edit Attribute</a></li>";
		html+="<li> <a title='Edit Spatial' id='' name=''  href='#' onclick=viewOnMap("+landid+",'"+geomType+"')>Edit Spatial</a></li>";
//		html+="<li> <a title='Edit Spatial' id='' name=''  href='#' onclick=getFarmReport("+landid+","+projectId+",'"+geomType+"')>Farm Report</a></li>";
		
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
function viewOnMap(usin,geom) {
	var fieldName;
	var layerAlias;
	var _featureTypes= [];
	$(".signin_menu").hide();
 
	var relLayerName ;
    $.ajaxSetup({
        cache: false
    });
	if(geom=="Polygon"){
		 relLayerName = "Mast:la_spatialunit_resource_land";
		 fieldName = "landid";
		 _featureTypes.push("la_spatialunit_resource_land");
		 layerAlias="Resource";
	 }else if(geom=="Line"){
		  relLayerName = "Mast:la_spatialunit_resource_line";
          fieldName = "landid";
		  _featureTypes.push("la_spatialunit_resource_line");
		  layerAlias="Line";
	 }else if(geom=="Point"){
		 relLayerName = "Mast:la_spatialunit_resource_point";
          fieldName = "landid";
		  _featureTypes.push("la_spatialunit_resource_point");
		  layerAlias="point";
	 }
    var fieldVal = usin;
    
    var layer = getLayerByAliesName(layerAlias);
    layer.getSource().clear();
    
    
    zoomToLayerFeatureResource(relLayerName, fieldName, fieldVal,_featureTypes,layerAlias);
}
function zoomToLayerFeatureResource(relLayerName, fieldName, fieldVal,_featureTypes,layerAlias) {
var _featureNS;
//Get the Layer object
        var layerName = layerAlias;
		objLayer=getLayerByAliesName(layerName);
	
    	 var _wfsurl=objLayer.values_.url;
        var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + objLayer.values_.name +"&maxFeatures=1&outputFormat=application/json";;

        //Get Geometry column name, featureTypes, targetNamespace for the selected layer object //
        $.ajax({
            url: PROXY_PATH + _wfsSchema,
            async: false,
            success: function (data) {
				 featureNS_=data.targetNamespace;
                 featureType_=data.featureTypes[0].typeName;
	        }
        });

		
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
					zoomToResource(features);
				  });
	  				  
				
}
function zoomToResource(geom) {
	 if (geom != undefined &&  geom!= null) {
	  
	   closeDialog('editingdiv')
	   map.getLayers().forEach(function (layer) {
			if (layer.get('aname') != undefined & layer.get('aname') === 'featureaoilayer') {
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
		 name:"featureaoilayer",
        source: new ol.source.Vector(),
        style: highlightStyle,
      });
		featureOverlay.set('aname', "featureaoilayer");	
		featureOverlay.getSource().addFeature(geom[0]);
		map.addLayer(featureOverlay)
		//map.getView().fit(featureOverlay.getSource().getExtent(), map.getSize());
		//map.getView().setZoom(9);
			
		var projection = new ol.proj.Projection({
          code: 'EPSG:4326',
          units: 'degrees',
          axisOrientation: 'neu',
          global: true
      })
	  
		var ext=featureOverlay.getSource().getExtent();
		var center=ol.extent.getCenter(ext);
		map.setView( new ol.View({
			projection: projection,//or any projection you are using
			center: [center[0] , center[1]],//zoom to the center of your feature
			zoom: 14 //here you define the levelof zoom
		}));
		
		
		 $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#map-tab"]').parent().index());
        $('#sidebar').show();
        $('#collapse').show();
		
		
		
	 }else{
		 
		 $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#landresource-div"]').parent().index());
        $('#sidebar').hide();
        $('#collapse').hide();
        jAlert('Site Not Found on Map', 'Alert'); 
		 
	 }
}
function viewAttribute(usin)
{
	
	 isAddPerson = 0;
	$(".signin_menu").hide();
	landId = usin;
	
	
	$.ajax({
		url :"resource/landdata/" + landId,
		 async: false,
		success : function(data1) {
			
//			$("#reg_date").val(formatDate_R(data1[0]));
			$("#reg_date").val(data1[0][0]);
			$("#size_id").val(data1[0][1]);
			data=data1;
		console.log(data);
		}
	});
	
	
	for(var i=0; i < dataList.length; i++){
		if(dataList[i].landid == usin){
			$("#landid").val("");
			$("#subClassification_id").val("");
        	$("#Classification_id").val("");
        	$("#tenure_type_res").val("");
        	
			$("#Classification_id").val(dataList[i].classificationName);
			$("#subClassification_id").val(dataList[i].subclassificationName);
			$("#tenure_type_res").val(dataList[i].categoryName);
			$("#landid").val(dataList[i].landid);
			geomName = dataList[i].geometryName;

			
		}
	}
	

	
	FillResourcePersonDataNew();
	FillResourceCustoAttributes();
	loadResourcePersonsOfEditingForEditing();

	
	attributeHistoryDialog = $( "#editattribute-res-dialog-form" ).dialog({
		autoOpen: false,
		height: 600,
		width: 1161,
		left: 95,
		resizable: true,
		modal: true,
		close: function () {
			 $("input,select,textarea").removeClass('addBg');
			attributeHistoryDialog.dialog("destroy");
        },

		buttons: [{
			text : "Save",
			"id" : "comment_Save",
			
			click : function()
			{	
				
					saveInstitute();
					
			}

		},
		{
			text : "Save",
			"id" : "Custom_Save",
			
			click : function()
			{	
				
				saveCustomAttributes();
					
			}

		},
		{
			text: "Cancel",
			"id": "comment_cancel",
				click: function () {
				 setInterval(function(){ 

				 }, 4000);
				jQuery('#attributeHistoryTableBody').empty();
				 jQuery("#attributeTable").hide();
			     attributeHistoryDialog.dialog( "close" );

			}
			
		}],
		Cancel: function() {
				
				 jQuery('#attributeHistoryTableBody').empty();
				 jQuery("#attributeTable").hide();
				 $("input,select,textarea").removeClass('addBg');
			attributeHistoryDialog.dialog( "close" );


		}
	});
	$("#comment_cancel").html('<span class="ui-button-text">cancel</span>');
	attributeHistoryDialog.dialog( "open" );
	$("#comment_Save").prop("disabled",false).hide();
	$("#Custom_Save").prop("disabled",false).hide();
	$("#tabs").tabs({ active: 0 });
}




function FillResourcePersonDataNew()
{
	var name_Columns = [];
	var myColumns = [];	
	var myColumns1 = [];
	var data = [];
	myColumns1[0] = {type: "control", deleteButton: false};
	myColumns1[1] = {name:"landid", title:"LandId", align:"left", type:"number",  width: 120,editing: false,visible: false, validate: {validator: "required", message: "Enter LandId"} };

	$.ajax({
		url :"resource/allAttribtesDatatype/" + landId+ "/" + projectId,
		 async: false,
		success : function(data1) {
			data=data1;
		console.log(data);
			if(data == ""){
				$("#ResourcepersonsEditingGrid0").hide();
			}
			else{
				$("#ResourcepersonsEditingGrid0").show();
			}
			var ctr = 2;
			
			var groupfinalid = 0;
			var lastsel;
			for ( var i = 0; i < data.length; ++i) {
					name_Columns[ctr] = data[i][0];
					if(data[i][3] =="Community Area"){
						name_Columns[ctr] = data[i][3];
					}
					var id = data[i][1];
					var groupid =data[i][2];
					if(groupfinalid == 0){
					groupfinalid = groupid;
					}
					else{
						
					}
					if(groupfinalid != groupid){
						
					}
					if(groupfinalid == groupid){
					var val = new Object();
					val.name = name_Columns[ctr];
					
					
					val.index = ctr;
					val.width = 120;
					val.editable = true;
					val.type= "text";
					if(id==1021 || id==1068 || id==1129 || id==1120){
						val.type= "date";
					}
					else if(id==1165 || id==1164 || id==1163 || id==1162 || id==1161 || id==1160|| id==1159 || id==1158|| id==1156){
						val.visible = false;
					}
					else if(id==1033){
						val.type= "date";
					}
					else if(id==1107){
						$.ajax({
							url :"resource/alloptions/" + id,
							 async: false,
							success : function(data1) {
						
						val.items=data1;
							}
						});
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];
					}
//					else if(id==1017 || id==1035 || id==1063 || id==1079 || id==1088 || id==1097 || id==1108){
//						val.index = 2;
//					}
//					else if(id==1018 || id==1036 || id==1065 || id==1080 || id==1089 || id==1109 || id==1098){
//						val.index = 3;
//					}
//					else if(id==1019 || id==1037 || id==1066 || id==1081 || id==1090 || id==1099 || id==1110){
//						val.index = 4;
//					}
					else if(id==1022 || id==1064 || id==1116){
						$.ajax({
							url :"resource/alloptions/" + id,
							 async: false,
							success : function(data1) {
						
						val.items=data1;
							}
						});
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];

					}
					else if(id==1024 || id==1070 || id==1122){
						$.ajax({
							url :"resource/alloptions/" + id,
							 async: false,
							success : function(data1) {
						
						val.items=data1;
							}
						});
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];
							
					}
					else if(id==1025 || id==1071 || id==1123){
						$.ajax({
							url :"resource/alloptions/" + id,
							 async: false,
							success : function(data1) {
						
						val.items=data1;
							}
						});
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.visible= false
						val.name = name_Columns[ctr];

					}
					else if(id==1020 || id==1067 || id==1119){
						$.ajax({
							url :"resource/alloptions/" + id,
							 async: false,
							success : function(data1) {
						
						val.items=data1;
							}
						});
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];

					}
					
					else if(id==1023 || id==1069 || id==1121){
						$.ajax({
							url :"resource/alloptions/" + id,
							 async: false,
							success : function(data1) {
						
						val.items=data1;
							}
						});
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];

					}
					
					
					myColumns1[ctr] = val;
					ctr++;
					

			}
			myColumns1[ctr]= {name: "groupId", title: "GroupId", type: "number", width: 120, visible: false};
			}
		}
	});
		
//
//		
//			
////			var rowHeight = 200;

	
	
	// Init editing grid
    $("#ResourcepersonsEditingGrid0").jsGrid({
        width: "100%",
        height: "200px",
        inserting: false,
        editing: true,
        sorting: false,
        filtering: false,
        paging: true,
        autoload: false,
        controller: personsEditingControllerForResourcePerson,
        pageSize: 50,
        pageButtonCount: 20,
        fields: myColumns1
//        	[
//            {type: "control", deleteButton: false},
//			{name:"landid", title:"LandId", align:"left", type:"number",  width: 120,editing: false, validate: {validator: "required", message: "Enter LandId"} },
//            {name: "firstname", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
//            {name: "middlename", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
//            {name: "lastname", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
//			{name: "mobNo", title: "ContactNo", align: "left", type: "text", width: 80,editing: true, filtering: false},
//			{name:"groupId", title:"GroupId", align:"left", type:"number",  width: 120,editing: false, hidden:true,  validate: {validator: "required", message: "Enter GroupId"} },
//
//			//            {name:"age", title:"Age", align:"left", type:"number",  width: 120, validate: {validator: "required", message: "Enter Age"} },
////		    {name: "maritalstatus", title: "Marital Status", type: "text", width: 80,editing: true, filtering: false},
////			{name: "citizenship", title: "Citizenship", type: "text", width: 80,editing: true, filtering: false},
////			{name: "ethnicity", title: "Ethnicity", type: "text", width: 80,editing: true, filtering: false},
////		    {name: "resident", title: "Resident", type: "text", width: 80,editing: true, filtering: false},
////            {name: "address", title: "Address", type: "text", width: 120, validate: {validator: "required", message: "Enter Address"}},
////            {name: "mobileno", title: "Mobile Number", type: "text", width: 120},
////			{name: "groupId", title: "GroupId", type: "number", width: 120},
//           
//
//        ]
    });

    $("#ResourcepersonsEditingGrid0 .jsgrid-table th:first-child :button").click();
   
    $("#ResourcepersonsEditingGrid0").jsGrid("loadData");
    
    
}




var personsEditingControllerForResourcePerson = {
	    loadData: function (filter) {
	        return $.ajax({
	            type: "GET",
	            url: "resource/allAttribue/" + landId+ "/" + projectId,
	            data: filter,
				async: false,
	            success: function(data)
	            {
	            }
	        }).then(function(result){
	        	var persondataArray=[];
	        	 $("#Institution_id").val("");
	        	 $("#members").val("");
//	        	 $("#reg_date").val("");
	        	 $("#reg_no").val("");
	        	 $("#members_tr").hide();
	        	 $("#institute").hide();
//	        	 $("#reg_date_td").hide();
	        	 $("#regNo").hide();
//	        	 $("#tenure_occupancy").hide();
	        	 
	        	 
	        	
	        	 
	        	
//	        	var Firstname = "";
//	        	var Middlename = "";
//	        	var Lastname= "";
//	        	var Mobile = "";
//	        	var Dob = "";
//	        	var Maritalstatus =""; 
//	        	var GroupId="";
//	        	var genderid="";
//	        	var  Citizenship="";
//	        	var Ethnicity="";
//	        	var Resident_of_Community="";
//	        	var Address="";
	        	
				
				$.each( result, function(index,value){
					 
					 for(var i in value){
					 if(value[i].attributevalueid == 1017 ||
					    value[i].attributevalueid == 1035 ||
						value[i].attributevalueid == 1063 ||
						value[i].attributevalueid == 1079 ||
						value[i].attributevalueid == 1088 ||
						value[i].attributevalueid == 1097 ||
						value[i].attributevalueid == 1108 ||
						value[i].attributevalueid == 1115){
						 LandId = value[0].landid;
						 GroupId = value[0].groupid;
						 Firstname = value[i].attributevalue;
						 $("#landId").val(LandId);
					 }
					 else if(value[i].attributevalueid == 1018 ||
							    value[i].attributevalueid == 1036 ||
								value[i].attributevalueid == 1065 ||
								value[i].attributevalueid == 1080 ||
								value[i].attributevalueid == 1089 ||
								value[i].attributevalueid == 1109 ||
								value[i].attributevalueid == 1098 ||
								value[i].attributevalueid == 1117){
						 Middlename = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1019 ||
							    value[i].attributevalueid == 1037 ||
								value[i].attributevalueid == 1066 ||
								value[i].attributevalueid == 1081 ||
								value[i].attributevalueid == 1090 ||
								value[i].attributevalueid == 1099 ||
								value[i].attributevalueid == 1110 ||
								value[i].attributevalueid == 1118){
						 Lastname = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1042 ||
							    value[i].attributevalueid == 1030 ||
								value[i].attributevalueid == 1073 ||
								value[i].attributevalueid == 1086 ||
								value[i].attributevalueid == 1095 ||
								value[i].attributevalueid == 1105 ||
								value[i].attributevalueid == 1125){
						 Mobile = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1021 ||
							    value[i].attributevalueid == 1068 ||
								value[i].attributevalueid == 1129 ||
								value[i].attributevalueid == 1120){
						 Dob = value[i].attributevalue;
						
					    	
					    	{
					    		if(Dob!=null && Dob!='')
					    		{
					    			  var date = new Date(Dob);
					    			
					    			
					    			
//					    			dob = date.getFullYear()+ '-' + date.getMonth() + '-' + date.getDate();
					    		}
					    	}
					 }
					 else if(value[i].attributevalueid == 1022 ||
							    value[i].attributevalueid == 1064 ||
								value[i].attributevalueid == 1116){
						 Maritalstatus = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1020 ||
							    value[i].attributevalueid == 1067 ||
								value[i].attributevalueid == 1119){
						 genderid = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1023 ||
							    value[i].attributevalueid == 1069 ||
								value[i].attributevalueid == 1121){
						 Citizenship = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1024 ||
							    value[i].attributevalueid == 1070 ||
								value[i].attributevalueid == 1122){
						 Ethnicity = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1025){
						 Resident_of_Community = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1071 ||
							   value[i].attributevalueid == 1123){
						 Resident_of_Community = value[i].attributevalue;
					 }
					
					
					 else if(value[i].attributevalueid == 1031||
							    value[i].attributevalueid == 1077){
						 $("#Institution_id").val(value[i].attributevalue);
						 Institution_Name = value[i].attributevalue;
			        	 $("#institute").show();
//			        	 $("#tenure_occupancy").show();
			        	 $("#Institution_id_id").val(value[i].attributevalueid);
			        	
			        	 
					 }
					 else if(value[i].attributevalueid == 1032){
						 $("#reg_no").val(value[i].attributevalue);
						 Registration_No = value[i].attributevalue;
						 $("#regNo").show();
//						 $("#tenure_occupancy").show();
					 }
					 else if(value[i].attributevalueid == 1033){
						 
			        	
						 Registration_Date = value[i].attributevalue;
						
					    	
					    	{
					    		if(Registration_Date!=null && Registration_Date!='')
					    		{
					    			  var registartion_date_date = new Date(Registration_Date);
					    			
//					    			  $("#reg_date").val(registartion_date_date);
//					    			  $("#reg_date_td").show();
//					    				 $("#tenure_occupancy").show();
					    			
//					    			dob = date.getFullYear()+ '-' + date.getMonth() + '-' + date.getDate();
					    		}
					    	}
					 
						
					 }
					 else if(value[i].attributevalueid == 1034 ||
							    value[i].attributevalueid == 1078){
						 $("#members").val(value[i].attributevalue);
						 No_Of_members = value[i].attributevalue;
						 $("#members_tr").show();
//						 $("#tenure_occupancy").show();
						 $("#members_id").val(value[i].attributevalueid);
					 }
					 else if(value[i].attributevalueid == 1053 ||
							    value[i].attributevalueid == 1055){
						 Other_details = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1106){
						 Agency = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1060){
						 land_handled = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1096){
						 Community = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1107){
						 Authority = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1087){
						 collective_members_no = value[i].attributevalue;
					 }
					 else if(value[i].attributevalueid == 1112){
						 Community_Area = value[i].attributevalue;
					 }
					 else if(value[i].fieldAliasName != "Community Area"){
						 if(value[i].fieldname == "Address" || value[i].fieldname == "Address/Street"){
							 Address = value[i].attributevalue;
						 }
					}
					 }
					 var persondata ={
			        			"landid"   :LandId,
				    			"firstname":  Firstname,
				    			"middlename":  Middlename,
				    			"lastname":  Lastname,
				    			"Mobile No":Mobile,
				    			"groupId":GroupId,
				    			"Address": Address,
				    			"Community Area": Community_Area, 
				    			"Resident of Community": Resident_of_Community,
				    			"Resident": Resident_of_Community,
				    			"Ethnicity": Ethnicity,
				    			"Citizenship": Citizenship,
				    			"genderid": genderid,
				    			"Marital Status": Maritalstatus,
				    			"Dob": date,
				    			"Institution Name": Institution_Name,
				    			"How many members?" : No_Of_members,
				    			"Registration Date": registartion_date_date,
				    			"Registration No" : Registration_No,
				    			"Other details" : Other_details,
				    			"Agency Name" : Agency,
				    			"How are concessions to land handled?" : land_handled,
				    			"Community or Parties" :  Community,
				    			"Level of Authority" : Authority,
				    			"How many members in collective organization?" : collective_members_no
				    			
				    			
				    			
				    			
//								"age":Age,
//								"maritalstatus":Maritalstatus,
//								"citizenship":value[6].attributevalue,
//								"ethnicity":value[7].attributevalue,
//								"resident":value[8].attributevalue,
//								"address":value[9].attributevalue,
//								"mobileno":value[10].attributevalue,
//								"groupId":value[0].attributevalue,	
				    		};
							
							persondataArray.push(persondata);
					 
					})
					


					 return persondataArray;	
	        });
	       
	    },
	    insertItem: function (item) {
	        
	    },
	    updateItem: function (item) {
	    
	    	
	    	
	        return $.ajax({
	            type: "POST",
//	            contentType: "application/json; charset=utf-8" landId,
	            traditional: true,
	            url: "landrecords/saveResourcePersonForEditing/"+landId,
	            data: item,
				success:function(){
					FillResourcePersonDataNew();
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


function addNewPerson(){
	
	$("#ResourcepersonsEditingGrid0").jsGrid("loadData");
	isAddPerson = 1;
	$("#ResourcepersonsEditingGrid0").jsGrid("insertItem",{});


}









function FillResourceCustoAttributes()
{
	var name_Columns = [];
	var myColumns = [];	
	var myColumns1 = [];
	var data = [];
	myColumns1[0] = {type: "control", deleteButton: false};
	myColumns1[1] = {name:"landid", title:"LandId", align:"left", type:"number",  width: 120,editing: false, validate: {validator: "required", message: "Enter LandId"} };

	$.ajax({
		url :"resource/allCustomAttribtesDatatype/" + landId+ "/" + projectId,
		 async: false,
		success : function(data1) {
			
			if(data1 == ""){
				$("#CustomAttributeEditingGrid0").hide();
//				$("#CustomAttributes").hide();
			}
			else{
				$("#CustomAttributeEditingGrid0").show();
//				$("#CustomAttributes").show();
				
			data=data1;
		console.log(data);
			
			var ctr = 2;
			
			var lastsel;
			for ( var i = 0; i < data.length; ++i) {
					name_Columns[ctr] = data[i][0];
					var id = data[i][1];
					
					var val = new Object();
					val.name = name_Columns[ctr];
					
					
					val.index = ctr;
					val.width = 120;
					val.editable = true;
					val.type= "text";
					
					
					myColumns1[ctr] = val;
					ctr++;
					

			}
			}
			}

	});
		
//
//		
//			
////			var rowHeight = 200;

	
	
	// Init editing grid
    $("#CustomAttributeEditingGrid0").jsGrid({
        width: "100%",
        height: "200px",
        inserting: false,
        editing: true,
        sorting: false,
        filtering: false,
        paging: true,
        autoload: false,
        controller: ControllerForResourceCustomAttributes,
        pageSize: 50,
        pageButtonCount: 20,
        fields: myColumns1
//        	[
//            {type: "control", deleteButton: false},
//			{name:"landid", title:"LandId", align:"left", type:"number",  width: 120,editing: false, validate: {validator: "required", message: "Enter LandId"} },
//            {name: "firstname", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
//            {name: "middlename", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
//            {name: "lastname", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
//			{name: "mobNo", title: "ContactNo", align: "left", type: "text", width: 80,editing: true, filtering: false},
//			{name:"groupId", title:"GroupId", align:"left", type:"number",  width: 120,editing: false, hidden:true,  validate: {validator: "required", message: "Enter GroupId"} },
//
//			//            {name:"age", title:"Age", align:"left", type:"number",  width: 120, validate: {validator: "required", message: "Enter Age"} },
////		    {name: "maritalstatus", title: "Marital Status", type: "text", width: 80,editing: true, filtering: false},
////			{name: "citizenship", title: "Citizenship", type: "text", width: 80,editing: true, filtering: false},
////			{name: "ethnicity", title: "Ethnicity", type: "text", width: 80,editing: true, filtering: false},
////		    {name: "resident", title: "Resident", type: "text", width: 80,editing: true, filtering: false},
////            {name: "address", title: "Address", type: "text", width: 120, validate: {validator: "required", message: "Enter Address"}},
////            {name: "mobileno", title: "Mobile Number", type: "text", width: 120},
////			{name: "groupId", title: "GroupId", type: "number", width: 120},
//           
//
//        ]
    });

    $("#CustomAttributeEditingGrid0 .jsgrid-table th:first-child :button").click();
   
    $("#CustomAttributeEditingGrid0").jsGrid("loadData");
    
    
}


var ControllerForResourceCustomAttributes = {
	    loadData: function (filter) {
	        return $.ajax({
	            type: "GET",
	            url: "resource/allCustomAttribue/" + landId+ "/" + projectId,
	            data: filter,
				async: false,
	            success: function(data)
	            {
	            }
	        }).then(function(result){
	        	var persondataArray=[];
	        	var map= new Map();
					 
					 for(var i=0; i<result.length; i++){
						 
						 if(map.size==0){
							 map["landid"]=result[i][0];
						 }
						 
						 map[result[i][6]]=result[i][2];
						 
					 }
						 
					/* if(result[i][5] == "103"){
						 LandId = result[0].landid;
						 Custom1 = result[i][2];
					 }
					 else if(result[i][5] == "104"){
						 Custom2 = result[i].attributevalue;
					 }
					 else if(result[i][5] == "105"){
						 Custom3 = result[i].attributevalue;
					 }*/
					 /*else if(result[i].fieldname == "116"){
						 Custom4 = result[i].attributevalue;
					 }
					 else if(result[i].fieldname == "117"){
						 Custom5 = result[i].attributevalue;
					 }
					 else if(result[i].fieldname == "118"){
						 Custom6 = result[i].attributevalue;
					 }
					 else if(result[i].fieldname == "119"){
						 Custom7 = result[i].attributevalue;
					 }
					 else if(result[i].fieldname == "120"){
						 Custom8 = result[i].attributevalue;
					 }*/
					
					
					/* var persondata ={
			        			"landid"   :LandId,
				    			"Primary Crop":  Custom1,
				    			"Plant Date (Primary Crop)":  Custom2,
				    			"Duration (Primary Crop)":  Custom3,
				    			"Secondary Crop":Custom4,
				    			"Plant Date (Secondary Crop)":Custom5,
				    			"Duration (Secondary Crop)":Custom6,
				    			"Total Expenditures (Farmer)":Custom7,
				    			"Total Sales (Farmer)":Custom8
				    			
				    		};*/
					 
					/* var persondata ={
			        			"landid"   :LandId,
				    			"Color of sky":  Custom1,
				    			"Color of bear":  Custom2,
				    			"Color of soil":  Custom3
				    			
				    			
				    		};*/
							
							persondataArray.push(map);
					


					 return persondataArray;	
	        });
	       
	    },
	    insertItem: function (item) {
	        return false;
	    },
	    updateItem: function (item) {
	    
	    	
	    	
	        return $.ajax({
	            type: "POST",
//	            contentType: "application/json; charset=utf-8",
	            traditional: true,
	            url: "landrecords/saveResourceCustomAttributes",
	            data: item,
				success:function(){
					FillResourceCustoAttributes();
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



function loadResourcePersonsOfEditingForEditing() {
	 
	  var name_Columns = [];
		var myColumns = [];	
		var myColumns1 = [];
		var data = [];
		myColumns1[0] = {type: "control", deleteButton: false};
		myColumns1[1] = {name:"landid", title:"LandId", align:"left", type:"number",  width: 120,editing: false, validate: {validator: "required", message: "Enter LandId"},  visible: false };

		$.ajax({
			url :"resource/allPoiDatatype/" + landId+ "/" + projectId,
			 async: false,
			success : function(data1) {
				
				if(data1 == ""){
					$("#ResourcePOIGrid").hide();
					$("#ResourcePOI").hide();
				}
				else{
					$("#ResourcePOIGrid").show();
					$("#ResourcePOI").show();
				data=data1;
			console.log(data);
				
				var ctr = 2;
				var groupfinalid = 0;
				var lastsel;
				for ( var i = 0; i < data.length; ++i) {
						name_Columns[ctr] = data[i][0];
						var id = data[i][1];
						var groupid =data[i][2];
						if(groupfinalid == 0){
							groupfinalid = groupid;
							}
							else{
								
							}
							if(groupfinalid != groupid){
								
							}
							if(groupfinalid == groupid){
						
						var val = new Object();
						val.name = name_Columns[ctr];
						
						
						val.index = ctr;
						val.width = 120;
						val.editable = true;
						val.type= "text";
						if(id==4){
							val.type= "date";
						}
						else if(id==5){
							
							
							$.ajax({
								url :"resource/relationshipTypes/",
								 async: false,
								success : function(data1) {
							
							val.items=data1;
								}
							});
							val.type= "select";
							val.valueField="name";
							val.textField= "name";
							val.filtering= false;
							val.name = name_Columns[ctr];
						}
						else if(id==6){
							val.items= [{id: 1, name: "Male"}, {id: 2, name: "Female"}];
							val.type= "select";
							val.valueField="name";
							val.textField= "name";
							val.filtering= false;
							val.name = name_Columns[ctr];
						}
						
						
						myColumns1[ctr] = val;
						ctr++;
						

				}
							myColumns1[ctr]= {name: "groupId", title: "GroupId", type: "number", width: 120, visible: false};
				}
				}
				}

		});
		
		 $("#ResourcePOIGrid").jsGrid({
		        width: "100%",
		        height: "200px",
		        inserting: false,
		        editing: true,
		        sorting: false,
		        filtering: false,
		        paging: true,
		        autoload: false,
		        controller: resourcepersonsEditingController,
		        pageSize: 50,
		        pageButtonCount: 20,
		        fields: myColumns1
		        	
		        	/*[
		            {type: "control", deleteButton: false},
		            {name: "firstName", title: "First name", type: "text", width: 120, validate: {validator: "required", message: "Enter first name"}},
		            {name: "middleName", title: "Middle name", type: "text", width: 120, validate: {validator: "required", message: "Enter middle name"}},
		            {name: "lastName", title: "Last name", type: "text", width: 120, validate: {validator: "required", message: "Enter last name"}},
		            {name: "gender", title: "Gender", type: "select", items: [{id: 1, name: "Male"}, {id: 2, name: "Female"}, {id: 3, name: "Other"}], valueField: "id", textField: "name", width: 120, validate: {validator: "required", message: "Enter first name"}},
		            {name: "dob", title: "Date of birth", type: "date", width: 120 ,validate: {validator: "required", message: "Enter first name"}},
		            {name: "relation", title: "Relation", type: "select", items: [{id: 1, name: "Spouse"}, {id: 2, name: "Son"}, {id: 3, name: "Daughter"}, {id: 4, name: "Grandson"}, {id: 5, name: "Granddaughter"}, {id: 6, name: "Brother"},
		                                                                          {id: 7, name: "Sister"}, {id: 8, name: "Father"}, {id: 9, name: "Mother"}, {id: 10, name: "Grandmother"}, {id: 11, name: "Grandfather"}, {id: 12, name: "Aunt"},
		                                                                          {id: 13, name: "Uncle"}, {id: 14, name: "Niece"}, {id: 15, name: "Nephew"}, {id: 16, name: "Other"}, {id: 17, name: "Other relatives"}, {id: 18, name: "Associate"},
		                                                                          {id: 19, name: "Parents and children"}, {id: 20, name: "Siblings"}], valueField: "id", textField: "name", width: 120, validate: {validator: "required", message: "Enter first name"}},
		            {name: "landid", title: "LandID", type: "number", width: 70, align: "left", editing: false, filtering: true, visible: false},
		            {name: "id", title: "ID", type: "number", width: 70, align: "left", editing: false, filtering: true, visible: false},


		        ]*/
		    });
		 $("#ResourcePOIGrid .jsgrid-table th:first-child :button").click();
		$("#ResourcePOIGrid").jsGrid("loadData");
	}

	var resourcepersonsEditingController = {
	   loadData: function (filter) {
	       return $.ajax({
	           type: "GET",
	           url: "resource/PoiData/" + landId+ "/" + projectId,
	           data: filter,
				async: false,
	           success: function(data)
	           {
	           }
	       }).then(function(result){
	       	var persondataArray=[];
	       	var gid=0;
	       /*	var poiFirstName = "";
	       	var poiMiddleName = "";
	       	var poiLastName ="";
	       	var poiDob= "";
	       	var poiRelation= "";
	       	var poiGender="";*/
	       	$.each( result, function(index,value){
	       	 for(var i in value){
					
					 if(value[i].poiattributevalueid == "1"){
						 LandId = value[i].landid;
						 gid=value[i].groupid;
						 poiFirstName = value[i].attributevalue;
					 }
					 else if(value[i].poiattributevalueid == "2"){
						 poiMiddleName = value[i].attributevalue;
					 }
					 else if(value[i].poiattributevalueid == "3"){
						 poiLastName =value[i].attributevalue;
					 }
					 else if(value[i].poiattributevalueid == "4"){
//						 poiDob = result[i][2];
						
						    			  var date = new Date(value[i].attributevalue);
						    			
						    			  poiDob= date			
						    			
//						    	
					 }
					 else if(value[i].poiattributevalueid == "5"){
						 poiRelation =value[i].attributevalue;
					 }
					 else if(value[i].poiattributevalueid == "6"){
						 poiGender = value[i].attributevalue;
					 }
					
					 }
	      
					 var persondata ={
			        			"landid"   :LandId,
				    			"First Name":  poiFirstName,
				    			"Middle Name":  poiMiddleName,
				    			"Last Name":  poiLastName,
				    			"DOB": poiDob,
				    			"Relationship Type": poiRelation,
				    			"Gender": poiGender,
				    			"groupId": gid
				    			
				    		};
							
							persondataArray.push(persondata);
					
	        });

					 return persondataArray;	
	       });
	      
	   },
	   insertItem: function (item) {
	      
	   },
	   updateItem: function (item) {
	   	
	  /* 	var ajaxdata = {
	   			"firstName": item.firstName,
	   			"middleName": item.middleName,
	   			"lastName": item.lastName,
	   			"gender": item.gender,
	   			"dob": item.dob,
	   			"relation": item.relation,
	   			"id": item.id
	   			
					}*/
	   
	       return $.ajax({
	           type: "POST",
//	           contentType: "application/json; charset=utf-8",
	           traditional: true,
	           url: "landrecords/saveResourcePoi/" + landId+ "/" +geomName,
	           data: item,
	           success:function(){
	        	   loadResourcePersonsOfEditingForEditing();
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

	
	function addNewResourcePOI(){
		$("#ResourcePOIGrid").jsGrid("loadData");
		$("#ResourcePOIGrid").jsGrid("insertItem",{});


	}


function formatDate_R(date) {
	return jQuery.datepicker.formatDate('yy-mm-dd', new Date(date));
}


function saveInstitute(){
	if ($("#editAttributeformID_res").valid()) {

	jQuery.ajax({
		type : "POST",
		url : "resource/saveattributes",
		data : jQuery("#editAttributeformID_res")
				.serialize(),
		success : function(result) {
			 $("input,select,textarea").removeClass('addBg');
			 $("#comment_Save").prop("disabled",false).hide();
		}
		});
	}
	
}


function saveCustomAttributes(){
	if ($("#editAttributeformID_res").valid()) {

	jQuery.ajax({
		type : "POST",
		url : "landrecords/saveResourceCustomAttributes",
		data : jQuery("#editAttributeformID_res")
				.serialize(),
		success : function(result) {
			 $("input,select,textarea").removeClass('addBg');
			 $("#Custom_Save").prop("disabled",false).hide();
		}
		});
	}
	
}


function getFarmReport(land_id,projectId,geomType)
{
	var extent;
	jQuery.ajax(
			{
			   	type: 'GET',
				url:  'landrecords/farmreport/'+land_id+ '/'+ projectId,
				async:false,
				cache: false,
				success: function (data) 
				{
					
					//if(data[0] != null && data[0].length != 0){
						if(true){

						jQuery.get("resources/templates/report/farm-report.html", function (template) 
						{
							jQuery("#printDiv").empty();
							jQuery("#printDiv").append(template);
							
							//if(data!=null || data!="" || data!="undefined")
						    if(true)
							{
									jQuery('#reportNameId').empty();
									jQuery('#reportNameId').text("Data Correction Report"); 
							
								 var layerName = "";
								 var relLayerName = "";
								 var fieldName = "";
								var _featureTypes= [];
									
								 $.ajaxSetup({
								        cache: false
								    });
									if(geomType=="Polygon"){
										 relLayerName = "Mast:la_spatialunit_resource_land";
										 fieldName = "landid";
										 _featureTypes.push("la_spatialunit_resource_land");
										 layerAlias="Resource";
									 }else if(geomType=="Line"){
										  relLayerName = "Mast:la_spatialunit_resource_line";
								          fieldName = "landid";
										  _featureTypes.push("la_spatialunit_resource_line");
										  layerAlias="Line";
									 }else if(geomType=="Point"){
										 relLayerName = "Mast:la_spatialunit_resource_point";
								          fieldName = "landid";
										  _featureTypes.push("la_spatialunit_resource_point");
										  layerAlias="point";
									 }
									
									
								 var objLayer=getLayerByAliesName(layerAlias);
								
									 var _wfsurl=objLayer.values_.url;
									var _wfsSchema = _wfsurl + "request=DescribeFeatureType&version=1.1.0&typename=" + objLayer.values_.name +"&maxFeatures=1&outputFormat=application/json";;

									//Get Geometry column name, featureTypes, targetNamespace for the selected layer object //
									$.ajax({
										url: PROXY_PATH + _wfsSchema,
										async: false,
										success: function (data) {
											 _featureNS=data.targetNamespace;
											 
										}
									});

			             	var fieldVal = land_id;
							var _featurePrefix="Mast";
							
							
								
							var featureRequest1 = new ol.format.WFS().writeGetFeature({
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
												body: new XMLSerializer().serializeToString(featureRequest1)
											  }).then(function(response) {
												return response.json();
											  }).then(function(json) {
											   var features = new ol.format.GeoJSON().readFeatures(json);

											         var vectorSource = new ol.source.Vector();
													 vectorSource.addFeatures(features);
													 extent=vectorSource.getExtent();
													 var cqlFilter = 'landid='+fieldVal ;  				  				
     												 var url1 = "http://"+location.host+"/geoserver/wms?" +"bbox="+extent+"&FORMAT=image/png&REQUEST=GetMap&layers=Mast:LBR_district,Mast:la_spatialunit_land&width=245&height=243&srs=EPSG:4326"+"&CQL_FILTER;INCLUDE;landid="+fieldVal+";";
													 
													 
                                                   
													jQuery('#mapImageId').empty();
													jQuery('#mapImageId').append('<img  src='+url1+'>');

																									    var _html="";
													var _th="<tr><th>Latitude</th><th>Longitude </th></tr>"
													 for (i = 0; i < features[0].geometryChangeKey_.target.flatCoordinates.length/2; i++) {
														 var j=i;
														_html =_html+ "<tr><td>"+features[0].geometryChangeKey_.target.flatCoordinates[i] +"</td><td>"+features[0].geometryChangeKey_.target.flatCoordinates[j++] + "</td><tr>";
													} 
													jQuery('#latLongId').empty();
													var _table=_th+_html;
	                                                jQuery('#latLongId').append(_table);	
													
												
	                            
	                       							var html = $("#printDiv").html();
													var printWindow=window.open('','popUpWindow', 'height=600,width=950,left=40,top=20,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
															printWindow.document.write ('<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN""http://www.w3.org/TR/html4/strict.dtd">'+
															'<html><head><title>Report</title>'+' <link rel="stylesheet" href="/mast/resources/styles/complete-style.css" type="text/css" />'
															+'<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>'+
															'<script src="../resources/scripts/cloudburst/viewer/Print.js"></script>'+
															 +'</head><body>'+html+'</body></html>');	
															
															printWindow.document.close();
									
									
											   });
											   
									
							}
							else
							{
								jAlert('info','Error in Fetching Details',"");
							}
						});
						
						
					}
					
				},
				error : function(jqXHR, textStatus, errorThrown) {									
					jAlert('info', "", "");
				}
			});
}


