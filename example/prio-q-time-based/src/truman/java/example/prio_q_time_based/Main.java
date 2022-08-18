package truman.java.example.prio_q_time_based;

public class Main {

    public static void main(String[] args) {

        DataHandler handler = new DataHandler();
        DataMaker maker1 = new DataMaker("Data Maker - 1", handler);
        DataMaker maker2 = new DataMaker("Data Maker - 2", handler);
        DataMaker maker3 = new DataMaker("Data Maker - 3", handler);

        // Start data makers
        maker1.start();
        maker2.start();
        maker3.start();

        // Wait for data to be accumulated
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        // Lazy start
        handler.start();

        // Wait for display
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        // Finish the loop
        handler.interrupt();

        System.out.println("Main thread terminated...");
    }
}
