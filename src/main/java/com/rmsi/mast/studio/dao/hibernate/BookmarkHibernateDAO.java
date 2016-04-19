
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


