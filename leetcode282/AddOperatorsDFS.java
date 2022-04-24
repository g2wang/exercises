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

public class AddOperatorsDFS {

    public static void main(String[] args) {
        AddOperatorsDFS a = new AddOperatorsDFS();

        // String num = "3456237490";
        // int target = 9191;
        // expected result: []

        String num = "123456789";
        int target = 45;

        List<String> result = a.addOperators(num, target);

        System.out.println(result);

    }


    public List<String> addOperators(String num, int target) {
        List<String> res = new LinkedList<>();
        if(num.length() == 0 || Long.valueOf(num) > Integer.MAX_VALUE) return res;
        char[] path = new char[num.length() * 2 - 1];
        char[] digits = num.toCharArray();
        
        long n = 0;
        for (int i = 0; i < digits.length; i++) {
            n = n * 10 + digits[i] - '0';
            path[i] = digits[i];
            dfs(res, path, i+1, 0, n, digits, i+1, target);
            if(n == 0) break;
        }
        return res;
    }
    
    public void dfs(List<String> ret, char[] path, int len, long left, long cur, char[] digits, int pos, int target) {
        // len is to record current position for path; pos is to record current position for digits
        if (pos == digits.length) {
            if (left + cur == target) ret.add(new String(path, 0, len));
            return;
        }
        long n = 0;
        int j = len + 1;
        for (int i = pos; i < digits.length; i++) {
            n = n * 10 + digits[i] - '0';
            path[j++] = digits[i];
            path[len] = '+';
            dfs(ret, path, j, left + cur, n, digits, i + 1, target);
            path[len] = '-';
            dfs(ret, path, j, left + cur, -n, digits, i + 1, target);
            path[len] = '*';
            dfs(ret, path, j, left, cur * n, digits, i + 1, target);
            if (n == 0) break; 
        }
    }

}
