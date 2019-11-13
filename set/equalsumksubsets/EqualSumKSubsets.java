/*
698. Partition to K Equal Sum Subsets

Given an array of integers nums and a positive integer k, find whether it's
possible to divide this array into k non-empty subsets whose sums are all
equal.

Example 1:
Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 
Note:
1 <= k <= len(nums) <= 16.
0 < nums[i] < 10000.

*/

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class EqualSumKSubsets {
    public static void main(String[] args) {
        int k = 4;
        int[] nums = new int[]{4, 3, 3, 2, 3, 1};
        EqualSumKSubsets ess =  new EqualSumKSubsets();
        boolean ans = ess.canPartitionKSubsets(nums, k); 
        System.out.printf("can %d-partition: %b <- %s\n", k, ans, Arrays.toString(nums));
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums.length < k) return false;
        int total = 0;
        for (int n : nums) {
            total += n;
        } 
        if (total % k != 0) return false;
        int s = total/k;

        boolean[] used = new boolean[nums.length];
        for (int i = 0; i < k-1; i++) {
            if (!findSum(nums, used, s))
                return false;
        }
        return true;
    }

    public boolean findSum(int[] nums, boolean[] used, int s) {
        return find(nums, nums.length-1, used, s); 
    }
    
    public boolean find(int[] nums, int i, boolean[] used, int s) {
        if (i < 0) return false;
        if (!used[i]) {
            if (i == 0) {
                if (nums[i] == s) {
                    used[i] = true; return true;
                } else {
                    used[i] = false; return false;
                }
            }
            if (nums[i] == s) {
                used[i] = true; return true;}
            if (nums[i] > s) {
                used[i] = false; return false;
            }
            if (find(nums, i-1, used, s - nums[i])) {
                used[i] = true; return true;
            } else if (find(nums, i-1, used, s)) {
                used[i] = false; return true;
            } else {
                used[i] = false; return false;
            }
        } else {
            return find(nums, i-1, used, s);
        }
    }

}
