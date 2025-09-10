package truman.java.demo.ints_parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import truman.java.demo.ints_parser.core.Ints;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntsTest {

    @ParameterizedTest(name = "[{index}] input={0} expected={1}")
    @CsvSource(textBlock = """
            '      234567, 123456     ,      3456677   , ', '123456, 234567, 3456677'
            '      234567, 123456     ,  \r \r    3456677 \r  \r\n, 4324\n56645', '4324, 56645, 123456, 234567, 3456677'
            '      234567, 123456     ,  \n\r    3456677 \r  \r\n, 5664590\n4324312', '123456, 234567, 3456677, 4324312, 5664590'
            """)
    void test(String input, String expected) {
        String actual = Ints.parse(input).toString();
        assertEquals(expected, actual);
    }
}
