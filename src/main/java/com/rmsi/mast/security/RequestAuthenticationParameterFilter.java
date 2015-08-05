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
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

public class RequestAuthenticationParameterFilter extends AbstractPreAuthenticatedProcessingFilter {

	 private String PRINCIPAL_PARAM = "_token";
	 private final String ENCRYPT_KEY = "HG58YZ3CR9";
	 
	  @Override
	  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request){
		  String principal = null;
		  String token = request.getParameter(PRINCIPAL_PARAM);
		  try{ 
			  token = URLEncoder.encode(token,  "UTF-8");
			  //System.out.println("----Recieved Token: " + token);
			  token = URLDecoder.decode(token, "UTF-8");
			  //System.out.println("---Decoded token: " + token);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  System.out.println("-------Encrypted Token: " + token);
		  StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		  encryptor.setPassword(ENCRYPT_KEY);
		  encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		  try{
			  token = encryptor.decrypt(token);
			  //System.out.println("--------Decrypted token: " + token);
			  String[] tokens = token.split("\\|");
			  principal = tokens[0];
			  
			  Authentication currentUser = SecurityContextHolder.getContext().getAuthentication(); 
			  if(currentUser != null){
				  System.out.println("---Current User: " + currentUser);
				  if(! currentUser.getPrincipal().equals(principal)){
					  setCheckForPrincipalChanges(true);
				  }
			  }
			  
			  System.out.println("----Principal object: " + principal);
		  }catch(EncryptionOperationNotPossibleException e){
			  e.printStackTrace();
		  }
		  
		  if (principal == null){
			  throw new PreAuthenticatedCredentialsNotFoundException(PRINCIPAL_PARAM
					  + " not found in request parameter.");
		  }
		  return principal;
	  }
	  
	  @Override
	  protected Object getPreAuthenticatedCredentials(HttpServletRequest request){
		  String credential = null;
		  String token = request.getParameter(PRINCIPAL_PARAM);
		  
		  StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		  encryptor.setPassword(ENCRYPT_KEY);
		  encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		  try{
			  token = encryptor.decrypt(token);
		  
			  String[] tokens = token.split("\\|");
			  credential = tokens[1];
			  //System.out.println("----Credential object: " + credential);
		  }catch(EncryptionOperationNotPossibleException e){
			  e.printStackTrace();
		  }
		  if (credential == null){
			  throw new PreAuthenticatedCredentialsNotFoundException(PRINCIPAL_PARAM
					  + " not found in request parameter.");
		  }
		  return credential;
	  }
}
