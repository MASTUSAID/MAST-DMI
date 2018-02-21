

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

var active_layerMap;
var measure_draw;
var helpTooltipElement;
var helpTooltip;
var measureTooltipElement;
var measureTooltip;
var selectedFeatures=null;
var selectedFeaturesPoint=null;
var selectedFeaturesEdit=null;
var selectedFeaturesSearch=null;
var selectedFeaturesExport=null;
var draw;
var drawLine=null;
var selectSingleClick=null;
var selectInteraction = new ol.interaction.Select({
                condition: ol.events.condition.never
            });
var dragBoxInteraction = new ol.interaction.DragBox({
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: [250, 25, 25, 1]
                    })
                })
            });
var selectClick = new ol.interaction.Select({
				condition: ol.events.condition.click,
				
			  });
var zIn = new ol.interaction.DragBox({
			condition: ol.events.condition.always,
			boxEndCondition : function(mapBrowserEvent, startPixel, endPixel){zoomOnBox(mapBrowserEvent, startPixel, endPixel, 'in')}
		});
var zOut = new ol.interaction.DragBox({
			condition: ol.events.condition.always,
			boxEndCondition : function(mapBrowserEvent, startPixel, endPixel){zoomOnBox(mapBrowserEvent, startPixel, endPixel, 'out')}
		});
		
		
var intraction_dragBox =new ol.interaction.DragBox({
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: [250, 25, 25, 1]
                    })
                })
            });
	
	var intraction_dragBox_aoi =new ol.interaction.DragBox({
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: [250, 25, 25, 1]
                    })
                })
            });
			
			var intraction_dragBox_aoi1 =new ol.interaction.DragBox({
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: [250, 25, 25, 1]
                    })
                })
            });
			
var selectInteraction_edit  = new ol.interaction.Select({
                condition: ol.events.condition.never
			});	
	 
var  modifyInteraction = new ol.interaction.Modify({
      features: selectInteraction_edit.getFeatures()
    });

var dragInteraction = new ol.interaction.Translate({
        features: selectInteraction_edit.getFeatures()
      });

	  
var snapInteraction ;

var snapInteraction_aoi;

var deleteInteraction = new ol.interaction.Select({
        condition: ol.events.condition.click
      });
	  
var  modifyInteraction_aoi = new ol.interaction.Modify({
      features: selectInteraction_edit.getFeatures()
    });
	  
var dragInteraction_aoi = new ol.interaction.Translate({
        features: selectInteraction_edit.getFeatures()
      });

var deleteInteraction_aoi = new ol.interaction.Select({
        condition: ol.events.condition.click
      });	  
var selectInteraction_search  = new ol.interaction.Select({
                condition: ol.events.condition.never
			});


var intraction_dragBox_search =new ol.interaction.DragBox({
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: [250, 25, 25, 1]
                    })
                })
            });		


var selectInteraction_export  = new ol.interaction.Select({
                condition: ol.events.condition.never
			});


var intraction_dragBox_export =new ol.interaction.DragBox({
                style: new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: [250, 25, 25, 1]
                    })
                })
            });	

			
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
        $("#sidebar").find(".ui-tabs-nav li:eq(1)").remove();
        $("#sidebar").tabs("refresh");
	
	//remove feature and deactive the markup tool when close tab 
		removeDeactiveMarkupTool();
		
		if(dialog == "searchdiv"){
			removeBoundExtentFeature();
		}
	
}

