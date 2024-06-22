package truman.java.example.junit.csvsourceconverter;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.ArrayList;
import java.util.List;

public class MyEnumListConverter extends SimpleArgumentConverter {

    public static final String DELIMITER = "|";

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String && List.class.isAssignableFrom(targetType)) {
            String[] parsed = ((String) source).split("\\" + DELIMITER);
            List<MyEnum> ret = new ArrayList<>();
            for (String e : parsed) {
                ret.add(MyEnum.valueOf(e));
            }
            return ret;
        } else {
            throw new ArgumentConversionException("Illegal conversion from " + source.getClass() + " to " + targetType);
        }
    }
}
