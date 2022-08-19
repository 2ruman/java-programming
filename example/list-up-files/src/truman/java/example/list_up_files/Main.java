package truman.java.example.list_up_files;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public Set<String> listUpFiles(File dir) {
        Set<String> fileList = new TreeSet<>();
        listUpFiles(dir, fileList);
        return fileList;
    }

    public List<String> listUpFiles(File dir, boolean sorted) {
        List<String> fileList = new ArrayList<>();
        listUpFiles(dir, fileList);
        if (sorted) {
            Collections.sort(fileList);
        }
        return fileList;
    }

    private void listUpFiles(File file, Collection<String> fileList) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length != 0) {
                for (int i = 0 ; i < files.length ; i++) {
                    listUpFiles(files[i], fileList);
                }
            }
        } else {
            fileList.add(file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        String currDir = System.getProperty("user.dir");
        File target = new File(currDir, "test.bak");
        if (!target.exists()) {
            System.out.println("Target [" + target + "] does not exist");
        } else {
            // Set<String> result = new Main().listUpFiles(target);
            // List<String> result = new Main().listUpFiles(target, true);
            List<String> result = new Main().listUpFiles(target, false);

            result.forEach(System.out::println);
        }
    }
}
