package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.SocialTenureRelationshipDAO;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;

@Repository
public class SocialTenureRelationshipHibernateDAO extends GenericHibernateDAO<SocialTenureRelationship, Long>
        implements SocialTenureRelationshipDAO {

    private static final Logger logger = Logger.getLogger(SocialTenureRelationshipHibernateDAO.class);

    @SuppressWarnings("unchecked")
	@Override
    public List<SocialTenureRelationship> findbyUsin(Long id) {

        try {
            Query query = getEntityManager().createQuery("Select st from SocialTenureRelationship st where (st.landid = :usin and st.isactive=true and st.laPartygroupPersontype.persontypeid in (1,2,3))");
            List<SocialTenureRelationship> socialTenure = query.setParameter("usin", id).getResultList();

            if (socialTenure.size() > 0) {
                return socialTenure;
            } else {
                return new ArrayList<SocialTenureRelationship>();
            }
        } catch (Exception e) {

            logger.error(e);
            return new ArrayList<SocialTenureRelationship>();
        }

    }

    @Override
    public List<SocialTenureRelationship> findByGid(Integer id) {

        try {
            Query query = getEntityManager().createQuery("Select st from SocialTenureRelationship st where st.gid = :gid and st.isActive = true");
            @SuppressWarnings("unchecked")
			List<SocialTenureRelationship> socialTenureBygid = query.setParameter("gid", id).getResultList();

            if (socialTenureBygid.size() > 0) {
                return socialTenureBygid;
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }
    
    @Override
    public SocialTenureRelationship getSocialTenure(long id){
        return findById(id, false);
    }

    @Override
    public boolean deleteTenure(Long id) {
        try {

            Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isActive = false  where st.gid = :gid");

            query.setParameter("gid", id.intValue());

            int rows = query.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

    }

    @Override
    public boolean deleteNatural(Long id) {
    	
    	
        try {

            Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isactive = false  where st.partyid = :gid");


            query.setParameter("gid", id);

            int rows = query.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }
        
        
        

    }

    @Override
    public boolean deleteNonNatural(Long id) {
        try {

            Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isactive = false  where st.partyid = :gid");

            query.setParameter("gid", id);

            int rows = query.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SocialTenureRelationship> findDeletedPerson(Long id) {

        ArrayList<SocialTenureRelationship> objTemp = new ArrayList<SocialTenureRelationship>();
        try {
            Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isactive = false  where st.partyid = :gid");

            List<SocialTenureRelationship> personList = query.setParameter("usin", id).getResultList();

            if (personList.size() > 0) {

                return personList;
            }

        } catch (Exception e) {

            logger.error(e);
            return objTemp;
        }
        return objTemp;

    }

    @Override
    public boolean addDeletedPerson(Long gid) {
        try {
            Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isActive = true  where st.person_gid.person_gid = :gid");
            query.setParameter("gid", gid);

            int rows = query.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean updateSharePercentage(String alias, long personGid) {

        try {

            String qry = "UPDATE SocialTenureRelationship st SET st.sharePercentage = :alias where st.person_gid.person_gid =:personGid";
            Query query = getEntityManager().createQuery(qry).setParameter("alias", alias).setParameter("personGid", personGid);
            int count = query.executeUpdate();
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

	@Override
	public SocialTenureRelationship getPersonLandMapDetails(Integer landid) {
		String qry = "Select st from SocialTenureRelationship st";
		Query query = getEntityManager().createQuery(qry);
		query.getResultList();
		return null;
	}

	@Override
	public SocialTenureRelationship getSocialTenureObj(Long partyid,
			Long landId) {

        try {
            Query query = getEntityManager().createQuery("Select st from SocialTenureRelationship st where st.partyid = :partyid and st.landid = :landid");
            @SuppressWarnings("unchecked")
			List<SocialTenureRelationship> socialTenureBygid = query.setParameter("partyid", partyid).setParameter("landid", landId).getResultList();

            if (socialTenureBygid.size() > 0) {
                return socialTenureBygid.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }

}
