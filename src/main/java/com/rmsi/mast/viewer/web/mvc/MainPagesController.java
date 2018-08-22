package com.rmsi.mast.viewer.web.mvc;

import com.rmsi.mast.studio.domain.Language;
import com.rmsi.mast.viewer.service.impl.LanguageService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPagesController {

    @Autowired
    LanguageService langService;

    private static final Logger logger = Logger.getLogger(MainPagesController.class);

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "login";
    }

    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "index";
    }

    @RequestMapping(value = "/accessdenied", method = {RequestMethod.GET, RequestMethod.POST})
    public String accessdenied(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "accessdenied";
    }

    @RequestMapping(value = "/viewer/", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewerIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "viewer/index";
    }

    @RequestMapping(value = "/viewer/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewerIndex2(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "viewer/index";
    }

    @RequestMapping(value = "/mobileconfig/", method = {RequestMethod.GET, RequestMethod.POST})
    public String mobileIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "mobile/index";
    }

    @RequestMapping(value = "/mobileconfig/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String mobileIndex2(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "mobile/index";
    }

    @RequestMapping(value = "/studio/", method = {RequestMethod.GET, RequestMethod.POST})
    public String studioIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "studio/index";
    }

    @RequestMapping(value = "/studio/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String studioIndex2(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        setLanguageCode(request, response);
        return "studio/index";
    }

    private void setLanguageCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (session.getAttribute(LanguageService.LANG_SESSION) == null
                || session.getAttribute(LanguageService.LANG_SESSION).equals("")
                || (request.getParameter(LanguageService.LANG_PARAM) != null
                && !request.getParameter(LanguageService.LANG_PARAM).equals(""))) {

            String langCode = null;
            String langRequestParam = request.getParameter(LanguageService.LANG_PARAM);
            Cookie existinCookie = null;

            if (langRequestParam != null && !langRequestParam.equals("")) {
                langCode = request.getParameter(LanguageService.LANG_PARAM);
            }

            // Try to load from cookies
            if (langCode == null || langCode.equals("")) {
                Cookie[] cookies = request.getCookies();

                if (cookies != null) {
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie cookie = cookies[i];
                        if (cookie.getName().equalsIgnoreCase(LanguageService.LANG_COOKIE)) {
                            langCode = cookie.getValue();
                            existinCookie = cookie;
                            break;
                        }
                    }
                }
            }

            // If no cookie found, get from browser
            if (langCode == null || langCode.equals("")) {
                langCode = request.getLocale().toLanguageTag();
            }

            // Verify that selected language exists in DB. Otherwise, load default.
            //LanguageService langService = new LanguageService();
            Language lang = langService.verifyGetLanguage(langCode);
            if (lang != null) {
                langCode = lang.getCode();
                session.setAttribute(LanguageService.LANG_LTR, lang.getLtr());
            } else {
                session.setAttribute(LanguageService.LANG_LTR, true);
            }

            // If nothing found, set to English
            if (langCode == null || langCode.equals("")) {
                langCode = "en";
            }

            // Finally save to session and cookie
            session.setAttribute(LanguageService.LANG_SESSION, langCode);
            session.setAttribute("langs", langService.getLanguages(langCode, true));

            Cookie cookie;
            if (existinCookie != null) {
                cookie = existinCookie;
            } else {
                cookie = new Cookie(LanguageService.LANG_COOKIE, langCode);
            }
            cookie.setPath("/");
            cookie.setMaxAge(31536000);
            response.addCookie(cookie);

            // Redirect to the same page in case of postback to avoid re-submit on page refresh
            if (langRequestParam != null && !langRequestParam.equals("")) {
                String queryString = request.getQueryString();

                try {
                    if (queryString != null && !queryString.equals("")) {
                        response.sendRedirect(request.getRequestURI() + "?" + queryString);
                    } else {
                        response.sendRedirect(request.getRequestURI());
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}
