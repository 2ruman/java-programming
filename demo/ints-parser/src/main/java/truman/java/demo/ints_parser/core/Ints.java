package truman.java.demo.ints_parser.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ints {

    private static final boolean DEBUG = true;
    private static final String REGEX_GENERAL_SEP = "\\s*[,\\r\\n]\\s*";
    private static final String DEFAULT_PRF = "";
    private static final String DEFAULT_SUF = "";
    private static final String DEFAULT_DEL = ", ";

    private final Set<Integer> ints;

    private Ints(Set<Integer> ints) {
        this.ints = Collections.unmodifiableSet(ints);
    }

    public static Ints parse(String s) {
        return parse(s, false);
    }

    public static Ints parse(String s, boolean fromFile) {
        try {
            return new Ints(fromFile ?
                    parseFile(s) : parseChunk(s));
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
        }
        return empty();
    }

    private static Set<Integer> parseFile(String path) throws IOException {
        return parseStream(Files.lines(Path.of(path)));
    }

    private static Set<Integer> parseChunk(String chunk) {
        return parseStream(Arrays.stream(chunk.split(REGEX_GENERAL_SEP)));
    }

    private static Set<Integer> parseStream(Stream<String> s) {
        return s.map(String::trim)
                .map(Integer::valueOf)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static Ints empty() {
        return new Ints(Collections.emptySet());
    }

    public static Ints aggregate(Ints left, Ints right) {
        Set<Integer> aggregated = new TreeSet<>(left.get());
        aggregated.addAll(right.get());
        return new Ints(aggregated);
    }

    public Set<Integer> get() {
        return ints;
    }

    public void forEach(Consumer<Integer> action) {
        Objects.requireNonNull(action);
        ints.forEach(action);
    }

    public int size() {
        return ints.size();
    }

    public String toString() {
        return toString(DEFAULT_DEL, DEFAULT_PRF, DEFAULT_SUF);
    }

    public String toString(String delimiter) {
        return toString(delimiter, DEFAULT_PRF, DEFAULT_SUF);
    }

    public String toString(String delimiter, String prefix, String suffix) {
        delimiter = (delimiter == null) ? DEFAULT_DEL : delimiter;
        prefix = (prefix == null) ? DEFAULT_PRF : prefix;
        suffix = (suffix == null) ? DEFAULT_SUF : suffix;

        StringJoiner sj = new StringJoiner(delimiter, prefix, suffix);
        ints.forEach(i -> {
            sj.add(Integer.toString(i));
        });
        return sj.toString();
    }
}
