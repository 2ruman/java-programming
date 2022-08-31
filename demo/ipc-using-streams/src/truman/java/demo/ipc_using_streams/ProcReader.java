package truman.java.demo.ipc_using_streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class ProcReader extends Thread {

    private static final String TAG_SEPARATOR = " : ";
    private final String mTag;
    private final InputStream mInputStream;

    public ProcReader(InputStream inputStream, String tag) {
        this.mInputStream = inputStream;
        this.mTag = (tag == null) ? "" : tag + TAG_SEPARATOR;
        setDaemon(true);
    }

    private void print(String line) {
        if (!mTag.isEmpty()) {
            System.out.print(mTag);
        }
        System.out.println(line);
    }

    @Override
    public void run() {
        String line;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(mInputStream))) {
            while (!isInterrupted()) {
                if ((line = br.readLine()) == null) {
                    interrupt();
                } else {
                    print(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
