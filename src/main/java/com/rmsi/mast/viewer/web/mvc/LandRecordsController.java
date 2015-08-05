package com.rmsi.mast.viewer.web.mvc;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;
import com.rmsi.mast.studio.domain.fetch.PersonAdministrator;
import com.rmsi.mast.studio.domain.fetch.ProjectTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonadministrator;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.domain.fetch.Usertable;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.viewer.service.LandRecordsService;



@Controller
public class LandRecordsController {

	private static final Logger logger = Logger.getLogger(LandRecordsController.class);

	@Autowired
	private ProjectService projectService;	

	@Autowired
	private UserService userService; 

	@Autowired
	private LandRecordsService landRecordsService;
	@Autowired
	private SpatialUnitService spatialUnitService;

	@Autowired
	private UserDataService userDataService;


	@RequestMapping(value = "/viewer/landrecords/", method = RequestMethod.GET)
	@ResponseBody
	public ProjectTemp list(Principal principal)
	{
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		return 	projectService.findProjectTempByName(user.getDefaultproject());	

	}

	@RequestMapping(value = "/viewer/tenuretype/", method = RequestMethod.GET)
	@ResponseBody
	public List<ShareType> tenureList()
	{

		return landRecordsService.findAllTenureList();



	}


	@RequestMapping(value = "/viewer/socialtenure/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> socialTenureList(@PathVariable Long id)
	{

		return landRecordsService.findAllSocialTenureByUsin(id);
	}


	@RequestMapping(value = "/viewer/spatialunitlist/", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> allspatialUnitList()
	{

		return landRecordsService.findAllSpatialUnitlist();

	}

	@RequestMapping(value = "/viewer/editattribute/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> editAttribute(@PathVariable Long id)
	{

		return landRecordsService.findSpatialUnitbyId(id);

	}


	@RequestMapping(value = "/viewer/landrecords/updateuka", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUkaNum(HttpServletRequest request, HttpServletResponse response)
	{
		long Usin = 0;
		String UkaNumber="";


		SpatialUnitTable spatialUnit= new SpatialUnitTable();

		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request, "primeryky");
			UkaNumber = ServletRequestUtils.getRequiredStringParameter(request, "uka_no");



			spatialUnit.setUsin(Usin);
			spatialUnit.setPropertyno(UkaNumber);


			return landRecordsService.update(spatialUnit);
			 

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}
	}

	@RequestMapping(value = "/viewer/landrecords/updateattributes", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateAttributes(HttpServletRequest request, HttpServletResponse response)
	{

		long Usin = 0;
		String type="";
		String houseType="";
		int househidno=0;
		String landOwner="";
		//String identity="";
		String persontype="";
		int existingUse=0;
		int proposedUse=0;
		Long statusId=0L;
		String project_name="";
		String survey_date="";
		Double perimeter=0.0;
		String house_shape="";
		Double area=0.0;
		String measurement_unit="";
		String uka_propertyno="";
		String comments="";
		String gtype="";
		String imei_number="";
		String address1="";
		String address2="";
		String postal_code="";
		int userid=0;
		int length_general=0;
		String project_general="";
		String witness1="";
		String witness2="";
		/*String witness3="";
		String witness4="";*/
		String neighbour_north="";
		String neighbour_south="";
		String neighbour_east="";
		String neighbour_west="";

		int slope_values=0;
		int quality_soil=0;
		int land_type=0;
		String usinStr="";

		SpatialUnitTable spatialUnit= new SpatialUnitTable();

		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request, "primary");
			usinStr=ServletRequestUtils.getRequiredStringParameter(request, "usinStr_key");
			type = ServletRequestUtils.getRequiredStringParameter(request, "type");
			houseType = ServletRequestUtils.getRequiredStringParameter(request, "house_type");
			househidno = ServletRequestUtils.getRequiredIntParameter(request, "household");
			landOwner = ServletRequestUtils.getRequiredStringParameter(request, "landowner");
			//identity = ServletRequestUtils.getRequiredStringParameter(request, "identity");
			persontype = ServletRequestUtils.getRequiredStringParameter(request, "person_type");
			existingUse= ServletRequestUtils.getRequiredIntParameter(request, "existing_use");
			proposedUse= ServletRequestUtils.getRequiredIntParameter(request, "proposed_use");



			project_name=ServletRequestUtils.getRequiredStringParameter(request, "project_key");
			ServletRequestUtils.getRequiredStringParameter(request, "type_name");
			house_shape=ServletRequestUtils.getRequiredStringParameter(request, "house_shape");
			measurement_unit=ServletRequestUtils.getRequiredStringParameter(request, "measurement_unit");
			uka_propertyno=ServletRequestUtils.getRequiredStringParameter(request, "uka_no_gen");
			comments=ServletRequestUtils.getRequiredStringParameter(request, "comments");
			gtype=ServletRequestUtils.getRequiredStringParameter(request, "gtype");
			imei_number=ServletRequestUtils.getRequiredStringParameter(request, "imei_no");
			address1=ServletRequestUtils.getRequiredStringParameter(request, "address_1");
			address2=ServletRequestUtils.getRequiredStringParameter(request, "address_2");
			postal_code=ServletRequestUtils.getRequiredStringParameter(request, "postal_code");
			statusId=ServletRequestUtils.getRequiredLongParameter(request, "workflow_status");
			perimeter=ServletRequestUtils.getRequiredDoubleParameter(request, "perimeter");
			area=ServletRequestUtils.getRequiredDoubleParameter(request, "area");
			survey_date=ServletRequestUtils.getRequiredStringParameter(request, "survey_date");
			userid=ServletRequestUtils.getRequiredIntParameter(request, "usertable_id");
			length_general=ServletRequestUtils.getRequiredIntParameter(request, "general_length");
			project_general=ServletRequestUtils.getRequiredStringParameter(request, "projectname_key1");
			witness1 = ServletRequestUtils.getRequiredStringParameter(request, "witness_1");
			witness2 = ServletRequestUtils.getRequiredStringParameter(request, "witness_2");
			/*witness3 = ServletRequestUtils.getRequiredStringParameter(request, "witness_3");
			witness4 = ServletRequestUtils.getRequiredStringParameter(request, "witness_4");*/
			neighbour_north = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_north");
			neighbour_south = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_south");
			neighbour_east = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_east");
			neighbour_west = ServletRequestUtils.getRequiredStringParameter(request, "neighbor_west");

			slope_values=ServletRequestUtils.getRequiredIntParameter(request, "slope");
			quality_soil=ServletRequestUtils.getRequiredIntParameter(request, "soil_quality");
			land_type=ServletRequestUtils.getRequiredIntParameter(request, "land_type");

			spatialUnit.setUsin(Usin);
			spatialUnit.setType(type);
			spatialUnit.setHouseType(houseType);
			spatialUnit.setHousehidno(househidno);
			spatialUnit.setLandOwner(landOwner);

			spatialUnit.setOtherUseType(persontype);
			spatialUnit.setAddress1(address1);
			spatialUnit.setAddress2(address2);
			spatialUnit.setArea(area);
			spatialUnit.setComments(comments);
			spatialUnit.setGtype(gtype);
			spatialUnit.setHouseshape(house_shape);
			spatialUnit.setImeiNumber(imei_number);
			spatialUnit.setPerimeter(perimeter);
			spatialUnit.setPostal_code(postal_code);
			spatialUnit.setProject(project_name);
			spatialUnit.setActive(true);

			spatialUnit.setPropertyno(uka_propertyno);
			spatialUnit.setStatusUpdateTime(new Date());
			spatialUnit.setNeighbor_east(neighbour_east);
			spatialUnit.setNeighbor_north(neighbour_north);
			spatialUnit.setNeighbor_south(neighbour_south);
			spatialUnit.setNeighbor_west(neighbour_west);
			spatialUnit.setWitness_1(witness1);
			spatialUnit.setWitness_2(witness2);
			/*spatialUnit.setWitness_3(witness3);
			spatialUnit.setWitness_4(witness4);*/
			spatialUnit.setUsinStr(usinStr);

			if(survey_date!="")

			{
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date = format.parse(survey_date);
					spatialUnit.setSurveyDate(date);


				} catch (ParseException e) {

					logger.error(e);
				}
			}

			if (statusId!=0)
			{
				Status statusObj = landRecordsService.findAllSTatus(statusId);
				spatialUnit.setStatus(statusObj);
			}

			if (measurement_unit!="")
			{
				Unit unitObj = landRecordsService.findAllMeasurementUnit(measurement_unit);
				spatialUnit.setMeasurementUnit(unitObj);
			}

			if(existingUse!=0)
			{
				LandUseType existingObj = landRecordsService.findLandUseById(existingUse);
				spatialUnit.setExistingUse(existingObj);

			}

			if(proposedUse!=0)
			{
				LandUseType proposedobj = landRecordsService.findProposedUseById(proposedUse);
				spatialUnit.setProposedUse(proposedobj);

			}


			if (userid!=0)
			{
				Usertable userObj = landRecordsService.findUserByID(userid);
				spatialUnit.setUser(userObj);
				spatialUnit.setUserid(userid);
			}

			if (quality_soil!=0)
			{
				SoilQualityValues soilQuailtyObj = landRecordsService.findSoilQuality(quality_soil);
				spatialUnit.setSoilQualityValues(soilQuailtyObj);
			}
			if (slope_values!=0)
			{
				SlopeValues slopeValuesObj = landRecordsService.findSlopeValues(slope_values);
				spatialUnit.setSlopeValues(slopeValuesObj);
			}
			if (land_type!=0)
			{
				LandType landTypeObj = landRecordsService.findLandType(land_type);
				spatialUnit.setLandType(landTypeObj);
			}

			new AttributeValues();

			if(length_general>0)
			{
				for (int i = 0; i <length_general; i++) {

					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias=sb.toString();
					Long value_key=0l;

					alias=ServletRequestUtils.getRequiredStringParameter(request,"alias_general"+i);
					value_key=ServletRequestUtils.getRequiredLongParameter(request,"alias_general_key"+i);
					if(value_key!=0)
					{
						landRecordsService.updateAttributeValues(value_key,alias);
					}
				}

			}
			//For updating in Attribute Values Table
			try {
				userDataService.updateGeneralAttribValues(spatialUnit, project_general);
			} catch (Exception e) {
				logger.error(e);

			}

			return landRecordsService.update(spatialUnit);


		} catch (ServletRequestBindingException e) {
			logger.error(e);

			return false;

		}


	}

