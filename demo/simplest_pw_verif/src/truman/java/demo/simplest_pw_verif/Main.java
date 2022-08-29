package truman.java.demo.simplest_pw_verif;

import static truman.java.demo.simplest_pw_verif.AIOU.*;

import java.util.Arrays;

/**
 * This program is to show how password verification is done in the simplest way
 * , without considering secure storage in which the password hash is stored.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public class Main {

    // SHA-256 hashed of "1234" - nowhere the password is stored.
    private static final byte[] PASSWORD_HASH = HexToBytes(
            "03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4");

    private static boolean verifyPassword(byte[] password) {
        return Arrays.equals(PASSWORD_HASH, sha256(password));
    }

    public static void main(String[] args) {
        char[] passwordChars = System.console().readPassword("Input your password : ");
        byte[] passwordBytes = charsTobytes(passwordChars);
        try {
            if (verifyPassword(passwordBytes)) {
                System.out.println("Password confirmed!");
            } else {
                System.out.println("Incorrect password...");
            }
        } finally {
            clear(passwordChars);
            clear(passwordBytes);
        }
    }
}
