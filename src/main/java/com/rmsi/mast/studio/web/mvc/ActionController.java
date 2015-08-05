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

import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.service.ActionService;

@Controller
public class ActionController {

	@Autowired
	ActionService actionService;
	
	@RequestMapping(value = "/studio/action/", method = RequestMethod.GET)
	@ResponseBody
    public List<Action> list(){
		return 	actionService.findAllActions();	
	}
	
	
	@RequestMapping(value = "/studio/action/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Action getActionById(@PathVariable String id){
		//return 	actionService.findActionById(id);
		return 	actionService.findActionByName(id);
	}
    	
	
	@RequestMapping(value = "/studio/action/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteActions(){
		actionService.deleteAction();
	}
	
	
	@RequestMapping(value = "/studio/action/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteActionById(@PathVariable String id){
		actionService.deleteActionByName(id);	
	}
	
	@RequestMapping(value = "/studio/action/create", method = RequestMethod.POST)
	@ResponseBody
    public void createAction(Action action){
		actionService.addAction(action);	
	}
	
	@RequestMapping(value = "/studio/action/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editAction(Action action){
		actionService.updateAction(action);	
	}
	
		
}
