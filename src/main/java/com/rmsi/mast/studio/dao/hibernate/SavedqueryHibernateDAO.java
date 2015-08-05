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

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.SavedqueryDAO;
import com.rmsi.mast.studio.domain.Savedquery;

@Repository
public class SavedqueryHibernateDAO extends GenericHibernateDAO<Savedquery, Long> implements
		SavedqueryDAO {

	@SuppressWarnings("unchecked")
	public Savedquery findByName(String name) {
		
		List<Savedquery> savedquery =
			getEntityManager().createQuery("Select sq from Savedquery sq where sq.name = :name").setParameter("name", name).getResultList();
		
		if(savedquery.size() > 0)
			return savedquery.get(0);
		else
			return null;
	}
	
	public List<String> getSavedQueryNames(String project, String layer){
		
		String sql = "Select q.name from Savedquery q where " +
				"q.projectBean.name =:project and q.layerBean.alias =:layer";
		
		TypedQuery<String> query = getEntityManager().createQuery(sql, String.class)
		.setParameter("project", project)
		.setParameter("layer", layer);
		return query.getResultList();
	}
	
	public List<String> getQueryExpression(String queryName){
		String sql = "Select q.whereexpression from Savedquery q " +
				"where q.name =:queryName";
		TypedQuery<String> query = getEntityManager().createQuery(sql, String.class)
		.setParameter("queryName", queryName);
	
		return query.getResultList();
	}
	
	public String getQueryDescription(String qryName){
		String sql = "Select s.description from Savedquery s " +
						"where s.name =:queryName";
		
		TypedQuery<String> query = getEntityManager().createQuery(sql, String.class)
		.setParameter("queryName", qryName);
	
		List<String> lstResult = query.getResultList();
		if(lstResult.size() > 0){
			return lstResult.get(0);
		}else{
			return new String();
		}
	}
}
