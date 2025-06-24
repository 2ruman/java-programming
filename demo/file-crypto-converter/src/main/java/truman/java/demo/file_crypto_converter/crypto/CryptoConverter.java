package truman.java.demo.file_crypto_converter.crypto;

import java.security.SecureRandom;

public class CryptoConverter {

    private static final boolean DEBUG = true;
    private final boolean encryptMode;

    public CryptoConverter(boolean encryptMode) {
        this.encryptMode = encryptMode;
    }

    public boolean getEncryptMode() {
        return encryptMode;
    }

    public String convert(String password, String inputPath) throws Exception {
        String outputPath = CryptoRules.generateOutputPath(inputPath, getEncryptMode());
        return convert(password, inputPath, outputPath);
    }

    public String convert(String password, String inputPath, String outputPath) throws Exception {
        if (DEBUG) {
            System.out.println("Encrypt Mode?  : " + encryptMode);
            System.out.println("Input Path  : " + inputPath);
            System.out.println("Output Path : " + outputPath);
        }

        if (encryptMode) {
            encrypt(password, inputPath, outputPath);
        } else {
            decrypt(password, inputPath, outputPath);
        }
        return outputPath;
    }

    public void encrypt(String keyString, String inputPath, String outputPath) throws Exception {

        byte[] key = CryptoUtil.sha256(keyString);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        byte[] plainText = FileUtil.readFromFile(inputPath);

        byte[] cipherText = CryptoUtil.encrypt(key, iv, plainText);

        FileUtil.saveToFile(iv, cipherText, outputPath);

        if (DEBUG) {
            System.out.println("Encryption completed!");
        }
    }


    public static void decrypt(String keyString, String inputPath, String outputPath) throws Exception {

        byte[] key = CryptoUtil.sha256(keyString);
        byte[] blob = FileUtil.readFromFile(inputPath);
        byte[] iv = new byte[16];
        System.arraycopy(blob, 0, iv, 0, iv.length);

        byte[] cipherText = new byte[blob.length - iv.length];
        System.arraycopy(blob, iv.length, cipherText, 0, cipherText.length);

        byte[] plainText = CryptoUtil.decrypt(key, iv, cipherText);

        FileUtil.saveToFile(null, plainText, outputPath);

        if (DEBUG) {
            System.out.println("Decryption completed!");
        }
    }
}
