package com.rmsi.mast.studio.web.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mvc.extensions.ajax.AjaxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImportDataController {
	
/*	@RequestMapping(method=RequestMethod.GET)
	public void fileUploadForm(WebRequest webRequest, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(webRequest));	
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public String processUpload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response, Model model){
		try{
			System.out.println("----------------ImportDataController----------------------------");
		String message = "File '" + file.getOriginalFilename() + "' uploaded successfully";
		//String test=ServletRequestUtils.getRequiredStringParameter(request, "row_id");
		//String user=verifyUserToken(request, response);
		
		String user="user";
		
		System.out.println("--------------------------------------------");
		System.out.println("USER: :"+user + ">>>" + request.getRealPath("\\"));
		System.out.println("--------------------------------------------");
		
		InputStream inputStream = file.getInputStream();
		
		String outDirPath=request.getRealPath("\\")+"resources/temp/uploads/data/tabfile/"+user;
		
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
        //call service
        return filepath+"|"+file.getOriginalFilename();
		}
		catch(Exception e){
			System.out.println("error");
			return "error";
			
		}
	}
*/
}
