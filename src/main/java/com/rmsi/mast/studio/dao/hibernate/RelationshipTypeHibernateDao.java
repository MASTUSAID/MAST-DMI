package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.RelationshipTypeDao;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.RelationshipType;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class RelationshipTypeHibernateDao extends GenericHibernateDAO<RelationshipType, Long> implements RelationshipTypeDao {
    private static final Logger logger = Logger.getLogger(RelationshipTypeHibernateDao.class);

    @Override
    public RelationshipType getTypeByAttributeOptionId(int optId) {
        try {
            String query = "select t.* from relationship_type t inner join attribute_options ao on ao.parent_id = t.code where ao.id =" + optId;
            List<RelationshipType> result = getEntityManager()
                    .createNativeQuery(query, IdType.class)
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
