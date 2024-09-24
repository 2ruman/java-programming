package truman.java.util.command_chain;

import java.util.concurrent.Callable;

public abstract class Command implements Callable<Boolean> {
    public abstract boolean execute();
    public void rollback() {
    }

    @Override
    public Boolean call() {
        boolean result = execute();
        if (!result) {
            rollback();
        }
        return result;
    }
}