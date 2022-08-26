package truman.java.demo.prop_generator;

public final class Console extends AbstractSelection {

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
            " (4) Load" + LF +
            " (5) Finish with Saving" + LF +
            " (6) Finish without Saving" + LF
            ;
    private static final String PROMPT = " : ";

    private final PropertyManager mPropertyManager;

    public Console(PropertyManager propertyManager) {
        super(MENU, PROMPT);
        mPropertyManager = propertyManager;
    }

    public void open() {
        clear();
        loop();
    }

    private void onAdd() {
        entitle("I need [ KEY and VALUE ] as a pair");
        mPropertyManager.add(
                getString("Please Enter Key : "),
                getString("Please Enter Value : ")
                );
    }

    private void onDelete() {
        entitle("I need [ KEY ] for deletion");
        mPropertyManager.delete(
                getString("Please Enter Key : ")
                );
    }

    private void onShow() {
        mPropertyManager.show();
    }

    private void onLoad() {
        entitle("I need [ FILE NAME ] for loading");
        mPropertyManager.load(
                getString("Please Enter File Name : "),
                getBoolean("Is the file in XML format? [Y/n] ")
                );
    }

    private void onFinish(boolean saving) {
        if (saving) {
            entitle("I need [ FILE NAME ] for saving");
            mPropertyManager.save(
                    getString("Please Enter File Name : "),
                    getString("Any comments? (empty for no comment) "),
                    getBoolean("Do you want to save in XML format? [Y/n] ")
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
                onLoad();
                break;
            case 5:
                onFinish(true);
                break;
            case 6:
                onFinish(false);
                break;
            default:
                entitle("Not supported menu... " + selection);
                break;
        }
    }
}