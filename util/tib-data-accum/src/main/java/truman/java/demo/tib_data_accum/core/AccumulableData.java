package truman.java.demo.tib_data_accum.core;

public abstract class AccumulableData<U> implements Accumulable {

    private final int type;
    private final U uuid;
    private final long timestamp;
    private volatile Object tag;

    protected AccumulableData(U uuid, long timestamp) {
        this(0, uuid, timestamp);
    }

    protected AccumulableData(int type, U uuid, long timestamp) {
        this.type = type;
        this.uuid = uuid;
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public U getUuid() {
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
