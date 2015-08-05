
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
		case "project":
			var project = new Project(id);
			break;
		case "layer":
			var layer = new Layer(id);
			break;
		case "layergroup":
			var layergroup = new LayerGroup(id);
			break;	
		case "unit":
			var unit = new Unit(id);
			break;
		case "user":
			var user = new User(id);
			break;
		case "role":
			var role = new Role(id);
			break;
		case "bookmark":
			var bookmark = new Bookmark(id);
			break;
		case "maptip":
			var maptip = new MapTip(id);
			break;
		case "dbconnection":
			var dbConn = new DBConnection(id);
			break;
		case "importdata":
			var importdata = new ImportData(id);
			break;
		case "style":
			var style = new Style(id);
			break;
		case "configsetting":
			var configsetting = new ConfigSetting(id);
			break;
		case "calendarsetting":
			var aAnnualHolidayCalendar=new AnnualHolidayCalendar(id);
			break;
			
		case "masterattribute":
			var masterAttribute=new MasterAttribute(id);
			break;
			
		default:
		}

		$items.removeClass('selected');
		$(this).addClass('selected');

		var index = $items.index($(this));
		$('#vtab>div').hide().eq(index).show();
	}).eq(0).click();

});