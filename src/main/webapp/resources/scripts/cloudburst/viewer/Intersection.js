

var geometry;
var geomFieldName;
var clonedLayer;
var featureNS;
var activeLayer;
var resultFeatures;
var myfilter;
var activeProject=null;
var newTemp=null;
var resulttmp=[];
var tempInValid=[];
var validate=true;
var check_spatial;
var validation_dialog=false;

function Intersection(geom, layer){

	if(geom!="allBounds")
		this.geometry = geom;
	if(map.getLayersByName("vector")[0]!=undefined && validation_dialog!=true)
		map.removeLayer(vectors);
	this.resultFeatures = null;
	activeLayer = layer;
	getGeomFieldName();

}


Intersection.prototype.creationSelectionCriteria = function(object,hamletId){
	var filter = null;
	if (object.handler instanceof OpenLayers.Handler.RegularPolygon ||object=="check" ) {
		var bounds = map.getLayersByName("spatial_unit")[0].maxExtent;
		if(this.geometry!=undefined)
			bounds = this.geometry.getBounds();
		filter = new OpenLayers.Filter.Spatial({
			type: OpenLayers.Filter.Spatial.INTERSECTS,//OpenLayers.Filter.Spatial.BBOX,
			value: bounds,
			property: geomFieldName,
			projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
		});
	}else if (object.handler instanceof OpenLayers.Handler.Polygon){
		filter = new OpenLayers.Filter.Spatial({
			type:  OpenLayers.Filter.Spatial.INTERSECTS,
			value:  this.geometry.geometry,
			property: geomFieldName,
			projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
		});
	}else if (object.handler instanceof OpenLayers.Handler.Point){
		if(object.handler.CLASS_NAME=="OpenLayers.Handler.Path"){
			filter = new OpenLayers.Filter.Spatial({
				type:  OpenLayers.Filter.Spatial.INTERSECTS,
				value:  this.geometry.geometry,
				property: geomFieldName,
				projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
			});
		}
		else{
			var point = this.geometry.geometry;
			var lonlat = new OpenLayers.LonLat(point.x, point.y);
			var pixel = map.getPixelFromLonLat(lonlat);

			var _x1 = pixel.x - 4;
			var _y1 = pixel.y - 4;
			var _x2 = pixel.x + 4;
			var _y2 = pixel.y + 4;

			var _lonlat1 = map.getLonLatFromPixel(new OpenLayers.Pixel(_x1, _y1));
			var _lonlat2 = map.getLonLatFromPixel(new OpenLayers.Pixel(_x2, _y2));

			var bounds = new OpenLayers.Bounds(); 
			bounds.extend(_lonlat1); 
			bounds.extend(_lonlat2);

			filter = new OpenLayers.Filter.Spatial({
				type:  OpenLayers.Filter.Spatial.INTERSECTS, //OpenLayers.Filter.Spatial.BBOX,
				value:  bounds, //this.geometry.geometry.getBounds(),
				property: geomFieldName,
				projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
			});
		}
	}
	else{
		filter = new OpenLayers.Filter.Spatial({
			type:  OpenLayers.Filter.Spatial.INTERSECTS,
			value:  this.geometry,
			property: geomFieldName,
			projection: activeLayer.projection //OpenLayers.Map.activelayer.projection
		});
	}


	if(activeLayer.name=='spatial_unit')
	{

		if(hamletId!=undefined)
		{
			myfilter = new OpenLayers.Filter.Logical({
				type: OpenLayers.Filter.Logical.AND,
				filters: [filter,
				          new OpenLayers.Filter.Comparison({
				        	  type: OpenLayers.Filter.Comparison.EQUAL_TO,
				        	  property:  "project_name",
				        	  value: activeProject
				          }),
				          new OpenLayers.Filter.Comparison({
				        	  type: OpenLayers.Filter.Comparison.EQUAL_TO,
				        	  property:  "hamlet_id",
				        	  value: hamletId
				          }),
				          new OpenLayers.Filter.Comparison({
				        	  type: OpenLayers.Filter.Comparison.EQUAL_TO,
				        	  property:  "active",
				        	  value: true
				          })
				]
			});

		}

		else

		{

			myfilter = new OpenLayers.Filter.Logical({
				type: OpenLayers.Filter.Logical.AND,
				filters: [filter,
				          new OpenLayers.Filter.Comparison({
				        	  type: OpenLayers.Filter.Comparison.EQUAL_TO,
				        	  property:  "project_name",
				        	  value: activeProject
				          }),
				          new OpenLayers.Filter.Comparison({
				        	  type: OpenLayers.Filter.Comparison.EQUAL_TO,
				        	  property:  "active",
				        	  value: true
				          })
				]
			});

		}

		filter=myfilter;
	}

	return filter;
}




