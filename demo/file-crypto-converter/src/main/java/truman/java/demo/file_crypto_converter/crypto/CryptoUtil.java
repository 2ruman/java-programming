package truman.java.demo.file_crypto_converter.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoUtil {

    private static SecretKey pbkdf2(char[] passwordChars, byte[] salt)
            throws NoSuchAlgorithmException,InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(passwordChars, salt, 65536, 256);
        return factory.generateSecret(spec);
    }

    public static byte[] sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        return digest.digest(inputBytes);
    }

    public static byte[] encrypt(byte[] key, byte[] iv, byte[] plainText) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        return aes(iv, key, plainText, true);
    }

    public static byte[] decrypt(byte[] key, byte[] iv, byte[] cipherText) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException {
        return aes(iv, key, cipherText, false);
    }

    private static byte[] aes(byte[] iv, byte[] key, byte[] input, boolean encryptMode) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(encryptMode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, ivSpec);

        return cipher.doFinal(input);
    }
}
