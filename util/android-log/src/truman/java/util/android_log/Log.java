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
 * @version 0.6.3
 * @author Truman Kim (truman.t.kim@gmail.com)
 * 
 */
public final class Log {

    private static PrintStream dStream;
    private static PrintStream eStream;
    private static PrintWriter fWriter;

    private static final Object LOG_LOCK = new Object();

    private static volatile boolean timeStamp;
    private static volatile boolean toFile;
    private static volatile boolean noTag;
    private static volatile boolean threadName;
    private static volatile String lastFileName;

    static {
        dStream = System.out;
        eStream = System.err;
    }

    private static final String TIME_LOG_FORMAT = "%-30s%s";

    private static final String TAG_LOG_FORMAT = "%-20s%s";

    private static final SimpleDateFormat LOG_TIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);

    private static final SimpleDateFormat FILE_TIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);

    private static String getLogTime() {
        return LOG_TIME_FORMATTER.format(new Date(System.currentTimeMillis()));
    }

    private static String getFileTime() {
        return FILE_TIME_FORMATTER.format(new Date(System.currentTimeMillis()));
    }

    public static Object getLock() {
        return LOG_LOCK;
    }

    /**
     * Determine whether to print time stamp or not.
     * Default, false.
     */
    public static void setTimeStamp(boolean yes) {
        timeStamp = yes;
    }

    /**
     * Determine whether to abandon the given tag forcibly.
     * Default, false.
     */
    public static void setNoTag(boolean forced) {
        noTag = forced;
    }

    /**
     * Determine whether to attach the calling thread's name to the tag field
     * Default, false.
     */
    public static void setThreadName(boolean yes) {
        threadName = yes;
    }

    /**
     * Changing the output destination is not multi-thread safe. So you'd better
     * determine where to print logs at the very beginning of your program.
     * 
     * This method will create new file named based on current time, and
     * coming log after this will be printed into that file.
     */
    public static void setOutToFile() {
        String fileName = "log_" + getFileTime() + ".txt";
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
                lastFileName = fileName;
                toFile = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getOutFileName() {
        return toFile ? lastFileName : "";
    }

    public static void d(String tag, Object obj) {
        if (noTag) d(obj);
        else d(String.format(TAG_LOG_FORMAT,
                (tag == null ? "null" : tag),
                (obj == null ? "null" : obj.toString())));
    }

    public static void d(Object obj) {
        String m = obj == null ? "null" : obj.toString();
        if (threadName) {
            d(Thread.currentThread().getName(), m);
        } else {
            d(m);
        }
    }

    private static void d(String m) {
        print(m, true);
    }

    public static void i(String tag, Object obj) {
        d(tag, obj);
    }

    public static void i(Object obj) {
        d(obj);
    }

    public static void e(String tag, Object obj) {
        if (noTag) e(obj);
        else e(String.format(TAG_LOG_FORMAT,
                (tag == null ? "null" : tag),
                (obj == null ? "null" : obj.toString())));
    }

    public static void e(Object obj) {
        String m = obj == null ? "null" : obj.toString();
        if (threadName) {
            e(Thread.currentThread().getName(), m);
        } else {
            e(m);
        }
    }

    private static void e(String m) {
        print(m, false);
    }

    public static void e(Exception e) {
        if (e == null) {
            return;
        }
        if (threadName) {
            e(Thread.currentThread().getName(), e);
            return;
        }
        synchronized (LOG_LOCK) {
            e(e.toString());
            for (StackTraceElement stack : e.getStackTrace()) {
                e("\tat " + stack);
            }
        }
    }

    public static void e(String tag, Exception e) {
        if (e == null) {
            return;
        }
        synchronized (LOG_LOCK) {
            e(tag, e.toString());
            for (StackTraceElement stack : e.getStackTrace()) {
                e(tag, "\tat " + stack);
            }
        }
    }

    private static void print(String m, boolean debugOrError) {
        synchronized (LOG_LOCK) {
            if (timeStamp) {
                m = String.format(TIME_LOG_FORMAT, getLogTime(), m);
            }
            if (toFile) {
                fWriter.println(m);
                fWriter.flush();
            } else if (debugOrError) {
                dStream.println(m);
            } else {
                eStream.println(m);
            }
        }
    }

    public static void printStackTrace(Exception e) {
        if (e == null) {
            return;
        }
        if (toFile) {
            e.printStackTrace(fWriter);
        } else {
            e.printStackTrace(eStream);
        }
    }
}
