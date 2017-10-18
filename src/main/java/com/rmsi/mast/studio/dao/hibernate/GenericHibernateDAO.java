package com.rmsi.mast.studio.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import com.rmsi.mast.studio.dao.GenericDAO;

public abstract class GenericHibernateDAO<T, ID extends Serializable>
        implements GenericDAO<T, ID> {

    private Class<T> persistentClass;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericHibernateDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public T findById(ID id, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) entityManager.find(getPersistentClass(), id,
                    LockModeType.WRITE);
        } else {
            entity = (T) entityManager.find(getPersistentClass(), id);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return entityManager.createQuery(
                "Select t from " + persistentClass.getSimpleName() + " t")
                .getResultList();
    }

    @Override
    public T makePersistent(T entity) {
        T entityPersist = entityManager.merge(entity);
        flush();
        return entityPersist;
    }

    @Override
    public void makeTransient(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void makeTransientByID(ID id) {
        entityManager.createQuery(
                new StringBuilder("delete ")
                .append(getPersistentClass().getName())
                .append(" where id = ").append(id).toString())
                .executeUpdate();
    }

    @Override
    public long countAll() {
        return (Long) entityManager.createQuery(
                new StringBuilder("select count(*) from ").append(
                        getPersistentClass().getName()).toString())
                .getSingleResult();
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
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
