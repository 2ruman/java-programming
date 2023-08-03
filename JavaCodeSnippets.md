# Java Code Snippets

### Contents
+ Parsing
+ File
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
