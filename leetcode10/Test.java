import java.util.*;

public class Test {
    public static void main(String[] args) {
        Map<Integer, Map<Character, Integer>> table = new HashMap<>();
        Map<Character, Integer> trans = table.computeIfAbsent(1, k -> new HashMap<>());
        trans.put('a', 2);
        System.out.printf("table: %s%n", table);
    }
}
