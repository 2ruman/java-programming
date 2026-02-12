package truman.java.demo.tib_data_accum.core;

import truman.java.demo.tib_data_accum.util.AbstractHandlerThread;

public class DataFlusher<E> extends AbstractHandlerThread<E> {

    private final DataSink<E> dataSink;

    public DataFlusher(DataSink<E> dataSink) {
        this.dataSink = dataSink;
    }

    public void flush(E data) {
        add(data);
    }

    @Override
    protected void handle(E data) {
        dataSink.down(data);
    }

    @Override
    protected void onInterrupted(InterruptedException e) {
    }

    @Override
    protected void onTerminated() {
    }
}

