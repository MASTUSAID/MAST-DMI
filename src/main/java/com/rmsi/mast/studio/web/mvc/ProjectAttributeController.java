package com.rmsi.mast.studio.web.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.hsqldb.lib.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.custom.dto.AttributeDto;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.service.AttributeCategoryService;
import com.rmsi.mast.studio.service.AttributeMasterService;
import com.rmsi.mast.studio.service.ProjectAttributeService;
import com.rmsi.mast.studio.service.RoleService;
import com.rmsi.mast.studio.service.UserService;
@Controller
public class ProjectAttributeController 
{
	
	private static final Logger logger = Logger.getLogger(ProjectAttributeController.class);
	
	@Autowired
	private ProjectAttributeService projectAttributeService;	
	
	@Autowired
	private AttributeMasterService attributemasterService;	

	
	@Autowired
	private AttributeCategoryService attributecategoryService;
	
	@Autowired UserService userService;
	
	@Autowired RoleService roleService;

	private List<Surveyprojectattribute> s;	
	
	


	@RequestMapping(value = "/mobileconfig/projectattrib/", method = RequestMethod.GET)
	@ResponseBody
	public List<Surveyprojectattribute> list()
	{
		return projectAttributeService.findAllSurveyProjects();
		
		
				
	}
	
	
	

