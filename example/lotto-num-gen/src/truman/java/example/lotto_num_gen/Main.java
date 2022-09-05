package truman.java.example.lotto_num_gen;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void generateLottoNumbers(int method) {
        repeater( method == 1 ? () -> method_1()
                : method == 2 ? () -> method_2()
                : method == 3 ? () -> method_3()
                : null);
    }

    static int repeat = 10;
    public static void repeater(Runnable method) {
        if (method == null) {
            System.out.println("Invalid method...");
        } else {
            System.out.println("\nGood luck!\n");
            while (repeat-- > 0) {
                method.run();
            }
        }
    }

    /**
     * This method is using "java.util.stream.InsStream" which deals with
     * Integer values as a stream while supporting the same functionalities with
     * "java.util.stream.Stream". This way is good to use since
     * "java.security.SecureRandom" is a implementation of "java.util.Random"
     * class which basically supports ints() method that makes IntStream in
     * the given range.
     */
    public static void method_1() {
        IntStream lott = new SecureRandom().ints(1, 46).distinct().limit(6);
        lott.boxed().sorted().map(i -> "[" + i + "]").forEach(System.out::print);
        System.out.println();
    }

    /**
     * The same way with the method_1 except how to display the result.
     */
    public static void method_2() {
        System.out.println(
                new SecureRandom()
                .ints(1, 46)
                .distinct()
                .limit(6)
                .sorted()
                .mapToObj(i -> Integer.toString(i))
                .collect(Collectors.joining(", ", "[", "]")));
    }

    /**
     * This method is using a collection class, "java.util.Set". The Set doesn't
     * allow to have duplicated values inside, so we don't need to consider to
     * remove any duplicated number while putting a number into it.
     */
    public static void method_3() {
        Set<Integer> lottoNumSet = new TreeSet<>();
        SecureRandom sr = new SecureRandom();
        while (lottoNumSet.size() < 6) {
            lottoNumSet.add(sr.nextInt(46));
        }
        System.out.println(lottoNumSet);
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        int method;
        if (args.length > 0) {
            method = Integer.parseInt(args[0]);
        } else {
            System.out.print("Choose a method (1~3) : ");
            method = new Scanner(System.in).nextInt();
        }
        generateLottoNumbers(method);
    }
}
