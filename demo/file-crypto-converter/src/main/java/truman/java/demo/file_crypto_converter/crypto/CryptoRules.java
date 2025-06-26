package truman.java.demo.file_crypto_converter.crypto;

public class CryptoRules {

    private static final String FN_PREFIX = "obfuscated_";

    public static Result checkFilePathRule(String filePath, boolean isEncryptMode) {
        if (filePath == null || filePath.isEmpty()) {
            return new Result("Invalid file path");
        }
        String fileName = FileUtil.getFileName(filePath);
        if (!isEncryptMode && !fileName.startsWith(FN_PREFIX)) {
            return new Result("File name must start with " + FN_PREFIX);
        }
        if (isEncryptMode && fileName.startsWith(FN_PREFIX)) {
            return new Result("File name must not start with " + FN_PREFIX);
        }
        return new Result();
    }

    public static Result checkPasswordRule(String password) {
        if (password == null || password.isEmpty()) {
            return new Result("Password cannot be empty");
        }
        return new Result();
    }

    public static String generateOutputPath(String inputPath, boolean isEncryptMode) {
        String fileName = FileUtil.getFileName(inputPath);
        String outputPath = FileUtil.getFileDir(inputPath);

        if (isEncryptMode) {
            outputPath += (FileUtil.attachPrefix(FN_PREFIX, fileName));
        } else {
            outputPath += (FileUtil.ripOffPrefix(FN_PREFIX, fileName));
        }
        return outputPath;
    }

    public static class Result {
        public boolean result;
        public String errMsg;

        Result() {
            this.result = true;
            this.errMsg = "";

        }

        Result(String errMsg) {
            this.result = false;
            this.errMsg = errMsg;
        }
    }
}
