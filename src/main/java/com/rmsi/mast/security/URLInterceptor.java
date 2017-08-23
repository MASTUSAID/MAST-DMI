package com.rmsi.mast.security;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class URLInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {
		
		StringBuffer bufURL = request.getRequestURL();
		System.out.println("prehandle: " + bufURL);
		if(bufURL.toString().endsWith("/studio") || bufURL.toString().endsWith("/studio/")){
			request.getSession().setAttribute("url", "./studio/");
			//System.out.println("Into Studio ----------");
		}else if(bufURL.toString().endsWith("/viewer") || bufURL.toString().endsWith("/viewer/")){
			request.getSession().setAttribute("url", "./viewer/");
			
			
			String param = getQueryString(request.getQueryString());
			request.getSession().setAttribute("lang", param);
			//System.out.println("Into Viewer ----------");
		}
		
		else if(bufURL.toString().endsWith("/mobileconfig") || bufURL.toString().endsWith("/mobileconfig/")){
			request.getSession().setAttribute("url", "./mobileconfig/");
			//System.out.println("Into Studio ----------");
		}
		else if(bufURL.toString().endsWith("/report") || bufURL.toString().endsWith("/report/")){
			request.getSession().setAttribute("url", "./report/");
			//System.out.println("Into Studio ----------");
		}
		
		
		else{
			String param = getQueryString(request.getQueryString());
			if(param == null){
				param = "en";
			}
			request.getSession().setAttribute("lang", param);
		}
		return true;
	}
	
	private String getQueryString(String qryString){
		String langParam = null;
		if(qryString != null){
			int pos = qryString.indexOf("=");
	    	if(pos > -1){
	    		langParam = qryString.substring(++pos);
			}else{
				langParam = "en";
			}
	    	return langParam;
		}else{
			langParam = "en";
			return langParam;
		}
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
