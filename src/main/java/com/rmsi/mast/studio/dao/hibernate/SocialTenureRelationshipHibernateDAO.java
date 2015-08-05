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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.dao.SocialTenureRelationshipDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SpatialUnit;


@Repository
public class SocialTenureRelationshipHibernateDAO extends GenericHibernateDAO<SocialTenureRelationship, Long>
		implements SocialTenureRelationshipDAO {
	private static final Logger logger = Logger.getLogger(SocialTenureRelationshipHibernateDAO.class);

	@Override
	public List<SocialTenureRelationship> findbyUsin(Long id) {

		try {
			Query query = getEntityManager().createQuery("Select st from SocialTenureRelationship st where (st.usin = :usin and st.isActive=true)");
			List<SocialTenureRelationship> socialTenure = query.setParameter("usin", id).getResultList();		

			if(socialTenure.size() > 0){
				return socialTenure;
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

	@Override
	public List<SocialTenureRelationship> findByGid(Integer id) {

		try {
			Query query = getEntityManager().createQuery("Select st from SocialTenureRelationship st where st.gid = :gid and st.isActive = true");
			List<SocialTenureRelationship> socialTenureBygid = query.setParameter("gid", id).getResultList();		

			if(socialTenureBygid.size() > 0){
				return socialTenureBygid;
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

	@Override
	public boolean deleteTenure(Long id) {		
		try {
			
			Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isActive = false  where st.gid = :gid");
			
			query.setParameter("gid",id.intValue());

			int rows = query.executeUpdate();
			
			if(rows>0)
			{
				return true;
			}
			else{
				return false;
}
		} catch (Exception e) {
			
			logger.error(e);
			return false;
		}
	
}

	@Override
	public boolean deleteNatural(Long id) {		
		try {
			
			Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isActive = false  where st.person_gid.person_gid = :gid");
			
			query.setParameter("gid",id);

			int rows = query.executeUpdate();
			
			if(rows>0)
			{
				return true;
			}
			else{
				return false;
}
		} catch (Exception e) {
			
			logger.error(e);
			return false;
		}
	
}
	
	
	@Override
	public boolean deleteNonNatural(Long id) {		
		try {
			
			Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isActive = false  where st.person_gid.person_gid = :gid");
			
			query.setParameter("gid",id);

			int rows = query.executeUpdate();
			
			if(rows>0)
			{
				return true;
			}
			else{
				return false;
}
		} catch (Exception e) {
			
			logger.error(e);
			return false;
		}
	
}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SocialTenureRelationship> findDeletedPerson(Long id) {

		ArrayList<SocialTenureRelationship>objTemp=new ArrayList<SocialTenureRelationship>();
		try {
			Query query = getEntityManager().createQuery("Select su from SocialTenureRelationship su where (su.usin = :usin and su.isActive=false)");
			List<SocialTenureRelationship> personList = query.setParameter("usin", id).getResultList();		

			if(personList.size() > 0){
				
				return personList;
			}		
			
		} catch (Exception e) {

			logger.error(e);
			return objTemp;
		}
		return objTemp;

	}

	@Override
	public boolean addDeletedPerson(Long gid) {
		
		try {
			
			Query query = getEntityManager().createQuery("UPDATE SocialTenureRelationship st SET st.isActive = true  where st.person_gid.person_gid = :gid");
			
			query.setParameter("gid",gid);

			int rows = query.executeUpdate();
			
			if(rows>0)
			{
				return true;
			}
			else{
				return false;
}
		} catch (Exception e) {
			
			logger.error(e);
			return false;
		}
	


	}


}
