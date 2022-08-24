package truman.java.util.abs_selection;

public final class Console extends AbstractSelection {

    private static final String LF = System.lineSeparator();
    private static final String MENU =
            LF +
            "==============================" + LF +
            "           [ Menu ]           " + LF +
            "==============================" + LF +
            LF +
            " (1) Pizza" + LF +
            " (2) Hotdog" + LF +
            " (3) Hamburger" + LF +
            " (4) Coke" + LF +
            " (5) Finish" + LF
            ;

    private static final String PROMPT = " : "; // Anything cool like " >> "

    public Console() {
        super(MENU, PROMPT);
    }

    public void open() {
        clear();
        loop();
    }

    @Override
    protected int getDefaultInt() {
        return 0;
    }

    @Override
    protected void onSelected(int selection) {
        clear();

        switch (selection) {
            case 1:
                System.out.println("Pizza selected");
                break;
            case 2:
                System.out.println("Hotdog selected");
                break;
            case 3:
                System.out.println("Hamburger selected");
                break;
            case 4:
                System.out.println("Coke selected");
                break;
            case 5:
                escape("Catch you later!");
                break;
            default:
                System.out.println("Not in menu... " + selection);
                break;
        }
    }
}