

package com.rmsi.mast.studio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserOrder;
import com.rmsi.mast.studio.domain.UserProject;

/**
 * @author Aparesh.Chakraborty
 *
 */
public interface UserService {

	@Transactional
	User addUser(User user);
	
	@Transactional
	void deleteUser();

	@Transactional
	boolean deleteUserById(Integer id);

	@Transactional
	void updateUser(User user);

	//@Transactional(readOnly=true)
	List<User> findUserById(ArrayList<Integer> userid);

	//@Transactional(readOnly=true)
	List<User> findAllUser();
	
	
	User findUserByName(String name);
	List<Project> getProjectsByUser(String user);
	
	User findByEmail(String email);
	
	/*@Transactional
	boolean addPublicUser(User user,PublicUser publicUser) ;
	
	@Transactional
	PublicUser addPublicUser(PublicUser publicUser) ;*/
	
	List<UserOrder> getUserOrderedById();
	
	//Added By PBJ
	User findUserByUserId(Integer id);
	List<User> getAllSurveyUsers();

	List<User> findUserByRole();

	boolean checkduplicate(String userName);

	boolean checkreportinmngr(String repotingId);
	
	@Transactional
	boolean checkUserProject(Integer val, String defProjName);

	@Transactional
	void addUserProject(UserProject userproject);

	List<Project> findAllDefaultProjects();

	List<User> findAllActiveUser();

	User findByUniqueName(String name);



}
