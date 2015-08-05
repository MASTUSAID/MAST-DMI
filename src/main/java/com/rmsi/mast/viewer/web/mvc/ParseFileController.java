/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

package com.rmsi.mast.viewer.web.mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.dao.hibernate.ProjectDataHibernateDAO;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


@Controller

public class ParseFileController {
	private static final Logger logger = Logger.getLogger(ParseFileController.class);

	@RequestMapping(value = "/viewer/getColumnName", method = RequestMethod.POST)
	@ResponseBody
    public String[] parseColumnName(HttpServletRequest request, HttpServletResponse response){
		
		String filePath = request.getParameter("filePath");
		String seperator= request.getParameter("seperator");
		
		
		System.out.println("====================================");
		System.out.println("Filepath: "+ filePath);
		System.out.println("seperator: "+ seperator);
		System.out.println("====================================");
		String[] values=null;
		
		try {
			//FileInputStream fstream = new FileInputStream(filePath);
			BufferedReader bufRdr  = new BufferedReader(new FileReader(filePath));
			
			
			String line = bufRdr.readLine();
			System.out.println ("LINE: "+ line);
			values = line.split(seperator);
			
			
			
		} catch (FileNotFoundException ex) {
		     logger.error(ex);
	    }
	    catch (IOException ex) {
	      logger.error(ex);
	    }
	    		
		
		return values;	
	}
	
	@RequestMapping(value = "/viewer/parseFile", method = RequestMethod.POST)
	@ResponseBody
	
	 public String parseFile(HttpServletRequest request, HttpServletResponse response){
		
		String filePath = request.getParameter("filePath");
		String seperator= request.getParameter("seperator");
		String lat = request.getParameter("latitude");
		String lon= request.getParameter("longitude");
		
		System.out.println("====================================");
		System.out.println("Filepath: "+ filePath);
		System.out.println("seperator: "+ seperator);
		System.out.println("LAt: "+ lat);
		System.out.println("Lon: "+ lon);
		System.out.println("====================================");
		int indexOfLongitude = -1;
        int indexOfLatitude = -1;
		
		
		String[] values = null;
		String header="lat,lon,title,description,iconSize,iconOffset,icon";
		try {
			
			
			CSVWriter writer = new CSVWriter(new FileWriter("c:/temp/myfile.txt"), '\t');

			CSVReader reader = new CSVReader(new FileReader(filePath),',','\'', 1);
			
			String []headerArr=header.split(",");

			
			writer.writeNext(headerArr);
			

			BufferedReader bufRdr  = new BufferedReader(new FileReader(filePath));
			
			
			String line = bufRdr.readLine();
			System.out.println ("LINE: "+ line);
			values = line.split(seperator);
			for (int i = 0; i < values.length; i++)
            {
                if (values[i].equalsIgnoreCase(lon))
                {
                    indexOfLongitude = i;
                }
                else if (values[i].equalsIgnoreCase(lat))
                {
                    indexOfLatitude = i;
                }
            }
			
			
			List<String[]> fileEntries = reader.readAll();
			
			Iterator<String[]> iterator = fileEntries.iterator();
			System.out.println("====================================");
			System.out.println("indexOfLatitude: "+ indexOfLatitude);
			System.out.println("indexOfLongitude: "+ indexOfLongitude);
			
			System.out.println("====================================");
			while(iterator.hasNext()){
				String[] columnValue=iterator.next() ;
				String[] colval = new String[7];
				System.out.println("=========="+columnValue.length);
				colval[0]=columnValue[indexOfLatitude];
				colval[1]=columnValue[indexOfLongitude];
				colval[2]="columnValue";
				colval[3]="description";
				colval[4]="21,25";
				colval[5]="-10,-25";
				colval[6]="http://www.openlayers.org/dev/img/marker.png";
								
				
				/*for(String s :values){
					System.out.println(">>>>>"+ s);
				}*/
				
				writer.writeNext(colval);
				
			}
			writer.close();
			
		} catch (FileNotFoundException ex) {
		     logger.error(ex);
	    }
	    catch (IOException ex) {
	     logger.error(ex);
	    }
		
		
		return null;
		
	}
}