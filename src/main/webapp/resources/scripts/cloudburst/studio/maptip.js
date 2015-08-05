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
var maptipData;
function MapTip(id)
{	
	$("#projectBean").change(getAssocaitedLayers);
	$("#layerBean").change(getLayerFieldForMapTip);
	if( jQuery("#maptipFormDiv").length <= 0){
		populateMaptipList(id);
	}
	else{
		
		displayMaptip();
	}
}


function displayMaptip(){
	
	jQuery("#maptip_accordion").hide();	        	
	jQuery("#maptipTable").show();
	
	$("#btn_MaptipSave").hide();
	$("#btn_MaptipBack").hide();
	$("#btn_MaptipNew").show();	

	
}

var populateMaptipList = function (id, reload) {
	 
	$("#MaptipRowData").empty();	
	
	$("#btn_MaptipSave").hide();
	$("#btn_MaptipBack").hide();
	$("#btn_MaptipNew").hide();
	 
	 $.ajax({
	        url: id + "/" + "?" + token,
	        success: function (data) {
	            _data = data;
	            
	            if(reload){
	        		//$("#btn_MaptipSave").hide();
	        		//$("#btn_MaptipBack").hide();
	        		//$("#btn_MaptipNew").show();
	        		//$("#search_MaptipDiv").show();
	        		//$("#MaptipGrid").hide();
	        		//$("#listMaptip").show();
	        		//jQuery("#maptipFields").empty();
	        		//jQuery("#maptipRows").empty();
	        		//jQuery("#maptipfrm").remove();
	            }
	            jQuery("#maptips").empty();	
	            jQuery.get("resources/templates/studio/" + id + ".html", function (template){
		            jQuery("#maptips").append(template);
			    	$('#maptipFormDiv').css("visibility", "visible");
					
					jQuery("#maptip_accordion").hide();	
					jQuery("#maptipTable").show();
					
					//jQuery("#maptipDetails").hide();	        	
					//jQuery("#MaptipRowData").empty();
					       		        	
					

					
			    	$("#MapTipTemplate").tmpl(data).appendTo("#MaptipRowData");
			    	
	                $("#btn_MaptipSave").hide();
	                $("#btn_MaptipBack").hide();
	                $("#btn_MaptipNew").show();
	                
	                $("#maptipTable").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
	                .tablesorterPager({ container: $("#maptip_pagerDiv"), positionFixed: false })
	                .tablesorterFilter({ filterContainer: $("#maptip_txtSearch"),                           
	                    filterColumns: [0],
	                    filterCaseSensitive: false
	                });
	                          
	            });
	        },
	        cache: false
	    });
}

var createEditRecord = function(maptipName){
	var projects;
	
	jQuery("#maptipTable").hide();
	jQuery("#maptipGeneralBody").empty();
	
	$("#btn_MaptipSave").hide();
	$("#btn_MaptipBack").hide();
	$("#btn_MaptipNew").hide();
	
	if(maptipName){
		$("#maptipfrm").attr("action", "maptip/edit");
		jQuery.ajax({
	        url: "maptip/" + maptipName + "?" + token,
	        success: function (data) {
	        	maptipData = data;
	        	 $("#MapTipTemplateForm").tmpl(maptipData,
                         {
	        		 		action: "Edit"
	        		 		//projectList: projectLayerGroups.projectBean
                         }
                      ).appendTo("#maptipGeneralBody");
	        	 
	        	 	getLayerFieldForMapTip();
	        	 	jQuery("#maptipTable").hide();
					
					/*	APARESH
	        	 	$("#btn_MaptipSave").show();
	        		$("#btn_MaptipBack").show();
	        		$("#btn_MaptipNew").hide();
	        		$("#search_MaptipDiv").hide();
	        		$("#listMaptip").hide();
	        		$("#MaptipGrid").show();
	        		$('#projectBean').empty();
	        		$('#layerBean').empty();
	        		$('#field').empty();
					*/
	        	},
	        	async: false
	    	});
	}else{
		$("#maptipfrm").attr("action", "maptip/create");
		jQuery.ajax({
			url: "project/names" + "?" + token,
			success: function(data){
				projects = data;
				
				$("#MapTipTemplateForm").tmpl(null,
		                {
		                    action: "Create"
		                }
		            ).appendTo("#maptipGeneralBody");
				
				jQuery.each(projects, function (i, value) {                    
					jQuery("#projectBean").append(jQuery("<option></option>").attr("value", value).text(value)); 
				});
				
				jQuery("#maptipTable").hide();
				
				/*
				$("#btn_MaptipSave").show();
        		$("#btn_MaptipBack").show();
        		$("#btn_MaptipNew").hide();
        		$("#search_MaptipDiv").hide();
        		$("#listMaptip").hide();
        		$("#MaptipGrid").show();
				*/
				//jQuery("#projectBean").val(maptipData.projectBean.name); 
				//jQuery("#projectBean").trigger('change');
			},
			async: false
		});
	}
	
	$("#maptip_accordion").show();
    jQuery("#maptip_accordion").accordion({fillSpace: true});
	
	jQuery("#btn_MaptipSave").show();
    jQuery("#btn_MaptipBack").show();
	
}

