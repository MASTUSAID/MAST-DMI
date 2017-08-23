

var featureSelection;
var cloneLayer = null;
var symURL = "http://localhost:8080/";
var rePost = "";
var g_wfs = null;
var current_date = null;

var lkp_Access_Land_type={};

var lkp_Row_Path_type={};
var lkp_Row_Path_class={};
var lkp_Row_Path_dept={};
var lkp_Row_Path_condition={};
var lkp_Row_Path_status={};
var lkp_Row_Path_surveyor={};

var lkp_Furniture_type={};
var lkp_Furniture_condition={};
var lkp_Furniture_surveyor={};

var lkp_Issue_issuetype={};		
var lkp_Issue_actionstatus={};	
var lkp_Issue_urgency={};		
var lkp_Issue_responsibility={};			
var lkp_Issue_why={};	
var lkp_Issue_assignto={};	

var lkp_Surface_type={};
var lkp_Surface_condition={};
var lkp_Surface_surveyor={};

var lkp_Complaint_enquiryType = {};
var lkp_Complaint_complainant = {};
var lkp_Complaint_contacts = {};
var complaintEnquiryTypes;
var complainants;
var contacts;
var access_land_sld;

var selectionSymbolizer = {
	    'Polygon': {fillColor: '#FFFFFF', fillOpacity:0.1, stroke: true, strokeColor:'#07FCFB', strokeWidth: 2},
	    'Line': {strokeColor: '#07FCFB', strokeWidth: 2},
	    'Point': {graphicName: 'circle', fillOpacity:0,stroke: true,strokeColor:'#07FCFB', pointRadius: 5,strokeWidth: 2}
	};

function isNumeric(strString)
//  check for valid numeric strings	
{
    var strValidChars = "0123456789";
    var strChar;
    var blnResult = true;

    if (strString.length == 0) return false;

    //  test strString consists of valid characters listed above
    for (i = 0; i < strString.length && blnResult == true; i++) {
        strChar = strString.charAt(i);
        if (strValidChars.indexOf(strChar) == -1) {
            blnResult = false;
        }
    }
    return blnResult;
}


function trim(stringToTrim) {
    return stringToTrim.replace(/^\s+|\s+$/g, "");
}
function ltrim(stringToTrim) {
    return stringToTrim.replace(/^\s+/, "");
}
function rtrim(stringToTrim) {
    return stringToTrim.replace(/\s+$/, "");
}

function sortAttributeValues(arr) {
    var r = new Array();
    o: for (var i = 0, n = arr.length; i < n; i++) {
        for (var x = 0, y = r.length; x < y; x++) {
            if (r[x] == arr[i]) {
                continue o;
            }
        }
        if(arr[i] != undefined){
        	r[r.length] = arr[i];
        }
    }
   
    if (!isNumeric(r[0])) {
        return r.sort();
    } else {
        return r.sort(function (a, b) { return a - b })
    }
}


function replaceString(mainString , regExp, replaceBy) {

   // wfsUrl = _wmsUrl.replace(regExp, 'wfs');

    var result = mainString.replace(regExp, replaceBy);
    return result;
}


function clearAllSelections() {

    /*if (currentControl) {
        var layerCache = mapControls[currentControl].layerCache;
        if (layerCache) {
            var selectionLayer = layerCache[activeLayer.id];
            if (selectionLayer) {
                selectionLayer.destroy();
            }
            delete layerCache[activeLayer.id];
        }
    }*/
	
	
	var __activelayer=OpenLayers.Map.activelayer;
	
	
	delete __activelayer.params["SLD"];
	__activelayer.redraw(true);
    refreshLegends(); 	

    if (cloneLayer != null) {
        map.removeLayer(cloneLayer, false);
        cloneLayer = null;
    }
    if (vectors != null) {
        map.removeLayer(vectors, false);
        vectors = null;
    }

//    if (vectorLayer.features.length > 0) {
//        vectorLayer.removeAllFeatures();
//    }

    featureSelection = null; /*Clears the selection filter*/
    //refreshLegend();
}


