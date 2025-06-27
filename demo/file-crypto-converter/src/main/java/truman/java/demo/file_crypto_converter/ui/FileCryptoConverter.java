package truman.java.demo.file_crypto_converter.ui;

import truman.java.demo.file_crypto_converter.crypto.CryptoConverter;
import truman.java.demo.file_crypto_converter.crypto.CryptoMode;
import truman.java.demo.file_crypto_converter.crypto.CryptoRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.function.Consumer;

public class FileCryptoConverter extends JFrame {

    private final String version;
    private final JTextField targetFileField;
    private final JPasswordField passwordField;
    private final CryptoConverter cryptoConverter;

    public FileCryptoConverter(String version) {
        this.version = version;
        this.cryptoConverter = new CryptoConverter();
        this.targetFileField = new JTextField();
        this.passwordField = new JPasswordField();

        initFrame();
        initPanel(panel -> {
            initRow1(panel);
            initRow2(panel);
            initRow3(panel);
            initRow4(panel);
            add(panel);
        });
    }

    private void initFrame() {
        updateFrameTitle();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 250);
        setLocationRelativeTo(null);
    }

    private void updateFrameTitle() {
        setTitle(String.format("File Crypto Converter (%s)", cryptoConverter.getCryptoMode()));
    }

    private void initPanel(Consumer<JPanel> panelConsumer) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelConsumer.accept(panel);
    }

    private void initRow1(JPanel panel) {
        panel.add(new JLabel("Target File:"));
        panel.add(this.targetFileField);
        this.targetFileField.setEditable(false);
    }

    private void initRow2(JPanel panel) {
        panel.add(new JLabel("Password:"));
        panel.add(this.passwordField);
    }

    private void initRow3(JPanel panel) {
        JButton chooseButton = new JButton("Choose File");
        JButton convertButton = new JButton("Convert");

        chooseButton.addActionListener(e -> chooseFile());
        convertButton.addActionListener(e -> convertFile());

        panel.add(chooseButton);
        panel.add(convertButton);
    }

    private void initRow4(JPanel panel) {
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));

        JRadioButton encryptModeButton = new JRadioButton("Encrypt");
        JRadioButton decryptModeButton = new JRadioButton("Decrypt");

        ButtonGroup modeButtonGroup= new ButtonGroup();
        modeButtonGroup.add(encryptModeButton);
        modeButtonGroup.add(decryptModeButton);

        encryptModeButton.setSelected(cryptoConverter.isEncryptMode());
        decryptModeButton.setSelected(!cryptoConverter.isEncryptMode());

        encryptModeButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cryptoConverter.setCryptoMode(CryptoMode.ENCRYPT);
                updateFrameTitle();
            }
        });
        decryptModeButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                cryptoConverter.setCryptoMode(CryptoMode.DECRYPT);
                updateFrameTitle();
            }
        });
        radioButtonPanel.add(encryptModeButton);
        radioButtonPanel.add(decryptModeButton);
        panel.add(radioButtonPanel);

        JLabel versionLabel = new JLabel("Version: " + version);
        versionLabel.setFont(new Font("Arial", Font.BOLD|Font.ITALIC, 12));
        versionLabel.setForeground(Color.GRAY);
        versionLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(versionLabel);
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

        checkResult = CryptoRules.checkFilePathRule(filePath, cryptoConverter.isEncryptMode());
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
