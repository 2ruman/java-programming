package truman.java.demo.prop_generator;

public interface FiveMenu {

    void onMenu_1();
    void onMenu_2();
    void onMenu_3();
    void onMenu_4();
    void onMenu_5();
    void onMenu_X();

    default void onSelected(int selected) {
        switch (selected) {
        case 1:
            onMenu_1();
            break;
        case 2:
            onMenu_2();
            break;
        case 3:
            onMenu_3();
            break;
        case 4:
            onMenu_4();
            break;
        case 5:
            onMenu_5();
            break;
        default:
            onMenu_X();
            break;
        }
    }
}
