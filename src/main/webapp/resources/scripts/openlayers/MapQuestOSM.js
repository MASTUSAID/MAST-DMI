OpenLayers.Layer.MapQuestOSM = OpenLayers.Class(OpenLayers.Layer.XYZ, {
name: "MapQuestOSM",
//attribution: "Data CC-By-SA by <a href='http://openstreetmap.org/'>OpenStreetMap</a>",
sphericalMercator: true,
url: ' http://otile1.mqcdn.com/tiles/1.0.0/osm/${z}/${x}/${y}.png',
clone: function(obj) {
    if (obj == null) {
        obj = new OpenLayers.Layer.OSM(
            this.name, this.url, this.getOptions());
    }
    obj = OpenLayers.Layer.XYZ.prototype.clone.apply(this, [obj]);
    return obj;
},
CLASS_NAME: "OpenLayers.Layer.MapQuestOSM"
});     