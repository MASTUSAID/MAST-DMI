

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Bookmark;


public interface BookmarkDAO extends GenericDAO<Bookmark, Long> {
	
	Bookmark findByName(String name);
	boolean deleteBookmarkByName(String name);
	List<Bookmark> getBookmarksByProject(String projectname);
	boolean deleteByProjectName(String name);
}