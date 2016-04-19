

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
