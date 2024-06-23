import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import truman.java.demo.PrimeNumberGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeNumberGeneratorTest {

    @ParameterizedTest
    @CsvSource({
        "50 , '[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47]'"
    })
    void testPrimeNumberGeneratorGenerate(int targetNum, String expected) {
        int[] primes = PrimeNumberGenerator.generate(targetNum);
        String actual = Arrays.toString(primes);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47})
    void testPrimeNumberGeneratorIsPrimeNumberWithPrimes(int targetNum) {
        assertTrue(PrimeNumberGenerator.isPrimeNumber(targetNum));
    }

    @Test
    void testPrimeNumberGeneratorIsPrimeNumberWithComposites() {
        assertFalse(PrimeNumberGenerator.isPrimeNumber(4));
        assertFalse(PrimeNumberGenerator.isPrimeNumber(10));
        assertFalse(PrimeNumberGenerator.isPrimeNumber(50));
        assertFalse(PrimeNumberGenerator.isPrimeNumber(100));
    }
}