function createSpatialQryXML(){
	dataPost = "<wfs:GetFeature service=\"WFS\" version=\"1.1.0\" " +
    "xmlns:gml=\"http://www.opengis.net/gml\" " +
    "xmlns:wfs=\"http://www.opengis.net/wfs\" " +
    "xmlns:ogc=\"http://www.opengis.net/ogc\" " +
    "xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\" " +
    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +

     "<wfs:Query typeName=${layer} xmlns:feature=${featureNS}> ${filter}" +
        "<ogc:SortBy><ogc:SortProperty><ogc:PropertyName>${uniqueFld}</ogc:PropertyName><ogc:SortOrder>${SortOrder}</ogc:SortOrder></ogc:SortProperty></ogc:SortBy>" +
 "</wfs:Query>" +
"</wfs:GetFeature>";
}

function createXML() {
	var featureCount = 0;
	
    dataPost = "<wfs:GetFeature service=\"WFS\" version=\"1.1.0\" maxFeatures =\"" + maxFeatureCount + "\" " +
        "xmlns:gml=\"http://www.opengis.net/gml\" " +
        "xmlns:wfs=\"http://www.opengis.net/wfs\" " +
        "xmlns:ogc=\"http://www.opengis.net/ogc\" " +
        "xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\" " +
        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +

         "<wfs:Query typeName=${layer} xmlns:feature=${featureNS}> ${filter}" +
            "<ogc:SortBy><ogc:SortProperty><ogc:PropertyName>${uniqueFld}</ogc:PropertyName><ogc:SortOrder>${SortOrder}</ogc:SortOrder></ogc:SortProperty></ogc:SortBy>" +
     "</wfs:Query>" +
"</wfs:GetFeature>";

}

function createThematicXML() {
	var featureCount = 0;
	
    dataPost = "<wfs:GetFeature service=\"WFS\" version=\"1.1.0\"  " +
        "xmlns:gml=\"http://www.opengis.net/gml\" " +
        "xmlns:wfs=\"http://www.opengis.net/wfs\" " +
        "xmlns:ogc=\"http://www.opengis.net/ogc\" " +
        "xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\" " +
        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +

         "<wfs:Query typeName=${layer} xmlns:feature=${featureNS}> ${filter}" +
            "<ogc:SortBy><ogc:SortProperty><ogc:PropertyName>${uniqueFld}</ogc:PropertyName><ogc:SortOrder>${SortOrder}</ogc:SortOrder></ogc:SortProperty></ogc:SortBy>" +
     "</wfs:Query>" +
"</wfs:GetFeature>";
    return dataPost;

}

function createQueryRequest(){
	var rePost =  "<wfs:GetFeature service=\"WFS\" version=\"1.1.0\" maxFeatures =\"" + maxFeatureCount + "\" " +
    "xmlns:gml=\"http://www.opengis.net/gml\" " +
    "xmlns:wfs=\"http://www.opengis.net/wfs\" " +
    "xmlns:ogc=\"http://www.opengis.net/ogc\" " +
    "xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\" " +
    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +

     "<wfs:Query typeName=${layer} xmlns:feature=${featureNS}> ${filter}" +
 "</wfs:Query>" +
"</wfs:GetFeature>";
	return rePost;
}

