package truman.java.demo.file_crypto_converter;

import truman.java.demo.file_crypto_converter.ui.FileCryptoConverter;

import javax.swing.*;

public class Main {

    private void run() {
        init();
        drawUi();
    }

    private void init() {
        System.setProperty("sun.java2d.uiScale", "1.5");
    }

    private void drawUi() {
        SwingUtilities.invokeLater(() -> {
            new FileCryptoConverter().setVisible(true);
        });
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
