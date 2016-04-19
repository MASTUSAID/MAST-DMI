

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
		
		bookmarkDAO.makePersistent(bookmark);
		
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
	//@Cacheable(cacheName="bookmarkFBNCache")
	public Bookmark findBookmarkById(Long id) {
		return bookmarkDAO.findById(id, false);

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
	public List<Bookmark> getBookmarksByProject(String project) {
		return bookmarkDAO.getBookmarksByProject(project);
	}

	@Override
	//@TriggersRemove(cacheName="bookmarkFBNCache", removeAll=true)	    
	public void deleteBookmarkByProject(String name) {
		bookmarkDAO.deleteByProjectName(name);
	}
	
}
