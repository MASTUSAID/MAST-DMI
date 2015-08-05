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
package com.rmsi.mast.studio.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import com.rmsi.mast.studio.dao.GenericDAO;

/**
 * @author Nicolas Milliard
 * 
 * @param <T>
 * @param <ID>
 */
public abstract class GenericHibernateDAO<T, ID extends Serializable>
		implements GenericDAO<T, ID> {

	private Class<T> persistentClass;

	@PersistenceContext
	private EntityManager entityManager;

	/**
     * 
     */
	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * @return
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.funix.userstory.dao.GenericDAO#findById(java.io.Serializable,
	 * boolean)
	 */
	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) entityManager.find(getPersistentClass(), id,
					LockModeType.WRITE);
		else
			entity = (T) entityManager.find(getPersistentClass(), id);

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.funix.userstory.dao.GenericDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return entityManager.createQuery(
				"Select t from " + persistentClass.getSimpleName() + " t")
				.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.funix.userstory.dao.GenericDAO#makePersistent(java.lang.Object)
	 */
	public T makePersistent(T entity) {
		T entityPersist = entityManager.merge(entity);
		flush();
		return entityPersist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.funix.userstory.dao.GenericDAO#makeTransient(java.lang.Object)
	 */
	public void makeTransient(T entity) {
		entityManager.remove(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.funix.userstory.dao.GenericDAO#makeTransientByID(ID id)
	 */
	public void makeTransientByID(ID id) {
		entityManager.createQuery(
				new StringBuilder("delete ")
						.append(getPersistentClass().getName())
						.append(" where id = ").append(id).toString())
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.funix.userstory.dao.GenericDAO#countAll()
	 */
	public long countAll() {
		return (Long) entityManager.createQuery(
				new StringBuilder("select count(*) from ").append(
						getPersistentClass().getName()).toString())
				.getSingleResult();
	}

	/**
     * 
     */
	public void flush() {
		entityManager.flush();
	}

	/**
     * 
     */
	public void clear() {
		entityManager.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.funix.userstory.dao.GenericDAO#findEntries(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findEntries(int firstResult, int maxResults) {
		return entityManager
				.createQuery(
						new StringBuilder("select entity from ")
								.append(getPersistentClass().getSimpleName())
								.append(" as entity").toString())
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}
}