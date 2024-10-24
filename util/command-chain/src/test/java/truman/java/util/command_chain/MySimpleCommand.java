package truman.java.util.command_chain;

public class MySimpleCommand extends SimpleCommand {

    private final int id;
    private final String info;

    public MySimpleCommand(int id, String info) {
        this.id = id;
        this.info = info;
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

    @Override
    public String toString() {
        return "MySimpleCommand(" + id + ") - " + info;
    }
}
