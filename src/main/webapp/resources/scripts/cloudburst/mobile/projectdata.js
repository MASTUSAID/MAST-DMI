var selectedItem = null;
var DataList = null;

function ProjectData(_selectedItem)
{
    selectedItem = _selectedItem;

    jQuery.ajax({
        url: "projectname/",
        async: false,
        success: function (data) {
            DataList = data;
        }
    });

    displayRefreshedProjectData();
}


function displayRefreshedProjectData(_project)
{
    jQuery.ajax({
        url: "projectdata/",
        async: false,
        success: function (data)
        {

            jQuery("#projectdata-div").empty();
            jQuery.get("resources/templates/mobile/" + selectedItem + ".html", function (template) {

                jQuery("#projectdata-div").append(template);
                jQuery("#projectdata-div").i18n();
                
                jQuery('#projectDataFormDiv').css("visibility", "visible");

                //$("#projectData_project").val(_project);

                jQuery("#projectDataTemplate").tmpl(data).appendTo("#AttchFileListBody");
                $("#AttchFileListBody").i18n();
                
                jQuery("#category_sel").append(jQuery("<option></option>").attr("value", "All").text($.i18n("gen-all")));
                jQuery.each(DataList, function (i, _dataobj) {

                    jQuery("#category_sel").append(jQuery("<option></option>").attr("value", _dataobj.name).text(_dataobj.name));

                });

                // for displaying selected dropdown after save
                $("#category_sel").val(_project);
                //jQuery("#ProjectDataLayerTemplate").tmpl().appendTo("#projectDataBody");
                jQuery("#projectData-accordion").accordion({fillSpace: true});

                $("#projectDataTable").tablesorter({
                    headers: {4: {sorter: false}, 6: {sorter: false}},
                    debug: false, sortList: [[0, 0]], widgets: ['zebra']})
                        .tablesorterPager({container: $("#projectData_pagerDiv"), positionFixed: false})
                        .tablesorterFilter({filterContainer: $("#masterAttr_txtSearch"),
                            filterColumns: [0],
                            filterCaseSensitive: false,
                            filterWaitTime: 1000
                        });


            });

        }
    });
}

function uploadFile()
{


    var project = $("#category_sel").val();
    var formData = new FormData();
    var file = $('#fileUploadSpatial')[0].files[0];

    var alias = $("#alias").val();


    if (project === "All")
    {
        jAlert($.i18n("err-select-proj-first"), $.i18n("err-alert"));

        displayCategory();
        //displayRefreshedProjectData();


    } else if (alias === "")
    {
        jAlert($.i18n("err-enter-alias"), $.i18n("err-alert"));
    } else if (typeof (file) === "undefined")
    {

        jAlert($.i18n("err-select-file-to-upload"), $.i18n("err-alert"));
    } else
    {
        formData.append("spatialdata", file);
        formData.append("ProjectID", project);
        formData.append("alias", alias);
        $.ajax({
            url: 'upload/',
            type: 'POST',
            data: formData,
            mimeType: "multipart/form-data",
            contentType: false,
            cache: false,
            processData: false,
            success: function (data, textStatus, jqXHR)
            {
                if (data == "mbtiles") {
                    jAlert($.i18n("err-select-mbtiles"), $.i18n("err-alert"));

                } else
                {
                    jAlert($.i18n("reg-file-uploaded"), $.i18n("gen-info"));
                    //displayRefreshedProjectData(project);
                    displaySelectedCategory(project);
                    $('#fileUploadSpatial').val('');
                    $('#alias').val('');
                }

            }

        });
        //displaySelectedCategory(project);
    }
}

var deleteProjectData = function (id, alias)
{
    jConfirm($.i18n("viewer-confirm-delete-object", alias), $.i18n("gen-confirm-delete"), function (response) {
        var project = $("#category_sel").val();
        if (response) {
            jQuery.ajax({
                type: 'GET',
                url: "projectdata/delete/" + id,
                success: function ()
                {

                    jAlert($.i18n("gen-data-deleted"), $.i18n("gen-info"));

                    displaySelectedCategory(project);

                },
                error: function (XMLHttpRequest, textStatus, errorThrown)
                {
                    jAlert($.i18n("err-not-deleted"), $.i18n("err-alert"));
                }
            });
        }

    });

}

function  displaySelectedCategory(name)
{
    if (name != '')
    {

        jQuery.ajax({
            type: 'GET',
            url: "projectdata/display/" + name,
            success: function (categorydata)
            {
                jQuery("#AttchFileListBody").empty();

                if (categorydata.length != 0 && categorydata.length != undefined)
                {
                    jQuery("#projectDataTemplate").tmpl(categorydata).appendTo("#AttchFileListBody");
                    $("#AttchFileListBody").i18n();
                    
                    $("#projectDataTable").tablesorter({
                        headers: {5: {sorter: false}, 7: {sorter: false}},
                        debug: false, sortList: [[0, 0]], widgets: ['zebra']})
                            .tablesorterPager({container: $("#projectData_pagerDiv"), positionFixed: false})
                            .tablesorterFilter({filterContainer: $("#masterAttr_txtSearch"),
                                filterColumns: [0],
                                filterCaseSensitive: false,
                                filterWaitTime: 1000
                            });
                }
            },
        });
    }

}


$(function () {

    $("table:first").tablesorter({
        theme: 'blue',
        // initialize zebra striping and resizable widgets on the table
        widgets: ["zebra", "resizable"],
        widgetOptions: {
            resizable_addLastColumn: true
        }
    });

    $("table:last").tablesorter({
        theme: 'blue',
        // initialize zebra striping and resizable widgets on the table
        widgets: ["zebra", "resizable"],
        widgetOptions: {
            resizable: true,
            // These are the default column widths which are used when the table is
            // initialized or resizing is reset; note that the "Age" column is not
            // resizable, but the width can still be set to 40px here
            resizable_widths: ['10%', '10%', '40px', '10%', '100px']
        }
    });

});


function test()
{
    alert("abc");
}