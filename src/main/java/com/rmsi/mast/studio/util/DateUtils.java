/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmsi.mast.studio.util;

import java.util.Calendar;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.Date;

public class DateUtils {

    /** 
     * Returns difference between dates in full years 
     * @param fromDate Date from
     * @param toDate Date to
     * @return 
     */
    public static int getYearsDifference(Date fromDate, Date toDate) {
        Calendar a = getCalendar(fromDate);
        Calendar b = getCalendar(toDate);
        
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH)
                || (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }
    
    /** 
     * Returns age 
     * @param birthday Date of birth
     */
    public static int getAge(Date birthday) {
        return getYearsDifference(birthday, Calendar.getInstance().getTime());
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