Intersection.prototype.displayResult = function(filter, displayInGrid){
	var layerName = activeLayer.name; //OpenLayers.Map.activelayer.name;

	clonedLayer = map.getLayersByName("clone")[0];
	if(clonedLayer!= undefined){
		map.removeLayer(clonedLayer);
		$("#bottombar").empty();
		$("#bottombar").hide();
		$("#bottomcollapse").css("bottom", "0px");
		$("#bottomcollapse").removeClass("bottom_collapse_down");
		$("#bottomcollapse").addClass("bottom_collapse");
	}


	$.ajax({
		url: STUDIO_URL + 'layer/' + layerName + "?" + token,
		async:false,
		success: function (data) {
			var uniqueField;
			var displayableCols = data.layerFields;
			if(displayableCols.length > 0)
				uniqueField = displayableCols[0].keyfield;

			if(displayableCols.length > 0 && uniqueField != undefined){

				result = new Result(layerName, featureNS, filter, true, undefined, undefined);
				result.displayResult(displayableCols, uniqueField, geomFieldName, false);
				getIntersections();
			}
		}
	});
}




function viewProjectName(project)

{
	activeProject=project;	

}
function getGmlFeatures(gmlFeatures)
{
	newTemp=gmlFeatures;
}

function getIntersections()

{
	if(validation_dialog){
		alert("kindly close the dialogue");

	}
	else{
		var tmp=newTemp.slice(0);
		var jsts_reader = new jsts.io.WKTReader();
		resulttmp=[];
		tempInValid=[];

		var i;

		i = tmp.length;


		while (i--) {


			var jsts_geomA = jsts_reader.read(tmp[i].geometry.toString());
			if(jsts_geomA.isValid())
			{

				for (var j = 0; j < tmp.length; j++) {


					if(i!=j)
					{

						//var jsts_geomA = jsts_reader.read(tmp[i].geometry.toString());
						var jsts_geomB = jsts_reader.read(tmp[j].geometry.toString());


						if(jsts_geomA.intersects(jsts_geomB)){

							try {
								var tmpgeometry = jsts_geomA.intersection(jsts_geomB);
								if(tmpgeometry.getNumGeometries()>1)
									resulttmp.push(tmpgeometry);
								else if(tmpgeometry.getNumGeometries()==1 && tmpgeometry.getArea()!=0)
									resulttmp.push(tmpgeometry);   
							} catch (e) {
								if($.inArray(tmp[j], tempInValid) == -1)
									tempInValid.push(tmp[j]);
							}

						}	

					}

				}
			}
			else{
				if($.inArray(tmp[i], tempInValid) == -1)
					tempInValid.push(tmp[i]);
			}

			tmp.splice(i, 1);
		}
		intersectionPopup(resulttmp,tempInValid);
	}

}



