package truman.java.util.android_log;

public class Test {

    private static final String TAG = "MyService";

    private void routine() {
        final int val = 10;

        Log.i(TAG, "routine() - Inside!");
        Log.i(null, null);

        Log.d(TAG, null);
        Log.d(TAG, "How much do you have?");
        Log.d(TAG, String.format("%d cents.", val));

        try {
            throw new Exception("Catch me!");
        } catch (Exception e) {
            Log.e(TAG, "What did you say? : " + e);
            Log.printStackTrace(e);
        }

       Log.i("This is for normal java code.");
       Log.d("This is for normal java code.");
       Log.e("This is for normal java code.");
    }

    public void test() {
        routine();
        Log.setOutToFile();
        routine();
        Log.setOutToFile("my.log");
        routine();
        Log.setOutToFile(null);
        routine();
    }
}
