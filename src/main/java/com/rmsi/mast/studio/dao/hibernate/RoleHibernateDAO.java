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
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.RoleDAO;
import com.rmsi.mast.studio.domain.Role;

/**
 * @author Aparesh.Chakraborty
 * 
 */

@Repository
public class RoleHibernateDAO extends GenericHibernateDAO<Role, Long> implements
		RoleDAO {
	private static final Logger logger = Logger.getLogger(RoleHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public Role findByName(String name) {

		List<Role> role = getEntityManager()
				.createQuery("Select r from Role r where r.name = :name")
				.setParameter("name", name).getResultList();

		if (role.size() > 0)
			return role.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteRole(String name) {

		try {
			Query query = getEntityManager().createQuery(
					"Delete from Role r where r.name =:name").setParameter(
					"name", name);

			int count = query.executeUpdate();
			System.out.println("Delete count ROLE: " + count);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

	}

	@Override
	public List<Role> findAll(int roleId) 
	{
		List<Role> roles = new ArrayList<Role>();
		try {
			
			if(roleId!=1)
			{ 
				String query = "Select r from Role r where r.id not in (1)";
				roles=getEntityManager().createQuery(query).getResultList();
			}
			else
			{
				roles = findAll();
			}
			
			if(roles.size() > 0){
				return roles;
			}		
			else
			{
				return null;
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}
