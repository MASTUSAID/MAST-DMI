
var selectedItem = null;
var _userdata = null;
var functionalRoles = null;
var username = null;
function User(_selectedItem)

{

    selectedItem = _selectedItem;
    if (jQuery("#userFormDiv").length <= 0) {
        displayRefreshedUser();

    } else {

        displayUser();
    }
}

function displayRefreshedUser() {
    jQuery.ajax({
        url: "user/" + "?" + token,
        success: function (data) {
            jQuery("#users").empty();
            _userdata = data;
            jQuery.get("resources/templates/studio/" + selectedItem + ".html", function (template) {

                jQuery("#users").append(template);
                jQuery("#users").i18n();
                jQuery('#userFormDiv').css("visibility", "visible");


                jQuery("#userDetails").hide();
                jQuery("#UsersRowData").empty();
                jQuery("#userTable").show();
                jQuery("#user_accordion").hide();

                if (data != null && data != "" && data != undefined)
                {
                    jQuery("#UserTemplate").tmpl(data).appendTo("#UsersRowData");
                    $("#UsersRowData").i18n();
                }
                jQuery("#user_btnSave").hide();
                jQuery("#user_btnBack").hide();
                jQuery("#user_btnNew").show();

                //$("#user_txtSearch").trigger("keyup");
                $("#userTable").tablesorter({
                    headers: {7: {sorter: false}, 8: {sorter: false}},
                    debug: false, sortList: [[0, 0]], widgets: ['zebra']})
                        .tablesorterPager({container: $("#user_pagerDiv"), positionFixed: false})
                        .tablesorterFilter({filterContainer: $("#user_txtSearch"),
                            filterColumns: [0],
                            filterCaseSensitive: false
                        });


            });
        }
    });

}

function displayUser() {

    jQuery("#user_accordion").hide();
    jQuery("#userTable").show();

    jQuery("#user_btnSave").hide();
    jQuery("#user_btnBack").hide();
    jQuery("#user_btnNew").show();

}



var user_role = null;
var user_roleList = null;
var user_ProjectList = null;
var user_ManagerList = null;
var user_genderList = null;
var CreateEditUser = function (_userId) {

    jQuery("#user_btnNew").hide();
    jQuery("#user_btnSave").hide();
    jQuery("#user_btnBack").hide();

    jQuery("#userTable").hide();
    jQuery("#userDetails").show();

    jQuery("#userDetailBody").empty();

    jQuery("#userRoleList").empty();

    jQuery.ajax({
        url: "defaultproject/",
        async: false,
        success: function (data) {
            user_ProjectList = data;

        }
    });


    //add for edit for reporTo dropdown data start
    jQuery.ajax({
        url: "user/?" + token,
        async: false,
        success: function (data) {
            user_ManagerList = data;

        }
    });

    jQuery.ajax({
        url: "role/?" + token,
        async: false,
        success: function (data) {
            functionalRoles = data;

        }
    });


    jQuery.ajax({
        url: "Allgender/?" + token,
        async: false,
        success: function (data) {
            user_genderList = data;

        }
    });



    if (_userId) {

        jQuery.ajax({
            type: 'GET',
            url: "user/userid/" + _userId,
            success: function (data) {
                user_role = data.userRole;

                jQuery("#UserTemplateForm").tmpl(data, {
                    addDatePicker: function () {
                        $("#passwordexpires").live('click', function () {
                            $(this).datepicker('destroy').datepicker({dateFormat: 'yy-mm-dd', minDate: 0}).focus();
                        });
                    }
                }).appendTo("#userDetailBody");
                $("#userDetailBody").i18n();


                //create DD                 
                jQuery.each(user_ProjectList, function (i, projects) {
                    jQuery("#user_defaultproject").append(jQuery("<option></option>").attr("value", projects.name).text(projects.name));
                });

                jQuery.each(user_genderList, function (i, obj) {
                    jQuery("#user_gender").append(jQuery("<option></option>").attr("value", obj.genderId).text(obj.gender));
                });


                //add for new reportTo start
                jQuery.each(user_ManagerList, function (i, manager) {
                    username = $("#name").val();

                    if (user_ManagerList[i].active != false) {
                        if (username != manager.username)
                        {
                            jQuery("#manager_name").append(jQuery("<option></option>").attr("value", manager.id).text(manager.username));
                        }
                    }
                });


                jQuery.each(functionalRoles, function (i, role) {
                    jQuery("#functionalRole").append(jQuery("<option></option>").attr("value", role.name).text(role.description));
                });

                //set DD value


                jQuery("#address").val(data.address);
                jQuery("#user_defaultproject").val(data.defaultproject);

                jQuery("#user_gender").val(data.gender);


                jQuery("#user_active").val((data.active).toString());
                jQuery("#manager_name").val(data.manager_name);
                jQuery("#functionalRole").val("ROLE_ADMIN");
                showSignature("SignatureUser", data.signaturePath);
                jQuery('.accessKey').show();
                jQuery('#name').attr('readonly', true);
                jQuery("#functionalRole").val(data.userRole[0].roleBean.name);

            },
            cache: false
        });
    } else {

        jQuery("#UserTemplateForm").tmpl(null,
                {
                    addDatePicker: function () {
                        $("#passwordexpires").live('click', function () {
                            //$(this).datepicker('destroy').datepicker().focus();
                            $(this).datepicker('destroy').datepicker({dateFormat: 'yy-mm-dd', minDate: 0}).focus();

                        });
                    }
                }
        ).appendTo("#userDetailBody");
        $("#userDetailBody").i18n();

        jQuery('#name').val('');
        jQuery('#name').removeAttr('readonly');
        jQuery('[id="ROLE_USER"]').attr('checked', true);

        jQuery.each(functionalRoles, function (i, role) {
            jQuery("#functionalRole").append(jQuery("<option></option>").attr("value", role.name).text(role.description));
        });

        jQuery.each(user_ProjectList, function (i, projects) {
            jQuery("#user_defaultproject").append(jQuery("<option></option>").attr("value", projects.name).text(projects.name));
        });

        jQuery.each(user_ManagerList, function (i, manager) {
            if (user_ManagerList[i].active == true) {
                jQuery("#manager_name").append(jQuery("<option></option>").attr("value", manager.id).text(manager.username));
            }
        });

        jQuery.each(user_genderList, function (i, obj) {
            jQuery("#user_gender").append(jQuery("<option></option>").attr("value", obj.genderId).text(obj.gender));
        });

        /*
         jQuery.each(user_roleList, function (i, role) {
         if(role.name=='ROLE_USER'){
         jQuery('[id=' + role.name + ']').attr('checked', true);
         return;
         }  
         
         });
         */
        jQuery('.accessKey').hide();

        //jQuery('[id="ROLE_USER"]').attr('checked', true);

    }

    jQuery("#user_accordion").show();
    jQuery("#user_accordion").accordion({fillSpace: true});

    jQuery("#user_btnSave").show();
    jQuery("#user_btnBack").show();



}


