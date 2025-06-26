package truman.java.demo.file_crypto_converter.crypto;

public enum CryptoMode {
    ENCRYPT,
    DECRYPT;

    @Override
    public String toString() {
        return switch (this) {
            case ENCRYPT -> "Encrypt";
            case DECRYPT -> "Decrypt";
        };
    }
}
