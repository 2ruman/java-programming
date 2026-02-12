package truman.java.demo.tb_data_accum.core;

import static truman.java.demo.tb_data_accum.core.DataAccumulator.Util.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataAccumulator<U, T extends AccumulableData<U>> {

    private static final long DEFAULT_BACK_OFF_TIME = 3000L;
    private static final long DEFAULT_INTERVAL_TIME = 1000L;

    private final boolean skipEmpty;
    private final long intervalTime;
    private final long backOffTime;
    private final Object gLock = new Object();
    private final NavigableMap<Integer, DataSnapshot<U, T>> map;

    private volatile long baseTime;
    private volatile boolean isRunning;
    private volatile DataConsumer<T> dataConsumer;
    private volatile DataSnapshotConsumer<U, T> dataSnapshotConsumer;

    private ScheduledExecutorService scheduler;
    private DataFlusher<NavigableMap<Integer, DataSnapshot<U, T>>> dataFlusher;
    private final DataSink<NavigableMap<Integer, DataSnapshot<U, T>>> dataSink = intervalData -> {
        var dc = getDataConsumer();
        var dsc = getDataSnapshotConsumer();
        if (dc == null && dsc == null) {
            return;
        }
        intervalData.forEach((i, ds) -> {
            if (dsc != null) {
                dsc.consume(i, ds);
            }
            if (dc != null) {
                ds.take().forEach(d -> {
                    dc.consume(i, d);
                });
            }
        });
    };

    private static <U, T extends AccumulableData<U>> NavigableMap<Integer, DataSnapshot<U, T>> genMap(
            SortedMap<Integer, DataSnapshot<U, T>> baseMap) {
        return (baseMap != null) ? new TreeMap<>(baseMap) : new TreeMap<>();
    }

    public DataAccumulator() {
        this(DEFAULT_BACK_OFF_TIME, DEFAULT_INTERVAL_TIME, true);
    }

    public DataAccumulator(long backOffTime, long intervalTime, boolean skipEmpty) {
        if (backOffTime <= 0 || intervalTime <= 0) {
            throw new IllegalArgumentException();
        }
        this.skipEmpty = skipEmpty;
        this.backOffTime = backOffTime;
        this.intervalTime = intervalTime;
        this.map = genMap(null);
    }

    public void start() {
        synchronized (gLock) {
            if (dataFlusher == null) {
                dataFlusher = new DataFlusher<>(dataSink);
                dataFlusher.start();
            }
            if (scheduler == null) {
                scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.scheduleAtFixedRate(
                        this::onTime, backOffTime + intervalTime, intervalTime, TimeUnit.MILLISECONDS);
            }
            isRunning = true;
        }
    }

    public synchronized void stop() {
        synchronized (gLock) {
            isRunning = false;
            if (dataFlusher != null) {
                dataFlusher.terminate();
                dataFlusher = null;
            }
            if (scheduler != null) {
                scheduler.shutdown();
                scheduler = null;
            }
        }
    }

    public void onData(T data) {
        if (!isRunning) {
            return;
        }
        if (data == null) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        updateBaseTime(currentTime);

        int idx = timeToIndex(currentTime, getBaseTime());
        if (!checkIdx(idx)) {
            return;
        }
        synchronized (gLock) {
            var dataSnapshot = getDataSnapshotLocked(idx);
            dataSnapshot.add(data);
        }
    }

    private void updateBaseTime(long currentTime) {
        if (!checkBaseTime()) {
            baseTime = currentTime;
        }
    }

    private boolean checkBaseTime() {
        return baseTime > 0L;
    }

    private DataSnapshot<U, T> getDataSnapshotLocked(int idx) {
        if (!map.containsKey(idx)) {
            map.put(idx, new DataSnapshot<>());
        }
        return map.get(idx);
    }

    private void onTime() {
        if (!checkBaseTime()) {
            return;
        }
        int currentIdx = currentTimeToIdx();
        if (!checkIdx(currentIdx)) {
            return;
        }
        synchronized (gLock) {
            if (skipEmpty && !hasDataLocked()) {
                return;
            }
            NavigableMap<Integer, DataSnapshot<U, T>> m = detachHead(currentIdx);
            flush(m);
        }
    }

    private int currentTimeToIdx() {
        return timeToIndex(System.currentTimeMillis(), getBaseTime());
    }

    public long getBaseTime() {
        return baseTime;
    }

    private boolean hasDataLocked() {
        return !map.isEmpty();
    }

    private NavigableMap<Integer, DataSnapshot<U, T>> detachHead(int bound) {
        synchronized (gLock) {
            var headView = map.headMap(bound, false);
            var copiedView = genMap(headView);
            headView.clear();
            return copiedView;
        }
    }

    private void flush(NavigableMap<Integer, DataSnapshot<U, T>> detached) {
        dataFlusher.flush(detached);
    }

    public void registerDataConsumer(DataConsumer<T> dataConsumer) {
        if (dataConsumer == null) {
            return;
        }
        this.dataConsumer = dataConsumer;
    }

    private DataConsumer<T> getDataConsumer() {
        return dataConsumer;
    }

    public void registerDataSnapshotConsumer(DataSnapshotConsumer<U, T> dataSnapshotConsumer) {
        if (dataSnapshotConsumer == null) {
            return;
        }
        this.dataSnapshotConsumer = dataSnapshotConsumer;
    }

    private DataSnapshotConsumer<U, T> getDataSnapshotConsumer() {
        return dataSnapshotConsumer;
    }

    static class Util {
        static int timeToIndex(long time, long bound) {
            long diff = msToSec(time - bound);
            if (diff >= Integer.MAX_VALUE || diff <= Integer.MIN_VALUE) {
                diff = -1;
            }
            return (int) diff;
        }

        static long msToSec(long ms) {
            return ms / 1000;
        }

        static boolean checkIdx(int idx) {
            return (idx >= 0);
        }

        private static final boolean DEBUG = false;
        static void debug(String msg) {
            if (DEBUG) System.out.println(msg);
        }
    }
}
