package com.rmsi.mast.studio.util;

import java.io.*;
import java.util.*;
import java.util.zip.*;
 
public class ZipFileUtil {
  	
    /**
     * This method
     * --Reads an input stream
     * --Writes the value to the output stream
     * --Uses 1KB buffer.
     */
    private static final void writeFile(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;
 
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
 
        in.close();
        out.close();
    }
    
    @SuppressWarnings("rawtypes")
    public static boolean unzipZipFile(String srcZipPath,
            String directoryToExtractTo,StringBuffer fileNameByType) {
        
    	String fileType=null;
    	if(fileNameByType!=null){
    		fileType=fileNameByType.toString();
    		fileNameByType.delete(0, fileNameByType.length());
    	}
		Enumeration entriesEnum;
        ZipFile zipFile;
        try {
        	
            zipFile = new ZipFile(srcZipPath);
            entriesEnum = zipFile.entries();
 
            File directory= new File(directoryToExtractTo);
 
            /**
             * Check if the directory to extract to exists
             */
            if(!directory.exists())
            {
                /**
                 * If not, create a new one.
                 */
                new File(directoryToExtractTo).mkdir();
                System.out.println("...Directory Created -"+directoryToExtractTo);
            }
            while (entriesEnum.hasMoreElements()) {
                try {
                    ZipEntry entry = (ZipEntry) entriesEnum.nextElement();
 
                    if (entry.isDirectory()) {
                        /**
                         * Currently not unzipping the directory structure.
                         * All the files will be unzipped in a Directory
                         *
                         **/
                    } else {
 
                        System.out.println("Extracting file: "
                                + entry.getName());
                        /**
                         * The following logic will just extract the file name
                         * and discard the directory
                         */
                        int index = 0;
                        String name = entry.getName();
                        index = entry.getName().lastIndexOf("/");
                        if (index > 0 && index != name.length())
                            name = entry.getName().substring(index + 1);
 
                        System.out.println(name);
 
                        if(fileType!=null && name.toUpperCase().trim().endsWith(fileType.toUpperCase().trim())){
                        	fileNameByType.append(name);
                        }
                        
                        writeFile(zipFile.getInputStream(entry),
                                new BufferedOutputStream(new FileOutputStream(
                                        directoryToExtractTo +"/"+ name)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
 
            zipFile.close();
        } catch (IOException ioe) {
            System.err.println("Some Exception Occurred:");
            ioe.printStackTrace();
            return false;
        }
        return true;
    }
 
}
