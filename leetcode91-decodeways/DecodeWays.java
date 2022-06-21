/**
 * 91. Decode Ways
Medium

A message containing letters from A-Z can be encoded into numbers using the following mapping:

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"
To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, "11106" can be mapped into:

"AAJF" with the grouping (1 1 10 6)
"KJF" with the grouping (11 10 6)
Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".

Given a string s containing only digits, return the number of ways to decode it.

The test cases are generated so that the answer fits in a 32-bit integer.

Example 1:
Input: s = "12"
Output: 2
Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).

Example 2:
Input: s = "226"
Output: 3
Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).

Example 3:
Input: s = "06"
Output: 0
Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06").

Constraints:
1 <= s.length <= 100
s contains only digits and may contain leading zero(s).
 */
public class DecodeWays {

    public static void main(String[] args) {
        DecodeWays dw = new DecodeWays();
        String[] inputs = new String[]{"12", "226", "06", "100"};
        for (String s : inputs) {
            System.out.printf("%s -> %d%n", s, dw.numDecodings(s));
        }
    }

    /**
     * Runtime: 1 ms, faster than 98.09% of Java online submissions for Decode Ways.
     * Memory Usage: 41.5 MB, less than 80.76% of Java online submissions for Decode Ways.
     */
    public int numDecodings(String s) {
        if (s.charAt(0) == '0') return 0;
        int[] ways = new int[2];
        ways[0] = 1;
        ways[1] = 1;
        for (int i = 2; i <= s.length(); i++) {
            int ways2 = 0;
            char c = s.charAt(i-1);
            char pc = s.charAt(i-2);
            if (c == '0' && pc == '0') return 0;
            if (c >= '1') ways2 = ways[1];
            int val = (pc-'0')*10 + (c-'0');
            if (val >= 10 && val <= 26) {
                ways2 += ways[0];
            }
            ways[0] = ways[1];
            ways[1] = ways2;
        }
        return ways[1];
    }

}
