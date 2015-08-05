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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.BookmarkDAO;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Repository

public class BookmarkHibernateDAO extends GenericHibernateDAO<Bookmark, Long>
implements BookmarkDAO {
	private static final Logger logger = Logger.getLogger(BookmarkHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public Bookmark findByName(String name) {
		List<Bookmark> bookmark =
			getEntityManager().createQuery("Select b from Bookmark b where b.name = :name").setParameter("name", name).getResultList();
				
		if(bookmark.size() > 0)
			return bookmark.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteBookmarkByName(String name) {
		Bookmark bookmark= findByName(name);				
		//System.out.println("DELETE BOOKMARK: "+bookmark.getName()+"-"+bookmark.getProjectBean().getName());
		if(bookmark != null){
			getEntityManager().remove(bookmark);
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Bookmark> getBookmarksByProject(String projectname) {
		List<Bookmark> bookmark =
			getEntityManager().createQuery("Select b from Bookmark b where b.projectBean.name = :name").setParameter("name", projectname).getResultList();		
		if(bookmark.size() > 0)
			return bookmark;
		else
			return null;
	}

	@Override
	public boolean deleteByProjectName(String name) {
		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from Bookmark bm where bm.projectBean.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			System.out.println("Delete count: " + count);
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

}


