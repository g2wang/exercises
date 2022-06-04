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
public class LongestPalindrome2 {

    public static void main(String[] args) {
        String[] input = new String[]{"babad", "cbbd"};
        LongestPalindrome2 lp = new LongestPalindrome2();
        for (String s : input) {
            System.out.printf("%s -> %s%n", s, lp.longestPalindrome(s));
        }
    }

    /**
     * Runtime: 34 ms, faster than 84.07% of Java online submissions for
     * Longest Palindromic Substring.  Memory Usage: 42.1 MB, less than 94.07%
     * of Java online submissions for Longest Palindromic Substring.
     */
    public String longestPalindrome(String s) {
        int L = 0;
        int R = 0;
        String sub = null;
        int len = s.length();
        if (len == 1) {
            return s;
        }
        int l = 0;
        int r = 0;
        for (int i = 0; i < len-1; i++) {
            l = i;
            r = i;
            while (l >= 0 && r < len && s.charAt(l) == s.charAt(r)) {
                l--;
                r++;
            }
            if ( r-l-1 > R-L) {
                L = l+1;
                R = r;
            }
            l = i;
            r = i+1;
            while (l >= 0 && r < len && s.charAt(l) == s.charAt(r)) {
                l--;
                r++;
            }
            if ( r-l-1 > R-L) {
                L = l+1;
                R = r;
            }
        }
        return s.substring(L, R);
    }

}
