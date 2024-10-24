package truman.java.util.command_chain;

import java.util.concurrent.Callable;

public abstract class SimpleCommand implements Command, Callable<Boolean> {

    @Override
    public void rollback() {
    }

    @Override
    public final Boolean call() {
        boolean result = execute();
        if (!result) {
            rollback();
        }
        return result;
    }
}
