import java.util.*;

/**
 * Find the longest repeated substring (LRS) in a string.
 * If there are more than one candidate, return the one that repeated the most times.
 * Output the LRS and its repeated times.
 */
public class LRS {
    public static void main(String[] args) {
       // String s = "abxefccefabef"; 
       String s = "Good morning, hello everyone, hello Tony, hello David, Tony is the best employee in our company"; 
       LRS instance = new LRS();
       Pair ans = instance.lrs(s);
       System.out.printf("'%s' %d%n", ans.sub, ans.count);
    }

    private class Pair {
        String sub = "";
        int count = 0;
    }

    public Pair lrs(String s) {
        int lower = 0; // lower bound of substring length
        int upper = s.length() - 1; // upper bound of the substring length
        Pair ans = new Pair();
        while (lower < upper) {
            int mid = lower + (upper - lower + 1)/2;
            Pair p = rsOfLength(s, mid);
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

    private Pair rsOfLength(String s, int len) {
        Map<String, Integer> map = new HashMap<>();
        // find all substrings of length len and record them into a map
        for (int i = 0; i <= s.length() - len; i++) {
            String sub = s.substring(i, i+len);
            if (map.containsKey(sub)) {
                map.put(sub, map.get(sub)+1); // increment count
            } else {
                map.put(sub, 1);
            }
        }
        Pair ans = new Pair();
        // find substring repeated (count > 1) and repeated most times in the map
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            String k = e.getKey();
            int c = e.getValue();
            if (c > 1 && c > ans.count) {
                ans.sub = k;
                ans.count = c;
            }
        } 
        return ans;
    }

}
