package truman.java.demo.prop_generator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try (PropGenerator generator = new PropGenerator()) {
            generator.generate();
        } catch (IOException e) { }
    }
}
