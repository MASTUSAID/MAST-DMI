var selectedItem = null;
var optVals = [];
var projectAreas = [];
var userroledata = null;
var selectedText = null;
var content = null;
var hamletDetails = [];
var adjName = null;
var adjList = [];
var hamletList = null;
var checkAdjEdit = false;
var checkHamletEdit = false;
var HamletName = null;
var HamletAlias = null;
var HamletCode = null;
var hamletLeaderName = null;
var checkeditHam = false;


function Project(_selectedItem) {
    selectedItem = _selectedItem;
    checkAdjEdit = false;
    checkHamletEdit = false;

    jQuery.ajax({
        type: 'GET',
        url: "project/userbyrole/",
        success: function (popdata)
        {
            userroledata = popdata;
        }
    });

    if (jQuery("#ProjectFormDiv").length <= 0) {
        displayRefreshedProject();
    } else {
        displayProject();
        displayRefreshedProject();
    }
}

function displayRefreshedProject() {
    $('body').find("#hamlet-dialog-form").remove();
    $('body').find("#adjudicator-dialog-form").remove();

    $("#hamlet-dialog-form").remove();
    jQuery.ajax({
        url: "project/",
        success: function (data) {
            jQuery("#projects").empty();
            jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {

                jQuery("#projects").append(template);
                jQuery('#ProjectFormDiv').css("visibility", "visible");
                jQuery("#projectDetails").hide();
                jQuery("#ProjectsRowData").empty();
                jQuery("#projectTable").show();
                jQuery("#project_accordion").hide();
                jQuery("#ProjectTemplate").tmpl(data).appendTo("#ProjectsRowData");
                jQuery("#project_btnSave").hide();
                jQuery("#project_btnBack").hide();
                jQuery("#project_btnNew").show();

				if(data.length>0){
                $("#projectTable").tablesorter({
                    headers: {6: {sorter: false}, 7: {sorter: false}},
                    debug: false, sortList: [[0, 0]], widgets: ['zebra']})
                        .tablesorterPager({container: $("#project_pagerDiv"), positionFixed: false})
                        .tablesorterFilter({filterContainer: $("#project_txtSearch"),
                            filterColumns: [0],
                            filterCaseSensitive: false,
                            filterWaitTime: 1000
                        });
						}
            });
        }
    });
   // GetActiveLayer('default', '');
}

function displayProjectNew() {
    checkAdjEdit = false;
    checkHamletEdit = false;
    displayProject();
}

function displayProject() {
    jQuery("#project_accordion").hide();
    jQuery("#projectTable").show();
    jQuery("#project_btnSave").hide();
    jQuery("#project_btnBack").hide();
    jQuery("#project_btnNew").show();
}

var proj_units = null;
var proj_projections = null;
var proj_formats = null;
var proj_editData = null;
var proj_layerTypes = null;
var proj_layerGroup = null;
var sortedLyrGroup = null;
var proj_users = null;
var proj_baselayer = null;
var proj_country = null;
var editableProject;
var proj_projection=null;

