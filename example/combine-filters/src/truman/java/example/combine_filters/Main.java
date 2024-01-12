package truman.java.example.combine_filters;

import java.util.function.Predicate;

import static truman.java.example.combine_filters.FilterUtils.*;

public class Main {

    public class MyData {
        int data1; int data2; int data3;
        MyData(int data1, int data2, int data3) {
            this.data1 = data1; this.data2 = data2; this.data3 = data3;
        }
    }

    /*
     * All the conditions are for the test by the author
     * - Once 0x01 is set, 10 is to be minimum value for data1
     * - Once 0x02 is set, 11 is to be minimum value for data2
     * - Once 0x04 is set, 12 is to be minimum value for data3
     */
    private Predicate<MyData> andFiltersByFlags(int flags) {
        Predicate<MyData> ret = null;
        if ((flags & 0x01) > 0) {
            ret = andFilters(ret, (d) -> (d.data1 >= 10)); 
        }
        if ((flags & 0x02) > 0) {
            ret = andFilters(ret, (d) -> (d.data2 >= 11));
        }
        if ((flags & 0x04) > 0) {
            ret = andFilters(ret, (d) -> (d.data3 >= 12));
        }
        return ret;
    }

    private Predicate<MyData> orFiltersByFlags(int flags) {
        Predicate<MyData> ret = null;
        if ((flags & 0x01) > 0) {
            ret = orFilters(ret, (d) -> (d.data1 >= 10));
        }
        if ((flags & 0x02) > 0) {
            ret = orFilters(ret, (d) -> (d.data2 >= 11));
        }
        if ((flags & 0x04) > 0) {
            ret = orFilters(ret, (d) -> (d.data3 >= 12));
        }
        return ret;
    }

    private void test() {

        Predicate<MyData> andedFilter = andFiltersByFlags(0x01|0x02|0x04);
        Predicate<MyData> oredFilter = orFiltersByFlags(0x01|0x02|0x04);
        MyData testData;

        testData = new MyData(10, 11, 12); // (TRUE, TRUE, TRUE)
        if (andedFilter.test(testData)) {
            System.out.println("All TRUE!");
        }

        testData = new MyData(10, 11, 11);  // (TRUE, TRUE, FALSE)
        if (!andedFilter.test(testData)) {
            System.out.println("One FALSE at least!");
        }

        testData = new MyData(1, 2, 12); // (FALSE, FALSE, TRUE)
        if (oredFilter.test(testData)) {
            System.out.println("One TRUE at least!");
        }

        testData = new MyData(9, 10, 11); // (FALSE, FALSE, FALSE)
        if (!oredFilter.test(testData)) {
            System.out.println("All FALSE!");
        }
    }

    public static void main(String[] args) {
        new Main().test();
    }
}
