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
        if (request == MyRequest.PRINT_MESSAGE) {
            mCallbacks.onReceived(e.getData());
        } else if (request == MyRequest.NPE_TEST) {
            throw new NullPointerException("Intended");
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

    @Override
    protected void onError(Exception e) {
        if (e instanceof NullPointerException
                && "Intended".equals(e.getMessage())) {
            System.out.println("Debug: It must be intended situation");
        }
        mCallbacks.onError(e, getName());
    }
}
