/**
 *
 * 1143. Longest Common Subsequence
Medium

Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.

A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

For example, "ace" is a subsequence of "abcde".
A common subsequence of two strings is a subsequence that is common to both strings.

 

Example 1:
Input: text1 = "abcde", text2 = "ace" 
Output: 3  
Explanation: The longest common subsequence is "ace" and its length is 3.

Example 2:
Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.

Example 3:
Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.
 

Constraints:

1 <= text1.length, text2.length <= 1000
text1 and text2 consist of only lowercase English characters.
 *
 */
import java.util.*;

public class LongestCommonSubsequenceFast {
    public static void main(String[] args) {
        LongestCommonSubsequenceFast lcs = new LongestCommonSubsequenceFast();
        String[][] input = new String[][]{{"abcde", "ace" }, {"abc", "abc"}, {"abc", "def"}};
        for (String[] s : input) {
            int ans = lcs.longestCommonSubsequence(s[0], s[1]);
            System.out.printf("%s -> %d%n", Arrays.toString(s), ans);
        }
    }

    /**
     * copyright: author of leetcode submission
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text1.length() == 0 || text2 ==  null || text2.length() == 0) return 0;
        char[] s, t;
        if (text1.length() < text2.length()) {
            s = text1.toCharArray();
            t = text2.toCharArray();
        } else {
            s = text2.toCharArray();
            t = text1.toCharArray();
        }

        int n = s.length, m = t.length;

        int[] prev = new int[n + 1];

        for (int i = m - 1; i >= 0; i--) {
            int[] curr = new int[n + 1];
            for (int j = n - 1; j >= 0; j--) {
                if (t[i] == s[j]) {
                    curr[j] = prev[j + 1] + 1;
                } else {
                    curr[j] = Math.max(curr[j + 1], prev[j]);
                }
            }
            prev = curr;
        }
        return prev[0];
    }

}
