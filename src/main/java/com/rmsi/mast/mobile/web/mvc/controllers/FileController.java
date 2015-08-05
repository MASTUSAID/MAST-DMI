package com.rmsi.mast.mobile.web.mvc.controllers;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.mobile.web.mvc.model.*;

@Controller
@RequestMapping("/mobile/projectdata")
public class FileController {

	String SERVERURL="/upload_files/";
	String  OUTPUTDIR="D:\\apache-tomcat-7.0.25-CB\\webapps\\upload_files\\";
  FileMeta fileMeta;
  LinkedList<FileMeta> files = new LinkedList<FileMeta>();
  public FileController(){
  	System.out.println("init RestController");
  	fileMeta = new FileMeta();
  	
  	
  }
  
  
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public String upload(MultipartHttpServletRequest request, HttpServletResponse response) {                 
 	 String msg="ERROR";
 	String outputUrl="";
   
 	 //0. notice, we have used MultipartHttpServletRequest
 	 System.out.println("Real path>>>"+ request.getRealPath("\\"));
 	
 	 //1. get the file from the request object
	 Iterator<String> itr =  request.getFileNames();

	 MultipartFile mpf = request.getFile(itr.next());
 	 System.out.println(mpf.getOriginalFilename() +" uploaded!");

 	 try {
		//fileMeta.setFileSize(mpf.getBytes().length+"");
		fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
		fileMeta.setBytes(mpf.getBytes());
	  	fileMeta.setFileType(mpf.getContentType());
	  	fileMeta.setFileName(mpf.getOriginalFilename());
	  	
	  	FileOutputStream outputStream = new FileOutputStream(OUTPUTDIR+ fileMeta.getFileName());
	  	FileCopyUtils.copy(fileMeta.getBytes(), outputStream);
	  	outputUrl=SERVERURL+fileMeta.getFileName();
	  	fileMeta.setFilePath(outputUrl);
	  	
	  	msg=outputUrl+"|"+fileMeta.getFileName();
	  	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		msg="<font color='green'>There is some error to upload file :"+fileMeta.getFileName()+ "</Font>";
	}
 	 
 	 //2. send it back to the client as <img> that calls get method
 	 //we are using getTimeInMillis to avoid server cached image 
 	 
 	 //return "<img src='/fileupload/rest/cont/get/"+Calendar.getInstance().getTimeInMillis()+"' />";
 	 return msg;
 	
 }
  
  
  @RequestMapping(value="/upload1", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload1(MultipartHttpServletRequest request, HttpServletResponse response) {

		//1. build an iterator
		 Iterator<String> itr =  request.getFileNames();
		 MultipartFile mpf = null;

		 //2. get each file
		 while(itr.hasNext()){
			 
			 //2.1 get next MultipartFile
			 mpf = request.getFile(itr.next()); 
			 System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());

			 //2.2 if files > 10 remove the first from the list
			 if(files.size() >= 10)
				 files.pop();
			 
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta();
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 
			 try {
				fileMeta.setBytes(mpf.getBytes());
				
				// copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/files/"+mpf.getOriginalFilename()));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 //2.4 add to files
			 files.add(fileMeta);
			 
		 }
		 
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		return files;

	}
  
  
 
}
