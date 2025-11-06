package truman.java.demo.tb_data_accum.core;

import static truman.java.demo.tb_data_accum.core.DataAccumulator.Util.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataAccumulator<T extends AccumulableData> {

    private static final long DEFAULT_BACK_OFF_TIME = 3000L;
    private static final long DEFAULT_INTERVAL_TIME = 1000L;

    private final long intervalTime;
    private final long backOffTime;
    private final Object gLock = new Object();
    private final NavigableMap<Integer, DataSnapshot<T>> map;

    private volatile long baseTime;
    private volatile boolean isRunning;
    private volatile DataConsumer<T> dataConsumer;

    private ScheduledExecutorService scheduler;
    private DataFlusher<NavigableMap<Integer, DataSnapshot<T>>> dataFlusher;
    private final DataSink<NavigableMap<Integer, DataSnapshot<T>>> dataSink = intervalData -> {
        var dc = getDataConsumer();
        if (dc != null) {
            intervalData.forEach((idx, ds) -> {
                ds.take().forEach(data -> {
                    dc.consume(idx, data);
                });
            });
        }
    };

    private static <T extends AccumulableData> NavigableMap<Integer, DataSnapshot<T>> genMap(
            SortedMap<Integer, DataSnapshot<T>> baseMap) {
        return (baseMap != null) ? new TreeMap<>(baseMap) : new TreeMap<>();
    }

    public DataAccumulator() {
        this.backOffTime = DEFAULT_BACK_OFF_TIME;
        this.intervalTime = DEFAULT_INTERVAL_TIME;
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
                dataFlusher.destory();
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
        long timestamp = data.getTimestamp();
        updateBaseTime(timestamp);

        int idx = timeToIndex(timestamp, getBaseTime());
        if (!checkIdx(idx)) {
            return;
        }
        synchronized (gLock) {
            var dataSnapshot = getDataSnapshotLocked(idx);
            dataSnapshot.add(data);
        }
    }

    private void updateBaseTime(long timestamp) {
        if (!checkBaseTime()) {
            baseTime = timestamp;
        }
    }

    private boolean checkBaseTime() {
        return baseTime > 0L;
    }

    private DataSnapshot<T> getDataSnapshotLocked(int idx) {
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
            if (hasDataLocked()) {
                NavigableMap<Integer, DataSnapshot<T>> m = detachHead(currentIdx);
                flush(m);
            }
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

    private NavigableMap<Integer, DataSnapshot<T>> detachHead(int bound) {
        synchronized (gLock) {
            var headView = map.headMap(bound, false);
            var copiedView = genMap(headView);
            headView.clear();
            return copiedView;
        }
    }

    private void flush(NavigableMap<Integer, DataSnapshot<T>> detached) {
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
