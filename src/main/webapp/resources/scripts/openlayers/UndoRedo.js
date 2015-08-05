
var deleteMarkupSymbolizers = {
    "Point": {
        pointRadius: 4,
        graphicName: "square",
        fillColor: "#ff0000",
        fillOpacity: 1,
        strokeWidth: 1,
        strokeOpacity: 1,
        strokeColor: "#ff0000"
    },
    "Line": {
        strokeWidth: 3,
        strokeOpacity: 1,
        strokeColor: "#ff0000",
        strokeLinecap: "square",
        strokeDashstyle: "dash"
    },
    "Polygon": {
        strokeWidth: 2,
        strokeOpacity: 1,
        strokeColor: "#ff0000",
        fillColor: "#ff0000",
        fillOpacity: 0.3,
        strokeLinecap: "square",
        strokeDashstyle: "solid"
    },
    "RegularPolygon": {
        strokeWidth: 2,
        strokeOpacity: 1,
        strokeColor: "#ff0000",
        fillColor: "#ff0000",
        fillOpacity: 0.3
    }
};
var delStyle = new OpenLayers.Style();
delStyle.addRules([
                new OpenLayers.Rule({ symbolizer: deleteMarkupSymbolizers })
                ]);
/**
 * Class: UndoRedo
 * Instance of this class can be used to undo and redo vector edits.
 */
