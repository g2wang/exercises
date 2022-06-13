/**
 * 312. Burst Balloons
Hard

You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked to burst all the balloons.

If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.

Return the maximum coins you can collect by bursting the balloons wisely.

Example 1:
Input: nums = [3,1,5,8]
Output: 167
Explanation:
nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167

Example 2:
Input: nums = [1,5]
Output: 10

Example 3:
Input: nums = [7,9,8,0,7,1,3,5,5,2,3]
Output: 1654

Constraints:

n == nums.length
1 <= n <= 300
0 <= nums[i] <= 100
 */

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BurstBalloonsIterative {

    public static void main(String[] args) {
        // int[] nums = new int[]{3,1,5};
        int[] nums = new int[]{7,9,8,0,7,1,3,5,5,2,3};
        BurstBalloonsIterative bb = new BurstBalloonsIterative();
        int ans = bb.maxCoins(nums);
        System.out.printf("%s->%d%n", Arrays.toString(nums), ans);
    }

    private Integer[][] dp;
    private int a[];

    public int maxCoins(int[] nums) {
        int n = nums.length;
        dp = new Integer[n][n];
        a = new int[n+2];
        a[0]=1;
        System.arraycopy(nums, 0, a, 1, n);
        a[n+1]=1;
        return burstLastOneInRange(0, n-1);
    }

    /**
     * Runtime: 84 ms, faster than 66.12% of Java online submissions for Burst Balloons.
     * Memory Usage: 43.4 MB, less than 32.39% of Java online submissions for Burst Balloons.
     *
     * int i and j are inclusive indices in nums array 
     */
    private int burstLastOneInRange(int idxStart, int idxEnd) {
        int maxRange = idxEnd - idxStart;
        for (int r = 0; r <= maxRange; r++) {
            for (int i = idxStart; i <= idxEnd - r; i++) {
                int max = Integer.MIN_VALUE;
                for (int k = i; k <= i+r; k++) {
                    max = Math.max(max,
                               a[i]*a[k+1]*a[i+r+2]
                               + (k > i? dp[i][k-1] : 0)
                               + (k < i+r? dp[k+1][i+r] : 0)
                              );
                }
                dp[i][i+r] = max;
            }
        }
        return dp[idxStart][idxEnd];
    }
}
