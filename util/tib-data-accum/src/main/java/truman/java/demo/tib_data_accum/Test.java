package truman.java.demo.tib_data_accum;

import truman.java.demo.tib_data_accum.core.DataSnapshot;

import static truman.java.demo.tib_data_accum.Test.Util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    private ExecutorService mExecutorService;

    private MyDataAccumulator mMyDataAccumulator;

    void dumpData(int idx, MyData data) {
        System.out.printf("[%d] %s%n", idx, data.toString());
    }

    void dumpDataSnapshot(int idx, DataSnapshot<MyUuid, MyData> dataSnapshot) {
        dataSnapshot.take().forEach(data -> {
            dumpData(idx, data);
        });
    }

    void setup() {
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    Test tearDown() {
        mExecutorService.shutdown();
        mExecutorService = null;
        return this;
    }

    public Test test_1() {
        System.out.println("\nTest - #1: ");
        System.out.println("\tNormal scenario\n");

        setup();

        mMyDataAccumulator = new MyDataAccumulator();

        mMyDataAccumulator.registerDataConsumer(this::dumpData);

        mMyDataAccumulator.start();

        mExecutorService.execute(() -> {
            long currentTime = currentTime();
            debug("Current time : " + currentTime);
            debug("base time    : " + mMyDataAccumulator.getBaseTime());
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-1
            debug("base time    : " + mMyDataAccumulator.getBaseTime());

            // Index 0 : 0 ~ 1 sec
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-2
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-3
            sleep(500);
            mMyDataAccumulator.onData(new MyData(1, 20000, 55555, currentTime(), 100, 100)); // 2-1
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-4
            mMyDataAccumulator.onData(new MyData(1, 20000, 55555, currentTime(), 100, 100)); // 2-2
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-5
            mMyDataAccumulator.onData(new MyData(1, 20000, 55555, currentTime(), 100, 100)); // 2-3
            mMyDataAccumulator.onData(new MyData(1, 30000, 55555, currentTime(), 100, 100)); // 3-1
            mMyDataAccumulator.onData(new MyData(1, 40000, 55555, currentTime(), 100, 100)); // 4-1

            // Index 1 : 1 ~ 2 sec
            sleep(1000);
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-1
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-2
            mMyDataAccumulator.onData(new MyData(1, 50000, 55555, currentTime(), 100, 100)); // 5-1

            // Index 2 : 2 ~ 3 sec
            sleep(1000);
            mMyDataAccumulator.onData(new MyData(1, 60000, 55555, currentTime(), 100, 100)); // 6-1

            // Index 4 : 4 ~ 5 sec
            sleep(2000);
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-1
            mMyDataAccumulator.onData(new MyData(1, 50000, 55555, currentTime(), 100, 100)); // 5-1

            // Index 5 : 5 ~ 6 sec
            sleep(500);
            mMyDataAccumulator.onData(new MyData(1, 50000, 55555, currentTime(), 100, 100)); // 5-1
        });

        sleep(7000);
        mMyDataAccumulator.stop();

        return tearDown();
    }

    public Test test_2() {
        System.out.println("\nTest - #2: ");
        System.out.println("\tNormal scenario with data snapshot consumer\n");

        setup();

        mMyDataAccumulator = new MyDataAccumulator(3000L);

        mMyDataAccumulator.registerDataSnapshotConsumer(this::dumpDataSnapshot);

        mMyDataAccumulator.start();

        mExecutorService.execute(() -> {
            long currentTime = currentTime();
            debug("Current time : " + currentTime);
            debug("base time    : " + mMyDataAccumulator.getBaseTime());
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-1
            debug("base time    : " + mMyDataAccumulator.getBaseTime());

            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-2
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-3

            sleep(1000);
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-1
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-4
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-2
            mMyDataAccumulator.onData(new MyData(1, 10000, 55555, currentTime(), 100, 100)); // 1-5
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-1

            sleep(1000);
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-2
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-3
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-3

            sleep(500);
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-4
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-5
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-4
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-5

            // ---------- 0.0s ~ 2.5s --> [0]

            sleep(1000);
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-1
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-2
            mMyDataAccumulator.onData(new MyData(2, 10000, 55555, currentTime(), 100, 100)); // 2-3
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-1

            sleep(1000);
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-2
            mMyDataAccumulator.onData(new MyData(2, 10000, 66666, currentTime(), 100, 100)); // 3-3
            mMyDataAccumulator.onData(new MyData(2, 20000, 66666, currentTime(), 100, 100)); // 4-1

            sleep(1000);
            mMyDataAccumulator.onData(new MyData(2, 20000, 66666, currentTime(), 100, 100)); // 4-2
            mMyDataAccumulator.onData(new MyData(2, 20000, 66666, currentTime(), 100, 100)); // 4-3
            // ---------- 3.5s ~ 5.5s --> [1]

/* ---------------------------- { Expected Result } ------------------------------

             There are 4 data types based on their UUID. (1) ~ (4).
             In each time index, accumulation will occur as below:

             [0] : (1) x 5, (2) x 5, (3) x 5
             [1] : (2) x 3, (4) x 3

   ------------------------------------------------------------------------------- */
        });

        sleep(10000);
        mMyDataAccumulator.stop();

        return tearDown();
    }

    static class Util {
        static void debug(String msg) {
            System.out.println(msg);
        }

        static long currentTime() {
            return System.currentTimeMillis();
        }

        static void sleep(long ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
