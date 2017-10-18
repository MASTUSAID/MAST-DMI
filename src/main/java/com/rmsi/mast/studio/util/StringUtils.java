package com.rmsi.mast.studio.util;

public class StringUtils {
    /**
     * Check given string and returns true if null or empty, otherwise false.
     * @param str String to check
     * @return 
     */
    public static boolean isEmpty(String str){
        return str == null || str.equals("");
    }
    
    /**
     * Check given string and returns true if not empty, otherwise false.
     * @param str String to check
     * @return 
     */
    public static boolean isNotEmpty(String str){
        return str != null && !str.equals("");
    }

    /**
     * Checks string for null and empty and if true, returns empty value, otherwise provided string will be returned.
     * @param str String to check
     * @return 
     */
    public static String empty(String str){
        if(isEmpty(str)){
            return "";
        }
        return str;
    }
}
