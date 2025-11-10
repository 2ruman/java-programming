package truman.java.demo.tb_data_accum.core;

public abstract class AccumulableData implements Accumulable {

    private final int type;
    private final int uuid;
    private final long timestamp;
    private volatile Object tag;

    protected AccumulableData(int uuid, long timestamp) {
        this(0, uuid, timestamp);
    }

    protected AccumulableData(int type, int uuid, long timestamp) {
        this.type = type;
        this.uuid = uuid;
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
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
