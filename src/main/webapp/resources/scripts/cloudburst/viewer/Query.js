

var map; 
var selectedLayer;
var actualLayerName;
var featureNS;
var prefix;
var type;
var url;

var displayableCols;
var uniqueField = "ID";
var featCount;
var featFetchCount = 100;
var maxFeatureCount = 50;
var valueIndex;
var unique;
var keyValue;
var CONST_SELECT_LAYER = "Select Layer";
var CONST_SELECT_QUERY = "Select Query";
//Amitabh (Temporary)
var layerMap = [];

var showResultsinDialog = false;

Cloudburst.Query = function(_showResultsinDialog) {
    //map = map1;
    showResultsinDialog = _showResultsinDialog;
    
	//$('#sidebar').empty();
    //removeChildElement(_parentDiv,excludedDiv)
	
	removeChildElement("sidebar","layerSwitcherContent");		
	
	$("#layerSwitcherContent").hide();
    
	jQuery.get('resources/templates/viewer/QueryBuilder.html', function(template) {
    	$('#sidebar').append(template);
        $('#layers').change(onLayerChange);
        $("#fields_1").change(onFldChange);
        $('#selectQuery').change(onQueryChange);
      
        if (populateFeatureLayers())
            onLayerChange();
		
		
		/*
        $("#querycontent").dialog({
            width: 370,
            resizable: false,
            title: '<img style="vertical-align: middle;" src="resources/images/layouts/sql.png" /><span style="vertical-align: middle;"> Query Builder </span>',
            close: function (ev, ui) {
                $('#querycontent').dialog('destroy');
            }
        });
		*/
    });
}

function populateFeatureLayers() {
    var lyrCount = map.getNumLayers();
    $('#layers').empty();
    $('#layers').append($("<option></option>").attr("value", CONST_SELECT_LAYER).text(CONST_SELECT_LAYER));
    for (var i = 0; i < lyrCount; i++) {
        if (map.layers[i] instanceof OpenLayers.Layer.WMS && map.layers[i].name.indexOf("Cosmetic_") == -1) {
            if (map.layers[i].visibility == true) {
                if (isLayerAdded(map.layers[i].name)) {
                    if (map.layers[i].queryable) {
                        $('#layers').append($("<option></option>").attr("value", map.layers[i].name).text(map.layers[i].name));
                    }
                }
            }
        }
    }

    if (lyrCount > 0) {
        $('#layers').get(0).selectedIndex = 0;
        return true;
    } else {
        return false;
    }
}

function isLayerAdded(lyrName) {
    var bFlag = true;

    var count = $('#layers option').size();
    for (var i = 0; i < count; i++) {
        var value = $("#layers option[value=" + lyrName + "]").text();
        if (value == lyrName) {
            bFlag = false;
            break;
        }
    }
    return bFlag;
}

function onLayerChange() {
    var selected = $("#layers option:selected");
    if (selected.text() == CONST_SELECT_LAYER) {
        $('#fields_1').empty();
        $('#values').empty();
        $('#selectQuery').empty();
        $("#whereClause").val('');
    } else {
        actualLayerName = selected.text();

        $.ajax({
            url: STUDIO_URL + 'layer/' + actualLayerName,
            success: function (data) {
                url = map.getLayersByName(actualLayerName)[0].url;
                url = replaceString(url, /wms/gi, 'wfs');  
                
                //Amitabh
                //selectedLayer = layerMap[actualLayerName];
                selectedLayer = data.name;
                //Amitabh (Temporary)
                layerMap[actualLayerName] = selectedLayer;
                
                var pos = selectedLayer.indexOf(":");
                prefix = selectedLayer.substring(0, pos);
                type = selectedLayer.substring(++pos);

                $('#fields_1').empty();
                $('#values').empty();

                populateFields(data);
                getFeatureCount();
                populateSaveQueries();
            }
        });
    }
}

function getFeatureCount() {
	var _url = PROXY_PATH + url + "&request=GetFeature&service=WFS&version=1.0.0&resultType=hits&typeName=" + selectedLayer + "&propertyname=" + uniqueField;
    $.ajax({
        url:  _url,
        dataType: "xml",
        success: function (data) {
            var response = new OpenLayers.Format.GML.v2({
                featureType: type,
                gmlns: 'http://www.opengis.net/gml',
                featureNS: featureNS,
                featurePrefix: prefix,
                extractAttributes: true
            }).read(data);

            if (data != null) {
                featCount = parseInt(data.childNodes[0].attributes[0].childNodes[0].data);
            } else {
                featCount = 0;
            }
        }
    });
}

