package truman.java.demo.tib_data_accum.core;

public interface Accumulable {
    Accumulable accumulate(Accumulable another);
}
