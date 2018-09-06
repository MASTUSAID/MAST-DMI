var map;
Cloudburst.UserProjects = function(_map, _searchdid) {
var userList=null;
	
	map = _map
	searchdiv = _searchdid;
	$("#tabs-Tool").empty();
	
	var userId=loggedUser.id;
	
	$.ajax({

		url: STUDIO_URL + "project/Allproject/" +userId,
		success: function (userproject) {           

			jQuery.get('resources/templates/viewer/userprojects.html', function (template) {
				addTab('Projects',template);
				
				
				  jQuery.each(userproject, function (i, projects) {
					jQuery("#user_projectID").append(jQuery("<option></option>").attr("value", projects.userProject).text(projects.userProject));
				});

		      var _val=$("#ShowProjectNameID").text();
            jQuery("#user_projectID").val(_val);             
			
		
			});




		}
	});




}

function openProject(_projectName){
	qryString=lang;
	var _url=window.location.protocol+'//'+window.location.host+'/mast/viewer/?lang=en';
	if(qryString == "null" || qryString==""){
			qryString = "en";
		}
		var url = "?lang=" + qryString;
		if(_projectName){
			_url=_url+"&project="+_projectName;
		   document.location.href = _url;
		}else{
			return false;
		}
		
		
}