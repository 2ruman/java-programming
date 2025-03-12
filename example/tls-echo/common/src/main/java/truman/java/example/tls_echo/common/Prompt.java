package truman.java.example.tls_echo.common;

import truman.java.util.hot_console.HotConsole;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Prompt extends Thread {

    private static final HotConsole console;
    static {
        console = HotConsole.getInstance();
        console.on();
    }

    private final String prompt;
    private final Consumer<String> consumer;
    private final Runnable terminator;
    private final Predicate<String> termCondition;

    public static  Prompt create(String prompt, Consumer<String> consumer, Runnable terminator, Predicate<String> termCondition) {
        return new Prompt(prompt, consumer, terminator, termCondition);
    }

    public Prompt(String prompt, Consumer<String> consumer, Runnable terminator, Predicate<String> termCondition) {
        this.prompt = prompt;
        this.consumer = consumer;
        this.terminator = terminator;
        this.termCondition = termCondition;
        setDaemon(true);
    }

    @Override
    public void run() {
        while(true) {
            String input = console.getStr(prompt);
            if (termCondition.test(input)) {
                break;
            }
            consumer.accept(input);
        }
        terminator.run();
    }
}
