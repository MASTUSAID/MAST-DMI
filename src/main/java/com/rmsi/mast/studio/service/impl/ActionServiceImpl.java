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










import com.rmsi.mast.studio.dao.ActionDAO;
import com.rmsi.mast.studio.domain.Action;
import com.rmsi.mast.studio.service.ActionService;

@Service
public class ActionServiceImpl implements ActionService {

	@Autowired
	private ActionDAO actionDAO;

	@Override
	public Action addAction(Action action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteActionByName(String id) {
		actionDAO.deleteByName(id);
	}

	@Override
	public void updateAction(Action action) {
		// TODO Auto-generated method stub

	}

	@Override
	public Action findActionById(Long id) {
		return actionDAO.findById(id, false);

	}

	@Override
	public List<Action> findAllActions() {
		
		return actionDAO.findAll();
	}

	@Override
	public Action findActionByName(String name) {
		return actionDAO.findByName(name);
	}

	
}
