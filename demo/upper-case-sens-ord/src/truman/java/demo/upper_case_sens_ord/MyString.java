package truman.java.demo.upper_case_sens_ord;

import java.util.Comparator;

public final class MyString implements Comparable<MyString> {

    private final String mString;
    
    public MyString(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        mString = string;
    }

    public String get() {
        return mString;
    }

    public String toString() {
        return mString;
    }

    /**
     * Upper-case sensitive lexicographical order
     */
    public int compareTo(MyString that) {
        int ret = -1;

        String thisS = this.get();
        String thatS = that.get();

        ret = thisS.compareToIgnoreCase(thatS);
        if (ret == 0) {
            ret = thisS.compareTo(thatS);
        }
        return ret;
    }

    public static final Comparator<String> COMPARATOR = (thisS, thatS) -> {
        int ret = thisS.compareToIgnoreCase(thatS);
        return ret != 0 ? ret : thisS.compareTo(thatS);
    };

    /*
     * The comparator below is the version of non-lambda expression.
     */
//        public static fianl Comparator<String> COMPARATOR = new Comparator<String>() {
//        @Override
//        public int compare(String thisS, String thatS) {
//            int ret = thisS.compareToIgnoreCase(thatS);
//            if (ret == 0) {
//                ret = thisS.compareTo(thatS);
//            }
//            return ret;
//        }
//    };
}
