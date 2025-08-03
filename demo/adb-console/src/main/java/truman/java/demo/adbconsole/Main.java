package truman.java.demo.adbconsole;

import truman.java.demo.adbconsole.ui.TestUiApp;

import javax.swing.*;

public class Main {

    private void init() {

    }

    private void drawUi() {

    }

    private void run() {
        init();
        drawUi();
    }

    public static void main(String[] args) {
        //        new Main().run();
        System.out.println("in path? " + AdbEnvironment.isExeInEnvPath());

        try {
            AdbEnvironment.initialize();
        } catch (AdbEnvException e) {
        }

        System.out.println("Code Path : " +
                Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Working Path : " + currentDirectory);

        String resourcePath = Main.class.getClassLoader().getResource("adb").getPath();
        System.out.println("Resource Path : " + resourcePath);
        System.out.println("OS : " + System.getProperty("os.name"));
//        System.setProperty("sun.java2d.uiScale", "2.0");
//        SwingUtilities.invokeLater(() -> new AdbConsoleApp().createAndShowGUI());
        SwingUtilities.invokeLater(() -> new TestUiApp().createAndShowGUI());

    }
}