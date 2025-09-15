package truman.java.demo.ints_parser;

import truman.java.demo.ints_parser.core.Ints;
import truman.java.demo.ints_parser.core.IntsPrinter;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

public class Cli {

    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;
    private final Void nope = null;
    private Ints single;
    private Ints left;
    private Ints right;

    private String getCommand() {
        out.print("Command : ");
        String line = scanner.nextLine();
        return line.isBlank() ? "" : line.trim().toLowerCase();
    }

    private <R> R withInt(String prompt, Function<Integer, R> func) {
        out.print(prompt + " : ");
        int i = 0;
        try {
            i = scanner.nextInt();
        } catch (Exception ignored) {
        } finally {
            scanner.nextLine();
        }
        return func.apply(i);
    }

    private <R> R withLine(String prompt, Function<String, R> func) {
        out.print(prompt + " : ");
        String l = scanner.nextLine();
        return func.apply(l);
    }

    public void process() {
        do {
            switch (getCommand()) {
                case "random":
                    Ints ints = withInt("Num", num -> Ints.parse(Utils.genRandomInts(num)));
                    out.println(ints.toString());
                    break;
                case "show":
                    if (single == null) {
                        out.println("Please set a single first...");
                    } else {
                        out.println(single.toString("," + System.lineSeparator()));
                        out.println("Size of the single = " + single.size() + ")");
                    }
                    break;
                case "single":
                    single = withLine("Single", line -> Ints.parse(line));
                    out.println("Singe Ints applied(size = " + single.size() + ")");
                    break;
                case "search":
                    withInt("Value", value -> {
                        if (single == null) {
                            out.println("Please set a single first...");
                        } else if (single.contains(value)) {
                            out.println(value + " found in the single");
                        } else {
                            out.println(value + " not found in the single...");
                        }
                        return nope;
                    });
                    break;
                case "left":
                    left = withLine("Left", line -> Ints.parse(line));
                    out.println("Left Ints applied(size = " + left.size() + ")");
                    break;
                case "right":
                    right = withLine("Right", line -> Ints.parse(line));
                    out.println("Right Ints applied(size = " + right.size() + ")");
                    break;
                case "lfile":
                    left = withLine("Left File", line -> Ints.parse(line, true));
                    out.println("Left Ints applied(size = " + left.size() + ")");
                    break;
                case "rfile":
                    right = withLine("Right File", line -> Ints.parse(line, true));
                    out.println("Right Ints applied(size = " + right.size() + ")");
                    break;
                case "gen":
                    withInt("Num", num -> {
                        if (num < 1) {
                            return nope;
                        }
                        int dupNum = Utils.genRandomInt(1, num);
                        Set<Integer> dup = Utils.genRandomInts(dupNum);
                        left = Ints.parse(Utils.genRandomInts(num, dup));
                        right = Ints.parse(Utils.genRandomInts(num, dup));
                        out.println("Left Ints applied(size  = " + left.size() + ")");
                        out.println("Right Ints applied(size = " + right.size() + ")");
                        out.println("Duplicated count : " + dupNum);

                        return nope;
                    });
                    break;
                case "comp":
                    IntsPrinter.printComp(left, right);
                    break;
                case "agg":
                    IntsPrinter.printAgg(left, right);
                    break;
                case "tos":
                    single = Ints.aggregate(left, right);
                    out.println("Singe Ints applied(size = " + single.size() + ")");
                    break;
                case "exit":
                    out.println("Good bye!");
                    return;
                case "":
                    break;
                default:
                    out.println("Invalid command...");
                    break;
            }
        } while (true);
    }
}
