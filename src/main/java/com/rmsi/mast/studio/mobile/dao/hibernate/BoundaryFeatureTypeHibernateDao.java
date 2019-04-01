package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.BoundaryFeatureType;
import com.rmsi.mast.studio.mobile.dao.BoundaryFeatureTypeDao;

@Repository
public class BoundaryFeatureTypeHibernateDao extends GenericHibernateDAO<BoundaryFeatureType, Integer> implements BoundaryFeatureTypeDao {

    @Override
    public List<BoundaryFeatureType> getAll() {
        return getEntityManager().createQuery("Select c from BoundaryFeatureType c order by c.id").getResultList();
    }

    @Override
    public BoundaryFeatureType getById(int id) {
        return getEntityManager().find(BoundaryFeatureType.class, id);
    }
}
