/**
 *
282. Expression Add Operators

Given a string num that contains only digits and an integer target, return all possibilities to insert the binary operators '+', '-', and/or '*' between the digits of num so that the resultant expression evaluates to the target value.

Note that operands in the returned expressions should not contain leading zeros.


Example 1:

Input: num = "123", target = 6
Output: ["1*2*3","1+2+3"]
Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.
Example 2:

Input: num = "232", target = 8
Output: ["2*3+2","2+3*2"]
Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.
Example 3:

Input: num = "3456237490", target = 9191
Output: []
Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.

Constraints:
1 <= num.length <= 10
num consists of only digits.
-2^31 <= target <= 2^31 - 1

*/


import java.util.*;

public class AddOperators {

    public static void main(String[] args) {
        AddOperators a = new AddOperators();

        String num = "3456237490";
        int target = 9191;
        // expected result: []

        // String num = "123456789";
        // int target = 45;

        List<String> result = a.addOperators(num, target);

        System.out.println(result);

    }

    public List<String> addOperators(String num, int target) {
        List <String> result = new ArrayList<>();
        Accumulator a = new Accumulator();
        char c = num.charAt(0);
        int v = c - '0';
        a.i = 1;
        a.e.append(c);
        a.v = 0;
        a.r = v;
        permute(a, num, target, result);
        return result;
    }

    private void permute(Accumulator a, String num, int target, List<String> result) {
        if (a.i == num.length()) {
            a.accum();
            if (a.v == target) {
                result.add(a.e.toString());
            }
            return;
        }
        move(a.clone(), num, '+', target, result);
        move(a.clone(), num, '-', target, result);
        move(a.clone(), num, '*', target, result);
        if (a.r != 0) {
            move(a, num, 's', target, result); // operator s stands for shift
        }
    }

    private void move(Accumulator a, String num, char op, int target, List<String> result) {
        char c = num.charAt(a.i);
        int v =  c - '0';
        switch (op) {
            case '+':
                a.e.append("+").append(c);
                a.accum();
                a.r = v;
                break;
            case '-':
                a.e.append("-").append(c);
                a.accum();
                a.r = -v;
                break;
            case '*':
                a.e.append("*").append(c);
                a.m = a.r * a.m;
                a.r = v;
                break;
            case 's':
                a.e.append(c);
                if (a.r < 0) {
                    a.r = 10 * a.r - v;
                } else {
                    a.r = 10 * a.r + v;
                }
                break;
            default:
                break;
        }
        a.i = a.i + 1;
        permute(a, num, target, result);
    }


    private class Accumulator {
        private int i = 0; // index into num string
        private StringBuilder e = new StringBuilder(); // expression
        private long v = 0; // accumulated value
        private long r = 0; // operand
        private long m = 1; // multiplicant

        public void accum() {
            v += r * m;
            m = 1;
            r = 0;
        }

        public Accumulator clone() {
            Accumulator a = new Accumulator();
            a.i = i;
            a.e = new StringBuilder(e.toString());
            a.v = v;
            a.r = r;
            a.m = m;
            return a;
        }
    }

}
