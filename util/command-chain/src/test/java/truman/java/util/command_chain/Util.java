package truman.java.util.command_chain;

import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Util {

    private static final PrintStream DEFAULT_OUT = System.out;

    public void runWith(String fileName, Executable executable) {
        try {
            setOut(fileName);
            executable.execute();
            resetOut();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void setOut(String fileName) throws FileNotFoundException {
        try {
            System.setOut(new PrintStream(getTargetFile(fileName)));
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    private void resetOut() {
        System.setOut(DEFAULT_OUT);
    }

    public File getTargetFile(String fileName) {
        return new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + fileName);
    }
}
