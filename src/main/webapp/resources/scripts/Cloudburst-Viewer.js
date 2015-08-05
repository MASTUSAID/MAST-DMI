(function() {
    /**
     * Before creating the Cloudburst namespace, check to see if
     * Cloudburst.singleFile is true.  This occurs if the
     * Cloudburst/SingleFile.js script is included before this one - as is the
     * case with old single file build profiles that included both
     * Cloudburst.js and Cloudburst/SingleFile.js.
     */
    var singleFile = (typeof Cloudburst == "object" && Cloudburst.singleFile);
    
    /**
     * Relative path of this script.
     */
    var scriptName = (!singleFile) ? "lib/Cloudburst.js" : "Cloudburst.js";

    /*
     * If window.Cloudburst isn't set when this script (Cloudburst.js) is
     * evaluated (and if singleFile is false) then this script will load
     * *all* Cloudburst scripts. If window.Cloudburst is set to an array
     * then this script will attempt to load scripts for each string of
     * the array, using the string as the src of the script.
     *
     * Example:
     * (code)
     *     <script type="text/javascript">
     *         window.Cloudburst = [
     *             "Cloudburst/Util.js",
     *             "Cloudburst/BaseTypes.js"
     *         ];
     *     </script>
     *     <script type="text/javascript" src="../lib/Cloudburst.js"></script>
     * (end)
     * In this example Cloudburst.js will load Util.js and BaseTypes.js only.
     */
    var jsFiles = window.Cloudburst;

    /**
     * Namespace: Cloudburst
     * The Cloudburst object provides a namespace for all things Cloudburst
     */
    window.Cloudburst = {
        /**
         * Method: _getScriptLocation
         * Return the path to this script. This is also implemented in
         * Cloudburst/SingleFile.js
         *
         * Returns:
         * {String} Path to this script
         */
        _getScriptLocation: (function() {
            var r = new RegExp("(^|(.*?\\/))(" + scriptName + ")(\\?|$)"),
                s = document.getElementsByTagName('script'),
                src, m, l = "";
            for(var i=0, len=s.length; i<len; i++) {
                src = s[i].getAttribute('src');
                if(src) {
                    var m = src.match(r);
                    if(m) {
                        l = m[1];
                        break;
                    }
                }
            }
            return (function() { return l; });
        })()
    };

    /**
     * Cloudburst.singleFile is a flag indicating this file is being included
     * in a Single File Library build of the Cloudburst Library.
     * 
     * When we are *not* part of a SFL build we dynamically include the
     * Cloudburst library code.
     * 
     * When we *are* part of a SFL build we do not dynamically include the 
     * Cloudburst library code as it will be appended at the end of this file.
     */
    if(!singleFile) {
        if (!jsFiles) {
            jsFiles = [
               "/cloudburst/viewer/util.js",
               "/cloudburst/viewer/jsts.js",
               "openlayers/OpenLayers.js",
               "openlayers/Watermark.js",
               "openlayers/MapQuestOSM.js",
               "openlayers/LayerSwitcherNew.js",
               "openlayers/UndoRedo.js",
               "jquery-1.7.1/jquery-1.7.1.min.js",               
               "jquery-1.6.3/jquery.cookie.js",
               "jquery-1.6.3/jquery.formHints.js",
               "jquery-ui-1.8.13/jquery-ui-1.8.13.custom.min.js",
               "jquery-i18n/jquery.i18n.js",
               "jquery-localize/jquery.localize.js",
               "jquery-meerkat/jquery.meerkat.1.3.min.js",
               "jquery-tmpl/jquery.tmpl.min.js",
               "jquery-alert/jquery.alerts.js",
               "jquery.a-tools/jquery.a-tools-1.5.2.min.js",
               "jquery-tiptip/jquery.tipTip.js",
               "jquery-timeout/js/timeoutwindow.js",
               "msdropdown/js/jquery.dd.js",               
               "contextmenu/jquery.contextMenu.js",
               "qtip2/jquery.qtip.min.js",
               "jqGrid/jquery.jqGrid.min.js",
               "dynatree/jquery.dynatree.js",
               "jcarousel/lib/jquery.jcarousel.js",
               "jquery-dropdown/js/jquery.ui.dropdown.js",
               "jquery-spinner/ui.spinner.js",
               "colorpicker/js/colorpicker.js",
               "xmljs/tinyxmldom.js",
               "xmljs/tinyxmlw3cdom.js",
               "xmljs/tinyxmlsax.js",
               "xmljs/tinyxmlxpath.js",               
               "tablesorter/jquery.tablesorter.min.js",
               "tablesorter/addons/pager/jquery.tablesorter.pager.js",
               "tablesorter/addons/filter/jquery.tablesorter.filer.js",
               "jquery-validate/jquery.validate.js",
               "jquery-form/jquery.form.js",
               "/cloudburst/viewer/Base64.js",
               "/cloudburst/Constant.js",
               "/cloudburst/SpatialVue_Constants.js",
               "/cloudburst/viewer/Common.js",
               "/cloudburst/viewer/Viewer.js",
               "/cloudburst/viewer/Toolbar.js",
               "/cloudburst/viewer/Navi.js",
               "/cloudburst/viewer/Search.js",
               "/cloudburst/viewer/Print.js",
               "/cloudburst/viewer/ZoomToXY.js",
               "/cloudburst/viewer/ExportData.js",
               "/cloudburst/viewer/Bookmark.js",
               "/cloudburst/viewer/SaveProject.js",
               "/cloudburst/viewer/Selection.js",
               "/cloudburst/viewer/Query1.js",
               "/cloudburst/viewer/Result.js",
               "/cloudburst/viewer/LayerManager.js",
               "/cloudburst/viewer/SaveQuery.js",
               "/cloudburst/viewer/Thematic.js",
               "/cloudburst/viewer/measure.js",
               "/cloudburst/viewer/Info.js",
               "/cloudburst/viewer/Maptip.js",
               "/cloudburst/viewer/Markup.js",
               "/cloudburst/viewer/DynaLayers.js",
               "/cloudburst/viewer/FileUpload.js",
               "/cloudburst/viewer/TextStyle.js",
               "/cloudburst/viewer/Editing.js",
               "/cloudburst/viewer/OpenProject.js",
               "/cloudburst/viewer/ExportMap.js",               
               "mtl-script/osgridref.js",
               "/cloudburst/viewer/UserProjects.js",
               "/cloudburst/viewer/LandRecords.js",
               "/cloudburst/viewer/CCRO.js",
               "/cloudburst/viewer/land-adjudication.js",
               "/cloudburst/viewer/mapLoader.js",
			   "/cloudburst/viewer/mapImage.js",
			   "/cloudburst/viewer/proj4js-combined.js",
			   "/cloudburst/viewer/Intersection.js"
               
            ]; // etc.
        }

        var scriptTags = new Array(jsFiles.length);
        var host = Cloudburst._getScriptLocation() + "resources/scripts/";
        for (var i=0, len=jsFiles.length; i<len; i++) {
            scriptTags[i] = "<script src='" + host + jsFiles[i] +
                                   "'></script>"; 
        }
        if (scriptTags.length > 0) {
            document.write(scriptTags.join(""));
        }
    }
})();

/**
 * Constant: VERSION_NUMBER
 */
Cloudburst.VERSION_NUMBER="0.1";
var undoredo = null;