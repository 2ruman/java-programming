package truman.java.demo.tib_data_accum.core;

public interface DataSink<T> {
    void down(T data);
}
