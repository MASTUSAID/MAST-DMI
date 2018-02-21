package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaExtPersonLandMapping;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.viewer.dao.LaExtPersonLandMappingsDao;

@Repository
public class LaExtPersonLandMappingsHibernateDao extends GenericHibernateDAO<LaExtPersonLandMapping, Integer>
implements LaExtPersonLandMappingsDao{

	private static final Logger logger = Logger.getLogger(LaExtPersonLandMappingsHibernateDao.class);

	@SuppressWarnings("unchecked")
	@Override
	public LaExtPersonLandMapping getPersonLandMapDetails(Integer landid) {
		
		String strQuery= "select LP.partyid,LP.persontypeid from la_ext_personlandmapping LP where landid = "+ landid +" and isactive = true";
		List<Object[]> lst = getEntityManager().createNativeQuery(strQuery).getResultList();
		if(lst.size()>0){
			Object[] arrObj = lst.get(0);
			int partyId = (int) arrObj[0];
			int persontypeId = (int) arrObj[1];
			if(persontypeId == 1){
				strQuery = "select LP.firstname, LP.middlename, LP.lastname, LG.gender, LM.maritalstatus, LP.identityno, LI.identitytype, dateofbirth, LP.contactno, LP.address "+ 
						"from la_party_person LP inner join la_partygroup_gender LG on LP.genderid = LG.genderid inner join la_partygroup_maritalstatus LM on " + 
						"LP.maritalstatusid = LM.maritalstatusid inner join la_partygroup_identitytype LI on LP.identitytypeid = Li.identitytypeid " +
						"where personid = " + partyId;
				
				List<Object[]> lstParty = getEntityManager().createNativeQuery(strQuery).getResultList();
				for(Object[] obj : lstParty){
					LaExtPersonLandMapping laExtPersonLandMapping = new LaExtPersonLandMapping();
				}
			}else {
				
			}
		}
		
		return null;
	}

	@Override
	public List<LaExtPersonLandMapping> getPersonOfInterst(Integer landid,Integer persontypeid) {
	          	return null;
	          	
	          	
	}

	@Override
	public List<LaExtPersonLandMapping> getPersonLandMapBylandId(Long Id) {
		try {
			String query = "select s from LaExtPersonLandMapping s where s.landid="+ Id;

			@SuppressWarnings("unchecked")
			List<LaExtPersonLandMapping> socailTenureList = getEntityManager()
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
}
