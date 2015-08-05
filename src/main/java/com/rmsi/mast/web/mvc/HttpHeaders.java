package com.rmsi.mast.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HttpHeaders {

	@RequestMapping(value = "/locale/lang/", method = RequestMethod.GET)
	@ResponseBody
	public String ReadHeaders(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return request.getLocale().getLanguage();
	}

}
