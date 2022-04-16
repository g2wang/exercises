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

public class LRSBruteForce {

    public static void main(String[] args) {

        LRSBruteForce lrsb = new LRSBruteForce();
        String[] input = new String[] {
            "abcd","abbaba","aabcaabdaab","aaaaa","Good morning, hello everyone, hello Tony, hello David, Tony is the best employee in our company"
        };
        for (String s : input) {
            Pair ans = lrsb.lrs(s);
            System.out.printf("-----%n%s%n'%s' %d%n", s, ans.sub, ans.count);
        }
    }

    public Pair lrs(String s) {
        Map<String, Integer> map = new HashMap<>();
        for (int j = 0; j < s.length(); j++) {
            for (int i = 0; i < j+1; i++) {
                String sub = s.substring(i, j+1); 
                if (map.containsKey(sub)) {
                    map.put(sub, map.get(sub) + 1);
                } else {
                    map.put(sub, 1);
                }
            }
        }
        Pair ans = new Pair();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String k = entry.getKey();
            int c = entry.getValue();
            if (c > 1 && (k.length() > ans.sub.length() || (k.length() == ans.sub.length() && c > ans.count))) {
                ans.sub = k;
                ans.count = c;
            }
        }
        return ans;
    }

    class Pair {
        String sub = "";
        int count = 0;
    }
}
