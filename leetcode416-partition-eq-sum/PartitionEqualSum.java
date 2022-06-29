/**
 * 416. Partition Equal Subset Sum
Medium

Given a non-empty array nums containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

Example 1:
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
 

Constraints:

1 <= nums.length <= 200
1 <= nums[i] <= 100
 *
 */

import java.util.Arrays;
import java.util.PriorityQueue;

public class PartitionEqualSum {

    public static void main(String[] args) {
        PartitionEqualSum pes = new PartitionEqualSum();
        int[] nums = new int[]{1,2,3};
        boolean ans = pes.canPartition(nums);
        System.out.printf("%s -> %b%n", Arrays.toString(nums), ans);

    }

    public boolean canPartition(int[] nums) {
        if (nums.length < 2) return false;
        int total = 0;
        for (int n : nums) {
            total += n;
        }
        if (total % 2 != 0) return false;
        int half = total/2;
        boolean dp[][] = new boolean[nums.length][half+1];
        for (int i = 0; i < nums.length; i++) {
            dp[i][0] = true;
            for (int j = 1; j <= half; j++) {
                if (i == 0) {
                    if (nums[i] == j) dp[i][j] = true;
                } else {
                    if (nums[i] > j) {
                        dp[i][j] = dp[i-1][j];
                    } else if (nums[i] == j || dp[i-1][j] || dp[i-1][j-nums[i]]) {
                        dp[i][j] = true;
                    }
                }
            }
        }
        return dp[nums.length-1][half];
    }

}
