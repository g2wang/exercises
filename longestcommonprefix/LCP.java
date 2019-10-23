/*
Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:

Input: ["flower","flow","flight"]
Output: "fl"
Example 2:

Input: ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
Note:

All given inputs are in lowercase letters a-z. 
 */

import java.util.Arrays;

public class LCP {

    public static void main(String[] args){
        LCP lcp = new LCP();
        String[] a = new String[] { "flower","flow","flight"};
        String p = lcp.longestCommonPrefix(a);
        System.out.printf("longest common prefix: %s;  %s\n", p, Arrays.toString(a));
    }

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String s0 = strs[0];
        if (strs.length == 1 || s0 == null || s0.length() == 0)  return s0;
        int e;
        outer:
        for (e = 0; e < s0.length(); e++) {
            char c = s0.charAt(e);
            for (int i = 1; i < strs.length; i++) {
                if (e >= strs[i].length() || c !=  strs[i].charAt(e))
                    break outer;
            }
        }
        return s0.substring(0, e);
    }

}
