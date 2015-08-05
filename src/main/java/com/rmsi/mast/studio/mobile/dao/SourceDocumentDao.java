/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SourceDocument;

/**
 * @author shruti.thakur
 *
 */
public interface SourceDocumentDao extends GenericDAO<SourceDocument, Integer> {

	/**
	 * This will add source document to the database
	 * 
	 * @param sourceDocument
	 */
	public SourceDocument addSourceDocument(SourceDocument sourceDocument);

	/**
	 * It can be used to find record by combination of usin and fileName
	 * 
	 * @param fileName
	 * @param usin
	 * @return
	 */
	public SourceDocument findByUsinandFile(String fileName, Long usin);

	/**
	 * It can be used to find record by combination of usin
	 * 
	 * @param fileName
	 * @param usin
	 * @return
	 */
	public List<SourceDocument> findByUsin(Long usin);
}