function relateXML() {

    dataPost = "<wfs:GetFeature service=\"WFS\" version=\"1.1.0\" maxFeatures =\"" + maxFeatureCount + "\" " +
        "xmlns:gml=\"http://www.opengis.net/gml\" " +
        "xmlns:wfs=\"http://www.opengis.net/wfs\" " +
        "xmlns:ogc=\"http://www.opengis.net/ogc\" " +
        "xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd\" " +
        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +

         "<wfs:Query typeName=${relatelayer} xmlns:feature=${featureNS}>" +
           "<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\">"
         			//+ "<ogc:AND>"
         			 + "<ogc:BBOX>" 
					 	+ "<ogc:PropertyName>_geom</ogc:PropertyName>"
					 	+ "<gml:Envelope srsName=\"${projection}\">"
					 		+ "<gml:lowerCorner>${lowerCorner}</gml:lowerCorner>"
					 		+ "<gml:upperCorner>${upperCorner}</gml:upperCorner>"
					 	+ "</gml:Envelope>"
					 + "</ogc:BBOX>"
					 + "<ogc:AND>"
					+ "<ogc:DWithin>"
					   + "<ogc:PropertyName>_geom</ogc:PropertyName>"
					   + "<ogc:Function name=\"collectGeometries\">"
						 + "<ogc:Function name=\"queryCollection\">"
						   + "<ogc:Literal>${layer}</ogc:Literal>"
						   + "<ogc:Literal>_geom</ogc:Literal>"
						   //+ "<ogc:Literal>INCLUDE</ogc:Literal>"
						   + "<ogc:Literal>${cql}</ogc:Literal>"
						 + "</ogc:Function>"
					   + "</ogc:Function>"
					   + "<ogc:Distance units=\"meter\">${distance}</ogc:Distance>"
					 + "</ogc:DWithin>"
					 + "</ogc:AND>"
		+ "</ogc:Filter>"
     + "</wfs:Query>"
+ "</wfs:GetFeature>";

}

function removeChildElement(_parentDiv,excludedDiv){
var len=$("#"+_parentDiv).children().length;
for(i=0;i<len;i++){
	var divid=$("#"+_parentDiv).children()[i].id;
	if(divid!=excludedDiv){
		$("#"+divid).remove();
	}
}

}

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function getUrlVar (name){
    return getUrlVars()[name];
}

function toggleButtons() {
		$(".fg-button:not(.ui-state-disabled)")
				.mousedown(
						function() {
							$(this).parents(
									'.fg-buttonset-single:first').find(
									".fg-button.ui-state-active1")
									.removeClass("ui-state-active1");
							if ($(this)
									.is(
											'.ui-state-active1.fg-button-toggleable, .fg-buttonset-multi .ui-state-active1')) {
								$(this).removeClass("ui-state-active1");
							} else {
								$(this).addClass("ui-state-active1");
							}
						})
				.mouseup(
						function() {
							if (!$(this)
									.is(
											'.fg-button-toggleable, .fg-buttonset-single .fg-button,  .fg-buttonset-multi .fg-button')) {
								$(this).removeClass("ui-state-active1");
							}
						});
	
}

function closeDialog(dialog){

	$("#"+dialog).remove();
	$("#layerSwitcherContent").show();
	$("#sidebar").tabs( "remove" , 1 );
	
	//remove feature and deactive the markup tool when close tab 
		removeDeactiveMarkupTool();
		
		if(dialog == "searchdiv"){
			removeBoundExtentFeature();
		}
	
}

function clearSelection(clearResultGrid, _lyr){
	selFeatureBbox=null;
	OpenLayers.Map.activelayer.selectFilter=null;
	var sel_clonedLayer = map.getLayersByName("clone")[0];
	var sel_vectorLayer = map.getLayersByName("vector")[0];
	if(sel_clonedLayer != undefined){
		map.removeLayer(sel_clonedLayer);
	}
	
	if(sel_vectorLayer != undefined){
		map.removeLayer(sel_vectorLayer);
	}
	
	
	var __activelayer=OpenLayers.Map.activelayer;
	
	if(clearResultGrid){
		delete __activelayer.params["SLD"];
			__activelayer.redraw(true);
			refreshLegends();	
	}
	/*if(_lyr != undefined && _lyr.name == 'Access_Land'){
		 //var al_layer = map.getLayersByName(_lyr)[0];
		   var al_theme = new AccessLand_Theme(_lyr);
		   var ap_filter = al_theme.createDisplayCriteria();
		   al_theme.applySLD(ap_filter);
	}*/
	
	var labelCloneLayer = map.getLayersByName(__activelayer.name + "_Clone")[0];	
	
	if(labelCloneLayer != undefined){
		map.removeLayer(labelCloneLayer);
	}
	
	 if(markers){
		 markers.clearMarkers();
	 }
	 
	 //Clear the result panel.
	 if(clearResultGrid){
		   var recordCount = $("#tablegrid1").jqGrid('getGridParam', 'records');
		   if(recordCount > 0){
			   //$("#tablegrid1").jqGrid().clearGridData();
			   //$("#grid").remove();
			   $("#bottombar").empty();
			   $("#bottombar").hide();
				$("#bottomcollapse").css("bottom", "0px");
				$("#bottomcollapse").removeClass("bottom_collapse_down");
				$("#bottomcollapse").addClass("bottom_collapse");
		   }
	 }
	 
	 var searchVector = map.getLayersByName("selected_vector")[0];
		if(searchVector != undefined){
			removeBoundExtentFeature();
		}
		
}

