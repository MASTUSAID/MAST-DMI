/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.mobile.dao.SourceDocumentDao;

/**
 * @author shruti.thakur
 *
 */
@Repository
public class SourceDocumentHibernateDao extends
		GenericHibernateDAO<SourceDocument, Integer> implements
		SourceDocumentDao {
	private static final Logger logger = Logger
			.getLogger(SourceDocumentHibernateDao.class);

	@Override
	public SourceDocument addSourceDocument(SourceDocument sourceDocument) {

		try {

			return makePersistent(sourceDocument);

		} catch (Exception ex) {

			logger.error(ex);
			throw ex;

		}
	}

	@Override
	public SourceDocument findByUsinandFile(String fileName, Long usin) {

		String query = "select sd from SourceDocument sd where sd.ScanedSourceDoc =:fileName and sd.usin =:usin";

		try {
			@SuppressWarnings("unchecked")
			List<SourceDocument> sourceDocumentList = getEntityManager()
					.createQuery(query).setParameter("fileName", fileName)
					.setParameter("usin", usin).getResultList();

			if (sourceDocumentList.size() > 0) {
				return sourceDocumentList.get(0);
			}
		} catch (Exception ex) {

			logger.error(ex);
			throw ex;
		}
		return null;
	}

	@Override
	public List<SourceDocument> findByUsin(Long usin) {
		String query = "select sd from SourceDocument sd where sd.usin =:usin";

		try {
			@SuppressWarnings("unchecked")
			List<SourceDocument> sourceDocumentList = getEntityManager()
					.createQuery(query).setParameter("usin", usin)
					.getResultList();

			if (sourceDocumentList.size() > 0) {
				return sourceDocumentList;
			}
		} catch (Exception ex) {

			logger.error(ex);
			throw ex;
		}
		return null;
	}

}
