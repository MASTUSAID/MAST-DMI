package com.rmsi.mast.viewer.web.mvc;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.impl.Image;
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

import com.rmsi.mast.custom.dto.CcroDto;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Citizenship;
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
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonadministrator;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.domain.fetch.Usertable;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.service.ProjectService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.viewer.service.LandRecordsService;
import com.rmsi.mast.studio.domain.Citizenship;

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

	@RequestMapping(value = "/viewer/landrecords/tenuretype/", method = RequestMethod.GET)
	@ResponseBody
	public List<ShareType> tenureList()
	{

		return landRecordsService.findAllTenureList();



	}


	@RequestMapping(value = "/viewer/landrecords/socialtenure/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> socialTenureList(@PathVariable Long id)
	{

		return landRecordsService.findAllSocialTenureByUsin(id);
	}


	@RequestMapping(value = "/viewer/landrecords/spatialunitlist/", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> allspatialUnitList()
	{

		return landRecordsService.findAllSpatialUnitlist();

	}

	@RequestMapping(value = "/viewer/landrecords/editattribute/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> editAttribute(@PathVariable Long id)
	{

		return landRecordsService.findSpatialUnitbyId(id);

	}


	/*@RequestMapping(value = "/viewer/landrecords/updateuka", method = RequestMethod.POST)
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
	}*/




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


			SpatialUnitTable spatialUnit=landRecordsService.findSpatialUnitbyId(Usin).get(0);	


			//spatialUnit.setUsin(Usin);
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
			if(uka_propertyno!="")
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
			/*else if(dateto!="")
			{
				SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat sdfDate1 = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
				Date date = sdfDate.parse(dateto);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				dateto = sdfDate1.format(calendar.getTime());
			}*/
			if(datefrom=="")

			{

				datefrom="1990-01-01 00:00:00";
			}
			/*else if(datefrom!="")
			{
				SimpleDateFormat sdfDate1 = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
				SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");//dd/MM/yyyy
				
				Date date = sdfDate.parse(datefrom);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				datefrom = sdfDate1.format(calendar.getTime());
			}*/
			return landRecordsService.search(UsinStr,UkaNumber,projname,dateto,datefrom,status,startpos);


		} catch (Exception e) {

			logger.error(e);
			return null;

		}

	}

	@RequestMapping(value = "/viewer/landrecords/status/", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/naturalperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<NaturalPerson> naturalPerson(@PathVariable Long id)
	{

		return landRecordsService.naturalPersonById(id);



	}


	@RequestMapping(value = "/viewer/landrecords/nonnaturalperson/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<NonNaturalPerson> nonnaturalPerson(@PathVariable Long id)
	{
		return landRecordsService.nonnaturalPersonById(id);

	}

	@RequestMapping(value = "/viewer/landrecords/gendertype/", method = RequestMethod.GET)
	@ResponseBody
	public List<Gender> genderList()
	{
		return landRecordsService.findAllGenders();

	}

	@RequestMapping(value = "/viewer/landrecords/maritalstatus/", method = RequestMethod.GET)
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
		//	String tenure="";
		int maritalid=0;
		long persontype=1;
		String first_name="";
		String middle_name="";
		String last_name="";
		String mobile_number="";
		int age = 0;
		/*String occupation="";
		Long education=0l;*/
		int length_natural=0;
		String project_name="";

		/*int owner_natural;
		int resd_village;*/
		/*String admin="";*/
		long citizenship=0;
		int newnatural_length=0;
		long usin=0;
		long person_subType=0;


		NaturalPerson naturalPerson= new NaturalPerson();
		Citizenship citizenshipobj=new Citizenship();
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
			//name = ServletRequestUtils.getRequiredStringParameter(request, "name");
			first_name = ServletRequestUtils.getRequiredStringParameter(request, "fname");
			middle_name = ServletRequestUtils.getRequiredStringParameter(request, "mname");
			last_name = ServletRequestUtils.getRequiredStringParameter(request, "lname");
			mobile_number = ServletRequestUtils.getRequiredStringParameter(request, "mobile_natural");
			/*occupation = ServletRequestUtils.getRequiredStringParameter(request, "occ");
			education = ServletRequestUtils.getRequiredLongParameter(request, "edu");*/
			try {
				age= ServletRequestUtils.getRequiredIntParameter(request, "age");
			} catch (Exception e1) {
				logger.error(e1);
			}
			maritalid= ServletRequestUtils.getRequiredIntParameter(request, "marital_status");

			//occupational_age= ServletRequestUtils.getRequiredIntParameter(request, "occ_age");
			genderid = ServletRequestUtils.getRequiredLongParameter(request, "gender");
			//tenure = ServletRequestUtils.getRequiredStringParameter(request, "tenure");
			length_natural= ServletRequestUtils.getRequiredIntParameter(request, "natual_length");
			project_name= ServletRequestUtils.getRequiredStringParameter(request, "projectname_key");

			//owner_natural= ServletRequestUtils.getRequiredIntParameter(request, "owner_nat");
			//resd_village= ServletRequestUtils.getRequiredIntParameter(request, "resident");
			/*admin= ServletRequestUtils.getRequiredStringParameter(request, "administrator");*/
			citizenship= ServletRequestUtils.getRequiredLongParameter(request, "citizenship");

			try {
				newnatural_length=ServletRequestUtils.getRequiredIntParameter(request, "newnatural_length");
			} catch (Exception e) {
				logger.error(e);
			}

			person_subType=ServletRequestUtils.getRequiredLongParameter(request, "person_subType");

			if(id!=0){
				naturalPerson=landRecordsService.naturalPersonById(id).get(0);

			}
			naturalPerson.setAlias(first_name);
			//naturalPerson.setTenure_Relation(tenure);
			naturalPerson.setFirstName(first_name);
			naturalPerson.setMiddleName(middle_name);
			naturalPerson.setLastName(last_name);
			naturalPerson.setMobile(mobile_number);
			//naturalPerson.setOccupation(occupation);
			naturalPerson.setOwner(false);
			 
			/*if(owner_natural==1){
				naturalPerson.setOwner(true);	
			}

			naturalPerson.setResident_of_village(false);
			if(resd_village==1)
			{
				naturalPerson.setResident_of_village(true);

			}*/
			//naturalPerson.setAdministator(admin);
			if(citizenship!=0){
				citizenshipobj= landRecordsService.findcitizenship(citizenship);
				naturalPerson.setCitizenship_id(citizenshipobj);
			}
				
			


		/*	if(education!=0)
			{
				EducationLevel educationObj = landRecordsService.findEducationById(education);
				naturalPerson.setEducation(educationObj);

			}*/
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

			if(person_subType!=0)
			{

				PersonType personsubTypeObj = landRecordsService.findPersonTypeById(person_subType);
				naturalPerson.setPersonSubType(personsubTypeObj);

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
			try {
				userDataService.updateNaturalPersonAttribValues(naturalobj,project_name);
			} catch (Exception e) {
				logger.error(e);
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
		long usin=0;
		long persontype=0;
		int occid=0;
		int tenureclassid=0;
		int resident_tenure=0;
	
		String project_tenure="";

		//SocialTenureRelationship socialTenureRelationship= new SocialTenureRelationship();

		try {
			id = ServletRequestUtils.getRequiredIntParameter(request, "tenure_key");
			type = ServletRequestUtils.getRequiredLongParameter(request, "tenure_type");
			//tenureclassid = ServletRequestUtils.getRequiredIntParameter(request, "tenureclass_id");
			usin = ServletRequestUtils.getRequiredLongParameter(request, "usin");
			persontype= ServletRequestUtils.getRequiredLongParameter(request, "person_gid");
			length =ServletRequestUtils.getRequiredIntParameter(request, "tenure_length");
			project_tenure=ServletRequestUtils.getRequiredStringParameter(request, "projectname_key3");
			resident_tenure=ServletRequestUtils.getRequiredIntParameter(request, "tenure_resident");
			List<SocialTenureRelationship> socialTenuretmplst = landRecordsService.findAllSocialTenureByUsin(usin);


			if(length>0)
			{

				for (int i = 0; i <length; i++) {
					long uid=0l;
					for (int j = 0; j < socialTenuretmplst.size(); j++) {

						StringBuilder sb = new StringBuilder();
						sb.append("alias");
						sb.append(i);
						String alias=sb.toString();
						Long value_key=0l;


						value_key=ServletRequestUtils.getRequiredLongParameter(request,"alias_tenure_key"+i);
						Long attributeKey=0L;
						if(value_key!=0){

							AttributeValues attributefetch = landRecordsService.getAttributeValue(value_key);
							uid=attributefetch.getUid();
							attributeKey = landRecordsService.getAttributeKey(socialTenuretmplst.get(j).getGid(),uid);
						}

						alias=ServletRequestUtils.getRequiredStringParameter(request,"alias_tenure"+i);
						if(attributeKey!=0)
						{
							landRecordsService.updateAttributeValues(attributeKey,alias);
						}
					}	
				}


			}


			for (int i = 0; i < socialTenuretmplst.size(); i++) {

				SocialTenureRelationship socialTenureRelationship=landRecordsService.findSocialTenureByGid(socialTenuretmplst.get(i).getGid()).get(0);
				//socialTenureRelationship.setGid(socialTenuretmplst.get(i).getGid());
				if(type!=0){

					ShareType tenuretypeobj = landRecordsService.findTenureType(type);
					socialTenureRelationship.setShare_type(tenuretypeobj);


				}
				
				socialTenureRelationship.setResident(false);
				TenureClass tenureclassobj = landRecordsService.findtenureClasseById(1);
				if(resident_tenure==2)
				{
					socialTenureRelationship.setResident(true);
					tenureclassobj = landRecordsService.findtenureClasseById(2);
				}
				
				socialTenureRelationship.setTenureclass_id(tenureclassobj);
				
				

				//For Updating tenure in AttributeValues
				try {
					userDataService.updateTenureAttribValues(socialTenureRelationship, project_tenure);
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					landRecordsService.edittenure(socialTenureRelationship);
				} catch (Exception e) {
					logger.error(e);
					return false;
				}
			}
			return true;

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

	@RequestMapping(value = "/viewer/landrecords/socialtenure/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> SocialTenurebyGidList(@PathVariable Integer id)
	{

		return landRecordsService.findSocialTenureByGid(id);

	}


	@RequestMapping(value = "/viewer/landrecords/multimedia/edit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> MultimediaList(@PathVariable Long id)
	{

		return landRecordsService.findMultimediaByUsin(id);

	}

	@RequestMapping(value = "/viewer/landrecords/multimedia/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SourceDocument> MultimediaGidList(@PathVariable Long id)
	{

		return landRecordsService.findMultimediaByGid(id);

	}

	@RequestMapping(value = "/viewer/landrecords/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean  deleteMultimedia(@PathVariable Long id)
	{

		return landRecordsService.deleteMultimedia(id);


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

	@RequestMapping(value = "/viewer/landrecords/educationlevel/", method = RequestMethod.GET)
	@ResponseBody
	public List<EducationLevel> educationList()
	{

		return landRecordsService.findAllEducation();

	}

	@RequestMapping(value = "/viewer/landrecords/landusertype/", method = RequestMethod.GET)
	@ResponseBody
	public List<LandUseType> landUserList()
	{
		return landRecordsService.findAllLanduserType();

	}

	@RequestMapping(value = "/viewer/landrecords/tenureclass/", method = RequestMethod.GET)
	@ResponseBody
	public List<TenureClass> tenureclassList()
	{

		return landRecordsService.findAllTenureClass();
	}

	@RequestMapping(value = "/viewer/landrecords/ukanumber/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String ukaNumberByUsin(@PathVariable Long id)
	{

		return landRecordsService.findukaNumberByUsin(id);
	}
	@RequestMapping(value = "/viewer/landrecords/hamletname/{project}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectHamlet> findAllHamlet(@PathVariable String project)
	{

		return projectService.findHamletByProject(project);
	}

	@RequestMapping(value = "/viewer/landrecords/occupancytype/", method = RequestMethod.GET)
	@ResponseBody
	public List<OccupancyType> OccTypeList()
	{

		return landRecordsService.findAllOccupancyTypes();
	}

	@RequestMapping(value = "/viewer/landrecords/attributecategory/", method = RequestMethod.GET)
	@ResponseBody
	public List<AttributeCategory> attribList()
	{

		return landRecordsService.findAllAttributeCategories();
	}


	@RequestMapping(value = "/viewer/landrecords/getCCRO/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/attributedata/{categoryid}/{parentid}", method = RequestMethod.GET)
	@ResponseBody
	public List<AttributeValuesFetch> attributeDataList(@PathVariable long categoryid,@PathVariable long parentid)
	{

		return landRecordsService.findAttributelistByCategoryId(parentid,categoryid);

	}

	@RequestMapping(value = "/viewer/landrecords/naturalpersondata/{personid}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/uploadweb/", method = RequestMethod.POST)
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
				uploadFileName=("resources/documents/"+projectName+"/webupload");
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

					//use for Compression if needed
					// return compressPicture(outDirPath,id,fileExtension);
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


	@RequestMapping(value = "/viewer/landrecords/sourcedocument/{usinId}", method = RequestMethod.GET)
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


	@RequestMapping(value = "/viewer/landrecords/download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void  download(@PathVariable Long id, HttpServletRequest request,HttpServletResponse response){

		SourceDocument doc = landRecordsService.getDocumentbyGid(id);
		String fileName = doc.getScanedSourceDoc();
		String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase();
		//Object path_temp = request.getSession().getServletContext().getRealPath(File.separator);
		String filepath=request.getSession().getServletContext().getRealPath(File.separator).replace("mast", "")+doc.getLocScannedSourceDoc()+File.separator+id+"."+fileType;
		Path path = Paths.get(filepath);
		try 
		{
			byte[] data = Files.readAllBytes(path);

			response.setContentLength(data.length);
			OutputStream out = response.getOutputStream();
			out.write(data);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error(e);
		}



	}


	@RequestMapping(value = "/viewer/landrecords/Adjuticator/{id}", method = RequestMethod.GET)
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


	@RequestMapping(value = "/viewer/landrecords/projectarea/", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectArea> updateFinal(Principal principal){

		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		String projectName=user.getDefaultproject();
		return landRecordsService.findProjectArea(projectName);

	}


	@RequestMapping(value = "/viewer/landrecords/soilquality/", method = RequestMethod.GET)
	@ResponseBody
	public List<SoilQualityValues> soilQualityList()
	{

		return landRecordsService.findAllsoilQuality();
	}

	@RequestMapping(value = "/viewer/landrecords/slope/", method = RequestMethod.GET)
	@ResponseBody
	public List<SlopeValues> slopeList()
	{

		return landRecordsService.findAllSlopeValues();

	}

	@RequestMapping(value = "/viewer/landrecords/typeofland/", method = RequestMethod.GET)
	@ResponseBody
	public List<LandType> landTypeList()
	{

		return landRecordsService.findAllLandType();
	}

	@RequestMapping(value = "/viewer/landrecords/grouptype/", method = RequestMethod.GET)
	@ResponseBody
	public List<GroupType> groupTypeList()
	{

		return landRecordsService.findAllGroupType();
	}



	@RequestMapping(value = "/viewer/landrecords/personbyusin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SocialTenureRelationship> personByUsin(@PathVariable Long id)
	{
		return landRecordsService.findAllSocialTenureByUsin(id);
	}


	@RequestMapping(value = "/viewer/landrecords/ccrodownload/{id}", method = RequestMethod.GET)
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



	@RequestMapping(value = "/viewer/landrecords/getpersontype/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/getinstitutename/{id}", method = RequestMethod.GET)
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


	@RequestMapping(value = "/viewer/landrecords/ccroinstituteperson/{id}", method = RequestMethod.GET)
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



	@RequestMapping(value = "/viewer/landrecords/personadmin/{id}", method = RequestMethod.GET)
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



	@RequestMapping(value = "/viewer/landrecords/spatialunit/{project}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/changeccrostatus/{id}", method = RequestMethod.GET)
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


	@RequestMapping(value = "/viewer/landrecords/spatialunit/{project}/{startfrom}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTemp> spatialUnitList(@PathVariable String project,@PathVariable Integer startfrom)
	{
		return landRecordsService.findAllSpatialUnitTemp(project,startfrom);
	}

	@RequestMapping(value = "/viewer/landrecords/spatialfalse/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean spatialUnitList(@PathVariable Long id)
	{
		return landRecordsService.deleteSpatialUnit(id);
	}


	@RequestMapping(value = "/viewer/landrecords/shownatural/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/addnatural/{gid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean addDeletedNatural(@PathVariable Long gid)
	{
		return landRecordsService.addDeletedPerson(gid);
	}

	@RequestMapping(value = "/viewer/landrecords/shownonnatural/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/addnonnatural/{gid}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/adminfetch/{id}", method = RequestMethod.GET)
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
	public List<String> naturalImageUrl(@PathVariable Long person_gid,@PathVariable Long admin_id )
	{
		SourceDocument sourcetemp=new SourceDocument();
		ArrayList<String> resulttmp=new ArrayList<String>();
		if(person_gid!=0)
			sourcetemp=landRecordsService.getdocumentByPerson(person_gid);

		else if(admin_id!=0)
			sourcetemp=landRecordsService.getdocumentByAdminId(admin_id);

		if(sourcetemp!=null && sourcetemp.isActive())
		{
			resulttmp.add(sourcetemp.getEntity_name());
			resulttmp.add(sourcetemp.getLocScannedSourceDoc()+"/"+sourcetemp.getGid()+".jpg");

			return resulttmp;
		}
		else{
			return resulttmp;	
		}

	}


	@RequestMapping(value = "/viewer/landrecords/upload/personimage/", method = RequestMethod.POST)
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
				SourceDocument sourceDocument1 = new SourceDocument();
				if(person_gid!=0)
					sourceDocument1 = landRecordsService.getdocumentByPerson(person_gid);
				if(admin_id!=0)
					sourceDocument1 = landRecordsService.getdocumentByAdminId(admin_id);
				if(sourceDocument1==null)
					sourceDocument1 = new SourceDocument();
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


				sourceDocument1.setScanedSourceDoc(originalFileName);

				uploadFileName=("resources/documents/"+projectName+"/multimedia");

				sourceDocument1.setLocScannedSourceDoc(uploadFileName);
				if(person_gid!=0)
					sourceDocument1.setPerson_gid(person_gid);
				if(admin_id!=0)
					sourceDocument1.setAdminid(admin_id);
				sourceDocument1.setEntity_name(document_name);
				sourceDocument1.setComments(document_comments);
				
				sourceDocument1.setActive(true);
				try {
					sourceDocument1.setRecordation(new Date());
					sourceDocument1.setUsin(usin);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sourceDocument1 =  landRecordsService.saveUploadedDocuments(sourceDocument1);

				Integer id = sourceDocument1.getGid();

				try {
					FileOutputStream uploadfile = new FileOutputStream(outDirPath+File.separator+ id+"."+fileExtension);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
					//use it for compression if needed
					// return compressPicture(outDirPath,id,fileExtension);
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



	@RequestMapping(value = "/viewer/landrecords/administrator/{id}", method = RequestMethod.GET)
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


	@RequestMapping(value = "/viewer/landrecords/existingadmin/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/viewer/landrecords/addadmin/{adminId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean existingadminList(@PathVariable Long adminId)
	{
		return landRecordsService.addAdminById(adminId);



	}


	@RequestMapping(value = "/viewer/landrecords/naturalcustom/{project}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> naturalCustom(@PathVariable String project)
	{

		return landRecordsService.findnaturalCustom(project);

	}

	@RequestMapping(value = "/viewer/landrecords/personofinterest/{usin}", method = RequestMethod.GET)
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
	@RequestMapping(value = "/viewer/landrecords/deceasedperson/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialunitDeceasedPerson> deceasedPerson(@PathVariable Long usin)
	{

		return landRecordsService.findDeceasedPersonByUsin(usin);

	}


	@RequestMapping(value = "/viewer/landrecords/autogenerateuka/{project}", method = RequestMethod.GET)
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
			return true;

		} catch (Exception e) {
			logger.error(e);
			return false;
		}


	}
	@RequestMapping(value = "/viewer/landrecords/updatehamlet/{project}", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUkaNum(HttpServletRequest request, HttpServletResponse response, @PathVariable String project)
	{
		long Usin = 0;
		long HamletId=0;

		//SpatialUnitTable spatialunit=new SpatialUnitTable();
		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request, "primeryky");
			HamletId = ServletRequestUtils.getRequiredLongParameter(request, "hamlet_id");

			String village=projectService.findProjectByName(project).getProjectAreas().get(0).getVillage_code();
			SpatialUnitTable spatialunit=landRecordsService.findSpatialUnitbyId(Usin).get(0);
			ProjectHamlet projectHamlet=projectService.findHamletById(HamletId);
			spatialunit.setHamlet_Id(projectHamlet);


			spatialunit.setPropertyno(village+"/"+projectHamlet.getHamletCode()+"/"+(projectHamlet.getCount()+1));
			projectHamlet.setCount(projectHamlet.getCount()+1);
			projectService.addHamlets(projectHamlet);
			return landRecordsService.update(spatialunit);


		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}
	}



	@RequestMapping(value = "/viewer/landrecords/personwithinterest/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialunitPersonwithinterest> nxtTokin(@PathVariable Long usin)
	{

		List<SpatialunitPersonwithinterest> personList = landRecordsService.findpersonInterestByUsin(usin);

		return personList;

	}



	@RequestMapping(value = "/viewer/landrecords/updatepwi", method = RequestMethod.POST)
	@ResponseBody
	public boolean updatepwi(HttpServletRequest request, HttpServletResponse response)
	{
		long Usin = 0;
		String name="";
		long id=0;


		SpatialunitPersonwithinterest spi= new SpatialunitPersonwithinterest();

		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_kin");
			name = ServletRequestUtils.getRequiredStringParameter(request, "name_kin");
			id = ServletRequestUtils.getRequiredLongParameter(request, "id_kin");

			if(id!=0)
				spi.setId(id);
			spi.setPersonName(name);
			spi.setUsin(Usin);



			return landRecordsService.addnxtTokin(spi);


		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}
	}
	@RequestMapping(value = "/viewer/landrecords/deletekin/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deletePersonWithInterest(@PathVariable Long id)
	{

		return landRecordsService.deletePersonWithInterest(id);

	}

	@RequestMapping(value = "/viewer/landrecords/personsubtype", method = RequestMethod.GET)
	@ResponseBody
	public List<PersonType> PersonTypelst()
	{

		return landRecordsService.AllPersonType();

	}
	@RequestMapping(value = "/viewer/landrecords/hamletbyusin/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpatialUnitTable> hamletName(@PathVariable long usin)
	{

		return landRecordsService.findSpatialUnitbyId(usin);

	}


	@RequestMapping(value = "/viewer/landrecords/updatedeceased", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateDeceasedPerson(HttpServletRequest request, HttpServletResponse response)
	{
		long Usin = 0;
		String firstname="";
		String middlename="";
		String lastname="";
		long id=0;


		SpatialunitDeceasedPerson spdeceased= new SpatialunitDeceasedPerson();

		try {
			Usin = ServletRequestUtils.getRequiredLongParameter(request, "usin_deceased");
			firstname = ServletRequestUtils.getRequiredStringParameter(request, "d_firstname");
			middlename = ServletRequestUtils.getRequiredStringParameter(request, "d_middlename");
			lastname = ServletRequestUtils.getRequiredStringParameter(request, "d_lastname");
			id = ServletRequestUtils.getRequiredLongParameter(request, "deceased_key");

			if(id!=0)
				spdeceased.setId(id);
			spdeceased.setFirstname(firstname);
			spdeceased.setMiddlename(middlename);
			spdeceased.setLastname(lastname);
			spdeceased.setUsin(Usin);



			return landRecordsService.saveDeceasedPerson(spdeceased);


		} catch (ServletRequestBindingException e) {

			logger.error(e);
			return false;

		}
	}
	@RequestMapping(value = "/viewer/landrecords/deletedeceased/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteDeceasedPerson(@PathVariable Long id)
	{

		return landRecordsService.deleteDeceasedPerson(id);

	}


	@RequestMapping(value = "/viewer/landrecords/validator/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String personValidator(@PathVariable Long id)
	{

		List<SocialTenureRelationship> socialTenureRelationshiptmp=landRecordsService.findAllSocialTenureByUsin(id);
		List<SpatialunitPersonwithinterest> spatialunitPersonwithinteresttmp= landRecordsService.findpersonInterestByUsin(id);
		List<SpatialunitDeceasedPerson> spatialunitDeceasedPersontmp=landRecordsService.findDeceasedPersonByUsin(id);
		List<String> personSubType=new ArrayList<String>();
		List<String> personAge=new ArrayList<String>();


		for (int i = 0; i < socialTenureRelationshiptmp.size(); i++) {

			if(socialTenureRelationshiptmp.get(i).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==1)

			{

				NaturalPerson naturalPerson=landRecordsService.naturalPersonById(socialTenureRelationshiptmp.get(i).getPerson_gid().getPerson_gid()).get(0);
				
				try {
					if(naturalPerson.getPersonSubType().getPerson_type_gid()==3){
						personSubType.add("Owner");
						if(naturalPerson.getAge()>=18)
						{
							personAge.add("Adult");
						}
						else{
							personAge.add("Minor");
						}
					}

					else if(naturalPerson.getPersonSubType().getPerson_type_gid()==4)
						personSubType.add("Admin");
					else if(naturalPerson.getPersonSubType().getPerson_type_gid()==5)
						personSubType.add("Guard");
				} catch (Exception e) {
					logger.error(e);
					return "Please specify person subtype";
				}
				

				if(personSubType.size()==socialTenureRelationshiptmp.size())
				{
					//Case-1 for Owner and Admin
					if(socialTenureRelationshiptmp.get(i).getShare_type().getGid()==4){
						if((!personSubType.contains("Guard") && personSubType.contains("Owner")) && personSubType.contains("Admin"))
						{
							if(spatialunitDeceasedPersontmp.size()>1)
								return "Only one deceased persons are allowed";
							/*if(personSubType.size()>2)
								return "Only two administrators are allowed";*/
							if(personAge.contains("Adult")){
								
								if(spatialunitDeceasedPersontmp.size()>0){
									/*if(spatialunitPersonwithinteresttmp.size()>0)
									{
										return "Success";

									}
									else{
										return "Add person of interest";

									}*/
									return "Success";
								}
								else{

									return "Add deceased person";
								}		
							}
							else{
								
								return "Owner is minor";
							}
							

						}
						else if((!personSubType.contains("Guard") && !personSubType.contains("Owner")) && personSubType.contains("Admin")){
							
							if(spatialunitDeceasedPersontmp.size()>0 && spatialunitDeceasedPersontmp.size()<=1)
							{
							
								return "Success";
							}
							else if(spatialunitDeceasedPersontmp.size()>1)
							{
								return "Only one deceased persons are allowed";
							}
							else if(spatialunitDeceasedPersontmp.size()==0)
							{
								return "Add deceased person";

							}
						}
						else{
							return "Tenure type is tenancy in probate,provide inputs accordingly";
						}
						
					}
				
					
					

					// Case-2 for Only Owner
					if(socialTenureRelationshiptmp.get(i).getShare_type().getGid()==1 ||socialTenureRelationshiptmp.get(i).getShare_type().getGid()==2 || socialTenureRelationshiptmp.get(i).getShare_type().getGid()==3 ){
						if((!personSubType.contains("Admin") && !personSubType.contains("Guard")) && personSubType.contains("Owner"))
						{
							if(spatialunitDeceasedPersontmp.size()>0)
							{
								return "Remove deceased person";
							}

							else if(personAge.contains("Minor"))
							{
								return "All owner must be adult";
							}
							
							else if(socialTenureRelationshiptmp.get(i).getShare_type().getGid()==2)
							{
								if(personSubType.size()==1){
									return "Success";	
								}
								
								else{
									return "For the selected tenure number of Owner must be one";	
								}
								
							}
							else if((socialTenureRelationshiptmp.get(i).getShare_type().getGid()==3 ||socialTenureRelationshiptmp.get(i).getShare_type().getGid()==1 ))
							{
								if(personSubType.size()>=2){
									return "Success";	
								}
								
								else{
									return "For the selected tenure number of Owner must be more than one";
								}

							}
							/*else {
								
								return "Condition not fulfilled";
							}*/
						}
						else{
							
							return "Tenure Mismatched";
						}
					}
				


				/*	// Case-3  Only Admin
					if(personSubType.contains("Admin") && !personSubType.contains("Owner") && !personSubType.contains("Guard"))
					{
						
						if(spatialunitDeceasedPersontmp.size()>0 && spatialunitDeceasedPersontmp.size()<=2)
						{
						
							return "Success";
						}
						else if(spatialunitDeceasedPersontmp.size()>2)
						{
							return "Only two deceased persons are allowed";
						}
						else if(spatialunitDeceasedPersontmp.size()==0)
						{
							return "Add deceased person";

						}

					}
					else{
						return "Tenure type is tenancy in probate,provide inputs accordingly";
					}*/

					// Case-4 Owner And Guardian
					
					if(socialTenureRelationshiptmp.get(i).getShare_type().getGid()==5){
						if(personSubType.contains("Guard") && personSubType.contains("Owner") && !personSubType.contains("Admin"))
						{

							if(spatialunitDeceasedPersontmp.size()>0)
							{
								return "Remove deceased person";
							}


							else if(personAge.contains("Adult"))
							{
								return "All Owner must be minor";
							}

							else if(spatialunitPersonwithinteresttmp.size()>0)
							{
								return "Remove person of interest";

							}
							else{

								return "Success";
							}

						}
						
						else{
							
							return "Tenure type is Guardian(minor),provide inputs accordingly";
						}
						
					}
					
					

				/*	// Case-5 Admin And Guardian
					if(personSubType.contains("Guard") && personSubType.contains("Admin") && !personSubType.contains("Owner")){

						if(spatialunitDeceasedPersontmp.size()>0){

							return "Remove guardian";
						}
						else{
							return "Remove administrator and add Minor";
						}

					}

					// Case-6 Admin And Guardian and Owner

					if(personSubType.contains("Guard") && personSubType.contains("Admin") && personSubType.contains("Owner"))
					{
						return "Invalid Data";	

					}*/
				}

			}
			else if(socialTenureRelationshiptmp.get(i).getPerson_gid().getPerson_type_gid().getPerson_type_gid()==2)
			{
				if(socialTenureRelationshiptmp.get(i).getShare_type().getGid()==6)
				return "Success";
				else
				return	 "Tenure Mismatched";	
			}
			
		}

		return "Invalid Data";


	}
	@RequestMapping(value = "/viewer/landrecords/deceasedpersonbyid/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> deceasedPersonAdjudication(@PathVariable Long usin)
	{
		ArrayList<String> tempList =new ArrayList<String>();
		List<SpatialunitDeceasedPerson> deceasedobj = landRecordsService.findDeceasedPersonByUsin(usin);
		for (int i = 0; i < deceasedobj.size(); i++) {

			String name=deceasedobj.get(i).getFirstname()+" "+deceasedobj.get(i).getMiddlename()+" "+deceasedobj.get(i).getLastname();
			tempList.add(name);
		}
		return tempList;	
	}
	@RequestMapping(value = "/viewer/landrecords/vertexlabel", method = RequestMethod.POST)
	@ResponseBody
	public boolean vertexLabel(HttpServletRequest request, HttpServletResponse response)
	{

		try {
			String vertexData = request.getParameter("vertexList");
			//ArrayList<Double> bbox=new ArrayList<Double>();
			if(landRecordsService.deleteAllVertexLabel())

			{

				String[] arr= vertexData.split(",");
				int serialId=1;
				for (int i = 0; i <arr.length;) {



					landRecordsService.addAllVertexLabel(serialId,arr[i+1],arr[i]);
					serialId++;
					i=i+2;
				}


			}
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}


	}
	@RequestMapping(value = "/viewer/landrecords/ccronew/{usin}", method = RequestMethod.GET)
	@ResponseBody
	public CcroDto ccroDetails(@PathVariable Long usin)
	{
		CcroDto tmpdto=new CcroDto();
		HashMap<String,String> personUrl=new HashMap<String, String>(); // for url against person
		List<String> admin = new ArrayList<String>(); // for admin name
		List<String> name = new ArrayList<String>(); // for natural person name
		String institute=""; // for institute name
		List<String> sharepercentage= new ArrayList<String>(); // for share percentage in case multiple tenancy
		List<String> guardianName= new ArrayList<String>(); // for Guardian List
		List<String> guardianUrl=new ArrayList<String>(); // for guardian Url

		SpatialUnitTable spa= landRecordsService.findSpatialUnitbyId(usin).get(0);
		ProjectArea projectArea= landRecordsService.findProjectArea(spa.getProject()).get(0);
		List<SocialTenureRelationship> socialtenure = landRecordsService.findAllSocialTenureByUsin(usin);
		HashMap<String,Long> personalogGid= new HashMap<String, Long>();


		for (int i = 0; i < socialtenure.size(); i++) {
			Person person=socialtenure.get(i).getPerson_gid();
			if(person.getPerson_type_gid().getPerson_type_gid()==1)
			{
				NaturalPerson naturalPerson= landRecordsService.naturalPersonById(person.getPerson_gid()).get(0);
				if(naturalPerson.getPersonSubType().getPerson_type_gid()==3){
					SourceDocument sourceDocument =landRecordsService.getdocumentByPerson(naturalPerson.getPerson_gid());
					String personname=naturalPerson.getFirstName()+" "+naturalPerson.getLastName();
					if(naturalPerson.getMiddleName()!=null)
					personname=naturalPerson.getFirstName()+" "+naturalPerson.getMiddleName()+" "+naturalPerson.getLastName();
					personUrl.put(personname,"Url");
					name.add(personname);

					personalogGid.put(personname, naturalPerson.getPerson_gid());

					sharepercentage.add(socialtenure.get(i).getSharePercentage()+"%");
					if(sourceDocument!=null && sourceDocument.isActive()){

						String fileName=sourceDocument.getScanedSourceDoc();
						String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase();
						personUrl.put(personname, sourceDocument.getLocScannedSourceDoc()+"/"+sourceDocument.getGid()+"."+fileType);
					}


				}
				else if(naturalPerson.getPersonSubType().getPerson_type_gid()==4)
				{
					String personname=naturalPerson.getFirstName()+" "+naturalPerson.getLastName();
					if(naturalPerson.getMiddleName()!=null)
					personname=naturalPerson.getFirstName()+" "+naturalPerson.getMiddleName()+" "+naturalPerson.getLastName();
					admin.add(personname);
				}
					

				else if(naturalPerson.getPersonSubType().getPerson_type_gid()==5){
					
					String personname=naturalPerson.getFirstName()+" "+naturalPerson.getLastName();
					if(naturalPerson.getMiddleName()!=null)
					personname=naturalPerson.getFirstName()+" "+naturalPerson.getMiddleName()+" "+naturalPerson.getLastName();
					guardianName.add(personname);
					SourceDocument sourceDocument =landRecordsService.getdocumentByPerson(naturalPerson.getPerson_gid());
					guardianUrl.add("Url");
					if(sourceDocument!=null && sourceDocument.isActive()){

						String fileName=sourceDocument.getScanedSourceDoc();
						String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase();
						guardianUrl.add(sourceDocument.getLocScannedSourceDoc()+"/"+sourceDocument.getGid()+"."+fileType);
					}


				}


			}

			else if(person.getPerson_type_gid().getPerson_type_gid()==2)
			{
				NonNaturalPerson nonNaturalPerson=landRecordsService.nonnaturalPersonById(person.getPerson_gid()).get(0);
				NaturalPerson naturalPerson= landRecordsService.naturalPersonById(nonNaturalPerson.getPoc_gid()).get(0);
				SourceDocument sourceDocument =landRecordsService.getdocumentByPerson(naturalPerson.getPerson_gid());
				String personname=naturalPerson.getFirstName()+" "+naturalPerson.getLastName();
				if(naturalPerson.getMiddleName()!=null)
				personname=naturalPerson.getFirstName()+" "+naturalPerson.getMiddleName()+" "+naturalPerson.getLastName();
				personUrl.put(personname,"Url");
				name.add(personname);
				institute=nonNaturalPerson.getInstitutionName();
				if(sourceDocument!=null && sourceDocument.isActive()){

					String fileName=sourceDocument.getScanedSourceDoc();
					String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length()).toLowerCase();
					personUrl.put(personname, sourceDocument.getLocScannedSourceDoc()+"/"+sourceDocument.getGid()+"."+fileType);
				}


			}

		}

		try {
			tmpdto.setPersonName_url(personUrl);
			tmpdto.setInstitute(institute);
			tmpdto.setName(name);
			tmpdto.setPerson_type(socialtenure.get(0).getPerson_gid().getPerson_type_gid().getPerson_type_gid());
			tmpdto.setOwnership(socialtenure.get(0).getShare_type().getGid());
			tmpdto.setDlo(projectArea.getDistrictOfficer());
			tmpdto.setVillagechairman(projectArea.getVillageChairman());
			tmpdto.setVillageexecutive(projectArea.getApprovingExecutive());
			tmpdto.setNeighbour_east(spa.getNeighbor_east());
			tmpdto.setNeighbour_north(spa.getNeighbor_north());
			tmpdto.setNeighbour_south(spa.getNeighbor_south());
			tmpdto.setNeighbour_west(spa.getNeighbor_west());
			tmpdto.setHamlet(spa.getHamlet_Id().getHamletName());
			tmpdto.setProposeduse(spa.getProposedUse().getLandUseType_sw());
			tmpdto.setAddress(projectArea.getAddress());
			tmpdto.setAdminName(admin);

			tmpdto.setSharepercentage(sharepercentage);
			tmpdto.setResident(socialtenure.get(0).isResident());
			tmpdto.setUka(spa.getPropertyno());
			tmpdto.setUsin(spa.getUsin());
			tmpdto.setPersonwithGid(personalogGid);
			tmpdto.setGuardian(guardianName);
			tmpdto.setGuardianUrl(guardianUrl);
		} catch (Exception e) {
			logger.error(e);
		}



		return tmpdto;	
	}

	@RequestMapping(value = "/viewer/landrecords/updateshare", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateShare(HttpServletRequest request, HttpServletResponse response)
	{
		int length_share=0;

		try {
			length_share=ServletRequestUtils.getRequiredIntParameter(request, "length_person");

			if(length_share>0)
			{
				for (int i = 0; i <length_share; i++) {

					String alias="";
					long personGid=0;
					try {
						alias=ServletRequestUtils.getRequiredStringParameter(request,"alias_person"+i);
						personGid=ServletRequestUtils.getRequiredLongParameter(request,"person_gid"+i);
						landRecordsService.updateSharePercentage(alias,personGid);
					} catch (ServletRequestBindingException e) {
						logger.error(e);
						return false;
					}


				}

				return true;

			}
			else
				return false;
		} catch (ServletRequestBindingException e) {
			logger.error(e);
			return false;
		}




	}


// compression method to compress image file
	
	@SuppressWarnings("unused")
	private String compressPicture(String outDirPath, Integer id,String fileExtension) {

		File compressedImageFile=new File(outDirPath+File.separator+ id+"."+fileExtension);
		float k = 1.0f;
		try {
			while (compressedImageFile.length() > 50*1024 ){
				File input =  new File(outDirPath+File.separator+ id+"."+fileExtension);
				BufferedImage image = ImageIO.read(input);

				compressedImageFile = new File(outDirPath+File.separator+ id+"."+fileExtension);
				OutputStream os =new FileOutputStream(compressedImageFile);

				Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName(fileExtension);
				ImageWriter writer = (ImageWriter) writers.next();

				ImageOutputStream ios = ImageIO.createImageOutputStream(os);
				writer.setOutput(ios);

				ImageWriteParam param = writer.getDefaultWriteParam();

				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality((float) (0.5*k));
				writer.write(null, new  IIOImage(image, null, null), param);


				os.flush();
				os.close();
				ios.flush();
				ios.close();
				writer.dispose();

				k = (float) (k*0.5);
			}
			return "Success";

		} catch (FileNotFoundException e) {
			logger.error(e);
			return "Error";
		} catch (IOException e) {
			logger.error(e);
			return "Error";
		}

	}

	@RequestMapping(value = "/viewer/landrecords/citizenship/", method = RequestMethod.GET)
	@ResponseBody
	public List<Citizenship> citizenList()
	{

		return landRecordsService.findAllCitizenShip();



	}
	
	

	@RequestMapping(value = "/viewer/landrecords/deletenaturalphoto/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String  deleteNaturalImage(@PathVariable Long id)
	{

		if(landRecordsService.checkActivePerson(id)==false)
		{
			return "false";
		}
		else{
			landRecordsService.deleteNaturalImage(id);
			return "true";
		}
	
	}

}