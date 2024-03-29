package truman.java.demo.prop_generator;

/**
 * This program is to provide convenience to generate java-property file based
 * on java.util.Properties which is Collection-like API.
 * 
 * @version 0.3.2
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public class Main {

    public static void main(String[] args) {
        new MainConsole(new PropertyManager()).open();
    }
}
