package truman.java.demo.adbconsole.common;

public interface Control {
    void onCommand(String command);
    void onControl(String command);
    void onTest(String name);
}
