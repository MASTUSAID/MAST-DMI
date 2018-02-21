package com.rmsi.mast.studio.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ShareTypeDAO;
import com.rmsi.mast.studio.domain.ShareType;

import java.util.List;

import org.apache.log4j.Logger;

@Repository
public class ShareTypeHibernateDAO extends GenericHibernateDAO<ShareType, Integer> implements ShareTypeDAO {

    private static final Logger logger = Logger.getLogger(ShareTypeHibernateDAO.class);

    @Override
    public ShareType getShareTypeByAttributeId(int attrId) {

        try {
            String query = "select st.* from share_type st inner join "
                    + "attribute_options ao on ao.parent_id = st.gid where "
                    + "ao.id = " + attrId;

            @SuppressWarnings("unchecked")
            List<ShareType> list = getEntityManager()
                    .createNativeQuery(query, ShareType.class)
                    .getResultList();

            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
        return null;
    }

	@Override
	public ShareType getShareTypeById(int attrId) {
		
		try{
			
			@SuppressWarnings("unchecked")
			List<ShareType> share =
					getEntityManager().createQuery("Select u from ShareType u where u.landsharetypeid = :Id").setParameter("Id", attrId).getResultList();

			if(share.size() > 0)
				return share.get(0);
			else
				return null;
			
			
		}catch(Exception e){
		 e.printStackTrace();
		 return null;
		}
	}
}