	@RequestMapping(value = "/viewer/landrecords/updateapprove/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateApprove(@PathVariable Long id,Principal principal){

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid=user.getId();
		return landRecordsService.updateApprove(id,userid);

	}

	@RequestMapping(value = "/viewer/landrecords/rejectstatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean rejectStatus(@PathVariable Long id,Principal principal){

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid=user.getId();
		return landRecordsService.rejectStatus(id,userid);

	}

	@RequestMapping(value = "/viewer/landrecords/search/{project}/{startpos}", method = RequestMethod.POST)
	@ResponseBody
	public List<SpatialUnitTable> searchUnitList(HttpServletRequest request, HttpServletResponse response, Principal principal,@PathVariable String project,@PathVariable Integer startpos)

	{		
		String UkaNumber="";
		String dateto="";
		String datefrom="";
		long status=0;
		String UsinStr="";

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projname = user.getDefaultproject();
		if(project!="")
			projname=project;

		try {
			/*try {
				Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_id");
			} catch (Exception e) {
				logger.error(e);
			}*/
			try {
				UsinStr = ServletRequestUtils.getRequiredStringParameter(request, "usinstr_id");
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				UkaNumber = ServletRequestUtils.getRequiredStringParameter(request, "uka_id");
			} catch (Exception e) {
				logger.error(e);
			}

			try {
				dateto= ServletRequestUtils.getRequiredStringParameter(request, "to_id");


			} catch (Exception e) {	
				logger.error(e);
			}

			try {
				datefrom= ServletRequestUtils.getRequiredStringParameter(request, "from_id");

			} catch (Exception e) {	
				logger.error(e);
			}

			try {
				status = ServletRequestUtils.getRequiredLongParameter(request, "status_id");
			} catch (Exception e) {

				logger.error(e);
			}

			if(dateto=="")

			{

				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
				Date now = new Date();
				dateto = sdfDate.format(now);
			}
			else if(dateto!="")
			{
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
				Date date = sdfDate.parse(dateto);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				dateto = sdfDate.format(calendar.getTime());
			}
			if(datefrom=="")

			{

				datefrom="1990-01-01 00:00:00";
			}

			return landRecordsService.search(UsinStr,UkaNumber,projname,dateto,datefrom,status,startpos);


		} catch (Exception e) {

			logger.error(e);
			return null;

		}

	}

	@RequestMapping(value = "/viewer/status/", method = RequestMethod.GET)
	@ResponseBody
	public List<Status> listdata()
	{
		List<Status> statuslst= new ArrayList<Status>();

		try {

			statuslst=landRecordsService.findallStatus();

		} catch (Exception e) {

			logger.error(e);
			return statuslst;
		}


		return statuslst;

	}

