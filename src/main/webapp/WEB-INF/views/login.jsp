<%@page contentType="text/html" pageEncoding="UTF-8" session="true" %>
<%@page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@page import="org.springframework.security.core.AuthenticationException"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${langCode}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="${langCode}" dir="${empty ltr ? 'ltr' : ltr ? 'ltr' : 'rtl'}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title data-i18n="login-title"></title>
        <script src="<c:url value="./resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"  />" type="text/javascript"></script>
        <link rel="stylesheet" href="<c:url value="./resources/styles/style_login.css" />" type="text/css" media="screen, projection" />
        <%@include file="../jspf/HeaderResources.jspf" %>
    </head>
    <body onload='document.loginForm.j_username.focus();'>
        <%
            String getURL = request.getQueryString();
            String requestParam = request.getQueryString();
            if (requestParam == null) {
                requestParam = "param=1";
            }
            if (requestParam.indexOf("login_error") == -1) {
                String[] params = requestParam.split("&");
                for (int i = 0; i < params.length; i++) {
                    String[] reqParam = params[i].split("=");
                    session.setAttribute(reqParam[0], reqParam[1]);
                }
            }
        %>
        <div class="demo">

            <div class="header_wrapper">
                <div id="usaid_logo" onClick="window.location = 'http://usaid.gov/land-tenure';"></div>
                <div class="header_title" data-i18n="[html]gen-mast-dmi"></div>
                <div id="languageBarWrapper">
                    <%@include file="../jspf/LanguageBar.jspf" %>
                </div>
            </div>

            <div class="home_background">
                <div class="homebox" id="homebox_whatismast">
                    <div class="homebox_title" data-i18n="gen-whatismast"></div>
                    <p data-i18n="gen-mastdesc"></p>
                    <p data-i18n="gen-mastdesc2"></p>
                    <p data-i18n="[html]gen-mastdesc3"></p>
                </div>
                <div class="homebox1" id="homebox_projects">
                    <div class="homebox1_title" data-i18n="login-logintomast"></div>
                    <form id="loginForm" name="loginForm" action="j_spring_security_check" method="post">

                        <div id="main">
                            <div id="login-box">
                                <div class="formElement">
                                    <div class="lg-login-form-row">
                                        <div id="authfail_div" class="auth"></div>
                                        <div id="login" class="vlabel" data-i18n="login-username"></div>
                                        <div id="login-box-field">
                                            <input class="form-login" id="usernameField" type="text" name="j_username" />
                                        </div>
                                        <div id="helptxt" ></div>
                                    </div>
                                </div>
                                <div class="formElement">
                                    <div class="lg-login-form-row">
                                        <div id="pwd" class="vlabel" data-i18n="login-password"></div>
                                        <div id="login-box-field">
                                            <input  class="form-login" id="passwordField" type="password" name="j_password" />
                                        </div>
                                    </div>
                                </div>
                                <div class="lg-login-form-row">
                                    <input class="login-btn" id="btnLogin" type="submit" data-i18n="[value]login-login" />
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <div class="footer"> 
            <span class="copyright-text" data-i18n="[html]gen-copyright"></span>
        </div>

        <script language="javascript">
            var auth_status = "";
            <%
                if (getURL != null) {
                    if (getURL.equalsIgnoreCase("login_error=1")) {
            %>
            auth_status = "<fmt:message key="LOGIN_FAILED" />";
            <%
                    } else if (getURL.equalsIgnoreCase("access_denied=1")) {
            %>
            auth_status = "<fmt:message key="ERROR_UNAUTHORIZED_ACCESS" />";
            <%
                    }
                }
            %>
            var authDiv = document.getElementById("authfail_div");
            authDiv.appendChild(document.createTextNode(auth_status));
        </script>
    </body>

</html>