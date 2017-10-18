<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<%
    java.lang.String s = null;
    java.lang.String principal = null;
    try {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal = user.getUsername();
        //java.util.Collection<org.springframework.security.core.GrantedAuthority> authorities = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getAuthorities();

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
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>MAST-Administration Tool</title>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <!--  -->
        <link rel="stylesheet"
              href="<c:url value="resources/styles/studio/blueprint/reset.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/studio/blueprint/screen.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/studio/blueprint/print.css" />"
              type="text/css" media="print" />

        <link rel="stylesheet" type="text/css" media="screen"
              href="<c:url value="resources/scripts/jquery-ui-1.8.13/css/redmond/jquery-ui-1.8.13.custom.css" />" />
        <link rel="stylesheet" type="text/css" media="screen"
              href="<c:url value="resources/scripts/jqGrid/css/ui.jqgrid.css" />" />

        <link rel="stylesheet" type="text/css" media="print, projection, screen"
              href="<c:url value="resources/scripts/tablesorter/themes/blue/style.css" />" />

        <link rel="stylesheet" type="text/css" media="print, projection, screen"
              href="<c:url value="resources/scripts/jquery-alert/jquery.alerts.css" />" />

        <link rel="stylesheet"
              href="<c:url value="resources/styles/studio/form-common.css" />"
              type="text/css" media="print" />

        <link rel="stylesheet"
              href="<c:url value="resources/styles/studio/header.css" />"
              type="text/css" media="screen, projection" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/font-awesome.min.css" />"
              type="text/css" media="screen, projection" />
        <script
            src="<c:url value="resources/scripts/Cloudburst-Studio.js"  />"
        type="text/javascript"></script>	


        <!--[if IE 8]>
                        <link rel="stylesheet" href="<c:url value="resources/styles/studio/blueprint/ie.css" />" type="text/css" media="screen, projection">
                <![endif]-->
        <!--[if IE 7]>
           <link rel="stylesheet" href="<c:url value="resources/styles/studio/blueprint/ie-style.css" />" type="text/css" media="screen, projection">
            <![endif]-->
        <style type="text/css">
            /* body { font-size: 62.5%; } */
            /* label, input { display:block; } */
            input.text { margin-bottom:12px; width:95%; padding: .4em; }
            fieldset { padding:0; border:0; margin-top:25px; }
            h1 { font-size: 1.2em; margin: .6em 0; }
            div#users-contain {  width: 350px; margin: 20px 0; }
            div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
            div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
            .ui-button { outline: 0; margin:0; padding: .4em 1em .5em; text-decoration:none; !important; cursor:pointer; position: relative; text-align: center; }
            .ui-dialog .ui-state-highlight, .ui-dialog .ui-state-error { padding: .3em;  }


        </style>

        <link rel="stylesheet" type="text/css" media="print, projection, screen"
              href="<c:url value="resources/styles/studio/vtav.css" />" />

        <link rel="stylesheet" type="text/css" media="print, projection, screen"
              href="<c:url value="resources/styles/studio/studio.css" />" />

    </head>
    <body>

        <div id="_splash" class="splash">				
            <div id="splash-content"> 
                <img id="enter" src="resources/images/studio/splash-logo.png" alt="Meerkat" /> 
            </div>
        </div>

        <div id="_loader" class="loader"><img src="resources/images/studio/ajax-loader.gif" /></div>
        <script language="javascript">
            var token = null;
            var roles = '<%=s%>';
            var useremail = '<%=principal%>';
            $(document).ready(
                    function () {
                        $('.splash').meerkat({
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
                    });
        </script>
        <!--start:header-->
        <div class="header-alt">
            <div class="subHead">
                <img src="./resources/images/Logo_text.png" style="width:100%"/>	
            </div>
            <div class="userinfo" >
                <ul>
                    <li  style="cursor:pointer"><span>	<i class="fa fa-user"></i>
                            Welcome <span class="username"><%=principal%></span></span></li>
                    <li><a href="/mast/j_spring_security_logout">Logout</a></li>
                </ul>
            </div>
        </div>

        <div id="vtab">
            <ul>
                <li id="user"  class="login selected"><img src="resources/images/studio/vtab/users.png" /><span>Users</span></li>			
                <li id="layer" class="login"><img src="resources/images/studio/vtab/layers.png" /><span>Data Layers</span></li>
                <li id="layergroup" class="login"><img src="resources/images/studio/vtab/layergroup.png" /><span>Layers Groups</span></li>
                <li id="project" class="login"><img src="resources/images/studio/vtab/project.png" /><span>Survey Projects</span></li>
                <li id="masterattribute"  class="login"><img src="resources/images/studio/vtab/users.png" /><span>Master Attribute</span></li>
            </ul>

            <div id="users"></div>  
            <div id="layers"></div>    		
            <div id="layergroups"></div> 
            <div id="projects"></div>
            <div id="masterattribute-div"></div>  

        </div>
        <div id="footer"></div>
        <div id="signature-dialog-form" title="Signature" style="display: none;">
            <form id="formSignature" action="" onsubmit="return false;">
                <div style="margin: 10px;">
                    <label>Select signature</label>
                    <input name="fileSignature" id="fileSignature" type="file" accept=".jpg,.png,.gif,.jpeg" style="border: #aaa solid 1px;" />
                    <label style="color: #0088cc">.jpeg,.png,.gif</label>
                </div>
            </form>
        </div>
    </body>
</html>