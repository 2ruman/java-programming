package truman.java.example.prio_q_time_based;

import java.util.concurrent.PriorityBlockingQueue;

public final class DataHandler extends Thread {

    private final PriorityBlockingQueue<MyData> mPrioQ = new PriorityBlockingQueue<>();

    public void put(MyData myData) {
        // Avoiding NPE!
        if (myData != null) {
            mPrioQ.add(myData);
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                process(mPrioQ.take()); // take() will never return null!
            } catch (InterruptedException e) {
                // e.printStackTrace();
                interrupt();
            }
        }
        System.out.println("Data handler thread terminated...");
    }

    private void process(MyData data) {
        System.out.println(data);
    }
}
