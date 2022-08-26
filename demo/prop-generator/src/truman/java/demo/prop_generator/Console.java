package truman.java.demo.prop_generator;

public class Console extends AbstractSelection {

    private static final String LF = System.lineSeparator();
    private static final String MENU =
            LF +
            "==============================" + LF +
            "           [ Menu ]           " + LF +
            "==============================" + LF +
            LF +
            " (1) Add" + LF +
            " (2) Delete" + LF +
            " (3) Show" + LF +
            " (4) Finish with Saving" + LF +
            " (5) Finish without Saving" + LF
            ;
    private static final String PROMPT = " : ";

    private final PropGenerator mPropGenerator;

    public Console(PropGenerator propGenerator) {
        super(MENU, PROMPT);
        mPropGenerator = propGenerator;
    }

    public void open() {
        clear();
        loop();
    }

    private void onAdd() {
        entitle("I need [ KEY and VALUE ] as a pair");
        mPropGenerator.add(
                getString("Please Enter Key : "),
                getString("Please Enter Value : ")
                );
    }

    private void onDelete() {
        entitle("I need [ KEY ] for deletion");
        mPropGenerator.delete(
                getString("Please Enter Key : ")
                );
    }

    private void onShow() {
        mPropGenerator.show();
    }

    private void onFinish(boolean saving) {
        if (saving) {
            entitle("I need [ FILE NAME ] for saving");
            mPropGenerator.generate(
                    getString("Please Enter File Name : "),
                    getString("Any comments? (empty for no comment) "),
                    getBoolean("Do you want to save in XML form? [Y/n] ")
                    );
        }
        escape("Bye bye!");
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
                onAdd();
                break;
            case 2:
                onDelete();
                break;
            case 3:
                onShow();
                break;
            case 4:
                onFinish(true);
                break;
            case 5:
                onFinish(false);
                break;
            default:
                entitle("Not supported menu... " + selection);
                break;
        }
    }
}