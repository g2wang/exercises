/**
287. Find the Duplicate Number
Medium

Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.

There is only one repeated number in nums, return this repeated number.

You must solve the problem without modifying the array nums and uses only constant extra space.

 

Example 1:
Input: nums = [1,3,4,2,2]
Output: 2

Example 2:
Input: nums = [3,1,3,4,2]
Output: 3

Example 2:
Input: nums = [2,2,2,2,2]
Output: 2
 
Constraints:
1 <= n <= 105
nums.length == n + 1
1 <= nums[i] <= n
All the integers in nums appear only once except for precisely one integer which appears two or more times.
 

Follow up:

How can we prove that at least one duplicate number must exist in nums?
Can you solve the problem in linear runtime complexity?
 */

import java.util.*;

public class FindDupNum {

    public static void main(String[] args) {
        int[] nums = new int[]{2,2,2,2,2};
        FindDupNum f = new FindDupNum();
        int a = f.findDuplicate(nums);
        System.out.printf("%s -> %d%n", Arrays.toString(nums), a);
    }

    /**
     * Floyd's Tortoise and Hare (Cycle Detection) algorithm
     */
    public int findDuplicate(int[] nums) {
        int t = 0; // tortoise
        int h = 0; // hare
        while (true) {
            t = nums[t]; 
            h = nums[nums[h]]; 
            if (t == h) break;
        }
        h = 0; // hare sent home and slowed down
        while (true) {
            t = nums[t];
            h = nums[h];
            if (t == h) return h;
        }
    }

}
