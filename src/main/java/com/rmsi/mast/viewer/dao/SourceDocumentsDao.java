package com.rmsi.mast.viewer.dao;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SourceDocument;

/**
 * 
 * @author Abhay.Pandey
 *
 */
public interface SourceDocumentsDao extends GenericDAO<SourceDocument, Long>{

	@Transactional
	public SourceDocument saveUploadedDocuments(SourceDocument sourceDocument);
	
	public SourceDocument findBypartyandtransid(Long partyid, Long transid);
}
