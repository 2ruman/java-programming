package truman.java.demo.adbconsole.control;

import truman.java.demo.adbconsole.common.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

import static truman.java.demo.adbconsole.common.Utils.*;

public class AdbEnvironment {

    public static String EXE = "adb";
    public static final String SHELL = "shell";
    private static final List<String> LIBS;

    static {
        LIBS = isWindows() ?
                List.of(getExeName(), "AdbWinApi.dll", "AdbWinUsbApi.dll") :
                List.of(getExeName());
    }

    public static void initialize() throws AdbEnvException {
        ensureSupportedOs();

        initializeExe();
    }

    private static void ensureSupportedOs() throws AdbEnvException {
        boolean isSupportedOs = isLinux() || isWindows();
        if (!isSupportedOs) {
            throw new AdbEnvException("Unsupported OS: " + Utils.getOsName());
        }
    }

    private static void initializeExe() throws AdbEnvException {
        String exeName = getExeName();
        if (isExeInEnvPath(exeName)) {
            EXE = exeName;
            return;
        }

        String jarPath = getJarPath();
        if (jarPath.isEmpty()) {
            throw new AdbEnvException("Cannot find jar path");
        }

        File jarDir = new File(jarPath).getParentFile();
        if (!checkPortableLibs(jarDir)) {
            installLibs(jarDir);
            if (!checkPortableLibs(jarDir)) {
                throw new AdbEnvException("Cannot find portable libs");
            }
        }
        EXE = new File(jarDir, exeName).getAbsolutePath();
    }

    private static void installLibs(File dir) throws AdbEnvException {

        for (String lib : LIBS) {
            File libFile = new File(dir, lib);
            if (!libFile.exists()) {
                try (InputStream in = AdbEnvironment.class.getClassLoader().getResourceAsStream(lib)) {
                    Files.copy(Objects.requireNonNull(in), libFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    if (libFile.getPath().endsWith(getExeName())) {
                        if (!libFile.setExecutable(true)) {
                            throw new AdbEnvException("Failed to set executable");
                        }
                    }
                    if (!libFile.setReadable(true)) {
                        throw new AdbEnvException("Failed to set readable");
                    }
                } catch (IOException e) {
                    throw new AdbEnvException("IO Error");
                }
            }
        }
    }

    private static boolean checkPortableLibs(File dir) {
        File exeFile = new File(dir, getExeName());
        if (!exeFile.exists() || !exeFile.canExecute()) {
            return false;
        }
        for (String lib : LIBS) {
            File libFile = new File(dir, lib);
            if (!libFile.exists() || !libFile.canRead()) {
                return false;
            }
        }
        return true;
    }

    public static String getExeName() {
        String exeName = "adb";
        return isWindows() ? exeName + ".exe" : exeName;
    }
}