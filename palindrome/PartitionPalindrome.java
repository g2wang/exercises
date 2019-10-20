import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class PartitionPalindrome {
    /**
     * Given a string s, partition s such that every substring of the partition
     * is a palindrome. Return the minimum cuts needed for a palindrome
     * partitioning of s.
     * Examples:
     * 1) aab will be paritioned to [aa,b] with 1 cut
     * 2) madamamadamlevelparalleldogallollahalloop
     *    will be paritioned to:
     *    madamamadam,level,p,ara,l,lel,d,o,g,a,ll,ollahallo,o,p
     *    with 13 cuts
     */
    public static void main(String[] args) {
        String[] input = new String[] {
            "x",
            "xa",
            "xxy",
            "madamamadam",
            "xyzabcdmadamamadamxyzabcd",
            "madamamadamxyz",
            "xyzmadamamadamxyz",
            "aab",
            "abb",
            "thaisandthat oasisthisis madamamadamlevelparalleldogallollahalloop"
        };

        for (String s : input) {
            Integer[] cutPoints = partition(s);
            printResults(s, cutPoints);
        }

    }

    private static void printResults(String s, Integer[] cutPoints) {
        System.out.println("----------------");
        System.out.print(s + " can be cut into palindromes with "
                + cutPoints.length + " cut points: " + Arrays.toString(cutPoints) + "; palindromes: ");
        List<String> palindromes = new ArrayList<>();
        int i0 = 0;
        for (Integer i : cutPoints) {
            palindromes.add(s.substring(i0, i+1));
            i0 = i + 1;
        }
        palindromes.add(s.substring(i0));
        System.out.println(Arrays.toString(palindromes.toArray(new String[]{})));
    }

    private static Integer[] partition(String s) {
        List<Integer> cutPoints = new ArrayList<>(s.length() - 1);
        partition(s, cutPoints, 0);
        return cutPoints.toArray(new Integer[]{});
    }

    private static void partition(String s, List<Integer> cutPoints, int offset) {
        int[] r = findLongestPalindrome(s);
        if (r[0] > 0) {
            String prefix = s.substring(0, r[0]);
            //recursion
            partition(prefix, cutPoints, offset);
        }

        int c = r[0] -1;
        if (c >= 0) cutPoints.add(c + offset);

        c = r[1];
        if (c < s.length()-1) cutPoints.add(c + offset);

        if (r[1] + 1 < s.length()) {
            String suffix = s.substring(r[1] + 1);
            //recursion
            partition(suffix, cutPoints, r[1] + 1 + offset);
        }

    }

    private static int[] findLongestPalindrome(String s){
        int[] a = new int[2]; //ranage indexes inclusive

        // longest palindrom tends to center at the middle
        for (int i = s.length()/2; i >= 0; i--) {
            int[] r = findLongestPalindrome(s, i);
            int d = r[1] - r[0]; 
            if (d > a[1] - a[0]) {
                a[0] = r[0]; a[1] = r[1];
            }
            int w = d + 1;
            if (w >= (i+1)*2 || w >= (s.length()-i)*2) {
                break;
            }
        }

        for (int i = s.length()/2 + 1; i < s.length(); i++) {
            int[] r = findLongestPalindrome(s, i);
            int d = r[1] - r[0]; 
            if (d > a[1] - a[0]) {
                a[0] = r[0]; a[1] = r[1];
            }
            int w = d + 1;
            if (w >= (i+1)*2 || w >= (s.length()-i)*2) {
                break;
            }
        }

        return a;
    }

    private static int[] findLongestPalindrome(String s, int i) {
        int j, k;
        int[] a = new int[2];
        for (j = i-1, k = i+1; j >= 0 && k < s.length(); j--, k++) {
            if (s.charAt(j) != s.charAt(k)) {
                break;
            }
        }
        a[0] = ++j; a[1] = --k;
        for (j = i, k = i+1; j >= 0 && k < s.length(); j--, k++) {
            if (s.charAt(j) != s.charAt(k)) {
                break;
            }
        }
        int w = --k - ++j; 
        if (w > a[1] - a[0]) {
            a[0] = j; a[1] = k;
        }
        return a;
    }
}
