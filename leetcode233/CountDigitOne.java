/**
233. Number of Digit One
Hard
￼
Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.

Example:

Input: 13
Output: 6
Explanation: Digit 1 occurred in the following numbers: 1, 10, 11, 12, 13.*
 */

public class CountDigitOne {

    public static void main(String[] args) {
        int n = 20;
        CountDigitOne cd1 = new CountDigitOne();
        int ans = cd1.countDigitOne(n);
        System.out.printf("n: %d -> ans: %d%n", n, ans);
    }

    /**
     * Time complexity: O(log(n))
     * Space complexity: O(1)
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Number of Digit One.
     * Memory Usage: 35.6 MB, less than 21.29% of Java online submissions for Number of Digit One.
     */
    public int countDigitOne(int n) {
        if (n < 1) return 0;
        int len = String.valueOf(n).length();
        int ans = 0;

        int prefixNumber = n;
        int suffixNumber = 0;
        int suffixMax = 1;
        int d = 0;
        int f = 1;

        for (int i = len - 1; i >= 0; i--) {
            if (i < len - 1) {
                suffixNumber += f*d;
                suffixMax *= 10;
                f *= 10;
            }

            d = prefixNumber%10;
            prefixNumber = prefixNumber/10;

            ans += prefixNumber * suffixMax;

            if (d > 1) {
                ans += suffixMax;
            } else if (d == 1) {
                ans += suffixNumber + 1;
            }

        }

        return ans;
    }

}
