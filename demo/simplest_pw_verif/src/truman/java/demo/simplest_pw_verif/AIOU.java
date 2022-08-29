package truman.java.demo.simplest_pw_verif;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * All-In-One Utils
 *
 * @version No version managed
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public final class AIOU {

    public static byte[] sha256(byte[] message) {
        byte[] hash = null;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(message);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < bytes.length ; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    public static byte[] HexToBytes(String hex) {
        byte[] ret = new byte[hex.length() / 2];
        for (int i = 0, j = 0; i < ret.length; i++, j+=2) {
            ret[i] = (byte) Integer.parseInt(hex.substring(j, j + 2), 16);
        }
        return ret;
    }

    public static byte[] charsTobytes(char chars[]) {
        if (chars == null) {
            return null;
        }
        byte[] bytes = new byte[chars.length];
        for (int i = 0 ; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        return bytes;
    }

    public static void clear(char chars[]) {
        if (chars != null) {
            Arrays.fill(chars, '\0');
        }
    }

    public static void clear(byte bytes[]) {
        if (bytes != null) {
            Arrays.fill(bytes, (byte) 0);
        }
    }
}
