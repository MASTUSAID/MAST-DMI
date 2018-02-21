package com.rmsi.mast.viewer.web.mvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.custom.dto.ResourceAttributeValueDto;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.fetch.ResourceDetails;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;
import com.rmsi.mast.viewer.service.ResourceAttributeValuesService;


@Controller
public class LandResourceController {

	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	ResourceAttributeValuesService resourceAttributeValuesService;
	
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	ResourceAttributeValuesService resourceAttributeValuesservice;
	
	@Autowired
	AttributeOptionsDao attributeOptionsDao;
	
	
	@RequestMapping(value = "/viewer/resource/allAttribue/{landid}", method = RequestMethod.GET)
    @ResponseBody
    public  Map<Integer, List<ResourceAttributeValues>> getAllResourceAttribute(@PathVariable Integer landid) {
		ResourceAttributeValueDto objDto= new ResourceAttributeValueDto();
		List<ResourceAttributeValues> lst = new ArrayList<ResourceAttributeValues>();
		lst=resourceAttributeValuesService.getResourceAttributeValuesBylandId(landid);
		Map<Integer, List<ResourceAttributeValues>> map = new HashMap<Integer, List<ResourceAttributeValues>>();
		int groupId=0;
		List<ResourceAttributeValues> lstres= new ArrayList<ResourceAttributeValues>();
		for(ResourceAttributeValues obj:lst){
			
			if(groupId!=obj.getGroupid()){
				groupId=obj.getGroupid();
				lstres= new ArrayList<ResourceAttributeValues>();
				lstres.add(obj);
				map.put(obj.getGroupid(), lstres);
			}else{
				lstres.add(obj);
				
			}
			
			
		}
		
		objDto.setMap(map);
		
        return map;
    }
	
	
	
	
	
	
	@RequestMapping(value = "/viewer/resource/allAttribtesDatatype/{landid}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object[]> getAllResourceAttributesDatatype(@PathVariable Integer landid) {
		ResourceAttributeValueDto objDto= new ResourceAttributeValueDto();
		List<Object[]> lst = new ArrayList<>();
		lst=resourceAttributeValuesService.getResourceAttributeValuesAndDatatypeBylandId(landid);
		
		
        return lst;
    }
	
