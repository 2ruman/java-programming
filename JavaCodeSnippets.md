# Java Code Snippets

## Parsing

### Get File Extension
   
```java
public static String getExtension(String fileName) {
    return (fileName != null) ?
            fileName.substring(fileName.lastIndexOf(".") + 1) : null;
}
```
