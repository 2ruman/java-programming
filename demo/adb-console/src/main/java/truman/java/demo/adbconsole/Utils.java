package truman.java.demo.adbconsole;

import java.net.URL;

public class Utils {

    public static boolean isLinux() {
        return getOsName().toLowerCase().startsWith("linux");
    }

    public static boolean isWindows() {
        return getOsName().toLowerCase().startsWith("win");
    }

    public static String getOsName() {
        String osName = System.getProperty("os.name");
        return osName != null ? osName : "";
    }

    public static String getEnvPath() {
        String envPath = System.getenv("PATH");
        return envPath != null ? envPath : "";
    }

    public static String getResourcePath(String resName) {
        URL resUrl = Utils.class.getClassLoader().getResource(resName);
        return resUrl != null ? resUrl.getPath() : "";
    }
}
