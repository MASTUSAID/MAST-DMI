

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Bookmark;


public interface BookmarkService {

	@Transactional
	void addBookmark(Bookmark bookmark);
	
	@Transactional
	void deleteBookmarkByProject(String name);

	@Transactional
	boolean deleteBookmarkById(String name);

	@Transactional
	void updateABookmark(Bookmark bookmark);

	@Transactional(readOnly=true)
	Bookmark findBookmarkById(Long id);

	@Transactional(readOnly=true)
	List<Bookmark> findAllBookmarks();
	
	@Transactional(readOnly=true)
	Bookmark findBookmarkByName(String name);
	
	List<Bookmark> getBookmarksByProject(String project);
}
