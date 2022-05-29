import java.util.*;

/**
 * Let's compare two strings, figure out what is the longest repeated common substring within the two strings, and output the total repeated times. 
 * String 1:    "If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough."
 * String 2:    "When you have something in your mind, just do it, otherwise you don't have the chance to succeed. If you do something, you don't have to blame yourself later"
 * Rule: a substring that exists both in String 1 and String 2.
 * Expected output:
 * Longest repeated substring: “ you don’t have “, repeat times: 3
 */
public class LRSTwoStr {
    public static void main(String[] args) {
       // String s = "abxefccefabef"; 
       String s1 = "If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough."; 
       String s2 = "When you have something in your mind, just do it, otherwise you don't have the chance to succeed. If you do something, you don't have to blame yourself later"; 
       LRSTwoStr instance = new LRSTwoStr();
       Pair ans = instance.lrs(s1, s2);
       System.out.printf("'%s' %d%n", ans.sub, ans.count);
    }

    private class Pair {
        String sub = "";
        int count = 0;
    }

    public Pair lrs(String s1, String s2) {
        int lower = 0; // lower bound of substring length
        int upper = Math.min(s1.length() - 1, s2.length() - 1); // upper bound of the substring length
        Pair ans = new Pair();
        while (lower < upper) {
            int mid = lower + (upper - lower + 1)/2;
            Pair p = rsOfLength(s1, s2, mid);
            if (p.count == 0) { // no repeated substring of length mid
                upper = mid -1;    
            } else { // found repeated substring of length mid
                lower = mid;
                if (p.sub.length() > ans.sub.length() || (p.sub.length() == ans.sub.length() && p.count > ans.count)) {
                    ans = p;
                }
            }
        }
        return ans;
    }

    private Pair rsOfLength(String s1, String s2, int len) {
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        // find all substrings of length len and record them into a map
        for (int i = 0; i <= s1.length() - len; i++) {
            String sub1 = s1.substring(i, i+len);
            if (map1.containsKey(sub1)) {
                map1.put(sub1, map1.get(sub1)+1); // increment count
            } else {
                map1.put(sub1, 1);
            }
        }
        for (int i = 0; i <= s2.length() - len; i++) {
            String sub2 = s2.substring(i, i+len);
            if (map2.containsKey(sub2)) {
                map2.put(sub2, map2.get(sub2)+1); // increment count
            } else {
                map2.put(sub2, 1);
            }
        }

        Pair ans = new Pair();
        // find substring repeated (count > 1) and repeated most times in the map
        for (Map.Entry<String, Integer> e : map1.entrySet()) {
            String k = e.getKey();
            int c1 = e.getValue();
            Integer c2 = map2.get(k);
            if (c2 != null) {
                int c = c1 + c2;
                if (c > ans.count) {
                    ans.sub = k;
                    ans.count = c;
                }
            }
        } 
        return ans;
    }

}
