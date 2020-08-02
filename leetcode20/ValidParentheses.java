/**
 * 20. Valid Parentheses
Easy

Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Note that an empty string is also considered valid.

Example 1:

Input: "()"
Output: true
Example 2:

Input: "()[]{}"
Output: true
Example 3:

Input: "(]"
Output: false
Example 4:

Input: "([)]"
Output: false
Example 5:

Input: "{[]}"
Output: true
 *
 */

public class ValidParentheses {

    public static void main(String[] args) {
        ValidParentheses v = new ValidParentheses();
        String input = "(5+1) * ((x-1)*[a + b]-{c * (d -e)})";
        System.out.printf("%s isValid? %b%n", input, v.isValid(input));
    }

    public boolean isValid(String s) {
        char[] stack = new char[s.length()];
        int head = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                    stack[head++] = ')';
                    break;
                case '[':
                    stack[head++] = ']';
                    break;
                case '{':
                    stack[head++] = '}';
                    break;
                case ')':
                case ']':
                case '}':
                    head--;
                    if (head < 0 || c != stack[head]) return false;
                    break;
                default:
                    break;
            }
        }
        return head == 0;
    }

}
