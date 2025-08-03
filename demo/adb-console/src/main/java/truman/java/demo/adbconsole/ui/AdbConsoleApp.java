package truman.java.demo.adbconsole.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class AdbConsoleApp {

    private JTextArea outputArea;
    private JTextField commandField;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("ADB 명령 실행기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        // SplitPane 생성
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//        splitPane.setResizeWeight(0.3); // 왼쪽 50%, 오른쪽 50%
//        splitPane.setResizeWeight(0.0); // 왼쪽 50%, 오른쪽 50%
        System.out.println("Continuous? " + splitPane.isContinuousLayout());
//        splitPane.setContinuousLayout(true);
//        System.out.println("Continuous? " + splitPane.isContinuousLayout());
        splitPane.setEnabled(false);
        splitPane.setDividerSize(5); // 시각적으로 작게
        splitPane.setDividerLocation(400); // Divider 고정 위치
        // 좌측 패널 (입력 및 버튼들)
        JPanel leftPanel = new JPanel(new BorderLayout());

        // TRUMAN
        Dimension fixedSize = new Dimension(400, Integer.MAX_VALUE);
        leftPanel.setMinimumSize(fixedSize);
        leftPanel.setMaximumSize(fixedSize);
        leftPanel.setPreferredSize(fixedSize);

        // 명령 입력 필드
        commandField = new JTextField();
        leftPanel.add(commandField, BorderLayout.NORTH);

        // 버튼들
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        JButton executeButton = new JButton("실행");
        executeButton.addActionListener(this::onExecute);
        buttonPanel.add(executeButton);

        JButton clearButton = new JButton("결과 지우기");
        clearButton.addActionListener(e -> outputArea.setText(""));
        buttonPanel.add(clearButton);

        // 버튼 영역 추가
        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        // 우측 패널 (결과 출력 영역)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane rightScrollPane = new JScrollPane(outputArea);

        // SplitPane에 양쪽 추가
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightScrollPane);

        // Frame에 SplitPane 추가
        frame.getContentPane().add(splitPane);
        frame.setVisible(true);
    }

    private void onExecute(ActionEvent e) {
        String command = commandField.getText().trim();
//        if (command.isEmpty()) return;

        // ADB 명령 실행
        new Thread(() -> runAdbCommand(command)).start();
    }

    private void test() {

        try {
            String resourcePath = this.getClass().getClassLoader().getResource("adb").getPath();
            ProcessBuilder builder = new ProcessBuilder(resourcePath, "version");
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                appendOutput(line);
            }

            int exitCode = process.waitFor();
            appendOutput("[종료 코드: " + exitCode + "]");

        } catch (IOException | InterruptedException ex) {
            appendOutput("에러 발생: " + ex.getMessage());
        } catch (Exception e) {
            appendOutput("예기치 못한 에러 발생: " + e.getMessage());
        }
    }
    private void runAdbCommand(String command) {
        System.out.println("command : " + command);
        if (true) {
            test();
            return;
        }
        try {
            ProcessBuilder builder = new ProcessBuilder("adb", "shell", command);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                appendOutput(line);
            }

            int exitCode = process.waitFor();
            appendOutput("[종료 코드: " + exitCode + "]");

        } catch (IOException | InterruptedException ex) {
            appendOutput("에러 발생: " + ex.getMessage());
        }
    }

    private void appendOutput(String line) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(line + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }
}