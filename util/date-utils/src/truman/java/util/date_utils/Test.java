package truman.java.util.date_utils;

public class Test {

    public void test() {

        long time = DateUtils.getCurrentTimeMillis();
        System.out.println("Current time in ms                  : " + time);

        String formatted = DateUtils.convTimeToFormatted(time);
        System.out.println("Current time in default date format : " + formatted);

        long conv = DateUtils.convTimeToMillis(formatted);
        System.out.println("The formatted time back to ms       : " + conv);

        formatted = DateUtils.convTimeToFormatted(time, DateUtils.HYP_UDS_FORMAT);
        System.out.println("Current time with hypen/underscore  : " + formatted);

        formatted = DateUtils.convTimeToFormatted(time, DateUtils.SINGLE_SEP_FORMAT);
        System.out.println("Current time with single separator  : " + formatted);

        String wrongFormat = formatted.substring(0, 10);
        System.out.println(String.format(
                "With a wrong format(%s), it will end up with (%d)-val return after the ParseException.",
                    wrongFormat, DateUtils.convTimeToMillis(wrongFormat)));
    }
}
