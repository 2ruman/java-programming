package truman.java.util.abs_handler_thread;

public class MyRequestHandler extends AbstractHandlerThread<MyRequest> {

    private final MyCallbacks mCallbacks;

    public MyRequestHandler(MyCallbacks callbacks) {
        super("MyRequestHandler");

        mCallbacks = callbacks;
    }

    @Override
    protected void handle(MyRequest e) {
        int request = e.getRequest();
        if (e.getRequest() == MyRequest.PRINT_MESSAGE) {
            mCallbacks.onReceived(e.getData());
        } else {
            System.out.println("Not supported request... " + request);
        }
    }

    @Override
    protected void onInterrupted(InterruptedException e) {
        mCallbacks.onError(e, getName());
    }

    @Override
    protected void onTerminated() {
        mCallbacks.onTerminated(getName());
    }
}
