package truman.java.demo.tb_data_accum.core;

import java.util.*;

public class DataSnapshot<T extends AccumulableData> implements Snapshot<T> {

    private volatile Map<Integer, T> snapshot = genMap();

    private Map<Integer, T> genMap() {
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
