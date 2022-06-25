/**
 *
523. Continuous Subarray Sum
Medium

Given an integer array nums and an integer k, return true if nums has a continuous subarray of size at least two whose elements sum up to a multiple of k, or false otherwise.

An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.

Example 1:
Input: nums = [23,2,4,6,7], k = 6
Output: true
Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up to 6.

Example 2:
Input: nums = [23,2,6,4,7], k = 6
Output: true
Explanation: [23, 2, 6, 4, 7] is an continuous subarray of size 5 whose elements sum up to 42.
42 is a multiple of 6 because 42 = 7 * 6 and 7 is an integer.

Example 3:
Input: nums = [23,2,6,4,7], k = 13
Output: false

Constraints:

1 <= nums.length <= 105
0 <= nums[i] <= 109
0 <= sum(nums[i]) <= 231 - 1
1 <= k <= 231 - 1

 */
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class SubarraySumModK {

    public static void main(String[] args) {
        int[] nums = new int[]{23,2,6,4,7}; 
        int k = 13;
        SubarraySumModK ssm = new SubarraySumModK();
        boolean ans = ssm.checkSubarraySum(nums, k);
        System.out.printf("%s -> %b%n", Arrays.toString(nums), ans);
    }

    /**
     * Runtime: 16 ms, faster than 89.58% of Java online submissions for Continuous Subarray Sum.
     * Memory Usage: 56.9 MB, less than 95.52% of Java online submissions for Continuous Subarray Sum.
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums.length <= 1) return false;
        Set<Integer> prefixSumModK = new HashSet<>();
        int sum = 0;
        int prevSumModK = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // System.out.printf("sum: %d%n" , sum);
            int sumModK = sum%k;
            if (prefixSumModK.contains(sumModK)) return true; 
            prefixSumModK.add(prevSumModK); 
            prevSumModK = sumModK;
        }
        return false;
    }
}
