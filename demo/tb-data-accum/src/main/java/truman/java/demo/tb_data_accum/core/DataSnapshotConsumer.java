package truman.java.demo.tb_data_accum.core;

public interface DataSnapshotConsumer<T extends AccumulableData> {
    void consume(int index, DataSnapshot<T> data);
}
