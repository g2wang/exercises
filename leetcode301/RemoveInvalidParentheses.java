/**
301. Remove Invalid Parentheses
Hard

Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.

Note: The input string may contain letters other than the parentheses ( and ).

Example 1:

Input: "()())()"
Output: ["()()()", "(())()"]
Example 2:

Input: "(a)())()"
Output: ["(a)()()", "(a())()"]
Example 3:

Input: ")("
Output: [""]
 */

import java.util.LinkedList;
import java.util.List;

public class RemoveInvalidParentheses {

    public static void main(String[] args) {
        // String s = ")(";
        // String s = "(a)())()";
        String s = "(r(()()("; // ans: ["r()()","r(())","(r)()","(r())"]
        RemoveInvalidParentheses r = new RemoveInvalidParentheses();
        List<String> out = r.removeInvalidParentheses(s);
        System.out.printf("%s -> %s%n", s, out);
    }

    /**
     * Runtime: 1 ms, faster than 99.97% of Java online submissions for Remove Invalid Parentheses.
     * Memory Usage: 39.6 MB, less than 64.03% of Java online submissions for Remove Invalid Parentheses.
     */
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new LinkedList<>();
        remove(s, 0, 0, result, '(', ')');
        return result;
    }

    private void remove(String s, int start, int lastRemove,
            List<String>result, char p1, char p2) {
        int balance = 0;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == p1) {
                balance++;
            } else if (c == p2) {
                balance--;
            }
            if (balance < 0) {
                for (int j = lastRemove; j <= i; j++) {
                    if (s.charAt(j) == p2
                        && (j == lastRemove || s.charAt(j-1) != p2)) {
                        remove(s.substring(0, j) + s.substring(j+1), i, j,
                                result, p1, p2);
                    }
                }
                return;
            }
        }
        String reverse = (new StringBuilder(s)).reverse().toString();
        if (p1 == '(') {
            remove(reverse, 0, 0, result, p2, p1);
        } else {
            result.add(reverse);
        }
    }

}
