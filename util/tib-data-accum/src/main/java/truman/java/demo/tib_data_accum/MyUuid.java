package truman.java.demo.tib_data_accum;

public class MyUuid {

    private final int uid;
    private final int addr;
    private final int port;

    public MyUuid(int uid, int addr, int port) {
        this.uid = uid;
        this.addr = addr;
        this.port = port;
    }

    @Override
    public int hashCode() {
        return uid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyUuid another) {
            return (another.addr == this.addr) && (another.port == this.port);
        }
        return false;
    }

    @Override
    public String toString() {
        return uid + "::" + addr + "::" + port;
    }
}
