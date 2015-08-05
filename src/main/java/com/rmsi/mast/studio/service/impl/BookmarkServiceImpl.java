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
