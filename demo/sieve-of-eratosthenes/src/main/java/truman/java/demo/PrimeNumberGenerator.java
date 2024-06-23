package truman.java.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class PrimeNumberGenerator {

    public static int[] generate(int to) {
        return generateInStream(to).toArray();
    }

    public static List<Integer> generateInList(int to) {
        return generateInStream(to).boxed().toList();
    }

    public static IntStream generateInStream(int to) {
        ensureNumberRange(to);

        int[] sieve = IntStream.rangeClosed(0, to).toArray();
        for (int i = 2; i <= to; i++) {
            if (sieve[i] == 0) {
                continue;
            }
            for (int j = (i << 1); j <= to; j += i) {
                sieve[j] = 0;
            }
        }
        return Arrays.stream(sieve).filter(value -> value > 1);
    }

    public static boolean isPrimeNumber(int number) {
        ensureNumberRange(number);

        for (int i = 2 ; i <= (int) Math.sqrt(number); i++) {
            if ((number % i) == 0) {
                return false;
            }
        }
        return true;
    }

    private static void ensureNumberRange(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Only natural numbers supported");
        }
        if (number <= 1) {
            throw new IllegalArgumentException("Natural number must be greater than 1");
        }
    }
}
