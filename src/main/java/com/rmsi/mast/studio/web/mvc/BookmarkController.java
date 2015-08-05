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

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.service.BookmarkService;


/**
 * @author Aparesh.Chakraborty
 *
 */


@Controller
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;
	
	@RequestMapping(value = "/studio/bookmark/", method = RequestMethod.GET)
	@ResponseBody
    public List<Bookmark> list(){
		return 	bookmarkService.findAllBookmarks();	
	}
    
	@RequestMapping(value = "/studio/bookmark/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Bookmark getBookmarkById(@PathVariable String id){
		return 	bookmarkService.findBookmarkByName(id);	
	}
    
	@RequestMapping(value = "/studio/bookmark/project/{id}", method = RequestMethod.GET)
	@ResponseBody
    public List<Bookmark> getBookmarksByProject(@PathVariable String id){
		return 	bookmarkService.getBookmarksByProject(id);	
	}
	
	
	
	@RequestMapping(value = "/studio/bookmark/delete/project/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteBookmark(@PathVariable String id){
		bookmarkService.deleteBookmarkByProject(id);
	}
	
	
	@RequestMapping(value = "/studio/bookmark/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public boolean deleteBookmarkById(@PathVariable String id){
		return bookmarkService.deleteBookmarkById(id);	
	}
	
	@RequestMapping(value = "/studio/bookmark/create", method = RequestMethod.POST)
	@ResponseBody
    public void createBookmark(Bookmark bookmark){
		bookmarkService.addBookmark(bookmark);	
	}
	
	@RequestMapping(value = "/studio/bookmark/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editBookmark(Bookmark bookmark){
		bookmarkService.updateABookmark(bookmark);	
	}
	
}
