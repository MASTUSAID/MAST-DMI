package com.rmsi.mast.viewer.web.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.studio.dao.DocumentTypeDao;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.domain.DocumentType;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.LaExtFinancialagency;
import com.rmsi.mast.studio.domain.LaExtPersonLandMapping;
import com.rmsi.mast.studio.domain.LaExtProcess;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaMortgage;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.LaPartyPerson;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.LaSurrenderLease;
import com.rmsi.mast.studio.domain.La_Month;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.viewer.dao.LaLeaseDao;
import com.rmsi.mast.viewer.service.LandRecordsService;
import com.rmsi.mast.viewer.service.RegistrationRecordsService;

@Controller
public class RegistrationRecordsController {

	Logger logger = Logger.getLogger(RegistrationRecordsController.class);
	
	@Autowired
	RegistrationRecordsService registrationRecordsService;
	
	@Autowired
    UserService userService;
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	DocumentTypeDao documentTypeDao;
	
	@Autowired
	LaLeaseDao laLeaseDao;
	
	@Autowired
	private LandRecordsService landRecordsService;

	@RequestMapping(value = "/viewer/registryrecords/spatialunit/{project}/{startfrom}", method = RequestMethod.GET)
	@ResponseBody
	public List<LaSpatialunitLand> spatialUnitList(@PathVariable String project, @PathVariable Integer startfrom) {
		Integer projectId=0;
		
		try{
			 Project objproject=projectDAO.findByName(project);
			 projectId= objproject.getProjectnameid();
		}catch(Exception e){
			e.printStackTrace();
		}

		return registrationRecordsService.findAllSpatialUnitTemp(projectId+"", startfrom);
	}
	
	
	@RequestMapping(value = "/viewer/registryrecords/spatialunitcount/{project}/{startfrom}", method = RequestMethod.GET)
	@ResponseBody
	public Integer spatialUnitCount(@PathVariable String project, @PathVariable Integer startfrom) {
		
		Integer projectId=0;
		try{
			 Project objproject=projectDAO.findByName(project);
			 projectId= objproject.getProjectnameid();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return registrationRecordsService.findSpatialUnitTempCount(projectId+"", startfrom);
	}

	/*
	 * @RequestMapping(value =
	 * "/viewer/registryrecords/spatialunit/{project}/{startfrom}", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public List<SpatialUnitTemp> spatialUnitList(@PathVariable
	 * String project, @PathVariable Integer startfrom) { return
	 * registryRecordsService.findAllSpatialUnitTemp(project, startfrom); }
	 */
	@RequestMapping(value = "/viewer/registration/partydetails/lease/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public LaExtPersonLandMapping getPersonLandMapDetails(
			@PathVariable Integer landid) {

		return registrationRecordsService.getPersonLandMapDetails(landid);
	}

	/*@RequestMapping(value = "/viewer/registration/partydetails/sale/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public LaPartyPerson getPartyPersonDetails(@PathVariable Integer landid) {

		return registrationRecordsService.getPartyPersonDetails(landid);
	}*/
	
	@RequestMapping(value = "/viewer/registration/partydetails/sale/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public List<LaPartyPerson> getAllPartyPersonDetails(@PathVariable Integer landid) {

		return registrationRecordsService.getAllPartyPersonDetails(landid);
	}
	
	@RequestMapping(value = "/viewer/registration/islandunderlease/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean ValidateLandunderLease(@PathVariable Long landid) 
	{
		return  registrationRecordsService.islandunderlease(landid);
		
	}
	
	@RequestMapping(value = "/viewer/registration/partydetailssurrenderlease/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public LaPartyPerson getPartyPersonDetailssurrenderlease(@PathVariable Integer landid) {

		return registrationRecordsService.getPartyPersonDetailssurrenderlease(landid);
	}

	// MaritalStatusDao

	@RequestMapping(value = "/viewer/registration/maritalstatus/", method = RequestMethod.GET)
	@ResponseBody
	public List<MaritalStatus> getMaritalStatusDetails() {

		return registrationRecordsService.getMaritalStatusDetails();
	}

	@RequestMapping(value = "/viewer/registration/genderstatus/", method = RequestMethod.GET)
	@ResponseBody
	public List<Gender> getGenderDetails() {

		return registrationRecordsService.getGenderDetails();
	}

	@RequestMapping(value = "/viewer/registration/idtype/", method = RequestMethod.GET)
	@ResponseBody
	public List<IdType> getIDTypeDetails() {

		return registrationRecordsService.getIDTypeDetails();
	}

	@RequestMapping(value = "/viewer/registration/laspatialunitland/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public List<LaSpatialunitLand> getLaSpatialunitLandDetails(
			@PathVariable Long landid) {

		return registrationRecordsService.getLaSpatialunitLandDetails(landid);
	}
	
	
	
	 @RequestMapping(value = "/viewer/registration/relationshiptypes/", method = RequestMethod.GET)
	 @ResponseBody
	 public List<RelationshipType> getAllRelationshipTypes() 
	    {
	        return landRecordsService.findAllRelationshipTypes();
	    }

	/*
	 * @RequestMapping(value =
	 * "/viewer/registration/laspatialunitland/{landid}", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public List<SpatialUnit>
	 * getLaSpatialunitLandDetails(@PathVariable Integer landid){
	 * 
	 * return
	 * registrationRecordsService.getSpatialUnitLandMappingDetails(landid); }
	 * 
	 * @RequestMapping(value =
	 * "/viewer/registration/laspatialunitland/{landid}", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public List<LaSpatialunitLand>
	 * getLaSpatialunitLandDetails(@PathVariable Integer landid){
	 * 
	 * return registrationRecordsService.getLaSpatialunitLandDetails(landid); }
	 */

	@RequestMapping(value = "/viewer/registration/landtype/", method = RequestMethod.GET)
	@ResponseBody
	public List<LandType> getLandTypeDetails() {

		return registrationRecordsService.getAllLandType();
	}

	@RequestMapping(value = "/viewer/registration/allcountry/", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getAllCountry() {

		return registrationRecordsService.getAllCountry();
	}

	@RequestMapping(value = "/viewer/registration/allregion/{country_r_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getAllRegion(@PathVariable Integer country_r_id) {

		return registrationRecordsService.getAllRegion(country_r_id);
	}

	@RequestMapping(value = "/viewer/registration/allprovince/{region_r_id}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectRegion> getAllProvience(@PathVariable Integer region_r_id) {

		return registrationRecordsService.getAllProvience(region_r_id);
	}

	//

	@RequestMapping(value = "/viewer/registration/landsharetypes/", method = RequestMethod.GET)
	@ResponseBody
	public List<ShareType> getAlllandsharetypes() {

		return registrationRecordsService.getAlllandsharetype();
	}

	@RequestMapping(value = "/viewer/registration/land/sharetype/", method = RequestMethod.GET)
	@ResponseBody
	public List<ShareType> getAlllandsharetype() {

		return registrationRecordsService.getAlllandsharetype();
	}

	@RequestMapping(value = "/viewer/registration/processdetails/", method = RequestMethod.GET)
	@ResponseBody
	public List<LaExtProcess> getAllProcessDetails() {

		return registrationRecordsService.getAllProcessDetails();
	}
	
	@RequestMapping(value = "/viewer/registration/monthoflease/", method = RequestMethod.GET)
	@ResponseBody
	public List<La_Month> getmonthofleaseDetails() {

		return registrationRecordsService.getmonthofleaseDetails();
	}
	
	@RequestMapping(value = "/viewer/registration/financialagency/", method = RequestMethod.GET)
	@ResponseBody
	public List<LaExtFinancialagency> getFinancialagencyDetails() {

		return registrationRecordsService.getFinancialagencyDetails();
	}

	@RequestMapping(value = "/viewer/registration/saveprocessdata", method = RequestMethod.POST)
	@ResponseBody
	public String saveProcessData(HttpServletRequest request, HttpServletResponse response,Principal principal) {
		Long landId = 0L;
		Long buyerRelationShipId = 0L;
		 String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			
			Long user_id = userObj.getId();
		/*if(landId == 0){
			return true;
		}*/
		try {
			String buyerFirstName = ServletRequestUtils.getRequiredStringParameter(request, "firstname_r_sale1");
			String buyerMiddleName = ServletRequestUtils.getRequiredStringParameter(request, "middlename_r_sale1");
			String buyerLastName = ServletRequestUtils.getRequiredStringParameter(request, "lastname_r_sale1");
			int buyerIdTypeid = ServletRequestUtils.getRequiredIntParameter(request, "sale_idtype_buyer");
			String buyerId = ServletRequestUtils.getRequiredStringParameter(request, "id_r1");
			String buyerContact_No = ServletRequestUtils.getRequiredStringParameter(request, "contact_no1");
			int buyerGenderId = ServletRequestUtils.getRequiredIntParameter(request, "sale_gender_buyer");
			String buyerAddress = ServletRequestUtils.getRequiredStringParameter(request, "address_sale1");
			String buyerDOBstr = ServletRequestUtils.getRequiredStringParameter(request, "date_Of_birth_sale1");
			int buyerMaritalStatusId = ServletRequestUtils.getRequiredIntParameter(request, "sale_marital_buyer");
			String buyerRemarks = ServletRequestUtils.getRequiredStringParameter(request, "remrks_sale");
			
			Long processid = ServletRequestUtils.getRequiredLongParameter(request, "registration_process");
			
			if(processid == 6)
			{
				buyerRelationShipId = ServletRequestUtils.getRequiredLongParameter(request, "sale_relation_buyer");
			}		

			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			Status status = registrationRecordsService.getStatusById(2);

			PersonType personType = registrationRecordsService.getPersonTypeById(1);

			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			/*Date date = null;
			long time = 0;*/
			Date buyerDOB = null;
			try {
				buyerDOB = dateformat.parse(buyerDOBstr);
				/*date = dateformat.parse(new Date().toString());
				time = date.getTime();*/

			} catch (ParseException e) {
				e.printStackTrace();
			}
			MaritalStatus maritalStatus = registrationRecordsService.getMaritalStatusByID(buyerMaritalStatusId);
			IdType idType = registrationRecordsService.getIDTypeDetailsByID(buyerIdTypeid);
			NaturalPerson naturalPerson = new NaturalPerson();
			naturalPerson.setContactno(buyerContact_No);
			naturalPerson.setCreatedby(user_id.intValue());
			naturalPerson.setCreateddate(new Date());
			naturalPerson.setDateofbirth(buyerDOB);
			naturalPerson.setFirstname(buyerFirstName);
			naturalPerson.setMiddlename(buyerMiddleName);
			naturalPerson.setLastname(buyerLastName);
			naturalPerson.setIsactive(true);
			naturalPerson.setGenderid(buyerGenderId);
			naturalPerson.setLaPartygroupMaritalstatus(maritalStatus);
			naturalPerson.setAddress(buyerAddress);
			naturalPerson.setLaPartygroupIdentitytype(idType);
			naturalPerson.setIdentityno(buyerId);
			
			if(processid == 6)
			{
				RelationshipType obj = new RelationshipType();
				obj.setRelationshiptypeid(buyerRelationShipId);
				naturalPerson.setLaPartygroupRelationshiptype(obj);
			}
			
			naturalPerson.setLaSpatialunitgroup1(registrationRecordsService.findLaSpatialunitgroupById(1));
			naturalPerson.setLaSpatialunitgroup2(registrationRecordsService.findLaSpatialunitgroupById(2));
			naturalPerson.setLaSpatialunitgroup3(registrationRecordsService.findLaSpatialunitgroupById(3));
			naturalPerson.setLaSpatialunitgroupHierarchy1(registrationRecordsService.findProjectRegionById(1));
			naturalPerson.setLaSpatialunitgroupHierarchy2(registrationRecordsService.findProjectRegionById(2));
			naturalPerson.setLaSpatialunitgroupHierarchy3(registrationRecordsService.findProjectRegionById(3));

			LaParty laParty = new LaParty();
			laParty.setCreatedby(user_id.intValue());
			laParty.setCreateddate(new Date());
			laParty.setLaPartygroupPersontype(personType);
			// laParty.setPartyid(234l);
			// laParty.setLaPartyPerson(naturalPerson);
			// naturalPerson.setLaParty(laParty);
			/*
			 * laParty.setLaPartyPerson(naturalPerson);
			 * naturalPerson.setLaParty(laParty);
			 */
			// naturalPerson.setLaParty(laParty);
			Long partyId = 0l;
			SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
			Long sellerPartyId = 0L;
			if (socialTenureRelationshipSellerDetails != null)
				sellerPartyId = socialTenureRelationshipSellerDetails.getPartyid();
			else
				return null;			
			
			try
			{
				if(processid == 7)
				{
					sellerPartyId = ServletRequestUtils.getRequiredLongParameter(request, "Owner_Elimanated");
				}
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				/*
				 * //Long partyID =
				 * registrationRecordsService.saveParty(laParty).getPartyid();
				 * //naturalPerson.setPersonid(partyID); Not
				 */
				naturalPerson.setLaPartygroupPersontype(personType);
				NaturalPerson naturalPerson2 = registrationRecordsService.saveNaturalPerson(naturalPerson);
				partyId = naturalPerson2.getPartyid();
			} catch (Exception er) {
				er.printStackTrace();
				return null;
			}
			SocialTenureRelationship socialTenureRelationship = new SocialTenureRelationship();
			socialTenureRelationship.setCreatedby(user_id.intValue());
			socialTenureRelationship.setPartyid(partyId);
			socialTenureRelationship.setLaPartygroupPersontype(personType);
			socialTenureRelationship.setCreateddate(new Date());
			socialTenureRelationship.setIsactive(true);
			socialTenureRelationship.setLandid(landId);
			// socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetail)

			// socialTenureRelationship.setLaSpatialunitLand(laSpatialunitLand)

			/*
			 * List<SocialTenureRelationship> lstSocialTenureRelationships = new
			 * ArrayList<SocialTenureRelationship>();
			 * lstSocialTenureRelationships.add(socialTenureRelationship);
			 */

			LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);
			laExtTransactiondetail.setModuletransid(partyId.intValue());
			laExtTransactiondetail.setRemarks(buyerRemarks);
			//laExtTransactiondetail.setProcessid(2l);
			laExtTransactiondetail.setProcessid(processid);
			// laExtTransactiondetail.setLaExtPersonlandmappings(lstSocialTenureRelationships);

			socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetail);

			try {
				// registrationRecordsService.saveTransaction(laExtTransactiondetail);
				/*
				 * List<LaSpatialunitLand> lstLaSpatialunitLand =
				 * registrationRecordsService
				 * .getLaSpatialunitLandDetails(landId);
				 */
				socialTenureRelationship = registrationRecordsService.saveSocialTenureRelationship(socialTenureRelationship);
				registrationRecordsService.updateSocialTenureRelationshipByPartyId(sellerPartyId,landId);
				/*
				 * registrationRecordsService.updateLaSpatialunitLand(
				 * lstLaSpatialunitLand.get(0));
				 * registrationRecordsService.addLaSpatialunitLand
				 * (lstLaSpatialunitLand.get(0));
				 */
				
			} catch (Exception er) {
				er.printStackTrace();
			}
			return socialTenureRelationship.getLaExtTransactiondetail().getTransactionid()+"";
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/viewer/registration/saveleasedata", method = RequestMethod.POST)
	@ResponseBody
	public String saveLeaseData(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			String firstName = ServletRequestUtils.getRequiredStringParameter(request, "firstname_r_applicant");
			String middlename = ServletRequestUtils.getRequiredStringParameter(request, "middlename_r_applicant");
			String lastname = ServletRequestUtils.getRequiredStringParameter(request, "lastname_r_applicant");
			String id = ServletRequestUtils.getRequiredStringParameter(request,"id_r_applicant");
			int id_type = ServletRequestUtils.getRequiredIntParameter(request, "id_type_applicant");
			String contact_no = ServletRequestUtils.getRequiredStringParameter(request, "contact_no_applicant");
			int genderId = ServletRequestUtils.getRequiredIntParameter(request,"gender_type_applicant");
			String address = ServletRequestUtils.getRequiredStringParameter(request, "address_lease_applicant");
			String date_Of_birth = ServletRequestUtils.getRequiredStringParameter(request,"date_Of_birth_applicant");
			int martialId = ServletRequestUtils.getRequiredIntParameter(request, "martial_sts_applicant");

			String remrks_lease = ServletRequestUtils.getRequiredStringParameter(request,"remrks_lease");
			
			int no_Of_month_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_month_Lease");
			Double lease_Amount = ServletRequestUtils.getRequiredDoubleParameter(request,"lease_Amount");
			//int no_Of_years_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_years_Lease");
			
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			Status status = registrationRecordsService.getStatusById(2);

			PersonType personType = registrationRecordsService.getPersonTypeById(1);

			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			/*Date date = null;
			long time = 0;*/
			Date dateOfBirth = null;
			try {
				dateOfBirth = dateformat.parse(date_Of_birth);
				/*date = dateformat.parse(new Date().toString());
				time = date.getTime();*/

			} catch (ParseException e) {
				e.printStackTrace();
			}
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			int year = no_Of_month_Lease/12;
			no_Of_month_Lease = no_Of_month_Lease%12;
			
			Long user_id = userObj.getId();
				
			MaritalStatus maritalStatus = registrationRecordsService.getMaritalStatusByID(martialId);
			IdType idType = registrationRecordsService.getIDTypeDetailsByID(id_type);
			NaturalPerson naturalPerson = new NaturalPerson();
			naturalPerson.setContactno(contact_no);
			naturalPerson.setCreatedby(user_id.intValue());
			naturalPerson.setCreateddate(new Date());
			naturalPerson.setDateofbirth(dateOfBirth);
			naturalPerson.setFirstname(firstName);
			naturalPerson.setMiddlename(middlename);
			naturalPerson.setLastname(lastname);
			naturalPerson.setIsactive(true);
			naturalPerson.setGenderid(genderId);
			naturalPerson.setLaPartygroupMaritalstatus(maritalStatus);
			naturalPerson.setAddress(address);
			naturalPerson.setLaPartygroupIdentitytype(idType);
			naturalPerson.setIdentityno(id);
			naturalPerson.setLaSpatialunitgroup1(registrationRecordsService.findLaSpatialunitgroupById(1));
			naturalPerson.setLaSpatialunitgroup2(registrationRecordsService.findLaSpatialunitgroupById(2));
			naturalPerson.setLaSpatialunitgroup3(registrationRecordsService.findLaSpatialunitgroupById(3));
			naturalPerson.setLaSpatialunitgroupHierarchy1(registrationRecordsService.findProjectRegionById(1));
			naturalPerson.setLaSpatialunitgroupHierarchy2(registrationRecordsService.findProjectRegionById(2));
			naturalPerson.setLaSpatialunitgroupHierarchy3(registrationRecordsService.findProjectRegionById(3));

			/*LaParty laParty = new LaParty();
			laParty.setCreatedby(user_id.intValue());
			laParty.setCreateddate(new Date());
			laParty.setLaPartygroupPersontype(personType);*/
//			Long partyId = 0l;
			/*SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
			Long sellerPartyId = 0L;
			if (socialTenureRelationshipSellerDetails != null)
				sellerPartyId = socialTenureRelationshipSellerDetails.getPartyid();
			else
				return false;*/
			try {
				naturalPerson.setLaPartygroupPersontype(personType);
				naturalPerson = registrationRecordsService.saveNaturalPerson(naturalPerson);
//				NaturalPerson naturalPerson2 = registrationRecordsService.saveNaturalPerson(naturalPerson);
//				partyId = naturalPerson2.getPartyid();
			} catch (Exception er) {
				er.printStackTrace();
				return null;
			}


			Long ownerid = 0L;
			try 
			{
				SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
				
				if (socialTenureRelationshipSellerDetails != null)
					ownerid = socialTenureRelationshipSellerDetails.getPartyid();
				else
					return null;
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
			
			LaLease laLease = new LaLease();
			laLease.setCreatedby(user_id.intValue());
			laLease.setCreateddate(new Date());
			laLease.setIsactive(true);
			laLease.setLa_Month(laMonth);
			laLease.setLeaseamount(lease_Amount);
			laLease.setLeaseyear(year);//no_Of_years_Lease
			laLease.setPersonid(naturalPerson.getPartyid());
			laLease.setLandid(landId);
			laLease.setOwnerid(ownerid);

//			LaExtFinancialagency laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
			
			LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());//new Timestamp(time)
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);
			
			laExtTransactiondetail.setRemarks(remrks_lease);
			laExtTransactiondetail.setProcessid(1L);// process Id 1 for Lease
			
			laLease = registrationRecordsService.saveLease(laLease);
			
			laExtTransactiondetail.setModuletransid(laLease.getLeaseid());
			
			laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);

			return laExtTransactiondetail.getTransactionid().toString();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}
	
	@RequestMapping(value = "/viewer/registration/savesurrenderleasedata", method = RequestMethod.POST)
	@ResponseBody
	public String saveSurrenderLeasedata(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			/*String firstName = ServletRequestUtils.getRequiredStringParameter(request, "firstname_r_applicant");
			String middlename = ServletRequestUtils.getRequiredStringParameter(request, "middlename_r_applicant");
			String lastname = ServletRequestUtils.getRequiredStringParameter(request, "lastname_r_applicant");
			String id = ServletRequestUtils.getRequiredStringParameter(request,"id_r_applicant");
			int id_type = ServletRequestUtils.getRequiredIntParameter(request, "id_type_applicant");
			String contact_no = ServletRequestUtils.getRequiredStringParameter(request, "contact_no_applicant");
			int genderId = ServletRequestUtils.getRequiredIntParameter(request,"gender_type_applicant");
			String address = ServletRequestUtils.getRequiredStringParameter(request, "address_lease_applicant");
			String date_Of_birth = ServletRequestUtils.getRequiredStringParameter(request,"date_Of_birth_applicant");
			int martialId = ServletRequestUtils.getRequiredIntParameter(request, "martial_sts_applicant");*/

			String remrks_lease = ServletRequestUtils.getRequiredStringParameter(request,"remrks_lease");
			
			int no_Of_month_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_month_Lease");
			Double lease_Amount = ServletRequestUtils.getRequiredDoubleParameter(request,"lease_Amount");
			
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			Status status = registrationRecordsService.getStatusById(2);

			PersonType personType = registrationRecordsService.getPersonTypeById(1);

			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			
			/*Date dateOfBirth = null;
			try 
			{
				dateOfBirth = dateformat.parse(date_Of_birth);
				

			} catch (ParseException e) {
				e.printStackTrace();
			}*/
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			int year = no_Of_month_Lease/12;
			no_Of_month_Lease = no_Of_month_Lease%12;
			
			Long user_id = userObj.getId();
				
			/*MaritalStatus maritalStatus = registrationRecordsService.getMaritalStatusByID(martialId);
			IdType idType = registrationRecordsService.getIDTypeDetailsByID(id_type);
			NaturalPerson naturalPerson = new NaturalPerson();
			naturalPerson.setContactno(contact_no);
			naturalPerson.setCreatedby(user_id.intValue());
			naturalPerson.setCreateddate(new Date());
			naturalPerson.setDateofbirth(dateOfBirth);
			naturalPerson.setFirstname(firstName);
			naturalPerson.setMiddlename(middlename);
			naturalPerson.setLastname(lastname);
			naturalPerson.setIsactive(true);
			naturalPerson.setGenderid(genderId);
			naturalPerson.setLaPartygroupMaritalstatus(maritalStatus);
			naturalPerson.setAddress(address);
			naturalPerson.setLaPartygroupIdentitytype(idType);
			naturalPerson.setIdentityno(id);
			naturalPerson.setLaSpatialunitgroup1(registrationRecordsService.findLaSpatialunitgroupById(1));
			naturalPerson.setLaSpatialunitgroup2(registrationRecordsService.findLaSpatialunitgroupById(2));
			naturalPerson.setLaSpatialunitgroup3(registrationRecordsService.findLaSpatialunitgroupById(3));
			naturalPerson.setLaSpatialunitgroupHierarchy1(registrationRecordsService.findProjectRegionById(1));
			naturalPerson.setLaSpatialunitgroupHierarchy2(registrationRecordsService.findProjectRegionById(2));
			naturalPerson.setLaSpatialunitgroupHierarchy3(registrationRecordsService.findProjectRegionById(3));
			
			try {
				naturalPerson.setLaPartygroupPersontype(personType);
				naturalPerson = registrationRecordsService.saveNaturalPerson(naturalPerson);
			} catch (Exception er) {
				er.printStackTrace();
				return null;
			}*/
			
			Long ownerid = 0L;
			try 
			{
				SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
				
				if (socialTenureRelationshipSellerDetails != null)
					ownerid = socialTenureRelationshipSellerDetails.getPartyid();
				else
					return null;
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
			
			LaPartyPerson lapartydetail= getPartyPersonDetailssurrenderlease(Integer.parseInt(landId.toString()));
			
			LaSurrenderLease laLease = new LaSurrenderLease();
			laLease.setCreatedby(user_id.intValue());
			laLease.setCreateddate(new Date());
			laLease.setIsactive(true);
			laLease.setLa_Month(laMonth);
			laLease.setLeaseamount(lease_Amount);
			laLease.setLeaseyear(year);//no_Of_years_Lease
			laLease.setPersonid(lapartydetail.getPersonid());
			laLease.setLandid(landId);
			laLease.setOwnerid(ownerid);;
			
			LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());//new Timestamp(time)
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);
			
			laExtTransactiondetail.setRemarks(remrks_lease);
			laExtTransactiondetail.setProcessid(5L);// process Id 1 for Lease
			registrationRecordsService.disablelease(laLease.getPersonid(),laLease.getLandid());
			laLease = registrationRecordsService.savesurrenderLease(laLease);
			
			laExtTransactiondetail.setModuletransid(laLease.getLeaseid());
			
			laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);

