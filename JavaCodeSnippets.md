# Java Code Snippets

### Contents
+ [Basics](#basics)
+ [Collection](#collection)
+ [String/Parsing](#string--parsing)
+ [File](#file)
+ [Process](#process)
+ [Lambda](#lambda)
<br>

## Basics

### Get Current Method Name

```java
/* Most simple */
String methodName = new Object() { }.getClass().getEnclosingMethod().getName();
String methodName = new Throwable().getStackTrace()[0].getMethodName();
String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

/* Considering the depth */
public static String getCallingMethodName() {
    return getStackedMethodNameSafe(2);
}

public static String getStackedMethodNameSafe(int n) {
    StackTraceElement[] e = new Throwable().getStackTrace();
    return (n >= 0 && n < e.length) ? e[n].getMethodName() : "";
}

/* For a function object like the lambda-expressed one */
public static String getMethodNameInFo() {
    return getStackedMethodNameSafe(4);
}
```

## Collection

### Get Key by Value in Map

```java
import java.util.Map;

/*
 * The map should be immutable and not include null value as a dictionary
 */
private static <K, V> K getKeyByValue(Map<K, V> map, Object value) {
    for (var entry : map.entrySet()) {
        if (entry.getValue().equals(value)) {
            return entry.getKey();
        }
    }
    return null;
}

```

### LRU Set(Cache)

```java
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
...
private static final int MAX_CACHE_SIZE = 5;
...
    private Set<String> lruCache= Collections.newSetFromMap(new LinkedHashMap<String, Boolean>() {
        private static final long serialVersionUID = 1L; // Default id for suppressing a warning

        protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
            return (size() > MAX_CACHE_SIZE);
        }
    });
...
        lruCache.add("1");
        lruCache.add("2");
        lruCache.add("3");
        lruCache.add("4");
        lruCache.add("5");
        lruCache.add("6");

        System.out.println("LRU Cache : " + lruCache); // Result --> lru : [2, 3, 4, 5, 6]
```

### Traverse Backwards through TreeSet

```java
public static int getPreviousVal(Set<Integer> orderedSet, int val, int defaultVal) {
    if (!(orderedSet instanceof TreeSet)) {
        throw new IllegalArgumentException("Argument not assignable to TreeSet");
    }
    if (orderedSet.isEmpty()) {
        return defaultVal;
    }
    TreeSet<Integer> navigableSet = (TreeSet<Integer>) orderedSet;
    Integer previousOne = navigableSet.lower(val);
    if (previousOne == null) {
        previousOne = navigableSet.last();
    }
    if (previousOne == null) {
        previousOne = defaultVal;
    }
    return previousOne;
}

void testTraverseBackwards() {
    TreeSet<Integer> treeSet = new TreeSet<>();
    treeSet.add(10);
    treeSet.add(7);
    treeSet.add(5);
    treeSet.add(3);
    treeSet.add(2);
    treeSet.add(1);

    for (int val = 5, i = 0; i < treeSet.size(); i++) {
        System.out.println(
                (val = getPreviousVal(treeSet, val, -1))
        );
    }
}
```
### Traverse Forwards through TreeSet

```java
public static int getNextVal(Set<Integer> orderedSet, int val, int defaultVal) {
    if (!(orderedSet instanceof TreeSet)) {
        throw new IllegalArgumentException("Argument not assignable to TreeSet");
    }
    if (orderedSet.isEmpty()) {
        return defaultVal;
    }
    TreeSet<Integer> navigableSet = (TreeSet<Integer>) orderedSet;
    Integer nextOne = navigableSet.higher(val);
    if (nextOne == null) {
        nextOne = navigableSet.first();
    }
    if (nextOne == null) {
        nextOne = defaultVal;
    }
    return nextOne;
}

void traverseForwards() {
    TreeSet<Integer> treeSet = new TreeSet<>();
    treeSet.add(10);
    treeSet.add(7);
    treeSet.add(5);
    treeSet.add(3);
    treeSet.add(2);
    treeSet.add(1);

    for (int val = 5, i = 0; i < treeSet.size(); i++) {
        System.out.println(
                (val = getNextVal(treeSet, val, -1))
        );
    }
}
```

## String / Parsing

### Return Null-safe String

```java
public static String nullSafe(String s) {
    return s != null ? s : "";
}
```
### String to Int Array / String to Integer List

```java
/* To int array with simplest input sequence */
String intSequence = "1,2,3,4,5,6,7,8,9,0";
int[] intArray = Arrays.stream(intSequence.split(","))
        .mapToInt(Integer::parseInt)
        .toArray();

/* To int array with whitespace-mixed input sequence */
String intSequence = "  1 , 2 , 3, 4 , 5  , 6, 7    , 8,9,0  ";
int[] intArray = Arrays.stream(intSequence.trim().split("\\s*,\\s*"))
        .mapToInt(Integer::parseInt)
        .toArray();

System.out.println(Arrays.toString(intArray));

/* To integer list with whitespace-mixed input sequence */
String intSequence = "  1 , 2 , 3, 4 , 5  , 6, 7    , 8,9,0  ";
List<Integer> intList = Arrays.stream(intSequence.trim().split("\\s*,\\s*"))
        .map(Integer::parseInt).collect(Collectors.toList());

System.out.println(intList);
```

### Parse File Extension
   
```java
public static String getExtension(String fileName) {
    return (fileName != null) ?
            fileName.substring(fileName.lastIndexOf(".") + 1) : null;
}
```

### Get Nth Record from String

```java
import java.util.StringTokenizer;

(...)

public static String getNthToken(String str, String del, int n) {
    String ret = "";
    if (str != null && del != null) {
        StringTokenizer tokenizer = new StringTokenizer(str, del);
        while (n > 0 && tokenizer.hasMoreTokens()) {
            ret = tokenizer.nextToken();
            n--;
        }
    }
    return (n == 0) ? ret : "";
}

public static String getNthToken(String str, String del, int n) {
    if (str == null || del == null || n < 1) {
        throw new IllegalArgumentException();
    }
    String ret = "";
    StringTokenizer tokenizer = new StringTokenizer(str, del);
    while (n > 0 && tokenizer.hasMoreTokens()) {
        ret = tokenizer.nextToken();
        n--;
    }
    return (n == 0) ? ret : "";
}

(...)

String str = "4.19.36";
String major = getNthToken(str, ".", 1);
String minor = getNthToken(str, ".", 2);
String revision = getNthToken(str, ".", 3);
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

## Lambda

### Combine Filters

```java
import java.util.function.Predicate;

/*
 * Nullable left-side hand and non-nullable right-side hand
 */
public static <T> Predicate<T> andFilters(Predicate<T> left, Predicate<T> right) {
    return (left == null) ? right : left.and(right);
}

public static <T> Predicate<T> orFilters(Predicate<T> left, Predicate<T> right) {
    return (left == null) ? right : left.or(right);
}

```
