package truman.java.demo.tb_data_accum.core;

import java.util.*;

public class DataSnapshot<U, T extends AccumulableData<U>> implements Snapshot<T> {

    private volatile Map<U, T> snapshot = genMap();

    private Map<U, T> genMap() {
        return Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void add(T another) {
        var data = snapshot.putIfAbsent(another.getUuid(), another);
        if (data != null) {
            data.accumulate(another);
        }
    }

    @Override
    public Collection<T> take() {
        var taken = snapshot;
        snapshot = genMap();
        return taken.values();
    }
}
