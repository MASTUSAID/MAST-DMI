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
var selectedMeasure=1;

Cloudburst.Measure = function (map, _searchdiv) {
   // if($("#measurediv").length<=0){
		this.map = map;
		searchdiv = _searchdiv;
		//$("#" + searchdiv).empty();
		//removeChildElement(_parentDiv,excludedDiv)
	
		//removeChildElement("sidebar","layerSwitcherContent");
		//$("#layerSwitcherContent").hide();
		$("#tabs-Tool").empty();
		
		jQuery.get('resources/templates/viewer/measure.html', function(template) {		
			//$("#" + searchdiv).append(template);
			//Add tad
			addTab($._('measure'),template);
			
			// Event binding for options div
			$("#options-s-d").hide();
			$("#options-s-t").click(function() {
				$("#options-s-d").slideToggle('fast');
			});
			
			
			toggleButtons();
			
			$("#btnLine").click(function() {
				
				
				$("#segLabel").text($._("measure_segmentlength") + " :");
				$("#totLabel").text($._("measure_length") + " :");
				$("#segValue").text("");
				$("#totValue").text("");
				selectedMeasure=1;
				Cloudburst.Navi.prototype.toggleControl("measurelength");
			});
			
			$("#btnArea").click(function() {
				//$("#segLabel").text("Segment Area:");
				$("#segLabel").text("");
				$("#totLabel").text($._("measure_area") + " :");
				$("#segValue").text("");
				$("#totValue").text("");
				selectedMeasure=2;
				Cloudburst.Navi.prototype.toggleControl("measurearea");
				
			});
			
			$('#measureUnit').change(function() { 
			
			//getActualMeasure(currentMeasure,applyedUnit);
			onUnitChange();
			});
			
			$('#btnLine').click();
			
			translateMeasureStrings();
		});
	
	//}
};

function translateMeasureStrings(){
	$('#measure_unit').html($._('measure_unit') + ":");
	$('#measureUnit').append($("<option></option>").attr("value", "m").text($._('measure_meter')));
	$('#measureUnit').append($("<option></option>").attr("value", "km").text($._('measure_km')));
	$('#measureUnit').append($("<option></option>").attr("value", "mi").text($._('measure_mile')));
}

function getUnitChangeValue(_currentMeasure,_applyedUnit,_selected_unit){
var currMeasure=0.000;
var factor=1;
if (_applyedUnit == 'm' && _selected_unit == 'km') {
        if(selectedMeasure==1){
			factor = 0.001;
        }
		else{
			factor = 0.000001;
		}
		currMeasure = _currentMeasure * factor;
    }
    else if (_applyedUnit == 'm' && _selected_unit == 'mi') {
        
		if(selectedMeasure==1){
			factor = 0.0006214;0
        }
		else{
			factor = 3.86102159e-7;
		}
		
        currMeasure = _currentMeasure * factor;
    }
    else if (_applyedUnit == 'km' && _selected_unit == 'm') {
		if(selectedMeasure==1){
			factor = 1000;
        }
		else{
			factor = 1000000;
		}
		
		
        currMeasure = _currentMeasure * factor;
    }
    else if (_applyedUnit == 'km' && _selected_unit == 'mi') {
        
        
		if(selectedMeasure==1){
			factor = 0.6214;
        }
		else{
			factor = 0.386102159;
		}
		
		currMeasure = _currentMeasure * factor;
    }
    else if (_applyedUnit == 'mi' && _selected_unit == 'm') {
        
        
		if(selectedMeasure==1){
			factor = 1609;
        }
		else{
			factor = 2589988.11034;
		}
		currMeasure = _currentMeasure * factor;
    }
    else if (_applyedUnit == 'mi' && _selected_unit == 'km') {
        
        
		if(selectedMeasure==1){
			factor = 1.6093;
        }
		else{
			factor = 2.58998811034;
		}
		currMeasure = _currentMeasure * factor;
    }
	else if (_applyedUnit == _selected_unit) {
		factor = 1;
		currMeasure=_currentMeasure * factor;
	}
	
	return currMeasure;
}



var segVal = 0.000;
var applyedUnit;
var selected_unit;
var currentMeasure = 0.000;
var evtOrder;
/*
function getActualMeasure(measure,unit,order,val){
    if(measure>0){
		if(order){evtOrder=order};
	var factor=1;
    var out = "";    
    selected_unit = $("#measureUnit").val();
	applyedUnit=unit;
	currentMeasure = getUnitChangeValue(measure,applyedUnit,selected_unit);
		
	
    if (evtOrder == 1) {
        
		out=currentMeasure.toFixed(3)+" "+ selected_unit;		
		
    } else if (evtOrder == 2) {
        
		out=currentMeasure.toFixed(3)+" "+ selected_unit +"2".sup();
    }

 
	$("#totValue").html(out);
	applyedUnit = selected_unit;

	}
}

*/

var lastMeasure = 0.0;

