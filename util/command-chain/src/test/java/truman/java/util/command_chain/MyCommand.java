package truman.java.util.command_chain;

public class MyCommand implements Command {

    private final int id;
    private final String info;

    public MyCommand(int id, String info) {
        this.id = id;
        this.info = info;
    }

    @Override
    public String toString() {
        return "MyCommand(" + id + ") - " + info;
    }

    @Override
    public boolean execute() {
        System.out.println("execute ::" + this);
        return true;
    }

    @Override
    public void rollback() {
        System.out.println("rollback ::" + this);
    }
}