

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttachmentDAO;
import com.rmsi.mast.studio.domain.Attachment;
import com.rmsi.mast.studio.service.AttachmentService;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class AttachmentServiceImpl  implements AttachmentService {

	private static final Logger logger = Logger.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private AttachmentDAO attachmentDAO;

	@Override
	public void addAttachment(Attachment attachment) {
				
		
		try{
		attachment.setTenantid("5");
		
		attachmentDAO.makePersistent(attachment);
		
		
		}
		catch(Exception e){
			
			logger.error(e);
		}
	}

	/*@Override
	public String deleteAttachment(String  layername,String associationIds) {
		
		
		return attachmentDAO.deleteAttachment(layername,associationIds);
		
	}*/

	@Override
	public List<Attachment> findAttachmentByLayer(String layer,String associationIds) {
		
		return attachmentDAO.findAttachmentByLayer(layer,associationIds);
		
	}

	@Override
	public List<Attachment> findAttachmentByRowId(String rowId) {
		return attachmentDAO.findAttachmentByRowId(rowId);
	}

	@Override
	public String deleteAttachmentBYAssociateId(Integer associateId) {
		return attachmentDAO.deleteAttachmentBYAssociateId(associateId);
	}

	@Override
	public List<Attachment> findAttachmentByGid(String layer,Integer gid) {
		// TODO Auto-generated method stub
		return attachmentDAO.findAttachmentByGid(layer,gid);
	}

	
	
}
