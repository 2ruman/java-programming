package truman.java.example.junit.csvsourceconverter;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class MyEnumConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String && MyEnum.class.isAssignableFrom(targetType)) {
            return MyEnum.valueOf((String) source);
        } else {
            throw new ArgumentConversionException("Illegal conversion from " + source.getClass() + " to " + targetType);
        }
    }
}
