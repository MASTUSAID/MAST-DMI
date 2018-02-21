/**
 *
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class AttributeOptionsHibernateDao extends
        GenericHibernateDAO<AttributeOptions, Integer> implements
        AttributeOptionsDao {

    private static final Logger logger = Logger.getLogger(AttributeOptionsHibernateDao.class);

    @Override
    public List<AttributeOptions> getAttributeOptions(Long attributeId) {

        String query = "select a from AttributeOptions a where a.attributeMaster.attributemasterid = :attributeId";

        try {
            @SuppressWarnings("unchecked")
            List<AttributeOptions> attributeOptions = getEntityManager()
                    .createQuery(query)
                    .setParameter("attributeId",attributeId)
                    .getResultList();

            if (!attributeOptions.isEmpty()) {
                return attributeOptions;
            }
        } catch (Exception ex) {
            logger.error(ex);

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AttributeOptions getAttributeOptionsId(Integer attributeId) {
        String query = "select a from AttributeOptions a where a.attributeoptionsid = :attributeId ";

        try {
            List<AttributeOptions> attributeids = getEntityManager()
                    .createQuery(query)
                    .setParameter("attributeId", attributeId).getResultList();

            if (attributeids != null && attributeids.size() > 0) {
                return attributeids.get(0);
            }
        } catch (Exception ex) {
            logger.error(ex);

        }
        return null;
    }

	@Override
	public boolean deleteAttributeOptionsbyId(Long id) {
		try{
			Query query = getEntityManager().createQuery(
					"Delete from AttributeOptions pbl where pbl.attributeMaster.attributemasterid =:id")
					.setParameter("id", id);
			
			int count = query.executeUpdate();
			if(count>0){
				return true;
			}else
			{
				return false;
			}
			
		}catch(Exception e){
		logger.error(e);
		return false;
			
		}
		
	}

}
