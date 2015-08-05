<%@page session="false"%>
<%@page import="java.net.*,java.io.*,java.util.*"%>
<%@page import="org.apache.commons.httpclient.*,org.apache.commons.httpclient.methods.*"%>

<%
	InputStream reqInputStream = null;
	InputStream resInputStream = null;
	OutputStream resOutputStream = null;

	if (request.getMethod().equals("POST")) {

		if (request.getParameter("url") != null
				&& request.getParameter("url") != "") {

			String targetUrl = request.getParameter("url");
			PostMethod post = new PostMethod(targetUrl);
			reqInputStream = request.getInputStream();
			RequestEntity entity = new InputStreamRequestEntity(
					reqInputStream, request.getContentType());
			//RequestEntity entity = new InputStreamRequestEntity(reqInputStream, "text/xml;charset=ISO-8859-1");
			post.setRequestEntity(entity);
			if(targetUrl.indexOf("maps.nationalparks.gov.uk") == -1){
				System.out.println("Into geoserver authorization --- ");
				//post.setRequestHeader("Authorization", "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE=");
			}

			HttpClient httpclient = new HttpClient();
			try {
				httpclient.executeMethod(post);
				resInputStream = post.getResponseBodyAsStream();

				String contentType = post.getResponseHeader(
						"Content-Type").getValue();
				response.setContentType(contentType);

				// for JSP only
				out.clear();
				out = pageContext.pushBody();

				resOutputStream = response.getOutputStream();
				int buffer_length = 4096;
				byte[] buffer = new byte[buffer_length];
				int bytesRead = 0;
				while ((bytesRead = resInputStream.read(buffer, 0,
						buffer_length)) > 0) {
					resOutputStream.write(buffer, 0, bytesRead);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}
		}

	} else

	{

		if (request.getParameter("url") != null
				&& request.getParameter("url") != "") {

			String resourceUrlStr = request.getParameter("url");
			Enumeration enu = request.getParameterNames();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				if (name.equalsIgnoreCase("url") == false) {
					resourceUrlStr = resourceUrlStr
							+ "&"
							+ name
							+ "="
							+ URLEncoder.encode(request
									.getParameter(name));
				}
			}
			URL resourceUrl = new URL(resourceUrlStr);
			//URL resourceUrl = new URL(request.getParameter("resourceUrl"));				
			HttpURLConnection connection = (HttpURLConnection) resourceUrl
					.openConnection();
			
			if(resourceUrlStr.indexOf("maps.nationalparks.gov.uk") == -1){
				System.out.println("Into geoserver authorization --- ");
				//connection.setRequestProperty("Authorization", request.getHeader("Authorization"));
				//connection.setRequestProperty("Authorization", "Basic YWRtaW5AZXJ5cmktbnBhLmdvdi51azpQNHJDM3J5cjE=");
			}
			
			connection.setDoInput(true);
			connection.setRequestMethod(request.getMethod());
			response.setContentType(connection.getContentType());
			// what's this for
			out.clear();
			out = pageContext.pushBody();
			InputStream ristream = connection.getInputStream();
			OutputStream rostream = response.getOutputStream();
			final int length = 5000;
			byte[] bytes = new byte[length];
			int bytesRead = 0;
			while ((bytesRead = ristream.read(bytes, 0, length)) > 0) {
				rostream.write(bytes, 0, bytesRead);
			}

		}
	}
%>