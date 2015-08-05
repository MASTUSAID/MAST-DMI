package com.rmsi.mast.studio.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KeepAliveController {

	@RequestMapping(value = "/studio/keepalive/", method = RequestMethod.GET)
	@ResponseBody
    public String keepAlive(){
		System.out.println("-----Ping to Keep-alive------");
		return 	"OK";	
	}
}
