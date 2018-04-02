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

       jQuery.ajax({
        url: "resource/getAllresouce/" + activeProject + "/" + 0,
        async: false,
        success: function (data) {
            dataList = data;
           projectId =  dataList[0].projectId
			

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
        alert("No records found");
    }
	
}
	
function nextRecords_Res(){
	
	records_from_Res = $('#records_from_Res').val();
    records_from_Res = parseInt(records_from_Res);
    records_from_Res = records_from_Res + 9;

    if (records_from_Res <= totalRecords_Res - 1) {
           RegistrationRecords_Res(records_from_Res);
		
    } else {
        alert("No records found");
    }
	
}


function RegistrationRecords_Res(records_from_Res){
	
		jQuery.ajax({
        url: "resource/getAllresouce/" + activeProject + "/" + records_from_Res,
        async: false,
        success: function (data) {
            
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
		
		closeDialog('editingdiv')
		 $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#map-tab"]').parent().index());
        $('#sidebar').show();
        $('#collapse').show();
		
		
		
	 }else{
		 
		 $('#mainTabs').tabs("option", "active", $('#mainTabs a[href="#landresource-div"]').parent().index());
        $('#sidebar').hide();
        $('#collapse').hide();
        jAlert('Site not found on Map', 'Alert'); 
		 
	 }
}
function viewAttribute(usin)
{
	$(".signin_menu").hide();
	landId = usin;
	
	FillResourcePersonDataNew();
	FillResourceCustoAttributes();

	
	attributeHistoryDialog = $( "#attributeDialog" ).dialog({
		autoOpen: false,
		height: 500,
		width: 1161,
		left: 95,
		resizable: true,
		modal: true,
		close: function () {
			attributeHistoryDialog.dialog("destroy");
        },

		buttons: [{
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
			attributeHistoryDialog.dialog( "close" );


		}
	});
	$("#comment_cancel").html('<span class="ui-button-text">cancel</span>');
	attributeHistoryDialog.dialog( "open" );

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
					if(id==1021 || id==1068 || id==1129 || id==1120){
						val.type= "date";
					}
					else if(id==1033){
						val.type= "date";
					}
					else if(id==1107){
						val.items=[{id: 1, name: "National"}, {id: 2, name: "Regional"},{id: 3, name: "District"}, {id: 4, name: "Local "}];
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
						val.items=[{id: 1, name: "un-Married"}, {id: 2, name: "married"},{id: 3, name: "divorced"}, {id: 4, name: "widow"},{id: 5, name: "widower"}];
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];

					}
					else if(id==1024 || id==1070 || id==1122){
						val.items=[{id: 1, name: "Ethnicity 1"}, {id: 2, name: "Ethnicity 2"},{id: 3, name: "Ethnicity 3"}, {id: 4, name: "Ethnicity 4"}];
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];
					}
					else if(id==1025 || id==1071 || id==1123){
						val.items=[{id: 1, name: "Yes"}, {id: 2, name: "No"}];
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.visible= false
						val.name = name_Columns[ctr];

					}
					else if(id==1020 || id==1067 || id==1119){
						val.items=[{id: 1, name: "male"}, {id: 2, name: "female"}];
						val.type= "select";
						val.valueField="name";
						val.textField= "name";
						val.filtering= false;
						val.name = name_Columns[ctr];

					}
					
					else if(id==1023 || id==1069 || id==1121){
						val.items=[{id: 1, name: "Not Known"}, {id: 2, name: "Country1"}, {id: 3, name: "Country2"}, {id: 4, name: "Others"}];
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
					 if(value[i].fieldname == "firstname"){
						 LandId = value[0].landid;
						 GroupId = value[0].groupid;
						 Firstname = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "middlename"){
						 Middlename = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "lastname"){
						 Lastname = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Mobile No"){
						 Mobile = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Dob"){
						 Dob = value[i].attributevalue;
						
					    	
					    	{
					    		if(Dob!=null && Dob!='')
					    		{
					    			  var date = new Date(Dob);
					    			
					    			
					    			
//					    			dob = date.getFullYear()+ '-' + date.getMonth() + '-' + date.getDate();
					    		}
					    	}
					 }
					 else if(value[i].fieldname == "Marital Status"){
						 Maritalstatus = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "genderid"){
						 genderid = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Citizenship"){
						 Citizenship = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Ethnicity"){
						 Ethnicity = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Resident of Community"){
						 Resident_of_Community = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Resident"){
						 Resident_of_Community = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Address"){
						 Address = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Institution Name"){
						 Institution_Name = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Registration No"){
						 Registration_No = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Registration Date"){

						 Registration_Date = value[i].attributevalue;
						
					    	
					    	{
					    		if(Registration_Date!=null && Registration_Date!='')
					    		{
					    			  var registartion_date_date = new Date(Registration_Date);
					    			
					    			
					    			
//					    			dob = date.getFullYear()+ '-' + date.getMonth() + '-' + date.getDate();
					    		}
					    	}
					 
						
					 }
					 else if(value[i].fieldname == "How many members?"){
						 No_Of_members = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Other details"){
						 Other_details = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Agency Name"){
						 Agency = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "How are concessions to land handled?"){
						 land_handled = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Community or Parties"){
						 Community = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "Level of Authority"){
						 Authority = value[i].attributevalue;
					 }
					 else if(value[i].fieldname == "How many members in collective organization?"){
						 collective_members_no = value[i].attributevalue;
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
	        return false;
	    },
	    updateItem: function (item) {
	    
	    	
	    	
	        return $.ajax({
	            type: "POST",
//	            contentType: "application/json; charset=utf-8",
	            traditional: true,
	            url: "landrecords/saveResourcePersonForEditing",
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
				$("#CustomAttributes").hide();
			}
			else{
				$("#CustomAttributeEditingGrid0").show();
				$("#CustomAttributes").show();
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
	        	
					 
					 for(var i=0; i<result.length; i++){
					 if(result[i].fieldname == "2"){
						 LandId = result[0].landid;
						 Custom1 = result[i].attributevalue;
					 }
					 else if(result[i].fieldname == "5"){
						 Custom2 = result[i].attributevalue;
					 }
					 else if(result[i].fieldname == "6"){
						 Custom3 = result[i].attributevalue;
					 }
					 else if(result[i].fieldname == "7"){
						 Custom4 = result[i].attributevalue;
					 }
					
					 }
					 var persondata ={
			        			"landid"   :LandId,
				    			"Resource Custom Attribute":  Custom1,
				    			"Resource Custom Attribute Sub Class 3":  Custom2,
				    			"Resource Custom Attribute Sub Class 4":  Custom3,
				    			"Resource Custom Attribute Sub Class 5":Custom4,
				    			
				    		};
							
							persondataArray.push(persondata);
					


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




