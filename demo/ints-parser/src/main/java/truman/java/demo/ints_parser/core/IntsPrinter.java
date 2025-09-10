package truman.java.demo.ints_parser.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class IntsPrinter {

    private static final boolean UNION = true;
    private static final int DEFAULT_FIELD_LEN = 10;
    private static final String DEFAULT_GAP = " ";
    private static final String DEFAULT_DIV_SYM = "-";
    private static final String DEFAULT_NON_SYM = "-";
    private static final String ABSTRACT_LINE_FORMAT = "%s%s%s%s%s";
    private static final String DEFAULT_LINE_FORMAT;
    private static final String DEFAULT_DIVIDER;

    static {
        DEFAULT_LINE_FORMAT = initLineFormat();
        DEFAULT_DIVIDER = initDivider();
    }

    private static String initLineFormat() {
        return UNION ?
                String.format(ABSTRACT_LINE_FORMAT,
                        initFieldFormat(), DEFAULT_GAP, initFieldFormat(), DEFAULT_GAP, initFieldFormat()) :
                String.format(ABSTRACT_LINE_FORMAT,
                        "", "", initFieldFormat(), DEFAULT_GAP, initFieldFormat());
    }

    private static String initFieldFormat() {
        return "%" + DEFAULT_FIELD_LEN + "s";
    }

    private static String initDivider() {
        int count = UNION ?
                DEFAULT_FIELD_LEN * 3 + DEFAULT_GAP.length() * 2 :
                DEFAULT_FIELD_LEN * 2 + DEFAULT_GAP.length();
        return DEFAULT_DIV_SYM.repeat(count);
    }

    private static String title(String leaderName, String leftFieldName, String rightFieldName) {
        return line(leaderName, leftFieldName, rightFieldName, true);
    }

    private static String line(String leader, String left, String right, boolean divider) {
        return (UNION ? String.format(DEFAULT_LINE_FORMAT, leader, left, right) :
                String.format(DEFAULT_LINE_FORMAT, left, right)) +
                (divider ? System.lineSeparator() + DEFAULT_DIVIDER : "");
    }

    public static void printComp(Ints leftInts, Ints rightInts) {
        if (isInvalid(leftInts, rightInts)) {
            return;
        }
        comp(leftInts, rightInts).forEach(IntsPrinter::print);
    }

    public static void printAgg(Ints leftInts, Ints rightInts) {
        if (isInvalid(leftInts, rightInts)) {
            return;
        }
        Ints aggregated = Ints.aggregate(leftInts, rightInts);
        print(aggregated.toString());
    }

    public static List<String> comp(Ints leftInts, Ints rightInts) {
        List<String> result = new LinkedList<>();
        result.add(title("Union", "Left", "Right"));
        Set<Integer> left = leftInts.get();
        Set<Integer> right = rightInts.get();
        Set<Integer> aggregated = new TreeSet<>(leftInts.get());
        aggregated.addAll(rightInts.get());

        aggregated.forEach(i -> {
            String l = (left.contains(i) ? i.toString() : DEFAULT_NON_SYM);
            String r = (right.contains(i) ? i.toString() : DEFAULT_NON_SYM);
            result.add(line(String.valueOf(i), l, r, false));
        });
        return result;
    }

    private static boolean isInvalid(Ints leftInts, Ints rightInts) {
        if (leftInts == null || rightInts == null) {
            print("Invalid left or right Ints");
            return true;
        }
        return false;
    }

    private static void print(String line) {
        System.out.println(line);
    }
}