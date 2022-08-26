package truman.java.util.abs_selection;

import java.util.Scanner;

/**
 * This abstract class is to provide convenience to compose a menu that users
 * can select on the screen for the desired function.
 * 
 * @version 0.2.0
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

    protected void entitle(String title) {
        System.out.println();
        System.out.println(title);
    }

    protected boolean getBoolean() {
        return getBoolean(null);
    }

    protected boolean getBoolean(String prompt) {
        if (prompt != null) {
            System.out.print(prompt);
        }
        String val = mScanner.nextLine();
        return (val.startsWith("Y") || val.startsWith("y"));
    }

    protected abstract int getDefaultInt();

    protected int getInt() {
        int ret;
        try {
            ret = Integer.parseInt(mScanner.nextLine());
        } catch (NumberFormatException e) {
            ret = getDefaultInt();
        }
        return ret;
    }

    protected String getString() {
        return getString(null);
    }

    protected String getString(String prompt) {
        if (prompt != null) {
            System.out.print(prompt);
        }
        return mScanner.nextLine();
    }

    protected abstract void onSelected(int selection);

    protected void select() {
        System.out.print(mMenu);
        System.out.print(mPrompt);

        onSelected(getInt());
    }

    protected void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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