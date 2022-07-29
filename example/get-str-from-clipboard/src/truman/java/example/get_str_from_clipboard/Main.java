package truman.java.example.get_str_from_clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasString = (contents != null)
                && contents.isDataFlavorSupported(DataFlavor.stringFlavor);

        String result = "";
        if (hasString) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
            System.out.println("String in clipboard :\n" + result);
        } else {
            // You can clean up your clipboard by "xsel -bc" command if you have xsel installed
            System.out.println("Nothing in clipboard...");
        }
    }
}
