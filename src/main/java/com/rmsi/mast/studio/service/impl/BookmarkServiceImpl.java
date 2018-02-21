

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.googlecode.ehcache.annotations.Cacheable;
//import com.googlecode.ehcache.annotations.TriggersRemove;


import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.dao.BookmarkDAO;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.service.BookmarkService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class BookmarkServiceImpl  implements BookmarkService{

	@Autowired
	private BookmarkDAO bookmarkDAO;

	@Override
	//@TriggersRemove(cacheName="bookmarkFBNCache", removeAll=true)	    
	public void addBookmark(Bookmark bookmark) {
		
		try {
			bookmarkDAO.makePersistent(bookmark);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	//@TriggersRemove(cacheName="bookmarkFBNCache", removeAll=true)	    
	public boolean deleteBookmarkById(String  name) {
		
		return bookmarkDAO.deleteBookmarkByName(name);
	}

	@Override
	public void updateABookmark(Bookmark bookmark) {
		// TODO Auto-generated method stub

	}

	@Override
	public Bookmark findBookmarkById(Integer id) {
		return bookmarkDAO.findBookmarkById(id);
		

	}

	@Override
	//@Cacheable(cacheName="bookmarkFBNCache")
	public List<Bookmark> findAllBookmarks() {
		return bookmarkDAO.findAll();
	}

	@Override
	//@Cacheable(cacheName="bookmarkFBNCache")
	public Bookmark findBookmarkByName(String name) {
		return bookmarkDAO.findByName(name);
	}

	@Override
	//@Cacheable(cacheName="bookmarkFBNCache")
	public List<Bookmark> getBookmarksByProject(Integer id) {
		return bookmarkDAO.getBookmarksByProject(id);
	}

	@Override
	//@TriggersRemove(cacheName="bookmarkFBNCache", removeAll=true)	    
	public void deleteBookmarkByProjectId(Integer id) {
		bookmarkDAO.deleteByProjectId(id);
	}
	
}
