<div id="fileupload">		
		<form id="fileuploadForm" action="fileupload" method="POST" enctype="multipart/form-data" class="cleanform">
			
			<label for="file">File</label>
			<input id="file" type="file" name="file" />
			<p><button type="submit">Upload</button></p>
			
			<div class="header">		  	
		  		<c:if test="${not empty message}">
					<div id="message" class="${message.type}">${message.text}</div>	  		
		  		</c:if>
			</div>		
		</form>
		
	</div>