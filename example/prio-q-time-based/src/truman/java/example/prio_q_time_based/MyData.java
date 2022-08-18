package truman.java.example.prio_q_time_based;

public class MyData implements Comparable<MyData> {

    private String data;
    private long time;
    private transient long vmnt;

    public MyData(String data, long time) {
        this.data = data;
        this.time = time;
        this.vmnt = System.nanoTime();
    }

    public String toString() {
        return ("(" + time + " / " + vmnt + " ) " + data);
    }

    @Override
    public int compareTo(MyData other) {
        // Long to Integer( 1, -1 or 0 )
        return (this.time > other.time)
                ? 1 : (this.time < other.time)
                ? -1 : thenCompareTo(other);
    }

    private int thenCompareTo(MyData other) {
        return (this.vmnt > other.vmnt)
                ? 1 : (this.vmnt < other.vmnt)
                ? -1 : 0;
        // If you make sure of non-duplication happening, just return 0.
        // return 0;
    }
}
