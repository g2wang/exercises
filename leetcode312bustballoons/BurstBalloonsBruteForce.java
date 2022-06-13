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

public class BurstBalloonsBruteForce {

    public static void main(String[] args) {
        // int[] nums = new int[]{3, 1, 5};
        // int[] nums = new int[]{3, 1, 5, 8};
        int[] nums = new int[]{7,9,8,0,7,1,3,5,5,2,3};
        BurstBalloonsBruteForce bb = new BurstBalloonsBruteForce();
        int ans = bb.maxCoins(nums);
        System.out.printf("%s->%d%n", Arrays.toString(nums), ans);
    }

    public int maxCoins(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>(n+2);
        list.add(1);
        for (int i : nums) {
            list.add(i);
        }
        list.add(1);
        return maxCoins(list);
    }

    private int maxCoins(List<Integer> list) {
        int n = list.size();
        if (n == 2) return 0;
        int max = 0;
        for (int i = 1; i < n-1; i++) {
            List<Integer> blist = new ArrayList<>(n-1);
            for (int j = 0; j < n; j++) {
                if (j != i) blist.add(list.get(j));
            }
            int contrib = list.get(i-1)*list.get(i)*list.get(i+1);
            int bval = maxCoins(blist);
            if (i == 1) {
                max = contrib + bval;
            } else {
                max = Math.max(contrib + bval, max);
            }
        }
        return max;
    }
}
