package com.dibya.sonar.entity.util;

import java.util.Date;

/**
 * Helper for comparing two dates. DateUtils has similar functionality but
 * assumes that the parameters must be not null which induces safety checks on
 * the client side and increased code complexity. Hence an alternative.
 * 
 * @author Dibya Ranjan
 */
public class DateComparator {
    
    /**
     * Checks if two dates are equal. Two dates can be said equal only if both
     * dates are not null and the mili seconds are equal
     * 
     * @param date1
     *            first date
     * @param date2
     *            second date
     * @return true if they are equal false otherwise
     */
    public static boolean areDatesEqual(Date date1, Date date2) {
    	if (date1 == null) {
    		return false;
    	}
    	
    	if (date2 == null) {
    		return false;
    	}
    	
        if (date1.getTime() != date2.getTime()) {
            return false;
        }

        return true;
    }
}