function measurementToolActivated() {
    lastMeasure = 0.0;
}

function handleFinalMeasurement(event) {
    var units = event.units;
    var measure = event.measure;
    var outFinal=0.000;
	var outSegment=0.000;
	var order=event.order;
	evtOrder=order;
	selected_unit = $("#measureUnit").val();
	applyedUnit=units;
	
	if (evtOrder == 1) {
		//outFinal=measure.toFixed(3) + " " + units;
		outFinal=getUnitChangeValue(measure,applyedUnit,selected_unit);
		finalmeasure=outFinal;
		
		$('#totValue').html(outFinal.toFixed(3)+" " + selected_unit);
    	
		if (lastMeasure > 0) {
			//out += "    last segment: " + (measure-lastMeasure).toFixed(3) + " " + units;
			//outSegment=(measure-lastMeasure).toFixed(3) + " " + units;
			outSegment=getUnitChangeValue((measure-lastMeasure),applyedUnit,selected_unit);
			segmeasure=outSegment;
			$('#segValue').html(outSegment.toFixed(3)+" " + selected_unit);
		}
	
	}
	else if (evtOrder == 2) {
        
		//outFinal=measure.toFixed(3) + " " + units +"2".sup();
		    	
		outFinal=getUnitChangeValue(measure,applyedUnit,selected_unit);		
		finalmeasure=outFinal;		
		$('#totValue').html(outFinal.toFixed(3)+" " + selected_unit+"2".sup());
		
		
		if (lastMeasure > 0) {
			//out += "    last segment: " + (measure-lastMeasure).toFixed(3) + " " + units;
			//outSegment=(measure-lastMeasure).toFixed(3) + " " + units +"2".sup();
			outSegment=getUnitChangeValue((measure-lastMeasure),applyedUnit,selected_unit);
			
			//$('#segValue').html(outSegment.toFixed(3)+" " + selected_unit);
		}
		
		
    }
    lastMeasure = 0.0;  
    applyedUnit = selected_unit;
    //segmeasure=measure-lastMeasure;
	//finalmeasure=measure;
}

var segmeasure=0.000;
var finalmeasure=0.000;



function handlePartialMeasurement(event) {
    var units = event.units;
    var measure = event.measure;
    var outFinal=0.000;
	var outSegment=0.000;
	var order=event.order;
	evtOrder=order;
	selected_unit = $("#measureUnit").val();
	applyedUnit=units;
	if (evtOrder == 1) {
		//outFinal=measure.toFixed(3) + " " + units;
		outFinal=getUnitChangeValue(measure,applyedUnit,selected_unit);
		finalmeasure=outFinal;
		
		$('#totValue').html(outFinal.toFixed(3)+" " + selected_unit);
    	$('#segValue').html(outFinal.toFixed(3)+" " + selected_unit);
		if (lastMeasure > 0) {
			
			//outSegment=(measure-lastMeasure).toFixed(3) + " " + units;
			outSegment=getUnitChangeValue((measure-lastMeasure),applyedUnit,selected_unit);
			segmeasure=outSegment;
			$('#segValue').html(outSegment.toFixed(3)+" " + selected_unit);
		}
	
	
	} else if (evtOrder == 2) {
        
		//outFinal=measure.toFixed(3) + " " + units +"2".sup();
		
		outFinal=getUnitChangeValue(measure,applyedUnit,selected_unit);		
		finalmeasure=outFinal;		
		$('#totValue').html(outFinal.toFixed(3)+" " + selected_unit+"2".sup());
    	
		if (lastMeasure > 0) {
			
			//outSegment=(measure-lastMeasure).toFixed(3) + " " + units +"2".sup();
			outSegment=getUnitChangeValue((measure-lastMeasure),applyedUnit,selected_unit);
			
			//$('#segValue').html(outSegment.toFixed(3)+" " + selected_unit);
		}
		
		
    }
	
    lastMeasure = measure;
    applyedUnit = selected_unit;
	//segmeasure=measure-lastMeasure;
	//finalmeasure=measure;
}


function onUnitChange(){
	var outFinal=0.000;
	var outSegment=0.000;
	
		selected_unit = $("#measureUnit").val();
	
		outFinal=getUnitChangeValue(finalmeasure,applyedUnit,selected_unit);
		outSegment=getUnitChangeValue(segmeasure,applyedUnit,selected_unit);
		
		if (evtOrder == 1) {
		
		$('#totValue').html(outFinal.toFixed(3)+" " + selected_unit);
		$('#segValue').html(outSegment.toFixed(3)+" " + selected_unit);
		}
		else 
		if (evtOrder == 2) {
		
		$('#totValue').html(outFinal.toFixed(3)+" " + selected_unit +"2".sup());
		//$('#segValue').html(outSegment.toFixed(3)+" " + selected_unit +"2".sup());
		}
		
		finalmeasure=outFinal;
		segmeasure=outSegment;
		applyedUnit = selected_unit;
}

