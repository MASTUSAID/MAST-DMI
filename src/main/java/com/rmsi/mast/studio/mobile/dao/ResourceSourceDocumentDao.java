package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ResourceSourceDocument;
import com.rmsi.mast.studio.domain.SourceDocument;

public interface ResourceSourceDocumentDao {

	/**
	 * This will add source document to the database
	 * 
	 * @param sourceDocument
	 */
	public ResourceSourceDocument addResourceDocument(ResourceSourceDocument resourceDocument);

}
