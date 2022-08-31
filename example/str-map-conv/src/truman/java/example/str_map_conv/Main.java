package truman.java.example.str_map_conv;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.TreeMap;

public class Main {

    public static Map<String, String> strToMap(String str) {
        return strToMap(str, false);
    }

    public static Map<String, String> strToMap(String str, boolean ordered) {
        Properties prop = new Properties();
        StringReader reader = new StringReader(str);
        Map<String, String> map = ordered ? new TreeMap<>() : new HashMap<>();

        try {
            prop.load(reader);

            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String mapToStr(Map<String, String> map) {
        StringJoiner sj = new StringJoiner("\n", "", "");
        for (Map.Entry<String, String> e : map.entrySet()) {
            sj.add(e.toString());
        }
        return sj.toString();
        /* 
         * Going with StringBuilder, we can have the same result, I've not
         * tested to figure out which one is better way for performance though.
         * 
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : m.entrySet()) {
            sj.add(e.toString());
            sb.append(e.toString()).append("\n");
        }
        */
    }

    public static void main(String[] args) {
        String s;
        Map<String, String> m;

        s = "# Comments start with the hash character\n" +
                "# and could not be an entry\n" +
                "aaa=AAA\nbbb=BBB\nccc=CCC";
        m = strToMap(s, true);
        s = mapToStr(m);
        System.out.println(s);

        s = "ddd=DDD ,eee=EEE,fff=FFF".replace(",", "\n").replace(" ", "");
        m = strToMap(s, true);
        s = mapToStr(m);
        System.out.println(s);

        s = "aaa=AAA\nbbb=BBB\nccc=CCC\n";
        m = strToMap(s, false);
        m.forEach((k, v) -> System.out.println("Key : " + k + ", Val : " + v));
    }
}
