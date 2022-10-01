package truman.java.example.shutdown_hook;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class WorkerThread extends Thread {

    private int loop = 10;

    private char[] password = "1234".toCharArray();

    private File tmpFile;

    private WrapUp shutdownHook = new WrapUp();

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        System.out.println("The worker thread will be terminated in 10 secs");
        System.out.println("Before that, press 'Ctrl + C' for the interrupt signal...");

        try {
            work();
            System.out.println("Worker thread : Everthing is done normally...");
            wrapUp();
            Runtime.getRuntime().removeShutdownHook(shutdownHook);
        } catch (InterruptedException e) {
            System.out.println("Worker thread : terminated abruptly!!!");
        }
    }

    private void work() throws InterruptedException {
        try {
            tmpFile = File.createTempFile("tmp", ".txt");
        } catch (IOException e) {
        }

        while (loop > 0) {
            System.out.println(loop-- + "...");
            sleep(1000);
        }
    }

    private void wrapUp() {
        System.out.println("Wrap-up : Erasing sensitive information from memory...");
        Arrays.fill(password, ' ');
        System.out.println("Wrap-up : Checking user password --> "+
                        (new String(password).trim().isEmpty() ? "Empty" : "Exposed"));

        if (tmpFile == null) {
            return;
        }

        System.out.println("Wrap-up : Removing temporary file --> " + tmpFile.getAbsolutePath());
        tmpFile.delete();
        System.out.println("Wrap-up : Checking temporary file --> " +
                        (tmpFile.exists() ? "Exist" : "Not found"));
        System.out.println("Wrap-up : Completed!");
    }

    private class WrapUp extends Thread {

        @Override
        public void run() {
            System.out.println("Wrap-up thread invoked!");
            WorkerThread.this.interrupt();
            try {
                WorkerThread.this.join();
            } catch (InterruptedException e) {
            }
            wrapUp();
        }
    }
}
