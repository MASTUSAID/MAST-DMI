package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.AcquisitionTypeDao;
import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.domain.LaPartygroupOccupation;
import com.rmsi.mast.studio.mobile.dao.LaPartygroupOccupationDAO;


@Repository
public class LaPartygroupOccupationHibernateDAO extends GenericHibernateDAO<LaPartygroupOccupation, Integer> implements LaPartygroupOccupationDAO {

	@Override
	public LaPartygroupOccupation getOccupation(Integer Id) {
        String query = "select a from LaPartygroupOccupation a where a.occupationid = :attributeId ";

        try {
            List<LaPartygroupOccupation> attributeids = getEntityManager()
                    .createQuery(query)
                    .setParameter("attributeId", Id).getResultList();

            if (attributeids != null && attributeids.size() > 0) {
                return attributeids.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;
    }

}
