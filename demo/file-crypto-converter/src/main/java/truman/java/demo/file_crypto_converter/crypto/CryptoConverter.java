package truman.java.demo.file_crypto_converter.crypto;

import java.security.SecureRandom;

public class CryptoConverter {

    private static final boolean DEBUG = true;
    private static final CryptoMode DEFAULT_CRYPTO_MODE = CryptoMode.ENCRYPT;

    private CryptoMode cryptoMode;

    public CryptoConverter() {
        this(DEFAULT_CRYPTO_MODE);
    }

    public CryptoConverter(CryptoMode cryptoMode) {
        this.cryptoMode = cryptoMode;
    }

    public CryptoMode getCryptoMode() {
        return cryptoMode;
    }

    public void setCryptoMode(CryptoMode cryptoMode) {
        this.cryptoMode = cryptoMode;
    }

    public boolean isEncryptMode() {
        return CryptoMode.ENCRYPT == getCryptoMode();
    }

    public String convert(String password, String inputPath) throws Exception {
        String outputPath = CryptoRules.generateOutputPath(inputPath, isEncryptMode());
        return convert(password, inputPath, outputPath);
    }

    public String convert(String password, String inputPath, String outputPath) throws Exception {
        if (DEBUG) {
            System.out.println("Crypto Mode?  : " + cryptoMode);
            System.out.println("Input Path  : " + inputPath);
            System.out.println("Output Path : " + outputPath);
        }

        if (isEncryptMode()) {
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
