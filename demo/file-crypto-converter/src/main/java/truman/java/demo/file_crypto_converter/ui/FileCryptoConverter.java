package truman.java.demo.file_crypto_converter.ui;

import truman.java.demo.file_crypto_converter.crypto.CryptoConverter;
import truman.java.demo.file_crypto_converter.crypto.CryptoRules;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileCryptoConverter extends JFrame {

    private final JTextField targetFileField;
    private final JPasswordField passwordField;
    private final JButton chooseButton, convertButton;

    private final CryptoConverter cryptoConverter;

    public FileCryptoConverter(CryptoConverter cryptoConverter) {
        this.cryptoConverter = cryptoConverter;

        setTitle(String.format("File Crypto Converter (%s Mode)",
                cryptoConverter.getEncryptMode() ? "Encrypt" : "Decrypt"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 150);
        setLocationRelativeTo(null);

        targetFileField = new JTextField();

        targetFileField.setEditable(false);

        passwordField = new JPasswordField();

        chooseButton = new JButton("Choose File");
        convertButton = new JButton("Convert");

        chooseButton.addActionListener(e -> chooseFile());
        convertButton.addActionListener(e -> convertFile());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Target File:"));
        panel.add(targetFileField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(chooseButton);
        panel.add(convertButton);

        add(panel);
    }

    private void chooseFile() {
        String cwd = System.getProperty("user.dir");
        chooseFile(new File(cwd));
    }

    private void chooseFile(File initialDir) {
        JFileChooser chooser = new JFileChooser(initialDir);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            targetFileField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void convertFile() {
        String filePath = targetFileField.getText();
        char[] passwordChars = passwordField.getPassword();

        CryptoRules.Result checkResult;

        checkResult = CryptoRules.checkFilePathRule(filePath, cryptoConverter.getEncryptMode());
        if (!checkResult.result) {
            JOptionPane.showMessageDialog(this, "Error: " + checkResult.errMsg);
            return;
        }

        String password = new String(passwordChars);
        checkResult = CryptoRules.checkPasswordRule(password);
        if (!checkResult.result) {
            JOptionPane.showMessageDialog(this, "Error: " + checkResult.errMsg);
            return;
        }

        try {
            String outputPath = cryptoConverter.convert(password, filePath);
            JOptionPane.showMessageDialog(this, "Conversion Success!\nOutput Path : " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed in file conversion");
        }
    }
}
