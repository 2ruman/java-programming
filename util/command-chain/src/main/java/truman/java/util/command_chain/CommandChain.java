package truman.java.util.command_chain;

import java.util.concurrent.Callable;

public class CommandChain implements Callable<Boolean> {

    private final Command mCommand;
    private CommandChain mNext;

    public CommandChain(Command command) {
        this(command, null);
    }

    public CommandChain(Command command, CommandChain next) {
        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }
        mCommand = command;
        mNext = next;
    }

    public static CommandChain of(Command command) {
        return new CommandChain(command);
    }

    @Override
    public Boolean call() {
        return execute();
    }

    public boolean execute() {
        boolean result = mCommand.execute();
        if (result) {
            result = executeNext();
            if (!result) {
                mCommand.rollback();
            }
        } else {
            mCommand.rollback();
        }
        return result;
    }

    private boolean executeNext() {
        if (hasNext()) {
            return next().execute();
        }
        return true;
    }

    public boolean hasNext() {
        return (mNext != null);
    }

    public CommandChain next() {
        return mNext;
    }

    public void setNext(CommandChain next) {
        mNext = next;
    }

    public CommandChain tail() {
        CommandChain tail = this;
        while (tail.hasNext()) {
            tail = tail.next();
        }
        return tail;
    }

    public int length() {
        int length = 1;
        CommandChain tail = this;
        while (tail.hasNext()) {
            tail = tail.next();
            length++;
        }
        return length;
    }

    public CommandChain followedBy(Command command) {
        CommandChain tail = tail();
        tail.setNext(new CommandChain(command));
        return this;
    }
}