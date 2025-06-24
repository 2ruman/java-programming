package truman.java.demo.file_crypto_converter.crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static byte[] readFromFile(String inputPath) throws IOException {
        return Files.readAllBytes(Paths.get(inputPath));
    }

    public static void saveToFile(byte[] header, byte[] content, String outputPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            if (header != null) {
                fos.write(header);
            }
            fos.write(content);
        }
    }

    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static String getFileDir(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
    }

    public static String attachPrefix(String prefix, String fileName) {
        return (prefix + fileName);
    }

    public static String ripOffPrefix(String prefix, String fileName) {
        return fileName.replace(prefix, "");
    }
}
