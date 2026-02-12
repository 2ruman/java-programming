package truman.java.demo.tb_data_accum.core;

public interface DataConsumer<T extends AccumulableData> {
    void consume(int index, T data);
}
