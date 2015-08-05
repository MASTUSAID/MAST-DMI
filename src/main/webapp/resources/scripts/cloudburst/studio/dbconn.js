var bValidated = false;

function DBConnection(id)
{	
	if( jQuery("#dbconnFormDiv").length <= 0){
		populateConnectionList(id);
	}
	else{
		reloadConnectionList();
	}
}

function reloadConnectionList(){
	
	jQuery("#dbconn_accordion").hide();	        	
	jQuery("#dbconnTable").show();
	
	$("#btn_DbconnSave").hide();
	$("#btn_DbconnBack").hide();
	$("#btn_DbconnNew").show();	
}

var populateConnectionList = function (id, reload) {
	 
	$("#DbConnRowData").empty();	
	
	$("#btn_DbconnSave").hide();
	$("#btn_DbconnBack").hide();
	$("#btn_DbconnNew").hide();
	 
	 $.ajax({
	        url: id + "/" + "?" + token,
	        success: function (data) {
	            _data = data;
	            
	            jQuery("#dbconns").empty();	
	            jQuery.get("resources/templates/studio/" + id + ".html", function (template){
		            jQuery("#dbconns").append(template);
			    	$('#dbconnFormDiv').css("visibility", "visible");
					
					//jQuery("#maptip_accordion").hide();	
					jQuery("#dbconnTable").show();
			    	$("#DBConnTemplate").tmpl(data).appendTo("#DbConnRowData");
			    	
	                $("#btn_DbconnSave").hide();
	                $("#btn_DbconnBack").hide();
	                $("#btn_DbconnNew").show();
	                $("#dbconn_accordion").hide();
	                
	                $("#dbconnTable").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
	                .tablesorterPager({ container: $("#dbconn_pagerDiv"), positionFixed: false })
	                .tablesorterFilter({ filterContainer: $("#dbconn_txtSearch"),                           
	                    filterColumns: [0],
	                    filterCaseSensitive: false
	                });
	                          
	            });
	        },
	        cache: false
	    });
}

var deleteConnectionRecord = function(url, itemToDelete){
	
	 jConfirm('Are You Sure You Want To Delete : <strong>' + itemToDelete + '</strong>', 'Delete Confirmation', function (r) {

		    if (r) {
			    $.ajax({
			        url: url + "?" + token,
			        success: function (data) {
			        	if(data){
				            jAlert('Data Successfully Deleted', 'Delete');
				            populateConnectionList('dbconnection', true);
			        	}else{
			        		jAlert('Failed to remove dbconnection record', 'Delete');
			        	}
			
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			            jAlert('Error', 'Delete');
			        }
			    });
		    }
	    });
}

var createEditConnectionRecord = function(connName){
	var _data;
	jQuery("#dbconnTable").hide();
	jQuery("#dbconnGeneralBody").empty();
	
	$("#btn_DbconnSave").hide();
	$("#btn_DbconnBack").hide();
	$("#btn_DbconnNew").hide();
	
	if(connName){
		$("#dbconnfrm").attr("action", "dbconnection/edit");
		jQuery.ajax({
	        url: "dbconnection/" + connName + "?" + token,
	        success: function (data) {
	        	_data = data;
	        	 $("#DBConnTemplateForm").tmpl(_data,
                         {
	        		 		action: "Edit"
                         }
                      ).appendTo("#dbconnGeneralBody");
	        	 
	        	 	jQuery("#dbconnTable").hide();
	        	 	populateSupportedDatabases(_data.databaseType);
	        	 	setCheckValidation(true);
	        	 	$("#btn_DbconnSave").show();
	        	},
	        	async: false
	    	});
	}else{
		$("#dbconnfrm").attr("action", "dbconnection/create");
		$("#DBConnTemplateForm").tmpl(null,
                {
                    action: "Create"
                }
            ).appendTo("#dbconnGeneralBody");
		jQuery("#dbconnTable").hide();
	 	populateSupportedDatabases();
	 	$("#btn_DbconnSave").show();
	 	$("#btn_DbconnSave").attr('disabled', 'disabled');
	}
	
	$("#dbconn_accordion").show();
    jQuery("#dbconn_accordion").accordion({fillSpace: true});
	
	$("#btn_DbconnBack").show();
}

var populateSupportedDatabases = function(dbType){
	var db = ['MSSQL Server', 'MYSQL', 'Oracle', 'PostGreSQL'];
	
	$("#dbType").empty();
	jQuery("#dbType").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
	 jQuery.each(db, function (i, value) {                    
         jQuery("#dbType").append(jQuery("<option></option>").attr("value", value).text(value)); 
	 });
	 
	 if(dbType){
		 $("#dbType").val(dbType); 
	 }
}

var validateConnection = function(connectionName){
	/*jQuery.ajax({
        url: "dbconnection/validate/" + connectionName + "?" + token,
        success: function (data) {
        	if(data){
        		jAlert('Connection test successful');
        		$("#btn_DbconnSave").removeAttr('disabled');
        		setCheckValidation(true);
        	}
        }
    });*/
	
	$("#dbconnfrm").validate({
        rules: {
        	connectionName: "required",
        	databaseType: "required",
        	databaseName: "required",
        	serverName: "required",
        	serverPort: "required",
        	userId: "required",
        	password: "required"
        },
        messages: {
        	connectionName: "Please enter  unique name",
        	databaseType: "Please Select database",
        	databaseName: "Please enter database name",
        	serverName: "Please enter server name",
        	serverPort: "Please enter server port",
        	userId: "Please enter user id",
        	password: "Please enter password"
        }
    });
	if(jQuery("#dbconnfrm").valid())	{
		var url = $("#dbconnfrm").attr("action");
		 var data = $("#dbconnfrm").serialize();
		 
		 $.post("dbconnection/validate?" + token, 
				 data,                
		 function(_data){    
			 if(_data){
	        	jAlert('Connection test successful');
	        	$("#btn_DbconnSave").removeAttr('disabled');
	        	setCheckValidation(true);
	         }else{
	        	 jAlert('Connection test failed');
	         }
		 });
	}
}

var setCheckValidation = function(state){
	bValidated = state;
}

var saveConnection = function(){
	if(bValidated){
		/*jQuery.validator.addMethod("noSpace", function (value, element) {
	        return value.indexOf(" ") < 0 && value != "";
	    }, "No space please and don't leave it empty");*/
		
		$("#dbconnfrm").validate({
	        rules: {
	        	connectionName: "required",
	        	databaseType: "required",
	        	databaseName: "required",
	        	serverName: "required",
	        	serverPort: "required",
	        	userId: "required",
	        	password: "required"
	        },
	        messages: {
	        	connectionName: "Please enter  unique name",
	        	databaseType: "Please Select database",
	        	databaseName: "Please enter database name",
	        	serverName: "Please enter server name",
	        	serverPort: "Please enter server port",
	        	userId: "Please enter user id",
	        	password: "Please enter password"
	        }
	    });
		if(jQuery("#dbconnfrm").valid())	{
			var url = $("#dbconnfrm").attr("action");
			 var data = $("#dbconnfrm").serialize();
			 
			 $.post(url + "?" + token, 
					 data,                
			 function(_data){    
				 if(_data){
					 jAlert('Data Successfully Saved', 'Save');   
					 populateConnectionList('dbconnection', true);
				 }else{
					 jAlert('Unable to save data, record with same name or primary key may already exists', 'Error');
				 }
			 });
		}
		
	}else{
		jAlert('Please validate connection.');
	}
}