UndoRedo = OpenLayers.Class(OpenLayers.Control, {
    /**
    * APIProperty: currentEditIndex
    * {integer} - sequence number for editing the feature[s] 
    */
    currentEditIndex: 0,
    /**
    * Property: undoFeatures
    * {array} - stack of the edit features
    */
    undoFeatures: [],
    /**
    * Property: redoFeatures
    * {array} - stack of the undo features
    */
    redoFeatures: [],
    /**
    * Property: isEditMulty
    * {boolean} - true if in one action multiple features are editied 
    */
    isEditMulty: false,

    layers: [],

    /**
    * Constructor: UndoRedo
    * Parameters:
    * layers - array of {<OpenLayers.Layers.Vector>}
    */
    initialize: function (layers) {
        if (!(layers instanceof Array)) {
            this.layers = [layers];
        } else {
            this.layers = layers;
        }
        for (var i = 0; i < layers.length; i++) {
            layers[i].events.register("featureadded", this, this.onInsert);
            layers[i].events.register("beforefeatureremoved", this, this.onDelete);
            layers[i].events.register("beforefeaturemodified", this, this.onUpdate);
            layers[i].events.register("featuremodified", this, this.onUpdate);

        }
    },
    /**
    * Method: onEdit
    * on any edit operation performed this has to be triggred
    * i.e. on insert, delete, update 
    * Parameters: 
    * feature - {<OpenLayers.Feature.Vector>}
    * editType - {string} edit type done "Insert","Delete","Update"
    * component - {string} layer or any other identifier
    * Returns: 
    */
    onEdit: function (feature, editType, component) {
        //console.log("Updating undo stack as there is - "+editType);
        if (component == undefined) {
            component = feature.layer.name;
        }
        if (this.undoFeatures[this.currentEditIndex] == undefined) {
            this.undoFeatures[this.currentEditIndex] = {};
            this.undoFeatures[this.currentEditIndex][component] = { "Insert": [], "Update": [], "Delete": [] };
        }
        if (feature.fid == undefined) {
            feature.fid = feature.id;
        }
        this.undoFeatures[this.currentEditIndex][component][editType].push(feature);
        this.redoFeatures.splice(0, this.redoFeatures.length);
        //run increase editIndex outside after this in case of multy feature edit
        if (!this.isEditMulty) {
            this.setEditIndex();
        }
    },

    /**
    * Method: onInsert
    * event haldler for featureadded 
    */
    onInsert: function (event) {
        //onEdit(event.feature,"Insert",event.feature.layer.name);
        if (event.feature.state == OpenLayers.State.INSERT || event.feature.state == "_blank") {
            feature = event.feature.clone();
            if (event.feature.fid == undefined) {
                event.feature.fid = event.feature.id;
            }
            feature.fid = event.feature.fid;
            feature.state = event.feature.state;

            if (event.feature.layer != null) {
                feature.layer = event.feature.layer;
            } else {
                feature.layer = this.layers[1]; /*Assign layer WFS_DEL by default*/
            }
            this.onEdit(feature, "Insert", feature.layer.name);
        }
    },

    /**
    * Method: onDelete
    * event haldler for beforefeatureremoved 
    */
    onDelete: function (event) {
        if (event.feature.state == "delete") {
            this.onEdit(event.feature, "Delete", event.feature.layer.name);
        }
    },

    /**
    * Method: onUpdate
    * event haldler for beforefeaturemodified
    */
    onUpdate: function (event) {
        //console.log("old feature geometry: " + event.feature.geometry);
        feature = event.feature.clone();
        feature.fid = event.feature.fid;
        feature.state = event.feature.state;
        if (event.feature.layer != null) {
            feature.layer = event.feature.layer;
        } else {
            feature.layer = this.layers[0]; /*Assign layer WFS by default*/
        }
        this.onEdit(feature, "Update", feature.layer.name);
    },

    /**
    * Method: setEditIndex
    * increase the editIndex
    */
    setEditIndex: function (delta) {
        delta = delta ? delta : 1;
        this.currentEditIndex += delta;
    },

    /**
    * Method: getUndoData
    * returns the last edited data
    */
    getUndoData: function () {
        //this.undoFeatures.pop();
        var data = this.undoFeatures.pop();
        if (this.currentEditIndex > 0) {
            this.currentEditIndex -= 1;
        }
        return data;
    },

    /* Method: getRedoData
    * returns the last redo data
    */
    getRedoData: function () {
        //this.redoFeatures.pop();
        var data = this.redoFeatures.pop();
        this.currentEditIndex += 1;
        return data;
    },

    /**
    * APIMethod: reseteditIndex
    * reset the editIndex to 0 and empety both undo and redo stack
    */
    resetEditIndex: function () {
        this.currentEditIndex = 0;
        this.undoFeatures.splice(0, this.undoFeatures.length);
        this.redoFeatures.splice(0, this.redoFeatures.length);
    },

    /**
    * APIMethod: undo
    * perform undo operation 
    */
    undo: function () {
        data = this.getUndoData();

        if (data == null)
            return;

        for (component in data) {
            layer = map.getLayersByName(component)[0];
            for (editType in data[component]) {
                for (var i = 0; i < data[component][editType].length; i++) {
                    feature = data[component][editType][i];
                    switch (editType) {
                        case "Insert":
                            if (feature.state != null) {
                                actualFeature = layer.getFeatureByFid(feature.fid);
                                layer.removeFeatures([actualFeature]);

                                if (actualFeature.state == OpenLayers.State.DELETE) {
                                    actualFeature.state = "del_undo";
                                    existingFeature = this.layers[0].getFeatureByFid(feature.fid);
                                    if (existingFeature == null) {
                                        this.layers[0].addFeatures([feature]);
                                    } else {
                                        feature.layer = this.layers[0];
                                        this.layers[0].drawFeature(existingFeature);
                                    }
                                    data[component][editType][i] = actualFeature;
                                } else if (actualFeature.state == OpenLayers.State.INSERT) {
                                    actualFeature.state = "_blank";
                                    data[component][editType][i] = actualFeature;
                                }
                            }
                            break;
                        case "Delete":
                            if (feature.state != null && feature.state == "delete") {
                                layer.features.push(feature);
                                layer.drawFeature(feature);
                            }
                            break;
                        case "Update":
                            updatedFeature = layer.getFeatureByFid(feature.fid);
                            if (updatedFeature != null) {
                                layer.eraseFeatures(updatedFeature);
                                OpenLayers.Util.removeItem(layer.features, updatedFeature);
                                layer.removeFeatures(updatedFeature);
                                layer.features.push(feature);
                                layer.drawFeature(feature);
                                data[component][editType][i] = updatedFeature;

                                obj = editControls["modify"];
                                control = map.getControl(obj.id);
                                if (control != null) {
                                    control.selectFeature(feature);
                                }

                            }
                            break;
                        default:
                            //console.log("unkown");
                            break;
                    }
                    //console.log("features after undo: "+layer.features.length);
                }
            }
        }
        if (data != null) {
            this.redoFeatures.push(data);
        }
    },

    /**
    * APIMethod:  redo
    * perform redo operation
    */

    redo: function () {
        data = this.getRedoData();
        for (component in data) {
            layer = map.getLayersByName(component)[0];
            for (editType in data[component]) {
                for (var i = 0; i < data[component][editType].length; i++) {
                    feature = data[component][editType][i];
                    switch (editType) {
                        case "Insert":
                            cloneFeature = this.layers[0].getFeatureByFid(feature.fid);
                            
                            if (feature.state == "del_undo") {
                                layer.addFeatures([feature]);
                                feature.state = OpenLayers.State.DELETE;
                                cloneFeature.layer = this.layers[0];
                                data[component][editType][i] = cloneFeature;
                            } else if (feature.state == "_blank") {
                                layer.features.push(feature);
                                layer.drawFeature(feature);
                                feature.state = OpenLayers.State.INSERT;
                                data[component][editType][i] = feature;
                            }
                            break;
                        case "Delete":
                            deleteFeature = layer.getFeatureByFid(feature.fid)
                            layer.eraseFeatures(deleteFeature);
                            OpenLayers.Util.removeItem(layer.features, deleteFeature);
                            break;
                        case "Update":
                            oldFeature = layer.getFeatureByFid(feature.fid);
                            layer.eraseFeatures(oldFeature);
                            OpenLayers.Util.removeItem(layer.features, oldFeature);
                            layer.features.push(feature);
                            layer.drawFeature(feature);

                            obj = editControls["modify"];
                            control = map.getControl(obj.id);
                            if (control != null) {
                                control.selectFeature(feature);
                            }
                            data[component][editType][i] = oldFeature;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        if (data != null) {
            this.undoFeatures.push(data);
        }
    },

    CLASS_NAME: "UndoRedo"
});	
