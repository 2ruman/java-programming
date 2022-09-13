package truman.java.util.abs_handler_thread;

public class Test {

    private MyRequestHandler mRequestHandler;

    private final MyCallbacks mCallbacks = new MyCallbacks() {

        @Override
        public void onReceived(String message) {
            System.out.println("Handler : Received Message --> " + message);
            for (long i = 0 ; i < Integer.MAX_VALUE ; i++) {
                // Delay without sleep
            }
        }

        @Override
        public void onError(Exception e, String who) {
            System.out.println("Error: " + who);
            if (e != null) {
                System.out.println("Error Info: ");
                e.printStackTrace(System.out);
                if (e instanceof InterruptedException) {
                    System.out.println("(Not an error actually...)");
                }
            }
        }

        @Override
        public void onTerminated(String who) {
            System.out.println("Terminated: " + who);
        }
    };

    public Test test_1() {
        System.out.println("\nTest - #1: ");
        System.out.println("\tTerminating the handler in the middle of data processing\n");

        mRequestHandler = new MyRequestHandler(mCallbacks);
        mRequestHandler.start();

        MessageSender sender = new MessageSender();
        sender.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}

        System.out.println("Terminate!");
        mRequestHandler.terminate();
        try {
            mRequestHandler.join();
        } catch (InterruptedException e) {}

        return this;
    }

    public Test test_2() {
        System.out.println("\nTest - #2: ");
        System.out.println("\tTerminating the handler while waiting\n");

        mRequestHandler = new MyRequestHandler(mCallbacks);
        mRequestHandler.start();

        mRequestHandler.add(new MyRequest(MyRequest.NONE));
        mRequestHandler.add(new MyRequest(100));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}

        System.out.println("Terminate!");
        mRequestHandler.terminate();
        try {
            mRequestHandler.join();
        } catch (InterruptedException e) {}

        return this;
    }

    class MessageSender extends Thread {

        public void run() {
            System.out.println("MessageSender : Start to send messages!");
            final int totalCnt = 10;
            for (int i = 0 ; i < totalCnt ; i++) {
                MyRequest request =
                        new MyRequest(MyRequest.PRINT_MESSAGE);
                request.setData("Hello - " + i);
                mRequestHandler.add(request);
            }
            System.out.println("MessageSender : Finished! Total of the sent : " + totalCnt);
        }
    }
}
