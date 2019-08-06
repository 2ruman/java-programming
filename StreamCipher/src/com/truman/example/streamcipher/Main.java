package com.truman.example.streamcipher;

import java.security.SecureRandom;

import com.truman.cipher.StreamCipher;
import com.truman.util.Log;

public class Main {

    public static void main(String[] args) {
        String streamStr = "Hi, there! I'm testing StreamCipher."; // 36 bytes of length
        SecureRandom rng = new SecureRandom();
        byte[] key16 = new byte[16]; rng.nextBytes(key16);
        byte[] key36 = new byte[36]; rng.nextBytes(key36);
        byte[] key50 = new byte[50]; rng.nextBytes(key50);

        byte[] cipher, plain;
        Log.d("\n### Test with key of 16 bytes ###");
        cipher = StreamCipher.streamCipher(streamStr.getBytes(), key16);
        plain = StreamCipher.streamCipher(cipher, key16);
        Log.d("Check the original stream >> " + new String(plain));
        Log.d("\n### Test with key of 36 bytes ###");
        cipher = StreamCipher.streamCipher(streamStr.getBytes(), key36);
        plain = StreamCipher.streamCipher(cipher, key36);
        Log.d("Check the original stream >> " + new String(plain));
        Log.d("\n### Test with key of 50 bytes ###");
        cipher = StreamCipher.streamCipher(streamStr.getBytes(), key50);
        plain = StreamCipher.streamCipher(cipher, key50);
        Log.d("Check the original stream >> " + new String(plain));
    }
}
