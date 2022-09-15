package truman.java.util.android_log;

public class Test {

    private static final String TAG = "MyService";

    private void routine() {
        final int val = 10;

        Log.i(TAG, "routine() - Inside!");
        Log.i(null, null);

        Log.d(TAG, null);
        Log.d("ABCDEFGHIJKLMNOPQRSTU", "With long tag...");
        Log.d("CheesePizzaManagerService", "With long tag...");
        Log.d("CheesePizzaToastManagerService", "With long tag...");
        Log.d(TAG, "How much do you have?");
        Log.d(TAG, String.format("%d cents.", val));

        try {
            throw new Exception("Catch me!");
        } catch (Exception e) {
            Log.e(TAG, "Print an exception!");
            Log.printStackTrace(e);

            Log.e(TAG, "Again?");
            Log.e(e);

            Log.e(TAG, "One last time!");
            Log.e(TAG, e);
        }

        Log.i("This is for normal java code.");
        Log.d("This is for normal java code.");
        Log.e("This is for normal java code.");
        Log.d("Log file name : " + Log.getOutFileName());
    }

    /**
     * Comprehensive Testing
     */
    public Test test_1() {
        // Normal
        routine();

        // To the temporary file
        Log.setOutToFile();
        routine();

        // To the specified file with time stamp
        Log.setTimeStamp(true);
        Log.setOutToFile("my.log");
        routine();

        // Turn back to the normal
        Log.setTimeStamp(false);
        Log.setOutToFile(null);
        routine();

        return this;
    }

    /**
     *  Tag-removal Testing
     */
    public Test test_2() {
        Log.setTimeStamp(false);
        Log.setNoTag(false);

        Exception e = new Exception("Tag-removal testing");

        Log.d(TAG, "I have a tag :-p");
        Log.d(TAG, "I have a tag :-p");
        Log.d(TAG, "I have a tag :-p");
        Log.d(TAG, "I have a tag :-p");
        Log.d(TAG, "I have a tag :-p");
        Log.e(TAG, e);
        Log.setNoTag(true);
        Log.d(TAG, "Where is the tag?");
        Log.d(TAG, "Where is the tag?");
        Log.d(TAG, "Where is the tag?");
        Log.d(TAG, "Where is the tag?");
        Log.e(TAG, e);
        Log.setTimeStamp(true);
        Log.d(TAG, "Tell me where the tag is!");
        Log.d(TAG, "Tell me where the tag is!");
        Log.d(TAG, "Tell me where the tag is!");
        Log.d(TAG, "Tell me where the tag is!");
        Log.d(TAG, "Tell me where the tag is!");
        Log.e(TAG, e);

        return this;
    }

    /**
     * Multi-threading Testing
     */
    public Test test_3() {
        Log.setTimeStamp(false);
        Log.setNoTag(false);

        Thread t1 = new LoggingThread(1);
        Thread t2 = new LoggingThread(2);
        Thread t3 = new LoggingThread(3);
        Thread t4 = new LoggingThread(4);

        try {
            Log.d("==================== Ordered output ====================");
            t1.start(); t2.start();
            t1.join(); t2.join();
            Log.d("==================== Jumbled output ====================");
            t3.start(); t4.start();
            t1.join(); t2.join();
        } catch (InterruptedException e) {}

        return this;
    }

    private class LoggingThread extends Thread {
        int id;
        int repeat = 10;
        Object lock;

        LoggingThread(int id) {
            this.id = id;
            lock = (id < 3) ? Log.getLock() : new Object();
        }

        public void run() {
            synchronized (lock) {
                while (repeat-- > 0) {
                    Log.d("LoggingThread - " + id, "Hello!!!");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {}
                }
            }
        }
    }
}
