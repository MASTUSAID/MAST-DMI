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

package com.rmsi.mast.studio.dao;

import java.io.Serializable;
import java.util.List;

/**
 * This class provides a generic default implementation for many functionalities
 * used in persistence mechanisms. It offers standard CRUD functions for JPA
 * applications plus count() and findInRange() functions as they are frequently
 * used in Web applications.
 * 
 * 
 * @author
 * 
 * @param <T>
 *            the type to be persisted
 * @param <ID>
 *            the identifier type
 */
public interface GenericDAO<T, ID extends Serializable> {

	/**
	 * Save entity.
	 * 
	 * @param entity
	 * @return Persistent entity
	 */
	T makePersistent(T entity);

	/**
	 * Remove entity.
	 * 
	 * @param entity
	 */
	void makeTransient(T entity);

	/**
	 * Remove entity by id.
	 * 
	 * @param entity
	 */
	void makeTransientByID(ID id);

	/**
	 * Count all entities.
	 * 
	 * @return
	 */
	long countAll();

	/**
	 * Find an entity by it id.
	 * 
	 * @param id
	 * @param lock
	 * @return
	 */
	T findById(ID id, boolean lock);

	/**
	 * Return all entities.
	 * 
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 
	 */
	public void flush();
	
	/**
	 * 
	 */
	public void clear();

	/**
	 * Find a range of entities.
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<T> findEntries(int firstResult, int maxResults);
}
