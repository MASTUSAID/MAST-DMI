package com.rmsi.mast.studio.dao;

import java.util.List;
import com.rmsi.mast.studio.domain.SourceDocument;

public interface SourceDocumentDAO extends GenericDAO<SourceDocument, Integer> {

    List<SourceDocument> findSourceDocumentById(Long id);

    List<SourceDocument> findByGId(Long id);

    boolean deleteMultimedia(Long id);

    SourceDocument findDocumentByAdminId(Long adminID);

    SourceDocument getDocumentByPerson(Long person_gid);
    SourceDocument getdocumentByPersonfortransaction(Long transactionid, Long personid);
    
    List<SourceDocument> getDocumentsByDispute(Long disputeId);

    boolean deleteNaturalPersonImage(Long id);

    boolean checkPersonImage(Long id);
    SourceDocument findDocumentByDocumentId(Long documentId);
    
    List<SourceDocument> findSourceDocumentByLandIdandTransactionid(Long id,Integer transactionid);
    
    List<SourceDocument> findBatchSourceDocumentByLandIdandTransactionid(Long transactionid);

    List<SourceDocument> findSourceDocumentByLandIdAndProessId(Long id,Long processId);
    
}
