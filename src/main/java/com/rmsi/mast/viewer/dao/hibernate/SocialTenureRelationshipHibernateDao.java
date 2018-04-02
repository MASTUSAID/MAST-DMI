package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.viewer.dao.SocialTenureRelationshipDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */
@Repository

public class SocialTenureRelationshipHibernateDao extends GenericHibernateDAO<SocialTenureRelationship, Long>
implements SocialTenureRelationshipDao{

	Logger logger = Logger.getLogger(SocialTenureRelationship.class);
	
	@Override
	public SocialTenureRelationship saveSocialTenureRelationship(SocialTenureRelationship socialTenureRelationship) {
		 try {
	            return makePersistent(socialTenureRelationship);

	        } catch (Exception ex) {
	            logger.error(ex);
	            throw ex;
	        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public SocialTenureRelationship getSocialTenureRelationshipByLandId(Long landId) {

		try {
            Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su where (su.landid = :landid and su.isactive=true)");
            List<SocialTenureRelationship> lstSocialTenureRelationship = query.setParameter("landid", landId).getResultList();

            if (lstSocialTenureRelationship.size() > 0) {

                return lstSocialTenureRelationship.get(0);
            }

        } catch (Exception e) {

            logger.error(e);
            return null;
        }
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SocialTenureRelationship getSocialTenureRelationshipByLandIdForBuyer(Long landId,Long processid) {

		try {
           // Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su where (su.landid = :landid and su.isactive=true and su.laPartygroupPersontype.persontypeid=11)");
			/* Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su "
			 		+ " left join la_ext_transactiondetails td on td.processid = " + processid
			 		+ " where (su.landid = :landid and su.isactive=true and su.laPartygroupPersontype.persontypeid=11)");
            List<SocialTenureRelationship> lstSocialTenureRelationship = query.setParameter("landid", landId).setParameter("processid", processid).getResultList();*/
            
            String strQuery = " select * from la_ext_personlandmapping plm left join la_ext_transactiondetails td on td.transactionid=plm.transactionid"
            		+ "  where plm.landid= "+landId +" and plm.isactive= true and plm.persontypeid=11 and td.processid= " + processid;
			
			List<SocialTenureRelationship> lstSocialTenureRelationship = getEntityManager().createNativeQuery(strQuery,SocialTenureRelationship.class).getResultList();
            
            

            if (lstSocialTenureRelationship.size() > 0) {

                return lstSocialTenureRelationship.get(0);
            }

        } catch (Exception e) {

            logger.error(e);
            return null;
        }
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SocialTenureRelationship getSocialTenureRelationshipForSellerByLandId(Long landId) {

		try {
            Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su where (su.landid = :landid and su.isactive=true and su.laPartygroupPersontype.persontypeid=1)");
            List<SocialTenureRelationship> lstSocialTenureRelationship = query.setParameter("landid", landId).getResultList();

            if (lstSocialTenureRelationship.size() > 0) {

                return lstSocialTenureRelationship.get(0);
            }

        } catch (Exception e) {

            logger.error(e);
            return null;
        }
		return null;
	}

	@Override
	public boolean updateSocialTenureRelationshipByPartyId(Long partyId,Long landid) {
		try {

            Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isactive = false where st.partyid = :partyid and st.landid = :landid");

            query.setParameter("partyid", partyId).setParameter("landid", landid);

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
	public boolean updateSocialTenureRelationshipByPartytypeId(Long partyId,Long landid) {
		try {

            Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.laPartygroupPersontype.persontypeid=1 where st.partyid = :partyid and st.landid = :landid");

            query.setParameter("partyid", partyId).setParameter("landid", landid);

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
	public SocialTenureRelationship getSocialTenureRelationshipByLandIdPartyIdandPersonTypeID(Long landId, Long partyid, Integer persontypeid) {
		

		try {
            Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su where su.landid = :landid and su.partyid= :partyid and su.laPartygroupPersontype.persontypeid= :persontypeid and   su.isactive=true");
            @SuppressWarnings("unchecked")
			List<SocialTenureRelationship> lstSocialTenureRelationship = query.setParameter("landid", landId).setParameter("partyid", partyid).setParameter("persontypeid", persontypeid).getResultList();

            if (lstSocialTenureRelationship.size() > 0) {

                return lstSocialTenureRelationship.get(0);
            }

        } catch (Exception e) {

            logger.error(e);
            return null;
        }
		return null;
		
		
		
	}

	@Override
	public List<SocialTenureRelationship> getSocialTenureRelationshipBylandID(Long landId) {
	
		try {
            Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su where (su.landid = :landid  and su.laPartygroupPersontype.persontypeid=1 and su.isactive=true)");
            List<SocialTenureRelationship> lstSocialTenureRelationship = query.setParameter("landid", landId).getResultList();

            return lstSocialTenureRelationship;

        } catch (Exception e) {

            logger.error(e);
            return null;
        }
		
	}

	@Override
	public List<SocialTenureRelationship> getSocialTenureRelationshipBylandIDAndPartyID(Long landId) {
		
		try {
            Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su where (su.landid = :landid and su.laPartygroupPersontype.persontypeid=2 and  su.isactive=true)");
            List<SocialTenureRelationship> lstSocialTenureRelationship = query.setParameter("landid", landId).getResultList();

            return lstSocialTenureRelationship;

        } catch (Exception e) {

            logger.error(e);
            return null;
        }
		
		
	}

	@Override
	public SocialTenureRelationship getSocialTenureRelationshipByLandIdandTypeId(Long landId, Long processid, Integer persontype) {
		
		try {
	            
	            String strQuery = " select * from la_ext_personlandmapping plm left join la_ext_transactiondetails td on td.transactionid=plm.transactionid"
	            		+ "  where plm.landid= "+landId +" and plm.isactive= true and plm.persontypeid=" + persontype +" and td.processid= " + processid;
				
				List<SocialTenureRelationship> lstSocialTenureRelationship = getEntityManager().createNativeQuery(strQuery,SocialTenureRelationship.class).getResultList();
	            if (lstSocialTenureRelationship.size() > 0) {

	                return lstSocialTenureRelationship.get(0);
	            }

	        } catch (Exception e) {

	            logger.error(e);
	            return null;
	        }
			return null;
			
	}

}
