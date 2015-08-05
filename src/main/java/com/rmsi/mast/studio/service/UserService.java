/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

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
