package truman.java.demo.adbconsole.ui;

import javax.swing.*;
import java.awt.*;

public class TestUiApp {

    private JTextArea outputArea;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Test Runner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        // SplitPane 설정
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.0); // 오른쪽만 resize 대상
        splitPane.setDividerSize(5);
        splitPane.setEnabled(false); // Divider 이동 막기

        // 왼쪽 패널
        JPanel leftPanel = createLeftPanel();
        int fixedWidth = 300;
        Dimension fixedSize = new Dimension(fixedWidth, Integer.MAX_VALUE);
        leftPanel.setPreferredSize(fixedSize);
        leftPanel.setMinimumSize(new Dimension(fixedWidth, 0));
        leftPanel.setMaximumSize(fixedSize);

        // 오른쪽 패널 (TextArea + Scroll)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane rightScrollPane = new JScrollPane(outputArea);

        // SplitPane 구성
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightScrollPane);
        splitPane.setDividerLocation(fixedWidth);

        frame.getContentPane().add(splitPane);
        frame.setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 절대 위치 사용 (vertical resize로부터 완전히 보호)

        int padding = 10;
        int labelHeight = 20;
        int fieldHeight = 30;
        int buttonHeight = 30;
        int fieldWidth = 160;
        int buttonWidth = 80;

        // Input Label
        JLabel inputLabel = new JLabel("Input:");
        inputLabel.setBounds(padding, padding, 100, labelHeight);

        // Input Field + Run 버튼
        JTextField inputField = new JTextField();
        inputField.setBounds(padding, padding + 25, fieldWidth, fieldHeight);

        JButton inputRunButton = new JButton("Run");
        inputRunButton.setBounds(padding + fieldWidth + 10, padding + 25, buttonWidth, buttonHeight);
        inputRunButton.addActionListener(e -> appendOutput("▶ Input Run: " + inputField.getText()));

        // Test Items Label
        JLabel testLabel = new JLabel("Test Items:");
        testLabel.setBounds(padding, padding + 70, 150, labelHeight);

        // ComboBox + Run 버튼
        String[] testItems = {"test_1", "test_2", "test_3"};
        JComboBox<String> comboBox = new JComboBox<>(testItems);
        comboBox.setBounds(padding, padding + 95, fieldWidth, fieldHeight);

        JButton testRunButton = new JButton("Run");
        testRunButton.setBounds(padding + fieldWidth + 10, padding + 95, buttonWidth, buttonHeight);
        testRunButton.addActionListener(e -> {
            String selected = (String) comboBox.getSelectedItem();
            appendOutput("▶ Test Run: " + selected);
        });

        // Bottom buttons
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");

        int bottomY = padding + 150;
        int buttonGap = 5;
        int bottomButtonWidth = 80;

        startButton.setBounds(padding, bottomY, bottomButtonWidth, buttonHeight);
        stopButton.setBounds(padding + bottomButtonWidth + buttonGap, bottomY, bottomButtonWidth, buttonHeight);
        resetButton.setBounds(padding + 2 * (bottomButtonWidth + buttonGap), bottomY, bottomButtonWidth, buttonHeight);

        // 패널에 컴포넌트 추가
        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(inputRunButton);
        panel.add(testLabel);
        panel.add(comboBox);
        panel.add(testRunButton);
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(resetButton);

        // 패널의 layout이 null이므로 preferred size를 수동 지정
        panel.setPreferredSize(new Dimension(300, 220)); // 높이 고정

        return panel;
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}