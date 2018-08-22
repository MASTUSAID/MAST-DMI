package com.rmsi.mast.viewer.service.impl;

import com.rmsi.mast.studio.domain.Language;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.dao.LanguageDAO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Contains methods, related to managing languages.
 */
@Service
public class LanguageService {

    private static final Logger logger = Logger.getLogger(RegistrationRecordsImpl.class);

    private List<Language> languages;
    private String languageCode;
    public static final String LANG_SESSION = "langCode";
    public static final String LANG_PARAM = "lang";
    public static final String LANG_COOKIE = "trust_language_code";
    public static final String LANG_LTR = "ltr";

    @Autowired
    LanguageDAO languageDao;
        
    public LanguageService() {
        super();
    }

    public String getLANG_SESSION() {
        return LANG_SESSION;
    }

    public String getLANG_PARAM() {
        return LANG_PARAM;
    }

    public String getLANG_COOKIE() {
        return LANG_COOKIE;
    }

    public String getLANG_LTR() {
        return LANG_LTR;
    }

    /**
     * Returns list of available languages.
     *
     * @param langCode Language code to be used for localization of language
     * names
     * @param onlyActive Indicates that only active languages have to be
     * returned.
     * @return
     */
    public List<Language> getLanguages(String langCode, boolean onlyActive) {
        try {
            // Load langauge if list is null or langauge code is different from previously saved
            if (languages == null || !StringUtils.empty(langCode).equalsIgnoreCase(StringUtils.empty(this.languageCode))) {
                languages = languageDao.getLanguages(onlyActive);
                // Save langauge code
                if (!StringUtils.empty(langCode).equalsIgnoreCase(StringUtils.empty(this.languageCode))) {
                    this.languageCode = langCode;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to extract languages", e);
        }
        return languages;
    }

    /**
     * Returns language by code. Otherwise returns default one
     *
     * @param code Language code
     * @return
     */
    public Language verifyGetLanguage(String code) {
        Language defaultLanguage = null;
        if (getLanguages(code, true) != null) {
            for (Language lang : getLanguages(code, true)) {
                if (defaultLanguage == null && lang.getIsDefault()) {
                    defaultLanguage = lang;
                }
                if (StringUtils.empty(code).equalsIgnoreCase(lang.getCode())) {
                    return lang;
                }
            }
        }
        return defaultLanguage;
    }
}
