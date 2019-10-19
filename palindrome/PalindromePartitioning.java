import java.util.Arrays;

public class PalindromePartitioning {
    /**
     * Given a string s, partition s such that every substring of the partition
     * is a palindrome. Return the minimum cuts needed for a palindrome
     * partitioning of s.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: PalindromePartioning <String>");
        }
        int[] cutPoints = partition(args[0]);
        System.out.println(args[0] + " can be cut into palindromes with cut points: "
                + Arrays.toString(cutPoints));

    }

    private static int[] partition(String s) {
    
        return new int[] {1, 3};
    }
}
