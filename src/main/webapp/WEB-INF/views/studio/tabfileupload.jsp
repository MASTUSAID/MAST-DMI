<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!ajaxRequest}">
<html>
<head>
	<title>Tab file Upload</title>
		
</head>
<body>
</c:if>

	<!-- div id="fileupload" class="dialog-v"-->
    	<form id="tabfileuploadForm" action="tabfileupload" method="POST" enctype="multipart/form-data" >
	     <table id="projectDetails" class="styleForm inputint">
			<tbody id="projectGeneralBody">
        		<tr>
				<td>
				
				<!-- form class="styleForm"-->
				<div class="formRow">
				<label>Select Layer:</label>
				<select name="layername" id="layername" class="int">
        				<!-- option  value="">Please Select</option>
        				<option  value="Row_Path">Row_Path</option>
        				<option  value="Furniture">Furniture</option>
        				<option  value="Access_Land_polygon">Access_Land_polygon</option>  
        				<option  value="Warden_Areas">Warden_Areas</option-->            
       			</select>
				</div>
				
				<div class="formRow">
				<label>Select Mode:</label>
				<!-- input id="SelectModeCreate" type="radio" checked="checked" name="SelectMode" value="" /><span> Create</span>
				
				<input id="SelectModeUpdate" type="radio" name="SelectMode" value="" /><span> Update</span-->
				<select name="SelectMode" id="SelectMode" class="int">
        				<option  value="">Please Select</option>
        				<option  value="Create">Create</option>
        				<option  value="Update">Update</option>
        				         
       			</select>
			
				</div>
								
					<input type="hidden"  class="int" id="row_id" name="row_id" value="10"/>				
								
				<div class="formRow">
				<label>Select File:</label>
				
				<!-- input type="file" name="datafile" size="40" /-->
					<input id="file"  size="44" style="height:25px; margin-top:0.5em;" name="file" type="file"/> 
			
				</div>
				
				<!--div class="formRow">
				<label>Label Uplaod:</label>
				<input type="file" name="datafile1" size="40" />
			
				
			
				</div-->
								
				<div class="buttons">
				<input id="btnupload" class="btn" type="submit" style="margin-top:5px; height:25px;" value="Upload"/> 
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