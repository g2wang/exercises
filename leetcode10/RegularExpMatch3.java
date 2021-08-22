/**
10. Regular Expression Matching
Hard

Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*' where:

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:

Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

Example 3:

Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".

Example 4:

Input: s = "aab", p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".

Example 5:

Input: s = "mississippi", p = "mis*is*p*."
Output: false

Constraints:

0 <= s.length <= 20
0 <= p.length <= 30
s contains only lowercase English letters.
p contains only lowercase English letters, '.', and '*'.
It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class RegularExpMatch3 {

    private int seq = 0;
    private static final char NONE = 0;
    private Map<Integer, Map<Character, List<Integer>>> transitionTable = new HashMap<>();
    private Set<Integer> acceptStates = new HashSet<>();
    private Map<String, Boolean> cache = new HashMap<>();

    public static void main(String[] args) {
        // String s = "abcaaaaaaabaabcabac";
        // String p = ".*ab.a.*a*a*.*b*b*";
        // String p = ".*ab.a.*a*.*b*";
        // ans = true

        // String s = "aaa";
        // String p = "a*a";

        // String s = "a";
        // String p = "ab*";
        // ans true

        // String s = "ccacbcbcccabbab";
        // String p = ".c*a*aa*b*.*b*.*";
        // ans = true

        String s = "aaaaaaaaaaaaab";
        String p = "a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*c";
        // String p = "a*b*c*d*e*f*g*h*i*j*k*l*m*n*o*p*q*r*s*t*u*v*c";

        RegularExpMatch3 r = new RegularExpMatch3();
        boolean matches = r.isMatch(s, p);
        System.out.printf("%s matches %s: %b%n", s, p, matches);
    }


    public boolean isMatch(String s, String p) {
        int n = s.length(), m = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        
        for (int i = 0; i < m; i++) {
            dp[i+1][0] = p.charAt(i) == '*' && dp[i-1][0];
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (p.charAt(i) == '.' || p.charAt(i) == s.charAt(j)) {
                    dp[i+1][j+1] = dp[i][j];
                } else if (p.charAt(i) == '*') {
                    dp[i+1][j+1] = dp[i-1][j+1] ||
                        ((p.charAt(i-1) == '.' || p.charAt(i-1) == s.charAt(j)) && dp[i+1][j]);
                }
            }
        }
        
        return dp[m][n];
    }

}
