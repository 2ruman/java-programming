package truman.java.demo.tb_data_accum.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
