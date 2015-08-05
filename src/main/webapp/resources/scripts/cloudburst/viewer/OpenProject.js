var map;
Cloudburst.OpenProject = function(_map, _searchdid) {
var userList=null;
	
	map = _map
	searchdiv = _searchdid;
	showResultsinDialog = true;

	$("#tabs-Tool").empty();
	var ownerProjects=null;
	var userProjects=null;
	
	$.ajax({
        	type: "POST",	
            url: STUDIO_URL + "ownerproject/"+ "?" + token,
            data: {email:user},
            async:false,
			success: function (_ownerProjects) {                           
            ownerProjects = _ownerProjects;

            }

        });
		
		$.ajax({
        	type: "POST",			
            url: STUDIO_URL + "user/email/"+ "?" + token,
            data: {emailid:user},
            async:false,
			success: function (_userProjects) {           
                
            userProjects = _userProjects.projects;

            }

        });
		
		
		jQuery.get('resources/templates/viewer/openproject.html', function(template) {
            		            		
            		addTab('My Projects', template);
            		
            		$("#openproject-help").tipTip({defaultPosition:"right"});
					
					 jQuery('#imgSearch').hide();
					
					jQuery('#txtSearch').keyup(function() {

						// only search when there are 3 or more characters in the textbox

						if (jQuery('#txtSearch').val().length > 1) {

							// hide all rows

							//jQuery('#ownerProjectBody table').hide();
							//jQuery('#userProjectBody table').hide();
							jQuery('.openProjectSearch').hide();	
									
							
							//$("#ownerProjectBody table .pojName:containsNoCase('"+jQuery('#txtSearch').val()+"')").show();
							//$("#userProjectBody table .projName:containsNoCase('"+jQuery('#txtSearch').val()+"')").show()
							
							$("#ownerProjectBody table .pojName:containsNoCase('"+jQuery('#txtSearch').val()+"')").parent().parent().parent().parent().show();
							$("#userProjectBody table .pojName:containsNoCase('"+jQuery('#txtSearch').val()+"')").parent().parent().parent().parent().show();
							// show the cancel search image

							jQuery('#imgSearch').show();

						}

						else if (jQuery('#txtSearch').val().length == 0) {

							// if the user removed all of the text, reset the search

							resetSearch();

						}
					
					});
					// cancel the search if the user presses the ESC key
					jQuery('#txtSearch').keyup(function(event) {

						if (event.keyCode == 27) {

							resetSearch();

						}

					});
					
					 // reset the search when the cancel image is clicked
					jQuery('#imgSearch').click(function() {

						resetSearch();

					});
					
            		
            		jQuery("#ownerProjectBody").empty();
					jQuery("#userProjectBody").empty();

            		jQuery("#userProjectTemplate").tmpl(null,

            		{
            			projectsList : ownerProjects,
						useremail:user,
						type:'owner'
            		}

            		).appendTo("#ownerProjectBody");
					
					
					jQuery("#userProjectTemplate").tmpl(null,

            		{
            			projectsList : userProjects,
						useremail:user,
						type:'user'
            		}

            		).appendTo("#userProjectBody");
            		
            		
            	  });
}

function openProject(_projectName){
	var url;
	if(token == "null"){
		url=serverPath+'?project='+_projectName;
	}else{
		if(token.indexOf("&") == -1){
			url=serverPath+'?project='+_projectName;
		}else{
			if(token.indexOf("project") == -1 && token.length > 0){
				url=serverPath+'?'+token+'&project='+_projectName;
			}else if(token.indexOf("project") != -1){
				pos = token.indexOf("project");
				token = token.substring(pos);
				url=serverPath+'?'+token+'&project='+_projectName;
			}
			
		}
	}
	location.href=url;
}


function deleteUserProject(_projectName){


	
	jConfirm('Are You Sure You Want To Delete : <strong>' + _projectName + '</strong>', 'Delete Confirmation', function (response) {

        if (response) {
        	jQuery.ajax({          
                url: STUDIO_URL + "project/delete/"+_projectName + "?" + token,
                success: function () { 
                	
                	$("#table-"+_projectName).remove();
                	jAlert('Data Successfully Deleted', 'Project');
					
					//var openProject = new Cloudburst.OpenProject(map,"sidebar");
                   
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    
                    alert('Unable to delete the project');
                }
            });
        }

    });
	



}


function resetSearch() {

    // clear the textbox

    jQuery('#txtSearch').val('');

    // show all table rows

	//jQuery('#ownerProjectBody table').show();
	//jQuery('#userProjectBody table').show();    
	jQuery('.openProjectSearch').show();	
	
	// remove the cancel search image

	jQuery('#imgSearch').hide();
	
    jQuery('#txtSearch').focus();

}


$.expr[":"].containsNoCase = function(el, i, m) {

	var search = m[3];

	if (!search) return false;

	return eval("/" + search + "/i").test($(el).text());

};