function addTab(tool,_template){	
	$("#sidebar").tabs( "remove" , 1 );
	$("#sidebar").tabs( "add" , '#tabs-Tool' , tool , [1] );			
	$("#tabs-Tool").append(_template);        		
	$("#sidebar").tabs( "select" , 1 );
}


function removeDeactiveMarkupTool(){
	//remove feature
	if(wfs_markup_point) wfs_markup_point.removeAllFeatures();
    if(wfs_markup_line) wfs_markup_line.removeAllFeatures();
    if(wfs_markup_poly) wfs_markup_poly.removeAllFeatures();    
	//Deactive markup tool
	
	//toggleMarkupControl('');
	
	/*Deactivate Edit Controls*/
	for (editKey in editControls) {
		var control = editControls[editKey];
		control.deactivate();
	}
	/*Deactivate Markup Controls*/	
	for (key in markupControls) {
        var control = markupControls[key];      
        control.deactivate();
    }
	
	/*Deactivate Export Controls*/	
	for (exportkey in exportRegionControls) {
        var control = exportRegionControls[exportkey];        
            control.deactivate();
	}
	/*Deactivate measure Controls*/	
	for ( var key in mapControls) {
		if(key=='measurelength'|| key=='measurearea'){
			var control = mapControls[key];		
			control.deactivate();
		}
	
	}
	
	/* Deactive editControls_issue controls of issue
	for (key_issue in editControls_issue) {
		var control = editControls_issue[key_issue];
			control.deactivate();
	}	
	*/
}



/* Retrieve the default language set for the browser. */
var defaultLanguage = normaliseLang(window.navigator.userLanguage || window.navigator.language);

/* Ensure language code is in the format aa-AA. */
function normaliseLang(lang) {
	lang = lang.replace(/_/, '-').toLowerCase();
	if (lang.length > 3) {
		lang = lang.substring(0, 3) + lang.substring(3).toUpperCase();
	}
	return lang;
}

function addListingTab(tab_title,_tabid,_html){

	
	
	var $tabs = $( "#tab").tabs({
		tabTemplate: "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close'>Remove Tab</span></li>",
		add: function( event, ui ) {
			//var tab_content = $tab_content_input.val() || "Tab " + tab_counter + " content.";
			$( ui.panel ).append(_html);
			$tabs.tabs('select', '#' + ui.panel.id);
			//$('#'+_tabid).append(_html);
		}
	});
	
	if($("a[href^=#"+_tabid+"]").length > 0)
	    {	        
			//$("#tab").tabs( "select" , 1 );
			$("#"+_tabid).empty();
			$("#"+_tabid).append(_html);
			$tabs.tabs('select', '#' + _tabid);
			
	    } else {
		
		$tabs.tabs( "add", "#"+_tabid, tab_title);
		//tab_counter++;
		
		}
	
	

}

function getHolidayList(){
var hilidayList=null; 
jQuery.ajax({
	async:false,
	url: STUDIO_URL + "annualholiday/",
    success: function (data) {
    	 hilidayList=data;
     }
});
return hilidayList;
}


