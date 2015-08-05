/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */
var selectedItem=null;
function Role(_selectedItem)
{
	selectedItem=_selectedItem;
	if( jQuery("#roleFormDiv").length<=0){
	
		displayRefreshedRole();
		
	}
	else{
		
		displayRole();
	}
}

function displayRefreshedRole(){
	
	jQuery.ajax({
		url: "role/" + "?" + token,
         success: function (data) {
        	jQuery("#roles").empty();
			jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {
		    			    	
		    	jQuery("#roles").append(template);
		    	jQuery('#roleFormDiv').css("visibility", "visible");		    			    	
		    	
		    	jQuery("#roleDetails").hide();	        	
	        	jQuery("#RolesRowData").empty();
	        	jQuery("#roleTable").show();	        	
	        	jQuery("#role_accordion").hide();		    	
		    	
		    	jQuery("#RoleTemplate").tmpl(data).appendTo("#RolesRowData");
		    	 		    	
		    	jQuery("#role_btnSave").hide();
		    	jQuery("#role_btnBack").hide();
		    	jQuery("#role_btnNew").show();
		    	
		    	//$("#role_txtSearch").trigger("keyup");
                $("#roleTable").tablesorter({ 
                	headers: {2: {sorter: false  },  3: {  sorter: false } },	
                	debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                       .tablesorterPager({ container: $("#role_pagerDiv"), positionFixed: false })
                       .tablesorterFilter({ filterContainer: $("#role_txtSearch"),                           
                           filterColumns: [0, 1],
                           filterCaseSensitive: false
                       });
		    	
			
			});
    
         }
	 });
	
}

function displayRole(){
	      				        
	jQuery("#role_accordion").hide();
	jQuery("#roleTable").show();
		
	jQuery("#role_btnSave").hide();
	jQuery("#role_btnBack").hide();
	jQuery("#role_btnNew").show();
}


var role_module=null;
var role_moduleActonList=null;
var moduleList=null;
var createEditRole = function (_name) {
  
	    jQuery("#role_btnNew").hide();    
	    jQuery("#role_btnSave").hide();
	    jQuery("#role_btnBack").hide();	   
	    jQuery("#roleTable").hide();
	    jQuery("#roleDetails").show();	    
	    jQuery("#roleDetailBody").empty();	    
	    jQuery("#roleModuleList").empty();	    
	    jQuery("#role_accordion").show();
    	jQuery("#role_accordion").accordion({fillSpace: true});
	    
    	
    	jQuery.ajax({
            url: "action/" + "?" + token,
            async:false,
            success: function (data) {
            	role_moduleActonList = data;
            	/*
            	jQuery("#RoleTemplateModuleAction").tmpl(data,

                        {
            				actionList=role_moduleActonList
                        }

                     ).appendTo("#moduleActionList");
            	*/
            }
        });
    	
    	jQuery.ajax({
            url: "module/" + "?" + token,
            async:false,
            success: function (data) {
            	moduleList = data;
            	jQuery("#roleModuleList").empty();
            	jQuery("#RoleTemplateModule").tmpl(null,

                        {
            				moduleList:data,
            				actionList:role_moduleActonList
            				
                        }

                     ).appendTo("#roleModuleList");
            	
            }
        });
    	
    	//hide action td
        jQuery.each(moduleList, function (i, module) {
         	    		 
    		 //jQuery("#TD_"+module.name).hide();    		 
         }); 
    	
    if (_name) {
    	
            jQuery.ajax({
            url: selectedItem+"/" + _name + "?" + token,
            async:false,
            success: function (data) {
            	
            	role_module=data.modules;	            	
            	
		    	jQuery("#RoleTemplateForm").tmpl(data,

                    {
			    		
                    }

                      ).appendTo("#roleDetailBody");
		    	
                jQuery('#name').attr('readonly', true);
                                               
                         	
                
                //set selected  module and hide Action TD
                
                jQuery.each(role_module, function (i, module) {
	
                	jQuery('[id=' + module.name + ']').attr('checked', true);
	                
                	jQuery.each(module.actions, function (j, action) {	                		                	
	                	jQuery('[id=' + module.name+'_'+action.name + ']').attr('checked', true);
	                });
                		
                	//jQuery("#TD_"+module.name).show();
					
                		
                    
                	});
                jQuery('#name').attr('readonly', true);
                
            },
            cache: false
        });
    } else {

        jQuery("#RoleTemplateForm").tmpl(null,

                {
        			
        		}	
              
            ).appendTo("#roleDetailBody");
        
        jQuery('#name').removeAttr('readonly');   
        
        
        
    }
     
    
    
    
    jQuery("#role_btnSave").show();
    jQuery("#role_btnBack").show();
    
    
} 



var saveRoleData= function () {
	
	jQuery.ajax({
        type: "POST",              
        url: "role/create" + "?" + token,
        data: jQuery("#roleForm").serialize(),
        success: function () {        
            
           
            jAlert('Data Successfully Saved', 'Role');
           //back to the list page 
            //var role=new Role('role'); 
            displayRefreshedRole();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
            alert('err.Message');
        }
    });
	
}




function saveRole(){
	
  //alert($(".roleCheckbox").filter(':checked').length);
    $("#roleForm").validate({

    	rules: {
    		name:"required",
    		description: "required"
    	},
    	messages: {
    		name: "Please enter Name",
    		description: "Please enter  Description"
    	}	
    			
    	});
  
    if ($("#roleForm").valid()) {
        // alert($(".roleCheckbox").filter(':checked').length);
        //saveRecord();

        if ($(".moduleCheckbox").filter(':checked').length > 0) {
        	saveRoleData();
        }
        else {
            if ($(".moduleCheckbox").filter(':checked').length <= 0) {
              
                jAlert('Select atleast one Module', 'Role');
            }
            
            return;
        }

    }


}

var deleteRole= function (_roleName) {
	
	
		
		jConfirm('Are You Sure You Want To Delete : <strong>' + _roleName + '</strong>', 'Delete Confirmation', function (response) {

	        if (response) {
	        	jQuery.ajax({          
	                url: "role/delete/"+_roleName + "?" + token,
	                success: function () { 	                	
	                	jAlert('Data Successfully Deleted', 'Role');
	                   //back to the list page 
	                	displayRefreshedRole();
	                    
	                },
	                error: function (XMLHttpRequest, textStatus, errorThrown) {
	                    
	                    alert('err.Message');
	                }
	            });
	        }

	    });

}



function toggleModule(_this) {
	
    /*
	if (_this.checked) {

    	jQuery("#TD_"+_this.id).show();
    }
    else {
    	
    	$("#TD_"+_this.id).find('input[type=checkbox]').removeAttr('checked');

    	jQuery("#TD_"+_this.id).hide();
    }
	*/
}
function toggleModuleChecked(status) {
	$(".moduleCheckbox").each( function() {
	$(this).attr("checked",status);
	})
}