package truman.java.demo.cmd_runner;

import java.util.Scanner;

/**
 * This program is to demonstrate how to get the result from the invoked
 * sub-process which is to run the given command.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public class Main {

    @SuppressWarnings("resource")
    public static void main(String[] args) {

        String command;
        if (args.length > 0) {
            command = String.join(" ", args);
        } else {
            System.out.print("Enter command : ");
            command = new Scanner(System.in).nextLine();
        }
        System.out.println();

        ProcResult procResult = CommandRunner.run(command);

        if (procResult.getOutCnt() > 0) {
            System.out.println("[ Result ] (" + procResult.getOutCnt() + ")");
            procResult.outToList().forEach(System.out::println);
        }

        if (procResult.getErrCnt() > 0) {
            System.out.println("[ Error ] (" + procResult.getErrCnt() + ")");
            procResult.errToList().forEach(System.out::println);
        }
    }
}
