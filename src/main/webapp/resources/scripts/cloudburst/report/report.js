
jQuery(document).ready(function() {
	
			var report=new Report('report');
			

});


var selectedItem=null;
function Report(_selectedItem)
{
$.ajaxSetup ({cache: false});	//Always Refresh From server
	
	$('#_loader').hide();
	
	jQuery.ajaxSetup({
        beforeSend: function () {
            $('#_loader').show();
        },
        complete: function () {
            $('#_loader').hide();
        },
        success: function () {
            $('#_loader').hide();
        },
        error: function () {
            $('#_loader').hide();
        }
    });
		
	displayReport(_selectedItem);

}

function displayReport(_selectedItem){

	var selectedItem=_selectedItem;
		jQuery("#report-div").empty();
		jQuery.get("resources/templates/report/" + selectedItem + ".html", function (template) {
	    			    	
	    	jQuery("#report-div").append(template);
	    	
			jQuery('#reportFormDiv').css("visibility", "visible");		    			    	
	    	
			
		
		});		//end-jquery.get
			
   
}