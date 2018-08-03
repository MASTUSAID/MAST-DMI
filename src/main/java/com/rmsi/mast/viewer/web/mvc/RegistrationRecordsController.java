package com.rmsi.mast.viewer.web.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
import com.rmsi.mast.studio.dao.OutputformatDAO;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.SocialTenureRelationshipDAO;
import com.rmsi.mast.studio.dao.SourceDocumentDAO;
import com.rmsi.mast.studio.domain.DocumentType;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.LaExtFinancialagency;
import com.rmsi.mast.studio.domain.LaExtPersonLandMapping;
import com.rmsi.mast.studio.domain.LaExtProcess;
import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;
import com.rmsi.mast.studio.domain.LaExtTransactionHistory;
import com.rmsi.mast.studio.domain.LaExtTransactiondetail;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaMortgage;
import com.rmsi.mast.studio.domain.LaParty;
import com.rmsi.mast.studio.domain.LaPartyPerson;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
import com.rmsi.mast.studio.domain.LaSurrenderLease;
import com.rmsi.mast.studio.domain.LaSurrenderMortgage;
import com.rmsi.mast.studio.domain.La_Month;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.Outputformat;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.PoiReport;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.dao.LaExtTransactiondetailDao;
import com.rmsi.mast.viewer.dao.LaLeaseDao;
import com.rmsi.mast.viewer.dao.LaMortgageDao;
import com.rmsi.mast.viewer.dao.LaMortgageSurrenderDao;
import com.rmsi.mast.viewer.dao.LaPartyDao;
import com.rmsi.mast.viewer.dao.LaSurrenderLeaseDao;
import com.rmsi.mast.viewer.dao.SourceDocumentsDao;
import com.rmsi.mast.viewer.service.LaExtRegistrationLandShareTypeService;
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
	
	@Autowired
    LaPartyDao laPartyDao;
	
	@Autowired
	LaMortgageDao laMortgagedao;

	@Autowired
	LaMortgageSurrenderDao lasurrenderMortgagedao;
	
	@Autowired
	LaExtTransactiondetailDao transactionDao;
	
	
	@Autowired
	SourceDocumentDAO sourceDocumentDAO;
	
	@Autowired
	SourceDocumentsDao sourceDocumentsDao;
	
	@Autowired	
	SocialTenureRelationshipDAO socialTenureRelationshipDAO;
	
	@Autowired
	OutputformatDAO Outputformatdao;
	
	@Autowired
	LaSurrenderLeaseDao laSurrenderLeaseDao;
	
	@Autowired
	LandUseTypeDao landusetypedao;
	
	@Autowired
	LaMortgageSurrenderDao laMortgageSurrenderDao;
	
	 @Autowired
	 SpatialUnitPersonWithInterestDao spatialUnitPersonWithInterestDao;
	 
	 @Autowired
	 LaExtRegistrationLandShareTypeService laExtRegistrationLandShareTypeservice;
	

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
	
	
	@RequestMapping(value = "/viewer/registration/editpartydetails/{transid}", method = RequestMethod.GET)
	@ResponseBody
	public List<LaPartyPerson> getAllPartyPersonDetailsByTransactionId(@PathVariable Integer transid) {

		return registrationRecordsService.getAllPartyPersonDetailsByTransactionId(transid);
	}
	
	
	@RequestMapping(value = "/viewer/registration/partydetails/filldetails/{landid}/{processid}", method = RequestMethod.GET)
	@ResponseBody
	public List<LaPartyPerson> fillAllPartyPersonDetails(@PathVariable Integer landid,@PathVariable Integer processid) {

		return registrationRecordsService.fillAllPartyPersonDetails(landid,processid);
	}
	
	@RequestMapping(value = "/viewer/registration/islandunderlease/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean ValidateLandunderLease(@PathVariable Long landid) 
	{
		return  registrationRecordsService.islandunderlease(landid);
		
	}
	
	@RequestMapping(value = "/viewer/registration/partypersondetailssurrenderlease/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public LaPartyPerson getPartyPersonDetailssurrenderlease(@PathVariable Integer landid) {

		return registrationRecordsService.getPartyPersonDetailssurrenderlease(landid);
	}
	
	@RequestMapping(value = "/viewer/registration/partydetailssurrenderlease/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public List<LaPartyPerson> getPersonDetailssurrenderlease(@PathVariable Integer landid) {

		return registrationRecordsService.getPartyPersonDetailssurrenderleaseList(landid);
	}

	
	@RequestMapping(value = "/viewer/registration/editpartydetailssurrenderlease/{landid}/{transid}", method = RequestMethod.GET)
	@ResponseBody
	public List<LaPartyPerson> editPartyPersonDetailssurrenderlease(@PathVariable Integer landid, @PathVariable Integer transid) {

		return registrationRecordsService.editPartyPersonDetailssurrenderlease(landid, transid);
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
		List<LaSpatialunitLand> landobj =null;
		landobj =registrationRecordsService.getLaSpatialunitLandDetails(landid);
		if(landobj.size()>0){
		for (LaSpatialunitLand obj:landobj){
		
			LandUseType landusetype = landusetypedao.getLandUseTypeBylandusetypeId(obj.getProposedused());
			obj.setFirstname(landusetype.getLandusetypeEn());
			
		}
		}
		else{
			LandUseType landusetype = landusetypedao.getLandUseTypeBylandusetypeId(landobj.get(0).getProposedused());
			landobj.get(0).setFirstname(landusetype.getLandusetypeEn());
		}
		
		return landobj;
	}
	
	@RequestMapping(value = "/viewer/registration/laMortgage/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public LaMortgage getLaMortgage(@PathVariable Long landid) {

		return laMortgagedao.getMortgageByLandId(landid);
	}
	
	
	
	@RequestMapping(value = "/viewer/registration/editlaMortgage/{landid}/{transid}", method = RequestMethod.GET)
	@ResponseBody
	public LaMortgage editLaMortgage(@PathVariable Long landid,@PathVariable Long transid) {

		return laMortgagedao.getMortgageByLandandTransactionId(landid, transid.intValue());
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

	@RequestMapping(value = "/viewer/registration/savefinalsaledata", method = RequestMethod.POST)
	@ResponseBody
	public String savefinalsaledata(HttpServletRequest request, HttpServletResponse response,Principal principal) 
	{
		Long landId = 0L;
		Long buyerRelationShipId = 0L;
		String username = principal.getName();
		User userObj = userService.findByUniqueName(username);
		
		Long user_id = userObj.getId();
		String sellerpartyids="";
		String buyerpartyids="";
		
		try {
			Long processid = ServletRequestUtils.getRequiredLongParameter(request, "registration_process");			
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");

			Long partyId = 0l;
			List<SocialTenureRelationship> socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipListForSellerByLandId(landId);
			Long sellerPartyId = 0L;
			Long sellertransid = 0L;
			Long buyertransid = 0L;
			 List<SpatialUnitPersonWithInterest> pois = new ArrayList<>();
			 List<SpatialUnitPersonWithInterest> obj = spatialUnitPersonWithInterestDao.findByUsin(landId);
			 
			 if(socialTenureRelationshipSellerDetails.size()>0 && processid == 7)
				{
					sellerPartyId = ServletRequestUtils.getRequiredLongParameter(request, "Owner_Elimanated");
					
					for(SocialTenureRelationship lmpobj: socialTenureRelationshipSellerDetails){
						Long sellerPartyIdfromList = lmpobj.getPartyid();
						Long sellertransidfromList = lmpobj.getLaExtTransactiondetail().getTransactionid().longValue();
						for(SpatialUnitPersonWithInterest poiobj: obj){
							if(poiobj.getTransactionid().intValue()==sellertransidfromList.intValue())
							{
								poiobj.setIsactive(false);
								 pois.add(poiobj);
								 if(editflag==0){
									spatialUnitPersonWithInterestDao.addNextOfKin(pois, landId);
								 }
							}
						}
						if(sellerPartyId==sellerPartyIdfromList){
						if(sellerpartyids.equalsIgnoreCase("")){
							sellerpartyids= sellerPartyId.toString();
						}
						else{
							sellerpartyids= sellerpartyids+","+sellerPartyId.toString();
						}
						if(editflag==0){
						registrationRecordsService.updateSocialTenureRelationshipByPartyId(sellerPartyId,landId);
						}
						}
						else{
							
							if(buyerpartyids.equalsIgnoreCase("")){
								buyerpartyids= sellerPartyId.toString();
							}
							else{
								buyerpartyids= buyerpartyids+","+sellerPartyId.toString();
							}
							
						}
					}
				}
			 
			 
			 if (socialTenureRelationshipSellerDetails.size()>0 && (processid == 2 || processid == 4 || processid == 6)){
			for(SocialTenureRelationship lmpobj: socialTenureRelationshipSellerDetails){
				sellerPartyId = lmpobj.getPartyid();
				sellertransid = lmpobj.getLaExtTransactiondetail().getTransactionid().longValue();
				for(SpatialUnitPersonWithInterest poiobj: obj){
					if(poiobj.getTransactionid()==sellertransid.intValue())
					{
						poiobj.setIsactive(false);
						 pois.add(poiobj);
						 if(editflag==0){
							spatialUnitPersonWithInterestDao.addNextOfKin(pois, landId);
						 }
					}
				}
				
				if(sellerpartyids.equalsIgnoreCase("")){
					sellerpartyids= sellerPartyId.toString();
				}
				else{
					sellerpartyids= sellerpartyids+","+sellerPartyId.toString();
				}
				if(editflag==0){
				registrationRecordsService.updateSocialTenureRelationshipByPartyId(sellerPartyId,landId);
				}
			}
			 }
				
			
			List<SocialTenureRelationship> socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipListByLandIdForBuyer(landId,processid);
				Long buyerPartyId = 0L;
				if (null !=socialTenureRelationshipBuyerDetails && socialTenureRelationshipBuyerDetails.size()>0)
				{
					for(SocialTenureRelationship lmpobjbuyer: socialTenureRelationshipBuyerDetails){
					buyerPartyId = lmpobjbuyer.getPartyid();
					buyertransid = lmpobjbuyer.getLaExtTransactiondetail().getTransactionid().longValue();
					
					if(buyerpartyids.equalsIgnoreCase("")){
						buyerpartyids= buyerPartyId.toString();
					}
					else{
						buyerpartyids= buyerpartyids+","+buyerPartyId.toString();
					}
					if(editflag==0){
					registrationRecordsService.updateSocialTenureRelationshipByPartytypeId(buyerPartyId,landId);
					}
					}
				}		
			
			try
			{
				
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{
				if(null !=socialTenureRelationshipBuyerDetails && editflag==0){
				LaExtTransactionHistory latranshist = new LaExtTransactionHistory();		
				latranshist.setOldownerid(sellerpartyids);
				latranshist.setNewownerid(buyerpartyids);
				latranshist.setLandid(landId);
				latranshist.setIsactive(true);		
				latranshist.setTransactionid(socialTenureRelationshipBuyerDetails.get(0).getLaExtTransactiondetail().getTransactionid());
				latranshist.setCreatedby(user_id.intValue());
				latranshist.setCreateddate(new Date());
				latranshist = registrationRecordsService.saveTransactionHistory(latranshist);
				}
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				List<SocialTenureRelationship> socialTenureRelationshipSize = registrationRecordsService.getSocialTenureRelationshipListForSellerByLandId(landId);
				

				if(null!=socialTenureRelationshipSize){
					if(socialTenureRelationshipSize.size()==1){
						laExtRegistrationLandShareTypeservice.updateRegistrationSharetype(6L, landId);
						
					
					}else if(socialTenureRelationshipSize.size()==2){
						laExtRegistrationLandShareTypeservice.updateRegistrationSharetype(7L, landId);
						
					}else if(socialTenureRelationshipSize.size()>2){
						laExtRegistrationLandShareTypeservice.updateRegistrationSharetype(8L, landId);
						
					}
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
			
			return "Success";
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		return null;
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
	
	
	@RequestMapping(value = "/viewer/registration/savebuyerdata", method = RequestMethod.POST)
	@ResponseBody
	public String saveBuyerdata(HttpServletRequest request, HttpServletResponse response,Principal principal) {
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
			
			/*String poiFirstName = ServletRequestUtils.getRequiredStringParameter(request, "poi_firstname_r_sale1");
			String poiMiddleName = ServletRequestUtils.getRequiredStringParameter(request, "poi_middlename_r_sale1");
			String poiLastName = ServletRequestUtils.getRequiredStringParameter(request, "poi_lastname_r_sale1");
			int poiGenderId = ServletRequestUtils.getRequiredIntParameter(request, "poi_sale_gender_buyer");
			int poiRelationid = ServletRequestUtils.getRequiredIntParameter(request, "poi_relation_sale1");
			String poiDOB = ServletRequestUtils.getRequiredStringParameter(request, "poi_date_Of_birth_sale1");
			
			*/
			

			int personid = ServletRequestUtils.getRequiredIntParameter(request, "personid");
			int buyerIdTypeid = ServletRequestUtils.getRequiredIntParameter(request, "sale_idtype_buyer");
			String buyerId = ServletRequestUtils.getRequiredStringParameter(request, "id_r1");
			String buyerContact_No = ServletRequestUtils.getRequiredStringParameter(request, "contact_no1");
			int buyerGenderId = ServletRequestUtils.getRequiredIntParameter(request, "sale_gender_buyer");
			String buyerAddress = ServletRequestUtils.getRequiredStringParameter(request, "address_sale1");
			String buyerDOBstr = ServletRequestUtils.getRequiredStringParameter(request, "date_Of_birth_sale1");
			int buyerMaritalStatusId = ServletRequestUtils.getRequiredIntParameter(request, "sale_marital_buyer");
			String buyerRemarks = ServletRequestUtils.getRequiredStringParameter(request, "remrks_sale");
			Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
			Long processid = ServletRequestUtils.getRequiredLongParameter(request, "registration_process");
			
			if(processid == 6 && editflag==0)
			{
				
				buyerRelationShipId = ServletRequestUtils.getRequiredLongParameter(request, "sale_relation_buyer");
			}		

			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			Status status = registrationRecordsService.getStatusById(2);

			PersonType personType = registrationRecordsService.getPersonTypeById(11);

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
			
			SocialTenureRelationship socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByLandIdForBuyer(landId,processid);
			Long buyerPartyId =((Integer) personid).longValue();
			/*if (socialTenureRelationshipBuyerDetails != null)
			{
				buyerPartyId = socialTenureRelationshipBuyerDetails.getPartyid();
			}*/
				
			if(buyerPartyId ==  0L)
			{
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
				naturalPerson.setOwnertype(2);
				if(null != socialTenureRelationshipBuyerDetails && (processid==4 || processid==2)){
					naturalPerson.setOwnertype(2);
					
				}else{
					naturalPerson.setOwnertype(1);
				}
				
				if(processid == 6 && editflag==0)
				{
					RelationshipType obj = new RelationshipType();
					obj.setRelationshiptypeid(buyerRelationShipId);
					naturalPerson.setLaPartygroupRelationshiptype(obj);
					naturalPerson.setOwnertype(1);
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
				
				Long partyId = 0l;
				SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
				Long sellerPartyId = 0L;
				if (socialTenureRelationshipSellerDetails != null)
					sellerPartyId = socialTenureRelationshipSellerDetails.getPartyid();
				else
					return null;			
				
				try
				{
					if(processid == 7 && editflag==0)
					{
						sellerPartyId = ServletRequestUtils.getRequiredLongParameter(request, "Owner_Elimanated");
						naturalPerson.setOwnertype(2);
					}
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					
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
				
					if(null!=socialTenureRelationshipBuyerDetails){
							LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
	
								laExtTransactiondetail.setTransactionid(socialTenureRelationshipBuyerDetails.getLaExtTransactiondetail().getTransactionid());
								laExtTransactiondetail.setCreatedby(user_id.intValue());
								laExtTransactiondetail.setCreateddate(new Date());
								laExtTransactiondetail.setIsactive(true);
								laExtTransactiondetail.setLaExtApplicationstatus(status);
								laExtTransactiondetail.setModuletransid(partyId.intValue());
								laExtTransactiondetail.setRemarks(buyerRemarks);			
								laExtTransactiondetail.setProcessid(processid);
									socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetail);

							}else{
								LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
								laExtTransactiondetail.setCreatedby(user_id.intValue());
								laExtTransactiondetail.setCreateddate(new Date());
								laExtTransactiondetail.setIsactive(true);
								laExtTransactiondetail.setLaExtApplicationstatus(status);
								laExtTransactiondetail.setModuletransid(partyId.intValue());
								laExtTransactiondetail.setRemarks(buyerRemarks);			
								laExtTransactiondetail.setProcessid(processid);
				
								socialTenureRelationship.setLaExtTransactiondetail(laExtTransactiondetail);
}

				try {
					
					socialTenureRelationship = registrationRecordsService.saveSocialTenureRelationship(socialTenureRelationship);
				// 	registrationRecordsService.updateSocialTenureRelationshipByPartyId(sellerPartyId,landId);
					
					 /*List<SpatialUnitPersonWithInterest> pois = new ArrayList<>();
					 SpatialUnitPersonWithInterest poi = new SpatialUnitPersonWithInterest();
	                   
	                        poi.setDob(dateformat.parse(poiDOB));
	                    
	                 
	                        poi.setRelation(poiRelationid);
	                    
	                   
	                        poi.setGender(poiGenderId);
	                    
	                  
	                    
	                   
	                    poi.setFirstName(poiFirstName);
	                    
	                   
	                    poi.setMiddleName(poiMiddleName);
	                    
	                  
	                    poi.setLastName(poiLastName);
	                    poi.setTransactionid(socialTenureRelationship.getLaExtTransactiondetail().getTransactionid());
	                    
	                    poi.setCreatedby(user_id.intValue());
	                    poi.setCreateddate(new Date());
	                    poi.setLandid(landId);
//	                    poi.setId(propPoi.getId());
	                    poi.setIsactive(true);
	                    pois.add(poi);
	                

	                if (pois.size() > 0) {
	                    spatialUnitPersonWithInterestDao.addNextOfKin(pois, landId);
	                }*/
					
					
					
				} catch (Exception er) {
					er.printStackTrace();
				}
				return socialTenureRelationship.getLaExtTransactiondetail().getTransactionid()+"";
			}
			
			else
			{
				//NaturalPerson naturalPerson = landRecordsService.naturalPersonById(buyerPartyId).get(0);
				NaturalPerson naturalPerson = (NaturalPerson) laPartyDao.getPartyIdByID(buyerPartyId);
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
				
				if(processid == 6 && editflag==0)
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
				
				Long partyId = 0l;
				SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
				Long sellerPartyId = 0L;
				if (socialTenureRelationshipSellerDetails != null)
					sellerPartyId = socialTenureRelationshipSellerDetails.getPartyid();
				else
					return null;			
				
				try
				{
					if(processid == 7 && editflag==0)
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
					
					naturalPerson.setLaPartygroupPersontype(personType);
					NaturalPerson naturalPerson2 = registrationRecordsService.saveNaturalPerson(naturalPerson);
					
//					Integer buyertransid = socialTenureRelationshipBuyerDetails.getLaExtTransactiondetail().getTransactionid();
			/*		 List<SpatialUnitPersonWithInterest> pois = new ArrayList<>(); 
					List<SpatialUnitPersonWithInterest>  poiobj = spatialUnitPersonWithInterestDao.findByUsinandTransid(landId.longValue(), buyertransid.longValue());
					 if(poiobj.size() != 0){
						 for (SpatialUnitPersonWithInterest obj: poiobj){
						 obj.setDob(dateformat.parse(poiDOB));
		                    
		                 
						 obj.setRelation(poiRelationid);
	                    
	                   
						 obj.setGender(poiGenderId);
	                    
	                  
	                    
	                   
						 obj.setFirstName(poiFirstName);
	                    
	                   
						 obj.setMiddleName(poiMiddleName);
	                    
	                  
						 obj.setLastName(poiLastName);
						 pois.add(obj);
						 if (pois.size() > 0) {
			                    spatialUnitPersonWithInterestDao.addNextOfKin(pois, landId);
			                }
						 }
					 }*/
					partyId = naturalPerson2.getPartyid();
				} catch (Exception er) {
					er.printStackTrace();
					return null;
				}
			}
					
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value = "/viewer/registration/saveLeaseeDetails", method = RequestMethod.POST)
	@ResponseBody
	public String saveLeaseeDetails(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			LaLease leaseeobj = null;
			NaturalPerson naturalPerson = null;
			Integer transactionId=0;
			Integer leaseepersonid=0;
			
			List<LaLease> leaseeobjList=null;
			LaExtTransactiondetail laExtTransactiondetail  = null;
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
			Integer personId = ServletRequestUtils.getRequiredIntParameter(request, "leaseeperson");

			String remrks_lease = ServletRequestUtils.getRequiredStringParameter(request,"remrks_lease");
			
			
			Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
			if(editflag==1){
				 leaseepersonid = ServletRequestUtils.getRequiredIntParameter(request, "leaseeperson");
				 transactionId = ServletRequestUtils.getRequiredIntParameter(request, "transactionId");
				}

			
//			int no_Of_month_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_month_Lease");
//			Double lease_Amount = ServletRequestUtils.getRequiredDoubleParameter(request,"lease_Amount");
			//int no_Of_years_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_years_Lease");
			
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
//			Status status = registrationRecordsService.getStatusById(2);
			Status status = registrationRecordsService.getStatusById(1);

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
//			int year = no_Of_month_Lease/12;
//			no_Of_month_Lease = no_Of_month_Lease%12;
			
			Long user_id = userObj.getId();
				
			MaritalStatus maritalStatus = registrationRecordsService.getMaritalStatusByID(martialId);
			IdType idType = registrationRecordsService.getIDTypeDetailsByID(id_type);
			if(editflag==1){
			 leaseeobjList = laLeaseDao.getleaseeListByLandandPersonId(landId,leaseepersonid.longValue());
			}
			else if(editflag==0 && personId==0){
				 leaseeobjList = laLeaseDao.getleaseeListByLandId(landId);
			}
			 
			if(personId!=0){
				 leaseeobjList = laLeaseDao.getleaseeListByLandandPersonId(landId,personId.longValue());
			}
//			
			
				if(leaseeobjList.size() > 0 && leaseepersonid != 0){
					leaseeobj= leaseeobjList.get(0);
//					  naturalPerson = (NaturalPerson) laPartyDao.getPartyIdByID(leaseeobj.getPersonid());
					  naturalPerson = (NaturalPerson) laPartyDao.getPartyIdByID(leaseepersonid.longValue());

					 
					 
					    naturalPerson.setContactno(contact_no);
						naturalPerson.setDateofbirth(dateOfBirth);
						naturalPerson.setFirstname(firstName);
						naturalPerson.setMiddlename(middlename);
						naturalPerson.setLastname(lastname);
						naturalPerson.setGenderid(genderId);
						naturalPerson.setLaPartygroupMaritalstatus(maritalStatus);
						naturalPerson.setAddress(address);
						naturalPerson.setLaPartygroupIdentitytype(idType);
						naturalPerson.setIdentityno(id);
						
						try {
							
							naturalPerson = registrationRecordsService.saveNaturalPerson(naturalPerson);
//							NaturalPerson naturalPerson2 = registrationRecordsService.saveNaturalPerson(naturalPerson);
//							partyId = naturalPerson2.getPartyid();
						} catch (Exception er) {
							er.printStackTrace();
							return null;
						}
					
						laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobj.getLeaseid().longValue());
				}
				else if(leaseeobjList.size() > 0 && personId != 0){
					leaseeobj= leaseeobjList.get(0);
//					  naturalPerson = (NaturalPerson) laPartyDao.getPartyIdByID(leaseeobj.getPersonid());
					  naturalPerson = (NaturalPerson) laPartyDao.getPartyIdByID(personId.longValue());

					 
					 
					    naturalPerson.setContactno(contact_no);
						naturalPerson.setDateofbirth(dateOfBirth);
						naturalPerson.setFirstname(firstName);
						naturalPerson.setMiddlename(middlename);
						naturalPerson.setLastname(lastname);
						naturalPerson.setGenderid(genderId);
						naturalPerson.setLaPartygroupMaritalstatus(maritalStatus);
						naturalPerson.setAddress(address);
						naturalPerson.setLaPartygroupIdentitytype(idType);
						naturalPerson.setIdentityno(id);
						
						try {
							
							naturalPerson = registrationRecordsService.saveNaturalPerson(naturalPerson);
//							NaturalPerson naturalPerson2 = registrationRecordsService.saveNaturalPerson(naturalPerson);
//							partyId = naturalPerson2.getPartyid();
						} catch (Exception er) {
							er.printStackTrace();
							return null;
						}
					
						laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobj.getLeaseid().longValue());
				}
			
				else if(leaseeobjList.size() >= 0 && personId==0){
			
			 naturalPerson = new NaturalPerson();
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
			
			
			
//			La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
			
			 leaseeobj = new LaLease();
			leaseeobj.setCreatedby(user_id.intValue());
			leaseeobj.setCreateddate(new Date());
			leaseeobj.setIsactive(true);
//			leaseeobj.setLa_Month(laMonth);
//			leaseeobj.setLeaseamount(lease_Amount);
//			leaseeobj.setLeaseyear(year);//no_Of_years_Lease
			leaseeobj.setPersonid(naturalPerson.getPartyid());
			leaseeobj.setLandid(landId);
			leaseeobj.setOwnerid(ownerid);
			leaseeobj = registrationRecordsService.saveLease(leaseeobj);

//			LaExtFinancialagency laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
			
			 laExtTransactiondetail = new LaExtTransactiondetail();
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());//new Timestamp(time)
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);
			
			laExtTransactiondetail.setRemarks(remrks_lease);
			laExtTransactiondetail.setProcessid(1L);// process Id 1 for Lease
			
			
			
			laExtTransactiondetail.setModuletransid(leaseeobj.getLeaseid());
			
			laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
		
				}
if(null!=laExtTransactiondetail){
			return laExtTransactiondetail.getTransactionid().toString();
}else{
	return "sucess";
}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}
	
	@RequestMapping(value = "/viewer/registration/savesurrenderleasedata", method = RequestMethod.POST)
	@ResponseBody
	public String saveSurrenderLeasedata(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		LaExtTransactiondetail laExtTransactiondetail =null;
		LaSurrenderLease laLease =null;
		Integer transactionId=0;
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
			String Start_date_Lease = ServletRequestUtils.getRequiredStringParameter(request, "Start_date_Lease");
			String End_date_Lease = ServletRequestUtils.getRequiredStringParameter(request, "End_date_Lease");
			String surrenderreason = ServletRequestUtils.getRequiredStringParameter(request, "surrender_reason");
			Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
			if(editflag==1){
			 transactionId = ServletRequestUtils.getRequiredIntParameter(request, "transactionId");
			}

			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			try 
			{
				startDate = dateformat.parse(Start_date_Lease);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			Date endDate = null;
			try 
			{
				endDate = dateformat.parse(End_date_Lease);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			Status status = registrationRecordsService.getStatusById(1);

			PersonType personType = registrationRecordsService.getPersonTypeById(1);

			//DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			
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
			
				try{
					if(editflag==0){
			 laLease = laSurrenderLeaseDao.getSurrenderleaseByLandandProcessId(landId, 5L);
					}
					else if(editflag==1){
			 
			 laLease = laSurrenderLeaseDao.getSurrenderleaseByLandandTransId(landId,transactionId);
					}
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
				La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
				
				
		if(null != laLease){
			laLease.setLa_Month(laMonth);
			laLease.setLeaseamount(lease_Amount);
			laLease.setLeaseyear(year);//no_Of_years_Lease
			laLease.setLeasestartdate(startDate);
			laLease.setLeaseenddate(endDate);
			laLease.setSurrenderreason(surrenderreason);
			laLease = registrationRecordsService.savesurrenderLease(laLease);
			laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(laLease.getLeaseid().longValue());
		}else{
			
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

			//La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
			
			LaPartyPerson lapartydetail= getPartyPersonDetailssurrenderlease(Integer.parseInt(landId.toString()));
			
			 laLease = new LaSurrenderLease();
			laLease.setCreatedby(user_id.intValue());
			laLease.setCreateddate(new Date());
			laLease.setIsactive(true);
			laLease.setLa_Month(laMonth);
			laLease.setLeaseamount(lease_Amount);
			laLease.setLeaseyear(year);//no_Of_years_Lease
			laLease.setPersonid(lapartydetail.getPersonid());
			laLease.setLandid(landId);
			laLease.setOwnerid(ownerid);;
			laLease.setLeasestartdate(startDate);
			laLease.setLeaseenddate(endDate);
			 laExtTransactiondetail = new LaExtTransactiondetail();
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());//new Timestamp(time)
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);
			
			laExtTransactiondetail.setRemarks(remrks_lease);
			laExtTransactiondetail.setProcessid(5L);// process Id 1 for Lease
//			registrationRecordsService.disablelease(laLease.getPersonid(),laLease.getLandid());
			laLease = registrationRecordsService.savesurrenderLease(laLease);
			
			laExtTransactiondetail.setModuletransid(laLease.getLeaseid());
			
			laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
		}

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
			LaMortgage laMortgage = null;
			LaExtFinancialagency laExtFinancialagency = null;
			LaExtTransactiondetail laExtTransactiondetail = null;
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Integer transactionId=0;
			Long user_id = userObj.getId();
			
			int financial_AgenciesID = ServletRequestUtils.getRequiredIntParameter(request, "mortgage_Financial_Agencies");
			String mortgage_from = ServletRequestUtils.getRequiredStringParameter(request, "mortgage_from");
			String mortgage_to = ServletRequestUtils.getRequiredStringParameter(request, "mortgage_to");
			Double amount_mortgage = ServletRequestUtils.getRequiredDoubleParameter(request, "amount_mortgage");
			String remrks_mortgage = ServletRequestUtils.getRequiredStringParameter(request, "remrks_mortgage");
			Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
			if(editflag==1){
			 transactionId = ServletRequestUtils.getRequiredIntParameter(request, "transactionId");
			}

			
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			
			Status status = registrationRecordsService.getStatusById(1);

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
			if(editflag==0){
			 laMortgage= laMortgagedao.getMortgageByLandId(landId);
			}
			else if(editflag==1){
				laMortgage= laMortgagedao.getMortgageByLandandTransactionId(landId,transactionId);
				 
			}
			 
			 
			 if(null != laMortgage && editflag==0){
				 laMortgage.setMortgagefrom(mortgage_fromDate);
				 laMortgage.setMortgageto(mortgage_toDate);
				 laMortgage.setMortgageamount(amount_mortgage);
				  laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
				 laMortgage.setLaExtFinancialagency(laExtFinancialagency);
				 laMortgage = registrationRecordsService.saveMortgage(laMortgage);
				 laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(laMortgage.getMortgageid().longValue());
				 laExtTransactiondetail.setLaExtApplicationstatus(status);
				 laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
				 
			 }
			 else if(null != laMortgage && editflag==1){

				 laMortgage.setMortgagefrom(mortgage_fromDate);
				 laMortgage.setMortgageto(mortgage_toDate);
				 laMortgage.setMortgageamount(amount_mortgage);
				  laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
				 laMortgage.setLaExtFinancialagency(laExtFinancialagency);
				 laMortgage = registrationRecordsService.saveMortgage(laMortgage);
				 /*laExtTransactiondetail = transactionDao.getpoiByLeaseeid(laMortgage.getMortgageid().longValue());
				 laExtTransactiondetail.setLaExtApplicationstatus(status);
				 laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);*/
				 
			 
				 
			 }
			 else if(null == laMortgage){
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
			
			 laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
			
			 laExtTransactiondetail = new LaExtTransactiondetail();
			laExtTransactiondetail.setCreatedby(user_id.intValue());
			laExtTransactiondetail.setCreateddate(new Date());//new Timestamp(time)
			laExtTransactiondetail.setIsactive(true);
			laExtTransactiondetail.setLaExtApplicationstatus(status);
			
			laExtTransactiondetail.setRemarks(remrks_mortgage);
			laExtTransactiondetail.setProcessid(3L);// process Id 3 for Mortgage
			
			 laMortgage= new LaMortgage();
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
			 }
			 if(null!=laExtTransactiondetail){

			return laExtTransactiondetail.getTransactionid().toString();
			 }
			 else{
				 return "Success";
			 }
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
		long _transactionid =0l;
		
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

		 if(!transactionid.equals("")){
			 _transactionid = Long.parseLong(transactionid.trim());
		 }else{
			 _transactionid =0l;
		 }
		 
		try {
			return registrationRecordsService.search( _transactionid,startfrom,projectId+"",communeId+"",parce_id);
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
		long _transactionid =0l;
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
		
		 if(!transactionid.equals("")){
			 _transactionid = Long.parseLong(transactionid.trim());
		 }else{
			 _transactionid =0l;
		 }
		 
		try {
			return registrationRecordsService.searchCount( _transactionid,startfrom,projectId+"",communeId+"",parce_id);
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
	
	
	
	
	
	@RequestMapping(value = "/viewer/registration/saveleasePersondata", method = RequestMethod.POST)
	@ResponseBody
	public String saveLeasePersonData(HttpServletRequest request, HttpServletResponse response, Principal principal) {

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
	
	
	@RequestMapping(value = "/viewer/registration/savedocumentInfo/Registration", method = RequestMethod.POST)
	@ResponseBody
	public Integer savedocumentInfoRegistration(HttpServletRequest request, HttpServletResponse response,Principal principal) {
		
		SocialTenureRelationship socialTenureRelationshipBuyerDetails = null;
		 List<LaLease> leaseeobjList = null;
		 LaExtTransactiondetail laExtTransactiondetail =null;
		 LaParty laParty=null;
		 LaMortgage laMortgage=null;
		 LaSurrenderMortgage lasurrenderMortgage=null;
		 LaSurrenderLease lasurenderleaseobj = null;
		 Integer transactionId = 0;
		Integer persontypeId =0;

		try {
			String landId = "";
			String doc_name ="";
			String doc_desc ="";
			String doc_date = "";
			Integer processid = 0;
			Long buyertransid =0L;
			
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Long user_id = userObj.getId();
			landId = ServletRequestUtils.getRequiredStringParameter(request, "landidhide");
			processid = ServletRequestUtils.getRequiredIntParameter(request, "processidhide");
			Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
			if(editflag==1){
			transactionId = ServletRequestUtils.getRequiredIntParameter(request, "transactionId");
			}

			
			if(processid == 1){
				persontypeId=1;
			 doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_Lease");
			 doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_Lease");
			 doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_Lease");
			 if(editflag==0){
			  leaseeobjList = laLeaseDao.getleaseeListByLandId(Long.parseLong(landId));
			 }
			 else if (editflag==1){
				 laExtTransactiondetail = transactionDao.getLaExtTransactiondetail(transactionId);
				 leaseeobjList.add(laLeaseDao.getLeaseById(laExtTransactiondetail.getModuletransid()));
			 }
				
			}
			else if(processid == 2){
				persontypeId=11;
				 doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_sale");
				 doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_sale");
				 doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_sale");
				 if(editflag==0){
				 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByLandIdandTypeId(Long.parseLong(landId),processid.longValue(),persontypeId);
				 }
				 else if(editflag==1){
					 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByTransactionId(transactionId.longValue());
				 }
				}
			else if(processid == 3){
				persontypeId=1;
				 doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_mortgage");
				 doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_mortgage");
				 doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_mortgage");
				 if(editflag==0){
					 laMortgage= laMortgagedao.getMortgageByLandId(Long.parseLong(landId));
					 }
					 else if (editflag==1){
						 laExtTransactiondetail = transactionDao.getLaExtTransactiondetail(transactionId);
						 laMortgage= laMortgagedao.getMortgageByMotgageId(laExtTransactiondetail.getModuletransid());
					 }
				 
				}
			else if(processid == 4){
				persontypeId=11;
				 doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_sale");
				 doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_sale");
				 doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_sale");
				 if(editflag==0){
					 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByLandIdandTypeId(Long.parseLong(landId),processid.longValue(),persontypeId);
					 }
					 else if(editflag==1){
						 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByTransactionId(transactionId.longValue());
					 }
				}
			else if(processid == 5){
				persontypeId=1;
				 doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_Lease");
				 doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_Lease");
				 doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_Lease");
				 if(editflag==0){
				 lasurenderleaseobj= laSurrenderLeaseDao.getSurrenderleaseByLandandProcessId(Long.parseLong(landId), processid.longValue());
				 		}
				 else if(editflag==1){
					 laExtTransactiondetail = transactionDao.getLaExtTransactiondetail(transactionId);
					 lasurenderleaseobj= laSurrenderLeaseDao.getObjbySurrenderLeaseeId(laExtTransactiondetail.getModuletransid());
				 }
					}
			else if(processid == 6){
				persontypeId=11;
				 doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_sale");
				 doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_sale");
				 doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_sale");
				 if(editflag==0){
					 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByLandIdandTypeId(Long.parseLong(landId),processid.longValue(),persontypeId);
					 }
					 else if(editflag==1){
						 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByTransactionId(transactionId.longValue());
					 }
				}
			else if(processid == 7){
				persontypeId=11;
				 doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_sale");
				 doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_sale");
				 doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_sale");
				 if(editflag==0){
					 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByLandIdandTypeId(Long.parseLong(landId),processid.longValue(),persontypeId);
					 }
					 else if(editflag==1){
						 socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByTransactionId(transactionId.longValue());
					 }
				}
			else if(processid == 9){
				persontypeId=1;
				doc_name = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_mortgage");
				doc_desc = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_mortgage");
				doc_date = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_mortgage");
				if(editflag==0){
					lasurrenderMortgage= lasurrenderMortgagedao.getMortgageByLandIdandprocessId(Long.parseLong(landId),processid.longValue());
				}
				else if (editflag==1){
					 laExtTransactiondetail = transactionDao.getLaExtTransactiondetail(transactionId);
						lasurrenderMortgage= lasurrenderMortgagedao.getMortgageBysurMortgageId(laExtTransactiondetail.getModuletransid());

				}
			}
			
			
			String outDirPath = FileUtils.getFielsFolder(request)+ "resources" + File.separator + "documents"+ File.separator + "Registry" + File.separator+ "webupload";
			File outDir = new File(outDirPath);
			boolean exists = outDir.exists();
			Date uploadDate = null;
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			if (!exists) {
				(new File(outDirPath)).mkdirs();
			}
			
			try {
				uploadDate = dateformat.parse(doc_date);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			SourceDocument sourceDocument = new SourceDocument();
			sourceDocument.setCreatedby(user_id.intValue());
			sourceDocument.setCreateddate(new Date());
			sourceDocument.setDocumentlocation("");
			sourceDocument.setDocumentname(doc_name);
			sourceDocument.setIsactive(true);
			sourceDocument.setRemarks(doc_desc);
			sourceDocument.setRecordationdate(uploadDate);
			sourceDocument.setDocumenttypeid(1);
			try {
//				SocialTenureRelationship socialTenureRelationship = registrationRecordsService.getSocialTenureRelationshipByLandId(Long.parseLong(landId));
				Long buyerPartyId = 0L;
				if (socialTenureRelationshipBuyerDetails != null)
				{
					buyerPartyId = socialTenureRelationshipBuyerDetails.getPartyid();
					buyertransid = socialTenureRelationshipBuyerDetails.getLaExtTransactiondetail().getTransactionid().longValue();
					Long partyId = buyerPartyId;
					 laParty = registrationRecordsService.getLaPartyById(partyId);
				    laExtTransactiondetail = registrationRecordsService.getLaExtTransactiondetail( buyertransid.intValue());
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(Long.parseLong(landId));
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);
				}	
				else if(null!=leaseeobjList && leaseeobjList.size() >0){
					laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobjList.get(0).getLeaseid().longValue());
					laParty = registrationRecordsService.getLaPartyById(leaseeobjList.get(0).getPersonid());
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(Long.parseLong(landId));
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);
				}
				else if(null != laMortgage){
					laExtTransactiondetail = transactionDao.getLaExtTransactionforMortgage(laMortgage.getMortgageid().longValue());
					laParty = registrationRecordsService.getLaPartyById(laMortgage.getOwnerid());
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(Long.parseLong(landId));
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);
				}
				else if(null!= lasurenderleaseobj){
					laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeidForSurrenderLease(lasurenderleaseobj.getLeaseid().longValue());
					
					laParty = registrationRecordsService.getLaPartyById(lasurenderleaseobj.getOwnerid());
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(Long.parseLong(landId));
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);
				}
				
				else if(null != lasurrenderMortgage){
					laExtTransactiondetail = transactionDao.getLaExtTransactionforSurrenderMortgage(lasurrenderMortgage.getMortgageid().longValue());
					laParty = registrationRecordsService.getLaPartyById(lasurrenderMortgage.getOwnerid());
					sourceDocument.setLaParty(laParty);
					sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
					sourceDocument.setLaSpatialunitLand(Long.parseLong(landId));
					sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);
				}

			} catch (Exception e) {
				logger.error(e);
				return null;
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return persontypeId;
		
	}
	
	@RequestMapping(value = "/viewer/registration/savedocumentInfo/split", method = RequestMethod.POST)
	@ResponseBody
	public Boolean savedocumentInfoSplits(HttpServletRequest request, HttpServletResponse response,Principal principal) {
		
		try {
			String landId = "";
			LaExtTransactiondetail laExtTransactiondetail = new LaExtTransactiondetail();
			String username = principal.getName();
			User userObj = userService.findByUniqueName(username);
			Long user_id = userObj.getId();
			landId = ServletRequestUtils.getRequiredStringParameter(request, "split_selectedlandid");
			String doc_name_sale = ServletRequestUtils.getRequiredStringParameter(request, "doc_name_split");
			String doc_desc_sale = ServletRequestUtils.getRequiredStringParameter(request, "doc_desc_split");
			String doc_date_sale = ServletRequestUtils.getRequiredStringParameter(request, "doc_date_split");
			
			
			String outDirPath = FileUtils.getFielsFolder(request)+ "resources" + File.separator + "documents"+ File.separator + "Registry" + File.separator+ "webupload";
			File outDir = new File(outDirPath);
			boolean exists = outDir.exists();
			Date uploadDate = null;
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			if (!exists) {
				(new File(outDirPath)).mkdirs();
			}
			
			try {
				uploadDate = dateformat.parse(doc_date_sale);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			SourceDocument sourceDocument = new SourceDocument();
			sourceDocument.setCreatedby(user_id.intValue());
			sourceDocument.setCreateddate(new Date());
			sourceDocument.setDocumentlocation("");
			sourceDocument.setDocumentname(doc_name_sale);
			sourceDocument.setIsactive(true);
			sourceDocument.setRemarks(doc_desc_sale);
			sourceDocument.setRecordationdate(uploadDate);
			sourceDocument.setDocumenttypeid(1);
			try {
				SocialTenureRelationship socialTenureRelationship = registrationRecordsService.getSocialTenureRelationshipByLandId(Long.parseLong(landId));
				Long partyId = socialTenureRelationship.getPartyid();
				LaParty laParty = registrationRecordsService.getLaPartyById(partyId);
				List<SourceDocument>lstsourceDocument= sourceDocumentDAO.findSourceDocumentByLandIdAndProessId(Long.parseLong(landId), 8l);
				if(lstsourceDocument.size()>0){
					
					 laExtTransactiondetail = registrationRecordsService.getLaExtTransactiondetail(lstsourceDocument.get(0).getLaExtTransactiondetail().getTransactionid());
				}else
				{
					laExtTransactiondetail.setCreatedby(user_id.intValue());
					laExtTransactiondetail.setCreateddate(new Date());
					laExtTransactiondetail.setIsactive(true);
					laExtTransactiondetail.setLaExtApplicationstatus(registrationRecordsService.getStatusById(1));
					laExtTransactiondetail.setModuletransid(partyId.intValue());
					laExtTransactiondetail.setRemarks("");			
					laExtTransactiondetail.setProcessid(8l);
				}
				
				sourceDocument.setLaParty(laParty);
				sourceDocument.setLaExtTransactiondetail(laExtTransactiondetail);
				sourceDocument.setLaSpatialunitLand(Long.parseLong(landId));
				sourceDocument = registrationRecordsService.saveUploadedDocuments(sourceDocument);

			} catch (Exception e) {
				logger.error(e);
				return false;
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
		
	}
	

	@RequestMapping(value = "/viewer/registryrecords/getprocessDocument/{landid}/{persontypeId}/{processId}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> getprocessDocument(@PathVariable Long landid,@PathVariable Integer persontypeId,@PathVariable Long processId) {
		List<SourceDocument>lstSourceDocument = new ArrayList<SourceDocument>();
		
		try{

			SocialTenureRelationship socialTenureRelationshipBuyerDetails = registrationRecordsService.getSocialTenureRelationshipByLandIdandTypeId(landid,processId,persontypeId); //landid,2l,11
			 List<LaLease> leaseeobjList = laLeaseDao.getleaseobjbylandandprocessidList(landid,processId);
			 LaMortgage laMortgageobj = laMortgagedao.getMortgageByLandIdandprocessId(landid,processId);
			 LaSurrenderMortgage lasurrenderMortgageobj = lasurrenderMortgagedao.getMortgageByLandIdandprocessId(landid,processId);
			LaSurrenderLease lasurenderleaseobj= laSurrenderLeaseDao.getSurrenderleaseByLandandProcessId(landid, processId);
			Integer transId = 0;
			if(processId == 2 || processId == 4 || processId == 6 || processId == 7)
			{
				if (socialTenureRelationshipBuyerDetails != null)
				{
					transId = socialTenureRelationshipBuyerDetails.getLaExtTransactiondetail().getTransactionid();
					
					lstSourceDocument=	sourceDocumentDAO.findSourceDocumentByLandIdandTransactionid(landid, transId);
					return lstSourceDocument;
				}
			}
			
			if(processId == 1)
			{
				if(null != leaseeobjList){
					for(LaLease obj:leaseeobjList){
					LaExtTransactiondetail laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(obj.getLeaseid().longValue());
					if(null!=laExtTransactiondetail){
						transId = laExtTransactiondetail.getTransactionid();
					lstSourceDocument=	sourceDocumentDAO.findSourceDocumentByLandIdandTransactionid(landid, transId);
					if(lstSourceDocument.size()>0){
						return lstSourceDocument;
					}
					             }
					}
					if(lstSourceDocument.size()==0){
						return null;
					}
					
				}
			}
			
			if(processId == 3)
			{
				if(null != laMortgageobj){
					LaExtTransactiondetail laExtTransactiondetail = transactionDao.getLaExtTransactionforMortgage(laMortgageobj.getMortgageid().longValue());
					transId = laExtTransactiondetail.getTransactionid();
					lstSourceDocument=	sourceDocumentDAO.findSourceDocumentByLandIdandTransactionid(landid, transId);
					return lstSourceDocument;
				}
			}
			
			if(processId == 9)
			{
				if(null != lasurrenderMortgageobj){
					LaExtTransactiondetail laExtTransactiondetail = transactionDao.getLaExtTransactionforSurrenderMortgage(lasurrenderMortgageobj.getMortgageid().longValue());
					transId = laExtTransactiondetail.getTransactionid();
					lstSourceDocument=	sourceDocumentDAO.findSourceDocumentByLandIdandTransactionid(landid, transId);
					return lstSourceDocument;
				}
			}
			
			if(processId == 5)
			{
				if(null !=lasurenderleaseobj){
					LaExtTransactiondetail laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeidForSurrenderLease(lasurenderleaseobj.getLeaseid().longValue());
					transId = laExtTransactiondetail.getTransactionid();
					lstSourceDocument=	sourceDocumentDAO.findSourceDocumentByLandIdandTransactionid(landid, transId);
					return lstSourceDocument;
				}
			}
			
			return null;
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	
	
	@RequestMapping(value = "/viewer/registryrecords/editDocuments/{landid}/{transid}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> editDocument(@PathVariable Long landid,@PathVariable Integer transid) {
		List<SourceDocument>lstSourceDocument = new ArrayList<SourceDocument>();
		
		try{
					
					lstSourceDocument=	sourceDocumentDAO.findSourceDocumentByLandIdandTransactionid(landid, transid);
			
			
			if(lstSourceDocument.size()>0){
				
				return lstSourceDocument;
			}
			else{
				
				return null;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@RequestMapping(value = "/viewer/registryrecords/getsplitDocument/{landid}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> getsplitDocument(@PathVariable Long landid) {
		List<SourceDocument>lstSourceDocument = new ArrayList<SourceDocument>();
		
		try{

			lstSourceDocument= sourceDocumentDAO.findSourceDocumentByLandIdAndProessId(landid, 8l);
			
			if (lstSourceDocument != null)
			{
			
				return lstSourceDocument;
			}else{
				
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	@RequestMapping(value = "/viewer/registration/updateLeaseeDetails", method = RequestMethod.POST)
	@ResponseBody
	public String updateLeaseeDetails(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			LaLease leaseeobj = null;
			LaExtTransactiondetail laExtTransactiondetail  = null;
			List<LaLease> leaseeobjList =null;
			Integer transactionId=0;
			Integer leaseepersonid=0;
			
			int no_Of_month_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_month_Lease");
			Double lease_Amount = ServletRequestUtils.getRequiredDoubleParameter(request,"lease_Amount");
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
			String Start_date_Lease = ServletRequestUtils.getRequiredStringParameter(request, "Start_date_Lease");
			String End_date_Lease = ServletRequestUtils.getRequiredStringParameter(request, "End_date_Lease");
			Integer personId = ServletRequestUtils.getRequiredIntParameter(request, "leaseeperson");
			
			Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
			if(editflag==1){
				 leaseepersonid = ServletRequestUtils.getRequiredIntParameter(request, "leaseeperson");
				transactionId = ServletRequestUtils.getRequiredIntParameter(request, "transactionId");
			}

			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			try 
			{
				startDate = dateformat.parse(Start_date_Lease);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			Date endDate = null;
			try 
			{
				endDate = dateformat.parse(End_date_Lease);

			} catch (ParseException e) {
				e.printStackTrace();
			}

		
			int year = no_Of_month_Lease/12;
			no_Of_month_Lease = no_Of_month_Lease%12;
			
			
			La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
			if(editflag==1){
			 leaseeobjList = laLeaseDao.getleaseeListByLandandPersonId(landId,leaseepersonid.longValue());
			}
			else if(personId !=0){
				leaseeobjList = laLeaseDao.getleaseeListByLandandPersonId(landId,personId.longValue());
			}
			else if(editflag==0 && personId==0 && leaseepersonid==0){
			 leaseeobjList = laLeaseDao.getleaseeListByLandId(landId);
			}
				if(leaseeobjList.size() > 0  && leaseepersonid!=0){
					leaseeobj= leaseeobjList.get(0);
					leaseeobj.setLa_Month(laMonth);
					leaseeobj.setLeaseamount(lease_Amount);
					leaseeobj.setLeaseyear(year);//no_Of_years_Lease
					leaseeobj.setLeasestartdate(startDate);
					leaseeobj.setLeaseenddate(endDate);
					leaseeobj = registrationRecordsService.saveLease(leaseeobj);
					
						laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobj.getLeaseid().longValue());
				}
				else if(leaseeobjList.size() > 0  && personId!=0){
					leaseeobj= leaseeobjList.get(0);
					leaseeobj.setLa_Month(laMonth);
					leaseeobj.setLeaseamount(lease_Amount);
					leaseeobj.setLeaseyear(year);//no_Of_years_Lease
					leaseeobj.setLeasestartdate(startDate);
					leaseeobj.setLeaseenddate(endDate);
					leaseeobj = registrationRecordsService.saveLease(leaseeobj);
					
						laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobj.getLeaseid().longValue());
				}
				else if(leaseeobjList.size() > 0 && personId==0 && leaseepersonid==0)
				{
					for(LaLease leaseeobjall:leaseeobjList){
					leaseeobjall.setLa_Month(laMonth);
					leaseeobjall.setLeaseamount(lease_Amount);
					leaseeobjall.setLeaseyear(year);//no_Of_years_Lease
					leaseeobjall.setLeasestartdate(startDate);
					leaseeobjall.setLeaseenddate(endDate);
					leaseeobjall = registrationRecordsService.saveLease(leaseeobjall);
					}
					
						laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobjList.get(0).getLeaseid().longValue());
					
				}else{
					
					return "success";
				}
				if(null != laExtTransactiondetail){
			return laExtTransactiondetail.getTransactionid().toString();
				}else{
					return "Success";
				}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}
	
	
	@RequestMapping(value = "/viewer/registration/saveleasedata", method = RequestMethod.POST)
	@ResponseBody
	public String saveLeaseData(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			
			LaExtTransactiondetail laExtTransactiondetail  = null;
		
			
//			int no_Of_month_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_month_Lease");
//			Double lease_Amount = ServletRequestUtils.getRequiredDoubleParameter(request,"lease_Amount");
			
			
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");

		
//			int year = no_Of_month_Lease/12;
//			no_Of_month_Lease = no_Of_month_Lease%12;
			
			
//			La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
			List<LaLease> leaseeobjList = laLeaseDao.getleaseeListByLandId(landId);
				if(leaseeobjList.size() > 0){
					
					for(LaLease leaseeobj:leaseeobjList){
					
						laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobj.getLeaseid().longValue());
						Status status = registrationRecordsService.getStatusById(2);
						laExtTransactiondetail.setLaExtApplicationstatus(status);
						laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
					}
				}
				else{
					
				}

			return laExtTransactiondetail.getTransactionid().toString();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}
	
	
	@RequestMapping(value = "/viewer/registration/updateMortgageData", method = RequestMethod.POST)
	@ResponseBody
	public String updateMortgageData(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		try {
			Long landId = 0L;
			LaMortgage laMortgage = null;
			LaExtTransactiondetail laExtTransactiondetail  = null;
		
			
//			int no_Of_month_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_month_Lease");
//			Double lease_Amount = ServletRequestUtils.getRequiredDoubleParameter(request,"lease_Amount");
			
			
			landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");

		
//			int year = no_Of_month_Lease/12;
//			no_Of_month_Lease = no_Of_month_Lease%12;
			
			
//			La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
			laMortgage= laMortgagedao.getMortgageByLandId(landId);
				if(null != laMortgage){
				
					
						laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(laMortgage.getMortgageid().longValue());
						Status status = registrationRecordsService.getStatusById(2);
						laExtTransactiondetail.setLaExtApplicationstatus(status);
						laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
				}
				else{
					
				}

			return laExtTransactiondetail.getTransactionid().toString();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}
	
	
	
	
	
	 @RequestMapping(value = "/viewer/upload/media_sale/", method = RequestMethod.POST)
	   @ResponseBody
	   public String upload(MultipartHttpServletRequest request, HttpServletResponse response, Principal principal) {	
		 SourceDocument doc =null;	
		 try {
				
				String userId[] = null ;
				String project="";
				String username = principal.getName();
				User userObj = userService.findByUniqueName(username);

				Long created_by = userObj.getId();
				Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by

				Iterator<String> file = request.getFileNames();
				
				
				Integer documentId =null;
				documentId= ServletRequestUtils.getRequiredIntParameter(request, "documentId");
				
//				Integer transid =null;
//				transid= ServletRequestUtils.getRequiredIntParameter(request, "transid");
//				
//				Long landid = 0L;
//				landid= ServletRequestUtils.getRequiredLongParameter(request, "landid");
				
				
				byte[] document = null;
				while(file.hasNext()) 
				{
					String fileName = file.next();
					String projName="";
					MultipartFile mpFile = request.getFile(fileName);
					long size = mpFile.getSize();
					String originalFileName = mpFile.getOriginalFilename();
					SourceDocument objDocument = new SourceDocument();
					
					

					if (originalFileName != "") {
						document = mpFile.getBytes();
					}
					 doc = sourceDocumentDAO.findDocumentByDocumentId(documentId.longValue());
			    	if(doc==null){
			    		//String filepath = "/storage/emulated/0/MAST/multimedia/" +originalFileName;
			    		String filepath = FileUtils.getFielsFolder(request) +"/storage/emulated/0/MAST/multimedia/Parcel_Media";
				        filepath =  filepath.replace("\\mast\\..", "");
				        Path path = Paths.get(filepath);
				        File existingdr =new File(filepath);
				        boolean exist = existingdr.exists();
						if (!exist) {
							
							boolean success = (new File(filepath)).mkdirs();
							
						}
						
				        try {
				        	File serverFile = new File(existingdr + File.separator
			                        + originalFileName);
				            
				            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
							uploadfile.write(document);
							uploadfile.flush();
							uploadfile.close();

				        } catch (Exception e) {
				            logger.error(e);
				        }
				        Outputformat outputformat = Outputformatdao.findByName("image"+"/" +FileUtils.getFileExtension(originalFileName));
				        objDocument.setLaExtDocumentformat(outputformat);
				        objDocument.setCreatedby(created_by.intValue());
				        objDocument.setCreateddate(new Date());
				        objDocument.setModifiedby(created_by.intValue());
				        objDocument.setModifieddate(new Date());
				        objDocument.setRecordationdate(new Date());
				        objDocument.setRemarks("");
				        objDocument.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
				        objDocument.setIsactive(true);
				        objDocument.setLaSpatialunitLand(doc.getLaSpatialunitLand().longValue());
				        objDocument.setLaParty(laPartyDao.getPartyListIdByID(doc.getLaParty().getPartyid()).get(0));       
				        objDocument.setLaExtTransactiondetail( transactionDao.getLaExtTransactiondetail(doc.getLaExtTransactiondetail().getTransactionid()));
				        
				        objDocument.setDocumentname(originalFileName);
				        sourceDocumentsDao.saveUploadedDocuments(objDocument);
						

			    	}
			        String filepath = FileUtils.getFielsFolder(request) +"/storage/emulated/0/MAST/multimedia/Parcel_Media";
			        filepath =  filepath.replace("\\mast\\..", "");
			        Path path = Paths.get(filepath);
			        File existingdr =new File(filepath);

			        
			        boolean exist = existingdr.exists();
					if (!exist) {
						
						boolean success = (new File(filepath)).mkdirs();
						
					}
					
					
			        try {
			        	File serverFile = new File(existingdr + File.separator
		                        + originalFileName);
			            
			            FileOutputStream uploadfile = new FileOutputStream(serverFile, false);
						uploadfile.write(document);
						uploadfile.flush();
						uploadfile.close();

			        } catch (Exception e) {
			            logger.error(e);
			        }
			    
			        objDocument= doc;
			        objDocument.setDocumentname(originalFileName);
			        objDocument.setDocumentlocation("/storage/emulated/0/MAST/multimedia/Parcel_Media");
			        sourceDocumentsDao.saveUploadedDocuments(objDocument);
					
					
				 System.out.println("true");
				 return doc.getLaSpatialunitLand().toString();
			} 
				
			}catch (Exception e) {
				logger.error(e);
			}		
			 return doc.getLaSpatialunitLand().toString();
		}
	
	
	 
	  @RequestMapping(value = "/viewer/registration/mediaavail/{documentId}", method = RequestMethod.GET)
	    @ResponseBody
	    public boolean isPersonMultimediaexist(@PathVariable Long documentId, HttpServletRequest request, HttpServletResponse response) {
	    	
	    	SourceDocument doc = sourceDocumentDAO.findDocumentByDocumentId(documentId.longValue());
	    	if(doc==null){
	    		return false;
	    	 }
	       
	        return true;
	    }
	  
	  @RequestMapping(value = "/viewer/registration/download/{documentId}", method = RequestMethod.GET)
	    @ResponseBody
	    public void PersonMultimediaShowSale(@PathVariable Long documentId,  HttpServletRequest request, HttpServletResponse response) {
	    	byte[] data =null;
	    	SourceDocument doc =null;
	    	
	    	doc = sourceDocumentDAO.findDocumentByDocumentId(documentId.longValue());
	    	
	    	 if(doc==null){
	    		 response.setContentLength(data.length);
	    	 }
	        String filepath = FileUtils.getFielsFolder(request) + doc.getDocumentlocation()
	                +"/"+ doc.getDocumentname();
	         filepath =  filepath.replace("\\mast\\..", "");
	        Path path = Paths.get(filepath);
	        try {
	            data = Files.readAllBytes(path);
	            response.setContentLength(data.length);
	            if(FileUtils.getFileExtension(doc.getDocumentname()).equalsIgnoreCase("xlsx")
	            		||FileUtils.getFileExtension(doc.getDocumentname()).equalsIgnoreCase("xls")){
		            response.setContentType("application/vnd.ms-excel");
		            }
	            else if(FileUtils.getFileExtension(doc.getDocumentname()).equalsIgnoreCase("docx")
	            		||FileUtils.getFileExtension(doc.getDocumentname()).equalsIgnoreCase("rtf")
	            		||FileUtils.getFileExtension(doc.getDocumentname()).equalsIgnoreCase("doc")){
		            response.setContentType("application/msword");
		            }
	            else if(FileUtils.getFileExtension(doc.getDocumentname()).equalsIgnoreCase("pdf")){
		            response.setContentType("application/pdf");
	            }
	            OutputStream out = response.getOutputStream();
	            out.write(data);
	            out.flush();
	            out.close();

	        } catch (Exception e) {
	            logger.error(e);
	        }
	    }
	  
	  
	  
	  @RequestMapping(value = "/viewer/delete/media_sale/", method = RequestMethod.POST)
	   @ResponseBody
	   public String delete_sale(HttpServletRequest request, HttpServletResponse response, Principal principal) {	
		  SourceDocument doc = null;
			
		  try {
				
				String userId[] = null ;
				String project="";
				String username = principal.getName();
				User userObj = userService.findByUniqueName(username);

				Long created_by = userObj.getId();
				Long modifiedby = created_by;// As discussed with rajendra sir modified by also same as created by

				
				
				Integer documentId =null;
				documentId= ServletRequestUtils.getRequiredIntParameter(request, "documentId");
				
//				Integer transid =null;
//				transid= ServletRequestUtils.getRequiredIntParameter(request, "transid");
//				
//				Long landid = 0L;
//				landid= ServletRequestUtils.getRequiredLongParameter(request, "landid");
				doc = sourceDocumentDAO.findDocumentByDocumentId(documentId.longValue());
				doc.setIsactive(false);
				
				sourceDocumentsDao.saveUploadedDocuments(doc);
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return doc.getLaSpatialunitLand().toString();
				
	   }
	
	
	  
	  
	  
	  @RequestMapping(value = "/viewer/registration/approvesurrenderleasedata", method = RequestMethod.POST)
		@ResponseBody
		public String approveSurrenderLeasedata(HttpServletRequest request, HttpServletResponse response, Principal principal) {

		  
		  LaExtTransactiondetail laExtTransactiondetail=null;
			try {
				Long landId = 0L;
				landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
	
				LaPartyPerson lapartydetail= getPartyPersonDetailssurrenderlease(Integer.parseInt(landId.toString()));
				LaSurrenderLease surrenderleaseobj = laSurrenderLeaseDao.getSurrenderleaseByLandandProcessId(landId, 5L);
				
				if(null != surrenderleaseobj){
					laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(surrenderleaseobj.getLeaseid().longValue());
					Status status = registrationRecordsService.getStatusById(2);
					laExtTransactiondetail.setLaExtApplicationstatus(status);
					laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
					List<LaLease> leaseeobjList = laLeaseDao.getleaseeListByLandId(landId);
					if(leaseeobjList.size() > 0){
						
						for(LaLease leaseeobj:leaseeobjList){
						
							laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobj.getLeaseid().longValue());
							registrationRecordsService.disablelease(leaseeobj.getPersonid(),landId);
						}
					}
					
					
			}
			
				return laExtTransactiondetail.getTransactionid().toString();
			} catch (Exception e) {
				logger.error(e);
			}
			return null;

		}
	  
	  
	  @RequestMapping(value = "/viewer/registration/savesurrenderMortgagedata", method = RequestMethod.POST)
		@ResponseBody
		public String saveSurrenderMortgagedata(HttpServletRequest request, HttpServletResponse response, Principal principal) {


			try {
				Long landId = 0L;
				LaMortgage mortgageobj =null;
				LaSurrenderMortgage laMortgage = null;
				LaExtFinancialagency laExtFinancialagency = null;
				LaExtTransactiondetail laExtTransactiondetail = null;
				String username = principal.getName();
				User userObj = userService.findByUniqueName(username);
				Integer transactionId=0;
				Long user_id = userObj.getId();
				
				int financial_AgenciesID = ServletRequestUtils.getRequiredIntParameter(request, "mortgage_Financial_Agencies");
				String mortgage_from = ServletRequestUtils.getRequiredStringParameter(request, "mortgage_from");
				String mortgage_to = ServletRequestUtils.getRequiredStringParameter(request, "mortgage_to");
				Double amount_mortgage = ServletRequestUtils.getRequiredDoubleParameter(request, "amount_mortgage");
				String mortgagesurrender_reason = ServletRequestUtils.getRequiredStringParameter(request, "mortgagesurrender_reason");
				Integer editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
				if(editflag==1){
				 transactionId = ServletRequestUtils.getRequiredIntParameter(request, "transactionId");
				}
				
				landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
				
				Status status = registrationRecordsService.getStatusById(1);
				
				

//				PersonType personType = registrationRecordsService.getPersonTypeById(1);

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
				
				
				
				
				 if(editflag==1){
					 laMortgage = laMortgageSurrenderDao.getMortgageByLandIdandTransId(landId,transactionId);
				 }
				 else if(editflag==0){
					  mortgageobj= laMortgagedao.getMortgageByLandId(landId);
					 laMortgage = laMortgageSurrenderDao.getMortgageByLandIdandprocessId(landId, 9L);
					 
				 }
				 
				 if(null != laMortgage){
					 laMortgage.setMortgagefrom(mortgage_fromDate);
					 laMortgage.setMortgageto(mortgage_toDate);
					 laMortgage.setMortgageamount(amount_mortgage);
					  laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
					 laMortgage.setLaExtFinancialagency(laExtFinancialagency);
					 laMortgage.setSurrenderreason(mortgagesurrender_reason);
					 laMortgage = registrationRecordsService.saveSurrenderMortgage(laMortgage);
					/* laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(laMortgage.getMortgageid().longValue());
					 laExtTransactiondetail.setLaExtApplicationstatus(status);
					 laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);*/
					 
				 }
				 else if(null == laMortgage && null != mortgageobj){
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
				
//				SocialTenureRelationship socialTenureRelationshipSellerDetails = registrationRecordsService.getSocialTenureRelationshipByLandId(landId);
				
				 laExtFinancialagency = registrationRecordsService.getFinancialagencyByID(financial_AgenciesID);
				
				 laExtTransactiondetail = new LaExtTransactiondetail();
				laExtTransactiondetail.setCreatedby(user_id.intValue());
				laExtTransactiondetail.setCreateddate(new Date());//new Timestamp(time)
				laExtTransactiondetail.setIsactive(true);
				laExtTransactiondetail.setLaExtApplicationstatus(status);
				
				/*laExtTransactiondetail.setRemarks(remrks_mortgage);*/
				laExtTransactiondetail.setProcessid(9L);// process Id 3 for Mortgage
				
				 laMortgage= new LaSurrenderMortgage();
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
				laMortgage.setSurrenderreason(mortgagesurrender_reason);
				
				laMortgage = registrationRecordsService.saveSurrenderMortgage(laMortgage);
				
				laExtTransactiondetail.setModuletransid(laMortgage.getMortgageid());
				
				laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
				 }

				return laExtTransactiondetail.getTransactionid().toString();
			} catch (Exception e) {
				logger.error(e);
			}
			return null;

		
	
		}
	  
	  
	  
	/*  @RequestMapping(value = "/viewer/registration/updateSurrenderMortgageData", method = RequestMethod.POST)
		@ResponseBody
		public String updateSurrenderMortgageData(HttpServletRequest request, HttpServletResponse response, Principal principal) {

			try {
				Long landId = 0L;
				LaMortgage laMortgage = null;
				LaExtTransactiondetail laExtTransactiondetail  = null;
			
				
//				int no_Of_month_Lease = ServletRequestUtils.getRequiredIntParameter(request,"no_Of_month_Lease");
//				Double lease_Amount = ServletRequestUtils.getRequiredDoubleParameter(request,"lease_Amount");
				
				
				landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");

			
//				int year = no_Of_month_Lease/12;
//				no_Of_month_Lease = no_Of_month_Lease%12;
				
				
//				La_Month laMonth = registrationRecordsService.getLaMonthById(no_Of_month_Lease);
				laMortgage= laMortgagedao.getMortgageByLandId(landId);
					if(null != laMortgage){
					
						
							laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(laMortgage.getMortgageid().longValue());
							Status status = registrationRecordsService.getStatusById(2);
							laExtTransactiondetail.setLaExtApplicationstatus(status);
							laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
					}
					else{
						
					}

				return laExtTransactiondetail.getTransactionid().toString();
			} catch (Exception e) {
				logger.error(e);
			}
			return null;

		}*/
	  
	  
	  @RequestMapping(value = "/viewer/registration/approveSurenderMortgageData", method = RequestMethod.POST)
			@ResponseBody
			public String approveSurrenderMortagagedata(HttpServletRequest request, HttpServletResponse response, Principal principal) {

			  
			  LaExtTransactiondetail laExtTransactiondetail=null;
				try {
					Long landId = 0L;
					landId = ServletRequestUtils.getRequiredLongParameter(request,"landidhide");
		
//					LaPartyPerson lapartydetail= getPartyPersonDetailssurrenderlease(Integer.parseInt(landId.toString()));
					LaSurrenderMortgage  laMortgage = laMortgageSurrenderDao.getMortgageByLandIdandprocessId(landId, 9L);
					
					if(null != laMortgage){
						laExtTransactiondetail = transactionDao.getLaExtTransactionforSurrenderMortgage(laMortgage.getMortgageid().longValue());
						Status status = registrationRecordsService.getStatusById(2);
						laExtTransactiondetail.setLaExtApplicationstatus(status);
						laExtTransactiondetail = registrationRecordsService.saveTransaction(laExtTransactiondetail);
						registrationRecordsService.disableMortagage(laMortgage.getOwnerid(),landId);
				}
				
					return laExtTransactiondetail.getTransactionid().toString();
				} catch (Exception e) {
					logger.error(e);
				}
				return null;

			}
	  
	  @RequestMapping(value = "/viewer/registryrecords/SurrenderMortagagedata/{landid}/{processId}", method = RequestMethod.GET)
		@ResponseBody
		public LaSurrenderMortgage getSurrendermortgagedata(@PathVariable Long landid,@PathVariable Long processId) {
			
	  
	  LaSurrenderMortgage lasurrenderMortgage =null;
	  lasurrenderMortgage = laMortgageSurrenderDao.getMortgageByLandIdandprocessId(landid, processId);
	  if(null  != lasurrenderMortgage){
	  return lasurrenderMortgage;
	  }
	  else {
		  return null;
	  }
	  }
	  
	  
	  
	  @RequestMapping(value = "/viewer/registrion/addLeaseePoi/{landid}", method = RequestMethod.GET)
		@ResponseBody
		public String getLeaseeObject(@PathVariable Long landid) {
			
	  
		  List<LaLease> leaseeobjList = laLeaseDao.getleaseeListByLandId(landid);
	  if(null  != leaseeobjList){
	  return "true";
	  }
	  else {
		  return "Save the leasee details first to add Poi's";
	  }
	  }
	  
	  
	  
	  @RequestMapping(value = "/viewer/landrecords/landPOILeasee/{landid}", method = RequestMethod.GET)
	  @ResponseBody
	  public List<SpatialUnitPersonWithInterest> getLeaseeObjectJsGrid(@PathVariable Long landid) {
				
		  
		  LaExtTransactiondetail laExtTransactiondetail = null;
		  List<SpatialUnitPersonWithInterest> poiObject = null;
		  
			  List<LaLease> leaseeobjList = laLeaseDao.getleaseeListByLandId(landid);
			  if(leaseeobjList.size()>0){
				  	laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobjList.get(0).getLeaseid().longValue());
				  	
				  	if(null != laExtTransactiondetail && laExtTransactiondetail.getLaExtApplicationstatus().getWorkflowStatusId()==1){
				  		
				  		poiObject = spatialUnitPersonWithInterestDao.findByUsinandTransid(landid, laExtTransactiondetail.getTransactionid().longValue());
				  		
				  		return poiObject;
				  	}

			  }
			  
			  return null;
		 
		  }
	  
	  @RequestMapping(value = "/viewer/registration/saveRegPersonOfInterestForLeasee/{landId}", method = RequestMethod.POST)
	    @ResponseBody
	    public SpatialUnitPersonWithInterest saveRegPersonOfInterestForEditing(HttpServletRequest request, @PathVariable Long landId, Principal principal) {
	        
	    	SpatialUnitPersonWithInterest personinterest =null;
	    	Date date1 =null;
	    	try {
	        	Integer PoiId =0;
	        	Integer genderid =0;
	        	Integer realtionid =0;
	        	String dateofbirth ="";
	        	String firstname ="";
	        	String middlename ="";
	        	String lastname ="";
	        	Integer genderidlease =0;
	        	Integer realtionidlease =0;
	        	String dateofbirthlease ="";
	        	String firstnamelease ="";
	        	String middlenamelease ="";
	        	String lastnamelease ="";
	        	Integer transactionId=0;
	        	Integer editflag=0;
	        	
	        	
	        	
				try{firstnamelease =  ServletRequestUtils.getRequiredStringParameter(request, "firstname_r_poi");}catch(Exception e){}
				try{middlenamelease =ServletRequestUtils.getRequiredStringParameter(request, "middlename_r_poi");}catch(Exception e){}
				try{lastnamelease =ServletRequestUtils.getRequiredStringParameter(request, "lastname_r_poi");}catch(Exception e){}
				try{genderidlease =ServletRequestUtils.getRequiredIntParameter(request, "gender_type_POI");}catch(Exception e){}
				try{realtionidlease= ServletRequestUtils.getRequiredIntParameter(request, "Relationship_POI");}catch(Exception e){}
				try{dateofbirthlease=ServletRequestUtils.getRequiredStringParameter(request, "date_Of_birthPOI");}catch(Exception e){}
				try{firstname =  ServletRequestUtils.getRequiredStringParameter(request, "firstname_sale_poi");}catch(Exception e){}
				try{middlename =ServletRequestUtils.getRequiredStringParameter(request, "middlename_sale_poi");}catch(Exception e){}
				try{lastname =ServletRequestUtils.getRequiredStringParameter(request, "lastname_sale_poi");}catch(Exception e){}
				try{genderid =ServletRequestUtils.getRequiredIntParameter(request, "gender_sale_POI");}catch(Exception e){}
				try{realtionid= ServletRequestUtils.getRequiredIntParameter(request, "Relationship_POI_sale");}catch(Exception e){}
				try{dateofbirth=ServletRequestUtils.getRequiredStringParameter(request, "date_Of_birthPOI_sale");}catch(Exception e){}
				try{PoiId=ServletRequestUtils.getRequiredIntParameter(request, "leaseepoiid");}catch(Exception e){}
				 editflag = ServletRequestUtils.getRequiredIntParameter(request, "editflag");
				if(dateofbirth != ""){
					
					
						     date1 = new SimpleDateFormat("YYYY-MM-DD").parse(dateofbirth);
	    		
	    		
//				 date1=new SimpleDateFormat("yyyy-MM-dd").parse(finaldob);
				}
				
				if(dateofbirthlease != ""){
					
					
						     date1 = new SimpleDateFormat("YYYY-MM-DD").parse(dateofbirthlease);
	    		
	    		
//				 date1=new SimpleDateFormat("yyyy-MM-dd").parse(finaldob);
				}
				List<SpatialUnitPersonWithInterest> poiList = new ArrayList<SpatialUnitPersonWithInterest>();
				 LaExtTransactiondetail laExtTransactiondetail = null;
				  List<SpatialUnitPersonWithInterest> poiObject = null;
				  if(editflag==0){
					  LaLease leaseeobjList = laLeaseDao.getleaseeListByLandId(landId).get(0);
					  if(null != leaseeobjList){
						  	laExtTransactiondetail = transactionDao.getLaExtTransactionByLeaseeid(leaseeobjList.getLeaseid().longValue());
						  	
						  

					  }
				  }
					  
					  if(editflag==1){
						  transactionId=landId.intValue();
					  }
					
				personinterest = spatialUnitPersonWithInterestDao.findSpatialUnitPersonWithInterestById(PoiId.longValue());
	        	
				if(null ==personinterest){
	            	personinterest = new SpatialUnitPersonWithInterest();
	            	 if(! firstname.equalsIgnoreCase("")){
	     	            personinterest.setFirstName(firstname);
	     	            	  }
	     	            	  if(! middlename.equalsIgnoreCase("")){
	     	            personinterest.setMiddleName(middlename);
	     	            	  }
	     	            	  if(! lastname.equalsIgnoreCase("")){
	     	            personinterest.setLastName(lastname);
	     	            	  }
	     	            	  if(null!=date1){
	     	            personinterest.setDob(date1);
	     	            	  }
	     	            	  if(genderid !=0){
	     	            personinterest.setGender(genderid);
	     	            	  }if(realtionid !=0){
	     	            personinterest.setRelation(realtionid);
	     	            	  }
	                 
	                 if(! firstnamelease.equalsIgnoreCase("")){
	                	 personinterest.setFirstName(firstnamelease);
	                 }
	                 if(! middlenamelease.equalsIgnoreCase("")){
	                	 personinterest.setMiddleName(middlenamelease);
	                 }
	                 if(! lastnamelease.equalsIgnoreCase("")){
	                	 personinterest.setLastName(lastnamelease);
	                 }
	                 if(genderidlease !=0){
	                	 personinterest.setGender(genderidlease);
	                 }
	                 if(realtionidlease !=0){
	                	 personinterest.setRelation(realtionidlease);
	                 }
	                 if(null!=date1){
	                 personinterest.setDob(date1);
	                 }
	                 
	 	           personinterest.setLandid(landId);
	 	          personinterest.setCreatedby(1);
	 	         personinterest.setCreateddate(new Date());
	 	        personinterest.setIsactive(true);
	 	       if(editflag==1){
               	
   		 	    personinterest.setTransactionid(transactionId);

               }
               else{
		 	    personinterest.setTransactionid(laExtTransactiondetail.getTransactionid());
               }
	            }
	            else{
	            	  if(! firstname.equalsIgnoreCase("")){
	            personinterest.setFirstName(firstname);
	            	  }
	            	  if(! middlename.equalsIgnoreCase("")){
	            personinterest.setMiddleName(middlename);
	            	  }
	            	  if(! lastname.equalsIgnoreCase("")){
	            personinterest.setLastName(lastname);
	            	  }
	            	  if(null!=date1){
	            personinterest.setDob(date1);
	            	  }
	            	  if(genderid !=0){
	            personinterest.setGender(genderid);
	            	  }if(realtionid !=0){
	            personinterest.setRelation(realtionid);
	            	  }
	            	  
	            	  
	            if(! firstnamelease.equalsIgnoreCase("")){
               	 personinterest.setFirstName(firstnamelease);
                }
                if(! middlenamelease.equalsIgnoreCase("")){
               	 personinterest.setMiddleName(middlenamelease);
                }
                if(! lastnamelease.equalsIgnoreCase("")){
               	 personinterest.setLastName(lastnamelease);
                }
                if(genderidlease !=0){
               	 personinterest.setGender(genderidlease);
                }
                if(realtionidlease !=0){
               	 personinterest.setRelation(realtionidlease);
                }
                if(null!=date1){
                personinterest.setDob(date1);
                }
                if(editflag==1){
                	
    		 	    personinterest.setTransactionid(transactionId);

                }
                else{
		 	    personinterest.setTransactionid(laExtTransactiondetail.getTransactionid());
                }

	            }
				poiList.add(personinterest);
				spatialUnitPersonWithInterestDao.addNextOfKin(poiList,landId);
				
					
	        	
	            return personinterest;
	        } catch (Exception e) {
	            logger.error(e);
	            return null;
	        }
	    }
	 
	  
	  @RequestMapping(value = "/viewer/registration/landLeaseePOI/{landid}", method = RequestMethod.GET)
	  @ResponseBody
	  public List<PoiReport>  getLeaseePoi(@PathVariable Long landid) {
				
		  
		  LaExtTransactiondetail laExtTransactiondetail = null;
		  List<PoiReport> poiObject = null;
		  
			  List<LaLease> leaseeobjList = laLeaseDao.getleaseeListByLandId(landid);
			  if(leaseeobjList.size()>0){
				  for(LaLease Obj:leaseeobjList){
				  
				  	laExtTransactiondetail = transactionDao.getpoiByLeaseeid(Obj.getLeaseid().longValue());
				  	
				  	if(null != laExtTransactiondetail && laExtTransactiondetail.getLaExtApplicationstatus().getWorkflowStatusId()==2){
				  		
				  		poiObject = landRecordsService.getDataCorrectionReportPOI(laExtTransactiondetail.getTransactionid().longValue(), landid);
				  		if(null!= poiObject){
				  		return poiObject;
				  		}
				  		
				  	}
				  }

			  }
			  
			  return null;
		 
		  }
	  
	  
	  @RequestMapping(value = "/viewer/registration/processid/{transid}", method = RequestMethod.GET)
	  @ResponseBody
	  public LaExtTransactiondetail getTransactiondetails(@PathVariable Long transid) {
				
		  
		  LaExtTransactiondetail laExtTransactiondetail = null;
		  List<SpatialUnitPersonWithInterest> poiObject = null;
		  
		  
		  
		  laExtTransactiondetail =  transactionDao.getLaExtTransactiondetail(transid.intValue());
		  
			
			  
			  return laExtTransactiondetail;
		 
		  }
	  
	  
	  
	  @RequestMapping(value = "/viewer/registration/getPOIbyId/{Id}", method = RequestMethod.GET)
	    @ResponseBody
	    public SpatialUnitPersonWithInterest getPOIbyId(HttpServletRequest request, @PathVariable Long Id, Principal principal) {
	        
	    	SpatialUnitPersonWithInterest personinterest =null;
	    	
				personinterest = spatialUnitPersonWithInterestDao.findSpatialUnitPersonWithInterestById(Id.longValue());
				
				if(null!=personinterest){
					return personinterest;
				}else{
					return null;
				}
	  }
	        	
	  
	  @RequestMapping(value = "/viewer/registration/salebuyerdetails/{personid}/{landid}", method = RequestMethod.GET)
	    @ResponseBody
	    public Long getBuyerbyPersonId(HttpServletRequest request, @PathVariable Long personid,@PathVariable Long landid, Principal principal) {
	        
		  SocialTenureRelationship plmobj =null;
	    	
		  plmobj = socialTenureRelationshipDAO.getSocialTenureObj(personid, landid);
		  
				
				if(null!=plmobj){
					LaExtTransactiondetail transobj = 	transactionDao.getLaExtTransactiondetail(plmobj.getLaExtTransactiondetail().getTransactionid());
					if(null!=transobj){
						return transobj.getProcessid();
					}
					else{
						return  null;
					}
				}else{
					return null;
				}
	  }
	  
	 
	  
	  
}
