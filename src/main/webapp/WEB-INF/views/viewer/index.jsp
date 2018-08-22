<%@page import="javax.mail.Session"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>

<fmt:setLocale value="${langCode}" />

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    String token = (String) request.getSession().getAttribute("auth");
    java.lang.String s = null;
    java.lang.String principal = null;

    try {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("User is: " + user.getUsername());
        principal = user.getUsername();
        java.util.Collection<org.springframework.security.core.GrantedAuthority> authorities = user.getAuthorities();

        java.lang.StringBuffer sb = new java.lang.StringBuffer();
        for (java.util.Iterator<org.springframework.security.core.GrantedAuthority> itr = authorities.iterator(); itr.hasNext();) {
            org.springframework.security.core.GrantedAuthority authority = itr.next();
            sb.append(authority.getAuthority()).append(", ");
        }
        s = sb.toString();
        s = s.substring(0, s.length() - 2);

    } catch (Exception e) {
        e.printStackTrace();
    }
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="${langCode}" dir="${empty ltr ? 'ltr' : ltr ? 'ltr' : 'rtl'}" xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title data-i18n="viewer-title"></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
        <meta name="apple-mobile-web-app-capable" content="yes" />

        <link rel="stylesheet"
              href="<c:url value="resources/scripts/openlayers/theme/default/ol.css" />"
              type="text/css" media="screen, projection" />

        <link rel="stylesheet"
              href="<c:url value="resources/scripts/openlayers/theme/default/style.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/openlayers/theme/default/google.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/jquery-ui-1.12.1/jquery-ui.min.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/msdropdown/dd.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/jquery-dropdown/css/jquery.ui.dropdown.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/colorpicker/css/colorpicker.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/jquery-spinner/ui.spinner.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/jqGrid/css/ui.jqgrid.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/contextmenu/jquery.contextMenu.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/dynatree/skin-vista/ui.dynatree.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/jcarousel/skins/tango/skin.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/qtip2/jquery.qtip.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/jquery-alert/jquery.alerts.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/scripts/jquery-tiptip/tipTip.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/viewer/viewer.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/studio/header.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet" href="resources/styles/viewer/vtav1.css"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet" type="text/css" media="screen, projection" />
        <link rel="stylesheet" type="text/css" media="print, projection, screen"
              href="<c:url value="resources/scripts/tablesorter/themes/blue/style.css" />" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/viewer/style-new.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/viewer/CCRO.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/font-awesome.min.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/viewer/jsgrid.min.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/viewer/jsgrid-theme.min.css" />"
              type="text/css" media="screen, projection" />

        <!--[if IE 7]>  
        <link rel="stylesheet"
                href="<c:url value="resources/styles/viewer/ie7.css" />"
                type="text/css" media="screen, projection" />
         <![endif]-->

        <!--[if IE 8]>  
        <link rel="stylesheet"
                href="<c:url value="resources/styles/viewer/ie_style.css" />"
                type="text/css" media="screen, projection" />
         <![endif]-->

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="https://files.codepedia.info/files/uploads/iScripts/html2canvas.js"></script>

        <script language="javascript">
            var roles = "<%=s%>";
            var token = "<%=token%>";
            token = "_token=" + token;
            var user = "<%=principal%>";
        </script>

        <script src="http://maps.google.com/maps/api/js?v=3.5&amp;sensor=false"></script>
        <script src="<c:url value="resources/scripts/Cloudburst-Viewer.js"  />" type="text/javascript"></script>
        <%@include file="../../jspf/HeaderResources.jspf" %>
        
        <script language="javascript">
            var loggedUser = null;
            var tDlg = new $.timeoutDialog({
                timeout: 900,
                countdown: 60,
                logout_redirect_url: '/mast/j_spring_security_logout',
                keep_alive_url: '/mast/studio/keepalive/'
            });

            window.onload = function () {
                $.ajaxSetup({cache: false});
                $("#toolbar").hide();
                $('.splash').meerkat({
                    background: 'url(resources/images/viewer/bg-splash.png) repeat-x left top',
                    height: '100%',
                    width: '100%',
                    position: 'center',
                    animationIn: 'none',
                    animationOut: 'fade',
                    animationSpeed: 500,
                    timer: 2,
                    removeCookie: '.reset'
                });

                $("#bottombar").hide();

                var activeproject = '';
                var project = getUrlVar('project');

                $.ajax({
                    type: "POST",
                    url: STUDIO_URL + "user/username/",
                    data: {username: user},
                    success: function (userdetail) {
                        loggedUser = userdetail;
                        activeproject = userdetail.defaultproject;

                        if (project) {
                            var cnt = 0;
                            jQuery.each(userdetail.project, function (i, proj) {
                                project = decodeURI(project);
                                if (project === proj.name) {
                                    activeproject = project;
                                    cnt += 1;
                                    return;
                                }
                            });
                        }
                        if (cnt == 0) {
                            $("body").hide();
                            alert(project + ' is not assigned to the user');
                            return;
                        }
                        var options = {
                            project: activeproject
                        };

                        var viewer = new Cloudburst.loadMap("map", options, maploaded);
                        viewProjectName(activeproject);
                        $("#ShowProjectNameID").text("");
                        $("#ShowProjectNameID").text(activeproject);
                    }
                });
            };
            //***** Callback function - Called when the viewer ********
            //has loaded the layers and the map is intialised
            //****************
            var maploaded = function (map) {
                $("#toolbar").show();
                var toolbar = new Cloudburst.Toolbar(map, loggedUser);

                jQuery.ajaxSetup({
                    beforeSend: function () {
                        tDlg.resetTimeoutDlg();
                        $('#_loader').show();
                    },
                    start: function () {
                        $('#_loader').show();
                    },
                    stop: function () {
                        $('#_loader').hide();
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

                //intialize tooltip
                $(".fg-button").tipTip({
                    fadeIn: 0,
                    fadeOut: 0
                });
            }


            $(window).resize(function () {
                windowResize();
            });

            <%String path = request.getContextPath();
                String getProtocol = request.getScheme();
                String getDomain = request.getServerName();
                String getPort = Integer.toString(request.getServerPort());
                String serverurl = getProtocol + "://" + getDomain + ":" + getPort + path + "/";%>
            var serverPath = "<%=serverurl%>";
        </script>
    </head>
    <body style="overflow-y: auto;">

        <div id="_splash" class="splash">
            <div id="splash-content">
                <img id="enter" src="resources/images/viewer/splash-logo.png"
                     alt="Meerkat" />
            </div>
        </div>

        <div id="_loader" class="loader">
            <img src="resources/images/viewer/ajax-loader.gif" />
        </div>

        <script language="javascript">
            var token = null;
            var roles = '<%=s%>';
            var useremail = '<%=principal%>';

            $(document).ready(
                    function () {
                        $('.splash')
                                .meerkat({
                                    background: 'url(resources/images/studio/bg-splash.png) repeat-x left top',
                                    height: '100%',
                                    width: '100%',
                                    position: 'center',
                                    animationIn: 'none',
                                    animationOut: 'fade',
                                    animationSpeed: 500,
                                    timer: 2,
                                    removeCookie: '.reset'
                                });

                        // Added by prashant to REMOVE LAnd records div for public user
                        if (roles == 'ROLE_PUBLICUSER' || roles == 'ROLE_USER')
                            $('#tab2').remove();
                    });
        </script>

        <div id="intersectionDialog" data-i18n="[title]viewer-spatial-validation" style="display: none;"></div>

        <div id="container">

            <%@include file="../../jspf/Header.jspf" %>

            <div class="mainContainer">

                <div id="mainTabs">

                    <div class="ProjectSelectContainer">
                        <label class="lblProject" data-i18n="viewer-project-name"></label>
                        <span class="ShowProjectName" id="ShowProjectNameID"></span>
                    </div>

                    <ul>
                        <li><a href="#map-tab" id="tab1" data-i18n="viewer-map" onclick="javascript:closeDialog('taskMngrdiv')"></a></li>
                        <li><a href="#landrecords-div" id="tab2" data-i18n="viewer-land-records"></a></li>
                        <li><a href="#landresource-div" id="tab6" data-i18n="viewer-res"> </a></li>
                        <li><a href="#tabReports" id="tab4" data-i18n="viewer-reports"></a></li>
                        <li><a href="#registryTab-div" id="tab5" data-i18n="viewer-reg"></a></li>
                    </ul>

                    <div id="map-tab">
                        <!--  Main Toolbar  -->
                        <div class="toolBarBG">
                            <div class="default-project">
                                <div class="btn-wrap">
                                    <button id="defaultbutton" class="btn"
                                            style="visibility: hidden;" data-i18n="[title]viewer-goto-def-proj"
                                            onclick="javascript:defaultProject();">
                                        <i class="fa fa-folder"></i><span data-i18n="viewer-def"></span>
                                    </button>
                                </div>
                            </div>


                            <div style="float: left; margin: 3px 6px;">
                                <button type="button" class="btn" onclick="javascript:initTaskManager();" data-i18n="viewer-taskman"></button>
                            </div>

                            <div style="float: right; postion: relative; height: 38px;">
                                <div id="toolbar" class="toolbar">
                                    <div class="fg-buttonset fg-buttonset-single">
                                        <ul id="mycarousel" class="jcarousel-skin-tango">
                                            <li id="li-openproject">
                                                <button id="openproject" data-i18n="[title]viewer-select-proj"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img alt="" src="<c:url value="resources/images/viewer/toolbar/open.png" />" />
                                                </button>
                                            </li>

                                            <li id="li-zoomin">
                                                <button id="zoomin"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-zoomin">
                                                    <img src="<c:url value="resources/images/viewer/navi/zoom_in.png" />" />
                                                </button>
                                            </li>

                                            <li id="li-zoomout">
                                                <button id="zoomout"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-zoomout">
                                                    <img src="<c:url value="resources/images/viewer/navi/zoom_out.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-pan">
                                                <button id="pan"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right ui-state-active1"
                                                        data-i18n="[title]viewer-pan">
                                                    <img src="<c:url value="resources/images/viewer/navi/center.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-zoomtolayer">
                                                <button id="zoomtolayer"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-zoomtolayer">
                                                    <img src="<c:url value="resources/images/viewer/navi/zoom_layer.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-fixedzoomin">
                                                <button id="fixedzoomin"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-fixedzoomin">
                                                    <img src="<c:url value="resources/images/viewer/navi/fixed_zoom_in.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-fixedzoomout">
                                                <button id="fixedzoomout"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-fixedzoomout">
                                                    <img src="<c:url value="resources/images/viewer/navi/fixed_zoom_out.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-zoomprevious">
                                                <button id="zoomprevious"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-zoomprev">
                                                    <img src="<c:url value="resources/images/viewer/navi/previous.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-zoomnext">
                                                <button id="zoomnext"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-zoomnext">
                                                    <img src="<c:url value="resources/images/viewer/navi/next.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-fullview">
                                                <button id="fullview"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                        data-i18n="[title]viewer-fullzoom">
                                                    <img src="<c:url value="resources/images/viewer/navi/zoom_full.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-info">
                                                <button id="info" data-i18n="[title]viewer-info"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/navi/info.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-measurelength">
                                                <button id="measurelength"
                                                        data-i18n="[title]viewer-measure"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/toolbar/length-measure.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-selectfeature">
                                                <button id="selectfeature"
                                                        data-i18n="[title]viewer-select-feature"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/navi/select-Feature.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-selectbox">
                                                <button id="selectbox"
                                                        data-i18n="[title]viewer-select-by-rect"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/navi/select-rectangle.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-clear_selection">
                                                <button id="clear_selection"
                                                        data-i18n="[title]viewer-clear"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/toolbar/clear_selection.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-search">
                                                <button id="search" data-i18n="[title]viewer-search"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img alt="" src="<c:url value="resources/images/viewer/navi/search.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-zoomtoxy">
                                                <button id="zoomtoxy"
                                                        data-i18n="[title]viewer-zoomtoxy"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img alt="" src="<c:url value="resources/images/viewer/navi/zoomtoxy.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-print">
                                                <button id="print" data-i18n="[title]gen-print"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/toolbar/print.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-bookmark"><button id="bookmark"
                                                                         data-i18n="[title]viewer-bookmarks"
                                                                         class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/toolbar/bookmark.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-export">
                                                <button id="export"
                                                        data-i18n="[title]viewer-export"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img src="<c:url value="resources/images/viewer/toolbar/export.png" />" />
                                                </button>
                                            </li>
                                            <li id="li-editing">
                                                <button id="editing" data-i18n="[title]viewer-edit"
                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                    <img alt="" src="<c:url value="resources/images/viewer/toolbar/edit.png" />" />
                                                </button>
                                            </li>
                                        </ul>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- ------------- Horizontal Shadow  -->
                        <div id="shadowHor">
                            <div class="sh1"></div>
                            <div class="sh2"></div>
                            <div class="sh3"></div>
                            <div class="sh4"></div>
                            <div class="sh5"></div>
                            <div class="sh6"></div>
                        </div>
                        <div id="map-print">
                            <table cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td valign="top">
                                        <div id="sidebar">
                                            <ul>
                                                <li><a href="#tabs-LayerManager">
                                                        <span id="layermgr" data-i18n="viewer-layer-manager"></span>
                                                    </a>
                                                </li>
                                            </ul>
                                            <div id="tabs-LayerManager"></div>
                                            <div id="tabs-Tool"></div>
                                        </div>
                                    </td>
                                    <td valign="top" style="width: 100%">
                                        <div id="map">
                                            <span id="collapse" class="collapse_left"></span> 
                                            <input type="text" class="markuptexttooltip" id="MarkupTextTooltip" title="" style="display: none" />
                                            <div id="baselayer" class="baselayer-b mapbl-buttonset mapbl-buttonset-single"></div>
                                            <div id="maptips" class="maptips-s"></div>
                                            <span id="bottomcollapse" class="bottom_collapse"></span>
                                            <!-- ------------- Vertical Shadow  -->
                                            <div id="shadowVer">
                                                <div class="sv1"></div>
                                                <div class="sv2"></div>
                                                <div class="sv3"></div>
                                                <div class="sv4"></div>
                                                <div class="sv5"></div>
                                                <div class="sv6"></div>
                                            </div>
                                            <div id="bottombar"></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                                                
                    <div id="landresource-div"></div>
                    <!--Land records div  -->
                    <div id="landrecords-div"></div>
                    <div id="tabReports">
                        <div style="padding: 10px;">
                            <div id="reportsAccordion">
                                <h3 data-i18n="viewer-landrec-forms"></h3>
                                <div class="filter-details">
                                    <form id="formAdjudicationForms" action="" onsubmit="return false;">
                                        <table>
                                            <tr>
                                                <td>
                                                    <label data-i18n="viewer-start-tran-id"></label>
                                                    <input type="text" id="landformStart" value="1" />
                                                </td>
                                                <td>
                                                    <label data-i18n="viewer-end-tran-id"></label> 
                                                    <input type="text" id="landformEnd" class="input-medium" /></td>
                                                <td>
                                                    <label>&nbsp;</label> 
                                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateLandForm();" />
                                                </td>
                                            </tr>
                                        </table>
                                        <br />
                                    </form>
                                </div>
                                <h3 data-i18n="viewer-land-certs"></h3>
                                <div class="filter-details">
                                    <form id="formAdjudicationForms" action="" onsubmit="return false;">
                                        <table>
                                            <tr>
                                                <td>
                                                    <label data-i18n="viewer-report-tran-id"></label>
                                                    <input type="text" id="ccroStart" />
                                                </td>
                                                <td>
                                                    <label>&nbsp;</label> 
                                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateCcro();" />
                                                </td>
                                            </tr>
                                        </table>
                                        <br />
                                    </form>
                                </div>
                                <h3 data-i18n="viewer-batch-certs-report"></h3>
                                <div class="filter-details">
                                    <form id="formAdjudicationForms" action="" onsubmit="return false;">
                                        <table>
                                            <tr>
                                                <td>
                                                    <label data-i18n="viewer-start-tran-id"></label> 
                                                    <input type="text" id="ccroStartbatch" />
                                                </td>
                                                <td>
                                                    <label data-i18n="viewer-end-tran-id"></label> 
                                                    <input type="text" id="ccroEndbatch" class="input-medium" />
                                                </td>
                                                <td>
                                                    <label>&nbsp;</label> 
                                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="generateCcros();" />
                                                </td>
                                            </tr>
                                        </table>
                                        <br />
                                    </form>
                                </div>

                                <h3 data-i18n="viewer-report-by-village"></h3>
                                <div>
                                    <br /> <label data-i18n="viewer-report-project"></label> 
                                    <select id="selectProjectsForSummary"></select> &nbsp;&nbsp; 
                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateSummaryReport();" /> 
                                    <br />
                                </div>
                                <h3 data-i18n="viewer-report-by-tenure"></h3>
                                <div>
                                    <br /> <label data-i18n="viewer-report-project"></label> 
                                    <select id="selectProjectsForDetailSummary"></select> &nbsp;&nbsp; 
                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateProjectDetailedSummaryReport();" />
                                    <br />
                                </div>
                                <h3 data-i18n="viewer-report-by-village-and-tenure"></h3>

                                <div>
                                    <br /> <label data-i18n="viewer-report-select-village"></label> 
                                    <select class="pagesize" id="CommuneId" name="CommuneId" onchange="getplaceval(this);">
                                        <option value="" data-i18n="gen-select"></option>
                                        <option value="10">Bana</option>
                                        <option value="9">Bagassi</option>
                                        <option value="15">Pompoi</option>
                                        <option value="13">Ouri</option>
                                        <option value="11">Boromo</option></select> 
                                        &nbsp;&nbsp; 
                                        <input type="button" data-i18n="[value]gen-generate" class="btn1"
                                            onclick="javascript:generateProjectDetailedSummaryReportForCommune();" />
                                    <br /> 
                                </div>

                                <h3 data-i18n="viewer-report-by-appstatus"></h3>
                                <div>
                                    <br /> <label data-i18n="viewer-report-project"></label> 
                                    <select id="selectProjectsForAppStatusSummary"></select> &nbsp;&nbsp; 
                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateProjectAppStatusSummaryReport();" />
                                    <br />
                                </div>
                                <h3 data-i18n="viewer-report-by-claimtype"></h3>
                                <div>
                                    <br /> <label data-i18n="viewer-report-project"></label> 
                                    <select id="selectProjectsForAppTypeSummary"></select> &nbsp;&nbsp; 
                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateProjectTypeStatusSummaryReport();" />
                                    <br />
                                </div>
                                <h3 data-i18n="viewer-report-workflow"></h3>
                                <div>
                                    <br /> <label data-i18n="viewer-report-project"></label> 
                                    <select id="selectProjectsForWorkFlowSummary"></select> &nbsp;&nbsp; <input
                                        type="button" data-i18n="[value]gen-generate" class="btn1"
                                        onclick="javascript:generateProjectWorkFlowSummaryReport();" />
                                    <br />
                                </div>
                                <h3 data-i18n="viewer-report-by-tenure-and-land"></h3>
                                <div>
                                    <br /> <label data-i18n="viewer-report-project"></label> 
                                    <select id="selectProjectsForTenureTypesLandUnitsSummary"></select> &nbsp;&nbsp; 
                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateProjectTenureTypesLandUnitsSummaryReport();" />
                                    <br />
                                </div>

                                <h3>Liberia Farm Status Report</h3>
                                <div>
                                    <br /> <label data-i18n="viewer-report-project"></label> <select
                                        id="selectProjectsForLiberaFarmSummary"></select> &nbsp;&nbsp; 
                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateProjectsForLiberaFarmSummaryReport();" />
                                    <br />
                                </div>

                                <h3 data-i18n="viewer-report-datacorrection"></h3>
                                <div class="filter-details">
                                    <form id="formAdjudicationForms" action="" onsubmit="return false;">
                                        <table>
                                            <tr>
                                                <td>
                                                    <label data-i18n="viewer-report-tran-id"></label>
                                                    <input type="text" id="ccroStartTransid" />
                                                </td>

                                                <td>
                                                    <label>&nbsp;</label> 
                                                    <input type="button" data-i18n="[value]gen-generate" class="btn1" onclick="javascript:generateDataCorrectionReport();" />
                                                </td>
                                            </tr>
                                        </table>
                                        <br />
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="registryTab-div"></div>
                    <div id="bottomstatusbar" class="bottom_statusbar"></div>
                </div>

                <div id="printDiv" style="display: none;"></div>

            </div>

            <div>
                <ul id="myMenu" class="contextMenu">

                    <li class="cm-fullview" id="cm-fullview">
                        <a href="#fullview" data-i18n="viewer-fullzoom"></a>
                    </li>
                    <li class="cm-zoomprevious" id="cm-zoomprevious">
                        <a href="#zoomprevious" data-i18n="viewer-zoomprev"></a>
                    </li>
                    <li class="cm-zoomnext" id="cm-zoomnext">
                        <a href="#zoomnext" data-i18n="viewer-zoomnext"></a>
                    </li>
                    <li class="cm-fixedzoomin separator" id="cm-fixedzoomin">
                        <a href="#fixedzoomin" data-i18n="viewer-fixedzoomin"></a>
                    </li>
                    <li class="cm-fixedzoomout" id="cm-fixedzoomout">
                        <a href="#fixedzoomout" data-i18n="viewer-fixedzoomout"></a>
                    </li>
                    <li class="cm-pan" id="cm-pan">
                        <a href="#pan" data-i18n="viewer-pan"></a>
                    </li>
                    <li class="cm-info" id="cm-info">
                        <a href="#info" data-i18n="viewer-info"></a>
                    </li>
                    <li class="cm-selectbox separator" id="cm-selectbox">
                        <a href="#selectbox" data-i18n="viewer-select-by-rect"></a>
                    </li>
                    <li class="cm-clearselection" id="cm-clearselection">
                        <a href="#clearselection" data-i18n="viewer-clear"></a>
                    </li>
                </ul>
            </div>

            <div id="print-dialog-form" data-i18n="[title]gen-print-pref" style="display: none;">

                <form class="cmxform" id="printformID" action="" onsubmit="return false;">
                    <fieldset>
                        <p>
                            <label for="email" data-i18n="gen-select-choise"></label>
                        </p>
                        <div id="radioPrint">
                            <input class="selectPrint" name="print" type="radio" value="1" id="pre-one" />
                            <label for="pre-one" data-i18n="viewer-parcel-details"></label> 
                            <input class="selectPrint" name="print" type="radio" value="2" id="pre-two" />
                            <label for="pre-two" data-i18n="viewer-map-layout"></label>
                        </div>
                    </fieldset>
                </form>
            </div>

        </div>
        <div id="querycontent" title="Query Builder"></div>
        <div id="tablegridContainer">

            <form method="post" action="" id="exportFrm">
                <input type="hidden" name="csvBuffer" id="csvBuffer" value="" />
            </form>
        </div>
        <div id="savequery" title="Save Query"></div>
        <div id="thematiccontent" title="Thematic"></div>
        <div id="freezeDiv"
             style="visibility: hidden; z-index: 10000; filter: alpha(opacity = 50); /*older IE*/ filter: progid:DXImageTransform.Microsoft.Alpha(opacity=50); /* IE */ -moz-opacity: .20; /*older Mozilla*/ -khtml-opacity: 0.5; /*older Safari*/ opacity: 0.5; /*supported by current Mozilla, Safari, and Opera*/ background-color: #ffffff; position: fixed; top: 0px; left: 0px; width: 100%; height: 100%;">
        </div>

        <div id="footer"></div>

        <script language="javascript">
            var currentValue = 0;
            var spatial_validType = "";
            function handleClick(myRadio) {
                $('#hamletSpatial').hide();
                console.log(myRadio.title);
                spatial_validType = myRadio.title;

                if (myRadio.value == "3") {

                    jQuery.ajax({
                        url: "landrecords/hamletname/" + activeProject,
                        async: false,
                        success: function (data) {

                            hamletList = data;
                            jQuery("#hamletSpatialId").empty();
                            jQuery("#hamletSpatialId").append(
                                    jQuery("<option></option>").attr("value", 0)
                                    .text($.i18n("gen-please-select")));
                            jQuery.each(hamletList, function (i, hamletobj) {

                                jQuery("#hamletSpatialId").append(
                                        jQuery("<option></option>").attr("value",
                                        hamletobj.id).text(
                                        hamletobj.hamletName));

                            });

                        }
                    });
                    jQuery("#hamletSpatial").show();

                }

            }
        </script>
    </body>
</html>