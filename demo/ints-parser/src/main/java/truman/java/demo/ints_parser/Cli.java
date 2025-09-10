package truman.java.demo.ints_parser;

import truman.java.demo.ints_parser.core.Ints;
import truman.java.demo.ints_parser.core.IntsPrinter;

import java.io.PrintStream;
import java.util.Scanner;

public class Cli {

    private final PrintStream out = System.out;
    private Ints single;
    private Ints left;
    private Ints right;

    public void process() {
        Scanner scanner = new Scanner(System.in);
        String line;
        do {
            out.print("Command : ");
            line = scanner.nextLine();
            switch (line.toLowerCase()) {
                case "single":
                    out.print("Single : ");
                    single = Ints.parse(scanner.nextLine());
                    out.println("Singe Ints applied(size = " + single.size() + ")");
                    break;
                case "search":
                    out.print("Value : ");
                    try {
                        int value = scanner.nextInt();
                        if (single.get().contains(value)) {
                            out.println(value + " found in the single");
                        } else {
                            out.println(value + " not found...");
                        }
                    } catch (Exception ignored) {
                    }
                    break;
                case "left":
                    out.print("Left : ");
                    left = Ints.parse(scanner.nextLine());
                    out.println("Left Ints applied(size = " + left.size() + ")");
                    break;
                case "right":
                    out.print("Right : ");
                    right = Ints.parse(scanner.nextLine());
                    out.println("Right Ints applied(size = " + right.size() + ")");
                    break;
                case "lfile":
                    out.print("Left File : ");
                    left = Ints.parse(scanner.nextLine(), true);
                    out.println("Left Ints applied(size = " + left.size() + ")");
                    break;
                case "rfile":
                    out.print("Right File : ");
                    right = Ints.parse(scanner.nextLine(), true);
                    out.println("Right Ints applied(size = " + right.size() + ")");
                    break;
                case "comp":
                    IntsPrinter.printComp(left, right);
                    break;
                case "agg":
                    IntsPrinter.printAgg(left, right);
                    break;
                case "exit":
                    out.println("Good bye!");
                    return;
                default:
                    if (!line.isBlank())
                        out.println("Invalid command: " + line);
                    break;
            }
        } while (true);
    }
}