//var finaldate=businessDays(nomdays,date);  
var _hilidayList=null;

function businessDays(_nomdays,txtdate,D){
	_hilidayList=getHolidayList();
	
	var num=Math.abs(_nomdays);
	var tem,count=0;
	var dir= (_nomdays<0)? -1: 1;
	txtdate = txtdate.split('-');
	D= D || new Date(txtdate[0],txtdate[1]-1,txtdate[2]);
	while(count< num){
		D= new Date(D.setDate(D.getDate()+dir));
		if(isHoliday(D))continue;
		tem=D.getDay();
		if(tem!=0 && tem!=6) ++count;
	}
	
	Date.prototype.toDDMMYYYYString = function () {return isNaN (D) ? 'NaN' : [D.getFullYear(), D.getMonth() > 8 ? D.getMonth() + 1 : '0' + (D.getMonth() + 1),D.getDate() > 9 ? D.getDate() : '0' + D.getDate()].join('-')}

	return new Date().toDDMMYYYYString();
 
function isHoliday(_D){
			
				for (i = 0; i < _hilidayList.length; i++) {	
				var hday=_hilidayList[i];
				var str="this is my new tutorial";
				var dateArr = new Array();
				dateArr=(hday.holidayDate).split("-");
				if ((_D.getFullYear() == dateArr[0]
				  && _D.getMonth() == dateArr[1] - 1
				  && _D.getDate() == dateArr[2])
				 // ||(date.getDay()==0)
				  ) {							
						return true;
				}

			}
			
return false;

}
 
 
 }
