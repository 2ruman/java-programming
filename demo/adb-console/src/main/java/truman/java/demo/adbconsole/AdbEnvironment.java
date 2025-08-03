package truman.java.demo.adbconsole;

import java.io.File;

import static truman.java.demo.adbconsole.Utils.*;

public class AdbEnvironment {

    private static void ensureSupportedOs() throws AdbEnvException {
        boolean isSupportedOs = isLinux() || isWindows();
        if (!isSupportedOs) {
            throw new AdbEnvException("Unsupported OS: " + Utils.getOsName());
        }
    }

    public static String getExeName() {
        String exeName = "adb";
        return isWindows() ? exeName + ".exe" : exeName;
    }

    public static boolean isExeInEnvPath() {
        String envPath = getEnvPath();
        if (envPath.isEmpty()) {
            return false;
        }
        String exeName = getExeName();
        String[] paths = envPath.split(File.pathSeparator);
        for (String path : paths) {
            File exeFile = new File(path, exeName);
            if (exeFile.exists() && exeFile.isFile() && exeFile.canExecute()) {
                return true;
            }
        }
        return false;
    }

    private static String initializeExe() {
        return isExeInEnvPath() ? getExeName() : Utils.getResourcePath(getExeName());
    }

    public static AdbEnv initialize() throws AdbEnvException {
        ensureSupportedOs();

        String exe = initializeExe();
        if (exe.isEmpty()) {
            throw new AdbEnvException("Cannot find executable file");
        }
        return null;
    }

    public static class AdbEnv {
        private final String exe;

        private AdbEnv(String exe) {
            this.exe = exe;
        }

        public String getExe() {
            return exe;
        }

    }

}