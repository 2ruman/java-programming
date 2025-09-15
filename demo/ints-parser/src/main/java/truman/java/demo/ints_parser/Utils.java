package truman.java.demo.ints_parser;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int genRandomInt() {
        final int origin = 1_000_000;
        final int bound = 10_000_000;
        return genRandomInt(origin, bound);
    }

    public static int genRandomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static Set<Integer> genRandomInts(int size) {
        return genRandomInts(size, null);
    }

    public static Set<Integer> genRandomInts(int size, Collection<Integer> base) {
        Set<Integer> ints = new TreeSet<>();
        if (base != null) {
            ints.addAll(base);
        }
        while (ints.size() < size) {
            ints.add(genRandomInt());
        }
        return ints;
    }
}
