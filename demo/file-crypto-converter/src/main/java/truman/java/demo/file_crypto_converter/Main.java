package truman.java.demo.file_crypto_converter;

import truman.java.demo.file_crypto_converter.ui.FileCryptoConverter;

import javax.swing.*;

public class Main {

    private static final String VERSION = "0.2.1";

    private void run() {
        init();
        drawUi();
    }

    private void init() {
        System.setProperty("sun.java2d.uiScale", "1.5");
    }

    private void drawUi() {
        SwingUtilities.invokeLater(() -> {
            new FileCryptoConverter(VERSION).setVisible(true);
        });
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
