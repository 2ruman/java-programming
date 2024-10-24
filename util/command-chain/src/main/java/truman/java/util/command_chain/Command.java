package truman.java.util.command_chain;

public interface Command {
    boolean execute();
    void rollback();
}