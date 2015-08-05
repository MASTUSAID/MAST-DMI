/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

package com.rmsi.mast.security;

import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestAuthenticationInterceptor extends HandlerInterceptorAdapter {
	private String PRINCIPAL_PARAM = "_token";
	private final String ENCRYPT_KEY = "HG58YZ3CR9";

    @Autowired
    UserDetailsService userDetailService;
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {

	
	 String principal = null;
	 //String token = request.getParameter(PRINCIPAL_PARAM);
	 
	 /*Code reads from cookies for authentication and is used as alternative for token implementation*/
	 String token = null;
	 String name = null;
	 Cookie[] cookies = request.getCookies();
	 if(cookies != null){
		 for (int i = 0; i < cookies.length; i++) {
		      name = cookies[i].getName().trim();
		      if(name.equals("auth")){
		    	  System.out.println("---decoding ---");
				  token = cookies[i].getValue().trim();
				  token = URLDecoder.decode(token, "UTF-8");
		    	  System.out.println("-------- Cookie Name: " + name);
		    	  System.out.println("-------- Cookie value: " + token);
		    	  cookies[i].setMaxAge(0); 
		       }
		    }
		 if(token == null){
			 System.out.println("----Token not recieved in cookie");
			 return true;
		 }else{
			 request.getSession().setAttribute(name, token);
		 }
	 }else{
		 System.out.println("----Token is Null user not authenticated----");
		 return true;
	 }
	 /*-----------------------------------------------------------------------*/ 
	 
	  /*if(token == null){
			//response.sendError(403);
			//return false;
		  System.out.println("----Token is Null user not authenticated----");
		  	return true;
		}*/
	  
	  //System.out.println("-------Encrypted Token: " + token);
	  StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	  encryptor.setPassword(ENCRYPT_KEY);
	  encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
	  try{
		  //token = "3mP15SaSj1mE0xbThhmRhIPNGHO1lmEULUNp6Z0Li/guEHu/lIZMvChM7uExKjtE1ZIx5jf/qmo=";
		  System.out.println("Decrypting token: " + token);
		  token = encryptor.decrypt(token);
		  System.out.println("--------Decrypted token: " + token);
		  String[] tokens = token.split("\\|");
		  principal = tokens[0];
		  		  
		  //Authenticate the principal object
		  UserDetails usrDetails = userDetailService.loadUserByUsername(principal);
		  
		  if(usrDetails == null){
			  response.sendError(403);
			  return false;
		  }else{
			  Authentication auth = new UsernamePasswordAuthenticationToken(usrDetails.getUsername(), 
					  			usrDetails.getPassword(), usrDetails.getAuthorities());
			  
			  SecurityContextHolder.getContext().setAuthentication(auth);
			 
			  return true;
		  }
	  }catch(UsernameNotFoundException ex){
		  response.sendError(403);
		  return false;
	  }catch(EncryptionOperationNotPossibleException e){
		  e.printStackTrace();
		  response.sendError(403);
		  return false;
	  }
}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

