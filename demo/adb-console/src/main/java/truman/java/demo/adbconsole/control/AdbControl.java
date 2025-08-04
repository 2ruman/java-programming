package truman.java.demo.adbconsole.control;

import truman.java.demo.adbconsole.Application;
import truman.java.demo.adbconsole.common.Control;

import static truman.java.demo.adbconsole.Application.withUi;

public class AdbControl implements Control {

    public static Control get() {
        return Holder.instance;
    }

    @Override
    public void onCommand(String command) {
        execute(command);
    }

    @Override
    public void onControl(String command) {
        executeLocal(command.toLowerCase());
    }

    @Override
    public void onTest(String name) {
        Application.getTester().test(name);
    }

    private void executeLocal(String command) {
        withUi(ui -> {
            AdbShell.executeLocal(command, ui::println);
        });
    }

    private void execute(String command) {
        withUi(ui -> {
            AdbShell.execute(command, ui::println);
        });
    }

    private static class Holder {
        private static final AdbControl instance = new AdbControl();
    }
}