	@RequestMapping(value = "/viewer/resource/getAllresouce/{project}/{startfrom}", method = RequestMethod.GET)
    @ResponseBody
    public  List<ResourceDetails> getAllresouce(@PathVariable String project,@PathVariable Integer startfrom,Principal principal) {
		
		Integer projectId=0;
		
		try{
			 Project objproject=projectDAO.findByName(project);
			 projectId= objproject.getProjectnameid();
		}catch(Exception e){
			e.printStackTrace();
		}

		return resourceAttributeValuesService.getAllresouceByproject(projectId+"",startfrom);
   }
	
	
	@RequestMapping(value = "/viewer/resource/getAllresouceCount/{project}", method = RequestMethod.GET)
    @ResponseBody
    public  Integer getAllresouceCount(@PathVariable String project) {
		
		Integer projectId=0;
		
		try{
			 Project objproject=projectDAO.findByName(project);
			 projectId= objproject.getProjectnameid();
		}catch(Exception e){
			e.printStackTrace();
		}

	
	return resourceAttributeValuesService.getAllresouceCountByproject(projectId+"");
   }
	
	
	@RequestMapping(value = "/viewer/landrecords/saveResourcePersonForEditing", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
   public ResponseEntity saveResourcelPersonForEditing(HttpServletRequest request, HttpServletResponse response)
	{
		try 
		{
			Integer landId = 0;
			String firstname = "";
			String middlename =""; 
			String lastname ="";
			String address ="";			
			String genderid ="";
			String dob = "";
			String MobileNo = "";
			String Address =""; 
			String maritalstatusid = "";
			String groupId ="";
			String Citizenship="";
			String Ethnicity = "";
			String resident = "";
			String Otherdetails ="";
			String Institutionname ="";
			String Agencyname ="";
			String Community ="";
			String Consession_handled ="";
			
			String RegistartionNo ="";
			String RegistrationDate ="";
			String levelofauthority ="";
			String Total_members ="";
			String Totalmemb_collective_Org ="";
			String output = "";
			String registartionOutputdate = "";

			
			try{RegistartionNo =  ServletRequestUtils.getRequiredStringParameter(request, "Registration No");}catch(Exception e){}
			try{RegistrationDate =  ServletRequestUtils.getRequiredStringParameter(request, "Registration Date");}catch(Exception e){}
			try{levelofauthority =  ServletRequestUtils.getRequiredStringParameter(request, "Level of Authority");}catch(Exception e){}
			try{Total_members =  ServletRequestUtils.getRequiredStringParameter(request, "How many members?");}catch(Exception e){}
			try{Totalmemb_collective_Org =  ServletRequestUtils.getRequiredStringParameter(request, "How many members in collective organization?");}catch(Exception e){}

			
			try{Otherdetails =  ServletRequestUtils.getRequiredStringParameter(request, "Other details");}catch(Exception e){}
			try{Institutionname =  ServletRequestUtils.getRequiredStringParameter(request, "Institution Name");}catch(Exception e){}
			try{Agencyname =  ServletRequestUtils.getRequiredStringParameter(request, "Agency Name");}catch(Exception e){}
			try{Community =  ServletRequestUtils.getRequiredStringParameter(request, "Community or Parties");}catch(Exception e){}
			try{Consession_handled =  ServletRequestUtils.getRequiredStringParameter(request, "How are concessions to land handled?");}catch(Exception e){}
			try{landId = ServletRequestUtils.getRequiredIntParameter(request, "landid");}catch(Exception e){}
			try{firstname =  ServletRequestUtils.getRequiredStringParameter(request, "firstname");}catch(Exception e){}
			try{middlename =ServletRequestUtils.getRequiredStringParameter(request, "middlename");}catch(Exception e){}
			try{lastname =ServletRequestUtils.getRequiredStringParameter(request, "lastname");}catch(Exception e){}
			try{groupId =ServletRequestUtils.getRequiredStringParameter(request, "groupId");}catch(Exception e){}
			try{genderid= ServletRequestUtils.getRequiredStringParameter(request, "genderid");}catch(Exception e){}
			try{dob=ServletRequestUtils.getRequiredStringParameter(request, "Dob");}catch(Exception e){}
			//try{Long mobileno = ServletRequestUtils.getRequiredLongParameter(request, "mobileno");}catch(Exception e){}
			try{Citizenship=ServletRequestUtils.getRequiredStringParameter(request, "Citizenship");}catch(Exception e){}
			try{Ethnicity=ServletRequestUtils.getRequiredStringParameter(request, "Ethnicity");}catch(Exception e){}
			try{resident=ServletRequestUtils.getRequiredStringParameter(request, "Resident of Community");}catch(Exception e){}

			try{MobileNo=ServletRequestUtils.getRequiredStringParameter(request, "Mobile No");}catch(Exception e){}
			try{Address=ServletRequestUtils.getRequiredStringParameter(request, "Address");}catch(Exception e){}
			try{maritalstatusid=ServletRequestUtils.getRequiredStringParameter(request, "Marital Status");}catch(Exception e){}
			
			if(null != dob && dob != ""){
			DateFormat inputFormat = new SimpleDateFormat(
			        "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
			    Date date = inputFormat.parse(dob);
			
			
			 DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
				      
				     output = outputFormat.format(date);
			}
			
			if(null != RegistrationDate && RegistrationDate != ""){
				DateFormat inputFormat = new SimpleDateFormat(
				        "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
				    Date date = inputFormat.parse(RegistrationDate);
				
				
				 DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
					      
					     registartionOutputdate = outputFormat.format(date);
				}
			
			List<ResourceAttributeValues> resobj  = resourceAttributeValuesService.getResourceAttributeValuesByMasterlandid(landId);
			for(ResourceAttributeValues objlst :resobj){
				if(objlst.getGroupid().toString().equalsIgnoreCase(groupId.toString())){
//				ResourceAttributeValues resmergeobj = new ResourceAttributeValues();
//				if(objlst.getAttributemaster().getFieldname().equalsIgnoreCase("firstname")){
					if(objlst.getAttributemaster().getAttributemasterid()==1017 ||
							objlst.getAttributemaster().getAttributemasterid()==1035 ||
							objlst.getAttributemaster().getAttributemasterid()==1063 ||
							objlst.getAttributemaster().getAttributemasterid()==1079 ||
							objlst.getAttributemaster().getAttributemasterid()==1088 ||
							objlst.getAttributemaster().getAttributemasterid()==1097 ||
							objlst.getAttributemaster().getAttributemasterid()==1108 ||
							objlst.getAttributemaster().getAttributemasterid()==1115){
					objlst.setAttributevalue(firstname);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1018 ||
						objlst.getAttributemaster().getAttributemasterid()==1036 ||
						objlst.getAttributemaster().getAttributemasterid()==1065 ||
						objlst.getAttributemaster().getAttributemasterid()==1080 ||
						objlst.getAttributemaster().getAttributemasterid()==1089 ||
						objlst.getAttributemaster().getAttributemasterid()==1109 ||
						objlst.getAttributemaster().getAttributemasterid()==1098 ||
						objlst.getAttributemaster().getAttributemasterid()==1117){
					objlst.setAttributevalue(middlename);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1019 ||
						objlst.getAttributemaster().getAttributemasterid()==1037 ||
						objlst.getAttributemaster().getAttributemasterid()==1066 ||
						objlst.getAttributemaster().getAttributemasterid()==1081 ||
						objlst.getAttributemaster().getAttributemasterid()==1090 ||
						objlst.getAttributemaster().getAttributemasterid()==1099 ||
						objlst.getAttributemaster().getAttributemasterid()==1110 ||
						objlst.getAttributemaster().getAttributemasterid()==1118){
					objlst.setAttributevalue(lastname);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1042 ||
						objlst.getAttributemaster().getAttributemasterid()==1030 ||
						objlst.getAttributemaster().getAttributemasterid()==1073 ||
						objlst.getAttributemaster().getAttributemasterid()==1086 ||
						objlst.getAttributemaster().getAttributemasterid()==1095 ||
						objlst.getAttributemaster().getAttributemasterid()==1105 ||
						objlst.getAttributemaster().getAttributemasterid()==1125){
					objlst.setAttributevalue(MobileNo);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1038 ||
						objlst.getAttributemaster().getAttributemasterid()==1026 ||
						objlst.getAttributemaster().getAttributemasterid()==1074 ||
						objlst.getAttributemaster().getAttributemasterid()==1082 ||
						objlst.getAttributemaster().getAttributemasterid()==1091 ||
						objlst.getAttributemaster().getAttributemasterid()==1100 ||
						objlst.getAttributemaster().getAttributemasterid()==1111 ||
						objlst.getAttributemaster().getAttributemasterid()==1112 ||
						objlst.getAttributemaster().getAttributemasterid()==1126){
					objlst.setAttributevalue(Address);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1021 ||
						objlst.getAttributemaster().getAttributemasterid()==1068 ||
						objlst.getAttributemaster().getAttributemasterid()==1129 ||
						objlst.getAttributemaster().getAttributemasterid()==1120){
					objlst.setAttributevalue(output.toString());
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1022 ||
						objlst.getAttributemaster().getAttributemasterid()==1064 ||
						objlst.getAttributemaster().getAttributemasterid()==1116){
					if(maritalstatusid.equalsIgnoreCase("un-married")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1011);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(maritalstatusid.equalsIgnoreCase("married")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1007);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(maritalstatusid.equalsIgnoreCase("divorced")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1008);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(maritalstatusid.equalsIgnoreCase("widow")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1009);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(maritalstatusid.equalsIgnoreCase("widower")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1010);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1025 ||
						objlst.getAttributemaster().getAttributemasterid()==1071 ||
						objlst.getAttributemaster().getAttributemasterid()==1123){
					if(resident.equalsIgnoreCase("Yes")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1012);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(resident.equalsIgnoreCase("No")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1013);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1024 ||
						objlst.getAttributemaster().getAttributemasterid()==1070 ||
						objlst.getAttributemaster().getAttributemasterid()==1122){
					if(Ethnicity.equalsIgnoreCase("Ethnicity 1")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1017);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(Ethnicity.equalsIgnoreCase("Ethnicity 2")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1018);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(Ethnicity.equalsIgnoreCase("Ethnicity 3")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1019);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(Ethnicity.equalsIgnoreCase("Ethnicity 4")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1020);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1023 ||
						objlst.getAttributemaster().getAttributemasterid()==1069 ||
						objlst.getAttributemaster().getAttributemasterid()==1121){
					if(Citizenship.equalsIgnoreCase("Not Known")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1014);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(Citizenship.equalsIgnoreCase("Country1")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1015);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(Citizenship.equalsIgnoreCase("Country2")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1016);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(Citizenship.equalsIgnoreCase("Others")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1142);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1020 ||
						objlst.getAttributemaster().getAttributemasterid()==1067 ||
						objlst.getAttributemaster().getAttributemasterid()==1119){
					if(genderid.equalsIgnoreCase("male")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1005);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					else if(genderid.equalsIgnoreCase("female")){
						AttributeOptions attroptions=attributeOptionsDao.getAttributeOptionsId(1006);
						objlst.setAttributevalue(attroptions.getOptiontext());
					}
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1053 ||
						objlst.getAttributemaster().getAttributemasterid()==1055){
					
					
					objlst.setAttributevalue(Otherdetails);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1031 ||
						objlst.getAttributemaster().getAttributemasterid()==1077){
					
					
					objlst.setAttributevalue(Institutionname);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1106){
					
					
					objlst.setAttributevalue(Agencyname);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1096){
					
					
					objlst.setAttributevalue(Community);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1060){
					
					
					objlst.setAttributevalue(Consession_handled);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1032){
					objlst.setAttributevalue(RegistartionNo);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1034 || 
						objlst.getAttributemaster().getAttributemasterid()==1078){
					objlst.setAttributevalue(Total_members);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1033){
					objlst.setAttributevalue(registartionOutputdate);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1107){
					objlst.setAttributevalue(levelofauthority);
					em.merge(objlst);
				}
				else if(objlst.getAttributemaster().getAttributemasterid()==1087){
					objlst.setAttributevalue(Totalmemb_collective_Org);
					em.merge(objlst);
				}
//				else if(objlst.getAttributemaster().getAttributemasterid()== 1021){
//					objlst.setAttributevalue(age.toString());
//				}
//				else if(objlst.getAttributemaster().getAttributemasterid()== 1022){
//					objlst.setAttributevalue(maritalstatusid);
//				}
					
				
				
			}
				else{
					System.out.println("Do Nothing");
				}
			}
		
			
	        
			return  ResponseEntity.status(HttpStatus.CREATED).body(resobj);
		} 
		catch (Exception e) {
			return null;
		}
	}
   
   
	
	

}
