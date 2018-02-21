package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.IdTypeDao;
import com.rmsi.mast.studio.domain.IdType;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class IdTypeHibernateDao extends GenericHibernateDAO<IdType, Integer> implements IdTypeDao {
    private static final Logger logger = Logger.getLogger(IdTypeHibernateDao.class);

    @Override
    public IdType getTypeByAttributeOptionId(int optId) {
        try {
            String query = "select t from IdType t where  t.identitytypeid =" + optId;
            List<IdType> result = getEntityManager().createQuery(query).getResultList();

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
