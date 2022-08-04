package truman.java.example.get_cwd;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    // Using System Property
    public static String method1() {
        return System.getProperty("user.dir");
    }

    // Using NIO APIs
    public static String method2() {
        Path currPath = Paths.get("");
        return currPath.toAbsolutePath().toString();
    }

    public static void main(String[] args) {
        System.out.println("Current Working Directory : " + method1());
        System.out.println("Current Working Directory : " + method2());
    }
}