var createEditProject = function (_name) {
    checkeditHam = false;
    editableProject = _name;
    optVals = [];
    jQuery("#project_btnNew").hide();
    jQuery("#project_btnSave").hide();
    jQuery("#project_btnBack").hide();
    jQuery("#projectTable").hide();
    jQuery("#projectDetails").show();
    jQuery("#projectGeneralBody").empty();
    jQuery("#projectConfigurationBody").empty();
    jQuery("#projectLayergroupBody").empty();
    jQuery("#projectBaselayerBody").empty();
    jQuery("#projectDisclaimerBody").empty();
    jQuery("#projectUserList").empty();

    jQuery.ajax({
        url: "unit/",
        success: function (data) {
            proj_units = data;
        },
        async: false
    });

    jQuery.ajax({
        url: "layergroup/",
        success: function (lgdata) {
            proj_layerGroup = lgdata;
        },
        async: false
    });

    jQuery.ajax({
        url: "baselayer/",
        success: function (bldata) {
            proj_baselayer = bldata;
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
    jQuery.ajax({
        url: "outputformat/",
        success: function (data) {
            proj_formats = data;
        },
        async: false
    });

    jQuery.ajax({
        url: "user/",
        success: function (userdata) {
            proj_users = userdata;
        },
        async: false
    });

 
     jQuery.ajax({
        url: "projectcontry/",
        success: function (userdata) {
             proj_country = userdata;
         },
         async: false
     });


	  jQuery.ajax({
        url: "projection/",
        success: function (userdata) {
             proj_projection = userdata;
         },
         async: false
     });
	 

	 

    if (_name) {
        jQuery.ajax({
            url: "adjudicators/" + _name,
            success: function (adjudicatorList) {
                adjList = adjudicatorList;
            },
            async: false
        });


        //@
       /* jQuery.ajax({
            url: "hamlet/" + _name,
            success: function (lsthamlet) {
                hamletList = lsthamlet;
            },
            async: false
        });
*/
        jQuery('#name').attr('readonly', true);
        jQuery.ajax({
            url: selectedItem + "/" + _name,
            async: false,
            success: function (data) {
               
			   // sortedLyrGroup = new Array(proj_layerGroup.length);
                // selectedLyrGroups = data.projectLayergroups;
                // var _i;
                // for (_i = 0; _i < selectedLyrGroups.length; _i++) {
                    // for (_j = 0; _j < proj_layerGroup.length; _j++) {
                        // if (selectedLyrGroups[_i].layergroups.name == proj_layerGroup[_j].name) {
                            // sortedLyrGroup[_i] = proj_layerGroup[_j];
                            // proj_layerGroup.splice(_j, 1);
                            // break;
                        // }
                    // }
                // }

                // for (_k = 0; _k < proj_layerGroup.length; _k++, _i++) {
                    // sortedLyrGroup[_i] = proj_layerGroup[_k];
                // }

				
                jQuery("#ProjectTemplateForm").tmpl(data, {}).appendTo("#projectGeneralBody");
                jQuery("#projectConfigurationTemplate").tmpl(data, {}).appendTo("#projectConfigurationBody");
			    jQuery("#projectLayergroupBody").empty();
                jQuery("#ProjectTemplateLayergroup").tmpl(proj_layerGroup, {}).appendTo("#projectLayergroupBody");
                jQuery("#projectBaselayerBody").empty();
                jQuery("#ProjectTemplateBaselayer").tmpl(proj_baselayer, {}).appendTo("#projectBaselayerBody");
				
				jQuery("#ProjectTemplateDisclaimer").tmpl(data, {}).appendTo("#projectDisclaimerBody");
                jQuery("#projectUserList").empty();
                jQuery("#ProjectTemplateUser").tmpl(userroledata).appendTo("#projectUserList");
				
               
                jQuery.each(proj_country, function (i, value) {
                  jQuery("#countryId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
               });
				
		        jQuery.each(proj_units, function (i, value) {
					jQuery("#project_unit").append(jQuery("<option></option>").attr("value", value.unitid).text(value.unitEn));
				});

				jQuery.each(proj_formats, function (i, value) {
					jQuery("#project_outputFormat").append(jQuery("<option></option>").attr("value", value.documentformatid).text(value.documentformatEn));
				});
				
				 jQuery.each(proj_projection, function (i, value) {
					 jQuery("#projection_code").append(jQuery("<option></option>").attr("value", value.projectionid).text(value.projection));
					 jQuery("#displayProjection_code").append(jQuery("<option></option>").attr("value", value.projectionid).text(value.projection));
				 });
				 
		  		 
		         jQuery("#project_outputFormat").val(data.outputformat.documentformatid);
				 jQuery("#project_unit").val(data.unit.unitid);
                 jQuery("#projection_code").val(data.projection.projectionid);
				 jQuery("#displayProjection_code").val(data.projection.projectionid);
               
				 
				jQuery("#hid_idseq").val(data.projectnameid);

                if (data.projectArea.length > 0) {
				   
					if(data.projectArea[0].laSpatialunitgroupHierarchy1!=null){
						jQuery("#countryId").val(data.projectArea[0].laSpatialunitgroupHierarchy1.hierarchyid);
					    getRegionOnCountryChange(data.projectArea[0].laSpatialunitgroupHierarchy1.hierarchyid);
                        }
                    jQuery("#hid_id").val(data.projectArea[0].projectareaid);
					
                    if(data.projectArea[0].laSpatialunitgroupHierarchy2!=null){
                    jQuery("#regionId").val(data.projectArea[0].laSpatialunitgroupHierarchy2.hierarchyid);
					getDistrictOnRegionChange(data.projectArea[0].laSpatialunitgroupHierarchy2.hierarchyid)
					}
					
					if(data.projectArea[0].laSpatialunitgroupHierarchy3!=null){
                    jQuery("#districtId").val(data.projectArea[0].laSpatialunitgroupHierarchy3.hierarchyid);
					getCommuneOnProvinceChange(data.projectArea[0].laSpatialunitgroupHierarchy3.hierarchyid)
					}
                   
				   if(data.projectArea[0].laSpatialunitgroupHierarchy4!=null){
                    jQuery("#CommuneId").val(data.projectArea[0].laSpatialunitgroupHierarchy4.hierarchyid);
					getHamletOnVillageChange(data.projectArea[0].laSpatialunitgroupHierarchy4.hierarchyid)
					}
					
					
					if(data.projectArea[0].laSpatialunitgroupHierarchy5!=null){
                    jQuery("#placeId").val(data.projectArea[0].laSpatialunitgroupHierarchy5.hierarchyid);
					}
					
					
                    jQuery("#villagechairmanId").val(data.projectArea[0].authorizedmember);
                    showSignature("SignatureVillageChairman", data.projectArea[0].authorizedmembersignature);
                    showSignature("SignatureVillageExecutive", data.projectArea[0].executiveofficersignature);
                    showSignature("SignatureDistrictOfficer", data.projectArea[0].landofficersignature);
                    jQuery("#executiveofficerId").val(data.projectArea[0].executiveofficer);
                    jQuery("#districtofficerId").val(data.projectArea[0].landofficer);
                    jQuery("#villagecode").val(data.projectArea[0].certificatenumber);
                    jQuery("#villagepostalcode").val(data.projectArea[0].postalcode);
                    jQuery("#vcmeetingdate").val(data.projectArea[0].vcMeetingDate);
                }


               // populateAdjudicators();

               /* if (hamletList.length > 0)
                {
                    checkHamletEdit = true;
                    hamletDetails = [];
                    for (var i = 0; i < hamletList.length; i++) {
                        HamletName = hamletList[i].hamletName;
                        HamletAlias = hamletList[i].hamletNameSecondLanguage;
                        HamletCode = hamletList[i].hamletCode;
                        hamletLeaderName = hamletList[i].hamletLeaderName;
                        hamletDetails.push(HamletName);
                        hamletDetails.push(HamletAlias);
                        hamletDetails.push(HamletCode);
                        hamletDetails.push(hamletLeaderName);
                    }
                    addHamlet('new');
                }*/

                jQuery('#name').attr('readonly', true);

                $('[class^="tr-"]').hide();

             

                if (data.disclaimer != null && data.disclaimer != "") {
                    jQuery("#chkDisclaimer").attr('checked', true);
                    jQuery('#Disclaimer').css("visibility", "visible");
                    jQuery('#Disclaimer').val(data.disclaimer);
                } else {
                    jQuery("#chkDisclaimer").attr('checked', false);
                    jQuery('#Disclaimer').val("");
                    jQuery('#Disclaimer').css("visibility", "hidden");
                }

                // var layergrouporder = {};
                // jQuery.each(data.projectLayergroups, function (i, layergroupList) {
                    // layergrouporder[layergroupList.grouporder] = layergroupList.layergroups.name;
                // });

				
                // for (var i = 1; i <= data.projectLayergroups.length; i++) {
                    // jQuery("#addedLayerGroupList").append(jQuery("<option></option>").attr("value", layergrouporder[i]).text(layergrouporder[i]));
                    // jQuery("#layerGroupList option[value=" + layergrouporder[i] + "]").remove();
                // }
                
				$.each(data.projectLayergroups, function (i, ob) {
						$.each(ob, function (ind, obj) {
							if(ind=="layergroupBean"){
								$.each(obj, function (ind1, obj1) {
									if(ind1!="layerLayergroups" && ind1=="layergroupid"){
										 jQuery("#" + obj1).attr('checked', true);
										 
									}
								
								});
							}
							
						});
					});

                if (data.projectBaselayers.length > 0) {

                 for (_i = 0; _i < data.projectBaselayers.length; _i++) {				
						if (data.projectBaselayers[_i].baselayers.baselayerEn.indexOf("Google_") > -1) {
							populatebaselayer('Google', data);
						} else if (data.projectBaselayers[_i].baselayers.baselayerEn.indexOf("Bing") > -1) {
							populatebaselayer('Bing', data);
						} else if (data.projectBaselayers[_i].baselayers.baselayerEn.indexOf("Open_") > -1) {
							populatebaselayer('Open', data);
						} else if (data.projectBaselayers[_i].baselayers.baselayerEn.indexOf("MapQuest") > -1) {
							populatebaselayer('MapQuest', data);
						}
			         }
					
                } else {
                    populatebaselayer('Google', '');
                }

				$.each(data.projectBaselayers, function (i, ob) {
						$.each(ob, function (ind, obj) {
							if(ind=="baselayers"){
								$.each(obj, function (ind1, obj1) {
									if( ind1=="baselayerid"){
										 jQuery("#" + obj1).attr('checked', true);
									}
								
								});
							}
							
						});
					});
               // GetActiveLayer('default', '');

                //Setting activelayer and Overview layer
                $('#activelayer').val(data.activelayer);
                $('#overlaymap').val(data.overlaymap);

                jQuery.ajax({
                    url: selectedItem + "/" + _name + "/user" + "?" + token,
                    async: false,
                    success: function (users) {
                        jQuery.each(users, function (i, value) {
                            //jQuery('#'+value).attr('checked', true);
                            $("input[id='" + value + "']").attr('checked', true);
                            jQuery(".tr-" + value).show();
                        });

                    }
                });
            },
            cache: false
        });
    } else {
        jQuery("#ProjectTemplateForm").tmpl(null, {}).appendTo("#projectGeneralBody");
        jQuery("#ProjectTemplateLayergroup").tmpl(null, {}).appendTo("#projectLayergroupBody");
        jQuery("#projectConfigurationTemplate").tmpl(null, {}).appendTo("#projectConfigurationBody");
        jQuery("#ProjectTemplateDisclaimer").tmpl(null, {}).appendTo("#projectDisclaimerBody");

        // add value for project configuration end 
        jQuery("#projectLayergroupBody").empty();
        jQuery("#ProjectTemplateLayergroup").tmpl(proj_layerGroup, {}).appendTo("#projectLayergroupBody");
        jQuery("#projectBaselayerBody").empty();
        jQuery("#ProjectTemplateBaselayer").tmpl(proj_baselayer, {}).appendTo("#projectBaselayerBody");

        //hide all base layer
        jQuery("#projectBaselayerBody tr").hide();
        $("#lyr_type").val('Google');
        populatebaselayer('Google');
        jQuery("#projectUserList").empty();

        jQuery("#ProjectTemplateUser").tmpl(userroledata).appendTo("#projectUserList");
        jQuery('#name').removeAttr('readonly');
        jQuery('#numzoomlevels').val(18);
       
	   
         jQuery.each(proj_country, function (i, value) {
             jQuery("#countryId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
         });
		
		  
        jQuery.each(proj_units, function (i, value) {
            jQuery("#project_unit").append(jQuery("<option></option>").attr("value", value.unitid).text(value.unitEn));
        });

        jQuery.each(proj_formats, function (i, value) {
            jQuery("#project_outputFormat").append(jQuery("<option></option>").attr("value", value.documentformatid).text(value.documentformatEn));
        });
		
		 jQuery.each(proj_projection, function (i, value) {
             jQuery("#projection_code").append(jQuery("<option></option>").attr("value", value.projectionid).text(value.projection));
			 jQuery("#displayProjection_code").append(jQuery("<option></option>").attr("value", value.projectionid).text(value.projection));
         });
		
		
       // jQuery("#temporaryAdjDiv").empty();
        //jQuery("#temporaryHamletDiv").empty();
    }

    $("#vcmeetingdate").live('click', function () {
        $(this).datepicker('destroy').datepicker({dateFormat: 'yy-mm-dd'}).focus();
    });
    jQuery("#project_accordion").show();
    jQuery("#project_accordion").accordion({fillSpace: true});
    jQuery("#project_btnSave").show();
    jQuery("#project_btnBack").show();
};

function changeActiveValue(_this) {
    if (_this.checked) {
        jQuery('#active').val("true");
    } else {
        jQuery('#active').val("false");
    }
}

function changeCosmeticValue(_this) {
    if (_this.checked) {
        jQuery('#cosmetic').val("true");
    } else {
        jQuery('#cosmetic').val("false");
    }

}
function toggleDisclaimer(_this) {
    if (_this.checked) {
        jQuery('#Disclaimer').css("visibility", "visible");
    } else {
        jQuery('#Disclaimer').css("visibility", "hidden");
    }
}

function populateActivelayer(_this) {
    if (_this.checked) {
        GetActiveLayer('add', _this.value);
    } else {
        GetActiveLayer('remove', _this.value);
    }
}

function GetActiveLayer(type, selectedGroup) {
    if (type == 'add' || type == 'remove') {
        jQuery.ajax({
            url: "layergroupName/" + selectedGroup + "?" + token,
            async: false,
            success: function (data) {
				//for (var j = 0; j < data[0].layers.length; j++) {
                for (var j = 0; j < data[0].layerLayergroups.length; j++) {       
                    if (type == 'add') {
                        jQuery('#activelayer').empty();
                        jQuery('#overlaymap').empty();
                        if (!isTilecache(data[0].layerLayergroups[j].layers.alias)) { //data[0].layerLayergroups[0].layers   if (!isTilecache(data[0].layers[j].layer)) {
                            jQuery('#activelayer').append(jQuery("<option></option>").attr("value", data[0].layerLayergroups[j].layers.alias).text(data[0].layerLayergroups[j].layers.alias));
                        }
                        jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", data[0].layerLayergroups[j].layers.alias).text(data[0].layerLayergroups[j].layers.alias));
                    } else if (type == 'remove') {
                        jQuery("#activelayer option[value=" + data[0].layerLayergroups[j].layers.alias + "]").remove();
                        jQuery("#overlaymap option[value=" + data[0].layerLayergroups[j].layers.alias + "]").remove();
                    }
                }
            }
        });
    } else if (type == 'default') {
        jQuery('#activelayer').empty();
        jQuery('#overlaymap').empty();

        jQuery('#activelayer').append(jQuery("<option></option>").attr("value", '').text('Please Select'));
        jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", '').text('Please Select'));

        $('#projectLayergroupBody input[type="checkbox"]:checked').each(function () {
            var lyrGrp = $(this).val();
            jQuery.ajax({
                url: "layergroupName/" + lyrGrp + "?" + token,
                async: false,
                success: function (data) {
					if(data.length>0)
					{
						$.each(data, function (i, ob) {
						$.each(ob, function (ind, obj) {
							if(ind=="layerLayergroups"){
								$.each(obj, function (ind1, obj1) {
									if( ind1=="layers"){
										$.each(obj1, function (ind2, obj2) {
											if( ind1=="alias"){
											 jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", obj2).text(obj2));
											}
										});	
									}
								
								});
							}
							
						});
					});
					}
					
					
                }
            });
        });
    }

    if (type == 'add') {
        optVals.push(selectedGroup);
    } else if (type == 'remove') {
        optVals.pop(selectedGroup);
    } else {
        $('#projectLayergroupBody input[type="checkbox"]:checked').each(function () {
            optVals.push($(this).val());
        });
    }

    jQuery('#activelayer').empty();
    jQuery('#overlaymap').empty();

    jQuery('#activelayer').append(jQuery("<option></option>").attr("value", '').text('Please Select'));
    jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", '').text('Please Select'));

    for (var i = 0; i < optVals.length; i++) {
        var lyrGrp = optVals[i];
        jQuery.ajax({
            url: "layergroupName/" + lyrGrp,
            async: false,
            success: function (data) {
                //for (var j = 0; j < data[0].layers.length; j++)
					//layers[j].layer
					
			    for (var j = 0; j < data[0].layerLayergroups.length; j++)
				{
						
						//jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", data[0].layerLayergroups[j].layers.alias).text(data[0].layerLayergroups[j].layers.alias));
                    jQuery('#activelayer').append(jQuery("<option></option>").attr("value", data[0].layerLayergroups[j].layers.alias).text(data[0].layerLayergroups[j].layers.alias));
                    jQuery('#overlaymap').append(jQuery("<option></option>").attr("value", data[0].layerLayergroups[j].layers.alias).text(data[0].layerLayergroups[j].layers.alias));
                }
            }
        });
    }
}

var saveProjectData = function () {
    jQuery.ajax({
        type: "POST",
        url: "project/create" + "?" + token + "&emailid=" + useremail,
        data: jQuery("#projectForm").serialize(),
        success: function (result) {
            if (result == 'ProjectAdded') {
                jAlert('Data Successfully Saved', 'Project');
				//return false;
                displayRefreshedProject();
            }

            if (result == 'DuplicateName') {
                jAlert('Project Name already Exists', 'Project');
            }

            if (result == 'EnterName') {
                jAlert('Please Enter Project Name', 'Project');
            }

            if (result == 'false') {
                jAlert('Error in Saving Data', 'Project');
				//return false;
               displayRefreshedProject();
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert('err.Message');
        }
    });

};

var saveProject = function () {
    checkAdjEdit = false;
    checkHamletEdit = false;
    $("#projectForm").validate({
        ignore: [],
        rules: {
            "name": "required",
            "activelayer": "required",
            "projection_code": "required",
			"displayProjection_code":"required",
            "project_unit": "required",
            "maxextent": "required",
            "minextent": "required",
            "restrictedextent": "required",
            "numzoomlevels": {
                required: true,
                digits: true
            },
            "displayProjection.code": "required",
            "project_outputFormat": "required",
            "overlaymap": "required",
            "countryId": "required",
			 "regionId":"required",
			 "districtId":"required",
			 "CommuneId":"required",
			 "regioncode": "required"
        },
        messages: {
            "name": "Please Enter Name",
            "projection_code": "Please enter  Projection ",
			"displayProjection_code": "Please enter Display Projection ",
            "project_unit": "Please Select  Unit ",
            "maxextent": "Please Enter  MaxExtent",
            "minextent": "Please Enter  MinExtent",
            "restrictedextent": "Please Enter  RestrictedExtent ",
            "numzoomlevels": {
                required: "Please Enter NumZoomLevels",
                digits: "Please Enter a valid number.  "
            },
            "displayProjection.code": "Please Enter  DisplayProjection  ",
            "project_outputFormat": "Please Enter  OutputFormat  ",
            "activelayer": "Please Select Active Layer",
            "overlaymap": "Please Select Overview Layer",
            "countryId": "Please Select Country Name",
			"regionId": "Please Select Region Name",
			"districtId":"Please Select Province Name",
			"CommuneId":"Please Select Commune Name",
            "regioncode": "Please enter Region Code"
        }
    });

    if ($("#projectForm").valid()) {
		
        // if (adjList.length >= 2) {
            // if (!$('#temporaryHamletDiv').is(':empty')) {
                // if (jQuery('#active').val() == "") {
                    // jQuery('#active').val("false");
                // }

                // if (jQuery('#cosmetic').val() == "") {
                    // jQuery('#cosmetic').val("false");
                // }

                // if ($("#chkDisclaimer").attr('checked')) {
                    // if (!$('#Disclaimer').val()) {
                        // jAlert('Enter Disclaimer ', 'Project');
                        // return;
                    // }
                // }

                // if (!jQuery("#chkDisclaimer").attr('checked')) {
                    // $('#Disclaimer').val("");
                // }

                // if (jQuery('#addedLayerGroupList option').size() > 0) {
                    // jQuery("#addedLayerGroupList option").attr("selected", "selected");
                    // saveProjectData();
                // }

                // if ($('.userCheckbox').filter(":checked").length > 0) {
                    // saveProjectData();
                // } else {
                    // jAlert('Select atleast one user Form Assign user section ', 'Project');
                // }
            // } else {
                // jAlert("Add atleast one hamlet");
            // }
        // } else {
            // jAlert("Add atleast two adjudicator");
            // checkAdjEdit = true;
        // }
    	
    	saveProjectData();
		
    }
    if (!$("#projectForm").valid() == true) {
        jAlert('Please Fill details in All Tabs', 'Project');
    }
};

var deleteProject = function (_projectName,_projectId) {

    jConfirm('Are You Sure You Want To Delete : <strong>' + _projectName + '</strong>', 'Delete Confirmation', function (response) {
        if (response) {
            jQuery.ajax({
                url: "project/delete/" + _projectId + "?" + token,
                success: function () {
                    jAlert('Data Successfully Deleted', 'Project');
                    //back to the list page 
                    displayRefreshedProject();
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert('err.Message');
                }
            });
        }

    });

}

jQuery(function () {
    jQuery("#addLayerGroup").live('click', function () {
        jQuery("#layerGroupList  option:selected").appendTo("#addedLayerGroupList");
    });

    jQuery("#removeLayerGroup").live('click', function () {
        jQuery("#addedLayerGroupList option:selected").appendTo("#layerGroupList");
    });

    jQuery("#upLayerGroup").live('click', function () {
        jQuery('#addedLayerGroupList option:selected').each(function () {
            var newPos = jQuery('#addedLayerGroupList option').index(this) - 1;
            if (newPos > -1) {
                jQuery('#addedLayerGroupList option').eq(newPos).before("<option value='" + jQuery(this).val() + "' selected='selected'>" + jQuery(this).text() + "</option>");
                jQuery(this).remove();
            }
        });
    });

    jQuery("#downLayerGroup").live('click', function () {
        var countOptions = jQuery('#addedLayerGroupList option').size();
        jQuery('#addedLayerGroupList option:selected').each(function () {
            var newPos = jQuery('#addedLayerGroupList option').index(this) + 1;
            if (newPos < countOptions) {
                jQuery('#addedLayerGroupList option').eq(newPos).after("<option value='" + jQuery(this).val() + "' selected='selected'>" + jQuery(this).text() + "</option>");
                jQuery(this).remove();
            }
        });
    });
});

function isTilecache(layeralias) {
    var response = false;
    jQuery.ajax({
        url: "layer/" + layeralias + "?" + token,
        async: false,
        success: function (layer) {
            if (layer.layertype.name == 'Tilecache') {
                response = true;
            } else {
                response = false;
            }
        }
    });
    return response;
}

jQuery(function () {
    $(".lgup,.lgdown").live('click', function () {
        var row = $(this).parents("tr:first");
        if ($(this).is(".lgup")) {
            row.insertBefore(row.prev());
        } else {
            row.insertAfter(row.next());
        }
    });

});

function populatebaselayer(_type) {

    jQuery("#lyr_type").val(_type);
    if (_type == 'Google') {
        jQuery('input[id^="Bing_"]').attr('checked', false);
        jQuery('input[id^="Open_"]').attr('checked', false);
        jQuery('input[id^="MapQuest"]').attr('checked', false);
    } else if (_type == 'Bing') {
        jQuery('input[id^="Google_"]').attr('checked', false);
        jQuery('input[id^="Open_"]').attr('checked', false);
        jQuery('input[id^="MapQuest"]').attr('checked', false);
    } else if (_type == 'Open') {
        jQuery('input[id^="Google_"]').attr('checked', false);
        jQuery('input[id^="Bing_"]').attr('checked', false);
        jQuery('input[id^="MapQuest"]').attr('checked', false);
    } else if (_type == 'MapQuest') {
        jQuery('input[id^="Google_"]').attr('checked', false);
        jQuery('input[id^="Bing_"]').attr('checked', false);
        jQuery('input[id^="Open_"]').attr('checked', false);
    }

    jQuery("#projectBaselayerBody tr").hide();
    jQuery('tr[id^=tr-' + _type + '_]').show();
}

function toggleUserChecked(status) {
    $(".userCheckbox").each(function () {
        $(this).attr("checked", status);
    });

    if (status) {
        $('[class^="tr-"]').show();
    } else {
        $('[class^="tr-"]').hide();
    }
}

function manageProjLink(_this) {
    if (_this.checked == true) {
        jQuery('.tr-' + _this.value).show();
    } else {
        jQuery('.tr-' + _this.value).hide();
    }
}

function getRegionOnCountryChange(id) {
	
		$("#districtId").empty();
		jQuery("#districtId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		
		$("#CommuneId").empty();
		jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		
		$("#placeId").empty();
		jQuery("#placeId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		
		$("#regionId").empty();
	    jQuery("#regionId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		

    if (id != '') {
		jQuery.ajax({
            url: "projectregion/" + id,
            async: false,
            success: function (regiondata) {
                var proj_region = regiondata;
                jQuery.each(proj_region, function (i, value) {
                    jQuery("#regionId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
                });
            }
        });
    }
	
    

}

function getDistrictOnRegionChange(id) {
	
		$("#CommuneId").empty();
		jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		
		$("#placeId").empty();
		jQuery("#placeId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		
	    $("#districtId").empty();
	    jQuery("#districtId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
	
		
    if (id != '') {
	
        jQuery.ajax({
            url: "projectdistrict/" + id,
            async: false,
            success: function (regiondata) {
                var proj_region = regiondata;
                jQuery.each(proj_region, function (i, value) {
                    jQuery("#districtId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
                });
            }
        });
    }
	
	
	}
	
	
	
function getCommuneOnProvinceChange(id) {
	
	$("#CommuneId").empty();
	jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", "").text("Select"));
	
	$("#placeId").empty();
    jQuery("#placeId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		
		
		
    if (id != '') {
		
		
        jQuery.ajax({
            url: "projectvillage/" + id,
            async: false,
            success: function (regiondata) {
                var proj_coomune = regiondata;
                jQuery.each(proj_coomune, function (i, value) {
                    jQuery("#CommuneId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
                });
            }
        });
    }
	
	
	}
	function getHamletOnVillageChange(id) {
		
	$("#placeId").empty();
	jQuery("#placeId").append(jQuery("<option></option>").attr("value", "").text("Select"));
		
    if (id != '') {
		
        jQuery.ajax({
            url: "projecthamlet/" + id,
            async: false,
            success: function (regiondata) {
                var proj_coomune = regiondata;
                jQuery.each(proj_coomune, function (i, value) {
                    jQuery("#placeId").append(jQuery("<option></option>").attr("value", value.hierarchyid).text(value.name));
                });
            }
        });
    }
	
	
	}
	
	
function addTempAdjudicator(id) {
    adjudicatorDialog = $("#adjudicator-dialog-form").dialog({
        autoOpen: false,
        height: 200,
        width: 232,
        resizable: false,
        modal: true,
        buttons: {
            "Save": function ()
            {
                validateAdjudicator(id);
            }
        }
    });
    if (id !== 'new')
        $('#adjudicator_name').val(adjList[id].adjudicatorName);
    else
        $('#adjudicator_name').val('');

    adjudicatorDialog.dialog("open");
}

// function addPersonAdjudicator(id) {
	 // if (adjList === "")  {
        // adjList = [];
    // }

    // adjName = $('#adjudicator_name').val();
    // if (id === 'new') {
        // // Add new adjudicator
    	// adjList.push({adjudicatorName: adjName, id: 0 - adjList.length, signaturePath: '', projectName: ''});
    // } else {
        // // Update
        // adjList[id].adjudicatorName = adjName;
    // }

    // populateAdjudicators();
// }

// function populateAdjudicators() {
    // jQuery("#temporaryAdjDiv").empty();
    // var content1 = "";
    // for (var i = 0; i < adjList.length; i++) {
        // content = '<tr><td><label>' + "" + adjList[i].adjudicatorName + "" + '</label>' +
                // '<input type="hidden" name="project_adjudicatorhid" id= "project_adjudicatorhid" value="' + adjList[i].adjudicatorName + '"> ' + '</td>';
        // content += '<td><img src="" id="imgSignatureAdjudicator' + i + '" alt="Signature" class="signatureImage" />' +
                // '<a href="#" id="linkDeleteSignatureAdjudicator' + i + '" class="deleteLink" onclick="deleteSignature(\'SignatureAdjudicator' + i + '\')" style="margin-left: 10px;display: none;">' +
                // '<i class="fa fa-times"></i> Delete</a>' +
                // '<a href="#" id="linkAddSignatureAdjudicator' + i + '" class="addLink" onclick="uploadSignature(\'SignatureAdjudicator' + i + '\')">' +
                // '<i class="fa fa-plus"></i> Add</a>' +
                // '<input type="hidden" id="hSignatureAdjudicator' + i + '" name="hSignatureAdjudicator" /></td>';
        // content += '<td align="center">' + '<div><a href ="#" title="Edit" onclick="javascript:addTempAdjudicator(' + i + ');"><img src="resources/images/studio/edit.png" title="Edit"/></a></div>' + '</td>';
        // content += '<td align="center">' + '<div><a href="javascript:deleteAdjudicator(' + i + ');"><img src="resources/images/studio/delete.png" title="Delete"/></a></div>' + '</td></tr>';
        // content1 = content1 + content;
    // }
    
    // if (adjList.length > 0) {
        // jQuery("#temporaryAdjDiv").append("<table class='temporaryDivTable'><th class='tableHeader'>Adjudicator Name</th><th class='tableHeader'>Signature</th><th class='tableHeader'>Edit</th><th class='tableHeader'>Delete</th>" + content1 + "</table>");
        // for (var i = 0; i < adjList.length; i++) {
            // showSignature("SignatureAdjudicator" + i, adjList[i].signaturePath);
            // $("#hSignatureAdjudicator" + i).change({ind: i}, function (event) {
                // var hId = $(this).attr('id');
                // //hId = hId.substr();
                // adjList[event.data.ind].signaturePath = $("#hSignatureAdjudicator" + event.data.ind).val();
            // });
        // }
    // }
// }


// function deleteAdjudicator(id) {
    // jConfirm('Are You Sure You Want To Delete : <strong>' + "adjudicator" + '</strong>', 'Delete Confirmation', function (response) {
        // if (response) {
            // adjList = $.grep(adjList, function (item, index) {
                // return id !== index;
            // });
            // populateAdjudicators();
            // if (adjList.length > 0)
                // jQuery("#temporaryAdjDiv").append("<table class='temporaryDivTable'><th class='tableHeader'>Adjudicator Name</th><th class='tableHeader'>Edit</th><th class='tableHeader'>Delete</th>" + content1 + "</table>");
            // jAlert("Data deleted successfully", "Delete");
        // }
    // });
// }

// function validateAdjudicator(id) {
    // $("#adjudicatorformID").validate({
        // rules: {
            // adjudicator_name: "required",
        // },
        // messages: {
            // adjudicator_name: "Please enter Adjudicator Name",
        // }
    // });

    // if ($("#adjudicatorformID").valid()) {
        // // Check name exists
        // adjName = $('#adjudicator_name').val();
        // if (adjName !== '') {
            // for (var i = 0; i < adjList.length; i++) {
                // if (adjList[i].adjudicatorName === adjName) {
                    // if (id === 'new' || id !== i) {
                        // jAlert("Name already exists");
                        // return;
                    // }
                // }
            // }
        // }
        // addPersonAdjudicator(id);
        // adjudicatorDialog.dialog("close");
    // }
// }

// function newHamlet(hamlet) {
    // if (hamlet == 'new')
        // checkeditHam = false;
    // hamletDialog = $("#hamlet-dialog-form").dialog({
        // autoOpen: false,
        // height: 390,
        // width: 300,
        // resizable: false,
        // modal: true,
        // buttons: {
            // "Save": function ()
            // {
                // validateHamlet(hamlet);
            // },
            // "Cancel": function ()
            // {
                // hamletDialog.dialog("destroy");
                // hamletDialog.dialog("close");
            // }
        // },
        // close: function () {
            // hamletDialog.dialog("destroy");
            // hamletDialog.dialog("close");
        // }
    // });
    // if (hamlet != 'new') {
        // $('#hamlet_name').val(hamlet.name);
        // $('#hamlet_alias').val(hamlet.alias);
        // $('#hamlet_code').val(hamlet.code);
        // $('#hamlet_leader_name').val(hamlet.leader);
    // } else {
        // $("#hamlet_name").val("");
        // $("#hamlet_alias").val("");
        // $("#hamlet_code").val("");
        // $("#hamlet_leader_name").val("");
    // }

    // hamletDialog.dialog("open");
// }

// function addHamlet(hamlet) {
    // if (!checkHamletEdit) {
        // checkHamletEdit = true;
        // hamletDetails = [];
    // }

    // hamlet_name = document.getElementById("hamlet_name").value;
    // hamlet_alias = document.getElementById("hamlet_alias").value;
    // hamlet_code = document.getElementById("hamlet_code").value;
    // hamlet_leader = document.getElementById("hamlet_leader_name").value;

    // jQuery("#hamlet_name").val("");
    // jQuery("#hamlet_alias").val("");
    // jQuery("#hamlet_code").val("");
    // $("#hamlet_leader_name").val("");

    // if (checkeditHam) {
        // var k = parseInt(hamlet.id);
        // hamletDetails[k] = hamlet_name;
        // hamletDetails[k + 1] = hamlet_alias;
        // hamletDetails[k + 2] = hamlet_code;
        // hamletDetails[k + 3] = hamlet_leader;
    // } else if (hamlet_name != "" && hamletDetails.indexOf(hamlet_name) == -1) {
        // hamletDetails.push(hamlet_name);
        // hamletDetails.push(hamlet_alias);
        // hamletDetails.push(hamlet_code);
        // hamletDetails.push(hamlet_leader);
    // } else if (hamlet_name != "" && hamletDetails.indexOf(hamlet_name) > -1) {
        // jAlert("Name already exists");
    // }

    // jQuery("#temporaryHamletDiv").empty();
    // var content1 = "";
    // for (var i = 0; i < (hamletDetails.length); i++) {
        // HamletName = hamletDetails[i];
        // HamletAlias = hamletDetails[i + 1];
        // HamletCode = hamletDetails[i + 2];
        // hamletLeaderName = hamletDetails[i + 3];
        
        // if( hamletLeaderName ) {
        	 // hamletLeaderName = hamletLeaderName.replace(/"/g, "&quot;");
             // hamletLeaderNameEsc = hamletLeaderName.replace(/'/g, "\\'");

        // }else
        	// {
        	// hamletLeaderNameEsc ="";
        	// }
       
        // content = '<tr><td>' + '<label id="hamlet_name' + "" + i + "" + '">' + "" + HamletName + "" + '</label><input type="hidden" name="hamletName" value=' + "" + HamletName + "" + '> ' + '</td>';
        // content += '<td>' + '<label id="hamlet_alias' + "" + i + "" + '">' + "" + HamletAlias + "" + '</label><input type="hidden" name="hamletAlias" value=' + "" + HamletAlias + "" + '> ' + '</td>';
        // content += '<td>' + '<label id="hamlet_code' + "" + i + "" + '">' + "" + HamletCode + "" + '</label><input type="hidden" name="hamletCode" value=' + "" + HamletCode + "" + '> ' + '</td>';
        // content += '<td>' + '<label id="hamlet_leader' + "" + i + "" + '">' + "" + hamletLeaderName + "" + '</label><input type="hidden" name="hamletLeaderName" value="' + hamletLeaderName + '"> ' + '</td>';
        // content += '<td align="center">' + '<div><a href ="#" id= ' + i + ' onclick="javascript:editHamlet({code: \'' + HamletCode + '\', name: \'' + HamletName + '\', alias: \'' + HamletAlias + '\', leader: \'' + hamletLeaderNameEsc + '\', id: \'' + i + '\'});"><img src="resources/images/studio/edit.png" title="Edit"/></a></div>' + '</td>';
        // content += '<td align="center">' + '<div><a href ="#" id= ' + i + ' onclick="javascript:deleteHamet({code: \'' + HamletCode + '\', id: \'' + i + '\'});"><img src="resources/images/studio/delete.png" title="Delete"/></a></div>' + '</td></tr>';

        // content1 = content1 + content;
        // i = i + 3;
    // }

    // if (hamletDetails.length > 0)
        // jQuery("#temporaryHamletDiv").append("<table class='temporaryDivTable'><th class='tableHeader'>Hamlet Name</th><th class='tableHeader'>Hamlet Name(Second Language)</th><th class='tableHeader'>Hamlet Code</th><th class='tableHeader'>Leader Name</th><th class='tableHeader'>Edit</th><th class='tableHeader'>Delete</th>" + content1 + "</table>");
    // if (hamlet != 'new')
        // jAlert("Data successfully saved", "Hamlet Info");
// }

// function deleteHamet(hamlet) {
    // var checkdelete = true;
    // jConfirm('Are You Sure You Want To Delete : <strong>' + hamlet.code + '</strong>', 'Delete Confirmation', function (response) {

        // if (response) {
            // if (editableProject != undefined) {
                // jQuery.ajax({
                    // type: 'GET',
                    // async: false,
                    // url: "project/delethamlet/" + hamlet.code + "/" + editableProject,
                    // success: function (result)
                    // {
                        // checkdelete = result;
                    // }
                // });
            // }
            // if (checkdelete) {
                // hamletDetails.splice(hamlet.id, 4);
                // jQuery("#temporaryHamletDiv").empty();
                // var content1 = "";
                // for (var i = 0; i < (hamletDetails.length); i++) {

                    // HamletName = hamletDetails[i];
                    // HamletAlias = hamletDetails[i + 1];
                    // HamletCode = hamletDetails[i + 2];
                    // hamletLeaderName = hamletDetails[i + 3];
                    // hamletLeaderName = hamletLeaderName.replace(/"/g, "&quot;");
                    // hamletLeaderNameEsc = hamletLeaderName.replace(/'/g, "\\'");

                    // content = '<tr><td>' + '<label id="hamlet_name' + "" + i + "" + '">' + "" + HamletName + "" + '</label><input type="hidden" name="hamletName" value=' + "" + HamletName + "" + '> ' + '</td>';
                    // content += '<td>' + '<label id="hamlet_alias' + "" + i + "" + '">' + "" + HamletAlias + "" + '</label><input type="hidden" name="hamletAlias" value=' + "" + HamletAlias + "" + '> ' + '</td>';
                    // content += '<td>' + '<label id="hamlet_code' + "" + i + "" + '">' + "" + HamletCode + "" + '</label><input type="hidden" name="hamletCode" value=' + "" + HamletCode + "" + '> ' + '</td>';
                    // content += '<td>' + '<label id="hamlet_leader' + "" + i + "" + '">' + "" + hamletLeaderName + "" + '</label><input type="hidden" name="hamletLeaderName" value=' + "" + hamletLeaderName + "" + '> ' + '</td>';
                    // content += '<td align="center">' + '<div><a href ="#" id= ' + i + ' onclick="javascript:editHamlet({code: \'' + HamletCode + '\', name: \'' + HamletName + '\', alias: \'' + HamletAlias + '\', leader: \'' + hamletLeaderNameEsc + '\', id: \'' + i + '\'});"><img src="resources/images/studio/edit.png" title="Edit"/></a></div>' + '</td>';
                    // content += '<td align="center">' + '<div><a href ="#" id= ' + i + ' onclick="javascript:deleteHamet({code: \'' + HamletCode + '\', id: \'' + i + '\'});"><img src="resources/images/studio/delete.png" title="Delete"/></a></div>' + '</td></tr>';

                    // content1 = content1 + content;
                    // i = i + 3;
                // }
                // if (hamletDetails.length > 0)
                    // jQuery("#temporaryHamletDiv").append("<table class='temporaryDivTable'><th class='tableHeader'>Hamlet Name</th><th class='tableHeader'>Hamlet Name(Second Language)</th><th class='tableHeader'>Hamlet Code</th><th class='tableHeader'>Leader Name</th><th class='tableHeader'>Delete</th><th class='tableHeader'>Edit</th>" + content1 + "</table>");
                // jAlert("Data deleted successfully", "Delete");
            // } else {
                // jAlert("Hamlet is mapped with spatial unit", "Delete Hamlet");
            // }
        // }
    // });
// }

// function validateHamlet(hamlet) {
    // $("#hamletformID").validate({
        // rules: {
            // hamlet_name: "required",
            // hamlet_alias: "required",
            // hamlet_code: "required",
            // hamlet_leader_name: "required"
        // },
        // messages: {
            // hamlet_name: "Enter Hamlet Name",
            // hamlet_alias: "Enter Hamlet Alias",
            // hamlet_code: "Enter Hamlet Code",
            // hamlet_leader_name: "Enter leader name"
        // }
    // });

    // if ($("#hamletformID").valid()) {
        // addHamlet(hamlet);
        // hamletDialog.dialog("destroy");
        // hamletDialog.dialog("close");
    // }
// }

// function editHamlet(hamlet) {
    // checkeditHam = true;

    // if (editableProject != undefined) {
        // jQuery.ajax({
            // type: 'GET',
            // async: false,
            // url: "project/delethamlet/" + hamlet.code + "/" + editableProject,
            // success: function (result)
            // {
                // checkeditHam = result;
            // }
        // });
    // }

    // if (checkeditHam) {
        // newHamlet(hamlet);
    // } else {
        // jAlert("Hamlet is mapped with spatial unit", "Edit Hamlet");
    // }
// }

function uploadSignature(name) {
    // Reset form
    $("#fileSignature").val('');

    signatureDialog = $("#signature-dialog-form").dialog({
        autoOpen: false,
        height: 200,
        width: 250,
        resizable: false,
        modal: true,
        buttons: {
            "Upload": function () {
                if ($("#fileSignature").val() === '') {
                    jAlert("Select signature for upload.");
                    return;
                }

                var formData = new FormData();
				var flag= true;
				
				$.each($('#fileSignature')[0].files[0], function (ind3, obj3) {
                                                                  	if( ind3=="type"){
																	   
																	   if(obj3=="image/png"  || obj3=="image/jpeg"  || obj3=="image/gif"){
																	     flag =true;
																		 return false;
																		 
																	   }else{
																		flag =true;
																		jAlert("Select File should be of type png,jpeg,gif.");
																		 return false;
																	    }
																	}
																	
                                                            });
															
						
                 if(flag)
				 {
					
				 formData.append("signature", $('#fileSignature')[0].files[0]);	
                $.ajax({
                    type: "POST",
                    url: "project/uploadsignature",
                    mimeType: "multipart/form-data",
                    contentType: false,
                    cache: false,
                    processData: false,
                    data: formData,
                    success: function (data) {
                        if (data) {
                            signatureDialog.dialog("close");
                            showSignature(name, data);
                        } else {
                            jAlert('Failed to upload signature');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        jAlert('Failed to upload signature');
                    }
                });
				 }				 
               

            }
        }
    });

    signatureDialog.dialog("open");
}

function showSignature(name, fileName) {
    // Check if file exists
    result = false;

    if (fileName !== '') {
        $.ajax({
            type: 'GET',
            async: false,
            url: "project/signatureexists/" + fileName,
            success: function (response) {
                if (response) {
				   $("#img" + name).attr('src', '/mast/studio/project/getsignature/' + fileName);
				   $("#h" + name).val(fileName).trigger('change');
					showHideSignature(false, name);
					result = true;
                   
                }
            }
        });
    }

    // Otherwise show add signature link
    if (!result) {
        $("#h" + name).val('').trigger('change');
        showHideSignature(true, name);
    }
}

function deleteSignature(name) {
    jConfirm('Are you sure?', 'Confirmation', function (response) {
        if (response) {
            $.ajax({
                type: 'GET',
                async: false,
                url: "project/deletesignature/" + $("#h" + name).val(),
                success: function (response) {
                    if (response) {
                        $("#h" + name).val('').trigger('change');
                        showHideSignature(true, name);
                    } else {
                        jAlert('Failed to delete signature');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    jAlert('Failed to delete signature');
                }
            });
        }
    });
}

function showHideSignature(hide, name) {
    if (hide) {
        $("#img" + name).hide();
        $("#linkDelete" + name).hide();
        $("#linkAdd" + name).show();
    } else {
        $("#img" + name).show();
        $("#linkDelete" + name).show();
        $("#linkAdd" + name).hide();
    }
}

function RunTopologyChecks() 
{
	activeProject = "New";
    $.ajax({
        type: "GET",
        url: "landrecords/runtopology/" + activeProject,
        async: false,
        success: function (result) 
        {
        	 if (result === "OK") 
        	 {
        		 //jAlert("Topology ran scuccessfully");  
        		 jAlert('Topology ran scuccessfully', 'Topology');
        	 }
        	
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Failed to run topology checks');
        }
    });
}
