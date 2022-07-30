package truman.java.example.search_sys_property;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.getProperties().entrySet().forEach(System.out::println);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Keyword to search : ");
            String keyword = scanner.nextLine();

            System.out.println("Result :");
            System.getProperties()
                    .entrySet()
                    .stream()
                    .filter(p -> p.getKey().toString().contains(keyword))
                    .forEach(System.out::println);
        }
    }
}
