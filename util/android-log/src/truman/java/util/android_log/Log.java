package truman.java.util.android_log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * This class is for increasing an inter-compatability between normal java codes
 * and android java codes.
 * 
 * @version 0.2.1
 * @author Truman Kim (truman.t.kim@gmail.com)
 * 
 */
public final class Log {

    private static PrintStream dStream;
    private static PrintStream eStream;
    private static PrintWriter fWriter;

    static {
        dStream = System.out;
        eStream = System.err;
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss", Locale.US);

    private static volatile boolean toFile;

    /**
     * Changing output destination is not multi-thread safe. So you'd better
     * determine where to print logs at the very beginning of your program.
     * 
     * This method will create new file named based on current time, and
     * coming log after this will be printed into that file.
     */
    public static void setOutToFile() {
        String time = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
        String fileName = "log_" + time + ".txt";
        setOutToFile(fileName);
    }

    public static void setOutToFile(String fileName) {
        if (fWriter != null) {
            fWriter.flush();
            fWriter.close();
            fWriter = null;
        }

        if (fileName == null || fileName.isEmpty()) {
            toFile = false;
        } else {
            try {
                fWriter = new PrintWriter(new FileWriter(fileName));
                toFile = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void d(String tag, Object obj) {
        d(String.format("%-20s %s",
                (tag == null ? "null" : tag),
                (obj == null ? "null" : obj.toString())));
    }

    public static void d(Object obj) {
        d(obj == null ? "null" : obj.toString());
    }

    private static void d(String m) {
        if (toFile) {
            fWriter.println(m);
        } else {
            dStream.println(m);
        }
    }

    public static void i(String tag, Object obj) {
        d(tag, obj);
    }

    public static void i(Object obj) {
        d(obj);
    }

    public static void e(String tag, Object obj) {
        e(String.format("%-20s %s",
                (tag == null ? "null" : tag),
                (obj == null ? "null" : obj.toString())));
    }

    public static void e(Object obj) {
        e(obj == null ? "null" : obj.toString());
    }

    private static void e(String m) {
        if (toFile) {
            fWriter.println(m);
        } else {
            eStream.println(m);
        }
    }

    public static void printStackTrace(Exception e) {
        if (e == null) return;
        if (toFile) {
            e.printStackTrace(fWriter);
        } else {
            e.printStackTrace(eStream);
        }
    }
}