	@RequestMapping(value = "/viewer/naturalperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<NaturalPerson> naturalPerson(@PathVariable Long id)
	{

		return landRecordsService.naturalPersonById(id);



	}


	@RequestMapping(value = "/viewer/nonnaturalperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<NonNaturalPerson> nonnaturalPerson(@PathVariable Long id)
	{
		return landRecordsService.nonnaturalPersonById(id);

	}

	@RequestMapping(value = "/viewer/gendertype/", method = RequestMethod.GET)
	@ResponseBody
	public List<Gender> genderList()
	{
		return landRecordsService.findAllGenders();

	}

	@RequestMapping(value = "/viewer/maritalstatus/", method = RequestMethod.GET)
	@ResponseBody
	public List<MaritalStatus> maritalStatusList()
	{

		return landRecordsService.findAllMaritalStatus();

	}


	@RequestMapping(value = "/viewer/landrecords/updatenatural", method = RequestMethod.POST)
	@ResponseBody
	public NaturalPerson updateNaturalPerson(HttpServletRequest request, HttpServletResponse response)
	{

		Long id=0L;
		String name="";
		Long genderid=0L;
		String tenure="";
		int maritalid=0;
		long persontype=1;
		String first_name="";
		String middle_name="";
		String last_name="";
		String mobile_number="";
		int age = 0;
		String occupation="";
		Long education=0l;
		int length_natural=0;
		String project_name="";

		int owner_natural;
		int resd_village;
		String admin="";
		String citizenship="";
		int newnatural_length=0;
		long usin=0;


		NaturalPerson naturalPerson= new NaturalPerson();
		try {
			try {
				id = ServletRequestUtils.getRequiredLongParameter(request, "natural_key");
			} catch (Exception e2) {
				logger.error(e2);
			}
			
			try {
				usin = ServletRequestUtils.getRequiredLongParameter(request, "natural_usin");
			} catch (Exception e) {
				logger.error(e);
			}
			name = ServletRequestUtils.getRequiredStringParameter(request, "name");
			first_name = ServletRequestUtils.getRequiredStringParameter(request, "fname");
			middle_name = ServletRequestUtils.getRequiredStringParameter(request, "mname");
			last_name = ServletRequestUtils.getRequiredStringParameter(request, "lname");
			mobile_number = ServletRequestUtils.getRequiredStringParameter(request, "mobile_natural");
			occupation = ServletRequestUtils.getRequiredStringParameter(request, "occ");
			education = ServletRequestUtils.getRequiredLongParameter(request, "edu");
			try {
				age= ServletRequestUtils.getRequiredIntParameter(request, "age");
			} catch (Exception e1) {
				logger.error(e1);
			}
			maritalid= ServletRequestUtils.getRequiredIntParameter(request, "marital_status");
			//occupational_age= ServletRequestUtils.getRequiredIntParameter(request, "occ_age");
			genderid = ServletRequestUtils.getRequiredLongParameter(request, "gender");
			tenure = ServletRequestUtils.getRequiredStringParameter(request, "tenure");
			length_natural= ServletRequestUtils.getRequiredIntParameter(request, "natual_length");
			project_name= ServletRequestUtils.getRequiredStringParameter(request, "projectname_key");

			owner_natural= ServletRequestUtils.getRequiredIntParameter(request, "owner_nat");
			resd_village= ServletRequestUtils.getRequiredIntParameter(request, "resident");
			admin= ServletRequestUtils.getRequiredStringParameter(request, "administrator");
			citizenship= ServletRequestUtils.getRequiredStringParameter(request, "citizenship");

			try {
				newnatural_length=ServletRequestUtils.getRequiredIntParameter(request, "newnatural_length");
			} catch (Exception e) {
				logger.error(e);
			}

			if(id!=0){
				naturalPerson.setPerson_gid(id);
			}
			naturalPerson.setAlias(name);
			naturalPerson.setTenure_Relation(tenure);
			naturalPerson.setFirstName(first_name);
			naturalPerson.setMiddleName(middle_name);
			naturalPerson.setLastName(last_name);
			naturalPerson.setMobile(mobile_number);
			naturalPerson.setOccupation(occupation);
			naturalPerson.setOwner(false);
			if(owner_natural==1){
				naturalPerson.setOwner(true);	
			}

			naturalPerson.setResident_of_village(false);
			if(resd_village==1)
			{
				naturalPerson.setResident_of_village(true);

			}
			naturalPerson.setAdministator(admin);
			naturalPerson.setCitizenship(citizenship);


			if(education!=0)
			{
				EducationLevel educationObj = landRecordsService.findEducationById(education);
				naturalPerson.setEducation(educationObj);

			}
			if(age!=0){
				naturalPerson.setAge(age);		
			}

			//naturalPerson.setOccAgeBelow(occupational_age);
			naturalPerson.setActive(true);

			if(genderid!=0)
			{
				Gender genderIdObj = landRecordsService.findGenderById(genderid);
				naturalPerson.setGender(genderIdObj);

			}

			PersonType personTypeObj = landRecordsService.findPersonTypeById(persontype);
			naturalPerson.setPerson_type_gid(personTypeObj);

			if(maritalid!=0){

				MaritalStatus maritalIdObj = landRecordsService.findMaritalById(maritalid);
				naturalPerson.setMarital_status(maritalIdObj);
			}


			//AttributeValues attributeValues=new AttributeValues();

			if(length_natural>0)
			{
				for (int i = 0; i <length_natural; i++) {

					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias=sb.toString();
					Long value_key=0l;

					alias=ServletRequestUtils.getRequiredStringParameter(request,"alias_natural"+i);
					value_key=ServletRequestUtils.getRequiredLongParameter(request,"alias_natural_key"+i);
					if(value_key!=0)
					{
						landRecordsService.updateAttributeValues(value_key,alias);
					}
				}

			}

			//For updating in Attribute Values Table
			try {
				userDataService.updateNaturalPersonAttribValues(naturalPerson,project_name);
			} catch (Exception e) {
				logger.error(e);
			}

			NaturalPerson naturalobj = landRecordsService.editnatural(naturalPerson);
			
			if(id==0)
			{
				
				SocialTenureRelationship socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(usin).get(0);
				socialtenuretmp.setGid(0);
				socialtenuretmp.setPerson_gid(naturalobj);
				/*SocialTenureRelationship socialTenureRelationship= new SocialTenureRelationship();
				socialTenureRelationship.setIsActive(true);
				socialTenureRelationship.setOccupancy_type_id(socialtenuretmp.getOccupancy_type_id());
				socialTenureRelationship.setPerson_gid(naturalobj);
				socialTenureRelationship.setShare_type(socialtenuretmp.getShare_type());
				socialTenureRelationship.setSocial_tenure_enddate(socialtenuretmp.getSocial_tenure_enddate());
				socialTenureRelationship.setSocial_tenure_startdate(socialtenuretmp.getSocial_tenure_startdate());
				socialTenureRelationship.setTenure_duration(socialtenuretmp.getTenure_duration());
				socialTenureRelationship.setTenureclass_id(socialtenuretmp.getTenureclass_id());
				socialTenureRelationship.setUsin(usin);*/
				landRecordsService.edittenure(socialtenuretmp);
				
				
			}

			if(newnatural_length!=0)
			{
				AttributeValues tmpvalue= new AttributeValues();
				for (int i = 0; i < newnatural_length; i++) {
					int j=i+1;
					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias_value=sb.toString();
					Long uid=0l;

					alias_value=ServletRequestUtils.getRequiredStringParameter(request,"alias_nat_custom"+j);
					uid=ServletRequestUtils.getRequiredLongParameter(request,"alias_uid"+j);

					tmpvalue.setValue(alias_value);
					tmpvalue.setParentuid(naturalobj.getPerson_gid());
					tmpvalue.setUid(uid);
					landRecordsService.saveAttributealues(tmpvalue);

				}



			}

			return naturalobj;

		} catch (ServletRequestBindingException e) {

			logger.error(e);


		}
		return null;

	}

	@RequestMapping(value = "/viewer/landrecords/updatenonnatural", method = RequestMethod.POST)
	@ResponseBody
	public boolean updatenonNaturalPerson(HttpServletRequest request, HttpServletResponse response)
	{

		Long id=0L;
		String institute_name="";
		String address="";
		String phone_no="";
		long persontype=2;
		long poc_id=0;
		String mobileGroupId="";
		int length_nonnatural=0;
		String project_nonnatural="";
		int group_type=0;

		NonNaturalPerson nonnaturalPerson= new NonNaturalPerson();

		try {
			id = ServletRequestUtils.getRequiredLongParameter(request, "non_natural_key");
			institute_name = ServletRequestUtils.getRequiredStringParameter(request, "institution");
			address = ServletRequestUtils.getRequiredStringParameter(request, "address");
			phone_no = ServletRequestUtils.getRequiredStringParameter(request, "mobile_no");
			poc_id=ServletRequestUtils.getRequiredLongParameter(request, "poc_id");
			mobileGroupId = ServletRequestUtils.getRequiredStringParameter(request, "mobileGroupId");
			length_nonnatural=ServletRequestUtils.getRequiredIntParameter(request, "nonnatual_length");
			project_nonnatural=ServletRequestUtils.getRequiredStringParameter(request, "projectname_key2");

			group_type=ServletRequestUtils.getRequiredIntParameter(request, "group_type");

			nonnaturalPerson.setPerson_gid(id);
			nonnaturalPerson.setAddress(address);
			nonnaturalPerson.setInstitutionName(institute_name);
			nonnaturalPerson.setPhoneNumber(phone_no);
			nonnaturalPerson.setActive(true);

			PersonType personTypeObj = landRecordsService.findPersonTypeById(persontype);
			nonnaturalPerson.setPerson_type_gid(personTypeObj);
			nonnaturalPerson.setMobileGroupId(mobileGroupId);
			nonnaturalPerson.setPoc_gid(poc_id);
			if(group_type!=0)
			{
				GroupType grouptypeObj = landRecordsService.findGroupType(group_type);
				nonnaturalPerson.setGroupType(grouptypeObj);
			}

			//AttributeValues attributeValues=new AttributeValues();

			if(length_nonnatural>0)
			{
				for (int i = 0; i <length_nonnatural; i++) {

					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias=sb.toString();
					Long value_key=0l;

					alias=ServletRequestUtils.getRequiredStringParameter(request,"alias_nonnatural"+i);
					value_key=ServletRequestUtils.getRequiredLongParameter(request,"alias_nonnatural_key"+i);
					if(value_key!=0)
					{
						landRecordsService.updateAttributeValues(value_key,alias);
					}
				}

			}

			//For Updating Non Natural in Attribute Vlaues
			try {
				userDataService.updateNonNaturalPersonAttribValues(nonnaturalPerson, project_nonnatural);
			} catch (Exception e) {
				logger.error(e);
			}

			return landRecordsService.editNonNatural(nonnaturalPerson);


		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}


	}

	@RequestMapping(value = "/viewer/landrecords/updatetenure", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateTenure(HttpServletRequest request, HttpServletResponse response)
	{

		int id=0;
		Long type=0L;
		int length=0;
		String startDate="";
		String endDate="";
		long usin=0;
		long persontype=0;
		int occid=0;
		int tenureclassid=0;
		int duration=0;
		String project_tenure="";

		SocialTenureRelationship socialTenureRelationship= new SocialTenureRelationship();

		try {
			id = ServletRequestUtils.getRequiredIntParameter(request, "tenure_key");
			type = ServletRequestUtils.getRequiredLongParameter(request, "tenure_type");

			startDate = ServletRequestUtils.getRequiredStringParameter(request, "start_date");
			endDate = ServletRequestUtils.getRequiredStringParameter(request, "end_date");

			occid = ServletRequestUtils.getRequiredIntParameter(request, "occtype_id");
			tenureclassid = ServletRequestUtils.getRequiredIntParameter(request, "tenureclass_id");
			usin = ServletRequestUtils.getRequiredLongParameter(request, "usin");
			persontype= ServletRequestUtils.getRequiredLongParameter(request, "person_gid");
			length =ServletRequestUtils.getRequiredIntParameter(request, "tenure_length");
			project_tenure=ServletRequestUtils.getRequiredStringParameter(request, "projectname_key3");

			//	AttributeValues attributeValues=new AttributeValues();

			if(length>0)
			{
				for (int i = 0; i <length; i++) {

					StringBuilder sb = new StringBuilder();
					sb.append("alias");
					sb.append(i);
					String alias=sb.toString();
					Long value_key=0l;

					alias=ServletRequestUtils.getRequiredStringParameter(request,"alias_tenure"+i);
					value_key=ServletRequestUtils.getRequiredLongParameter(request,"alias_tenure_key"+i);
					if(value_key!=0)
					{
						landRecordsService.updateAttributeValues(value_key,alias);
					}
				}

			}

			Date datestart=null;
			Date dateend=null;
			try {
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

				datestart = sdfDate.parse(startDate);
				dateend = sdfDate.parse(endDate);
				duration=dateend.getYear()-datestart.getYear();

			} catch (ParseException e) {

				logger.error(e);
			}

			socialTenureRelationship.setGid(id);
			if(type!=0){

				ShareType tenuretypeobj = landRecordsService.findTenureType(type);
				socialTenureRelationship.setShare_type(tenuretypeobj);


			}
			Person personObj = landRecordsService.findPersonGidById(persontype);
			socialTenureRelationship.setPerson_gid(personObj);

			OccupancyType occupancytypeObj = landRecordsService.findOccupancyTyoeById(occid);
			socialTenureRelationship.setOccupancy_type_id(occupancytypeObj);

			TenureClass tenureclassobj = landRecordsService.findtenureClasseById(tenureclassid);
			socialTenureRelationship.setTenureclass_id(tenureclassobj);

			//socialTenureRelationship.setShare(share);
			socialTenureRelationship.setSocial_tenure_startdate(datestart);
			socialTenureRelationship.setSocial_tenure_enddate(dateend);
			socialTenureRelationship.setUsin(usin);
			socialTenureRelationship.setIsActive(true);
			socialTenureRelationship.setTenure_duration(duration);

			//For Updating tenure in AttributeValues
			try {
				userDataService.updateTenureAttribValues(socialTenureRelationship, project_tenure);
			} catch (Exception e) {
				logger.error(e);
			}

			return landRecordsService.edittenure(socialTenureRelationship);

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}


	}

	@RequestMapping(value = "/viewer/landrecords/updatemultimedia", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateMultimedia(HttpServletRequest request, HttpServletResponse response)
	{

		int gid = 0;
		String id="";
		String recordation="";
		String scannedDocs="";
		String qualityType="";
		String comments="";
		long usin=0;
		long personGid=0;
		int socialGid=0;
		String inventory_type="";
		String source_path="";
		String project_multimedia="";
		String entityName="";

		SourceDocument sourceDocument= new SourceDocument();

		try {
			gid = ServletRequestUtils.getRequiredIntParameter(request, "primary_key");
			id = ServletRequestUtils.getRequiredStringParameter(request, "multimedia_id");
			try {
				recordation = ServletRequestUtils.getRequiredStringParameter(request, "recordation");
			} catch (Exception e) {
				logger.error(e);
			}
			entityName = ServletRequestUtils.getRequiredStringParameter(request, "entity_name");
			scannedDocs =  ServletRequestUtils.getRequiredStringParameter(request, "scanned_srs");
			qualityType = ServletRequestUtils.getRequiredStringParameter(request, "quality_type");
			comments = ServletRequestUtils.getRequiredStringParameter(request, "comments_multimedia");
			usin = ServletRequestUtils.getRequiredLongParameter(request, "usink");
			source_path= ServletRequestUtils.getRequiredStringParameter(request, "source_path");
			project_multimedia=ServletRequestUtils.getRequiredStringParameter(request, "projectname_multimedia_key");

			try {
				personGid = ServletRequestUtils.getRequiredLongParameter(request, "person_gidk");
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				socialGid = ServletRequestUtils.getRequiredIntParameter(request, "social_gid");
			} catch (Exception e) {

				logger.error(e);
			}
			inventory_type=ServletRequestUtils.getRequiredStringParameter(request, "inventory_type");


			sourceDocument.setGid(gid);
			if(id!="")
			{
				sourceDocument.setId(id);
			}
			Date recordDate=null;
			try {
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

				recordDate = sdfDate.parse(recordation);
			} catch (ParseException e) {

				logger.error(e);
			}
			sourceDocument.setEntity_name(entityName);
			sourceDocument.setRecordation(recordDate);
			sourceDocument.setScanedSourceDoc(scannedDocs);
			sourceDocument.setQualityType(qualityType);
			sourceDocument.setComments(comments);
			sourceDocument.setUsin(usin);
			if(personGid!=0)
			{
				sourceDocument.setPerson_gid(personGid);
			}
			if(socialGid!=0){
				sourceDocument.setSocial_tenure_gid(socialGid);
			}
			sourceDocument.setSocialTenureInvantoryType(inventory_type);
			sourceDocument.setLocScannedSourceDoc(source_path);
			sourceDocument.setActive(true);

			//For Updating tenure in AttributeValues
			try {
				userDataService.updateMultimediaAttribValues(sourceDocument, project_multimedia);
			} catch (Exception e) {
				logger.error(e);
			}


			landRecordsService.updateMultimedia(sourceDocument);
			return true;

		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}


	}

	@RequestMapping(value = "/viewer/socialtenure/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> SocialTenurebyGidList(@PathVariable Integer id)
	{

		return landRecordsService.findSocialTenureByGid(id);

	}


	@RequestMapping(value = "/viewer/multimedia/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> MultimediaList(@PathVariable Long id)
	{

		return landRecordsService.findMultimediaByUsin(id);

	}

	@RequestMapping(value = "/viewer/multimedia/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> MultimediaGidList(@PathVariable Long id)
	{

		return landRecordsService.findMultimediaByGid(id);

	}

	@RequestMapping(value = "/viewer/landrecords/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean  deleteMultimedia(@PathVariable Long id)
	{

		landRecordsService.deleteMultimedia(id);
		return true;

	}

	@RequestMapping(value = "/viewer/landrecords/deleteNatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean  deleteNatural(@PathVariable Long id)
	{

		return landRecordsService.deleteNatural(id);


	}

	@RequestMapping(value = "/viewer/landrecords/deletenonnatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean  deleteNonNatural(@PathVariable Long id)
	{

		return landRecordsService.deleteNonNatural(id);

	}

	@RequestMapping(value = "/viewer/landrecords/deleteTenure/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean  deleteTenure(@PathVariable Long id)
	{

		return 	landRecordsService.deleteTenure(id);

	}

	@RequestMapping(value = "/viewer/educationlevel/", method = RequestMethod.GET)
	@ResponseBody
	public List<EducationLevel> educationList()
	{

		return landRecordsService.findAllEducation();

	}

	@RequestMapping(value = "/viewer/landusertype/", method = RequestMethod.GET)
	@ResponseBody
	public List<LandUseType> landUserList()
	{
		return landRecordsService.findAllLanduserType();

	}

	@RequestMapping(value = "/viewer/tenureclass/", method = RequestMethod.GET)
	@ResponseBody
	public List<TenureClass> tenureclassList()
	{

		return landRecordsService.findAllTenureClass();
	}

	@RequestMapping(value = "/viewer/ukanumber/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String ukaNumberByUsin(@PathVariable Long id)
	{

		return landRecordsService.findukaNumberByUsin(id);
	}

	@RequestMapping(value = "/viewer/occupancytype/", method = RequestMethod.GET)
	@ResponseBody
	public List<OccupancyType> OccTypeList()
	{

		return landRecordsService.findAllOccupancyTypes();
	}

	@RequestMapping(value = "/viewer/attributecategory/", method = RequestMethod.GET)
	@ResponseBody
	public List<AttributeCategory> attribList()
	{

		return landRecordsService.findAllAttributeCategories();
	}


	@RequestMapping(value = "/viewer/getCCRO/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> CCROList(@PathVariable Long id, Principal principal)
	{
		/*
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid=user.getId();

		List<SpatialUnitTable> spatialunit = landRecordsService.findSpatialUnitbyId(id);
		if(spatialunit.get(0).getStatus().getWorkflowStatusId()==4)
		{		if(landRecordsService.updateCCRO(id,userid))
		{
			return landRecordsService.findCCRO_by_USIN(id);
		}
		else	{

			return null;
		}
		}
		else{

			return landRecordsService.findCCRO_by_USIN(id);

		}*/

		return landRecordsService.findSpatialUnitbyId(id);


	}

/*	@RequestMapping(value = "/viewer/gethamlet/{projectname}", method = RequestMethod.GET)
	@ResponseBody
	public String HamletbyUsin(@PathVariable String projectname)
	{

		Project project=	projectService.findProjectByName(projectname);
		return project.getProjectAreas().get(0).getVillage();

	}
*/

	@RequestMapping(value = "/viewer/attributedata/{categoryid}/{parentid}", method = RequestMethod.GET)
	@ResponseBody
	public List<AttributeValuesFetch> attributeDataList(@PathVariable long categoryid,@PathVariable long parentid)
	{

		return landRecordsService.findAttributelistByCategoryId(parentid,categoryid);

	}

	@RequestMapping(value = "/viewer/naturalpersondata/{personid}", method = RequestMethod.GET)
	@ResponseBody
	public Person naturalPersonList(@PathVariable long personid)
	{

		return landRecordsService.findPersonGidById(personid);

	}

	@RequestMapping(value = "/viewer/landrecords/finalstatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateFinal(@PathVariable Long id,Principal principal){

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid=user.getId();
		return landRecordsService.updateFinal(id,userid);

	}

	@RequestMapping(value = "/viewer/uploadweb/", method = RequestMethod.POST)
	@ResponseBody
	public String uploadSpatialData(MultipartHttpServletRequest request, HttpServletResponse response , Principal principal)
	{	
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projectName=user.getDefaultproject();

		try {

			Iterator<String> file = request.getFileNames();
			Long usin=0l;
			usin=ServletRequestUtils.getRequiredLongParameter(request, "Usin_Upload");
			String document_name ="";
			document_name= ServletRequestUtils.getRequiredStringParameter(request, "document_name");
			String document_comments ="";
			try {
				document_comments= ServletRequestUtils.getRequiredStringParameter(request, "document_comments");
			} catch (Exception e2) {
				logger.error(e2);

			}

			byte[] document = null;
			while(file.hasNext()) 
			{
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);

				String originalFileName = mpFile.getOriginalFilename();
				SourceDocument sourceDocument = new SourceDocument();


				String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1,originalFileName.length()).toLowerCase();

				if (originalFileName != "") {
					document = mpFile.getBytes();
				}
				String uploadFileName = null;

				String tmpDirPath=request.getSession().getServletContext().getRealPath(File.separator);

				String outDirPath=tmpDirPath.replace("mast", "")+"resources"+File.separator+"documents"+File.separator+projectName+File.separator+"webupload";


				File outDir=new File(outDirPath);
				boolean exists = outDir.exists();
				if (!exists) {

					(new File(outDirPath)).mkdirs();

				}


				sourceDocument.setScanedSourceDoc(originalFileName);
				uploadFileName=("resources"+File.separator+"documents"+File.separator+projectName+File.separator+"webupload");
				sourceDocument.setLocScannedSourceDoc(uploadFileName);
				sourceDocument.setEntity_name(document_name);
				sourceDocument.setComments(document_comments);
				sourceDocument.setActive(true);
				sourceDocument.setRecordation(new Date());
				sourceDocument.setUsin(usin);

				sourceDocument =  landRecordsService.saveUploadedDocuments(sourceDocument);

				Integer id = sourceDocument.getGid();

				try {

					FileOutputStream uploadfile = new FileOutputStream(outDirPath+File.separator+ id+"."+fileExtension);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
					return "Success";

				} 

				catch (Exception e) {

					logger.error(e);
					return "Error";
				}

			} 

		}catch (Exception e) {
			logger.error(e);
			return "Error";
		}		
		return "false";
	}


	@RequestMapping(value = "/viewer/sourcedocument/{usinId}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> sourcedocumentList(@PathVariable long usinId)
	{

		return landRecordsService.findMultimediaByUsin(usinId);

	}

	@RequestMapping(value = "/viewer/landrecords/adjudicatestatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateAdjudicated(@PathVariable Long id,Principal principal){

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid=user.getId();
		return landRecordsService.updateAdjudicated(id,userid);

	}


	@RequestMapping(value = "/viewer/download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String  download(@PathVariable Long id, HttpServletRequest request){

		SourceDocument doc = landRecordsService.getDocumentbyGid(id);
		String fileName = doc.getScanedSourceDoc();
		String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase();
		//Object path_temp = request.getSession().getServletContext().getRealPath(File.separator);
		String filepath=doc.getLocScannedSourceDoc()+File.separator+id+"."+fileType;

		return filepath;

	}


	@RequestMapping(value = "/viewer/Adjuticator/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> AdjuticatorList(Principal principal ,@PathVariable Long id)
	{
		/*
		List<SpatialUnitTable> spatialunit = landRecordsService.findSpatialUnitbyId(id);
		if(spatialunit.get(0).getStatus().getWorkflowStatusId()==1)
		{
			if(updateAdjudicated(id, principal))
			{
				return landRecordsService.findAdjuticator_by_USIN(id);
			}

			else{

				return null;
			}
		}
		else
		{
			return landRecordsService.findAdjuticator_by_USIN(id);

		}*/


		return landRecordsService.findSpatialUnitbyId(id);


	}


	@RequestMapping(value = "/viewer/projectarea/", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectArea> updateFinal(Principal principal){

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projectName=user.getDefaultproject();
		return landRecordsService.findProjectArea(projectName);

	}


	@RequestMapping(value = "/viewer/soilquality/", method = RequestMethod.GET)
	@ResponseBody
	public List<SoilQualityValues> soilQualityList()
	{

		return landRecordsService.findAllsoilQuality();
	}

	@RequestMapping(value = "/viewer/slope/", method = RequestMethod.GET)
	@ResponseBody
	public List<SlopeValues> slopeList()
	{

		return landRecordsService.findAllSlopeValues();

	}

	@RequestMapping(value = "/viewer/typeofland/", method = RequestMethod.GET)
	@ResponseBody
	public List<LandType> landTypeList()
	{

		return landRecordsService.findAllLandType();
	}

	@RequestMapping(value = "/viewer/grouptype/", method = RequestMethod.GET)
	@ResponseBody
	public List<GroupType> groupTypeList()
	{

		return landRecordsService.findAllGroupType();
	}



	@RequestMapping(value = "/viewer/personbyusin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> personByUsin(@PathVariable Long id)
	{
		return landRecordsService.findAllSocialTenureByUsin(id);
	}


	@RequestMapping(value = "/viewer/ccrodownload/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> ccrodownload(@PathVariable Long id){

		List<SourceDocument> doc = landRecordsService.findMultimediaByUsin(id);
		ArrayList<String> ccrodoc= new ArrayList<String>();
		ArrayList<Long> naturalGid= landRecordsService.findOwnerPersonByUsin(id);

		try {
			for (int i = 0; i < doc.size(); i++) {
				if((naturalGid.contains(doc.get(i).getPerson_gid()))){
					String fileName = doc.get(i).getScanedSourceDoc();
					if(fileName.toLowerCase().contains("jpg"))

					{
						String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase();
						String filepath=doc.get(i).getLocScannedSourceDoc()+File.separator+doc.get(i).getGid()+"."+fileType;
						NaturalPerson naturalpersontmp = landRecordsService.naturalPersonById(doc.get(i).getPerson_gid()).get(0);
						naturalGid.remove(doc.get(i).getPerson_gid());
						String name=naturalpersontmp.getFirstName()+" "+naturalpersontmp.getMiddleName()+" "+naturalpersontmp.getLastName();
						//doc.get(i).getPerson_gid();
						ccrodoc.add(name);
						ccrodoc.add(filepath);

					}
				}
			}

			if(naturalGid.size()!=0)
			{
				for (int i = 0; i < naturalGid.size(); i++) {
					NaturalPerson naturalpersontmp = landRecordsService.naturalPersonById(naturalGid.get(i)).get(0);
					String name=naturalpersontmp.getFirstName()+" "+naturalpersontmp.getMiddleName()+" "+naturalpersontmp.getLastName();
					ccrodoc.add(name);
					ccrodoc.add("");				
				}

			}
		} catch (Exception e) {

			logger.error(e);
		}

		return ccrodoc;

	}



	@RequestMapping(value = "/viewer/getpersontype/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Integer getpersontypeByUsin(@PathVariable Long id)
	{


		List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);



		if(socialtenuretmp.size()>0)
		{
			if(socialtenuretmp.size()>1)
			{
				return 1;
			}

			else if(socialtenuretmp.get(0).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==1)
				return 0;

			else if(socialtenuretmp.get(0).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==2)
				return 2;

		}
		return null;
	}

	@RequestMapping(value = "/viewer/getinstitutename/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String Institute(@PathVariable Long id)
	{

		List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);
		long gid=socialtenuretmp.get(0).getPerson_gid().getPerson_gid();

		List<NonNaturalPerson> nonNaturalpersonList;
		try {
			nonNaturalpersonList = landRecordsService.nonnaturalPersonById(gid);
			return nonNaturalpersonList.get(0).getInstitutionName();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}


	@RequestMapping(value = "/viewer/ccroinstituteperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String InstitutePerson(@PathVariable Long id)
	{

		List<SocialTenureRelationship> socialtenuretmp = landRecordsService.findAllSocialTenureByUsin(id);
		long gid=socialtenuretmp.get(0).getPerson_gid().getPerson_gid();

		List<NonNaturalPerson> nonNaturalpersonList = landRecordsService.nonnaturalPersonById(gid);
		long naturalid= nonNaturalpersonList.get(0).getPoc_gid();

		NaturalPerson naturaltmp=landRecordsService.naturalPersonById(naturalid).get(0);
		String name= naturaltmp.getFirstName()+" "+naturaltmp.getMiddleName()+" "+naturaltmp.getLastName();
		return name;

	}



	@RequestMapping(value = "/viewer/personadmin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> PersonAdmin(@PathVariable Long id)
	{

		List<String> adminName= new ArrayList<String>();

		try {
			List<Long> adminList= landRecordsService.getAdminId(id);

			for (Long adminID: adminList) {

				PersonAdministrator personadmin=	landRecordsService.getAdministratorById(adminID);
				SourceDocument admindoc=landRecordsService.getdocumentByAdminId(adminID);
				String name= personadmin.getFirstname()+" "+personadmin.getMiddlename()+" "+personadmin.getLastname();
				String fileName=admindoc.getScanedSourceDoc();
				String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase();
				String filepath=admindoc.getLocScannedSourceDoc()+File.separator+admindoc.getGid()+"."+fileType;
				adminName.add(name);
				adminName.add(filepath);
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return adminName;
	}



	@RequestMapping(value = "/viewer/spatialunit/{project}", method = RequestMethod.GET)
	@ResponseBody
	public Integer totalRecords(Principal principal,@PathVariable String project)
	{


		String loggeduser = principal.getName();
		User user = userService.findByUniqueName(loggeduser);
		String defaultProject = user.getDefaultproject();
		if(project!=null){
			defaultProject=project;
		}

		//return landRecordsService.findAllSpatialUnit(defaultProject);
		return landRecordsService.AllSpatialUnitTemp(defaultProject);

	}

	@RequestMapping(value = "/viewer/changeccrostatus/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean changeStatus(@PathVariable Long id,Principal principal)
	{
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		long userid=user.getId();
		List<SpatialUnitTable> spatialunit = landRecordsService.findSpatialUnitbyId(id);
		if(spatialunit.get(0).getStatus().getWorkflowStatusId()==4)
		{
			return landRecordsService.updateCCRO(id,userid);	
		}
		else{

			return false;
		}

	}

	@RequestMapping(value = "/viewer/landrecords/{project}", method = RequestMethod.GET)
	@ResponseBody
	public ProjectTemp list(@PathVariable String project)
	{
		return 	projectService.findProjectTempByName(project);	
	}


	@RequestMapping(value = "/viewer/spatialunit/{project}/{startfrom}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTemp> spatialUnitList(@PathVariable String project,@PathVariable Integer startfrom)
	{
		return landRecordsService.findAllSpatialUnitTemp(project,startfrom);
	}

	@RequestMapping(value = "/viewer/spatialfalse/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean spatialUnitList(@PathVariable Long id)
	{
		return landRecordsService.deleteSpatialUnit(id);
	}


	@RequestMapping(value = "/viewer/shownatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> showDeletedNatural(@PathVariable Long id)
	{
		ArrayList<SocialTenureRelationship> objtemp=new ArrayList<SocialTenureRelationship>();
		List<SocialTenureRelationship> obj = landRecordsService.showDeletedPerson(id);
		for (int i = 0; i < obj.size(); i++) {
			if(obj.get(i).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==1)
				objtemp.add(obj.get(i));
		}

		return objtemp;
	}

	@RequestMapping(value = "/viewer/addnatural/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean addDeletedNatural(@PathVariable Long gid)
	{
		return landRecordsService.addDeletedPerson(gid);
	}

	@RequestMapping(value = "/viewer/shownonnatural/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> showDeletedNonNatural(@PathVariable Long id)
	{
		ArrayList<SocialTenureRelationship>objtemp=new ArrayList<SocialTenureRelationship>();
		List<SocialTenureRelationship> obj = landRecordsService.showDeletedPerson(id);
		for (int i = 0; i < obj.size(); i++) {
			if(obj.get(i).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==2)
				objtemp.add(obj.get(i));
		}

		return objtemp;
	}

	@RequestMapping(value = "/viewer/addnonnatural/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean addDeletedNonNatural(@PathVariable Long gid)
	{
		return landRecordsService.addDeletedPerson(gid);
	}


	@RequestMapping(value = "/viewer/landrecords/search/{project}", method = RequestMethod.POST)
	@ResponseBody
	public Integer searchListSize(HttpServletRequest request, HttpServletResponse response, Principal principal,@PathVariable String project)
	{		
		String UkaNumber="";
		String dateto="";
		String datefrom="";
		long status=0;
		String UsinStr="";

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projname = user.getDefaultproject();	

		if(project!="")
			projname=project;

		try {
			/*try {
				Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_id");
			} catch (Exception e) {
				logger.error(e);
			}*/
			try {
				UsinStr = ServletRequestUtils.getRequiredStringParameter(request, "usinstr_id");
			} catch (Exception e) {
				logger.error(e);
			}
			try {
				UkaNumber = ServletRequestUtils.getRequiredStringParameter(request, "uka_id");
			} catch (Exception e) {
				logger.error(e);
			}

			try {
				dateto= ServletRequestUtils.getRequiredStringParameter(request, "to_id");


			} catch (Exception e) {	
				logger.error(e);
			}

			try {
				datefrom= ServletRequestUtils.getRequiredStringParameter(request, "from_id");

			} catch (Exception e) {	
				logger.error(e);
			}

			try {
				status = ServletRequestUtils.getRequiredLongParameter(request, "status_id");
			} catch (Exception e) {

				logger.error(e);
			}

			if(dateto=="")

			{

				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
				Date now = new Date();
				dateto = sdfDate.format(now);
			}
			else if(dateto!="")
			{
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
				Date date = sdfDate.parse(dateto);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				dateto = sdfDate.format(calendar.getTime());
			}
			if(datefrom=="")

			{

				datefrom="1990-01-01 00:00:00";
			}

			return landRecordsService.searchListSize(UsinStr,UkaNumber,projname,dateto,datefrom,status);


		} catch (Exception e) {

			logger.error(e);
			return 0;

		}

	}

	@RequestMapping(value = "/viewer/adminfetch/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<PersonAdministrator> adminList(@PathVariable Long id)
	{
		List<Long> adminList= landRecordsService.getAdminId(id);
		List<PersonAdministrator> personadminList= new ArrayList<PersonAdministrator>();
		if(adminList!=null){
			for (Long adminID: adminList) {

				PersonAdministrator personadmin=landRecordsService.getAdministratorById(adminID);
				if(personadmin.getActive())
					personadminList.add(personadmin);
			}
		}
		return personadminList;
	}



	@RequestMapping(value = "/viewer/landRecords/check/naturalimage/{person_gid}/{admin_id}", method = RequestMethod.GET)
	@ResponseBody
	public String naturalImageUrl(@PathVariable Long person_gid,@PathVariable Long admin_id )
	{
		SourceDocument sourcetemp=new SourceDocument();
		if(person_gid!=0)
			sourcetemp=landRecordsService.getdocumentByPerson(person_gid);

		else if(admin_id!=0)
			sourcetemp=landRecordsService.getdocumentByAdminId(admin_id);

		if(sourcetemp!=null)
		{

			return sourcetemp.getLocScannedSourceDoc()+"/"+sourcetemp.getGid()+".jpg";
		}
		else{
			return "";	
		}

	}


	@RequestMapping(value = "/viewer/upload/personimage/", method = RequestMethod.POST)
	@ResponseBody
	public String uploadNaturalImage(MultipartHttpServletRequest request, HttpServletResponse response , Principal principal)
	{	
		try {


			Long usin=0l;
			String document_name ="";
			String document_comments ="";
			String projectName="";
			long person_gid=0;
			long admin_id=0;

			Iterator<String> file = request.getFileNames();


			usin=ServletRequestUtils.getRequiredLongParameter(request, "Usin_Upload");
			document_name= ServletRequestUtils.getRequiredStringParameter(request, "document_name");
			try {
				projectName= ServletRequestUtils.getRequiredStringParameter(request,"proj_name");
			} catch (Exception e) {
				logger.error(e);

			}

			try {
				person_gid= ServletRequestUtils.getRequiredLongParameter(request,"person_gid");
			} catch (Exception e) {
				logger.error(e);

			}
			try {
				admin_id= ServletRequestUtils.getRequiredLongParameter(request,"admin_id");
			} catch (Exception e) {
				logger.error(e);

			}
			/*try {
				document_comments= ServletRequestUtils.getRequiredStringParameter(request, "document_comments");
			} catch (Exception e2) {
				logger.error(e2);

			}*/




			byte[] document = null;
			while(file.hasNext()) 
			{
				String fileName = file.next();
				MultipartFile mpFile = request.getFile(fileName);

				String originalFileName = mpFile.getOriginalFilename();
				SourceDocument sourceDocument = new SourceDocument();
				if(person_gid!=0)
					sourceDocument = landRecordsService.getdocumentByPerson(person_gid);
				if(admin_id!=0)
					sourceDocument = landRecordsService.getdocumentByAdminId(admin_id);
				if(sourceDocument==null)
					sourceDocument = new SourceDocument();
				String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1,originalFileName.length()).toLowerCase();

				if (originalFileName != "") {
					document = mpFile.getBytes();
				}
				String uploadFileName = null;

				String tmpDirPath=request.getSession().getServletContext().getRealPath(File.separator);

				String outDirPath=tmpDirPath.replace("mast", "")+"resources"+File.separator+"documents"+File.separator+projectName+File.separator+"multimedia";


				File outDir=new File(outDirPath);
				boolean exists = outDir.exists();
				if (!exists) {
					boolean success = (new File(outDirPath)).mkdirs();

				}


				sourceDocument.setScanedSourceDoc(originalFileName);

				uploadFileName=("resources"+File.separator+"documents"+File.separator+projectName+File.separator+"multimedia");

				sourceDocument.setLocScannedSourceDoc(uploadFileName);
				if(person_gid!=0)
					sourceDocument.setPerson_gid(person_gid);
				if(admin_id!=0)
					sourceDocument.setAdminid(admin_id);
				sourceDocument.setEntity_name(document_name);
				sourceDocument.setComments(document_comments);
				sourceDocument.setActive(true);
				sourceDocument.setRecordation(new Date());
				sourceDocument.setUsin(usin);

				sourceDocument =  landRecordsService.saveUploadedDocuments(sourceDocument);

				Integer id = sourceDocument.getGid();

				try {
					FileOutputStream uploadfile = new FileOutputStream(outDirPath+File.separator+ id+"."+fileExtension);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
					return "Success";

				} 

				catch (Exception e) {

					logger.error(e);
					return "Error";
				}

			} 

		}catch (Exception e) {
			logger.error(e);
			return "Error";
		}		
		return "false";
	}



	@RequestMapping(value = "/viewer/administrator/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PersonAdministrator findadminByID(@PathVariable Long id)
	{
		return landRecordsService.getAdministratorById(id);
	}



	@RequestMapping(value = "/viewer/landrecords/updateadmin/{id}", method = RequestMethod.POST)
	@ResponseBody
	public PersonAdministrator updateAdmin(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id)
	{
		Long adminid=0L;
		String first_name="";
		String middle_name="";
		String last_name="";
		long genderid=0;
		int maritalid=0;
		int age=0;
		String citizenship="";
		int resident;
		String mobile_number="";
		String address="";
		Long usin=0L;

		PersonAdministrator personobj= new PersonAdministrator();
		try {
			try {
				adminid = ServletRequestUtils.getRequiredLongParameter(request, "adminId");
			} catch (Exception e2) {
				logger.error(e2);
			}
			first_name = ServletRequestUtils.getRequiredStringParameter(request, "admin_fname");
			middle_name = ServletRequestUtils.getRequiredStringParameter(request, "admin_mname");
			last_name = ServletRequestUtils.getRequiredStringParameter(request, "admin_lname");
			mobile_number = ServletRequestUtils.getRequiredStringParameter(request, "admin_mobile");

			try {
				age= ServletRequestUtils.getRequiredIntParameter(request, "admin_age");
			} catch (Exception e1) {
				logger.error(e1);
			}
			maritalid= ServletRequestUtils.getRequiredIntParameter(request, "admin_marital_status");
			address = ServletRequestUtils.getRequiredStringParameter(request, "admin_address");

			genderid = ServletRequestUtils.getRequiredLongParameter(request, "admin_gender");

			ServletRequestUtils.getRequiredStringParameter(request, "projectname_key");

			resident= ServletRequestUtils.getRequiredIntParameter(request, "admin_resident");
			citizenship= ServletRequestUtils.getRequiredStringParameter(request, "admin_citizenship");
			usin= ServletRequestUtils.getRequiredLongParameter(request, "admin_usin");

			if(adminid!=0){
				personobj.setAdminid(adminid);
			}
			personobj.setFirstname(first_name);
			personobj.setMiddlename(middle_name);
			personobj.setLastname(last_name);
			personobj.setPhonenumber(mobile_number);
			personobj.setCitizenship(citizenship);
			personobj.setAddress(address);
			personobj.setActive(true);
			if(age!=0){
				personobj.setAge(age);
			}
			if(genderid!=0)
			{
				Gender genderIdObj = landRecordsService.findGenderById(genderid);
				personobj.setGender(genderIdObj);

			}

			personobj.setResident(false);
			if(resident==1)
			{
				personobj.setResident(true);

			}

			if(maritalid!=0){

				MaritalStatus maritalIdObj = landRecordsService.findMaritalById(maritalid);
				personobj.setMaritalstatus(maritalIdObj);
			}
			//landRecordsService.editAdmin(personobj);

			//For updating Spacial Unit Administrator

			if (adminid!=0) {
				return 	landRecordsService.editAdmin(personobj);

			}

			else
			{

				SpatialunitPersonadministrator spaobj = new SpatialunitPersonadministrator();
				spaobj.setPersonAdministrator(personobj);
				spaobj.setUsin(usin);
				landRecordsService.updateSpatialAdmin(spaobj);
				return personobj;
			}



			//For updating in Attribute Values Table
			/*try {
				userDataService.updateNaturalPersonAttribValues(personobj,project_name);
			} catch (Exception e) {
				logger.error(e);
			}*/
		} catch (ServletRequestBindingException e) {
			logger.error(e);
			return null;
		}

	}

	@RequestMapping(value = "/viewer/landrecords/deleteadmin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean  deleteAdmin(@PathVariable Long id)
	{

		return landRecordsService.deleteAdminById(id);


	}


	@RequestMapping(value = "/viewer/existingadmin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<PersonAdministrator> deletedAdminList(@PathVariable Long id)
	{
		List<Long> adminList= landRecordsService.getAdminId(id);
		List<PersonAdministrator> personadminList= new ArrayList<PersonAdministrator>();
		if(adminList!=null){
			for (Long adminID: adminList) {

				PersonAdministrator personadmin=landRecordsService.getAdministratorById(adminID);
				if(!personadmin.getActive())
					personadminList.add(personadmin);
			}
		}
		return personadminList;
	}

	@RequestMapping(value = "/viewer/addadmin/{adminId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean existingadminList(@PathVariable Long adminId)
	{
		return landRecordsService.addAdminById(adminId);



	}


	@RequestMapping(value = "/viewer/naturalcustom/{project}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> naturalCustom(@PathVariable String project)
	{

		return landRecordsService.findnaturalCustom(project);

	}
	
	@RequestMapping(value = "/viewer/personofinterest/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> personWithInterest(@PathVariable Long usin)
	{
		ArrayList<String> tempList=new ArrayList<String>();
		List<SpatialunitPersonwithinterest> personList = landRecordsService.findpersonInterestByUsin(usin);
		for (int i = 0; i < personList.size(); i++) {
			tempList.add(personList.get(i).getPersonName());
			
		}
		return tempList;
	}
	@RequestMapping(value = "/viewer/autogenerateuka/{project}", method = RequestMethod.GET)
	@ResponseBody
	public boolean autogenerateUKA(@PathVariable String project)
	{
		/*List<SpatialUnitTemp> spatialunit =landRecordsService.findSpatialUnitforUKAGeneration(project);
		String village=projectService.findProjectByName(project).getProjectAreas().get(0).getVillage_code();
		
		try {
			for (int i = 0; i < spatialunit.size(); i++) {
				SpatialUnitTemp spatialunitmp=spatialunit.get(i);
				spatialunitmp.setPropertyno(village+"/"+spatialunitmp.getHamlet_Id().getHamletCode()+"/"+(spatialunitmp.getHamlet_Id().getCount()+1));
				spatialunitmp.getHamlet_Id().setCount(spatialunitmp.getHamlet_Id().getCount()+1);
				projectService.saveHamlet(spatialunitmp.getHamlet_Id());
				landRecordsService.addSpatialUnitTemp(spatialunitmp);
				
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return true;*/
		List<ProjectHamlet> projecthamlets=projectService.findHamletByProject(project);
		String village=projectService.findProjectByName(project).getProjectAreas().get(0).getVillage_code();
		try {
			for (int i = 0; i < projecthamlets.size(); i++) {
				String hamletCode=projecthamlets.get(i).getHamletCode();
				int count=0;
				List<Long> usinList =landRecordsService.findUsinforUKAGeneration(project,hamletCode);
				
				for (int j = 0; j < usinList.size(); j++) {
					String uka=village+"/"+hamletCode+"/"+(projecthamlets.get(i).getCount()+j+1);
					
					count=projecthamlets.get(i).getCount()+j+1;
					landRecordsService.updateUKAnumber(usinList.get(j),uka);
					
				}
				if(count!=0)
				{
					projecthamlets.get(i).setCount(count);
					projectService.saveHamlet(projecthamlets.get(i));
						
					
				}
				
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	
	return true;
}
}