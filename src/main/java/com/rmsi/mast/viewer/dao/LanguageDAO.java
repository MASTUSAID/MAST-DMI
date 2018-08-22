package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.Language;
import java.util.List;

public interface LanguageDAO extends GenericDAO<Language, String> {
    List<Language> getLanguages(boolean onlyActive);
}
