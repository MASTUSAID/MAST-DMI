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

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.domain.Project;


@Repository
public class ProjectHibernateDAO extends GenericHibernateDAO<Project, Long>
		implements ProjectDAO {
	private static final Logger logger = Logger.getLogger(ProjectHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public Project findByName(String name) {
		List<Project> project =
			getEntityManager().createQuery("Select p from Project p where p.name = :name").setParameter("name", name).getResultList();
		
		if(project.size() > 0)
			return project.get(0);
		
		else
			return null;
	}
	
	public List<String> getProjectNames(){
		
		TypedQuery<String> query = getEntityManager().createQuery("Select p.name from Project p", String.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public boolean deleteProject(String name) {		
		
		try {
			Query query = getEntityManager().createQuery("UPDATE Project p SET p.active = false where p.name = :name and p.active = true");
			
			query.setParameter("name",name);

			int rows = query.executeUpdate();
			
			if(rows>0)
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

	@SuppressWarnings("unchecked")
	public List<Project> findAllProjects() {
		
		//String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
		String hqlstr="Select p from Project p where (p.admincreated=true and p.active=true) order by p.name";
		List<Project> project =
			getEntityManager().createQuery(hqlstr).getResultList();
	
		
		return project;
	}

	@SuppressWarnings("unchecked")
	public List<Project> getAllUserProjects() {
		//String hqlstr= "Select p from Project p inner join ProjectLayergroup plg on p.name = plg.projectBean.name"; 
		String hqlstr="Select p from Project p where p.admincreated=false order by p.name";
		List<Project> project =
			getEntityManager().createQuery(hqlstr).getResultList();
	
		
		return project;
	}
		
	@SuppressWarnings("unchecked")
	public List<Project> getProjectsByOwner(String email) {
		
		String hqlstr="Select p from Project p where p.admincreated=false and p.owner=:email order by p.name";
		List<Project> project =
			getEntityManager().createQuery(hqlstr).setParameter("email", email).getResultList();
				
		return project;
	}

	@Override
	public boolean checkduplicatename(String projectName) {
		
		try {
			String hqlstr="Select p from Project p where p.name=:name";
			List<Project> project =
				getEntityManager().createQuery(hqlstr).setParameter("name", projectName).getResultList();
			
			if(project.size()>0)
			{
				return true;
				
			}
			else
			{
				return false;
				
			}
		} catch (Exception e) {
			
			logger.error(e);
			return false;
		}
		
	}
	
	
}
