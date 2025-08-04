package truman.java.demo.adbconsole.test;

import truman.java.demo.adbconsole.Application;
import truman.java.demo.adbconsole.common.TestableUi;
import truman.java.demo.adbconsole.common.Tester;
import truman.java.demo.adbconsole.common.Ui;

import java.util.HashMap;
import java.util.Map;

public class MyTester implements Tester {

    private static final String increasePidFormat = "for i in $(seq 1 %d); do ls /proc/self/task; done";
    private final Map<String, Runnable> testers;

    private static final Tester instance = new MyTester();
    public static Tester get() {
        return instance;
    }

    private MyTester() {
        testers = new HashMap<>();
    }

    @Override
    public void test(String name) {
        Runnable tester = testers.get(name);
        if (tester != null) {
            tester.run();
        }
    }

    @Override
    public void attach(Ui ui) {
        if (ui instanceof TestableUi) {
            attach((TestableUi) ui);
        }
    }

    private void attach(TestableUi tui) {
        add(tui, "Increase PID - 100", this::test1);
        add(tui, "Increase PID - 1000", this::test2);
        add(tui, "Increase PID - 10000", this::test3);
    }

    private synchronized void add(TestableUi tui, String name, Runnable tester) {
        tui.addTester(name);
        testers.put(name, tester);
    }

    private void test1() {
        Application.getExecutor().execute(String.format(increasePidFormat, 100));
    }

    private void test2() {
        Application.getExecutor().execute(String.format(increasePidFormat, 1000));
    }

    private void test3() {
        Application.getExecutor().execute(String.format(increasePidFormat, 10000));
    }
}
