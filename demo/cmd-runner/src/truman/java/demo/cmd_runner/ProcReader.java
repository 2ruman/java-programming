package truman.java.demo.cmd_runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class is to read the output from the given process until there's nothing
 * to read through the respective input streams, and make up the result, putting
 * them in a ProcResult object.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public final class ProcReader {

    private final InputStream mOutStream;
    private final InputStream mErrStream;

    private ProcResult mProcResult;
    private ReaderThread mOutReader;
    private ReaderThread mErrReader;

    public ProcReader(Process process) {
        mOutStream = process.getInputStream();
        mErrStream = process.getErrorStream();
    }

    public synchronized ProcResult readToEnd() {
        mProcResult = new ProcResult();
        mOutReader = new ReaderThread(mOutStream, (l) -> mProcResult.addOut(l));
        mErrReader = new ReaderThread(mErrStream, (l) -> mProcResult.addErr(l));
        mOutReader.start();
        mErrReader.start();

        try {
            mOutReader.join();
            mErrReader.join();
        } catch (InterruptedException e) {
        }
        return mProcResult;
    }

    @FunctionalInterface
    private interface ReaderListener {
        void onRead(String line);
    }

    private class ReaderThread extends Thread {

        final InputStream inputstream;
        final ReaderListener listener;

        ReaderThread(InputStream inputstream, ReaderListener listener) {
            this.inputstream = inputstream;
            this.listener = listener;

            setDaemon(true);
        }

        @Override
        public void run() {
            String line;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputstream))) {
                while (!isInterrupted()) {
                    if ((line = br.readLine()) == null) {
                        interrupt();
                    } else {
                        listener.onRead(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
