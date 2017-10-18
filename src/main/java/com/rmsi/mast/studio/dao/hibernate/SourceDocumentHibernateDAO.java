package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.SourceDocumentDAO;
import com.rmsi.mast.studio.domain.SourceDocument;

@Repository
public class SourceDocumentHibernateDAO extends GenericHibernateDAO<SourceDocument, Integer> implements SourceDocumentDAO {

    private static final Logger logger = Logger.getLogger(SourceDocumentHibernateDAO.class);

    @Override
    public List<SourceDocument> findSourceDocumentById(Long id) {
        try {
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.usin = :usin and sd.active= true");
            List<SourceDocument> sourcedoc = query.setParameter("usin", id).getResultList();

            if (sourcedoc.size() > 0) {
                return sourcedoc;
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }
    }
    
    @Override
    public List<SourceDocument> getDocumentsByDispute(Long disputeId){
        try {
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.disputeId = :disputeId and sd.active= true");
            List<SourceDocument> sourcedoc = query.setParameter("disputeId", disputeId).getResultList();

            if (sourcedoc.size() > 0) {
                return sourcedoc;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<SourceDocument> findByGId(Long id) {

        try {
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.gid = :gid");
            List<SourceDocument> sourcedoc = query.setParameter("gid", id.intValue()).getResultList();

            if (sourcedoc.size() > 0) {
                return sourcedoc;
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }

    @Override
    public boolean deleteMultimedia(Long id) {
        try {

            Query query = getEntityManager().createQuery("UPDATE SourceDocument sd SET sd.active = false  where sd.gid = :gid");

            query.setParameter("gid", id.intValue());

            int rows = query.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    @Override
    public SourceDocument findDocumentByAdminId(Long adminID) {

        try {
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.adminid = :adminid");
            List<SourceDocument> documentlist = query.setParameter("adminid", adminID).getResultList();

            if (documentlist.size() > 0) {
                return documentlist.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }

    @Override
    public SourceDocument getDocumentByPerson(Long person_gid) {

        try {
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.person_gid = :person_gid");
            List<SourceDocument> documentlist = query.setParameter("person_gid", person_gid).getResultList();

            if (documentlist.size() > 0) {
                return documentlist.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }

    }

    @Override
    public boolean deleteNaturalPersonImage(Long id) {
        try {

            Query query = getEntityManager().createQuery("UPDATE SourceDocument sd SET sd.active = false  where sd.person_gid = :id");

            query.setParameter("id", id);

            int rows = query.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }

    }

    @Override
    public boolean checkPersonImage(Long id) {

        try {
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.person_gid.person_gid = :person_gid");
            List<SourceDocument> documentlist = query.setParameter("person_gid", id).getResultList();

            if (documentlist.size() > 0) {
                return documentlist.get(0).isActive();
            } else {
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

    }

}
