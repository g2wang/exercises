/*
416. Partition Equal Subset Sum

Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

Note:

Each of the array element will not exceed 100.
The array size will not exceed 200.
 

Example 1:

Input: [1, 5, 11, 5]

Output: true

Explanation: The array can be partitioned as [1, 5, 5] and [11].
 

Example 2:

Input: [1, 2, 3, 5]

Output: false

Explanation: The array cannot be partitioned into equal sum subsets.
*/

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class EqualSumSubsets2 {
    public static void main(String[] args) {
        //int[] nums = new int[]{1, 5, 11, 5}; 
        //int[] nums = new int[]{1, 1, 1, 5, 8, 8}; 
        //int[] nums = new int[]{1, 2, 3, 4, 5, 6,7}; 
        //int[] nums = new int[]{1,1,2,5,5,5,5}; 
        //int[] nums = new int[]{28,63,95,30,39,16,36,44,37,100,61,73,32,71,100,2,37,60,23,71,53,70,69,82,97,43,16,33,29,5,97,32,29,78,93,59,37,88,89,79,75,9,74,32,81,12,34,13,16,15,16,40,90,70,17,78,54,81,18,92,75,74,59,18,66,62,55,19,2,67,30,25,64,84,25,76,98,59,74,87,5,93,97,68,20,58,55,73,74,97,49,71,42,26,8,87,99,1,16,79}; 
        int[] nums = new int[]{39,68,6,73,8,81,90,12,60,87,20,84,83,8,55,62,97,8,77,51,71,96,3,29,90,63,2,14,38,60,33,34,79,41,83,32,17,67,63,97,23,16,19,8,95,57,56,96,31,85,47,19,86,60,68,11,84,5,70,87,70,49,30,86,63,90,73,70,86,49,98,91,57,48,98,35,22,23,78,40,96,82,94,14,78,49,43,12,53,23,22,90,87,92,1,39,24,7,54,84};
        EqualSumSubsets2 ess =  new EqualSumSubsets2();
        boolean ans = ess.canPartition(nums); 
        System.out.printf("can partition: %b <- %s\n", ans, Arrays.toString(nums));
    }

    public boolean canPartition(int[] nums) {
        int total = 0;
        for (int n : nums) total += n;
        if (total % 2 != 0) return false;
        int half = total / 2;
        return findSum(nums, nums.length-1, half);
    }

    public boolean findSum(int[] nums, int ub, int sum){
        int last = nums[ub];
        if (ub == 0) return last == sum;
        if (last == sum) return true;
        if (last > sum) return false; 
        return findSum(nums, ub-1, sum - last) || findSum(nums, ub-1, sum);
        //return findSum(nums, ub-1, sum) || findSum(nums, ub-1, sum - last); 
    }
}
