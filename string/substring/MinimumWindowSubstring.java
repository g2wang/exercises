/*
LeetCode: 76. Minimum Window Substring

Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

Example:

Input: S = "ADOBECODEBANC", T = "ABC"
Output: "BANC"
Note:

If there is no such window in S that covers all characters in T, return the empty string "".
If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
 */
import java.util.HashMap;

public class MinimumWindowSubstring {

    public static void main(String[] args) {
        String s = "ADOBECODEBANC", t = "ABC";
        MinimumWindowSubstring mws = new MinimumWindowSubstring();
        String m = mws.minWindow(s, t);
        System.out.printf("Minimum window in \"%s\" that contains all the characters in \"%s\" is \"%s\"\n", s, t, m);
    }
    
//                 A D O B E C O D E B A N C
//    r 0          0 1 2 3 4 5 
//    l 0          0 0 0 0 0 0
//    n            1 - - 1 - 1 
//    A 1          0 0 0 0 0 0
//    B 1          1 1 1 0 0 0
//    C 1          1 1 1 1 1 0
//    Count 0      1 1 1 2 2 3
//
//    r            1
//    n            0
//    A            1

    public String minWindow(String s, String t) {

                if (s.length() < t.length()) return "";
        char[] tc = t.toCharArray();
        HashMap<Character, Integer> tm = new HashMap<>(tc.length);
        for (char c : tc) {
            Integer n = tm.get(c);
            if (n == null) n = 0;
            tm.put(c, n+1);
        }
        int l = 0, r = 0, count = 0;
        int start = 0, window = s.length();
        boolean found = false;
        for (r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            Integer n = tm.get(c);
            if (n == null) continue;
            tm.put(c, n-1);
            if (n <= 0) continue;
            count++;
            if (count == tc.length) {
                while (l <= r) {
                    c = s.charAt(l++);
                    n = tm.get(c);
                    if (n == null) continue;
                    tm.put(c, n+1);
                    if (n >= 0) break;
                }
                count--;
                int newWindow = r - l + 2;
                if (newWindow <= window) {
                    if (!found) found = true;
                    start = l - 1;
                    window = newWindow;
                }
            }
        }
        if (!found) return "";
        return s.substring(start, start + window);
    }

}
