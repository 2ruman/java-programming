package truman.java.example.shutdown_hook;

public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Hook #1 : Shutdown!");
        }));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Hook #2 : The hooks do not guarantee the order!");
        }));
        WorkerThread thread = new WorkerThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        System.out.println("Main thread : terminated normally...");
    }

}
