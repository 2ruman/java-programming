package truman.java.demo.tib_data_accum.core;

import java.util.Collection;

public interface Snapshot<T> {
    void add(T data);
    Collection<T> take();
}
