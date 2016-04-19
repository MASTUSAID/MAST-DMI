

package com.rmsi.mast.viewer.web.mvc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.jws.WebResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.mvc.extensions.ajax.AjaxUtils;
import org.springframework.mvc.extensions.flash.FlashMap.Message;
import org.springframework.mvc.extensions.flash.FlashMap.MessageType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.rmsi.mast.studio.dao.hibernate.ProjectDataHibernateDAO;

@Controller
@RequestMapping("/viewer/fileupload")
public class FileUploadController {
	private static final Logger logger = Logger.getLogger(FileUploadController.class);

	@RequestMapping(method=RequestMethod.GET)
	public void fileUploadForm(WebRequest webRequest, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(webRequest));	
	}

	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public String processUpload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response, Model model){
		try{
		String message = "File '" + file.getOriginalFilename() + "' uploaded successfully";
		
		//String user=verifyUserToken(request, response);
		
		String user="user";
		
		System.out.println("--------------------------------------------");
		System.out.println("USER: :"+user + ">>>" + request.getRealPath("\\"));
		System.out.println("--------------------------------------------");
		
		InputStream inputStream = file.getInputStream();
		
		String outDirPath=request.getRealPath("\\")+"resources/temp/uploads/"+user;
		
		File outDir=new File(outDirPath);
		boolean exists = outDir.exists();
		if (!exists) {
			
			boolean success = (new File(outDirPath)).mkdir();
			
		}
		
				
		FileOutputStream outputStream = new FileOutputStream(outDirPath+"/"+ file.getOriginalFilename());
        int readBytes = 0;
        byte[] buffer = new byte[10000];
        while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
                outputStream.write(buffer, 0, readBytes);
        }
        outputStream.close();
        inputStream.close();

		//model.addAttribute("message", new Message(MessageType.success, message));
		//model.addAttribute("ajaxRequest", AjaxUtils.isAjaxUploadRequest(webRequest));
        String filepath=outDirPath;     
        return filepath+"|"+file.getOriginalFilename();
		}
		catch(Exception e){
			logger.error(e);
			return "error";
			
		}
	}
	
	
	private String verifyUserToken(HttpServletRequest request, HttpServletResponse response){
		 String token = request.getParameter("_token");
		 final String ENCRYPT_KEY = "HG58YZ3CR9";
		 String principal = "";
		  //System.out.println("-------Encrypted Token: " + token);
		  StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		  encryptor.setPassword(ENCRYPT_KEY);
		  encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		  try{
			  token = encryptor.decrypt(token);
			  //System.out.println("--------Decrypted token: " + token);
			  String[] tokens = token.split("\\|");
			  principal = tokens[0];
		  }catch(EncryptionOperationNotPossibleException e){
			  e.printStackTrace();
			  try{
				  response.sendError(403);
			  }catch(Exception ex){
				  logger.error(e);
			  }
		  }
		  
		  return principal;
	}
	
	
	
}