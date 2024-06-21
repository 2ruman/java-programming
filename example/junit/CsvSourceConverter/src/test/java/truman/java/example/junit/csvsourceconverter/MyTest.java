package truman.java.example.junit.csvsourceconverter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTest {

    @ParameterizedTest
    @CsvSource({
            "'1, 2, 3, 4, 5, 6, 7, 8, 9, 10', '[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]'",
            "'5, 4, 3, 2, 1, 0, -1, -2, -3, -4', '[5, 4, 3, 2, 1, 0, -1, -2, -3, -4]'",
    })
    void testIntArrayConversion(@ConvertWith(IntArrayConverter.class) int[] sequence, String expected) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i : sequence) {
            sj.add(i + "");
        }
        assertEquals(expected, sj.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "A|B|C|D|E|F, 1",
            "B|C|D|E|F, 2",
            "C|D|E|F, 3",
            "D|E|F, 4",
    })
    void testEnumListConversion(@ConvertWith(MyEnumListConverter.class) List<MyEnum> sequence, int expected) {
        for (MyEnum e : sequence) {
            System.out.println(String.format("MyEnum.%s(%d)", e.name(), e.getCode()));
        }
        int firstCode = sequence.stream().findFirst().map(MyEnum::getCode).orElse(-1);
        assertEquals(expected, firstCode);
    }

    @ParameterizedTest
    @CsvSource({
            "'Hello|,| world|!'",
            "A|B|CD|E|FG",
    })
    void testStringArrayConversion(@ConvertWith(MyStringArrayConverter.class) String[] sequence) {
        for (String e : sequence) {
            System.out.print(e);
        }
        System.out.println();
    }
}
