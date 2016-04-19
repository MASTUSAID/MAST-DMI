

package com.rmsi.mast.viewer.web.mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExportResultController {

	@RequestMapping(value = "/viewer/export/", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ExportToXLS(HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		String csvBuffer = request.getParameter("csvBuffer");
		//System.out.println("----Value: " + csvBuffer);
		
		byte[] buffer = csvBuffer.getBytes();
		//System.out.println("----- Buffer size: " + buffer.length);
		response.setContentType("application/vnd.ms-excel");
		response.setBufferSize(buffer.length);
		ServletOutputStream os = response.getOutputStream();
		os.write(buffer);
		os.flush();
		os.close();
		
		return null;
	}
	
	@RequestMapping(value = "/viewer/exportmap/wmc", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ExportMapWmc(HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		String xmlText = request.getParameter("xmlText");
	//String filename = request.getParameter("exportName")+".xml";	
		
		Calendar c = Calendar.getInstance(); 
		
		String filename=c.getTimeInMillis()+".xml";	
					
		byte[] buffer = xmlText.getBytes();			
		response.setBufferSize(buffer.length);
		ServletOutputStream os = response.getOutputStream();
		
		response.setContentType( "text/xml;charset=UTF-8" );		 
		response.setHeader( "Content-Disposition", "attachment;filename="+ filename );
		  
		os.write(buffer);		
		os.flush();
		os.close();
		return null;
	}
	
	
	
	/* Export Image */
	
	
	@RequestMapping(value = "/viewer/exportmap/image", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView ExportMapImage(HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		int width=Integer.parseInt(request.getParameter("img-width").toString());//835;
		int height=Integer.parseInt(request.getParameter("img-height").toString());//710
		String layerurl = request.getParameter("lyrurl");
		String _bbox = request.getParameter("bbox");
		
		System.out.println("----------------------------------");
		System.out.println("------layerurl:"+layerurl);
		System.out.println("------_bbox:"+_bbox);
		System.out.println("------width: "+width);
		System.out.println("------height: "+height);
		System.out.println("----------------------------------");
		//String filename = request.getParameter("exportName");
		
		Calendar c = Calendar.getInstance(); 
	
		String filename=c.getTimeInMillis()+".png";		
		
		String[] urls= layerurl.split("[|]");
		
		BufferedImage img= null;
		
		BufferedImage margeImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
		Graphics g = margeImg.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		for(int i=0;i<urls.length;i++){
			
			try {
				  
				 //URL url = new URL(urls[i]+"&WIDTH="+width+"&HEIGHT="+height);				 
				URL url = new URL(urls[i]+"&BBOX="+_bbox+"&WIDTH="+width+"&HEIGHT="+height); 
				img = ImageIO.read(url);
				 g.drawImage(img, 0, 0, null);
				   
				   
			} catch (IOException e) {
			
			}
		
		}
		
		
		ServletOutputStream os = response.getOutputStream();
		response.setContentType("image/png"); 		
		response.setHeader( "Content-Disposition", "attachment;filename="
			      + filename );
		
		
		//ImageIO.write(margeImg, "PNG", new File("D:\\exportmap.png"));
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ImageIO.write(margeImg,"png",baos);
		
		byte[] buffer = baos.toByteArray();
		
		System.out.println("FILE write");
		os.write(buffer);		
		os.flush();
		os.close();
		
		
		return null;
	}

	
}
