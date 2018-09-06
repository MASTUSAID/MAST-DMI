<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!ajaxRequest}">
<html>
<head>
	<title>riskfileupload</title>
</head>
<body>

</c:if>

	<div id="riskfileupload" class="dialog-v">
    	<form id="riskfileuploadForm" action="riskfileupload" method="POST" enctype="multipart/form-data" class="styleForm">
            	<input type="hidden" id="associationid1" name="associationid1">	
				<input type="hidden" id="layername1" name="layername1">	
				<input type="hidden" id="keyfield1" name="keyfield1">	
				<input type="hidden" id="filename1" name="filename1">	
				<input type="hidden" id="filepath1" name="filepath1">	
				<input type="hidden" id="extension1" name="extension1">
                <div class="header">
                	<c:if test="${not empty message}">
						<div id="riskmessage" class="${message.type}">${message.text}</div>	  		
		  			</c:if>
				</div>
    			<table width="100%" cellpadding="1" cellspacing="1">        	
                	<!--start:heading-->
                    <tr>
                        <td class="form_head">
                            Risk Assessment
                        </td>
                   </tr>
               		<!--end:heading--> 
               		<tr>
                    	<td class="spacerupload" >
                    	</td>
               		</tr>
               		
               		<tr>	
						<td>
							<label for="riskAssRequired"> Risk Assessment Required :</label> 
							
							<div>
							<div style="float:left; padding-right:1.0em"">
								<label style="font-weight:normal;">Yes</label> 				
								<input style="margin:0 0 0 0.2em" type="radio" name="riskAssRequired" value="y">
							</div>
							<div style="float:left"">
								<label style="font-weight:normal;">No</label> 				
					            <input style="margin:0 0 0 0.2em" type="radio" name="riskAssRequired" value="n">
							</div>			
							</div>
						</td>
					</tr>
					
               		</table>
               	<div id="table-riskAssRequired">
				<table width="100%" cellpadding="1" cellspacing="1">   
               		<!--start:Description-->
					<tr>
                    	<td class="spacerupload" >
                    	</td>
               		</tr> 
					<tr>
                        <td>
                            Risk Assessment Reference
                            <input class="int" type="text" value="" name="riskassref" id="riskassref" />
                        </td>
                   </tr>
                   <!--end:Description--> 
                   <tr>
                    	<td class="spacerupload" >
                    	</td>
               		</tr> 
                    <!--start:file upload-->
                    <tr>
                    	<td>
                        	File Name
                      
                        	<input id="riskfile" class="btn-holder-upload fileinput" size="33" name="riskfile" type="file"/> 
                        </td>                    
                     </tr> 
                     <tr>
                     	<td id="riskerrmsg" class="msgcolor">
                        	
                        </td>
                    </tr>	            
                    <!--end:file upload-->
                     <tr>
                     	<td class="mssdisplay">
                        	<span class="msgcolor">*</span>File upload limit is 2MB
                        </td>
                        </tr>
                        
                        <tr>
                        <td align="right">
                           <div class="btn-holder-w">
								<input id="riskbtnupload" class="btn-w fileinput btnAttach" type="submit" value="Upload"/> 
							</div>
                        </td>
                   </tr>
                   <tr>
                    	<td class="spacerupload" >
                    	</td>
               		</tr> 
        		</table>
        		</div>
        		<div id="div-riskAssRequired">
        		<div style="clear:both; margin:0.5em 0.5em 0 0.5em;">	
        			<label for="riskAssCompleted">Assessment Completed: </label> 	
					<div style="float:left; padding-right:1.5em"">
						<label style="font-weight:normal;">Yes</label> 				
						<input style="margin:0 0 0 0.7em" type="radio" name="riskAssCompleted" value="y">
					</div>
					<div style="float:left"">
						<label style="font-weight:normal;">No</label> 				
                		<input style="margin:0 0 0 0.7em" type="radio" name="riskAssCompleted" value="n">
					</div>
				</div>
		</div>		
        		
		</form>  
			
		
			
				
					
			
			
			
	</div>
<c:if test="${!ajaxRequest}">
</body>
</html>
</c:if>