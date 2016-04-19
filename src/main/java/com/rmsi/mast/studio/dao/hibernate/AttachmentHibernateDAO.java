

package com.rmsi.mast.studio.dao.hibernate;

import java.io.File;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.AttachmentDAO;
import com.rmsi.mast.studio.dao.BookmarkDAO;
import com.rmsi.mast.studio.domain.Attachment;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Maptip;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.User;

/**
 * @author Aparesh.Chakraborty
 * 
 */
@Repository
public class AttachmentHibernateDAO extends
		GenericHibernateDAO<Attachment, Long> implements AttachmentDAO {

	@SuppressWarnings("unchecked")
	public void deleteAttachment(String name) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachmentByLayer(String layer,
			String associationIds) {
		System.out
				.println("----------------------AttchHDAO------------------------------------------");
		System.out.println(associationIds);
		System.out
				.println("---------------------------------------------------------------");
		// List<Attachment> attachment =
		// getEntityManager().createQuery("Select a from Attachment a where a.layer.alias = :name and a.associationid in (:ids)").setParameter("name",
		// layer).setParameter("ids",associationIds ).getResultList();

		String queryString = "Select a from Attachment a where a.layer.alias = :name and a.attachmentId.associationid in (";
		queryString = queryString + associationIds + ")";

		System.out
				.println("----------------------QUR------------------------------------------");
		System.out.println(queryString);
		System.out
				.println("---------------------------------------------------------------");

		// for(int i=0;i<aid.length;i++){
		// queryString=queryString+

		// }

		List<Attachment> attachment = getEntityManager()
				.createQuery(queryString).setParameter("name", layer)
				.getResultList();

		return attachment;
	}

	/*@SuppressWarnings("unchecked")
	public String deleteAttachment(String layername, String associationid) {

		List<Attachment> attachment = findAttachmentByLayer(layername,
				associationid);
		Attachment att = attachment.get(0);
		String msg = "";

		String filename = att.getFilepath() + "/" + att.getFilename();

		System.out
				.println("----------------------DELETING FILE------------------------------------------");
		System.out.println(filename);
		System.out
				.println("---------------------------------------------------------------");

		// DELETE FILE FROM DISK

		if (deleteFileFromDisk(filename)) {

			System.out
					.println("----------------------FILE DELETIED FROM DISK------------------------------------------");

			// DELETE FROM DB
			if (deleteFileFromDB(layername, associationid)) {

				System.out
						.println("----------------------FILE DELETIED FROM DB------------------------------------------");
				msg = "File Deleted successfully";

			} else {

				msg = "ERROR";
			}

		} else {
			msg = "ERROR";

		}

		return msg;

		// TODO Auto-generated method stub

	}*/

	public boolean deleteFileFromDisk(String filename) {
		System.out.println("deleteFileFromDisk----------------------: "+filename);

		File file = new File(filename);
		return file.delete();
	}

	/*public boolean deleteFileFromDB(String layername, String associationid) {
		System.out.println("deleteFileFromDB----------------------: "+layername+"-"+associationid);
		try {
			Query query = getEntityManager()
					.createQuery(
							"Delete from Attachment a where a.attachmentId.layername =:layername and a.attachmentId.associationid=:associationid")
					.setParameter("layername", layername)
					.setParameter("associationid",
							Integer.parseInt(associationid));

			int count = query.executeUpdate();
			System.out.println("Delete count: " + count);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	public boolean deleteFileFromDB(Integer associationid) {
		System.out.println("deleteFileFromDB----------------------: "+associationid);
		try {
			Query query = getEntityManager()
					.createQuery(
							"Delete from Attachment a where associationid=:associationid")
					
					.setParameter("associationid",associationid);

			int count = query.executeUpdate();
			System.out.println("Delete count: " + count);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean checkIfKeyExists(Integer associationid, String layername) {

		Attachment attachment = getAttachmentByPK(associationid, layername);
		if (attachment == null) {
			return false;
		} else {
			return true;
		}

	}

	private Attachment getAttachmentByPK(Integer associationid, String layername) {

		Query query = getEntityManager()
				.createQuery(
						"Select a from Attachment a where a.attachmentId.associationid =:associationid "
								+ "and a.attachmentId.layername =:layername")
				.setParameter("associationid", associationid)
				.setParameter("layername", layername);

		@SuppressWarnings("unchecked")
		List<Attachment> lstAttachment = query.getResultList();
		if (lstAttachment.size() > 0) {
			return lstAttachment.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachmentByRowId(String rowId) {
		
		String queryString = "Select a from Attachment a where a.keyfield = :rowId";
		

		System.out
				.println("----------------------QUR------------------------------------------");
		System.out.println(queryString);
		System.out
				.println("---------------------------------------------------------------");

		// for(int i=0;i<aid.length;i++){
		// queryString=queryString+

		// }

		List<Attachment> attachment = getEntityManager()
				.createQuery(queryString).setParameter("rowId", rowId)
				.getResultList();

		return attachment;
	}
	
	
	@SuppressWarnings("unchecked")
	public Attachment findAttachmentByAssociateId(Integer associateId) {
		
		String queryString = "Select a from Attachment a where a.associationid = :associateId";		
		List<Attachment> attachment = getEntityManager()
				.createQuery(queryString).setParameter("associateId", associateId)
				.getResultList();
		
		if(attachment.size() > 0)
		{			
			return attachment.get(0);
		}
		else
			return null;
		}

	
	

	@Override
	public String deleteAttachmentBYAssociateId(Integer associateId) {
		Attachment attachment = findAttachmentByAssociateId(associateId);
		//Attachment att = attachment.get(0);
		String msg = "1";

		String filename = attachment.getFilepath() + "/" + attachment.getFilename();

		System.out
				.println("----------------------DELETING FILE------------------------------------------");
		System.out.println(filename);
		System.out
				.println("---------------------------------------------------------------");

		// DELETE FILE FROM DB
		deleteFileFromDB(associateId);
		
		// DELETE FILE FROM DISK
		deleteFileFromDisk(filename);
		

		/*if (deleteFileFromDisk(filename)) {

			System.out
					.println("----------------------FILE DELETIED FROM DISK------------------------------------------");

			
			if (deleteFileFromDB(associateId)) {

				System.out
						.println("----------------------FILE DELETIED FROM DB------------------------------------------");
				msg = "1";

			} else {

				msg = "2";
			}

		} else {
			msg = "3";

		}*/

		return msg;
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> findAttachmentByGid(String layer,Integer gid) {
		System.out.println("#######"+layer+ "---"+gid);
		String queryString = "Select a from Attachment a where a.layer.alias=:layer and a.gid=:gid order by a.associationid desc";		
		

		List<Attachment> attachments = getEntityManager().createQuery(queryString).setParameter("layer", layer).setParameter("gid", gid).getResultList();
		
		String url = "resources/temp/uploads/";
		
		//String user=verifyUserToken(request,response);
		String user="user";
		
		String attachmentUrl = url + user;
		System.out.println("---------AccURL: attachmentUrl-----------------------------------");
		
		for(int x=0 ; x< attachments.size() ; x++){
					
				attachments.get(x).setFilepath(attachmentUrl +"/"+ attachments.get(x).getFilename());			
		}
		
		
		
		return attachments;
	}
	
	
	
	
	
	
}
