package com.rmsi.mast.studio.util;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

/**
 * File utilities
 */
public class FileUtils {
    /** 
     * Returns MAST files folder path. The folder where uploaded files are stored.
     * @param request HttpServletRequest object to extract context path
     * @return  
     */
    public static String getFielsFolder(HttpServletRequest request){
        return request.getSession().getServletContext().getRealPath(".") + File.separator + ".." + File.separator + "mast_files" + File.separator;
    }
    
    /**
     * Returns file extension based on file name.
     * @param fileName File name
     * @return 
     */
    public static String getFileExtension(String fileName){
        if(fileName == null || fileName.equals("") || !fileName.contains(".")){
            return "";
        }
        return fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toLowerCase();
    }
}
