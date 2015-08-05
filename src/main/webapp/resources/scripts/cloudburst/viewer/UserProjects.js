var map;
//var wms_url="http://10.2.151.95:8080/geoserver/wms";
Cloudburst.UserProjects = function(_map, _searchdid) {
var userList=null;
	
	map = _map
	searchdiv = _searchdid;
	$("#tabs-Tool").empty();
	
	var userProjects=loggedUser.projects;
	
		
	jQuery.get('resources/templates/viewer/userprojects.html', function (template) {
		addTab('Projects',template);
        jQuery("#userProjectsBody").empty();

        jQuery("#userProjectsTemplate").tmpl(null,

        {
            userProjecList: userProjects
        }

        ).appendTo("#userProjectsBody");
		
		 setProjectImg(userProjects);
        
    });
}





function openProject(_projectName){
	
	qryString=lang;
	if(qryString == "null" || qryString==""){
			qryString = "en";
		}
		var url = "?lang=" + qryString;
		if(_projectName){
			url=url+"&project="+_projectName;
		}
		document.location.href = url;
		
		
}


function setProjectImg(_projects) {
	//var remote_url="http://10.2.151.95:8080/geoserver/wms";
	for (var i = 0; i < _projects.length; i++) {
		var active_layer=_projects[i].activelayer;
		var lyr_url;
		var lyr_srs;
		var lyr_name;
		var lyr_bbox;
		$.ajax({
			url: STUDIO_URL + "layer/" + active_layer + "?" + token,
			async: false,
			success: function (lyrData) {
				lyr_name=lyrData.name;
				lyr_srs= lyrData.projectionBean.code;
				lyr_url=lyrData.url;
				lyr_bbox=lyrData.maxextent;
				
				//var extent=map.getLayersByName('ShoppingCenters')[0].getExtent();
				//var bbox=extent.left+','+extent.bottom+','+extent.right+','+extent.top;
				var wmsurl = lyr_url + "REQUEST=GetMap&SRS=" + lyr_srs + "&STYLES=&FORMAT=image/png&SERVICE=WMS&VERSION=1.1.1&TRANSPARENT=TRUE&width=64&height=64";

				var imgurl = wmsurl + '&BBOX=' + lyr_bbox + '&LAYERS=' + lyr_name;
		
				$("#" + _projects[i].name + "_img").attr('src', imgurl);
			
			}
			});
		
		
		
		//var layerSrs= lyrData.projectionBean.code;	//_projects[i].projection.code;
		//var extent=map.getLayersByName('ShoppingCenters')[0].getExtent();
		//var bbox=extent.left+','+extent.bottom+','+extent.right+','+extent.top;
		//alert("SRS: "+ layerSrs +"Act Lyr:"+ active_layer+"BBOX"+  bbox);	
		
		
	
	}
    
	
	
    

}




/*function setProjectImg(_projects) {
	var remote_url="http://10.2.151.95:8080/geoserver/wms";
	for (var i = 0; i < _projects.length; i++) {
	
		var active_layer=_projects[i].activelayer;
		
		var layerSrs= _projects[i].projection.code;
		var extent=map.getLayersByName('ShoppingCenters')[0].getExtent();
		var bbox=extent.left+','+extent.bottom+','+extent.right+','+extent.top;
		alert("SRS: "+ layerSrs +"Act Lyr:"+ active_layer+"BBOX"+  bbox);	
		
		var wmsurl = remote_url + "?REQUEST=GetMap&SRS=" + layerSrs + "&STYLES=&FORMAT=image/png&SERVICE=WMS&VERSION=1.1.1&TRANSPARENT=TRUE&width=64&height=64";

		var imgurl = wmsurl + '&BBOX=' + bbox + '&LAYERS=' + layerMap[active_layer];
		
		$("#" + _projects[i].name + "_img").attr('src', imgurl);
	
	}
    
	
	
    

}*/


