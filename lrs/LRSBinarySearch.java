/**
 *
 * The longest repeated substirng problem
 * or
 * The longest repeating substirng problem
 *
 * Find the longest substring which repeats the most times in a string and output repeat times for this repeated substring.
 * return 0 if no repeating substring exists.
 *
 * Example 1:
 * Input: S = "abcd"
 * Output: "" 0
 * Explanation: There is no repeating substring
 *
 * Example 2:
 * Input: S = "abbaba"
 * Output: "ab" 2
 * Explanation: The longest repeating substrings are "ab" and "ba", each of which occurs twice.
 *
 * Example 3:
 * Input: S = "aabcaabdaab"
 * Output: "aab" 3
 * Explanation: The longest repeating substing is "aab", which occurs 3 times.
 *
 * Example 4:
 * Input: S = "aaaaa"
 * Output: "aaaa" 2
 * Explanation: The longest repeating substring is "aaaa", which occurs 4 times.
 * 
 * Example 5:
 * Input: S = "Good morning, hello everyone, hello Tony, hello David, Tony is the best employee in our company"
 * Output: ",hello " 3
 * Explanation: The longest repeating substring is "hello", which occurs 3 times.
 * 
 */
import java.util.*;

public class LRSBinarySearch {

    public static void main(String[] args) {

        LRSBinarySearch lrs = new LRSBinarySearch();
        String[] input = new String[] {
            "abcd","abbaba","aabcaabdaab","aaaaa","Good morning, hello everyone, hello Tony, hello David, Tony is the best employee in our company",
            "abxefccefabef"
        };
        for (String s : input) {
            Pair ans = lrs.lrs(s);
            System.out.printf("-----%n%s%n'%s' %d%n", s, ans.sub, ans.count);
        }
    }

    public Pair lrs(String s) {
        int lower = 0;
        int upper = s.length() - 1;
        Pair ans = new Pair();
        while (lower < upper) {
            int mid = lower + (upper-lower+1)/2;
            Pair p = rsOfLength(s, mid);
            if (p.count == 0) { // no repeated substring of length mid
                upper = mid - 1;
            } else {
                lower = mid;
                if (p.sub.length() > ans.sub.length() || (p.sub.length() == ans.sub.length() && p.count > ans.count)) {
                    ans = p;
                }
            }
        }
        return ans;
    }

    private Pair rsOfLength(String s, int len) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= s.length()-len; i++) {
            String sub = s.substring(i, i+len);
            if (map.containsKey(sub)) {
                map.put(sub, map.get(sub) + 1);
            } else {
                map.put(sub, 1);
            }
        }
        // System.out.printf("---- len: %d --- %n", len);
        Pair ans = new Pair();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String k = entry.getKey();
            int c = entry.getValue();
            // System.out.printf("%s -> %d%n", k, c);
            if (c > 1 && c > ans.count) {
                ans.sub = k;
                ans.count = c;
            }
        }
        return ans;
    }

    private class Pair {
        String sub = "";
        int count = 0;
    }
}
