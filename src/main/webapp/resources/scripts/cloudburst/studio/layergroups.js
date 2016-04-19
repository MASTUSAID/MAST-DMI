
var selectedItem=null;
function LayerGroup(_selectedItem)
{
	
	
	//jQuery("#tableGrid").empty();	
	selectedItem=_selectedItem;
	
	/*if( jQuery("#LayergroupFormDiv").length<=0){*/
		
		displayRefreshedLayergroup();
	
	/*}
	else{
		
		displayLayergroup();
		
	}*/
}


function displayRefreshedLayergroup(){

	
	jQuery.ajax({
		url: "layergroup/" + "?" + token,
         success: function (data) {
        	jQuery("#layergroups").empty(); 
			jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {
		    			    	
		    	jQuery("#layergroups").append(template);
		    	jQuery('#LayergroupFormDiv').css("visibility", "visible");
		    	
		    	//jQuery("#projectDetails").hide();	        	
	        	jQuery("#LayergroupRowData").empty();
	        	jQuery("#layergroupTable").show();	        		        	
	        	jQuery("#layergroup_accordion").hide();		    	
		    	
	        	if(data!="")
	        		{
		    	jQuery("#LayergroupTemplate").tmpl(data).appendTo("#LayergroupRowData");
	        		}	    	
		    	jQuery("#layergroup_btnSave").hide();
		    	jQuery("#layergroup_btnBack").hide();
		    	jQuery("#layergroup_btnNew").show();
		    	
		      	//$("#project_txtSearch").trigger("keyup");
                $("#layergroupTable").tablesorter({ 
                		headers: {2: {sorter: false  },  3: {  sorter: false } },	
                		debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                       .tablesorterPager({ container: $("#layergroup_pagerDiv"), positionFixed: false })
                       .tablesorterFilter({ filterContainer: $("#layergroup_txtSearch"),                           
                           filterColumns: [0],
                           filterCaseSensitive: false
                       });
		    	
			
			});
    
         }
	 });
	
	
}
function displayLayergroup(){
	jQuery("#layergroup_accordion").hide();
	jQuery("#layergroupTable").show();

	jQuery("#layergroup_btnSave").hide();
	jQuery("#layergroup_btnBack").hide();
	jQuery("#layergroup_btnNew").show();
}



var layergroup_layer=null;

var createEditLayergroup = function (_name) {
    
	jQuery("#layergroup_btnSave").hide();
	jQuery("#layergroup_btnBack").hide();
	jQuery("#layergroup_btnNew").hide();
    
    //jQuery("#projectTable").empty();
    jQuery("#layergroupTable").hide();
    //jQuery("#projectDetails").show();    
    
    jQuery("#layergroupGeneralBody").empty();
    jQuery("#layergroupLayerBody").empty();
    
    
    

    jQuery.ajax({
        url: "layer/" + "?" + token,
        success: function (layers) {
        	layergroup_layer= layers;
        },
        async: false
    });

   
    if (_name) {

        jQuery.ajax({
            url: selectedItem+"/" + _name + "?" + token,
            async:false,
            success: function (layerdata) {

                jQuery("#LayergroupTemplateForm").tmpl(layerdata,

                            {
                	
                            }

                         ).appendTo("#layergroupGeneralBody");
                
                
                jQuery("#LayergroupTemplateLayer").tmpl(layerdata,

                        {
            	
                        }

                     ).appendTo("#layergroupLayerBody");
                
                           	
                
               
                //empty   list box              
                
               //create layerlist
                jQuery.each(layergroup_layer, function (i, value) {    	
                	jQuery("#layerList").append(jQuery("<option></option>").attr("value", value.alias).text(value.alias));        
                });
                
                //set layer list value
                var layerorder={};
                
                jQuery.each(layerdata[0].layers, function (i, layerList) {      
                	layerorder[layerList.layerorder]=layerList.layer;                	                	
                });
                
                for(var i=1; i<=layerdata[0].layers.length;i++){
                	if(layerorder[i] != undefined){
                		jQuery("#addedLayerList").append(jQuery("<option></option>").attr("value", layerorder[i]).text(layerorder[i]));                        
                		jQuery("#layerList option[value=" + layerorder[i] + "]").remove();
                	}
                }
                jQuery('#name').attr('readonly', true);
				
                /*jQuery.each(layerdata[0].layers, function (i, layerList) {                    
                        jQuery("#addedLayerList").append(jQuery("<option></option>").attr("value", layerList.layer).text(layerList.layer));                        
                        jQuery("#layerList option[value=" + layerList.layer + "]").remove();
                });*/
                
            },
            cache: false
        });
    } else {

    	jQuery("#LayergroupTemplateForm").tmpl(null,

                {
    	
                }

             ).appendTo("#layergroupGeneralBody");
    
    
    	jQuery("#LayergroupTemplateLayer").tmpl(null,

            {
	
            }

         ).appendTo("#layergroupLayerBody");
        
    	
        jQuery('#name').removeAttr('readonly');
               //create layerlist                 
        jQuery.each(layergroup_layer, function (i, value) {    	
        	jQuery("#layerList").append(jQuery("<option></option>").attr("value", value.alias).text(value.alias));        
        });
        
    }
    
    jQuery("#layergroup_accordion").show();  
	jQuery("#layergroup_accordion").accordion({fillSpace: true});
    
    jQuery("#layergroup_btnSave").show();
    jQuery("#layergroup_btnBack").show();

} 

////////////save layergroup


var saveLayergroupData= function () {
	
	jQuery.ajax({
        type: "POST",              
        url: "layergroup/create" + "?" + token,
        data: jQuery("#layergroupForm").serialize(),
        success: function () {        
                                   
            jAlert('Data Successfully Saved', 'Layergroup');
           //back to the list page 
           
           displayRefreshedLayergroup();
            
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
            alert('err.Message');
        }
    });
	
}

////////////////////////////
var saveLayergroup= function () {

	jQuery("#layergroupForm").validate({
		
		ignore:[],
		rules: {
			name:"required",
			alias: "required"
		},
		messages: {
			name: "Please enter Name",
			alias: "Please enter  Alias"
		}	
				
		});

 if ($("#layergroupForm").valid()) {

	
	if (jQuery('#addedLayerList option').size() > 0) {
		
		jQuery("#addedLayerList option").attr("selected", "selected");
		saveLayergroupData();
	}
	else {	    
	    jAlert('Add atleast one Layer ', 'layergroup');
	    return;
	}
 }
 else {

		jAlert("Please Fill Mandatory fields in all tabs","Message");
	}
	
}


///////////////////

var deleteLayergroup= function (_lgName) {
	
	
		
		jConfirm('Are You Sure You Want To Delete : <strong>' + _lgName + '</strong>', 'Delete Confirmation', function (response) {

	        if (response) {
	        	jQuery.ajax({          
	                url: "layergroup/delete/"+_lgName + "?" + token,
	                success: function () { 	                	
	                	jAlert('Data Successfully Deleted', 'Layer Group');
	                   
	                	displayRefreshedLayergroup();
	                    
	                },
	                error: function (XMLHttpRequest, textStatus, errorThrown) {
	                    
	                    alert('err.Message');
	                }
	            });
	        }

	    });

}




///////////////////////////


jQuery(function () {

	jQuery("#addLayer").live('click', function () {
	    
		jQuery("#layerList  option:selected").appendTo("#addedLayerList");
	});

	jQuery("#removeLayer").live('click', function () {
	    jQuery("#addedLayerList option:selected").appendTo("#layerList");
	});


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


	});

/////////////////////////