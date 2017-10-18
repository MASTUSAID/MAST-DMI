<%@ page
    import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
    <%@ page
        import="org.springframework.security.core.AuthenticationException"%>


        <%@ page session="true"%>

        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <html>
            <head>
                <title>MAST-DMI Login</title>
                <script src="<c:url value="./resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"  />"
                type="text/javascript"></script>
                <link rel="stylesheet"
                      href="<c:url value="./resources/styles/style_login.css" />"
                      type="text/css" media="screen, projection" />
            </head>
            <body onload='document.loginForm.j_username.focus();'>
                <%
                    String LOGIN_ERR = "";
                    String auth_fail_status = "";
                    String getURL = request.getQueryString();
                    String requestParam = request.getQueryString();
                    if (requestParam == null) {
                        requestParam = "param=1";
                    }
                    if (requestParam.indexOf("login_error") == -1) {
                        String[] params = requestParam.split("&");
                        for (int i = 0; i < params.length; i++) {
                            System.out.println("request attributes: " + i + "is " + params[i]);
                            String[] reqParam = params[i].split("=");
                            System.out.println("request attribute: " + reqParam[0] + " " + reqParam[1]);
                            session.setAttribute(reqParam[0], reqParam[1]);
                        }
                        //session.setAttribute("param", requestParam);
                    }
                    if (getURL != null) {
                        if (getURL.equalsIgnoreCase("login_error=1")) {
                            //out.println("<div class='auth-fail'>Credentials supplied are incorrect</div>");
                            auth_fail_status = "Login credentials incorrect";
                        } else if (getURL.equalsIgnoreCase("access_denied=1")) {
                            //out.println("<div class='auth-fail'>You are not authorized to access the application</div>");
                            auth_fail_status = "Un-authorized access not allowed";
                        }
                    }
                    String urlLang = request.getQueryString();
                    //System.out.println("Url lang: " + urlLang);
                    String lang = null;
                    if (urlLang != null) {
                        int pos = urlLang.indexOf("=");
                        if (pos > -1) {
                            lang = urlLang.substring(++pos);
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
                %>
                <div class="header_bluebar_top"></div>
                <div class="header_wrapper">
                    <div id="usaid_logo" onClick="window.location = 'http://usaid.gov/land-tenure';"></div>
                    <div class="header_title">Mobile Application to Secure Tenure (MAST)<br />Data Management Infrastructure</div>
                </div>
                <div class="header_bluebar_bottom"></div>
                <div class="home_background">
                    <div class="homebox" id="homebox_whatismast">
                        <div class="homebox_title">What is MAST?</div>
                        <p>Around the world, millions of people lack documented land rights. This may be the result of weak land governance systems or
                            limited capacity to provide accessible and accountable land administration services. These institutional challenges are widespread
                            and drive costly and systemic problems on the ground. USAID is addressing these problems through an innovative pilot called the
                            Mobile Applications to Secure Tenure (MAST) project, an easy-to-use, open-source smartphone application that can capture the
                            information needed to issue formal documentation of land rights. Coupled with a cloud-based data management system to store
                            geospatial and demographic information, the project is designed to lower costs and time involved in registering land rights and,
                            importantly, to make the process more transparent and accessible to local people.</p>
                        <p>Learn more about this project at <a target="_new" href="http://www.google.com/url?q=http%3A%2F%2Fwww.usaidlandtenure.net%2Fproject%2Fmobile-applications-secure-tenure-tanzania&sa=D&sntz=1&usg=AFQjCNFWVKqfDObjPCu_W4K--WQ3nPnNVw">USAID's Land Tenure Portal</a>.</p>
                    </div>
                    <div class="homebox" id="homebox_projects">
                        <div class="homebox_title">Login to MAST System</div>
                        <form id="loginForm" name="loginForm" action="j_spring_security_check" method="post">

                            <div id="main">
                                <div id="login-box">
                                    <div class="formElement">
                                        <div class="lg-login-form-row">
                                            <div id="authfail_div" class="auth"> </div>
                                            <div id="login" class="vlabel"></div>
                                            <div id="login-box-field"><input class="form-login" id="usernameField" type="text" name="j_username" /></div>
                                            <div id="helptxt" >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="formElement">
                                        <div class="lg-login-form-row">
                                            <div id="pwd" class="vlabel"></div>
                                            <div id="login-box-field">
                                                <input  class="form-login" id="passwordField" type="password" name="j_password" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="lg-login-form-row">
                                        <input class="login-btn" id="btnLogin" type="submit" value="Login" />
                                    </div>
                                </div>
                            </div>
                    </div>
                    </form>

                    <div style="clear: both;"></div>

                    <div id="brochure_button" onClick="window.open('./resources/pdf/MAST_Brochure_web.pdf');"></div>
                    <div id="android_logo"></div>
                </div>
                <div class="header_bluebar_bottom"></div>
                <div id="copyright">&copy; 2015. All Rights Reserved.</div>
                <script language="javascript">
                    var auth_status = "<%=auth_fail_status%>";
                    var language = "<%=lang%>";
                    displayLanguageStrings(language);
                    var authDiv = document.getElementById("authfail_div");
                    authDiv.appendChild(document.createTextNode(auth_status));

                    function displayLanguageStrings(lang) {
                        //alert(lang);
                        var login = document.getElementById("login");
                        var password = document.getElementById("pwd");
                        var btn = document.getElementById("btnLogin");
                        //	var register = document.getElementById("register");
                        var helptxt = document.getElementById("helptxt");
                        // if(lang.indexOf("en") > -1){
                        login.appendChild(document.createTextNode("Username:"));
                        password.appendChild(document.createTextNode("Password:"));
                        btn.value = "Login";
                    }
                </script>
            </body>

        </html>