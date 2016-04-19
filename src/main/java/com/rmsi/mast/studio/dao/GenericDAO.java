

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