function clearSelection(clearResultGrid, _lyr){
	
	$(".tooltip-static").remove();

	if(measure_draw!=null)
	{
		map.removeInteraction(measure_draw);
	}
	
	if(draw!=null)
	{
		map.removeInteraction(draw);
	}
	
	if (helpTooltipElement!=null) {
	  if(helpTooltipElement.parentNode!=null){
		if(helpTooltipElement.parentNode.hasChildNodes(helpTooltipElement))
	            helpTooltipElement.parentNode.removeChild(helpTooltipElement);
			
	  }	
	} 
	 if (measureTooltipElement!=null) {
	    if(measureTooltipElement.parentNode!=null){
		 if(measureTooltipElement.parentNode.hasChildNodes(measureTooltipElement))
		 	measureTooltipElement.parentNode.removeChild(measureTooltipElement);
		}	 
	 }
	  
	  if(helpTooltip!=null){
		   map.removeOverlay(helpTooltip);
	  }
	  
	  if(measureTooltip!=null){
		  map.removeOverlay(measureTooltip);
	  }
	  if (map.getOverlays().getArray().length >= 2) {
		  map.getOverlays().clear()
		  
	  };
	  
	  map.getLayers().forEach(function (layer) {
			if (layer.get('aname') != undefined & layer.get('aname') === 'measure') {
				map.removeLayer(layer);
			}
			if (layer.get('aname') != undefined & layer.get('aname') === 'pan_icon') {
				map.removeLayer(layer);
			}
			if (layer.get('aname') != undefined & layer.get('aname') === 'featureWorkflow') {
				map.removeLayer(layer);
			}
		});
		
		if(selectedFeatures!=null){
			selectedFeatures.clear();
			map.removeInteraction(selectInteraction);
            map.removeInteraction(dragBoxInteraction);
		}
		if (selectClick != null) {
		   var features = selectClick.getFeatures();
           features.clear();
			map.removeInteraction(selectClick);
			selectClick.un('select', myCallback);
		}

		if(zIn!=null)
		{
		 zIn.setActive(false);	
		}
        
		if(zOut!=null)
		{
		  zOut.setActive(false);	
		}
      
		 if (intraction_dragBox != null) {
		     map.removeInteraction(intraction_dragBox);
			
		 }
		  if (intraction_dragBox_aoi != null) {
		     map.removeInteraction(intraction_dragBox_aoi);
			
		 }
		 
		  if (intraction_dragBox_aoi1 != null) {
		     map.removeInteraction(intraction_dragBox_aoi1);
			
		 }
		 
	if (dragInteraction != null) {
		     map.removeInteraction(dragInteraction);
			
		 }
		 
	if (modifyInteraction != null) {
		     map.removeInteraction(modifyInteraction);
			
		 }	

if (modifyInteraction_aoi != null) {
		     map.removeInteraction(modifyInteraction_aoi);
			
		 }
	 if (selectInteraction_edit != null) {
		     map.removeInteraction(selectInteraction_edit);
			
	}	
	 
		 
	if(selectedFeaturesEdit!=null){
		selectedFeaturesEdit.clear()
	}	 
	

	if($("#tablegrid1").length>0)
    {
		 $("#tablegridContainer").html("");
		 $("#bottombar").empty();
		 $("#bottombar").hide();
		 $("#bottomcollapse").css("bottom", "0px");
		 $("#bottomcollapse").removeClass("bottom_collapse_down");
		$("#bottomcollapse").addClass("bottom_collapse");
		     
	}
	if (draw != null)
		  map.removeInteraction(draw);
	  
	  
	if (selectSingleClick != null)
		map.removeInteraction(selectSingleClick);
     
     if (drawLine != null)	 
	  map.removeInteraction(drawLine);


		
}

function addTab(tool,_template){
        $( "#sidebar" ).find(".ui-tabs-nav li:eq(1)").remove();
        $("<li><a href='#tabs-Tool'>" + tool + "</a></li>").appendTo("#sidebar .ui-tabs-nav");
        $("#sidebar").tabs("refresh");
        $("#tabs-Tool").append(_template);
        $('#sidebar').tabs("option", "active", $('#sidebar a[href="#tabs-Tool"]').parent().index());
}


