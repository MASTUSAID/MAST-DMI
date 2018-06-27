package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.SourceDocumentDAO;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
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
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.laSpatialunitLand = :gid");
            List<SourceDocument> sourcedoc = query.setParameter("gid", id).getResultList();

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
    public SourceDocument getDocumentByPerson(Long documentid) {

        try {
        	
        	//documentid = 7L;
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.laExtTransactiondetail.transactionid = :documentid");
            List<SourceDocument> documentlist = query.setParameter("documentid", documentid.intValue()).getResultList();

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
    public SourceDocument getdocumentByPersonfortransaction(Long transactionid, Long partyid)
    {

        try {
        	
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.laExtTransactiondetail.transactionid = :transactionid and sd.laParty.partyid= :partyid ");
            List<SourceDocument> documentlist = query.setParameter("transactionid", transactionid).setParameter("partyid", partyid).getResultList();

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
                return documentlist.get(0).getIsactive();
            } else {
                return false;
            }
        } catch (Exception e) {

            logger.error(e);
            return false;
        }

    }

	@Override
	public List<SourceDocument> findSourceDocumentByLandIdandTransactionid(
			Long id, Integer transactionid) {
		
		  try {
	        	
	            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.laExtTransactiondetail.transactionid = :transactionid and sd.laSpatialunitLand= :landId  and sd.isactive= true");
	            List<SourceDocument> documentlist = query.setParameter("transactionid", transactionid).setParameter("landId", id).getResultList();
	           return    documentlist;
	           
	        } catch (Exception e) {

	            logger.error(e);
	            return null;
	        }

	}

	@Override
	public List<SourceDocument> findSourceDocumentByLandIdAndProessId(Long id,Long processId) {
	

		try {
	            
	            String strQuery = " select * from la_ext_documentdetails plm left join la_ext_transactiondetails td on td.transactionid=plm.transactionid"
	            		+ "  where plm.landid= "+id +" and td.processid=" + processId +" and td.applicationstatusid=1 and plm.isactive= true";
				
				List<SourceDocument> lstSourceDocument = getEntityManager().createNativeQuery(strQuery,SourceDocument.class).getResultList();
	           return lstSourceDocument;

	        } catch (Exception e) {

	            logger.error(e);
	            return null;
	        }
			
	}

	@Override
	public SourceDocument findDocumentByDocumentId(Long documentId) {
        try {
            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.documentid = :documentid and sd.isactive= true");
            List<SourceDocument> sourcedoc = query.setParameter("documentid", documentId).getResultList();

            if (sourcedoc.size() > 0) {
                return sourcedoc.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {

            logger.error(e);
            return null;
        }
    }

	@Override
	public List<SourceDocument> findBatchSourceDocumentByLandIdandTransactionid(
			Long transactionid) {
		
	/*	Long startid=transactionidstart;
		
		Long endid=transactionidend;
		
		Long transiddiffr = transactionidend - transactionidstart;
		String ids= startid+",";
		for(int i=0; i<transiddiffr; i++){
			transactionidstart =transactionidstart+1;
			ids=ids+transactionidstart+",";
		}
		
		ids=ids.substring(0, ids.length()-1);
		*/
		  try {
	        	
	            Query query = getEntityManager().createQuery("Select sd from SourceDocument sd where sd.laExtTransactiondetail.transactionid ="+ transactionid+" and sd.isactive= true");
	            List<SourceDocument> documentlist = query.getResultList();
	           return    documentlist;
	           
	        } catch (Exception e) {

	            logger.error(e);
	            return null;
	        }

	}

}
