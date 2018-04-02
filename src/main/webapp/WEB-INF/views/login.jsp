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
               <div class="demo">
               
                <div class="header_wrapper">
                    <div id="usaid_logo" onClick="window.location = 'http://usaid.gov/land-tenure';"></div>
                    <div class="header_title">Mobile Application to Secure Tenure (MAST)<br />Data Management Infrastructure</div>
                </div>
                
                <div class="home_background">
                    <div class="homebox" id="homebox_whatismast">
                        <div class="homebox_title">What is MAST?</div>
                        <p>
	                        MAST is a suite of innovative technology tools and inclusive methods that use mobile phones and tablets to efficiently, transparently, 
	                        and affordably map and document land and resource rights.  MAST helps people and communities define, record, and register local land boundaries 
	                        and important information, such as uses, how the land is occupied, as well as the names and photos of people who live there. MAST combines 
	                        an easy-to-use mobile phone application with training and a participatory approach that empowers citizens in the process of understanding, 
	                        mapping, and registering their own rights and resources.
                        </p>
                        <p>
	                        MAST has been used by stakeholders to solve a myriad of challenges that impede tenure security by making the technology more accessible 
	                        to more people. MAST provides transparent and effective mechanisms that improve land governance, build institutional capacities, engage 
	                        citizens and help them understand their rights, responsibilities in either formal or informal tenure systems or in areas where these two 
	                        systems operate simultaneously.
	                        </p>
	                        <p> Learn more about this project at <a target="_new" href="https://www.land-links.org/tool-resource/mobile-applications-to-secure-tenure-mast/"> Learn more about this project at USAID's Land Tenure Portal. </a>.</p>
                     
                        
                        
                        
                        
                    </div>
                    <div class="homebox1" id="homebox_projects">
                        <div class="homebox1_title">Login to MAST System</div>
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
                   
                    </form>
                     </div>

                   
                </div>
                
                </div>
				
				
				<div class="footer"> 
					<!-- <div id="brochure_button" onClick="window.open('./resources/pdf/MAST_Brochure_web.pdf');">
					</div>
               		 <div id="android_logo">
                	</div> -->
               	 <span class="copyright-text">&copy; 2018. All Rights Reserved.</span>
                </div>
           
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