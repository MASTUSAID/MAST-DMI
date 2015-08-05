package com.rmsi.mast.studio.web.mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.service.ProjectAttributeService;
import com.rmsi.mast.studio.service.ProjectDataService;
import com.rmsi.mast.studio.service.UserService;


@Controller
public class ProjectDataController 
{
	
	private static final Logger logger = Logger.getLogger(ProjectDataController.class);
	
	@Autowired
	ProjectDataService projectDataService ;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectAttributeService  projectAttributeService;
	@RequestMapping(value = "/mobileconfig/upload/", method = RequestMethod.POST)
	@ResponseBody
    public String uploadSpatialData(MultipartHttpServletRequest request, HttpServletResponse response)
    {	
		//List<ProjectSpatialData> uploadDocuments = new ArrayList<ProjectSpatialData>();
		try {

			Iterator<String> file = request.getFileNames();
			String filename = (String) request.getAttribute("filename");
			String alias ="";
			alias= ServletRequestUtils.getRequiredStringParameter(request, "alias");
			byte[] document = null;
			while(file.hasNext()) 
			{
				String fileName = file.next();
				String projName="";
				MultipartFile mpFile = request.getFile(fileName);
				long size = mpFile.getSize();
				String originalFileName = mpFile.getOriginalFilename();
				ProjectSpatialData objDocument = new ProjectSpatialData();
				
				projName=ServletRequestUtils.getRequiredStringParameter(request, "ProjectID");
				
				String fileExtension = originalFileName.substring(originalFileName.indexOf(".") + 1,originalFileName.length()).toLowerCase();

				if (originalFileName != "") {
					document = mpFile.getBytes();
				}
				String uploadFileName = null;
				
				String outDirPath=request.getSession().getServletContext().getRealPath(File.separator)+"resources/documents/"+projName+"/mbtiles";
				
				File outDir=new File(outDirPath);
				boolean exists = outDir.exists();
				if (!exists) {
					
					boolean success = (new File(outDirPath)).mkdirs();
					
				}
				
			
				objDocument.setFileName(originalFileName);
				if(projName!="")
				{
					objDocument.setName(projName);
				}
				
				objDocument.setFileExtension(fileExtension);
				objDocument.setSize(size/1024);
				uploadFileName=("resources/documents/"+projName+"/mbtiles");
				objDocument.setFileLocation(uploadFileName);
				objDocument.setAlias(alias);
			
			if(!fileExtension.equalsIgnoreCase("mbtiles"))
			{
				
				return "mbtiles";
			}
			else{
				objDocument =  projectDataService.saveUploadedDocuments(objDocument);
			}
				Integer id = objDocument.getId();
				
				try {
					//String user=projName;

					//InputStream inputStream = file.getInputStream();		
					/*String outDirPath=request.getRealPath("\\")+"resources/documents/"+projName;
					
					File outDir=new File(outDirPath);
					boolean exists = outDir.exists();
					if (!exists) {
						
						boolean success = (new File(outDirPath)).mkdir();
						
					}*/
					
					 //uploadFileName=("resources/documents/"+projName+"/");
					FileOutputStream uploadfile = new FileOutputStream(outDirPath+"/"+ id+"."+fileExtension);
					uploadfile.write(document);
					uploadfile.flush();
					uploadfile.close();
					
					
				} 
				
				catch (Exception e) {
				
					logger.error(e);
				}
				
			 System.out.println("true");

		} 
			
		}catch (Exception e) {
			logger.error(e);
		}		
		return "Success";
	}
	
	
	@RequestMapping(value = "/mobileconfig/projectdata/", method = RequestMethod.GET)
	@ResponseBody
	public List<ProjectSpatialData> list()
	{
		return projectDataService.findAllProjectdata();
		
		
				
	}
	
	@RequestMapping(value = "/mobileconfig/projectdata/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteProjectData(@PathVariable Long id){
		
		projectDataService.deleteProjectData(id);
		
	}
	
	@RequestMapping(value = "/mobileconfig/projectname/", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/mobileconfig/projectdata/display/{name}", method = RequestMethod.GET)
	@ResponseBody
    public List<ProjectSpatialData> displaySelectedProject(@PathVariable String name){
		
		return projectDataService.displaySelectedProject(name);
		
	}
	
}


