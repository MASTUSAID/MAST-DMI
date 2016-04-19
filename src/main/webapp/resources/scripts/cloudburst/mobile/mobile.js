
jQuery(document).ready(function() {
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
	
	var $items = $('#vtab>ul>li');

	$items.click(function(e) {

		var id = e.currentTarget.id;
		switch (id) {
		/*case "masterattribute":
			var masterAttribute=new MasterAttribute(id);
			break;*/
		case "projectattribute":
			var projectAttribute=new ProjectAttribute(id);
			break;
			
		case "projectdata":
			var projectData=new ProjectData(id);
			break;	
					
		default:
		}

		$items.removeClass('selected');
		$(this).addClass('selected');

		var index = $items.index($(this));
		$('#vtab>div').hide().eq(index).show();
	}).eq(0).click();

});