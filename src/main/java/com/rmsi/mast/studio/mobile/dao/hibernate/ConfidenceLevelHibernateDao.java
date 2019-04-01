package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ConfidenceLevel;
import com.rmsi.mast.studio.mobile.dao.ConfidenceLevelDao;

@Repository
public class ConfidenceLevelHibernateDao extends GenericHibernateDAO<ConfidenceLevel, Integer> implements ConfidenceLevelDao {

    @Override
    public List<ConfidenceLevel> getAll() {
        return getEntityManager().createQuery("Select c from ConfidenceLevel c order by c.id").getResultList();
    }

    @Override
    public ConfidenceLevel getById(int id) {
        return getEntityManager().find(ConfidenceLevel.class, id);
    }
}
