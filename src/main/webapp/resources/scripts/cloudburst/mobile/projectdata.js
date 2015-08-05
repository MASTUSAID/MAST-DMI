var selectedItem=null;
var DataList=null;

function ProjectData(_selectedItem)
{
	selectedItem=_selectedItem;

	jQuery.ajax({
		url: "projectname/",
		async:false,
		success: function (data) {
			DataList=data;
		}
	});

	displayRefreshedProjectData();
}


function displayRefreshedProjectData(_project)
{
	jQuery.ajax({
		url: "projectdata/",
		async:false,
		success: function (data) 
		{

			jQuery("#projectdata-div").empty();
			jQuery.get("resources/templates/mobile/" + selectedItem + ".html", function (template) {

				jQuery("#projectdata-div").append(template);

				jQuery('#projectDataFormDiv').css("visibility", "visible");		    			    	

				//$("#projectData_project").val(_project);

				jQuery("#projectDataTemplate").tmpl(data).appendTo("#AttchFileListBody");

				jQuery("#category_sel").append(jQuery("<option></option>").attr("value", "All").text("All")); 
				jQuery.each(DataList, function (i, _dataobj) {

					jQuery("#category_sel").append(jQuery("<option></option>").attr("value", _dataobj.name).text(_dataobj.name)); 

				}); 
				
				// for displaying selected dropdown after save
				$("#category_sel").val(_project);
				//jQuery("#ProjectDataLayerTemplate").tmpl().appendTo("#projectDataBody");
				jQuery("#projectData-accordion").accordion({fillSpace: true});  
				
				$("#projectDataTable").tablesorter({ 
            		headers: {5: {sorter: false  },  7: {  sorter: false } },	
            		debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                   .tablesorterPager({ container: $("#projectData_pagerDiv"), positionFixed: false })
                   .tablesorterFilter({ filterContainer: $("#masterAttr_txtSearch"),                           
                       filterColumns: [0],
                       filterCaseSensitive: false,
                       filterWaitTime:1000 
                   });
					   

			});

		}
	});
}

function uploadFile()
{


	var project=$("#category_sel").val();
	var formData = new FormData();
	var file = $( '#fileUploadSpatial')[0].files[0];

	var alias=$("#alias").val();


	if(project==="All" )
	{	
		jAlert('Please Select Project name to upload','upload');

		displayCategory();
		//displayRefreshedProjectData();


	}

   else if(alias==="")
	{
		jAlert('Please Enter Alias','upload');
	}


	else if(typeof(file)==="undefined")
	{
	
		jAlert('Please Select file to upload','upload');
	}

	

	else
	{
		formData.append("spatialdata",file); 
		formData.append("ProjectID",project);
		formData.append("alias",alias);
		$.ajax({
			url: 'upload/',
			type: 'POST',
			data:  formData,
			mimeType:"multipart/form-data",
			contentType: false,
			cache: false,
			processData:false,
			success: function(data, textStatus, jqXHR)
			{	
				if(data=="mbtiles"){
					jAlert('Please Enter mbtile file','Upload');
					
				}
				else
					{
				jAlert('File uploaded','upload');
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

var deleteProjectData= function (id,alias) 
{	
	jConfirm('Are You Sure You Want To Delete : <strong>' + alias + '</strong>', 'Delete Confirmation', function (response) {
		var project=$("#category_sel").val();
		if (response) {
			jQuery.ajax({          
				type: 'GET',
				url: "projectdata/delete/"+id,
				success: function () 
				{ 

					jAlert('Data Successfully Deleted', 'Project Data');

					displaySelectedCategory(project);

				},
				error: function (XMLHttpRequest, textStatus, errorThrown) 
				{	                    
					jAlert('Error in Deleting Data', 'Project Data');
				}
			});
		}

	});

}

function  displaySelectedCategory (name) 
{	
	if(name!='')	
	{

		jQuery.ajax({          
			type: 'GET',
			url: "projectdata/display/"+name,

			success: function (categorydata) 
			{ 
				jQuery("#AttchFileListBody").empty();
				
				if(categorydata.length!=0 && categorydata.length!=undefined)
			{
					jQuery("#projectDataTemplate").tmpl(categorydata).appendTo("#AttchFileListBody");
				
				$("#projectDataTable").tablesorter({ 
            		headers: {5: {sorter: false  },  7: {  sorter: false } },	
            		debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                   .tablesorterPager({ container: $("#projectData_pagerDiv"), positionFixed: false })
                   .tablesorterFilter({ filterContainer: $("#masterAttr_txtSearch"),                           
                       filterColumns: [0],
                       filterCaseSensitive: false,
                       filterWaitTime:1000 
                   });
			}
			},

		});
	}

}


$(function() {

  $("table:first").tablesorter({
    theme : 'blue',
    // initialize zebra striping and resizable widgets on the table
    widgets: [ "zebra", "resizable" ],
    widgetOptions: {
      resizable_addLastColumn : true
    }
  });

  $("table:last").tablesorter({
    theme : 'blue',
    // initialize zebra striping and resizable widgets on the table
    widgets: [ "zebra", "resizable" ],
    widgetOptions: {
      resizable: true,
      // These are the default column widths which are used when the table is
      // initialized or resizing is reset; note that the "Age" column is not
      // resizable, but the width can still be set to 40px here
      resizable_widths : [ '10%', '10%', '40px', '10%', '100px' ]
    }
  });

});


function test()
{
alert("abc");	
}