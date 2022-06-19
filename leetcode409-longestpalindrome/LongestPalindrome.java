/**
 * 409. Longest Palindrome
Easy

Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.

Letters are case sensitive, for example, "Aa" is not considered a palindrome here.

Example 1:
Input: s = "abccccdd"
Output: 7
Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.

Example 2:
Input: s = "a"
Output: 1

Example 3:
Input: s = "bb"
Output: 2

Constraints:

1 <= s.length <= 2000
s consists of lowercase and/or uppercase English letters only.
 *
 */

import java.util.*;

public class LongestPalindrome {

    public static void main(String[] args) {
       String s = "abccccdd"; // ans: 7
       LongestPalindrome lp = new LongestPalindrome();
       int ans = lp.longestPalindrome(s);
       System.out.printf("%s -> %d%n", s, ans);
    }


    /**
     * Runtime: 5 ms, faster than 59.06% of Java online submissions for Longest Palindrome.
     * Memory Usage: 40.7 MB, less than 88.96% of Java online submissions for Longest Palindrome.
     */
    public int longestPalindrome(String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int count = freq.getOrDefault(c, 0);
            freq.put(c, count+1);
        }
     
        int ans = 0;
        boolean oddIncluded = false;
        for (Map.Entry<Character, Integer> e : freq.entrySet()) {
            int count = e.getValue();
            if (count%2 == 0) {
                ans += count;
            } else {
               if (!oddIncluded) {
                   ans += count;
                   oddIncluded = true;
               } else {
                   ans += count - 1;
               } 
            }
        }
        return ans;
    }
}
