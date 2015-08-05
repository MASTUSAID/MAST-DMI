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

import com.rmsi.mast.studio.dao.ProjectionDAO;
import com.rmsi.mast.studio.domain.Projection;
import com.rmsi.mast.studio.service.ProjectionService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class ProjectionServiceImpl implements ProjectionService{

	@Autowired
	private ProjectionDAO projectionDAO;

	@Override
	public Projection addProjection(Projection Projection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProjection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProjectionById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProjection(Projection Projection) {
		// TODO Auto-generated method stub

	}

	@Override
	public Projection findProjectionById(Long id) {
		return projectionDAO.findById(id, false);

	}

	@Override
	public List<Projection> findAllProjection() {
		return projectionDAO.findAll();
	}

	@Override
	public Projection findProjectionByName(String name) {
		return projectionDAO.findByName(name);
	}
	
}
