

package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserOrder;

@Repository
public class UserHibernateDAO extends GenericHibernateDAO<User, Long> implements
UserDAO {
	private static final Logger logger = Logger.getLogger(UserHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public User findByName(String name) {

		List<User> user =
				getEntityManager().createQuery("Select u from User u where u.username = :name").setParameter("name", name).getResultList();

		if(user.size() > 0)
			return user.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteUserByName(Integer id){

		try{

			String qry = "UPDATE User u SET u.active = false  where u.id = :id and u.active = true";
			Query query = getEntityManager().createQuery(qry).setParameter("id", id);
			System.out.println("UserHibernateDao: " + qry);
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
	public User findByUniqueName(String username)
	{
		List<User> user =
				getEntityManager().createQuery("Select u from User u where u.active = true and u.username = :name").setParameter("name", username).getResultList();

		if(user.size() > 0)
			return user.get(0);
		else
			return null;
	}

	/*@SuppressWarnings("unchecked")
	public User findByUniqueId(Integer userId){
		List<User> user =
			getEntityManager().createQuery("Select u from User u where u.active = true and u.id = :id").setParameter("id", userId).getResultList();

		if(user.size() > 0)
			return user.get(0);
		else
			return null;
	}*/

	@SuppressWarnings("unchecked")
	public User findByEmail(String email) {
		System.out.println("--Printing DAO Query  --- " + email);
		try{
			List<User> user =
					getEntityManager().createQuery("Select u from User u where u.email = :name").setParameter("name", email).getResultList();

			System.out.println("--Query executed  --- ");
			if(user.size() > 0){

				return user.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}

	public List<UserOrder> getUserOrderedById(){
		@SuppressWarnings("unchecked")
		List<User> users = 
		getEntityManager().createQuery("Select u from User u order by u.id asc").getResultList();

		List<UserOrder> lstUserOrder = new ArrayList<UserOrder>();
		if(users.size() > 0){
			for(User usr: users){
				UserOrder usrOrder = new UserOrder();
				usrOrder.setId(usr.getId());
				usrOrder.setName(usr.getName());
				lstUserOrder.add(usrOrder);
			}
			return lstUserOrder;
		}else{
			return null;
		}
	}
	//Added by PBJ
	public User findUserByUserId(Integer id){
		try {
			List<User> user = getEntityManager().createQuery("Select u from User u where u.id = :id").setParameter("id", id).getResultList();
			System.out.println(user);
			if(user.size() > 0)
				return user.get(0);
			else
				return null;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;

	}

	@Override
	public List<User> findUserByRole() {
		@SuppressWarnings("unchecked")
		List<User> user = getEntityManager().createQuery("Select u from User u where u.roles = :role").getResultList();
		System.out.println(user);
		if(user.size() > 0)
			return user;
		else
			return null;


	}

	@Override
	public List<User> selectedUserByUserRole(List<String> lstRole) {

		List<User> lstUser = new ArrayList<User>();

		try {
			String userRoleQuery = "Select r from User r where r.userRole. in (:lstRole)"; 
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

	@Override
	public boolean duplicatevalidate(String userName) {

		try {
			Query query = getEntityManager().createQuery("Select u from User u where lower(u.username) = :name");

			query.setParameter("name",userName.toLowerCase());

			List rows = query.getResultList();

			if(rows.size()>0)
			{

				return true;

			}
			else
				return false;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}

	}

	@Override
	public boolean checkreprotinmngr(String repotingId) {

		try {
			Query query = getEntityManager().createQuery("Select u from User u where u.manager_name = :name");

			query.setParameter("name",repotingId);

			List rows = query.getResultList();

			if(rows.size()>0)
			{

				return true;

			}
			else
				return false;
		} catch (Exception e) {

			logger.error(e);
			return false;
		}


	}

	@Override
	public List<User> findUserByUser(ArrayList<Integer> userid) {
		try {
			List<User> user = getEntityManager().createQuery("Select u from User u where u.id in (:userid)").setParameter("userid", userid).getResultList();
			System.out.println(user);
			if(user.size() > 0)
			{
				return user;

			}
			else
			{
				return null;
			}
		} catch (Exception e) {

			logger.error(e);
		}
		return null;
	}

	@Override
	public List<User> findAllActiveUser() {



		try {
			List<User> user =
					getEntityManager().createQuery("Select u from User u where u.active =:true").getResultList();

			if(user.size() > 0)
			{
				return user;
			}
			else
			{	return null;
			}
		} catch (Exception e) {

			logger.error(e);
			return null;
		}

	}

	@Override
	public List<User> findactiveUsers() {

		try {
			@SuppressWarnings("unchecked")
			List<User> user = getEntityManager().createQuery("Select u from User u where u.active = true").getResultList();
			System.out.println(user);
			if(user.size() > 0)
				return user;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
