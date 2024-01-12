package truman.java.example.combine_filters;

import java.util.function.Predicate;

public class FilterUtils {

    /**
     *  Nullable left-side hand and non-nullable right-side hand
     */ 
    public static <T> Predicate<T> andFilters( Predicate<T> left, Predicate<T> right) {
        return (left == null) ? right : left.and(right);
    }

    /**
     *  Nullable left-side hand and non-nullable right-side hand
     */
    public static <T> Predicate<T> orFilters(Predicate<T> left, Predicate<T> right) {
        return (left == null) ? right : left.or(right);
    }
}
