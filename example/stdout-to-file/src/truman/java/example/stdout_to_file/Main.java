package truman.java.example.stdout_to_file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    static String getFileName() {
        String fileName = "";
        try {
            File tmp = File.createTempFile("tmp_", ".txt");
            fileName = tmp.getPath();
        } catch (IOException e) {}
        return fileName;
    }

    public static void main(String[] args) {
        /**
         *           Setup
         */
        PrintStream origOut = System.out;

        String fileName = getFileName();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            System.setOut(new PrintStream(fos));
        } catch (FileNotFoundException e) {}

        /**
         *           Test
         */
        origOut.println("[How-To]");
        origOut.println("1. Open a new terminal separately and type this command: tail -f " + fileName);
        origOut.println("2. Type 'q' or 'Q' to terminate this program");
        origOut.println("3. Type any word for test");
        try (Scanner scanner = new Scanner(System.in)) {
            String input = "";
            while (!input.equalsIgnoreCase("q")) {
                input = scanner.nextLine();
                System.out.println(input);
            }
        }

        /**
         *           Tear-down
         */
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {}
        }
        System.setOut(origOut);
        System.out.println("Bye bye!");
    }
}