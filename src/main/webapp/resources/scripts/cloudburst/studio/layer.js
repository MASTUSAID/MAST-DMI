var layers = {};
var layerData=null;
function Layer(id)
{	
	if( jQuery("#layerFormDiv").length<=0){
		populateList(id);
	
	}
	else{
	
		displayLayer();
	}
	
	
	
}
function Refresh()
{
   jQuery("#layer_txtSearch").val("");
populateList("layer");
}

function displayLayer(){
	
	
	jQuery("#lyrTypeDiv").hide();	        	
	jQuery("#layer_accordion").hide();	        	
	jQuery("#layerTable").show();
	
	jQuery("#layer_btnSave").hide();
	jQuery("#layer_btnBack").hide();
	jQuery("#layer_btnNew").show();
}




var deleteLayerRecord = function (url, itemToDelete, id) {
    jConfirm('Are You Sure You Want To Delete : <strong>' + itemToDelete + '</strong>', 'Delete Confirmation', function (r) {

	    if (r) {
		    $.ajax({
		        url: url + "?" + token,
		        success: function (data) {
		        	if(data=="success"){
			            jAlert('Data Successfully Deleted', 'Delete');
			            layerArray = [];
			            populateList(id);
		        	}else{
		        		 jAlert(data, 'Delete');
						 
		        	}
		
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		            jAlert('Error', 'Delete');
		        }
		    });
	    }
    });
}

