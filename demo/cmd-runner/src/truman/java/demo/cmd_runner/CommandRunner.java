package truman.java.demo.cmd_runner;

import java.io.IOException;

/**
 * This class is to run the given command and return the result.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public final class CommandRunner {

    public static ProcResult run(String command) {
        ProcResult procResult = ProcResult.EMPTY;
        if (command == null || command.isEmpty()) {
            return procResult;
        }

        try {
            Process proc = Runtime.getRuntime().exec(command);
            ProcReader procReader = new ProcReader(proc);

            procResult = procReader.readToEnd();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return procResult;
    }
}
