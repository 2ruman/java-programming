# Java Code Snippets

### Contents
+ [Parsing](#parsing)
+ [File](#file)
+ [Process](#process)
<br>

## Parsing

### Get File Extension
   
```java
public static String getExtension(String fileName) {
    return (fileName != null) ?
            fileName.substring(fileName.lastIndexOf(".") + 1) : null;
}
```

## File

### Read One Line from a File

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public static String readOneLine(String filePath) {
    String ret = "";
    try (FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr)) {
        ret = br.readLine();
    } catch (IOException e) {
    }
    return (ret != null) ? ret : "";
}
```

### Get inode Number in Unix-like Environment

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

(...)

public static long getIno(String filePath) {
    long ret = -1;
    try {
        Path path = Paths.get(filePath);
        Long ino = (Long) Files.getAttribute(path, "unix:ino");
        ret = ino.longValue();
    } catch (RuntimeException | IOException e) {
        e.printStackTrace();
    }
    return ret;
}
```

## Process

### Get PID

```java
long pid = ProcessHandle.current().pid();
```