function populateFields(data) {

    displayableCols = data.layerFields;
    if(displayableCols.length > 0)
    	uniqueField = displayableCols[0].keyfield;
    
    var _url = PROXY_PATH + url + "&request=DescribeFeatureType&service=WFS&version=1.0.0&typeName=" + selectedLayer + "&maxFeatures=1";
    $.ajax({
        url: _url,
        success: function (data) {
            var featureTypesParser = new OpenLayers.Format.WFSDescribeFeatureType();
            var responseText = featureTypesParser.read(data);
            var featureTypes = responseText.featureTypes;
            featureNS = responseText.targetNamespace;

            for (var i = 0; i < featureTypes[0].properties.length; ++i) {
                if (featureTypes[0].properties[i].type.contains("gml:GeometryPropertyType")) {
                    var l = displayableCols.length;
                    displayableCols[l] = []);
                    var obj = new Object();
                    obj.Name = featureTypes[0].properties[i].name;
                    obj.Alias = featureTypes[0].properties[i].name;
                    displayableCols[l] = obj;
                    continue;
                }
                $('#fields_1').append($("<option></option>").attr("value", featureTypes[0].properties[i].name).text(featureTypes[0].properties[i].name));
            }
        }
    });
}

function getFeatureValues(selectedField) {
    var filter = new OpenLayers.Filter.Comparison({
        type: OpenLayers.Filter.Comparison.GREATER_THAN,
        property: uniqueField,
        value: valueIndex
    });

    var filter_1_0 = new OpenLayers.Format.Filter({
        version: "1.0.0"
    });
    var xml = new OpenLayers.Format.XML();
    var xmlFilter = xml.write(filter_1_0.write(filter));
    var _url = PROXY_PATH + url + "&request=GetFeature&service=WFS&version=1.0.0&typeName=" + selectedLayer + "&propertyname=" + selectedField + "&filter=" + xmlFilter + "&maxFeatures=" + featFetchCount;
    $.ajax({
        url: _url,
        dataType: "xml",
        async:false,
        success: function (xml) {
            var gmlFeatures = new OpenLayers.Format.GML.v2({
                featureType: type,
                gmlns: 'http://www.opengis.net/gml',
                featureNS: featureNS,
                featurePrefix: prefix,
                extractAttributes: true
            }).read(xml);

            valueIndex += featFetchCount;
            parseFeatures(gmlFeatures, selectedField);
        }
    });
}

function onFldChange() {
    var selected = $("#fields_1 option:selected");
    var selectedField = selected.text();

    valueIndex = 0;
    unique = []);
    $('#values').empty();
    getFeatureValues(selectedField);
}

function fetchMoreValues() {

    var selectedField = $('#fields_1').val();
    getFeatureValues(selectedField);
}

function parseFeatures(features, selectedField) {
    var i = unique.length;
    var arr = unique;
    var x = 0;
    for (var feat in features) {
        for (var j in features[feat].attributes) {
            if(j == selectedField)
                arr[x + i] = features[feat].attributes[j];
        }
        x++;
    }

    $('#values').empty();
    unique = sortAttributeValues(arr);
    for (var k = 0; k < unique.length; k++) {
        $('#values').append($("<option></option>").attr("value", k).text(unique[k]));
    }
//    $('#values').get(0).selectedIndex = 0;

}


$("#fields_1").live("dblclick", function () {
    var selected = $("#fields_1 option:selected");
    var temp = $("#whereClause").val();
    var pos = getCaret();

    var s1 = temp.substring(0, pos);
    var s2 = temp.substring(++pos);
    s1 += " " + selected.text();
    s1 += s2
  
    $("#whereClause").val(s1);
});

$("#values").live("dblclick", function () {
    var selected = $("#values option:selected");
    var temp = $("#whereClause").val();

    var pos = getCaret();
    var s1 = temp.substring(0, pos);
    var s2 = temp.substring(pos);

    if (!isNumeric(selected.text())) {
        s1 += "'" + selected.text() + "'";
    } else {
        s1 += " " + selected.text();
    }
    s1 += s2;

    $("#whereClause").val(s1);
});

