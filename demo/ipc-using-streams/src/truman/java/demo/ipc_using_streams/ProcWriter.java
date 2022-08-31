package truman.java.demo.ipc_using_streams;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public final class ProcWriter extends Thread {

    private final Scanner mScanner;
    private final Process mProcess;
    private final OutputStream mOutputStream;
    private final String mPrompt;

    public ProcWriter(Process process, String prompt) {
        mScanner = new Scanner(System.in);
        mProcess = process;
        mOutputStream = process.getOutputStream();
        mPrompt = (prompt == null) ? "" : prompt;
        setDaemon(true);
    }

    private String read() {
        if (!mPrompt.isEmpty()) {
            System.out.print(mPrompt);
        }
        return mScanner.nextLine();
    }

    @Override
    public void run() {
        String input;

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(mOutputStream))) {
            while (mProcess.isAlive()) {
                input = read();
                if (!input.isEmpty()) {
                    bw.write(input);
                    bw.newLine();
                    bw.flush();
                }
            }
            System.out.println("Dead! (rc: " + mProcess.exitValue() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
