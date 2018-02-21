package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.DocumentType;

public interface DocumentTypeDao extends GenericDAO<DocumentType, Long> {
    DocumentType getTypeByAttributeOptionId(int optId);
    
    List<DocumentType> getAllDocumentTypes();
}
