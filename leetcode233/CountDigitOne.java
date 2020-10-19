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
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Number of Digit One.
     * Memory Usage: 35.9 MB, less than 21.29% of Java online submissions for Number of Digit One.
     */
    public int countDigitOne(int n) {
        if (n < 1) return 0;
        char[] ca = String.valueOf(n).toCharArray();
        int ans = 0;
        for (int i = ca.length - 1; i >= 0; i--) {
            ans += count(ca, i);
        }
        return ans;
    }

    private int count(char[] ca, int i) {
        int d = ca[i] - '0';
        int count = 0;
        int prefixNumber = 0;
        for (int j = i-1, f = 1; j >= 0; j--, f *= 10) {
            prefixNumber = (ca[j] - '0') * f + prefixNumber;
        }
        int suffixMax = 1;
        int suffixNumber = 0;
        for (int j = i+1; j < ca.length; j++) {
            suffixNumber = suffixNumber * 10 + (ca[j] - '0');
            suffixMax *= 10;
        }

        count += prefixNumber * suffixMax;

        if (d > 1) {
            count += suffixMax;
        } else if (d == 1) {
            count += suffixNumber + 1;
        }

        return count;
    }
}
