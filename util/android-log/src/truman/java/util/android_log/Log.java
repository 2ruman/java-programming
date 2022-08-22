package truman.java.util.android_log;

/**
 * 
 * This class is for increasing an inter-compatability between normal java codes
 * and android java codes.
 * 
 * @version 0.1.1
 * @author Truman Kim (truman.t.kim@gmail.com)
 * 
 */
public final class Log {

    public static void d(Object obj) {
        System.out.println(obj == null ? "null" : obj.toString());
    }

    public static void d(String tag, Object obj) {
        System.out.println(String.format("%-20s %s",
                (tag == null ? "null" : tag),
                (obj == null ? "null" : obj.toString())));
    }

    public static void e(Object obj) {
        System.err.println(obj == null ? "null" : obj.toString());
    }

    public static void e(String tag, Object obj) {
        System.err.println(String.format("%-20s %s",
                (tag == null ? "null" : tag),
                (obj == null ? "null" : obj.toString())));
    }

    public static void i(Object obj) {
        d(obj);
    }

    public static void i(String tag, Object obj) {
        d(tag, obj);
    }

}