/*
function populateLayerLookups(_layer){
	
	if(_layer=='Access_Land'){
		if(accesslandTypes==null){
			jQuery.ajax({
			async: false,
			type: "GET",
			url: STUDIO_URL + "accessland/type",
			success: function (data) {
				accesslandTypes = data

			}
			});
		}
	jQuery.each(
		accesslandTypes, function (i, accesslandType) {
		lkp_Access_Land_type[accesslandType.typeid] = (lang=='en')?accesslandType.type:accesslandType.math;				
	});
	//load AL LKP	lkp_Access_Land_type
	
	}
	else if(_layer=='RoW_Path'){
		
		if(_pathtype==null){
		jQuery.ajax({
	       async:false,
	    	type: "GET",              
	        url: STUDIO_URL + "path/type",        		               
	        success: function (data) {
	        	_pathtype=data
	        	
	        }
	    });
		}


		if(_pathclassLkp==null){
			 jQuery.ajax({
				   async:false,
					type: "GET",              
					url: STUDIO_URL + "path/classLkp/",        		               
					success: function (data) {
						_pathclassLkp=data
						
					}
				});
		}
	  				
		if(_pathdeptLkp==null){
			jQuery.ajax({
				   async:false,
					type: "GET",              
					url: STUDIO_URL + "path/dept/",        		               
					success: function (data) {
						_pathdeptLkp=data
						
					}
				});
		}
		
		if(_pathcondition==null){
			jQuery.ajax({
			   async:false,
				type: "GET",              
				url: STUDIO_URL + "path/condition",        		               
				success: function (data) {
					_pathcondition=data
					
				}
			});
		}
					
	if(_pathlegalstatus==null){  
		jQuery.ajax({
			   async:false,
				type: "GET",              
				url: STUDIO_URL + "path/legalstatus/",        		               
				success: function (data) {
					_pathlegalstatus=data
					
				}
			});	
	}
	if(patheSurveyor==null){  
		jQuery.ajax({
		async:false,
		type: "GET",              
		 url: STUDIO_URL + "furniture/surveyor",        		               
		 success: function (data) {
			 patheSurveyor=data
			
		 }
		});
	}
	

	jQuery.each(
		_pathtype, function (i, pathtype) {
		lkp_Row_Path_type[pathtype.pathTypeId] = (lang=='en')?pathtype.type:pathtype.math;				
	});
	jQuery.each(
		_pathclassLkp, function (i, pathclassLkp) {
		lkp_Row_Path_class[pathclassLkp.id] = pathclassLkp.priority;				
	});
	jQuery.each(
		_pathdeptLkp, function (i, pathdeptLkp) {
		lkp_Row_Path_dept[pathdeptLkp.departmentid] = (lang=='en')?pathdeptLkp.department:pathdeptLkp.adran;				
	});
	jQuery.each(
		_pathcondition, function (i, pathcondition) {
		lkp_Row_Path_condition[pathcondition.conditionid] = (lang=='en')?pathcondition.condition:pathcondition.cyflwr;				
	});
	jQuery.each(
		_pathlegalstatus, function (i, pathlegalstatus) {
		lkp_Row_Path_status[pathlegalstatus.legalstatusid] = (lang=='en')?pathlegalstatus.status:pathlegalstatus.statws;				
	});
	jQuery.each(
		patheSurveyor, function (i, surveyor) {
		lkp_Row_Path_surveyor[surveyor.id] = surveyor.name;				
	});
	
	
}
else if(_layer=='Furniture'){


		if(furnitureTypes==null){
			jQuery.ajax({
			async: false,
			type: "GET",
			url: STUDIO_URL + "furniture/type/" + lang,
			success: function (data) {
				furnitureTypes = data

			}
			});
		}
		if(furnitureCondition==null){
			jQuery.ajax({
				async: false,
				type: "GET",
				url: STUDIO_URL + "furniture/condition",
				success: function (data) {
					furnitureCondition = data

				}
			});
		}
		if(furnitureSurveyor==null){
			jQuery.ajax({
				async: false,
				type: "GET",
				url: STUDIO_URL + "furniture/surveyor",
				success: function (data) {
					furnitureSurveyor = data

				}
			});
		}
		
		jQuery.each(
			furnitureTypes, function (i, furnitureType) {
			lkp_Furniture_type[furnitureType.typeid] = (lang=='en')?furnitureType.type:furnitureType.math;				
		});
		
		jQuery.each(
			furnitureCondition, function (i, _furnitureCondition) {
			lkp_Furniture_condition[_furnitureCondition.conditionid] = _furnitureCondition.condition;				
		});
		
		jQuery.each(
			furnitureSurveyor, function (i, _furnitureSurveyor) {
			lkp_Furniture_surveyor[_furnitureSurveyor.id] = _furnitureSurveyor.name;				
		});
	
}

else if(_layer=='Issue'){


		if(issueTypes==null){
			jQuery.ajax({
			async:false,
			type: "GET",              
			 url: STUDIO_URL + "issue/type/lang/"+lang,        		               
			 success: function (data) {
				 issueTypes=data;
				
			 }
			});
		}
		if(issueStatusList==null){
			jQuery.ajax({
			async:false,
			type: "GET",              
			 url: STUDIO_URL + "issue/status",        		               
			 success: function (data) {
				 issueStatusList=data;
				
			 }
		 });
		}
		if(issueurgencyList==null){
			jQuery.ajax({
			async:false,
			type: "GET",              
			 url: STUDIO_URL + "issue/urgency",        		               
			 success: function (data) {
				 issueurgencyList=data;
				
			 }
		 });
		}
		if(issueResponsibleDeptList==null){
			jQuery.ajax({
			async:false,
			type: "GET",              
			 url: STUDIO_URL + "issue/responsibledept",        		               
			 success: function (data) {
				 issueResponsibleDeptList=data;
				
			 }
		 });

		}
		if(issueReasonList==null){
			jQuery.ajax({
			async:false,
			type: "GET",              
			 url: STUDIO_URL + "issue/reason",        		               
			 success: function (data) {
				 issueReasonList=data;
				
			 }
		 });
		}
		if(issueUserList==null){
			jQuery.ajax({
				async: false,
				type: "GET",
				url: STUDIO_URL + "user/",
				success: function (data) {
					issueUserList = data

				}
			});
		}
		
		
		jQuery.each(
			issueTypes, function (i, _issueTypes) {
			lkp_Issue_issuetype[_issueTypes.issuetypeid] = (lang=='en')?_issueTypes.type:_issueTypes.math;				
		});
		
		jQuery.each(
			issueStatusList, function (i, _issueStatusList) {
			lkp_Issue_actionstatus[_issueStatusList.actionstatusid] = (lang=='en')?_issueStatusList.actionStatus:_issueStatusList.statws;				
		});
		
		jQuery.each(
			issueurgencyList, function (i, _issueurgencyList) {
			lkp_Issue_urgency[_issueurgencyList.urgencyid] = (lang=='en')?_issueurgencyList.urgencyType:_issueurgencyList.brys;				
		});

		jQuery.each(
			issueResponsibleDeptList, function (i, _issueResponsibleDeptList) {
			lkp_Issue_responsibility[_issueResponsibleDeptList.departmentid] = (lang=='en')?_issueResponsibleDeptList.department:_issueResponsibleDeptList.adran;				
		});
		
		jQuery.each(
			issueReasonList, function (i, _issueReasonList) {
			lkp_Issue_why[_issueReasonList.reasonid] = (lang=='en')?_issueReasonList.reason:_issueReasonList.math;				
		});
		
		jQuery.each(
			issueUserList, function (i, _issueUserList) {
			lkp_Issue_assignto[_issueUserList.id] = _issueUserList.name;	
	
		});
	
}
else if(_layer=='Surface'){


	if(surfaceTypes==null){
		jQuery.ajax({
		async: false,
		type: "GET",
		url: STUDIO_URL + "surface/type",
		success: function (data) {
			surfaceTypes = data

		}
		});
	}
	if(surfaceCondition==null){
		jQuery.ajax({
			async: false,
			type: "GET",
			url: STUDIO_URL + "surface/condition",
			success: function (data) {
				surfaceCondition = data

			}
		});
	}
	if(surfaceSurveyor==null){
		jQuery.ajax({
			async: false,
			type: "GET",
			url: STUDIO_URL + "surface/surveyor",
			success: function (data) {
				surfaceSurveyor = data

			}
		});
	}
	
	jQuery.each(
			surfaceTypes, function (i, surfaceType) {
		lkp_Surface_type[surfaceType.typeid] = surfaceType.type;				
	});
	
	jQuery.each(
			surfaceCondition, function (i, _surfaceCondition) {
		lkp_Surface_condition[_surfaceCondition.conditionid] = _surfaceCondition.condition;				
	});
	
	jQuery.each(
		surfaceSurveyor, function (i, _surfaceSurveyor) {
		lkp_Surface_surveyor[_surfaceSurveyor.id] = _surfaceSurveyor.name;				
	});

	}else if(_layer == 'Complaint_Layer'){
		if(complaintEnquiryTypes==null){
			jQuery.ajax({
			async: false,
			type: "GET",
			url: "/spatialvue/viewer/complaint/enquiryTypes",
			success: function (data) {
				complaintEnquiryTypes = data
			}
			});
		}
		jQuery.each(
				complaintEnquiryTypes, function (i, complaintEnquiryType) {
					lkp_Complaint_enquiryType[complaintEnquiryType.enquiryid] = complaintEnquiryType.enquiryType;				
		});
		
		jQuery.ajax({
			async: false,
			type: "GET",
			url: "/spatialvue/viewer/complaint/complainants",
			success: function (data) {
				complainants = data
			}
		});
		jQuery.each(
				complainants, function (i, complainant) {
					lkp_Complaint_complainant[complainant.complainantid] 
						= complainant.email;				
			});
		
		jQuery.ajax({
			async: false,
			type: "GET",
			url: "/spatialvue/viewer/listcontact",
			success: function (data) {
				contacts = data
			}
		});
		jQuery.each(
				contacts, function (i, contact) {
					lkp_Complaint_contacts[contact.contactid] 
						= contact.email;				
			});
		
	}
}*/

