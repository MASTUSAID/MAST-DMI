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

import com.rmsi.mast.studio.dao.LayertypeDAO;
import com.rmsi.mast.studio.domain.Layertype;
import com.rmsi.mast.studio.service.LayertypeService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Service
public class LayertypeServiceImpl implements LayertypeService{

	@Autowired
	private LayertypeDAO layertypeDAO;

	@Override
	public Layertype addLayertype(Layertype layertype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLayertype() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLayertypeById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLayertype(Layertype layertype) {
		// TODO Auto-generated method stub

	}

	@Override
	//@Cacheable(cacheName="layertypeFBNCache")
	public Layertype findLayertypeById(Long id) {
		return layertypeDAO.findById(id, false);

	}

	@Override
	//@Cacheable(cacheName="layertypeFBNCache")
	public List<Layertype> findAllLayertype() {
		return layertypeDAO.findAll();
	}

	@Override
	//@Cacheable(cacheName="layertypeFBNCache")
	public Layertype findLayertypeByName(String name) {
		return layertypeDAO.findByName(name);
	}
	
}
