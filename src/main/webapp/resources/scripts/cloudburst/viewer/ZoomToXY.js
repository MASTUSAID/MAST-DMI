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

var map;
var size;
var offset;
var icon;
var objMarker=null;
var markers;
Cloudburst.ZoomToXY =  function(_map, _searchdiv) {
    map = _map;
   
    markers = new OpenLayers.Layer.Markers("Markers");
    map.addLayer(markers);
    
    size = new OpenLayers.Size(32, 32);
    offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
    icon = new OpenLayers.Icon('resources/images/pushpin.png', size, offset);
    
    
    	
    searchdiv = _searchdiv;
    
	$("#tabs-Tool").empty();
	
	//removeChildElement("sidebar","layerSwitcherContent");		
	
	//$("#layerSwitcherContent").hide();
	
    jQuery.get('resources/templates/viewer/zoomtoxy.html', function(template) {
		
        //$("#" + searchdiv).append(template);    	
		addTab($._("zoom_to_xy"),template);
    	
		$("#zoomtoxy-help").tipTip({defaultPosition:"right"});
		
		// Event binding for options div
		$("#options-s-d").hide();
		$("#options-s-t").click(function() {
			$("#options-s-d").slideToggle('fast');
		});
		translateZoomToXYStrings();
    });
};

function translateZoomToXYStrings(){
	$('#longitude').html($._('longitude'));
	$('#latitude').html($._('latitude'));
	$('#btnZoom_To').attr("value", $._('zoom_to'));
	$('#btnPan_To').attr("value", $._('pan_to'));
	$('#btnCalLatLong').attr("value", $._('calculate'));
	$('#osref').html($._('os grid reference'));
}

var Pan = function(x,y) 
{   
   if (x != "" && y != "") {
        

	   map.panTo(new OpenLayers.LonLat(x, y));
       if (objMarker) {
           markers.removeMarker(objMarker);
       }
       
       size = new OpenLayers.Size(32, 32);
       offset = new OpenLayers.Pixel(-(size.w / 2), -size.h);
       icon = new OpenLayers.Icon('resources/images/pushpin.png', size, offset);
       
       objMarker=new OpenLayers.Marker(new OpenLayers.LonLat(x, y), icon)
       markers.addMarker(objMarker);
       $("#loading").hide();
       
    }
    else {
        //alert('Please enter X and Y values both.');
        jAlert('Please enter X and Y values both', 'Zoom To XY');
        return;
    }
};


var Zoom = function(x, y) {
    if (x != "" && y != "") {
        var geomPoint = new OpenLayers.Geometry.Point(x, y);
        var bounds = geomPoint.getBounds();
        
        if(bounds.left == bounds.right && bounds.top == bounds.bottom){
   		 	var lonlat = new OpenLayers.LonLat(bounds.left, bounds.top);
   		 	map.setCenter(lonlat, map.baseLayer.resolutions.length - 2, false, false);
	   	}else{
	   		map.zoomToExtent(bounds);
	   	}

        //map.zoomTo(8);
        if (objMarker) {
            markers.removeMarker(objMarker);
        }
        objMarker = new OpenLayers.Marker(new OpenLayers.LonLat(x, y), icon)
        markers.addMarker(objMarker);
        $("#loading").hide();

    }
    else {
        //alert('Please enter X and Y values both.');
        jAlert('Please enter X and Y values both', 'Zoom To XY');
        return;
    }
};


function calculateLatLong(){
	var osgref=$('#osgridref').val();	
	if(osgref!=""){
		var gridref = OsGridRef.parse(osgref);	
		lon = gridref.easting - 50;;
		lat = gridref.northing - 50;
		$('#LonX').val(lon);	
		$('#LatY').val(lat);	
	}
	else{
		jAlert('Please enter OS Grid Referance', 'Zoom To XY');
	}
}