function convertDateToEuropeanFormat(dateString){
	if(dateString != undefined && dateString != null && dateString != ""){
		if(jQuery.browser.msie){
			dateString = dateString.replace(/-/g, "/");
			var dt = new Date(dateString);
			month = dt.getMonth() + 1;
				
			var yr_length = dt.getYear().toString().length;
			if(yr_length < 4){//This case is for IE9.0
				return $.datepicker.formatDate('dd/mm/yy', new Date(dateString));
			}else{
				var date = ((dt.getDate()<10)?"0"+dt.getDate():dt.getDate()) + '/' + ((month<10)?"0"+month:month)  + '/' + dt.getYear();
				return date;
			}
		}else{
			return $.datepicker.formatDate('dd/mm/yy', new Date(dateString));
		}
	}else{
		return "";
	}
}

function convertDateToUSFormat(dateString){
	if(dateString != undefined && dateString != null && dateString != ""){
		dateString = dateString.split("/");
		//var date = dateString[2] + "-" + dateString[1] + "-" + dateString[0];
		var date = dateString[2] + "-" + ((dateString[1].length<2)?"0"+dateString[1]:dateString[1]) + "-" + ((dateString[0].length<2)?"0"+dateString[0]:dateString[0]);
		return date;
	}else{
		return "";
	}
	/*if(jQuery.browser.msie){
		var dt = new Date(dateString);
		month = dt.getMonth() + 1;
		var date = dt.getYear() + '-' + month + '-' + dt.getDate();
		return date;
	}else{
		return $.datepicker.formatDate('yy-mm-dd', new Date(dateString));
	}*/
}

