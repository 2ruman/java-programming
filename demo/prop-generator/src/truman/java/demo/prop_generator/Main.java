package truman.java.demo.prop_generator;

/**
 * This abstract class is to provide convenience to compose a menu that users
 * can select on the screen for the desired function.
 * 
 * @version 0.2.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public class Main {

    public static void main(String[] args) {
        new Console(new PropGenerator()).open();
    }
}
