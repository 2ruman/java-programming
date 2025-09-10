package truman.java.demo.adbconsole.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdbShell {

    private static final boolean ATTACH_EXIT_CODE = true;

    private static String formatExitCode(int exitCode) {
        return "[ Exit Code: " + exitCode + " ]" + System.lineSeparator();
    }

    public static void execute(String command, AdbShellCallback callback) {
        executeInternal(command, callback, false);
    }

    public static void executeLocal(String command, AdbShellCallback callback) {
        executeInternal(command, callback, true);
    }

    private static void executeInternal(String command, AdbShellCallback callback, boolean isLocal) {
        if (command == null || command.isEmpty() || callback == null) {
            return;
        }

        try {
            ProcessBuilder builder = isLocal ?
                    new ProcessBuilder(AdbEnvironment.EXE, command) :
                    new ProcessBuilder(AdbEnvironment.EXE, AdbEnvironment.SHELL, command);

            Process process = builder.redirectErrorStream(true).start();

            String line;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    callback.onReadLine(line);
                }
            }

            if (ATTACH_EXIT_CODE) {
                int exitCode = process.waitFor();
                callback.onReadLine(formatExitCode(exitCode));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}