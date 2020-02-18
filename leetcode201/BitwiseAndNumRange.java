/**
201. Bitwise AND of Numbers Range
Medium

Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of
all numbers in this range, inclusive.

Example 1:
Input: [5,7]
Output: 4

Example 2:
Input: [0,1]
Output: 0
 */

import java.util.Arrays;

public class BitwiseAndNumRange{

    public static void main(String[] args) {
        int[] range = new int[]{5, 7};
        BitwiseAndNumRange b = new BitwiseAndNumRange();
        int ans = b.rangeBitwiseAnd(range[0], range[1]);
        System.out.printf("range %s => %d%n", Arrays.toString(range), ans);
    }

    public int rangeBitwiseAnd(int m, int n) {
        int count = 0;
        while (m != n) {
            m >>= 1;
            n >>= 1;
            count++;
        } 
        return m << count;
    }
}
