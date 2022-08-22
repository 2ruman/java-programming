package truman.java.demo.prop_generator;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

public final class PropGenerator implements Closeable {

    private Properties mProperties;
    private Input mInput;
    private Menu mMenu;

    public PropGenerator() {
        mProperties = new Properties();
        mInput = new Input();
        mMenu = new Menu(mInput);
    }

    public void generate() {
        mMenu.open();
    }

    private class Menu implements FiveMenu {

        final String LF = System.lineSeparator();
        final String MENU = LF +
                "==============================" + LF +
                "           [ Menu ]           " + LF +
                "==============================" + LF + LF +
                " (1) Add" + LF +
                " (2) Delete" + LF +
                " (3) Show" + LF +
                " (4) Finish with Saving" + LF +
                " (5) Finish without Saving" + LF
                ;

        final Input input;
        int selected;
        boolean loopOn;

        Menu(Input input) {
            this.input = input;
        }

        void open() {
            input.clear();

            loopOn = true;
            while (loopOn) {
                select();
            }
        }

        void select() {
            input.foreshow(MENU);
            selected = input.getInt(" : ");
            onSelected(selected);
        }

        void close() {
            loopOn = false;
        }

        @Override
        public void onMenu_1() {
            onAdd();
        }

        @Override
        public void onMenu_2() {
            onDelete();
        }

        @Override
        public void onMenu_3() {
            onShow();
        }

        @Override
        public void onMenu_4() {
            onFinish(true);
        }

        @Override
        public void onMenu_5() {
            onFinish(false);
        }

        @Override
        public void onMenu_X() {
            mInput.clear();
            mInput.foreshow("Not supported menu... " + selected);
        }
    };

    private void onAdd() {
        mInput.clear();
        mInput.foreshow("I need [ KEY and VALUE ] as a pair");
        mProperties.put(
                mInput.getString("Please Enter Key : "),
                mInput.getString("Please Enter Value : ")
                );
    }

    private void onDelete() {
        mInput.clear();
        mInput.foreshow("I need [ KEY ] for deletion");
        mProperties.remove(
                mInput.getString("Please Enter Key : ")
                );
    }

    private void onShow() {
        mInput.clear();
        mProperties.list(System.out);
    }

    private void onFinish(boolean saving) {

        if (saving) {
            mInput.clear();
            mInput.foreshow("I need [ FILE NAME ] for saving");
            String fileName = mInput.getString("Please Enter File Name : ");
            String comments = mInput.getString("Any comments? (empty for no comment) ");
            boolean toXml = mInput.getBoolean("Do you want to save in XML form? (y/n) ");

            try (FileOutputStream fos = new FileOutputStream(fileName);
                    PrintWriter pw = new PrintWriter(fos)) {
                if (toXml) {
                    mProperties.storeToXML(fos, comments);
                } else {
                    mProperties.store(fos, comments);
                }
                // mProperties.store(System.out, "For debugging");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMenu.close();
    }

    @Override
    public void close() throws IOException {
        mInput.foreshow("Bye bye!");
        mInput.close();
    }

    private class Input {
        Scanner scanner;
        Input() {
            scanner = new Scanner(System.in);
        }

        int getInt(String prompt) {
            System.out.print(prompt);
            int val = scanner.nextInt(); scanner.nextLine();
            return val;
        }

        boolean getBoolean(String prompt) {
            System.out.print(prompt);
            String val = scanner.nextLine();
            return (val.startsWith("Y") || val.startsWith("y"));
        }

        String getString(String prompt) {
            System.out.print(prompt);
            return scanner.nextLine();
        }

        void foreshow(String message) {
            System.out.println();
            System.out.println(message);
        }

        void clear() {
            System.out.print("\033[H\033[2J");  
            System.out.flush();  
        }

        void close() {
            scanner.close();
        }
    }
}
