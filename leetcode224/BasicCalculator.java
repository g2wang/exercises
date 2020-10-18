/**
224. Basic Calculator

Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

Example 1:

Input: "1 + 1"
Output: 2
Example 2:

Input: " 2-1 + 2 "
Output: 3
Example 3:

Input: "(1+(4+5+2)-3)+(6+8)"
Output: 23
Note:
You may assume that the given expression is always valid.
Do not use the eval built-in library function.
 */

import java.util.Deque;
import java.util.ArrayDeque;

public class BasicCalculator {

    public static void main(String[] args) {
        BasicCalculator bc = new BasicCalculator();
        String s = "2-(5-6) + 20";
        int ans = bc.calculate(s);
        System.out.printf("ans=%d%n", ans);
    }

    /**
     * Runtime: 4 ms, faster than 95.89% of Java online submissions for Basic Calculator.
     * Memory Usage: 39.2 MB, less than 5.03% of Java online submissions for Basic Calculator.
     */
    public int calculate(String s) {
        char[] cs = s.toCharArray();
        int ans = 0;
        int num = 0;
        int sign = 1;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            if (Character.isDigit(c)) { 
                num = num*10 + (c - '0');
            } else if (c == '(') {
                stack.push(ans); 
                stack.push(sign); 
                ans = 0;
                num = 0;
                sign = 1;
            } else if (c == ')') {
                ans += sign * num;
                sign = stack.pop();
                ans = stack.pop() + sign * ans; 
                num = 0;
            } else {
                ans += sign * num;
                num = 0;
                if (c == '+') {
                    sign = 1;
                } else if (c == '-') {
                    sign = -1;
                }
            }
        }
        ans += sign * num;
        return ans;
    }

}
