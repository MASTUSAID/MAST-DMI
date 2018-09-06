<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!ajaxRequest}">
<html>
<head>
	<title>fileupload</title>
		
</head>
<body>
</c:if>

	<div id="fileupload" class="dialog-v">
    	<form id="fileuploadForm" action="fileupload" method="POST" enctype="multipart/form-data" class="styleForm">
            	<input type="hidden" id="associationid" name="associationid">	
				<input type="hidden" id="layername" name="layername">	
				<input type="hidden" id="keyfield" name="keyfield">	
				<input type="hidden" id="filename" name="filename">	
				<input type="hidden" id="filepath" name="filepath">	
				<input type="hidden" id="extension" name="extension">
                <div class="header">
                	<c:if test="${not empty message}">
						<div id="message" class="${message.type}">${message.text}</div>	  		
		  			</c:if>
				</div>
    			<table width="100%" cellpadding="1" cellspacing="1">        	
                	<!--start:heading-->
                    <tr>
                        <td class="form_head">
                            <span id="attachment_header">Attachment</span>
                        </td>
                   </tr>
               		<!--end:heading--> 
               		<tr>
                    	<td class="spacerupload" >
                    	</td>
               		</tr>  
               		<!--start:Description-->
                    <tr>
                        <td>
                            <span id="attachment_desc">Description</span>
                        
                            <input class="int" type="text" value="" name="fileDesc" id="fileDesc" />
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
                        	<span id="attachment_file">File Name</span>
                      
                        	<input id="file" class="btn-holder-upload fileinput" size="33" name="file" type="file"/> 
                        </td>                    
                     </tr> 
                     <tr>
                     	<td id="errmsg" class="msgcolor">
                        	
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
								<input id="btnupload" class="btn-w fileinput btnAttach" type="submit" value="Attach"/> 
							</div>
                        </td>
                   </tr>
                   
        		</table>
        		
		</form>  
			
		
			
				
					
			
			
			
	</div>
<c:if test="${!ajaxRequest}">
</body>
</html>
</c:if>