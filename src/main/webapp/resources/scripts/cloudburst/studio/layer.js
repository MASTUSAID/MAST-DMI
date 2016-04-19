
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
		        	if(data){
			            jAlert('Data Successfully Deleted', 'Delete');
			            layerArray = [];
			            populateList(id);
		        	}else{
		        		jAlert('Failed to remove layer, layer is associated with more than one project', 'Delete');
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
            
            
            //hideAllLayerTR();
            //$('.urlTR').hide();
            //$('.typeTR').hide();
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
               
	            
                
                $("#layerTable").tablesorter({ 
                headers: {6: {sorter: false  },  7: {  sorter: false } },		
                debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                .tablesorterPager({ container: $("#layer_pagerDiv"), positionFixed: false })
                .tablesorterFilter({ filterContainer: $("#layer_txtSearch"),                           
                    filterColumns: [0],
                    filterCaseSensitive: false
                });
                          
            });
        },
        cache: false
    });
 
}

function saveLayerData1() {
	
	var url = $("#layerfrm").attr("action");
	
	/* By Aparesh
	  var data = $("#layerfrm").serialize();
	 
	 $.post(url+ "?" + token,
			 data,                
	 function(_data){                    
		 jAlert('Data Successfully Saved', 'Layer');
		populateList('layer');
	 });*/
	 
	 
	 jQuery.ajax({
	        type: "POST",              
	        url: url+ "?" + token,
	        data: jQuery("#layerfrm").serialize(),
	        async:false,
			success: function () {        
	                                   
	            jAlert('Data Successfully Saved', 'Layer');
	           
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
	
	if (currentLayerType == 'WFS') {
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
                projectionBean: "required",
                //outputformat: "required",
                maxextent: "required",
                geomtype:"required"
				},
				messages: {
	                layertype: "Please Enter  Type",
	                name: "Please Select Layer Name",
	                alias: {
	                    required: "Please enter Alias",
	                    noSpace: "No white space please"
	                }
				}
				
	        });
	        
    }
	
	if (currentLayerType == 'Tilecache') {
	        $("#layerfrm").validate({
	            rules: {
	                url: "required",
					layertype: "required",
	                //name: "required",
	                alias: {
	                    required: true,
	                    noSpace: true
	                },
					maxextent: "required"
	            },
	            messages: {
	                layertype: "Please Enter  Type",
	                //name: "Please Select Layer Name",
	                alias: {
	                    required: "Please enter Alias",
	                    noSpace: "No white space please"
	                }
	            }
	        });
	        
    }
	
	else if (currentLayerType == 'BING' || currentLayerType == 'GOOGLE') {
	        $("#layerfrm").validate({
	            rules: {
	                layertype: "required",
	                name: "required",
	                alias: {
	                    required: true,
	                    noSpace: true
	                }
	            },
	            messages: {
	                layertype: "Please Enter  Type",
	                name: "Please Select Layer Name",
	                alias: {
	                    required: "Please enter Alias",
	                    noSpace: "No white space please"
	                }
	            }
	        });
	        
    }
	else {
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
                projectionBean: "required",
                outputformat: "required",
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
                projectionBean: "Please enter  Projection",
                outputformat: "Please Select  Format",
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
/*
function showLayerTR(_layertype) {
    _type = _layertype.toLowerCase();
    hideAllLayerTR();
    $('.commonTR').show();
    $('.' + _type + 'TR').show();
    $('.wmsTD').show();
}

function hideAllLayerTR() {
    $('.commonTR').hide();
    $('.wmsTR').hide();
    $('.wfsTR').hide();
}

function showLayerTRForGoogleBing() {
    hideAllLayerTR();
    $('.commonTR').show();
    $('.wmsTD').hide();
    $('.urlTR').hide();
}
function showTilecacheTR() {
    hideAllLayerTR();
    $("#aliasextend").show();
	$("#numzoom").show();   
    $('.urlTR').show();
}
*/

var units = null;
    var prj = null;
    var formats = null;
    var editData = null;
    var layerTypes = null;
    var layerGroup = null;
    var users = null;
	var layer_format=null;

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
    
	$("#layertypeBody").empty();
	
		
	if (_id) {
	$("#layerfrm").attr("action", id + "/edit");
		$.ajax({
            url: id + "/" + _id + "?" + token,
            success: function (data) {
				
				
				$("#LayerTemplateLayertype").tmpl(data,
					{
						action: "Edit",                                
						layerTypesList: layerTypes
					   
					}
				).appendTo("#layertypeBody");
				
				
				jQuery("#layer_btnBack").show();
				jQuery("#lyrTypeDiv").show();
				
				layerData=data;	
				loadTemplate(data.layertype.name,"Edit");
				//by Aparesh on 24nov2011
				
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

                var capabilityXML = new OpenLayers.Format.WMSCapabilities();
                var obj = capabilityXML.read(xml);
                var capability = obj.capability;
				$('#name').empty();
				if(_act!='Edit'){
					$('#name').append($("<option></option>").attr("value", '').text('Please Select'));
				}
				
                for (var i = 0, len = capability.layers.length; i < len; i++) {
                    if ("name" in capability.layers[i]) {
                    	
                    	if(_act!='Edit'){
                    		$('#name').append($("<option></option>").attr("value", capability.layers[i].name).text(capability.layers[i].name));
                    	}
							layers[capability.layers[i].name] = capability.layers[i];
						
                    }
                }
                //showLayerTR(type);
                //$('#name').empty();
                //$('#name').append($("<option></option>").attr("value", '').text('Please Select'));

               // $.each(layers, function (val, text) {
                //    $('#name').append($("<option></option>").attr("value", text.name).text(text.name));
               // });
               // $('#name').droplistFilter();
              
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
            //url: "../Proxy.ashx?" + remoteurl,
            url: PROXY_PATH + wfs_remoteurl,
            dataType: "xml",
            success: function (xml) {
                var capabilityXML = new OpenLayers.Format.WFSCapabilities();
                var obj = capabilityXML.read(xml);
                var capability = obj.featureTypeList;
                wfsVersion = obj.version;
				$('#name').empty();
				if(_act!='Edit'){
					$('#name').append($("<option></option>").attr("value", '').text('Please Select'));
				}
                for (var i = 0, len = capability.featureTypes.length; i < len; i++) {
                    layers[capability.featureTypes[i].name] = capability.featureTypes[i];
                    if(_act!='Edit'){
                    	$('#name').append($("<option></option>").attr("value", capability.featureTypes[i].name).text(capability.featureTypes[i].name));
                    }
					
                }
                
				$('#url').attr("readonly", "true");
                $('#btnConnect').attr("disabled", "disabled");
                //$('#btnConnect').hide();
				jQuery('#layerGeneralBody > tr').show();
								
                //showLayerTR(type);
                //$('#outputformat').empty();
                //$(new Option('Please Select', '')).appendTo('#outputformat');
                //$(new Option('GML2', 'GML2')).appendTo('#outputformat');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // var err = eval("(" + XMLHttpRequest.responseText + ")");            
                $('#name').empty();
                //$(new Option('Please Select', '')).appendTo('#name');
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
            //hideAllLayerTR();
            //$('.urlTR').show();
        }
        
		else if (_this.value == 'Tilecache') {
			//alert('tile');
			$('#url').removeAttr("disabled");
			$('#url').val('');
            $('#btnConnect').attr("disabled", "disabled");
			//showTilecacheTR();
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
    

    getLayerInfo(_layerName, _layerName, _layerType, _wmsUrl);
    
}

function getLayerInfo(layer, wfsLayerName, _layerType, _wmsUrl) {
    var layerInfo;
    var wmsformat=null;
    var srs;
    var srsActual;
    var bbox;
    
    wfsUrl = replaceString(_wmsUrl, /wms/gi, 'wfs');
    if (layer == '') {
        $('#outputformat').empty();
        $('#projectionBean.code').empty();
    }
    else {
        layerInfo = layers[layer];
        if (layerInfo) {
            if (_layerType == 'WMS') {
                wmsformat = layerInfo.formats;
                srs = layerInfo.srs;
                $.each(srs, function (val, text) {
                    if (layerInfo.bbox[val]) {
                        bbox = layerInfo.bbox[val].bbox;
                        srsActual = layerInfo.bbox[val].srs;
                    }
                });

                $('#maxextent').val(bbox[0] + ',' + bbox[1] + ',' + bbox[2] + ',' + bbox[3]);
                $('#minextent').val(bbox[0] + ',' + bbox[1] + ',' + bbox[2] + ',' + bbox[3]);
                $('#outputformat').empty();
                
                $.each(wmsformat, function (val, text) {
                    //$('#outputformat').append($("<option></option>").attr("value", text).text(text));					
					$.each(layer_format, function (key,lyrformat ) {
				
						if(text==lyrformat.name){
							$('#outputformat').append($("<option></option>").attr("value", lyrformat.name).text(lyrformat.name));
							
						}
					});
					
					
                });
				
				
                
               
                
                $("#projectionBean").val(srsActual);
                if ($("#projectionBean").val() == 'EPSG:4326') {
                    $("#unitBean.name").val("dd");

                }
                $("#fieldGrid").show('fast');
                populateLayerFields(wfsLayerName, wfsUrl);
            }   //if wms

            if (_layerType == 'WFS') {
                //$("#version").val(wfsVersion);
                //$("#Alias").val(layerInfo.title);
                //$("#featurens").val(layerInfo.featureNS);
                //$('#outputformat').empty();
               // $(new Option('Please Select', '')).appendTo('#outputformat');
               // $(new Option('GML2', 'GML2')).appendTo('#outputformat');
				

				
                srsActual = layerInfo.srs;               
				$("#projectionBean").val(srsActual);
				
                var url = wfsUrl + "request=DescribeFeatureType&typeName=" + wfsLayerName + "&maxFeatures=1";
                $.ajax({                    
                	type: "GET",
                    cache: false,                    
                    url: PROXY_PATH + url, 
                	success: function (data) {
                        var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
                        var responseText = featureTypesParser.read(data);
                        var featureTypes = responseText.featureTypes;
                        featureNS = responseText.targetNamespace;

                        for (var i = 0; i < featureTypes[0].properties.length; ++i) {
                            if (featureTypes[0].properties[i].type.contains('gml')) {
														
                                
								if (featureTypes[0].properties[i].type.contains('MultiSurface')||featureTypes[0].properties[i].type.contains('Polygon')) {
                		
									$("#geomtype").val('POLYGON');
								}
								else if(featureTypes[0].properties[i].type.contains('Line')){
									$("#geomtype").val('LINESTRING');
									
								}
								else if(featureTypes[0].properties[i].type.contains('Point')){
									$("#geomtype").val('POINT');
									
								}
								else{
									$("#geomtype").val('');
								}
								
                            }
                        }   //for
                    }       //success
                });
				
				
            }   //if wfs
        } //iflayerinfo
    } //else
}

function populateLayerFields(selectedLayer, _featureUrl) {
    var str = "";
	var _wfsurl = _featureUrl.replace(new RegExp( "wms", "i" ), "wfs");
    var layerFields = {};
    var url = _wfsurl + "&version=1.1.0&service=WFS&request=DescribeFeatureType&typeName=" + selectedLayer + "&maxFeatures=1";
    $("#layerFieldBody").empty();
    $.ajax({
        //url: "../Proxy.ashx?" + _featureUrl + "&version=1.1.0&service=WFS&request=DescribeFeatureType&typeName=" + selectedLayer + "&maxFeatures=1",
    	type: "GET",
        cache: false,
        url: PROXY_PATH + url, 
        success: function (data) {
		console.log(data);
            var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
            var responseText = featureTypesParser.read(data);
            var featureTypes = responseText.featureTypes;
            featureNS = responseText.targetNamespace;
            totalColumnWidth = 0; //re-initialize columnwidth

            for (var i = 0; i < featureTypes[0].properties.length; ++i) {
                //if (!featureTypes[0].properties[i].type.contains('gml')) {
            	if(featureTypes[0].properties[i].type.indexOf('gml')==-1){
                	//if (i == featureTypes[0].properties.length - 1) {
                      //  str = str + '{ "LayerField": "' + featureTypes[0].properties[i].name + '"}';
                    //}
                    //else {
                        str = str + '{ "LayerField": "' + featureTypes[0].properties[i].name + '"},';
                    //}
                }
                
                else if (featureTypes[0].properties[i].type.indexOf('Surface')>0||featureTypes[0].properties[i].type.indexOf('MultiSurface')>0||featureTypes[0].properties[i].type.indexOf('Polygon')>0) {
                		
                	$("#geomtype").val('POLYGON');
                }
                else if(featureTypes[0].properties[i].type.indexOf('Line')>0){
                	$("#geomtype").val('LINESTRING');
                	
                }
                else if(featureTypes[0].properties[i].type.indexOf('Point')>0){
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
            $('#Displayable__' + value.field).attr('checked', true);
            $('#FieldAlias__' + value.field).val(value.alias);
            $('#FieldAlias__' + value.field).removeAttr("disabled");
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
							
							selectedLayerFields = _layerdata.layerFields;
							if(selectedLayerFields.length > 0)
								selectedLayerKey = selectedLayerFields[0].keyfield;
							
							
							$('#url').val(_layerdata.url);
							$('#url').attr("readonly", "true");
							
							$("#name").val(_layerdata.name);
							$('#name').attr("readonly", "true");
							
							$("#layertype").val(currentLayerType);	
							$('#layertype').attr("readonly", "true");		
							
							$('#alias').attr("readonly", "true");
				
							$("#geomtype").val(_layerdata.geomtype);
							
							$('#outputformat').append($("<option></option>").attr("value", 'Please select').text('Please select'));
							$("#unitBean.name").val(_layerdata.unitBean.name);
							
							//$("#outputformat.name").val(_layerdata.outputformat.name);
							$("#projectionBean.code").val(_layerdata.projectionBean.code);
							$("#layerstyle").val(_layerdata.style);
							
							$("#style").val(_layerdata.style);
							$("#filter").val(_layerdata.filter);																		
							
							getLayersInfo(lyrtype, _layerdata.url, _action);                    
							//getLayerInfo(data.name, data.wfsname, _type, _url);	//by Aparesh for remove wfs name
							getLayerInfo(_layerdata.name, _layerdata.name, lyrtype, _layerdata.url);
							
							$("#outputformat").val(_layerdata.outputformat.name);
							
							
							
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
						
						
							$("#LayerTemplateWFSForm").tmpl(_layerdata,
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
							
							selectedLayerFields = _layerdata.layerFields;
							if(selectedLayerFields.length > 0)
								selectedLayerKey = selectedLayerFields[0].keyfield;
							
							
							$('#url').val(_layerdata.url);
							$('#url').attr("readonly", "true");
							
							$("#name").val(_layerdata.name);
							$('#name').attr("readonly", "true");
							
							$("#layertype").val(currentLayerType);	
							$('#layertype').attr("readonly", "true");		
							
							$('#alias').attr("readonly", "true");
				
							$("#geomtype").val(_layerdata.geomtype);
							
							//$('#outputformat').append($("<option></option>").attr("value", 'Please select').text('Please select'));
							//$("#unitBean.name").val(_layerdata.unitBean.name);
							
							
							$("#projectionBean.code").val(_layerdata.projectionBean.code);
							//$("#layerstyle").val(_layerdata.style);
							
							$("#style").val(_layerdata.style);
							$("#filter").val(_layerdata.filter);																		
							
							//getLayersInfo(lyrtype, _layerdata.url);                    
							
							//getLayerInfo(_layerdata.name, _layerdata.name, lyrtype, _layerdata.url);
							
							//$("#outputformat").val(_layerdata.outputformat.name);
							
							
							
						}
						else{
						
							$("#LayerTemplateWFSForm").tmpl(_layerdata,
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