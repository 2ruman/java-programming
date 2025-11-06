package truman.java.demo.tb_data_accum;

import truman.java.demo.tb_data_accum.core.Accumulable;
import truman.java.demo.tb_data_accum.core.AccumulableData;

public class MyData extends AccumulableData {

    private int rxBytes;
    private int txBytes;

    public MyData(int uuid, long timestamp) {
        this(uuid, timestamp, 0, 0);
    }

    public MyData(int uuid, long timestamp, int rxBytes, int txBytes) {
        super(uuid, timestamp);
        this.rxBytes = rxBytes;
        this.txBytes = txBytes;
    }

    public int getRxBytes() {
        return rxBytes;
    }

    public int getTxBytes() {
        return txBytes;
    }

    public MyData accumulate(MyData another) {
        this.rxBytes += another.getRxBytes();
        this.txBytes += another.getTxBytes();
        return this;
    }

    @Override
    public Accumulable accumulate(Accumulable another) {
        if (another instanceof MyData) {
            return accumulate((MyData) another);
        }
        return another;
    }

    @Override
    public String toString() {
        return String.format("uuid: %6d, t: %d, rx: %6d, tx: %6d",
                getUuid(), getTimestamp(), getRxBytes(), getTxBytes());
    }
}
