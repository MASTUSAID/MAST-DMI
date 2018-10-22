<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!ajaxRequest}">
<html>
<head>
	<title>GPS Job file Upload</title>
		
</head>
<body>
</c:if>

	<!-- div id="fileupload" class="dialog-v"-->
    	<form id="gpsfileuploadForm" action="gpsfileupload" method="POST" enctype="multipart/form-data" >
	     <table id="gpsFileTbl" class="styleForm inputint">
			<tbody id="projectGeneralBody">
        		<tr>
				<td>
				
				<div class="formRow">
				
				<label>Select GPS Job FILE:</label>
				<!-- input type="file" name="datafile1" size="40" /-->
				<input id="gpsfile"  size="44" style="height:25px; margin-top:0.5em;" name="gpsfile" type="file"/> 
				
			
				</div>
								
				<div align="right">
				<input id="btngpsupload" class="btnUpload" type="submit" style="margin-top:5px; height:25px;" value="Upload"/> 
				</div>
								
				<!-- /form-->
				
				</td>
				</tr>
    		</tbody>
		</table>
		</form>
		
		
	<!-- /div-->
<c:if test="${!ajaxRequest}">
</body>
</html>
</c:if>