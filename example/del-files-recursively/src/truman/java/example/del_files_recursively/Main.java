package truman.java.example.del_files_recursively;

import java.io.File;

public class Main {

    public String getRWX(File file) {
        if (file == null || !file.exists())
            return "   ";

        return new StringBuilder(3)
                .append(file.canRead() ? 'R' : ' ')
                .append(file.canWrite() ? 'W' : ' ')
                .append(file.canExecute() ? 'X' : ' ')
                .toString();
    }

    public void deleteRecursively(File file) {
        if (file == null || !file.exists())
            return;

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (int i = 0 ; i < files.length ; i++) {
                    deleteRecursively(files[i]);
                }
            }
        }
        String fileName = file.getAbsolutePath();
        boolean res = file.delete();
        System.out.println("[" + fileName + "] " + (res ? "deleted!" : "not deleted... " + getRWX(file)));
    }

    public static void main(String[] args) {

        String currWorkingDir = System.getProperty("user.dir");
        File target = new File(currWorkingDir, "test");
        if (!target.exists()) {
            System.out.println("Target [" + target + "] does not exist");
        } else {
            new Main()
                .deleteRecursively(target);
        }
    }
}
