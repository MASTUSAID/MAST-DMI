

package com.rmsi.mast.studio.web.mvc;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserOrder;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.service.ProjectAttributeService;
import com.rmsi.mast.studio.service.RoleService;
import com.rmsi.mast.studio.service.UserService;
import com.rmsi.mast.studio.util.ConfigurationUtil;
import com.rmsi.mast.studio.util.SMTPMailServiceUtil;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@Autowired
	ProjectAttributeService projectAttributeService; 


	@RequestMapping(value = "/studio/user/", method = RequestMethod.GET)
	@ResponseBody
	public List<User> list(){

		List<User> templist= userService.findAllUser();
		List<User> userlst= new ArrayList<User>();

		try {
			if(templist.size()>0)
			{
				for(User objuser:templist)
				{
					String reportingTo="";

					if(objuser.getManager_name()!=null  && !objuser.getManager_name().isEmpty())
					{
						User objtemp=userService.findUserByUserId(Integer.parseInt(objuser.getManager_name()));
						reportingTo=objtemp.getUsername();
						objuser.setReportingTo(reportingTo);
					}else
					{
						objuser.setReportingTo(reportingTo);
					}

					userlst.add(objuser);
				}
			}
		} catch (Exception e) {

			logger.error(e);
			return userlst;
		}


		return userlst;

	}

	@RequestMapping(value = "/studio/user/order", method = RequestMethod.GET)
	@ResponseBody
	public List<String> listByOrder(){
		List<UserOrder> users = userService.getUserOrderedById();
		List<String> ls = new ArrayList<String>();

		int j = 1;
		for(int i=0; i<users.size();){
			if(users.get(i).getId().intValue() == j){
				ls.add(users.get(i).getName());
				j++;
				i++;
			}else{
				ls.add(" ");
				j++;
			}
		}


		return ls;
	}


	@RequestMapping(value = "/studio/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUserById(@PathVariable String id){		
		System.out.println("------------userid:"+ id);
		User usr=userService.findUserByName(id);

		//if(usr != null){
		//usr.setPassword(decryptPassword(usr.getPassword()));

		//}
		return 	usr;	
	}

	@RequestMapping(value = "/studio/_user/", method = RequestMethod.POST)
	@ResponseBody
	//public boolean deleteUserById(@PathVariable String id){
	public User getUserById(HttpServletRequest request, HttpServletResponse response){
		String data = request.getParameter("data");
		try{
			String email = URLDecoder.decode(data, "UTF-8");
			//System.out.println("---- deleteUserById: " + id);
			User usr = userService.findByEmail(email);
			return usr;
		}catch(Exception e){
			logger.error(e);
		}
		return null;

	}

	@RequestMapping(value = "/studio/user/email/", method = RequestMethod.POST)
	@ResponseBody
	public User getUserByEmail(HttpServletRequest request, HttpServletResponse response){		
		String email=request.getParameter("email");
		System.out.println("------------EMAIL:"+ email);
		User usr=userService.findByEmail(email);

		return 	usr;	
	}


	@RequestMapping(value = "/studio/user/delete/", method = RequestMethod.GET)
	@ResponseBody
	public void deleteUser()
	{


		userService.deleteUser();

	}



	@RequestMapping(value = "/studio/user/create", method = RequestMethod.POST)
	@ResponseBody
	public String createUser(HttpServletRequest request, HttpServletResponse response){

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date expDate=null;
		Set<Role> roleList = new HashSet<Role> ();

		String userName="";
		String emailId = "";
		String userId="";
		String defProjName="";
		String pass="";
		String name;

		User user = null;
		user=new User();
		try{		
			userId = ServletRequestUtils.getRequiredStringParameter(request, "hid_userid");
			userName = ServletRequestUtils.getRequiredStringParameter(request, "name");
			name = ServletRequestUtils.getRequiredStringParameter(request, "name_user");
			emailId = ServletRequestUtils.getRequiredStringParameter(request, "email");
			defProjName = ServletRequestUtils.getRequiredStringParameter(request, "defaultproject");
			pass=ServletRequestUtils.getRequiredStringParameter(request, "password");


			if(userId=="")
			{
				boolean value1=userService.checkduplicate(userName);

				if(value1)
				{				
					return "duplicate";
				}

			}


			if(userId!="")
			{
				user = getUserByUserId(userId);

			}

			user.setName(name);
			user.setUsername(userName);
			user.setEmail(emailId);
			user.setDefaultproject(defProjName);
			user.setManager_name("");

						
			if(pass.equals(user.getPassword())){
				user.setPassword(pass);
			}
			else{

				//Encrypted pass
				final String ENCRYPT_KEY = "HG58YZ3CR9";
				StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
				encryptor.setPassword(ENCRYPT_KEY);                  
				encryptor.setAlgorithm("PBEWithMD5AndTripleDES");   		
				String encryptedText = encryptor.encrypt(pass);

				if(encryptedText != null){
					user.setPassword(encryptedText);
				}

			}

			try {
				user.setManager_name(ServletRequestUtils
						.getRequiredStringParameter(request, "manager_name"));
			} catch (Exception e1) {
				logger.error(e1);

			}

			user.setActive(Boolean.parseBoolean(ServletRequestUtils
					.getRequiredStringParameter(request, "active")));

			try {
				String expDateStr=ServletRequestUtils
						.getRequiredStringParameter(request, "passwordexpires");							
				expDate= df.parse(expDateStr);
			} catch (Exception e) {
				logger.error(e);

			} 

			user.setPasswordexpires(expDate);

			user.setLastactivitydate(new Date());


			String roles[]=request.getParameterValues("functionalRole");


			for(int i = 0; i < roles.length; i++){
				Role userrole=new Role();
				userrole=roleService.findRoleByName(roles[i]);
				roleList.add(userrole);	           
			}
			user.setRoles(roleList);			

			String authkey=generateAuthKey(user.getEmail(), user.getPassword());
			user.setAuthkey(authkey);
			user=userService.addUser(user);
			return "true";

		}
		catch(Exception e){
			logger.error(e);
			return "false";
		}



	}




	/*@RequestMapping(value = "/studio/user/cbrole", method = RequestMethod.GET)
	@ResponseBody
	public List<Role> listdata()
	{
		List<Role> Rolelst= new ArrayList<Role>();

		try {

			Rolelst=roleService.findAllRole();

		} catch (Exception e) {

			e.printStackTrace();
			return Rolelst;
		}


		return Rolelst;

	}
	 */

	/*/
	 */	

	@RequestMapping(value = "/studio/user/edit", method = RequestMethod.POST)
	@ResponseBody
	public void editUser(User user){
		userService.updateUser(user);	
	}

	@RequestMapping(value = "/studio/user/{id}/project/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getProjectsByUser(@PathVariable String id){

		return userService.getProjectsByUser(id);
	}


	//@RequestMapping(value="/studio/user/auth", method = RequestMethod.POST)
	//@ResponseBody
	private String generateAuthKey(String userid, String password){
		String _token = null;
		final String ENCRYPT_KEY = "HG58YZ3CR9";
		try{

			//String userid = request.getParameter("userid");
			//String password = request.getParameter("password");

			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword(ENCRYPT_KEY);
			encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

			String tokenText = userid + "|" + password;  
			_token = encryptor.encrypt(tokenText);
			_token = URLEncoder.encode(_token, "UTF-8");
		}catch(UsernameNotFoundException ex){
			//User not authenticated to access application
			_token = "403(Forbidden)��Authentication failed.";
			logger.error(ex);
		}catch(DataAccessException dataAccessEx){
			_token = "500��Server error.";
			logger.error(dataAccessEx);
		}catch(Exception e){
			_token = "500��Server error.";
			logger.error(e);
		}
		return _token;
	}



	private String decryptPassword(String encPassword){
		final String ENCRYPT_KEY = "HG58YZ3CR9";
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(ENCRYPT_KEY);
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		String storedPwd = encryptor.decrypt(encPassword);  
		return storedPwd;
	}

	@RequestMapping(value = "/user/forgotpassword", method = RequestMethod.POST)
	@ResponseBody
	public boolean forgotPassword(HttpServletRequest request, HttpServletResponse response){
		String userName = null;
		String email = null;



		try{
			/*userName = ServletRequestUtils.getRequiredStringParameter(request,
		"usrname");*/
			email = ServletRequestUtils.getRequiredStringParameter(request,
					"usrMail");

			User usr = userService.findByEmail(email);
			if(usr == null){
				return false;
			}else{
				String password = decryptPassword(usr.getPassword());
				//System.out.println("----Decrypted Password: " + password);

				/* Write code to mail the password */
				// TODO Auto-generated method stub	
				try {	
					String adminEmailAdd=ConfigurationUtil.getProperty("admin.email.address");
					String message=ConfigurationUtil.getProperty("forgetpassword.mail");
					String subject = ConfigurationUtil.getProperty("forgetpassword.subject");
					message=message.replace("<1>", usr.getName());
					message=message.replace("<2>", password);
					SMTPMailServiceUtil.sendMail(adminEmailAdd, email, subject, message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e);
					return false;
				}
				return true;
			}
		}catch(Exception e){
			logger.error(e);
		}
		return false;

	}


	@RequestMapping(value = "/studio/user/userid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUserByUserId(@PathVariable String id){		
		System.out.println("------------userid:"+ id);
		User usr=userService.findUserByUserId(Integer.parseInt(id));

		return 	usr;	
	}


	@RequestMapping(value = "/studio/user/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteUserById(@PathVariable String id){

		try{
			String repotingId=String.valueOf(id);
			boolean reportingcheck=userService.checkreportinmngr(repotingId);

			if(reportingcheck)
			{
				return false;

			}
			else{


				return userService.deleteUserById(Integer.parseInt(id));
			}
		}
		catch(Exception e){
			logger.error(e);
		}
		return false;

	}

	@RequestMapping(value = "/studio/defaultproject/", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> listproject() {
		return projectAttributeService.findallProjects();

	}

	@RequestMapping(value = "/studio/user/username/", method = RequestMethod.POST)
	@ResponseBody
	public User getUserByUsername(HttpServletRequest request, HttpServletResponse response)
	{		
		String username=request.getParameter("username");
		User usr=userService.findUserByName(username);		
		return 	usr;	
	}
	
	@RequestMapping(value = "/publicuser/register", method = RequestMethod.POST)
	@ResponseBody
	public String createGuestUser(HttpServletRequest request, HttpServletResponse response)
	{

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date expDate=null;
		Set<Role> roleList = new HashSet<Role> ();

		String userName="";
		String emailId = "";
		//String userId="";
		//String defProjName="";
		String pass="";

		User user = null;
		user=new User();
		try{		
			//userId = ServletRequestUtils.getRequiredStringParameter(request, "hid_userid");
			userName = ServletRequestUtils.getRequiredStringParameter(request, "guest_name");
			emailId = ServletRequestUtils.getRequiredStringParameter(request, "guest_email");
			//defProjName = ServletRequestUtils.getRequiredStringParameter(request, "defaultproject");
			pass=ServletRequestUtils.getRequiredStringParameter(request, "guest_password");


			
				boolean value1=userService.checkduplicate(userName);

				if(value1)
				{				
					return "duplicate";
				}

			

			user.setName(userName);
			user.setEmail(emailId);
			user.setDefaultproject("");
			user.setManager_name("");

						
			if(pass.equals(user.getPassword())){
				user.setPassword(pass);
			}
			else{

				//Encrypted pass
				final String ENCRYPT_KEY = "HG58YZ3CR9";
				StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
				encryptor.setPassword(ENCRYPT_KEY);                  
				encryptor.setAlgorithm("PBEWithMD5AndTripleDES");   		
				String encryptedText = encryptor.encrypt(pass);

				if(encryptedText != null){
					user.setPassword(encryptedText);
				}

			}

			/*try {
				user.setManager_name(ServletRequestUtils
						.getRequiredStringParameter(request, "manager_name"));
			} catch (Exception e1) {
				logger.error(e1);

			}*/

			user.setActive(true);

			try {
				String expDateStr="2100-12-31";						
				expDate= df.parse(expDateStr);
			} catch (Exception e) {
				logger.error(e);

			} 

			user.setPasswordexpires(expDate);

			user.setLastactivitydate(new Date());
			


				Role userrole=new Role();
				userrole=roleService.findRoleByName("ROLE_PUBLICUSER");
				roleList.add(userrole);
				user.setRoles(roleList);			

			String authkey=generateAuthKey(user.getEmail(), user.getPassword());
			user.setAuthkey(authkey);
			user=userService.addUser(user);
			return "true";

		}
		catch(Exception e){
			logger.error(e);
			return "false";
		}



	}
	
	
	@RequestMapping(value = "/studio/publicproject/{project}", method = RequestMethod.GET)
	@ResponseBody
	public boolean setDefaultProject(@PathVariable String project , Principal principal){		
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		
		user.setDefaultproject(project);
		if(userService.addUser(user)!=null)
		return true;
		else
			return false;
			
	}

	

	
}

