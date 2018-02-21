package com.rmsi.mast.viewer.dao.hibernate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaPartyPerson;
import com.rmsi.mast.viewer.dao.LaPartyPersonDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class LaPartyPersonHibernateDao extends GenericHibernateDAO<LaPartyPerson, Long>
implements LaPartyPersonDao{

	@SuppressWarnings("unchecked")
	@Override
	public LaPartyPerson getPartyPersonDetails(Integer landid) {
		
		
		String strQuery= "select LP.partyid,LP.persontypeid from la_ext_personlandmapping LP where landid = " + landid +" and isactive = true";
		List<Object[]> lst = getEntityManager().createNativeQuery(strQuery).getResultList();
		if(lst.size()>0){
			Object[] arrObj = lst.get(0);
			int partyId = Integer.parseInt(arrObj[0].toString());
			int persontypeId =  Integer.parseInt(arrObj[1].toString());
			if(persontypeId == 1){
				strQuery = "select LP.firstname, LP.middlename, LP.lastname, LG.gender, LM.maritalstatus, LP.identityno, LI.identitytypeid, dateofbirth, LP.contactno, LP.address, LM.maritalstatusid, LG.genderid "+ 
						"from la_party_person LP inner join la_partygroup_gender LG on LP.genderid = LG.genderid inner join la_partygroup_maritalstatus LM on " + 
						"LP.maritalstatusid = LM.maritalstatusid inner join la_partygroup_identitytype LI on LP.identitytypeid = Li.identitytypeid " +
						"where LP.personid = " + partyId;
				
				List<Object[]> lstParty = getEntityManager().createNativeQuery(strQuery).getResultList();
				for(Object[] obj : lstParty){
					LaPartyPerson laPartyPerson = new LaPartyPerson();
					laPartyPerson.setFirstname(obj[0] + "");
					laPartyPerson.setMiddlename(obj[1] + "");
					laPartyPerson.setLastname(obj[2] + "");
					laPartyPerson.setGendername(obj[3] + "");
					//laPartyPerson.setMaritalstatusid(Integer.parseInt(obj[4] + ""));
					laPartyPerson.setIdentityno(obj[5] + "");
					laPartyPerson.setIdentitytypeid(Integer.parseInt(obj[6].toString()));
					//laPartyPerson.setDateofbirth(dateofbirth);
					DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						if(null != obj[7] && !obj[7].toString().isEmpty()){
							Date dateofbirth = dateformat.parse(obj[7].toString());
							laPartyPerson.setDateofbirth(dateofbirth);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					} 
					
					laPartyPerson.setContactno(obj[8] + "");
					if(null != obj[9] && !obj[9].toString().isEmpty())
						laPartyPerson.setAddress(obj[9] + "");
					else
						laPartyPerson.setAddress("");
					laPartyPerson.setMaritalstatusid(Integer.parseInt(obj[10].toString()));
					laPartyPerson.setGenderid(Integer.parseInt(obj[11].toString()));
					
					if(null != obj[7] && !obj[7].toString().isEmpty()){
						laPartyPerson.setDob(obj[7].toString());
					}
					return laPartyPerson;
				}
			}else {
				
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaPartyPerson> getAllPartyPersonDetails(Integer landid) {
		
		List<LaPartyPerson> lstpartyperson = new ArrayList<LaPartyPerson>();
		try {
			String strQuery= "select LP.partyid,LP.persontypeid from la_ext_personlandmapping LP where landid = " + landid +" and isactive = true";
			List<Object[]> lst = getEntityManager().createNativeQuery(strQuery).getResultList();
			if(lst.size()>0)
			{			
				for(Object[] arrObj : lst)
				{
					//Object[] arrObj = lst.get(0);
					int partyId = Integer.parseInt(arrObj[0].toString());
					int persontypeId =  Integer.parseInt(arrObj[1].toString());
					if(persontypeId == 1){
						strQuery = "select LP.firstname, LP.middlename, LP.lastname, LG.gender, LM.maritalstatus, LP.identityno, LI.identitytypeid, dateofbirth, LP.contactno, LP.address, LM.maritalstatusid, LG.genderid,LP.personid "+ 
								"from la_party_person LP inner join la_partygroup_gender LG on LP.genderid = LG.genderid inner join la_partygroup_maritalstatus LM on " + 
								"LP.maritalstatusid = LM.maritalstatusid inner join la_partygroup_identitytype LI on LP.identitytypeid = Li.identitytypeid " +
								"where LP.personid = " + partyId;
						
						List<Object[]> lstParty = getEntityManager().createNativeQuery(strQuery).getResultList();
						for(Object[] obj : lstParty){
							LaPartyPerson laPartyPerson = new LaPartyPerson();
							laPartyPerson.setFirstname(obj[0] + "");
							laPartyPerson.setMiddlename(obj[1] + "");
							laPartyPerson.setLastname(obj[2] + "");
							laPartyPerson.setGendername(obj[3] + "");					
							laPartyPerson.setIdentityno(obj[5] + "");
							laPartyPerson.setIdentitytypeid(Integer.parseInt(obj[6].toString()));					
							DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
							try {
								if(null != obj[7] && !obj[7].toString().isEmpty()){
									Date dateofbirth = dateformat.parse(obj[7].toString());
									laPartyPerson.setDateofbirth(dateofbirth);
								}
							} catch (ParseException e) {
								e.printStackTrace();
							} 
							
							laPartyPerson.setContactno(obj[8] + "");
							if(null != obj[9] && !obj[9].toString().isEmpty())
								laPartyPerson.setAddress(obj[9] + "");
							else
								laPartyPerson.setAddress("");
							laPartyPerson.setMaritalstatusid(Integer.parseInt(obj[10].toString()));
							laPartyPerson.setGenderid(Integer.parseInt(obj[11].toString()));
							laPartyPerson.setPersonid(Long.parseLong(obj[12].toString()));
							
							if(null != obj[7] && !obj[7].toString().isEmpty()){
								laPartyPerson.setDob(obj[7].toString());
							}
							
							lstpartyperson.add(laPartyPerson);
							
						}
					}
					
				}
				return lstpartyperson;
				
			}
		} catch (NumberFormatException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lstpartyperson;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public LaPartyPerson getPartyPersonDetailssurrenderlease(Integer landid) {
		
		
		try {
			String strQuery= "select LP.personid,LP.monthid from la_lease LP where landid = " + landid +" and isactive = true";
			List<Object[]> lst = getEntityManager().createNativeQuery(strQuery).getResultList();
			if(lst.size()>0){
				Object[] arrObj = lst.get(0);
				int partyId = Integer.parseInt(arrObj[0].toString());
				int persontypeId =  1;
				if(persontypeId == 1){
					/*strQuery = "select LP.firstname, LP.middlename, LP.lastname, LG.gender, LM.maritalstatus, LP.identityno, LI.identitytypeid, dateofbirth, LP.contactno, LP.address, LM.maritalstatusid, LG.genderid "+ 
							"from la_party_person LP inner join la_partygroup_gender LG on LP.genderid = LG.genderid inner join la_partygroup_maritalstatus LM on " + 
							"LP.maritalstatusid = LM.maritalstatusid inner join la_partygroup_identitytype LI on LP.identitytypeid = Li.identitytypeid " +
							"where LP.personid = " + partyId;*/
					
					strQuery = "select LP.firstname, LP.middlename, LP.lastname, LG.gender, LM.maritalstatus, LP.identityno, LI.identitytypeid, dateofbirth, LP.contactno, LP.address, LM.maritalstatusid, LG.genderid ,"
							+ " lea.leaseamount,lea.leaseyear,lea.monthid from la_party_person LP"
							+ " inner join la_partygroup_gender LG on LP.genderid = LG.genderid"
							+ " inner join la_partygroup_maritalstatus LM on  LP.maritalstatusid = LM.maritalstatusid"
							+ " inner join la_partygroup_identitytype LI on LP.identitytypeid = Li.identitytypeid"
							+ " left join la_lease lea on  LP.personid=lea.personid"
							+ " where LP.personid =  " + partyId +" and lea.landid= "+ landid;
					
					
					
					List<Object[]> lstParty = getEntityManager().createNativeQuery(strQuery).getResultList();
					for(Object[] obj : lstParty){
						LaPartyPerson laPartyPerson = new LaPartyPerson();
						laPartyPerson.setFirstname(obj[0] + "");
						laPartyPerson.setMiddlename(obj[1] + "");
						laPartyPerson.setLastname(obj[2] + "");
						laPartyPerson.setGendername(obj[3] + "");					
						laPartyPerson.setIdentityno(obj[5] + "");
						laPartyPerson.setIdentitytypeid(Integer.parseInt(obj[6].toString()));	
						laPartyPerson.setPersonid(Long.parseLong(arrObj[0].toString()));
						
						Double d = (Double) obj[12];
						Integer intvalue = d.intValue();						
						
						laPartyPerson.setHierarchyid1(Integer.parseInt(intvalue.toString()));	// Lease Amount	
						Integer yearvalue = (Integer) obj[13];
						Integer monthvalue = (Integer) obj[14];
						Integer totalmonthvalue =  ((yearvalue)*12) + monthvalue;
						
						laPartyPerson.setHierarchyid2(Integer.parseInt(totalmonthvalue.toString()));	// Lease Year					
						DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
						try {
							if(null != obj[7] && !obj[7].toString().isEmpty()){
								Date dateofbirth = dateformat.parse(obj[7].toString());
								laPartyPerson.setDateofbirth(dateofbirth);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						} 
						
						laPartyPerson.setContactno(obj[8] + "");
						if(null != obj[9] && !obj[9].toString().isEmpty())
							laPartyPerson.setAddress(obj[9] + "");
						else
							laPartyPerson.setAddress("");
						laPartyPerson.setMaritalstatusid(Integer.parseInt(obj[10].toString()));
						laPartyPerson.setGenderid(Integer.parseInt(obj[11].toString()));
						
						if(null != obj[7] && !obj[7].toString().isEmpty()){
							laPartyPerson.setDob(obj[7].toString());
						}
						return laPartyPerson;
					}
				}
			}
			
		
		} catch (NumberFormatException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
}
