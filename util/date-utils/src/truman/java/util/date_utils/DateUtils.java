package truman.java.util.date_utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * This utility class is to provides convenience in formatting/printing
 * the current time.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 * 
 */
public final class DateUtils {

    public static final SimpleDateFormat LOG_TIME_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS", Locale.US);

    public static final SimpleDateFormat HYP_UDS_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss", Locale.US);

    public static final SimpleDateFormat SINGLE_SEP_FORMAT = new SimpleDateFormat(
            "yyyyMMdd_HHmmss", Locale.US);

    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = LOG_TIME_FORMAT;

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String getCurrentTimeFormatted() {
        return convTimeToFormatted(getCurrentTimeMillis());
    }

    public static String convTimeToFormatted(long timeMillis) {
        return convTimeToFormatted(timeMillis, DEFAULT_DATE_FORMAT);
    }

    public static String convTimeToFormatted(long timeMillis, DateFormat dateFormat) {
        Date date = new Date(timeMillis);
        return dateFormat.format(date);
    }

    public static long convTimeToMillis(String formatted) {
        return convTimeToMillis(formatted, DEFAULT_DATE_FORMAT);
    }

    public static long convTimeToMillis(String formatted, DateFormat dateFormat) {
        long timeMillis = 0L;
        try {
            Date date = dateFormat.parse(formatted);
            timeMillis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeMillis;
    }
}
