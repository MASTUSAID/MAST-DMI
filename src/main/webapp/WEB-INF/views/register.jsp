<%@ page session="true" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet"
				href="<c:url value="./resources/styles/landing.css" />"
				type="text/css" media="screen, projection" />

<link rel="stylesheet"
	href="<c:url value="./resources/scripts/jquery-ui-1.8.13/css/redmond/jquery-ui-1.8.13.custom.css" />"
	type="text/css" media="screen, projection" />

<link rel="stylesheet" type="text/css" media="print, projection, screen"
	href="<c:url value="./resources/scripts/jquery-alert/jquery.alerts.css" />" />

<script
	src="<c:url value="./resources/scripts/jquery-1.7.1/jquery-1.7.1.min.js"  />"
	type="text/javascript"></script>
<script
	src="<c:url value="./resources/scripts/jquery-ui-1.8.13/jquery-ui-1.8.13.custom.min.js"  />"
	type="text/javascript"></script>
<script
	src="<c:url value="./resources/scripts/jquery-validate/jquery.validate.js"  />"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<c:url value="./resources/scripts/spatialvue/studio/registerUser.js" />"></script>
	
<script type="text/javascript"
	src="<c:url value="./resources/scripts/jquery-alert/jquery.alerts.js" />"></script>

	
<script type="text/javascript">
function formatPhone(){
	 number = $('#phone').val();
	 number = number.replace(/\s/g, "");
	 if(number != undefined && number != ""){
		 var regexLetter = /^[0-9]+$/;
		 if(regexLetter.test(number)){
			 _val = number.substring(0, 5);
			 _val = _val + " " + number.substring(5);
			 $('#phone').val(_val);
		 }else{
			 jAlert("Invalid Phone Number");
		 }
	 }else{
		 //jAlert("Invalid Mobile Number");
	 }
}

function formatMobile(){
	 number = $('#mobile').val();
	 if(number.length > 0){
	 	number = number.replace(/\s/g, "");
		 if(number != undefined && number != ""){
			 var regexLetter = /^[0-9]+$/;
			 if(regexLetter.test(number)){
				 _val = number.substring(0, 5);
				 _val = _val + " " + number.substring(5);
				 $('#mobile').val(_val);
			 }else{
				 jAlert("Invalid Mobile Number");
			 }
		 }else{
			 //jAlert("Invalid Mobile Number");
		 }
	 }
}

</script>

<title>Registration Form</title>
</head>
<body>
	<!--start:header-->
	<div id="header">
		<div class="widthheader">
			<div class="leftheader">
				<img src="<c:url value="resources/images/viewer/logo-text.png" />" />
			</div>

		</div>
	</div>


	<!--end:header-->
	<form class="cmxform" id="publicUserForm" onsubmit="return false;">
		<div id="signup-form">
			<ul id="company-info" class="section">
				<li class="header"><span></span>
					<h3>Registration</h3>
					<h4>
						To register please enter your details on the form below. Fields
						marked with <em style="font-size: 15px;">&#42;</em> are mandatory.
					</h4></li>
				<li><span> <label for="userName">Name <em>&#42;</em>
					</label> <input type="text" class="int" id="name" name="name" value="" /> 
						<div class="field_with_errors">
					<div id="errName" name="errName" style="display:none" class="formError"></div>
					</div>
					</span>
				</li>


				<li><span> <label for="userName">Password <em>&#42;</em>
					</label> <input type="password" class="int" id="password" name="password"
						value="" /> 
						<div class="field_with_errors">
					<div style="display:none" id="errPassword" name="errPassword" class="formError"></div>
						</div>
						
						</span></li>

				<li><span> <label for="userName">Re-Enter
							Password <em>&#42;</em>
					</label> <input type="password" class="int" id="confirmPassword"
						name="confirmPassword" value="" />
						<div style="display:none" id="errConfirmPassword" name="errConfirmPassword" class="formError"></div>
						</span>
						</li>


				<li><span> <label>Phone <em>&#42;</em>
					</label> <input type="text" class="int" id="phone" name="phone" value="" onblur="javascript:formatPhone();" />
					
					<div style="display:none" id="errPhone" name="errPhone" class="formError"></div>
					
				</span></li>


				<li><span> <label>Mobile </label> <input type="text"
						class="int" id="mobile" name="mobile" value="" onblur="javascript:formatMobile();" />
						<div style="display:none" id="errMobile" name="errMobile" class="formError">
						
						</div>
						</span>
						
						</li>

				<li><span> <label>Language Pref <em>&#42;</em>
					</label> <select id="langpref" name="langpref">
							<option value="">Select Language</option>
							<option value="English">English</option>
							<option value="Welsh">Welsh</option>
					</select> 
					<div style="display:none" id="errlang" name="errlang" class="formError"></div>
					
					</span>
					
					</li>
				<li><span> <label>Email (use as userid) <em>&#42;</em>
					</label> <input type="text" class="int" id="email" name="email" value="" />
					<div style="display:none" id="errEmail" name="errEmail" class="formError"></div>
				</span></li>
				<li><span> <label>Address <em>&#42;</em>
					</label> <textarea id="address" class="qb_querybox" rows="2" name="address"
							spellcheck="false"> </textarea>
							<div style="display:none" id="errAddress" name="errAddress" class="formError"></div>
							</span>
							</li>
				<li class="bottom"><input id="btnSubmit" class="btn-w"
					type="submit" value="Submit" onclick="javascript:registerUsers();" />
				</li>
			</ul>

		</div>
	</form>
</body>
</html>