function removeDeactiveMarkupTool(){
	
	
	$(".tooltip-static").remove();

	
	if(measure_draw!=null)
	{
		map.removeInteraction(measure_draw);
	}
	
	
	if (helpTooltipElement!=null) {
	  if(helpTooltipElement.parentNode!=null){
		if(helpTooltipElement.parentNode.hasChildNodes(helpTooltipElement))
	            helpTooltipElement.parentNode.removeChild(helpTooltipElement);
			
	  }	
	} 
	 if (measureTooltipElement!=null) {
	    if(measureTooltipElement.parentNode!=null){
		 if(measureTooltipElement.parentNode.hasChildNodes(measureTooltipElement))
		 	measureTooltipElement.parentNode.removeChild(measureTooltipElement);
		}	 
	 }
	  
	  if(helpTooltip!=null){
		   map.removeOverlay(helpTooltip);
	  }
	  
	  if(measureTooltip!=null){
		  map.removeOverlay(measureTooltip);
	  }
	  if (map.getOverlays().getArray().length >= 2) {
		  map.getOverlays().clear()
		  
	  };
	  
	  map.getLayers().forEach(function (layer) {
			if (layer.get('aname') != undefined & layer.get('aname') === 'measure') {
				map.removeLayer(layer);
			}
			if (layer.get('aname') != undefined & layer.get('aname') === 'pan_icon') {
				map.removeLayer(layer);
			}if (layer.get('aname') != undefined & layer.get('aname') === 'featureWorkflow') {
				map.removeLayer(layer);
			}
		});
		
		if(selectedFeatures!=null){
			selectedFeatures.clear();
			map.removeInteraction(selectInteraction);
            map.removeInteraction(dragBoxInteraction);
		}
		if (selectClick !== null) {
		   var features = selectClick.getFeatures();
           features.clear();
			map.removeInteraction(selectClick);
			selectClick.un('select', myCallback);
		}

		if(zIn!=null)
		{
		 zIn.setActive(false);	
		}
        
		if(zOut!=null)
		{
		  zOut.setActive(false);	
		}
       
	   if (intraction_dragBox != null) {
		     map.removeInteraction(intraction_dragBox);
			
		 }
		   if (intraction_dragBox_aoi != null) {
		     map.removeInteraction(intraction_dragBox_aoi);
			
		 }
		  if (intraction_dragBox_aoi1 != null) {
		     map.removeInteraction(intraction_dragBox_aoi1);
			
		 }
		 
		if (dragInteraction != null) {
				 map.removeInteraction(dragInteraction);
				
			 }
			 
		if (modifyInteraction != null) {
				 map.removeInteraction(modifyInteraction);
				
			 }	

if (modifyInteraction_aoi != null) {
				 map.removeInteraction(modifyInteraction_aoi);
				
			 }	

		 if (selectInteraction_edit != null) {
				 map.removeInteraction(selectInteraction_edit);
				
		}	
		 
			 
		if(selectedFeaturesEdit!=null){
			selectedFeaturesEdit.clear()
		}	 
		
		if(deleteInteraction!=null)
		{
		map.removeInteraction(deleteInteraction);
	    }
		
		if(deleteInteraction_aoi!=null)
		{
		map.removeInteraction(deleteInteraction_aoi);
	    }
		
		if(dragInteraction_aoi!=null)
		{
		map.removeInteraction(dragInteraction_aoi);
	    }

	if(selectInteraction_export!=null)
	  map.removeInteraction(selectInteraction_export);

   if(intraction_dragBox_export!=null)
       map.removeInteraction(intraction_dragBox_export);
    
	if(selectedFeaturesExport!=null){
			selectedFeaturesExport.clear()
	}		
if (draw != null)
	 map.removeInteraction(draw);

if($("#tablegrid1").length!=0)
    {
		   var recordCount = $("#tablegrid1").jqGrid('getGridParam', 'records');
		   if(recordCount > 0){
			   $("#bottombar").empty();
			   $("#bottombar").hide();
				$("#bottomcollapse").css("bottom", "0px");
				$("#bottomcollapse").removeClass("bottom_collapse_down");
				$("#bottomcollapse").addClass("bottom_collapse");
		   }
	}
	
	
	if (selectSingleClick != null)
		map.removeInteraction(selectSingleClick);
     
     if (drawLine != null)	 
	  map.removeInteraction(drawLine);
  
  
	
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

var zoomOnBox = function(mapBrowserEvent, startPixel, endPixel, direction) {

	var width = endPixel[0] - startPixel[0];
	var height = endPixel[1] - startPixel[1];
	var start_x = map.getCoordinateFromPixel([startPixel[0],startPixel[1]])[0];
	var start_y = map.getCoordinateFromPixel([startPixel[0],startPixel[1]])[1];
	var end_x = map.getCoordinateFromPixel([endPixel[0],endPixel[1]])[0];
	var end_y = map.getCoordinateFromPixel([endPixel[0],endPixel[1]])[1];
	
	var x_min = (start_x>end_x) ? end_x : start_x;
	var x_max = (start_x>end_x) ? start_x : end_x;
	var y_min = (start_y>end_y) ? end_y : start_y;
	var y_max = (start_y>end_y) ? start_y : end_y;
	
//	if(direction=='in' && ((x_max-x_min<1)||(y_max-y_min<1))) return false;
	
	if(direction=='out'){
		var dx = x_max - x_min;
		var dy = y_max - y_min;
		var mitte_x = (x_max + x_min)/2;
		var mitte_y = (y_max + y_min)/2;
		
		var extent_now = map.getView().calculateExtent(map.getSize());

		var edx = extent_now[2] - extent_now[0];
		var edy = extent_now[3] - extent_now[1];
		
		var max_dx_ext = extent_now[2] - extent_now[0];
		var max_dy_ext = extent_now[3] - extent_now[1];
		
		
		var exponent4zoom_out = Math.pow((1.5-(Math.round(map.getView().getResolution()*1000).toString().length*0.1)), 1.5);
		exponent4zoom_out = (exponent4zoom_out<1) ? 1 : exponent4zoom_out; 
		
	
		if(edx/edy >= dx/dy){

			x_max = mitte_x + (dx/2);
			x_min = mitte_x - (dx/2);
			y_max = mitte_y + (dy/2) + Math.pow((edy-dy), exponent4zoom_out);
			y_min = mitte_y - (dy/2) - Math.pow((edy-dy), exponent4zoom_out);

		} else {

			x_max = mitte_x + (dx/2) + Math.pow((edx-dx), exponent4zoom_out);
			x_min = mitte_x - (dx/2) - Math.pow((edx-dx), exponent4zoom_out);
			y_max = mitte_y + (dy/2);
			y_min = mitte_y - (dy/2);

		}
		
		if(max_dx_ext<(x_max-x_min) || max_dy_ext<(y_max-y_min)){
			x_max = mitte_x + (max_dx_ext/2);
			x_min = mitte_x - (max_dx_ext/2);
			y_max = mitte_y + (max_dy_ext/2);
			y_min = mitte_y - (max_dy_ext/2);
		}

	}
	
	map.getView().fit([x_min, y_min, x_max, y_max], map.getSize(), {constrainResolution:false});
};

function clearEditTool() {

    if(intraction_dragBox!=null)
	     map.removeInteraction(intraction_dragBox);
	 
	 if(intraction_dragBox_aoi!=null)
	     map.removeInteraction(intraction_dragBox_aoi);
	 
	  if(intraction_dragBox_aoi1!=null)
	     map.removeInteraction(intraction_dragBox_aoi1);
	 

	if (dragInteraction != null) 
         map.removeInteraction(dragInteraction);
		 
	if (modifyInteraction != null) 
		  map.removeInteraction(modifyInteraction);
		
		
	if(deleteInteraction!=null)	
	  map.removeInteraction(deleteInteraction);	
  
  if (modifyInteraction_aoi != null) 
		  map.removeInteraction(modifyInteraction_aoi);
	  
  if(deleteInteraction_aoi!=null)	
	  map.removeInteraction(deleteInteraction_aoi);	
  
	if(dragInteraction_aoi!=null)
		{
		map.removeInteraction(dragInteraction_aoi);
	    }
		
    if (snapInteraction != null)
		  map.removeInteraction(snapInteraction);
	  
	if (snapInteraction_aoi != null)
		  map.removeInteraction(snapInteraction_aoi);

	if (draw != null)
		  map.removeInteraction(draw);
    
	
	if (selectSingleClick != null)
		map.removeInteraction(selectSingleClick);
     
     if (drawLine != null)	 
	  map.removeInteraction(drawLine);
  
  
  
		
}