var populateList = function (id) {
	
	
	$("#LayerRowData").empty();
	$("#layerPropertyGrid").empty();
	$("#layerFieldGrid").empty();
	$("#layer_btnSave").hide();
    $("#layer_btnNew").hide();
    $("#layer_btnBack").hide();
	
	var _data = null;
    $.ajax({
        url: id+"/" + "?" + token, 
        success: function (data) {
            _data = data;
       
            $("#layerPropertyGrid").empty();
            $("#fieldGrid").hide();
            $("#layerFieldGrid").empty();
            
            jQuery("#layers").empty();
            jQuery.get("resources/templates/studio/" + id + ".html", function (template){
	            jQuery("#layers").append(template);
	            jQuery('#layerFormDiv').css("visibility", "visible");
		    	jQuery("#lyrTypeDiv").hide();
				jQuery("#layer_accordion").hide();
		    	jQuery("#layerTable").show();
		    	
		    	jQuery("#LayerTemplate").tmpl(data).appendTo("#LayerRowData");
		    	jQuery("#layer_btnSave").hide();
		    	jQuery("#layer_btnNew").show();
		    	jQuery("#layer_btnBack").hide();
               
			   	if(data.length>0){
                $("#layerTable").tablesorter({ 
                headers: {6: {sorter: false  },  7: {  sorter: false } },		
                debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                .tablesorterPager({ container: $("#layer_pagerDiv"), positionFixed: false })
                .tablesorterFilter({ filterContainer: $("#layer_txtSearch"),                           
                    filterColumns: [0],
                    filterCaseSensitive: false
                });
				
			}
				
                          
            });
        },
        cache: false
    });
 
}

function saveLayerData1() {
	
	var url = $("#layerfrm").attr("action");
	 
	 jQuery.ajax({
	        type: "POST",              
	        url: url+ "?" + token,
	        data: jQuery("#layerfrm").serialize(),
	        async:false,
			success: function () {        
	                                   
	           jAlert('Data Successfully Saved', 'Layer');
	           //return false;
	           populateList('layer');
	            
	        },
	        error: function (XMLHttpRequest, textStatus, errorThrown) {
	            
	            alert('err.Message');
	        }
	    });
	 
	
}


function saveLayer() {
	
	  
	
	jQuery.validator.addMethod("noSpace", function (value, element) {
        return value.indexOf(" ") < 0 && value != "";
    }, "No space please and don't leave it empty");
	
		
	if(currentLayerType == 'WFS' ||currentLayerType == 'WMS' ) {
        $("#layerfrm").validate({
            rules: {
                url: "required",
                layertype: "required",
                name: "required",
                displayname: "required",
                alias: {
                    required: true,
                    noSpace: true
                },
                projection_code: "required",
                project_outputFormat: "required",
				layer_unit:"required",
                maxextent: "required",
                Gutter: {
                    // required: true,
                    digits: true
                },
                minscale: {
                     required: true,
                    digits: true
                },
                maxscale: {
                     required: true,
                    digits: true
                },
                buffer: {
                    // required: true,
                    digits: true
                },
                tilesize: {
                    required: true,
                    digits: true
                },
				geomtype:"required"
            },
            messages: {
                url: "Please enter  URL",
                layertype: "Please enter  Type",
                name: "Please Select Layer Name",
                displayname: "Please enter  Display name",
                alias: {
                    required: "Please enter Alias",

                    noSpace: "No white space please"
                },
                projection_code: "Please enter  Projection",
                project_outputFormat: "Please Select  Format",
				layer_unit:"Please Select  projection",
                MaxExtent: "Please enter  maxextent",
                Gutter: {
                    // required: "Please Enter Gutter",
                    digits: "Please Enter a valid number.  "
                },
                minscale: {
                     required: "Please Enter MinScale",
                    digits: "Please Enter a valid number.  "
                },
                maxscale: {
                     required: "Please Enter MaxScale",
                    digits: "Please Enter a valid number.  "
                },
                tilesize: {
                     required: "Please Enter TileSize",
                    digits: "Please Enter a valid number.  "
                },
                buffer: {
                    //required: "Please Enter Buffer",
                    number: "Please Enter a valid number.  "
                }
            }

        });
    }

		
		if(jQuery("#layerfrm").valid())	{						
			
			
			saveLayerData1();
		
		}
	
}

var wfsNameArr = {};
var currentLayerType = "";
var id;
var units = null;
    var prj = null;
    var formats = null;
    var editData = null;
    var layerTypes = null;
    var layerGroup = null;
    var users = null;
	var layer_format=null;
	var proj_projectionsproj_projections=null;

function CreateEditLayerRecord(_id, _type, _url, type) {
    
    selectedLayerFields = null;
    selectedLayerKey = '';
    id = type;
    //$("#pagerDiv").hide();
    jQuery("#layerTable").hide();
    
    //empty all the body
    jQuery("#layerGeneralBody").empty();
    jQuery("#layerFieldBody").empty();
    jQuery("#layerStyleBody").empty(); 
    jQuery("#layerFilterBody").empty();
    
    jQuery("#layer_btnNew").hide();    
    jQuery("#layer_btnSave").hide();
    jQuery("#layer_btnBack").hide();
 
    
    //hideAllLayerTR();
    selectedId = _id;

    $("#tableGrid").empty();
    $.ajax({
        url: "unit/"+ "?" + token,
        success: function (data) {
            units = data;
        },
        async: false
    });
    $.ajax({
        url: "outputformat/" + "?" + token,
        success: function (data) {
            formats = data;
			layer_format=data;
        },
        async: false
    });
    
	$.ajax({
		url: "layertype/" + "?" + token,
		success: function (data) {
			layerTypes = data;
		},
		async: false
	});
    
	
	 jQuery.ajax({
        url: "projection/",
        success: function (data) {
            proj_projections = data;
        },
        async: false
    });
	
	
	$("#layertypeBody").empty();
	
		
	if (_id) {
	$("#layerfrm").attr("action", id + "/edit");
		$.ajax({
            url: id + "/" + _id,
            success: function (data) {
				$("#layer_id").val("");
				$("#layer_id").val(data.layerid);
				
				$("#LayerTemplateLayertype").tmpl(data,
					{
						action: "Edit",                                
						layerTypesList: layerTypes
					   
					}
				).appendTo("#layertypeBody");
				
				jQuery("#layer_btnBack").show();
				jQuery("#lyrTypeDiv").show();
				
				layerData=data;	
				loadTemplate(data.layertype.layertypeEn.trim(),"Edit");
				
				$('#maxextent').val(data.maxextent);
                $('#minextent').val(data.minextent);
                if(data.tiled){
                	$('#tiled').val("true");                	
                }
                else{
                	$('#tiled').val("false");
                	
                }
                
				
			}
		});
		
		
		
	}
	else{
	$("#layerfrm").attr("action", id + "/create");	
	$("#LayerTemplateLayertype").tmpl(null,
								{
									action: "Create",                                
									layerTypesList: layerTypes
								   
								}
		).appendTo("#layertypeBody");
		
		jQuery("#layer_btnBack").show();
		jQuery("#lyrTypeDiv").show();
		
	}
    
}

function connect(){
	if(!($('#url').val())) {
		jAlert('Enter Url', 'Layer');
		
		return;
	}
	var type = $('#layertype :selected').text();
	getLayersInfo(type ,$('#url').val());
	jQuery("#lyrTypeDiv").show();
	
}

function getLayersInfo(type, _remoteurl,_act) {
    
	var remoteurl = _remoteurl + 'service=' + type + '&request=GetCapabilities&version=1.1.1'
    var wfs_remoteurl = replaceString(_remoteurl, /wms/gi, 'wfs') + 'service=WFS&version=1.0.0&request=GetCapabilities'

    if (type == 'WMS') {
        layers = {};
        wfsNameArr = {};
        wfsVersion = '';
        $.ajax({
        	type: "GET",
            cache: false,
            url: PROXY_PATH + remoteurl, 
            async: false,
            dataType: "xml",
            success: function (xml) {

                var capabilityXML = new ol.format.WMSCapabilities();
                var obj = capabilityXML.read(xml);
                var capability = obj.Capability.Layer;
				$('#name').empty();
				if(_act!='Edit'){
					$('#name').append($("<option></option>").attr("value", '').text('Please Select'));
				}
				
                for (var i = 0, len = capability.Layer.length; i < len; i++) {
                    if ("Name" in capability.Layer[i]) {
                    	
                    	if(_act!='Edit'){
                    		$('#name').append($("<option></option>").attr("value", capability.Layer[i].Name).text(capability.Layer[i].Name));
                    	}
							layers[capability.Layer[i].Name] = capability.Layer[i];
						
                    }
                }
				
					$("#queryable").val("false")
					$('#queryable').attr("disabled", true); 



					$("#editable").val("false")
					$("#editable").attr("disabled", true); 

					$("#selectable").val("false")
					$('#selectable').attr("disabled", true); 

					$("#exportable").val("false")
					$('#exportable').attr("disabled", true); 


               
			    $('#project_outputFormat').empty();
				 $('#project_outputFormat').append($("<option></option>").attr("value", '').text('Please Select'));
			      jQuery.each(layer_format, function (i, value) {
					jQuery("#project_outputFormat").append(jQuery("<option></option>").attr("value", value.documentformatid).text(value.documentformatEn));
				});
				
				$('#projection_code').empty();
				 $('#projection_code').append($("<option></option>").attr("value", '').text('Please Select'));
				jQuery.each(proj_projections, function (i, value) {
					 jQuery("#projection_code").append(jQuery("<option></option>").attr("value", value.projectionid).text(value.projection));
					
				 });
				
				$('#layer_unit').empty();
				 $('#layer_unit').append($("<option></option>").attr("value", '').text('Please Select'));
				jQuery.each(units, function (i, value) {
					jQuery("#layer_unit").append(jQuery("<option></option>").attr("value", value.unitid).text(value.unitEn));
				});
				
              
                $('#url').attr("readonly", "true");
                $('#btnConnect').attr("disabled", "disabled");
                //$('#btnConnect').hide();
				jQuery('#layerGeneralBody > tr').show();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                jAlert('Error Connecting To Remote Server', 'Layer');
                $('#name').empty();
                $('#name').append($("<option></option>").attr("value", '').text('Please Select'));
            }
        });

        
    }
    if (type == 'WFS') {
        layers = {};
        wfsVersion = '';
        $.ajax({
            type: "GET",
            async: false,
            url: PROXY_PATH + wfs_remoteurl,
            dataType: "xml",
            success: function (xml) {
          
            	var markers = 	xml.getElementsByTagName("FeatureTypeList")[0].childNodes.length;
            	
				$('#name').empty();
				if(_act!='Edit'){
					$('#name').append($("<option></option>").attr("value", '').text('Please Select'));
				}
                for (var i = 1, len = markers; i < len; i++) {
                    layers[xml.getElementsByTagName("FeatureTypeList")[0].childNodes[i].getElementsByTagName("Name")[0].childNodes[0].nodeValue] = xml.getElementsByTagName("FeatureTypeList")[0].childNodes[i].getElementsByTagName("Name")[0].childNodes[0].nodeValue;
                    if(_act!='Edit'){
                    	$('#name').append($("<option></option>").attr("value",xml.getElementsByTagName("FeatureTypeList")[0].childNodes[i].getElementsByTagName("Name")[0].childNodes[0].nodeValue).text(xml.getElementsByTagName("FeatureTypeList")[0].childNodes[i].getElementsByTagName("Name")[0].childNodes[0].nodeValue));
                    }
					
                }
                
				$('#url').attr("readonly", "true");
                $('#btnConnect').attr("disabled", "disabled");
				jQuery('#layerGeneralBody > tr').show();
				
				 $('#project_outputFormat').empty();
				 $('#project_outputFormat').append($("<option></option>").attr("value", '').text('Please Select'));
			      jQuery.each(layer_format, function (i, value) {
					jQuery("#project_outputFormat").append(jQuery("<option></option>").attr("value", value.documentformatid).text(value.documentformatEn));
				});
				
				$('#projection_code').empty();
				 $('#projection_code').append($("<option></option>").attr("value", '').text('Please Select'));
				jQuery.each(proj_projections, function (i, value) {
					 jQuery("#projection_code").append(jQuery("<option></option>").attr("value", value.projectionid).text(value.projection));
					
				 });
				
				$('#layer_unit').empty();
				 $('#layer_unit').append($("<option></option>").attr("value", '').text('Please Select'));
				jQuery.each(units, function (i, value) {
					jQuery("#layer_unit").append(jQuery("<option></option>").attr("value", value.unitid).text(value.unitEn));
				});
				
				
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $('#name').empty();
                $('#name').append($("<option></option>").attr("value", '').text('Please Select'));
                alert('Error in fetching data');
            }
        });
    }
}

function enableLayerURL(_this) {
    
	$('#name').empty();
    $('#name').append($("<option></option>").attr("value", '').text('Please Select'));
    $('#wfsname').empty();
    $('#wfsname').append($("<option></option>").attr("value", '').text('Please Select'));
    $('#projectionBean.code').val('');
    $('#maxextent').val('');
    $('#minextent').val('');
    
	currentLayerType=$("#layertype").val();
	
    if (_this.value) {
        if (_this.value.toUpperCase() == 'WMS') {
        	 $('#url').removeAttr("disabled");
             $('#url').removeAttr("readonly", "");
             $('#btnConnect').removeAttr("disabled");
             $('#url').val('');
          }
        
		else if (_this.value == 'Tilecache') {
			$('#url').removeAttr("disabled");
			$('#url').val('');
            $('#btnConnect').attr("disabled", "disabled");
			
		}
	
		
    }
    else {
        $('#url').attr("disabled", "disabled");
        $('#btnConnect').attr("disabled", "disabled");
        //hideAllLayerTR();
    }
}

function checkLayerExistence(_layerAlias) {
    if (_layerAlias == '') {
        jAlert('Please Enter Alias', 'Layer');
        return;
    }
    $.ajax({
        url: "layer/" + _layerAlias,
        success: function (data) {
            if (data.alias != undefined && data.alias != null) {
                jAlert('Layer Alias Already Exists', 'Layer');
                return;
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //alert('ERROR')
        }
    });
}

function selectWFSLayerName(__layerName) {
    var _layerName = $('#name').val();
    var _wfsLayerName='';
    var _layerType = $('#layertype').val();
    var _wmsUrl = $('#url').val();
    $("#layerFieldBody").empty()

    getLayerInfo(_layerName, _layerName, _layerType, _wmsUrl);
    
}

function getLayerInfo(layer, wfsLayerName, _layerType, _wmsUrl) {
    var layerInfo;
    var wmsformat=null;
    var srs;
    var srsActual;
    var bbox=[];
    
    wfsUrl = replaceString(_wmsUrl, /wms/gi, 'wfs');
    if (layer == '') {
       
       
    }
    else {
        layerInfo = layers[layer];
        if (layerInfo) {
            if (_layerType == 'WMS') {
                wmsformat = layerInfo.formats;
                srs = layerInfo.BoundingBox[0].extent

                	for (var i = 0; i < srs.length; i++) {
					   bbox.push(srs[i]);
					}

                    srsActual = "";

				
                $('#maxextent').val(bbox[0] + ',' + bbox[1] + ',' + bbox[2] + ',' + bbox[3]);
                $('#minextent').val(bbox[0] + ',' + bbox[1] + ',' + bbox[2] + ',' + bbox[3]);
               // $('#outputformat').empty();
               
			   
				 $("#fieldGrid").show('fast');
               // populateLayerFields(wfsLayerName, _wmsUrl);
            }   //if wms

            if (_layerType == 'WFS') {
                var str="";				
                srsActual = layerInfo.srs;               
				//$("#projectionBean").val(srsActual);
				
                var url = wfsUrl + "request=DescribeFeatureType&typeName=" + wfsLayerName + "&maxFeatures=1&outputFormat=application/json";
                $.ajax({                    
                	type: "GET",
                    cache: false,   
                    async: false, 				
                    url: PROXY_PATH + url, 
                	success: function (data) {
                        
						for (var i = 0; i < data.featureTypes[0].properties.length; ++i) {
								if(data.featureTypes[0].properties[i].type.indexOf('gml')==-1){
										str = str + '{ "LayerField": "' + data.featureTypes[0].properties[i].name + '"},';
									
								}
								
								else if (data.featureTypes[0].properties[i].type.indexOf('Surface')>0||data.featureTypes[0].properties[i].type.indexOf('MultiSurface')>0||data.featureTypes[0].properties[i].type.indexOf('Polygon')>0) {
										
									$("#geomtype").val('POLYGON');
								}
								else if(data.featureTypes[0].properties[i].type.indexOf('Line')>0){
									$("#geomtype").val('LINESTRING');
									
								}
								else if(data.featureTypes[0].properties[i].type.indexOf('Point')>0){
									$("#geomtype").val('POINT');
									
								}
								else{
									$("#geomtype").val('');
								}		                
							} 

							var commaidx = str.lastIndexOf(",");
										if(commaidx > 0){
											
											str = str.substr(0,commaidx);
										}
										
										str = '[' + str + ']';            
										str = jQuery.parseJSON(str);
								 
										var markup = "<tr>"+            
											"<td>${LayerField}</td>"+
											"<td><input type='textbox' name='FieldAlias' id='FieldAlias__${LayerField}' value='${LayerField}' /></td>"+
											
											"<td><input type='checkbox' id='Displayable__${LayerField}' class='displayableCheckbox' name='Displayable' value='${LayerField}' checked onclick='toggleCheckbox(this);' /></td>" +           
											"<td><input type='radio' id='Key__${LayerField}' name='Key' value='${LayerField}' /></td>" +                 
										"</tr>";
										$.template( "fieldTemplate", markup );
										$("#layerFieldBody").empty()
										$.tmpl( "fieldTemplate", str ).appendTo( "#layerFieldBody" );


									$("#layerFieldBody").show();

									//set Layer fields
									setSelectedLayerFields(str[0].LayerField);

		

                    }       
                });
				
				
            }   //if wfs
        } //iflayerinfo
    } //else
}

function populateLayerFields(selectedLayer, _featureUrl) {

    var str = "";
	var _wfsurl = _featureUrl.replace(new RegExp( "wms", "i" ), "wfs");
    var layerFields = {};
    var url = _wfsurl + "&version=1.1.0&service=WFS&request=DescribeFeatureType&typeName=" + selectedLayer + "&maxFeatures=1&outputFormat=application/json";
    $("#layerFieldBody").empty();
    $.ajax({
    	type: "GET",
        cache: false,
        url: PROXY_PATH + url, 
        success: function (data) {
		console.log(data);
		
            for (var i = 0; i < data.featureTypes[0].properties.length; ++i) {
                if(data.featureTypes[0].properties[i].type.indexOf('gml')==-1){
                	    str = str + '{ "LayerField": "' + data.featureTypes[0].properties[i].name + '"},';
                    
                }
                
                else if (data.featureTypes[0].properties[i].type.indexOf('Surface')>0||data.featureTypes[0].properties[i].type.indexOf('MultiSurface')>0||data.featureTypes[0].properties[i].type.indexOf('Polygon')>0) {
                		
                	$("#geomtype").val('POLYGON');
                }
                else if(data.featureTypes[0].properties[i].type.indexOf('Line')>0){
                	$("#geomtype").val('LINESTRING');
                	
                }
                else if(data.featureTypes[0].properties[i].type.indexOf('Point')>0){
                	$("#geomtype").val('POINT');
                	
                }
				else{
					$("#geomtype").val('');
				}		                
            }   //for

            var commaidx = str.lastIndexOf(",");
            if(commaidx > 0){
            	
            	str = str.substr(0,commaidx);
            }
            
            str = '[' + str + ']';            
            str = jQuery.parseJSON(str);
     
            var markup = "<tr>"+            
			    "<td>${LayerField}</td>"+
			    "<td><input type='textbox' name='FieldAlias' id='FieldAlias__${LayerField}' value='${LayerField}' /></td>"+
                
                "<td><input type='checkbox' id='Displayable__${LayerField}' class='displayableCheckbox' name='Displayable' value='${LayerField}' checked onclick='toggleCheckbox(this);' /></td>" +           
                "<td><input type='radio' id='Key__${LayerField}' name='Key' value='${LayerField}' /></td>" +                 
			"</tr>";
			$.template( "fieldTemplate", markup );
			$("#layerFieldBody").empty()
            $.tmpl( "fieldTemplate", str ).appendTo( "#layerFieldBody" );


        $("#layerFieldBody").show();

        //set Layer fields
        setSelectedLayerFields(str[0].LayerField);
    }  
});
}

function setSelectedLayerFields(_layerFieldKey) {

    $("#Key__" + _layerFieldKey).attr('checked', true);

    if (selectedLayerFields) {
        // $('input[name^="Displayable"]').attr('checked', false);
        $('[name^="Displayable"]').attr('checked', false);
        $('[name^="FieldAlias"]').attr("disabled", "disabled");

        $.each(selectedLayerFields, function (name, value) {
            $('#Displayable__' + value.layerfield).attr('checked', true);
            $('#FieldAlias__' + value.layerfield).val(value.alias);
            $('#FieldAlias__' + value.layerfield).removeAttr("disabled");
            $('#Key__' + selectedLayerKey).attr('checked', true);

        });
    }
}

function toggleCheckbox(_this) {
    if (_this.checked) {
        $('#FieldAlias__' + _this.value).removeAttr("disabled");
    }
    else {
        $('#FieldAlias__' + _this.value).attr("disabled", "disabled");
    }
}




function loadTemplate(lyrtype,_action){
	//alert(_action);
	var _layerdata=null;
		if(_action=="Edit"){
		_layerdata=layerData;
		}
	
	
	if (lyrtype) {
		currentLayerType= 	lyrtype;
		$("#layerGeneralBody").empty();
		$("#layerStyleBody").empty();
		$("#layerFilterBody").empty();
		
			if (lyrtype.toUpperCase() == 'WMS') {
				
				
				
						if(_action=="Edit"){
						
						
							$("#LayerTemplateWMSForm").tmpl(_layerdata,
								{
									action: _action,
									unitsList: units,
									layerTypesList: layerTypes,
									//formatsList: formats,
									currentLayerType: currentLayerType
								}
								
							 ).appendTo("#layerGeneralBody");
					
					
							$("#LayerStyleTemplate").tmpl(_layerdata,
							{
								
							}
							).appendTo("#layerStyleBody");
					
							$("#LayerFilterTemplate").tmpl(_layerdata,
							{
								
							}
						 ).appendTo("#layerFilterBody");
				
						
						
							$('#btnConnect').hide();
							selectedLayerFields = _layerdata.layerField;
							if(selectedLayerFields!=undefined && selectedLayerFields.length > 0)
							   selectedLayerKey = selectedLayerFields[0].keyfield;							
						   
						   getLayersInfo(lyrtype, _layerdata.url, _action);                    
						   getLayerInfo(_layerdata.name, _layerdata.name, lyrtype, _layerdata.url);
							
							
							$('#url').val(_layerdata.url);
							$('#url').attr("readonly", "true");
							
							
							$('#name_layer').attr("readonly", "true");
							
							$("#layertype").val(currentLayerType);	
							$('#layertype').attr("readonly", "true");		
							$('#alias').attr("readonly", "true");
				            $("#layerstyle").val(_layerdata.style);
							$("#style").val(_layerdata.style);
							$("#filter").val(_layerdata.filter);																		
							
							
							
							$("#project_outputFormat").val(_layerdata.outputformat.documentformatid);
							$("#layer_unit").val(_layerdata.unitBean.unitid);
							$("#projection_code").val(_layerdata.projectionBean.projectionid);
							$("#geomtype").val(_layerdata.geomtype);
							
							
						}
						else{
						
							$("#LayerTemplateWMSForm").tmpl(_layerdata,
									{
										action: _action,
										unitsList: units,
										layerTypesList: layerTypes,
										//formatsList: formats,
										currentLayerType: currentLayerType
									}
								 ).appendTo("#layerGeneralBody");
						
						
								$("#LayerStyleTemplate").tmpl(_layerdata,
								{
									
								}
								).appendTo("#layerStyleBody");
						
								$("#LayerFilterTemplate").tmpl(_layerdata,
								{
									
								}
							 ).appendTo("#layerFilterBody");
							
							
							
							$('#btnConnect').show();
							$('#btnConnect').removeAttr("disabled");
							
							$('#layerGeneralBody > tr').hide();
							$('#urlTR').show();
							$("#visibility").val('true');
							$("#displayinlayermanager").val('true');
						
						}
			}
			
			else if (lyrtype.toUpperCase() == 'WFS') {
				
				
				
						if(_action=="Edit"){
						
						
							$("#LayerTemplateWMSForm").tmpl(_layerdata,
								{
									action: _action,
									unitsList: units,
									layerTypesList: layerTypes,
									//formatsList: formats,
									currentLayerType: currentLayerType
								}
								
							 ).appendTo("#layerGeneralBody");
					
					
							$("#LayerStyleTemplate").tmpl(_layerdata,
							{
								
							}
							).appendTo("#layerStyleBody");
					
							$("#LayerFilterTemplate").tmpl(_layerdata,
							{
								
							}
						 ).appendTo("#layerFilterBody");
				
							$('#btnConnect').hide();
							
							selectedLayerFields = _layerdata.layerField;
							if(selectedLayerFields){
							if(selectedLayerFields.length > 0)
								selectedLayerKey = selectedLayerFields[0].keyfield;
							}
							getLayersInfo(lyrtype, _layerdata.url);                    
							getLayerInfo(_layerdata.name, _layerdata.name, lyrtype, _layerdata.url);
							
							$('#url').val(_layerdata.url);
							$('#url').attr("readonly", "true");
							
							$('#name_layer').attr("readonly", "true");
							
							$("#layertype").val(currentLayerType);	
							$('#layertype').attr("readonly", "true");		
							
							$('#alias').attr("readonly", "true");
							
							
							
					        $("#project_outputFormat").val(_layerdata.outputformat.documentformatid);
							$("#layer_unit").val(_layerdata.unitBean.unitid);
							$("#projection_code").val(_layerdata.projectionBean.projectionid);
							$("#geomtype").val(_layerdata.geomtype);
							
							
							
						}
						else{
						
							$("#LayerTemplateWMSForm").tmpl(_layerdata,
									{
										action: _action,
										unitsList: units,
										layerTypesList: layerTypes,
										//formatsList: formats,
										currentLayerType: currentLayerType
									}
								 ).appendTo("#layerGeneralBody");
						
						
								$("#LayerStyleTemplate").tmpl(_layerdata,
								{
									
								}
								).appendTo("#layerStyleBody");
						
								$("#LayerFilterTemplate").tmpl(_layerdata,
								{
									
								}
							 ).appendTo("#layerFilterBody");
							
							
							
							$('#btnConnect').show();
							$('#btnConnect').removeAttr("disabled");
							
							$('#layerGeneralBody > tr').hide();
							$('#urlTR').show();
							
						
						}
			}
			
			else if (lyrtype == 'Tilecache') {							
				
				$("#LayerTemplateTileForm").tmpl(_layerdata,
								{
									action: _action,
									//unitsList: units,
									layerTypesList: layerTypes,
									//formatsList: formats,
									currentLayerType: currentLayerType
								}
							 ).appendTo("#layerGeneralBody");
					
					
					$("#LayerStyleTemplate").tmpl(_layerdata,
							{
								
							}
						 ).appendTo("#layerStyleBody");
					
					$("#LayerFilterTemplate").tmpl(_layerdata,
							{
								
							}
						 ).appendTo("#layerFilterBody");
				
				

					if(_action=="Edit"){
						$('#url').val(_layerdata.url);
						$('#url').attr("readonly", "true");
						
						//$("#layertype").val(currentLayerType);	
						$('#layertype').attr("readonly", "true");		
							
						$('#alias').attr("readonly", "true");
					}
					
					else{
					
					}
				
				
			}
			
			jQuery("#lyrTypeDiv").show();
			jQuery("#layer_btnSave").show();
			jQuery("#layer_accordion").show(); 
			jQuery("#layer_accordion").accordion({fillSpace: true});
	
		
    }
	else{
	
			$("#layerGeneralBody").empty();
			$("#layerStyleBody").empty();
			$("#layerFilterBody").empty();
	}
	
	


}
function toggleLayerFieldChecked(status) {
	$(".displayableCheckbox").each( function() {
	$(this).attr("checked",status);	
	})
	if(status)
		$('[name^="FieldAlias"]').removeAttr("disabled");
	else{
		$('[name^="FieldAlias"]').attr("disabled", "disabled");
	}
}