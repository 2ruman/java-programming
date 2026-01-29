package truman.java.demo.tb_data_accum.core;

public interface DataSnapshotConsumer<U, T extends AccumulableData<U>> {
    void consume(int index, DataSnapshot<U, T> data);
}
