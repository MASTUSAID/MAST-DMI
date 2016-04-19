

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Attachment;
import com.rmsi.mast.studio.domain.Bookmark;


public interface AttachmentService {

	@Transactional
	void addAttachment(Attachment attachment);
	
	//@Transactional
	//String deleteAttachment(String layer,String associationIds);
	
	
	List<Attachment> findAttachmentByLayer(String layer,String associationIds);
	
	
	List<Attachment> findAttachmentByRowId(String rowId);
	
	@Transactional
	String deleteAttachmentBYAssociateId(Integer associateId);
	
	
	List<Attachment> findAttachmentByGid(String layer,Integer gid);
}
