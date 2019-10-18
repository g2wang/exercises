import java.util.LinkedList;
import java.util.List;

public class ListToArrayViaStream {

    public static void main(String[] args) {
        List<String> list = new LinkedList<String>();
        list.add("Geeks");
        list.add("for");
        list.add("Geeks");
        list.add("Practice");
        String[] arr = list.stream().toArray(String[] ::new);
        String[] arr2 = list.toArray(new String[]{});
        for (String x : arr)
            System.out.print(x + " ");
        System.out.println(" ");
        for (String x : arr2)
            System.out.print(x + " ");
        System.out.println(" ");
    }
}
