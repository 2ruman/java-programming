package truman.java.example.keep_input_line;

import java.util.Scanner;

public class Main {

//    @SuppressWarnings("resource")
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String input = "";
//
//        while (true) {
//            // Display last input; It's empty at first
//            System.out.println("Last Input : " + input);
//
//            // Get user input
//            input = scanner.nextLine();
//
//            // Remove a single upper line using escape characters
//            System.out.print("\033[1A\033[2K");
//
//            // Remove a single upper line again; 2 lines removed at last
//            System.out.print("\033[1A\033[2K");
//        }
//    }

    /**
     * The main method below will work the same as above as long as be launched
     * on a terminal, but not on virtual console like the one in eclipse.
     */
    public static void main(String[] args) {
        String input = "";

        while (true) {
            // Display last input; It's empty at first
            System.console().printf("Last Input : %s\n", input);

            // Get user input
            input = System.console().readLine();

            // Remove a single upper line using escape characters
            System.console().printf("\033[1A\033[2K");

            // Remove a single upper line again; 2 lines removed at last
            System.console().printf("\033[1A\033[2K");
        }
    }

}

