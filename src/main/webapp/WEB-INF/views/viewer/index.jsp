<%@page import="javax.mail.Session"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    String queryString = request.getQueryString();
    String token = (String) request.getSession().getAttribute("auth");
    java.lang.String s = null;
    java.lang.String principal = null;
    String lang = null;

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
        String urlLang = request.getQueryString();

        if (urlLang != null) {
            int pos = urlLang.indexOf("=");
            if (pos > -1) {
                if (urlLang.contains("#")) {
                    lang = urlLang.substring(++pos, urlLang.length() - 1);
                } else {
                    lang = urlLang.substring(++pos);
                }
            } else {
                lang = "en";
            }
        } else {
            lang = request.getHeader("accept-language");
            int pos = lang.indexOf(",");
            if (pos > -1) {
                lang = lang.substring(0, pos);
            } else {
                lang = "en";
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <title>MAST-Data Management</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <meta name="viewport"
                  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
                <meta name="apple-mobile-web-app-capable" content="yes">
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

                    <script language="javascript">
                        var roles = "<%=s%>";
                        var token = "<%=token%>";
                        token = "_token=" + token;
                        var user = "<%=principal%>";
                        var lang = "<%=lang%>";
                    </script>

                    <script src="http://maps.google.com/maps/api/js?v=3.5&amp;sensor=false"></script>
                    <script src="<c:url value="resources/scripts/Cloudburst-Viewer.js"  />" type="text/javascript"></script>
                    <script language="javascript">
                        var loggedUser = null;
                        var tDlg = new $.timeoutDialog({
                            timeout: 900,
                            countdown: 60,
                            logout_redirect_url: '/mast/j_spring_security_logout',
                            keep_alive_url: '/mast/studio/keepalive/'
                        });

                        changeLanguage = function (selectedLang) {
                            var location = window.location.href;
                            lang = selectedLang;

                            //Find and remove the # from url
                            var _pos = location.indexOf("#");
                            if (_pos != -1) {
                                location = location.substring(0, _pos);
                            }

                            if (location.indexOf("?") == -1) {
                                document.location.href = location + "?lang=" + selectedLang;
                            } else {
                                var pos = location.indexOf("?");
                                var location = location.substring(0, pos) + "?lang=" + selectedLang;
                                document.location.href = location;
                            }
                        }

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

                            /*************** Locale ****************************/
                            var currentLocale = getUrlVar('lang');
                            if (currentLocale == undefined) {
                                currentLocale = "<%=lang%>";
                                if (currentLocale != "en" && currentLocale != "cy") {
                                    currentLocale = "en";
                                }
                            }
                            if (currentLocale) {
                                lang = currentLocale;
                                $.i18n.setLocale(currentLocale);

                            }
                            /*************** End Locale ****************************/
                            $.ajax({
                                type: "POST",
                                url: STUDIO_URL + "user/username/",
                                data: {username: user},
                                success: function (userdetail) {
                                    loggedUser = userdetail;
                                    activeproject = userdetail.defaultproject;

                                    if (project) {
                                        var cnt = 0;
                                        jQuery.each(userdetail.projects, function (i, proj) {

                                            if (project == proj.name) {
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

                                    var viewer = new Cloudburst.Viewer("map", options, maploaded);
                                    viewProjectName(activeproject);
                                }
                            });
                        };
                        //***** Callback function - Called when the viewer ********
                        //has loaded the layers and the map is intialised
                        //****************
                        var maploaded = function (map) {
                            $("#toolbar").show();
                            var toolbar = new Cloudburst.Toolbar(map);

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
                            //windows resize
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
                    <body>
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

                            $(document)
                                    .ready(
                                            function () {

                                                $('.splash')
                                                        .meerkat(
                                                                {
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
                                                if (roles == 'ROLE_PUBLICUSER'
                                                        || roles == 'ROLE_USER')
                                                    $('#tab2').remove();
                                            });
                        </script>

                        <div id="intersectionDialog" title="Spatial Validation"
                             style="display: none;"></div>

                        <div id="container">
                            <!--  header  -->
                            <div class="header-alt">
                                <div class="subHead">
                                    <img src="./resources/images/Logo_text.png" style="width: 100%" />
                                </div>
                                <div class="userinfo">
                                    <ul>
                                        <li style="cursor: pointer"><span> <i
                                                    class="fa fa-user"></i> Welcome <span class="username"><%=principal%></span></span></li>
                                        <li><a href="/mast/j_spring_security_logout">Logout</a></li>
                                    </ul>
                                </div>
                                <!--/loginInfo-->
                            </div>

                            <div class="mainContainer">

                                <div id="mainTabs">
                                    <ul>
                                        <li><a href="#map-tab" id="tab1">Map</a></li>
                                        <li><a href="#landrecords-div" id="tab2">Land Records</a></li>
                                        <li><a href="#tabPersonsEdit" id="tab3">Persons editing</a></li>
                                        <li><a href="#tabReports" id="tab4">Reports</a></li>
                                    </ul>

                                    <div id="map-tab">
                                        <!--  Main Toolbar  -->
                                        <div class="toolBarBG">
                                            <div class="default-project">
                                                <div class="btn-wrap">
                                                    <button id="defaultbutton" class="btn" style="visibility: hidden;"
                                                            title="Go to Default Project"
                                                            onclick="javascript:defaultProject();">
                                                        <i class="fa fa-folder"></i>Default
                                                    </button>
                                                </div>
                                            </div>

                                            <div
                                                style="float: right; width: 10%; postion: relative; height: 34px; padding-top: 4px;">
                                                <div style="padding-top: 5px; padding-left: 0px;">
                                                    <!--<label for="scale" style="height: 1.6em; color: #fff; float: left; margin-right: 5px;">1:</label> -->
                                                    <select id="scale-interval" style="height: 24px; width: 80px;">
                                                    </select>
                                                </div>
                                            </div>

                                            <div style="float: right; postion: relative; height: 38px;">
                                                <div id="toolbar" class="toolbar">
                                                    <div class="fg-buttonset fg-buttonset-single">
                                                        <ul id="mycarousel" class="jcarousel-skin-tango" lang="cy">
                                                            <li id="li-intersection">
                                                                <button id="intersection"
                                                                        title="Spatial Validation"
                                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img src="<c:url value="resources/images/viewer/toolbar/intersections.png" />" />
                                                                </button>
                                                            </li>

                                                            <li id="li-openproject">
                                                                <button id="openproject"
                                                                        title="Open Project"
                                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img alt="" src="<c:url value="resources/images/viewer/toolbar/open.png" />" />
                                                                </button>
                                                            </li>

                                                            <li id="li-zoomin">
                                                                <button id="zoomin"
                                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                        title="Zoom In">
                                                                    <img src="<c:url value="resources/images/viewer/navi/zoom_in.png" />" />
                                                                </button>
                                                            </li>

                                                            <li id="li-zoomout">
                                                                <button id="zoomout"
                                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                        title="Zoom Out">
                                                                    <img src="<c:url value="resources/images/viewer/navi/zoom_out.png" />" />
                                                                </button>
                                                            </li>
                                                            <li id="li-pan"><button id="pan"
                                                                                    class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right ui-state-active1"
                                                                                    title="Pan">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/center.png" />" />
                                                                </button></li>
                                                            <li id="li-zoomtolayer"><button id="zoomtolayer"
                                                                                            class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                            title="Zoom To Layer">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/zoom_layer.png" />" />
                                                                </button></li>
                                                            <li id="li-fixedzoomin"><button id="fixedzoomin"
                                                                                            class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                            title="Fixed ZoomIn">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/fixed_zoom_in.png" />" />
                                                                </button></li>
                                                            <li id="li-fixedzoomout"><button id="fixedzoomout"
                                                                                             class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                             title="Fixed ZoomOut">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/fixed_zoom_out.png" />" />
                                                                </button></li>
                                                            <li id="li-zoomprevious"><button id="zoomprevious"
                                                                                             class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                             title="Zoom Previous">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/previous.png" />" />
                                                                </button></li>
                                                            <li id="li-zoomnext"><button id="zoomnext"
                                                                                         class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                         title="zoom Next">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/next.png" />" />
                                                                </button></li>
                                                            <li id="li-fullview"><button id="fullview"
                                                                                         class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                         title="Full Zoom">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/zoom_full.png" />" />
                                                                </button></li>
                                                            <li id="li-info"><button id="info" title="Info"
                                                                                     class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/info.png" />" />
                                                                </button></li>
                                                            <li id="li-measurelength"><button id="measurelength"
                                                                                              title="Measure"
                                                                                              class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/length-measure.png" />" />
                                                                </button></li>
                                                            <li id="li-selectfeature"><button id="selectfeature"
                                                                                              title="Select Feature"
                                                                                              class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/select-Feature.png" />" />
                                                                </button></li>
                                                            <li id="li-selectbox"><button id="selectbox"
                                                                                          title="Select By Rectangle"
                                                                                          class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/select-rectangle.png" />" />
                                                                </button></li>
                                                            <li id="li-selectpolygon"><button id="selectpolygon"
                                                                                              title="Select By Polygon"
                                                                                              class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/navi/select-polygon.png" />" />
                                                                </button></li>
                                                            <li id="li-clear_selection"><button id="clear_selection"
                                                                                                title="Clear"
                                                                                                class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/clear_selection.png" />" />
                                                                </button></li>
                                                            <li id="li-search"><button id="search" title="Search"
                                                                                       class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img alt=""
                                                                         src="<c:url value="resources/images/viewer/navi/search.png" />" />
                                                                </button></li>
                                                            <li id="li-zoomtoxy"><button id="zoomtoxy"
                                                                                         title="Zoom To XY"
                                                                                         class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img alt=""
                                                                         src="<c:url value="resources/images/viewer/navi/zoomtoxy.png" />" />
                                                                </button></li>
                                                            <li id="li-print"><button id="print" title="Print"
                                                                                      class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/print.png" />" />
                                                                </button></li>
                                                            <li id="li-query"><button id="query" title="Query Builder"
                                                                                      class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/query.png" />" />
                                                                </button></li>
                                                            <li id="li-bookmark"><button id="bookmark" title="Bookmarks"
                                                                                         class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/bookmark.png" />" />
                                                                </button></li>
                                                            <li id="li-maptip"><button id="maptip" title="Map Tips"
                                                                                       class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/maptips.png" />" />
                                                                </button></li>
                                                            <li id="li-thematic"><button id="thematic" title="Thematic"
                                                                                         class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/thematic.png" />" />
                                                                </button></li>
                                                            <li id="li-textstyle"><button id="textstyle"
                                                                                          title="Text Style"
                                                                                          class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img alt=""
                                                                         src="<c:url value="resources/images/viewer/toolbar/text-style.png" />" />
                                                                </button></li>
                                                            <li id="li-export"><button id="export" title="Export Data"
                                                                                       class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/export.png" />" />
                                                                </button></li>
                                                            </li>
                                                            <li id="li-exportmap"><button id="exportmap"
                                                                                          title="Export Map"
                                                                                          class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img alt=""
                                                                         src="<c:url value="resources/images/viewer/toolbar/exportmap.png" />" />
                                                                </button></li>
                                                            <li id="li-markup"><button id="markup" title="Markup"
                                                                                       class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img alt=""
                                                                         src="<c:url value="resources/images/viewer/toolbar/markup.png" />" />
                                                                </button></li>
                                                            <li id="li-editing"><button id="editing" title="Editing"
                                                                                        class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right">
                                                                    <img alt=""
                                                                         src="<c:url value="resources/images/viewer/toolbar/edit.png" />" />
                                                                </button></li>
                                                            <li id="li-complaint"><button id="complaint"
                                                                                          class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                          title="Complaint">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/complaint.png" />" />
                                                                </button></li>
                                                            <li id="li-report"><button id="report"
                                                                                       class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                       title="Report">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/report.png" />" />
                                                                </button></li>
                                                            <li id="li-importdata"><button id="importdata"
                                                                                           class=" fg-button ui-state-default1 ui-priority-primary1 ui-corner-left ui-corner-right"
                                                                                           title="Import Data">
                                                                    <img
                                                                        src="<c:url value="resources/images/viewer/toolbar/import.png" />" />
                                                                </button></li>
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

                                        <table cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td valign="top">
                                                    <div id="sidebar">
                                                        <ul>
                                                            <li>
                                                                <a href="#tabs-LayerManager">
                                                                    <span id="layermgr">Layer Manager</span>
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
                                    <!--Land records div  -->
                                    <div id="landrecords-div"></div>
                                    <div id="tabPersonsEdit">
                                        <div style="padding: 7px;font-size: 11px;">
                                            <input type="button" id="btnLoadPersons" value="Load" class="btn1" 
                                                   onclick="javascript:loadPersonsForEditing();" style="margin-bottom: 10px;" />
                                            <div id="personsEditingGrid"></div>
                                        </div>
                                    </div>
                                    <div id="tabReports">
                                        <div style="padding: 10px;">
                                            <div id="reportsAccordion">
                                                <h3>Adjudication Forms</h3>
                                                <div class="filter-details">
                                                    <form id="formAdjudicationForms" action="" onsubmit="return false;">
                                                        <table>
                                                            <tr>
                                                                <td>
                                                                    <label>Record (start)</label>
                                                                    <input type="text" id="adjStart" value="1" />
                                                                </td>
                                                                <td>
                                                                    <label>Record (end)</label>
                                                                    <input type="text" id="adjEnd" value="100000" class="input-medium" />
                                                                </td>
                                                                <td>
                                                                    <label>&nbsp;</label>
                                                                    <input type="button" value="Generate" class="btn1" onclick="javascript:generateAdjudicationForms();" />
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <br />
                                                    </form>
                                                </div>
                                                <h3>CCRO Forms</h3>
                                                <div class="filter-details">
                                                    <form id="formAdjudicationForms" action="" onsubmit="return false;">
                                                        <table>
                                                            <tr>
                                                                <td>
                                                                    <label>Record (start)</label>
                                                                    <input type="text" id="ccroStart" value="1" />
                                                                </td>
                                                                <td>
                                                                    <label>Record (end)</label>
                                                                    <input type="text" id="ccroEnd" value="100000" class="input-medium" />
                                                                </td>
                                                                <td>
                                                                    <label>&nbsp;</label>
                                                                    <input type="button" value="Generate" class="btn1" onclick="javascript:generateCcros();" />
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <br />
                                                    </form>
                                                </div>
                                                <h3>Transaction Sheet</h3>
                                                <div>
                                                    <br />
                                                    <input type="button" value="Generate" class="btn1" onclick="javascript:generateTransactionSheet(0);" />
                                                    <br />
                                                </div>
                                                <h3>District Register Book</h3>
                                                <div>
                                                    <br />
                                                    <input type="button" value="Generate" class="btn1" onclick="javascript:generateDistrictRegBook();" />
                                                    <br />
                                                </div>
                                                <h3>Village Register Book</h3>
                                                <div>
                                                    <br />
                                                    <input type="button" value="Generate" class="btn1" onclick="javascript:generateVillageRegBook();" />
                                                    <br />
                                                </div>
                                                <h3>Village Issuance Book</h3>
                                                <div>
                                                    <br />
                                                    <input type="button" value="Generate" class="btn1" onclick="javascript:generateVillageIssuanceBook();" />
                                                    <br />
                                                </div>
                                                <h3>Claims Profile Statistics</h3>
                                                <div>
                                                    <br />
                                                    <label>Village/Project:</label>
                                                    <select id="selectProjects"></select>
                                                    &nbsp;&nbsp;
                                                    <input type="button" value="Generate" class="btn1" onclick="javascript:generateClaimsProfile();" />
                                                    <br />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="bottomstatusbar" class="bottom_statusbar"></div>
                                </div>

                                <!-- Popup for spatial validation -->

                                <div id="validation-dialog-form" title="Spatial Validation"
                                     style="display: none;">



                                    <form class="cmxform" id="spatialValidationformID" action="" onsubmit="return false;">

                                        <fieldset>

                                            <div id="radioSpatial">
                                                <div class="bottom-clr-5">
                                                    <input class="selectSpatial" name="spatial_validation"
                                                           type="radio" value="1" id="pre-selectAll"
                                                           onclick="handleClick(this)" title="Select all" /><label
                                                           for="pre-english">Select all</label>
                                                </div>
                                                <div class="bottom-clr-5">
                                                    <input class="selectlang" name="spatial_validation" type="radio"
                                                           value="2" id="pre-selectRect" onclick="handleClick(this)"
                                                           title="Select by rectangle" /><label for="pre-swahili">Select
                                                        by rectangle</label>
                                                </div>
                                                <div class="bottom-clr-5">
                                                    <input class="selectlang" name="spatial_validation" type="radio"
                                                           value="3" id="pre-wahili" onclick="handleClick(this)"
                                                           title="Select by hamlet" /><label for="pre-selectHam">Select
                                                        by hamlet</label>
                                                </div>
                                                <div id="hamletSpatial" style="display: none">
                                                    <select name="hamletSpatialId" id="hamletSpatialId">
                                                    </select>

                                                </div>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                                <!-- End Popup -->

                                <div id="printDiv" style="display: none;"></div>

                                <!--  End of Main Toolbar  -->

                                <!--  Navigation floating toolbar -->

                            </div>


                            <!--  End Navigation floating toolbar -->

                            <!-- Context Menu -->
                            <div>
                                <ul id="myMenu" class="contextMenu">

                                    <li class="cm-fullview" id="cm-fullview"><a href="#fullview">Full
                                            Extent</a></li>
                                    <li class="cm-zoomprevious" id="cm-zoomprevious"><a
                                            href="#zoomprevious">Previous Zoom</a></li>
                                    <li class="cm-zoomnext" id="cm-zoomnext"><a href="#zoomnext">Next
                                            Zoom</a></li>

                                    <li class="cm-fixedzoomin separator" id="cm-fixedzoomin"><a
                                            href="#fixedzoomin">Fixed Zoom In</a></li>
                                    <li class="cm-fixedzoomout" id="cm-fixedzoomout"><a
                                            href="#fixedzoomout"> Fixed Zoom Out</a></li>
                                    <li class="cm-pan" id="cm-pan"><a href="#pan"> Pan</a></li>
                                    <li class="cm-info" id="cm-info"><a href="#info">Info</a></li>
                                    <li class="cm-selectbox separator" id="cm-selectbox"><a
                                            href="#selectbox">Select Features</a></li>
                                    <li class="cm-clearselection" id="cm-clearselection"><a
                                            href="#clearselection"> Clear Selected Feature(s)</a></li>


                                </ul>
                            </div>
                            <div id="print-dialog-form" title="Print Preference"
                                 style="display: none;">



                                <form class="cmxform" id="printformID" action=""
                                      onsubmit="return false;">

                                    <fieldset>


                                        <p>
                                            <label for="email">Select your choice</label>
                                        </p>
                                        <div id="radioPrint">
                                            <input class="selectPrint" name="print" type="radio" value="1"
                                                   id="pre-one" /><label for="pre-one">Parcel Details</label> <input
                                                   class="selectPrint" name="print" type="radio" value="2"
                                                   id="pre-two" /><label for="pre-two">Map Layout</label>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>

                            <!--  End Context Menu -->
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
                             style="visibility: hidden; z-index: 10000; filter: alpha(opacity = 50); /*older IE*/ filter: progid:DXImageTransform.Microsoft.Alpha(                                  opacity=                                  50); /* IE */ -moz-opacity: .20; /*older Mozilla*/ -khtml-opacity: 0.5; /*older Safari*/ opacity: 0.5; /*supported by current Mozilla, Safari, and Opera*/ background-color: #ffffff; position: fixed; top: 0px; left: 0px; width: 100%; height: 100%;">
                        </div>

                        <div id="footer">

                        </div>


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
                                                    .text("Please Select"));
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