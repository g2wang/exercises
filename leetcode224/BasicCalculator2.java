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

public class BasicCalculator2 {

    public static void main(String[] args) {
        BasicCalculator2 bc = new BasicCalculator2();
        String s = "2-(5-6) + 20";
        int ans = bc.calculate(s);
        System.out.printf("ans=%d%n", ans);
    }

    /**
     * Runtime: 2 ms, faster than 99.36% of Java online submissions for Basic Calculator.
     * Memory Usage: 41.5 MB, less than 5.01% of Java online submissions for Basic Calculator.
     */
    public int calculate(String s) {   
        char[] charArray = s.toCharArray();
        return calculate(charArray, 0)[0];
    }
    
    int[] calculate(char[] charArray, int index) {
        int operand1 = 0;
        char operator = '+';
        int operand2 = 0;
        
        while (index < charArray.length) {
            char ch = charArray[index++];
            
            if (ch >= '0' && ch <= '9') {
                operand2 = (operand2 * 10) + (ch - '0');
            } else if (ch == '(') {
                int[] result = calculate(charArray, index);
                operand2 = result[0];
                index = result[1];
            } else if (ch == ')') {
                break;
            } else if (ch == '+' || ch == '-') {
                operand1 = operator == '+'? (operand1 + operand2) : (operand1 - operand2);
                operand2 = 0;
                operator = ch;
            }
        }
        
        operand1 = operator == '+'? (operand1 + operand2) : (operand1 - operand2);
             
        return new int[]{operand1, index};
    }

}
