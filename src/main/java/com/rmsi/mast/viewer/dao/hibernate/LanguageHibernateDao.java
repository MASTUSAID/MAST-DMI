package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Language;
import com.rmsi.mast.viewer.dao.LanguageDAO;

@Repository
public class LanguageHibernateDao extends GenericHibernateDAO<Language, String> implements LanguageDAO {

    private static final Logger logger = Logger.getLogger(LanguageHibernateDao.class);

    @Override
    public List<Language> getLanguages(boolean onlyActive) {
        try {
            String query = "select l from Language l where l.active = 't' order by l.itemOrder";
            if(!onlyActive){
                query = "select l from Language l order by l.itemOrder";
            }
            
            return getEntityManager().createQuery(query).getResultList();
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
}
