package truman.java.example.run_cmd_in_sub_proc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int PROC_TIMEOUT = 5;

    private void test() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter command : ");
            String cmd = scanner.nextLine();

            Process proc = Runtime.getRuntime().exec(cmd);
            System.out.println("Process Running...");

            boolean inTime = proc.waitFor(PROC_TIMEOUT, TimeUnit.SECONDS);
            if (inTime) {
                System.out.println("Process Finished! (" + proc.exitValue() + ")");
                System.out.println();

                readToEnd(proc.getInputStream(), "[ Result ]");
                readToEnd(proc.getErrorStream(), "[ Error ]");
            } else {
                System.out.println("Process Terminated by Timeout...");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readToEnd(InputStream inputStream, String title) {
        String line;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream))) {
            if (br.ready()) {
                if (title != null) {
                    System.out.println(title);
                }
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main().test();
    }
}
