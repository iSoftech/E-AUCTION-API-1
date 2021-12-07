package com.cognizant.fse.eauction.seller.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Seller Helper class to manage utility methods
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
public class SellerHelper {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private SellerHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static Date now() {
        return new Date();
    }

    public static Date toDate(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(true);
            date = dateFormat.parse(dateString);
        } catch (Exception exc) {
           return null;
        }
        return date;
    }

    public static boolean isFutureDate(Date date) {
        return isFutureDate(now(), date);
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
