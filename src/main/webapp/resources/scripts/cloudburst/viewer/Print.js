

var map;
Cloudburst.Print = function(_map, _searchdiv) {


	map = _map;
	searchdiv = _searchdiv;

	$("#tabs-Tool").empty();

	jQuery.get('resources/templates/viewer/print.html', function(
			template) {

		addTab($._("Print"), template);

		$("#print-help").tipTip({
			defaultPosition : "right"
		});
		$('#map_title').html($._('Title'));
		$('#btnPrint').attr("value", $._('print'))
		
		 
	});

};


var printMapDetails=function(){
	
var _value=	$("#title").val();
if( _value=="")
	{
		 jAlert("Please Enter Title first.");
         return false;
	}
	


	
$("btnPrintDC").show();
var _project=$("#ShowProjectNameID").text();

   $.ajax({

        url: STUDIO_URL + "project/" + _project + "?" + token,
		async:false,
		cache: false,
        success: function (projects) {

            jQuery.get('resources/templates/viewer/printLabel.html', function (template) {
					jQuery("#printDiv").empty();
					jQuery("#printDiv").append(template);
				
				jQuery("#layermanagerBody_print").empty();
				var layerVisible={};
				map.getLayers().forEach(function (layer) {
						if (layer.get('aname') != undefined) {
								 if(layer.getVisible()){
									 layerVisible[layer.get('aname')] = true;
							 }else{
								 layerVisible[layer.get('aname')] = false;
							 }
								 
							 
							
						}
					});
					
					
                var $tt = jQuery("#layermanagerTemplate_Print").tmpl(null,

                {
                    projectLayergroup: projects.projectLayergroups,
                    layerDispNameList:layerDispName
                });

                $("#layermanagerBody_print").html($tt);
				
				  for (x in projects.projectLayergroups) {
                	
                	
                    for (y in projects.projectLayergroups[x].layergroupBean.layerLayergroups) {
						
						
						
					
						if(layerVisible[projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias]==true)
						 {
							$("#VisibilityPrint__"+projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias).attr("checked",true);

						 }else{
							 $("#VisibilityPrint__"+projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias).attr('checked', false);;
						 }
						 
						  $("#VisibilityPrint__"+projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias).attr('disabled', true);
						 
						if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.editable){
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editablePrint").attr('src', lyrmgroptions.editable.yes);
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editablePrint").attr('title', $._('layermanager_editable_layer'));
						}else{
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editablePrint").attr('src', lyrmgroptions.editable.no);
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_editablePrint").attr('title', $._('layermanager_editable_layer'));
						 }
						
						if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.selectable){
						    $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectablePrint").attr('src', lyrmgroptions.selectable.yes);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectablePrint").attr('title', $._('layermanager_selectable_layer'));
						 }
						else{
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectablePrint").attr('src', lyrmgroptions.selectable.no);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_selectablePrint").attr('title', $._('layermanager_non-selectable_layer'));
						}
						
					  if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.queryable){
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryablePrint").attr('src', lyrmgroptions.queryable.yes);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryablePrint").attr('title', $._('layermanager_queryable_layer'));
						 }
						else{
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryablePrint").attr('src', lyrmgroptions.queryable.no);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_queryablePrint").attr('title', $._('layermanager_non-queryable_layer'));
						 }
						
						if(projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.exportable){
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportablePrint").attr('src', lyrmgroptions.exportable.yes);
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportablePrint").attr('title', $._('layermanager_exportable_layer'));
						 }
						else{
							 $("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportablePrint").attr('src', lyrmgroptions.exportable.no);
							$("#" + projects.projectLayergroups[x].layergroupBean.layerLayergroups[y].layers.alias + "_exportablePrint").attr('title', $._('layermanager_non-exportable_layer'));
						 }
						
						
					
                    };
                    
                };
				
				
				
				var element = $("#map"); // global variable
					var getCanvas; // global variable
					html2canvas(element, {
					onrendered: function (canvas) {
							var imgageData = canvas.toDataURL("image/png");    	   
						    var newData = imgageData.replace(/^data:image\/png;base64/, "data:image/png;base64");
                           $("#mapImageId1").attr("src",newData);
						        var _title= $("#title").val();

							jQuery('#map_printTitle').empty();
							jQuery('#map_printTitle').text(_title);
				
			                  	var html = $("#printDiv").html();
					var printWindow=window.open('','popUpWindow', 'height=900,width=950,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no,status=no, location=no');
															printWindow.document.write ('<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN""http://www.w3.org/TR/html4/strict.dtd">'+
															'<html><head><title></title>'+
															' <link rel="stylesheet" href="/mast/resources/styles/complete-style.css" type="text/css" />'+
															'<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>'+
															'<script src="../resources/scripts/cloudburst/viewer/Print.js"></script>'+
															 +'</head><body style="overflow-y:auto">'+html+'</body></html>');	
															
															printWindow.document.close();

							
						   
						}
					});
	                    
				
				
			});
			
		
		}
		
		
	});




}


 
function printDataCorrection(){
	$("#btnPrintDC").hide();
				window.print();
				
		}
		
	
	
	 
function print_Map(){
			    $("btnPrintDC").hide();
				window.print();
		}	
		
		
		
		
		