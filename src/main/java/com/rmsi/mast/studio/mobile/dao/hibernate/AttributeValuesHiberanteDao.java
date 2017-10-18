package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.mobile.dao.AttributeValuesDao;

@Repository
public class AttributeValuesHiberanteDao extends
        GenericHibernateDAO<AttributeValues, Long> implements AttributeValuesDao {

    private static final Logger logger = Logger.getLogger(AttributeValuesHiberanteDao.class);

    @Override
    public void addAttributeValues(List<AttributeValues> attributeValuesList, Long parentuid) {

        Iterator<AttributeValues> attributeValuesIter = attributeValuesList.iterator();
        AttributeValues attributeValues = null;

        while (attributeValuesIter.hasNext()) {
            attributeValues = attributeValuesIter.next();

            // Setting parent id
            attributeValues.setParentuid(parentuid);
            makePersistent(attributeValues);
        }
    }

    @Override
    public void updateAttributeValues(List<AttributeValues> attributeValuesList) {
        try {
            String updateSql = "UPDATE AttributeValues a SET a.value =:value where a.uid = :uid "
                    + "and a.parentuid = :parentuid";
            Query query = getEntityManager().createQuery(updateSql);

            for (Iterator<AttributeValues> iterator = attributeValuesList.iterator(); iterator
                    .hasNext();) {
                AttributeValues attributeValues = (AttributeValues) iterator.next();

                query.setParameter("uid", attributeValues.getUid());
                query.setParameter("parentuid", attributeValues.getParentuid());
                query.setParameter("value", attributeValues.getValue());

                int rows = query.executeUpdate();

                if (rows == 0) {
                    makePersistent(attributeValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> getAttributeValueandId(long parentUid, int attributeCategoryId) {
        try {
            String query = "select spa.id, av.value, am.listing, am.datatype_id from attribute av "
                    + "inner join surveyprojectattributes spa on spa.uid = av.uid "
                    + "inner join attribute_master am on spa.id = am.id where "
                    + "av.parentuid = " + parentUid + " and am."
                    + "attributecategoryid = " + attributeCategoryId;

            @SuppressWarnings("unchecked")
            List<Object> attributes = getEntityManager().createNativeQuery(
                    query).getResultList();

            if (attributes.size() > 0) {
                return attributes;
            }
        } catch (Exception ex) {
            logger.error("Exception while fetching ATTRIBUTE VALUES::: " + ex);
            throw ex;
        }
        return null;
    }

    @Override
    public boolean checkEntieswithUid(List<Long> uids) {
        try {
            String query = "SELECT COUNT(a) from AttributeValues a where a.uid in (:uids)";

            long count = (long) getEntityManager().createQuery(query)
                    .setParameter("uids", uids)
                    .getSingleResult();

            if (count > 0) {
                return true;
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long getAttributeKeyById(long person_gid, long uid) {
        try {
            Query query = getEntityManager().createQuery("Select av.attributevalueid from AttributeValues av where av.parentuid = :person_gid and av.uid =:uid");
            List<Long> keyValue = query.setParameter("person_gid", person_gid).setParameter("uid", uid).getResultList();

            if (keyValue.size() > 0) {
                return keyValue.get(0).longValue();
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }
    }
}
