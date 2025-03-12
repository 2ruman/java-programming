package com.truman.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Author  : Truman
 * Contact : truman.t.kim@gmail.com
 * Version : 1.1.1
 */
public class BytesUtil {

    private static final ByteOrder DEFAULT_BYTE_ORDER = ByteOrder.BIG_ENDIAN;
    private static final int INTEGER_SIZE = Integer.SIZE/8;
    private static final int LONG_SIZE = Long.SIZE/8;
    private static final String DEFAULT_HEX_PREFIX = ""; // = "0x";
    private static final String DEFAULT_HEX_DELIMITER = " "; // = "";
    private static final HexCase DEFAULT_HEX_CASE = HexCase.UPPER_CASE;

    public enum HexCase {
        UPPER_CASE, LOWER_CASE
    }

    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, DEFAULT_HEX_PREFIX, DEFAULT_HEX_DELIMITER, DEFAULT_HEX_CASE);
    }

    public static String bytesToHex(byte[] bytes, String prefix, String delimiter,
            HexCase hexCase) {
        String ret = "";
        if (bytes == null) {
            ret = "null";
        } else {
            final String hex = (hexCase == HexCase.UPPER_CASE)
                    ? "0123456789ABCDEF" : "0123456789abcdef";
            final boolean hasDel = (delimiter != null && !delimiter.isEmpty());
            final boolean hasPrf = (prefix != null && !prefix.isEmpty());

            StringBuilder sb = new StringBuilder();
            for(byte b : bytes) {
                if (hasPrf) {
                    sb.append(prefix);
                }
                sb.append(hex.charAt((b >>> 4) & 0xF));
                sb.append(hex.charAt(b & 0xF));
                if (hasDel) {
                    sb.append(delimiter);
                }
            }
            if (sb.length() > 0 && hasDel) {
                sb.deleteCharAt(sb.length() -1);
            }
            ret = sb.toString();
        }
        return ret;
    }

    public static byte[] hexToBytes(String hex) throws IllegalArgumentException {
        return hexToBytes(hex, DEFAULT_HEX_PREFIX, DEFAULT_HEX_DELIMITER);
    }

    public static byte[] hexToBytes(String hex, String prefix, String delimiter)
            throws IllegalArgumentException {
        if (hex == null) return null;
        if (hex.isEmpty()) return new byte[0];

        int prfLen = (prefix == null || prefix.isEmpty()) ? 0 : prefix.length();
        int delLen = (delimiter == null || delimiter.isEmpty()) ? 0 : delimiter.length();
        int hexLen = hex.length();

        if ((hexLen + delLen) % (prfLen + delLen + 2) != 0) {
            throw new IllegalArgumentException("Invalid hex length");
        }

        int resIdx = 0;
        int resLen = (hexLen + delLen) / (prfLen + delLen + 2);
        byte[] res = new byte[resLen];
        int i = 0;
        while(i < hexLen) {
            i += prfLen;
            res[resIdx++] = (byte) (charToDigit(hex.charAt(i)) << 4 | charToDigit(hex.charAt(i + 1)));
            i += 2;
            i += delLen;
        }
        return res;
    }

    private static int charToDigit(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        } else if ('a' <= c && c <= 'f') {
            return 10 + (c - 'a');
        } else if ('A' <= c && c <= 'F') {
            return 10 + (c - 'A');
        }

        throw new IllegalArgumentException("Invalid hex char");
    }

    public static String bytesToString(byte[] bytes) {
        return bytes == null ? "null" : new String(bytes);
    }

    public static byte[] intToBytes(int integer) {
        return intToBytes(integer, DEFAULT_BYTE_ORDER);
    }

    public static byte[] intToBytes(int integer, ByteOrder order) {
        ByteBuffer buff = ByteBuffer.allocate(INTEGER_SIZE);
        buff.order(order);
        buff.putInt(integer);

        return buff.array();
    }
 
    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes,DEFAULT_BYTE_ORDER);
    }

    public static int bytesToInt(byte[] bytes, ByteOrder order) {
        ByteBuffer buff = ByteBuffer.allocate(INTEGER_SIZE);
        buff.order(order);
        buff.put(bytes);
        buff.flip();

        return buff.getInt();
    }

    public static byte[] longToBytes(long longVal) {
        return longToBytes(longVal, DEFAULT_BYTE_ORDER);
    }

    public static byte[] longToBytes(long longVal, ByteOrder order) {
        ByteBuffer buff = ByteBuffer.allocate(LONG_SIZE);
        buff.order(order);
        buff.putLong(longVal);

        return buff.array();
    }

    public static long bytesToLong(byte[] bytes) {
        return bytesToLong(bytes,DEFAULT_BYTE_ORDER);
    }

    public static long bytesToLong(byte[] bytes, ByteOrder order) {
        ByteBuffer buff = ByteBuffer.allocate(LONG_SIZE);
        buff.order(order);
        buff.put(bytes);
        buff.flip();

        return buff.getLong();
    }

    public static byte[] stripLSB(byte[] bytes, int length) {
        if (bytes == null || length < 0 || length > bytes.length) {
            return null;
        }
        byte[] ret = new byte[length];
        System.arraycopy(bytes, 0, ret, 0, length);

        return ret;
    }

    public static byte[] stripMSB(byte[] bytes, int length) {
        if (bytes == null || length < 0 || length > bytes.length) {
            return null;
        }
        int srcPos = bytes.length - length;
        byte[] ret = new byte[length];
        System.arraycopy(bytes, srcPos, ret, 0, length);

        return ret;
    }

    public static boolean compareBytes(byte[] b1, byte[] b2) {
        if (b1 == null && b2 == null) return true;
        if ((b1 == null && b2 != null) || (b1 != null && b2 == null)
                || (b1.length != b2.length)) return false;
        for (int i = 0 ; i < b1.length ; i++) {
            if (b1[i] != b2[i]) return false;
        }
        return true;
    }

    public static char[] bytesToChars(byte[] bytes) {
        if (bytes == null) return null;

        char[] chars = new char[bytes.length];
        for (int i = 0 ; i < bytes.length ; i++) {
            chars[i] = (char)bytes[i];
        }
        return chars;
    }

    public static boolean validateBytes(byte[] bytes) {
        return (bytes != null && bytes.length > 0);
    }

    public static byte[] serializeBytes(byte[]... byteArrays) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (byte[] byteArray : byteArrays) {
            if (byteArray == null) {
                continue;
            } else {
                try {
                    outputStream.write(byteArray);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return outputStream.toByteArray();
    }

    public static void zeroize(byte[] bytes) {
        if (bytes != null)
            Arrays.fill(bytes, (byte) 0);
        return;
    }

    public static void zeroize(char[] chars) {
        if (chars != null)
            Arrays.fill(chars, (char) 0);
        return;
    }
}
