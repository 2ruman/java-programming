package truman.java.example.prio_q_time_based;

import java.util.HashSet;
import java.util.Set;

public class DataMaker extends Thread {

    private static final int CNT = 10;  // Repetition count

    String name;
    DataHandler handler;
    Set<MyData> dataSet;

    DataMaker(String name, DataHandler handler) {
        this.name = name;
        this.handler = handler;
    }

    void make() {
        dataSet = new HashSet<>();

        for (int i = 0 ; i < CNT ; i++) {
            String data = "I'm " + name + "! (Cnt:" + i + ")";
            long now = System.currentTimeMillis();
            // long now = System.nanoTime();

            dataSet.add(new MyData(data, now));
        }
        /* Expose back the line below to check all the jumbled data made up */
        // dataSet.forEach(System.out::println);
    }

    @Override
    public void run() {
        make();

        dataSet.forEach(myData -> handler.put(myData));

        System.out.println(name + " terminated...");
    }
}
