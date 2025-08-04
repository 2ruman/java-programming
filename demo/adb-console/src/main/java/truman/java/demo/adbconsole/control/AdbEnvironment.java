package truman.java.demo.adbconsole.control;

import truman.java.demo.adbconsole.common.Utils;

import static truman.java.demo.adbconsole.common.Utils.*;

public class AdbEnvironment {

    public static String EXE = "adb";
    public static final String SHELL = "shell";

    public static void initialize() throws AdbEnvException {
        ensureSupportedOs();

        String exe = initializeExe();
        if (exe.isEmpty()) {
            throw new AdbEnvException("Cannot find executable file");
        }
        EXE = exe;
    }

    private static void ensureSupportedOs() throws AdbEnvException {
        boolean isSupportedOs = isLinux() || isWindows();
        if (!isSupportedOs) {
            throw new AdbEnvException("Unsupported OS: " + Utils.getOsName());
        }
    }

    private static String initializeExe() {
        String exeName = getExeName();
        return isExeInEnvPath(exeName) ? exeName : Utils.getResourcePath(exeName);
    }

    public static String getExeName() {
        String exeName = "adb";
        return isWindows() ? exeName + ".exe" : exeName;
    }
}