package truman.java.util.abs_handler_thread;

public interface MyCallbacks {

    void onReceived(String message);
    void onError(Exception e, String who);
    void onTerminated(String who);
}
