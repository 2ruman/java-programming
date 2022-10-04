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
        Out.println(" - Type any sentence for 5 times!");
        Out.println();
        disturber.setDaemon(true);
        disturber.start();
        int i = 0;
        while (i++ < 5) {
            String s = Out.getStr();
            Out.println("#" + i + " : " + s);
        }
        disturber.interrupt();
        Out.println();
        Out.println("Bye bye!");
        return this;
    }

    public Test test_2() {
        Out.println();
        Out.println("[ How-to ]");
        Out.println(" - Press [K] for Up, [J] for Down, [H] for Left, [L] for Right");
        Out.println(" - Press [ESC] for termination...");
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

    public Test test_3() {
        Out.println();
        Out.println("[ How-to ]");
        Out.println(" - Press any of [Home], [End], [Insert], [Del], "
                + "[PageUp], [PageDown] and the arrow keys");
        Out.println(" - Press [Enter] for termination");
        Out.println();

        char c;
        SpecialKeySequence sks = null;
        while (true) {
            c = Out.getCh();
            if (sks != null) {
                sks.setCh(c);
                if (SpecialKeySequence.isKnown(sks)) {
                    SpecialKeySequence known = SpecialKeySequence.which(sks, null);
                    Out.println("[" + known.getAlias() + "]");
                    sks = null;
                } else if (sks.isFull()) {
                    sks = null;
                }
            } else if (c == 27) {
                sks = new SpecialKeySequence();
            } else if (HotConsole.isPrintable(c)) {
                Out.println("[" + c + "]");
            } else if (HotConsole.isEnter(c)) {
                Out.println("Bye bye!");
                break;
            }
        }
        return this;
    }
}
