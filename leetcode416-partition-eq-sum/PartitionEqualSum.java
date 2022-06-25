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
        int[] nums = new int[]{1,5,11,5};
        boolean ans = pes.canPartition(nums);
        System.out.printf("%s -> %b%n", Arrays.toString(nums), ans);

    }

    public boolean canPartition(int[] nums) {
        if (nums.length < 2) return false;
        int min = Integer.MAX_VALUE;
        int max = 0;
        int total = 0;
        for (int n : nums) {
            if (n > max) max = n;
            if (n < min) min = n;
            total += n;
        }
        if (total % 2 != 0) return false;
        int half = total/2;
        // one subset must contain the max element
        if (max == half) return true;
        int maxPlusMin = max + min;
        if (maxPlusMin == half) return true;
        if (maxPlusMin > half) return false;
        return findSum(nums, nums.length-1, half);
    }

    public boolean findSum(int[] nums, int ub, int sum) {
        int last = nums[ub];
        if (ub == 0) return last == sum;
        if (last == sum)  return true;
        if (last > sum) return false;
        return findSum(nums, ub-1, sum - last)
            || findSum(nums, ub-1, sum);
    }

}
