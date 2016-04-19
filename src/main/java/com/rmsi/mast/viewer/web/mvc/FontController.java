

package com.rmsi.mast.viewer.web.mvc;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class FontController {

	@RequestMapping(value = "/viewer//font/getfonts", method = RequestMethod.GET)
	@ResponseBody
	
	
	public List<String> getFonts(){
		
		//String[] fonts = null;
		List<String> fonts = new ArrayList<String>();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
//		if(ge.getAvailableFontFamilyNames().length>0){
//			for(int i=0;i<ge.getAvailableFontFamilyNames().length;i++){
//				System.out.println(ge.getAvailableFontFamilyNames()[i]);
//				fonts[i]=ge.getAvailableFontFamilyNames()[i];
//			}
//		}
		
		for(String font:ge.getAvailableFontFamilyNames()){
		   System.out.println(font);
		   fonts.add(font);
		   
		}
		  
		  
		return fonts;	
	}
}
