package truman.java.demo.tb_data_accum.core;

public abstract class AccumulableData implements Accumulable {
    private final int uuid;
    private final long timestamp;
    private volatile Object tag;

    protected AccumulableData(int uuid, long timestamp) {
        this.uuid = uuid;
        this.timestamp = timestamp;
    }

    public int getUuid() {
        return uuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
