package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ResourceSourceDocument;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.mobile.dao.ResourceSourceDocumentDao;
import com.rmsi.mast.studio.mobile.dao.SourceDocumentDao;

@Repository
public class ResourceSourceDocumentHibernateDao extends GenericHibernateDAO<ResourceSourceDocument, Integer> implements
ResourceSourceDocumentDao {

	@Override
	public ResourceSourceDocument addResourceDocument(
			ResourceSourceDocument resourceDocument) {

		try {

			return makePersistent(resourceDocument);

		} catch (Exception ex) {

			throw ex;

		}
	}
	
}
