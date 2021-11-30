package com.cognizant.fse.eauction.seller.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Product Helper class to manage utility methods
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
public class ProductHelper {


    public static boolean isFutureDate(Date date) {
        return isFutureDate(new Date(), date);
    }

    public static boolean isFutureDate(Date sourceDate, Date targetDate) {
        return stripTimeFromDate(sourceDate).before(stripTimeFromDate(targetDate));
    }

    private static Calendar stripTimeFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}
