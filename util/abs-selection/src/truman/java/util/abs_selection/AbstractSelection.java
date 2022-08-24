package truman.java.util.abs_selection;

import java.util.Scanner;

/**
 * This abstract class is to provide convenience to compose a menu that users
 * can select on the screen for the desired function.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public abstract class AbstractSelection {

    private final String mMenu;
    private final String mPrompt;
    private final Scanner mScanner;

    private boolean mLoop;

    public AbstractSelection(String menu, String prompt) {
        if (menu == null || prompt == null) {
            throw new IllegalArgumentException();
        }
        mMenu = menu;
        mPrompt = prompt;
        mScanner = new Scanner(System.in);
    }

    private int getInt() {
        int ret;
        try {
            ret = Integer.parseInt(mScanner.nextLine());
        } catch (NumberFormatException e) {
            ret = getDefaultInt();
        }
        return ret;
    }

    protected void select() {
        System.out.print(mMenu);
        System.out.print(mPrompt);

        onSelected(getInt());
    }

    protected void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected abstract int getDefaultInt();
    protected abstract void onSelected(int selection);

    public void loop() {
        mLoop = true;
        while (mLoop) {
            select();
        }
    }

    public void escape() {
        escape(null);
    }

    public void escape(String lastWord) {
        if (lastWord != null) {
            System.out.println(lastWord);
        }
        mLoop = false;
    }
}