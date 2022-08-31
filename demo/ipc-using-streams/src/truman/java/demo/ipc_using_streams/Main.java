package truman.java.demo.ipc_using_streams;

import java.io.IOException;
import java.util.Scanner;

/**
 * This program is to demonstrate doing communication with the other process
 * using I/O streams in a "asynchronous" way. If you are an android developer,
 * you can try "adb shell" for fun.
 * 
 * [ ! ] If you can't see the prompting symbol, '$', after invoking a command,
 * just type the enter key to return back to the normal state.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter commands : ");
            String cmdStr = scanner.nextLine();
            String[] cmdArr = cmdStr.split(" ");

            Process proc = Runtime.getRuntime().exec(cmdArr);

            ProcReader oReader = new ProcReader(proc.getInputStream(), "Out");
            ProcReader eReader = new ProcReader(proc.getErrorStream(), "Err ");
            ProcWriter cWriter = new ProcWriter(proc, "$ ");

            oReader.start();
            eReader.start();
            cWriter.start();

            oReader.join();
            eReader.join();
//          cWriter.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
