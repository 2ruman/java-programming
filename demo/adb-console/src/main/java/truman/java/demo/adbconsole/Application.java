package truman.java.demo.adbconsole;

import truman.java.demo.adbconsole.common.Executor;
import truman.java.demo.adbconsole.control.AdbControl;
import truman.java.demo.adbconsole.control.AdbEnvException;
import truman.java.demo.adbconsole.control.AdbEnvironment;
import truman.java.demo.adbconsole.common.Control;
import truman.java.demo.adbconsole.control.AdbExecutor;
import truman.java.demo.adbconsole.test.MyTester;
import truman.java.demo.adbconsole.common.Tester;
import truman.java.demo.adbconsole.ui.AdbConsole;
import truman.java.demo.adbconsole.common.Ui;

import javax.swing.*;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class Application {

    private static WeakReference<Ui> ui = new WeakReference<>(null);

    public static Application get() {
        return Holder.application;
    }

    public static void create() {
        get().initialize();
        get().onCreate();
    }

    private void initialize() {
//        System.setProperty("sun.java2d.uiScale", "2.0");
    }

    private void onCreate() {
        SwingUtilities.invokeLater(() -> {
            AdbConsole adbConsole = AdbConsole.create(
                    ui -> getTester().attach(ui), Application::setUi);
            try {
                AdbEnvironment.initialize();
            } catch (AdbEnvException e) {
                adbConsole.showError("Initialization Error",
                        "Failed to initialize the program." + System.lineSeparator() +
                                "Error: " + e.getMessage());
                adbConsole.dispose();
            }
        });
    }

    public static Ui getUi() {
        synchronized (Ui.class) {
            return ui.get();
        }
    }

    public static void setUi(Ui ui) {
        synchronized (Ui.class) {
            Application.ui = new WeakReference<>(ui);
        }
    }

    public static void withUi(Consumer<Ui> consumer) {
        Ui ui = getUi();
        if (ui != null) {
            consumer.accept(ui);
        }
    }

    public static Control getControl() {
        return AdbControl.get();
    }

    public static Tester getTester() {
        return MyTester.get();
    }

    public static Executor getExecutor() {
        return AdbExecutor.get();
    }

    private static class Holder {
        private static final Application application = new Application();
    }
}