function getNameFromEMail(emailId){
	if(emailId != undefined && emailId != ""){
		var name = emailId.replace(/@eryri-npa.gov.uk/,""); 
		pos = name.indexOf('.');
		fname = name.substring(0, pos)
		if(fname == "briang"){
			fname = "brian";
		}
		lname = name.substring(pos+ 1);
		return fname.toUpperCase().charAt(0) + fname.substring(1) + " " + lname.toUpperCase().charAt(0) + lname.substring(1);
	}else{
		return "";
	}
}


function getActionType(gid, actions){
	var actionType = "";
	for(i=0; i<actions.length; i++){
		if(actions[i].issueId == gid){
			actionType = actions[i].actionType;
			break;
		}
	}
	return actionType;
}

function attachmentLabelTranslations(){
	$('#attachment_header').html($._('attachment'));
	$('#attachment_desc').html($._('description'));
	$('#attachment_file').html($._('filename'));
	$('#btnupload').attr("value", $._('attach'));
	$('#attached_files').html($._('attached_files'));
	$('#attchmt_file').html($._('file'));
	$('#attchmt_action').html($._('action'));
	
	//Job
	$('#job_attached_files').html($._('attached_files'));
	$('#job_attchmt_file').html($._('file'));
	$('#job_attchmt_action').html($._('action'));
	
	//Legal
	$('#legal_attached_files').html($._('attached_files'));
	$('#legal_attchmt_file').html($._('file'));
	$('#legal_attchmt_action').html($._('action'));
	
	//Access Land
	$('#al_attach_files').html($._('attached_files'));
	$('#al_file').html($._('file'));
	$('#al_action').html($._('action'));
	
	//Furniture
	$('#issue_attached_file').html($._('attached_files'));
	$('#issue_file').html($._('file'));
	$('#issue_action').html($._('action'));
	
	//Surface
	$('#surface_attached_files').html($._('attached_files'));
	$('#surface_file').html($._('file'));
	$('#surface_action').html($._('action'));
}

function getPathConditionIssueCount(_rowId){

	var unResolvedIssue=null;
		 jQuery.ajax({
			   async:false,
				type: "GET",              
				url: STUDIO_URL + "path/unresolvedissue/condition/"+_rowId,        		               
				success: function (data) {
					unResolvedIssue=data
				}
			});
		var pathConditionAndIssueCount=unResolvedIssue.split(",");
		return pathConditionAndIssueCount;
	}



