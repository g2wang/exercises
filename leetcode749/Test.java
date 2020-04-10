import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Map<Integer, String> regionsMap = new HashMap<>();
        regionsMap.put(3, "region3");
        regionsMap.put(1, "region1");
        regionsMap.put(4, "region4");
        regionsMap.put(5, "region5");
        regionsMap.put(2, "region2");
        String[] regions = regionsMap.values().toArray(new String[]{});

        Arrays.sort(regions, (a, b) -> b.compareTo(a));
        System.out.println("sorted desc");

        for (String r : regions) {
            System.out.println(r);
        }

        regionsMap.remove(5);
        System.out.println("removed 5 from regionsMap");

        Arrays.sort(regions, (a, b) -> a.compareTo(b));
        System.out.println("sorted asc");

        for (String r : regions) {
            System.out.println(r);
        }

    }
}

