package truman.java.demo.adbconsole.ui;

import truman.java.demo.adbconsole.Application;
import truman.java.demo.adbconsole.common.Control;
import truman.java.demo.adbconsole.common.TestableUi;
import truman.java.demo.adbconsole.common.Ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import static truman.java.demo.adbconsole.ui.AdbConsole.Utils.*;

public class AdbConsole extends JFrame implements TestableUi {

    private static final String FRAME_TITLE = "ADB Console";
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;

    private JTextArea out;
    private JComboBox<String> testerList;

    private final Control control;

    public static AdbConsole create(Consumer<Ui> onCreate, Consumer<Ui> postCreate) {
        AdbConsole adbConsole = new AdbConsole();
        if (onCreate != null) {
            onCreate.accept(adbConsole);
        }
        adbConsole.setVisible(true);
        if (postCreate != null) {
            postCreate.accept(adbConsole);
        }
        return adbConsole;
    }

    private AdbConsole() {
        control = Application.getControl();

        initFrame(frame -> initSplitPane(frame, (splitPane) -> {
            initLeft(splitPane);
            initRight(splitPane);
        }));
    }

    private void initFrame(Consumer<JFrame> consumeMe) {
        JFrame me = this;
        me.setTitle(FRAME_TITLE);
        me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        me.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        consumeMe.accept(me);
    }

    private void initSplitPane(JFrame parent, Consumer<JSplitPane> consumeMe) {
        JSplitPane me = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        me.setResizeWeight(0.0);
        me.setDividerSize(5);
        me.setEnabled(false);

        consumeMe.accept(me);
        parent.add(me);
    }

    private void initLeft(JSplitPane parent) {
        JPanel left = new JPanel();
        left.setLayout(null);

        int horizontalMargin = 10;
        int verticalMargin = 15;
        int padding = 10;
        int gap = 5;

        int labelWidth = 100;
        int labelHeight = 20;
        int textFieldWidth = 160;
        int textFieldHeight = 30;
        int comboBoxWidth = 160;
        int comboBoxHeight = 30;
        int buttonWidth = 80;
        int buttonHeight = 30;
        int longButtonWidth = 120;;

        JLabel cmdLabel = new JLabel("Command :");
        cmdLabel.setBounds(padding, padding, labelWidth, labelHeight);

        JTextField cmdField = new JTextField();
        cmdField.setBounds(padding, getBottom(cmdLabel, gap), textFieldWidth, textFieldHeight);

        JButton cmdRunButton = new JButton("Run");
        cmdRunButton.setBounds(getEnd(cmdField, horizontalMargin), getTop(cmdField), buttonWidth, buttonHeight);
        cmdRunButton.addActionListener(e -> control.onCommand(cmdField.getText()));

        JLabel testersLabel = new JLabel("Testers :");
        testersLabel.setBounds(padding, getBottom(cmdField, verticalMargin), labelWidth, labelHeight);

        JComboBox<String> testerComboBox = new JComboBox<>();
        testerComboBox.setBounds(padding, getBottom(testersLabel, gap), comboBoxWidth, comboBoxHeight);
        this.testerList = testerComboBox;

        JButton testerRunButton = new JButton("Run");
        testerRunButton.setBounds(getEnd(testerComboBox, horizontalMargin), getTop(testerComboBox), buttonWidth, buttonHeight);
        testerRunButton.addActionListener(e -> {
            control.onTest((String) testerComboBox.getSelectedItem());
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(getStart(testerRunButton), getBottom(testerRunButton, verticalMargin), buttonWidth, buttonHeight);
        clearButton.addActionListener(e -> {
            clear();
        });

        JLabel ctrlLabel = new JLabel("Control :");
        ctrlLabel.setBounds(padding, getBottom(clearButton, verticalMargin), labelWidth, labelHeight);

        JButton devicesButton = new JButton("Devices");
        devicesButton.setBounds(padding, getBottom(ctrlLabel, gap), longButtonWidth, buttonHeight);
        devicesButton.addActionListener(e -> {
            control.onControl(devicesButton.getText());
        });

        JButton rebootButton = new JButton("Reboot");
        rebootButton.setBounds(getEnd(devicesButton, gap + gap), getTop(devicesButton), longButtonWidth, buttonHeight);
        rebootButton.addActionListener(e -> {
            control.onControl(rebootButton.getText());
        });

        left.add(cmdLabel);
        left.add(cmdField);
        left.add(cmdRunButton);
        left.add(testersLabel);
        left.add(testerComboBox);
        left.add(testerRunButton);
        left.add(clearButton);
        left.add(ctrlLabel);
        left.add(devicesButton);
        left.add(rebootButton);

        left.setPreferredSize(new Dimension(FRAME_WIDTH / 3, 0));

        parent.setLeftComponent(left);
    }

    private void initRight(JSplitPane parent) {
        out = new JTextArea();
        out.setEditable(false);
        out.setLineWrap(true);
        out.setWrapStyleWord(true);
        JScrollPane right = new JScrollPane(out);

        parent.setRightComponent(right);
    }

    private void appendOutput(String text) {
        out.append(text + "\n");
        out.setCaretPosition(out.getDocument().getLength());
    }


    @Override
    public void println(String s) {
        SwingUtilities.invokeLater(() -> {
            out.append(s + System.lineSeparator());
            out.setCaretPosition(out.getDocument().getLength());
        });
    }

    @Override
    public void clear() {
        SwingUtilities.invokeLater(() -> {
            out.setText("");
        });
    }

    @Override
    public void addTester(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        testerList.addItem(name);
    }

    public void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    static class Utils {
        static int getStart(JComponent component) {
            return component.getX();
        }

        static int getEnd(JComponent component, int spacing) {
            return getEnd(component) + spacing;
        }

        static int getEnd(JComponent component) {
            return component.getX() + component.getWidth();
        }

        static int getTop(JComponent component) {
            return component.getY();
        }

        static int getBottom(JComponent component, int spacing) {
            return getBottom(component) + spacing;
        }

        static int getBottom(JComponent component) {
            return component.getY() + component.getHeight();
        }
    }
}