var getAssocaitedLayers = function(){
		$("#layerBean").empty();
		jQuery("#layerBean").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
		var selected = $("#projectBean option:selected");
		if (selected.val() != "") {
			jQuery.ajax({
				url: "projectlayergroup/" + selected.val() + "?" + token,
				success: function(data){
					 jQuery.each(data, function (i, value) {                    
	                     jQuery("#layerBean").append(jQuery("<option></option>").attr("value", value).text(value)); 
		        	 });
					 //jQuery("#layerBean").val(maptipData.layerBean.alias); 
					// jQuery("#layerBean").trigger('change');
				},
				async: false
			});
		}else{
			$("#field").empty();
			jQuery("#field").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
		}
}

var getLayerFieldForMapTip = function(){
	$("#field").empty();
	jQuery("#field").append(jQuery("<option></option>").attr("value", "").text("Please Select"));
	
	var selected;
	if(maptipData){
		selected = maptipData.layerBean.alias;
	}else{
		var optionSelected = $("#layerBean option:selected");
		selected = optionSelected.val();
	}
	if (selected != "") {
		jQuery.ajax({
			url: "layer/" + selected + "?" + token,
			success: function(data){
				var wfsName = data.name;
				var url = replaceString(data.url, /wms/gi, 'wfs') +  "&version=1.1.0&service=WFS&request=DescribeFeatureType&typeName=" + wfsName + "&maxFeatures=1";
				
				$.ajax({
			    	type: "GET",
			        cache: false,
			        url: PROXY_PATH + url, 
			        success: function (data) {
			            var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
			            var responseText = featureTypesParser.read(data);
			            var featureTypes = responseText.featureTypes;
			            featureNS = responseText.targetNamespace;
			            totalColumnWidth = 0; //re-initialize columnwidth

			            for (var i = 0; i < featureTypes[0].properties.length; ++i) {
			                if (featureTypes[0].properties[i].type.indexOf('gml')== -1) {
			                   var strField = featureTypes[0].properties[i].name;
			                   jQuery("#field").append(jQuery("<option></option>").attr("value", strField).text(strField)); 
			                }
			            }
			            if(maptipData){
			            	jQuery("#field").val(maptipData.field);
			            }
			        }
				});
				
			},
			async: false
		});
	}
}

var saveMapTip = function(){
	jQuery.validator.addMethod("noSpace", function (value, element) {
        return value.indexOf(" ") < 0 && value != "";
    }, "No space please and don't leave it empty");
	
	$("#maptipfrm").validate({
        rules: {
        	name: "required",
        	projectBean: "required",
            layerBean: "required",
            field: "required"
        },
        messages: {
            name: "Please enter  unique name",
            projectBean: "Please Select Project",
            layerBean: "Please Select Layer",
            field: "Please Select Field"
        }
    });
	if(jQuery("#maptipfrm").valid())	{
		saveMaptipData();
	}
}

var saveMaptipData = function() {
	
	var url = $("#maptipfrm").attr("action");
	 var data = $("#maptipfrm").serialize();
	 
	 $.post(url + "?" + token, 
			 data,                
	 function(_data){    
		 if(_data){
			 jAlert('Data Successfully Saved', 'Save');   
			 populateMaptipList('maptip', true);
		 }else{
			 jAlert('Unable to save data, record with same name or primary key may already exists', 'Error');
		 }
	 });
}

var deleteRecord = function(url, itemToDelete){
	
	 jConfirm('Are You Sure You Want To Delete : <strong>' + itemToDelete + '</strong>', 'Delete Confirmation', function (r) {

		    if (r) {
			    $.ajax({
			        url: url + "?" + token,
			        success: function (data) {
			        	if(data){
				            jAlert('Data Successfully Deleted', 'Delete');
				            populateMaptipList('maptip', true);
			        	}else{
			        		jAlert('Failed to remove Maptip record', 'Delete');
			        	}
			
			        },
			        error: function (XMLHttpRequest, textStatus, errorThrown) {
			            jAlert('Error', 'Delete');
			        }
			    });
		    }
	    });
}

var checkNameExists = function(maptipName){
	$.ajax({
        url: "maptip/" + maptipName  + "?" + token,
        success: function (data) {
        	if(data.name != undefined){
	            jAlert("Maptip name already exists", "Search");
        	}else{
        		jAlert("Maptip name doesn't exists", "Search");
        	}

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Error', 'Search');
        }
    });
}
