package truman.java.util.loop_scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * In contrast to "java.util.Scanner", the class is implemented on a mechanism
 * that makes it escapable from blocking input. The mechanism would be
 * applicable to other types of streams.
 * 
 * We need only two threads for use. The one is for going into the loop and the
 * other is for escaping from the loop. As the fact that the implementation is
 * not much multi-thread safe, make sure that only two threads access
 * the initiated object. Plus,the object is for one-time use only, so do not
 * open the loop over and over again. It will not work then.
 * 
 * The back-off value determines how long the looper thread has a sleep after
 * checking the stream buffer. It's better not to set the value high over
 * a second because the given callback, "java.util.function.Consumer.accept()"
 * is supposed to be run in the looper thread, thus it can be affected.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 *
 */
public class LoopScanner {

    private static final int DEFAULT_BACKOFF_MS = 200;

    private final InputStreamReader mIr = new InputStreamReader(System.in);
    private final BufferedReader mBr = new BufferedReader(mIr);
    private final int mBackoff;

    private Looper mLooper;
    private Boolean mLoopable;
    private Consumer<String> mListener;

    public LoopScanner(Consumer<String> listener) {
        this(0, listener);
    }

    public LoopScanner(int backoff, Consumer<String> listener) {
        if (backoff <= 0) {
            backoff = DEFAULT_BACKOFF_MS;
        }
        mBackoff = backoff;
        mListener = listener;
        mLoopable = true;
    }

    public void loop() {
        synchronized (mLoopable) {
            if (!mLoopable) {
                return;
            }
            mLoopable = false;
        }
        mLooper = new Looper();
        mLooper.start();
        try {
            mLooper.join();
        } catch (InterruptedException e) {
        }
    }

    public void escape() {
        synchronized (mLoopable) {
            if (mLoopable) {
                return;
            }
        }
        if (mLooper != null) {
            mLooper.interrupt();
            /*
             * We can escape the loop with IOException by closing the stream.
             * However it seems we don't need to do like that, and seems enough
             * to interrupt the looper thread. Above all, closing the stream
             * would make standard input do not work.
            try {
                mBr.close();
            } catch (IOException e) {}
             */
            mLooper = null;
        }
    }

    private class Looper extends Thread {

        @Override
        public void run() {
            try {
                while (!interrupted()) {
                    if (mBr.ready()) {
                        mListener.accept(
                                mBr.readLine());
                    } else {
                        try {
                            sleep(mBackoff);
                        } catch (InterruptedException e) {
                            interrupt();
                        }
                    }
                }
            } catch (IOException e) {
                /*
                 * Maybe rarely happens.
                e.printStackTrace();
                 */
            }
            System.out.println("Escaped the loop!");
        }
    }
}
