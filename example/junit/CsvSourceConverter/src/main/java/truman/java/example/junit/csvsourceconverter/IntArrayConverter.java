package truman.java.example.junit.csvsourceconverter;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.Arrays;

public class IntArrayConverter extends SimpleArgumentConverter {

    public static final String DELIMITER = ",";

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        try {
            if (source instanceof String && int[].class.isAssignableFrom(targetType)) {
                return Arrays.stream(((String) source).split("\\s*"+DELIMITER+"\\s*"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ArgumentConversionException("Illegal conversion from " + source.getClass() + " to " + targetType);
    }
}