function ButtonClick(value) {
    var temp = $("#whereClause").val();
    var pos = getCaret();

    var s1 = temp.substring(0, pos);
    var s2 = temp.substring(pos);
    s1 += " " + value;
    s1 += s2;
    $("#whereClause").val(s1);

}

function clearText() {
    $("#whereClause").val('');
}

function cancelDialog() {
    $('#querycontent').dialog('destroy');
}

function sortResultInDesc(filters, result) {

    var filter_1_0 = new OpenLayers.Format.Filter({
        version: "1.1.0"
    });

    createXML();
    var xml = new OpenLayers.Format.XML();
    var xmlFilter = xml.write(filter_1_0.write(filters));

    dataPost = dataPost.replace("${layer}", "\"" + selectedLayer + "\"");
    dataPost = dataPost.replace("${featureNS}", "\"" + featureNS + "\"");
    dataPost = dataPost.replace("${filter}", xmlFilter);
    dataPost = dataPost.replace("${uniqueFld}", uniqueField);

    dataPost = dataPost.replace("${SortOrder}", "DESC");

    var mapProj = map.projection;
    var reverseCoords = true;
    if (mapProj == "EPSG:4326") {
        reverseCoords = false;
    }

    var _url = PROXY_PATH + url;
    $.ajax({
        url: _url,
        dataType: "xml",
        contentType: "text/xml; subtype=gml/3.1.1; charset=utf-8",
        type: "POST",
        data: dataPost,
        success: function (data) {
            var gmlFeatures = new OpenLayers.Format.WFST.v1_1_0({
                xy: reverseCoords,
                featureType: type,
                gmlns: 'http://www.opengis.net/gml',
                featureNS: featureNS,
                geometryName: displayableCols[displayableCols.length - 1],
                featurePrefix: prefix,
                extractAttributes: true
            }).read(data);

            if (gmlFeatures.length > 0) {
                var maxVal;
                var minVal;
                var dataObjMaxRange = gmlFeatures[0].data;
                var dataObjMinRange = gmlFeatures[gmlFeatures.length - 1].data;
                for (var propertyName in dataObjMaxRange) {
                    if (propertyName == uniqueField) {
                        maxVal = parseInt(dataObjMaxRange[propertyName]);
                    }
                }
                for (var propertyName in dataObjMinRange) {
                    if (propertyName == uniqueField) {
                        minVal = parseInt(dataObjMinRange[propertyName]);
                    }
                }

                result.min_maxIndex(maxVal, minVal);
            } else {
                result.min_maxIndex(0, 0);
            }
        },
        error: function (xhr, status) {
            jAlert('Sorry, there is a problem!');
        }
    });
}

function displayFeature() {

    var whereClause = $("#whereClause").val();
    if (whereClause != "") {
        whereClause = whereClause.replace(/>/g, "gt");
        whereClause = whereClause.replace(/</g, "lt");
        whereClause = whereClause.replace(/%/g, "percentage");

       // $.ajax({
         //   url: "../Studio/DbConnection/ValidateSql/" + whereClause,
           // async: false,
            //success: function (data) {
              //  var msg = data.Message;

               // if (msg != null) {
                 //   jAlert(msg);
                //} else {
                    var criteria = $("#whereClause").val();

                    if (criteria != null)
                        keyValue = parseCriteria(criteria);
                    else
                        keyValue = []);

                    if (actualLayerName != undefined) {

                        //Get features in reverse order (sortby unique field indescending order)
                        var fltr = getCriteriaFilter();
                        var result = new Result(actualLayerName, featureNS, fltr, showResultsinDialog);
                        sortResultInDesc(fltr, result);
                        result.displayResult(displayableCols, uniqueField);
                       // centerLayout.open("south", true);
                    } else {
                        jAlert("Please select layer for executing the query");
                    }

                //}
            //},
            //error: function (xhr, status) {
              //  jAlert('Sorry, there is a problem!');
            //}

       // });
    } else {
        jAlert("Please specify Where Clause");
    }
}

function populateSaveQueries() {
    $('#selectQuery').empty();
    //Amitabh (Hard-coded)
    projectName = "USA";
    
    $.ajax({
        type: "GET",
        url: STUDIO_URL + 'savedquery/' + "names/" + projectName + "/" + actualLayerName,
        //data: "project=" + projectName + "&layer=" +actualLayerName,
        success: function (data) {
            $('#selectQuery').append($("<option></option>").attr("value", CONST_SELECT_QUERY).text(CONST_SELECT_QUERY));
            for (var i = 0; i < data.length; i++) {
                $('#selectQuery').append($("<option></option>").attr("value", data[i]).text(data[i]));
            }
            return true;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var err = eval("(" + XMLHttpRequest.responseText + ")");
            return false;
        }
    });
}

