/**
 * 5. Longest Palindromic Substring
Medium

Given a string s, return the longest palindromic substring in s.
 

Example 1:
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.

Example 2:
Input: s = "cbbd"
Output: "bb"
 
Constraints:
1 <= s.length <= 1000
s consist of only digits and English letters.
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        String[] input = new String[]{"babad", "cbbd"};
        LongestPalindrome lp = new LongestPalindrome();
        for (String s : input) {
            System.out.printf("%s -> %s%n", s, lp.longestPalindrome(s));
        }
    }

    /**
     * Runtime: 20 ms, faster than 95.87% of Java online submissions for
     * Longest Palindromic Substring. Memory Usage: 42 MB, less than 97.59% of
     * Java online submissions for Longest Palindromic Substring.
     */
    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return s;
        if (isPD(s, 0, s.length())) {
            return s; 
        }
        int[] p = new int[]{0, 1};
        int l = 1, r = s.length() - 1, m = l + (r-l)/2; // palindrom length
        while (l <= r) {
            int[] q = pdWithLength(m, s);
            if (q == null) q = pdWithLength(m+1, s);
            if (q != null) {
                p = q;
                l = m + 1; 
            } else {
                r = m - 1; 
            }
            m = l + (r-l)/2;
        }
        return s.substring(p[0], p[1]);
    }
   
    private int[] pdWithLength(int len, String s) {
        for (int i = 0; i <= s.length()-len; i++) {
            if (isPD(s, i, i+len)) return new int[]{i, i+len};
        }
        return null;
    }
    
    private boolean isPD(String s, int startIndex, int endIndexExcl) {
        int i = startIndex, j = endIndexExcl - 1;
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) return false;
        }
        return true;
    }
}