			return laExtTransactiondetail.getTransactionid().toString();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}


	@RequestMapping(value = "/viewer/registration/savemortgagedata", method = RequestMethod.POST)
	@ResponseBody
	public String saveMortgageData(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			
			Long user_id = userObj.getId();
			
			int financial_AgenciesID = ServletRequestUtils.getRequiredIntParameter(request, "mortgage_Financial_Agencies");
			String mortgage_from = ServletRequestUtils.getRequiredStringParameter(request, "mortgage_from");
			String mortgage_to = ServletRequestUtils.getRequiredStringParameter(request, "mortgage_to");
			Double amount_mortgage = ServletRequestUtils.getRequiredDoubleParameter(request, "amount_mortgage");
			String remrks_mortgage = ServletRequestUtils.getRequiredStringParameter(request, "remrks_mortgage");

			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			
			Status status = registrationRecordsService.getStatusById(2);

//			PersonType personType = registrationRecordsService.getPersonTypeById(1);

			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			//Date date = null;
			Date mortgage_fromDate = null;
			Date mortgage_toDate = null;
			//long time = 0;
			try {
				mortgage_fromDate = dateformat.parse(mortgage_from);
				mortgage_toDate = dateformat.parse(mortgage_to);
				
				//date = dateformat.parse(new Date().toString());
				//time = date.getTime();

			} catch (ParseException e) {
				e.printStackTrace();
			}

			Long ownerid = 0L;
			try 
			{
				SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
				
				if (socialTenureRelationshipSellerDetails != null)
					ownerid = socialTenureRelationshipSellerDetails.getPartyid();
				else
					return null;
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
			
			LaExtFinancialagency laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
			
			LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());//new Timestamp(time)
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);
			
			laExtTransactiondetail.setRemarks(remrks_mortgage);
			laExtTransactiondetail.setProcessid(3L);// process Id 3 for Mortgage
			
			LaMortgage laMortgage= new LaMortgage();
			laMortgage.setCreatedby(user_id.intValue());
			laMortgage.setCreateddate(new Date());
			laMortgage.setIsactive(true);
			//laMortgage.setLaExtTransactiondetail(laExtTransactiondetail);
			laMortgage.setMortgageamount(amount_mortgage);
			laMortgage.setMortgagefrom(mortgage_fromDate);
			laMortgage.setMortgageto(mortgage_toDate);
			laMortgage.setLaExtFinancialagency(laExtFinancialagency);
			laMortgage.setLandid(landId);
			laMortgage.setOwnerid(ownerid);
			
			laMortgage = registrationRecordsService.saveMortgage(laMortgage);
			
			laExtTransactiondetail.setModuletransid(laMortgage.getMortgageid());
			
			laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);

			return laExtTransactiondetail.getTransactionid().toString();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}

	@RequestMapping(value = "/viewer/registration/savedocument", method = RequestMethod.POST)
	@ResponseBody
	public boolean savedocument(MultipartHttpServletRequest request, HttpServletResponse response,Principal principal) {
		try {
			Long landId = 0L;

			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Long user_id = userObj.getId();
			
			Iterator<String> file = request.getFileNames();

			landId = ServletRequestUtils.getRequiredLongParameter(request,"selectedlandid");

			String doc_name_sale = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_sale");
			String doc_desc_sale = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_sale");
			String doc_date_sale = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_sale");
			Long doc_typeid = ServletRequestUtils.getRequiredLongParameter(request, "doc_Type_Sale");
			String transactionid = ServletRequestUtils.getRequiredStringParameter(request, "transactionid");

			byte[] document = null;
			while (file.hasNext()) {
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);
				String originalFileName = mpFile.getOriginalFilename();
				//String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1, originalFileName.length()).toLowerCase();

				if (!"".equals(originalFileName)) {
					document = mpFile.getBytes();
					originalFileName=originalFileName.substring(0, originalFileName.indexOf(".")) + "_" + transactionid + originalFileName.substring(originalFileName.indexOf("."), originalFileName.length());
				}
				DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date uploadDate = null;
				try {
					uploadDate = dateformat.parse(doc_date_sale);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				String outDirPath = FileUtils.getFielsFolder(request)+ "resources" + File.separator + "documents"+ File.separator + "Registry" + File.separator+ "webupload";
				File outDir = new File(outDirPath);
				boolean exists = outDir.exists();

				if (!exists) {
					(new File(outDirPath)).mkdirs();
				}

				String uploadFileName = ("resources/documents/" + "Registry" + "/webupload");

				SourceDocument sourceDocument = new SourceDocument();
				sourceDocument.setCreatedby(user_id.intValue());
				sourceDocument.setCreateddate(new Date());
				sourceDocument.setDocumentlocation(uploadFileName + File.separator + originalFileName);
//				sourceDocument.setDocumentname(originalFileName);
				sourceDocument.setDocumentname(doc_name_sale);
				sourceDocument.setIsactive(true);
				sourceDocument.setRemarks(doc_desc_sale);
				sourceDocument.setRecordationdate(uploadDate);
				sourceDocument.setDocumenttypeid(doc_typeid.intValue());
				try {
					//List<SpatialUnit> lstSpatialUnit = registrationRecordsService.getSpatialUnitLandMappingDetails(landId);
					SocialTenureRelationship socialTenureRelationship = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
					Long partyId = socialTenureRelationship.getPartyid();
					//Integer transactionid1 = socialTenureRelationship.getLaExtTransactiondetail().getTransactionid();
					LaParty laParty = registrationRecordsService.getLaPartyById(partyId);
					LaExtTransactiondetail laExtTransactiondetail = registrationRecordsService.getLaExtTransactiondetail(Integer.parseInt(transactionid));
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(landId);
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);

				} catch (Exception e) {
					logger.error(e);
					return false;
				}
				try {
					FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + originalFileName);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
				} catch (Exception e) {
					logger.error(e);
					return false;
				}

			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return false;
	}
	@RequestMapping(value = "/viewer/registration/savedocumentmortgage", method = RequestMethod.POST)
	@ResponseBody
	public boolean savedocumentMortgage(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Long user_id = userObj.getId();
			Iterator<String> file = request.getFileNames();

			landId = ServletRequestUtils.getRequiredLongParameter(request,"selectedlandid");

			String doc_name_mortgage = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_mortgage");
			String doc_desc_mortgage = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_mortgage");
			String doc_date_mortgage = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_mortgage");
			Long doc_typeid = ServletRequestUtils.getRequiredLongParameter(request, "doc_Type_Mortgage");
			
			String transactionid = ServletRequestUtils.getRequiredStringParameter(request, "transactionid");

			byte[] document = null;
			while (file.hasNext()) {
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);
				String originalFileName = mpFile.getOriginalFilename();
//				String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1, originalFileName.length()).toLowerCase();

				
				if (!"".equals(originalFileName)) {
					document = mpFile.getBytes();
					originalFileName=originalFileName.substring(0, originalFileName.indexOf(".")) + "_" + transactionid + originalFileName.substring(originalFileName.indexOf("."), originalFileName.length());
				}
				DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date uploadDate = null;
				try {
					uploadDate = dateformat.parse(doc_date_mortgage);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				String outDirPath = FileUtils.getFielsFolder(request)+ "resources" + File.separator + "documents"+ File.separator + "Registry" + File.separator+ "webupload";
				File outDir = new File(outDirPath);
				boolean exists = outDir.exists();

				if (!exists) {
					(new File(outDirPath)).mkdirs();
				}

				String uploadFileName = ("resources/documents/" + "Registry"+ "/webupload");

				SourceDocument sourceDocument = new SourceDocument();
				sourceDocument.setCreatedby(user_id.intValue());
				sourceDocument.setCreateddate(new Date());
				sourceDocument.setDocumentlocation(uploadFileName+ File.separator + originalFileName);
//				sourceDocument.setDocumentname(originalFileName);
				sourceDocument.setDocumentname(doc_name_mortgage);
				sourceDocument.setIsactive(true);
				sourceDocument.setRemarks(doc_desc_mortgage);
				sourceDocument.setRecordationdate(uploadDate);
				sourceDocument.setDocumenttypeid(doc_typeid.intValue());
				try {
//					List<SpatialUnit> lstSpatialUnit = registrationRecordsService.getSpatialUnitLandMappingDetails(landId);
					SocialTenureRelationship socialTenureRelationship = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
					Long partyId = socialTenureRelationship.getPartyid();
					//Integer transactionid = socialTenureRelationship.getLaExtTransactiondetail().getTransactionid();
					LaParty laParty = registrationRecordsService.getLaPartyById(partyId);
					LaExtTransactiondetail laExtTransactiondetail = registrationRecordsService.getLaExtTransactiondetail(Integer.parseInt(transactionid));
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(landId);
					//sourceDocument.setLaSpatialunitLand(lstSpatialUnit.get(0).getLandid());
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);

				} catch (Exception e) {
					logger.error(e);
					return false;
				}
				try {
					FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + originalFileName);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
				} catch (Exception e) {
					logger.error(e);
					return false;
				}

			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return false;
	}

	@RequestMapping(value = "/viewer/registration/savedocumentlease", method = RequestMethod.POST)
	@ResponseBody
	public boolean savedocumentLease(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {
		
		try {
			Long landId = 0L;
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Long user_id = userObj.getId();
			Iterator<String> file = request.getFileNames();

			landId = ServletRequestUtils.getRequiredLongParameter(request,"selectedlandid");

			String doc_name_Lease = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_Lease");
			String doc_desc_Lease = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_Lease");
			String doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_Lease");
			Long doc_typeid = ServletRequestUtils.getRequiredLongParameter(request, "doc_Type_Lease");
			String transactionid = ServletRequestUtils.getRequiredStringParameter(request, "transactionid");

			byte[] document = null;
			while (file.hasNext()) {
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);
				String originalFileName = mpFile.getOriginalFilename();
//				String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1, originalFileName.length()).toLowerCase();

				if (!"".equals(originalFileName)) {
					document = mpFile.getBytes();
					originalFileName=originalFileName.substring(0, originalFileName.indexOf(".")) + "_" + transactionid + originalFileName.substring(originalFileName.indexOf("."), originalFileName.length());
				}

				String outDirPath = FileUtils.getFielsFolder(request)+ "resources" + File.separator + "documents"+ File.separator + "Registry" + File.separator+ "webupload";
				File outDir = new File(outDirPath);
				boolean exists = outDir.exists();

				if (!exists) {
					(new File(outDirPath)).mkdirs();
				}
				
				DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date uploadDate = null;
				try {
					uploadDate = dateformat.parse(doc_date);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				String uploadFileName = ("resources/documents/" + "Registry"+ "/webupload");

				SourceDocument sourceDocument = new SourceDocument();
				sourceDocument.setCreatedby(user_id.intValue());
				sourceDocument.setCreateddate(new Date());
				sourceDocument.setDocumentlocation(uploadFileName + File.separator + originalFileName);
//				sourceDocument.setDocumentname(originalFileName);
				sourceDocument.setDocumentname(doc_name_Lease);
				sourceDocument.setIsactive(true);
				sourceDocument.setRemarks(doc_desc_Lease);
				sourceDocument.setRecordationdate(uploadDate);
				sourceDocument.setDocumenttypeid(doc_typeid.intValue());
				try {
//					List<SpatialUnit> lstSpatialUnit = registrationRecordsService.getSpatialUnitLandMappingDetails(landId);
					/*SocialTenureRelationship socialTenureRelationship = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
					Long partyId = socialTenureRelationship.getPartyid();*/
//					Integer transactionid = socialTenureRelationship.getLaExtTransactiondetail().getTransactionid();
					
					LaExtTransactiondetail laExtTransactiondetail = registrationRecordsService.getLaExtTransactiondetail(Integer.parseInt(transactionid));
					Integer leaseid = laExtTransactiondetail.getModuletransid();
					LaLease laLease = laLeaseDao.getLeaseById(leaseid);
					LaParty laParty = registrationRecordsService.getLaPartyById(laLease.getPersonid());
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(landId);
					//sourceDocument.setLaSpatialunitLand(lstSpatialUnit.get(0).getLandid());
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);

				} catch (Exception e) {
					logger.error(e);
					return false;
				}
				try {
					FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + originalFileName);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
				} catch (Exception e) {
					logger.error(e);
					return false;
				}

			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return false;
	}
	
	@RequestMapping(value = "/viewer/registration/savedocumentsurrenderlease", method = RequestMethod.POST)
	@ResponseBody
	public boolean savedocumentSurrenderLease(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {
		
		try {
			Long landId = 0L;
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Long user_id = userObj.getId();
			Iterator<String> file = request.getFileNames();

			landId = ServletRequestUtils.getRequiredLongParameter(request,"selectedlandid");

			String doc_name_Lease = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_Lease");
			String doc_desc_Lease = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_Lease");
			String doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_Lease");
			Long doc_typeid = ServletRequestUtils.getRequiredLongParameter(request, "doc_Type_Lease");
			String transactionid = ServletRequestUtils.getRequiredStringParameter(request, "transactionid");

			byte[] document = null;
			while (file.hasNext()) {
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);
				String originalFileName = mpFile.getOriginalFilename();
//				String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1, originalFileName.length()).toLowerCase();

				if (!"".equals(originalFileName)) {
					document = mpFile.getBytes();
					originalFileName=originalFileName.substring(0, originalFileName.indexOf(".")) + "_" + transactionid + originalFileName.substring(originalFileName.indexOf("."), originalFileName.length());
				}

				String outDirPath = FileUtils.getFielsFolder(request)+ "resources" + File.separator + "documents"+ File.separator + "Registry" + File.separator+ "webupload";
				File outDir = new File(outDirPath);
				boolean exists = outDir.exists();

				if (!exists) {
					(new File(outDirPath)).mkdirs();
				}
				
				DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date uploadDate = null;
				try {
					uploadDate = dateformat.parse(doc_date);

				} catch (ParseException e) {
					e.printStackTrace();
				}

				String uploadFileName = ("resources/documents/" + "Registry"+ "/webupload");

				SourceDocument sourceDocument = new SourceDocument();
				sourceDocument.setCreatedby(user_id.intValue());
				sourceDocument.setCreateddate(new Date());
				sourceDocument.setDocumentlocation(uploadFileName + File.separator + originalFileName);
//				sourceDocument.setDocumentname(originalFileName);
				sourceDocument.setDocumentname(doc_name_Lease);
				sourceDocument.setIsactive(true);
				sourceDocument.setRemarks(doc_desc_Lease);
				sourceDocument.setRecordationdate(uploadDate);
				sourceDocument.setDocumenttypeid(doc_typeid.intValue());
				try {
//					List<SpatialUnit> lstSpatialUnit = registrationRecordsService.getSpatialUnitLandMappingDetails(landId);
					/*SocialTenureRelationship socialTenureRelationship = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
					Long partyId = socialTenureRelationship.getPartyid();*/
//					Integer transactionid = socialTenureRelationship.getLaExtTransactiondetail().getTransactionid();
					
					LaExtTransactiondetail laExtTransactiondetail = registrationRecordsService.getLaExtTransactiondetail(Integer.parseInt(transactionid));
					Integer leaseid = laExtTransactiondetail.getModuletransid();
					LaSurrenderLease laLease = laLeaseDao.getSurrenderLeaseById(leaseid);
					LaParty laParty = registrationRecordsService.getLaPartyById(laLease.getPersonid());
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(landId);
					//sourceDocument.setLaSpatialunitLand(lstSpatialUnit.get(0).getLandid());
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);

				} catch (Exception e) {
					logger.error(e);
					return false;
				}
				try {
					FileOutputStream uploadfile = new FileOutputStream(outDirPath + File.separator + originalFileName);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
				} catch (Exception e) {
					logger.error(e);
					return false;
				}

			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return false;
	}
	
	
	@RequestMapping(value = "/viewer/registration/search1/{project}/{startfrom}"  , method = RequestMethod.POST)
	@ResponseBody
	public List<LaSpatialunitLand> searchUnitList(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String project,@PathVariable Integer startfrom) {

		String transactionid="0"; 
		Integer projectId=0;
		Integer communeId =0;
		String parce_id="";
		   
		
		 try {
      	   communeId = ServletRequestUtils.getRequiredIntParameter(request, "community_id_R");
         } catch (Exception e) {
             logger.error(e);
         }
         
         try {
      	   parce_id = ServletRequestUtils.getRequiredStringParameter(request, "parce_id_R");
         } catch (Exception e) {
             logger.error(e);
         }
         
         
		 try {
			 transactionid = ServletRequestUtils.getRequiredStringParameter(request, "usinstr_id_R");
         } catch (Exception e) {
             logger.error(e);
         }
		
		 try{
			 Project objproject=projectDAO.findByName(project);
			 projectId= objproject.getProjectnameid();
		}catch(Exception e){
			e.printStackTrace();
		}

		 
		 
		try {
			return registrationRecordsService.search( Long.parseLong(transactionid.trim()),startfrom,projectId+"",communeId+"",parce_id);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	
	@RequestMapping(value = "/viewer/registration/search1Count/{project}/{startfrom}" , method = RequestMethod.POST)
	@ResponseBody
	public Integer searchUnitCount(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String project,@PathVariable Integer startfrom) {

		String transactionid="0"; 
		Integer projectId=0;
		Integer communeId =0;
		String parce_id="";
		
		 try {
			 transactionid = ServletRequestUtils.getRequiredStringParameter(request, "usinstr_id_R");
         } catch (Exception e) {
             logger.error(e);
         }
		 
		 try {
	      	   communeId = ServletRequestUtils.getRequiredIntParameter(request, "community_id_R");
	         } catch (Exception e) {
	             logger.error(e);
	         }
	         
	         try {
	      	   parce_id = ServletRequestUtils.getRequiredStringParameter(request, "parce_id_R");
	         } catch (Exception e) {
	             logger.error(e);
	         }
	         
	         
		 try{
			 Project objproject=projectDAO.findByName(project);
			 projectId= objproject.getProjectnameid();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			return registrationRecordsService.searchCount( Long.parseLong(transactionid.trim()),startfrom,projectId+"",communeId+"",parce_id);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	@RequestMapping(value = "/viewer/registration/doctype", method = RequestMethod.GET)
	@ResponseBody
	public List<DocumentType> getDocumentType() {

		return documentTypeDao.getAllDocumentTypes();
	}
	
}
