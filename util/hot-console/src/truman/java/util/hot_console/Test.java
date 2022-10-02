package truman.java.util.hot_console;

public class Test {

    private static final HotConsole Out = HotConsole.getInstance();

    static {
        Out.on(true);
    }

    Thread disturber = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            Out.insertln("Hi, there!");
            Out.insertln("How do I look today?");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    });

    public Test test_1() {
        Out.println();
        Out.println("[ How-to ]");
        Out.println(" - Type anything for 5 times!");
        Out.println();
        disturber.setDaemon(true);
        disturber.start();
        int i = 0;
        while (i++ < 5) {
            String s = Out.getStr();
            Out.println("#" + i + " : " + s);
        }
        disturber.interrupt();
        return this;
    }

    public Test test_2() {
        Out.println();
        Out.println("[ How-to ]");
        Out.println(" - [K] for Up, [J] for Down, [H] for Left, [L] for Right");
        Out.println(" - [ESC] for termination...");
        Out.println();
        char c;
        while (true) {
            c = Out.getCh();
            switch (c) {
                case 'k': Out.println("Up"); break;
                case 'j': Out.println("Down"); break;
                case 'h': Out.println("Left"); break;
                case 'l': Out.println("Right"); break;
                default:
                    if (!HotConsole.isPrintable(c)) {
                        Out.println("Bye bye!");
                        return this;
                    }
            }
        }
    }
}
