

var map;

var searchdiv;
var uploadFilepath=null;
var associationIds=null;
var uploadFilename=-null;

Cloudburst.FileUpload = function (_map, _searchdiv,_layerName,_featureGid) {
   
    map = _map;
    searchdiv = _searchdiv;
    showResultsinDialog = true;
    var _layer=layer.name;
    var _associationIds=associationIds;
	var serverurl=window.location.protocol+'//'+window.location.host+window.location.pathname;	
	$("#tabs-Tool").empty();
	
	$.ajax({ 
		type: "GET",
		url: STUDIO_URL + "attachment/"+_layer+"/gid/"+_featureGid+ "?" + token,		
		success: function (attachedFiles) {					
			$("#AttchFileListBody").empty();
			
			var htmlStr = '';
			
			$.each(
			attachedFiles, function (key, val) {
            var filepath = serverurl + val.filepath
            htmlStr = htmlStr + '<tr id="' + _layer + '_' + val.associationid + '">';
            htmlStr = htmlStr + '<td align="left" class="padcellup fileName-TD"><a href="' + filepath + '" target="_blank">' + val.filename + '</a></td>';
            htmlStr = htmlStr + '<td align="center" class="padcellup deleteFileTD"><a href="#" OnClick="deleteAttachedFile(' + "'" + _layer + "'" + ',' + "'" + val.associationid + "'" + ');"><img src="resources/images/viewer/delete.png" boder="0"></a></td></tr>';
        });		
			jQuery('#AttchFileListBody').append(htmlStr);
			
		}
	});
	
	
	
	jQuery.get('resources/templates/viewer/fileupload.html', function(template) {
		//Add tad
		addTab('File Upload',template);
		
		$("#fileupload-help").tipTip({defaultPosition:"right"});
				
    	jQuery.get('fileupload', function (template1) {
    		
    		$("#fileAttachmentDiv").append(template1);
			
	        $('<input type="hidden" name="ajaxUpload" value="true" />').insertAfter($("#file"));
					$("#fileuploadForm").attr("action", "fileupload?"+ token);
					$("#fileuploadForm").ajaxForm({ 
						success: function(filepath_name) {
							
							if(filepath_name.indexOf("HTTP Status 500") != -1){
								//jAlert('File size is greater than permissible limit');								
								$('#errmsg').html('File size is greater than permissible limit');
							}else{
								$('#errmsg').html('');
								filepath_name=filepath_name.replace("<PRE>","");
								filepath_name=filepath_name.replace("</PRE>","");
								
								var pathArr=filepath_name.split("|");											
								
								uploadFilepath=pathArr[0];
								uploadFilename=pathArr[1];
	
								var selRow = $('#tablegrid1').jqGrid('getGridParam', 'selarrrow');
	
								//var fieldVal = featureGeom[selRow[0] - 1][1]; 							
								
									var filename=uploadFilename;
									var associationid=_featureGid;		//SET AS GID IN aTTACHMENT CONTROLLER
									var layername=_layer;
									var keyfield=uniqueField;								
									var desc=$('#fileDesc').val();
									var filepath=uploadFilepath;
									var extension=/[^.]+$/.exec(filename)[0];
									
									//set the hidden field
									
									$('#associationid').val(associationid);
									$('#layername').val(layername);
									$('#keyfield').val(keyfield);
									$('#filename').val(filename);
									$('#filepath').val(filepath);
									$('#extension').val(extension);
									
									var fileArr=[];
									$("tr td .fileName-TD").each(function(){
									fileArr.push($(this).text());
									});		
									//if(jQuery("#"+layername+"_"+associationid).length==0){		
									
										if($.inArray(filename, fileArr) <= -1){
										$.ajax({ 
											type: "POST",
											url: STUDIO_URL + "attachment/create" + "?" + token,
											data: $("#fileuploadForm").serialize(),
											success: function (fileurl) {																		
											var _fileurl=serverurl+fileurl;
											
											var markup="";
											markup = markup + '<tr id="' + layername+'_'+associationid+ '">';									
											markup = markup + '<td align="left" class="padcellup fileName-TD"><a href="'+_fileurl+'" target="_blank">'+filename+'</a></td>';
											markup = markup+'<td align="center" class="padcellup"><a href="#" OnClick="deleteAttachedFile('+"'"+layername+"'"+','+"'"+associationid+"'"+');"><img src="resources/images/viewer/delete.png" boder="0"></a></td></tr>';
											
											
											
											jQuery('#AttchFileListBody').append(markup);
											//add row
											}
										});
									
									}
									else{
																		
									 jAlert('File Already Added, Please Choose Another File','File Upload');
									}
									attachmentLabelTranslations();
							}
								
						},
						error: function (xhr, status) {
				            jAlert('File size is greater than permissible limit');
				        }
					});
	
	    });
    
    });
    
};


function parseUploadedFile() {
	var _uploadFilepath=uploadFilepath	
	var lat= $('#latitude').val()
	var lon =$('#longitude').val();
	
	if(lat && lon){
		if(lat != lon){
			
			$.ajax({ 
				url: "parseFile/",
				type: "POST",
				data: { filePath: _uploadFilepath, seperator:",",latitude:lat,longitude:lon},
				success: function (colName) {
					
				}
			});
			
			
		}
		else{
			alert('Please select different columns for Latitude and Longitude');
		}


	}
	else{

	 alert('Please select Latitude and Longitude fields.');
	}

	
}


function deleteAttachedFile(layername, associateId) {
    // var r=confirm("Do you want to Delete Association id: "+associtionid);
    jConfirm('Are You Sure You Want To Delete : ', 'Delete Confirmation', function (response) {

        if (response == true) {
            // jQuery('#'+layername+'_'+associateId).remove();
            $.ajax({
                type: "POST",
                url: STUDIO_URL + "attachment/delete/" + associateId,
                success: function (resp) {

                    if(resp=="1"){
                		jQuery('#' + layername + '_' + associateId).remove();
                	}
            		else if(resp=="2"){
            			jAlert("unable to delete the file from Database","Delete");
            			
            		}
            		else if(resp=="3"){
            			
            			jAlert("Unable to locate the file","Delete");
            			
            		}
            		else{            			
            			jAlert("Unable to Delete the file","Delete");
            			
            		}

                }

            });
        } else {
            return;
        }

    });

}