package truman.java.util.loop_scanner;

public class Test {

    public void test() {

        LoopScanner scanner = new LoopScanner(
                (line) -> System.out.println("Echo: " + line));

        Thread escaper = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            System.out.println("Time to escape!");
            scanner.escape();
        });

        escaper.start();
        scanner.loop();

        System.out.println("Test finished!");
    }
}
