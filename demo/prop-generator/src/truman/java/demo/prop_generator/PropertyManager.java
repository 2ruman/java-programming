package truman.java.demo.prop_generator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public final class PropertyManager {

    private final Properties mProperties;

    public PropertyManager() {
        mProperties = new Properties();
    }

    public void add(String key, String value) {
        mProperties.put(key, value);
    }

    public void delete(String key) {
        mProperties.remove(key);
    }

    public void show() {
        mProperties.list(System.out);
    }

    public void load(String fileName) {
        load(fileName, false);
    }

    public void load(String fileName, boolean fromXml) {

        if (fileName != null) {
            if (fromXml) {
                try (FileInputStream fis = new FileInputStream(fileName)) {
                    mProperties.loadFromXML(fis);
                } catch (IOException e) {
                    e.printStackTrace();
                };
            } else {
                try (FileReader fr = new FileReader(fileName)) {
                    mProperties.load(fr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void save(String fileName, String comments) {
        save(fileName, comments, false);
    }

    public void save(String fileName, String comments, boolean toXml) {

        if (fileName != null) {
            if (toXml) {
                try (FileOutputStream fos = new FileOutputStream(fileName)) {
                    mProperties.storeToXML(fos, comments);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try (FileWriter fw = new FileWriter(fileName)) {
                    mProperties.store(fw, comments);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // mProperties.store(System.out, "For debugging");
        }
    }
}