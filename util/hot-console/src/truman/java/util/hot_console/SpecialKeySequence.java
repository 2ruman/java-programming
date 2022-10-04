package truman.java.util.hot_console;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
*
* This class is a container for a sequence of special key, so it would be
* helpful when handling the input sequence and determining if the input sequence
* is for one of the special keys that are defined beforehand and stored in tree
* data structure.
*
* @version 0.1.0
* @author Truman Kim (truman.t.kim@gmail.com)
*
*/

public class SpecialKeySequence {

    public static final int MAX_SEQ_LEN = 3;

    public static final SpecialKeySequence UP_ARROW_KEY = new SpecialKeySequence("Up", new char[] {'[', 'A'});
    public static final SpecialKeySequence DOWN_ARROW_KEY = new SpecialKeySequence("Down", new char[] {'[', 'B'});
    public static final SpecialKeySequence RIGHT_ARROW_KEY = new SpecialKeySequence("Right", new char[] {'[', 'C'});
    public static final SpecialKeySequence LEFT_ARROW_KEY = new SpecialKeySequence("Left", new char[] {'[', 'D'});
    public static final SpecialKeySequence END_KEY = new SpecialKeySequence("End", new char[] {'[', 'F'});
    public static final SpecialKeySequence HOME_KEY = new SpecialKeySequence("Home", new char[] {'[', 'H'});
    public static final SpecialKeySequence INSERT_KEY = new SpecialKeySequence("Insert", new char[] {'[', '2', '~'});
    public static final SpecialKeySequence DEL_KEY = new SpecialKeySequence("Del", new char[] {'[', '3', '~'});
    public static final SpecialKeySequence PAGE_UP_KEY = new SpecialKeySequence("PageUp", new char[] {'[', '5', '~'});
    public static final SpecialKeySequence PAGE_DOWN_KEY = new SpecialKeySequence("PageDown", new char[] {'[', '6', '~'});

    public static final Comparator<SpecialKeySequence> comparator = new Comparator<SpecialKeySequence>() {

        @Override
        public int compare(SpecialKeySequence seq1, SpecialKeySequence seq2) {
            char[] cs1 = seq1.get();
            char[] cs2 = seq2.get();

            if (cs1 == cs2) {
                return 0;
            }

            for (int i = 0; i < Math.min(cs1.length, cs2.length); i++) {
                if (cs1[i] != cs2[i]) {
                    return cs1[i] - cs2[i];
                }
            }
            return cs1.length - cs2.length;
        }
    };

    private static final Set<SpecialKeySequence> SEQ_SET = new TreeSet<>(comparator);
    static {
        SEQ_SET.add(UP_ARROW_KEY);
        SEQ_SET.add(DOWN_ARROW_KEY);
        SEQ_SET.add(RIGHT_ARROW_KEY);
        SEQ_SET.add(LEFT_ARROW_KEY);
        SEQ_SET.add(END_KEY);
        SEQ_SET.add(HOME_KEY);
        SEQ_SET.add(INSERT_KEY);
        SEQ_SET.add(DEL_KEY);
        SEQ_SET.add(PAGE_UP_KEY);
        SEQ_SET.add(PAGE_DOWN_KEY);
    }

    public static boolean isKnown(SpecialKeySequence seq) {
        return SEQ_SET.contains(seq);
    }

    public static SpecialKeySequence which(SpecialKeySequence seq,
                                                SpecialKeySequence defaultSeq) {
        return SEQ_SET.stream().filter(s -> {
            return comparator.compare(s, seq) == 0;
        }).findFirst().orElse(defaultSeq);
    }

    private char[] sequence = new char[MAX_SEQ_LEN];
    private int pos;
    private String alias;

    public SpecialKeySequence() {
        this("", new char[0]);
    }

    public SpecialKeySequence(char[] sequence) {
        this("", sequence);
    }

    public SpecialKeySequence(String alias, char[] sequence) {
        this.alias = alias;
        for (int i = 0;
                i < Math.min(this.sequence.length, sequence.length); i++) {
            this.sequence[i] = sequence[i];
        }
    }

    public char[] get() {
        return this.sequence;
    }

    public String getAlias() {
        return this.alias;
    }

    public SpecialKeySequence setCh(char c) {
        if (!isFull()) {
            sequence[pos++] = c;
        }
        return this;
    }

    public boolean isFull() {
        return (pos >= MAX_SEQ_LEN);
    }
}
