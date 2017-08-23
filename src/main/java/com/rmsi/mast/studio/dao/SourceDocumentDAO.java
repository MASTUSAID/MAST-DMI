

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.SourceDocument;

public interface SourceDocumentDAO extends GenericDAO<SourceDocument, Integer> {

	List<SourceDocument> findSourceDocumentById(Long id);

	List<SourceDocument> findByGId(Long id);

	boolean deleteMultimedia(Long id);

	SourceDocument findDocumentByAdminId(Long adminID);

	SourceDocument getDocumentByPerson(Long person_gid);

	boolean deleteNaturalPersonImage(Long id);

	boolean checkPersonImage(Long id);

	
	
}