function intersectionPopup(resulttmp,tempInValid)
{


	if (resulttmp.length > 0 || tempInValid.length >0) {

		var popupInfo = '';
		popupInfo += '<html><body>';		
		popupInfo += '<h3 class="rowhead" style="padding: 5px; padding-left: 26px;"> Summary </h3>';
		popupInfo += '<div><p><strong>Number of intersections:' +resulttmp.length+ '</strong></p>';
		popupInfo += '<p><strong>Number of invalid geometries:' +tempInValid.length+ '</strong></p>';
		popupInfo += '<p><strong>Selected type:'+spatial_validType+'</strong></p></div>';
		//Info diplay in popup
		popupInfo += '<h3 class="rowhead" style="padding: 5px; padding-left: 26px;"> Intersection errors</h3>';
		popupInfo +='<div class="setOverflow"> <table  class="featureInfo" style="margin-top: 10px; margin-bottom: 10px;">';
		$.each(resulttmp, function (i, dispField) {
			var k=i+1;
			popupInfo += "<tr><td style=' width: 335px; font-size: 12px; padding: 5px;  '>";
			popupInfo += '<a id="'+dispField+'" href="#" name="toggleId'+k+'"> '+k+'</a></td>';
			popupInfo +='<td><button  style="float:right;" name="'+dispField+'" title="invalidId'+k+'" onclick="javascript:toggleView(this);"><img id="invalidId'+k+'" src="resources/images/viewer/toggle.png" title="Display On" /></button></td>';
			popupInfo +='<td><button  style="float:right;" onclick="javascript:fixedIssues('+i+',1);"><img id="fixedId" src="resources/images/viewer/fixed.png" title="Errors Fixed" /></button></td></tr>';



		});
		popupInfo += '</table></div>';

		popupInfo += '<h3 class="rowhead" style="padding: 5px; padding-left: 26px;"> Invalid geometry errors</h3>';

		popupInfo +='<div class="setOverflow"><table class="featureInfo" style="margin-top: 10px; margin-bottom: 10;">';
		$.each(tempInValid, function (i, dispGeom) {

			var k=i+1;
			popupInfo += "<tr><td style=' width: 335px; font-size: 12px; padding: 5px;'>";

			popupInfo += '<a id="'+dispGeom.geometry+'" href="#">'+k+'(Feature Id:'+dispGeom.attributes.usin+')</a></td>';
			popupInfo +='<td><button style="float:right;" name= "'+dispGeom.geometry+'" title="toggleId'+k+'" onclick="javascript:toggleView(this);"><img id="toggleId'+k+'" src="resources/images/viewer/toggle.png" title="Display On" /></button></td>';
			popupInfo +='<td><button style="float:right;" onclick="javascript:fixedIssues('+i+',2);"><img id="fixedId" src="resources/images/viewer/fixed.png" title="Errors Fixed" /></button></td></tr>';

		});
		popupInfo += '</table> </div>';


		popupInfo += '</body></html>'; 

		/********************popupr*****************************/
		/*var popup = new OpenLayers.Popup.FramedCloud("identify", lonLat, new OpenLayers.Size(250, 150), popupInfo, null, true, onPopupClose);
    popup.autoSize = false;
    map.addPopup(popup);*/
		/********************end*****************************/


		/********************Side Bar*****************************/

		jQuery.get('resources/templates/viewer/info.html', function(template) {

			jQuery("#intersectionDialog").empty();
			jQuery("#intersectionDialog").html(template);	
			jQuery(".span-c").css("display","none");
			jQuery("#info-help").css("display","none");
			jQuery("#intersectionDialog").accordion({autoHeight: false});

			intersectionDialog = $( "#intersectionDialog" ).dialog({
				autoOpen: false,
				height:350,
				width:250,
				resizable: false,
				modal: false,
		
				close : function() {
					intersectionDialog.dialog( "destroy" );
					validation_dialog=false;
					if(map.getLayersByName("vector")[0]!=undefined)
						map.getLayersByName("vector")[0].setVisibility(false);

				}

			});
			
			$('#intersectionDialog').dialog({open: function(event, ui) {
                $(this).parent().find('.ui-dialog-titlebar').append('<a href="#" title="Minimize" style="width: 12px; height: 12px; margin:0 5px; position: relative; right: -74px; top: 2px;; "onclick="javascript:minimize();"><img src="resources/images/studio/min.png"/></a><a href="#" title="Maximize" style="width: 12px; height: 12px; margin:0 5px; position: relative; right: -69px; top: 2px;" onclick="javascript:maximize();"><img src="resources/images/studio/max.png"/></a>');
            }});

	
			validation_dialog=true;
			intersectionDialog.dialog( "open" );	

			/*addTab('Validation Errors',template);*/
			/*$('<div />').html(template).dialog();
			 */			
			jQuery("#info_accordion").empty();
			jQuery("#info_accordion").html(popupInfo);	

			jQuery("#info_accordion").accordion({autoHeight: false});



			/*	defaultTab=$('#info_accordion tr a')[0].id;
		showIntersection(defaultTab);*/


			/*	$('#info_accordion tr a').click(function(event) {

				var str=event.currentTarget.id;
				var str1=event.currentTarget.name;
				jQuery('#'+str1+'').attr("src","resources/images/viewer/toggleOff.png");
				showIntersection(str);

			});*/

		});

		/********************end*****************************/   


	}

	else
	{

		jAlert("No Validation Error","Spatial Validation");
	}

	OpenLayers.Element.removeClass(map.viewPortDiv, "olCursorWait");

}

function showIntersection(featureGeom,feature_sel)

{
	jQuery('#'+feature_sel+'').attr("src","resources/images/viewer/toggleOff.png");
	jQuery('#'+check_spatial+'').attr("title","Display Off");
	if(map.getLayersByName("vector")[0]!=undefined)
		map.removeLayer(vectors);
	OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';
	vectors = new OpenLayers.Layer.Vector("vector", {isBaseLayer: true});

	map.addLayers([vectors]);

	var feature = new OpenLayers.Feature.Vector(
			OpenLayers.Geometry.fromWKT(
					featureGeom
			)
	);

	vectors.addFeatures([feature]);

	map.zoomToExtent(vectors.getDataExtent());
/*	map.zoomTo(13);*/
}

function toggleView(id)
{

	if(validate){
		check_spatial=id.title;
		showIntersection(id.name,check_spatial);
		validate=false;

	}
	else {
		if(check_spatial!=id.title){
			showIntersection(id.name,id.title);
			validate=false;
			if(check_spatial!=undefined){
				jQuery('#'+check_spatial+'').attr("src","resources/images/viewer/toggle.png");	
				jQuery('#'+check_spatial+'').attr("title","Display On");	
				check_spatial=id.title;	
			}


		}
		else{
			jQuery('#'+check_spatial+'').attr("src","resources/images/viewer/toggle.png");
			jQuery('#'+check_spatial+'').attr("title","Display On");
			if(map.getLayersByName("vector")[0]!=undefined)
			map.removeLayer(vectors);
			validate=true;	
		}

	}
	

}


function fixedIssues(geomIndex,type)
{

	if(type==1){
		resulttmp.splice(geomIndex, 1);
		intersectionPopup(resulttmp,tempInValid);
	}
	else if(type==2){
		tempInValid.splice(geomIndex, 1);
		intersectionPopup(resulttmp,tempInValid);
	}
}

function minimize(){
	
	$( "#intersectionDialog" ).dialog({
		width:250,
		height:50,
		resizable: true,
		modal: false,
		
	});
	$( "#intersectionDialog" ).css('display','none');
	
	}
function maximize(){
	
	$( "#intersectionDialog" ).dialog({
		height: 350,
		width:250,
		resizable: false,
		modal: false,
	});
	
}