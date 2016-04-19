

var geometry;
var geomFieldName;
var clonedLayer;
var featureNS;
var activeLayer;
var resultFeatures;
var myfilter;
var activeProject=null;
var newTemp=null;

function Intersection(geom, layer){
	this.geometry = geom;
	this.resultFeatures = null;
	activeLayer = layer;
	getGeomFieldName();

}


Intersection.prototype.creationSelectionCriteria = function(object){
	var filter = null;
	if (object.handler instanceof OpenLayers.Handler.RegularPolygon) {
		var bounds = this.geometry.getBounds();
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

		myfilter = new OpenLayers.Filter.Logical({
			type: OpenLayers.Filter.Logical.AND,
			filters: [filter,
			          new OpenLayers.Filter.Comparison({
			        	  type: OpenLayers.Filter.Comparison.EQUAL_TO,
			        	  property:  "project_name",
			        	  value: activeProject
			          })
			]
		});

		filter=myfilter;
	}

	return filter;
}




Intersection.prototype.displayResult = function(filter, displayInGrid){
	var layerName = activeLayer.name; //OpenLayers.Map.activelayer.name;

	clonedLayer = map.getLayersByName("clone")[0];
	if(clonedLayer != undefined){
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
	var tmp=newTemp.slice(0);
	var jsts_reader = new jsts.io.WKTReader();
	var resulttmp=[];
	var tempInValid=[];

	var i;

	i = tmp.length;


	while (i--) {

		for (var j = 0; j < tmp.length; j++) {
		
		var jsts_geomA = jsts_reader.read(tmp[i].geometry.toString());
		if(jsts_geomA.isValid())
		{
		if(i!=j)
			{

				//var jsts_geomA = jsts_reader.read(tmp[i].geometry.toString());
				var jsts_geomB = jsts_reader.read(tmp[j].geometry.toString());
				
				
				if(jsts_geomA.intersects(jsts_geomB)){

					try {
						var tmpgeometry = jsts_geomA.intersection(jsts_geomB);
						resulttmp.push(tmpgeometry);   
					} catch (e) {
						if($.inArray(tmp[j], tempInValid) == -1)
							tempInValid.push(tmp[j]);
					}

				}	

			}
		
		}
		
		else{
		if($.inArray(tmp[i], tempInValid) == -1)
		tempInValid.push(tmp[i]);
		}
			


		}
		tmp.splice(i, 1);
	}

	console.log(tempInValid.length);
	intersectionPopup(resulttmp,tempInValid);


}



function intersectionPopup(resulttmp,tempInValid)
{

	if (resulttmp.length > 0 || tempInValid.length >0) {

		var popupInfo = '';
		popupInfo += '<html><body>';				//Info diplay in popup
		popupInfo += '<h3 class="rowhead" style="padding: 5px; padding-left: 26px;"> Intersection Info</h3>';
		popupInfo +='<div> <table class="featureInfo" style="margin-top: 10px;">';
		$.each(resulttmp, function (i, dispField) {

			popupInfo += "<tr><td style=' width: 335px; font-size: 12px; padding: 5px;  '>";

			popupInfo += '<a id="'+dispField+'" href="#"> Intersection '+i+'</a></td></tr>';

		});
		popupInfo += '</table></div>';
		
		popupInfo += '<h3 class="rowhead" style="padding: 5px; padding-left: 26px;"> Invalid Geometry</h3>';
		
		popupInfo +='<div><table class="featureInfo" style="margin-top: 10px;">';
		$.each(tempInValid, function (i, dispGeom) {

			popupInfo += "<tr><td style=' width: 335px; font-size: 12px; padding: 5px;'>";
			
			popupInfo += '<a id="'+dispGeom.geometry+'" href="#">'+dispGeom.fid+'</a></td></tr>';

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


			addTab('Validation Errors',template);

			jQuery("#info_accordion").empty();
			jQuery("#info_accordion").html(popupInfo);	
			jQuery("#info_accordion").accordion({autoHeight: false});
			


			/*	defaultTab=$('#info_accordion tr a')[0].id;
		showIntersection(defaultTab);*/


			$('#info_accordion tr a').click(function(event) {

				var str=event.currentTarget.id;

				showIntersection(str);
			});

		});

		/********************end*****************************/   


	}
	
	else
	{
	
	jAlert("No Validation Error","Spatial Validation");
	}

	OpenLayers.Element.removeClass(map.viewPortDiv, "olCursorWait");

}

function showIntersection(featureGeom)

{

	if(vectors!=null)
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
	map.zoomTo(13);
}