
var selectedItem=null;
function Unit(_selectedItem)
{
	

	selectedItem=_selectedItem;
	jQuery.ajax({
		url: "unit/",
         success: function (data) {
        	jQuery("#tableGrid").empty(); 
			jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {
		    			    	
		    	jQuery("body").append(template);
		    	jQuery('#unitFormDiv').css("visibility", "visible");
		    	
		    	jQuery("#unitFormDiv").dialog({title: "Unit", modal: true, minWidth:800, autoOpen: true,
		    	
		    		close: function (ev, ui) {    					    			
    					jQuery("#unitFormDiv").remove();
    					
    				}
		    	});
		    	
		    	
		    	jQuery("#tableDetails").hide();	        	
	        	jQuery("#tableRowData").empty();
	        	jQuery("#tableList").show();	        	
	        			    	
		    	
		    	jQuery("#UnitTemplate").tmpl(data).appendTo("#tableRowData");
		    	 		    	
		    	jQuery("#btnSave").hide();
		    	jQuery("#btnBack").hide();
		    	jQuery("#btnNew").show();		    			    
		    	//jQuery("#pagerDiv").show();
		    	
		    	//$("#tableList").tablesorter();
		    	
		    	/*$("#txtSearch").trigger("keyup");
		    	$("#tableList").tablesorterFilter({ debug: false,
		    		filterContainer: $("#txtSearch"),
		    		filterColumns: [0, 1],
                    filterCaseSensitive: false
                    });*/
		    	
		    	//$("#txtSearch").trigger("keyup");
                $("#tableList").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                       .tablesorterPager({ container: $("#pagerDiv"), positionFixed: false })
                       .tablesorterFilter({ filterContainer: $("#txtSearch"),                           
                           filterColumns: [0],
                           filterCaseSensitive: false
                       });
		    	
			});
    
         }
	 });
}

var CreateEditUnit = function (_name) {
  
	    jQuery("#btnNew").hide();    
	    jQuery("#btnSave").hide();
	    jQuery("#btnBack").hide();
	    //jQuery("#pagerDiv").hide();
	    //jQuery("#searchDiv").hide();
	    
	    //jQuery("#tableList").empty();
	    jQuery("#tableList").hide();
	    jQuery("#tableDetails").show();    
	    jQuery("#detailData").empty();
	
    if (_name) {

            jQuery.ajax({
            url: selectedItem+"/" + _name,
            async:false,
            success: function (data) {

                jQuery("#UnitTemplateForm").tmpl(data,

                            {
                	
                            }

                         ).appendTo("#detailData");

                jQuery('#name').attr('readonly', true);
                
            },
            cache: false
        });
    } else {

        jQuery("#UnitTemplateForm").tmpl(null,

                {
        	
                }
            ).appendTo("#detailData");
        
        jQuery('#name').removeAttr('readonly');        
        
    }
      
    jQuery("#btnSave").show();
    jQuery("#btnBack").show();
    
    
} 

var saveUnitData= function () {
	
	jQuery.ajax({
        type: "POST",              
        url: "unit/create",
        data: jQuery("#unitForm").serialize(),
        success: function () {        
            
        	jAlert('Data Successfully Saved', 'Unit');
           //back to the list page 
            var unit=new Unit('unit');
            
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
            alert('err.Message');
        }
    });
	
}


var deleteUnit= function (_unitName) {
	
	jConfirm('Are You Sure You Want To Delete : <strong>' + _unitName + '</strong>', 'Delete Confirmation', function (response) {

        if (response) {
        	jQuery.ajax({          
                url: "unit/delete/"+_unitName,
                success: function () { 
                	
                	jAlert('Data Successfully Deleted', 'Unit');
                   //back to the list page 
                    var unit=new Unit('unit');
                    
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    
                    alert('err.Message');
                }
            });
        }

    });
	
	
	

	
}

function saveUnit(){
	
	jQuery("#unitForm").validate({

	rules: {
		name:"required",
		description: "required"
	},
	messages: {
		name: "Please enter Name",
		description: "Please enter  Description"
	}	
			
	});
	
	if(jQuery("#unitForm").valid())	{						
		saveUnitData();
	
	}
	
	
}