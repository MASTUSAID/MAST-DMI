/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.mobile.dao.SocialTenureDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
@Transactional
public class SocialTenureHibernateDao extends
		GenericHibernateDAO<SocialTenureRelationship, Integer> implements
		SocialTenureDao {
	private static final Logger logger = Logger.getLogger(SocialTenureHibernateDao.class);

	@Override
	public List<SocialTenureRelationship> getSocailTenure() {
		try {
			String query = "select s from SocialTenureRelationship s";

			@SuppressWarnings("unchecked")
			List<SocialTenureRelationship> socailTenureList = getEntityManager()
					.createQuery(query).getResultList();

			if (!socailTenureList.isEmpty()) {
				return socailTenureList;
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

	@Override
	public SocialTenureRelationship addSocialTenure(
			SocialTenureRelationship socialTenureRelationship) {

		try {
			return makePersistent(socialTenureRelationship);
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SocialTenureRelationship> findSocailTenureByUsin(Long usin) {
		String query = "select str.partyid from la_ext_personlandmapping "
				+ "str inner join la_spatialunit_land su on su.landid = str.landid where"
				+ " su.landid =" + usin +"  and str.isactive=true";
		
		try {
		List<SocialTenureRelationship> socialTenureRelationship=new ArrayList<SocialTenureRelationship>();
		
		List<Object[]> arrObject = getEntityManager().createNativeQuery(query).getResultList();
        
        for(Object [] object : arrObject){
        	SocialTenureRelationship personlandmapping = new SocialTenureRelationship();
        	personlandmapping.setPersonlandid(Long.valueOf(object[0].toString()));
        	personlandmapping.setPartyid(Long.valueOf(object[1].toString()));
        	personlandmapping.setLandid(Long.valueOf(object[2].toString()));
//        	personlandmapping.setpersontyprid(object[3].toString());
//        	personlandmapping.setLaExtTransactiondetail(4);
        	personlandmapping.setCertIssueDate(new SimpleDateFormat("dd/MM/yyyy").parse(object[5].toString()));
        	//laSpatialunitLand.setTransactionid(Integer.valueOf(object[6].toString()));
        	personlandmapping.setCertNumber(object[6].toString());
//        	personlandmapping.setsharepercentage(object[7].toString());
        	personlandmapping.setIsactive((boolean) object[8]);
        	personlandmapping.setCreatedby(Integer.valueOf(object[9].toString()));
        	personlandmapping.setCreateddate(new SimpleDateFormat("dd/MM/yyyy").parse(object[10].toString()));
//        	personlandmapping.setWorkflowstatus(object[11].toString());
        	
        	socialTenureRelationship.add(personlandmapping);
        }
        		
		
//
//			@SuppressWarnings("unchecked")
//			List<SocialTenureRelationship> tenureList = getEntityManager()
//					.createNativeQuery(query, SocialTenureRelationship.class).getResultList();

			if (socialTenureRelationship.size() > 0) {

				return socialTenureRelationship;

			}

		} catch (Exception ex) {

			logger.error(ex);
			

		}
		return null;
	}

	@Override
	public Boolean updateSocialTenure(Integer Id, Long landId) {
		String query = "update SocialTenureRelationship s set s.LaExtTransactiondetail.transactionid= "+ Id + "Where s.landid= "+landId+" and s.LaExtTransactiondetail.transactionid="+99999;

		 getEntityManager().createQuery(query).executeUpdate();
		return true;
	}

}
