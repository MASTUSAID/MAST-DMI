

var map;
var _project='';
Cloudburst.Bookmark = function(_map, _searchdiv) {
   // alert(projectName); 
    map = _map;    
    this.map = map;
    searchdiv = _searchdiv;
    _project=project;
    
	//removeChildElement("sidebar","layerSwitcherContent");
    $("#tabs-Tool").empty();
	
	//$("#layerSwitcherContent").hide();
        $.ajax({
        		
            url: STUDIO_URL + "bookmark/project/"+project + "?" + token,
            success: function (bookmarks) {           
                
            	jQuery.get('resources/templates/viewer/bookmark.html', function(template) {
            		
            		//$("#"+searchdiv).append(template);
            		//Add tad
            		addTab($._('bookmark'),template);
            		
            		$("#bookmark-help").tipTip({defaultPosition:"right"});
            		
            		$("#addbk").hide();
            		
            		$("#bookmarkstar").click(function(){
            			$("#addbk").toggle();            			
            		});
					
					$("#bk-save").click(function(){
						createBookmark();
            		});
					
            		jQuery("#bokkmarkBody").empty();

            		jQuery("#bookmarkTemplate").tmpl(null,

            		{
            			bookmarkList : bookmarks
            		}

            		).appendTo("#bokkmarkBody");
            		
            		translateBookmarks();
            	  });

            }

        });
  

};

function translateBookmarks(){
	$('.bk-s').html($._('Save'));
	$('.zoomlink-s').html($._('bookmark_zoom'));
	$('.removebklink-s').html($._('bookmark_remove'));
}

var zoomBookmark = function(bookmarkName) {

    var currentExtent = map.getExtent().toBBOX();

    if (bookmarkName) {
        $.ajax({
            url: STUDIO_URL + "bookmark/" + bookmarkName + "?" + token,
            success: function (data) {                
                var bounds = new OpenLayers.Bounds(data.minx, data.miny, data.maxx, data.maxy);
                map.zoomToExtent(bounds);
            }
        });
    }

};


var createBookmark = function() {

    var currentExtent = map.getExtent().toBBOX();   
    var arrCurrentExtent = new Array();
    arrCurrentExtent = currentExtent.split(",");
    
    if ($("#name").val() == '') {        
        jAlert('Enter Bookmark Name', 'Bookmark');
		return;
    }
    
    
    $("#description").val($("#name").val());
    $("#minx").val(arrCurrentExtent[0]);
    $("#miny").val(arrCurrentExtent[1]);
    $("#maxx").val(arrCurrentExtent[2]);
    $("#maxy").val(arrCurrentExtent[3]);
	$("#bkProjectName").val(_project);
  

    $.ajax({
        type: "POST",
        url: STUDIO_URL + "bookmark/create" + "?" + token,
        data: $("#BookmarkForm").serialize(),
        success: function () {
              
            //$("#Bookmark").append($("<option></option>").attr("value", $("#name").val()).text($("#name").val()));                                    
            
			$("#name").val('');
			var bookmark = new Cloudburst.Bookmark(map,"sidebar");

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var err = eval("(" + XMLHttpRequest.responseText + ")");
            alert(err.Message)
        }
    });
};


var removeBookmark = function(bookmarkName) {
   
    //var bookmarkName = $("#Bookmark").val();         
    if (bookmarkName) {

        jConfirm('Are You Sure You Want To Delete : <strong>' + bookmarkName + '</strong>', 'Delete Confirmation', function (r) {

            if (r) {
                $.ajax({
                    url: STUDIO_URL + "bookmark/delete/" + bookmarkName + "?" + token,
                    success: function () {
                       // $("#"+bookmarkName).remove();
                    	
                    	var bookmark = new Cloudburst.Bookmark(map,"sidebar");
                        
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert('Error', 'Bookmark');
                    }
                });
           }

        });

    }

    else {
        
        
    
    }
};

var removeAll = function() {

   // jConfirm('Are You Sure You Want To Delete all Bookmarks', 'Delete Confirmation', function (r) {

     //   if (r) {
            $.ajax({
                url: STUDIO_URL + "bookmark/delete/project/" + project+"/" + "?" + token,
                success: function () {
                    $("#Bookmark").empty(); 
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert('Error', 'Bookmark');
                }
            });
    //   }

  //  });


};

Cloudburst.Bookmark.prototype.toggle = function() {
	if ($('#bookmarkdiv').dialog('isOpen')) {
		//$('#printdiv').dialog('close');
	} else {
		$('#bookmarkdiv').dialog('open');
	}
};