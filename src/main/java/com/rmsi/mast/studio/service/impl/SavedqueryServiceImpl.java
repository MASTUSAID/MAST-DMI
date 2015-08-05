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

import com.rmsi.mast.studio.dao.SavedqueryDAO;
import com.rmsi.mast.studio.domain.Savedquery;
import com.rmsi.mast.studio.service.SavedqueryService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class SavedqueryServiceImpl implements SavedqueryService{

	@Autowired
	private SavedqueryDAO savedqueryDAO;

	@Override
	public Savedquery addSavedquery(Savedquery savedquery) {

		try {
			return savedqueryDAO.makePersistent(savedquery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void deleteSavedquery() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSavedqueryById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Savedquery updateSavedquery(Savedquery savedquery) {
		return savedqueryDAO.makePersistent(savedquery);

	}

	@Override
	public Savedquery findSavedqueryById(Long id) {
		return savedqueryDAO.findById(id, false);

	}

	@Override
	public List<Savedquery> findAllSavedquery() {
		return savedqueryDAO.findAll();
	}

	@Override
	public Savedquery findSavedqueryByName(String name) {
		return savedqueryDAO.findByName(name);
	}
	
	public List<String> getSavedQueryNames(String project, String layer){
		return savedqueryDAO.getSavedQueryNames(project, layer);
	}
	
	public List<String> getQueryExpression(String queryName){
		return savedqueryDAO.getQueryExpression(queryName);
	}
	
	public String getQueryDescriptionByQueryName(String queryName){
		return savedqueryDAO.getQueryDescription(queryName);
	}
}