var saveUserData = function () {

    jQuery.ajax({
        type: "POST",
        url: "user/create" + "?" + token,
        data: jQuery("#userForm").serialize(),
        success: function (result) {

            if (result == 'true')
            {

                jAlert($.i18n("gen-data-saved"), $.i18n("gen-info"));
                displayRefreshedUser();
            } else if (result == 'duplicate')
            {
                jAlert($.i18n("err-name-exists"), $.i18n("err-alert"));
                // displayRefreshedUser();
            } else if (result == 'false')
            {
                jAlert($.i18n("err-not-saved"), $.i18n("err-alert"));
                //attrDialog.dialog("destroy");


            }


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            alert('err.Message');
        }
    });

}




function saveUser() {
    var ck_username = /^[A-Za-z0-9_]{3,20}$/;
    //alert($(".roleCheckbox").filter(':checked').length);
    $("#userForm").validate({
        rules: {
            name: "required",
            password: "required",
            confirmPassword: {
                required: true,
                equalTo: "#password"
            },
            defaultproject: "required",
            email: {
                required: true,
                email: true
            },
            mobile: {
                required: true,
                number: true,
                minlength: 10,
                maxlength: 10
            },
            user_active: "required",
            passwordexpires: "required",
            lastactivitydate: "required",
            managerName: "required",
            functionalRole: "required",
            user_gender: "required",
            address: {
                required: true,
                maxlength: 100
            }

        },
        messages: {
            name: $.i18n("err-enter-name"),
            password: $.i18n("err-enter-pass"),
            confirmPassword: {
                required: $.i18n("err-pass-confirm-req"),
                equalTo: $.i18n("err-pass-not-matching")
            },
            defaultproject: $.i18n("err-select-def-proj"),
            email: $.i18n("err-enter-valid-email"),
            mobile: {
                required: $.i18n("err-enter-mob-num"),
                number: jQuery.format($.i18n("err-only-numeric")),
                minlength: jQuery.format("Required Minimum {0} characters"),
                maxlength: jQuery.format("Required Maximum {0} characters")

            },
            user_active: $.i18n("err-enter-active"),
            passwordexpires: $.i18n("err-enter-pass-exp"),
            lastactivitydate: $.i18n("err-enter-last-activity-date"),
            managerName: $.i18n("err-select-boss"),
            functionalRole: $.i18n("err-select-role"),
            user_gender: $.i18n("err-select-gender"),
            address: {
                required: $.i18n("err-enter-address"),
                maxlength: jQuery.format(" Maximum {0} characters Allowed")

            }
        }

    });



    if ($("#userForm").valid()) {
        // alert($(".roleCheckbox").filter(':checked').length);
        //saveRecord();
        if (!ck_username.test($('#name').val())) {
            jAlert($.i18n("err-enter-alfanum-value"), $.i18n("err-alert"));
        } else if (validatePassword()) {

            saveUserData();


        }
    }


}




var deleteUser = function (id, name)
{



    jConfirm($.i18n("viewer-confirm-delete-object", name), $.i18n("gen-confirm-delete"), function (response) {

        if (response) {
            jQuery.ajax({
                type: 'GET',
                url: "user/delete/" + id,
                success: function (result) {
                    if (result == true) {
                        jAlert($.i18n("gen-data-deleted"), $.i18n("gen-info"));

                        displayRefreshedUser();
                    }

                    if (result == false)
                    {

                        jAlert($.i18n("err-cant-delete-user-boss"), $.i18n("err-alert"));

                        displayRefreshedUser();
                    }

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                    alert('err.Message');
                }
            });
        }

    });

}

var validatePassword = function (_userName) {

    var valid = true;
    if ($.trim($("#password").val()) == "")
    {
        alert($.i18n("err-enter-pass"));
        valid = false;
        return valid;
    }

    if ($.trim($("#confirmPassword").val()) == "")
    {
        alert($.i18n("err-pass-confirm-req"));
        valid = false;
        return valid;
    }

    if ($.trim($("#password").val()) != $.trim($("#confirmPassword").val()))
    {
        alert($.i18n("err-pass-not-matching"));
        valid = false;
        return valid;

    }
    return valid;

}