	@RequestMapping(value = "/mobileconfig/projecttype/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> listproject(Principal principal)
	{
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		Integer id = user.getId();
	
		Set<Role> role = user.getRoles();
		String rolename = role.iterator().next().getName();
		
		//role.setName("ROLE_ADMIN");
		List<Project> Projectlst= new ArrayList<Project>();
		List<UserProject> UserProjectlst= new ArrayList<UserProject>();
		
		try {
			if(rolename.equals("ROLE_ADMIN"))
			{
			Projectlst=projectAttributeService.findallProjects();
			return Projectlst;
			}
			else{
				
				UserProjectlst=projectAttributeService.findUserProjects(id);
				for (int i = 0; i < UserProjectlst.size(); i++) {
					Projectlst.add(UserProjectlst.get(i).getProjectBean());
				}
				
				return Projectlst;
			}
		} catch (Exception e) {
			
			logger.error(e);
			return Projectlst;
		}
		
		
		
		
	}
	
	@RequestMapping(value = "/mobileconfig/projectcategory/", method = RequestMethod.GET)
	@ResponseBody
	public List<AttributeCategory> listcategory()
	{
		List<AttributeCategory> categorylst= new ArrayList<AttributeCategory>();
		
		try {
			
			categorylst=attributecategoryService.findallAttributeCategories();
			
		} catch (Exception e) {
			
			logger.error(e);
			return categorylst;
		}
		
		
		return categorylst;
		
	}
	
	
	@RequestMapping(value = "/mobileconfig/projectattrib/display/{uid}/{name}/", method = RequestMethod.GET)
	@ResponseBody
    public List<Surveyprojectattribute> displaySelectedCategory(@PathVariable Long uid,@PathVariable String name){
		
		return projectAttributeService.displaySelectedCategory(uid,name);
		
	}
	

	@RequestMapping(value = "/mobileconfig/projectattrib/displaypop/{uid}/{project}", method = RequestMethod.GET)
	@ResponseBody
		
 public List<AttributeDto> displaySelectedAttribute(@PathVariable Long uid ,@PathVariable String project)
 {
		List<Surveyprojectattribute> categorylst= new ArrayList<Surveyprojectattribute>();
		List<AttributeMaster> lstAttributeMaster= new ArrayList<AttributeMaster>();
		//List<String> lstId= new ArrayList<String>();
		Map<Long,Long> mapId_uid = new HashMap<Long,Long>();
		List<AttributeDto> lstattribute = new ArrayList<AttributeDto>();
		try{
			categorylst= projectAttributeService.displaySelectedAttribute(uid,project);
			lstAttributeMaster= attributemasterService.displayAttribute(uid);

			if(categorylst!=null && categorylst.size()>0 )
			{	
				for(Surveyprojectattribute obj:categorylst)
				{
					//lstId.add(obj.getAttributeMaster().getId()+"");
					mapId_uid.put(obj.getAttributeMaster().getId(), obj.getUid());
				}				
				if(lstAttributeMaster!=null && lstAttributeMaster.size()>0)
				{

					for(AttributeMaster obj:lstAttributeMaster)
					{
						AttributeDto objAttribute = new AttributeDto();

						//if(lstId.contains(obj.getId()+""))
						if(mapId_uid.containsKey(obj.getId()))
						{
							objAttribute.setAlias(obj.getAlias());
							objAttribute.setId(obj.getId());
							objAttribute.setFlag(true);
							objAttribute.setUid(mapId_uid.get(obj.getId()));							
						}else
						{
							objAttribute.setAlias(obj.getAlias());
							objAttribute.setId(obj.getId());
							objAttribute.setFlag(false);					
						}						
						lstattribute.add(objAttribute);					
					}
				}
			}else  //if accordian is empty
			{
				if(lstAttributeMaster!=null && lstAttributeMaster.size()>0)
				{

					for(AttributeMaster obj:lstAttributeMaster)
					{
						AttributeDto objAttribute = new AttributeDto();
						objAttribute.setAlias(obj.getAlias());
						objAttribute.setId(obj.getId());
						objAttribute.setFlag(false);

						lstattribute.add(objAttribute);
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return lstattribute;

 }

	@RequestMapping(value = "/mobileconfig/projectattrib/create", method = RequestMethod.POST)
	@ResponseBody
	public String createMasterAttribute(HttpServletRequest request, HttpServletResponse response)
	{	
		String projName="";	
		String[] id = null ;
		long[] mapped_uids = null ;
		long[] previous_uids = null ;
		boolean flag=false;
		Long attributecategory = null;
		boolean mappedResult = false;
		List<Long> tmpPrevious_uids = new LinkedList<Long>();

		try{
			try{
				id=ServletRequestUtils.getRequiredStringParameters(request,"alias");
				flag=true;
				mapped_uids=ServletRequestUtils.getRequiredLongParameters(request,"aliasuid");
				previous_uids=ServletRequestUtils.getRequiredLongParameters(request,"hid_aliasuid");
			}
			catch(Exception e){				
				logger.error(e);				
			}
			if(id==null)
			{
				return "null";
				
			}
			try {
				List<Long> tmpMapped_uids = new LinkedList<Long>(Arrays.asList(ArrayUtils.toObject(mapped_uids)));
				tmpPrevious_uids = new LinkedList<Long>(Arrays.asList(ArrayUtils.toObject(previous_uids)));			
				tmpPrevious_uids.removeAll(tmpMapped_uids);

				if(tmpPrevious_uids.size()>0)
				{
					mappedResult = projectAttributeService.checkForProjectAttributeMapping(tmpPrevious_uids);
				}

				if(mappedResult)
				{
					return "mapping";
				}
			} catch (Exception e) {logger.error(e);}
			if (flag)
			{
				projName=ServletRequestUtils.getRequiredStringParameter(request, "project");
				attributecategory =ServletRequestUtils.getRequiredLongParameter(request, "attributecategory");
				projectAttributeService.addsurveyProject(id,projName,attributecategory);
				if(tmpPrevious_uids.size()>0)projectAttributeService.deleteMappedAttribute(tmpPrevious_uids);
			}
			else
			{
				/*projName=ServletRequestUtils.getRequiredStringParameter(request, "project");
				attributecategory =ServletRequestUtils.getRequiredLongParameter(request, "attributecategory");
				projectAttributeService.deleteallcategory(attributecategory, projName);*/
				return "false";
			}
		}
		catch(Exception e)
		{			
			logger.error(e);
			return "false";
		}
		return "true";
	}
	
	// add by RMSI NK for save up and down project attribute start
	
	@RequestMapping(value = "/mobileconfig/projectattrib/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateMasterAttribute(HttpServletRequest request, HttpServletResponse response)
	
	{
		
		List<Surveyprojectattribute> categorylst= new ArrayList<Surveyprojectattribute>();
		String projName="";	
		Long attributecategory = null;
		String Order_id="";
		
		try {
			Order_id=ServletRequestUtils.getRequiredStringParameter(request,"_optionOrder");
			projName=ServletRequestUtils.getRequiredStringParameter(request, "project");
			attributecategory =ServletRequestUtils.getRequiredLongParameter(request, "attributecategory");
			
		
			String [] stringArray = Order_id.split(",");
		    int length =stringArray.length;
			long[] result = new long[length];
			for(int i=0;i<stringArray.length;i++){
				result[i] = Long.parseLong(stringArray[i]);
			}
			
			projectAttributeService.updatesurveyProject(result,projName,attributecategory);
			//projectAttributeService.updatesurveyProjectSave(result, projName,attributecategory);
			
		} catch (ServletRequestBindingException e) {
		
			logger.error(e);
			return false;
		}
		
		return true;
		
	}
	
    //add by RMSI NK for save up and down project attribute end
	    

	
}
