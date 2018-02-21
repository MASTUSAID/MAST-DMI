

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Bookmark;


public interface BookmarkDAO extends GenericDAO<Bookmark, Long> {
	
	Bookmark findByName(String name);
	boolean deleteBookmarkByName(String name);
	List<Bookmark> getBookmarksByProject(Integer id);
	boolean deleteByProjectId(Integer id);
	public Bookmark findBookmarkById(Integer id);
}