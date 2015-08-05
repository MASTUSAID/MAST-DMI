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
function Bookmark(_selectedItem)
{
	
	selectedItem=_selectedItem;
	
	if( jQuery("#bookmarkFormDiv").length<=0){
		displayRefreshedBookmark();
	}
	else{
		
		displayBookmark();
	}
}


function displayRefreshedBookmark(){
	jQuery.ajax({
		url: "bookmark/" + "?" + token,
         success: function (data) {
        	jQuery("#tableGrid").empty(); 
        	jQuery("#bookmarks").empty(); 
			jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {
		    			    	
		    	jQuery("#bookmarks").append(template);
		    	jQuery('#bookmarkFormDiv').css("visibility", "visible");
		    	
		    	jQuery("#bookmarkDetails").hide();	        	
	        	jQuery("#BookmarksRowData").empty();
	        	jQuery("#bookmarkTable").show();	        		        			    
		    	
				jQuery("#bookmark_accordion").hide();
				
		    	jQuery("#BookmarkTemplate").tmpl(data).appendTo("#BookmarksRowData");
		    	 		    	
		    	jQuery("#bookmark_btnSave").hide();
		    	jQuery("#bookmark_btnBack").hide();
		    	jQuery("#bookmark_btnNew").show();		    			    
		    	
                $("#bookmarkTable").tablesorter({ 
                	headers: {7: {sorter: false  },  8: {  sorter: false } },	
                	debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                       .tablesorterPager({ container: $("#bookmark_pagerDiv"), positionFixed: false })
                       .tablesorterFilter({ filterContainer: $("#bookmark_txtSearch"),                           
                           filterColumns: [0],
                           filterCaseSensitive: false
                       });
		    	
			});
    
         }
	 });
	
}

function displayBookmark(){
	
	jQuery("#bookmark_accordion").hide();
	
	jQuery("#bookmarkDetails").hide();	        		
	jQuery("#bookmarkTable").show();
	
	jQuery("#bookmark_btnSave").hide();
	jQuery("#bookmark_btnBack").hide();
	jQuery("#bookmark_btnNew").show();	
	
}

var bookmark_projectList=null;

var createEditBookmark = function (_name) {
  
	    jQuery("#bookmark_btnNew").hide();    
	    jQuery("#bookmark_btnSave").hide();
	    jQuery("#bookmark_btnBack").hide();
	    
	    jQuery("#bookmarkTable").hide();
	    jQuery("#bookmarkDetails").show();    
	    jQuery("#bookmarkDetailsBody").empty();
	
	    
	    jQuery.ajax({
	        url: "project/" + "?" + token,
	        success: function (data) {
	        	bookmark_projectList = data;
	        },
	        async: false
	    });
	    
    if (_name) {

            jQuery.ajax({
            url: selectedItem+"/" + _name + "?" + token,
            async:false,
            success: function (data) {

                jQuery("#BookmarkTemplateForm").tmpl(data,

                            {
                	
                            }

                         ).appendTo("#bookmarkDetailsBody");
                
                jQuery('#name').attr('readonly', true);
                jQuery.each(bookmark_projectList, function (i, project) {    	
                	jQuery("#bookmark_project").append(jQuery("<option></option>").attr("value", project.name).text(project.name));        
                });
                
                jQuery('#bookmark_project').val(data.projectBean.name);
                
                
            },
            cache: false
        });
    } else {

        jQuery("#BookmarkTemplateForm").tmpl(null,

                {
        	
                }
            ).appendTo("#bookmarkDetailsBody");
        
        jQuery('#name').removeAttr('readonly');
        
        jQuery.each(bookmark_projectList, function (i, project) {    	
        	jQuery("#bookmark_project").append(jQuery("<option></option>").attr("value", project.name).text(project.name));        
        });
        
       
        
    }
    
    jQuery("#bookmark_accordion").show();
	jQuery("#bookmark_accordion").accordion({fillSpace: true});
    
    jQuery("#bookmark_btnSave").show();
    jQuery("#bookmark_btnBack").show();
    
    
} 

var saveBookmarkData= function () {
	
	jQuery.ajax({
        type: "POST",              
        url: "bookmark/create" + "?" + token,
        data: jQuery("#bookmarkForm").serialize(),
        success: function () {        
            
        	jAlert('Data Successfully Saved', 'Bookmark');
           //back to the list page 
           // var bookmark=new Bookmark('bookmark');
        	displayRefreshedBookmark();
            
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            
            alert('err.Message');
        }
    });
	
}




function saveBookmark(){
	
	jQuery("#bookmarkForm").validate({

		rules: {
			name:"required",
			description: "required",			
			minx: {
			required: true,
			number: true
			},
			miny: {
			required: true,
			number: true
			},
			maxx: {
			required: true,
			number: true
			},
			maxy: {
			required: true,
			number: true
			},
			"projectBean.name": "required"
			
		},
		messages: {
			name: "Please enter Name",
			description: "Please enter  Description",						
			minx: {
			required: "Please enter Minx",
			number: "Please enter a valid number.  "
			},
			
			miny: {
				required: "Please enter MinY",
				number: "Please enter a valid number.  "
			},
			maxx: {
				required: "Please enter Maxx",
				number: "Please enter a valid number.  "
			},
			maxy: {
				required: "Please enter Maxy",
				number: "Please enter a valid number.  "
			},
			"projectBean.name": "Please enter  Project"
		}		
			
	});
	
	if(jQuery("#bookmarkForm").valid())	{						
		saveBookmarkData();
	
	}
	
	
}
var deleteBookmark= function (_bookmarkName) {
	
	jConfirm('Are You Sure You Want To Delete : <strong>' + _bookmarkName + '</strong>', 'Delete Confirmation', function (response) {

        if (response) {
        	jQuery.ajax({          
                url: "bookmark/delete/"+_bookmarkName  + "?" + token,
                success: function () { 
                	
                	jAlert('Data Successfully Deleted', 'Bookmark');
                   //back to the list page 
                	displayRefreshedBookmark();
                    
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    
                    alert('err.Message');
                }
            });
        }

    });
	
	
	

	
}