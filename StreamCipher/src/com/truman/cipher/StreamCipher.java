package com.truman.cipher;

public class StreamCipher {

    public static byte[] streamCipher(byte[] stream, byte[] key)
            throws IllegalArgumentException {
        if (stream == null || stream.length == 0
                || key == null || key.length == 0) {
            throw new IllegalArgumentException("Invalid parameter");
        }

        byte[] res = new byte[stream.length];
        if (stream.length > key.length) {
            for (int i = 0, kI = 0 ; i < stream.length ; i++, kI = i % key.length) {
                res[i] = (byte) (stream[i] ^ key[kI]);
            }
        } else {
            for (int i = 0 ; i < stream.length ; i++) {
                res[i] = (byte) (stream[i] ^ key[i]);
            }
        }
        return res;
    }
}
