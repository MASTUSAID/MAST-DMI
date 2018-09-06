var map;
Cloudburst.SaveProject = function(_map, _lyrManager) {
var userList=null;
	// /////////////
	map = _map
	searchdiv = _lyrManager;
	showResultsinDialog = true;

	// removeChildElement("sidebar", "layerSwitcherContent");
	$("#tabs-Tool").empty();
	
	$.ajax({
		
        url: STUDIO_URL + "user/"+ "?" + token,
        async:false,
        success: function (users) { 
        	userList=users;
        }
        });
	
	
	jQuery.get('resources/templates/viewer/saveproject.html', function(template) {

		// Add tad
		addTab('Save Project', template);
		
		$("#saveproject-help").tipTip({defaultPosition:"right"});
		
		$("#options-s-d").hide();
        $("#options-s-t").click(function () {
            $("#options-s-d").slideToggle('fast');
        });

		jQuery("#userTemplate").tmpl(userList,

		{
			useremail:user
		}

		).appendTo("#userListBody");

		$("#btnSaveproject").click(function() {
			saveUserProject();
		});

	});

}
// ///////////////////////

// Replace the code from here to a button click handler

function saveUserProject() {
	
	var saveProject = {};
	var newProjectName=$('#projectName').val();
	var newProjectDesc=$('#projectDescription').val();
	//$('#defaultProject')[0].checked 
	
	if(!newProjectName){
		jAlert("Enter Project Name");
		return;		
	}
	if(!newProjectDesc){
		jAlert("Enter Project Description");
		return;		
	}
	
	
	if(newProjectName && newProjectDesc){
		
		$.ajax({
		
        url: STUDIO_URL + "project/names/"+ "?" + token,
        async:false,
        success: function (projects) { 
   
			if($.inArray(newProjectName, projects) > -1) {
				jAlert("Project Alrady Exists");
				return;	
			}
			
			else{
			
				////////////
				
				var arUser = [];
				$.each($("input[name='users']:checked"), function() {
					arUser.push($(this).val());  
				});
				saveProject.users=arUser;
				
				saveProject.extent = map.getExtent().left + "," + map.getExtent().bottom
						+ "," + map.getExtent().right + "," + map.getExtent().top;
				saveProject.actualProjectName = project;
				saveProject.newProjectName = newProjectName;
				saveProject.newProjectDescription = newProjectDesc;
				saveProject.activelayer=OpenLayers.Map.activelayer.name;
				saveProject.owner=user;
				
				var arLayers = [];
				var _cnt = 0;
				for ( var lyr in layerMap) {
					arLayers[_cnt] = new Array(2);
					arLayers[_cnt][0] = lyr;
					arLayers[_cnt][1] = map.getLayersByName(lyr)[0].visibility;
					_cnt++;
				}
				saveProject.layerVisibility = arLayers;

				$.ajax({
					type : 'POST',
					url : STUDIO_URL + "project/save?" + token,
					data : JSON.stringify(saveProject),
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					success : function(data) {
						if (data) {
							closeDialog('saveprojectdiv');
							jAlert('Project successfully saved');
						} else {
							jAlert('Failed to save the project');
						}
					},
					error : function(xhr, status) {
						jAlert('Sorry, there is a problem!');
					}
				});
				
				////////
			
			}
	
        }
        });

			
	}

}
