import java.util.*;

public class CollectionsReverseTest {

    public static void main(String[] args) {
        List<String> input = Arrays.asList("1", "2", "3", "4", "5");
        System.out.printf("input: %s%n", Arrays.toString(input.toArray(new String[]{})));
        Collections.reverse(input);
        System.out.printf("input reversed: %s%n", Arrays.toString(input.toArray(new String[]{})));
    }

}
