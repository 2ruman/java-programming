package truman.java.example.set_str_to_clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class Main {

    static void method1(String string) {
        StringSelection selection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    static void method2(String string) {
        String[] cmd = {
            "/bin/sh",
            "-c",
            String.format("echo %s | xsel -b", string)
            };

            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {

        String string = "Hello, world!";
        /**
         *  This method will not work with OpenJDK on Linux,
         *  whereas would work with SunJDK on Linux.
         */
//      method1(string);

        /**
         * This method requires "xsel" installed
         */
        method2(string);

        System.out.println("Done!");
    }

}
