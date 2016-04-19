

var qryProject;
var qryLayer;
var qryName;
var criteria;
var description;
var newQry = false;

function SaveQuery(project, layer, criterion, name, desc, newQuery) {
    qryProject = project;
    qryLayer = layer;
    qryName = name;
    criteria = criterion;
    description = desc;
    newQry = newQuery;

    if (name != null && desc == null) {
        loadDescription();
    }
//    $('input[name=WhereExpression]').val(criteria);
//    $('input[name=Layer]').val(qryLayer);
//    $('input[name=Project]').val(qryProject);
}

var queryNameExists = function(queryName){
	$.ajax({
        url: STUDIO_URL + 'savedquery/' + queryName + "?" + token,
        success: function (data) {
        	if(data.name != undefined){
	            jAlert("Query name already exists", "Search");
        	}else{
        		jAlert("Query name doesn't exists", "Search");
        	}
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            jAlert('Error', 'Search');
        }
    });
}

SaveQuery.prototype.toggleSaveQry = function(){
	 if($("#saveQuery").css("display") == 'none'){
			$("#saveQuery").show();
		}else{
			$("#saveQuery").hide();
		}
}

function loadDescription() {
    $.ajax({
        type: "GET",
        url: STUDIO_URL + 'savedquery/' + "description/" + qryName + "?" + token,
        success: function (data) {
            $("#querydesc").val(data);

            $("#qryName").val(qryName);
            
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var err = eval("(" + XMLHttpRequest.responseText + ")");
            
        }
    });
}

SaveQuery.prototype.Save = function() {

    if ($('#qryName').val() == "") {
        jAlert("Please enter query name");
    } else {
        //store value in respective hidden field
        $('input[name=Name]').val($('#qryName').val());
    }
    if ($('#querydesc').val() == "") {
        jAlert("Please enter description for query");
    } else {
        $('input[name=Description]').val($('#querydesc').val());
    }

    $('input[name=WhereExpression]').val(criteria);
    $('input[name=Layer]').val(qryLayer);
    $('input[name=Project]').val(qryProject);

    //alert($('#Name').val() + "---" + $('#Description').val() + "---" + $('#WhereExpression').val() + "---" + $('#Layer').val() + "---" + $('#Project').val());
    var name = $('#qryName').val();
    var urlCreate = STUDIO_URL + 'savedquery' + "/create?";
    var urlEdit = STUDIO_URL + 'savedquery' + "/edit?";
    $.ajax({
        type: "POST",
        url: (newQry) ? urlCreate : urlEdit,
		//url: urlCreate,
        data: $("#frmSaveQry").serialize(),
        cache: false,
        async: false,
        success: function (data) {
        	if(data.name != undefined){
        		jAlert("Data successfully saved.", "Save Query");
        	}else{
        		jAlert("Failed to save query. Please check if query name is unique", "Save Query");
        	}
           hideDiv();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var err = eval("(" + XMLHttpRequest.responseText + ")");
            jAlert(err.Message)
        }
    });
}

function hideDiv() {
	 if($("#saveQuery").css("display") != 'none'){
		$("#saveQuery").hide();
	 }
}