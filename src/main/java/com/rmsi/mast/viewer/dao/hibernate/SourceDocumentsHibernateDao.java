package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.viewer.dao.SourceDocumentsDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */
@Repository
public class SourceDocumentsHibernateDao extends GenericHibernateDAO<SourceDocument, Long> implements SourceDocumentsDao{

	Logger logger = Logger.getLogger(SourceDocument.class);
	@Override
	public SourceDocument saveUploadedDocuments(SourceDocument sourceDocument) {
		
		try {
            return makePersistent(sourceDocument);

        } catch (Exception ex) {
            logger.error(ex);
        }
		return null;
	}
	@Override
	public SourceDocument findBypartyandtransid(Long partyid, Long transid) {

		String query = "select sd from SourceDocument sd where sd.laParty.partyid =:partyid and sd.laExtTransactiondetail.transactionid =:transid and sd.isactive= true";

		try {
			@SuppressWarnings("unchecked")
			List<SourceDocument> sourceDocumentList = getEntityManager()
					.createQuery(query).setParameter("partyid", partyid)
					.setParameter("transid", transid.intValue()).getResultList();

			if (sourceDocumentList.size() > 0) {
				return sourceDocumentList.get(0);
			}
		} catch (Exception ex) {

			logger.error(ex);
			throw ex;
		}
		return null;
	}
}