function onQueryChange() {
    var selected = $("#selectQuery option:selected");
    if (selected.text() == CONST_SELECT_QUERY) return;
    var qryName = selected.text();

    $.ajax({
        type: "GET",
        url: STUDIO_URL + 'savedquery/' + "queryexpression/" + qryName,
        //data: "query=" + qryName,
        success: function (data) {
            $("#whereClause").val(data);
            return true;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var err = eval("(" + XMLHttpRequest.responseText + ")");
            return false;
        }
    });
}

function validateQuery() {
    var whereClause = $("#whereClause").val();
    if (whereClause != "") {
        whereClause = whereClause.replace(/>/g, "gt");
        whereClause = whereClause.replace(/</g, "lt");
        whereClause = whereClause.replace(/%/g, "percentage");

        $.ajax({
            url: "../Studio/DbConnection/ValidateSql/" + whereClause,
            async: false,
            success: function (data) {
                var msg = data.Message;
                if (msg != null) {
                    jAlert(msg);
                } else {
                    return true;
                }
            },
            error: function (xhr, status) {
                jAlert('Sorry, there is a problem!');
            }

        });
    } else {
        jAlert("Please specify Where Clause");
    }
}

function saveQuery() {
    var name = null;
    var description = null;

    //var whereClause = $("#whereClause").val();
    //if (whereClause != "") {
      //  whereClause = whereClause.replace(/>/g, "gt");
        //whereClause = whereClause.replace(/</g, "lt");
        //whereClause = whereClause.replace(/%/g, "percentage");

        //$.ajax({
          //  url: "../Studio/DbConnection/ValidateSql/" + whereClause,
         //   async: false,
         //   success: function (data) {
           //     var msg = data.Message;

               // if (msg != null) {
                //    jAlert(msg);
               // } else {
                    var selected = $("#selectQuery option:selected");
                    if (selected.text() != CONST_SELECT_QUERY) {
                        name = selected.text();
                    }
                    //Open the SaveQuery dialog
                    var whereClause = $("#whereClause").val();
                    var save = new SaveQuery(projectName, actualLayerName, whereClause, name, description);
                //}
            //},
           // error: function (xhr, status) {
          //      jAlert('Sorry, there is a problem!');
          //  }

        //});
    //}
   
}

