package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.viewer.dao.LaPartyDao;


@Repository
public class LaPartyHibernateDao extends
GenericHibernateDAO<LaParty, Long> implements LaPartyDao{

	Logger logger = Logger.getLogger(LaPartyHibernateDao.class);
	@Override
	public LaParty saveParty(LaParty laParty) {
		
		 try {
	            return makePersistent(laParty);

	        } catch (Exception ex) {
	            logger.error(ex);
	            throw ex;
	        }
	}
	@SuppressWarnings("unchecked")
	@Override
	public LaParty getPartyIdByID(Long id) {
		 List<LaParty> lstLaParty = new ArrayList<LaParty>();
		 
	        try {
	     

	        	String query = "select s from LaParty  s where s.partyid = :id";
	        	lstLaParty = getEntityManager().createQuery(query).setParameter("id", id).getResultList();

	            if (lstLaParty.size()>0) {
	                return lstLaParty.get(0);
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SocialTenureRelationship> findSocailTenureByUsin(Long usin) {

		String query = "select str.partyid, su.landid, str.transactionid from la_ext_personlandmapping "
				+ "str inner join la_spatialunit_land su on su.landid = str.landid where"
				+ " su.landid =" + usin +"  and str.isactive=true";
		
		try {
			List<Object[]> arrObject = getEntityManager().createNativeQuery(query).getResultList();
		List<SocialTenureRelationship> socialTenureRelationship=new ArrayList<SocialTenureRelationship>();
		
        for(Object [] object : arrObject){
        	SocialTenureRelationship personlandmapping = new SocialTenureRelationship();
//        	personlandmapping.setPersonlandid(Long.valueOf(object[0].toString()));
        	personlandmapping.setPartyid(Long.valueOf(object[0].toString()));
        	personlandmapping.setLandid(Long.valueOf(object[1].toString()));
//        	personlandmapping.setLandid(Long.valueOf(object[2].toString()));
////        	personlandmapping.setpersontyprid(object[3].toString());
////        	personlandmapping.setLaExtTransactiondetail(4);
//        	personlandmapping.setCertIssueDate(new SimpleDateFormat("dd/MM/yyyy").parse(object[5].toString()));
//        	//laSpatialunitLand.setTransactionid(Integer.valueOf(object[6].toString()));
//        	personlandmapping.setCertNumber(object[6].toString());
////        	personlandmapping.setsharepercentage(object[7].toString());
//        	personlandmapping.setIsactive((boolean) object[8]);
//        	personlandmapping.setCreatedby(Integer.valueOf(object[9].toString()));
//        	personlandmapping.setCreateddate(new SimpleDateFormat("dd/MM/yyyy").parse(object[10].toString()));
////        	personlandmapping.setWorkflowstatus(object[11].toString());
        	
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
	@SuppressWarnings("unchecked")
	@Override
	public List<LaParty> getPartyListIdByID(Long id) {
		 List<LaParty> lstLaParty = new ArrayList<LaParty>();
		 
	        try {
	     	        	String query = "select s from LaParty  s where s.partyid = :id";
	        	lstLaParty = getEntityManager().createQuery(query).setParameter("id", id).getResultList();
	            return lstLaParty;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
	}

}
