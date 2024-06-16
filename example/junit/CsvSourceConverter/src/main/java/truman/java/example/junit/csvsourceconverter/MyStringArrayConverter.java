package truman.java.example.junit.csvsourceconverter;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class MyStringArrayConverter extends SimpleArgumentConverter {

    public static final String DELIMITER = "|";

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String && String[].class.isAssignableFrom(targetType)) {
            return ((String) source).split("\\" + DELIMITER);
        } else {
            throw new ArgumentConversionException("Illegal conversion from " + source.getClass() + " to " + targetType);
        }
    }
}
