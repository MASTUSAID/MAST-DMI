package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.AcquisitionTypeDao;
import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.IdType;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class AcquisitionTypeHibernateDao extends GenericHibernateDAO<AcquisitionType, Integer> implements AcquisitionTypeDao {
    private static final Logger logger = Logger.getLogger(AcquisitionTypeHibernateDao.class);

    @Override
    public AcquisitionType getTypeByAttributeOptionId(int optId) {
        try {
            String query = "select t.* from acquisition_type t inner join attribute_options ao on ao.parent_id = t.code where ao.id =" + optId;
            List<AcquisitionType> result = getEntityManager()
                    .createNativeQuery(query, AcquisitionType.class)
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
