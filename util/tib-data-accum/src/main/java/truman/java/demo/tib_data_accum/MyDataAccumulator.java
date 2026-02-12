package truman.java.demo.tib_data_accum;

import truman.java.demo.tib_data_accum.core.DataAccumulator;

public class MyDataAccumulator extends DataAccumulator<MyUuid, MyData> {
    public MyDataAccumulator() {
    }

    public MyDataAccumulator(long intervalTime) {
        super(intervalTime);
    }
}
