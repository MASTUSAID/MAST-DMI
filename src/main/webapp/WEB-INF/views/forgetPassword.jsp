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
<title>Registration Form</title>
<script>
function forgotPassword(){
	var valid = true;
	/* if ( $.trim($("#usrname").val()) == "" )
	    {
	        valid = false;
			$("#errUsrName").show();
			$("#errUsrName").text('Please enter Name');
	    }else{
	    	$("#errUsrName").hide();
	    }*/
	 
	 if ( $.trim($("#usrMail").val()) == "" )
	    {
		  	valid = false;
	        $("#errUsrMail").show();
			$("#errUsrMail").text('Please enter Email');
	    }
		else{
			$("#errUsrMail").hide();
		}
	 
	 if(valid){
		 jQuery.ajax({
		        type: "POST",              
		        url: "user/forgotpassword",
		        data: jQuery("#forgotpasswordform").serialize(),
		        success: function (data) {
		        	if(data==false){
		        		jAlert('User name or email is incorrect.','Forgot Password');
		        	}
		        	else if(data==true){
		        		jAlert('Your password has been sent on your email id.', 'Forgot Password', function () {
		                    document.location.href = "login";
		                 });
		           	}
		        },
		        error: function (XMLHttpRequest, textStatus, errorThrown) {
		           	jAlert('Please try again ','Forgot Password');
		        	 //document.location.href = "login";
		        }
		    }); 
	 }
}

</script>
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
	<form class="cmxform" id="forgotpasswordform" onsubmit="return false;">
		<div id="signup-form">
			<ul id="company-info" class="section">
				<li class="header"><span></span>
					<h3>Forgot Password</h3>
					<h4>
						To retrieve password please enter your details on the form below. Fields
						marked with <em style="font-size: 15px;">&#42;</em> are mandatory.
					</h4></li>
				<!--<li><span> <label for="usrName">User Name <em>&#42;</em>
					</label> <input type="text" class="int" id="usrname" name="usrname" value="" /> 
						<div class="field_with_errors">
					<div id="errUsrName" name="errUsrName" style="display:none" class="formError"></div>
					</div>
					</span>
				</li> -->


				<li><span> <label for="userMail">Email <em>&#42;</em>
					</label> <input type="text" class="int" id="usrMail" name="usrMail"
						value="" /> 
						<div class="field_with_errors">
					<div style="display:none" id="errUsrMail" name="errUsrMail" class="formError"></div>
						</div>
						
						</span></li>

				
				<li><input id="btnSubmitFP"
					type="button" value="Submit" onclick="javascript:forgotPassword();" />
				</li>
			</ul>

		</div>
	</form>
</body>
</html>