function getQueryFilter() {
    var i = 0;

    for (i = 0; i < keyValue.length; i++) {
        if (keyValue[i][1] == "=") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.EQUAL_TO, property: keyValue[i][0], value: keyValue[i][2]});");
            if (keyValue[i][5] == "!") {
                if (keyValue[i][4] == "&&" || keyValue[i][4] == "||") {
                    continue;
                } else {
                    eval("filter" + i + " = new OpenLayers.Filter.Logical({" +
                    "type: OpenLayers.Filter.Logical.NOT," +
                    "filters: [filter" + i + "]});");
                }
            }
        }

        if (keyValue[i][1] == "!=") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.NOT_EQUAL_TO, property: keyValue[i][0], value: keyValue[i][2]});");
        }

        if (keyValue[i][1] == "<") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.LESS_THAN, property: keyValue[i][0], value: keyValue[i][2]});");
        }

        if (keyValue[i][1] == ">") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.GREATER_THAN, property: keyValue[i][0], value: keyValue[i][2]});");
        }

        if (keyValue[i][1] == "<=") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.LESS_THAN_OR_EQUAL_TO, property: keyValue[i][0], value: keyValue[i][2]});");
        }

        if (keyValue[i][1] == ">=") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO, property: keyValue[i][0], value: keyValue[i][2]});");
        }

        if (keyValue[i][1] == "LIKE") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.LIKE, property: keyValue[i][0], value: keyValue[i][2]});");
        }

        if (keyValue[i][1] == "BETWEEN") {
            eval("var filter" + i + " = new OpenLayers.Filter.Comparison({type: OpenLayers.Filter.Comparison.BETWEEN," +
            "property: keyValue[i][3], " +
            "lowerBoundary: keyValue[i][0], " +
            "upperBoundary: keyValue[i][2]});");

            if (keyValue[i][5] == "!") {
                eval("filter" + i + " = new OpenLayers.Filter.Logical({" +
                "type: OpenLayers.Filter.Logical.NOT," +
                "filters: [filter" + i + "]});");
            }
        }

        if (i > 0 && keyValue[i - 1][4] == "&&") {
            eval("var filterJoin" + i + " = new OpenLayers.Filter.Logical({" +
                "type: OpenLayers.Filter.Logical.AND," +
                "filters: [filter" + (i - 1) + ", filter" + i + "]});");
            eval("filter" + i + "= filterJoin" + i);

            if(keyValue[i - 1][5] == "!"){
                eval("filterJoin" + i + " = new OpenLayers.Filter.Logical({" +
                "type: OpenLayers.Filter.Logical.NOT," +
                "filters: [filter" + i + "]});");
                eval("filter" + i + "= filterJoin" + i);
            }
        }

        if (i > 0 && keyValue[i - 1][4] == "||") {
            eval("var filterJoin" + i + " = new OpenLayers.Filter.Logical({" +
                "type: OpenLayers.Filter.Logical.OR," +
                "filters: [filter" + (i - 1) + ", filter" + i + "]});");
            eval("filter" + i + "= filterJoin" + i);

             if(keyValue[i - 1][5] == "!"){
                eval("filterJoin" + i + " = new OpenLayers.Filter.Logical({" +
                "type: OpenLayers.Filter.Logical.NOT," +
                "filters: [filter" + i + "]});");
                eval("filter" + i + "= filterJoin" + i);
            }
        }

    }

    //Case wher NOT operator is used without AND or OR

    if (i == 0) {
        return null;
    } else {
        return eval("filter" + (i - 1));
    }
}

function parseCriteria(criteria) {
    var keyvalue;
    var isNotOperator = false;
    var operators = []);

    for (var i = 0; i < criteria.length; i++) {
        if (criteria.charAt(i) == "=") {
            operators = arrangeOperands("=", criteria, operators, i);
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
        } else if (criteria.charAt(i) == "!" && "!" + criteria.charAt(i + 1) == "!=") {
            operators = arrangeOperands("!=", criteria, operators, i);
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
            i++;
        } else if (criteria.charAt(i) == "<" && "<" + criteria.charAt(i + 1) == "<=") {
            operators = arrangeOperands("<=", criteria, operators, i);
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
            i++;
        } else if (criteria.charAt(i) == ">" && ">" + criteria.charAt(i + 1) == ">=") {
            operators = arrangeOperands(">=", criteria, operators, i);
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
            i++;
        } else if (criteria.charAt(i) == "A" && "A" + criteria.charAt(i + 1) == "AN" && "AN" + criteria.charAt(i + 2) == "AND") {
            operators[operators.length - 1][4] = "&&";
            if(isNotOperator){
                 isNotOperator = false;
                 operators[operators.length - 1][5] = "!";
             }
            // alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2] + " " + operators[operators.length - 1][3]);
            i += 2;
        } else if (criteria.charAt(i) == "<") {
            operators = arrangeOperands("<", criteria, operators, i);
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
        } else if (criteria.charAt(i) == ">") {
            operators = arrangeOperands(">", criteria, operators, i);
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
        } else if (criteria.charAt(i) == "O" && "O" + criteria.charAt(i + 1) == "OR") {
            operators[operators.length - 1][4] = "||";
            if(isNotOperator){
                 isNotOperator = false;
                 operators[operators.length - 1][5] = "!";
             }
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2] + " " + operators[operators.length - 1][3]);
            i += 1;
        } else if (criteria.charAt(i) == "L" && "L" + criteria.charAt(i + 1) == "LI" && "LI" + criteria.charAt(i + 2) == "LIK" && "LIK" + criteria.charAt(i + 3) == "LIKE") {
            operators = arrangeOperands("LIKE", criteria, operators, i);
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
            i += 3;
        } else if (criteria.charAt(i) == "B" && "B" + criteria.charAt(i + 1) == "BE" && "BE" + criteria.charAt(i + 2) == "BET" && "BET" + criteria.charAt(i + 3) == "BETW" && "BETW" + criteria.charAt(i + 4) == "BETWE" && "BETWE" + criteria.charAt(i + 5) == "BETWEE" && "BETWEE" + criteria.charAt(i + 6) == "BETWEEN") {
            operators = arrangeOperands("BETWEEN", criteria, operators, i);
            if (isNotOperator) {
                isNotOperator = false;
                operators[operators.length - 1][5] = "!";
            }
            //alert(operators[operators.length - 1][0] + " " + operators[operators.length - 1][1] + " " + operators[operators.length - 1][2]);
            i += 6;
        } else if (criteria.charAt(i) == "N" && "N" + criteria.charAt(i + 1) == "NO" && "NO" + criteria.charAt(i + 2) == "NOT") {
            isNotOperator = true;
        }
    }

    //Case where not operator is used without AND, OR
    if(isNotOperator){
         isNotOperator = false;
         operators[operators.length - 1][5] = "!";
    }
        
    return operators;
}

