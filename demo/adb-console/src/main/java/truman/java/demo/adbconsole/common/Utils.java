package truman.java.demo.adbconsole.common;

import java.io.File;
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

    public static boolean isExeInEnvPath(String exeName) {
        String envPath = getEnvPath();
        if (envPath.isEmpty()) {
            return false;
        }
        String[] paths = envPath.split(File.pathSeparator);
        for (String path : paths) {
            File exeFile = new File(path, exeName);
            if (exeFile.exists() && exeFile.isFile() && exeFile.canExecute()) {
                return true;
            }
        }
        return false;
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
