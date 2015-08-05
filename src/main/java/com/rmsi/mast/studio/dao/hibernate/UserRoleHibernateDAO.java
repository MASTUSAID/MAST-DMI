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

package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.ProjectLayergroupDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectBaselayer;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.Role;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserRole;


@Repository
public class UserRoleHibernateDAO extends GenericHibernateDAO<UserRole, Long>
		implements UserRoleDAO {
	private static final Logger logger = Logger.getLogger(UserRoleHibernateDAO.class);

	
	@SuppressWarnings("unchecked")
	public boolean deleteUserRoleByRole(String role) {
		
		System.out.println(">>>>>>Deleteed from user_role: " + role);
		try{
			Query query = getEntityManager().createQuery(
					"Delete from UserRole ur where ur.roleBean.name =:name")
					.setParameter("name", role);
			
			int count = query.executeUpdate();
			System.out.println("Delete UserRole count: " + count);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error(e);
			return false;
	}
	}

	@SuppressWarnings("unchecked")
	public boolean deleteUserRoleByUser(Integer id) {
		try{
			String qry = "Delete from UserRole ur where ur.user.id =:id";
			Query query = getEntityManager().createQuery(qry).setParameter("id", id);
			System.out.println("UserRoleHibernateDao: " + qry + " " + id);
			
			int count = query.executeUpdate();
			System.out.println("Delete User count: " + count);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			logger.error(e);
			return false;
	}
	}

	
	@SuppressWarnings("unchecked")
	public void addUserRoles(Set<Role> roles, User user) {
				
		//deleteUserRoleByUserId(user.getId());	 
		
		//inser new record
	    Iterator iter1 = roles.iterator();
	    while (iter1.hasNext()) {
	       
	    	Role role=new Role();	    	
	    	role=(Role) iter1.next();	    	
	    	UserRole userrole=new UserRole();	    	
	    	
	    	userrole.setUser(user);
	    	userrole.setRoleBean(role);
	    	
	    	makePersistent(userrole);	
	    	System.out.println("######## USER role INSERT: " +user.getName()+"-"+role.getName());
	    	
	    }
		
	}

	@Override
	public List<UserRole> selectedUserByUserRole(List<String> lstRole) {
		
		

		 
        List<UserRole> lstUser = new ArrayList<UserRole>();
        
        try {
			String userRoleQuery = "SELECT ur FROM Role r JOIN r.userRoles ur WHERE r.name in (:lstRole)"; 
			
			Query query = getEntityManager().createQuery(userRoleQuery);
			query.setParameter("lstRole", lstRole);
			lstUser=query.getResultList();
			if(lstUser.size()>0)
			{
			return lstUser;
			}
			else
			{
				return null;
			}
		} catch (Exception e) {
		
			logger.error(e);
		}
		return lstUser;
	
		
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<UserRole> findAllUserRole(String name) {
		
		List<UserRole> userRole =
			getEntityManager().createQuery("Select ur from UserRole ur where ur.user.name = :name").setParameter("name", name).getResultList();
		
			return userRole;		
	}	

	*/
	
	
	/*
	@SuppressWarnings("unchecked")
	private boolean deleteUserRoleByUserId(Integer userId) {
		try{
			String qry = "Delete from UserRole ur where ur.user.id =:userId";
			Query query = getEntityManager().createQuery(qry).setParameter("userId", userId);
			System.out.println("Deleting id: "+ userId);
			
			int count = query.executeUpdate();
			System.out.println("Delete User count: " + count);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
	}
	}
	*/
	
}
