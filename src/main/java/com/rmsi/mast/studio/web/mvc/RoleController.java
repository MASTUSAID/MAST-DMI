

package com.rmsi.mast.studio.web.mvc;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.Module;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.service.RoleService;
import com.rmsi.mast.studio.service.UserService;


/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class RoleController {
	
	private static final Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/studio/role/", method = RequestMethod.GET)
	@ResponseBody
    public List<Role> list(Principal principal)
    {
		String username = principal.getName();
		User user = userService.findByUniqueName(username);
		return 	roleService.findAllRole(user.getRoles().iterator().next().getId());	
	}
	
	@RequestMapping(value = "/studio/role/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Role getRoleById(@PathVariable String id){
		return 	roleService.findRoleByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/role/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteRoles(){
		roleService.deleteRole();
	}
	
	
	@RequestMapping(value = "/studio/role/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteRoleById(@PathVariable String id){
		roleService.deleteRoleById(id);	
	}
	
	@RequestMapping(value = "/studio/role/create", method = RequestMethod.POST)
	@ResponseBody
    public void createRole(Role role, HttpServletRequest request, HttpServletResponse response){
			//Role role=new Role(); 
		try{
			String modules[]=request.getParameterValues("role_module");
			Set<Module> moduleList = new HashSet<Module> ();
			
			String moduleAction1[]=request.getParameterValues("project_action1");
			System.out.println("############################################");
			
			//System.out.println("LEN>>>>"+moduleAction1.length);
        	
			/*Set<Action> actionList = new HashSet<Action> ();
			//itrate action and add to action list
			Action roleaction1=new Action();
			roleaction1.setName("Action1");
			actionList.add(roleaction1);
			
			Action roleaction2=new Action();
			roleaction2.setName("ac3");
			actionList.add(roleaction2);*/
			
			for(int i = 0; i < modules.length; i++){
	            System.out.println(">>>>>ROLE CTRL Module:"+ modules[i]+">>"+modules[i]+"_action");
				String str=	modules[i]+"_action";
	            	String moduleAction[]=request.getParameterValues(str);
	            	//System.out.println("LEN>>>>"+moduleAction.length);
	            	Set<Action> actionList = new HashSet<Action> ();
	            		for(int j = 0; j < moduleAction.length; j++){
	            			System.out.println(">>>>>ROLE CTRL Module>>Action  :"+ moduleAction[j]);
	            			Action action=new Action();
		            		action.setName(moduleAction[j]);	            		
		            		actionList.add(action);
	            		}
	            		
		            Module rolemodule=new Module();
		            rolemodule.setName(modules[i]);
		            rolemodule.setActions(actionList);
		            moduleList.add(rolemodule);	           
	        }		
			//role.setName(request.getParameter("name"));
			//role.setDescription(request.getParameter("description"));
			role.setModules(moduleList);	
			roleService.addRole(role);	
			
		}catch(Exception e){
			logger.error(e);
		}
	}
	
	@RequestMapping(value = "/studio/role/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editRole(Role role){
		roleService.updateRole(role);	
	}
	
	@RequestMapping(value = "/studio/restrictRoles/", method = RequestMethod.POST)
	@ResponseBody
    public String getRestrictedRoles(){
		return 	roleService.getRestrictedRoles();	
	}
	
	
}
