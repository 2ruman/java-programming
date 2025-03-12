package com.truman.util;

public class Log {

    public static void d(Object obj) {
        System.out.println(obj == null ? "null" : obj.toString());
    }

    public static void d(String tag, Object obj) {
        System.out.println((tag == null ? "null" : tag) + "\t" + (obj == null ? "null" : obj.toString()));
    }

    public static void e(Object obj) {
        System.err.println(obj == null ? "null" : obj.toString());
    }

    public static void e(String tag, Object obj) {
        System.err.println((tag == null ? "null" : tag) + "\t" + (obj == null ? "null" : obj.toString()));
    }

}
