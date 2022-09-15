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

    public void test() {
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
    }
}
