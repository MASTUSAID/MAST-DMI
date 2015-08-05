package com.rmsi.mast.studio.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;
//import com.rmsi.spatialvue.studio.dao.PublicUserDAO;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.dao.UserProjectDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserOrder;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.service.UserService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ProjectDAO projectDAO;


	@Autowired
	private UserRoleDAO userRoleDAO;

	@Autowired
	private UserProjectDAO userProjectDAO;



	@Override
	//@TriggersRemove(cacheName="userFBNCache", removeAll=true)	 
	public User addUser(User user) {
		//final String ENCRYPT_KEY = "HG58YZ3CR9";
		Set<Role> roleList = new HashSet<Role> ();
		roleList=user.getRoles();

		//user.setRoles(new HashSet<Role> ());

		return userDAO.makePersistent(user);

		//userRoleDAO.addUserRoles(roleList, user);



	}

	/*@Override
	@TriggersRemove(cacheName="userFBNCache", removeAll=true)
	public boolean addPublicUser(User user,PublicUser publicUser) {

		boolean result=false;
		try{
			Set<Role> roleList = new HashSet<Role> ();
			roleList=user.getRoles();
			user.setRoles(new HashSet<Role> ());

			userDAO.makePersistent(user);

			//publicUserDAO.makePersistent(publicUser);
			userRoleDAO.addUserRoles(roleList, user);
			result=true;
		}
		catch(Exception ex){
			result=false;
		}
		return result;
	}

	@Override
	@TriggersRemove(cacheName="userFBNCache", removeAll=true)
	public PublicUser addPublicUser(PublicUser publicUser) {

		PublicUser objpublicUser=null;
		try{
			//objpublicUser=publicUserDAO.makePersistent(publicUser);
		}
		catch(Exception ex){
			objpublicUser=null;
		}
		return objpublicUser;
	}*/

	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub

	}

	@Override
	//@TriggersRemove(cacheName="userFBNCache", removeAll=true)	    
	public boolean deleteUserById(Integer id) {

		//userRoleDAO.deleteUserRole(id);

		//delete userrole
		userRoleDAO.deleteUserRoleByUser(id);

		//delete project
		userProjectDAO.deleteUserProjectByUser(id);

		//deleting from public user
		//publicUserDAO.deletePublic_UserByName(id);

		//delete user
		return userDAO.deleteUserByName(id);

	}

	@Override
	public void updateUser(User user) {

	}

	@Override
	//@Cacheable(cacheName="userFBNCache")
	public List<User> findUserById(ArrayList<Integer> userid ) {
		return userDAO.findUserByUser(userid);

	}

	@Override
	//@Cacheable(cacheName="userFBNCache")
	public List<User> findUserByRole() {
		return userDAO.findUserByRole();

	}

	@Override
	//@Cacheable(cacheName="userFBNCache")
	public List<User> findAllUser() {
		return userDAO.findactiveUsers();
	}

	@Override
	//@Cacheable(cacheName="userFBNCache")
	public User findUserByName(String name) {
		return userDAO.findByName(name);
	}

	@Override
	//@Cacheable(cacheName="userFBNCache")
	public List<Project> getProjectsByUser(String userName) {

		User user = userDAO.findByName(userName);			

		List<Project> projectlist = new ArrayList<Project>(user.getProjects());

		return  projectlist;
	}

	@Override
	//@Cacheable(cacheName="userFBNCache")
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	@Override
	//@Cacheable(cacheName="userFBNCache")
	public List<UserOrder> getUserOrderedById(){
		return userDAO.getUserOrderedById();
	}

	//added By PBJ
	public User findUserByUserId(Integer id){
		return userDAO.findUserByUserId(id);
	}

	@Override
	public List<User> getAllSurveyUsers() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean checkduplicate(String userName) {
		return userDAO.duplicatevalidate(userName);
	}

	@Override
	public boolean checkreportinmngr(String repotingId) {
		return userDAO.checkreprotinmngr(repotingId);
	}

	@Override
	public boolean checkUserProject(Integer val, String defProjName) {
		return userProjectDAO.checkUserProject(val,defProjName);
	}

	@Override
	public void addUserProject(UserProject userproject) {
		userProjectDAO.makePersistent(userproject);

	}

	@Override
	public List<Project> findAllDefaultProjects() {
		return projectDAO.findAll();

	}

	@Override
	public List<User> findAllActiveUser() {
		return userDAO.findAllActiveUser();
	}

	@Override
	public User findByUniqueName(String name) {
		return userDAO.findByUniqueName(name);
	}





}
