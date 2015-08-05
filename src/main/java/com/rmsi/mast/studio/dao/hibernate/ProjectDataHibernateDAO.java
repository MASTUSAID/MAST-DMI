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
import com.rmsi.mast.studio.dao.ProjectDataDAO;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;


@Repository
public class ProjectDataHibernateDAO extends GenericHibernateDAO<ProjectSpatialData, Long>
		implements ProjectDataDAO {
	private static final Logger logger = Logger.getLogger(ProjectDataHibernateDAO.class);
	
	@Override
	public boolean deleteData(Long id) {
		
		try{
			String qry = "Delete from ProjectSpatialData p where p.id =:id";
			Query query = getEntityManager().createQuery(qry).setParameter("id", id.intValue());
				int count = query.executeUpdate();
	
			if(count > 0){
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e){
			logger.error(e);
			return false;
	}

		
	}

	@Override
	public List<ProjectSpatialData> selectedAttachment(String name) {
		
		if(name.equals("All"))
		{
		
			Query query = getEntityManager().createQuery("Select c from ProjectSpatialData c") ;
			
			List<ProjectSpatialData> selectedattachment = query.getResultList();	
			return selectedattachment;
			
		}
	
		else {
			Query query = getEntityManager().createQuery("Select c from ProjectSpatialData c where c.name = :name") ;
		
			query.setParameter("name",name);
		List<ProjectSpatialData> selectedattachment = query.getResultList();		
		
		if(selectedattachment.size() > 0){
			return selectedattachment;
		}		
		else
		{
			return null;
		}
		
		
	}
	
	}

	@Override
	public void deleteByProjectName(String name) {

		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from ProjectSpatialData psd where psd.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			
			System.out.println(count);
			System.out.println(count);
			
		}catch(Exception e){
			logger.error(e);
			
		}

		
		
	}
	

	
}
