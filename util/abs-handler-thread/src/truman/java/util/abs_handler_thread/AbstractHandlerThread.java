package truman.java.util.abs_handler_thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * This abstract class is to provide convenience when writing up a sort of
 * "Handler Queue" codes for handling a certain type of data.
 *
 * @version 0.1.1
 * @author Truman Kim (truman.t.kim@gmail.com)
 *
 */
public abstract class AbstractHandlerThread<E> extends Thread {

    private final BlockingQueue<E> mQ =
                    new LinkedBlockingQueue<>();

    public AbstractHandlerThread() {
        this(null);
    }

    public AbstractHandlerThread(String name) {
        if (name == null) {
            name ="HandlerThread";
        }
        setName(name);
        setDaemon(true);
    }

    public void add(E e) {
        if (e != null) {
            mQ.add(e);
        }
    }

    protected abstract void handle(E e);
    protected abstract void onInterrupted(InterruptedException e);
    protected abstract void onTerminated();
    protected void onError(Exception e) {
    }

    @Override
    public void run() {

        try {
            while (true) {
                handle(mQ.take());
                if (interrupted()) {
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException e) {
            onInterrupted(e);
        } catch (Exception e) {
            onError(e);
        }
        onTerminated();
    }

    public void terminate() {
        interrupt();
    }
}
