package truman.java.demo.upper_case_sens_ord;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Test {

    String[] origStrings = new String[] {
            "a",
            "B",
            "A",
            "AB",
            "aB",
            "bbb",
            "abC",
            "Abc",
            "abc",
            "ABc",
            "bBC",
    };

    public void test() {

        // For example, making use of MyString, you can create an object that
        // contains file information so that apply it to file browser.
        MyString[] myStrings = new MyString[origStrings.length];
        for (int i = 0 ; i < origStrings.length ; i++) {
            myStrings[i] = new MyString(origStrings[i]);
        }

        Set<MyString> set = new TreeSet<>();
        for (MyString ms : myStrings) {
            set.add(ms);
        }

        List<String> listForDefault = new LinkedList<>();
        Collections.addAll(listForDefault, origStrings);
        Collections.sort(listForDefault);

        List<String> listForMyString= new LinkedList<>();
        Collections.addAll(listForMyString, origStrings);
        listForMyString.sort(MyString.COMPARATOR);

        List<String> listForCaseInsens = new LinkedList<>();
        Collections.addAll(listForCaseInsens, origStrings);
        listForCaseInsens.sort(String.CASE_INSENSITIVE_ORDER);

        System.out.println("Original Strings           : " + Arrays.toString(origStrings));
        System.out.println("Default Compare(Lexicog.)  : " + listForDefault);
        System.out.println("MyString's Compare         : " + set);
        System.out.println("MyString.comprator         : " + listForMyString);
        System.out.println("(cf.) Case-Insensitive     : " + listForCaseInsens);
        listForCaseInsens.sort(String.CASE_INSENSITIVE_ORDER
                .thenComparing((thisS, thatS) -> thisS.compareTo(thatS)));
        System.out.println("(cf.) Upper-case Sensitive : " + listForCaseInsens);
    }
}
