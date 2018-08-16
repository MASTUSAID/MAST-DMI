<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>

<fmt:setLocale value="${langCode}" />

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server 

    java.lang.String s = null;
    try {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title data-i18n="gen-app-title"></title>

        <link rel="stylesheet" type="text/css" href="./resources/styles/font-awesome.min.css" />
        <link rel="stylesheet" type="text/css" href="./resources/styles/cloudMain.css" />
        <link rel="stylesheet"
              href="<c:url value="resources/styles/header.css" />"
              type="text/css" media="screen, projection" />
        <script src="<c:url value="./resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"  />" type="text/javascript"></script>
        <%@include file="../jspf/HeaderResources.jspf" %>
        
        <script type="text/javascript">
            var url;
            var qryString;
        </script>
        <%
            if (request.getSession().getAttribute("url") != null) {
        %>	
        <script type="text/javascript">
                var url = "<%=request.getSession().getAttribute("url")%>";
                document.location.href = url;
        </script>

        <%
        } else if (s.equalsIgnoreCase("ROLE_PUBLICUSER") || s.equalsIgnoreCase("ROLE_USER")) {
        %>
        <script type="text/javascript">
            var studio_url = window.location.href;
            var pos = studio_url.indexOf("index");
            studio_url = studio_url.substring(0, pos - 1);
            studio_url = studio_url + "/viewer/";
            document.location.href = studio_url;
        </script>
        <%
            }
        %>
    </head>

    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/Header.jspf" %>
            
            <div class="pageContent">
                <div class="items-container clearfix">
                    
                    <div class="dashboard-item">
                        <a href="studio/"  class="dashboard-content clearfix">
                            <span class="icon-img"><img src="./resources/images/administration.png" height="40"></span>
                            <span class="dashboard-title"><h2 class="margin-top-35" data-i18n="home-admin"></h2></span>
                        </a>
                    </div>

                    <div class="dashboard-item">
                        <a href = "mobileconfig/" class="dashboard-content clearfix">
                            <span class="icon-img-2"><img src="./resources/images/mobile.png" height="40"></span>
                            <span class="dashboard-title"><h2 class="margin-top-35" data-i18n="home-mobile"></h2></span>
                        </a>
                    </div>

                    <div class="dashboard-item">
                        <a href="viewer/" class="dashboard-content clearfix">
                            <span class="icon-img-3"><img src="./resources/images/data.png" height="40"></span>
                            <span class="dashboard-title"><h2 class="margin-top-35" data-i18n="home-dmi"></h2></span>
                        </a>
                    </div>

                </div>
            </div>
                            
            <div class="footer"> 
                <span class="copyright-text" data-i18n="[html]gen-copyright"></span>
            </div>
        </div>
    </body>
</html>
