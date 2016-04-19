
package com.rmsi.mast.studio.web.mvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.dao.AttachmentDAO;
import com.rmsi.mast.studio.dao.BookmarkDAO;
import com.rmsi.mast.studio.dao.LayerDAO;
import com.rmsi.mast.studio.domain.Attachment;
import com.rmsi.mast.studio.domain.AttachmentPK;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Layer;
import com.rmsi.mast.studio.service.AttachmentService;
import com.rmsi.mast.studio.service.BookmarkService;
import com.rmsi.mast.studio.service.LayerService;


/**
 * @author Aparesh.Chakraborty
 *
 */


@Controller
public class AttachmentController {
	
	private static final Logger logger = Logger.getLogger(AttachmentController.class);
	
	@Autowired
	private AttachmentDAO attachmentDAO;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	private LayerService layerService;
	
	@RequestMapping(value = "/studio/attachment/create", method = RequestMethod.POST)
	@ResponseBody
    public String createAttachment(HttpServletRequest request, HttpServletResponse response){
		System.out.println("********************");
		System.out.println("INSIDE : attachment/create");
		System.out.println("********************");
		Attachment attachment=new Attachment();
		AttachmentPK attachmentPK=new AttachmentPK();
		Layer layer;
		int gid=0;
		String layername="";
		String keyfield="";
		String filename="";
		String filepath="";
		String extension="";
		String description="";
		String url = "resources/temp/uploads/";		
		//String user=verifyUserToken(request,response);
		String user="user";
		String attachmentUrl = url + user;
		
		try {
			gid=Integer.parseInt(ServletRequestUtils.getRequiredStringParameter(request, "associationid"));
			
			layername = ServletRequestUtils.getRequiredStringParameter(request, "layername");
			keyfield=ServletRequestUtils.getRequiredStringParameter(request, "keyfield");		
			filename=ServletRequestUtils.getRequiredStringParameter(request, "filename");
			filepath=ServletRequestUtils.getRequiredStringParameter(request, "filepath");
			extension=ServletRequestUtils.getRequiredStringParameter(request, "extension");			
			description=ServletRequestUtils.getRequiredStringParameter(request, "fileDesc");	
											
			layer=layerService.findLayerByName(layername);
			
			/*attachmentPK.setAssociationid(associationid);
			attachmentPK.setLayername(layername);			
			attachment.setAttachmentId(attachmentPK);*/
			
			//attachment.setAssociationid(associationid);
			attachment.setLayer(layer);
			attachment.setGid(gid);
			attachment.setKeyfield(keyfield);
			attachment.setFilename(filename);
			attachment.setFilepath(filepath);
			attachment.setExtension(extension);					
			attachment.setDescription(description);
			System.out.println("********************");
			System.out.println("***Gid "+attachment.getGid());
			System.out.println("***keyfield "+attachment.getKeyfield());
			System.out.println("***filename "+attachment.getFilename());
			System.out.println("*** filepath "+attachment.getFilepath());
			System.out.println("*** extension "+attachment.getExtension());
			System.out.println("*** description "+attachment.getDescription());
			System.out.println("********************");
			
			attachmentService.addAttachment(attachment);
			
			System.out.println("11>>>>>>>");
			return attachmentUrl+"/"+ attachment.getFilename();
			
			
		} catch (ServletRequestBindingException e) {
			logger.error(e);
			return null;
		}
			
	}
	
	/*@RequestMapping(value = "/studio/attachment/delete", method = RequestMethod.POST)
	@ResponseBody
    public void deleteAttachment(HttpServletRequest request, HttpServletResponse response){		
		String associationIds = request.getParameter("associationId");
		String layername = request.getParameter("layer");
		
		System.out.println("--------------------------ATTch CTRL--------------------------------------");		
		System.out.println(associationIds);	
		System.out.println(layername);
		System.out.println("--------------------------ATTch CTRL--------------------------------------");
		
		attachmentService.deleteAttachment(layername,associationIds);		
	}*/
	
	
	@RequestMapping(value = "/studio/attachment/layer/{id}", method = RequestMethod.POST)
	@ResponseBody
	public List<Attachment> findAttachmentByLayer(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
		
		String associationIds = request.getParameter("ids");
		
		List<Attachment> attachments = 	attachmentService.findAttachmentByLayer(id,associationIds)	;
		String url = "resources/temp/uploads/";
		
		//String user=verifyUserToken(request,response);
		String user="user";
		
		String attachmentUrl = url + user;
		System.out.println("---------AccURL: attachmentUrl-----------------------------------");
		
		for(int x=0 ; x< attachments.size() ; x++){
			
			Attachment attachment = attachments.get(x);
			
			attachment.setFilepath(attachmentUrl +"/"+ attachment.getFilename());
			
		}
		
		return attachments;
		
		
		
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
			  logger.error(e);
			  try{
				  response.sendError(403);
			  }catch(Exception ex){
				  logger.error(ex);
			  }
		  }
		  
		  return principal;
	}
	
	@RequestMapping(value = "/studio/attachment/rowid/{rowid}", method = RequestMethod.GET)
	@ResponseBody
	public List<Attachment> findAttachmentByRowId(@PathVariable String rowid,HttpServletRequest request, HttpServletResponse response){
		
		
		
		List<Attachment> attachments = 	attachmentService.findAttachmentByRowId(rowid)	;
		String url = "resources/temp/uploads/";
		
		//String user=verifyUserToken(request,response);
		String user="user";
		
		String attachmentUrl = url + user;
		System.out.println("---------AccURL: attachmentUrl-----------------------------------");
		
		for(int x=0 ; x< attachments.size() ; x++){
			
			Attachment attachment = attachments.get(x);
			
			attachment.setFilepath(attachmentUrl +"/"+ attachment.getFilename());
			
		}
		
		return attachments;
		
		
		
	}
	
	@RequestMapping(value = "/studio/attachment/delete/{associateId}", method = RequestMethod.POST)
	@ResponseBody
    public String deleteAttachment(@PathVariable String associateId){		
				
		System.out.println("--------------------------ATTch CTRL--------------------------------------");		
		System.out.println(associateId);	
		
		System.out.println("--------------------------ATTch CTRL--------------------------------------");
		
		return attachmentService.deleteAttachmentBYAssociateId(new Integer(associateId));		
	}
	
	
	@RequestMapping(value = "/studio/attachment/{layer}/gid/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public List<Attachment> findAttachmentByGid(@PathVariable String layer,@PathVariable String gid,HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("--------------------------Layer:"+layer+" -gid: "+gid);
		
		List<Attachment> attachments = 	attachmentService.findAttachmentByGid(layer,new Integer(gid));
		System.out.println("---LEN: "+ attachments.get(0).getFilename());
		
		/* Move to AttachmentHibernateDAO BY Aparesh*/
		 
		 
		/*String url = "resources/temp/uploads/";
		
		//String user=verifyUserToken(request,response);
		String user="user";
		
		String attachmentUrl = url + user;
		System.out.println("---------AccURL: attachmentUrl-----------------------------------");
		
		for(int x=0 ; x< attachments.size() ; x++){
			
			Attachment attachment = attachments.get(x);
			
			attachment.setFilepath(attachmentUrl +"/"+ attachment.getFilename());
			
		}
		*/
		
		return attachments;
		
		
		
	}
	
}


