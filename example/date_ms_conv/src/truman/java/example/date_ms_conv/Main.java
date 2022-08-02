package truman.java.example.date_ms_conv;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static void main(String[] args) {

        long currTime = System.currentTimeMillis();
        System.out.println("Current time sourced   (ms)   : " + currTime);

        Format formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        Date date = new Date(currTime);
        String formattedTime = formatter.format(date);
        System.out.println("Current time formatted (date) : " + formattedTime);

        DateFormat parser = (DateFormat) formatter; // Down-casting
        long convertedTime = 0L;
        try {
            date = parser.parse(formattedTime);
            convertedTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Current time converted (ms)   : " + convertedTime);

    }
}
