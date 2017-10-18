package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.DocumentTypeDao;
import com.rmsi.mast.studio.domain.DocumentType;
import com.rmsi.mast.studio.domain.IdType;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentTypeHibernateDao extends GenericHibernateDAO<DocumentType, Long> implements DocumentTypeDao {
    private static final Logger logger = Logger.getLogger(DocumentTypeHibernateDao.class);

    @Override
    public DocumentType getTypeByAttributeOptionId(int optId) {
        try {
            String query = "select t.* from document_type t inner join attribute_options ao on ao.parent_id = t.code where ao.id =" + optId;
            List<DocumentType> result = getEntityManager()
                    .createNativeQuery(query, DocumentType.class)
                    .getResultList();

            if (result != null && result.size() > 0) {
                return result.get(0);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
        return null;
    }
}
