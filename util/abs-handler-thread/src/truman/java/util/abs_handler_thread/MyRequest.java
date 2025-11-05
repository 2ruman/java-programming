package truman.java.util.abs_handler_thread;

public class MyRequest {

    public static final int NONE = 0;
    public static final int PRINT_MESSAGE = 1;
    public static final int NPE_TEST = 2;

    private final int mRequest;
    private String mData;

    public MyRequest(int request) {
        mRequest = request;
    }

    public int getRequest() {
        return mRequest;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getData() {
        return mData;
    }
}
