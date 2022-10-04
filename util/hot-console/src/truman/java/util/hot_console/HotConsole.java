package truman.java.util.hot_console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
*
* This class would be useful when you keep printing any result from your program
* while maintaining stand-alone input line, and this class provides convenience
* when you need to read an input immediately.
*
* @version 0.1.1
* @author Truman Kim (truman.t.kim@gmail.com)
*
*/

public class HotConsole extends PrintStream {

    private static final boolean DEBUG = false;
    private static final int BUFF_SIZ = 1024;
    private static final Object LOCK = new Object();

    private char buff[] = new char[BUFF_SIZ];
    private int pos;
    private volatile boolean isOn;
    private final Thread offHook = new Thread(() -> {
        off();
    });

    private HotConsole() {
        super(System.out, true);
    }

    public static final HotConsole getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final HotConsole INSTANCE = new HotConsole();
    }

    public void on(boolean autoOff) {
        synchronized (LOCK) {
            if (!isOn) {
                isOn = true;
                Stty.saveSettings();
                Stty.switchCanonicalMode(false);
                Stty.switchEchoMode(false);
                Stty.setMin(1);
                if (autoOff) {
                    Runtime.getRuntime().addShutdownHook(offHook);
                }
            }
        }
    }

    public void off() {
        synchronized (LOCK) {
            if (isOn) {
                isOn = false;
                Stty.restoreSettings();
                Stty.switchCanonicalMode(true);
                Stty.switchEchoMode(true);
            }
        }
    }

    public boolean isOn() {
        synchronized (LOCK) {
            return isOn;
        }
    }

    public static boolean isBackspace(char c) {
        return (c == 127);
    }

    public static boolean isEnter(char c) {
        return (c == 10);
    }

    public static boolean isPrintable(char c) {
        return (c >= ' ' && c <= '~');
    }

    private void onBackspace() {
        synchronized (LOCK) {
            Control.backspace();
            Control.eraseCursorToEnd();
            pop();
        }
    }

    private void onEnter() {
        synchronized (LOCK) {
            Control.carrigeReturn();
            Control.eraseTheLine();
        }
    }

    private boolean isFull() {
        return (pos >= BUFF_SIZ);
    }

    private void put(char c) {
        synchronized (LOCK) {
            buff[pos++] = c;
        }
    }

    private char pop() {
        synchronized (LOCK) {
            if (pos > 0) {
                return buff[--pos];
            } else {
                return '\0';
            }
        }
    }

    private String take() {
        synchronized (LOCK) {
            return new String(buff, 0, pos);
        }
    }

    private void reset() {
        synchronized (LOCK) {
            buff = new char[BUFF_SIZ];
            pos = 0;
        }
    }

    public char getCh() {
        try {
            return (char) System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
            return '\0';
        }
    }

    public String getStr() {
        String ret = "";
        try {
            char c;
            while (isOn()) {
                c = (char) System.in.read();
                synchronized (LOCK) {
                    if (isBackspace(c)) {
                        onBackspace();
                    } else if (isEnter(c)) {
                        onEnter();
                        break;
                    } else if (isPrintable(c)) {
                        put(c);
                        print(c);
                    }
                    if (isFull()) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ret = take();
            reset();
        }
        return ret;
    }

    @Override
    public void print(char c) {
        synchronized (LOCK) {
            super.print(c);
        }
    }

    @Override
    public void print(String s) {
        synchronized (LOCK) {
            super.print(s);
        }
    }

    @Override
    public void println(char c) {
        synchronized (LOCK) {
            super.println(c);
        }
    }

    @Override
    public void println(String s) {
        synchronized (LOCK) {
            super.println(s);
        }
    }

    public void insertln(String s) {
        synchronized (LOCK) {
            Control.carrigeReturn();
            Control.eraseTheLine();
            super.println(s);
            super.print(take());
        }
    }

    private static class Control {
        private static void backspace() {
            System.out.print("\b");
        }

        private static void carrigeReturn() {
            System.out.print("\r");
        }

        private static void eraseCursorToEnd() {
            System.out.print("\033[0K");
        }

        private static void eraseTheLine() {
            System.out.print("\033[2K");
        }
    }

    private static class Stty {

        private static String lastSettings;

        private static void switchEchoMode(boolean on) {
            if (on) stty("echo", null);
            else stty("-echo", null);
        }

        private static void setMin(int n) {
            stty("min " + n, null);
        }

        private static void switchCanonicalMode(boolean on) {
            if (on) stty("icanon", null);
            else stty("-icanon", null);
        }

        private static void saveSettings() {
            List<String> result = new LinkedList<>();
            int exitStatus = stty("--save", result);
            if (exitStatus == 0 && result.size() > 0) {
                lastSettings = result.get(0);
            }
            if (DEBUG) {
                System.out.println("saveSettings() -> " + lastSettings);
            }
        }

        private static void restoreSettings() {
            if (DEBUG) {
                System.out.println("restoreSettings() -> " + lastSettings);
            }
            if (lastSettings != null) {
                stty(lastSettings, null);
            }
        }

        private static int stty(String arg, Collection<String> result) {
            String cmd = String.format("stty %s < /dev/tty", arg);
            if (DEBUG) {
                System.out.println("stty() - Command : " + cmd);
            }
            return exec(new String[] {"sh", "-c", cmd}, result);
        }

        private static int exec(String[] cmdArray, Collection<String> result) {
            int exitStatus = -1;

            try {
                Process proc = Runtime.getRuntime().exec(cmdArray);
                exitStatus = proc.waitFor();
                if (DEBUG) {
                    System.out.println("exec() - Exit Status : " + exitStatus);
                }
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(exitStatus == 0 ?
                                proc.getInputStream() : proc.getErrorStream()))) {
                    if (result != null) {
                        result.add(br.readLine());
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return exitStatus;
        }
    }
}
