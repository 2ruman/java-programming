package truman.java.demo.tb_data_accum.core;

public interface DataSink<T> {
    void down(T data);
}
