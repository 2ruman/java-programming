package truman.java.demo.prop_generator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public final class PropGenerator {

    private final Properties mProperties;

    public PropGenerator() {
        mProperties = new Properties();
    }

    public void add(String key, String value) {
        mProperties.put(key, value);
    }

    protected void delete(String key) {
        mProperties.remove(key);
    }

    protected void show() {
        mProperties.list(System.out);
    }

    protected void generate(String fileName, String comments, boolean toXml) {

        if (fileName != null) {
            try (FileOutputStream fos = new FileOutputStream(fileName);
                    PrintWriter pw = new PrintWriter(fos)) {
                if (toXml) {
                    mProperties.storeToXML(fos, comments);
                } else {
                    mProperties.store(fos, comments);
                }
                // mProperties.store(System.out, "For debugging");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}