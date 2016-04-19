

package com.rmsi.mast.studio.dao;

import java.util.List;






import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Attachment;
import com.rmsi.mast.studio.domain.Baselayer;
import com.rmsi.mast.studio.domain.Bookmark;

public interface AttachmentDAO extends GenericDAO<Attachment, Long> {
	
	//String deleteAttachment(String layername,String associationIds);
	List<Attachment> findAttachmentByLayer(String layer,String associationIds);
	boolean checkIfKeyExists(Integer associationid, String layername);
	List<Attachment> findAttachmentByRowId(String rowId);
	String deleteAttachmentBYAssociateId(Integer associateId);
	List<Attachment> findAttachmentByGid(String layer,Integer gid);
	
	
}