function arrangeOperands(operator, criteria, operators, index) {
    var exp = new RegExp(/AND|OR|NOT|=|<|>|>=|<=|!=/g);
    var l = operators.length;
    operators[l] = []6);
    var fldName = "";
    var keyvalue = []);

    if (operator.toUpperCase() != "BETWEEN") {
        keyvalue[0] = criteria.substring(0, index);
        if (operator.length > 1) {
            keyvalue[1] = criteria.substring(index + 2);
        } else {
            keyvalue[1] = criteria.substring(index + 1);
        }
    } else {
        var fldPos = trim(criteria).indexOf("BETWEEN");
        if (fldPos > -1) {
            var newPos = fldPos - 2;
            while (criteria.charAt(newPos) != " ") {
                newPos--;
            }
            fldName = criteria.substring(newPos, fldPos);
            criteria = criteria.substring(newPos);
        }
        keyvalue = criteria.split("AND");
    }

    var pos = rtrim(keyvalue[0]).lastIndexOf(" ");
    if (pos > -1)
        keyvalue[0] = keyvalue[0].substring(pos);

    pos = keyvalue[1].search(exp);
    if (pos > -1)
        keyvalue[1] = keyvalue[1].substring(0, pos);


    operators[l][0] = trim(keyvalue[0].replace(/'/g, ""));
    operators[l][1] = operator; //comparison opertaor
    operators[l][2] = trim(keyvalue[1].replace(/'/g, ""));
    operators[l][3] = fldName;
    operators[l][4] = ""; //AND, OR operator
    operators[l][5] = ""; //NOT operator

    return operators;
}

function getCriteriaFilter() {
    var filters;
    var filterCriteria;

    var selected = $("#select2 option:selected");
    if (selected.val() == 'current selection') {
        if (featureSelection != null && featureSelection.layerName == actualLayerName) {
            filterCriteria = getQueryFilter();
            if (filterCriteria != null) {
                filters = new OpenLayers.Filter.Logical({
                    type: OpenLayers.Filter.Logical.AND,
                    filters: [featureSelection.filter, filterCriteria]
                });
                filterCriteria = filters;
            }
        } else {
            filterCriteria = getQueryFilter();
        }   
    } else {
         filterCriteria = getQueryFilter();
    }

    return filterCriteria;
}

function isColumnDisplayable(colName) {
    for (var i = 0; i < displayableCols.length; i++) {
        if (displayableCols[i].field == colName) {
            return true;
        }
    }
    return false;
}


function getCaret() {
//    var el = document.getElementById('whereClause');
//    if (el.selectionStart >= 0) {
//        return el.selectionStart;
//    } else if (document.selection) {
//        el.focus();
//
//        var r = document.selection.createRange();
//        if (r == null) {
//            return 0;
//        }
//
//        var re = el.createTextRange(),
//				rc = re.duplicate();
//        re.moveToBookmark(r.getBookmark());
//        rc.setEndPoint('EndToStart', re);
//
//        return rc.text.length;
//    }
//    return 0;
	
	//Using jquery.a-tools plugin
	var pos = 0;
	var obj =$("#whereClause").getSelection();
	pos = obj.start;
	 
    if(pos == 0 && $("#whereClause").val().length == 0){
    	return pos;
    }else if(pos == 0 && $("#whereClause").val().length > 0){
    	$("#whereClause").setCaretPos($("#whereClause").val().length + 1);
    	var obj =$("#whereClause").getSelection();
    	pos = obj.end;
    	return pos;
    }else{
    	$("#whereClause").setCaretPos(pos);
    	return pos;
    }
	 

}
