package truman.java.example.prog_bar_in_console;

public class Main {

    private static final int TOTAL = 1000;
    private static final int WIDTH = 50;

    ProgressBar mProgressBar = new ProgressBar(WIDTH, TOTAL);

    int task() {
        return (int)(Math.random() * 50) + 50; // in the range of 50 ~ 10
    }

    public void test() {
        System.out.println();

        mProgressBar.init();

        int workload, progress = 0;
        while (progress < TOTAL) {
            workload = task();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}

            System.out.print("\033[2K\033[1F");
            System.out.println("Task finished! (" + workload + ")");
            System.out.println();
            mProgressBar.onProgress(progress+=workload);
        }

        System.out.println();
    }

    public static void main(String[] args) {
        new Main().test();
    }
}
